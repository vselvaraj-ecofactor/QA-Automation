/*
 * LSProgramEventLocationDaoImpl.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.dao.dr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.ecofactor.common.pojo.EcpCoreLSEvent;
import com.ecofactor.common.pojo.EcpCoreLSEventLocation;
import com.ecofactor.common.pojo.Status;
import com.ecofactor.qa.automation.dao.BaseDaoImpl;

/**
 * The Class LSProgramEventLocationDaoImpl.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class LSProgramEventLocationDaoImpl extends BaseDaoImpl<EcpCoreLSEventLocation> implements
        LSProgramEventLocationDao {

    /**
     * List by program event.
     * @param lsProgramEvent the ls program event
     * @return the list
     * @see com.ecofactor.qa.automation.dao.dr.LSProgramEventLocationDao#listByProgramEvent(com.ecofactor.common.pojo.EcpCoreLSEvent)
     */
    @Override
    public List<EcpCoreLSEventLocation> listByProgramEvent(final Integer lsProgramEvent) {

        final String sql = "SELECT lsEventLocation from EcpCoreLSEventLocation lsEventLocation where lsEventLocation.ecpCoreLSEvent.id=:lsEventId";
        final Map<String, Object> paramVals = new HashMap<String, Object>();
        paramVals.put("lsEventId", lsProgramEvent);
        List<EcpCoreLSEventLocation> values = listByQuery(sql, paramVals);
        return values;
    }

    /**
     * List by program event and status.
     * @param lsProgramEvent the ls program event
     * @param eventStatus the event status
     * @return the list
     * @see com.ecofactor.qa.automation.dao.dr.LSProgramEventLocationDao#listByProgramEventAndStatus(com.ecofactor.common.pojo.EcpCoreLSEvent,
     *      java.lang.String)
     */
    @Override
    public List<EcpCoreLSEventLocation> listByProgramEventAndStatus(final Integer lsProgramEvent,
            final String eventStatus) {

        final String sql = "SELECT lsEventLocation from EcpCoreLSEventLocation lsEventLocation where lsEventLocation.ecpCoreLSEvent.id=:lsEventId and lsEventlocation.status=:status";
        final Map<String, Object> paramVals = new HashMap<String, Object>();
        paramVals.put("lsEventId", lsProgramEvent);
        paramVals.put("status", Status.valueOf(eventStatus));
        List<EcpCoreLSEventLocation> values = listByQuery(sql, paramVals);
        return values;
    }

    /**
     * List by program event location id and status.
     * @param lsProgramEvent the ls program event
     * @param locationId the location id
     * @param eventStatus the event status
     * @return the list
     * @see com.ecofactor.qa.automation.dao.dr.LSProgramEventLocationDao#listByProgramEventLocationIdAndStatus(com.ecofactor.common.pojo.EcpCoreLSEvent,
     *      java.lang.Integer, java.lang.String)
     */
    @Override
    public List<EcpCoreLSEventLocation> listByProgramEventLocationId(
            final EcpCoreLSEvent lsProgramEvent, final Integer locationId) {

        final String sql = "select t from EcpCoreLSEventLocation t  where t.ecpCoreLSEvent = :ecpCoreLSEvent "
                + "and t.locationId = :locationId";
        final Map<String, Object> paramVals = new HashMap<String, Object>();
        paramVals.put("ecpCoreLSEvent", lsProgramEvent);
        paramVals.put("locationId", locationId);
     //   paramVals.put("status", Status.valueOf(eventStatus));
        return listByQuery(sql, paramVals);
    }

    /**
     * List by location id.
     * @param locationId the location id
     * @return the list
     * @see com.ecofactor.qa.automation.dao.dr.LSProgramEventLocationDao#listByLocationId(java.lang.Integer)
     */
    @Override
    public List<EcpCoreLSEventLocation> listByLocationId(final Integer locationId) {

        final String sql = "select t from EcpCoreLSEventLocation t  where t.locationId = :locationId";
        final Map<String, Object> paramVals = new HashMap<String, Object>();
        paramVals.put("locationId", locationId);
        return listByQuery(sql, paramVals);
    }

    /**
     * List by location id and status.
     * @param locationId the location id
     * @param eventStatus the event status
     * @return the list
     * @see com.ecofactor.qa.automation.dao.dr.LSProgramEventLocationDao#listByLocationIdAndStatus(java.lang.Integer,
     *      java.lang.String)
     */
    @Override
    public List<EcpCoreLSEventLocation> listByLocationIdAndStatus(final Integer locationId,
            final String eventStatus) {

        final String sql = "select t from EcpCoreLSEventLocation t  where t.locationId = :locationId and t.status = :status";
        final Map<String, Object> paramVals = new HashMap<String, Object>();
        paramVals.put("locationId", locationId);
        paramVals.put("status", Status.valueOf(eventStatus));
        return listByQuery(sql, paramVals);
    }

    /**
     * Fetch location Ids based on program event id.
     * @param programEventId the program id.
     * @return list.
     * @see com.ecofactor.qa.automation.dao.dr.LSProgramEventLocationDao#listByProgramEventId(java.lang.Integer)
     */
    @Override
    public int listByProgramEventId(Integer programEventId) {

        final String sql = "select distinct t.locationid from EcpCoreLSEventLocation t where t.ecpCoreLSEvent.id= :programEventId";
        final Map<String, Object> paramVals = new HashMap<String, Object>();
        paramVals.put("programEventId", programEventId);
        // List<EcpCoreLSEventLocation> values = listByQuery(sql, paramVals);
        final int locationId = findByQueryInt(sql, paramVals);
        return locationId;
    }

    /**
     * List by location id status.
     * @param programEventId the program event id
     * @return the ecp core ls event location
     * @see com.ecofactor.qa.automation.dao.dr.LSProgramEventLocationDao#fetchByLocationId(java.lang.Integer)
     */
    @Override
    public List<Integer> fetchByLocationId(Integer programEventId) {

        final String sql = "SELECT t FROM EcpCoreLSEventLocation AS t where t.ecpCoreLSEvent.id= :programEventId";
        final Map<String, Object> paramVals = new HashMap<String, Object>();
        List<Integer> locId = new ArrayList<Integer>();
        paramVals.put("programEventId", programEventId);
        List<EcpCoreLSEventLocation> values = listByQuery(sql, paramVals);
        for (EcpCoreLSEventLocation ecpCoreLSEventLocation : values) {

            locId.add(ecpCoreLSEventLocation.getLocationid());
        }
        return locId;
    }

    /**
     * List by location id status.
     * @param programEventId the program event id
     * @return the ecp core ls event location
     * @see com.ecofactor.qa.automation.dao.dr.LSProgramEventLocationDao#listByLocationIdStatus(java.lang.Integer)
     */
    @Override
    public List<String> fullListByLocationIdStatus(Integer programEventId) {

        final String sql = "SELECT t FROM EcpCoreLSEventLocation AS t where t.ecpCoreLSEvent.id= :programEventId";
        final Map<String, Object> paramVals = new HashMap<String, Object>();
        Map<Object, Object> locidAndStatus = new HashMap<Object, Object>();
        List<String> finallist = new ArrayList<String>();
        paramVals.put("programEventId", programEventId);
        List<EcpCoreLSEventLocation> values = listByQuery(sql, paramVals);
        for (EcpCoreLSEventLocation ecpCoreLSEventLocation : values) {
            locidAndStatus.put(ecpCoreLSEventLocation.getStatus(),
                    ecpCoreLSEventLocation.getLocationid());
            Iterator it = locidAndStatus.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pairs = (Map.Entry) it.next();

                finallist.add(pairs.getKey() + " = " + pairs.getValue());

            }

        }
        return finallist;
    }

    /**
     * List by location id status.
     * @param programEventId the program event id
     * @return the ecp core ls event location
     * @see com.ecofactor.qa.automation.dao.dr.LSProgramEventLocationDao#listByLocationIdStatus(java.lang.Integer)
     */
    @Override
    public List<EcpCoreLSEventLocation> listByLocationIdStatus(Integer programEventId) {

        final String sql = "SELECT t FROM EcpCoreLSEventLocation AS t where t.ecpCoreLSEvent.id= :programEventId";
        final Map<String, Object> paramVals = new HashMap<String, Object>();
        Map<Object, Object> locidAndStatus = new HashMap<Object, Object>();
        paramVals.put("programEventId", programEventId);
        List<EcpCoreLSEventLocation> values = listByQuery(sql, paramVals);
        for (EcpCoreLSEventLocation ecpCoreLSEventLocation : values) {
            locidAndStatus.put(ecpCoreLSEventLocation.getStatus(),
                    ecpCoreLSEventLocation.getLocationid());
        }
        return values;
    }

}
