package it.unicam.cs.ids.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.unicam.cs.ids.model.Location;

public interface LocationRepository extends JpaRepository<Location, Integer> {
		Location findById(int id);
}
