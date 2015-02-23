/*
 * LocationDaoImpl.java
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

import com.ecofactor.common.pojo.Location;

/**
 * The Class LocationDaoImpl.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class LocationDaoImpl extends BaseDaoImpl<Location> implements LocationDao {

    /**
     * @param userdId
     * @return
     * @see com.ecofactor.qa.automation.dao.LocationDao#getLocationByUserId(java.lang.Integer)
     */
    @Override
    public List<Location> getLocationByUserId(Integer userdId) {

        String sql = "SELECT loc from Location loc where loc.user.id =:userId and status = 'ACTIVE' order by name";
        Map<String, Object> paramVals = new HashMap<String, Object>();
        paramVals.put("userId", userdId);
        List<Location> results = listByQuery(sql, paramVals);
        return results;
    }

    @Override
    public Location getLocationForAThermostat(Integer thermostatId)
    {
        String ql = "SELECT loc FROM Location loc, Thermostat tstat WHERE  loc.id =  tstat.locationid and tstat.id=:thermostatId" ;
        Map<String, Object> paramVals = new HashMap<String, Object>();
        paramVals.put("thermostatId", thermostatId);
        Location location = findByQuery(ql, paramVals);
        return location;
    }

    @Override
    public List<Location> getLocationByUserName(String userName)
    {
        String sql = "SELECT loc FROM Location loc, User user WHERE  loc.user.id =  user.id and user.username=:userName" ;
        Map<String, Object> paramVals = new HashMap<String, Object>();
        paramVals.put("userName", userName);
        List<Location> results = listByQuery(sql, paramVals);
        return results;
    }
}
