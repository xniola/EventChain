package it.unicam.cs.ids.test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import it.unicam.cs.ids.controller.EventController;
import it.unicam.cs.ids.controller.LocationController;
import it.unicam.cs.ids.controller.TopicController;
import it.unicam.cs.ids.controller.UserController;
import it.unicam.cs.ids.model.Event;
import it.unicam.cs.ids.model.Location;
import it.unicam.cs.ids.model.Topic;
import it.unicam.cs.ids.model.User;
import it.unicam.cs.ids.repository.EventRepository;
import it.unicam.cs.ids.repository.LocationRepository;
import it.unicam.cs.ids.repository.TopicRepository;
import it.unicam.cs.ids.repository.UserRepository;

@SpringBootTest
class JustMeetTest {
	
	@Autowired
	private UserController userController;
	
	@Autowired
	private EventController eventController;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private EventRepository eventRepository;
	
	@Autowired
	private LocationRepository locationRepository;
	
	@Autowired
	private TopicRepository topicRepository;
	
	@Autowired
	private LocationController locationController;
	
	@Autowired
	private TopicController topicController;
	
	@Test
	public void clearDatabase() {
		this.userRepository.deleteAll();
		this.eventRepository.deleteAll();
		this.locationRepository.deleteAll();
		this.topicRepository.deleteAll();
		assertEquals(0,this.userRepository.count()+this.eventRepository.count()
					+this.locationRepository.count()+this.topicRepository.count());
	}

	@Test
	public void findUserTest() {
	    
		//                        nome,      cognome,             email,                        photoUrl
	    User stefano = new User("Stefano", "Perniola","stefano.perniola0@gmail.com","https://lh3.googleusercontent.com/a-/AOh14GgnhU9UFv2ZN6eFbYEmZQ9B-7KZkIxY1dCzvOfa2w");
	    userRepository.save(stefano);
	    User found = userController.findUser(stefano.getEmail());
	    assertEquals(found,stefano);
	    userRepository.deleteAll();
	    
	}
	
	@Test
	public void indexTest() {

		User stefano = new User("Stefano", "Perniola","stefano.perniola0@gmail.com", "https://lh3.googleusercontent.com/a-/AOh14GgnhU9UFv2ZN6eFbYEmZQ9B-7KZkIxY1dCzvOfa2w");
		User utente = new User("NomeUtente1","CognomeUtente1","email.utente10@gmail.com","./utente1.png");
		
		List<User> risultatoAtteso = new ArrayList<User>();
		risultatoAtteso.add(stefano);
		risultatoAtteso.add(utente);
		
		List<User> risultatoEffettivo = new ArrayList<User>(); 
		userRepository.save(utente);
		userRepository.save(stefano);
		risultatoEffettivo = userController.index();
		
		assertEquals(true,risultatoEffettivo.containsAll(risultatoAtteso));
		userRepository.deleteAll();
	}
	
	@Test
	public void createTest() {
		User stefano = new User("Stefano", "Perniola","stefano.perniola0@gmail.com", "https://lh3.googleusercontent.com/a-/AOh14GgnhU9UFv2ZN6eFbYEmZQ9B-7KZkIxY1dCzvOfa2w");
		Map<String,String> json = new HashMap<String,String>();
		json.put("nome",stefano.getNome());
		json.put("cognome", stefano.getCognome());
		json.put("email",stefano.getEmail());
		json.put("photoUrl",stefano.getPhotoUrl());
		
		userController.create(json);
		assertEquals(stefano,userController.findUser(stefano.getEmail()));
		userRepository.deleteAll();
	}

