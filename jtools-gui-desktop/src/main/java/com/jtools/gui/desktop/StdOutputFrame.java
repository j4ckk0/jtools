package com.jtools.gui.desktop;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;

import com.jtools.utils.gui.io.StdOutputTextArea;

/**
 * 
 * @author j4ckk0
 *
 */
public class StdOutputFrame extends JInternalFrame {

	private static final long serialVersionUID = 7894578588310528769L;

	private StdOutputTextArea stdOutputTextArea;

	public StdOutputFrame() {
		super("Standard output");

		this.stdOutputTextArea = new StdOutputTextArea(40, 40);

		setIconifiable(true);
		setClosable(true);
		setResizable(true);

		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout(6, 6));

		add(new JScrollPane(stdOutputTextArea));

		pack();
	}

	public void dispose() {
		stdOutputTextArea.dispose();
	}

	public void clear() {
		stdOutputTextArea.clear();
	}

}