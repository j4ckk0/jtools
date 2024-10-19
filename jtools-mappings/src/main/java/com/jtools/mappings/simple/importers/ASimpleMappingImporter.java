/**
 * 
 */
package com.jtools.mappings.simple.importers;

import java.io.IOException;
import java.util.List;

import com.jtools.mappings.simple.SimpleMappingRow;

/**
 * @author j4ckk0
 *
 */
public abstract class ASimpleMappingImporter {

	public abstract <T> List<T> importData(Class<T> objectClass, List<SimpleMappingRow> mappings) throws IOException;

}
