package it.unicam.cs.ids.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Location")
public class Location {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private int id;
	
	@Column
	private String nome;
	
	@Column
	private double latitudine;
	
	@Column
	private double longitudine;
	
	public Location() {
		
	}
	
	public Location(String nome,double latitudine, double longitudine) {
		this.nome = nome;
		this.latitudine = latitudine;
		this.longitudine = longitudine;
	}
	
	public double distance(double lat2, double lon2) {
		if ((this.getLatitudine() == lat2) && (this.getLongitudine() == lon2)) 
			return 0;
		
		double theta = this.getLongitudine() - lon2;
		double dist = Math.sin(Math.toRadians(this.getLatitudine())) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(this.getLatitudine())) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
		dist = Math.acos(dist);
		dist = Math.toDegrees(dist);
		dist = dist * 60 * 1.1515;
		dist = dist * 1.609344; // converte in chilometri
		return dist;
		}

	public int getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public double getLatitudine() {
		return latitudine;
	}

	public void setLatitudine(double latitudine) {
		this.latitudine = latitudine;
	}

	public double getLongitudine() {
		return longitudine;
	}

	public void setLongitudine(double longitudine) {
		this.longitudine = longitudine;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		Location other = (Location) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
	
}
