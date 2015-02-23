/*
 * ThermostatAlgorithmDaoImpl.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ecofactor.common.pojo.ThermostatAlgorithm;

/**
 * The Class ThermostatAlgorithmDaoImpl.
 * @author $Author: rvinoopraj $
 * @version $Rev: 16999 $ $Date: 2013-04-24 17:05:38 +0530 (Wed, 24 Apr 2013) $
 */
public class ThermostatAlgorithmDaoImpl extends BaseDaoImpl<ThermostatAlgorithm> implements
        ThermostatAlgorithmDao {

    /**
     * Find by thermostat id.
     * @param thermostatId the thermostat id
     * @return the list
     * @see com.ecofactor.qa.automation.dao.ThermostatAlgorithmDao#findByThermostatId(java.lang.Integer)
     */
    public List<ThermostatAlgorithm> findByThermostatId(Integer thermostatId) {

        List<ThermostatAlgorithm> thAlgorithmList = new ArrayList<ThermostatAlgorithm>();
        String query = "SELECT tAlgorithm from ThermostatAlgorithm tAlgorithm where tAlgorithm.thermostatId = :thermostatId";
        Map<String, Object> paramVals = new HashMap<String, Object>();
        paramVals.put("thermostatId", thermostatId);
        thAlgorithmList = listByQuery(query, paramVals);
        return thAlgorithmList;
    }

    /**
     * @param thermostatId
     * @return
     * @see com.ecofactor.qa.automation.dao.ThermostatAlgorithmDao#listBySubscribedAlgo(java.lang.Integer)
     */
    public List<String> listBySubscribedAlgo(Integer thermostatId) {

        List<String> thAlgorithmList = new ArrayList<String>();
        String query = "SELECT tSubAlgo.algorithm.name from ThermostatAlgorithm tSubAlgo where tSubAlgo.thermostatId = :thermostatId";
        Map<String, Object> paramVals = new HashMap<String, Object>();
        paramVals.put("thermostatId", thermostatId);
        thAlgorithmList = listStringByQuery(query, paramVals);
        return thAlgorithmList;
    }
}
