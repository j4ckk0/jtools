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

import org.jtools.utils.messages.DefaultBusConfig;
// TODO: Auto-generated Javadoc

/**
 * The Class DefaultPubSubBus.
 */
public class DefaultPubSubBus extends PubSubBus {

	/** The instance. */
	private static DefaultPubSubBus instance;

	/**
	 * Instantiates a new default pub sub bus.
	 */
	private DefaultPubSubBus() {
		super(DefaultBusConfig.getUrl());
	}

	/**
	 * Instance.
	 *
	 * @return the default pub sub bus
	 */
	public static DefaultPubSubBus instance() {
		if (instance == null) {
			instance = new DefaultPubSubBus();
		}
		return instance;
	}

}
