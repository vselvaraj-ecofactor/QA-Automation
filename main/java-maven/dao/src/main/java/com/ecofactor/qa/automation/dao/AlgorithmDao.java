/*
 * AlgorithmDao.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.dao;

import com.ecofactor.common.pojo.Algorithm;

/**
 * The Interface AlgorithmDao.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public interface AlgorithmDao {

	/**
     * Find by id.
     * @param id the id
     * @return the algorithm
     */
	Algorithm findById(Integer id);
}
