package it.unicam.cs.ids.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Ticket")
public class Ticket {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private int id;

    @JoinColumn
	@ManyToOne(cascade = CascadeType.ALL)
	private Buyer buyer;
	
	@JoinColumn
	@ManyToOne(cascade = CascadeType.ALL)
	private Event event;

    @JoinColumn
	@ManyToOne(cascade = CascadeType.ALL)
	private Payment payment;

    @Column
	private Double price;

    @JoinColumn
	@ManyToOne(cascade = CascadeType.ALL)
	private Seller seller;
    	
	@Column
	private String type;
	
	@Column
	private TicketState state;
		
	public Ticket() {

	}

		
}