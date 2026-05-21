package com.ftn.sbnz.service;

import com.ftn.sbnz.kjar.BackwardChainingKieBaseFactory;
import com.ftn.sbnz.kjar.ForwardChainingKieBaseFactory;
import org.kie.api.KieBase;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceApplication.class, args);
	}

	@Bean
	public KieBase forwardChainingKieBase() {
		return ForwardChainingKieBaseFactory.create();
	}

	@Bean
	public KieBase backwardChainingKieBase() {
		return BackwardChainingKieBaseFactory.create();
	}

}
