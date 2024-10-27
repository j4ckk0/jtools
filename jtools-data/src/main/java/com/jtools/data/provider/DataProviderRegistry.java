/**
 * 
 */
package com.jtools.data.provider;

import java.util.HashMap;
import java.util.Map;

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
	}

	public void unregister(IDataProvider provider) {
		providers.remove(provider.getProviderName());
	}

	public IDataProvider get(String providerName) {
		return providers.get(providerName);
	}
}
