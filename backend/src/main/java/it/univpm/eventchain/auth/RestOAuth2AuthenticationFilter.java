package it.univpm.eventchain.auth;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.http.HttpMethod;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.authentication.OAuth2LoginAuthenticationToken;
import org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.oidc.authentication.OidcAuthorizationCodeAuthenticationProvider;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationExchange;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationResponse;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationResponse.Builder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher.MatchResult;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import it.univpm.eventchain.model.Member;
import it.univpm.eventchain.repository.MemberRepository;

@Component
public final class RestOAuth2AuthenticationFilter extends GenericFilterBean {
	private final AuthenticationManager authenticationManager;
	private final DefaultOAuth2AuthorizationRequestResolver authorizationRequestResolver;
	private final AntPathRequestMatcher requestMatcher;
	private final ClientRegistrationRepository clientRegistrationRepository;
	private final OAuth2AuthorizedClientRepository authorizedClientRepository;
	private final TokenManager tokenManager;
	private final MemberUserDetailsService userDetailsService;
	private final MemberRepository memberRepository;
	private static final String CONTENT_TYPE_HEADER = "Content-Type";
	private static final String BASE_URI = "/auth";
	private static final String NONCE_PARAM_NAME = "nonce";	
	private static final String REDIRECT_URI = "postmessage";
	private static final String REG_ID_VAR_URI_NAME = "registrationId";

	private final Consumer<OAuth2AuthorizationRequest.Builder> authorizationRequestCustomizer() {
		return customizer -> customizer.redirectUri(REDIRECT_URI).
				additionalParameters(additionalParameters -> additionalParameters.remove(NONCE_PARAM_NAME)).
				attributes(attributes -> attributes.remove(it.univpm.eventchain.auth.RestOAuth2AuthenticationFilter.NONCE_PARAM_NAME));
	}

	public RestOAuth2AuthenticationFilter(@NotNull final ClientRegistrationRepository clientRegistrationRepository,
			@NotNull final OAuth2AuthorizedClientRepository authorizedClientRepository, @NotNull final TokenManager tokenManager,
			@NotNull final MemberUserDetailsService userDetailsService, @NotNull final MemberRepository memberRepository) {
		super();
		Intrinsics.checkParameterIsNotNull(clientRegistrationRepository, "clientRegistrationRepository");
		Intrinsics.checkParameterIsNotNull(authorizedClientRepository, "authorizedClientRepository");
		Intrinsics.checkParameterIsNotNull(tokenManager, "tokenManager");
		Intrinsics.checkParameterIsNotNull(userDetailsService, "userDetailsService");
		Intrinsics.checkParameterIsNotNull(memberRepository, "memberRepository");

		this.clientRegistrationRepository = clientRegistrationRepository;
		this.authorizedClientRepository = authorizedClientRepository;
		this.tokenManager = tokenManager;
		this.userDetailsService = userDetailsService;
		this.memberRepository = memberRepository;
		this.authorizationRequestResolver = new DefaultOAuth2AuthorizationRequestResolver(
				this.clientRegistrationRepository, BASE_URI);
		this.requestMatcher = new AntPathRequestMatcher(BASE_URI + "/{" + REG_ID_VAR_URI_NAME + "}",
				HttpMethod.POST.name());
		DefaultAuthorizationCodeTokenResponseClient accessTokenResponseClient = new DefaultAuthorizationCodeTokenResponseClient();
		OidcUserService userService = new OidcUserService();
		OidcAuthorizationCodeAuthenticationProvider authenticationProvider = new OidcAuthorizationCodeAuthenticationProvider(
				accessTokenResponseClient, userService);
		this.authenticationManager = new ProviderManager(authenticationProvider);
		this.authorizationRequestResolver.setAuthorizationRequestCustomizer(authorizationRequestCustomizer());
	}

