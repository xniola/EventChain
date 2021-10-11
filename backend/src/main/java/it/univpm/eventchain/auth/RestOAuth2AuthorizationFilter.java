package it.univpm.eventchain.auth;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

@Component
public final class RestOAuth2AuthorizationFilter extends GenericFilterBean {
	private final TokenManager tokenManager;
	private static final String AUTHENTICATION_HEADER = "Authorization";
	private static final String AUTHENTICATION_SCHEME = "Bearer";
	
	@Override
	public final void doFilter(@NotNull final ServletRequest request, @NotNull final ServletResponse response, @NotNull final FilterChain chain) throws IOException, ServletException {
	      Intrinsics.checkParameterIsNotNull(request, "request");
	      Intrinsics.checkParameterIsNotNull(response, "response");
	      Intrinsics.checkParameterIsNotNull(chain, "chain");
	      String token = this.extractToken((HttpServletRequest)request);
	      if (token != null && StringUtils.hasText(token)) {
	         OAuth2AuthenticationToken authentication = this.tokenManager.get(token);
	         if (authentication != null) {
	            SecurityContext securityContext = SecurityContextHolder.getContext();
	            Intrinsics.checkExpressionValueIsNotNull(securityContext, "SecurityContextHolder.getContext()");
	            securityContext.setAuthentication(authentication);
	         }
	      }
	      chain.doFilter(request, response);
	   }

	   private final String extractToken(final HttpServletRequest request) {
	      String bearerToken = request.getHeader(AUTHENTICATION_HEADER);
	      if (StringUtils.hasText(bearerToken)) {
	         Intrinsics.checkExpressionValueIsNotNull(bearerToken, "bearerToken");
	         String prefix = AUTHENTICATION_SCHEME + " ";
	         if (bearerToken.startsWith(prefix)) {
	            String token = bearerToken.substring(prefix.length());
	            Intrinsics.checkExpressionValueIsNotNull(token, "(this as java.lang.String).substring(startIndex)");
	            return token;
	         }
	      }

	      return null;
	   }
	
	public RestOAuth2AuthorizationFilter(@NotNull final TokenManager tokenManager) {
		super();
	    Intrinsics.checkParameterIsNotNull(tokenManager, "tokenManager");
	    this.tokenManager = tokenManager;
	}

}
