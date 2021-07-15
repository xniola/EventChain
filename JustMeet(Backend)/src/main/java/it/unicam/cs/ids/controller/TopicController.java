package it.unicam.cs.ids.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import it.unicam.cs.ids.model.Topic;
import it.unicam.cs.ids.repository.TopicRepository;

@RestController
public class TopicController {
	
	@Autowired
	private TopicRepository topicRepository;
	
	 @GetMapping("/topics")
	    public List<Topic> index(){
	        return topicRepository.findAll();
	    }
	 
	  @GetMapping("/topics/{id}")
	  public Topic findTopic(@PathVariable String id) {
		  return topicRepository.findById(Integer.parseInt(id));
	  }
	  
	  @PostMapping("/topics")
	    public void create(@RequestBody Map<String, String> body){	
		  	topicRepository.save(new Topic(Integer.parseInt(body.get("argomento"))));
	    }
	  
	  @PutMapping("/topics/{id}")
	  /**
	   * Modifica le informaizoni di un user con email specifica
	   */
	    public Topic update(@PathVariable String id, @RequestBody Map<String, String> body){
	        Topic topic = topicRepository.findById(Integer.parseInt(id)); 
	        topic.setArgomento(Integer.parseInt(body.get("argomento")));
	        return topicRepository.save(topic);
	    }
	  
	  @DeleteMapping("/topics/{id}")
	  //@Transactional
	  /**
	   * Rimuove un utente con email specifica
	   */
	    public boolean delete(@PathVariable String id){
	        topicRepository.deleteById(Integer.parseInt(id));
	        return true;
	    }
}
