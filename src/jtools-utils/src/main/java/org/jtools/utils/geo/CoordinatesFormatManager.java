package org.jtools.utils.geo;

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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import org.jtools.utils.CommonUtils;
// TODO: Auto-generated Javadoc

/**
 * The Class CoordinatesFormatManager.
 */
public class CoordinatesFormatManager {

	//
	// LAT/LON General
	//

	/** The Constant LAT_LON_SEPARATOR_REGEX. */
	public static final String LAT_LON_SEPARATOR_REGEX = "(\\s?[,]\\s?)?";

	/** The Constant LAT_LON_PLUS_MINUS_REGEX. */
	public static final String LAT_LON_PLUS_MINUS_REGEX = "([\\+\\-]?)";

	//
	// LAT/LON Degrees, minutes, and seconds (DMS)
	//

	/** The Constant LAT_LON_DMS_LAT_DEGREES_REGEX. */
	public static final String LAT_LON_DMS_LAT_DEGREES_REGEX = "(?:90|(?:[0-8]{0,1}[0-9]))[°\\s]"; // ex : 40°

	/** The Constant LAT_LON_DMS_LAT_MINUTES_REGEX. */
	public static final String LAT_LON_DMS_LAT_MINUTES_REGEX = "(?:[0-5]{0,1}[0-9])['\\s]"; // ex : 31'

	/** The Constant LAT_LON_DMS_LAT_SECONDS_REGEX. */
	public static final String LAT_LON_DMS_LAT_SECONDS_REGEX = "(?:[0-5]{0,1}[0-9](?:[.,]\\d{1,5})?)[\"\\s]{0,1}"; // ex : 21"

	/** The Constant LAT_LON_DMS_LAT_LETTER_REGEX. */
	public static final String LAT_LON_DMS_LAT_LETTER_REGEX = "(\\s?[NnSs]\\s?)?";

	/** The Constant LAT_LON_DMS_LAT_REGEX. */
	public static final String LAT_LON_DMS_LAT_REGEX = LAT_LON_DMS_LAT_LETTER_REGEX + LAT_LON_PLUS_MINUS_REGEX + LAT_LON_DMS_LAT_DEGREES_REGEX + LAT_LON_DMS_LAT_MINUTES_REGEX + LAT_LON_DMS_LAT_SECONDS_REGEX + LAT_LON_DMS_LAT_LETTER_REGEX;


	/** The Constant LAT_LON_DMS_LON_DEGREES_REGEX. */
	public static final String LAT_LON_DMS_LON_DEGREES_REGEX = "(?:180|(?:1[0-7]{1}[0-9]{1})|(?:0?[0-9]{1}[0-9]{1})|(?:[0-9]{1}))[°\\s]"; // ex : 105°

	/** The Constant LAT_LON_DMS_LON_MINUTES_REGEX. */
	public static final String LAT_LON_DMS_LON_MINUTES_REGEX = "(?:[0-5]{0,1}[0-9])['\\s]"; // ex : 05'

	/** The Constant LAT_LON_DMS_LON_SECONDS_REGEX. */
	public static final String LAT_LON_DMS_LON_SECONDS_REGEX = "(?:[0-5]{0,1}[0-9](?:[.,]\\d{1,5})?)[\"\\s]{0,1}"; // ex : 39"

	/** The Constant LAT_LON_DMS_LON_LETTER_REGEX. */
	public static final String LAT_LON_DMS_LON_LETTER_REGEX = "(\\s?[EeWw]\\s?)?";

	/** The Constant LAT_LON_DMS_LON_REGEX. */
	public static final String LAT_LON_DMS_LON_REGEX = LAT_LON_DMS_LON_LETTER_REGEX + LAT_LON_PLUS_MINUS_REGEX + LAT_LON_DMS_LON_DEGREES_REGEX + LAT_LON_DMS_LON_MINUTES_REGEX + LAT_LON_DMS_LON_SECONDS_REGEX + LAT_LON_DMS_LON_LETTER_REGEX;


	/** The Constant LAT_LON_DMS_REGEX. */
	public static final String LAT_LON_DMS_REGEX = LAT_LON_DMS_LAT_REGEX + LAT_LON_SEPARATOR_REGEX + LAT_LON_DMS_LON_REGEX; // 41°24'12.2"N 2°10'26.5"E - Degrees, minutes, and seconds (DMS)


	//
	// LAT/LON Decimal degrees (DD)
	//

	/** The Constant LAT_LON_DD_LAT_DECIMAL_DEGREE_REGEX. */
	public static final String LAT_LON_DD_LAT_DECIMAL_DEGREE_REGEX = "(([0-8]?[0-9](\\.\\d+)?|90(.[0]+)?)\\s?)"; // ex : 41.40338

