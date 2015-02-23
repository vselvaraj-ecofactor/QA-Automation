/*
 * PartnerAccountTypeDao.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.dao;


import com.ecofactor.common.pojo.PartnerAccount;
import com.ecofactor.common.pojo.PartnerAccountType;
import com.ecofactor.common.pojo.PartnerType;


/**
 * The Interface PartnerAccountTypeDao.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public interface PartnerAccountTypeDao extends BaseDao<PartnerAccountType> {

    /**
     * Gets the partner account type by partner acc.
     * @param partnerAccount the partner account
     * @return the partner account type by partner acc
     */
    public PartnerAccountType getPartnerAccountTypeByPartnerAcc(PartnerAccount partnerAccount);
    
    /**
     * Update partner type.
     * @param partnerAccount the partner account
     * @param partnerType the partner type
     * @return TODO
     */
    public boolean updatePartnerType(PartnerAccount partnerAccount, PartnerType partnerType);
}
