package com.jtools.gui.desktop;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JTabbedPane;

/**
 * 
 * @author j4ckk0
 *
 */
public class ShowComponentSelectorAction extends AbstractAction {

	private static final long serialVersionUID = 2870619093541127857L;

	private final JDesktopPane desktopPane;
	private final Component component;

	private boolean isVisible;

	public ShowComponentSelectorAction(String name, JDesktopPane desktopPane, Component component) {
		super(name);
		this.desktopPane = desktopPane;
		this.component = component;

		isVisible = false;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (!isVisible) {
			desktopPane.add(component);

			Container parent = desktopPane.getParent();
			if(parent instanceof JTabbedPane) {
				((JTabbedPane)parent).setSelectedComponent(desktopPane);
			}

			component.setVisible(true);

			if(component instanceof JInternalFrame) {
				((JInternalFrame)component).moveToFront();
			}
		} else {
			component.setVisible(false);
			desktopPane.remove(component);

			Container parent = desktopPane.getParent();
			if(parent instanceof JTabbedPane) {
				((JTabbedPane)parent).setSelectedComponent(desktopPane);
			}

		}
		isVisible = !isVisible;
	}
}