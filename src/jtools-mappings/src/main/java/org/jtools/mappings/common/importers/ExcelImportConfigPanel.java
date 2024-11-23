package org.jtools.mappings.common.importers;

/*-
 * #%L
 * Java Tools - Mappings
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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jtools.utils.CommonUtils;
import org.jtools.utils.gui.components.JLinkButton;
import org.jtools.utils.gui.components.NumberTextField;
// TODO: Auto-generated Javadoc

/**
 * The Class ExcelImportConfigPanel.
 */
public class ExcelImportConfigPanel extends JPanel {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -1666561068364287203L;

	/** The Constant RIGHT_LEFT_INSETS. */
	private static final Insets RIGHT_LEFT_INSETS = new Insets(0, 4, 0, 4);

	/** The mapping label. */
	private final JLabel mappingLabel;
	
	/** The mapping text field. */
	private final JTextField mappingTextField;

	/** The excel file label. */
	private final JLabel excelFileLabel;
	
	/** The excel file text field. */
	private final JTextField excelFileTextField;
	
	/** The excel file selection button. */
	private final JLinkButton excelFileSelectionButton;

	/** The firs data row index label. */
	private final JLabel firsDataRowIndexLabel;
	
	/** The first data row index text field. */
	private final NumberTextField<Integer> firstDataRowIndexTextField;

	/** The input file. */
	private File inputFile;

	/**
	 * Instantiates a new excel import config panel.
	 *
	 * @param objectClass the object class
	 */
	public ExcelImportConfigPanel(Class<?> objectClass) {

		setLayout(new GridBagLayout());

		GridBagConstraints gc = new GridBagConstraints();
		gc.insets = RIGHT_LEFT_INSETS;

		// Mapping
		gc.gridx = 0;
		gc.gridy = 0;
		gc.gridwidth = 1;
		gc.weightx = 0.2;
		gc.anchor = GridBagConstraints.LINE_END;
		mappingLabel = new JLabel("Mapping:");
		add(mappingLabel, gc);

		gc.gridx = 1;
		gc.gridy = 0;
		gc.gridwidth = 1;
		gc.weightx = 0.6;
		gc.anchor = GridBagConstraints.LINE_START;
		mappingTextField = new JTextField();
		mappingTextField.setText(objectClass.getCanonicalName());
		mappingTextField.setEditable(false);
		add(mappingTextField, gc);

		// Excel file selection
		gc.gridx = 0;
		gc.gridy = 1;
		gc.gridwidth = 1;
		gc.weightx = 0.2;
		gc.anchor = GridBagConstraints.LINE_END;
		excelFileLabel = new JLabel("Input Excel file:");
		add(excelFileLabel, gc);

		gc.gridx = 1;
		gc.gridy = 1;
		gc.gridwidth = 1;
		gc.weightx = 0.6;
		gc.anchor = GridBagConstraints.LINE_START;
		excelFileTextField = new JTextField();
		Dimension excelFileTextFieldPreferredSize = excelFileTextField.getPreferredSize();
		excelFileTextFieldPreferredSize.width = 300;
		excelFileTextField.setPreferredSize(excelFileTextFieldPreferredSize);
		excelFileTextField.setEditable(false);
		excelFileTextField.setBackground(Color.WHITE);
		add(excelFileTextField, gc);

		gc.gridx = 2;
		gc.gridy = 1;
		gc.gridwidth = 1;
		gc.weightx = 0.2;
		gc.anchor = GridBagConstraints.LINE_START;
		excelFileSelectionButton = new JLinkButton();
		add(excelFileSelectionButton, gc);
		excelFileSelectionButton.setLinkColor(Color.BLACK);
		excelFileSelectionButton.setLinkBehavior(JLinkButton.NEVER_UNDERLINE);

		// Header index
		gc.gridx = 0;
		gc.gridy = 2;
		gc.gridwidth = 1;
		gc.weightx = 0.2;
		gc.anchor = GridBagConstraints.LINE_END;
		firsDataRowIndexLabel = new JLabel("First data row index (as shown in file):");
		add(firsDataRowIndexLabel, gc);

		gc.gridx = 1;
		gc.gridy = 2;
		gc.gridwidth = 1;
		gc.weightx = 0.8;
		gc.anchor = GridBagConstraints.LINE_START;
		gc.fill = GridBagConstraints.HORIZONTAL;
		firstDataRowIndexTextField = new NumberTextField<>(Integer.class);
		Dimension firstDataRowIndexTextFieldPreferredSize = excelFileTextField.getPreferredSize();
		firstDataRowIndexTextFieldPreferredSize.width = 300;
		excelFileTextField.setPreferredSize(firstDataRowIndexTextFieldPreferredSize);
		add(firstDataRowIndexTextField, gc);

		// select excel file action
		excelFileSelectionButton.setAction(new AbstractAction("...") {

			private static final long serialVersionUID = 5395071812523046429L;

			@Override
			public void actionPerformed(ActionEvent e) {
				inputFile = CommonUtils.chooseFile(JFileChooser.OPEN_DIALOG, new File("."),
						CommonUtils.LOAD_EXCEL_DIALOG_TITLE, CommonUtils.EXCEL_FILES_EXTENSION);

				excelFileTextField.setText(inputFile.getName());
				excelFileTextField.setToolTipText(inputFile.getAbsolutePath());
			}
		});

	}

	/**
	 * Gets the selected file.
	 *
	 * @return the selected file
	 */
	public File getSelectedFile() {
		return inputFile;
	}

	/**
	 * Gets the first data row.
	 *
	 * @return the first data row
	 */
	public int getFirstDataRow() {
		// -1 is because index is starting on 0 and first line in excel is line 1
		return Integer.parseInt(firstDataRowIndexTextField.getText()) - 1 ; 
	}

}
