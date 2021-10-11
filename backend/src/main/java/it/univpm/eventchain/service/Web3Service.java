package it.univpm.eventchain.service;

import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;

@Service
public final class Web3Service{
			
	@Nullable
	private DefaultGasProvider defaultGasProvider;
	
	@Autowired
	private Environment environment;
	
	@Nullable
	private Web3j web3j;
	
	public final void init() {
		this.web3j=Web3j.build(new HttpService(environment.getProperty("eventchain.ethereum.provider")));
		this.defaultGasProvider=new DefaultGasProvider();
	}
			
	@Nullable
	public final DefaultGasProvider getDefaultGasProvider() {
		return this.defaultGasProvider;
	}
	
	@Nullable
	public final Web3j getWeb3j() {
		return this.web3j;
	}
	
}
