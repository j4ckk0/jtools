/**
 * 
 */
package com.jtools.utils.messages;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import jakarta.jms.ConnectionFactory;
import jakarta.jms.DeliveryMode;
import jakarta.jms.JMSException;
import jakarta.jms.Queue;
import jakarta.jms.Session;
import jakarta.jms.TextMessage;
import jakarta.jms.Topic;
import jakarta.jms.TopicConnection;
import jakarta.jms.TopicConnectionFactory;
import jakarta.jms.TopicPublisher;
import jakarta.jms.TopicSession;
import jakarta.jms.TopicSubscriber;

/**
 * @author j4ckk0
 *
 */
public class PubSub implements AutoCloseable {

	private static PubSub INSTANCE;

	private final InitialContext initialContext;

	private final ConnectionFactory connectionFactory;

	private final TopicConnectionFactory topicConnectionFactory;
	private final TopicConnection topicConnection;
	private final TopicSession topicSession;

	private final Queue ptpQueue;
	private final Queue replyQueue;

	private final Map<String, Topic> topics;
	private final Map<String, TopicPublisher> topicPublishers;
	private final Map<String, TopicSubscriber> topicSubscribers;

	private PubSub() {
		try {
			this.initialContext = new InitialContext();

			this.connectionFactory = (ConnectionFactory) initialContext.lookup("jms/__defaultConnectionFactory");

			this.ptpQueue = (Queue) initialContext.lookup("jms/PTPQueue");
			this.replyQueue = (Queue) initialContext.lookup("jms/ReplyQueue");

			this.topicConnectionFactory = (TopicConnectionFactory) initialContext.lookup("topic/connectionFactory");
			this.topicConnection = topicConnectionFactory.createTopicConnection();
			this.topicSession = topicConnection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);

		} catch (NamingException | JMSException e) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
			throw new RuntimeException(e);
		}

		this.topics = new HashMap<>();
		this.topicPublishers = new HashMap<>();
		this.topicSubscribers = new HashMap<>();
	}

	public static PubSub instance() {
		if (INSTANCE == null) {
			INSTANCE = new PubSub();
		}
		return INSTANCE;
	}

	public void publishMessage(String topicName, String messageStr) {
		try {
			TopicPublisher topicPublisher = retrievePublisher(topicName);

			// create the "Hello World" message
			TextMessage message = topicSession.createTextMessage();
			message.setText("Hello World");

			// publish the messages
			topicPublisher.publish(message);

			// print what we did
			System.out.println("Message published: " + message.getText());
		} catch (JMSException e) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
		}
	}

	private Topic retrieveTopic(String topicName) {
		Topic topic = topics.get(topicName);
		if (topic == null) {
			try {
				topic = (Topic) initialContext.lookup("jms/" + topicName);
				topics.put(topicName, topic);
			} catch (NamingException e) {
				Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
			}
		}
		return topic;
	}

	private TopicPublisher retrievePublisher(String topicName) {
		TopicPublisher publisher = topicPublishers.get(topicName);
		if(publisher == null) {
			try {
				Topic topic = retrieveTopic(topicName);

				publisher = topicSession.createPublisher(topic);
				publisher.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

				topicPublishers.put(topicName, publisher);
			} catch (JMSException e) {
				Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
			}
		}
		return publisher;
	}

	private TopicSubscriber retrieveSubscriber(String topicName) {
		TopicSubscriber subscriber = topicSubscribers.get(topicName);
		if(subscriber == null) {
			try {
				Topic topic = retrieveTopic(topicName);

				subscriber = topicSession.createSubscriber(topic);

				topicSubscribers.put(topicName, subscriber);
			} catch (JMSException e) {
				Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
			}
		}
		return subscriber;
	}

	@Override
	public void close() throws Exception {
		topicSession.close();
		topicConnection.close();
	}
}
