/**
 * 
 */
package com.jtools.utils.geo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import com.jtools.utils.CommonUtils;

/**
 * 
 * @author j4ckk0
 *
 */
public class CoordinatesFormatManager {

	//
	// LAT/LON General
	//

	public static final String LAT_LON_SEPARATOR_REGEX = "(\\s?[,]\\s?)?";

	public static final String LAT_LON_PLUS_MINUS_REGEX = "([\\+\\-]?)";

	//
	// LAT/LON Degrees, minutes, and seconds (DMS)
	//

	public static final String LAT_LON_DMS_LAT_DEGREES_REGEX = "(?:90|(?:[0-8]{0,1}[0-9]))[°\\s]"; // ex : 40°

	public static final String LAT_LON_DMS_LAT_MINUTES_REGEX = "(?:[0-5]{0,1}[0-9])['\\s]"; // ex : 31'

	public static final String LAT_LON_DMS_LAT_SECONDS_REGEX = "(?:[0-5]{0,1}[0-9](?:[.,]\\d{1,5})?)[\"\\s]{0,1}"; // ex : 21"

	public static final String LAT_LON_DMS_LAT_LETTER_REGEX = "(\\s?[NnSs]\\s?)?";

	public static final String LAT_LON_DMS_LAT_REGEX = LAT_LON_DMS_LAT_LETTER_REGEX + LAT_LON_PLUS_MINUS_REGEX + LAT_LON_DMS_LAT_DEGREES_REGEX + LAT_LON_DMS_LAT_MINUTES_REGEX + LAT_LON_DMS_LAT_SECONDS_REGEX + LAT_LON_DMS_LAT_LETTER_REGEX;


	public static final String LAT_LON_DMS_LON_DEGREES_REGEX = "(?:180|(?:1[0-7]{1}[0-9]{1})|(?:0?[0-9]{1}[0-9]{1})|(?:[0-9]{1}))[°\\s]"; // ex : 105°

	public static final String LAT_LON_DMS_LON_MINUTES_REGEX = "(?:[0-5]{0,1}[0-9])['\\s]"; // ex : 05'

	public static final String LAT_LON_DMS_LON_SECONDS_REGEX = "(?:[0-5]{0,1}[0-9](?:[.,]\\d{1,5})?)[\"\\s]{0,1}"; // ex : 39"

	public static final String LAT_LON_DMS_LON_LETTER_REGEX = "(\\s?[EeWw]\\s?)?";

	public static final String LAT_LON_DMS_LON_REGEX = LAT_LON_DMS_LON_LETTER_REGEX + LAT_LON_PLUS_MINUS_REGEX + LAT_LON_DMS_LON_DEGREES_REGEX + LAT_LON_DMS_LON_MINUTES_REGEX + LAT_LON_DMS_LON_SECONDS_REGEX + LAT_LON_DMS_LON_LETTER_REGEX;


	public static final String LAT_LON_DMS_REGEX = LAT_LON_DMS_LAT_REGEX + LAT_LON_SEPARATOR_REGEX + LAT_LON_DMS_LON_REGEX; // 41°24'12.2"N 2°10'26.5"E - Degrees, minutes, and seconds (DMS)


	//
	// LAT/LON Decimal degrees (DD)
	//

	public static final String LAT_LON_DD_LAT_DECIMAL_DEGREE_REGEX = "(([0-8]?[0-9](\\.\\d+)?|90(.[0]+)?)\\s?)"; // ex : 41.40338

	public static final String LAT_LON_DD_LAT_REGEX = LAT_LON_DMS_LAT_LETTER_REGEX + LAT_LON_PLUS_MINUS_REGEX + LAT_LON_DD_LAT_DECIMAL_DEGREE_REGEX + LAT_LON_DMS_LAT_LETTER_REGEX;


	public static final String LAT_LON_DD_LON_DECIMAL_DEGREE_REGEX = "([1]?[0-7]?[0-9](\\.\\d+)?|180((.[0]+)?))"; // ex : 2.17403

	public static final String LAT_LON_DD_LON_REGEX = LAT_LON_DMS_LON_LETTER_REGEX + LAT_LON_PLUS_MINUS_REGEX + LAT_LON_DD_LON_DECIMAL_DEGREE_REGEX + LAT_LON_DMS_LON_LETTER_REGEX;


	public static final String LAT_LON_DD_REGEX = LAT_LON_DD_LAT_REGEX + LAT_LON_SEPARATOR_REGEX + LAT_LON_DD_LON_REGEX; // 41.40338, 2.17403 - Decimal degrees (DD)


	//
	// LAT/LON Degrees Decimal Minutes (DDM)
	//

	public static final String LAT_LON_DDM_LAT_DEGREE_DECIMAL_MINUTES_REGEX = "[\\+-]?(([1-8]?\\d)\\D+[1-6]?\\d(\\.\\d{1,})?|90(\\D+0)?)\\D+"; 

	public static final String LAT_LON_DDM_LAT_REGEX = LAT_LON_DMS_LAT_LETTER_REGEX + LAT_LON_PLUS_MINUS_REGEX + LAT_LON_DDM_LAT_DEGREE_DECIMAL_MINUTES_REGEX + LAT_LON_DMS_LAT_LETTER_REGEX;


