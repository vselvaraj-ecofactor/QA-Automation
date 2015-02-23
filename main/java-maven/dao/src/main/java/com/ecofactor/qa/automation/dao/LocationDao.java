/*
 * LocationDao.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */

package com.ecofactor.qa.automation.dao;

import java.util.List;

import com.ecofactor.common.pojo.Location;

/**
 * The Interface LocationDao.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public interface LocationDao extends BaseDao<Location> {

    /**
     * Gets the location by user id.
     * @param userdId the userd id
     * @return the location by user id
     */
    public List<Location> getLocationByUserId(Integer userdId);

    /**
     * Gets the location by user name.
     * @param userName the user name
     * @return the location by user name
     */
    public List<Location> getLocationByUserName(String userName);

    /**
     * Gets the location for a thermostat.
     * @param thermostatId the thermostat id
     * @return the location for a thermostat
     */
    public Location getLocationForAThermostat(Integer thermostatId);

}
