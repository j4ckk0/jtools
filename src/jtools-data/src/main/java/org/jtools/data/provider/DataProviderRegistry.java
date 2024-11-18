/**
 * 
 */
package org.jtools.data.provider;

/*-
 * #%L
 * Java Tools - Data
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

import java.util.HashMap;
import java.util.Map;

import org.jtools.utils.messages.pubsub.DefaultPubSubBus;

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
