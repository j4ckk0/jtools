package com.jtools.utils.gui.border;

import java.awt.Color;
import java.awt.Component;
import java.awt.Insets;

import javax.swing.border.BevelBorder;

/**
 * 
 * @author j4ckk0
 *
 */
public class MarginBevelBorder extends BevelBorder {

	//////////////////////////////////////////////////
	//
	// Class variables and constants
	//
	//////////////////////////////////////////////////

	/** The serial UID */
	private static final long serialVersionUID = 2247287585606210963L;

	/** The margins */
	private int topMargin, leftMargin, bottomMargin, rightMargin;

	//////////////////////////////////////////////////
	//
	// Constructors
	//
	//////////////////////////////////////////////////

	/**
	 * <p>
	 * Constructor for MarginBevelBorder.
	 * </p>
	 *
	 * @param bevelType           a int.
	 * @param highlightOuterColor a {@link java.awt.Color} object.
	 * @param highlightInnerColor a {@link java.awt.Color} object.
	 * @param shadowOuterColor    a {@link java.awt.Color} object.
	 * @param shadowInnerColor    a {@link java.awt.Color} object.
	 * @param topMargin           a int.
	 * @param leftMargin          a int.
	 * @param bottomMargin        a int.
	 * @param rightMargin         a int.
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
	 * <p>
	 * Constructor for MarginBevelBorder.
	 * </p>
	 *
	 * @param bevelType    a int.
	 * @param highlight    a {@link java.awt.Color} object.
	 * @param shadow       a {@link java.awt.Color} object.
	 * @param topMargin    a int.
	 * @param leftMargin   a int.
	 * @param bottomMargin a int.
	 * @param rightMargin  a int.
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
