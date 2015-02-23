/*
 * HvacComponentDao.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.dao.hvac;

import com.ecofactor.common.pojo.HvacComponent;

/**
 * The Interface HvacComponentDao.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public interface HvacComponentDao {

    /**
     * Find by id.
     * @param id the id
     * @return the hvac component
     */
    HvacComponent findById(final Integer id);
}
