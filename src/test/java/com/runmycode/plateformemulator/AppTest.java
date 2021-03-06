package com.runmycode.plateformemulator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Unit test for simple App.
 */
@RunWith(JUnit4.class)
public class AppTest{
	
	ActivemqTest activemqTest;
	
	@Before
	public void init(){
		try{
			ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
			activemqTest = (ActivemqTest)context.getBean("activemqTest");
		}catch(Exception e){
			Assert.fail(e.getMessage());
		}
		
	}
	
	@Test
    public void testApp()
    {
		try {
			activemqTest.sendMessage();
			Assert.assertTrue(activemqTest.receiveMessage());
		} catch (Exception e) {
			Assert.fail(e.getMessage());
			e.printStackTrace();
		}
		
		
		
		
    }
	
}
