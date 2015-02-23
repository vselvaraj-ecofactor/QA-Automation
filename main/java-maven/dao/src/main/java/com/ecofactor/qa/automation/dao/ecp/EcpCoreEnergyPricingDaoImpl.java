/*
 * EcpCoreEnergyPricingDaoImpl.java
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

import com.ecofactor.common.pojo.EcpCore;
import com.ecofactor.common.pojo.EcpCoreEnergyPricing;
import com.ecofactor.qa.automation.dao.BaseDaoImpl;

/**
 * The Class EcpCoreEnergyPricingDaoImpl.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class EcpCoreEnergyPricingDaoImpl extends BaseDaoImpl<EcpCoreEnergyPricing> implements
        EcpCoreEnergyPricingDao {

    /**
     * Find by ecp core and type.
     * @param ecpCore the ecp core
     * @param type the type
     * @return the ecp core energy pricing
     * @see com.ecofactor.qa.automation.dao.ecp.EcpCoreEnergyPricingDao#findByEcpCoreAndType(com.ecofactor.common.pojo.EcpCore,
     *      java.lang.String)
     */
    @Override
    public EcpCoreEnergyPricing findByEcpCoreAndType(EcpCore ecpCore, String type) {

        final String query = "SELECT ecp FROM EcpCoreEnergyPricing ecp WHERE ecp.ecpCore = :ecpCore and ecp.type = :type";
        final Map<String, Object> paramVals = new HashMap<String, Object>();
        paramVals.put("ecpCore", ecpCore);
        paramVals.put("type", type);
        return findByQuery(query, paramVals);
    }

}
