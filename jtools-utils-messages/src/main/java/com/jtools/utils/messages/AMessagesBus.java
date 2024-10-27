/**
 * 
 */
package com.jtools.utils.messages;

import java.net.URI;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerFactory;
import org.apache.activemq.broker.BrokerService;

import jakarta.jms.Connection;
import jakarta.jms.JMSException;
import jakarta.jms.Session;

/**
 * Help:
 * https://examples.javacodegeeks.com/java-development/enterprise-java/jms/jms-messagelistener-example/
 * 
 * Examples: ACTIVEMQ_URL = "tcp://localhost:61616" BROKER_URL = "broker:(" +
 * ACTIVEMQ_URL + ")";
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

	private static final String BROKER = "broker:";
	private static final String FAILOVER = "failover:";

	private static final String DEFAULT_MAX_RECONNECT_ATTEMPT = "3";

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

	protected AMessagesBus(String url) {
		this(url, null);
	}

	/**
	 * See options on :
	 * https://activemq.apache.org/components/classic/documentation/failover-transport-reference
	 * 
	 * @param url
	 * @param clientConnectionProperties
	 */
	protected AMessagesBus(String url, Properties clientConnectionProperties) {
		this.brokerURL = BROKER + "(" + url + ")";

		if (clientConnectionProperties == null) {
			this.clientURL = FAILOVER + "(" + url + ")?maxReconnectAttempts=" + DEFAULT_MAX_RECONNECT_ATTEMPT;
		} else {
			String clientConnectionOptions = "";

			// Concatenate options
			// Trust the developer to use correct options
			// @see
			// https://activemq.apache.org/components/classic/documentation/failover-transport-reference
			for (Object key : clientConnectionProperties.keySet()) {
				if (key instanceof String) {
					clientConnectionOptions = "&" + ((String) key) + "="
							+ clientConnectionProperties.getProperty(((String) key));
				} else {
					Logger.getLogger(getClass().getName()).log(Level.WARNING, "Ignored property: " + key.toString());
				}
			}

			// Remove first "&"
			clientConnectionOptions = clientConnectionOptions.substring(1);

			if (clientConnectionOptions.length() > 0) {
				this.clientURL = FAILOVER + "(" + url + ")?" + clientConnectionOptions;
			} else {
				this.clientURL = FAILOVER + "(" + url + ")";
			}
		}
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

	public BrokerService startBroker() {
		if (broker == null) {
			Logger.getLogger(getClass().getName()).log(Level.FINE, "Creating broker at " + brokerURL);
			try {
				this.broker = BrokerFactory.createBroker(new URI(brokerURL));
				broker.setPersistent(false);

				Logger.getLogger(getClass().getName()).log(Level.FINE, "Starting broker at " + brokerURL);
				broker.start();
				Logger.getLogger(getClass().getName()).log(Level.INFO, "Broker at " + brokerURL + " started");
			} catch (Exception e) {
				Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage());
				Logger.getLogger(getClass().getName()).log(Level.FINE, e.getMessage(), e);
			}

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

			Logger.getLogger(getClass().getName()).log(Level.FINE, "Creating connecting factory to broker at " + brokerURL);
			ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(clientURL);
			connectionFactory.setTrustAllPackages(true);

			// Connection
			Logger.getLogger(getClass().getName()).log(Level.FINE, "Creating client connection to broker at " + brokerURL);
			this.connection = connectionFactory.createConnection();
			connection.setClientID("Client to " + brokerURL);

			Logger.getLogger(getClass().getName()).log(Level.FINE, "Starting client connection to broker at " + brokerURL);
			connection.start();
			Logger.getLogger(getClass().getName()).log(Level.INFO,
					"Client connection to broker at " + brokerURL + " started");

			// Session
			Logger.getLogger(getClass().getName()).log(Level.FINE, "Creating client session to broker at " + brokerURL);
			this.session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Logger.getLogger(getClass().getName()).log(Level.FINE,
					"Client session to broker at " + brokerURL + " created");
		}
		return session;
	}

}
