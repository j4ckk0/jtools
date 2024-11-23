package org.jtools.gui.list;

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
import java.awt.event.ActionEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.jtools.gui.form.ObjectForm;
import org.jtools.gui.list.cellRenderers.DefaultObjectListCellRenderer;
import org.jtools.gui.list.listModels.ObjectsListModel;
import org.jtools.utils.gui.GuiUtils;
import org.jtools.utils.objects.ObjectUtils;
// TODO: Auto-generated Javadoc

/**
 * The Class ObjectsListPanel.
 *
 * @param <E> the element type
 */
public class ObjectsListPanel<E extends Object> extends JPanel {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -844752210816149989L;

	/**
	 * The Enum Buttons.
	 */
	protected enum Buttons {
		
		/** The add. */
		ADD, 
 /** The edit. */
 EDIT, 
 /** The remove. */
 REMOVE
	}

	/** The objects list. */
	private final ObjectsList<E> objectsList;

	/** The buttons panel. */
	private final JPanel buttonsPanel;

	/** The buttons map. */
	private final Map<Buttons, JButton> buttonsMap;

	/**
	 * Instantiates a new objects list panel.
	 *
	 * @param objectClass the object class
	 */
	public ObjectsListPanel(Class<E> objectClass) {
		this(objectClass, null);
	}

	/**
	 * Instantiates a new objects list panel.
	 *
	 * @param objectClass the object class
	 * @param initialValues the initial values
	 */
	public ObjectsListPanel(Class<E> objectClass, List<E> initialValues) {
		super(new BorderLayout(6, 6));

		buttonsMap = new HashMap<>();

		objectsList = new ObjectsList<>(objectClass, initialValues);
		add(new JScrollPane(objectsList), BorderLayout.CENTER);

		buttonsPanel = new JPanel();
		BoxLayout buttonsPanelLayout = new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS);
		buttonsPanel.setLayout(buttonsPanelLayout);
		add(buttonsPanel, BorderLayout.EAST);

		buttonsPanel.add(getButton(Buttons.ADD));
		buttonsPanel.add(getButton(Buttons.EDIT));
		buttonsPanel.add(getButton(Buttons.REMOVE));

