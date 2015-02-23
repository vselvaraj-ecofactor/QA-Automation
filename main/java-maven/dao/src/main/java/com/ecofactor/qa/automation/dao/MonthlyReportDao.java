/*
 * MonthlyReportDao.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.dao;

import java.util.List;

import com.ecofactor.common.pojo.analytics.AggMonthlyStatsThermostatWeather;

/**
 * MonthlyReportDao provides the interface for monthly reports of location and user.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public interface MonthlyReportDao {

    /**
     * List by month.
     * @param locationId the location id
     * @param month the month
     * @param year the year
     * @return the list
     */
    public List<AggMonthlyStatsThermostatWeather> listByMonthLocation(int locationId, int month, int year);

    /**
     * List by month user.
     * @param userId the user id
     * @param month the month
     * @param year the year
     * @return the list
     */
    public List<AggMonthlyStatsThermostatWeather> listByMonthUser(int userId, int month, int year);
}
