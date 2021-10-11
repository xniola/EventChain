package it.univpm.eventchain.config;

import java.util.Arrays;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import it.univpm.eventchain.model.Member;
import it.univpm.eventchain.service.Web3Service;
import kotlin.jvm.internal.Intrinsics;

@Configuration
public class DBConfiguration {

	@Bean
	@NotNull
	public ApplicationRunner initMembers(@NotNull final Web3Service web3Service) {		
		Intrinsics.checkParameterIsNotNull(web3Service, "web3Service");
		return (new ApplicationRunner() {
			
			private final List<String> allRoles = Arrays.asList(Member.ROLE_EVENT_MANAGER,
					Member.ROLE_TICKET_BUYER, Member.ROLE_TICKET_RESELLER, Member.ROLE_USER);

			private final List<String> buyerRole = Arrays.asList(Member.ROLE_TICKET_BUYER,Member.ROLE_USER);
			private final List<String> resellerRole = Arrays.asList(Member.ROLE_TICKET_RESELLER,Member.ROLE_USER);

			public final void run(final ApplicationArguments it) throws Exception {
				
				// Eseguito solo la prima volta
				/*
				Member stefano = new Member("stefano.perniola0@gmail.com",MemberController.GOOGLE_REGISTRATION_ID, allRoles);
				stefano.setPublicName("User 1");
				stefano.setName("Stefano Perniola");
				stefano.setFamilyName("Perniola");
				stefano.setGivenName("Stefano");
				stefano.setPicture("https://lh3.googleusercontent.com/ogw/ADea4I6N343jIQgs9eOyMag6R-znKGfid9DT7OzFpaxPdA=s64-c-mo");
				
	
				
				Member ticketBuyer = new Member("eventchain.ticketbuyer@gmail.com", MemberController.GOOGLE_REGISTRATION_ID, buyerRole);
				Member ticketReseller = new Member("eventchain.ticketreseller@gmail.com",MemberController.GOOGLE_REGISTRATION_ID,resellerRole);				
				
				ticketBuyer.setPublicName("User 4");
				ticketBuyer.setName("Mario Rossi");
				ticketBuyer.setFamilyName("Rossi");
				ticketBuyer.setGivenName("Mario");
				ticketBuyer.setPicture("https://lh3.googleusercontent.com/ogw/ADea4I7Z8MI9niZ83nP-yxVl6glb76DM8HYYEMTs4PlA=s83-c-mo");
				
				ticketReseller.setPublicName("User 5");
				ticketReseller.setName("Fabrizio Fabrizi");
				ticketReseller.setFamilyName("Fabrizi");
				ticketReseller.setGivenName("Fabrizio");
				ticketReseller.setPicture("https://lh3.googleusercontent.com/ogw/ADea4I4_16lKDYX8HYJFacIEfiVGB6CI2X4xCnAJB87V=s83-c-mo");				

				memberRepository.saveAll(Arrays.asList(ticketReseller, ticketBuyer,stefano));
				*/
				
				/* qui andrebbero definiti Event Manager e Ticket Reseller */
				
				web3Service.init();
			}

		});
	}

}
