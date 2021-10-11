package it.univpm.eventchain.controller;

import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.Principal;
import java.sql.SQLException;
import java.util.List;
import java.util.function.Function;

import javax.xml.bind.ValidationException;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import it.univpm.eventchain.contract.NFTicket;
import it.univpm.eventchain.contract.PurchaseRequestContract;
import it.univpm.eventchain.controller.EventController.Web3CredentialsRequest;
import it.univpm.eventchain.model.Member;
import it.univpm.eventchain.model.PurchaseRequest;
import it.univpm.eventchain.model.PurchaseRequest.PurchaseRequestState;
import it.univpm.eventchain.model.Ticket;
import it.univpm.eventchain.repository.MemberRepository;
import it.univpm.eventchain.repository.PurchaseRequestRepository;
import it.univpm.eventchain.repository.TicketRepository;
import it.univpm.eventchain.service.Web3Service;
import it.univpm.eventchain.view.View;
import kotlin.jvm.internal.Intrinsics;

@RestController
public class PurchaseRequestController {
	
	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private PurchaseRequestRepository purchaseRequestRepository;
	
	@Autowired
	private TicketRepository ticketRepository;

	@Autowired
	private Web3Service web3Service;
	
	public static final String BUSY_ERROR_MSG = "You already have a pending request!!!";
	public static final String GENERIC_ERROR_MSG = "Not found!!!";
	public static final String SQL_ERROR_MSG = "SQL saving error!!!";
		
	@NotNull
	public static final boolean isOwner(@NotNull final Principal principal,@NotNull final PurchaseRequest purchaseRequest) {
		return isSomeone(principal,purchaseRequest,x->(x.getTicketBuyer()));
	}
	
	@NotNull
	public static final boolean isSomeone(@NotNull final Principal principal,
										  @NotNull final PurchaseRequest purchaseRequest, 
										  @NotNull final Function<PurchaseRequest,Member> funcMember) {
		Intrinsics.checkParameterIsNotNull(principal,"principal");
		Intrinsics.checkParameterIsNotNull(purchaseRequest,"purchaseRequest");		
		Intrinsics.checkParameterIsNotNull(funcMember,"funcMember");
		String email = MemberController.getPrincipalEmail(principal);
		return email!=null &&
			   email.equals(funcMember.apply(purchaseRequest).getUsername());
	}
	
	@NotNull
	public static final boolean isReseller(@NotNull final Principal principal,@NotNull final PurchaseRequest purchaseRequest) {
		return isSomeone(principal,purchaseRequest,x->(x.getTicketReseller()));
	}
	
	@NotNull
	public final boolean isCurrentRequest(@NotNull final Principal principal,@NotNull final PurchaseRequest purchaseRequest,@NotNull final PurchaseRequestState status) {
		Intrinsics.checkParameterIsNotNull(status,"status");
		return isReseller(principal,purchaseRequest) && 
			   purchaseRequest.equals(purchaseRequestRepository.findFirstByEventAndTicketResellerAndStatusOrderByPriorityAsc(purchaseRequest.getEvent(),purchaseRequest.getTicketReseller(),status).orElseThrow());
	}
	
	@JsonView(value = View.Public.class)
	@GetMapping("/purchaseRequests")
	@PreAuthorize("hasRole('ROLE_TICKET_BUYER')")
	public List<PurchaseRequest> findMyPurchaseRequest(@NotNull final Principal principal){
		String email = MemberController.getPrincipalEmail(principal);
		Member ticketBuyer = memberRepository.findByUsername(email);
		return purchaseRequestRepository.findByTicketBuyerOrderByTimestampDesc(ticketBuyer);
	}
	
	@JsonView(value = View.Public.class)
	@GetMapping("/resellerPurchaseRequests")
	@PreAuthorize("hasRole('ROLE_TICKET_RESELLER')")
	public List<PurchaseRequest> resellerPurchaseRequests(@NotNull final Principal principal){
		String email = MemberController.getPrincipalEmail(principal);
		Member ticketReseller = memberRepository.findByUsername(email);
		return purchaseRequestRepository.findByTicketResellerOrderByTimestampDesc(ticketReseller);
	}
	
	
			
