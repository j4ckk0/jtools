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

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jtools.utils.messages.pubsub.DefaultPubSubBus;
import org.jtools.utils.network.NetworkUtils;
public class DefaultBusConfig {

	private static final int FIRST_PORT = 61616;
	private static final int LAST_PORT = 61716;

	private static final String BASE_URL = "tcp://localhost:";

	public static String getUrl() {
		try {
			int freePort = NetworkUtils.getFreePort(FIRST_PORT, LAST_PORT);
			return BASE_URL + freePort;
		} catch (IOException e) {
			Logger.getLogger(DefaultPubSubBus.class.getName()).log(Level.SEVERE, e.getMessage());
			Logger.getLogger(DefaultPubSubBus.class.getName()).log(Level.FINE, e.getMessage(), e);
		}

		return null;
	}

}
