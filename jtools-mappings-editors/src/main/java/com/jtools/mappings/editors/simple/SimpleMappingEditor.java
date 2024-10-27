/**
 * 
 */
package com.jtools.mappings.editors.simple;

import java.awt.BorderLayout;
import java.io.IOException;
import java.util.List;

import javax.swing.JScrollPane;

import com.jtools.mappings.editors.common.MappingOptionsPanel;
import com.jtools.mappings.editors.common.MappingRegistry;
import com.jtools.mappings.simple.SimpleMapping;
import com.jtools.mappings.simple.SimpleMappingRow;
import com.jtools.mappings.simple.io.SimpleMappingFileManager;
import com.jtools.utils.gui.border.MarginTitledBorder;
import com.jtools.utils.gui.editor.AEditor;

/**
 * @author j4ckk0
 *
 */
public class SimpleMappingEditor<E extends Object> extends AEditor {

	// ////////////////////////////
	//
	// 	Constants and fields
	//
	// ////////////////////////////

	private static final long serialVersionUID = 7699822287171134053L;

	private final SimpleMapping<E> mapping;

	private SimpleMappingEditorTable mappingEditorTable;

	private final MappingOptionsPanel optionsPanel;

	// ////////////////////////////
	//
	// 	Constructors
	//
	// ////////////////////////////
	
	/**
	 * 
	 * @param objectClass
	 * @throws IOException 
	 */
	public SimpleMappingEditor(SimpleMapping<E> mapping) throws IOException {
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

	@Override
	public String getEditorName() {
		return mapping.getMappingName();
	}

	public List<SimpleMappingRow> getRows() {
		if(mappingEditorTable == null) {
			throw new IllegalStateException("Editor not initialized. Please call init() method before");
		}

		return mappingEditorTable.getRows();
	}

	public Class<E> getObjectClass() {
		return mapping.getObjectClass();
	}

	@Override
	protected void save() throws IOException {
		SimpleMappingFileManager.instance().save(mapping);
	}

	@Override
	protected void onWindowOpened() {
		MappingRegistry.instance().register(mapping);
	}

	@Override
	protected void onWindowClosed() {
		MappingRegistry.instance().unregister(mapping);
	}

	//////////////////////////////
	//
	// Private methods
	//
	//////////////////////////////

	private void buildPanel() {
		setLayout(new BorderLayout(6, 6));

		JScrollPane tableScrollPane = new JScrollPane(mappingEditorTable);
		tableScrollPane.setBorder(new MarginTitledBorder("Mappings", 6, 2, 6, 2));
		add(tableScrollPane, BorderLayout.CENTER);

		add(optionsPanel, BorderLayout.SOUTH);
	}

}
