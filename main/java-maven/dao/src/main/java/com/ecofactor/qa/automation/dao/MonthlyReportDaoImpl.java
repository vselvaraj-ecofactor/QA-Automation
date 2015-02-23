/*
 * MonthlyReportDaoImpl.java
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

import com.ecofactor.common.pojo.analytics.AggMonthlyStatsThermostatWeather;

/**
 * MonthlyReportDaoImpl implements MonthlyReportDao.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class MonthlyReportDaoImpl extends BaseDaoImpl<AggMonthlyStatsThermostatWeather> implements MonthlyReportDao {

    /**
     * List by month.
     * @param locationId the location id
     * @param month the month
     * @param year the year
     * @return the list
     */
    @Override
    public List<AggMonthlyStatsThermostatWeather> listByMonthLocation(int locationId, int month, int year) {

        String sql = "select s from AggMonthlyStatsThermostatWeather s where s.locationId = :locationId and s.month = :month and s.year = :year";
        Map<String, Object> paramVals = new HashMap<String, Object>();
        paramVals.put("locationId", locationId);
        paramVals.put("month", month);
        paramVals.put("year", year);
        List<AggMonthlyStatsThermostatWeather> stats = listByReportQuery(sql, paramVals);
        return stats;
    }

    /**
     * List by month user.
     * @param userId the user id
     * @param month the month
     * @param year the year
     * @return the list
     * @see com.ecofactor.qa.automation.dao.MonthlyReportDao#listByMonthUser(int, int, int)
     */
    public List<AggMonthlyStatsThermostatWeather> listByMonthUser(int userId, int month, int year) {

        String sql = "select s from AggMonthlyStatsThermostatWeather s where s.userId = :userId and s.month = :month and s.year = :year";
        Map<String, Object> paramVals = new HashMap<String, Object>();
        paramVals.put("userId", userId);
        paramVals.put("month", month);
        paramVals.put("year", year);
        List<AggMonthlyStatsThermostatWeather> stats = listByReportQuery(sql, paramVals);
        return stats;
    }
}
