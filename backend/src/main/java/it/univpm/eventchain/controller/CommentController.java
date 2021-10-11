package it.univpm.eventchain.controller;

import java.security.GeneralSecurityException;
import java.security.Principal;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import it.univpm.eventchain.model.Comment;
import it.univpm.eventchain.repository.CommentRepository;
import it.univpm.eventchain.view.View;
import kotlin.jvm.internal.Intrinsics;

@RestController
public class CommentController {
	
	private static final String GENERIC_ERROR_MSG = "Comment not found!!!";
	
	@Autowired
	private CommentRepository commentRepository;
	
	@NotNull
	public static final boolean isOwner(@NotNull final Principal principal,@NotNull final Comment comment){
		Intrinsics.checkParameterIsNotNull(comment, "comment");
		String email = MemberController.getPrincipalEmail(principal);
		return (comment.getSender().getUsername().equals(email));
	}
			
	@JsonView(View.Public.class)
	@GetMapping("/comment/{id}")
	@PreAuthorize("hasRole('ROLE_USER')")
	public Optional<Comment> find(@PathVariable final Long id){
		return this.commentRepository.findById(id);
	}
	
	@JsonView(value = View.Public.class)
	@PutMapping("/comment/{id}/update")
	@PreAuthorize("hasRole('ROLE_USER')")
	public Comment update(@PathVariable final Long id, @RequestBody final CommentRequest request,
						  @NotNull final Principal principal) throws GeneralSecurityException{
		Comment comment = this.commentRepository.findById(id).orElseThrow();
		if (isOwner(principal, comment)) {
			comment.setBody(request.getBody());
			comment.setTimestamp();
			return this.commentRepository.save(comment);
		} else {
			throw new GeneralSecurityException(GENERIC_ERROR_MSG);
		}
	}
	
	@DeleteMapping("/comment/{id}/delete")
	@PreAuthorize("hasRole('ROLE_USER')")
	public boolean delete(@PathVariable final Long id, @NotNull final Principal principal) {
		Comment comment = this.commentRepository.findById(id).orElseThrow();
		if (isOwner(principal, comment)) {
			this.commentRepository.delete(comment);
			return true;
		} else {
			return false;
		}
	}
	
	public static final class CommentRequest {		
		@NotNull
		private final String body;
		
		public CommentRequest() {
			this(StringUtils.EMPTY);
		}

		public CommentRequest(@NotNull final String body) {
			super();
			Intrinsics.checkParameterIsNotNull(body, "body");
			this.body = body;
		}
		@NotNull
		public final String getBody() {
			return this.body;
		}
	}
}
