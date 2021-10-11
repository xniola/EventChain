package it.univpm.eventchain.controller;

import java.security.Principal;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import it.univpm.eventchain.model.Member;
import it.univpm.eventchain.repository.MemberRepository;
import it.univpm.eventchain.view.View;
import kotlin.jvm.internal.Intrinsics;

@RestController
public class MemberController {
	
	private static final String EMAIL = "email";
	private static final String NAME = "name";
	public static final String GENERIC_ERROR_MSG = "Member not found!!!";
	public static final String GOOGLE_REGISTRATION_ID = "google";
	
	@Autowired
	private MemberRepository memberRepository;

	public MemberController() {
		super();
	}
			
	public static final Object getPrincipalAttribute(@NotNull final Principal principal,@NotNull final String attributeName) {		
		Intrinsics.checkParameterIsNotNull(principal, "principal");
		Intrinsics.checkParameterIsNotNull(attributeName, "attributeName");
		OAuth2AuthenticationToken authentication = (OAuth2AuthenticationToken) principal;
		OAuth2User oAuth2User = authentication.getPrincipal();
		Intrinsics.checkExpressionValueIsNotNull(oAuth2User, "authentication.principal");
		Object attribute = oAuth2User.getAttributes().get(attributeName);
		if (attribute == null) {
			throw new NullPointerException("Null "+attributeName+"!");
		} else {
			return attribute;
		}
	}
	
	public static final String getPrincipalEmail(@NotNull final Principal principal) {		
		return (String)getPrincipalAttribute(principal,EMAIL);
	}	

	public static final String getPrincipalName(@NotNull final Principal principal) {		
		return (String)getPrincipalAttribute(principal,NAME);
	}
	    	
	@JsonView(value = View.Private.class)
	@GetMapping({ "/user" })
	@PreAuthorize("hasRole('ROLE_USER')")	
	public Member user(@NotNull final Principal principal){
		return memberRepository.findByUsername(getPrincipalEmail(principal));		
	}
	
}