	/** The Constant LAT_LON_DD_LAT_REGEX. */
	public static final String LAT_LON_DD_LAT_REGEX = LAT_LON_DMS_LAT_LETTER_REGEX + LAT_LON_PLUS_MINUS_REGEX + LAT_LON_DD_LAT_DECIMAL_DEGREE_REGEX + LAT_LON_DMS_LAT_LETTER_REGEX;


	/** The Constant LAT_LON_DD_LON_DECIMAL_DEGREE_REGEX. */
	public static final String LAT_LON_DD_LON_DECIMAL_DEGREE_REGEX = "([1]?[0-7]?[0-9](\\.\\d+)?|180((.[0]+)?))"; // ex : 2.17403

	/** The Constant LAT_LON_DD_LON_REGEX. */
	public static final String LAT_LON_DD_LON_REGEX = LAT_LON_DMS_LON_LETTER_REGEX + LAT_LON_PLUS_MINUS_REGEX + LAT_LON_DD_LON_DECIMAL_DEGREE_REGEX + LAT_LON_DMS_LON_LETTER_REGEX;


	/** The Constant LAT_LON_DD_REGEX. */
	public static final String LAT_LON_DD_REGEX = LAT_LON_DD_LAT_REGEX + LAT_LON_SEPARATOR_REGEX + LAT_LON_DD_LON_REGEX; // 41.40338, 2.17403 - Decimal degrees (DD)


	//
	// LAT/LON Degrees Decimal Minutes (DDM)
	//

	/** The Constant LAT_LON_DDM_LAT_DEGREE_DECIMAL_MINUTES_REGEX. */
	public static final String LAT_LON_DDM_LAT_DEGREE_DECIMAL_MINUTES_REGEX = "[\\+-]?(([1-8]?\\d)\\D+[1-6]?\\d(\\.\\d{1,})?|90(\\D+0)?)\\D+"; 

	/** The Constant LAT_LON_DDM_LAT_REGEX. */
	public static final String LAT_LON_DDM_LAT_REGEX = LAT_LON_DMS_LAT_LETTER_REGEX + LAT_LON_PLUS_MINUS_REGEX + LAT_LON_DDM_LAT_DEGREE_DECIMAL_MINUTES_REGEX + LAT_LON_DMS_LAT_LETTER_REGEX;


	/** The Constant LAT_LON_DDM_LON_DEGREE_DECIMAL_MINUTES_REGEX. */
	public static final String LAT_LON_DDM_LON_DEGREE_DECIMAL_MINUTES_REGEX = "[\\+-]?((1[0-7]\\d|[1-9]?\\d)\\D+[1-6]?\\d(\\.\\d{1,})?|180(\\D+0)?)\\D+";

	/** The Constant LAT_LON_DDM_LON_REGEX. */
	public static final String LAT_LON_DDM_LON_REGEX = LAT_LON_DMS_LON_LETTER_REGEX + LAT_LON_PLUS_MINUS_REGEX + LAT_LON_DDM_LON_DEGREE_DECIMAL_MINUTES_REGEX + LAT_LON_DMS_LON_LETTER_REGEX;


	/** The Constant LAT_LON_DDM_REGEX. */
	public static final String LAT_LON_DDM_REGEX = LAT_LON_DDM_LAT_REGEX + LAT_LON_SEPARATOR_REGEX + LAT_LON_DDM_LON_REGEX; // 40° 26.767′ N    79° 58.933′ W - Degrees Decimal Minutes (DDM)


	//
	// MGRS
	//

	/** The Constant MGRS_REGEX. */
	public static final String MGRS_REGEX = "\\d{1,2}(\\s?)[^aboiyzABOIYZ\\d\\[-\\` -@][A-Za-z]{2}(\\s?)([0-9]){5}((\\.\\d{1,3})?)(\\s?)([0-9]){5}((\\.\\d{1,3})?)";


	//
	// UTM
	//

	/** The Constant UTM_REGEX. */
	public static final String UTM_REGEX = "\\d{1,2}(\\s?)[^aboiyzABOIYZ\\d\\[-\\` -@](\\s?)([0-9])+((\\.\\d{1,3})?)(\\s?)([0-9])+((\\.\\d{1,3})?)";



	/**
	 * The Enum CoordinatesFormat.
	 */
	public enum CoordinatesFormat {

		/** The lat lon dms lat. */
		LAT_LON_DMS_LAT("LAT_LON_DMS_LAT", "40°31'21\"", LAT_LON_DMS_LAT_REGEX),
		
