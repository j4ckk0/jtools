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
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.net.URL;

import javax.swing.Action;
import javax.swing.ButtonModel;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.UIManager;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.metal.MetalButtonUI;

// TODO: Auto-generated Javadoc
/**
 * The Class JLinkButton.
 */
public class JLinkButton extends JButton {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 2273968908255873890L;

	/** The Constant uiString. */
	private static final String uiString = "LinkButtonUI";

	/** The Constant ALWAYS_UNDERLINE. */
	public static final int ALWAYS_UNDERLINE = 0;

	/** The Constant HOVER_UNDERLINE. */
	public static final int HOVER_UNDERLINE = 1;

	/** The Constant NEVER_UNDERLINE. */
	public static final int NEVER_UNDERLINE = 2;

	/** The Constant SYSTEM_DEFAULT. */
	public static final int SYSTEM_DEFAULT = 3;

	/** The link behavior. */
	private int linkBehavior;

	/** The link color. */
	private Color linkColor;

	/** The color pressed. */
	private Color colorPressed;

	/** The visited link color. */
	private Color visitedLinkColor;

	/** The disabled link color. */
	private Color disabledLinkColor;

	/** The button URL. */
	private URL buttonURL;

	/** The default action. */
	private Action defaultAction;

	/** The is link visited. */
	private boolean isLinkVisited;

	/**
	 * Instantiates a new j link button.
	 */
	public JLinkButton() {
		this(null, null, null);
	}

	/**
	 * Instantiates a new j link button.
	 *
	 * @param action the action
	 */
	public JLinkButton(Action action) {
		this();
		setAction(action);
	}

	/**
	 * Instantiates a new j link button.
	 *
	 * @param icon the icon
	 */
	public JLinkButton(Icon icon) {
		this(null, icon, null);
	}

	/**
	 * Instantiates a new j link button.
	 *
	 * @param s the s
	 */
	public JLinkButton(String s) {
		this(s, null, null);
	}

	/**
	 * Instantiates a new j link button.
	 *
	 * @param url the url
	 */
	public JLinkButton(URL url) {
		this(null, null, url);
	}

	/**
	 * Instantiates a new j link button.
	 *
	 * @param s the s
	 * @param url the url
	 */
	public JLinkButton(String s, URL url) {
		this(s, null, url);
	}

	/**
	 * Instantiates a new j link button.
	 *
	 * @param icon the icon
	 * @param url the url
	 */
	public JLinkButton(Icon icon, URL url) {
		this(null, icon, url);
	}

	/**
	 * Instantiates a new j link button.
	 *
	 * @param text the text
	 * @param icon the icon
	 * @param url the url
	 */
	public JLinkButton(String text, Icon icon, URL url) {
		super(text, icon);
		linkBehavior = SYSTEM_DEFAULT;
		linkColor = Color.blue;
		colorPressed = Color.red;
		visitedLinkColor = new Color(128, 0, 128);
		if (text == null && url != null)
			setText(url.toExternalForm());
		setLinkURL(url);
		setCursor(Cursor.getPredefinedCursor(12));
		setBorderPainted(false);
		setContentAreaFilled(false);
		setRolloverEnabled(true);
		addActionListener(defaultAction);
	}

	/**
	 * Update UI.
	 */
	public void updateUI() {
		setUI(BasicLinkButtonUI.createUI(this));
	}

	/**
	 * Sets the default.
	 */
	public void setDefault() {
		UIManager.getDefaults().put("LinkButtonUI", "BasicLinkButtonUI");
	}

	/**
	 * Gets the UI class ID.
	 *
	 * @return the UI class ID
	 */
	public String getUIClassID() {
		return uiString;
	}

	/**
	 * Setup tool tip text.
	 */
	protected void setupToolTipText() {
		String tip = null;
		if (buttonURL != null)
			tip = buttonURL.toExternalForm();
		setToolTipText(tip);
	}

	/**
	 * Sets the link behavior.
	 *
	 * @param bnew the new link behavior
	 */
	public void setLinkBehavior(int bnew) {
		checkLinkBehaviour(bnew);
		int old = linkBehavior;
		linkBehavior = bnew;
		firePropertyChange("linkBehavior", old, bnew);
		repaint();
	}

	/**
	 * Check link behaviour.
	 *
	 * @param beha the beha
	 */
	private void checkLinkBehaviour(int beha) {
		if (beha != ALWAYS_UNDERLINE && beha != HOVER_UNDERLINE
				&& beha != NEVER_UNDERLINE && beha != SYSTEM_DEFAULT)
			throw new IllegalArgumentException("Not a legal LinkBehavior");
		else
			return;
	}

	/**
	 * Gets the link behavior.
	 *
	 * @return the link behavior
	 */
	public int getLinkBehavior() {
		return linkBehavior;
	}

	/**
	 * Sets the link color.
	 *
	 * @param color the new link color
	 */
	public void setLinkColor(Color color) {
		Color colorOld = linkColor;
		linkColor = color;
		firePropertyChange("linkColor", colorOld, color);
		repaint();
	}

	/**
	 * Gets the link color.
	 *
	 * @return the link color
	 */
	public Color getLinkColor() {
		return linkColor;
	}

	/**
	 * Sets the active link color.
	 *
	 * @param colorNew the new active link color
	 */
	public void setActiveLinkColor(Color colorNew) {
		Color colorOld = colorPressed;
		colorPressed = colorNew;
		firePropertyChange("activeLinkColor", colorOld, colorNew);
		repaint();
	}

	/**
	 * Gets the active link color.
	 *
	 * @return the active link color
	 */
	public Color getActiveLinkColor() {
		return colorPressed;
	}

