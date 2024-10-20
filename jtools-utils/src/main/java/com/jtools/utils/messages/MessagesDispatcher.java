/**
 * 
 */
package com.jtools.utils.messages;

import java.util.HashSet;
import java.util.Set;

import jakarta.jms.Message;
import jakarta.jms.MessageListener;

/**
 * @author j4ckk0
 *
 */
public class MessagesDispatcher implements MessageListener, AutoCloseable {

	private final Set<MessageListener> listeners;

	public MessagesDispatcher() {
		this.listeners = new HashSet<>();
	}
	
	@Override
	public void close() throws Exception {
		if(listeners != null) {
			listeners.clear();
		}
	}

	public void addListener(MessageListener listener) {
		listeners.add(listener);
	}

	public void removeListener(MessageListener listener) {
		listeners.remove(listener);
	}

	public Set<MessageListener> getListeners() {
		return listeners;
	}

	@Override
	public void onMessage(Message message) {
		for (MessageListener listener : listeners) {
			listener.onMessage(message);
		}
	}

}
