package org.jtools.utils.messages;

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

import java.io.IOException;
import java.net.URI;
import java.util.Properties;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerFactory;
import org.apache.activemq.broker.BrokerService;

import jakarta.jms.Connection;
import jakarta.jms.JMSException;
import jakarta.jms.Session;
// TODO: Auto-generated Javadoc

/**
 * The Class AMessagesBus.
 */
public abstract class AMessagesBus implements AutoCloseable {

	// //////////////////////////////
	//
	// Attributes and constants
	//
	// //////////////////////////////

	/** The Constant BROKER. */
	private static final String BROKER = "broker:";
	
	/** The Constant FAILOVER. */
	private static final String FAILOVER = "failover:";

	/** The Constant DEFAULT_MAX_RECONNECT_ATTEMPT. */
	private static final String DEFAULT_MAX_RECONNECT_ATTEMPT = "3";

	/** The broker URL. */
	private final String brokerURL;
	
	/** The client URL. */
	private final String clientURL;

	/** The broker. */
	private BrokerService broker;

	/** The connection. */
	private Connection connection;
	
	/** The session. */
	private Session session;

	// //////////////////////////////
	//
	// Constructors / Close
	//
	// //////////////////////////////

	/**
	 * Instantiates a new a messages bus.
	 *
	 * @param url the url
	 */
	protected AMessagesBus(String url) {
		this(url, null);
	}
	
	/**
	 * Instantiates a new a messages bus.
	 *
	 * @param url the url
	 * @param clientConnectionProperties the client connection properties
	 */
	protected AMessagesBus(String url, Properties clientConnectionProperties) {
		if(url == null) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Cannot create messages bus: URL is null");
		}
		
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

	/**
	 * Close.
	 *
	 * @throws Exception the exception
	 */
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

	/**
	 * Start.
	 */
	public void start() {
		if (broker == null) {
			Logger.getLogger(getClass().getName()).log(Level.FINE, "Creating broker at " + brokerURL);
			try {
				this.broker = BrokerFactory.createBroker(new URI(brokerURL));
				broker.setPersistent(false);

				Logger.getLogger(getClass().getName()).log(Level.FINE, "Starting broker at " + brokerURL);
				broker.start();
				Logger.getLogger(getClass().getName()).log(Level.INFO, "Broker at " + brokerURL + " started");
			} catch (IOException e) {
				Logger.getLogger(getClass().getName()).log(Level.WARNING, e.getMessage());
			} catch (Exception e) {
				Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage());
				Logger.getLogger(getClass().getName()).log(Level.FINE, e.getMessage(), e);
			}
		}
	}
	
	/**
	 * Checks if is started.
	 *
	 * @return true, if is started
	 */
	public boolean isStarted() {
		return broker != null;
	}

	// //////////////////////////////
	//
	// Protected Methods
	//
	// //////////////////////////////

	/**
	 * Gets the session.
	 *
	 * @return the session
	 * @throws JMSException the JMS exception
	 */
	protected Session getSession() throws JMSException {
		if (session == null) {

			Logger.getLogger(getClass().getName()).log(Level.FINE, "Creating connecting factory to broker at " + brokerURL);
			ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(clientURL);
			connectionFactory.setTrustAllPackages(true);

			// Connection
			Logger.getLogger(getClass().getName()).log(Level.FINE, "Creating client connection to broker at " + brokerURL);
			this.connection = connectionFactory.createConnection();
			
			String clientID = UUID.randomUUID().toString();
			Logger.getLogger(getClass().getName()).log(Level.INFO, "Set client connection to broker at " + brokerURL + " to: " + clientID);
			connection.setClientID(clientID);

			Logger.getLogger(getClass().getName()).log(Level.FINE, "Starting client connection to broker at " + brokerURL);
			connection.start();
			Logger.getLogger(getClass().getName()).log(Level.INFO, "Client connection to broker at " + brokerURL + " started");

			// Session
			Logger.getLogger(getClass().getName()).log(Level.FINE, "Creating client session to broker at " + brokerURL);
			this.session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Logger.getLogger(getClass().getName()).log(Level.FINE, "Client session to broker at " + brokerURL + " created");
		}
		return session;
	}

}
