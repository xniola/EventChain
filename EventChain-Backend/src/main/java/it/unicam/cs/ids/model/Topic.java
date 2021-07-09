package it.unicam.cs.ids.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonValue;

@Entity
@Table(name = "topic")
public class Topic {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private int id;
	
	@Column
	private TopicEnum argomento;
	
	public Topic() {
		
	}
	
	public Topic(int argomento) {
		
		switch(argomento) {
		case 1:
		  this.argomento = TopicEnum.STUDIO;
		break;
		
		case 2:
		  this.argomento = TopicEnum.AMICI;
		break;
		
		case 3:
		  this.argomento = TopicEnum.SPORT;
		break;
		  
		case 4:
			this.argomento = TopicEnum.PARTY;
		break;
		
		case 5:
			this.argomento = TopicEnum.GENERALE;
		break;
		default:
			this.argomento = TopicEnum.GENERALE;
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public TopicEnum getArgomento() {
		return argomento;
	}

	public void setArgomento(int argomento) {
		switch(argomento) {
		case 1:
		  this.argomento = TopicEnum.STUDIO;
		break;
		
		case 2:
		  this.argomento = TopicEnum.AMICI;
		break;
		
		case 3:
		  this.argomento = TopicEnum.SPORT;
		break;
		  
		case 4:
			this.argomento = TopicEnum.PARTY;
		break;
		
		case 5:
			this.argomento = TopicEnum.GENERALE;
		break;
		default:
			this.argomento = TopicEnum.GENERALE;
	    }
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
		Topic other = (Topic) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	@JsonValue
	public String toString() {
		return this.argomento.name();
	}
	
	
}
