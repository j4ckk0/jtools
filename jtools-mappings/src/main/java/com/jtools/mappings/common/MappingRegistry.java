/**
 * 
 */
package com.jtools.mappings.common;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.jtools.mappings.block.BlockMapping;
import com.jtools.mappings.simple.SimpleMapping;

/**
 * @author j4ckk0
 *
 */
public class MappingRegistry {

	private static MappingRegistry instance;

	private final Map<UUID, SimpleMapping<?>> simpleMappings;
	private final Map<UUID, BlockMapping<?>> blockMappings;

	private MappingRegistry() {
		this.simpleMappings = new HashMap<>();
		this.blockMappings = new HashMap<>();
	}

	public static MappingRegistry instance() {
		if (instance == null) {
			instance = new MappingRegistry();
		}
		return instance;
	}

	public void registerSimpleMapping(SimpleMapping<?> simpleMapping) {
		simpleMappings.put(simpleMapping.getId(), simpleMapping);
	}

	public void unregisterSimpleMapping(SimpleMapping<?> simpleMapping) {
		simpleMappings.remove(simpleMapping.getId());
	}

	public SimpleMapping<?> getSimpleMapping(UUID simpleMappingId) {
		return simpleMappings.get(simpleMappingId);
	}

	public void registerBlockMapping(BlockMapping<?> blockMapping) {
		blockMappings.put(blockMapping.getId(), blockMapping);
	}

	public void unregisterBlockMapping(BlockMapping<?> blockMapping) {
		blockMappings.remove(blockMapping.getId());
	}

	public BlockMapping<?> getBlockMapping(UUID blockMappingId) {
		return blockMappings.get(blockMappingId);
	}
}
