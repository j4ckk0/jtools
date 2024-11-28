package org.jtools.utils.gui.editor;

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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import org.jtools.utils.concurrent.NamedCallable;
// TODO: Auto-generated Javadoc

/**
 * The Class AEditor.
 */
public abstract class AEditor extends JPanel {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1830181888153315752L;

	/**
	 * Instantiates a new a editor.
	 */
	protected AEditor() {
		super();
	}

	/**
	 * Save.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	protected abstract void save() throws IOException;

	/**
	 * Gets the editor name.
	 *
	 * @return the editor name
	 */
	protected abstract String getEditorName();

	/**
	 * On window opened.
	 */
	protected void onWindowOpened() {
		// Nothing by default
	}

	/**
	 * On window closed.
	 */
	protected void onWindowClosed() {
		// Nothing by default
	}

	/**
	 * Show editor as dialog.
	 *
	 * @param parentWindow the parent window
	 * @param modal the modal
	 * @param onOK the on OK
	 * @param onCancel the on cancel
	 * @return the j dialog
	 */
	public JDialog showEditorAsDialog(Window parentWindow, boolean modal, NamedCallable<Void> onOK,
			NamedCallable<Void> onCancel) {

		JDialog editorFrame = new JDialog(parentWindow);
		editorFrame.setTitle(getEditorName());
		editorFrame.setPreferredSize(new Dimension(1200, 600));

		Container contentPane = editorFrame.getContentPane();

		buildContentPane(contentPane, onOK, onCancel);

		editorFrame.addWindowListener(new WindowAdapter() {

			@Override
			public void windowOpened(WindowEvent e) {
				onWindowOpened();
			}

			@Override
			public void windowClosed(WindowEvent e) {
				onWindowClosed();
			}
		});

		editorFrame.pack();
		editorFrame.setModal(modal);
		editorFrame.setLocationRelativeTo(parentWindow);
		editorFrame.setVisible(true);
		editorFrame.toFront();

		return editorFrame;
	}

	/**
	 * Show editor as dialog.
	 *
	 * @param parentWindow the parent window
	 * @param modal the modal
	 * @param onOK the on OK
	 * @return the j dialog
	 */
	public JDialog showEditorAsDialog(Window parentWindow, boolean modal, NamedCallable<Void> onOK) {
		return this.showEditorAsDialog(parentWindow, modal, onOK, closeCallable());
	}

	/**
	 * Show editor as dialog.
	 *
	 * @param parentWindow the parent window
	 * @param modal the modal
	 * @return the j dialog
	 */
	public JDialog showEditorAsDialog(Window parentWindow, boolean modal) {
		return this.showEditorAsDialog(parentWindow, modal, saveAndCloseCallable(), closeCallable());
	}

	/**
	 * Show editor as frame.
	 *
	 * @param parentWindow the parent window
	 * @param onOK the on OK
	 * @param onCancel the on cancel
	 * @return the j frame
	 */
	public JFrame showEditorAsFrame(Window parentWindow, NamedCallable<Void> onOK, NamedCallable<Void> onCancel) {

		JFrame editorFrame = new JFrame(getEditorName());
		JFrame.setDefaultLookAndFeelDecorated(true);
		editorFrame.setPreferredSize(new Dimension(1200, 600));

		Container contentPane = editorFrame.getContentPane();

		buildContentPane(contentPane, onOK, onCancel);

		editorFrame.addWindowListener(new WindowAdapter() {

			@Override
			public void windowOpened(WindowEvent e) {
				onWindowOpened();
			}

			@Override
			public void windowClosed(WindowEvent e) {
				onWindowClosed();
			}
		});

		editorFrame.pack();
		editorFrame.setLocationRelativeTo(parentWindow);
		editorFrame.setVisible(true);
		editorFrame.toFront();

		return editorFrame;
	}

	/**
	 * Show editor as frame.
	 *
	 * @param parentWindow the parent window
	 * @param onOK the on OK
	 * @return the j frame
	 */
	public JFrame showEditorAsFrame(Window parentWindow, NamedCallable<Void> onOK) {
		return this.showEditorAsFrame(parentWindow, onOK, closeCallable());
	}

	/**
	 * Show editor as frame.
	 *
	 * @param parentWindow the parent window
	 * @return the j frame
	 */
	public JFrame showEditorAsFrame(Window parentWindow) {
		return this.showEditorAsFrame(parentWindow, saveAndCloseCallable(), closeCallable());
	}

