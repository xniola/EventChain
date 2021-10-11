package it.univpm.eventchain.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.ValidationException;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import it.univpm.eventchain.view.View;
import kotlin.jvm.internal.Intrinsics;


@Entity
@Table
public final class Event implements Serializable{
			
	private static final long serialVersionUID = 1161977436679803889L;

	@JsonView(View.Public.class)
	@Id
	@Column(length = 42)
    @Size(min = 42, max = 42)
	@NotNull
	private final String id;
	
	@JsonView(View.Public.class)
	@Column(length = 100)
    @Size(min = 1, max = 100)
	@NotNull
	private String title;
	
	@JsonView(View.Public.class)
	@Column(length = 500)
    @Size(min = 1, max = 500)
	@NotNull
	private String description;
	
	@JsonView(View.Public.class)
	@Column
	@NotNull
	private Timestamp start;
	
	@JsonView(View.Public.class)
	@Column
	@NotNull
	private Timestamp end;
	
	@JsonView(View.Public.class)
	@Column(length = 100)
    @Size(min = 1, max = 100)
	@NotNull
	private String location;
			
	@JsonView(View.Public.class)
	@JoinColumn
	@ManyToOne
	@NotNull
	private final Member eventManager;
	
	@JsonView(View.Public.class)
	@Column
	@NotNull
	private boolean opened;
	
	@NotNull
	private final boolean isPast(@NotNull final Timestamp timestamp) {
		Intrinsics.checkParameterIsNotNull(timestamp, "timestamp");
		return timestamp.before(new Timestamp(System.currentTimeMillis()));
	}
	
	@NotNull
	private final String printStartEnd() {
		return "start:" + this.start + " end:"+this.end;
	}
	
	public Event() throws Exception{
		this(() -> (StringUtils.EMPTY),
			 new Member(Arrays.asList(Member.ROLE_EVENT_MANAGER)), StringUtils.EMPTY, StringUtils.EMPTY,
			 new Timestamp(System.currentTimeMillis()+TimeUnit.HOURS.toMillis(1)), 
			 new Timestamp(System.currentTimeMillis()+TimeUnit.HOURS.toMillis(2)), 
			 StringUtils.EMPTY);
	}
		
	public Event(@NotNull final Callable<String> funcId,@NotNull final Member eventManager,@NotNull final String title,
			     @NotNull final String description,@NotNull final Timestamp start,@NotNull final Timestamp end,
			     @NotNull final String location) throws Exception{
		super();
		Intrinsics.checkParameterIsNotNull(funcId, "funcId");
		Intrinsics.checkParameterIsNotNull(eventManager, "eventManager");
		if (eventManager.isEventManager()){
			this.eventManager = eventManager;
		}else {
			throw new ValidationException("Not valid Event Manager!!!");
		}
		this.setTitle(title);
		this.setDescription(description);
		this.setStartEnd(start,end);
		this.setLocation(location);
		this.opened=true;
		this.id = funcId.call();	
	}
	
	@NotNull
	public final String getTitle() {
		return this.title;
	}
	
	public final void setTitle(@NotNull final String title) {
		Intrinsics.checkParameterIsNotNull(title, "title");
		this.title = title;
	}
	
	@NotNull
	public final String getDescription() {
		return this.description;
	}

	public final void setDescription(@NotNull final String description) {
		Intrinsics.checkParameterIsNotNull(description, "description");
		this.description = description;
	}
	
	@NotNull
	public final Timestamp getStart() {
		return this.start;
	}

	public final void setStart(@NotNull final Timestamp start) throws ValidationException {
		Intrinsics.checkParameterIsNotNull(start, "start");
		if (isPast(start)||start.after(this.end)) {
			throw new ValidationException("Not valid start!!! " + printStartEnd());
		}else {			
			this.start = start;	 		
		}
	}
	
	public final void setStartEnd(@NotNull final Timestamp start,@NotNull final Timestamp end) throws ValidationException {
		Intrinsics.checkParameterIsNotNull(start, "start");
		Intrinsics.checkParameterIsNotNull(end, "end");
		if (isPast(start)||isPast(end)||end.before(start)) {
			throw new ValidationException("Not valid start/end:" + printStartEnd());
		}else {			
			this.start = start;
			this.end = end;
		}
	}
	
	@NotNull
	public final Timestamp getEnd() {
		return this.end;
	}

	public final void setEnd(@NotNull final Timestamp end) throws ValidationException {
		Intrinsics.checkParameterIsNotNull(end, "end");
		if (isPast(end)||end.before(this.start)) {
			throw new ValidationException("Not valid end!!! " + printStartEnd());
		}else {			
			this.end = end; 		
		}		
	}
	
	@NotNull
	public final String getLocation() {
		return this.location;
	}

	public final void setLocation(@NotNull final String location) {
		Intrinsics.checkParameterIsNotNull(location, "location");
		this.location = location;
	}
	
	@NotNull
	public final String getId() {
		return this.id;
	}
	
	@NotNull
	public final Member getEventManager() {
		return this.eventManager;
	}	
		
	@JsonIgnore
	@NotNull
	public final boolean isOpened() {
		return this.opened;
	}

	public final void closeMe() throws ValidationException {
		if (!this.opened) {
			throw new ValidationException("Event already closed!");
		}else {			
			this.opened = false;			
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
		Event other = (Event) obj;
		return this.id == other.id;
	}
	
	@Override
	@NotNull
	public String toString() {
		return "Event [id=" + this.id + ", title=" + this.title + ", description=" + this.description + 
				", start=" + this.start + ", end="+ this.end +
				", location=" + this.location + ", eventManager=" + this.eventManager + 
				", opened=" + this.opened +"]";
	}
}
