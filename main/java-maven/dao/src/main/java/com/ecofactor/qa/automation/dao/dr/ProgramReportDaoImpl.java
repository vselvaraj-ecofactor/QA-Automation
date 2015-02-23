/*
 * ProgramReportDaoImpl.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.dao.dr;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ecofactor.common.pojo.analytics.QuantReport;
import com.ecofactor.qa.automation.dao.BaseDaoImpl;

public class ProgramReportDaoImpl extends BaseDaoImpl<QuantReport> implements ProgramReportDao {

    /**
     * List report for particular program event based on thermostat Id.
     * @param thermostatId the thermostat id.
     * @return list.
     * @see com.ecofactor.qa.automation.dao.dr.ProgramReportDao#listReportByThermostatId(java.lang.Integer)
     */
    @Override
    public List<QuantReport> listReportByThermostatId(Integer thermostatId) {

        String sql = "select q from QuantReport q where q.thermostatId = :thermostatId";
        Map<String, Object> paramVals = new HashMap<String, Object>();
        paramVals.put("thermostatId", thermostatId);
        List<QuantReport> reports = listByReportQuery(sql, paramVals);
        return reports;
    }

    /**
     * List report for particular program event based on thermostat id with start and end time.
     * @param thermostatId the thermostat Id.
     * @param startDate the start date.
     * @param endDate the end date.
     * @return list.
     * @see com.ecofactor.qa.automation.dao.dr.ProgramReportDao#listReportbyThermostatStartAndEndTime(int,
     *      java.util.Calendar, java.util.Calendar)
     */
    @Override
    public List<QuantReport> listReportbyThermostatStartAndEndTime(int thermostatId,
            Calendar startDate, Calendar endDate) {

        String sql = "select q from QuantReport q where q.thermostatId = :thermostatId and q.validFrom between :startDate and :endDate";
        Map<String, Object> paramVals = new HashMap<String, Object>();
        paramVals.put("thermostatId", thermostatId);
        paramVals.put("startDate", startDate);
        paramVals.put("endDate", endDate);
        List<QuantReport> reports = listByReportQuery(sql, paramVals);
        return reports;
    }
}
