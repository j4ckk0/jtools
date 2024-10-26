/**
 * 
 */
package com.jtools.utils.messages.pubsub;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jtools.utils.messages.AMessagesBus;

import jakarta.jms.DeliveryMode;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.MessageConsumer;
import jakarta.jms.MessageProducer;
import jakarta.jms.Session;
import jakarta.jms.Topic;

/**
 * @author j4ckk0
 *
 */
public class PubSubBus extends AMessagesBus {

	// //////////////////////////////
	//
	// Attributes and constants
	//
	// //////////////////////////////

	private final Map<String, Topic> topics;
	private final Map<String, MessageProducer> topicProducers;
	private final Map<String, MessageConsumer> topicConsumers;
	private final Map<String, PubSubMessagesDispatcher> topicMessagesDispatchers;

	// //////////////////////////////
	//
	// Constructors / Close
	//
	// //////////////////////////////

	public PubSubBus(String url) {
		this(url, null);
	}

	/**
	 * See options on :
	 * https://activemq.apache.org/components/classic/documentation/failover-transport-reference
	 * 
	 * @param url
	 * @param clientConnectionProperties
	 */
	public PubSubBus(String url, Properties clientConnectionProperties) {
		super(url, clientConnectionProperties);

		this.topics = new HashMap<>();
		this.topicProducers = new HashMap<>();
		this.topicConsumers = new HashMap<>();
		this.topicMessagesDispatchers = new HashMap<>();
	}

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

	public void addListener(PubSubMessageListener listener, String... topicsNames) {
		for (String topicName : topicsNames) {
			try {
				PubSubMessagesDispatcher messagesDispatcher = getMessagesDispatcher(topicName);
				messagesDispatcher.addListener(listener);
			} catch (JMSException e) {
				Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
			}
		}
	}

	public void removeListener(PubSubMessageListener listener, String... topicsNames) {
		for (String topicName : topicsNames) {
			try {
				PubSubMessagesDispatcher messagesDispatcher = getMessagesDispatcher(topicName);
				messagesDispatcher.removeListener(listener);
			} catch (JMSException e) {
				Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
			}
		}
	}

	public void sendObjectMessage(String topicName, Serializable payload) {
		try {
			Session session = getSession();
			Message msg = session.createObjectMessage(payload);

			Logger.getLogger(getClass().getName()).log(Level.FINE, "Sending message '" + payload + "'");

			MessageProducer producer = getProducer(topicName);
			producer.send(msg);
		} catch (JMSException e) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
		}
	}

	public void sendTextMessage(String topicName, String payload) {
		try {
			Session session = getSession();
			Message msg = session.createTextMessage(payload);

			Logger.getLogger(getClass().getName()).log(Level.FINE, "Sending message '" + payload + "'");

			MessageProducer producer = getProducer(topicName);
			producer.send(msg);
		} catch (JMSException e) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
		}
	}

	// //////////////////////////////
	//
	// Private Methods
	//
	// //////////////////////////////

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

	private Topic getTopic(String topicName) {
		Topic topic = topics.get(topicName);
		if (topic == null) {
			try {
				Session session = getSession();
				topic = session.createTopic(topicName);

				topics.put(topicName, topic);
			} catch (JMSException e) {
				Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
			}
		}
		return topic;
	}

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
