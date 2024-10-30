package com.jtools.mappings.block.exporters;

import java.io.IOException;
import java.util.List;

import com.jtools.mappings.block.BlockMapping;
import com.jtools.mappings.common.MappingException;

public interface IBlockMappingExporter {

	public <T> void exportData(List<T> data, BlockMapping<?> mappings) throws IOException, MappingException;

}
