/**
 * 
 */
package com.jtools.utils.messages;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jtools.utils.messages.pubsub.DefaultPubSubBus;
import com.jtools.utils.network.NetworkUtils;

/**
 * @author j4ckk0
 *
 */
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
