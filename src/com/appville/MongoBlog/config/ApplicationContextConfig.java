package com.appville.MongoBlog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.appvile.app.mongo.Client;
import com.appvile.app.mongo.MongoBlogDaoImpl;

@Configuration
public class ApplicationContextConfig {
	@Bean
	public Client client(){
		return new Client(100);
	}
	@Bean(name="testDao")
	public MongoBlogDaoImpl testDao(){
		return new MongoBlogDaoImpl(client(), "course", "insertTest");
	}

}
