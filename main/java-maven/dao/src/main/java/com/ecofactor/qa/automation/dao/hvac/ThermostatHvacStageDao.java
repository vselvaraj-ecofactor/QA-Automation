/*
 * ThermostatHvacStageDao.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.dao.hvac;

import java.util.List;

import com.ecofactor.common.pojo.ThermostatHvacStage;

/**
 * The Interface ThermostatHvacStageDao.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public interface ThermostatHvacStageDao {

    /**
     * Find by id.
     * @param thermostatId the thermostat id
     * @return the thermostat hvac stage
     */
    List<ThermostatHvacStage> findByTstatId(final Integer thermostatId);

}
