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

import javax.swing.border.BevelBorder;
public class MarginBevelBorder extends BevelBorder {

	//////////////////////////////////////////////////
	//
	// Class variables and constants
	//
	//////////////////////////////////////////////////
	private static final long serialVersionUID = 2247287585606210963L;
	private int topMargin, leftMargin, bottomMargin, rightMargin;

	//////////////////////////////////////////////////
	//
	// Constructors
	//
	//////////////////////////////////////////////////
	public MarginBevelBorder(int bevelType, Color highlightOuterColor, Color highlightInnerColor,
			Color shadowOuterColor, Color shadowInnerColor, final int topMargin, final int leftMargin,
			final int bottomMargin, final int rightMargin) {
		super(bevelType, highlightOuterColor, highlightInnerColor, shadowOuterColor, shadowInnerColor);

		this.topMargin = topMargin;
		this.leftMargin = leftMargin;
		this.bottomMargin = bottomMargin;
		this.rightMargin = rightMargin;
	}
	public MarginBevelBorder(int bevelType, Color highlight, Color shadow, final int topMargin, final int leftMargin,
			final int bottomMargin, final int rightMargin) {
		super(bevelType, highlight, shadow);

		this.topMargin = topMargin;
		this.leftMargin = leftMargin;
		this.bottomMargin = bottomMargin;
		this.rightMargin = rightMargin;
	}

	/**
	 * <p>
	 * Constructor for MarginBevelBorder.
	 * </p>
	 *
	 * @param bevelType    a int.
	 * @param topMargin    a int.
	 * @param leftMargin   a int.
	 * @param bottomMargin a int.
	 * @param rightMargin  a int.
	 */
	public MarginBevelBorder(int bevelType, final int topMargin, final int leftMargin, final int bottomMargin,
			final int rightMargin) {
		super(bevelType);

		this.topMargin = topMargin;
		this.leftMargin = leftMargin;
		this.bottomMargin = bottomMargin;
		this.rightMargin = rightMargin;
	}

	//////////////////////////////////////////////////
	//
	// BevelBorder methods
	//
	//////////////////////////////////////////////////

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.border.BevelBorder#getBorderInsets(java.awt.Component,
	 * java.awt.Insets)
	 */
	/** {@inheritDoc} */
	@Override
	public Insets getBorderInsets(Component c, Insets insets) {
		Insets ins = super.getBorderInsets(c, insets);
		ins.set(topMargin > 1 ? topMargin : ins.top, leftMargin > 1 ? leftMargin : ins.left,
				bottomMargin > 1 ? bottomMargin : ins.bottom, rightMargin > 1 ? rightMargin : ins.right);
		return ins;
	}

	//////////////////////////////////////////////////
	//
	// Public methods
	//
	//////////////////////////////////////////////////

	//////////////////////////////////////////////////
	//
	// Private methods
	//
	//////////////////////////////////////////////////

}
