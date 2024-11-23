package org.jtools.utils.messages.pubsub;

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
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jtools.utils.messages.AMessagesBus;

import jakarta.jms.DeliveryMode;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.MessageConsumer;
import jakarta.jms.MessageProducer;
import jakarta.jms.Session;
import jakarta.jms.Topic;
// TODO: Auto-generated Javadoc

/**
 * The Class PubSubBus.
 */
public class PubSubBus extends AMessagesBus {

	// //////////////////////////////
	//
	// Attributes and constants
	//
	// //////////////////////////////

	/** The topics. */
	private final Map<String, Topic> topics;
	
	/** The topic producers. */
	private final Map<String, MessageProducer> topicProducers;
	
	/** The topic consumers. */
	private final Map<String, MessageConsumer> topicConsumers;
	
	/** The topic messages dispatchers. */
	private final Map<String, PubSubMessagesDispatcher> topicMessagesDispatchers;

	// //////////////////////////////
	//
	// Constructors / Close
	//
	// //////////////////////////////

	/**
	 * Instantiates a new pub sub bus.
	 *
	 * @param url the url
	 */
	public PubSubBus(String url) {
		this(url, null);
	}
	
	/**
	 * Instantiates a new pub sub bus.
	 *
	 * @param url the url
	 * @param clientConnectionProperties the client connection properties
	 */
	public PubSubBus(String url, Properties clientConnectionProperties) {
		super(url, clientConnectionProperties);

		this.topics = new HashMap<>();
		this.topicProducers = new HashMap<>();
		this.topicConsumers = new HashMap<>();
		this.topicMessagesDispatchers = new HashMap<>();
	}

	/**
	 * Close.
	 *
	 * @throws Exception the exception
	 */
	@Override
	public void close() throws Exception {
		if (topics != null) {
			topics.clear();
		}
		if (topicProducers != null) {
			topicProducers.clear();
		}
		if (topicConsumers != null) {
			topicConsumers.clear();
		}

		if (topicMessagesDispatchers != null) {
			for (PubSubMessagesDispatcher messagesDispatcher : topicMessagesDispatchers.values()) {
				messagesDispatcher.close();
			}
			topicMessagesDispatchers.clear();
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
	 * @param topicsNames the topics names
	 */
	public void addListener(PubSubMessageListener listener, String... topicsNames) {

		if(!isStarted()) {
			Logger.getLogger(getClass().getName()).log(Level.FINE, "Pub/Sub bus is not started. Register listener will fail");
		}
		
		for (String topicName : topicsNames) {
			try {
				PubSubMessagesDispatcher messagesDispatcher = getMessagesDispatcher(topicName);
				messagesDispatcher.addListener(listener);
			} catch (JMSException e) {
				Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage());
				Logger.getLogger(getClass().getName()).log(Level.FINE, e.getMessage(), e);
			}
		}
	}

	/**
	 * Removes the listener.
	 *
	 * @param listener the listener
	 * @param topicsNames the topics names
	 */
	public void removeListener(PubSubMessageListener listener, String... topicsNames) {

		if(!isStarted()) {
			Logger.getLogger(getClass().getName()).log(Level.FINE, "Pub/Sub bus is not started. Unregister listener will fail");
		}
		
		for (String topicName : topicsNames) {
			try {
				PubSubMessagesDispatcher messagesDispatcher = getMessagesDispatcher(topicName);
				messagesDispatcher.removeListener(listener);
			} catch (JMSException e) {
				Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage());
				Logger.getLogger(getClass().getName()).log(Level.FINE, e.getMessage(), e);
			}
		}
	}

	/**
	 * Send object message.
	 *
	 * @param topicName the topic name
	 * @param payload the payload
	 */
	public void sendObjectMessage(String topicName, Serializable payload) {
		
		if(!isStarted()) {
			Logger.getLogger(getClass().getName()).log(Level.FINE, "Pub/Sub bus is not started. No message will be sent");
		}
		
		try {
			Session session = getSession();
			Message msg = session.createObjectMessage(payload);

			Logger.getLogger(getClass().getName()).log(Level.FINE, "Sending message: '" + payload + "' on topic: " + topicName);

			MessageProducer producer = getProducer(topicName);
			producer.send(msg);
		} catch (JMSException e) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage());
			Logger.getLogger(getClass().getName()).log(Level.FINE, e.getMessage(), e);
		}
	}

	/**
	 * Send text message.
	 *
	 * @param topicName the topic name
	 * @param payload the payload
	 */
	public void sendTextMessage(String topicName, String payload) {
		
		if(!isStarted()) {
			Logger.getLogger(getClass().getName()).log(Level.FINE, "Pub/Sub bus is not started. No message will be sent");
		}
		
		try {
			Session session = getSession();
			Message msg = session.createTextMessage(payload);

			Logger.getLogger(getClass().getName()).log(Level.FINE, "Sending message: '" + payload + "' on topic: " + topicName);

			MessageProducer producer = getProducer(topicName);
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
	 * Gets the consumer.
	 *
	 * @param topicName the topic name
	 * @return the consumer
	 * @throws JMSException the JMS exception
	 */
	private MessageConsumer getConsumer(String topicName) throws JMSException {
		MessageConsumer consumer = topicConsumers.get(topicName);
		if (consumer == null) {
			Topic topic = getTopic(topicName);
			Session session = getSession();
			consumer = session.createConsumer(topic);

			topicConsumers.put(topicName, consumer);
		}
		return consumer;
	}

	/**
	 * Gets the producer.
	 *
	 * @param topicName the topic name
	 * @return the producer
	 * @throws JMSException the JMS exception
	 */
	private MessageProducer getProducer(String topicName) throws JMSException {
		MessageProducer producer = topicProducers.get(topicName);
		if (producer == null) {
			Topic topic = getTopic(topicName);
			Session session = getSession();
			producer = session.createProducer(topic);
			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

			topicProducers.put(topicName, producer);
		}
		return producer;
	}

	/**
	 * Gets the topic.
	 *
	 * @param topicName the topic name
	 * @return the topic
	 */
	private Topic getTopic(String topicName) {
		Topic topic = topics.get(topicName);
		if (topic == null) {
			try {
				Session session = getSession();
				topic = session.createTopic(topicName);

				topics.put(topicName, topic);
			} catch (JMSException e) {
				Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage());
				Logger.getLogger(getClass().getName()).log(Level.FINE, e.getMessage(), e);
			}
		}
		return topic;
	}

	/**
	 * Gets the messages dispatcher.
	 *
	 * @param topicName the topic name
	 * @return the messages dispatcher
	 * @throws JMSException the JMS exception
	 */
	private PubSubMessagesDispatcher getMessagesDispatcher(String topicName) throws JMSException {
		PubSubMessagesDispatcher messagesDispatcher = topicMessagesDispatchers.get(topicName);
		if (messagesDispatcher == null) {
			messagesDispatcher = new PubSubMessagesDispatcher(topicName);
			topicMessagesDispatchers.put(topicName, messagesDispatcher);

			MessageConsumer consumer = getConsumer(topicName);
			consumer.setMessageListener(messagesDispatcher);
		}
		return messagesDispatcher;
	}

}
