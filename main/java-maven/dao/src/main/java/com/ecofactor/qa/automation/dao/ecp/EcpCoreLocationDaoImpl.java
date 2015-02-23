/*
 * EcpCoreLocationDaoImpl.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.dao.ecp;

import java.util.HashMap;
import java.util.Map;

import com.ecofactor.common.pojo.EcpCoreLocation;
import com.ecofactor.qa.automation.dao.BaseDaoImpl;

/**
 * The Class EcpCoreLocationDaoImpl.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class EcpCoreLocationDaoImpl extends BaseDaoImpl<EcpCoreLocation> implements
        EcpCoreLocationDao {

    /**
     * Find by id.
     * @param locationId the location id
     * @return the ecp core location
     * @see com.ecofactor.qa.automation.dao.ecp.EcpCoreLocationDao#findById(java.lang.Integer)
     */
    @Override
    public EcpCoreLocation findById(final Integer locationId) {

        final String query = "SELECT ecl FROM EcpCoreLocation ecl WHERE ecl.locationId = :locationId";
        final Map<String, Object> paramVals = new HashMap<String, Object>();
        paramVals.put("locationId", locationId);
        return findByQuery(query, paramVals);
    }

}
