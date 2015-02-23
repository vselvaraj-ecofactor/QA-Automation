/*
 * PartnerTypeDao.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.dao;


import com.ecofactor.common.pojo.PartnerType;
import com.ecofactor.common.pojo.PartnerType.PartnerTypeName;

/**
 * The Interface PartnerTypeDao.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public interface PartnerTypeDao extends BaseDao<PartnerType> {

    /**
     * Gets the partner type by name.
     * @param partnerTypeName the partner type name
     * @return the partner type by name
     */
    public PartnerType getPartnerTypeByName(PartnerTypeName partnerTypeName);
}
