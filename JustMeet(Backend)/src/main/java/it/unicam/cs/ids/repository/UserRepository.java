package it.unicam.cs.ids.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.unicam.cs.ids.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
		User findByid(int id);
		User findByEmail(String email);
		void deleteByEmail(String email);
}
