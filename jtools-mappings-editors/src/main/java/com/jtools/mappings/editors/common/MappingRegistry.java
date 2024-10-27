/**
 * 
 */
package com.jtools.mappings.editors.common;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jtools.mappings.common.IMapping;
import com.jtools.utils.messages.pubsub.DefaultPubSubBus;

/**
 * @author j4ckk0
 *
 */
public class MappingRegistry {

	private static MappingRegistry instance;

	private final Map<UUID, IMapping> mappings;

	private MappingRegistry() {
		this.mappings = new HashMap<>();
	}

	public static MappingRegistry instance() {
		if (instance == null) {
			instance = new MappingRegistry();
		}
		return instance;
	}

	public void register(IMapping mapping) {
		mappings.put(mapping.getId(), mapping);
		
		DefaultPubSubBus.instance().sendObjectMessage(MappingPubSub.MAPPING_ADDED, mapping.getId());
	}

	public void unregister(IMapping mapping) {
		mappings.remove(mapping.getId());
		
		DefaultPubSubBus.instance().sendObjectMessage(MappingPubSub.MAPPING_REMOVED, mapping.getId());
	}

	public IMapping get(UUID mappingId) {
		return mappings.get(mappingId);
	}

	public <M extends IMapping> M get(UUID mappingId, Class<M> mappingClass) {
		IMapping mapping = get(mappingId);

		try {
			return mappingClass.cast(mapping);
		} catch (ClassCastException e) {
			Logger.getLogger(getClass().getName()).log(Level.WARNING, "Wrong mapping type found for mappingId: "
					+ mappingId + ". Found: " + mapping.getClass() + ". Requested: " + mappingClass);
			return null;
		}
	}
}
