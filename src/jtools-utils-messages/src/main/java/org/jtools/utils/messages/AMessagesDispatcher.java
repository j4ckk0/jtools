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

import java.util.HashSet;
import java.util.Set;

import jakarta.jms.Message;
import jakarta.jms.MessageListener;
// TODO: Auto-generated Javadoc

/**
 * The Class AMessagesDispatcher.
 *
 * @param <L> the generic type
 */
public abstract class AMessagesDispatcher<L extends MessageListener> implements MessageListener, AutoCloseable {

	/** The listeners. */
	protected final Set<L> listeners;

	/**
	 * Instantiates a new a messages dispatcher.
	 */
	protected AMessagesDispatcher() {
		this.listeners = new HashSet<>();
	}

	/**
	 * Close.
	 *
	 * @throws Exception the exception
	 */
	@Override
	public void close() throws Exception {
		if (listeners != null) {
			listeners.clear();
		}
	}

	/**
	 * Adds the listener.
	 *
	 * @param listener the listener
	 */
	public void addListener(L listener) {
		listeners.add(listener);
	}

	/**
	 * Removes the listener.
	 *
	 * @param listener the listener
	 */
	public void removeListener(L listener) {
		listeners.remove(listener);
	}

	/**
	 * Gets the listeners.
	 *
	 * @return the listeners
	 */
	public Set<L> getListeners() {
		return listeners;
	}

	/**
	 * On message.
	 *
	 * @param message the message
	 */
	@Override
	public void onMessage(Message message) {
		for (MessageListener listener : listeners) {
			listener.onMessage(message);
		}
	}

}
