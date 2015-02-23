/*
 * ThermostatEventDaoImpl.java
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

import com.ecofactor.common.pojo.Status;
import com.ecofactor.common.pojo.timeseries.PartitionedThermostatEvent;
import com.ecofactor.common.pojo.timeseries.ThermostatEventAttributeDef;

/**
 * ThermostatEventDaoImpl is the dao implementation for thermostat events.
 * @author $Author: rvinoopraj $
 * @version $Rev: 23883 $ $Date: 2013-10-08 18:03:59 +0530 (Tue, 08 Oct 2013) $
 */
public class ThermostatEventDaoImpl extends BaseDaoImpl<PartitionedThermostatEvent> implements ThermostatEventDao {


    // Event Type
    // USER(0), ALGO(1), ADMIN(2), LS(3), Null(255);

    // Action Integer Values
    //heat_setting(0), cool_setting(1), program(2), startEvent(3), endEvent(4), off(5), Null(255);
    // ---------------------

    // Status Integer values
    // PENDING(0), PROCESSED(1), FAILED(2), ANALYZED(3), SKIPPED(4), DELAYED(5), SCHEDULED(6),
    // COMPLETED(7), NOT_ACK(8), Null(255);
    // ---------------------

    public List<PartitionedThermostatEvent> listAlgoProcessedEventsByThermostatAndTimeRange(Integer thermostatId, Calendar startTime, Calendar endTime) {

        String ql = "SELECT te FROM PartitionedThermostatEvent te WHERE te.type=1  AND te.thermostatId = :thermostatId AND te.status = :status AND te.id.eventSysTime BETWEEN :startTime AND :endTime";
        Map<String, Object> paramVals = new HashMap<String, Object>();
        paramVals.put("status", ThermostatEventAttributeDef.Status.PROCESSED.status());
        paramVals.put("thermostatId", thermostatId);
        paramVals.put("startTime", startTime);
        paramVals.put("endTime", endTime);

        List<PartitionedThermostatEvent> thermostatEvents = listByQuery(ql, paramVals);
        return thermostatEvents;
    }

    /**
     * List by thermostat and event sys time range.
     * @param thermostatId the thermostat id
     * @param startTime the start time
     * @param endTime the end time
     * @return the list
     * @see com.ecofactor.qa.automation.dao.ThermostatEventDao#listByThermostatAndEventSysTimeRange(java.lang.Integer,
     *      java.util.Calendar, java.util.Calendar)
     */
    public List<PartitionedThermostatEvent> listByThermostatAndEventSysTimeRange(Integer thermostatId, Calendar startTime, Calendar endTime) {

        String ql = "SELECT te FROM PartitionedThermostatEvent te WHERE te.thermostatId = :thermostatId AND te.id.eventSysTime BETWEEN :startTime AND :endTime order by te.id.eventSysTime asc";
        Map<String, Object> paramVals = new HashMap<String, Object>();
        paramVals.put("thermostatId", thermostatId);
        paramVals.put("startTime", startTime);
        paramVals.put("endTime", endTime);

        List<PartitionedThermostatEvent> thermostatEvents = listByQuery(ql, paramVals);
        return thermostatEvents;
    }

    /**
     * Find latest by thermostat.
     * @param thermostatId the thermostat id
     * @return the thermostat event
     * @see com.ecofactor.qa.automation.dao.ThermostatEventDao#findLatestByThermostat(java.lang.Integer)
     */
    public PartitionedThermostatEvent findLatestByThermostat(Integer thermostatId) {

        String ql = "SELECT te FROM PartitionedThermostatEvent te WHERE te.thermostatId = :thermostatId ORDER BY te.id.thermostatEventId DESC";
        Map<String, Object> paramVals = new HashMap<String, Object>();
        paramVals.put("thermostatId", thermostatId);

        PartitionedThermostatEvent thermostatEvent = findByQuery(ql, paramVals);
        return thermostatEvent;
    }

    /**
     * List by thermostat and event sys time.
     * @param thermostatId the thermostat id
     * @param startTime the start time
     * @return the list
     * @see com.ecofactor.qa.automation.dao.ThermostatEventDao#listByThermostatAndEventSysTime(java.lang.Integer,
     *      java.util.Calendar)
     */
    public List<PartitionedThermostatEvent> listByThermostatAndEventSysTime(Integer thermostatId, Calendar startTime) {

        String sqlForEvent = "SELECT te FROM PartitionedThermostatEvent te WHERE te.thermostatId = :thermostatId AND te.id.eventSysTime > :startTime";
        Map<String, Object> obj = new HashMap<String, Object>();
        obj.put("thermostatId", thermostatId);
        obj.put("startTime", startTime);

        List<PartitionedThermostatEvent> events = listByQuery(sqlForEvent, obj);
        return events;
    }

