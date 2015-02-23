/*
 * ThermostatAlgorithmDao.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.dao;

import java.util.List;

import com.ecofactor.common.pojo.ThermostatAlgorithm;

/**
 * The Interface ThermostatAlgorithmDao.
 * @author $Author: rvinoopraj $
 * @version $Rev: 16999 $ $Date: 2013-04-24 17:05:38 +0530 (Wed, 24 Apr 2013) $
 */
public interface ThermostatAlgorithmDao extends BaseDao<ThermostatAlgorithm> {

    /**
     * Find by thermostat id.
     * @param thermostatId the thermostat id
     * @return the list
     */
    List<ThermostatAlgorithm> findByThermostatId(Integer thermostatId);

    /**
     * List by subscribed algo.
     * @param thermostatId the thermostat id
     * @return the list
     */
    List<String> listBySubscribedAlgo(Integer thermostatId);

}
