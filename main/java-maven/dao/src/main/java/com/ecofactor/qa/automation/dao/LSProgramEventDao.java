/*
 * LSProgramEventDao.java
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

import com.ecofactor.common.pojo.EcpCoreLSEvent;
import com.ecofactor.common.pojo.Status;

/**
 * The Interface LSProgramEventDao.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public interface LSProgramEventDao extends BaseDao<EcpCoreLSEvent> {

    /**
     * List by event name.
     * @param eventName the event name
     * @param eventStatus the event status
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

}
