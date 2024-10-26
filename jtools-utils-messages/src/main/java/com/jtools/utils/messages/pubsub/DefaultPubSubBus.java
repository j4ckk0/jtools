/**
 * 
 */
package com.jtools.utils.messages.pubsub;

/**
 * @author j4ckk0
 *
 */
public class DefaultPubSubBus extends PubSubBus {

	private static DefaultPubSubBus instance;

	private static final String URL = "tcp://localhost:61616";

	private DefaultPubSubBus() {
		super(URL);
	}

	public static DefaultPubSubBus instance() {
		if(instance == null) {
			instance = new DefaultPubSubBus();
		}
		return instance;
	}

}
