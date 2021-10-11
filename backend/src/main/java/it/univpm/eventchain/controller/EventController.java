package it.univpm.eventchain.controller;

import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.Principal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.xml.bind.ValidationException;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.crypto.Credentials;

import com.fasterxml.jackson.annotation.JsonView;

import it.univpm.eventchain.contract.EventContract;
import it.univpm.eventchain.contract.EventResellerContract;
import it.univpm.eventchain.model.Comment;
import it.univpm.eventchain.model.Event;
import it.univpm.eventchain.model.EventReseller;
import it.univpm.eventchain.model.Member;
import it.univpm.eventchain.model.PurchaseRequest;
import it.univpm.eventchain.model.Ticket;
import it.univpm.eventchain.model.Ticket.TicketState;
import it.univpm.eventchain.repository.CommentRepository;
import it.univpm.eventchain.repository.EventRepository;
import it.univpm.eventchain.repository.EventResellerRepository;
import it.univpm.eventchain.repository.MemberRepository;
import it.univpm.eventchain.repository.PurchaseRequestRepository;
import it.univpm.eventchain.repository.TicketRepository;
import it.univpm.eventchain.service.Web3Service;
import it.univpm.eventchain.view.View;
import kotlin.jvm.internal.Intrinsics;

@RestController
public class EventController {

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private EventRepository eventRepository;
	
	@Autowired
	private EventResellerRepository eventResellerRepository;

	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private PurchaseRequestRepository purchaseRequestRepository;

	@Autowired
	private TicketRepository ticketRepository;
	
	@Autowired
	private Web3Service web3Service;

	private static final String VAR_EVENT_NAME = "event";

	public static final String GENERIC_ERROR_MSG = "Not found!!!";
	
	@NotNull
	public static final boolean isOwner(@NotNull final String email, @NotNull final Event event) {
		Intrinsics.checkParameterIsNotNull(email,"email");
		Intrinsics.checkParameterIsNotNull(event, VAR_EVENT_NAME);
		return email.equals(event.getEventManager().getUsername());
	}
	@NotNull
	public static final boolean isOwner(@NotNull final Principal principal, @NotNull final Event event) {		
		return isOwner(MemberController.getPrincipalEmail(principal),event);
	}
	@NotNull
	public static final boolean isEditable(@NotNull final Principal principal,@NotNull final Event event) {
		return isOwner(principal,event)&&event.isOpened();
	}
	@NotNull
	public static final boolean isDeletable(@NotNull final Principal principal,@NotNull final Event event, @NotNull final PurchaseRequestRepository purchaseRequestRepository) {
		Intrinsics.checkParameterIsNotNull(purchaseRequestRepository, "purchaseRequestRepository");
		return isEditable(principal,event)&&(purchaseRequestRepository.findByEvent(event).isEmpty());
	}

	@JsonView(value = View.Public.class)
	@GetMapping("/events")
	@PreAuthorize("hasRole('ROLE_USER')")
	public List<Event> findActiveEvents() {
		return this.eventRepository.findByEndAfter(new Timestamp(System.currentTimeMillis()));
	}
	
	@JsonView(value = View.Public.class)
	@GetMapping("/events/old")
	@PreAuthorize("hasRole('ROLE_USER')")
	public List<Event> findInactiveEvents() {
		return this.eventRepository.findByEndBefore(new Timestamp(System.currentTimeMillis()));
	}
	
	@JsonView(value = View.Public.class)
	@GetMapping("/myEvents")
	@PreAuthorize("hasRole('ROLE_EVENT_MANAGER')")
	public List<Event> findMyEvents(@NotNull final Principal principal) {
		String email = MemberController.getPrincipalEmail(principal);
		Member eventManager = memberRepository.findByUsername(email);
		return eventRepository.findByEventManagerOrderByEndDesc(eventManager);
	}

	@JsonView(value = View.Public.class)
	@GetMapping("/event/{id}")
	@PreAuthorize("hasRole('ROLE_USER')")	
	public Optional<Event> show(@PathVariable final String id) {
		return this.eventRepository.findById(id);
	}
	
