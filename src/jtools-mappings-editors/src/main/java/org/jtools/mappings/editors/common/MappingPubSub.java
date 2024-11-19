package org.jtools.mappings.editors.common;

/*-
 * #%L
 * Java Tools - Mappings Editors
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

import java.io.Serializable;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jtools.data.provider.DataProviderPubSub;

import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.ObjectMessage;
import jakarta.jms.TextMessage;
public interface MappingPubSub {

	public static final String MAPPING_ADDED = "MAPPING_ADDED";

	public static final String MAPPING_REMOVED = "MAPPING_REMOVED";

	public static final String MAPPING_CHANGED = "MAPPING_CHANGED";

	public static ObjectMessage castMessage(Message message) throws ClassCastException {
		if (!(message instanceof ObjectMessage)) {
			String msg = "Pub/Sub message received. Unexpected message type. Expected: " + TextMessage.class + ". Got: "
					+ message.getClass();
			Logger.getLogger(DataProviderPubSub.class.getName()).log(Level.WARNING, msg);
			throw new ClassCastException(msg);
		}

		return (ObjectMessage) message;
	}
	public static UUID readMessage(Message message) throws JMSException, ClassCastException {
		ObjectMessage objectMessage = castMessage(message);

		Serializable object = objectMessage.getObject();
		
		if (!(object instanceof UUID)) {
			String msg = "Pub/Sub message received. Unexpected message content. Expected: " + UUID.class + ". Got: "
					+ object.getClass();
			Logger.getLogger(DataProviderPubSub.class.getName()).log(Level.WARNING, msg);
			throw new ClassCastException(msg);
		}

		return (UUID)object;
	}

}
