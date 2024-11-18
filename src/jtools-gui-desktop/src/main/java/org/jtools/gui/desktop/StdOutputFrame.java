package org.jtools.gui.desktop;

/*-
 * #%L
 * Java Tools - GUI - Desktop
 * %%
 * Copyright (C) 2024 j4ckk0
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

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;

import org.jtools.utils.gui.io.StdOutputTextArea;

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
