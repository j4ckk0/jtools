/**
 * 
 */
package com.jtools.mappings.editors.common;

import java.io.Serializable;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jtools.data.provider.DataProviderPubSub;

import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.ObjectMessage;
import jakarta.jms.TextMessage;

/**
 * @author j4ckk0
 *
 */
public interface MappingPubSub {

	public static final String MAPPING_ADDED = "MAPPING_ADDED";

	public static final String MAPPING_REMOVED = "MAPPING_REMOVED";

	public static final String MAPPING_CHANGED = "MAPPING_CHANGED";

	public static ObjectMessage castMessage(Message message) throws ClassCastException {
		if (!(message instanceof ObjectMessage)) {
			String msg = "Pub/Sub message received. Unexpected message type. Expected: " + TextMessage.class + ". Got: "
					+ message.getClass();
			Logger.getLogger(DataProviderPubSub.class.getName()).log(Level.WARNING, msg);
			throw new ClassCastException(msg);
		}

		return (ObjectMessage) message;
	}

	/**
	 * Returns the UUID of the IMapping.
	 * 
	 * {@code com.jtools.mappings.common.IMapping.getId()}
	 * 
	 * @param message
	 * @return
	 * @throws JMSException
	 */
	public static UUID readMessage(Message message) throws JMSException, ClassCastException {
		ObjectMessage objectMessage = castMessage(message);

		Serializable object = objectMessage.getObject();
		
		if (!(object instanceof ObjectMessage)) {
			String msg = "Pub/Sub message received. Unexpected message content. Expected: " + UUID.class + ". Got: "
					+ object.getClass();
			Logger.getLogger(DataProviderPubSub.class.getName()).log(Level.WARNING, msg);
			throw new ClassCastException(msg);
		}

		return (UUID)object;
	}

}
