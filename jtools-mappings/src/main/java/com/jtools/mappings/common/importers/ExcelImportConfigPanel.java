package com.jtools.mappings.common.importers;

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

import com.jtools.utils.CommonUtils;
import com.jtools.utils.gui.components.JLinkButton;
import com.jtools.utils.gui.components.NumberTextField;

/**
 * 
 * @author j4ckk0
 *
 */
public class ExcelImportConfigPanel extends JPanel {

	private static final long serialVersionUID = -1666561068364287203L;

	private static final Insets RIGHT_LEFT_INSETS = new Insets(0, 4, 0, 4);

	private final JLabel mappingLabel;
	private final JTextField mappingTextField;

	private final JLabel excelFileLabel;
	private final JTextField excelFileTextField;
	private final JLinkButton excelFileSelectionButton;

	private final JLabel headerIndexLabel;
	private final NumberTextField<Integer> headerIndexTextField;

	private File inputFile;

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
		headerIndexLabel = new JLabel("Last header row index:");
		add(headerIndexLabel, gc);

		gc.gridx = 1;
		gc.gridy = 2;
		gc.gridwidth = 1;
		gc.weightx = 0.8;
		gc.anchor = GridBagConstraints.LINE_START;
		gc.fill = GridBagConstraints.HORIZONTAL;
		headerIndexTextField = new NumberTextField<>(Integer.class);
		Dimension headerIndexTextFieldPreferredSize = excelFileTextField.getPreferredSize();
		headerIndexTextFieldPreferredSize.width = 300;
		excelFileTextField.setPreferredSize(headerIndexTextFieldPreferredSize);
		add(headerIndexTextField, gc);

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

	public File getSelectedFile() {
		return inputFile;
	}

	public int getLastHeaderRow() {
		return Integer.parseInt(headerIndexTextField.getText());
	}

}