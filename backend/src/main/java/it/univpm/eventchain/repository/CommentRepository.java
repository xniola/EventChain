package it.univpm.eventchain.repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;

import it.univpm.eventchain.model.Comment;
import it.univpm.eventchain.model.Event;

public interface CommentRepository extends JpaRepository<Comment, Long>{
	@Retryable( value = SQLException.class, maxAttempts = 5, backoff = @Backoff(delay = 600000))
	void deleteAll(@NotNull Iterable<?extends Comment> entities);
	@NotNull
	Optional<Comment> findById(@NotNull Long id);
	@Nullable
	List<Comment> findByEvent(@NotNull Event event);
}
