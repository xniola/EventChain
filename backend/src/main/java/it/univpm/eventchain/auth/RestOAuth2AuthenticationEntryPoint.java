package it.univpm.eventchain.auth;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

public final class RestOAuth2AuthenticationEntryPoint implements AuthenticationEntryPoint {
	public void commence(@NotNull final HttpServletRequest request, @NotNull final HttpServletResponse response,
			@NotNull AuthenticationException authException) throws IOException {
		Intrinsics.checkParameterIsNotNull(request, "request");
		Intrinsics.checkParameterIsNotNull(response, "response");
		Intrinsics.checkParameterIsNotNull(authException, "authException");
		response.sendError(401, authException.getMessage());
	}
}
