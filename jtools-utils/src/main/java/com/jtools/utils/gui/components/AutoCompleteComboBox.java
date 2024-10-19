package com.jtools.utils.gui.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * 
 * @author j4ckk0
 *
 */
public class AutoCompleteComboBox extends JComboBox<String> implements JComboBox.KeySelectionManager {

	private static final long serialVersionUID = -790677937456857146L;

	private String searchFor;
	private long lap;

	/**
	 * 
	 */
	public AutoCompleteComboBox() {
		super();
		lap = new java.util.Date().getTime();
		setKeySelectionManager(this);
		JTextField tf;
		if (getEditor() != null) {
			tf = (JTextField) getEditor().getEditorComponent();
			if (tf != null) {
				tf.setDocument(new CBDocument());
				addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						JTextField tf = (JTextField) getEditor().getEditorComponent();
						String text = tf.getText();
						ComboBoxModel<String> aModel = getModel();
						String current;
						for (int i = 0; i < aModel.getSize(); i++) {
							current = aModel.getElementAt(i).toString();
							if (current.toLowerCase().startsWith(text.toLowerCase())) {
								tf.setText(current);
								tf.setSelectionStart(text.length());
								tf.setSelectionEnd(current.length());
								break;
							}
						}
					}
				});
			}
		}
	}

	public void setItems(String[] items) {
		setModel(new DefaultComboBoxModel<String>(items));
	}

	public int selectionForKey(char aKey, ComboBoxModel<?> aModel) {
		long now = new java.util.Date().getTime();
		if (searchFor != null && aKey == KeyEvent.VK_BACK_SPACE && searchFor.length() > 0) {
			searchFor = searchFor.substring(0, searchFor.length() - 1);
		} else {
			if (lap + 1000 < now)
				searchFor = "" + aKey;
			else
				searchFor = searchFor + aKey;
		}
		lap = now;
		String current;
		for (int i = 0; i < aModel.getSize(); i++) {
			current = aModel.getElementAt(i).toString().toLowerCase();
			if (current.toLowerCase().startsWith(searchFor.toLowerCase()))
				return i;
		}
		return -1;
	}

	public void fireActionEvent() {
		super.fireActionEvent();
	}

	/**
	 * 
	 * @author j4ckk0
	 *
	 */
	public class CBDocument extends PlainDocument {
		private static final long serialVersionUID = 7985172243562749588L;

		public void insertString(int offset, String str, AttributeSet a) throws BadLocationException {
			if (str == null)
				return;
			super.insertString(offset, str, a);
			if (!isPopupVisible() && str.length() != 0)
				fireActionEvent();
		}
	}
}