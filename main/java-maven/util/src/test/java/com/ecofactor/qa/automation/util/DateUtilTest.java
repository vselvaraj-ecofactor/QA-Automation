/*
 * DateUtilTest.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.util;

import java.util.Calendar;
import java.util.Date;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.testng.annotations.Test;

/**
 * The Class DateUtilTest.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class DateUtilTest {

    /**
     * Gets the uTC date.
     * @return the uTC date
     */
    @Test
    public void getUTCDate() {

        Date date = DateUtil.getUTCDate();
        Assert.assertNotNull(date);
    }

    /**
     * Parses the to date.
     */
    @Test
    public void parseToDate() {

        Date date = DateUtil.parseToUTCDate("2013-03-27 13:05:53 PM +0000", DateUtil.DATE_FMT_FULL_TZ);
        Assert.assertNotNull(date);
    }

    /**
     * Parses the to calendar.
     */
    @Test
    public void parseToCalendar() {

        String timestamp = "2013-03-28 12:15:00 PM";
        Calendar utcCalendar = DateUtil.parseToUTCCalendar(timestamp, DateUtil.DATE_FMT_FULL);
        Assert.assertNotNull(utcCalendar);
        String strCalendar = DateUtil.formatToUTC(utcCalendar, DateUtil.DATE_FMT_FULL);
        Assert.assertEquals(timestamp, strCalendar);
    }

    /**
     * Format date.
     */
    @Test
    public void formatDate() {

        String timestamp = "2013-03-27 13:05:53 PM +0000";
        Date date = DateUtil.parseToUTCDate("2013-03-27 13:05:53 PM +0000", DateUtil.DATE_FMT_FULL_TZ);
        String strDate = DateUtil.formatToUTC(date, DateUtil.DATE_FMT_FULL_TZ);
        Assert.assertEquals(timestamp, strDate);
    }

    /**
     * Gets the uTC day of the week.
     * @return the uTC day of the week
     */
    @Test
    public void getUTCDayOfTheWeek() {

        int val = DateUtil.getUTCDayOfWeek();
        Assert.assertTrue(val != 0 && val <= 7);
    }

    /**
     * Convert date to utc.
     */
    @Test
    public void convertDateToUTC() {

        Date currentDate = new Date();
        DateTime date = new DateTime(currentDate);
        Calendar utcCalendar = DateUtil.convertTimeToUTCCalendar(date);
        Assert.assertTrue(utcCalendar.getTimeZone().getID().equalsIgnoreCase("UTC"));
    }

    /**
     * Gets the time zone calendar.
     * @return the time zone calendar
     */
    @Test
    public void getTimeZoneCalendar() {

        Calendar cal = DateUtil.getCurrentTimeZoneCalendar("America/Los_Angeles");
    }
}
