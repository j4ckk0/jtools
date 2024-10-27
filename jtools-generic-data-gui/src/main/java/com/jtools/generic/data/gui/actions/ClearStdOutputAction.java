package com.jtools.generic.data.gui.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.jtools.generic.data.gui.iframe.StdOutputFrame;

/**
 * 
 * @author j4ckk0
 *
 */
public class ClearStdOutputAction extends AbstractAction {

	private static final long serialVersionUID = -915029730697379837L;

	private ShowStdOutputAction showStdOutputAction;

	public ClearStdOutputAction(String name, ShowStdOutputAction showStdOutputAction) {
		super(name);
		this.showStdOutputAction = showStdOutputAction;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		StdOutputFrame stdOutputFrame = showStdOutputAction.getStdOutputFrame();
		if (stdOutputFrame != null) {
			stdOutputFrame.clear();
		}
	}
}