	public static final String LAT_LON_DDM_LON_DEGREE_DECIMAL_MINUTES_REGEX = "[\\+-]?((1[0-7]\\d|[1-9]?\\d)\\D+[1-6]?\\d(\\.\\d{1,})?|180(\\D+0)?)\\D+";

	public static final String LAT_LON_DDM_LON_REGEX = LAT_LON_DMS_LON_LETTER_REGEX + LAT_LON_PLUS_MINUS_REGEX + LAT_LON_DDM_LON_DEGREE_DECIMAL_MINUTES_REGEX + LAT_LON_DMS_LON_LETTER_REGEX;


	public static final String LAT_LON_DDM_REGEX = LAT_LON_DDM_LAT_REGEX + LAT_LON_SEPARATOR_REGEX + LAT_LON_DDM_LON_REGEX; // 40° 26.767′ N    79° 58.933′ W - Degrees Decimal Minutes (DDM)


	//
	// MGRS
	//

	public static final String MGRS_REGEX = "\\d{1,2}(\\s?)[^aboiyzABOIYZ\\d\\[-\\` -@][A-Za-z]{2}(\\s?)([0-9]){5}((\\.\\d{1,3})?)(\\s?)([0-9]){5}((\\.\\d{1,3})?)";


	//
	// UTM
	//

	public static final String UTM_REGEX = "\\d{1,2}(\\s?)[^aboiyzABOIYZ\\d\\[-\\` -@](\\s?)([0-9])+((\\.\\d{1,3})?)(\\s?)([0-9])+((\\.\\d{1,3})?)";




	/**
	 * 
	 * @author j4ckk0
	 *
	 */
	public enum CoordinatesFormat {

		LAT_LON_DMS_LAT("LAT_LON_DMS_LAT", "40°31'21\"", LAT_LON_DMS_LAT_REGEX),
		LAT_LON_DMS_LON("LAT_LON_DMS_LON", "105°05'39\"", LAT_LON_DMS_LON_REGEX),
		LAT_LON_DMS("LAT_LON_DMS", "N 40°31'21\\\" E 105°05'39\"", LAT_LON_DMS_REGEX),
		LAT_LON_DD_LAT("LAT_LON_DD_LAT", "41.40338", LAT_LON_DD_LAT_REGEX),
		LAT_LON_DD_LON("LAT_LON_DD_LON", "2.17403", LAT_LON_DD_LON_REGEX),
		LAT_LON_DD("LAT_LON_DD", "41.40338, 2.17403", LAT_LON_DD_REGEX),
		LAT_LON_DDM_LAT("LAT_LON_DDM_LAT", "40° 26.767′ N", LAT_LON_DDM_LAT_REGEX),
		LAT_LON_DDM_LON("LAT_LON_DDM_LON", "79° 58.933′ W", LAT_LON_DDM_LON_REGEX),
		LAT_LON_DDM("LAT_LON_DDM", "40° 26.767′ N  79° 58.933′ W", LAT_LON_DDM_REGEX),
		MGRS("MGRS", "4QFJ12345678", MGRS_REGEX),
		UTM("UTM", "780950E 2052283N", UTM_REGEX);


		private final String label;
		private final String exemple;
		private final String regex;

		private CoordinatesFormat(String label, String exemple, String regex) {
			this.label = label;
			this.exemple = exemple;
			this.regex = regex;
		}

		public String getLabel() {
			return label;
		}

		public String getExemple() {
			return exemple;
		}

		public String getRegex() {
			return regex;
		}

		public String[] labels() {
			List<String> list = Arrays.stream(values()).map((m) -> m.getLabel()).toList();
			return CommonUtils.stringListToArray(list);
		}

		@Override
		public String toString() {
			return name();
		}

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

	private static CoordinatesFormatManager instance;

	private final List<CoordinatesFormat> coordinatesFormatsList;

	private CoordinatesFormat activeCoordinatesFormat;

	private CoordinatesFormatManager() {
		this.coordinatesFormatsList = new ArrayList<>();
	}

	public static CoordinatesFormatManager instance() {
		if (instance == null) {
			instance = new CoordinatesFormatManager();
		}
		return instance;
	}

	protected void initDefaults() {
		setActiveCoordinatesFormat(CoordinatesFormat.LAT_LON_DMS);
	}

	public CoordinatesFormat getActiveCoordinatesFormat() {
		if (activeCoordinatesFormat == null) {
			Logger.getLogger(getClass().getName()).log(Level.WARNING,
					"No active coordinates format. Define a default pattern");
			initDefaults();
		}

		return activeCoordinatesFormat;
	}

	public void setActiveCoordinatesFormat(CoordinatesFormat activeCoordinateFormat) {
		this.activeCoordinatesFormat = activeCoordinateFormat;
	}

	public List<CoordinatesFormat> getCoordinatesFormatsList() {
		return coordinatesFormatsList;
	}

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

