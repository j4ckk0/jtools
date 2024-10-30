/**
 * 
 */
package com.jtools.utils.messages.pubsub;

/*-
 * #%L
 * Java Tools - Utils - Messages
 * %%
 * Copyright (C) 2024 j4ckk0
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
