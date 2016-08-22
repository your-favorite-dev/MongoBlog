package com.appvile.app.mongo;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

@Repository
public class MongoBlogDaoImpl {

	private Client client;
	private MongoCollection<Document> coll;

	@Autowired
	public MongoBlogDaoImpl(Client client, String dbName, String collName) {
		this.client = client;
		setColl(this.client.getDb(dbName).getCollection(collName));
	}

	public Document create(Document document) {
		coll.insertOne(document);
		return document;
	}

	public List<Document> bulkCreate(List<Document> docList) {
		coll.insertMany(docList);
		return docList;
	}
	
	public Document read(){
		return coll.find().first();
	}
	
	public List<Document> readAll() {
		return coll.find().into(new ArrayList<Document>());
	}
	
	public List<Document> readAllByFilter(Bson filter) {
		return coll.find(filter).into(new ArrayList<Document>());
	}
	
	public MongoCursor<Document> readIterator() throws Exception{
		//Always close cursor 
		return coll.find().iterator();
	}

	public MongoCollection<Document> getColl() {
		return coll;
	}

	public void setColl(MongoCollection<Document> coll) {
		this.coll = coll;
	}

}
