/**
 * 
 */
package com.jtools.utils.messages.pointtopoint;

import com.jtools.utils.messages.AMessagesDispatcher;

import jakarta.jms.MessageListener;

/**
 * @author j4ckk0
 *
 */
public class PointToPointMessageDispatcher extends AMessagesDispatcher<MessageListener> {

	public PointToPointMessageDispatcher() {
		super();
	}

}