	public final void doFilter(@NotNull final ServletRequest req, @NotNull final ServletResponse res, @NotNull final FilterChain chain)
			throws IOException, ServletException {
		Intrinsics.checkParameterIsNotNull(req, "req");
		Intrinsics.checkParameterIsNotNull(res, "res");
		Intrinsics.checkParameterIsNotNull(chain, "chain");
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		if (!this.requireAuthentication(request)) {			
			chain.doFilter(request, response);			
		} else {			
			try {
				OAuth2AuthenticationToken authentication = this.authenticate(request, response);				
				this.successfulAuthentication(response, authentication);				
			} catch (Exception ex) {
				this.unsuccessfulAuthentication(response, ex);
			}

		}
	}

	private final boolean requireAuthentication(final HttpServletRequest request) {
		return this.requestMatcher.matches(request);
	}

	private final OAuth2AuthenticationToken authenticate(final HttpServletRequest request,final  HttpServletResponse response)
			throws IOException {
		String code = this.readCode(request);
		if (code != null) {
			MatchResult matchResult = this.requestMatcher.matcher(request);
			Intrinsics.checkExpressionValueIsNotNull(matchResult, "requestMatcher.matcher(request)");
			String registrationId = matchResult.getVariables().get(REG_ID_VAR_URI_NAME);
			if (registrationId != null) {
				ClientRegistration clientRegistration = this.clientRegistrationRepository
						.findByRegistrationId(registrationId);
				if (clientRegistration != null) {
					OAuth2AuthorizationRequest authorizationRequest = this.authorizationRequestResolver.resolve(request,
							registrationId);
					Builder builder = OAuth2AuthorizationResponse.success(code).redirectUri(REDIRECT_URI);
					Intrinsics.checkExpressionValueIsNotNull(authorizationRequest, "authorizationRequest");
					OAuth2AuthorizationResponse authorizationResponse = builder.state(authorizationRequest.getState())
							.build();
					OAuth2LoginAuthenticationToken authenticationRequest = new OAuth2LoginAuthenticationToken(
							clientRegistration,
							new OAuth2AuthorizationExchange(authorizationRequest, authorizationResponse));
					Authentication authentication = this.authenticationManager.authenticate(authenticationRequest);
					if (authentication == null) {
						throw new NullPointerException("Null authentication");
					} else {
						OAuth2LoginAuthenticationToken authenticationResult = (OAuth2LoginAuthenticationToken) authentication;
						OAuth2User oAuth2User = authenticationResult.getPrincipal();
						Intrinsics.checkExpressionValueIsNotNull(oAuth2User, "authenticationResult.principal");
						UserDetails userDetails = this.createUpdateUser(authenticationResult);
						Collection<GrantedAuthority> authorities = this.mergeAuthorities(authenticationResult,
								userDetails);
						clientRegistration = authenticationResult.getClientRegistration();
						Intrinsics.checkExpressionValueIsNotNull(clientRegistration,
								"authenticationResult.clientRegistration");
						OAuth2AuthenticationToken oauth2Authentication = new OAuth2AuthenticationToken(oAuth2User,
								authorities, clientRegistration.getRegistrationId());
						OAuth2AuthorizedClient authorizedClient = new OAuth2AuthorizedClient(
								authenticationResult.getClientRegistration(), oauth2Authentication.getName(),
								authenticationResult.getAccessToken(), authenticationResult.getRefreshToken());
						this.authorizedClientRepository.saveAuthorizedClient(authorizedClient, oauth2Authentication,
								request, response);
						return oauth2Authentication;
						
					}
				} else {
					throw new OAuth2AuthenticationException(new OAuth2Error("client_registration_not_found"));
				}
			} else {
				throw new OAuth2AuthenticationException(new OAuth2Error("client_registration_not_found"));
			}
		} else {
			throw new OAuth2AuthenticationException(new OAuth2Error("authentication_code_missing"));
		}
	}

	private final String readCode(final HttpServletRequest request) throws IOException {
		MappingJackson2HttpMessageConverter jacksonMessageConverter = new MappingJackson2HttpMessageConverter();
		ObjectMapper objectMapper = jacksonMessageConverter.getObjectMapper();
		BufferedReader bufferReader = request.getReader();
		Intrinsics.checkExpressionValueIsNotNull(bufferReader, "request.reader");
		Reader reader = bufferReader;
		Map<String, Object> authRequest = objectMapper.readValue(reader, new TypeReference<Map<String, Object>>() {
		});
		Object requestCode = authRequest.get("code");
		if (!(requestCode instanceof String)) {
			requestCode = null;
		}

		return (String) requestCode;
	}

