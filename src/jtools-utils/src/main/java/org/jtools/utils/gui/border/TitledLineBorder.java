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
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JComponent;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;


public class TitledLineBorder extends TitledBorder {

	private static final long serialVersionUID = -7572972693245059564L;

	private static Method drawStringMethod;

	public TitledLineBorder(Border border, String title, int titleJustification, int titlePosition, Font titleFont,
			Color titleColor) {
		super(border, title, titleJustification, titlePosition, titleFont, titleColor);
	}

	public TitledLineBorder(Border border, String title, int titleJustification, int titlePosition, Font titleFont) {
		super(border, title, titleJustification, titlePosition, titleFont);
	}

	public TitledLineBorder(Border border, String title, int titleJustification, int titlePosition) {
		super(border, title, titleJustification, titlePosition);
	}

	public TitledLineBorder(Border border) {
		super(border);
	}

	public TitledLineBorder(String title) {
		super(title);
	}

	@Override
	public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
		Point textLoc = new Point();

		Border border = getBorder();

		if (getTitle() == null || getTitle().equals("")) {
			if (border != null) {
				border.paintBorder(c, g, x, y, width, height);
			}
			return;
		}

		Rectangle grooveRect = new Rectangle(x + EDGE_SPACING, y + EDGE_SPACING, width - (EDGE_SPACING * 2),
				height - (EDGE_SPACING * 2));
		Font font = g.getFont();
		Color color = g.getColor();

		g.setFont(getFont(c));

		JComponent jc = (c instanceof JComponent) ? (JComponent) c : null;
		FontMetrics fm = getFontMetrics(jc, g);
		int descent = fm.getDescent();
		int ascent = fm.getAscent();
		int diff;
		int stringWidth = fm.stringWidth(getTitle());
		int stringHeight = fm.getHeight();
		Insets insets;

		if (border != null) {
			insets = border.getBorderInsets(c);
		} else {
			insets = new Insets(0, 0, 0, 0);
		}

		diff = Math.max(0, ((ascent / 2) + TEXT_SPACING) - EDGE_SPACING);
		grooveRect.y += diff;
		grooveRect.height -= diff;
		textLoc.y = (grooveRect.y - descent) + (insets.top + ascent + descent) / 2;

		textLoc.x = grooveRect.x + insets.left;

		if (border != null) {
			if (grooveRect.y > textLoc.y - ascent) {

				Rectangle clipRect = new Rectangle();
				Rectangle saveClip = g.getClipBounds();

				clipRect.setBounds(saveClip);
				if (computeIntersection(clipRect, x, y, textLoc.x - 1 - x, height)) {
					g.setClip(clipRect);
					border.paintBorder(c, g, grooveRect.x, grooveRect.y, grooveRect.width, grooveRect.height);
				}

				clipRect.setBounds(saveClip);
				if (computeIntersection(clipRect, textLoc.x + stringWidth + 1, y,
						x + width - (textLoc.x + stringWidth + 1), height)) {
					g.setClip(clipRect);
					border.paintBorder(c, g, grooveRect.x, grooveRect.y, grooveRect.width, grooveRect.height);
				}

				clipRect.setBounds(saveClip);
				if (computeIntersection(clipRect, textLoc.x - 1, textLoc.y + descent, stringWidth + 2,
						y + height - textLoc.y - descent)) {
					g.setClip(clipRect);
					border.paintBorder(c, g, grooveRect.x, grooveRect.y, grooveRect.width, grooveRect.height);
				}

				g.setClip(saveClip);

			} else {
				border.paintBorder(c, g, grooveRect.x, grooveRect.y, grooveRect.width, grooveRect.height);
			}
		}

		g.setColor(getTitleColor());

		if (drawStringMethod == null) {
			try {
				Class<?> su = Class.forName("sun.swing.SwingUtilities2");
				drawStringMethod = su.getMethod("drawString", JComponent.class, Graphics.class, String.class, int.class,
						int.class);
			} catch (Throwable e) {
				Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage());
				Logger.getLogger(getClass().getName()).log(Level.FINE, e.getMessage(), e);
			}
		}
		try {
			drawStringMethod.invoke(null, jc, g, getTitle(), textLoc.x, textLoc.y);
		} catch (Throwable e) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage());
			Logger.getLogger(getClass().getName()).log(Level.FINE, e.getMessage(), e);
		}

		g.setColor(Color.LIGHT_GRAY);
		g.drawLine(x + stringWidth + textLoc.x, y + stringHeight / 2, x + width, y + stringHeight / 2);

		g.setFont(font);
		g.setColor(color);
	}

	private boolean computeIntersection(Rectangle dest, int rx, int ry, int rw, int rh) {
		int x1 = Math.max(rx, dest.x);
		int x2 = Math.min(rx + rw, dest.x + dest.width);
		int y1 = Math.max(ry, dest.y);
		int y2 = Math.min(ry + rh, dest.y + dest.height);
		dest.x = x1;
		dest.y = y1;
		dest.width = x2 - x1;
		dest.height = y2 - y1;

		if (dest.width <= 0 || dest.height <= 0)
		{
			return false;
		}
		return true;
	}

	@SuppressWarnings("deprecation")
	private FontMetrics getFontMetrics(JComponent component, Graphics g) {
		if(component != null) {
			return component.getFontMetrics(g.getFont());
		}
		return Toolkit.getDefaultToolkit().getFontMetrics(g.getFont());
	}
}
