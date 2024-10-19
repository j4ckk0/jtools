package com.jtools.utils.gui.border;

import java.awt.Color;

import javax.swing.border.TitledBorder;

/**
 * 
 * @author j4ckk0
 *
 */
public class MarginTitledBorder extends TitledBorder {

	//////////////////////////////////////////////////
	//
	// Class variables and constants
	//
	//////////////////////////////////////////////////

	/** The serial UID */
	private static final long serialVersionUID = 4813496499535613615L;

	//////////////////////////////////////////////////
	//
	// Constructors
	//
	//////////////////////////////////////////////////

	/**
	 * <p>
	 * Constructor for MarginTitledBorder.
	 * </p>
	 *
	 * @param title        a {@link java.lang.String} object.
	 * @param lineColor    a {@link java.awt.Color} object.
	 * @param topMargin    a int.
	 * @param leftMargin   a int.
	 * @param bottomMargin a int.
	 * @param rightMargin  a int.
	 */
	public MarginTitledBorder(String title, Color lineColor, final int topMargin, final int leftMargin,
			final int bottomMargin, final int rightMargin) {
		super(new MarginLineBorder(lineColor, topMargin, leftMargin, bottomMargin, rightMargin), title);
	}

	/**
	 * <p>
	 * Constructor for MarginTitledBorder.
	 * </p>
	 *
	 * @param title        a {@link java.lang.String} object.
	 * @param topMargin    a int.
	 * @param leftMargin   a int.
	 * @param bottomMargin a int.
	 * @param rightMargin  a int.
	 */
	public MarginTitledBorder(String title, final int topMargin, final int leftMargin, final int bottomMargin,
			final int rightMargin) {
		super(new MarginLineBorder(topMargin, leftMargin, bottomMargin, rightMargin), title);
	}

	//////////////////////////////////////////////////
	//
	// Public methods
	//
	//////////////////////////////////////////////////

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

	//////////////////////////////////////////////////
	//
	// Private methods
	//
	//////////////////////////////////////////////////

}
