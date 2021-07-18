package it.unicam.cs.ids.controller;

import java.util.List;
import java.util.Map;

//import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import it.unicam.cs.ids.model.Location;
import it.unicam.cs.ids.repository.LocationRepository;

@RestController
public class LocationController {
	
	@Autowired
	private LocationRepository locationRepository;
	
	 @GetMapping("/locations")
	    public List<Location> index(){
	        return locationRepository.findAll();
	    }
	 
	  @GetMapping("/locations/{id}")
	  public Location findLocation(@PathVariable String id) {
		  return locationRepository.findById(Integer.parseInt(id));
	  }
	  
	  @PostMapping("/locations")
	    public void create(@RequestBody Map<String, String> body){	
		  	locationRepository.save(
		  			new Location(
		  						 body.get("nome"),
		  						 Double.parseDouble(body.get("latitude")),
		  						 Double.parseDouble(body.get("longitude")))
		  		);
	        
	    }
	  
	  @PutMapping("/locations/{id}")
	  /**
	   * Modifica le informaizoni di un user con email specifica
	   */
	    public Location update(@PathVariable String id, @RequestBody Map<String, String> body){
	        Location location = locationRepository.findById(Integer.parseInt(id));
	        location.setLatitudine(Double.parseDouble(body.get("latitude")));
	        location.setLongitudine(Double.parseDouble(body.get("longitude")));
	        return locationRepository.save(location);
	    }
	  
	  @DeleteMapping("/locations/{id}")
	  //@Transactional
	  /**
	   * Rimuove un utente con email specifica
	   */
	    public boolean delete(@PathVariable String id){
	        locationRepository.deleteById(Integer.parseInt(id));
	        return true;
	    }
}
