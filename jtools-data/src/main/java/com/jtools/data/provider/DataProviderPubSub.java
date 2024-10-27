/**
 * 
 */
package com.jtools.data.provider;

import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.TextMessage;

/**
 * @author j4ckk0
 *
 */
public interface DataProviderPubSub {

	public static final String DATA_PROVIDER_ADDED = "DATA_PROVIDER_ADDED_PROPERTY";

	public static final String DATA_PROVIDER_REMOVED = "DATA_PROVIDER_REMOVED_PROPERTY";

	public static final String DATA_PROVIDER_CHANGED = "DATA_PROVIDER_CHANGED_PROPERTY";

	public static TextMessage castMessage(Message message) throws ClassCastException {
		if (!(message instanceof TextMessage)) {
			String msg = "Pub/Sub message received. Unexpected message type. Expected: " + TextMessage.class + ". Got: "
					+ message.getClass();
			Logger.getLogger(DataProviderPubSub.class.getName()).log(Level.WARNING, msg);
			throw new ClassCastException(msg);
		}

		return (TextMessage) message;
	}

	/**
	 * Returns the name of the IDataProvider.
	 * 
	 * {@code com.jtools.data.provider.IDataProvider.getProviderName()}
	 * 
	 * @param message
	 * @return
	 * @throws JMSException
	 */
	public static String readMessage(Message message) throws JMSException, ClassCastException {
		TextMessage textMessage = castMessage(message);

		return textMessage.getText();
	}
}
