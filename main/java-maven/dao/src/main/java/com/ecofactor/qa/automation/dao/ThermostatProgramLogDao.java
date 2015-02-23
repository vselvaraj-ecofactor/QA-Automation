/*
 * ThermostatProgramLogDao.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.dao;

import java.util.List;

import com.ecofactor.common.pojo.ThermostatProgramLog;

/**
 * The Interface ThermostatProgramLogDao.
 * @author $Author: dramkumar $
 * @version $Rev: 30638 $ $Date: 2014-05-20 20:53:08 +0530 (Tue, 20 May 2014) $
 */
public interface ThermostatProgramLogDao extends BaseDao<ThermostatProgramLog>
{

	/**
     * Find latest by thermostat.
     * @param thermostatId the thermostat id
     * @return the thermostat program log
     */
	ThermostatProgramLog findLatestByThermostat(Integer thermostatId);

	/**
     * List utc current day log.
     * @param thermostatId the thermostat id
     * @param currentDay the current day
     * @return the list
     */
	List<ThermostatProgramLog> listUTCCurrentDayLog(Integer thermostatId, int currentDay);

	/**
     * Find latest by thermostat active program type.
     * @param thermostatId the thermostat id
     * @param programType the program type
     * @return the list
     */
	List<ThermostatProgramLog> findLatestByThermostatActiveProgramType(Integer thermostatId, String programType);
}
