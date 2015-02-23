/*
 * LSProgramEventDaoImpl.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
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

import com.ecofactor.common.pojo.EcpCoreLSEvent;
import com.ecofactor.common.pojo.Status;
import com.ecofactor.qa.automation.dao.BaseDaoImpl;
import com.ecofactor.qa.automation.util.DateUtil;

/**
 * The Class LSProgramEventDaoImpl.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class LSProgramEventDaoImpl extends BaseDaoImpl<EcpCoreLSEvent> implements LSProgramEventDao {

    /**
     * List by event name.
     * @param eventName the event name
     * @param eventStatus the event status
     * @return the list
     * @see com.ecofactor.qa.automation.dao.dr.LSProgramEventDao#listByEventName(java.lang.String,
     *      java.lang.String)
     */
    public List<EcpCoreLSEvent> listByEventName(String eventName, String eventStatus) {

        String sql = " select t from EcpCoreLSEvent t  where t.name = :lsEvnt_name and t.status = :lsEvnt_status";
        Map<String, Object> paramVals = new HashMap<String, Object>();
        paramVals.put("lsEvnt_name", eventName);
        paramVals.put("lsEvnt_status", Status.valueOf(eventStatus));
        List<EcpCoreLSEvent> events = listByQuery(sql, paramVals);
        return events;
    }

    /**
     * Find latest by event name.
     * @param eventName the event name
     * @return the ecp core ls event
     * @see com.ecofactor.qa.automation.dao.dr.LSProgramEventDao#findLatestByEventName(java.lang.String)
     */
    public EcpCoreLSEvent findLatestByEventName(String eventName) {

        String sql = " select t from EcpCoreLSEvent t  where t.name = :lsEvnt_name and t.lastUpdated=max(t.lastUpdated)";
        Map<String, Object> paramVals = new HashMap<String, Object>();
        paramVals.put("lsEvnt_name", eventName);
        List<EcpCoreLSEvent> events = listByQuery(sql, paramVals);
        return events.get(0);

    }

    /**
     * List by start date and status.
     * @param eventStartDateBefore the event start date before
     * @param eventStatus the event status
     * @return the ecp core ls event
     * @see com.ecofactor.qa.automation.dao.dr.LSProgramEventDao#listByStartDateAndStatus(java.util.Calendar,
     *      com.ecofactor.common.pojo.Status)
     */
    public EcpCoreLSEvent listByStartDateAndStatus(Calendar eventStartDateBefore, Status eventStatus) {

        String sql = "select e from EcpCoreLSEvent e where e.startDate < :event_before_date and e.status = :event_status";
        Map<String, Object> paramVals = new HashMap<String, Object>();
        paramVals.put("event_before_date", eventStartDateBefore);
        paramVals.put("event_status", eventStatus);
        EcpCoreLSEvent events = findByQuery(sql, paramVals);
        return events;
    }

    /**
     * List Details based on event name after update the start time.
     * @param eventName the program Event Name.
     * @return Map.
     * @see com.ecofactor.qa.automation.dao.dr.LSProgramEventDao#updateEventByStartDateAndEndDate(java.lang.String)
     */
    @Override
    public Map<String, Object> updateEventByStartDateAndEndDate(final String eventName) {

        Calendar currentTime = DateUtil.addTimeToUTCCalendar();
        currentTime.add(Calendar.MINUTE, 2);
        Calendar currentEndTime = DateUtil.addTimeToUTCCalendar();
        currentEndTime.add(Calendar.MINUTE, 10);
        String sql = "select lsEvent from EcpCoreLSEvent lsEvent where lsEvent.name = :event_name";
        Map<String, Object> paramVals = new HashMap<String, Object>();
        paramVals.put("event_name", eventName);
        EcpCoreLSEvent events = findByQuery(sql, paramVals);
        events.setStartDate(currentTime);
        events.setEndDate(currentEndTime);
        updateEntity(events);
        Map<String, Object> valueDetails = new HashMap<String, Object>();
        final int programId = events.getId();
        final String eventStatus = events.getStatus().toString();
        final Calendar startDateTime = events.getStartDate();
        final int algorithmId = events.getAlgorithmId();
        valueDetails.put("ProgramEventID", programId);
        valueDetails.put("Event Status", eventStatus);
        valueDetails.put("Event Start DateTime", startDateTime);
        valueDetails.put("Algorithm Id", algorithmId);
        return valueDetails;
    }

    /**
     * List Event Id and Status.
     * @param eventName the event name
     * @return list.
     * @see com.ecofactor.qa.automation.dao.dr.LSProgramEventDao#listByEventName(java.lang.String)
     */
    @Override
    public String listByEventName(String eventName) {

        String sql = "select lsEvent from EcpCoreLSEvent lsEvent where lsEvent.name = :event_name";
        Map<String, Object> paramVals = new HashMap<String, Object>();
        paramVals.put("event_name", eventName);
        EcpCoreLSEvent events = findByQuery(sql, paramVals);
        final String eventStatus = events.getStatus().toString();
        return eventStatus;
    }

    /**
     * Update event status based on Event name.
     * @param eventName the event name.
     * @see com.ecofactor.qa.automation.dao.dr.LSProgramEventDao#updateEventStatus(java.lang.String)
     */
    @Override
    public int updateEventStatus(String eventName) {

        String sql = "select lsEvent from EcpCoreLSEvent lsEvent where lsEvent.name = :event_name";
        Map<String, Object> paramVals = new HashMap<String, Object>();
        paramVals.put("event_name", eventName);
        EcpCoreLSEvent events = findByQuery(sql, paramVals);
        int programEventID = events.getId();
        events.setStatus(Status.CANCELED);
        updateEntity(events);
        return programEventID;
    }

    /**
     * Update event status based on Event id.
     * @param id the program event id.
     * @see com.ecofactor.qa.automation.dao.dr.LSProgramEventDao#updateById(java.lang.Integer)
     */
    @Override
    public void updateById(Integer id) {

        String sql = "select lsEvent from EcpCoreLSEvent lsEvent where lsEvent.id = :proId";
        Map<String, Object> paramVals = new HashMap<String, Object>();
        paramVals.put("proId", id);
        EcpCoreLSEvent events = findByQuery(sql, paramVals);
        events.setStatus(Status.CANCELED);
        updateEntity(events);
    }

    /**
     * Fetch the program Event Id based on Event name.
     * @param eventName the event name.
     * @return Integer.
     * @see com.ecofactor.qa.automation.dao.dr.LSProgramEventDao#programEventId(java.lang.String)
     */
    @Override
    public int programEventId(String eventName) {

        String sql = "select lsEvent from EcpCoreLSEvent lsEvent where lsEvent.name = :event_name";
        Map<String, Object> paramVals = new HashMap<String, Object>();
        paramVals.put("event_name", eventName);
        EcpCoreLSEvent events = findByQuery(sql, paramVals);
        final int programEventId = events.getId();
        return programEventId;
    }

    /**
     * @param eventName
     * @return
     * @see com.ecofactor.qa.automation.dao.dr.LSProgramEventDao#updateEventByStartDate(java.lang.String)
     */
    @Override
    public Map<String, Object> updateEventByStartDate(String eventName) {

        Calendar currentTime = DateUtil.addTimeToUTCCalendar();
        currentTime.add(Calendar.MINUTE, 2);
        String sql = "select lsEvent from EcpCoreLSEvent lsEvent where lsEvent.name = :event_name";
        Map<String, Object> paramVals = new HashMap<String, Object>();
        paramVals.put("event_name", eventName);
        EcpCoreLSEvent events = findByQuery(sql, paramVals);
        events.setStartDate(currentTime);
        updateEntity(events);
        Map<String, Object> valueDetails = new HashMap<String, Object>();
        final int programId = events.getId();
        final String eventStatus = events.getStatus().toString();
        final Calendar startDateTime = events.getStartDate();
        valueDetails.put("ProgramEventID :", programId);
        valueDetails.put("Event Status :", eventStatus);
        valueDetails.put("Event Start DateTime :", startDateTime);
        return valueDetails;
    }
}
