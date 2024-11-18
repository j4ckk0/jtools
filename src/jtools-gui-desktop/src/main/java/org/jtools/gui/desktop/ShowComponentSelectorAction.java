package org.jtools.gui.desktop;

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

import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JTabbedPane;

import org.jtools.utils.gui.components.CascadeDesktopPane;

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
