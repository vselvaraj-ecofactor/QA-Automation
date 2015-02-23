/*
 * ThermostatProgramLogDaoImpl.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
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

import com.ecofactor.common.pojo.ThermostatProgramLog;

/**
 * The Class ThermostatProgramLogDaoImpl.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class ThermostatProgramLogDaoImpl extends BaseDaoImpl<ThermostatProgramLog> implements ThermostatProgramLogDao {

    /**
     * Find latest by thermostat.
     * @param thermostatId the thermostat id
     * @return the thermostat program log
     * @see com.ecofactor.qa.automation.dao.ThermostatProgramLogDao#findLatestByThermostat(java.lang.Integer)
     */
    @Override
    public ThermostatProgramLog findLatestByThermostat(Integer thermostatId) {

        String ql = "SELECT tpl FROM ThermostatProgramLog tpl WHERE tpl.thermostatId = :thermostatId ORDER BY tpl.id DESC";
        Map<String, Object> paramVals = new HashMap<String, Object>();
        paramVals.put("thermostatId", thermostatId);

        ThermostatProgramLog programLog = findByQuery(ql, paramVals);
        return programLog;
    }

    /**
     * @param thermostatId
     * @param currentDay
     * @return
     * @see com.ecofactor.qa.automation.dao.ThermostatProgramLogDao#listUTCCurrentDayLog(java.lang.Integer, int)
     */
    @Override
    public List<ThermostatProgramLog> listUTCCurrentDayLog(Integer thermostatId, int currentDay) {

        String sql = "SELECT tpl FROM ThermostatProgramLog tpl WHERE tpl.thermostatId = :thermostatId and programStatus='ACTIVE' and dowUTC=:currentDay";
        Map<String, Object> paramVals = new HashMap<String, Object>();
        paramVals.put("thermostatId", thermostatId);
        paramVals.put("currentDay", Integer.valueOf(currentDay));
        List<ThermostatProgramLog> programLogList = listByQuery(sql, paramVals);
        return programLogList;
    }

    /**
     * @param thermostatId
     * @param programType
     * @return
     * @see com.ecofactor.qa.automation.dao.ThermostatProgramLogDao#findLatestByThermostatActiveProgramType(java.lang.Integer, java.lang.String)
     */
    @Override
    public List<ThermostatProgramLog> findLatestByThermostatActiveProgramType(Integer thermostatId,
            String programType) {

        String sql = "SELECT tpl FROM ThermostatProgramLog tpl WHERE programStatus = 'ACTIVE' and tpl.thermostatId = :thermostatId and programType = :programType ORDER BY tpl.id DESC";
        Map<String, Object> paramVals = new HashMap<String, Object>();
        paramVals.put("thermostatId", thermostatId);
        paramVals.put("programType", programType);
        List<ThermostatProgramLog> programLogList = listByQuery(sql, paramVals);
        return programLogList;
    }
}
