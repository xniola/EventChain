package it.univpm.eventchain.controller;

import javax.servlet.http.HttpServletRequest;

import org.jetbrains.annotations.NotNull;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import kotlin.jvm.internal.Intrinsics;

@ControllerAdvice
@RestController
public class GlobalDefaultExceptionHandler {
	
  @ExceptionHandler(value = Exception.class)
  @ResponseStatus(value=HttpStatus.BAD_REQUEST)
  public ExceptionResponse defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
    // If the exception is annotated with @ResponseStatus rethrow it and let
    // the framework handle it. 
    // AnnotationUtils is a Spring Framework utility class.
    if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
      throw e;
    }else {
    	return new ExceptionResponse(e.getMessage());
    }
  }
  
  public static final class ExceptionResponse {
		@NotNull
		private final String errorMessage;
		
		public ExceptionResponse(@NotNull final String errorMessage) {
			super();
			Intrinsics.checkParameterIsNotNull(errorMessage, "errorMessage");
			this.errorMessage = errorMessage;
		}

		@NotNull
		public final String getErrorMessage() {
			return this.errorMessage;
		}
	}
}
