package org.jtools.mappings.gui.desktop;

/*-
 * #%L
 * Java Tools - GUI - Desktop
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
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JComboBox;
import javax.swing.JInternalFrame;

import org.jtools.mappings.common.IMapping;
import org.jtools.mappings.editors.common.MappingListCellRenderer;
import org.jtools.mappings.editors.common.MappingPubSub;
import org.jtools.mappings.editors.common.MappingRegistry;
import org.jtools.utils.messages.pubsub.DefaultPubSubBus;
import org.jtools.utils.messages.pubsub.PubSubMessageListener;

import jakarta.jms.JMSException;
import jakarta.jms.Message;
// TODO: Auto-generated Javadoc

/**
 * The Class MappingSelector.
 */
public class MappingSelector extends JInternalFrame implements PubSubMessageListener, ItemListener {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -4168476618127593101L;

	/** The mappings combo box. */
	private final JComboBox<IMapping> mappingsComboBox;

	/** The mapping class. */
	private final Class<? extends IMapping> mappingClass;

	/**
	 * Instantiates a new mapping selector.
	 *
	 * @param mappingClass the mapping class
	 */
	public MappingSelector(Class<? extends IMapping> mappingClass) {
		super(mappingClass.getSimpleName() + " mappings selector");

		this.mappingClass = mappingClass;

		setIconifiable(true);
		setClosable(true);
		setResizable(true);

		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout(6, 6));

		this.mappingsComboBox = new JComboBox<>();
		contentPane.add(mappingsComboBox, BorderLayout.CENTER);

		Dimension preferredSize = mappingsComboBox.getPreferredSize();
		mappingsComboBox.setPreferredSize(new Dimension(700, preferredSize.height));

		mappingsComboBox.setRenderer(new MappingListCellRenderer());

		mappingsComboBox.addItemListener(this);

		DefaultPubSubBus.instance().addListener(this, MappingPubSub.MAPPING_ADDED, MappingPubSub.MAPPING_REMOVED);

		pack();

	}

	/**
	 * On message.
	 *
	 * @param topicName the topic name
	 * @param message the message
	 */
	@Override
	public void onMessage(String topicName, Message message) {
		try {

			UUID mappingId = MappingPubSub.readMessage(message);

			if (topicName.equals(MappingPubSub.MAPPING_ADDED)) {

				IMapping mapping = MappingRegistry.instance().get(mappingId);

				if ( mapping ==null ) {
					Logger.getLogger(getClass().getName()).log(Level.WARNING, "Pub/Sub message received. Could not retrieve a " + mappingClass.getSimpleName() + " matching with id: " + mappingId);
					return;
				}

				if ( !(mappingClass.isAssignableFrom(mapping.getClass())) ) {
					Logger.getLogger(getClass().getName()).log(Level.FINER, "Pub/Sub message received. Mapping class not matching -> ignore message");
					return;
				}

				mappingsComboBox.addItem(mapping);
			}

			if (topicName.equals(MappingPubSub.MAPPING_REMOVED)) {
				IMapping mapping = getMappingForId(mappingId);
				if(mapping != null) {
					mappingsComboBox.removeItem(mapping);
				}
			}

		} catch (JMSException | ClassCastException e) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage());
			Logger.getLogger(getClass().getName()).log(Level.FINE, e.getMessage(), e);
		}
	}

	/**
	 * Gets the mapping for id.
	 *
	 * @param mappingId the mapping id
	 * @return the mapping for id
	 */
	private IMapping getMappingForId(UUID mappingId) {
		for(int i = 0; i < mappingsComboBox.getItemCount(); i++) {
			IMapping mapping = mappingsComboBox.getItemAt(i);
			if(mapping.getId().equals(mappingId)) {
				return mapping;
			}
		}
		return null;
	}

	/**
	 * Item state changed.
	 *
	 * @param e the e
	 */
	@Override
	public void itemStateChanged(ItemEvent e) {
		Object selectedItem = e.getItem();

		if (selectedItem instanceof IMapping) {
			DefaultPubSubBus.instance().sendObjectMessage(MappingPubSub.MAPPING_CHANGED,
					((IMapping) selectedItem).getId());
		}

	}

}