	/**
	 * Show editor as internal frame.
	 *
	 * @param onOK the on OK
	 * @param onCancel the on cancel
	 * @return the j internal frame
	 */
	public JInternalFrame showEditorAsInternalFrame(NamedCallable<Void> onOK, NamedCallable<Void> onCancel) {

		JInternalFrame editorFrame = new JInternalFrame(getEditorName(), true, true);
		editorFrame.setPreferredSize(new Dimension(1200, 600));
		editorFrame.setIconifiable(true);
		editorFrame.setClosable(true);
		editorFrame.setResizable(true);

		Container contentPane = editorFrame.getContentPane();

		buildContentPane(contentPane, onOK, onCancel);

		editorFrame.addInternalFrameListener(new InternalFrameAdapter() {

			@Override
			public void internalFrameOpened(InternalFrameEvent e) {
				onWindowOpened();
			}

			@Override
			public void internalFrameClosed(InternalFrameEvent e) {
				onWindowClosed();
			}
		});

		editorFrame.pack();
		editorFrame.setVisible(true);

		return editorFrame;
	}

	/**
	 * Show editor as internal frame.
	 *
	 * @param onOK the on OK
	 * @return the j internal frame
	 */
	public JInternalFrame showEditorAsInternalFrame(NamedCallable<Void> onOK) {
		return this.showEditorAsInternalFrame(onOK, closeCallable());
	}

	/**
	 * Show editor as internal frame.
	 *
	 * @return the j internal frame
	 */
	public JInternalFrame showEditorAsInternalFrame() {
		return this.showEditorAsInternalFrame(saveCallable(), closeCallable());
	}

	/**
	 * Show editor as option dialog.
	 *
	 * @param parentWindow the parent window
	 * @return the int
	 */
	public int showEditorAsOptionDialog(Window parentWindow) {
		Object[] options = { "Save", "Cancel" };

		int result = JOptionPane.showOptionDialog(parentWindow, this, getEditorName(), JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE, null, options, null);
		if (result == JOptionPane.OK_OPTION) {
			try {
				save();
			} catch (IOException e) {
				Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage());
				Logger.getLogger(getClass().getName()).log(Level.FINE, e.getMessage(), e);
			}
		}

		return result;
	}

	/**
	 * Builds the content pane.
	 *
	 * @param contentPane the content pane
	 * @param onOK the on OK
	 * @param onCancel the on cancel
	 */
	@SuppressWarnings("serial")
	protected void buildContentPane(Container contentPane, NamedCallable<Void> onOK, NamedCallable<Void> onCancel) {
		contentPane.setLayout(new BorderLayout(6, 6));

		contentPane.add(this, BorderLayout.CENTER);

		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		contentPane.add(buttonPanel, BorderLayout.SOUTH);
		buttonPanel.setBorder(new LineBorder(Color.GRAY));

		Action okAction = new AbstractAction(onOK.getName()) {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (onOK != null) {
						onOK.call();
					}
				} catch (Exception ex) {
					Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage());
					Logger.getLogger(getClass().getName()).log(Level.FINE, ex.getMessage(), ex);
				}
			}
		};
		buttonPanel.add(new JButton(okAction));

		Action cancelAction = new AbstractAction(onCancel.getName()) {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (onCancel != null) {
						onCancel.call();
					}
				} catch (Exception ex) {
					Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage());
					Logger.getLogger(getClass().getName()).log(Level.FINE, ex.getMessage(), ex);
				}
			}
		};
		buttonPanel.add(new JButton(cancelAction));
	}

	/**
	 * Close.
	 */
	public void close() {
		JInternalFrame internalFrame = (JInternalFrame) SwingUtilities.getAncestorOfClass(JInternalFrame.class, this);
		if(internalFrame != null) {
			internalFrame.setVisible(false);
			internalFrame.dispose();
			return;
		}

		Window window = (Window) SwingUtilities.getAncestorOfClass(Window.class, this);
		window.setVisible(false);
		window.dispose();
	}

	/**
	 * Close callable.
	 *
	 * @return the named callable
	 */
	private NamedCallable<Void> closeCallable() {
		return new NamedCallable<Void>() {
			@Override
			public Void call() throws Exception {
				close();
				return null;
			}

			@Override
			public String getName() {
				return "Close";
			}
		};
	}

	/**
	 * Save callable.
	 *
	 * @return the named callable
	 */
	private NamedCallable<Void> saveCallable() {
		return new NamedCallable<Void>() {
			@Override
			public Void call() throws Exception {
				save();
				return null;
			}

			@Override
			public String getName() {
				return "Save";
			}
		};
	}

	/**
	 * Save and close callable.
	 *
	 * @return the named callable
	 */
	private NamedCallable<Void> saveAndCloseCallable() {
		return new NamedCallable<Void>() {
			@Override
			public Void call() throws Exception {
				save();
				close();
				return null;
			}

			@Override
			public String getName() {
				return "Save & close";
			}
		};
	}

}
