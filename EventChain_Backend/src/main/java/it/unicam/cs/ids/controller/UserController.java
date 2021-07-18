package it.unicam.cs.ids.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import it.unicam.cs.ids.model.*;
import it.unicam.cs.ids.repository.CommentoRepository;
import it.unicam.cs.ids.repository.EventRepository;
import it.unicam.cs.ids.repository.UserRepository;

@RestController
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private EventRepository eventRepository;
	
	@Autowired
	private CommentoRepository commentoRepository;
	  
	 @GetMapping("/users")
	    public List<User> index(){
	        return userRepository.findAll();
	    }
	  
	  @GetMapping("/users/{email}")
	  /**
	   * Ritorna un user con email specifica
	   */
	  public User findUser(@PathVariable String email) {
		  return userRepository.findByEmail(email);
	  }
	  
	  @PostMapping("/users")
	  /**
	   * Crea un nuovo utente
	   */
	    public void create(@RequestBody Map<String, String> body){	  
	        String nome = body.get("nome");
	        String cognome = body.get("cognome");
	        String email = body.get("email");
	        String photoUrl = body.get("photoUrl");
	        if (userRepository.findByEmail(email) == null)
	         userRepository.save(new User(nome, cognome,email,photoUrl));
	    }
	  
	
	  
	  @GetMapping("/users/{email}/participantEvent")
	  /**
	   * Ritorna la lista degli eventi a cui un utente con email specifica parteciperà
	   */
	  public Set<Event> showParticipantEvents(@PathVariable String email) {
		  if(userRepository.findByEmail(email) != null)
		  return userRepository.findByEmail(email).getPartecipazioneEventi();
		  else return null;
	  }
	  
	  
	  @GetMapping("/users/{email}/createdEvent")
	  /**
	   * Ritorna la lista degli eventi creati da un utente con email specifica
	   */
	  public Set<Event> showCreatedEvents(@PathVariable String email){
		  User user = userRepository.findByEmail(email);
		  return user.getEventiCreati();
	  }
	  
	  
	  
	  
	  	@PostMapping("/users/{email}/participantEvent/{title}")
	  	/**
	  	 * Aggiunge un evento con titolo specifico alla lista degli eventi a cui un user con email specifica partecipa
	  	 */
	  public void addParticipantEvent(@PathVariable String email, @PathVariable String title) {
	  		User user = userRepository.findByEmail(email);
	  		Event event = eventRepository.findByTitle(title);
	      if(!(user.getPartecipazioneEventi().contains(event))) {
	    	  user.getPartecipazioneEventi().add(event);
	    	  userRepository.save(user);
	      }
	  	}
	  	
	  	
	  	
	  	@PostMapping("/users/{email}/createdEvent/{title}")
	  	/**
	  	 * Aggiunge un evento con titolo specifico alla lista degli eventi creati da un user con email specifica
	  	 */
		  public void addCreatedEvent(@PathVariable String email, @PathVariable String title) {
		  		User user = userRepository.findByEmail(email);
		  		Event event = eventRepository.findByTitle(title);
		  		user.getEventiCreati().add(event);
		  		userRepository.save(user);
		  	}
	  	
	  	
	  
	  @PutMapping("/users/{email}")
	  /**
	   * Modifica le informaizoni di un user con email specifica
	   */
	    public User update(@PathVariable String email, @RequestBody Map<String, String> body){
	        User user = userRepository.findByEmail(email);
	        user.setNome(body.get("nome"));
	        user.setCognome(body.get("cognome"));
	        user.setEmail(body.get("email"));
	        user.setPhotoUrl(body.get("photoUrl"));
	        return userRepository.save(user);
	    }
	  
	  
	  
	  @DeleteMapping("/users/{email}/createdEvent/{title}")
	  /**
	   * Rimuove un evento con titolo specificato dalla lista degli eventi dell'user con email specifica
	   */
	  public void deleteCreatedEvent(@PathVariable String email, @PathVariable String title) {
		  User user = userRepository.findByEmail(email);
		  for(Event e : user.getEventiCreati()) {
			  if(e.getTitle().equals(title))
				  user.getEventiCreati().remove(e);
			      userRepository.save(user);
		  }
	  }
	  
	  
	  @DeleteMapping("/users/{email}/participantEvent/{title}")
	  /**
	   * Rimuove un evento con titolo specificato dalla lista degli eventi dell'user con email specifica
	   */
	  public void deleteParticipantEvent(@PathVariable String email, @PathVariable String title) {
		  User user = userRepository.findByEmail(email);
		  for(Event e : user.getPartecipazioneEventi()) {
			  if(e.getTitle().equals(title))
				  user.getPartecipazioneEventi().remove(e);
			  userRepository.save(user);
		  }
	  }
	  
	  @DeleteMapping("/users/{email}")
	  @Transactional
	  /**
	   * Rimuove un utente con email specifica
	   */
	    public boolean delete(@PathVariable String email){
	        userRepository.deleteByEmail(email);
	        return true;
	    }
	  
	  @DeleteMapping("/users/admin/{id}")
	  	public boolean deleteById(@PathVariable String id) {
	  		userRepository.deleteById(Integer.parseInt(id));
	  		return true;
	  	}
	  
	  
	  @GetMapping("/users/{email}/comments")
	  public Set<Commento> showMyComments(@PathVariable String email){
		  return this.userRepository.findByEmail(email).getCommentiPubblicati();
	  }
	  
	  
	  @PostMapping("/users/{email}/comments/{title}")
	  public void createCommento(@PathVariable String email,@PathVariable String title,@RequestBody Map<String,String> commento) {
		  DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/YYYY HH:mm:ss");  
		  LocalDateTime now = LocalDateTime.now();
		  Commento nuovoCommento = new Commento(
				  commento.get("body"),
				  commento.get("photo"),
				  dtf.format(now),
				  Integer.parseInt(commento.get("idEvento")));   
		
		this.commentoRepository.save(nuovoCommento);
		  
		User user = this.userRepository.findByEmail(email);
		user.getCommentiPubblicati().add(nuovoCommento);
		this.userRepository.save(user);
		
		Event e = this.eventRepository.findByTitle(title);
		e.getCommento().add(nuovoCommento);
		this.eventRepository.save(e);
	  }
	  
	  @PutMapping("/users/{email}/comments/{id}")
	  public void updateCommento(@PathVariable String email,@PathVariable String id,@RequestBody Map<String,String> body) {
		  DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/YYYY HH:mm:ss");  
		  LocalDateTime now = LocalDateTime.now();
		  Commento c = this.commentoRepository.findById(Integer.parseInt(id));
		  c.setBody(body.get("body"));
		  c.setOrarioPubblicazione(dtf.format(now));
		  this.commentoRepository.save(c);
	  }
	  
	  @PutMapping("/users/{email}/ammonizioni")
	  public void ammonisciUser(@PathVariable String email) {
		  User u = this.userRepository.findByEmail(email);
		  u.setAmmonizioni(u.getAmmonizioni()+1);
		  if(u.getAmmonizioni() == 3) {
			  this.userRepository.delete(u);
		  }
		  else this.userRepository.save(u);
	  }
}
