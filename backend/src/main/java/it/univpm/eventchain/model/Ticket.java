package it.univpm.eventchain.model;

import java.io.Serializable;
import java.util.Objects;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.ValidationException;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import it.univpm.eventchain.view.View;
import kotlin.jvm.internal.Intrinsics;

@Entity
@Table
public final class Ticket implements Serializable{

	private static final long serialVersionUID = 3046900319624056739L;

	@JsonView(View.Public.class)
	@Nullable
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)	
	private Long id;
	
	@JsonView(View.Public.class)
	@NotNull
	@JoinColumn
    @ManyToOne
	private final Event event;
			
	@JsonView(View.Public.class)
    @NotNull
    @Column
	private final Integer price;
    
	@JsonView(View.Public.class)
    @NotNull
    @Column
	private TicketState state;
    
    @JsonView(View.Public.class)
    @Nullable
    @JoinColumn
	@ManyToOne
	private Member ticketBuyer;
    
    @JsonView(View.Public.class)
    @Nullable
    @JoinColumn
	@ManyToOne
	private Member ticketReseller;
    
    @JsonView(View.Public.class)
    @NotNull
    @Column(length = 50)
    @Size(min = 1, max = 50)
	private final String type;
    
    @JsonView(View.Public.class)
    @Nullable
    @Column(length = 200,unique=true)
    @Size(min = 1, max = 200)
	private String taxSeal;
    
    @JsonView(View.Public.class)
    @Nullable
    @Column(length = 42,unique=true)
    @Size(min = 42, max = 42)
	private String tokenAddress;

	private final void setTaxSeal() throws NoSuchAlgorithmException {
		MessageDigest messageDigest = MessageDigest.getInstance("SHA3-512");
		messageDigest.update(this.toString().getBytes());
		this.taxSeal = new String(messageDigest.digest());
	}
    
    private final void setTicketBuyer(@NotNull final Member ticketBuyer) throws ValidationException {
		Intrinsics.checkParameterIsNotNull(ticketBuyer, "ticketBuyer");
		if (ticketBuyer.isTicketBuyer()) {
			this.ticketBuyer = ticketBuyer;
		} else {			
			throw new ValidationException("Member is not a ticket buyer!!!");
		}
	}
	
	private final void setTicketReseller(@NotNull final Member ticketReseller) throws ValidationException {
		Intrinsics.checkParameterIsNotNull(ticketReseller, "ticketReseller");
		if (ticketReseller.isTicketReseller()) {
			this.ticketReseller = ticketReseller;
		} else {			
			throw new ValidationException("Member is not a ticket reseller!!!");
		}		
	}
	
	public Ticket() throws Exception{
		this(new Event(),0,StringUtils.EMPTY);
	}
		
	public Ticket(@NotNull final Event event,@NotNull final Integer price,@NotNull final String type) throws ValidationException {
		super();
		Intrinsics.checkParameterIsNotNull(event, "event");
		Intrinsics.checkParameterIsNotNull(type, "type");
		Intrinsics.checkParameterIsNotNull(price, "price");	
		this.event = event;		
		if(price>=0){
			this.price = price;
		}else{
			throw new ValidationException("Price must be not negative!!!");
		}		
		this.type = type;
		this.state=TicketState.BUYABLE;	
	}
	
	@Nullable
	public final Long getId() {
		return this.id;
	}
	
	@NotNull
	public final Event getEvent() {
		return this.event;
	}	
			
	@NotNull
	public final Integer getPrice() {
		return this.price;
	}
	
	@NotNull
	public final TicketState getState() {
		return this.state;
	}
	
	@Nullable
	public final Member getTicketBuyer() {
		return this.ticketBuyer;
	}
	
	@Nullable
	public final Member getTicketReseller() {
		return this.ticketReseller;
	}
		
	@NotNull
	public final String getType() {
		return this.type;
	}
	
	@Nullable
	public String getTaxSeal() {
		return this.taxSeal;
	}
	
	@Nullable
	public String getTokenAddress() {
		return this.tokenAddress;
	}
	
	@JsonIgnore
	@NotNull
	public final boolean isBuyable() {
		return this.state.equals(TicketState.BUYABLE) && this.event.isOpened();
	}
	
	@NotNull
	public final void bookTicket(@NotNull final Member ticketBuyer,@NotNull final Member ticketReseller) throws ValidationException {
		if (this.isBuyable()) {
			this.state = TicketState.BOOKED;
			setTicketBuyer(ticketBuyer);
			setTicketReseller(ticketReseller);
		}else{
			throw new ValidationException("Ticket is not bookable!!!");
		}
	}
	
	@NotNull
	public final void unBookTicket() throws ValidationException {
		if (this.state.equals(TicketState.BOOKED)) {
			this.state = TicketState.BUYABLE;
			this.ticketBuyer = null;
			this.ticketReseller = null;
		}else{
			throw new ValidationException("Ticket is not unbookable!!!");
		}
	}
	
	@NotNull
	public final void buyTicket() throws ValidationException, NoSuchAlgorithmException {
		if (this.state.equals(TicketState.BOOKED)) {
			this.state = TicketState.PAYED;
			setTaxSeal();
		}else{
			throw new ValidationException("Ticket is not payable!!!");
		}
	}
	
	@NotNull
	public final void sellTicket(@NotNull final String tokenAddress) throws ValidationException {
		Intrinsics.checkParameterIsNotNull(tokenAddress, "tokenAddress");
		if (this.state.equals(TicketState.PAYED)) {			
			this.state = TicketState.SELLED;
			this.tokenAddress = tokenAddress;
		}else{
			throw new ValidationException("Ticket is not sellable!!!");
		}
	}
	
	@Override
	@NotNull
	public int hashCode() {
		return Objects.hash(this.id);
	}

	@Override
	@NotNull
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ticket other = (Ticket) obj;
		return this.id == other.id;
	}

	@Override
	@NotNull
	public String toString() {
		return "Ticket [id=" + this.id + ", event=" + this.event + ", price=" + this.price + 
				", state=" + this.state + ", ticketBuyer=" + this.ticketBuyer + ", ticketReseller=" + this.ticketReseller + 
				", type=" + this.type + ", taxSeal=" + this.taxSeal + "]";
	}
	
	public enum TicketState {
		BUYABLE,BOOKED,PAYED,SELLED,VALIDATED
	}
}