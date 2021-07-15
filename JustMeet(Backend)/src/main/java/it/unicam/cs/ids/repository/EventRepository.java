package it.unicam.cs.ids.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.unicam.cs.ids.model.Event;

public interface EventRepository extends JpaRepository<Event, Integer> {
		Event findByid(int id);
		Event findByTitle(String title);
}