	/**
	 * Sets the disabled link color.
	 *
	 * @param color the new disabled link color
	 */
	public void setDisabledLinkColor(Color color) {
		Color colorOld = disabledLinkColor;
		disabledLinkColor = color;
		firePropertyChange("disabledLinkColor", colorOld, color);
		if (!isEnabled())
			repaint();
	}

	/**
	 * Gets the disabled link color.
	 *
	 * @return the disabled link color
	 */
	public Color getDisabledLinkColor() {
		return disabledLinkColor;
	}

	/**
	 * Sets the visited link color.
	 *
	 * @param colorNew the new visited link color
	 */
	public void setVisitedLinkColor(Color colorNew) {
		Color colorOld = visitedLinkColor;
		visitedLinkColor = colorNew;
		firePropertyChange("visitedLinkColor", colorOld, colorNew);
		repaint();
	}

	/**
	 * Gets the visited link color.
	 *
	 * @return the visited link color
	 */
	public Color getVisitedLinkColor() {
		return visitedLinkColor;
	}

	/**
	 * Gets the link URL.
	 *
	 * @return the link URL
	 */
	public URL getLinkURL() {
		return buttonURL;
	}

	/**
	 * Sets the link URL.
	 *
	 * @param url the new link URL
	 */
	public void setLinkURL(URL url) {
		URL urlOld = buttonURL;
		buttonURL = url;
		setupToolTipText();
		firePropertyChange("linkURL", urlOld, url);
		revalidate();
		repaint();
	}

	/**
	 * Sets the link visited.
	 *
	 * @param flagNew the new link visited
	 */
	public void setLinkVisited(boolean flagNew) {
		boolean flagOld = isLinkVisited;
		isLinkVisited = flagNew;
		firePropertyChange("linkVisited", flagOld, flagNew);
		repaint();
	}

	/**
	 * Checks if is link visited.
	 *
	 * @return true, if is link visited
	 */
	public boolean isLinkVisited() {
		return isLinkVisited;
	}

	/**
	 * Sets the default action.
	 *
	 * @param actionNew the new default action
	 */
	public void setDefaultAction(Action actionNew) {
		Action actionOld = defaultAction;
		defaultAction = actionNew;
		firePropertyChange("defaultAction", actionOld, actionNew);
	}

	/**
	 * Gets the default action.
	 *
	 * @return the default action
	 */
	public Action getDefaultAction() {
		return defaultAction;
	}

	/**
	 * Param string.
	 *
	 * @return the string
	 */
	protected String paramString() {
		String str;
		if (linkBehavior == ALWAYS_UNDERLINE)
			str = "ALWAYS_UNDERLINE";
		else if (linkBehavior == HOVER_UNDERLINE)
			str = "HOVER_UNDERLINE";
		else if (linkBehavior == NEVER_UNDERLINE)
			str = "NEVER_UNDERLINE";
		else
			str = "SYSTEM_DEFAULT";
		String colorStr = linkColor == null ? "" : linkColor.toString();
		String colorPressStr = colorPressed == null ? "" : colorPressed
				.toString();
		String disabledLinkColorStr = disabledLinkColor == null ? ""
				: disabledLinkColor.toString();
		String visitedLinkColorStr = visitedLinkColor == null ? ""
				: visitedLinkColor.toString();
		String buttonURLStr = buttonURL == null ? "" : buttonURL.toString();
		String isLinkVisitedStr = isLinkVisited ? "true" : "false";
		return super.paramString() + ",linkBehavior=" + str + ",linkURL="
		+ buttonURLStr + ",linkColor=" + colorStr + ",activeLinkColor="
		+ colorPressStr + ",disabledLinkColor=" + disabledLinkColorStr
		+ ",visitedLinkColor=" + visitedLinkColorStr
		+ ",linkvisitedString=" + isLinkVisitedStr;
	}
}

class BasicLinkButtonUI extends MetalButtonUI {
	private static final BasicLinkButtonUI ui = new BasicLinkButtonUI();

	public BasicLinkButtonUI() {
	}

	public static ComponentUI createUI(JComponent jcomponent) {
		return ui;
	}

	protected void paintText(Graphics g, JComponent com, Rectangle rect,
			String s) {
		JLinkButton bn = (JLinkButton) com;
		ButtonModel bnModel = bn.getModel();
//		Color color = bn.getForeground();
//		Object obj = null;
		if (bnModel.isEnabled()) {
			if (bnModel.isPressed())
				bn.setForeground(bn.getActiveLinkColor());
			else if (bn.isLinkVisited())
				bn.setForeground(bn.getVisitedLinkColor());

			else
				bn.setForeground(bn.getLinkColor());
		} else {
			if (bn.getDisabledLinkColor() != null)
				bn.setForeground(bn.getDisabledLinkColor());
		}
		super.paintText(g, com, rect, s);
		int behaviour = bn.getLinkBehavior();
		boolean drawLine = false;
		if (behaviour == JLinkButton.HOVER_UNDERLINE) {
			if (bnModel.isRollover())
				drawLine = true;
		} else if (behaviour == JLinkButton.ALWAYS_UNDERLINE || behaviour == JLinkButton.SYSTEM_DEFAULT)
			drawLine = true;
		if (!drawLine)
			return;
		FontMetrics fm = g.getFontMetrics();
		int x = rect.x + getTextShiftOffset();
		int y = (rect.y + fm.getAscent() + fm.getDescent() + getTextShiftOffset()) - 1;
		if (bnModel.isEnabled()) {
			g.setColor(bn.getForeground());
			g.drawLine(x, y, (x + rect.width) - 1, y);
		} else {
			g.setColor(bn.getBackground().brighter());
			g.drawLine(x, y, (x + rect.width) - 1, y);
		}
	}
}
