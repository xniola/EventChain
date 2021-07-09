package it.unicam.cs.ids.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import it.unicam.cs.ids.repository.CommentoRepository;

@RestController
public class CommentoController {
	
	@Autowired
	private CommentoRepository commentoRepository;
	
	@GetMapping("/comments")
	public List<Commento> index(){
		return this.commentoRepository.findAll();
	}
	
	@GetMapping("/comments/{id}")
	public Commento findCommento(@PathVariable String id){
		return this.commentoRepository.findById(Integer.parseInt(id));
	}
	
	@PostMapping("/comments")
	public void createCommento(@RequestBody Map<String,String> body) {
		//body: body,email
		String bodyCommento = body.get("body");
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/YYYY HH:mm:ss");  
		LocalDateTime now = LocalDateTime.now();   
		
		Commento nuovoCommento = new Commento(bodyCommento,body.get("photo"),dtf.format(now),Integer.parseInt(body.get("idEvento")));
		this.commentoRepository.save(nuovoCommento);
	}
	
	@PutMapping("/comments/{id}")
	public void modificaCommento(@PathVariable String id,@RequestBody Map<String,String> body) {
		String bodyCommento = body.get("body");
	
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/YYYY HH:mm:ss");  
		LocalDateTime now = LocalDateTime.now();
		
		Commento commento = this.commentoRepository.findById(Integer.parseInt(id));
		commento.setBody(bodyCommento);
		commento.setOrarioPubblicazione(dtf.format(now));
		this.commentoRepository.save(commento);
	}
	
	@DeleteMapping("/comments/{id}")
	public boolean eliminaCommento(@PathVariable String id) {
		this.commentoRepository.deleteById(Integer.parseInt(id));
		return true;
	}
}
