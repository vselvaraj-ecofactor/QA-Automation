/*
 * ThermostatJobDaoImpl.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.dao;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import com.ecofactor.common.pojo.ThermostatJob;
import com.ecofactor.qa.automation.util.DateUtil;

/**
 * The Class ThermostatJobDaoImpl.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class ThermostatJobDaoImpl extends BaseDaoImpl<ThermostatJob> implements ThermostatJobDao {

    /**
     * Find a day bfr job data.
     * @param thermostatId the thermostat id
     * @return the thermostat job
     * @see com.ecofactor.qa.automation.dao.ThermostatJobDao#findADayBfrJobData(java.lang.Integer)
     */
    @Override
    public ThermostatJob findADayBfrJobData(Integer thermostatId) {

        String utcStartString = DateUtil.getUTCCurrentTimeStamp();
        Calendar startCalendar = DateUtil.parseToCalendar(utcStartString, DateUtil.DATE_FMT_FULL);
        startCalendar.add(Calendar.DATE, -2);
        
        String utcEndString = DateUtil.getUTCCurrentTimeStamp();
        Calendar endalendar = DateUtil.parseToCalendar(utcEndString, DateUtil.DATE_FMT_FULL);
        endalendar.add(Calendar.DATE, -1);
        
        ThermostatJob thermostatJob = null;
        String query = "SELECT tJob from ThermostatJob tJob where tJob.thermostatId = :thermostatId and tJob.startDate between :startDate and :endDate order by tJob.id desc";
        Map<String, Object> paramVals = new HashMap<String, Object>();
        paramVals.put("thermostatId", thermostatId);
        paramVals.put("startDate", startCalendar);
        paramVals.put("endDate", endalendar);
        
        thermostatJob = listQueryByLimit(query, paramVals, 2).get(1);
        return thermostatJob;
    }

    /**
     * Find latest job data.
     * @param thermostatId the thermostat id
     * @return the thermostat job
     * @see com.ecofactor.qa.automation.dao.ThermostatJobDao#findLatestJobData(java.lang.Integer)
     */
    @Override
    public ThermostatJob findLatestJobData(Integer thermostatId) {

        ThermostatJob thermostatJob = null;
        String query = "SELECT tJob from ThermostatJob tJob where tJob.thermostatId = :thermostatId order by tJob.id desc";
        Map<String, Object> paramVals = new HashMap<String, Object>();
        paramVals.put("thermostatId", thermostatId);
        thermostatJob = listQueryByLimit(query, paramVals, 1).get(0);
        return thermostatJob;
    }
}
