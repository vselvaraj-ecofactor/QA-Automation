/*
 * EcpCoreEnergyPricingDao.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.dao.ecp;

import com.ecofactor.common.pojo.EcpCore;
import com.ecofactor.common.pojo.EcpCoreEnergyPricing;

/**
 * The Interface EcpCoreEnergyPricingDao.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public interface EcpCoreEnergyPricingDao {

    /**
     * Find by ecp core and type.
     * @param ecpCore the ecp core
     * @param type the type
     * @return the ecp core energy pricing
     */
    EcpCoreEnergyPricing findByEcpCoreAndType(final EcpCore ecpCore, final String type);
}