	private final UserDetails createUpdateUser(final OAuth2LoginAuthenticationToken authentication) {
		OAuth2User oAuth2User = authentication.getPrincipal();
		Map<String, Object> attributes = oAuth2User.getAttributes();
		Intrinsics.checkExpressionValueIsNotNull(attributes,"authentication.attributes");
		Object email = attributes.get("email");
		if (email == null) {
			throw new NullPointerException("Null email");
		} else {
			String username = (String) email;
			ClientRegistration clientRegistration = authentication.getClientRegistration();
			Intrinsics.checkExpressionValueIsNotNull(clientRegistration, "authentication.clientRegistration");
			String registrationId = clientRegistration.getRegistrationId();
			Intrinsics.checkExpressionValueIsNotNull(registrationId,"authentication.clientRegistration.registrationId");			
			Member member= memberRepository.findByUsername(username);
			
			if (member==null) {
				member=new Member(username, registrationId, Arrays.asList(Member.ROLE_USER,Member.ROLE_TICKET_BUYER));
				
			}

			Object name = attributes.get("name");
			if (name != null) {
				member.setName((String) name);
			}

			Object givenName = attributes.get("given_name");
			if (givenName != null) {
				member.setGivenName((String) givenName);
			}

			Object familyName = attributes.get("family_name");
			if (familyName != null) {
				member.setFamilyName((String) familyName);
			}

			Object picture = attributes.get("picture");
			if (picture != null) {
				member.setPicture((String) picture);
			}

			this.memberRepository.save(member);
			return this.userDetailsService.loadUserByUsername(username);

		}
	}

	private final Collection<GrantedAuthority> mergeAuthorities(final OAuth2LoginAuthenticationToken authentication,
			UserDetails user) {
		HashSet<GrantedAuthority> authorities = new HashSet<>();
		authorities.addAll(authentication.getAuthorities());
		authorities.addAll(user.getAuthorities());
		return authorities;
	}

	private final void successfulAuthentication(final HttpServletResponse response,final  OAuth2AuthenticationToken authentication)
			throws IOException {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		Intrinsics.checkExpressionValueIsNotNull(securityContext, "SecurityContextHolder.getContext()");
		securityContext.setAuthentication(authentication);
		String token = this.tokenManager.generate(authentication);
		this.tokenManager.set(token, authentication);
		response.addHeader(CONTENT_TYPE_HEADER, "application/json");
		MappingJackson2HttpMessageConverter jacksonMessageConverter = new MappingJackson2HttpMessageConverter();
		response.getWriter().println(jacksonMessageConverter.getObjectMapper()
				.writeValueAsString(new RestOAuth2AuthenticationFilter.TokenResponse(token)));
	}

	private final void unsuccessfulAuthentication(HttpServletResponse response, Exception exception)
			throws IOException {
		SecurityContextHolder.clearContext();
		response.sendError(401, exception.getMessage());
	}

	private static final class TokenResponse {
		@NotNull
		private final String token;

		public TokenResponse(@NotNull String token) {
			super();
			Intrinsics.checkParameterIsNotNull(token, "token");
			this.token = token;
		}

		@NotNull
		public final String getToken() {
			return this.token;
		}

		@NotNull
		public final RestOAuth2AuthenticationFilter.TokenResponse copy(@NotNull final String token) {
			Intrinsics.checkParameterIsNotNull(token, "token");
			return new RestOAuth2AuthenticationFilter.TokenResponse(token);
		}

		@NotNull
		public String toString() {
			return "TokenResponse(token=" + this.token + ")";
		}

		@Override
		public int hashCode() {
			return Objects.hash(token);
		}

		@Override
		public boolean equals(@Nullable final Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			TokenResponse other = (TokenResponse) obj;
			return Objects.equals(this.token, other.token);
		}

	}

}
