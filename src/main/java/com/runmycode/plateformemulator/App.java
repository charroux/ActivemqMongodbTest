package com.runmycode.plateformemulator;

import org.junit.Assert;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	try{
    		
			ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
			ActivemqTest activemqTest = (ActivemqTest)context.getBean("activemqTest");
			activemqTest.sendMessage();
			
			
			MongoDBTest mongoDB = new MongoDBTest();
			mongoDB.write();
			mongoDB.request();
			mongoDB.delete();
			
		}catch(Exception e){
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
    }
}
