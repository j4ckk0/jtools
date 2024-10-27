/**
 * 
 */
package com.jtools.utils.dates;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jtools.utils.CommonUtils;

/**
 * @author j4ckk0
 *
 */
public class DateFormatManager {

	public static final String BASIC_DATE_FORMAT = "dd/MM/yyyy";

	private static DateFormatManager instance;

	private final Map<String, SimpleDateFormat> datesFormatsMap;

	private SimpleDateFormat activeDateFormat;

	private DateFormatManager() {
		this.datesFormatsMap = new HashMap<>();

		initDefaults();
	}

	public static DateFormatManager instance() {
		if (instance == null) {
			instance = new DateFormatManager();
		}
		return instance;
	}

	protected void initDefaults() {
		addFormat(BASIC_DATE_FORMAT);
		
		setActiveDateFormat(BASIC_DATE_FORMAT);
	}

	public SimpleDateFormat addFormat(String pattern) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		datesFormatsMap.put(pattern, dateFormat);
		return dateFormat;
	}

	public void removeFormat(String pattern) {
		datesFormatsMap.remove(pattern);

		if (datesFormatsMap.keySet().size() == 0) {
			Logger.getLogger(getClass().getName()).log(Level.WARNING,
					"The last known pattern has been removed. DateFormatManager shall not have no registered pattern. Defaults pattern will be set back");
			initDefaults();
		}
	}

	public SimpleDateFormat getDateFormat(String pattern) {
		return datesFormatsMap.get(pattern);
	}

	public Date parse(String pattern, String source) {

		SimpleDateFormat dateFormat = getDateFormat(pattern);
		if (dateFormat == null) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, "No formatter found for pattern " + pattern);
			return null;
		}

		try {
			return dateFormat.parse(source);
		} catch (ParseException e) {
			Logger.getLogger(getClass().getName()).log(Level.WARNING, e.getMessage());
			Logger.getLogger(getClass().getName()).log(Level.FINE, e.getMessage(), e);
			return null;
		}
	}

	public String format(String pattern, Date date) {

		SimpleDateFormat dateFormat = getDateFormat(pattern);
		if (dateFormat == null) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, "No formatter found for pattern " + pattern);
			return null;
		}

		return dateFormat.format(date);
	}

	public void setActiveDateFormat(String pattern) {
		SimpleDateFormat dateFormat = getDateFormat(pattern);
		if (dateFormat == null) {
			this.activeDateFormat = addFormat(pattern);
		} else {
			this.activeDateFormat = dateFormat;
		}
	}

	public SimpleDateFormat getActiveDateFormat() {
		if (activeDateFormat == null) {
			Logger.getLogger(getClass().getName()).log(Level.WARNING,
					"No active date format. DateFormatManager shall not have no registered pattern. Defaults pattern will be set back");
			initDefaults();
		}
		return activeDateFormat;
	}

	public String getActiveDateFormatPattern() {
		if (activeDateFormat != null) {
			return activeDateFormat.toPattern();
		}
		Logger.getLogger(getClass().getName()).log(Level.WARNING, "No active date format");
		return null;
	}

	public Date parse(String source) {
		SimpleDateFormat dateFormat = getActiveDateFormat();

		try {
			return dateFormat.parse(source);
		} catch (ParseException e) {
			Logger.getLogger(getClass().getName()).log(Level.WARNING, e.getMessage());
			Logger.getLogger(getClass().getName()).log(Level.FINE, e.getMessage(), e);
			return null;
		}
	}

	public String format(Date date) {
		SimpleDateFormat dateFormat = getActiveDateFormat();

		return dateFormat.format(date);
	}

	public String getPattern() {
		SimpleDateFormat dateFormat = getActiveDateFormat();

		return dateFormat.toPattern();
	}

	public String[] getPatternsAsArray() {
		return CommonUtils.stringSetToArray(datesFormatsMap.keySet());
	}

	public List<String> getPatternsAsList() {
		return datesFormatsMap.keySet().stream().toList();
	}

}
