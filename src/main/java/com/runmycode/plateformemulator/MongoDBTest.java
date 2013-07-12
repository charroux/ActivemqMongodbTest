package com.runmycode.plateformemulator;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.WriteResult;

public class MongoDBTest {

	class MyCollection{
		
		String name = "RunMyCode";

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
		
	}

	MongoOperations mongoOperations;
	
	public MongoDBTest(){
		try
		{
			ApplicationContext ctx = new GenericXmlApplicationContext("applicationContextMongoDB.xml");		    
			mongoOperations = (MongoOperations)ctx.getBean("mongoTemplate");
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	public void write(){
		//mongoOperations.insert(new MyCollection(), "myCollection");
		DBCollection table = mongoOperations.getCollection("user");
		BasicDBObject document = new BasicDBObject();
		document.put("name", "RunMyCode");
		table.insert(document);
	}
	
	public boolean request(){
		DBCollection table = mongoOperations.getCollection("user");
		 
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("name", "RunMyCode");
	 
		DBCursor cursor = table.find(searchQuery);
	 
		if(cursor.hasNext()){
			return true;
		} else {
			return false;
		}
		//return true;
	}
	
	public void delete(){
		DBCollection table = mongoOperations.getCollection("user");
		 
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("name", "RunMyCode");
	 
		WriteResult result = table.remove(searchQuery);
		System.out.println(result.getN());
	}
}
