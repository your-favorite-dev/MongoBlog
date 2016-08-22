package com.appvile.app.mongo;

import org.springframework.stereotype.Component;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;

@Component
public class Client {
	private static MongoClient client;
	private static MongoClientOptions options;
	
	public Client(int numOfConns){
		//MongoClient is heavy weight object use Spring or make a Singleton
		options = new MongoClientOptions
				.Builder()
				.connectionsPerHost(numOfConns)
				.build();
		
		client = new MongoClient(new ServerAddress(), options);
	}
	
	public MongoDatabase getDb(String dbName){
		
		return client.getDatabase(dbName);
	}

}
