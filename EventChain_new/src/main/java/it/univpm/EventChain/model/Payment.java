package com.univpm.EventChain.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Payment")
public class Payment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private int id;
    	
	@Column
	private Date date;
   
    @Column
	private Double total;
       	
	@Column
	private String type;   
		
	public Payment() {

	}
	
}