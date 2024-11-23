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
// TODO: Auto-generated Javadoc

/**
 * The Class MarginBevelBorder.
 */
public class MarginBevelBorder extends BevelBorder {

	//////////////////////////////////////////////////
	//
	// Class variables and constants
	//
	//////////////////////////////////////////////////
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 2247287585606210963L;
	
	/** The right margin. */
	private int topMargin, leftMargin, bottomMargin, rightMargin;

	//////////////////////////////////////////////////
	//
	// Constructors
	//
	//////////////////////////////////////////////////

	/**
	 * 
	 * Instantiates a new margin bevel border.
	 *
	 * @param bevelType the bevel type
	 * @param highlightOuterColor the highlight outer color
	 * @param highlightInnerColor the highlight inner color
	 * @param shadowOuterColor the shadow outer color
	 * @param shadowInnerColor the shadow inner color
	 * @param topMargin the top margin
	 * @param leftMargin the left margin
	 * @param bottomMargin the bottom margin
	 * @param rightMargin the right margin
	 */
	public MarginBevelBorder(int bevelType, Color highlightOuterColor, Color highlightInnerColor,
			Color shadowOuterColor, Color shadowInnerColor, final int topMargin, final int leftMargin,
			final int bottomMargin, final int rightMargin) {
		super(bevelType, highlightOuterColor, highlightInnerColor, shadowOuterColor, shadowInnerColor);

		this.topMargin = topMargin;
		this.leftMargin = leftMargin;
		this.bottomMargin = bottomMargin;
		this.rightMargin = rightMargin;
	}
	
	/**
	 * Instantiates a new margin bevel border.
	 *
	 * @param bevelType the bevel type
	 * @param highlight the highlight
	 * @param shadow the shadow
	 * @param topMargin the top margin
	 * @param leftMargin the left margin
	 * @param bottomMargin the bottom margin
	 * @param rightMargin the right margin
	 */
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

}
