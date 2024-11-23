package org.jtools.mappings.editors.simple;

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

import javax.swing.JScrollPane;

import org.jtools.mappings.editors.common.MappingOptionsPanel;
import org.jtools.mappings.editors.common.MappingRegistry;
import org.jtools.mappings.simple.SimpleMapping;
import org.jtools.mappings.simple.io.SimpleMappingFileManager;
import org.jtools.utils.gui.border.MarginTitledBorder;
import org.jtools.utils.gui.editor.AEditor;
// TODO: Auto-generated Javadoc

/**
 * The Class SimpleMappingEditor.
 *
 * @param <E> the element type
 */
public class SimpleMappingEditor<E extends Object> extends AEditor {

	// ////////////////////////////
	//
	// 	Constants and fields
	//
	// ////////////////////////////

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 7699822287171134053L;

	/** The mapping. */
	private final SimpleMapping<E> mapping;

	/** The mapping editor table. */
	private SimpleMappingEditorTable mappingEditorTable;

	/** The options panel. */
	private final MappingOptionsPanel optionsPanel;

	// ////////////////////////////
	//
	// 	Constructors
	//
	/**
	 * Instantiates a new simple mapping editor.
	 *
	 * @param mapping the mapping
	 */
	// ////////////////////////////
		public SimpleMappingEditor(SimpleMapping<E> mapping) {
		this.mapping = mapping;
		this.mappingEditorTable = new SimpleMappingEditorTable(mapping);

		this.optionsPanel = new MappingOptionsPanel();

		buildPanel();
	}

	//////////////////////////////
	//
	// Public methods
	//
	//////////////////////////////

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
	 * Apply.
	 *
	 * @return the simple mapping
	 */
	@SuppressWarnings("unchecked")
	public SimpleMapping<E> apply() {
		if(mappingEditorTable == null) {
			throw new IllegalStateException("Editor not initialized. Please call init() method before");
		}

		return (SimpleMapping<E>) mappingEditorTable.apply();
	}

	/**
	 * Gets the object class.
	 *
	 * @return the object class
	 */
	public Class<E> getObjectClass() {
		return mapping.getObjectClass();
	}

	/**
	 * Save.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Override
	protected void save() throws IOException {
		SimpleMappingFileManager.instance().save(apply());
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