	@Test
	public void showParticipantEventsTest(){
		
		User stefano = new User("Stefano", "Perniola","stefano.perniola0@gmail.com", "https://lh3.googleusercontent.com/a-/AOh14GgnhU9UFv2ZN6eFbYEmZQ9B-7KZkIxY1dCzvOfa2w");
		Location location = new Location("Recanati",43.12345,13.12345);
		Topic topic = new Topic(5);
		Event evento = new Event("Capodanno","è una cena","21 Marzo 21.00",location,topic,"Org",20);
		stefano.getPartecipazioneEventi().add(evento);
	
		eventRepository.save(evento);
		userRepository.save(stefano);
		Set<Event> risultatoEffettivo = new HashSet<Event>();
		risultatoEffettivo = userController.showParticipantEvents(stefano.getEmail());
		assertEquals(true,risultatoEffettivo.containsAll(stefano.getPartecipazioneEventi()));
		userRepository.deleteAll();
		eventRepository.deleteAll();
	}
	
	
	@Test
	public void showCreatedEventTest() {
		User stefano = new User("Stefano", "Perniola","stefano.perniola0@gmail.com", "https://lh3.googleusercontent.com/a-/AOh14GgnhU9UFv2ZN6eFbYEmZQ9B-7KZkIxY1dCzvOfa2w");
		Location location = new Location("Recanati",43.12345,13.12345);
		Topic topic = new Topic(5);
		Event evento1 = new Event("Capodanno","è una cena","21 Marzo 21.00",location,topic,"Org",20);
		Location location2 = new Location("Recanati",43.54321,13.54321);
		Topic topic2 = new Topic(5);
		Event evento2 = new Event("Capodanno","è una cena","21 Marzo 21.00",location2,topic2,"Org",20);
		
		eventRepository.save(evento1);
		eventRepository.save(evento2);
		
		stefano.getEventiCreati().add(evento1);
		stefano.getEventiCreati().add(evento2);
		userRepository.save(stefano);
		
		Set<Event> risultatoAtteso = new HashSet<Event>();
		risultatoAtteso.add(evento1);
		risultatoAtteso.add(evento2);
		
		User u = userRepository.findByEmail(stefano.getEmail());
		assertEquals(true,u.getEventiCreati().containsAll(risultatoAtteso));
		eventRepository.deleteAll();
		userRepository.deleteAll();
	}
	
	@Test
	public void addParticipantEventTest() {
		User stefano = new User("Stefano", "Perniola","stefano.perniola0@gmail.com", "https://lh3.googleusercontent.com/a-/AOh14GgnhU9UFv2ZN6eFbYEmZQ9B-7KZkIxY1dCzvOfa2w");
		Location location = new Location("Recanati",43.12345,13.12345);
		Topic topic = new Topic(5);
		Event evento = new Event("Capodanno","è una cena","21 Marzo 21.00",location,topic,"Org",20);
		
		eventRepository.save(evento);
		userRepository.save(stefano);
		
		userController.addParticipantEvent(stefano.getEmail(), evento.getTitle());
		User u = userRepository.findByEmail(stefano.getEmail());	
		assertEquals(true,u.getPartecipazioneEventi().contains(evento));
		userRepository.deleteAll();
		eventRepository.deleteAll();
	}
	
	@Test
	public void addParticipantAlreadyExistTest() {
		User stefano = new User("Stefano", "Perniola","stefano.perniola0@gmail.com", "https://lh3.googleusercontent.com/a-/AOh14GgnhU9UFv2ZN6eFbYEmZQ9B-7KZkIxY1dCzvOfa2w");
		Location location = new Location("Recanati",43.12345,13.12345);
		Topic topic = new Topic(5);
		Event evento = new Event("Capodanno","è una cena","21 Marzo 21.00",location,topic,"Org",20);
		eventRepository.save(evento);
		userRepository.save(stefano);
		userController.addParticipantEvent(stefano.getEmail(), evento.getTitle());
		userController.addParticipantEvent(stefano.getEmail(), evento.getTitle());
		User u = userRepository.findByEmail(stefano.getEmail());
		
		assertEquals(1,u.getPartecipazioneEventi().size());
		
		eventRepository.deleteAll();
		userRepository.deleteAll();
	}
	
