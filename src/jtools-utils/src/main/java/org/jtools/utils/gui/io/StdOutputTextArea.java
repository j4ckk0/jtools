package org.jtools.utils.gui.io;

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

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
// TODO: Auto-generated Javadoc

/**
 * The Class StdOutputTextArea.
 */
public class StdOutputTextArea extends JTextArea {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 514734858253559607L;

	/** The initial out. */
	private final transient PrintStream initialOut;
	
	/** The initial err. */
	private final transient PrintStream initialErr;

	/** The print stream. */
	private final transient PrintStream printStream;

	/**
	 * Instantiates a new std output text area.
	 *
	 * @param rows the rows
	 * @param columns the columns
	 */
	public StdOutputTextArea(int rows, int columns) {
		super(rows, columns);

		setEditable(false);

		this.initialOut = System.out;
		this.initialErr = System.err;

		this.printStream = new PrintStream(new StdOutputTextAreaOutputStream());
	}

	/**
	 * Redirect std output.
	 *
	 * @param enable the enable
	 */
	public void redirectStdOutput(boolean enable) {
		if (enable) {
			System.setOut(printStream);
			System.setErr(printStream);
		} else {
			System.setOut(initialOut);
			System.setErr(initialErr);
		}
	}

	/**
	 * Clear.
	 */
	public void clear() {
		setText("");
		scrollToEnd();
	}

	/**
	 * Dispose.
	 */
	public void dispose() {
		redirectStdOutput(false);
	}

	/**
	 * Scroll to end.
	 */
	private void scrollToEnd() {
		try {
			setCaretPosition(getLineEndOffset(getLineCount() - 1));
		} catch (BadLocationException e) {
			Logger.getLogger(getClass().getName()).log(Level.FINEST, e.getMessage());
		}
	}

	/**
	 * The Class StdOutputTextAreaOutputStream.
	 */
	private class StdOutputTextAreaOutputStream extends OutputStream {

		/**
		 * Write.
		 *
		 * @param b the b
		 * @throws IOException Signals that an I/O exception has occurred.
		 */
		@Override
		public void write(int b) throws IOException {
			// redirects data to the text area
			// textArea.append(String.valueOf((char) b));
			// scrolls the text area to the end of data
			// textArea.setCaretPosition(textArea.getDocument().getLength());
			// keeps the textArea up to date
			// textArea.update(textArea.getGraphics());

			setText(getText() + String.valueOf((char) b));
			scrollToEnd();
		}
	}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		StdOutputTextArea ta = new StdOutputTextArea(100, 100);
		frame.add(ta);

		ta.redirectStdOutput(true);

		frame.pack();
		frame.setVisible(true);

		for (int i = 0; i < 100; i++) {
			System.out.println("Counter is " + i);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		ta.redirectStdOutput(false);

		for (int i = 0; i < 100; i++) {
			System.out.println("Counter is " + i);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

}
