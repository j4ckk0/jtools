/**
 * 
 */
package com.jtools.utils.messages.pointtopoint;

/*-
 * #%L
 * Java Tools - Utils - Messages
 * %%
 * Copyright (C) 2024 j4ckk0
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import java.io.Serializable;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jtools.utils.messages.AMessagesBus;

import jakarta.jms.DeliveryMode;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.MessageConsumer;
import jakarta.jms.MessageListener;
import jakarta.jms.MessageProducer;
import jakarta.jms.Queue;
import jakarta.jms.Session;

/**
 * Examples: 
 * ACTIVEMQ_URL = "tcp://localhost:61616" 
 * BROKER_URL = "broker:(" + ACTIVEMQ_URL + ")";
 * 
 * @author j4ckk0
 *
 */
public class PointToPointBus extends AMessagesBus {

	// //////////////////////////////
	//
	// Attributes and constants
	//
	// //////////////////////////////

	private Queue ptpQueue;
	private MessageProducer ptpProducer;
	private MessageConsumer ptpConsumer;

	private PointToPointMessageDispatcher ptpMessagesDispatcher;

	// //////////////////////////////
	//
	// Constructors / Close
	//
	// //////////////////////////////

	public PointToPointBus(String url) {
		this(url, null);
	}

	/**
	 * See options on :
	 * https://activemq.apache.org/components/classic/documentation/failover-transport-reference
	 * 
	 * @param url
	 * @param clientConnectionProperties
	 */
	public PointToPointBus(String url, Properties clientConnectionProperties) {
		super(url, clientConnectionProperties);
	}

	@Override
	public void close() throws Exception {

		if (ptpMessagesDispatcher != null) {
			ptpMessagesDispatcher.close();
		}

		super.close();
	}

	// //////////////////////////////
	//
	// Methods
	//
	// //////////////////////////////

	public void addListener(MessageListener listener) {

		if(!isStarted()) {
			Logger.getLogger(getClass().getName()).log(Level.FINE, "Point To Point bus is not started. Register listener will fail");
		}
		
		try {
			PointToPointMessageDispatcher messagesDispatcher = getMessagesDispatcher();
			messagesDispatcher.addListener(listener);
		} catch (JMSException e) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage());
			Logger.getLogger(getClass().getName()).log(Level.FINE, e.getMessage(), e);
		}
	}

	public void removeListener(MessageListener listener) {

		if(!isStarted()) {
			Logger.getLogger(getClass().getName()).log(Level.FINE, "Point To Point bus is not started. Register listener will fail");
		}
		
		try {
			PointToPointMessageDispatcher messagesDispatcher = getMessagesDispatcher();
			messagesDispatcher.removeListener(listener);
		} catch (JMSException e) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage());
			Logger.getLogger(getClass().getName()).log(Level.FINE, e.getMessage(), e);
		}
	}

	public void sendObjectMessage(Serializable payload) {
		
		if(!isStarted()) {
			Logger.getLogger(getClass().getName()).log(Level.FINE, "Point To Point bus is not started. No message will be sent");
		}
		
		try {
			Session session = getSession();
			Message msg = session.createObjectMessage(payload);

			Logger.getLogger(getClass().getName()).log(Level.FINE, "Sending message '" + payload + "'");

			MessageProducer producer = getProducer();
			producer.send(msg);
		} catch (JMSException e) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage());
			Logger.getLogger(getClass().getName()).log(Level.FINE, e.getMessage(), e);
		}
	}

	public void sendTextMessage(String payload) {
		
		if(!isStarted()) {
			Logger.getLogger(getClass().getName()).log(Level.FINE, "Point To Point bus is not started. No message will be sent");
		}
		
		try {
			Session session = getSession();
			Message msg = session.createTextMessage(payload);

			Logger.getLogger(getClass().getName()).log(Level.FINE, "Sending message '" + payload + "'");

			MessageProducer producer = getProducer();
			producer.send(msg);
		} catch (JMSException e) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage());
			Logger.getLogger(getClass().getName()).log(Level.FINE, e.getMessage(), e);
		}
	}

	// //////////////////////////////
	//
	// Private Methods
	//
	// //////////////////////////////

	private Queue getQueue() throws JMSException {
		if (ptpQueue == null) {
			Session session = getSession();
			this.ptpQueue = session.createQueue("ptpQueue");
		}
		return ptpQueue;
	}

	private MessageConsumer getConsumer() throws JMSException {
		if (ptpConsumer == null) {
			Queue queue = getQueue();
			Session session = getSession();

			this.ptpConsumer = session.createConsumer(queue);

			PointToPointMessageDispatcher messagesDispatcher = getMessagesDispatcher();
			ptpConsumer.setMessageListener(messagesDispatcher);
		}
		return ptpConsumer;
	}

	private MessageProducer getProducer() throws JMSException {
		if (ptpProducer == null) {
			Queue queue = getQueue();
			Session session = getSession();

			this.ptpProducer = session.createProducer(queue);
			ptpProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
		}
		return ptpProducer;
	}

	private PointToPointMessageDispatcher getMessagesDispatcher() throws JMSException {
		if (ptpMessagesDispatcher == null) {
			this.ptpMessagesDispatcher = new PointToPointMessageDispatcher();
			MessageConsumer consumer = getConsumer();
			consumer.setMessageListener(ptpMessagesDispatcher);
		}
		return ptpMessagesDispatcher;
	}
}
