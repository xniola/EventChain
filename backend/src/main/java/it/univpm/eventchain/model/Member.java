package it.univpm.eventchain.model;

import java.io.Serializable;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.StringUtils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import it.univpm.eventchain.view.View;
import kotlin.jvm.internal.Intrinsics;

@Entity
public final class Member implements Serializable{
	
	private static final long serialVersionUID = -6728196447449766573L;
	public static final String ROLE_ADMIN = "ROLE_ADMIN";
	public static final String ROLE_EVENT_MANAGER = "ROLE_EVENT_MANAGER";
	public static final String ROLE_USER = "ROLE_USER";
	public static final String ROLE_TICKET_BUYER = "ROLE_TICKET_BUYER";
	public static final String ROLE_TICKET_RESELLER = "ROLE_TICKET_RESELLER";
		
	@JsonView(View.Private.class)
	@Column(length = 100,unique = true)
    @Size(min = 3, max = 100)
	@NotNull
	private final String username;
	
	@JsonView(View.Private.class)
	@Column(length = 10)
    @Size(min = 1, max = 10)
	@NotNull
	private final String registrationId;
	
	@JsonView(View.Private.class)
	@Column(length = 50)
    @Size(min = 1, max = 50)
	@Nullable
	private String name;
	
	@JsonView(View.Private.class)
	@Column(length = 100)
    @Size(min = 1, max = 100)
	@Nullable
	private String givenName;
	
	@JsonView(View.Private.class)
	@Column(length = 50)
    @Size(min = 1, max = 50)
	@Nullable
	private String familyName;
	
	@JsonView(View.Private.class)
	@Column(length = 100)
    @Size(min = 1, max = 100)
	@Nullable
	private String picture;
	
	@JsonIgnore
	@ElementCollection(fetch = FetchType.EAGER)
	@NotNull
	private final Collection<String> authorities;
	
	@JsonIgnore
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Nullable
	private Long id;
	
	@JsonView(View.Public.class)
	@Column(length = 100,unique = true)
    @Size(min = 1, max = 100)
	@Nullable	
	private String publicName;
		
	public Member() {
		this(new HashSet<>());
	}
	
	public Member(@NotNull final Collection<String> authorities) {
		this(StringUtils.EMPTY,StringUtils.EMPTY,authorities);
	}
	
	public Member(@NotNull final String username, @NotNull final String registrationId,
				  @NotNull final Collection<String> authorities) {
		super();
		Intrinsics.checkParameterIsNotNull(username, "username");
		Intrinsics.checkParameterIsNotNull(registrationId, "registrationId");
		Intrinsics.checkParameterIsNotNull(authorities, "authorities");
		this.username = username;
		this.registrationId = registrationId;
		this.authorities = authorities;
	}
	
	@NotNull
	public final String getUsername() {
		return this.username;
	}

	@NotNull
	public final String getRegistrationId() {
		return this.registrationId;
	}

	@Nullable
	public final String getName() {
		return this.name;
	}
	
	public final void setName(@NotNull final String name) {
		Intrinsics.checkParameterIsNotNull(name, "name");
		this.name = name;
	}

	@NotNull
	public final Collection<String> getAuthorities() {
		return this.authorities;
	}
	
	@Nullable
	public final Long getId() {
		return this.id;
	}
		
	@Nullable
	public final String getGivenName() {
		return this.givenName;
	}

	public final void setGivenName(@NotNull final String givenName) {
		Intrinsics.checkParameterIsNotNull(givenName, "givenName");
		this.givenName = givenName;
	}
	
	@Nullable
	public final String getFamilyName() {
		return this.familyName;
	}

	public final void setFamilyName(@NotNull final String familyName) {
		Intrinsics.checkParameterIsNotNull(familyName, "familyName");
		this.familyName = familyName;
	}
	
	@Nullable
	public final String getPicture() {
		return this.picture;
	}
	
	public final void setPicture(@NotNull final String picture) {
		Intrinsics.checkParameterIsNotNull(picture, "picture");
		this.picture = picture;
	}
	
	@Nullable			
	public final String getPublicName() {
		return this.publicName;
	}

	public final void setPublicName(@NotNull final String publicName) {
		Intrinsics.checkParameterIsNotNull(publicName, "publicName");
		this.publicName = publicName;
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
		Member other = (Member) obj;
		return Objects.equals(this.id, other.id);
	}
		
	@Override
	@NotNull
	public String toString() {
		return "Member [username=" + this.username + ", registrationId="
				+ this.registrationId + ", name=" + this.name + ", givenName=" + this.givenName + ", familyName=" + this.familyName
				+ ", picture=" + this.picture + ", authorities=" + this.authorities + ", id=" + this.id + 
				", publicName"+ this.publicName +"]";
	}
	
	@NotNull
	public Boolean hasAuthority(@NotNull final String authority) {
		Intrinsics.checkParameterIsNotNull(authority, "authority");
		return this.authorities.contains(authority);
	}
	@JsonView(View.Private.class)
	@NotNull
	public Boolean isAdmin() {
		return this.hasAuthority(ROLE_ADMIN);
	}
	@JsonView(View.Private.class)
	@NotNull
	public Boolean isEventManager() {
		return this.hasAuthority(ROLE_EVENT_MANAGER);
	}	
	@JsonView(View.Private.class)
	@NotNull
	public Boolean isTicketBuyer() {
		return this.hasAuthority(ROLE_TICKET_BUYER);
	}
	@JsonView(View.Private.class)
	@NotNull
	public Boolean isTicketReseller() {
		return this.hasAuthority(ROLE_TICKET_RESELLER);
	}	
	
}
