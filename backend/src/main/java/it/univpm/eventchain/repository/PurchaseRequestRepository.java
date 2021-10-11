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
import it.univpm.eventchain.model.PurchaseRequest;
import it.univpm.eventchain.model.PurchaseRequest.PurchaseRequestState;

public interface PurchaseRequestRepository extends JpaRepository<PurchaseRequest, String> {
	@NotNull
	Optional<PurchaseRequest> findById(@NotNull String id);
	@Nullable
	List<PurchaseRequest> findByEvent(@NotNull Event event);
	@Nullable
	List<PurchaseRequest> findByTicketBuyerOrderByTimestampDesc(@NotNull Member ticketBuyer);
	@Nullable
	List<PurchaseRequest> findByTicketResellerOrderByTimestampDesc(@NotNull Member ticketReseller);
	@Nullable
	List<PurchaseRequest> findByEventAndTicketBuyerOrderByPriorityDesc(@NotNull Event event,@NotNull Member ticketBuyer);
	@Nullable
	List<PurchaseRequest> findByEventAndTicketBuyerAndStatusLessThanEqual(@NotNull Event event,@NotNull Member ticketBuyer,@NotNull PurchaseRequestState status);
	@Nullable
	List<PurchaseRequest> findByEventAndTicketResellerOrderByPriorityDesc(@NotNull Event event,@NotNull Member ticketReseller);
	@NotNull
	Optional<PurchaseRequest> findFirstByEventAndTicketResellerOrderByPriorityDesc(@NotNull Event event,@NotNull Member ticketReseller);
	@NotNull
	Optional<PurchaseRequest> findFirstByEventAndTicketResellerAndStatusOrderByPriorityAsc(@NotNull Event event,@NotNull Member ticketReseller,@NotNull PurchaseRequestState status);
	@SuppressWarnings("unchecked")	 
	@NotNull
	@Retryable( value = SQLException.class, maxAttempts = 5, backoff = @Backoff(delay = 600000))
	PurchaseRequest save(@NotNull PurchaseRequest purchaseRequest);
}
