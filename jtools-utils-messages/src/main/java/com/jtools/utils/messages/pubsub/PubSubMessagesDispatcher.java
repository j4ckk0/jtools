/**
 * 
 */
package com.jtools.utils.messages.pubsub;

import com.jtools.utils.messages.AMessagesDispatcher;

import jakarta.jms.Message;

/**
 * @author j4ckk0
 *
 */
public class PubSubMessagesDispatcher extends AMessagesDispatcher<PubSubMessageListener> {

	private String topicName;

	public PubSubMessagesDispatcher(String topicName) {
		this.topicName = topicName;
	}

	@Override
	public void onMessage(Message message) {
		for (PubSubMessageListener listener : listeners) {
			listener.onMessage(topicName, message);
		}
	}
}
