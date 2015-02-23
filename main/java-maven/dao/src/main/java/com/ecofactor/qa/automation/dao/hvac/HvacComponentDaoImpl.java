/*
 * HvacComponentDaoImpl.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.dao.hvac;

import java.util.HashMap;
import java.util.Map;

import com.ecofactor.common.pojo.HvacComponent;
import com.ecofactor.qa.automation.dao.BaseDaoImpl;

/**
 * The Class HvacComponentDaoImpl.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class HvacComponentDaoImpl extends BaseDaoImpl<HvacComponent> implements HvacComponentDao {

    /**
     * Find by id.
     * @param hvacComponentId the id
     * @return the hvac component
     * @see com.ecofactor.qa.automation.dao.hvac.HvacComponentDao#findById(java.lang.Integer)
     */
    @Override
    public HvacComponent findById(final Integer hvacComponentId) {

        final String query = "SELECT hc FROM HvacComponent hc WHERE hc.id = :hvacComponentId";
        final Map<String, Object> paramVals = new HashMap<String, Object>();
        paramVals.put("hvacComponentId", hvacComponentId);
        return findByQuery(query, paramVals);
    }

}