	@JsonView(value = View.Public.class)
	@PostMapping("/event/create")
	@PreAuthorize("hasRole('ROLE_EVENT_MANAGER')")
	public Event create(@RequestBody final EventRequest request, @NotNull final Principal principal)
			throws Exception {
		String email = MemberController.getPrincipalEmail(principal);
		Member eventManager = memberRepository.findByUsername(email);
		Event event = new Event(
				() -> (EventContract
						.deploy(web3Service.getWeb3j(), request.getCredentials(), web3Service.getDefaultGasProvider(),
								request.getTitle(), BigInteger.valueOf(request.getStart().getTime()),
								BigInteger.valueOf(request.getEnd().getTime()), request.getLocation())
						.send().getContractAddress()),
				eventManager, request.getTitle(), request.getDescription(), request.getStart(), request.getEnd(),
				request.getLocation());	
		
		return this.eventRepository.save(event);
	}
	
	@JsonView(value = View.Public.class)
	@GetMapping("/event/{id}/comments")
	@PreAuthorize("hasRole('ROLE_USER')")
	public List<Comment> showComments(@PathVariable final String id) {
		return this.commentRepository.findByEvent(eventRepository.findById(id).orElseThrow());
	}
	
	@JsonView(value = View.Public.class)
	@PostMapping("/event/{id}/createComment")
	@PreAuthorize("hasRole('ROLE_USER')")
	public Comment createComment(@PathVariable final String id,
			                     @RequestBody final CommentController.CommentRequest request,
						  		 @NotNull final Principal principal){
		String email = MemberController.getPrincipalEmail(principal);
		Member sender = memberRepository.findByUsername(email);		
		return this.commentRepository.save(new Comment(eventRepository.findById(id).orElseThrow(),request.getBody(),sender));
	}
	
	@JsonView(value = View.Public.class)
	@PostMapping("/event/{id}/createEventReseller")
	@PreAuthorize("hasRole('ROLE_TICKET_RESELLER')")
	public EventReseller createEventReseller(@PathVariable final String id,
											  @RequestBody final Web3CredentialsRequest request,
											  @NotNull final Principal principal) throws Exception {
		String email = MemberController.getPrincipalEmail(principal);
		Member ticketReseller = memberRepository.findByUsername(email);
		Event event = eventRepository.findById(id).orElseThrow();
		EventReseller eventReseller = new EventReseller(
				() -> (EventResellerContract.deploy(web3Service.getWeb3j(), request.getCredentials(),
						web3Service.getDefaultGasProvider(), event.getId()).send().getContractAddress()),
				event, ticketReseller);
	  return this.eventResellerRepository.save(eventReseller);			
	
	}
	
	@JsonView(value = View.Public.class)
	@GetMapping("/event/{id}/findNewEventReseller")
	@PreAuthorize("hasRole('ROLE_EVENT_MANAGER')")
	public List<EventReseller> findNewEventReseller(@PathVariable final String id,
											  		@NotNull final Principal principal) throws GeneralSecurityException {
		Event event = eventRepository.findById(id).orElseThrow();
		if (isOwner(principal, event)) {
			return eventResellerRepository.findByEventAndAccepted(event, false);
		} else {
			throw new GeneralSecurityException(EventController.GENERIC_ERROR_MSG);
		}
	}
	
	@JsonView(value = View.Public.class)
	@GetMapping("/event/{id}/resellers")
	@PreAuthorize("hasRole('ROLE_TICKET_BUYER')")
	public List<EventReseller> findEventReseller(@PathVariable final String id,
											  	 @NotNull final Principal principal){
		Event event = eventRepository.findById(id).orElseThrow();		
		return eventResellerRepository.findByEventAndAccepted(event, true);		
	}
			
