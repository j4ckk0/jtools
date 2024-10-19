/**
 * 
 */
package com.jtools.mappings.block.importers;

import java.io.IOException;
import java.util.List;

import com.jtools.mappings.block.BlockMapping;

/**
 * @author j4ckk0
 *
 */
public abstract class ABlockMappingImporter {

	public abstract <T> List<T> importData(Class<T> objectClass, BlockMapping<?> mapping) throws IOException;

}
