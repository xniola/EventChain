package it.univpm.eventchain.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.function.Function;
import java.util.Objects;
import java.util.concurrent.Callable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.Size;
import javax.xml.bind.ValidationException;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.fasterxml.jackson.annotation.JsonView;

import it.univpm.eventchain.view.View;
import kotlin.jvm.internal.Intrinsics;

@Entity
public final class PurchaseRequest implements Serializable{

	private static final long serialVersionUID = 3942337176157433921L;
	private static final String STATE_MSG_ERROR = "Not valid state changing!";
	
	@JsonView(View.Public.class)
	@Id
	@Column(length = 42)
    @Size(min = 42, max = 42)
	@NotNull
	private final String id;
	
	@JsonView(View.Public.class)
	@NotNull
	@JoinColumn
    @ManyToOne
	private final Event event;
	
	@JsonView(View.Public.class)
	@NotNull
	@JoinColumn
	@ManyToOne	
	private final Member ticketBuyer;
	
	@JsonView(View.Public.class)
	@NotNull
	@JoinColumn
	@ManyToOne	
	private final Member ticketReseller;
	
	@JsonView(View.Public.class)
	@Column(length = 100)
    @Size(min = 1, max = 100)
	@NotNull
	private final String description;
	
	@JsonView(View.Public.class)
	@Column
	@NotNull
	private final BigInteger priority;
			
	@JsonView(View.Public.class)
	@Column
	@Nullable
	private Timestamp paymentTime;
	
	@JsonView(View.Public.class)
	@Column
	@Nullable
	private final Timestamp timestamp;
	
	@JsonView(View.Public.class)
	@Column(length = 100)
    @Size(min = 1, max = 100)
	@Nullable
	private String responseText;
	
	@JsonView(View.Public.class)
	@JoinColumn
	@OneToOne
	@Nullable
	private Ticket ticket;
	
	@JsonView(View.Public.class)
	@Column
	@NotNull
	private PurchaseRequestState status;
		
	@JsonView(View.Public.class)
	@Column
	@NotNull
	private Integer failedPayment;
	
	private final void setPaymentTime() throws ValidationException {
		if (this.paymentTime!=null) {
			throw new ValidationException("Pyament time already set");
		}else {		
			this.paymentTime = new Timestamp(System.currentTimeMillis());		
		}
	}
	
	private final void setResponseText(@NotNull final String responseText) throws ValidationException {
		Intrinsics.checkParameterIsNotNull(responseText, "responseText");
		if (this.responseText!=null) {
			throw new ValidationException("Response text already set");
		}else {		
			this.responseText = responseText;		
		}
	}
	
	public PurchaseRequest() throws Exception {
		this(() -> (StringUtils.EMPTY),new Event(),new Member(Arrays.asList(Member.ROLE_TICKET_BUYER)),
			 new Member(Arrays.asList(Member.ROLE_TICKET_RESELLER)),StringUtils.EMPTY,x -> (BigInteger.ONE));
	}

	public PurchaseRequest(@NotNull final Callable<String> funcId,@NotNull final Event event, 
						   @NotNull final Member ticketBuyer,@NotNull final Member ticketReseller,
						   @NotNull final String description,@NotNull final Function<String,BigInteger> funcPriority ) throws Exception {
		super();
		Intrinsics.checkParameterIsNotNull(funcId, "funcId");
		Intrinsics.checkParameterIsNotNull(event, "event");
		Intrinsics.checkParameterIsNotNull(ticketBuyer, "ticketBuyer");
		Intrinsics.checkParameterIsNotNull(ticketReseller, "ticketReseller");
		Intrinsics.checkParameterIsNotNull(description, "description");
		Intrinsics.checkParameterIsNotNull(funcPriority, "funcPriority");
		if (event.isOpened()) {
			this.event = event;
		} else {
			throw new ValidationException("Event is not opened!!!");
		}
		if (ticketBuyer.isTicketBuyer()) {
			this.ticketBuyer = ticketBuyer;
		} else {
			throw new ValidationException("Not valid ticket buyer: " +ticketBuyer.getId());
		}
		if (ticketReseller.isTicketReseller()) {
			this.ticketReseller = ticketReseller;
		}else{
			throw new ValidationException("Not valid ticket reseller: " +ticketReseller.getId());
		}		
		this.description = description;
		this.status=PurchaseRequestState.INIT;
		this.id = funcId.call();
		this.priority = funcPriority.apply(this.id);		
		if (this.priority.compareTo(BigInteger.ZERO)<=0){
			throw new ValidationException("Not valid priority: " + this.priority);
		}	
		this.failedPayment=0;
		this.timestamp=new Timestamp(System.currentTimeMillis());
	}

