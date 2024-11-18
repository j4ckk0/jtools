package org.jtools.data.gui.desktop;

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
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
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

import org.jtools.data.provider.DataProviderRegistry;
import org.jtools.data.provider.IDataProvider;
import org.jtools.gui.list.cellRenderers.DefaultClassListCellRenderer;

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
		super("Example classes provider");

		this.dataClasses = dataClasses;

		setIconifiable(true);
		setClosable(true);
		setResizable(false);

		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout(6, 6));

		dataClassesComboBox = new JComboBox<Class<?>>(new DefaultComboBoxModel<>(dataClasses));
		contentPane.add(dataClassesComboBox, BorderLayout.CENTER);

		Dimension preferredSize = dataClassesComboBox.getPreferredSize();
		dataClassesComboBox.setPreferredSize(new Dimension(300, preferredSize.height));

		dataClassesComboBox.setRenderer(new DefaultClassListCellRenderer());

		pack();

		addComponentListener(new ComponentAdapter() {
			
			@Override
			public void componentShown(ComponentEvent e) {
				DataProviderRegistry.instance().register(DefaultDataProvider.this);
			}
		});
		
		addInternalFrameListener(new InternalFrameAdapter() {

			@Override
			public void internalFrameClosed(InternalFrameEvent e) {
				DataProviderRegistry.instance().unregister(DefaultDataProvider.this);
			}
		});

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
