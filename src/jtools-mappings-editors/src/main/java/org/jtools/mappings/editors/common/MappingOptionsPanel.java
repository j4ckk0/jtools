package org.jtools.mappings.editors.common;

/*-
 * #%L
 * Java Tools - Mappings Editors
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
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ItemEvent;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.jtools.utils.dates.DateFormatManager;
import org.jtools.utils.geo.CoordinatesFormatManager;
import org.jtools.utils.geo.CoordinatesFormatManager.CoordinatesFormat;
import org.jtools.utils.gui.border.MarginTitledBorder;
import org.jtools.utils.gui.components.ExtensibleComboBox;
import org.jtools.utils.resources.ResourcesManager;
public class MappingOptionsPanel extends JPanel {

	private static final long serialVersionUID = 2090830784741546658L;

	private final CoordinatesFormatRenderer renderer = new CoordinatesFormatRenderer();

	protected static final Insets RIGHT_LEFT_INSETS = new Insets(0, 4, 0, 4);

	private static final String DATE_COMBO_BOX_TOOLTIP_FILE = "date-format.html";

	private final JLabel dateFormatLabel;
	private final ExtensibleComboBox dateFormatComboBox;

	private final JLabel coordinatesFormatLabel;
	private final JComboBox<CoordinatesFormat> coordinatesFormatComboBox;

	public MappingOptionsPanel() {

		setLayout(new GridLayout(2, 1, 6, 6));

		setBorder(new MarginTitledBorder("Options", 6, 6, 6, 6));

		// Dates format
		JPanel datesFormatPanel = new JPanel(new BorderLayout(6, 6));
		add(datesFormatPanel);
		dateFormatLabel = new JLabel("Date format:");
		datesFormatPanel.add(dateFormatLabel, BorderLayout.WEST);
		dateFormatComboBox = new ExtensibleComboBox();
		dateFormatComboBox.setItems(DateFormatManager.instance().getPatternsAsArray());
		dateFormatComboBox.setSelectedIndex(0);
		dateFormatComboBox.setEditable(true);
		datesFormatPanel.add(dateFormatComboBox, BorderLayout.CENTER);

		// Coordinates format
		JPanel coordinatesFormatPanel = new JPanel(new BorderLayout(6, 6));
		add(coordinatesFormatPanel);
		coordinatesFormatLabel = new JLabel("Coordinates format:");
		coordinatesFormatPanel.add(coordinatesFormatLabel, BorderLayout.WEST);
		coordinatesFormatComboBox = new JComboBox<>(CoordinatesFormatManager.CoordinatesFormat.values());
		coordinatesFormatComboBox.setRenderer(renderer);
		coordinatesFormatComboBox.setEditable(false);
		coordinatesFormatPanel.add(coordinatesFormatComboBox, BorderLayout.CENTER);

		// Initial values
		dateFormatComboBox.setSelectedItem(DateFormatManager.instance().getActiveDateFormatPattern());
		coordinatesFormatComboBox.setSelectedItem(CoordinatesFormatManager.instance().getActiveCoordinatesFormat());

		// Listeners
		dateFormatComboBox.addItemListener((ItemEvent event) -> DateFormatManager.instance().setActiveDateFormat((String) dateFormatComboBox.getSelectedItem()));
		coordinatesFormatComboBox.addItemListener((ItemEvent event) -> CoordinatesFormatManager.instance().setActiveCoordinatesFormat((CoordinatesFormat) coordinatesFormatComboBox.getSelectedItem()));

		// Tooltips
		dateFormatComboBox.setToolTipText(ResourcesManager.instance().loadFileContent(DATE_COMBO_BOX_TOOLTIP_FILE));
		coordinatesFormatComboBox.setToolTipText(CoordinatesFormat.getTooltipText());

		// Sizes
		Dimension dateFormatLabelPreferredSize = dateFormatLabel.getPreferredSize();
		Dimension coordinatesFormatLabelPreferredSize = coordinatesFormatLabel.getPreferredSize();

		int labelsWidth = Math.max(dateFormatLabelPreferredSize.width, coordinatesFormatLabelPreferredSize.width) + 10;
		int labelsHeight = Math.max(dateFormatLabelPreferredSize.height, coordinatesFormatLabelPreferredSize.height);
		Dimension preferredSize = new Dimension(labelsWidth, labelsHeight);

		dateFormatLabel.setPreferredSize(preferredSize);
		coordinatesFormatLabel.setPreferredSize(preferredSize);

		dateFormatLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		coordinatesFormatLabel.setHorizontalAlignment(SwingConstants.RIGHT);

	}

	public String getDateFormat() {
		return (String) dateFormatComboBox.getSelectedItem();
	}

	public String getCoordinatesFormat() {
		return (String) coordinatesFormatComboBox.getSelectedItem();
	}
	private static class CoordinatesFormatRenderer extends DefaultListCellRenderer {
		private static final long serialVersionUID = 4084496731811108441L;

		@Override
		public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
				boolean cellHasFocus) {
			Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			if (c instanceof JLabel && value instanceof CoordinatesFormat) {
				((JLabel) c).setText(((CoordinatesFormat) value).getLabel());
				((JLabel) c).setToolTipText("Exemple: " + ((CoordinatesFormat) value).getExemple());
			}

			return c;
		}
	}

}