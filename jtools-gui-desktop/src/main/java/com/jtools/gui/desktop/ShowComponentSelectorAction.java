package com.jtools.gui.desktop;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JTabbedPane;

import com.jtools.utils.gui.components.CascadeDesktopPane;

/**
 * 
 * @author j4ckk0
 *
 */
public class ShowComponentSelectorAction extends AbstractAction {

	private static final long serialVersionUID = 2870619093541127857L;

	private static final int CASCADE_HORIZONTAL_OFFSET = 0;
	private static final int CASCADE_VERTICAL_OFFSET = 60;

	private final JDesktopPane desktopPane;
	private final Component component;

	public ShowComponentSelectorAction(String name, JDesktopPane desktopPane, Component component) {
		super(name);
		this.desktopPane = desktopPane;
		this.component = component;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (!component.isVisible()) {
			if(desktopPane instanceof CascadeDesktopPane) {
				((CascadeDesktopPane)desktopPane).add(component, CASCADE_HORIZONTAL_OFFSET, CASCADE_VERTICAL_OFFSET);
			}
			else {
				desktopPane.add(component);
			}

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
	}
}