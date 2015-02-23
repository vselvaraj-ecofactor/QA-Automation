/*
 * ThermostatEventDao.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.dao.dr;

import java.util.Calendar;
import java.util.List;

import com.ecofactor.common.pojo.timeseries.PartitionedThermostatEvent;
import com.ecofactor.qa.automation.dao.BaseDao;

/**
 * The Interface ThermostatEventDao.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public interface ThermostatEventDao extends BaseDao<PartitionedThermostatEvent> {

    /**
     * List Thermostat event based on group event id.
     * @param groupEventId the groupEventId.
     * @return the list.
     */
    public List<PartitionedThermostatEvent> listThermostatEventByGroupEventId(Double groupEventId);

    /**
     * List Thermostat event based on group event id with start and end time.
     * @param groupEventId the groupEventId
     * @param startTime the start time
     * @param endTime the end time
     * @return the list.
     */
    public List<PartitionedThermostatEvent> listEventbyGroupIdwithStartEndTime(Double groupEventId,
            Calendar startTime, Calendar endTime);

    /**
     * List Thermostat event based on thermostat id with start and end time.
     * @param thermostatId the thermostatId
     * @param startTime the start time
     * @param endTime the end time
     * @return the list.
     */
    public List<PartitionedThermostatEvent> listByThermostatIdwithStartandEndTime(
            Integer thermostatId, Calendar startTime, Calendar endTime);

    /**
     * List Thermostat event based on algorithm Id with start and end time.
     * @param algorithmId the algorithmId
     * @param startTime the start time
     * @param endTime the end time
     * @return the list.
     */
    public List<PartitionedThermostatEvent> listByAlgoIdwithStartandEndTime(Integer algorithmId,
            Calendar startTime, Calendar endTime);

    /**
     * List Thermostat event based on algorithm id with group event id.
     * @param algorithmId the algorithmId
     * @param groupEventId the groupEventId
     * @return the list.
     */
    public List<PartitionedThermostatEvent> listByAlgoIdwithGroupEventId(Integer algorithmId,
            Double groupEventId);

    /**
     * List Thermostat event based on algorithm id, group id with start and end time.
     * @param algorithmId the algorithmId
     * @param groupEventId the groupEventId
     * @param startTime the start time
     * @param endTime the end time
     * @return the list.
     */
    public List<PartitionedThermostatEvent> listByAlgoIdGroupIdwithStartandEndTime(
            Integer algorithmId, Double groupEventId, Calendar startTime, Calendar endTime);

    /**
     * Fetch the details after completion of DR Event based on thermostat id.
     * @param thermostatId
     * @return list.
     */
    public List<PartitionedThermostatEvent> setPointEDR(int thermostatId);
    
    /**
     * Fetch the details after completion of DR Event based on thermostat id.
     * @param thermostatId
     * @return list.
     */
    public List<PartitionedThermostatEvent> std210DR(int thermostatId,Double groupEventId);

    /**
     * Fetch group event id as program event id.
     * @param programEventId the program event id.
     * @return list.
     */
    public  int listByGroupId(final double programEventId);
}
