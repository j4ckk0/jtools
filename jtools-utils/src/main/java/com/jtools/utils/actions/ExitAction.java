package com.jtools.utils.actions;

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

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Icon;

/**
 * 
 * @author j4ckk0
 *
 */
public class ExitAction extends AbstractAction {

	private static final long serialVersionUID = -8034632895201890192L;

	public ExitAction(String name, Icon icon) {
		super(name, icon);
	}

	public ExitAction(String name) {
		super(name);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.exit(0);
	}
}