	@Test
	public void addCreatedEventTest() {
		User stefano = new User("Stefano", "Perniola","stefano.perniola0@gmail.com", "https://lh3.googleusercontent.com/a-/AOh14GgnhU9UFv2ZN6eFbYEmZQ9B-7KZkIxY1dCzvOfa2w");
		Location location = new Location("Recanati",43.12345,13.12345);
		Topic topic = new Topic(5);
		Event evento = new Event("Capodanno","è una cena","21 Marzo 21.00",location,topic,"Org",20);
		eventRepository.save(evento);
		userRepository.save(stefano);
		
		userController.addCreatedEvent(stefano.getEmail(), evento.getTitle());
		User u = userRepository.findByEmail(stefano.getEmail());
		assertEquals(true,u.getEventiCreati().contains(evento));
		userRepository.deleteAll();
		eventRepository.deleteAll();
	}
	
	@Test
	public void updateUserTest() {
		User stefano = new User("Stefano", "Perniola","stefano.perniola0@gmail.com", "https://lh3.googleusercontent.com/a-/AOh14GgnhU9UFv2ZN6eFbYEmZQ9B-7KZkIxY1dCzvOfa2w");
		userRepository.save(stefano);
		
		Map<String,String> json = new HashMap<String,String>();
		json.put("nome", "Stivenson");
		json.put("cognome", "Perno");
		json.put("email", "stivenson.perno@gmail.com");
		json.put("photoUrl","./stiv.png");
		
	    User updatedUser = userController.update(stefano.getEmail(),json);
		assertEquals("stivenson.perno@gmail.com",updatedUser.getEmail());
		
		userRepository.deleteAll();
	}
	
	@Test
	public void deleteCreatedEventTest() {
		User stefano = new User("Stefano", "Perniola","stefano.perniola0@gmail.com", "https://lh3.googleusercontent.com/a-/AOh14GgnhU9UFv2ZN6eFbYEmZQ9B-7KZkIxY1dCzvOfa2w");
		Location location = new Location("Recanati",43.12345,13.12345);
		Topic topic = new Topic(5);
		Event evento = new Event("Capodanno","è una cena","21 Marzo 21.00",location,topic,"Org",20);
		stefano.getEventiCreati().add(evento);
		
		eventRepository.save(evento);
		userRepository.save(stefano);
		
		userController.deleteCreatedEvent(stefano.getEmail(), evento.getTitle());
		User u = userRepository.findByEmail(stefano.getEmail());
		assertEquals(true,u.getEventiCreati().isEmpty());
		userRepository.deleteAll();
		eventRepository.deleteAll();
	}
	
