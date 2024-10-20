/**
 * 
 */
package com.jtools.utils.messages;

import java.net.URI;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerFactory;
import org.apache.activemq.broker.BrokerService;

import jakarta.jms.Connection;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSException;
import jakarta.jms.Session;

/**
 * Help:
 * https://examples.javacodegeeks.com/java-development/enterprise-java/jms/jms-messagelistener-example/
 * 
 * Examples: 
 * ACTIVEMQ_URL = "tcp://localhost:61616"
 * BROKER_URL = "broker:(" + ACTIVEMQ_URL + ")";
 * 
 * @author j4ckk0
 *
 */
public abstract class AMessagesBus implements AutoCloseable {

	// //////////////////////////////
	//
	// Attributes and constants
	//
	// //////////////////////////////

	private final String brokerURL;
	private final String clientURL;

	private BrokerService broker;

	private Connection connection;
	private Session session;

	// //////////////////////////////
	//
	// Constructors / Close
	//
	// //////////////////////////////

	public AMessagesBus(String url) {
		this.brokerURL = "broker:(" + url + ")";
		this.clientURL = "failover:(" + url + ")?maxReconnectAttempts=-1";
	}

	@Override
	public void close() throws Exception {
		if (session != null) {
			session.close();
		}
		if (connection != null) {
			connection.close();
		}
		if (broker != null) {
			broker.stop();
		}
	}

	// //////////////////////////////
	//
	// Methods
	//
	// //////////////////////////////

	public BrokerService startBroker() throws Exception {
		if (broker == null) {
			this.broker = BrokerFactory.createBroker(new URI(brokerURL));
			broker.start();
		}
		return broker;
	}

	// //////////////////////////////
	//
	// Protected Methods
	//
	// //////////////////////////////

	protected Session getSession() throws JMSException {
		if (session == null) {

			ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(clientURL);

			// Connection
			this.connection = connectionFactory.createConnection();
			connection.setClientID("Client to " + brokerURL);
			connection.start();

			// Point To Point session
			this.session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		}
		return session;
	}

}
