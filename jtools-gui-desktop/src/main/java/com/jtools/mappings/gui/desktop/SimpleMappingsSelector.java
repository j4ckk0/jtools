/**
 * 
 */
package com.jtools.mappings.gui.desktop;

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

import com.jtools.mappings.common.IMapping;
import com.jtools.mappings.editors.common.MappingListCellRenderer;
import com.jtools.mappings.editors.common.MappingPubSub;
import com.jtools.mappings.editors.common.MappingRegistry;
import com.jtools.mappings.simple.SimpleMapping;
import com.jtools.utils.messages.pubsub.DefaultPubSubBus;
import com.jtools.utils.messages.pubsub.PubSubMessageListener;

import jakarta.jms.JMSException;
import jakarta.jms.Message;

/**
 * @author j4ckk0
 *
 */
public class SimpleMappingsSelector extends JInternalFrame implements PubSubMessageListener, ItemListener {

	private static final long serialVersionUID = -3813295486024271091L;

	private final JComboBox<SimpleMapping<?>> mappingsComboBox;

	public SimpleMappingsSelector() {
		super("Simple mappings selector");

		setIconifiable(true);
		setClosable(true);
		setResizable(true);

		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout(6, 6));

		this.mappingsComboBox = new JComboBox<>();
		contentPane.add(mappingsComboBox, BorderLayout.CENTER);

		Dimension preferredSize = mappingsComboBox.getPreferredSize();
		mappingsComboBox.setPreferredSize(new Dimension(300, preferredSize.height));

		mappingsComboBox.setRenderer(new MappingListCellRenderer());

		mappingsComboBox.addItemListener(this);

		DefaultPubSubBus.instance().addListener(this, MappingPubSub.MAPPING_ADDED, MappingPubSub.MAPPING_REMOVED);

		pack();

	}

	@Override
	public void onMessage(String topicName, Message message) {
		try {

			UUID mappingId = MappingPubSub.readMessage(message);

			SimpleMapping<?> simpleMapping = MappingRegistry.instance().get(mappingId, SimpleMapping.class);

			if (simpleMapping == null) {
				Logger.getLogger(getClass().getName()).log(Level.FINE,
						"Pub/Sub message received. Could not retriev a SimpleMapping matching with id: " + mappingId);
				return;
			}

			if (topicName.equals(MappingPubSub.MAPPING_ADDED)) {
				mappingsComboBox.addItem(simpleMapping);
			}

			if (topicName.equals(MappingPubSub.MAPPING_REMOVED)) {
				mappingsComboBox.removeItem(simpleMapping);
			}

		} catch (JMSException | ClassCastException e) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage());
			Logger.getLogger(getClass().getName()).log(Level.FINE, e.getMessage(), e);
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		Object selectedItem = e.getItem();

		if (selectedItem instanceof IMapping) {
			DefaultPubSubBus.instance().sendObjectMessage(MappingPubSub.MAPPING_CHANGED,
					((IMapping) selectedItem).getId());
		}

	}

}