		/** The lat lon dms lon. */
		LAT_LON_DMS_LON("LAT_LON_DMS_LON", "105°05'39\"", LAT_LON_DMS_LON_REGEX),
		
		/** The lat lon dms. */
		LAT_LON_DMS("LAT_LON_DMS", "N 40°31'21\\\" E 105°05'39\"", LAT_LON_DMS_REGEX),
		
		/** The lat lon dd lat. */
		LAT_LON_DD_LAT("LAT_LON_DD_LAT", "41.40338", LAT_LON_DD_LAT_REGEX),
		
		/** The lat lon dd lon. */
		LAT_LON_DD_LON("LAT_LON_DD_LON", "2.17403", LAT_LON_DD_LON_REGEX),
		
		/** The lat lon dd. */
		LAT_LON_DD("LAT_LON_DD", "41.40338, 2.17403", LAT_LON_DD_REGEX),
		
		/** The lat lon ddm lat. */
		LAT_LON_DDM_LAT("LAT_LON_DDM_LAT", "40° 26.767′ N", LAT_LON_DDM_LAT_REGEX),
		
		/** The lat lon ddm lon. */
		LAT_LON_DDM_LON("LAT_LON_DDM_LON", "79° 58.933′ W", LAT_LON_DDM_LON_REGEX),
		
		/** The lat lon ddm. */
		LAT_LON_DDM("LAT_LON_DDM", "40° 26.767′ N  79° 58.933′ W", LAT_LON_DDM_REGEX),
		
		/** The mgrs. */
		MGRS("MGRS", "4QFJ12345678", MGRS_REGEX),
		
		/** The utm. */
		UTM("UTM", "780950E 2052283N", UTM_REGEX);


		/** The label. */
		private final String label;
		
		/** The exemple. */
		private final String exemple;
		
		/** The regex. */
		private final String regex;

		/**
		 * Instantiates a new coordinates format.
		 *
		 * @param label the label
		 * @param exemple the exemple
		 * @param regex the regex
		 */
		private CoordinatesFormat(String label, String exemple, String regex) {
			this.label = label;
			this.exemple = exemple;
			this.regex = regex;
		}

		/**
		 * Gets the label.
		 *
		 * @return the label
		 */
		public String getLabel() {
			return label;
		}

		/**
		 * Gets the exemple.
		 *
		 * @return the exemple
		 */
		public String getExemple() {
			return exemple;
		}

		/**
		 * Gets the regex.
		 *
		 * @return the regex
		 */
		public String getRegex() {
			return regex;
		}

		/**
		 * Labels.
		 *
		 * @return the string[]
		 */
		public String[] labels() {
			List<String> list = Arrays.stream(values()).map(CoordinatesFormat::getLabel).toList();
			return CommonUtils.stringListToArray(list);
		}

		/**
		 * To string.
		 *
		 * @return the string
		 */
		@Override
		public String toString() {
			return name();
		}

		/**
		 * Gets the tooltip text.
		 *
		 * @return the tooltip text
		 */
		public static String getTooltipText() {
			StringBuilder sb = new StringBuilder();
			sb.append("<html>");
			sb.append("<table>");
			sb.append("<thead>");
			sb.append("<tr>");
			sb.append("<th scope='col'>Label</th>");
			sb.append("<th scope='col'>Example</th>");
			sb.append("</tr>");
			sb.append("</thead>");
			sb.append("<tbody>");

			for(CoordinatesFormat format : values()) {
				sb.append("<tr>");
				sb.append("<td><b>" + format.getLabel() + "</b></td>");
				sb.append("<td>" + format.getExemple() + "</td>");
				sb.append("</tr>");
			}

			sb.append("</tbody>");
			sb.append("</table>");
			sb.append("</html>");

			return sb.toString();
		}
	}

	/** The instance. */
	private static CoordinatesFormatManager instance;

	/** The coordinates formats list. */
	private final List<CoordinatesFormat> coordinatesFormatsList;

	/** The active coordinates format. */
	private CoordinatesFormat activeCoordinatesFormat;

	/**
	 * Instantiates a new coordinates format manager.
	 */
	private CoordinatesFormatManager() {
		this.coordinatesFormatsList = new ArrayList<>();
	}

	/**
	 * Instance.
	 *
	 * @return the coordinates format manager
	 */
	public static CoordinatesFormatManager instance() {
		if (instance == null) {
			instance = new CoordinatesFormatManager();
		}
		return instance;
	}

	/**
	 * Inits the defaults.
	 */
	protected void initDefaults() {
		setActiveCoordinatesFormat(CoordinatesFormat.LAT_LON_DMS);
	}

