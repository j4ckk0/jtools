/**
 * 
 */
package com.jtools.generic.gui.list;

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

import com.jtools.generic.gui.form.ObjectForm;
import com.jtools.generic.gui.list.cellRenderers.DefaultObjectListCellRenderer;
import com.jtools.generic.gui.list.listModels.ObjectsListModel;
import com.jtools.utils.gui.GuiUtils;
import com.jtools.utils.objects.ObjectUtils;

/**
 * @author j4ckk0
 *
 */
public class ObjectsListPanel<E extends Object> extends JPanel {

	private static final long serialVersionUID = -844752210816149989L;

	protected enum Buttons {
		ADD, EDIT, REMOVE
	}

	private final ObjectsList<E> objectsList;

	private final JPanel buttonsPanel;

	private final Map<Buttons, JButton> buttonsMap;

	public ObjectsListPanel(Class<E> objectClass) {
		this(objectClass, null);
	}

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

	private JButton getButton(Buttons buttonKey) {
		JButton button = buttonsMap.get(buttonKey);
		if (button == null) {
			button = createButton(buttonKey);
			buttonsMap.put(buttonKey, button);
		}

		return button;
	}

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
	 * 
	 * @author j4ckk0
	 *
	 */
	public abstract static class ObjectsListAction<E extends Object> extends AbstractAction {

		private static final long serialVersionUID = 1555073527470591250L;

		public static final String TXT_ICON = "TXT_ICON";

		protected final ObjectsList<E> objectsList;

		protected ObjectsListAction(String name, Icon icon, String txtIcon, ObjectsList<E> objectsList) {
			super(name, icon);
			putValue(TXT_ICON, txtIcon);
			this.objectsList = objectsList;
		}

		protected ObjectsListAction(String name, ObjectsList<E> objectsList) {
			this(name, null, null, objectsList);
		}

		public String getName() {
			return (String) getValue(Action.NAME);
		}

		public Icon getIcon() {
			return (Icon) getValue(Action.SMALL_ICON);
		}

		public String getTxtIcon() {
			return (String) getValue(TXT_ICON);
		}
	}

	public List<E> getElements() {
		return objectsList.getDataList();
	}

	public int showDialog() {

		Object[] options = { "Apply", "Cancel" };

		int result = JOptionPane.showOptionDialog(null, this, "Edit list " + objectsList.getDataClass().toString(),
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, null);

		return result;

	}

	/**
	 * 
	 * @author j4ckk0
	 *
	 * @param <E>
	 */
	public static class DefaultAddAction<E extends Object> extends ObjectsListAction<E> {

		private static final long serialVersionUID = -471576623493101731L;

		public DefaultAddAction(ObjectsList<E> objectsList) {
			super("Add", null, GuiUtils.PLUS_ICON_TXT, objectsList);
		}

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
	 * 
	 * @author j4ckk0
	 *
	 * @param <E>
	 */
	public static class DefaultRemoveAction<E extends Object> extends ObjectsListAction<E> {

		private static final long serialVersionUID = -2468296432935193901L;

		public DefaultRemoveAction(ObjectsList<E> objectsList) {
			super("Remove", null, GuiUtils.MINUS_ICON_TXT, objectsList);
		}

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
	 * 
	 * @author j4ckk0
	 *
	 * @param <E>
	 */
	public static class DefaultEditAction<E extends Object> extends ObjectsListAction<E> {

		private static final long serialVersionUID = -4812290476514216214L;

		public DefaultEditAction(ObjectsList<E> objectsList) {
			super("Edit", null, GuiUtils.EDIT_ICON_TXT, objectsList);
		}

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
