package com.jtools.utils.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Icon;

/**
 * 
 * @author j4ckk0
 *
 */
public class ExitAction extends AbstractAction {

	private static final long serialVersionUID = -8034632895201890192L;

	public ExitAction(String name, Icon icon) {
		super(name, icon);
	}

	public ExitAction(String name) {
		super(name);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.exit(0);
	}
}