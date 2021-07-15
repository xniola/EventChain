package it.univpm.EventChain.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name="users")
public class Client {
	
	@Column(name="id")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name="nome")
	private String nome;
	
	@Column(name="cognome")
	private String cognome;
	
	@Column(name="email")
	private String email;
	
	@Column(name="photoUrl")
	private String photoUrl;
	
	@JoinColumn(name = "partecipazioneEventi")
	@OneToMany(fetch = FetchType.EAGER)
	private Set<Event> partecipazioneEventi;
	
	
	public Client() {
		
	}
	
	public Client(String nome,String cognome,String email,String photoUrl) {
		//this.id = countId++;
		this.nome = nome;
		this.cognome = cognome;
		this.email = email;
		this.photoUrl = photoUrl;
		this.partecipazioneEventi = new HashSet<Event>();
	}

	public Set<Event> getPartecipazioneEventi() {
		return partecipazioneEventi;
	}

	public void setPartecipazioneEventi(Set<Event> partecipazioneEventi) {
		this.partecipazioneEventi = partecipazioneEventi;
	}

	public int getId() {
		return id;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Client other = (Client) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		return true;
	}
	
	
	/*
	public GPSLocation getPosition() {
		return position;
	}
	
	public void setPosition(GPSLocation location) {
		this.position = location;
	}
	
	 public GeneratorQR getGeneratorQR() {
		return generatorQR;
	}
	 */
}
