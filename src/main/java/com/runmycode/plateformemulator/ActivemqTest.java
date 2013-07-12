/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.runmycode.plateformemulator;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.TreeMap;

import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.tools.Tool;

import org.apache.log4j.Logger;

public class ActivemqTest{
	
	class TextMessageReceiver implements MessageListener{

		@Override
		public void onMessage(Message message) {
			try{
				TextMessage text = (TextMessage)message;
				System.out.println("Text received : " + text.getText());
			}catch(Exception e){
				System.out.println(e.getMessage());
			}
		}
		
	}
	
	private transient Logger logger = Logger.getLogger(com.runmycode.plateformemulator.ActivemqTest.class);
	
	private QueueConnectionFactory connectionFactory;
	private QueueSession jobSubmissionQueueSession;
	private QueueReceiver queueReceiver;
	private QueueSender jobSubmissionQueueSender;

	private int deliveryModeForActiveMQ;
	
	public ActivemqTest(QueueConnectionFactory connectionFactory, Queue jobSubmissionQueue, String messagePersistenceIntoActiveMQ) throws JMSException 
	{
		this.connectionFactory = connectionFactory;
		QueueConnection connection = this.connectionFactory.createQueueConnection();
		jobSubmissionQueueSession = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
		
		logger.debug("JobClient_ClientSide : " + connection + " " + queueReceiver);
		
		jobSubmissionQueueSender = jobSubmissionQueueSession.createSender(jobSubmissionQueue);
		
		if(messagePersistenceIntoActiveMQ == "true"){
			this.deliveryModeForActiveMQ = DeliveryMode.PERSISTENT;
		} else {
			this.deliveryModeForActiveMQ = DeliveryMode.NON_PERSISTENT;
		}
		
		queueReceiver = jobSubmissionQueueSession.createReceiver(jobSubmissionQueue);
		queueReceiver.setMessageListener(new TextMessageReceiver());
		
		connection.start();

	}

  public void sendMessage() throws Exception 
  {  
	  logger.debug("sending the message \"Hello RunMyCode user !\" to ActiMQ");
	  
	  TextMessage textMessage = jobSubmissionQueueSession.createTextMessage("Hello RunMyCode user !");
	  
	  // normal priority = 4
	  // time to live = 0 (no expiration) 
	  jobSubmissionQueueSender.send(textMessage, deliveryModeForActiveMQ, 4, 0);
	  
	  
  }
  
  
}

