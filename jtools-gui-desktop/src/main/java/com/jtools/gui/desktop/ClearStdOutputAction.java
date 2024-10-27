package com.jtools.gui.desktop;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

/**
 * 
 * @author j4ckk0
 *
 */
public class ClearStdOutputAction extends AbstractAction {

	private static final long serialVersionUID = -915029730697379837L;

	private StdOutputFrame stdOutputFrame;

	public ClearStdOutputAction(String name, StdOutputFrame stdOutputFrame) {
		super(name);
		this.stdOutputFrame = stdOutputFrame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (stdOutputFrame != null) {
			stdOutputFrame.clear();
		}
	}
}