package it.univpm.eventchain.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonView;

import it.univpm.eventchain.view.View;
import kotlin.jvm.internal.Intrinsics;

@Entity
@Table
public final class Comment implements Serializable{
	
	private static final long serialVersionUID = 6797128270384268357L;

	@JsonView(View.Public.class)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Nullable
	private Long id;
	
	@JsonView(View.Public.class)
	@NotNull
	@JoinColumn
    @ManyToOne
	private final Event event;
	
	@JsonView(View.Public.class)
	@Column(length = 500)
    @Size(min = 1, max = 500)
	@NotNull
	private String body;
	
	@JsonView(View.Public.class)
	@NotNull
	@JoinColumn
	@ManyToOne	
	private final Member sender;
	
	@JsonView(View.Public.class)
	@Column
	@NotNull
	private Timestamp timestamp;
			
	public Comment() throws Exception {
		this(new Event(),StringUtils.EMPTY,new Member());
	}
	
	public Comment(@NotNull final Event event,@NotNull final String body,@NotNull final Member sender){
		super();
		Intrinsics.checkParameterIsNotNull(event, "event");
		Intrinsics.checkParameterIsNotNull(sender, "sender");		
		this.event = event;		
		this.sender = sender;
		this.setBody(body);
		this.setTimestamp();		
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
	public final String getBody() {
		return this.body;
	}
	
	@NotNull
	public final Member getSender() {
		return this.sender;
	}
	
	@NotNull
	public Timestamp getTimestamp() {
		return this.timestamp;
	}

	public final void setTimestamp(){
		this.timestamp = new Timestamp(System.currentTimeMillis());
	}

	public final void setBody(@NotNull final String body) {
		Intrinsics.checkParameterIsNotNull(body, "body");
		this.body = body;
	}

	@Override
	@NotNull
	public String toString() {
		return "Comment [id=" + this.id + ", event=" + this.event + ", body=" + this.body + ", sender=" + this.sender
				+ ", timestamp=" + this.timestamp + "]";
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
		Comment other = (Comment) obj;
		return Objects.equals(id, other.id);
	}
			
}
