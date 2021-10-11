package it.univpm.eventchain.repository;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;

import it.univpm.eventchain.model.Event;
import it.univpm.eventchain.model.Member;

public interface EventRepository extends JpaRepository<Event, String> {
  @Retryable( value = SQLException.class, maxAttempts = 5, backoff = @Backoff(delay = 600000))
  void delete(@NotNull Event event);
  @NotNull
  Optional<Event> findById(@NotNull String id);
  @Nullable
  List<Event> findByTitle(@NotNull String title);
  @Nullable
  List<Event> findByEndAfter(@NotNull Timestamp timestamp);  
  @Nullable
  List<Event> findByEndBefore(@NotNull Timestamp timestamp);
  @Nullable
  List<Event> findByEventManagerOrderByEndDesc(@NotNull Member eventManager); 
  @SuppressWarnings("unchecked")
  @NotNull
  @Retryable( value = SQLException.class, maxAttempts = 5, backoff = @Backoff(delay = 600000))
  Event save(@NotNull Event event);
}
