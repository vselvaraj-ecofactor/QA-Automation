/*
 * LSProgramEventLocationDao.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.dao.dr;

import java.util.List;

import com.ecofactor.common.pojo.EcpCoreLSEvent;
import com.ecofactor.common.pojo.EcpCoreLSEventLocation;
import com.ecofactor.qa.automation.dao.BaseDao;

/**
 * The Interface LSProgramEventLocationDao.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public interface LSProgramEventLocationDao extends BaseDao<EcpCoreLSEventLocation> {

    /**
     * List by program event.
     * @param lsProgramEvent the ls program event
     * @return the list
     */
    public List<EcpCoreLSEventLocation> listByProgramEvent(final Integer lsProgramEvent);

    /**
     * List by program event and status.
     * @param lsProgramEvent the ls program event
     * @param eventStatus the event status
     * @return the list
     */
    public List<EcpCoreLSEventLocation> listByProgramEventAndStatus(final Integer lsProgramEvent,
            final String eventStatus);

    /**
     * List by program event location id and status.
     * @param lsProgramEvent the ls program event
     * @param locationId the location id
     * @param eventStatus the event status
     * @return the list
     */
    public List<EcpCoreLSEventLocation> listByProgramEventLocationId(
            final EcpCoreLSEvent lsProgramEvent, final Integer locationId);

    /**
     * List by location id.
     * @param locationId the location id
     * @return the list
     */
    public List<EcpCoreLSEventLocation> listByLocationId(final Integer locationId);

    /**
     * List by location id and status.
     * @param locationId the location id
     * @param eventStatus the event status
     * @return the list
     */
    public List<EcpCoreLSEventLocation> listByLocationIdAndStatus(final Integer locationId,
            final String eventStatus);

    /**
     * Fetch location Ids based on program event id.
     * @param programEventId the program event id.
     * @return the list.
     */
    public int listByProgramEventId(final Integer programEventId);

    /**
     * List by location id.
     * @param programEventId the program event id
     * @return the ecp core ls event location
     */
    public List<Integer> fetchByLocationId(final Integer programEventId);

    /**
     * List location id with status based on event id.
     * @param programEventId the program event id.
     * @return map.
     */
    public List<EcpCoreLSEventLocation> listByLocationIdStatus(Integer programEventId);

    /**
     * List location id with status based on event id.
     * @param programEventId the program event id.
     * @return map.
     */
    public List<String> fullListByLocationIdStatus(Integer programEventId);

}
