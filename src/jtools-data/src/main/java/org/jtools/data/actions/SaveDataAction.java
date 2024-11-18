/**
 * 
 */
package org.jtools.data.actions;

/*-
 * #%L
 * Java Tools - Data
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

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.AbstractAction;
import javax.swing.Icon;

import org.jtools.data.io.DataFileManager;
import org.jtools.data.provider.IDataProvider;

/**
 * @author j4ckk0
 *
 */
public class SaveDataAction extends AbstractAction {

	private static final long serialVersionUID = -1709026773373353372L;

	private transient IDataProvider dataProvider;

	public SaveDataAction(String name, Icon icon) {
		super(name, icon);
	}

	public SaveDataAction(String name) {
		super(name);
	}

	public void setDataProvider(IDataProvider dataProvider) {
		this.dataProvider = dataProvider;
	}

	@Override
	public void actionPerformed(ActionEvent event) {

		if (dataProvider == null) {
			throw new IllegalStateException("Data provider not been set");
		}

		try {
			List<?> dataList = dataProvider.getDataList();
			DataFileManager.instance().save(dataList);
		} catch (IOException e) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage());
			Logger.getLogger(getClass().getName()).log(Level.FINE, e.getMessage(), e);
		}
	}
}
