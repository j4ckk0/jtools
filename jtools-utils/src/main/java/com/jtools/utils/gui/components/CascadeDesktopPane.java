/**
 * 
 */
package com.jtools.utils.gui.components;

import java.awt.Component;

import javax.swing.JDesktopPane;

/**
 * @author j4ckk0
 *
 */
public class CascadeDesktopPane extends JDesktopPane {
	
	private static final long serialVersionUID = 419155362915146329L;
	
	private static final int horizontalOffset = 30;
	
	private static final int verticalOffset = 30;

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
		comp.setBounds(getComponentCount() * horizontalOffset, getComponentCount() * verticalOffset, comp.getWidth(), comp.getHeight());
		addImpl(comp, null, -1);
		return comp;
	}
}
