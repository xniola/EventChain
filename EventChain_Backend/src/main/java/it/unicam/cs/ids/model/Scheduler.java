package it.unicam.cs.ids.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name = "scheduling")
public class Scheduler {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private int id;
	
	
	@Column
	private String orarioScheduling;

	
	public Scheduler() {	
	     DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	     String todayAsString = df.format(Calendar.getInstance().getTime());
		this.orarioScheduling = todayAsString;
	}

	public String getOrarioScheduling() {
		return orarioScheduling;
	}


	public int getId() {
		return id;
	}
	
	
  
}