package it.univpm.eventchain.auth;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import kotlin.jvm.internal.Intrinsics;

import java.util.ArrayList;
import java.util.Date;
import javax.crypto.SecretKey;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

@Component
public final class TokenManager {
	
	private final Cache cache;
	private static final String CLAIM_AUTHORITIES = "authorities";
	private static final String CLAIM_NAME = "name";
	private static final String CLAM_EMAIL = "email";
	private static final String SECRET = "qsbWaaBHBN/I7FYOrev4yQFJm60sgZkWIEDlGtsRl7El/k+DbUmg8nmWiVvEfhZ91Y67Sc6Ifobi05b/XDwBy4kXUcKTitNqocy7rQ9Z3kMipYjbL3WZUJU2luigIRxhTVNw8FXdT5q56VfY0LcQv3mEp6iFm1JG43WyvGFV3hCkhLPBJV0TWnEi69CfqbUMAIjmymhGjcbqEK8Wt10bbfxkM5uar3tpyqzp3Q==";
	private static final SecretKey S_KEY=Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET));
			
	public TokenManager(@NotNull final CacheManager cacheManager) {
		super();
		Intrinsics.checkParameterIsNotNull(cacheManager, "cacheManager");
		Cache currenteCache = cacheManager.getCache("tokenManager");
		if (currenteCache == null) {
	         Intrinsics.throwNpe();
	      }
	    Intrinsics.checkExpressionValueIsNotNull(currenteCache, "cacheManager.getCache(\"tokenManager\")!!");
	    this.cache = currenteCache;
	}
	
	@Nullable
	public final OAuth2AuthenticationToken get(@NotNull final String token) {
		Intrinsics.checkParameterIsNotNull(token, "token");		
		if (validate(token)) {
			ValueWrapper tokenWrapper = this.cache.get(token);		
			return tokenWrapper != null ? (OAuth2AuthenticationToken) tokenWrapper.get() : null;
		} else {
			this.cache.evict(token);
			return null;
		}
	}
	
	public final void set(@NotNull final String token,@NotNull final OAuth2AuthenticationToken authentication) {
		Intrinsics.checkParameterIsNotNull(token, "token");
	    Intrinsics.checkParameterIsNotNull(authentication, "authentication");
		this.cache.put(token, authentication);
	}
	
	@NotNull
	public final String generate(@NotNull final OAuth2AuthenticationToken authentication) {
		
		Intrinsics.checkParameterIsNotNull(authentication, "authentication");
		String subject =  authentication.getName();
		OAuth2User oAuthUser = authentication.getPrincipal();
		Intrinsics.checkExpressionValueIsNotNull(oAuthUser, "authentication.principal");
		Object name = oAuthUser.getAttributes().get(CLAIM_NAME);
		Object email = oAuthUser.getAttributes().get(CLAM_EMAIL);
		Date expiration = new Date(System.currentTimeMillis() + 3600000);
						
		ArrayList<String> authorities = new ArrayList<>();  
		
		for (GrantedAuthority ga : authentication.getAuthorities()) 
		{ 
		    authorities.add(ga.getAuthority());
		}
		
		return Jwts.builder().setSubject(subject).claim(CLAIM_AUTHORITIES, String.join(",",authorities)).claim(CLAIM_NAME, name)
				.claim(CLAM_EMAIL, email).signWith(S_KEY, SignatureAlgorithm.HS512).setExpiration(expiration)
				.compact();

	}
	
	private final boolean validate(final String token) {
		boolean result=false;
		
		try {
			JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(S_KEY).build();
			jwtParser.parseClaimsJws(token).getBody();
			result=true;
		} catch (Exception ex) {
			result = false;
		}

		return result;
	}

}
