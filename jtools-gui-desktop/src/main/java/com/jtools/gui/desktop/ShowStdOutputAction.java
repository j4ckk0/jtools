package com.jtools.gui.desktop;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JDesktopPane;

/**
 * 
 * @author j4ckk0
 *
 */
public class ShowStdOutputAction extends AbstractAction {

	private static final long serialVersionUID = 2914655924914576949L;

	private final JDesktopPane desktopPane;

	private StdOutputFrame stdOutputFrame;

	private boolean isVisible;

	public ShowStdOutputAction(String name, JDesktopPane desktopPane) {
		super(name);
		this.desktopPane = desktopPane;
		isVisible = false;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (!isVisible) {
			stdOutputFrame = new StdOutputFrame();
			desktopPane.add(stdOutputFrame);
			stdOutputFrame.setVisible(true);
			stdOutputFrame.moveToFront();
		} else {
			stdOutputFrame.setVisible(false);
			desktopPane.remove(stdOutputFrame);
			stdOutputFrame = null;
		}
		isVisible = !isVisible;
	}

	public StdOutputFrame getStdOutputFrame() {
		return stdOutputFrame;
	}
}