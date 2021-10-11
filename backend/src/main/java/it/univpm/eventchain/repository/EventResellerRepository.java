package it.univpm.eventchain.repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;

import it.univpm.eventchain.model.Event;
import it.univpm.eventchain.model.EventReseller;

public interface EventResellerRepository extends JpaRepository<EventReseller, String> {
	@Retryable( value = SQLException.class, maxAttempts = 5, backoff = @Backoff(delay = 600000))
	void deleteAll(@NotNull Iterable<?extends EventReseller> entities);
	@NotNull
	Optional<EventReseller> findById(@NotNull String id);	
	@Nullable
	List<EventReseller> findByEvent(@NotNull Event event);
	@Nullable
	List<EventReseller> findByEventAndAccepted(@NotNull Event event,@NotNull Boolean accepted);
	@SuppressWarnings("unchecked")
	@NotNull
	@Retryable( value = SQLException.class, maxAttempts = 5, backoff = @Backoff(delay = 600000))
	EventReseller save(@NotNull EventReseller eventReseller);	
}