	@Test
	public void deleteParticipantEventTest() {
		User stefano = new User("Stefano", "Perniola","stefano.perniola0@gmail.com", "https://lh3.googleusercontent.com/a-/AOh14GgnhU9UFv2ZN6eFbYEmZQ9B-7KZkIxY1dCzvOfa2w");
		Location location = new Location("Recanati",43.12345,13.12345);
		Topic topic = new Topic(5);
		Event evento = new Event("Capodanno","è una cena","21 Marzo 21.00",location,topic,"Org",20);
		stefano.getPartecipazioneEventi().add(evento);
		
		eventRepository.save(evento);
		userRepository.save(stefano);
		
		userController.deleteParticipantEvent(stefano.getEmail(), evento.getTitle());
		User u = userRepository.findByEmail(stefano.getEmail());
		assertEquals(true,u.getPartecipazioneEventi().isEmpty());
		userRepository.deleteAll();
		eventRepository.deleteAll();
	}
	
	
	@Test
	public void deleteUserTest() {
		User stefano = new User("Stefano", "Perniola","stefano.perniola0@gmail.com", "https://lh3.googleusercontent.com/a-/AOh14GgnhU9UFv2ZN6eFbYEmZQ9B-7KZkIxY1dCzvOfa2w");
		userRepository.save(stefano);
		userController.delete(stefano.getEmail());
		assertEquals(true,userRepository.count() == 0);
	}
	
	
	@Test
	public void eventIndexTest() {
		Location location = new Location("Recanati",43.12345,13.12345);
		
		Topic topic = new Topic(5);
		Event evento1 = new Event("Capodanno","è una cena","21 Marzo 21.00",location,topic,"Org",20);
		
		Location location2 = new Location("Recanati",43.54321,13.54321);
		Topic topic2 = new Topic(5);
		Event evento2 = new Event("Capodanno","è una cena","21 Marzo 21.00",location2,topic2,"Org",20);
		List<Event> risultatoAtteso = new ArrayList<Event>();
		risultatoAtteso.add(evento1);
		risultatoAtteso.add(evento2);
		
		List<Event> risultatoEffettivo = new ArrayList<Event>(); 
		eventRepository.save(evento1);
		eventRepository.save(evento2);
		risultatoEffettivo = eventController.index();
		
		assertEquals(true,risultatoEffettivo.containsAll(risultatoAtteso));
		eventRepository.deleteAll();
		topicRepository.deleteAll();
	}
	
	@Test
	public void eventShowTest() {
		Location location = new Location("Recanati",43.12345,13.12345);
		Topic topic = new Topic(5);
		Event evento1 = new Event("Capodanno","è una cena","21 Marzo 21.00",location,topic,"Org",20);;
		
		Location location2 = new Location("Recanati",43.54321,13.54321);
		Topic topic2 = new Topic(5);
		Event evento2 = new Event("Capodanno","è una cena","21 Marzo 21.00",location2,topic2,"Org",20);
		eventRepository.save(evento1);
		eventRepository.save(evento2);
		
		String idEvent = Integer.toString(evento1.getId());
		Event e = eventController.show(idEvent);
		assertEquals(evento1.getTitle(),e.getTitle());
		eventRepository.deleteAll();
		topicRepository.deleteAll();
	}
	
	@Test
	public void createEventTest() {
		Map<String,String> json = new HashMap<String,String>();
		json.put("title", "Sagra della salsiccia");
		json.put("description", "Partecipa alla XIV Edizione della Sagra della Salsiccia!");
		json.put("date","9000");
		json.put("latitude","43.12345");
		json.put("longitude", "13.12345");
		json.put("orario","19.00");
		json.put("topic", "5");
		json.put("organizzatore", "Comune di Ascoli Piceno");
		json.put("numPartecipanti", "800");
		
		eventController.create(json);
		assertEquals(1,eventRepository.count());
		eventRepository.deleteAll();
		topicRepository.deleteAll();
	}
	
