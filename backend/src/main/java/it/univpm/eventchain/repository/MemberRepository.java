package it.univpm.eventchain.repository;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;

import it.univpm.eventchain.model.Member;

public interface MemberRepository extends JpaRepository<Member, Long>{
	 @Nullable
	 Member findByUsername(@NotNull String username);
	 @Nullable
	 Member findByPublicName(@NotNull String publicName);
}
