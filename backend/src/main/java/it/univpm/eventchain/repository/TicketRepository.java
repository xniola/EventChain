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
import it.univpm.eventchain.model.Member;
import it.univpm.eventchain.model.Ticket;
import it.univpm.eventchain.model.Ticket.TicketState;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
	@Retryable( value = SQLException.class, maxAttempts = 5, backoff = @Backoff(delay = 600000))
	void deleteAll(@NotNull Iterable<?extends Ticket> entities);
	@NotNull
	Optional<Ticket> findById(@NotNull Long id);
	@Nullable
	List<Ticket> findByEvent(@NotNull Event event);
	@Nullable
	List<Ticket> findByEventAndState(@NotNull Event event,@NotNull TicketState state);
	@Nullable
	List<Ticket> findByTicketResellerOrderByIdDesc(@NotNull Member ticketReseller);
	@Nullable
	List<Ticket> findByTicketBuyerOrderByIdDesc(@NotNull Member ticketBuyer);
}
