package com.jtools.utils.gui.border;

import java.awt.Color;
import java.awt.Component;
import java.awt.Insets;

import javax.swing.AbstractButton;
import javax.swing.JToolBar;
import javax.swing.border.LineBorder;
import javax.swing.text.JTextComponent;

/**
 * 
 * @author j4ckk0
 *
 */
public class MarginLineBorder extends LineBorder {

	//////////////////////////////////////////////////
	//
	// Class variables and constants
	//
	//////////////////////////////////////////////////

	/** The serial UID */
	private static final long serialVersionUID = 4813496499535613615L;

	/** The margins */
	private Integer topMargin, leftMargin, bottomMargin, rightMargin;

	//////////////////////////////////////////////////
	//
	// Constructors
	//
	//////////////////////////////////////////////////

	/**
	 * 
	 * @param lineColor
	 */
	public MarginLineBorder(Color lineColor) {
		super(lineColor);
	}

	/**
	 * <p>
	 * Constructor for MarginLineBorder.
	 * </p>
	 *
	 * @param lineColor    a {@link java.awt.Color} object.
	 * @param topMargin    a int.
	 * @param leftMargin   a int.
	 * @param bottomMargin a int.
	 * @param rightMargin  a int.
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

	//////////////////////////////////////////////////
	//
	// Private methods
	//
	//////////////////////////////////////////////////

}
