/**
 * 
 */
package com.jtools.data.provider;

import java.util.HashMap;
import java.util.Map;

import com.jtools.utils.messages.pubsub.DefaultPubSubBus;

/**
 * @author j4ckk0
 *
 */
public class DataProviderRegistry {

	private static DataProviderRegistry instance;

	private final Map<String, IDataProvider> providers;

	private DataProviderRegistry() {
		this.providers = new HashMap<>();
	}

	public static DataProviderRegistry instance() {
		if (instance == null) {
			instance = new DataProviderRegistry();
		}
		return instance;
	}

	public void register(IDataProvider provider) {
		providers.put(provider.getProviderName(), provider);
		
		DefaultPubSubBus.instance().sendTextMessage(DataProviderPubSub.DATA_PROVIDER_ADDED, provider.getProviderName());
	}

	public void unregister(IDataProvider provider) {
		providers.remove(provider.getProviderName());
		
		DefaultPubSubBus.instance().sendTextMessage(DataProviderPubSub.DATA_PROVIDER_REMOVED, provider.getProviderName());
	}

	public IDataProvider get(String providerName) {
		return providers.get(providerName);
	}
}
