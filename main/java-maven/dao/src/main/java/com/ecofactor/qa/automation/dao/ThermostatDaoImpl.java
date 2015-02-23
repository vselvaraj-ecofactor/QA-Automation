/*
 * ThermostatDaoImpl.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.ecofactor.common.pojo.Thermostat;

/**
 * The Class ThermostatDaoImpl.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class ThermostatDaoImpl extends BaseDaoImpl<Thermostat> implements ThermostatDao {

    /**
     * Find by thermostat id.
     * @param thermostatId the thermostat id
     * @return the thermostat
     * @see com.ecofactor.qa.automation.dao.ThermostatDao#findByThermostatId(java.lang.String)
     */
    @Override
    public Thermostat findByThermostatId(String thermostatId) {

        String ql = "SELECT tht FROM Thermostat tht WHERE tht.id = :thermostatId";
        Map<String, Object> paramVals = new HashMap<String, Object>();
        paramVals.put("thermostatId", Integer.parseInt(thermostatId));
        Thermostat thermostat = findByQuery(ql, paramVals);
        return thermostat;
    }

    /**
     * Find thermostats by ecp core and rule string.
     * @param ecpCoreId the ecp core id
     * @param ruleString the rule string
     * @return the list
     * @see com.ecofactor.qa.automation.dao.ThermostatDao#findThermostatsByEcpCoreAndRuleString(java.lang.Integer,
     *      java.lang.String)
     */
    @Override
    public List<Thermostat> findThermostatsByEcpCoreAndRuleString(Integer ecpCoreId, String ruleString) {

        String ql = "select * from ef_thermostat where  thermostat_id in ( select distinct b.thermostat_id "
                + " from ef_user a,ef_thermostat b,ef_location c,ef_ecp_core_location d, ef_region e "
                + " where a.user_id=c.user_id and b.location_id = c.location_id and b.location_id=d.location_id  and e.region_id = c.region_id "
                + " and b.thermostat_status='ACTIVE' and c.location_status='ACTIVE' and e.region_status='ACTIVE' "
                + " and e.zip in (:zips)  and d.ecp_core_id =: ecpcore order by d.ecp_core_id,a.user_name )";
        Map<String, Object> paramVals = new HashMap<String, Object>();
        paramVals.put("ecpcore", ecpCoreId);
        paramVals.put("zips", ruleString);
        List<Thermostat> thermostat = listByQuery(ql, paramVals);
        return thermostat;
    }

    /**
     * Gets the thermostat id for user.
     * @param userName the user name
     * @return the thermostat id for user
     * @see com.ecofactor.qa.automation.dao.ThermostatDao#getThermostatIdForUser(java.lang.String)
     */
    public List<Integer> getThermostatIdForUser(String userName) {

        EntityManager emObj = getEntityManager();
        String nativeQuery1 = "select t.thermostat_id" + " from ef_thermostat t, ef_location l, ef_user u" + " where t.thermostat_status = 'ACTIVE' and t.location_id = l.location_id and l.user_id = u.user_id"
                + " and u.user_name ='" + userName + "'";
        Query query1 = emObj.createNativeQuery(nativeQuery1);
        @SuppressWarnings("unchecked")
        List<Integer> thermostatList = query1.getResultList();
        return thermostatList;
    }

    /**
     * List thermostats by location id.
     * @param locationId the location id
     * @param thermostatId the thermostat id
     * @return the list
     * @see com.ecofactor.qa.automation.dao.ThermostatDao#listThermostatsByLocationId(java.lang.Integer)
     */
    public List<Thermostat> listThermostatsByLocationId(Integer locationId, Integer thermostatId) {

        String ql = "SELECT tht FROM Thermostat tht WHERE tht.locationid = :locationId and tht.id= :thermostatId";
        Map<String, Object> paramVals = new HashMap<String, Object>();
        paramVals.put("locationId", locationId);
        paramVals.put("thermostatId", thermostatId);
        List<Thermostat> thermostat = listByQuery(ql, paramVals);
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
        return thermostat;
    }
}
