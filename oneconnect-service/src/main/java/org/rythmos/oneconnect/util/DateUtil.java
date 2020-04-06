package org.rythmos.oneconnect.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import org.rythmos.oneconnect.constant.OneConnectConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DateUtil {
	private static Logger LOGGER = LoggerFactory.getLogger(DateUtil.class);

	@SuppressWarnings("deprecation")
	public Date AddSixDays(String format, String dateConv) {

		Date date = this.getFormattedDate(format, dateConv);
		date.setDate(date.getDate() + 6);
		return date;
	}

	public Date getFormattedDate(String formatType, String input) {
		SimpleDateFormat parser = null;
		Date date = null;

		if (OneConnectUtility.isNullOrEmpty(formatType)) {
			parser = new SimpleDateFormat(OneConnectConstants.DEFAULT_DATE_FORMAT);
		} else {
			parser = new SimpleDateFormat(formatType);
		}
		// parser.setTimeZone(TimeZone.getTimeZone(
		// OneConnectConstants.DEFAULT_TIME_ZONE));
		try {
			date = parser.parse(input);
			LOGGER.info("Parsed Date: {}", date);
			LOGGER.info("TimeZone :: ", parser.getTimeZone());
			System.out.println("Parsed Date: " + date);
			System.out.println("TimeZone :: " + parser.getTimeZone());
		} catch (Exception e) {
			System.out.println("Invalid Date {}" + date);
		}
		return date;
	}

	public Date getDateFormatter(String formatType, String input, String timeZone) {

		SimpleDateFormat parser = new SimpleDateFormat(formatType);

		parser = new SimpleDateFormat(formatType);
		parser.setTimeZone(TimeZone.getTimeZone(timeZone));
		Date date = null;
		try {
			date = parser.parse(input);
			LOGGER.info("Parsed Date: {}", date);
			LOGGER.info("TimeZone :: ", parser.getTimeZone());
		} catch (Exception e) {
			LOGGER.info("Invalid Date {}", date);
		}

		return date;
	}

	public String getMonthName(String format, String input) {
		DateUtil dateUtil = new DateUtil();
		Date myear = dateUtil.getFormattedDate(format, input);
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat month_date = new SimpleDateFormat("MMMM", Locale.US);
		cal.setTime(myear);
		String month_name = month_date.format(cal.getTime());
//    System.out.println("Month :: " + month_name);  //Mar 2016
		return month_name;
	}

	public String getMonthYear(String dateString) {
		DateUtil dateUtil = new DateUtil();
		Date myear = dateUtil.getFormattedDate(OneConnectConstants.DEFAULT_DATE_FORMAT, dateString);
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat month_date = new SimpleDateFormat("MMMM yyyy", Locale.US);
		cal.setTime(myear);
		String month_year = month_date.format(cal.getTime());
//    System.out.println("Month :: " + month_name);  //Mar 2016
		return month_year;
	}

	
	
	
	public String getFormattedDateString(Date date, String pattern) {
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		String formattedDate = null;
		try {
			formattedDate = formatter.format(date);
			LOGGER.info("Formatted Date : {}", formattedDate);
		} catch (Exception e) {
			LOGGER.info("Invalid Date {}", date);
		}
		return formattedDate;
	}

	public String getDateFormatterWithTimeZone(String formatType, String input, String timezone) {

		SimpleDateFormat parser = new SimpleDateFormat(formatType);

		parser = new SimpleDateFormat(formatType);
		Date date = null;
		try {
			date = parser.parse(input);
			LOGGER.info("Parsed DAte: {}" + date);
		} catch (Exception e) {
			e.printStackTrace();
		}

		parser.setTimeZone(TimeZone.getTimeZone(timezone));
		LOGGER.info(parser.format(date));

		return parser.format(date);
	}

	public long getDaysDifference(Date endDate, Date startDate) {

		long diffInMillies = Math.abs(endDate.getTime() - startDate.getTime());
		long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

		return diff;
	}

}
