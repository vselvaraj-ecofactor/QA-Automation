/*
 * RegionDaoImpl.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ecofactor.common.pojo.Region;

/**
 * The Class RegionDaoImpl.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class RegionDaoImpl extends BaseDaoImpl<Region> implements RegionDao {

    /**
     * Gets the available zipcodes.
     * @param ecpCoreId the ecp core id
     * @return the available zipcodes
     * @see com.ecofactor.qa.automation.dao.RegionDao#getAvailableZipcodes(int)
     */
    @Override
    public List<Region> getAvailableZipcodes(int ecpCoreId) {

        String sql = " select distinct e from Location c,EcpCoreLocation d, Region e "
                + " where  e.id = c.region.id and d.location.id=c.id "
                + " and c.status='ACTIVE' and e.status='ACTIVE' and d.ecpCore.id=:ecpCore  order by c.id";
        Map<String, Object> paramVals = new HashMap<String, Object>();
        paramVals.put("ecpCore", ecpCoreId);
        List<Region> region = listByQuery(sql, paramVals);
        return region;
    }

}
