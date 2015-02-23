/*
 * DailyReportDaoImpl.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ecofactor.common.pojo.analytics.AggDailyStatsThermostatWeather;

/**
 * DailyReportDaoImpl implements the DailyReportDao.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class DailyReportDaoImpl extends BaseDaoImpl<AggDailyStatsThermostatWeather> implements DailyReportDao {

    /**
     * List by day.
     * @param locationId the location id
     * @param day the day
     * @return the list
     */
    @Override
    public List<AggDailyStatsThermostatWeather> listByDayLocation(int locationId, int day, int month, int year) {

        String sql = "select s from AggDailyStatsThermostatWeather s where s.locationId = :locationId and s.day = :day and s.month = :month and s.year = :year";
        Map<String, Object> paramVals = new HashMap<String, Object>();
        paramVals.put("locationId", locationId);
        paramVals.put("day", day);
        paramVals.put("month", month);
        paramVals.put("year", year);
        List<AggDailyStatsThermostatWeather> stats = listByReportQuery(sql, paramVals);
        return stats;
    }

    /**
     * List by day user.
     * @param userId the user id
     * @param day the day
     * @param month the month
     * @param year the year
     * @return the list
     * @see com.ecofactor.qa.automation.dao.DailyReportDao#listByDayUser(int, int, int, int)
     */
    public List<AggDailyStatsThermostatWeather> listByDayUser(int userId, int day, int month, int year) {

        String sql = "select s from AggDailyStatsThermostatWeather s where s.userId = :userId and s.day = :day and s.month = :month and s.year = :year";
        Map<String, Object> paramVals = new HashMap<String, Object>();
        paramVals.put("userId", userId);
        paramVals.put("day", day);
        paramVals.put("month", month);
        paramVals.put("year", year);
        List<AggDailyStatsThermostatWeather> stats = listByReportQuery(sql, paramVals);
        return stats;
    }
}
