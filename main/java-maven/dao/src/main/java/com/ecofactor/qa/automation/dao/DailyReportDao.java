/*
 * DailyReportDao.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.dao;

import java.util.List;

import com.ecofactor.common.pojo.analytics.AggDailyStatsThermostatWeather;

/**
 * DailyReportDao provides the interface for Daily Reports of location and user.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public interface DailyReportDao {

    /**
     * List by day.
     * @param locationId the location id
     * @param day the day
     * @param month the month
     * @param year the year
     * @return the list
     */
    public List<AggDailyStatsThermostatWeather> listByDayLocation(int locationId, int day, int month, int year);

    /**
     * List by day user.
     * @param userId the user id
     * @param day the day
     * @param month the month
     * @param year the year
     * @return the list
     */
    public List<AggDailyStatsThermostatWeather> listByDayUser(int userId, int day, int month, int year);
}