	/**
	 * Gets the active coordinates format.
	 *
	 * @return the active coordinates format
	 */
	public CoordinatesFormat getActiveCoordinatesFormat() {
		if (activeCoordinatesFormat == null) {
			Logger.getLogger(getClass().getName()).log(Level.WARNING,
					"No active coordinates format. Define a default pattern");
			initDefaults();
		}

		return activeCoordinatesFormat;
	}

	/**
	 * Sets the active coordinates format.
	 *
	 * @param activeCoordinateFormat the new active coordinates format
	 */
	public void setActiveCoordinatesFormat(CoordinatesFormat activeCoordinateFormat) {
		this.activeCoordinatesFormat = activeCoordinateFormat;
	}

	/**
	 * Gets the coordinates formats list.
	 *
	 * @return the coordinates formats list
	 */
	public List<CoordinatesFormat> getCoordinatesFormatsList() {
		return coordinatesFormatsList;
	}

	/**
	 * Find matching.
	 *
	 * @param coordinates the coordinates
	 * @return the coordinates format
	 */
	public static CoordinatesFormat findMatching(String coordinates) {
		for (CoordinatesFormat format : CoordinatesFormat.values()) {
			Pattern.compile(format.getRegex()).matcher(coordinates);
			//boolean find = m.find();
			boolean fullMatch = coordinates.matches(format.getRegex());
			if (fullMatch) {
				return format;
			}
		}
		return null;
	}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		int testIndex = 0;

		testIndex++;
		String test = "40°31'21\"";
		Logger.getLogger(CoordinatesFormatManager.class.getName()).log(Level.INFO, "test " + testIndex + " is " + test + " : " + findMatching(test));

		testIndex++;
		test = "40 31 21";
		Logger.getLogger(CoordinatesFormatManager.class.getName()).log(Level.INFO, "test " + testIndex + " is " + test + " : " + findMatching(test));

		testIndex++;
		test = "N 40°31'21\"";
		Logger.getLogger(CoordinatesFormatManager.class.getName()).log(Level.INFO, "test " + testIndex + " is " + test + " : " + findMatching(test));

		testIndex++;
		test = "40°31'21\" N";
		Logger.getLogger(CoordinatesFormatManager.class.getName()).log(Level.INFO, "test " + testIndex + " is " + test + " : " + findMatching(test));

		testIndex++;
		test = "N 40 31 21";
		Logger.getLogger(CoordinatesFormatManager.class.getName()).log(Level.INFO, "test " + testIndex + " is " + test + " : " + findMatching(test));

		testIndex++;
		test = "40 31 21 N";
		Logger.getLogger(CoordinatesFormatManager.class.getName()).log(Level.INFO, "test " + testIndex + " is " + test + " : " + findMatching(test));

		testIndex++;
		test = "105°05'39\"";
		Logger.getLogger(CoordinatesFormatManager.class.getName()).log(Level.INFO, "test " + testIndex + " is " + test + " : " + findMatching(test));

		testIndex++;
		test = "105 05 39";
		Logger.getLogger(CoordinatesFormatManager.class.getName()).log(Level.INFO, "test " + testIndex + " is " + test + " : " + findMatching(test));

		testIndex++;
		test = "E 105°05'39\"";
		Logger.getLogger(CoordinatesFormatManager.class.getName()).log(Level.INFO, "test " + testIndex + " is " + test + " : " + findMatching(test));

		testIndex++;
		test = "105°05'39\" E";
		Logger.getLogger(CoordinatesFormatManager.class.getName()).log(Level.INFO, "test " + testIndex + " is " + test + " : " + findMatching(test));

		testIndex++;
		test = "105°05'39\" N";
		Logger.getLogger(CoordinatesFormatManager.class.getName()).log(Level.INFO, "test " + testIndex + " is " + test + " : " + findMatching(test));

		testIndex++;
		test = "41°24'12.2\"N 2°10'26.5\"E";
		Logger.getLogger(CoordinatesFormatManager.class.getName()).log(Level.INFO, "test " + testIndex + " is " + test + " : " + findMatching(test));

		testIndex++;
		test = "41.40338, 2.17403";
		Logger.getLogger(CoordinatesFormatManager.class.getName()).log(Level.INFO, "test " + testIndex + " is " + test + " : " + findMatching(test));

		testIndex++;
		test = "41.40338 2.17403";
		Logger.getLogger(CoordinatesFormatManager.class.getName()).log(Level.INFO, "test " + testIndex + " is " + test + " : " + findMatching(test));

		testIndex++;
		test = "43.63871944444445";
		Logger.getLogger(CoordinatesFormatManager.class.getName()).log(Level.INFO, "test " + testIndex + " is " + test + " : " + findMatching(test));

