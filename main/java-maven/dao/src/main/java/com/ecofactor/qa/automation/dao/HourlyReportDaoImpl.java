/*
 * HourlyReportDaoImpl.java
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

import com.ecofactor.qa.automation.pojo.AggHourlyStatsThermostatWeather;

/**
 * HourlyReportDaoImpl implements HourlyReportDao.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class HourlyReportDaoImpl extends BaseDaoImpl<AggHourlyStatsThermostatWeather> implements HourlyReportDao {

    /**
     * List by hour.
     * @param locationId the location id
     * @param hour the hour
     * @param day the day
     * @param month the month
     * @param year the year
     * @return the list
     * @see com.ecofactor.qa.automation.dao.HourlyReportDao#listByHour(int, int, int, int, int)
     */
    @Override
    public List<AggHourlyStatsThermostatWeather> listByHourLocation(int locationId, int hour, int day, int month, int year) {

        String sql = "select s from AggHourlyStatsThermostatWeather s where s.locationId = :locationId and s.hour = :hour and s.day = :day and s.month = :month and s.year = :year";
        Map<String, Object> paramVals = new HashMap<String, Object>();
        paramVals.put("locationId", locationId);
        paramVals.put("hour", hour);
        paramVals.put("day", day);
        paramVals.put("month", month);
        paramVals.put("year", year);
        List<AggHourlyStatsThermostatWeather> stats = listByReportQuery(sql, paramVals);
        return stats;
    }

    public List<AggHourlyStatsThermostatWeather> listByHourUser(int userId, int hour, int day, int month, int year) {

        String sql = "select s from AggHourlyStatsThermostatWeather s where s.userId = :userId and s.hour = :hour and s.day = :day and s.month = :month and s.year = :year";
        Map<String, Object> paramVals = new HashMap<String, Object>();
        paramVals.put("userId", userId);
        paramVals.put("hour", hour);
        paramVals.put("day", day);
        paramVals.put("month", month);
        paramVals.put("year", year);
        List<AggHourlyStatsThermostatWeather> stats = listByReportQuery(sql, paramVals);
        return stats;
    }
}