		objectsList.setCellRenderer(new DefaultObjectListCellRenderer());
	}

	/**
	 * Gets the button.
	 *
	 * @param buttonKey the button key
	 * @return the button
	 */
	private JButton getButton(Buttons buttonKey) {
		JButton button = buttonsMap.get(buttonKey);
		if (button == null) {
			button = createButton(buttonKey);
			buttonsMap.put(buttonKey, button);
		}

		return button;
	}

	/**
	 * Creates the button.
	 *
	 * @param buttonKey the button key
	 * @return the j button
	 */
	@SuppressWarnings("unchecked")
	protected JButton createButton(Buttons buttonKey) {
		Action action = getAction(buttonKey);
		JButton button = new JButton(action);

		if (action != null) {
			if (action instanceof ObjectsListAction) {
				button.setText(((ObjectsListAction<E>) action).getTxtIcon());
				button.setIcon(((ObjectsListAction<E>) action).getIcon());
				button.setToolTipText(((ObjectsListAction<E>) action).getName());
			} else {
				button.setText((String) action.getValue(Action.NAME));
				button.setIcon((Icon) action.getValue(Action.SMALL_ICON));
				button.setToolTipText((String) action.getValue(Action.SHORT_DESCRIPTION));
			}
		}

		return button;
	}

	/**
	 * Gets the action.
	 *
	 * @param buttonKey the button key
	 * @return the action
	 */
	protected Action getAction(Buttons buttonKey) {
		switch (buttonKey) {
		case ADD:
			return new DefaultAddAction<E>(objectsList);
		case REMOVE:
			return new DefaultRemoveAction<E>(objectsList);
		case EDIT:
			return new DefaultEditAction<E>(objectsList);
		default:
			throw new IllegalArgumentException("Unexpected value: " + buttonKey);
		}
	}
	
	/**
	 * The Class ObjectsListAction.
	 *
	 * @param <E> the element type
	 */
	public abstract static class ObjectsListAction<E extends Object> extends AbstractAction {

		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = 1555073527470591250L;

		/** The Constant TXT_ICON. */
		public static final String TXT_ICON = "TXT_ICON";

		/** The objects list. */
		protected final ObjectsList<E> objectsList;

		/**
		 * Instantiates a new objects list action.
		 *
		 * @param name the name
		 * @param icon the icon
		 * @param txtIcon the txt icon
		 * @param objectsList the objects list
		 */
		protected ObjectsListAction(String name, Icon icon, String txtIcon, ObjectsList<E> objectsList) {
			super(name, icon);
			putValue(TXT_ICON, txtIcon);
			this.objectsList = objectsList;
		}

		/**
		 * Instantiates a new objects list action.
		 *
		 * @param name the name
		 * @param objectsList the objects list
		 */
		protected ObjectsListAction(String name, ObjectsList<E> objectsList) {
			this(name, null, null, objectsList);
		}

		/**
		 * Gets the name.
		 *
		 * @return the name
		 */
		public String getName() {
			return (String) getValue(Action.NAME);
		}

		/**
		 * Gets the icon.
		 *
		 * @return the icon
		 */
		public Icon getIcon() {
			return (Icon) getValue(Action.SMALL_ICON);
		}

		/**
		 * Gets the txt icon.
		 *
		 * @return the txt icon
		 */
		public String getTxtIcon() {
			return (String) getValue(TXT_ICON);
		}
	}

	/**
	 * Gets the elements.
	 *
	 * @return the elements
	 */
	public List<E> getElements() {
		return objectsList.getDataList();
	}

	/**
	 * Show dialog.
	 *
	 * @return the int
	 */
	public int showDialog() {

		Object[] options = { "Apply", "Cancel" };

		int result = JOptionPane.showOptionDialog(null, this, "Edit list " + objectsList.getDataClass().toString(),
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, null);

		return result;

	}
	
	/**
	 * The Class DefaultAddAction.
	 *
	 * @param <E> the element type
	 */
	public static class DefaultAddAction<E extends Object> extends ObjectsListAction<E> {

		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = -471576623493101731L;

		/**
		 * Instantiates a new default add action.
		 *
		 * @param objectsList the objects list
		 */
		public DefaultAddAction(ObjectsList<E> objectsList) {
			super("Add", null, GuiUtils.PLUS_ICON_TXT, objectsList);
		}

		/**
		 * Action performed.
		 *
		 * @param e the e
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			// Build form
			try {
				ObjectForm form = new ObjectForm(objectsList.getDataClass());
				int result = form.showDialog();
				if (result == JOptionPane.OK_OPTION) {
					((ObjectsListModel<E>) objectsList.getModel())
							.addElement(objectsList.getDataClass().cast(form.getObject()));
				}
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException ex) {
				Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage());
			}
		}

	}
	
	/**
	 * The Class DefaultRemoveAction.
	 *
	 * @param <E> the element type
	 */
	public static class DefaultRemoveAction<E extends Object> extends ObjectsListAction<E> {

		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = -2468296432935193901L;

		/**
		 * Instantiates a new default remove action.
		 *
		 * @param objectsList the objects list
		 */
		public DefaultRemoveAction(ObjectsList<E> objectsList) {
			super("Remove", null, GuiUtils.MINUS_ICON_TXT, objectsList);
		}

		/**
		 * Action performed.
		 *
		 * @param e the e
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			List<E> selectedObjects = objectsList.getSelectedValuesList();
			if (selectedObjects != null) {
				for (E object : selectedObjects) {
					((ObjectsListModel<E>) objectsList.getModel()).removeElement(object);
				}
			}
		}

	}

	/**
	 * The Class DefaultEditAction.
	 *
	 * @author j4ckk0
	 * @param <E> the element type
	 */
	public static class DefaultEditAction<E extends Object> extends ObjectsListAction<E> {

		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = -4812290476514216214L;

		/**
		 * Instantiates a new default edit action.
		 *
		 * @param objectsList the objects list
		 */
		public DefaultEditAction(ObjectsList<E> objectsList) {
			super("Edit", null, GuiUtils.EDIT_ICON_TXT, objectsList);
		}

		/**
		 * Action performed.
		 *
		 * @param e the e
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			List<E> selectedObjects = objectsList.getSelectedValuesList();
			if (selectedObjects != null) {
				for (E object : selectedObjects) {
					ObjectForm form = new ObjectForm(object);
					int result = form.showDialog();
					if (result == JOptionPane.OK_OPTION) {
						ObjectUtils.apply(form.getObject(), object);
					}
				}
			}
		}

	}
}
