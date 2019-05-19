package com.xgx.demo.utils;

import java.util.Calendar;
import java.util.Date;

/**
 */

public class DateUtil {

	public static Date getDateFromLong(long time){
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time);
		return calendar.getTime();
	}
}
