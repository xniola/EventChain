package it.unicam.cs.ids.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.unicam.cs.ids.model.Topic;

public interface TopicRepository extends JpaRepository<Topic,Integer>{
	Topic findById(int id);
}
