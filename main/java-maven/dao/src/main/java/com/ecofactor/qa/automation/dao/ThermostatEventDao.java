/*
 * ThermostatEventDao.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.dao;

import java.util.Calendar;
import java.util.List;

import com.ecofactor.common.pojo.Status;
import com.ecofactor.common.pojo.timeseries.PartitionedThermostatEvent;
import com.ecofactor.common.pojo.timeseries.ThermostatEventAttributeDef;

/**
 * ThermostatEventDao is the dao interface for thermostat events.
 * @author $Author: rvinoopraj $
 * @version $Rev: 23883 $ $Date: 2013-10-08 18:03:59 +0530 (Tue, 08 Oct 2013) $
 */
public interface ThermostatEventDao extends BaseDao<PartitionedThermostatEvent> {

    /**
     * List algo processed events by thermostat and time range.
     * @param thermostatId the thermostat id
     * @param startTime the start time
     * @param endTime the end time
     * @return the list
     */
    public List<PartitionedThermostatEvent> listAlgoProcessedEventsByThermostatAndTimeRange(Integer thermostatId, Calendar startTime, Calendar endTime);

    /**
     * List by thermostat and event sys time range.
     * @param thermostatId the thermostat id
     * @param startTime the start time
     * @param endTime the end time
     * @return the list
     */
    List<PartitionedThermostatEvent> listByThermostatAndEventSysTimeRange(Integer thermostatId, Calendar startTime, Calendar endTime);

    /**
     * List by thermostat and event sys time.
     * @param thermostatId the thermostat id
     * @param startTime the start time
     * @return the list
     */
    List<PartitionedThermostatEvent> listByThermostatAndEventSysTime(Integer thermostatId, Calendar startTime);

    /**
     * Find latest by thermostat.
     * @param thermostatId the thermostat id
     * @return the thermostat event
     */
    PartitionedThermostatEvent findLatestByThermostat(Integer thermostatId);

    /**
     * List by start date and status.
     * @param eventStartDateBefore the event start date before
     * @param eventStatus the event status
     * @return the list
     */
    List<PartitionedThermostatEvent> listByStartDateAndStatus(Calendar eventStartDateBefore, Status eventStatus);

    /**
     * Find latest by phase.
     * @param thermostatId the thermostat id
     * @param phaseValue the phase value
     * @return the thermostat event
     */
    PartitionedThermostatEvent findLatestByPhase(Integer thermostatId, int phaseValue);

    /**
     * Find thermostat event by event id.
     * @param eventId the event id
     * @return the list
     */
    List<PartitionedThermostatEvent> findThermostatEventByEventID(Double eventId);

    /**
     * Find thermostat event by group event id and thermostat id.
     * @param groupEventId the group event id
     * @param thermostatId the thermostat id
     * @return the thermostat event
     */
    PartitionedThermostatEvent findThermostatEventByGroupEventIdAndThermostatId(Double groupEventId, Integer thermostatId);

    /**
     * List events by thermostat algorithm.
     * @param thermostatId the thermostat id
     * @param algorithm the algorithm
     * @param startDate the start date
     * @param endDate the end date
     * @return the list
     */
    List<PartitionedThermostatEvent> listEventsByThermostatAlgorithm(Integer thermostatId, int algorithm, Calendar startDate, Calendar endDate);

    /**
     * Find base temp.
     * @param thermostatId the thermostat id
     * @param startTime the start time
     * @param endTime the end time
     * @return the thermostat event
     */
    PartitionedThermostatEvent findBaseTemp(Integer thermostatId, Calendar startTime, Calendar endTime);

    /**
     * Gets the base temp heat.
     * @param thermostatId the thermostat id
     * @return the base temp heat
     */
    double getBaseTempHeat(Integer thermostatId);

    /**
     * Gets the base temp cool.
     * @param thermostatId the thermostat id
     * @return the base temp cool
     */
    double getBaseTempCool(Integer thermostatId);

    /**
     * Find latest by thermostat and algorithm.
     * @param thermostatId the thermostat id
     * @param algorithmId the algorithm id
     * @return the thermostat event
     */
    PartitionedThermostatEvent findLatestByThermostatAndAlgorithm(Integer thermostatId, Integer algorithmId);
}
