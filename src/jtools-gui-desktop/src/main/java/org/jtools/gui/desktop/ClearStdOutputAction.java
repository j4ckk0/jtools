package org.jtools.gui.desktop;

/*-
 * #%L
 * Java Tools - GUI - Desktop
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

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
// TODO: Auto-generated Javadoc

/**
 * The Class ClearStdOutputAction.
 */
public class ClearStdOutputAction extends AbstractAction {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -915029730697379837L;

	/** The std output frame. */
	private StdOutputFrame stdOutputFrame;

	/**
	 * Instantiates a new clear std output action.
	 *
	 * @param name the name
	 * @param stdOutputFrame the std output frame
	 */
	public ClearStdOutputAction(String name, StdOutputFrame stdOutputFrame) {
		super(name);
		this.stdOutputFrame = stdOutputFrame;
	}

	/**
	 * Action performed.
	 *
	 * @param e the e
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (stdOutputFrame != null) {
			stdOutputFrame.clear();
		}
	}
}
