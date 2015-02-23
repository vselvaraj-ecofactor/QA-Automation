/*
 * HourlyReportDao.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.dao;

import java.util.List;

import com.ecofactor.qa.automation.pojo.AggHourlyStatsThermostatWeather;


/**
 * HourlyReportDao provides the interface for hourly report dao.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public interface HourlyReportDao {

    /**
     * List by hour.
     * @param locationId the location id
     * @param hour the hour
     * @param day the day
     * @param month the month
     * @param year the year
     * @return the list
     */
    public List<AggHourlyStatsThermostatWeather> listByHourLocation(int locationId, int hour, int day, int month, int year);

    /**
     * List by hour user.
     * @param userId the user id
     * @param hour the hour
     * @param day the day
     * @param month the month
     * @param year the year
     * @return the list
     */
    public List<AggHourlyStatsThermostatWeather> listByHourUser(int userId, int hour, int day, int month, int year);
}
