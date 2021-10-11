package it.univpm.eventchain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = {"it.univpm.eventchain.model"}) 
public class EventChainApplication {

	public static final void main(String[] args) {
		SpringApplication.run(EventChainApplication.class, args);
	}

}
