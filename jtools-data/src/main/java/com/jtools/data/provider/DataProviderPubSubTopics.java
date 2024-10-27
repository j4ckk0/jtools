/**
 * 
 */
package com.jtools.data.provider;

import jakarta.jms.Message;
import jakarta.jms.TextMessage;

/**
 * @author j4ckk0
 *
 */
public interface DataProviderPubSubTopics {
	
	public static final Class<? extends Message> MESSAGES_TYPE = TextMessage.class;
	
	public static final String CONTENT_TYPE_DESCRIPTION = "The name of the provider. @see com.jtools.data.provider.IDataProvider.getProviderName()";
	

	public static final String DATA_PROVIDER_ADDED = "DATA_PROVIDER_ADDED_PROPERTY";

	public static final String DATA_PROVIDER_REMOVED = "DATA_PROVIDER_REMOVED_PROPERTY";
	
	public static final String DATA_PROVIDER_CHANGED = "DATA_PROVIDER_CHANGED_PROPERTY";
}
