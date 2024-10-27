/**
 * 
 */
package com.jtools.data.gui.desktop;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JComboBox;
import javax.swing.JInternalFrame;

import com.jtools.data.provider.DataProviderPubSub;
import com.jtools.data.provider.DataProviderRegistry;
import com.jtools.data.provider.IDataProvider;
import com.jtools.utils.messages.pubsub.DefaultPubSubBus;
import com.jtools.utils.messages.pubsub.PubSubMessageListener;

import jakarta.jms.JMSException;
import jakarta.jms.Message;

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

		DefaultPubSubBus.instance().addListener(this, DataProviderPubSub.DATA_PROVIDER_ADDED, DataProviderPubSub.DATA_PROVIDER_REMOVED);

		pack();

	}

	@Override
	public void onMessage(String topicName, Message message) {
		try {
			
			String providerName = DataProviderPubSub.readMessage(message);
			
			IDataProvider dataProvider = DataProviderRegistry.instance().get(providerName);
			
			if(dataProvider == null) {
				Logger.getLogger(getClass().getName()).log(Level.WARNING, "Pub/Sub message received. Could not retieve IDataProvider matching with name: " + providerName);
				return;
			}

			if(topicName.equals(DataProviderPubSub.DATA_PROVIDER_ADDED)) {
				dataProvidersComboBox.addItem(dataProvider);
			}

			if(topicName.equals(DataProviderPubSub.DATA_PROVIDER_REMOVED)) {
				dataProvidersComboBox.removeItem(dataProvider);
			}

		} catch (JMSException | ClassCastException e) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage());
			Logger.getLogger(getClass().getName()).log(Level.FINE, e.getMessage(), e);
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		Object selectedItem = e.getItem();

		if(selectedItem instanceof IDataProvider) {
			DefaultPubSubBus.instance().sendTextMessage(DataProviderPubSub.DATA_PROVIDER_CHANGED, ((IDataProvider)selectedItem).getProviderName());
		}

	}

}
