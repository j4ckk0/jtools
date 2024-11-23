package org.jtools.utils.gui.border;

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

import java.awt.Color;
import java.awt.Component;
import java.awt.Insets;

import javax.swing.AbstractButton;
import javax.swing.JToolBar;
import javax.swing.border.LineBorder;
import javax.swing.text.JTextComponent;
// TODO: Auto-generated Javadoc

/**
 * The Class MarginLineBorder.
 */
public class MarginLineBorder extends LineBorder {

	//////////////////////////////////////////////////
	//
	// Class variables and constants
	//
	//////////////////////////////////////////////////
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 4813496499535613615L;
	
	/** The right margin. */
	private Integer topMargin, leftMargin, bottomMargin, rightMargin;

	//////////////////////////////////////////////////
	//
	// Constructors
	//
	//////////////////////////////////////////////////
	
	/**
	 * Instantiates a new margin line border.
	 *
	 * @param lineColor the line color
	 */
	public MarginLineBorder(Color lineColor) {
		super(lineColor);
	}
	
	/**
	 * Instantiates a new margin line border.
	 *
	 * @param lineColor the line color
	 * @param topMargin the top margin
	 * @param leftMargin the left margin
	 * @param bottomMargin the bottom margin
	 * @param rightMargin the right margin
	 */
	public MarginLineBorder(Color lineColor, int topMargin, int leftMargin, int bottomMargin, int rightMargin) {
		super(lineColor);
		this.topMargin = topMargin;
		this.leftMargin = leftMargin;
		this.bottomMargin = bottomMargin;
		this.rightMargin = rightMargin;
	}

	/**
	 * <p>
	 * Constructor for MarginLineBorder.
	 * </p>
	 *
	 * @param topMargin    a int.
	 * @param leftMargin   a int.
	 * @param bottomMargin a int.
	 * @param rightMargin  a int.
	 */
	public MarginLineBorder(int topMargin, int leftMargin, int bottomMargin, int rightMargin) {
		super(Color.LIGHT_GRAY);
		this.topMargin = topMargin;
		this.leftMargin = leftMargin;
		this.bottomMargin = bottomMargin;
		this.rightMargin = rightMargin;
	}

	//////////////////////////////////////////////////
	//
	// TitledBorder methods
	//
	//////////////////////////////////////////////////

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.border.TitledBorder#getBorderInsets(java.awt.Component)
	 */
	/** {@inheritDoc} */
	@Override
	public Insets getBorderInsets(Component c) {
		Insets insets = super.getBorderInsets(c);
		return getBorderInsets(c, insets);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.border.LineBorder#getBorderInsets(java.awt.Component,
	 * java.awt.Insets)
	 */
	/** {@inheritDoc} */
	@Override
	public Insets getBorderInsets(Component c, Insets insets) {
		if (topMargin != null && leftMargin != null && bottomMargin != null && rightMargin != null) {
			insets.set(topMargin + thickness, leftMargin + thickness, bottomMargin + thickness,
					rightMargin + thickness);
			return insets;
		} else {
			Insets margin = null;
			//
			// Ideally we'd have an interface defined for classes which
			// support margins (to avoid this hackery), but we've
			// decided against it for simplicity
			//
			if (c instanceof AbstractButton) {
				margin = ((AbstractButton) c).getMargin();
			} else if (c instanceof JToolBar) {
				margin = ((JToolBar) c).getMargin();
			} else if (c instanceof JTextComponent) {
				margin = ((JTextComponent) c).getMargin();
			}
			insets.top = (margin != null ? margin.top : 0) + thickness;
			insets.left = (margin != null ? margin.left : 0) + thickness;
			insets.bottom = (margin != null ? margin.bottom : 0) + thickness;
			insets.right = (margin != null ? margin.right : 0) + thickness;

			return insets;
		}
	}

}
