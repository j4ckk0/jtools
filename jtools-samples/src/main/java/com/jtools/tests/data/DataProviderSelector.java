/**
 * 
 */
package com.jtools.tests.data;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;

import com.jtools.generic.data.provider.DataProviderChangeSupport;
import com.jtools.generic.data.provider.IDataProvider;

/**
 * @author j4ckk0
 *
 */
public class DataProviderSelector extends JInternalFrame implements PropertyChangeListener, ItemListener {

	private static final long serialVersionUID = -6803902145432674502L;

	private final JComboBox<IDataProvider> dataProvidersComboBox;

	public DataProviderSelector() {
		super("Data provider selector");

		setIconifiable(true);
		setClosable(true);
		setResizable(false);

		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout(6, 6));

		this.dataProvidersComboBox = new JComboBox<>();
		contentPane.add(dataProvidersComboBox, BorderLayout.CENTER);

		dataProvidersComboBox.setRenderer(new DataProviderListCellRenderer());

		addPropertyChangeListener(this);
		
		dataProvidersComboBox.addItemListener(this);

		pack();

	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {

		if (evt.getPropertyName().equals(DataProviderChangeSupport.DATA_PROVIDER_ADDED_PROPERTY)) {
			Object newValue = evt.getNewValue();
			if (newValue instanceof IDataProvider) {
				dataProvidersComboBox.addItem((IDataProvider)newValue);
			}
		}

		if (evt.getPropertyName().equals(DataProviderChangeSupport.DATA_PROVIDER_REMOVED_PROPERTY)) {
			Object newValue = evt.getNewValue();
			if (newValue instanceof IDataProvider) {
				dataProvidersComboBox.removeItem((IDataProvider)newValue);
			}
		}
	}

	private class DataProviderListCellRenderer extends DefaultListCellRenderer {

		private static final long serialVersionUID = 5381004978356081557L;

		@Override
		public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
				boolean cellHasFocus) {
			Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			if (c instanceof JLabel && value instanceof IDataProvider) {
				((JLabel) c).setText(((IDataProvider) value).getProviderName());
			}
			return c;
		}

	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		Object selectedItem = e.getItem();
		
		if(selectedItem instanceof IDataProvider) {
			firePropertyChange(DataProviderChangeSupport.DATA_PROVIDER_CHANGED_PROPERTY, null, (IDataProvider)selectedItem);
		}
		
	}

}
