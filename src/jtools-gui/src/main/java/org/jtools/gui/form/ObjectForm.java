package org.jtools.gui.form;

/*-
 * #%L
 * Java Tools - GUI
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
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.security.InvalidParameterException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.jtools.gui.table.utils.TableUtils;
import org.jtools.utils.objects.ObjectInfoProvider;
import org.jtools.utils.objects.ObjectUtils;
import org.jtools.utils.objects.ObjectInfoProvider.ObjectInfo;
public class ObjectForm extends JPanel {

	private static final long serialVersionUID = -520587385214252977L;

	private final transient Object object;
	private final transient Object clone;

	private final transient ObjectInfo objectInfo;

	private final transient ObjectFormTableModel model;
	private final transient JTable table;

	public ObjectForm(Class<? extends Object> objectClass) throws InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		this(objectClass.getDeclaredConstructor().newInstance());
	}

	public ObjectForm(Object object) {
		if (object == null) {
			throw new InvalidParameterException(
					"Object shall not be null. If object is null, use constructor com.jtools.utils.objects.gui.form.ObjectForm.ObjectForm(Class<? extends Object>)");
		}

		this.object = object;
		this.objectInfo = ObjectInfoProvider.getObjectInfo(object.getClass());

		if (this.object instanceof Serializable) {
			this.clone = ObjectUtils.clone((Serializable) object);
		} else {
			this.clone = null;
		}

		model = new ObjectFormTableModel(this.clone != null ? this.clone : this.object, this.objectInfo);
		table = new JTable(model);

		setLayout(new BorderLayout());
		add(new JScrollPane(table), BorderLayout.CENTER);

		TableUtils.installDefaultTableCellRenderers(table);
		TableUtils.installCenteredLabelsCellRenderers(table);

		TableUtils.installDefaultTableCellEditors(table);
		TableUtils.installAutoStopEditingCellEditors(table);
	}

	public boolean isCancelable() {
		return (this.clone != null);
	}

	public Object getObject() {
		return this.object;
	}

	public int showDialog() {

		JScrollPane jScrollPane = new JScrollPane(this);

		if (isCancelable()) {
			Object[] options = { "Apply", "Cancel" };

			int result = JOptionPane.showOptionDialog(null, jScrollPane, "Edit object " + object.toString(),
					JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, null);

			if (result == JOptionPane.OK_OPTION) {
				apply();
			}

			return result;
		} else {
			Object[] options = { "Close" };

			return JOptionPane.showOptionDialog(null, jScrollPane, "Edit object " + object.toString(),
					JOptionPane.OK_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, null);
		}

	}
	private void apply() {
		if (this.clone == null) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE,
					"Clone is null. The form should not be cancelable");
			return;
		}

		// Set fields values
		ObjectUtils.apply(clone, object);
	}

}
