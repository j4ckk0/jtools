/**
 * 
 */
package com.jtools.utils.gui.editor;

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

import com.jtools.utils.concurrent.NamedCallable;

/**
 * @author j4ckk0
 *
 */
public abstract class AEditor extends JPanel {

	private static final long serialVersionUID = 1830181888153315752L;

	/**
	 * 
	 * @throws IOException
	 */
	protected abstract void save() throws IOException;

	/**
	 * 
	 * @return
	 */
	protected abstract String getEditorName();

	/**
	 * 
	 */
	protected void onWindowOpened() {
		// Nothing by default
	}

	/**
	 * 
	 */
	protected void onWindowClosed() {
		// Nothing by default
	}

	/**
	 * 
	 * @param parentWindow
	 * @param modal
	 * @param onOK
	 * @param onCancel
	 * @return
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
	 * 
	 * @param parentWindow
	 * @param modal
	 * @param onOK
	 * @return
	 */
	public JDialog showEditorAsDialog(Window parentWindow, boolean modal, NamedCallable<Void> onOK) {
		return this.showEditorAsDialog(parentWindow, modal, onOK, closeCallable());
	}

	/**
	 * 
	 * @param parentWindow
	 * @param modal
	 * @return
	 */
	public JDialog showEditorAsDialog(Window parentWindow, boolean modal) {
		return this.showEditorAsDialog(parentWindow, modal, saveAndCloseCallable(), closeCallable());
	}

	/**
	 * 
	 * @param parentWindow
	 * @param onOK
	 * @param onCancel
	 * @return
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
	 * 
	 * @param parentWindow
	 * @param onOK
	 * @return
	 */
	public JFrame showEditorAsFrame(Window parentWindow, NamedCallable<Void> onOK) {
		return this.showEditorAsFrame(parentWindow, onOK, closeCallable());
	}

	/**
	 * 
	 * @param parentWindow
	 * @return
	 */
	public JFrame showEditorAsFrame(Window parentWindow) {
		return this.showEditorAsFrame(parentWindow, saveAndCloseCallable(), closeCallable());
	}

	/**
	 * 
	 * @param onOK
	 * @param onCancel
	 * @return
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
	 * 
	 * @param onOK
	 * @return
	 */
	public JInternalFrame showEditorAsInternalFrame(NamedCallable<Void> onOK) {
		return this.showEditorAsInternalFrame(onOK, closeCallable());
	}

	/**
	 * 
	 * @return
	 */
	public JInternalFrame showEditorAsInternalFrame() {
		return this.showEditorAsInternalFrame(saveCallable(), closeCallable());
	}

	/**
	 * 
	 * @param parentWindow
	 * @return
	 */
	public int showEditorAsOptionDialog(Window parentWindow) {
		Object[] options = { "Save", "Cancel" };

		int result = JOptionPane.showOptionDialog(parentWindow, this, getEditorName(), JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE, null, options, null);
		if (result == JOptionPane.OK_OPTION) {
			try {
				save();
			} catch (IOException e) {
				Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
			}
		}

		return result;
	}

	/**
	 * 
	 * @param contentPane
	 * @param onOK
	 * @param onCancel
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
					Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
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
					Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
				}
			}
		};
		buttonPanel.add(new JButton(cancelAction));
	}

	/**
	 * 
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
	 * 
	 * @return
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
	 * 
	 * @return
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
	 * 
	 * @return
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
