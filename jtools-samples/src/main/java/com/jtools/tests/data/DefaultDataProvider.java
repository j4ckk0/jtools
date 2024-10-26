package com.jtools.tests.data;

import java.awt.BorderLayout;
import java.awt.Container;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import com.jtools.generic.data.provider.DataProviderPubSubTopics;
import com.jtools.generic.data.provider.DataProviderRegistry;
import com.jtools.generic.data.provider.IDataProvider;
import com.jtools.generic.gui.list.cellRenderers.DefaultClassListCellRenderer;
import com.jtools.utils.messages.pubsub.DefaultPubSubBus;

/**
 * Default data provider : only provides possible classes, but no data
 * 
 * @author j4ckk0
 *
 */
public class DefaultDataProvider extends JInternalFrame implements IDataProvider {

	private static final long serialVersionUID = -7986194491175022109L;

	private final Class<?>[] dataClasses;

	private final JComboBox<Class<?>> dataClassesComboBox;

	public DefaultDataProvider(Class<?>[] dataClasses) {
		super("Default data provider");

		this.dataClasses = dataClasses;

		setIconifiable(true);
		setClosable(true);
		setResizable(false);

		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout(6, 6));

		dataClassesComboBox = new JComboBox<Class<?>>(new DefaultComboBoxModel<>(dataClasses));
		contentPane.add(dataClassesComboBox, BorderLayout.CENTER);

		dataClassesComboBox.setRenderer(new DefaultClassListCellRenderer());

		pack();

		addInternalFrameListener(new InternalFrameAdapter() {

			@Override
			public void internalFrameOpened(InternalFrameEvent e) {
				DefaultPubSubBus.instance().sendTextMessage(DataProviderPubSubTopics.DATA_PROVIDER_ADDED, DefaultDataProvider.this.getProviderName());
			}

			@Override
			public void internalFrameClosed(InternalFrameEvent e) {
				DefaultPubSubBus.instance().sendTextMessage(DataProviderPubSubTopics.DATA_PROVIDER_REMOVED, DefaultDataProvider.this.getProviderName());
			}
		});
		
		// Register the DataEditor to the DataProvider registry
		DataProviderRegistry.instance().register(this);

	}

	@Override
	public List<Class<?>> getPossibleDataClasses() {
		return Arrays.asList(dataClasses);
	}

	@Override
	public Class<?> getDataClass() {
		return (Class<?>) dataClassesComboBox.getSelectedItem();
	}

	@Override
	public List<?> getDataList() {
		Logger.getLogger(getClass().getName()).log(Level.WARNING,
				"Default data provider. It can only provide empty data lists");
		return Collections.emptyList();
	}

	@Override
	public String getProviderName() {
		return getTitle();
	}

}