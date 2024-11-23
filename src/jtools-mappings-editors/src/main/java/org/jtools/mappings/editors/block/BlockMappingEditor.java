package org.jtools.mappings.editors.block;

/*-
 * #%L
 * Java Tools - Mappings Editors
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

import java.awt.BorderLayout;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.swing.JScrollPane;

import org.jtools.mappings.block.BlockMapping;
import org.jtools.mappings.block.BlockMappingRow;
import org.jtools.mappings.block.io.BlockMappingFileManager;
import org.jtools.mappings.editors.common.MappingOptionsPanel;
import org.jtools.mappings.editors.common.MappingRegistry;
import org.jtools.utils.CommonUtils;
import org.jtools.utils.gui.border.MarginTitledBorder;
import org.jtools.utils.gui.editor.AEditor;
// TODO: Auto-generated Javadoc

/**
 * The Class BlockMappingEditor.
 *
 * @param <E> the element type
 */
public class BlockMappingEditor<E extends Object> extends AEditor {

	//////////////////////////////
	//
	// Constants and fields
	//
	//////////////////////////////

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 5685099248589163338L;

	/** The id. */
	private final UUID id = UUID.randomUUID();

	/** The mapping. */
	private final BlockMapping<E> mapping;

	/** The possible classes. */
	private final Class<?>[] possibleClasses;

	/** The possible columns. */
	private final String[] possibleColumns;

	/** The mapping editor table. */
	private final BlockMappingEditorTable<E> mappingEditorTable;
	
	/** The options panel. */
	private final MappingOptionsPanel optionsPanel;

	//////////////////////////////
	//
	// Constructors
	//
	//////////////////////////////
	/**
	 * Instantiates a new block mapping editor.
	 *
	 * @param blockMapping the block mapping
	 * @param possibleColumns the possible columns
	 * @param possibleClasses the possible classes
	 */
	public BlockMappingEditor(BlockMapping<E> blockMapping, String[] possibleColumns, Class<?>... possibleClasses) {
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

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public UUID getId() {
		return id;
	}

	/**
	 * Gets the editor name.
	 *
	 * @return the editor name
	 */
	@Override
	public String getEditorName() {
		return mapping.getMappingName();
	}

	/**
	 * Gets the table.
	 *
	 * @return the table
	 */
	public BlockMappingEditorTable<?> getTable() {
		return mappingEditorTable;
	}

	/**
	 * Apply.
	 *
	 * @return the block mapping
	 */
	public BlockMapping<E> apply() {
		return mappingEditorTable.apply();
	}

	/**
	 * Gets the object class.
	 *
	 * @return the object class
	 */
	public Class<?> getObjectClass() {
		return mapping.getObjectClass();
	}

	/**
	 * Gets the block mapping.
	 *
	 * @return the block mapping
	 */
	public BlockMapping<E> getBlockMapping() {
		return mapping;
	}

	/**
	 * Gets the possible columns.
	 *
	 * @return the possible columns
	 */
	public String[] getPossibleColumns() {
		return possibleColumns;
	}

	/**
	 * Gets the possible classes.
	 *
	 * @return the possible classes
	 */
	public Class<?>[] getPossibleClasses() {
		return possibleClasses;
	}

	/**
	 * Save.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Override
	protected void save() throws IOException {
		BlockMappingFileManager.instance().save(apply());
	}

	/**
	 * On window opened.
	 */
	@Override
	protected void onWindowOpened() {
		MappingRegistry.instance().register(mapping);
	}

	/**
	 * On window closed.
	 */
	@Override
	protected void onWindowClosed() {
		MappingRegistry.instance().unregister(mapping);
	}

	//////////////////////////////
	//
	// Private methods
	//
	//////////////////////////////

	/**
	 * Gets the possible classes.
	 *
	 * @param blockMapping the block mapping
	 * @param additionalPossibleClasses the additional possible classes
	 * @return the possible classes
	 */
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

	/**
	 * Fill possible classes fom mapping.
	 *
	 * @param blockMapping the block mapping
	 * @param possibleClasses the possible classes
	 */
	protected void fillPossibleClassesFomMapping(BlockMapping<?> blockMapping, List<Class<?>> possibleClasses) {

		if(blockMapping != null) {
			possibleClasses.add(blockMapping.getObjectClass());

			for(BlockMappingRow mappingRow : blockMapping.getRows()) {
				BlockMapping<?> subBlockMapping = mappingRow.getSubBlockMapping();
				if(subBlockMapping != null) {
					fillPossibleClassesFomMapping(subBlockMapping, possibleClasses);
				}
			}
		}
	}

	/**
	 * Builds the panel.
	 */
	private void buildPanel() {
		setLayout(new BorderLayout(6, 6));

		JScrollPane tableScrollPane = new JScrollPane(mappingEditorTable);
		tableScrollPane.setBorder(new MarginTitledBorder("Mappings", 6, 2, 6, 2));
		add(tableScrollPane, BorderLayout.CENTER);
		
		add(optionsPanel, BorderLayout.SOUTH);
	}

}
