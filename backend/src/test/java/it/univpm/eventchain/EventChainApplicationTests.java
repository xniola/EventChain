package it.univpm.eventchain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigInteger;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.web3j.EVMTest;
import org.web3j.protocol.Web3j;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

import it.univpm.eventchain.contract.EventContract;
import it.univpm.eventchain.repository.CommentRepository;
import it.univpm.eventchain.repository.EventRepository;
import it.univpm.eventchain.repository.TicketRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@EVMTest
@TestMethodOrder(OrderAnnotation.class)
class EventChainApplicationTests {
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private EventRepository eventRepository;
			
	@Autowired
	private TicketRepository ticketRepository;	
					
	@Test
	@Order(1)
	void contextLoads() {
		Boolean result = true; 
		assertEquals(true,result);
	}
	
	@Test
	@Order(2)
	void clearDatabase() {
		this.commentRepository.deleteAll();
		this.ticketRepository.deleteAll();
		this.eventRepository.deleteAll();
		assertEquals(0,this.commentRepository.count()+this.eventRepository.count()
					+this.ticketRepository.count());
	}
					
	@Test
	@Order(3)
	void eventContractTest(Web3j web3j,TransactionManager transactionManager,ContractGasProvider gasProvider) throws Exception {
		String eventTitle = "Evento di prova";
		String eventLocation = "Location di prova";
		String contractAddress = EventContract.deploy(web3j, transactionManager, gasProvider,eventTitle,BigInteger.valueOf(1),
													  BigInteger.valueOf(1),
													  eventLocation).send().getContractAddress();		
		EventContract eventContract = EventContract.load(contractAddress,web3j,transactionManager, gasProvider);										
		assertEquals(eventTitle,eventContract.title().send());
	}
	
}
