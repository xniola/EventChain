package it.unicam.cs.ids.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name="comment")
public class Commento {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private int id;
	
	@Column
	private int idEvento;
	
	@Column
	private String body;
	
	@Column
	private String photoMittente;
	
	@Column
	private String orarioPubblicazione;
	
	public Commento() {
		
	}
	
	public Commento(String body,String photo,String orario,int idEvento) {
		this.body = body;
		this.photoMittente = photo;
		this.orarioPubblicazione = orario;
		this.idEvento = idEvento;
	}
	
	

	public int getIdEvento() {
		return idEvento;
	}


	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	
	public String getPhotoMittente() {
		return photoMittente;
	}

	public void setPhotoMittente(String photoMittente) {
		this.photoMittente = photoMittente;
	}

	public int getId() {
		return id;
	}

	public String getOrarioPubblicazione() {
		return orarioPubblicazione;
	}

	public void setOrarioPubblicazione(String orarioPubblicazione) {
		this.orarioPubblicazione = orarioPubblicazione;
	}
	
}
