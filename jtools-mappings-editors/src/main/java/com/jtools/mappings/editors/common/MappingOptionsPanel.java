/**
 * 
 */
package com.jtools.mappings.editors.common;

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

import com.jtools.utils.dates.DateFormatManager;
import com.jtools.utils.geo.CoordinatesFormatManager;
import com.jtools.utils.geo.CoordinatesFormatManager.CoordinatesFormat;
import com.jtools.utils.gui.border.MarginTitledBorder;
import com.jtools.utils.gui.components.ExtensibleComboBox;
import com.jtools.utils.resources.ResourcesManager;

/**
 * @author j4ckk0
 *
 */
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

		// Listeners
		dateFormatComboBox.addItemListener((ItemEvent event) -> DateFormatManager.instance().setActiveDateFormat((String) dateFormatComboBox.getSelectedItem()));

		// Tooltips
		dateFormatComboBox.setToolTipText(ResourcesManager.instance().loadFileContent(DATE_COMBO_BOX_TOOLTIP_FILE));

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

	/**
	 * 
	 * @author j4ckk0
	 *
	 */
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
