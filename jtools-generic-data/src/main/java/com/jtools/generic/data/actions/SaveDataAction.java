/**
 * 
 */
package com.jtools.generic.data.actions;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.AbstractAction;
import javax.swing.Icon;

import com.jtools.generic.data.io.DataFileManager;
import com.jtools.generic.data.provider.IDataProvider;

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
