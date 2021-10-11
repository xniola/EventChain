package it.univpm.eventchain.auth;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

public final class RestOAuth2AccessDeniedHandler implements AccessDeniedHandler {
	
	public final void handle(@NotNull final HttpServletRequest request, @NotNull final HttpServletResponse response,
			@NotNull AccessDeniedException accessDeniedException) throws IOException {
		Intrinsics.checkParameterIsNotNull(request, "request");
		Intrinsics.checkParameterIsNotNull(response, "response");
		Intrinsics.checkParameterIsNotNull(accessDeniedException, "accessDeniedException");
		response.sendError(403, accessDeniedException.getMessage());
	}
	
}