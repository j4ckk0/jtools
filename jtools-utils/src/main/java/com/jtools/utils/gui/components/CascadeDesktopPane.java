/**
 * 
 */
package com.jtools.utils.gui.components;

/*-
 * #%L
 * Java Tools - Utils
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

import javax.swing.JDesktopPane;

/**
 * @author j4ckk0
 *
 */
public class CascadeDesktopPane extends JDesktopPane {
	
	private static final long serialVersionUID = 419155362915146329L;
	
	private static final int DEFAUT_HORIZONTAL_OFFSET = 60;
	private static final int DEFAULT_VERTICAL_OFFSET = 60;

	/**
     * Appends the specified component to the end of this container.
     * This is a convenience method for {@link #addImpl}.
     * <p>
     * This method changes layout-related information, and therefore,
     * invalidates the component hierarchy. If the container has already been
     * displayed, the hierarchy must be validated thereafter in order to
     * display the added component.
     *
     * @param     comp   the component to be added
     * @exception NullPointerException if {@code comp} is {@code null}
     * @see #addImpl
     * @see #invalidate
     * @see #validate
     * @see javax.swing.JComponent#revalidate()
     * @return    the component argument
     */
	@Override
	public Component add(Component comp) {
		return add(comp, DEFAUT_HORIZONTAL_OFFSET, DEFAULT_VERTICAL_OFFSET);
	}
	
	public Component add(Component comp, int horizontalOffset, int verticalOffset) {
		comp.setBounds(getComponentCount() * horizontalOffset, getComponentCount() * verticalOffset, comp.getWidth(), comp.getHeight());
		addImpl(comp, null, -1);
		return comp;
	}
}