	@Test
	public void showParticipantOfEventTest() {
		Location location = new Location("Recanati",43.12345,13.12345);
		Topic topic = new Topic(5);
		Event evento1 = new Event("Capodanno","è una cena","21 Marzo 21.00",location,topic,"Org",20);
		User stefano = new User("Stefano", "Perniola","stefano.perniola0@gmail.com", "https://lh3.googleusercontent.com/a-/AOh14GgnhU9UFv2ZN6eFbYEmZQ9B-7KZkIxY1dCzvOfa2w");
		User utente = new User("NomeUtente1","CognomeUtente1","email.utente10@gmail.com","./utente1.png");
		
		evento1.getParticipants().add(stefano.getFullName());
		evento1.getParticipants().add(utente.getFullName());
		
		userRepository.save(stefano);
		userRepository.save(utente);
		eventRepository.save(evento1);
		
		List<String> valoreAtteso = new ArrayList<String>();
		valoreAtteso.add(stefano.getFullName());
		valoreAtteso.add(utente.getFullName());
		
		List<String> participants = eventController.showParticipants(Integer.toString(evento1.getId()));
		
		assertEquals(true,participants.containsAll(valoreAtteso));
		userRepository.deleteAll();
		eventRepository.deleteAll();
		topicRepository.deleteAll();
	}
	
	
	@Test
	public void addParticipantOfEventTest() {
		Location location = new Location("Recanati",43.12345,13.12345);
		Topic topic = new Topic(5);
		Event evento1 = new Event("Capodanno","è una cena","21 Marzo 21.00",location,topic,"Org",20);
		User stefano = new User("Stefano", "Perniola","stefano.perniola0@gmail.com", "https://lh3.googleusercontent.com/a-/AOh14GgnhU9UFv2ZN6eFbYEmZQ9B-7KZkIxY1dCzvOfa2w");
			
		userRepository.save(stefano);
		eventRepository.save(evento1);
		
		Map<String,String> json = new HashMap<String,String>();
		json.put("nome", stefano.getNome());
		json.put("cognome", stefano.getCognome());
		json.put("email", stefano.getEmail());
		json.put("photoUrl", stefano.getPhotoUrl());
		
		eventController.addPartecipant((Integer.toString(evento1.getId())), json);
		User u = userRepository.findByEmail(stefano.getEmail());
		assertEquals(u,stefano);
		
		userRepository.deleteAll();
		eventRepository.deleteAll();
		topicRepository.deleteAll();
	}
	
	@Test
	public void deleteParticipantOfEventTest() {
		Location location = new Location("Recanati",43.12345,13.12345);
		Topic topic = new Topic(5);
		Event evento1 = new Event("Capodanno","è una cena","21 Marzo 21.00",location,topic,"Org",20);
		User stefano = new User("Stefano", "Perniola","stefano.perniola0@gmail.com", "https://lh3.googleusercontent.com/a-/AOh14GgnhU9UFv2ZN6eFbYEmZQ9B-7KZkIxY1dCzvOfa2w");
			
		evento1.getParticipants().add(stefano.getFullName());
		userRepository.save(stefano);
		eventRepository.save(evento1);
		
		Map<String,String> json = new HashMap<String,String>();
		json.put("fullName", stefano.getFullName());
		
		String idEvent = Integer.toString(evento1.getId());
		eventController.deleteParticipant(idEvent,stefano.getEmail());
		Event e = eventController.show(idEvent);
		
		assertEquals(0,e.getParticipants().size());
		userRepository.deleteAll();
		eventRepository.deleteAll();
		topicRepository.deleteAll();
	}
	
	@Test
	public void updateEventTest() {
		Location location = new Location("Recanati",43.12345,13.12345);
		Topic topic = new Topic(5);
		Event evento1 = new Event("Capodanno","è una cena","21 Marzo 21.00",location,topic,"Org",20);
		eventRepository.save(evento1);
		
		Map<String,String> json = new HashMap<String,String>();
		json.put("title", "Sagra della salsiccia");
		json.put("description", "Partecipa alla XIV Edizione della Sagra della Salsiccia!");
		json.put("date","90000");
		json.put("latitude","43.12345");
		json.put("longitude", "13.12345");
		json.put("orario","19.00");
		json.put("topic", "5");
		json.put("organizzatore", "Comune di Ascoli Piceno");
		json.put("numPartecipanti", "800");
		
		String idEvent = Integer.toString(evento1.getId());
		eventController.update(idEvent, json);
		Event e = eventController.show(idEvent);
		
		assertEquals("Sagra della salsiccia",e.getTitle());
		eventRepository.deleteAll();
		topicRepository.deleteAll();
	}
	
	@Test
	public void deleteEventTest() {
		Location location = new Location("Recanati",43.12345,13.12345);
		Topic topic = new Topic(5);
		Event evento1 = new Event("Capodanno","è una cena","21 Marzo 21.00",location,topic,"Org",20);
		eventRepository.save(evento1);
		
		String idEvent = Integer.toString(evento1.getId());
		eventController.delete(idEvent);
		
		assertEquals(0,eventRepository.count());
		topicRepository.deleteAll();
	}
	
