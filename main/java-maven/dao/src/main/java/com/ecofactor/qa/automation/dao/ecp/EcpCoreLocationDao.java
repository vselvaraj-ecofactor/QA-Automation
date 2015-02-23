/*
 * EcpCoreLocationDao.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.dao.ecp;

import com.ecofactor.common.pojo.EcpCoreLocation;

/**
 * The Interface EcpCoreLocationDao.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public interface EcpCoreLocationDao {

    /**
     * Find by id.
     * @param locationId the location id
     * @return the ecp core location
     */
    EcpCoreLocation findById(final Integer locationId);
}
