package it.unicam.cs.ids.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.unicam.cs.ids.model.Commento;

public interface CommentoRepository extends JpaRepository<Commento, Integer>{
	Commento findById(int id);
}
