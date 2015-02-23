/*
 * HvacModelDaoImpl.java
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

import com.ecofactor.common.pojo.HvacModel;
import com.ecofactor.qa.automation.dao.BaseDaoImpl;

/**
 * The Class HvacModelDaoImpl.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class HvacModelDaoImpl extends BaseDaoImpl<HvacModel>  implements HvacModelDao {

    /**
     * Find by id.
     * @param id the id
     * @return the hvac model
     * @see com.ecofactor.qa.automation.dao.hvac.HvacModelDao#findById(java.lang.Integer)
     */
    @Override
    public HvacModel findById(final Integer hvacModelId) {

        final String query = "SELECT hm FROM HvacModel hm WHERE hm.id = :hvacModelId";
        final Map<String, Object> paramVals = new HashMap<String, Object>();
        paramVals.put("hvacModelId", hvacModelId);
        return findByQuery(query, paramVals);
    }

}
