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
public abstract class AMessagesDispatcher<L extends MessageListener> implements MessageListener, AutoCloseable {

	protected final Set<L> listeners;

	protected AMessagesDispatcher() {
		this.listeners = new HashSet<>();
	}

	@Override
	public void close() throws Exception {
		if (listeners != null) {
			listeners.clear();
		}
	}

	public void addListener(L listener) {
		listeners.add(listener);
	}

	public void removeListener(L listener) {
		listeners.remove(listener);
	}

	public Set<L> getListeners() {
		return listeners;
	}

	@Override
	public void onMessage(Message message) {
		for (MessageListener listener : listeners) {
			listener.onMessage(message);
		}
	}

}
