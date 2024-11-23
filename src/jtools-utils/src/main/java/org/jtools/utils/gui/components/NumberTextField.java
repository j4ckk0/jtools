package org.jtools.utils.gui.components;

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

import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;
// TODO: Auto-generated Javadoc

/**
 * The Class NumberTextField.
 *
 * @param <E> the element type
 */
public class NumberTextField<E extends Number> extends JTextField {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -3100227894055002252L;

	/**
	 * Instantiates a new number text field.
	 *
	 * @param numberClass the number class
	 */
	public NumberTextField(Class<E> numberClass) {

		PlainDocument doc = (PlainDocument) getDocument();
		doc.setDocumentFilter(new NumberFilter<>(numberClass));

	}
	
	/**
	 * The Class NumberFilter.
	 *
	 * @param <E> the element type
	 */
	private static class NumberFilter<E extends Number> extends DocumentFilter {

		/** The number class. */
		private final Class<E> numberClass;

		/**
		 * Instantiates a new number filter.
		 *
		 * @param numberClass the number class
		 */
		public NumberFilter(Class<E> numberClass) {
			this.numberClass = numberClass;
		}

		/**
		 * Insert string.
		 *
		 * @param fb the fb
		 * @param offset the offset
		 * @param string the string
		 * @param attr the attr
		 * @throws BadLocationException the bad location exception
		 */
		@Override
		public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
				throws BadLocationException {

			Document doc = fb.getDocument();
			StringBuilder sb = new StringBuilder();
			sb.append(doc.getText(0, doc.getLength()));
			sb.insert(offset, string);

			if (isAccepted(sb.toString())) {
				super.insertString(fb, offset, string, attr);
			} else {
				// warn the user and don't allow the insert
			}
		}

		/**
		 * Checks if is accepted.
		 *
		 * @param text the text
		 * @return true, if is accepted
		 */
		private boolean isAccepted(String text) {
			try {
				if(text == null || text.length() == 0) {
					return true;
				}
				
				if (numberClass == Byte.class) {
					Byte.parseByte(text);
					return true;
				}
				if (numberClass == Short.class) {
					Short.parseShort(text);
					return true;
				}
				if (numberClass == Integer.class) {
					Integer.parseInt(text);
					return true;
				}
				if (numberClass == Double.class) {
					Double.parseDouble(text);
					return true;
				}
				if (numberClass == Float.class) {
					Float.parseFloat(text);
					return true;
				}
				if (numberClass == Long.class) {
					Long.parseLong(text);
					return true;
				}
				return false;
			} catch (NumberFormatException e) {
				return false;
			}
		}

		/**
		 * Replace.
		 *
		 * @param fb the fb
		 * @param offset the offset
		 * @param length the length
		 * @param text the text
		 * @param attrs the attrs
		 * @throws BadLocationException the bad location exception
		 */
		@Override
		public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {

			Document doc = fb.getDocument();
			StringBuilder sb = new StringBuilder();
			sb.append(doc.getText(0, doc.getLength()));
			sb.replace(offset, offset + length, text);

			if (isAccepted(sb.toString())) {
				super.replace(fb, offset, length, text, attrs);
			} else {
				// warn the user and don't allow the insert
			}

		}

		/**
		 * Removes the.
		 *
		 * @param fb the fb
		 * @param offset the offset
		 * @param length the length
		 * @throws BadLocationException the bad location exception
		 */
		@Override
		public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
			Document doc = fb.getDocument();
			StringBuilder sb = new StringBuilder();
			sb.append(doc.getText(0, doc.getLength()));
			sb.delete(offset, offset + length);

			if (isAccepted(sb.toString())) {
				super.remove(fb, offset, length);
			} else {
				// warn the user and don't allow the insert
			}

		}
	}

}
