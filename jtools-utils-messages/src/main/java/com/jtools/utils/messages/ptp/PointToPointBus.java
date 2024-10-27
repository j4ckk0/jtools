/**
 * 
 */
package com.jtools.utils.messages.ptp;

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
		try {
			PointToPointMessageDispatcher messagesDispatcher = getMessagesDispatcher();
			messagesDispatcher.addListener(listener);
		} catch (JMSException e) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage());
			Logger.getLogger(getClass().getName()).log(Level.FINE, e.getMessage(), e);
		}
	}

	public void removeListener(MessageListener listener) {
		try {
			PointToPointMessageDispatcher messagesDispatcher = getMessagesDispatcher();
			messagesDispatcher.removeListener(listener);
		} catch (JMSException e) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage());
			Logger.getLogger(getClass().getName()).log(Level.FINE, e.getMessage(), e);
		}
	}

	public void sendObjectMessage(Serializable payload) {
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
