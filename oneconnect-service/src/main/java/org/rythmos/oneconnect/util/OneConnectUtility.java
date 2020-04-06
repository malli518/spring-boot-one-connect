package org.rythmos.oneconnect.util;

import java.lang.reflect.Field;
import java.util.Date;

import org.apache.commons.math3.util.Precision;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class OneConnectUtility {

	public static Logger logger = LoggerFactory.getLogger(OneConnectUtility.class);
	public static final String dateFormat = "MM/dd/yyyy";

	public static boolean isNullOrEmpty(String string) {
		if (null != string && !string.isEmpty())
			return false;
		return true;
	}

	public static boolean isNull(Object object) {
		if (object instanceof String) {
			return isNullOrEmpty((String) object);
		} else if (object != null) {
			return false;
		}
		return true;
	}

	public boolean objectVariablesNullCheck(Object o) {

		for (Field field : o.getClass().getDeclaredFields()) {
			try {
				field.setAccessible(true);
				if (field.get(this) != null) {
					return false;
				}
			} catch (Exception e) {
				System.out.println("objectVariablesNullCheck :: Exception occured in processing ");
			}
		}
		return true;
	}

	public static boolean compareDate(Date date1, Date date2) {
		logger.info("Date 1 :: {} ,Date 2 :: {}", date1, date2);
		int result = date1.compareTo(date2);
		if (result > 0) {
			return true;
		} else if (result < 0) {
			return false;
		} else {
			return true;
		}
	}

	public float round(float number, int scale) {
		return Precision.round(number, scale);
	}

}
