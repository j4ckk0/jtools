/**
 * 
 */
package com.jtools.mappings.editors.block;

import java.awt.BorderLayout;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.swing.JScrollPane;

import com.jtools.mappings.block.BlockMapping;
import com.jtools.mappings.block.BlockMappingRow;
import com.jtools.mappings.block.io.BlockMappingFileManager;
import com.jtools.mappings.editors.common.MappingOptionsPanel;
import com.jtools.utils.CommonUtils;
import com.jtools.utils.gui.border.MarginTitledBorder;
import com.jtools.utils.gui.editor.AEditor;

/**
 * @author j4ckk0
 *
 */
public class BlockMappingEditor<E extends Object> extends AEditor {

	//////////////////////////////
	//
	// Constants and fields
	//
	//////////////////////////////

	private static final long serialVersionUID = 5685099248589163338L;

	private final UUID id = UUID.randomUUID();

	private final BlockMapping<E> mapping;

	private final Class<?>[] possibleClasses;

	private final String[] possibleColumns;

	private final BlockMappingEditorTable<E> mappingEditorTable;
	
	private final MappingOptionsPanel optionsPanel;

	//////////////////////////////
	//
	// Constructors
	//
	//////////////////////////////

	/**
	 * 
	 * @param objectClass
	 * @param blockMapping
	 * @param possibleColumns
	 * @param possibleClasses
	 * @throws IOException
	 */
	public BlockMappingEditor(BlockMapping<E> blockMapping, String[] possibleColumns, Class<?>... possibleClasses) throws IOException {
		this.mapping = blockMapping;

		this.possibleClasses = getPossibleClasses(blockMapping, possibleClasses);

		this.possibleColumns = possibleColumns;

		this.mappingEditorTable = new BlockMappingEditorTable<>(blockMapping, possibleColumns, this.possibleClasses);

		this.optionsPanel = new MappingOptionsPanel();

		buildPanel();
	}

	//////////////////////////////
	//
	// Public methods
	//
	//////////////////////////////

	public UUID getId() {
		return id;
	}

	@Override
	public String getEditorName() {
		return "Block mapping " + getObjectClass().getName();
	}

	public BlockMappingEditorTable<?> getTable() {
		return mappingEditorTable;
	}

	public List<BlockMappingRow> getRows() {
		return mappingEditorTable.getRows();
	}

	public Class<?> getObjectClass() {
		return mapping.getObjectClass();
	}

	public BlockMapping<E> getBlockMapping() {
		return mapping;
	}

	public String[] getPossibleColumns() {
		return possibleColumns;
	}

	public Class<?>[] getPossibleClasses() {
		return possibleClasses;
	}

	@Override
	protected void save() throws IOException {
		BlockMappingFileManager.instance().save(mapping);
	}

	//////////////////////////////
	//
	// Private methods
	//
	//////////////////////////////

	private Class<?>[] getPossibleClasses(BlockMapping<?> blockMapping, Class<?>... additionalPossibleClasses) {
		List<Class<?>> possibleClasses = new ArrayList<>();

		// From mappings
		if(blockMapping != null) {
			fillPossibleClassesFomMapping(blockMapping, possibleClasses);
		}

		// From additionnal possible classes
		if (additionalPossibleClasses != null) {
			for (Class<?> possibleClass : additionalPossibleClasses) {
				if (!possibleClasses.contains(possibleClass)) {
					possibleClasses.add(possibleClass);
				}
			}
		}
		return CommonUtils.classListToArray(possibleClasses);
	}

	protected void fillPossibleClassesFomMapping(BlockMapping<?> blockMapping, List<Class<?>> possibleClasses) {

		if(blockMapping != null) {
			possibleClasses.add(blockMapping.getObjectClass());

			for(BlockMappingRow mappingRow : blockMapping.getMappingRows()) {
				BlockMapping<?> subBlockMapping = mappingRow.getSubBlockMapping();
				if(subBlockMapping != null) {
					fillPossibleClassesFomMapping(subBlockMapping, possibleClasses);
				}
			}
		}
	}

	private void buildPanel() {
		setLayout(new BorderLayout(6, 6));

		JScrollPane tableScrollPane = new JScrollPane(mappingEditorTable);
		tableScrollPane.setBorder(new MarginTitledBorder("Mappings", 6, 2, 6, 2));
		add(tableScrollPane, BorderLayout.CENTER);
		
		add(optionsPanel, BorderLayout.SOUTH);
	}

}