    /**
     * List by start date and status.
     * @param eventStartDateBefore the event start date before
     * @param eventStatus the event status
     * @return the list
     * @see com.ecofactor.qa.automation.dao.ThermostatEventDao#listByStartDateAndStatus(java.util.Calendar,
     *      com.ecofactor.common.pojo.Status)
     */
    @Override
    public List<PartitionedThermostatEvent> listByStartDateAndStatus(Calendar eventStartDateBefore, Status eventStatus) {

        String sql = " select t from PartitionedThermostatEvent t  where groupEventId in (select e.id from EcpCoreLSEvent e where e.startDate < :event_before_date and e.status = :event_status)";
        Map<String, Object> paramVals = new HashMap<String, Object>();
        paramVals.put("event_before_date", eventStartDateBefore);
        paramVals.put("event_status", eventStatus);
        List<PartitionedThermostatEvent> events = listByQuery(sql, paramVals);
        return events;
    }

    /**
     * Find latest by phase.
     * @param thermostatId the thermostat id
     * @param phaseValue the phase value
     * @return the thermostat event
     * @see com.ecofactor.qa.automation.dao.ThermostatEventDao#findLatestByPhase(java.lang.Integer,
     *      int)
     */
    @Override
    public PartitionedThermostatEvent findLatestByPhase(Integer thermostatId, int phaseValue) {

        String ql = "SELECT te FROM PartitionedThermostatEvent te WHERE te.thermostatId = :thermostatId and te.phase = :phaseValue ORDER BY te.id.thermostatEventId DESC limit 1";
        Map<String, Object> paramVals = new HashMap<String, Object>();
        paramVals.put("thermostatId", thermostatId);
        paramVals.put("phaseValue", phaseValue);

        PartitionedThermostatEvent thermostatEvent = findByQuery(ql, paramVals);
        return thermostatEvent;
    }

    /**
     * Find thermostat event by event id.
     * @param eventId the event id
     * @return the list
     * @see com.ecofactor.qa.automation.dao.ThermostatEventDao#findThermostatEventByEventID(java.lang.Double)
     */
    @Override
    public List<PartitionedThermostatEvent> findThermostatEventByEventID(Double eventId) {

        String sql = "select t from PartitionedThermostatEvent t where groupEventId = :event_id";
        Map<String, Object> paramVals = new HashMap<String, Object>();
        paramVals.put("event_id", eventId);
        List<PartitionedThermostatEvent> events = listByQuery(sql, paramVals);
        return events;
    }

    /**
     * Find thermostat event by group event id and thermostat id.
     * @param groupEventId the group event id
     * @param thermostatId the thermostat id
     * @return the thermostat event
     * @see com.ecofactor.qa.automation.dao.ThermostatEventDao#findThermostatEventByGroupEventIdAndThermostatId(java.lang.Double,
     *      java.lang.Integer)
     */
    @Override
    public PartitionedThermostatEvent findThermostatEventByGroupEventIdAndThermostatId(Double groupEventId, Integer thermostatId) {

        String sql = "select t from PartitionedThermostatEvent t where groupEventId=:group_event_id and thermostatId=:thermostat_Id";
        Map<String, Object> paramVals = new HashMap<String, Object>();
        paramVals.put("group_event_id", groupEventId);
        paramVals.put("thermostat_Id", thermostatId);
        PartitionedThermostatEvent thermostatEvent = findByQuery(sql, paramVals);
        return thermostatEvent;
    }

    /**
     * List events by thermostat algorithm.
     * @param thermostatId the thermostat id
     * @param algorithm the algorithm
     * @param startDate the start date
     * @param endDate the end date
     * @return the list
     * @see com.ecofactor.qa.automation.dao.ThermostatEventDao#listEventsByThermostatAlgorithm(java.lang.Integer,
     *      com.ecofactor.common.pojo.Algorithm, java.util.Date, java.util.Date)
     */
    @Override
    public List<PartitionedThermostatEvent> listEventsByThermostatAlgorithm(Integer thermostatId, int algorithm, Calendar startDate, Calendar endDate) {

        String sql = "select t from PartitionedThermostatEvent t where t.thermostatId=:tstatId and t.algorithmId=:algrthm and t.id.eventSysTime between :strtDte and :endDte";
        Map<String, Object> paramVals = new HashMap<String, Object>();
        paramVals.put("tstatId", thermostatId);
        paramVals.put("algrthm", algorithm);
        paramVals.put("strtDte", startDate);
        paramVals.put("endDte", endDate);
        List<PartitionedThermostatEvent> thermostatEvent = listByQuery(sql, paramVals);
        return thermostatEvent;
    }

