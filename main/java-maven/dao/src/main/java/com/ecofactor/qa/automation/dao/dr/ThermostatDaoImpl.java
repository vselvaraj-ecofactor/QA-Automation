/*
 * ThermostatDaoImpl.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.dao.dr;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ecofactor.common.pojo.Status;
import com.ecofactor.common.pojo.Thermostat;
import com.ecofactor.qa.automation.dao.BaseDaoImpl;

/**
 * The Class ThermostatDaoImpl.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class ThermostatDaoImpl extends BaseDaoImpl<Thermostat> implements ThermostatDao {

    /**
     * Fetch the thermostat Id based on Location Id.
     * @param locationId the location id.
     * @return Integer.
     * @see com.ecofactor.qa.automation.dao.dr.ThermostatDao#listByLoationId(java.lang.Integer)
     */
    @Override
    public int listByLoationId(Integer locationId) {

        String sql = "select e from Thermostat e where e.locationid= :locationId";
        Map<String, Object> paramVals = new HashMap<String, Object>();
        paramVals.put("locationId", locationId);
        Thermostat tsat = findByQuery(sql, paramVals);
        final int thermostatID = tsat.getId();
        return thermostatID;
    }

    /**
     * Count of thermostat bylocations.
     * @param programEventId the program event id
     * @return the int
     * @see com.ecofactor.qa.automation.dao.dr.ThermostatDao#countOfThermostatBylocations(java.lang.Integer)
     */
    @Override
    public long countOfThermostatBylocations(Integer programEventId) {

        /*
         * select sum(thermostat) from(select count(thermostat_id) as thermostat from
         * ef11qa_plat.ef_thermostat where location_id in (select location_id FROM
         * ef11qa_plat.ef_ls_program_event_location where ls_program_event_id=2747) group by
         * location_id) as a;
         */
        String sql = "select sum(thermostat) from(select count(e.id) as thermostat from Thermostat e where e.locationid in (select t.locationid from EcpCoreLSEventLocation t where t.ecpCoreLSEvent.id= :programEventId) group by e.locationid)as a";
        Map<String, Object> paramVals = new HashMap<String, Object>();
        paramVals.put("programEventId", programEventId);
        final long countOfThermostat = findByQueryInt(sql, paramVals);
        return countOfThermostat;
    }

    /**
     * @param programEventId
     * @param status
     * @return
     * @see com.ecofactor.qa.automation.dao.dr.ThermostatDao#countOfThermostatByStatus(java.lang.Integer,
     *      java.lang.String)
     */
    @Override
    public List<Thermostat> countOfThermostatByStatus(Integer programEventId, String status) {

        String sql = "SELECT tht FROM Thermostat tht WHERE tht.locationid = :locationId and tht.status = :status";
        Map<String, Object> paramVals = new HashMap<String, Object>();
        paramVals.put("locationId", programEventId);
        paramVals.put("status", Status.valueOf(status));
        List<Thermostat> thermostat = listByQuery(sql, paramVals);
        return thermostat;
    }

    /**
     * List thermostats by location.
     * @param locationId the location id
     * @return the list
     * @see com.ecofactor.qa.automation.dao.ThermostatDao#listThermostatsByLocation(java.lang.Integer)
     */
    public List<Thermostat> listThermostatsByLocation(Integer locationId) {

        String ql = "SELECT tht FROM Thermostat tht WHERE tht.locationid = :locationId";
        Map<String, Object> paramVals = new HashMap<String, Object>();
        paramVals.put("locationId", locationId);
        List<Thermostat> thermostat = listByQuery(ql, paramVals);
        // System.out.println(thermostat + "am here");
        return thermostat;
    }

}
