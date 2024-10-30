/**
 * 
 */
package com.jtools.utils.gui.components;

/*-
 * #%L
 * Java Tools - Utils
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

import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;

/**
 * @author j4ckk0
 *
 */
public class NumberTextField<E extends Number> extends JTextField {

	private static final long serialVersionUID = -3100227894055002252L;

	public NumberTextField(Class<E> numberClass) {

		PlainDocument doc = (PlainDocument) getDocument();
		doc.setDocumentFilter(new NumberFilter<>(numberClass));

	}

	/**
	 * 
	 * @author j4ckk0
	 *
	 * @param <E>
	 */
	private static class NumberFilter<E extends Number> extends DocumentFilter {

		private final Class<E> numberClass;

		public NumberFilter(Class<E> numberClass) {
			this.numberClass = numberClass;
		}

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