	@Test
	public void locationIndexTest() {
		Location location1 = new Location("Recanati",43.12345,13.12345);
		Location location2 = new Location("Recanati",43.54321,13.54321);
		
		locationRepository.save(location1);
		locationRepository.save(location2);
		
		List<Location> risultatoAtteso = new ArrayList<Location>();
		risultatoAtteso.add(location1);
		risultatoAtteso.add(location2);
		
		assertEquals(true,locationController.index().containsAll(risultatoAtteso));
		locationRepository.deleteAll();
		topicRepository.deleteAll();
	}
	
	@Test
	public void findLocationTest() {
		Location location1 = new Location("Recanati",43.12345,13.12345);
		locationRepository.save(location1);
		
		String id = Integer.toString(location1.getId());
		Location loc = locationController.findLocation(id);
		
		assertEquals(location1,loc);
		locationRepository.deleteAll();
		topicRepository.deleteAll();
	}
	
	@Test
	public void createLocationTest() {
		Map<String,String> json = new HashMap<String,String>();
		json.put("latitude", "43.12345");
		json.put("longitude", "13.12345");
		
		locationController.create(json);
		
		assertEquals(1,locationRepository.count());
		locationRepository.deleteAll();
	}
	
	@Test
	public void updateLocationTest() {
		Location location1 = new Location("Recanati",43.12345,13.12345);
		locationRepository.save(location1);
		
		Map<String,String> json = new HashMap<String,String>();
		json.put("latitude", "50.12345");
		json.put("longitude", "20.12345");
		
		Location loc = locationController.update(Integer.toString(location1.getId()), json);
		
		assertEquals(true,loc.getLatitudine() == 50.12345);
		locationRepository.deleteAll();
	}
	
	@Test
	public void deleteLocationTest() {
		Location location1 = new Location("Recanati",43.12345,13.12345);
		locationRepository.save(location1);
		
		locationController.delete(Integer.toString(location1.getId()));
		
		assertEquals(0,locationRepository.count());
	}
	
	@Test
	public void topicIndexTest() {
		Topic topic1 = new Topic(5);
		Topic topic2 = new Topic(5);
		topicRepository.save(topic1);
		topicRepository.save(topic2);
		
		List<Topic> risultatoAtteso = new ArrayList<Topic>();
		risultatoAtteso.add(topic1);
		risultatoAtteso.add(topic2);
		
		assertEquals(true,topicController.index().containsAll(risultatoAtteso));
		topicRepository.deleteAll();
	}
	
	@Test
	public void findTopicTest() {
		Topic topic1 = new Topic(5);
		topicRepository.save(topic1);
		
		Topic t = topicController.findTopic(Integer.toString(topic1.getId()));
		assertEquals(topic1,t);
		topicRepository.deleteAll();
	}
	
	@Test
	public void createTopicTest() {
		Map<String,String> json = new HashMap<String,String>();
		json.put("argomento","5");
		
		topicController.create(json);
		assertEquals(1,topicRepository.count());
		topicRepository.deleteAll();
	}
	
	@Test
	public void updateTopicTest() {
		Topic topic = new Topic(1);
		topicRepository.save(topic);
		
		Map<String,String> json = new HashMap<String,String>();
		json.put("argomento","5");
		
		String idTopic = Integer.toString(topic.getId());
		Topic t = topicController.update(idTopic, json);
		
		assertEquals(topicController.findTopic(idTopic).getArgomento(),t.getArgomento());
		topicRepository.deleteAll();
	}
	
	@Test
	public void deleteTopicTest() {
		Topic topic = new Topic(5);
		topicRepository.save(topic);
		
		topicController.delete(Integer.toString(topic.getId()));
		assertEquals(0,topicRepository.count());
	}
	
}
