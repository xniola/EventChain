package it.univpm.eventchain.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.Callable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;
import javax.xml.bind.ValidationException;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import it.univpm.eventchain.view.View;
import kotlin.jvm.internal.Intrinsics;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "event_id", "member_id" }) })
public final class EventReseller implements Serializable{
	
	private static final long serialVersionUID = -5905384283290042884L;
		
	@JsonView(View.Public.class)
	@Column
	@NotNull
	private boolean accepted;
	
	@JsonView(View.Public.class)
	@Id
	@Column(length = 42)
    @Size(min = 42, max = 42)
	@NotNull
	private final String id;
	
	@JsonView(View.Public.class)
	@NotNull
	@JoinColumn(name="event_id")
    @ManyToOne
	private final Event event;
	
	@JsonView(View.Public.class)
	@NotNull
	@JoinColumn(name="member_id")
    @ManyToOne
	private final Member ticketReseller;
	
	public EventReseller() throws Exception{
		this(() -> (StringUtils.EMPTY),
			 new Event(),new Member(Arrays.asList(Member.ROLE_TICKET_RESELLER)));
	}
	
	public EventReseller(@NotNull final Callable<String> funcId,@NotNull final Event event,@NotNull final Member ticketReseller) throws Exception {
		super();
		Intrinsics.checkParameterIsNotNull(funcId, "funcId");
		Intrinsics.checkParameterIsNotNull(event, "event");
		if (event.isOpened()) {
			this.event = event;
		} else {
			throw new ValidationException("Event is not opened!!!");
		}
		Intrinsics.checkParameterIsNotNull(ticketReseller, "ticketReseller");
		if (ticketReseller.isTicketReseller()) {
			this.ticketReseller = ticketReseller;
		} else {
			throw new ValidationException("Not valid ticket reseller!!!");
		}
		this.accepted = false;
		this.id = funcId.call();
	}
	
	@JsonIgnore
	@NotNull
	public final Boolean isAccepted() {
		return this.accepted;
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
	public final Member getTicketReseller() {
		return this.ticketReseller;
	}
	
	public final void acceptMe() throws ValidationException{		
		if (this.accepted) {
			throw new ValidationException("Already accepted!");
		}else {			
			this.accepted = true;			
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
		EventReseller other = (EventReseller) obj;
		return Objects.equals(this.id, other.id);
	}

	@Override
	@NotNull
	public String toString() {
		return "EventReseller [accepted=" + this.accepted + ", id=" + this.id + ", event=" + this.event + ", ticketReseller="
				+ this.ticketReseller + "]";
	}
	
}