	@NotNull
	public final String getId() {
		return this.id;
	}

	@NotNull
	public final Event getEvent() {
		return this.event;
	}

	@NotNull
	public final Member getTicketBuyer() {
		return this.ticketBuyer;
	}

	@NotNull
	public final Member getTicketReseller() {
		return this.ticketReseller;
	}

	@NotNull
	public final BigInteger getPriority() {
		return this.priority;
	}
	
	@NotNull
	public final Timestamp getTimestamp() {
		return this.timestamp;
	}

	@NotNull
	public final String getDescription() {
		return this.description;
	}
	@Nullable	
	public final String getRequestResponse() {
		return this.responseText;
	}
	
	@Nullable	
	public final Timestamp getPaymentTime() {
		return this.paymentTime;
	}
	
	@Nullable
	public final Ticket getTicket() {
		return this.ticket;
	}
		
	@NotNull
	public final PurchaseRequestState getStatus() {
		return this.status;
	}
	
	@NotNull
	public final Integer getFailedPayment() {
		return this.failedPayment;
	}
	
	@NotNull
	public final void stateAcceptRequest(@NotNull final Ticket ticket) throws ValidationException {
		Intrinsics.checkParameterIsNotNull(ticket, "ticket");
		if (this.status!=PurchaseRequestState.INIT||
			!(ticket.getEvent().equals(this.event))) {			
			throw new ValidationException(STATE_MSG_ERROR);			
		}else {			
			ticket.bookTicket(this.getTicketBuyer(), this.getTicketReseller());
			this.status=PurchaseRequestState.ACCEPTED;
			this.ticket=ticket;
		}
	}
	
	@NotNull
	public final void stateRefuseRequest(@NotNull final String responseText) throws ValidationException {
		if (this.status!=PurchaseRequestState.INIT) {
			throw new ValidationException(STATE_MSG_ERROR);
		}else {
			this.setResponseText(responseText);
			this.status=PurchaseRequestState.REFUSED;
		}
	}
	
	@NotNull
	public final void stateFailedRequest(@NotNull final String responseText) throws ValidationException {
		if (this.status!=PurchaseRequestState.ACCEPTED) {
			throw new ValidationException(STATE_MSG_ERROR);
		}else {
			this.ticket.unBookTicket();
			this.setResponseText(responseText);
			this.status=PurchaseRequestState.FAILED;
			this.ticket=null;
		}
	}
	
	@NotNull
	public final void statePayedRequest(@NotNull final Boolean proceed) throws ValidationException, NoSuchAlgorithmException {
		Intrinsics.checkParameterIsNotNull(proceed, "proceed");
		if (this.status!=PurchaseRequestState.ACCEPTED || 
		    this.paymentTime!=null) {
			throw new ValidationException(STATE_MSG_ERROR);
		}else {
			if(Boolean.TRUE.equals(proceed)) {				
				this.ticket.buyTicket();
				this.setPaymentTime();				
			}else{				
				this.failedPayment +=1;			
			}			
		}
	}
	
	@NotNull
	public final void stateSucceedRequest(@NotNull final String tokenAddress) throws ValidationException {
		if (this.status!=PurchaseRequestState.ACCEPTED) {
			throw new ValidationException(STATE_MSG_ERROR);
		}else {
			this.ticket.sellTicket(tokenAddress);
			this.setResponseText("TICKET EMITTED!");
			this.status=PurchaseRequestState.SUCCEED;
		}
	}

	@Override
	@NotNull
	public int hashCode() {
		return Objects.hash(this.id);
	}

	@Override
	@NotNull
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PurchaseRequest other = (PurchaseRequest) obj;
		return Objects.equals(this.id, other.id);
	}
		
	@Override
	@NotNull
	public String toString() {
		return "PurchaseRequest [id=" + this.id + ", event=" + this.event + ", ticketBuyer=" + this.ticketBuyer + ", ticketReseller="
				+ this.ticketReseller + ", description=" + this.description + ", responseText="	+ this.responseText + ", paymentTime="	
				+ this.paymentTime + ", status=" + this.status + ", ticket=" + this.ticket + ", priority="
				+ this.priority + ", failedPayment=" + this.failedPayment + ", timestamp=" + this.timestamp + "]";
	}
	
	public enum PurchaseRequestState {
		INIT,ACCEPTED,REFUSED,FAILED,SUCCEED
	}	
}
