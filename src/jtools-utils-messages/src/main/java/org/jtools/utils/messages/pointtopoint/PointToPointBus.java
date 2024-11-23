package org.jtools.utils.messages.pointtopoint;

/*-
 * #%L
 * Java Tools - Utils - Messages
 * %%
 * Copyright (C) 2024 jtools.org
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

import org.jtools.utils.messages.AMessagesBus;

import jakarta.jms.DeliveryMode;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.MessageConsumer;
import jakarta.jms.MessageListener;
import jakarta.jms.MessageProducer;
import jakarta.jms.Queue;
import jakarta.jms.Session;
// TODO: Auto-generated Javadoc

/**
 * The Class PointToPointBus.
 */
public class PointToPointBus extends AMessagesBus {

	// //////////////////////////////
	//
	// Attributes and constants
	//
	// //////////////////////////////

	/** The ptp queue. */
	private Queue ptpQueue;
	
	/** The ptp producer. */
	private MessageProducer ptpProducer;
	
	/** The ptp consumer. */
	private MessageConsumer ptpConsumer;

	/** The ptp messages dispatcher. */
	private PointToPointMessageDispatcher ptpMessagesDispatcher;

	// //////////////////////////////
	//
	// Constructors / Close
	//
	// //////////////////////////////

	/**
	 * Instantiates a new point to point bus.
	 *
	 * @param url the url
	 */
	public PointToPointBus(String url) {
		this(url, null);
	}
	
	/**
	 * Instantiates a new point to point bus.
	 *
	 * @param url the url
	 * @param clientConnectionProperties the client connection properties
	 */
	public PointToPointBus(String url, Properties clientConnectionProperties) {
		super(url, clientConnectionProperties);
	}

	/**
	 * Close.
	 *
	 * @throws Exception the exception
	 */
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

	/**
	 * Adds the listener.
	 *
	 * @param listener the listener
	 */
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

	/**
	 * Removes the listener.
	 *
	 * @param listener the listener
	 */
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

	/**
	 * Send object message.
	 *
	 * @param payload the payload
	 */
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

	/**
	 * Send text message.
	 *
	 * @param payload the payload
	 */
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

	/**
	 * Gets the queue.
	 *
	 * @return the queue
	 * @throws JMSException the JMS exception
	 */
	private Queue getQueue() throws JMSException {
		if (ptpQueue == null) {
			Session session = getSession();
			this.ptpQueue = session.createQueue("ptpQueue");
		}
		return ptpQueue;
	}

	/**
	 * Gets the consumer.
	 *
	 * @return the consumer
	 * @throws JMSException the JMS exception
	 */
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

	/**
	 * Gets the producer.
	 *
	 * @return the producer
	 * @throws JMSException the JMS exception
	 */
	private MessageProducer getProducer() throws JMSException {
		if (ptpProducer == null) {
			Queue queue = getQueue();
			Session session = getSession();

			this.ptpProducer = session.createProducer(queue);
			ptpProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
		}
		return ptpProducer;
	}

	/**
	 * Gets the messages dispatcher.
	 *
	 * @return the messages dispatcher
	 * @throws JMSException the JMS exception
	 */
	private PointToPointMessageDispatcher getMessagesDispatcher() throws JMSException {
		if (ptpMessagesDispatcher == null) {
			this.ptpMessagesDispatcher = new PointToPointMessageDispatcher();
			MessageConsumer consumer = getConsumer();
			consumer.setMessageListener(ptpMessagesDispatcher);
		}
		return ptpMessagesDispatcher;
	}
}
