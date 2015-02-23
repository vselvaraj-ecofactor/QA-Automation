/*
 * LSProgramEventDaoImpl.java
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

import com.ecofactor.common.pojo.EcpCoreLSEvent;
import com.ecofactor.common.pojo.Status;

/**
 * The Class LSProgramEventDaoImpl.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class LSProgramEventDaoImpl extends BaseDaoImpl<EcpCoreLSEvent> implements LSProgramEventDao {

    /**
     * @param eventName
     * @param eventStatus
     * @return
     * @see com.ecofactor.qa.automation.dao.LSProgramEventDao#listByEventName(java.lang.String,
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
     * @param eventName
     * @return
     * @see com.ecofactor.qa.automation.dao.LSProgramEventDao#findLatestByEventName(java.lang.String)
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
     */
	public EcpCoreLSEvent listByStartDateAndStatus(Calendar eventStartDateBefore, Status eventStatus) {

		String sql = "select e from EcpCoreLSEvent e where e.startDate < :event_before_date and e.status = :event_status";
		Map<String, Object> paramVals = new HashMap<String, Object>();
		paramVals.put("event_before_date", eventStartDateBefore);
		paramVals.put("event_status", eventStatus);
		EcpCoreLSEvent events = findByQuery(sql, paramVals);
		return events;
	}
}
