package it.univpm.eventchain.controller;

import java.security.GeneralSecurityException;
import java.security.Principal;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import it.univpm.eventchain.model.Event;
import it.univpm.eventchain.model.Member;
import it.univpm.eventchain.model.Ticket;
import it.univpm.eventchain.repository.MemberRepository;
import it.univpm.eventchain.repository.TicketRepository;
import it.univpm.eventchain.view.View;
import kotlin.jvm.internal.Intrinsics;

@RestController
public class TicketController {

	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private TicketRepository ticketRepository;
	
	private static final String GENERIC_ERROR_MSG = "Ticket not found!!!";
	@NotNull
	public static final boolean isOwner(@NotNull final String email, @NotNull final Ticket ticket) {
		Intrinsics.checkParameterIsNotNull(email,"email");
		Intrinsics.checkParameterIsNotNull(ticket, "ticket");
		Member ticketBuyer = ticket.getTicketBuyer();
		return (ticketBuyer!=null)&&email.equals(ticketBuyer.getUsername());
	}
	@NotNull
	public static final boolean isOwner(@NotNull final Principal principal, @NotNull final Ticket ticket) {		
		return isOwner(MemberController.getPrincipalEmail(principal),ticket);
	}
	@NotNull		
	public static final boolean isEditable(@NotNull Principal principal,@NotNull Ticket ticket){
		return EventController.isEditable(principal,ticket.getEvent())&&
			   ticket.isBuyable();
	}
	
	@NotNull		
	public static final boolean isVisible(@NotNull Principal principal,@NotNull Ticket ticket){
		Intrinsics.checkParameterIsNotNull(ticket, "ticket");
		String email = MemberController.getPrincipalEmail(principal);
		Member ticketBuyer = ticket.getTicketBuyer();
		Member ticketReseller = ticket.getTicketReseller();
		Event event =ticket.getEvent(); 
		return event.isOpened() ||
			   EventController.isOwner(email,event)||
			   (ticketReseller != null && ticketReseller.getUsername().equals(email))||
			   (ticketBuyer != null && ticketBuyer.getUsername().equals(email));
	}
	
	@JsonView(value = View.Public.class)
	@GetMapping("/ticket/{id}")
	@PreAuthorize("hasRole('ROLE_USER')")
	public Ticket find(@PathVariable final Long id, @NotNull final Principal principal)
			throws GeneralSecurityException {
		Ticket ticket = this.ticketRepository.findById(id).orElseThrow();
		if (isVisible(principal, ticket)) {
			return ticket;
		} else {
			throw new GeneralSecurityException(GENERIC_ERROR_MSG);
		}
	}
	
	@JsonView(value = View.Public.class)
	@GetMapping("/tickets")
	@PreAuthorize("hasRole('ROLE_TICKET_BUYER')")
	public List<Ticket> findMyTicket(@NotNull final Principal principal){
		String email = MemberController.getPrincipalEmail(principal);
		Member ticketBuyer = memberRepository.findByUsername(email);
		return ticketRepository.findByTicketBuyerOrderByIdDesc(ticketBuyer);
	}
	
	@JsonView(value = View.Public.class)
	@GetMapping("/resellerTickets")
	@PreAuthorize("hasRole('ROLE_TICKET_RESELLER')")
	public List<Ticket> resellerTickets(@NotNull final Principal principal){
		String email = MemberController.getPrincipalEmail(principal);
		Member ticketReseller = memberRepository.findByUsername(email);
		return ticketRepository.findByTicketResellerOrderByIdDesc(ticketReseller);
	}
	
	@DeleteMapping("/ticket/{id}/delete")
	@PreAuthorize("hasRole('ROLE_EVENT_MANAGER')")
	public boolean delete(@PathVariable final Long id, @NotNull final Principal principal) {
		Ticket ticket = this.ticketRepository.findById(id).orElseThrow();
		if (isEditable(principal, ticket)) {
			this.ticketRepository.delete(ticket);
			return true;
		} else {
			return false;
		}
	}
			
	public static final class TicketRequest {		
		@NotNull
		private final Integer price;		
		
		@NotNull
		private final String type;
				
		public TicketRequest(@NotNull final Integer price,
				             @NotNull final String type) {
			super();
			Intrinsics.checkParameterIsNotNull(price, "price");
			Intrinsics.checkParameterIsNotNull(type, "type");
			this.price = price;
			this.type = type;
		}
		@NotNull
		public final Integer getPrice() {
			return this.price;
		}
		@NotNull
		public final String getType() {
			return this.type;
		}		
	}	
	
}