		testIndex++;
		test = "N43°38'19.39\"";
		Logger.getLogger(CoordinatesFormatManager.class.getName()).log(Level.INFO, "test " + testIndex + " is " + test + " : " + findMatching(test));

		testIndex++;
		test = "43°38'19.39\"N";
		Logger.getLogger(CoordinatesFormatManager.class.getName()).log(Level.INFO, "test " + testIndex + " is " + test + " : " + findMatching(test));

		testIndex++;
		test = "43d 38m 19.39s N";
		Logger.getLogger(CoordinatesFormatManager.class.getName()).log(Level.INFO, "test " + testIndex + " is " + test + " : " + findMatching(test));

		testIndex++;
		test = "43 38 19.39";
		Logger.getLogger(CoordinatesFormatManager.class.getName()).log(Level.INFO, "test " + testIndex + " is " + test + " : " + findMatching(test));

		testIndex++;
		test = "43°38.3232'N";
		Logger.getLogger(CoordinatesFormatManager.class.getName()).log(Level.INFO, "test " + testIndex + " is " + test + " : " + findMatching(test));

		testIndex++;
		test = "N41°36.36 E041°36.00\"";
		Logger.getLogger(CoordinatesFormatManager.class.getName()).log(Level.INFO, "test " + testIndex + " is " + test + " : " + findMatching(test));

		testIndex++;
		test = "41°36.36 N 041°36.00\" E";
		Logger.getLogger(CoordinatesFormatManager.class.getName()).log(Level.INFO, "test " + testIndex + " is " + test + " : " + findMatching(test));

		testIndex++;
		test = "41°24'12.2\"N 2°10'26.5\"E";
		Logger.getLogger(CoordinatesFormatManager.class.getName()).log(Level.INFO, "test " + testIndex + " is " + test + " : " + findMatching(test));

		testIndex++;
		test = "N41°36\'21.6\" E041°36\'00\"";
		Logger.getLogger(CoordinatesFormatManager.class.getName()).log(Level.INFO, "test " + testIndex + " is " + test + " : " + findMatching(test));

		testIndex++;
		test = "41.606 041.6";
		Logger.getLogger(CoordinatesFormatManager.class.getName()).log(Level.INFO, "test " + testIndex + " is " + test + " : " + findMatching(test));

		testIndex++;
		test = "37 T 716658 4609298";
		Logger.getLogger(CoordinatesFormatManager.class.getName()).log(Level.INFO, "test " + testIndex + " is " + test + " : " + findMatching(test));

		testIndex++;
		test = "37TGG 16658 09298";
		Logger.getLogger(CoordinatesFormatManager.class.getName()).log(Level.INFO, "test " + testIndex + " is " + test + " : " + findMatching(test));

		testIndex++;
		test = "4 Q 6109372363778";
		Logger.getLogger(CoordinatesFormatManager.class.getName()).log(Level.INFO, "test " + testIndex + " is " + test + " : " + findMatching(test));

		testIndex++;
		test = "4QFJ1093763778";
		Logger.getLogger(CoordinatesFormatManager.class.getName()).log(Level.INFO, "test " + testIndex + " is " + test + " : " + findMatching(test));

		testIndex++;
		test = "N 51 E 10";
		Logger.getLogger(CoordinatesFormatManager.class.getName()).log(Level.INFO, "test " + testIndex + " is " + test + " : " + findMatching(test));

		testIndex++;
		test = "N 51° 0.000000 E 10° 0.000000";
		Logger.getLogger(CoordinatesFormatManager.class.getName()).log(Level.INFO, "test " + testIndex + " is " + test + " : " + findMatching(test));

		testIndex++;
		test = "N 51° 0' 0 E 10° 0' 0";
		Logger.getLogger(CoordinatesFormatManager.class.getName()).log(Level.INFO, "test " + testIndex + " is " + test + " : " + findMatching(test));

		testIndex++;
		test = "32U 570168.862 5650300.787";
		Logger.getLogger(CoordinatesFormatManager.class.getName()).log(Level.INFO, "test " + testIndex + " is " + test + " : " + findMatching(test));

		testIndex++;
		test = "32UNB 70168.862 50300.787";
		Logger.getLogger(CoordinatesFormatManager.class.getName()).log(Level.INFO, "test " + testIndex + " is " + test + " : " + findMatching(test));

		testIndex++;
		test = "433819N";
		Logger.getLogger(CoordinatesFormatManager.class.getName()).log(Level.INFO, "test " + testIndex + " is " + test + " : " + findMatching(test));
	}
}