	public static void main(String[] args) {
		int testIndex = 0;

		testIndex++;
		String test = "40°31'21\"";
		System.out.println("test " + testIndex + " is " + test + " : " + findMatching(test));

		testIndex++;
		test = "40 31 21";
		System.out.println("test " + testIndex + " is " + test + " : " + findMatching(test));

		testIndex++;
		test = "N 40°31'21\"";
		System.out.println("test " + testIndex + " is " + test + " : " + findMatching(test));

		testIndex++;
		test = "40°31'21\" N";
		System.out.println("test " + testIndex + " is " + test + " : " + findMatching(test));

		testIndex++;
		test = "N 40 31 21";
		System.out.println("test " + testIndex + " is " + test + " : " + findMatching(test));

		testIndex++;
		test = "40 31 21 N";
		System.out.println("test " + testIndex + " is " + test + " : " + findMatching(test));

		testIndex++;
		test = "105°05'39\"";
		System.out.println("test " + testIndex + " is " + test + " : " + findMatching(test));

		testIndex++;
		test = "105 05 39";
		System.out.println("test " + testIndex + " is " + test + " : " + findMatching(test));

		testIndex++;
		test = "E 105°05'39\"";
		System.out.println("test " + testIndex + " is " + test + " : " + findMatching(test));

		testIndex++;
		test = "105°05'39\" E";
		System.out.println("test " + testIndex + " is " + test + " : " + findMatching(test));

		testIndex++;
		test = "105°05'39\" N";
		System.out.println("test " + testIndex + " is " + test + " : " + findMatching(test));

		testIndex++;
		test = "41°24'12.2\"N 2°10'26.5\"E";
		System.out.println("test " + testIndex + " is " + test + " : " + findMatching(test));

		testIndex++;
		test = "41.40338, 2.17403";
		System.out.println("test " + testIndex + " is " + test + " : " + findMatching(test));

		testIndex++;
		test = "41.40338 2.17403";
		System.out.println("test " + testIndex + " is " + test + " : " + findMatching(test));

		testIndex++;
		test = "43.63871944444445";
		System.out.println("test " + testIndex + " is " + test + " : " + findMatching(test));

		testIndex++;
		test = "N43°38'19.39\"";
		System.out.println("test " + testIndex + " is " + test + " : " + findMatching(test));

		testIndex++;
		test = "43°38'19.39\"N";
		System.out.println("test " + testIndex + " is " + test + " : " + findMatching(test));

		testIndex++;
		test = "43d 38m 19.39s N";
		System.out.println("test " + testIndex + " is " + test + " : " + findMatching(test));

		testIndex++;
		test = "43 38 19.39";
		System.out.println("test " + testIndex + " is " + test + " : " + findMatching(test));

		testIndex++;
		test = "43°38.3232'N";
		System.out.println("test " + testIndex + " is " + test + " : " + findMatching(test));

		testIndex++;
		test = "N41°36.36 E041°36.00\"";
		System.out.println("test " + testIndex + " is " + test + " : " + findMatching(test));

		testIndex++;
		test = "41°36.36 N 041°36.00\" E";
		System.out.println("test " + testIndex + " is " + test + " : " + findMatching(test));

		testIndex++;
		test = "41°24'12.2\"N 2°10'26.5\"E";
		System.out.println("test " + testIndex + " is " + test + " : " + findMatching(test));

		testIndex++;
		test = "N41°36\'21.6\" E041°36\'00\"";
		System.out.println("test " + testIndex + " is " + test + " : " + findMatching(test));

		testIndex++;
		test = "41.606 041.6";
		System.out.println("test " + testIndex + " is " + test + " : " + findMatching(test));

		testIndex++;
		test = "37 T 716658 4609298";
		System.out.println("test " + testIndex + " is " + test + " : " + findMatching(test));

		testIndex++;
		test = "37TGG 16658 09298";
		System.out.println("test " + testIndex + " is " + test + " : " + findMatching(test));

		testIndex++;
		test = "4 Q 6109372363778";
		System.out.println("test " + testIndex + " is " + test + " : " + findMatching(test));

		testIndex++;
		test = "4QFJ1093763778";
		System.out.println("test " + testIndex + " is " + test + " : " + findMatching(test));

		testIndex++;
		test = "N 51 E 10";
		System.out.println("test " + testIndex + " is " + test + " : " + findMatching(test));

		testIndex++;
		test = "N 51° 0.000000 E 10° 0.000000";
		System.out.println("test " + testIndex + " is " + test + " : " + findMatching(test));

		testIndex++;
		test = "N 51° 0' 0 E 10° 0' 0";
		System.out.println("test " + testIndex + " is " + test + " : " + findMatching(test));

		testIndex++;
		test = "32U 570168.862 5650300.787";
		System.out.println("test " + testIndex + " is " + test + " : " + findMatching(test));

		testIndex++;
		test = "32UNB 70168.862 50300.787";
		System.out.println("test " + testIndex + " is " + test + " : " + findMatching(test));

		testIndex++;
		test = "433819N";
		System.out.println("test " + testIndex + " is " + test + " : " + findMatching(test));
	}
}