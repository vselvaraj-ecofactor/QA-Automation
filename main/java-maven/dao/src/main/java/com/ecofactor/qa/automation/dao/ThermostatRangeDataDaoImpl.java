/*
 * ThermostatRangeDataDaoImpl.java
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
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.ecofactor.common.pojo.timeseries.PartitionedThermostatRangeData;

/**
 * The Class ThermostatRangeDataDaoImpl.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class ThermostatRangeDataDaoImpl extends BaseDaoImpl<PartitionedThermostatRangeData>
        implements ThermostatRangeDataDao {

    /**
     * List by thermostat and start time range.
     * @param thermostatId the thermostat id
     * @param startTime the start time
     * @param endTime the end time
     * @return the list
     * @see com.ecofactor.qa.automation.dao.ThermostatRangeDataDao#listByThermostatAndStartTimeRange(java.lang.Integer,
     *      java.util.Calendar, java.util.Calendar)
     */
    public List<PartitionedThermostatRangeData> listByThermostatAndStartTimeRange(
            Integer thermostatId, Calendar startTime, Calendar endTime) {

        String ql = "SELECT trd FROM PartitionedThermostatRangeData trd WHERE trd.id.thermostatId = :thermostatId AND trd.id.startTime BETWEEN :startTime AND :endTime";
        Map<String, Object> paramVals = new HashMap<String, Object>();
        paramVals.put("thermostatId", thermostatId);
        paramVals.put("startTime", startTime);
        paramVals.put("endTime", endTime);
        List<PartitionedThermostatRangeData> thermostatRangeDatas = listByQuery(ql, paramVals);
        return thermostatRangeDatas;
    }

    /**
     * List by thermostat and start time range.
     * @param thermostatId the thermostat id
     * @param startTime the start time
     * @param endTime the end time
     * @return the list
     * @see com.ecofactor.qa.automation.dao.ThermostatRangeDataDao#listByThermostatAndStartTimeRange(java.lang.Integer,
     *      java.util.Calendar, java.util.Calendar)
     */
    public List<PartitionedThermostatRangeData> listByThermostatAndStartTimeRange(
            Integer thermostatId, Calendar startTime) {

        String ql = "SELECT trd FROM PartitionedThermostatRangeData trd WHERE trd.id.thermostatId = :thermostatId AND trd.id.startTime > :startTime ORDER BY trd.id.startTime DESC";
        Map<String, Object> paramVals = new HashMap<String, Object>();
        paramVals.put("thermostatId", thermostatId);
        paramVals.put("startTime", startTime);
        List<PartitionedThermostatRangeData> thermostatRangeDatas = listByQuery(ql, paramVals);
        return thermostatRangeDatas;
    }

    /**
     * Find latest by thermostat.
     * @param thermostatId the thermostat id
     * @return the thermostat range data
     * @see com.ecofactor.qa.automation.dao.ThermostatRangeDataDao#findLatestByThermostat(java.lang.Integer)
     */
    public PartitionedThermostatRangeData findLatestByThermostat(Integer thermostatId) {

        String ql = "SELECT trd FROM PartitionedThermostatRangeData trd WHERE trd.id.thermostatId = :thermostatId ORDER BY trd.id.startTime DESC limit 1";
        Map<String, Object> paramVals = new HashMap<String, Object>();
        paramVals.put("thermostatId", thermostatId);
        PartitionedThermostatRangeData thermostatRangeData = findByQuery(ql, paramVals);
        return thermostatRangeData;
    }

    public EntityManager getEntityManager() {

        return getRangeDataEntityManager();
    }
}
