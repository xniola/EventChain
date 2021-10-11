package it.univpm.eventchain.controller;

import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.Principal;
import java.util.Optional;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import it.univpm.eventchain.contract.EventContract;
import it.univpm.eventchain.contract.PurchaseRequestContract;
import it.univpm.eventchain.controller.EventController.Web3CredentialsRequest;
import it.univpm.eventchain.model.Event;
import it.univpm.eventchain.model.EventReseller;
import it.univpm.eventchain.model.Member;
import it.univpm.eventchain.model.PurchaseRequest;
import it.univpm.eventchain.model.PurchaseRequest.PurchaseRequestState;
import it.univpm.eventchain.repository.EventResellerRepository;
import it.univpm.eventchain.repository.MemberRepository;
import it.univpm.eventchain.repository.PurchaseRequestRepository;
import it.univpm.eventchain.service.Web3Service;
import it.univpm.eventchain.view.View;

@RestController
public class EventResellerController {
	
	@Autowired
	private EventResellerRepository eventResellerRepository;
	
	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private PurchaseRequestRepository purchaseRequestRepository;
	
	@Autowired
	private Web3Service web3Service;	
			
	@JsonView(value = View.Public.class)
	@PutMapping("/eventReseller/{id}/accept")
	@PreAuthorize("hasRole('ROLE_EVENT_MANAGER')")
	public EventReseller acceptEventReseller(@PathVariable final String id,
			@RequestBody final Web3CredentialsRequest request, @NotNull final Principal principal) throws Exception {
		EventReseller eventReseller = eventResellerRepository.findById(id).orElseThrow();
		if (!eventReseller.isAccepted() && EventController.isEditable(principal, eventReseller.getEvent())) {
			EventContract eventContract = EventContract.load(eventReseller.getEvent().getId(), web3Service.getWeb3j(),
					request.getCredentials(), web3Service.getDefaultGasProvider());
			eventContract.addTicketReseller(id).send();
			eventReseller.acceptMe();
			return this.eventResellerRepository.save(eventReseller);
		} else {
			throw new GeneralSecurityException(EventController.GENERIC_ERROR_MSG);
		}
	}
	
	@JsonView(value = View.Public.class)
	@PostMapping("/eventReseller/{id}/createPurchaseRequest")
	@PreAuthorize("hasRole('ROLE_TICKET_BUYER')")
	public PurchaseRequest createPurchaseRequest(@PathVariable final String id,
												 @RequestBody final Web3CredentialsRequest request,
												 @NotNull final Principal principal) throws Exception {
		String email = MemberController.getPrincipalEmail(principal);
		Member ticketBuyer = memberRepository.findByUsername(email);
		EventReseller eventReseller = eventResellerRepository.findById(id).orElseThrow();
		Event event = eventReseller.getEvent();
		if(eventReseller.isAccepted() &&
		   purchaseRequestRepository.findByEventAndTicketBuyerAndStatusLessThanEqual(event,ticketBuyer,PurchaseRequestState.ACCEPTED).isEmpty()){ //a pending reqeust already exists
			
			Member ticketReseller = eventReseller.getTicketReseller();
			//priority recovery  
			Optional<PurchaseRequest> lastPurchaseRequest = purchaseRequestRepository.findFirstByEventAndTicketResellerOrderByPriorityDesc(event, ticketReseller);
			final BigInteger priorityRecover;
			if (lastPurchaseRequest.isPresent()){
				priorityRecover = BigInteger.ONE.add(lastPurchaseRequest.get().getPriority());	
			}else {
				priorityRecover = BigInteger.ONE;
			}
			
			PurchaseRequest purchaseRequest = new PurchaseRequest(
					() -> (PurchaseRequestContract.deploy(web3Service.getWeb3j(), request.getCredentials(),
							web3Service.getDefaultGasProvider(), eventReseller.getId(), request.getText()).send()
							.getContractAddress()),
					event, ticketBuyer, ticketReseller, request.getText(),
					x ->{PurchaseRequestContract purchaseRequestContract = PurchaseRequestContract.load(x, web3Service.getWeb3j(),request.getCredentials(), web3Service.getDefaultGasProvider());
							try {
								purchaseRequestContract.addRequest().send();
							} catch (Exception e) {
								return BigInteger.ZERO;
							}
							try {
								return purchaseRequestContract.requestId().send();
							} catch (Exception e) {
								return priorityRecover;
							}
					});			
			
			return this.purchaseRequestRepository.save(purchaseRequest);
		}else{
			throw new GeneralSecurityException(PurchaseRequestController.BUSY_ERROR_MSG);			
		}
	}

	
}
