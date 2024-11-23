package org.jtools.utils.messages.pubsub;

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

import jakarta.jms.Message;
import jakarta.jms.MessageListener;
// TODO: Auto-generated Javadoc

/**
 * The listener interface for receiving pubSubMessage events.
 * The class that is interested in processing a pubSubMessage
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addPubSubMessageListener</code> method. When
 * the pubSubMessage event occurs, that object's appropriate
 * method is invoked.
 *
 * @see PubSubMessageEvent
 */
public interface PubSubMessageListener extends MessageListener {

	/**
	 * On message.
	 *
	 * @param message the message
	 */
	@Override
	public default void onMessage(Message message) {
		onMessage(null, message);
	}

	/**
	 * On message.
	 *
	 * @param topicName the topic name
	 * @param message the message
	 */
	public void onMessage(String topicName, Message message);

}
