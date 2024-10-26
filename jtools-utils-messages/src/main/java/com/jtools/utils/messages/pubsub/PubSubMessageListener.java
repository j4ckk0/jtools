/**
 * 
 */
package com.jtools.utils.messages.pubsub;

import jakarta.jms.Message;
import jakarta.jms.MessageListener;

/**
 * @author j4ckk0
 *
 */
public interface PubSubMessageListener extends MessageListener {

	@Override
	public default void onMessage(Message message) {
		onMessage(null, message);
	}

	public void onMessage(String topicName, Message message);

}
