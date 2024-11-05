/**
 * 
 */
package com.jtools.data.gui.desktop;

/*-
 * #%L
 * Java Tools - GUI - Desktop
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

			if(topicName.equals(DataProviderPubSub.DATA_PROVIDER_ADDED)) {

				IDataProvider dataProvider = DataProviderRegistry.instance().get(providerName);

				if(dataProvider == null) {
					Logger.getLogger(getClass().getName()).log(Level.WARNING, "Pub/Sub message received. Could not retieve IDataProvider matching with name: " + providerName);
					return;
				}

				dataProvidersComboBox.addItem(dataProvider);
			}

			if(topicName.equals(DataProviderPubSub.DATA_PROVIDER_REMOVED)) {
				IDataProvider dataProvider = getDataProviderForName(providerName);
				if(dataProvider != null) {
					dataProvidersComboBox.removeItem(dataProvider);
				}
			}

		} catch (JMSException | ClassCastException e) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage());
			Logger.getLogger(getClass().getName()).log(Level.FINE, e.getMessage(), e);
		}
	}

	private IDataProvider getDataProviderForName(String providerName) {
		for(int i = 0; i < dataProvidersComboBox.getItemCount(); i++) {
			IDataProvider dataProvider = dataProvidersComboBox.getItemAt(i);
			if(dataProvider.getProviderName().equals(providerName)) {
				return dataProvider;
			}
		}
		return null;
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		Object selectedItem = e.getItem();

		if(selectedItem instanceof IDataProvider) {
			DefaultPubSubBus.instance().sendTextMessage(DataProviderPubSub.DATA_PROVIDER_CHANGED, ((IDataProvider)selectedItem).getProviderName());
		}

	}

}
