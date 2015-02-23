/*
 * AlgorithmDaoImpl.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.dao;

import java.util.HashMap;
import java.util.Map;

import com.ecofactor.common.pojo.Algorithm;

/**
 * The Class AlgorithmDaoImpl.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class AlgorithmDaoImpl extends BaseDaoImpl<Algorithm> implements AlgorithmDao {

    /**
     * Find by id.
     * @param id the id
     * @return the algorithm
     * @see com.ecofactor.qa.automation.dao.AlgorithmDao#findById(java.lang.Integer)
     */
    public Algorithm findById(Integer id) {

        String ql = "SELECT a FROM Algorithm a WHERE a.id = :algorithmId";
        Map<String, Object> paramVals = new HashMap<String, Object>();
        paramVals.put("algorithmId", id);

        Algorithm algorithm = findByQuery(ql, paramVals);
        return algorithm;
    }
}