	@JsonView(value = View.Public.class)
	@PutMapping("/purchaseRequest/{purchaseId}/accept/{ticketId}")
	@PreAuthorize("hasRole('ROLE_TICKET_RESELLER')")
	@Transactional(rollbackFor = { SQLException.class })
	public PurchaseRequest acceptPurchaseRequest(@PathVariable final String purchaseId, 
												 @PathVariable final Long ticketId,
												 @NotNull final Principal principal) throws ValidationException, GeneralSecurityException {
		PurchaseRequest purchaseRequest = purchaseRequestRepository.findById(purchaseId).orElseThrow();
		Ticket ticket = ticketRepository.findById(ticketId).orElseThrow();
		if (isCurrentRequest(principal, purchaseRequest,PurchaseRequestState.INIT)) {
			purchaseRequest.stateAcceptRequest(ticket);
			ticketRepository.save(purchaseRequest.getTicket());
			return this.purchaseRequestRepository.save(purchaseRequest);
		} else {
			throw new GeneralSecurityException(GENERIC_ERROR_MSG);
		}
	}
	
	@JsonView(value = View.Public.class)
	@PutMapping("/purchaseRequest/{id}/refuse")
	@PreAuthorize("hasRole('ROLE_TICKET_RESELLER')")
	public PurchaseRequest refusePurchaseRequest(@PathVariable final String id,
												 @RequestBody final Web3CredentialsRequest request, 
												 @NotNull final Principal principal) throws Exception {
		PurchaseRequest purchaseRequest = purchaseRequestRepository.findById(id).orElseThrow();
		if (isCurrentRequest(principal, purchaseRequest,PurchaseRequestState.INIT)) {
			PurchaseRequestContract purchaseRequestContract = PurchaseRequestContract.load(id, web3Service.getWeb3j(),
					request.getCredentials(), web3Service.getDefaultGasProvider());
			purchaseRequest.stateRefuseRequest(request.getText());
			purchaseRequestContract.failedRequest(request.getText()).send();
			return this.purchaseRequestRepository.save(purchaseRequest);
		} else {
			throw new GeneralSecurityException(GENERIC_ERROR_MSG);
		}
	}
	
	@JsonView(value = View.Public.class)
	@PutMapping("/purchaseRequest/{id}/buy/{proceed}")
	@PreAuthorize("hasRole('ROLE_TICKET_BUYER')")
	@Transactional(rollbackFor = { SQLException.class })
	public PurchaseRequest buyPurchaseRequest(@PathVariable final String id,
											  @PathVariable final Boolean proceed,
											  @NotNull final Principal principal) throws ValidationException, GeneralSecurityException{
		PurchaseRequest purchaseRequest = purchaseRequestRepository.findById(id).orElseThrow();
		if (isOwner(principal, purchaseRequest)) {
			purchaseRequest.statePayedRequest(proceed);
			ticketRepository.save(purchaseRequest.getTicket());
			return this.purchaseRequestRepository.save(purchaseRequest);
		} else {
			throw new GeneralSecurityException(GENERIC_ERROR_MSG);
		}
	}
	
	@JsonView(value = View.Public.class)
	@PutMapping("/purchaseRequest/{id}/complete")
	@PreAuthorize("hasRole('ROLE_TICKET_RESELLER')")
	public PurchaseRequest completePurchaseRequest(@PathVariable final String id,
												   @RequestBody final Web3CredentialsRequest request, 
												   @NotNull final Principal principal) throws Exception {
		PurchaseRequest purchaseRequest = purchaseRequestRepository.findById(id).orElseThrow();		
		if (isCurrentRequest(principal, purchaseRequest,PurchaseRequestState.ACCEPTED)) {
			Ticket ticket = purchaseRequest.getTicket();
			if (purchaseRequest.getPaymentTime() != null) {				
				String uri = "https://localhost:8081/tickets/"+ purchaseRequest.getTicket().getId();
				purchaseRequest.stateSucceedRequest(NFTicket
						.deploy(web3Service.getWeb3j(), request.getCredentials(), web3Service.getDefaultGasProvider(),
								purchaseRequest.getId(), purchaseRequest.getTicket().getType(),
								BigInteger.valueOf(purchaseRequest.getTicket().getPrice()),
								purchaseRequest.getTicket().getTaxSeal(), uri)
						.send().getContractAddress());
				NFTicket nfTicket = NFTicket.load(purchaseRequest.getTicket().getTokenAddress(), web3Service.getWeb3j(),
									request.getCredentials(), web3Service.getDefaultGasProvider());
				nfTicket.addTicket().send();
			} else {
				PurchaseRequestContract purchaseRequestContract = PurchaseRequestContract.load(id, web3Service.getWeb3j(),
																  request.getCredentials(), web3Service.getDefaultGasProvider());
				purchaseRequest.stateFailedRequest(request.getText());
				purchaseRequestContract.failedRequest(request.getText()).send();
			}
			try {
				ticketRepository.save(ticket);
				return this.purchaseRequestRepository.save(purchaseRequest);
			} catch (Exception e) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				throw new SQLException(SQL_ERROR_MSG);
			}
		} else {
			throw new GeneralSecurityException(GENERIC_ERROR_MSG);
		}
	}
}
