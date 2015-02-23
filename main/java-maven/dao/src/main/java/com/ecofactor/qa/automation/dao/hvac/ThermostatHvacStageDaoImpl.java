/*
 * ThermostatHvacStageDaoImpl.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.dao.hvac;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ecofactor.common.pojo.ThermostatHvacStage;
import com.ecofactor.qa.automation.dao.BaseDaoImpl;

/**
 * The Class ThermostatHvacStageDaoImpl.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class ThermostatHvacStageDaoImpl extends BaseDaoImpl<ThermostatHvacStage> implements
        ThermostatHvacStageDao {

    /**
     * Find by tstat id.
     * @param thermostatId the thermostat id
     * @return the list
     * @see com.ecofactor.qa.automation.dao.hvac.ThermostatHvacStageDao#findByTstatId(java.lang.Integer)
     */
    @Override
    public List<ThermostatHvacStage> findByTstatId(Integer thermostatId) {

        final String query = "SELECT ths FROM ThermostatHvacStage ths WHERE ths.thermostatId = :thermostatId";
        final Map<String, Object> paramVals = new HashMap<String, Object>();
        paramVals.put("thermostatId", thermostatId);
        return listByQuery(query, paramVals);
    }

}