	@JsonView(value = View.Public.class)
	@GetMapping("/event/{id}/tickets")
	@PreAuthorize("hasRole('ROLE_USER')")
	public List<Ticket> ticketByEvents(@PathVariable final String id,@NotNull final Principal principal) throws GeneralSecurityException {
		Event event = eventRepository.findById(id).orElseThrow();
		if (isOwner(principal, event)) {
			return this.ticketRepository.findByEvent(event);
		} else {
			if (event.isOpened()) {
				return this.ticketRepository.findByEventAndState(event, TicketState.BUYABLE);
			} else {
				throw new GeneralSecurityException(EventController.GENERIC_ERROR_MSG);
			}
		}		
	}
	
	@JsonView(value = View.Public.class)
	@GetMapping("/event/{id}/purchaseRequests")
	@PreAuthorize("hasRole('ROLE_TICKET_BUYER')")
	public List<PurchaseRequest> findMyPurchaseRequest(@PathVariable final String id,@NotNull final Principal principal){
		Event event = eventRepository.findById(id).orElseThrow();
		String email = MemberController.getPrincipalEmail(principal);
		Member ticketBuyer = memberRepository.findByUsername(email);
		return this.purchaseRequestRepository.findByEventAndTicketBuyerOrderByPriorityDesc(event,ticketBuyer);
	}
	
	@JsonView(value = View.Public.class)
	@GetMapping("/event/{id}/resellerPurchaseRequests")
	@PreAuthorize("hasRole('ROLE_TICKET_RESELLER')")
	public List<PurchaseRequest> resellerFindPurchaseRequest(@PathVariable final String id,@NotNull final Principal principal){
		Event event = eventRepository.findById(id).orElseThrow();
		String email = MemberController.getPrincipalEmail(principal);
		Member ticketReseller = memberRepository.findByUsername(email);
		return this.purchaseRequestRepository.findByEventAndTicketResellerOrderByPriorityDesc(event,ticketReseller);
	}
	
	@JsonView(value = View.Public.class)
	@PostMapping("/event/{id}/createTickets/{qty}")
	@PreAuthorize("hasRole('ROLE_EVENT_MANAGER')")
	public List<Ticket> createTickets(@PathVariable final String id, @PathVariable final Integer qty,
			@RequestBody final TicketController.TicketRequest request, @NotNull final Principal principal)
			throws GeneralSecurityException, ValidationException {
		Event event = this.eventRepository.findById(id).orElseThrow();
		if (isEditable(principal, event)) {
			List<Ticket> tickets = new ArrayList<>();
			for (int i = 0; i < qty; i++) {
				tickets.add(new Ticket(event, request.getPrice(), request.getType()));
			}
			return ticketRepository.saveAll(tickets);
		} else {
			throw new GeneralSecurityException(GENERIC_ERROR_MSG);
		}
	}
	
	@JsonView(value = View.Public.class)
	@PutMapping("/event/{id}/update")
	@PreAuthorize("hasRole('ROLE_EVENT_MANAGER')")	
	public Event update(@PathVariable final String id, 
			            @RequestBody final EventRequest request, 
			            @NotNull final Principal principal)
			throws Exception {
		Optional<Event> optEvent = this.eventRepository.findById(id);
		if (optEvent.isPresent()) {
			Event event = optEvent.get();
			if (isEditable(principal, event)) {
				event.setTitle(request.getTitle());
				event.setDescription(request.getDescription());
				event.setStartEnd(request.getStart(),request.getEnd());				
				event.setLocation(request.getLocation());
				EventContract eventContract = EventContract.load(id, web3Service.getWeb3j(), request.getCredentials(), web3Service.getDefaultGasProvider());
				eventContract.updateEvent(request.getTitle(),BigInteger.valueOf(request.getStart().getTime()),
						                  BigInteger.valueOf(request.getEnd().getTime()), request.getLocation()).send();
				return this.eventRepository.save(event);
			} else {
				throw new GeneralSecurityException(GENERIC_ERROR_MSG);
			}
		} else {
			throw new GeneralSecurityException(GENERIC_ERROR_MSG);
		}
	}
	
