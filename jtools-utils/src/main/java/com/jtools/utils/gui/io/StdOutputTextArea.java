/**
 * 
 */
package com.jtools.utils.gui.io;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;

/**
 * @author j4ckk0
 *
 */
public class StdOutputTextArea extends JTextArea {

	private static final long serialVersionUID = 514734858253559607L;

	private final PrintStream initialOut;
	private final PrintStream initialErr;

	private final PrintStream printStream;

	public StdOutputTextArea(int rows, int columns) {
		super(rows, columns);

		setEditable(false);

		this.initialOut = System.out;
		this.initialErr = System.err;

		this.printStream = new PrintStream(new StdOutputTextAreaOutputStream());
	}

	public void redirectStdOutput(boolean enable) {
		if (enable) {
			System.setOut(printStream);
			System.setErr(printStream);
		} else {
			System.setOut(initialOut);
			System.setErr(initialErr);
		}
	}

	public void clear() {
		setText("");
		scrollToEnd();
	}

	public void dispose() {
		redirectStdOutput(false);
	}

	private void scrollToEnd() {
		try {
			setCaretPosition(getLineEndOffset(getLineCount() - 1));
		} catch (BadLocationException e) {
			Logger.getLogger(getClass().getName()).log(Level.FINEST, e.getMessage());
		}
	}

	private class StdOutputTextAreaOutputStream extends OutputStream {

		public StdOutputTextAreaOutputStream() {
		}

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
