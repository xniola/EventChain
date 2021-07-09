package it.unicam.cs.ids.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.unicam.cs.ids.model.Scheduler;

public interface SchedulerRepository extends JpaRepository<Scheduler, Integer>{
	Scheduler findById(int id);
}
