package org.jtools.utils.gui.components;

/*-
 * #%L
 * Java Tools - Utils
 * %%
 * Copyright (C) 2024 jtools.org
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

import javax.swing.JDesktopPane;
// TODO: Auto-generated Javadoc

/**
 * The Class CascadeDesktopPane.
 */
public class CascadeDesktopPane extends JDesktopPane {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 419155362915146329L;

	/** The Constant DEFAUT_HORIZONTAL_OFFSET. */
	private static final int DEFAUT_HORIZONTAL_OFFSET = 60;

	/** The Constant DEFAULT_VERTICAL_OFFSET. */
	private static final int DEFAULT_VERTICAL_OFFSET = 60;

	/**
	 * Instantiates a new cascade desktop pane.
	 */
	public CascadeDesktopPane() {
		super();
	}

	/**
	 * Adds the.
	 *
	 * @param comp the comp
	 * @return the component
	 */
	@Override
	public Component add(Component comp) {
		return add(comp, DEFAUT_HORIZONTAL_OFFSET, DEFAULT_VERTICAL_OFFSET);
	}

	/**
	 * Adds the.
	 *
	 * @param comp the comp
	 * @param horizontalOffset the horizontal offset
	 * @param verticalOffset the vertical offset
	 * @return the component
	 */
	public Component add(Component comp, int horizontalOffset, int verticalOffset) {
		comp.setBounds(getComponentCount() * horizontalOffset, getComponentCount() * verticalOffset, comp.getWidth(), comp.getHeight());
		addImpl(comp, null, -1);
		return comp;
	}
}