	@PutMapping("/event/{id}/close")
	@PreAuthorize("hasRole('ROLE_EVENT_MANAGER')")
	public Event close(@PathVariable final String id,
			 			 @RequestBody final Web3CredentialsRequest request,
						 @NotNull final Principal principal) throws Exception {
		Optional<Event> optEvent = this.eventRepository.findById(id);
		if (optEvent.isPresent()) {
			Event event = optEvent.get();
			if (isEditable(principal, event)) {
				EventContract eventContract = EventContract.load(id, web3Service.getWeb3j(), request.getCredentials(), web3Service.getDefaultGasProvider());
				eventContract.closeEvent().send();
				event.closeMe();
				return this.eventRepository.save(event);
			} else {
				throw new GeneralSecurityException(GENERIC_ERROR_MSG);
			}
		} else {
			throw new GeneralSecurityException(GENERIC_ERROR_MSG);
		}
	}
	
	@DeleteMapping("/event/{id}/delete")
	@PreAuthorize("hasRole('ROLE_EVENT_MANAGER')")
	public boolean delete(@PathVariable final String id,
			 			  @RequestBody final Web3CredentialsRequest request,
						  @NotNull final Principal principal) throws Exception {
		Optional<Event> optEvent = this.eventRepository.findById(id);
		if (optEvent.isPresent()) {
			Event event = optEvent.get();
			if (isDeletable(principal, event, purchaseRequestRepository)) {
				EventContract eventContract = EventContract.load(id, web3Service.getWeb3j(), request.getCredentials(), web3Service.getDefaultGasProvider());
				eventContract.deleteEvent().send();
				try {
					this.eventResellerRepository.deleteAll(this.eventResellerRepository.findByEvent(event));
					this.commentRepository.deleteAll(this.commentRepository.findByEvent(event));
					this.ticketRepository.deleteAll(this.ticketRepository.findByEvent(event));
					this.eventRepository.delete(event);
					return true;
				} catch (Exception e) {
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return false;
				}
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
		    
	public static final class EventRequest {
		@NotNull
		private final String title;
		@NotNull
		private final String description;
		@NotNull
		private final Timestamp start;
		@NotNull
		private final Timestamp end;
		@NotNull
		private final String location;
		@NotNull
		private final Credentials credentials;

		public EventRequest(@NotNull final String title,@NotNull final String description,
				            @NotNull final Timestamp start,@NotNull final Timestamp end, 
				            @NotNull final String location,@NotNull final String pk) {
			super();
			Intrinsics.checkParameterIsNotNull(title, "title");
			Intrinsics.checkParameterIsNotNull(description, "description");
			Intrinsics.checkParameterIsNotNull(start, "start");
			Intrinsics.checkParameterIsNotNull(end, "end");
			Intrinsics.checkParameterIsNotNull(location, "location");
			Intrinsics.checkParameterIsNotNull(pk, "pk");
			this.title = title;
			this.description = description;
			this.start = start;
			this.end = end;
			this.location = location;
			this.credentials = Credentials.create(pk);
		}
		@NotNull
		public final String getTitle() {
			return this.title;
		}
		@NotNull
		public final String getDescription() {
			return this.description;
		}
		@NotNull
		public final Timestamp getStart() {
			return this.start;
		}
		@NotNull
		public final Timestamp getEnd() {
			return this.end;
		}
		@NotNull
		public final String getLocation() {
			return this.location;
		}		
		@NotNull
		public final Credentials getCredentials() {
			return this.credentials;
		}
	}
     
	public static final class Web3CredentialsRequest {
		
		@Nullable
		private final String text;
		
		@NotNull
		private final Credentials credentials;

		public Web3CredentialsRequest(@Nullable final String text,@NotNull final String pk) {
			super();			
			Intrinsics.checkParameterIsNotNull(pk, "pk");
			this.text =text;
			this.credentials = Credentials.create(pk);
		}
		@Nullable
		public final String getText() {
			return this.text;
		}
		@NotNull
		public final Credentials getCredentials() {
			return this.credentials;
		}
	}
}
