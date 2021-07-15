package it.unicam.cs.ids.controller;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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

import it.unicam.cs.ids.model.Commento;
import it.unicam.cs.ids.model.Event;
import it.unicam.cs.ids.model.Location;
import it.unicam.cs.ids.model.Topic;
import it.unicam.cs.ids.repository.CommentoRepository;
import it.unicam.cs.ids.repository.EventRepository;
import it.unicam.cs.ids.repository.UserRepository;

@RestController
public class EventController {
	
	@Autowired
	private EventRepository eventRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CommentoRepository commentoRepository;
	
	
	  @GetMapping("/events")
	  /**
	   * Ritorna la lista di tutti gli eventi disponibili
	   */
	    public List<Event> index(){
		  return eventRepository.findAll();
	  }
	    
	  
	  @GetMapping("/events/{id}")
	  /**
	   * Ritorna l'evento con id specifico
	   */
	    public Event show(@PathVariable String id){
	        int eventID = Integer.parseInt(id);
	        return eventRepository.findByid(eventID);
	    }
	  
	  @PostMapping("/events")
	  /**
	   * Crea un nuovo evento
	   */
	    public void create(@RequestBody Map<String,String> body){
		    String title = body.get("title");
	        String description = body.get("description");
	       
	        
	        String data = body.get("date");
	     	String latitude = body.get("latitude");
	        String longitude = body.get("longitude");
	        Location location = new Location(body.get("nome"),Double.parseDouble(latitude),Double.parseDouble(longitude));
	        Topic topic = new Topic(Integer.parseInt(body.get("topic")));
	        String organizzatore = body.get("organizzatore");
	        String numPartecipanti = body.get("numPartecipanti");
		    int partecipanti = Integer.parseInt(numPartecipanti);
		    if(eventRepository.findByTitle(title) != null)
		    	return;
		    Event e = new Event(title,description,data,location,topic,organizzatore,partecipanti);
		    e.getParticipants().add(organizzatore);
		    eventRepository.save(e);
	    }
	  
	  @GetMapping("/events/{id}/participants")
	  /**
	   * Ritorna la lista dei partecipanti di un evento con id specifico
	   */
	  public List<String> showParticipants(@PathVariable String id) {
		  int eventId = Integer.parseInt(id);
	      Event event = eventRepository.findByid(eventId);
	      return event.getParticipants();
	  }
	  
	  @PostMapping("/events/{id}/participants")
	  /**
	   * Aggiunge un partecipante all'evento con id specifico
	   */
	  public void addPartecipant(@PathVariable String id,@RequestBody Map<String,String> body) {
		  int eventId = Integer.parseInt(id);
	      Event event = eventRepository.findByid(eventId);
	      String participant = body.get("fullName");
	      if((!event.getParticipants().contains(participant)) && event.getAdesioniAttuali() != event.getNumPartecipanti()) {
	      event.getParticipants().add(participant);
	      int adesioni = event.getAdesioniAttuali() + 1;
	      event.setAdesioniAttuali(adesioni);
	      eventRepository.save(event);
	      }
	  }
	  
	  @DeleteMapping("/events/{id}/participants/{email}")
	  /**
	   * Rimuove un partecipante dall'evento con id specifico
	   */
	  public void deleteParticipant(@PathVariable String id,@PathVariable String email) {
		  String nomeUser = this.userRepository.findByEmail(email).getFullName();
		  
	      Event event = eventRepository.findByid(Integer.parseInt(id));
	    
	      event.getParticipants().remove(nomeUser);
	      int adesioni = event.getAdesioniAttuali() - 1;
	      event.setAdesioniAttuali(adesioni);
	    	  
	      eventRepository.save(event);
	  }
	  
	  @PutMapping("/events/{id}")
	  /**
	   *  Modifica un'evento con id specifico 
	   */
	    public Event update(@PathVariable String id, @RequestBody Map<String, String> body){
	        int eventId = Integer.parseInt(id);
	        Event event = eventRepository.findByid(eventId);
	        event.setTitle(body.get("title"));
	        event.setDescription(body.get("description"));
	        event.getTopic().setArgomento(Integer.parseInt(body.get("topic")));
	        event.setOrganizzatore(body.get("organizzatore"));
	        String numPartecipanti = body.get("numPartecipanti");
	        event.setNumPartecipanti(Integer.parseInt(numPartecipanti));
	        return eventRepository.save(event);
	  }
	  
	  @DeleteMapping("/events/{id}")
	  /**
	   * Rimuove un evento con id specifico
	   */
	    public boolean delete(@PathVariable String id){
	        int eventId = Integer.parseInt(id);
	        eventRepository.deleteById(eventId);
	        return true;
	    }
	  
	  @GetMapping("/events/topics/{id}")
	  public List<Event> showByTopic(@PathVariable String id){
		  List<Event> eventi = new ArrayList<Event>();
		  for(Event e : this.eventRepository.findAll()) {
			  if(e.getTopic().toString().equals(id)) {
				  eventi.add(e);
			  }
		  }
		return eventi;
	  }
	  
	  
	  @GetMapping("/events/locations/{latitude}/{longitude}")
	  /**
	   * Mostra tutti gli eventi distanti meno di 100km
	   */
	  public List<Event> showByLocation(@PathVariable String latitude,@PathVariable String longitude){
		
		  double latitudine = Double.parseDouble(latitude);
		  double longitudine = Double.parseDouble(longitude);
		  List<Event> eventi = new ArrayList<Event>();
		  for(Event e : this.eventRepository.findAll()) { 
			  if(e.getLocation().distance(latitudine, longitudine) < 100) {
				  eventi.add(e);
			  }
		  }
		  return eventi;
	  }
	  
	  @GetMapping("/events/{id}/comments")
	  public List<Commento> showComments(@PathVariable String id){
		  return this.eventRepository.findByid(Integer.parseInt(id)).getCommento();
	  }
	  
	  
	  @PutMapping("/events/{id}/comments/{id2}")
	  public void modificaCommento(@PathVariable String id, @PathVariable String id2,@RequestBody Map<String,String> commento) {
		  DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/YYYY HH:mm:ss");  
		  LocalDateTime now = LocalDateTime.now();
		  Commento com = this.commentoRepository.findById(Integer.parseInt(id2));
		  com.setBody(commento.get("body"));
		  com.setOrarioPubblicazione(dtf.format(now));
		  this.commentoRepository.save(com);
	  }
}