    /**
     * Find base temp.
     * @param thermostatId the thermostat id
     * @param startCal the start cal
     * @param endcal the endcal
     * @return the thermostat event
     * @see com.ecofactor.qa.automation.dao.ThermostatEventDao#findBaseTemp(java.lang.Integer,
     *      java.util.Calendar, java.util.Calendar)
     */
    @Override
    public PartitionedThermostatEvent findBaseTemp(Integer thermostatId, Calendar startCal, Calendar endcal) {

        String sql = "select t from PartitionedThermostatEvent t where thermostatId=:thermostat_Id and deltaEE=0 and t.id.eventSysTime BETWEEN :startTime AND :endTime ORDER BY t.id.thermostatEventId DESC";
        Map<String, Object> paramVals = new HashMap<String, Object>();
        paramVals.put("thermostat_Id", thermostatId);
        paramVals.put("startTime", startCal);
        paramVals.put("endTime", endcal);
        PartitionedThermostatEvent thermostatEvent = findByQuery(sql, paramVals);
        return thermostatEvent;
    }

    /**
     * Gets the base temp heat.
     * @param thermostatId the thermostat id
     * @return the base temp heat
     * @see com.ecofactor.qa.automation.dao.ThermostatEventDao#getBaseTempHeat(java.lang.Integer)
     */
    @Override
    public double getBaseTempHeat(Integer thermostatId) {

        String ql = "SELECT te FROM PartitionedThermostatEvent te WHERE te.thermostatId = :thermostatId ORDER BY te.id.eventSysTime DESC";
        Map<String, Object> paramVals = new HashMap<String, Object>();
        paramVals.put("thermostatId", thermostatId);
        PartitionedThermostatEvent thEvent = findByQuery(ql, paramVals);
        double deltaEE = thEvent.getDeltaEE();
        double baseTemp = thEvent.getNewSetting();

        // deltaEE for heat = new setting + base temperature
        // deltaEE is greater than zero or less than zero
        if (deltaEE != 0) {
            baseTemp = baseTemp + deltaEE;
        }

        return baseTemp;
    }

    /**
     * Gets the base temp cool.
     * @param thermostatId the thermostat id
     * @return the base temp cool
     * @see com.ecofactor.qa.automation.dao.ThermostatEventDao#getBaseTempCool(java.lang.Integer)
     */
    @Override
    public double getBaseTempCool(Integer thermostatId) {

        String ql = "SELECT te FROM PartitionedThermostatEvent te WHERE te.thermostatId = :thermostatId  ORDER BY te.id.eventSysTime DESC";
        Map<String, Object> paramVals = new HashMap<String, Object>();
        paramVals.put("thermostatId", thermostatId);
        PartitionedThermostatEvent thEvent = findByQuery(ql, paramVals);
        double deltaEE = thEvent.getDeltaEE();
        double baseTemp = thEvent.getNewSetting();

        // deltaEE for cool = new setting - base temperature
        // deltaEE is greater than zero or less than zero
        if (deltaEE != 0) {
            baseTemp = baseTemp - deltaEE;
        }

        return baseTemp;
    }

    /**
     * Find latest by thermostat and algorithm.
     * @param thermostatId the thermostat id
     * @param algorithmId the algorithm id
     * @return the thermostat event
     * @see com.ecofactor.qa.automation.dao.ThermostatEventDao#findLatestByThermostatAndAlgorithm(java.lang.Integer,
     *      java.lang.Integer)
     */
    @Override
    public PartitionedThermostatEvent findLatestByThermostatAndAlgorithm(Integer thermostatId, Integer algorithmId) {

        String ql = "SELECT te FROM PartitionedThermostatEvent te WHERE te.thermostatId = :thermostatId and te.algorithmId=:algorithmId ORDER BY te.id.thermostatEventId DESC";
        Map<String, Object> paramVals = new HashMap<String, Object>();
        paramVals.put("thermostatId", thermostatId);
        paramVals.put("algorithmId", algorithmId);

        PartitionedThermostatEvent thermostatEvent = findByQuery(ql, paramVals);
        return thermostatEvent;
    }

    /**
     * @return
     * @see com.ecofactor.qa.automation.dao.BaseDaoImpl#getEntityManager()
     */
    public EntityManager getEntityManager() {

        return getRangeDataEntityManager();
    }
}
