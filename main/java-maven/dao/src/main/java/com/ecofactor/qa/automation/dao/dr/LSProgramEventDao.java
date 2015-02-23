/*
 * LSProgramEventDao.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.dao.dr;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import com.ecofactor.common.pojo.EcpCoreLSEvent;
import com.ecofactor.common.pojo.Status;
import com.ecofactor.qa.automation.dao.BaseDao;

/**
 * The Interface LSProgramEventDao.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public interface LSProgramEventDao extends BaseDao<EcpCoreLSEvent> {

    /**
     * update event status as cancel.
     * @param eventName the event name.
     */
    public int updateEventStatus(String eventName);

    /**
     * List Event Id and Status.
     * @param eventName the event name
     * @return String.
     */
    public String listByEventName(String eventName);

    /**
     * Fetch the Program event Id.
     * @param eventName the event name
     * @return Integer.
     */
    public int programEventId(String eventName);

    /**
     * List by event name.
     * @param eventName the event name
     * @return the list
     */
    public List<EcpCoreLSEvent> listByEventName(String eventName, String eventStatus);

    /**
     * Find latest by event name.
     * @param eventName the event name
     * @return the ecp core ls event
     */
    public EcpCoreLSEvent findLatestByEventName(String eventName);

    /**
     * List by start date and status.
     * @param eventStartDateBefore the event start date before
     * @param eventStatus the event status
     * @return the ecp core ls event
     */
    public EcpCoreLSEvent listByStartDateAndStatus(Calendar eventStartDateBefore, Status eventStatus);

    /**
     * Update program Event based on start date and end date.
     * @param eventName the program event name
     */
    public Map<String, Object> updateEventByStartDateAndEndDate(final String eventName);
    
    /**
     * Update event by start date.
     * @param eventName the event name
     * @return the map
     */
    public Map<String, Object> updateEventByStartDate(final String eventName);

    public void updateById(Integer id);
}
