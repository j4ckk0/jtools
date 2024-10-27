package com.jtools.data.gui.desktop;

import java.awt.Container;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JDesktopPane;
import javax.swing.JTabbedPane;

/**
 * 
 * @author j4ckk0
 *
 */
public class ShowDefaultDataProviderAction extends AbstractAction {

	private static final long serialVersionUID = 2914655924914576949L;

	private final JDesktopPane desktopPane;
	private final DefaultDataProvider defaultDataTypesProvider;

	private boolean isVisible;

	public ShowDefaultDataProviderAction(String name, JDesktopPane desktopPane,
			DefaultDataProvider defaultDataTypesProvider) {
		super(name);
		this.desktopPane = desktopPane;
		this.defaultDataTypesProvider = defaultDataTypesProvider;

		isVisible = false;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (!isVisible) {
			desktopPane.add(defaultDataTypesProvider);
			
			Container parent = desktopPane.getParent();
			if(parent instanceof JTabbedPane) {
				((JTabbedPane)parent).setSelectedComponent(desktopPane);
			}
			
			defaultDataTypesProvider.setVisible(true);
			defaultDataTypesProvider.moveToFront();
		} else {
			defaultDataTypesProvider.setVisible(false);
			desktopPane.remove(defaultDataTypesProvider);
			
			Container parent = desktopPane.getParent();
			if(parent instanceof JTabbedPane) {
				((JTabbedPane)parent).setSelectedComponent(desktopPane);
			}
			
		}
		isVisible = !isVisible;
	}
}