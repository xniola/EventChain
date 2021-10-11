package it.univpm.eventchain.auth;

import java.util.Collection;
import java.util.HashSet;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import it.univpm.eventchain.model.Member;
import it.univpm.eventchain.repository.MemberRepository;
import kotlin.jvm.internal.Intrinsics;

@Component
public final class MemberUserDetailsService implements UserDetailsService{
	
	private final MemberRepository memberRepository;
	
	public MemberUserDetailsService(@NotNull final MemberRepository memberRepository) {
		super();
		Intrinsics.checkParameterIsNotNull(memberRepository, "memberRepository");
		this.memberRepository = memberRepository;
	}

	@Override
	@NotNull
	public final UserDetails loadUserByUsername(@NotNull final String username) throws UsernameNotFoundException {
		Intrinsics.checkParameterIsNotNull(username, "username");
	      Member member = this.memberRepository.findByUsername(username);
	      if (member == null) {
	         throw new UsernameNotFoundException(username + " was not found");
	      } else {
	         
	         Collection<SimpleGrantedAuthority> authority = new HashSet<>();
	         for (String role : member.getAuthorities()) {
	        	 SimpleGrantedAuthority sga = new SimpleGrantedAuthority(role);
		         authority.add(sga);
	         }
	         
	         return new User(member.getUsername(), StringUtils.EMPTY, authority);
	      }
	}

}
