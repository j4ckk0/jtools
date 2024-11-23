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

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.jms.BytesMessage;
import jakarta.jms.JMSException;
import jakarta.jms.MapMessage;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;
import jakarta.jms.ObjectMessage;
import jakarta.jms.StreamMessage;
import jakarta.jms.TextMessage;

// TODO: Auto-generated Javadoc
/**
 * The listener interface for receiving defaultMessage events.
 * The class that is interested in processing a defaultMessage
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addDefaultMessageListener</code> method. When
 * the defaultMessage event occurs, that object's appropriate
 * method is invoked.
 *
 * @see DefaultMessageEvent
 */
public class DefaultMessageListener implements MessageListener {
	
	/** The consumer name. */
	private String consumerName;

	/**
	 * Instantiates a new default message listener.
	 *
	 * @param consumerName the consumer name
	 */
	public DefaultMessageListener(String consumerName) {
		this.consumerName = consumerName;
	}

	/**
	 * On message.
	 *
	 * @param message the message
	 */
	@Override
	public void onMessage(Message message) {

		if (message instanceof TextMessage) {
			onTextMessage((TextMessage) message);
		}
		if (message instanceof ObjectMessage) {
			onObjectMessage((ObjectMessage) message);
		}
		if (message instanceof BytesMessage) {
			onBytesMessage((BytesMessage) message);
		}
		if (message instanceof MapMessage) {
			onMapMessage((MapMessage) message);
		}
		if (message instanceof StreamMessage) {
			onStreamMessage((StreamMessage) message);
		}
		if (message instanceof org.apache.activemq.Message) {
			onActiveMQMessage((org.apache.activemq.Message) message);
		}

	}

	/**
	 * On text message.
	 *
	 * @param message the message
	 */
	protected void onTextMessage(TextMessage message) {
		try {
			String text = message.getText();
			Logger.getLogger(getClass().getName()).log(Level.FINE, consumerName + " received a TextMessage: " + text);
		} catch (JMSException e) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage());
			Logger.getLogger(getClass().getName()).log(Level.FINE, e.getMessage(), e);
		}
	}

	/**
	 * On object message.
	 *
	 * @param message the message
	 */
	protected void onObjectMessage(ObjectMessage message) {
		try {
			Serializable object = message.getObject();
			Logger.getLogger(getClass().getName()).log(Level.FINE, consumerName + " received an ObjectMessage : " + "["
					+ object.getClass().getSimpleName() + "] " + object.toString());
		} catch (JMSException e) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage());
			Logger.getLogger(getClass().getName()).log(Level.FINE, e.getMessage(), e);
		}
	}

	/**
	 * On bytes message.
	 *
	 * @param message the message
	 */
	protected void onBytesMessage(BytesMessage message) {
		try {
			Logger.getLogger(getClass().getName()).log(Level.FINE, consumerName + " received a BytesMessage");
		} catch (Exception e) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage());
			Logger.getLogger(getClass().getName()).log(Level.FINE, e.getMessage(), e);
		}
	}

	/**
	 * On map message.
	 *
	 * @param message the message
	 */
	protected void onMapMessage(MapMessage message) {
		try {
			Logger.getLogger(getClass().getName()).log(Level.FINE, consumerName + " received a MapMessage");
		} catch (Exception e) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage());
			Logger.getLogger(getClass().getName()).log(Level.FINE, e.getMessage(), e);
		}
	}

	/**
	 * On stream message.
	 *
	 * @param message the message
	 */
	protected void onStreamMessage(StreamMessage message) {
		try {
			Logger.getLogger(getClass().getName()).log(Level.FINE, consumerName + " received a StreamMessage");
		} catch (Exception e) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage());
			Logger.getLogger(getClass().getName()).log(Level.FINE, e.getMessage(), e);
		}
	}

	/**
	 * On active MQ message.
	 *
	 * @param message the message
	 */
	protected void onActiveMQMessage(org.apache.activemq.Message message) {
		try {
			Logger.getLogger(getClass().getName()).log(Level.FINE, consumerName + " received an ActiveMQMessage");
		} catch (Exception e) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage());
			Logger.getLogger(getClass().getName()).log(Level.FINE, e.getMessage(), e);
		}
	}

}
