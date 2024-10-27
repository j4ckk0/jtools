/**
 * 
 */
package com.jtools.tests.data;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;

import com.jtools.generic.data.provider.DataProviderPubSubTopics;
import com.jtools.generic.data.provider.DataProviderRegistry;
import com.jtools.generic.data.provider.IDataProvider;
import com.jtools.utils.messages.pubsub.DefaultPubSubBus;
import com.jtools.utils.messages.pubsub.PubSubMessageListener;

import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.TextMessage;

/**
 * @author j4ckk0
 *
 */
public class DataProviderSelector extends JInternalFrame implements PubSubMessageListener, ItemListener {

	private static final long serialVersionUID = -6803902145432674502L;

	private final JComboBox<IDataProvider> dataProvidersComboBox;

	public DataProviderSelector() {
		super("Data provider selector");

		setIconifiable(true);
		setClosable(true);
		setResizable(true);

		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout(6, 6));

		this.dataProvidersComboBox = new JComboBox<>();
		contentPane.add(dataProvidersComboBox, BorderLayout.CENTER);

		Dimension preferredSize = dataProvidersComboBox.getPreferredSize();
		dataProvidersComboBox.setPreferredSize(new Dimension(300, preferredSize.height));
		
		dataProvidersComboBox.setRenderer(new DataProviderListCellRenderer());

		dataProvidersComboBox.addItemListener(this);

		DefaultPubSubBus.instance().addListener(this, DataProviderPubSubTopics.DATA_PROVIDER_ADDED, DataProviderPubSubTopics.DATA_PROVIDER_REMOVED);

		pack();

	}

	@Override
	public void onMessage(String topicName, Message message) {
		try {
			if(!(message instanceof TextMessage)) {
				Logger.getLogger(getClass().getName()).log(Level.WARNING, "Pub/Sub message received. Unexpected content");
				return;
			}
			
			String providerName = ((TextMessage)message).getText();
			
			IDataProvider dataProvider = DataProviderRegistry.instance().get(providerName);
			
			if(dataProvider == null) {
				Logger.getLogger(getClass().getName()).log(Level.WARNING, "Pub/Sub message received. Unexpected content: not a IDataProvider matching with name: " + providerName);
				return;
			}

			if(topicName.equals(DataProviderPubSubTopics.DATA_PROVIDER_ADDED)) {
				dataProvidersComboBox.addItem(dataProvider);
			}

			if(topicName.equals(DataProviderPubSubTopics.DATA_PROVIDER_REMOVED)) {
				dataProvidersComboBox.removeItem(dataProvider);
			}

		} catch (JMSException e) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
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
			DefaultPubSubBus.instance().sendTextMessage(DataProviderPubSubTopics.DATA_PROVIDER_CHANGED, ((IDataProvider)selectedItem).getProviderName());
		}

	}

}
