/**
 * 
 */
package org.jtools.mappings.editors.actions.block;

/*-
 * #%L
 * Java Tools - Mappings Editors
 * %%
 * Copyright (C) 2024 j4ckk0
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

import java.awt.event.ActionEvent;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.Icon;
import javax.swing.JOptionPane;

import org.jtools.data.provider.IDataClassProvider;
import org.jtools.mappings.block.BlockMapping;
import org.jtools.mappings.common.MappingUtils;
import org.jtools.mappings.editors.block.BlockMappingEditor;
import org.jtools.utils.CommonUtils;
import org.jtools.utils.gui.editor.AEditorAction;

/**
 * @author j4ckk0
 *
 */
public class BlockMappingCreateAction extends AEditorAction {

	private static final long serialVersionUID = 5655082933456528045L;

	private transient IDataClassProvider dataClassProvider;

	public BlockMappingCreateAction(String name) {
		super(name);
	}

	public BlockMappingCreateAction(String name, Icon icon) {
		super(name, icon);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (dataClassProvider == null) {
			Logger.getLogger(getClass().getName()).log(Level.WARNING, "Data class provider not been set");
			JOptionPane.showMessageDialog(null, "No data class provider has been set", "No provider", JOptionPane.WARNING_MESSAGE);
			return;
		}

		List<Class<?>> possibleDataClasses = dataClassProvider.getPossibleDataClasses();
		Class<?> dataClass = dataClassProvider.getDataClass();

		try {
			BlockMapping<?> mapping = new BlockMapping<>(dataClass);

			BlockMappingEditor<?> mappingEditor = new BlockMappingEditor<>(mapping, MappingUtils.getPossibleColumns(), CommonUtils.classListToArray(possibleDataClasses));
			showEditor(mappingEditor);
		} catch (Exception ex) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage());
			Logger.getLogger(getClass().getName()).log(Level.FINE, ex.getMessage(), ex);
		}
	}

	public void setDataClassProvider(IDataClassProvider dataProvider) {
		this.dataClassProvider = dataProvider;
	}
}
