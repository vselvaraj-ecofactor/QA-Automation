/*
 * ThermostatDao.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.dao.dr;

import java.util.List;

import com.ecofactor.common.pojo.Thermostat;
import com.ecofactor.qa.automation.dao.BaseDao;

// TODO: Auto-generated Javadoc
/**
 * The Interface ThermostatDao.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public interface ThermostatDao extends BaseDao<Thermostat> {

    /**
     * Fetch thermostat Id based on Location id.
     * @param locationId the location id.
     * @return Integer.
     */
    public int listByLoationId(Integer locationId);

    /**
     * Count of thermostat bylocations.
     * @param programEventId the program event id
     * @return the long
     */
    public long countOfThermostatBylocations(final Integer programEventId);

    /**
     * Count of thermostat by status.
     * @param programEventId the program event id
     * @param status the status
     * @return the long
     */
    public long countOfThermostatByStatus(final Integer programEventId, final String status);
    
    List<Thermostat> listThermostatsByLocation(Integer locationId);

}
