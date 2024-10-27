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
	private JScrollPane stdOutputTextAreaScrollPane;

	public StdOutputFrame() {
		super("Standard output");

		setIconifiable(true);
		setClosable(true);
		setResizable(true);

		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout(6, 6));

		pack();
	}

	@Override
	public void setVisible(boolean visible) {

		if (visible && stdOutputTextArea == null) {
			this.stdOutputTextArea = new StdOutputTextArea(40, 40);
			this.stdOutputTextAreaScrollPane = new JScrollPane(stdOutputTextArea);
			add(stdOutputTextAreaScrollPane);

			pack();

			stdOutputTextArea.redirectStdOutput(visible);
		}

		if (!visible && stdOutputTextArea != null) {
			stdOutputTextArea.redirectStdOutput(visible);

			remove(stdOutputTextAreaScrollPane);

			stdOutputTextAreaScrollPane = null;

			stdOutputTextArea.dispose();
			stdOutputTextArea = null;
		}

		super.setVisible(visible);
	}

	@Override
	public void dispose() {
		if (stdOutputTextArea != null) {
			stdOutputTextArea.dispose();
		}
	}

	public void clear() {
		if (stdOutputTextArea != null) {
			stdOutputTextArea.clear();
		}
	}

}