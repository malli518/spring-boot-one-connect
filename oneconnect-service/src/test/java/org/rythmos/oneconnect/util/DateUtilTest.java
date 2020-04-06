package org.rythmos.oneconnect.util;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Test;

public class DateUtilTest {

	@Test
	public void getFormattedDateTest() {

		DateUtil dateUtil = new DateUtil();
		Calendar myCalendar = new GregorianCalendar(2019, 00, 26);
		Date myDate = myCalendar.getTime();

		assertEquals(myDate, dateUtil.getFormattedDate("MM/dd/yyyy", "01/26/2019"));

	}

}
