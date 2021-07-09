package it.unicam.cs.ids.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import it.unicam.cs.ids.model.Event;
import it.unicam.cs.ids.model.Scheduler;
import it.unicam.cs.ids.repository.EventRepository;
import it.unicam.cs.ids.repository.SchedulerRepository;

@RestController
public class SchedulerController {
	
	@Autowired
	private SchedulerRepository scheduler;
	
	@Autowired
	private EventRepository eventRepository;
	
	@DeleteMapping("/events")
	 @Scheduled(fixedRate = 600000) //10 minuti
	    public void checkDatabase() {
		    this.scheduler.save(new Scheduler());
	        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	        String todayAsString = df.format(Calendar.getInstance().getTime());
	        
	        for(Event e : eventRepository.findAll()) {
	        	if(e.before(todayAsString)) {
	        		eventRepository.delete(e);
	        	}
	        }
	    }
	 
	 @GetMapping("/schedulers")
	    public List<Scheduler> index(){
	        return scheduler.findAll();
	    }
	 
	  @GetMapping("/schedulers/{id}")
	  public Scheduler findScheduler(@PathVariable String id) {
		  return scheduler.findById(Integer.parseInt(id));
	  }
}
