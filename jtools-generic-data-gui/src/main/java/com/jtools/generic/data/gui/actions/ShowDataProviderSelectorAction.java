package com.jtools.generic.data.gui.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JDesktopPane;

import com.jtools.generic.data.gui.iframe.DataProviderSelector;

/**
 * 
 * @author j4ckk0
 *
 */
public class ShowDataProviderSelectorAction extends AbstractAction {

	private static final long serialVersionUID = 214611260483170265L;
	
	private final JDesktopPane desktopPane;
	private final DataProviderSelector dataProviderSelector;

	private boolean isVisible;

	public ShowDataProviderSelectorAction(String name, JDesktopPane desktopPane,
			DataProviderSelector dataProviderSelector) {
		super(name);
		this.desktopPane = desktopPane;
		this.dataProviderSelector = dataProviderSelector;

		isVisible = false;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (!isVisible) {
			desktopPane.add(dataProviderSelector);
			dataProviderSelector.setVisible(true);
			dataProviderSelector.moveToFront();
		} else {
			dataProviderSelector.setVisible(false);
			desktopPane.remove(dataProviderSelector);
		}
		isVisible = !isVisible;
	}
}