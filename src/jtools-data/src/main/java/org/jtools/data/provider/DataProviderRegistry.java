package org.jtools.data.provider;

/*-
 * #%L
 * Java Tools - Data
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

import java.util.HashMap;
import java.util.Map;

import org.jtools.utils.messages.pubsub.DefaultPubSubBus;
// TODO: Auto-generated Javadoc

/**
 * The Class DataProviderRegistry.
 */
public class DataProviderRegistry {

	/** The instance. */
	private static DataProviderRegistry instance;

	/** The providers. */
	private final Map<String, IDataProvider> providers;

	/**
	 * Instantiates a new data provider registry.
	 */
	private DataProviderRegistry() {
		this.providers = new HashMap<>();
	}

	/**
	 * Instance.
	 *
	 * @return the data provider registry
	 */
	public static DataProviderRegistry instance() {
		if (instance == null) {
			instance = new DataProviderRegistry();
		}
		return instance;
	}

	/**
	 * Register.
	 *
	 * @param provider the provider
	 */
	public void register(IDataProvider provider) {
		providers.put(provider.getProviderName(), provider);
		
		DefaultPubSubBus.instance().sendTextMessage(DataProviderPubSub.DATA_PROVIDER_ADDED, provider.getProviderName());
	}

	/**
	 * Unregister.
	 *
	 * @param provider the provider
	 */
	public void unregister(IDataProvider provider) {
		providers.remove(provider.getProviderName());
		
		DefaultPubSubBus.instance().sendTextMessage(DataProviderPubSub.DATA_PROVIDER_REMOVED, provider.getProviderName());
	}

	/**
	 * Gets the.
	 *
	 * @param providerName the provider name
	 * @return the i data provider
	 */
	public IDataProvider get(String providerName) {
		return providers.get(providerName);
	}
}
