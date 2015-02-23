/*
 * RegionDao.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.dao;

import java.util.List;

import com.ecofactor.common.pojo.Region;

/**
 * The Interface RegionDao.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public interface RegionDao extends BaseDao<Region> {

    /**
     * Gets the available zipcodes.
     * @param lsProgramid the ls programid
     * @return the available zipcodes
     */
    public List<Region> getAvailableZipcodes(int lsProgramid);
}
