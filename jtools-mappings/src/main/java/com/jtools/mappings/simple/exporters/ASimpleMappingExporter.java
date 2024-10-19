package com.jtools.mappings.simple.exporters;

import java.io.IOException;
import java.util.List;

import com.jtools.mappings.common.MappingException;
import com.jtools.mappings.simple.SimpleMappingRow;

public abstract class ASimpleMappingExporter {

	public abstract <T> void exportData(List<T> data, List<SimpleMappingRow> mappings) throws IOException, MappingException;

}
