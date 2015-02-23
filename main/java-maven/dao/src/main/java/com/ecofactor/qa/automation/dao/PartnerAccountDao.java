/*
 * PartnerAccountDao.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.dao;

import com.ecofactor.common.pojo.PartnerAccount;


/**
 * The Interface PartnerAccountDao.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public interface PartnerAccountDao extends BaseDao<PartnerAccount> {

    /**
     * Gets the partner account by name.
     * @param partnerAccountName the partner account name
     * @return the partner account by name
     */
    public PartnerAccount getPartnerAccountByName(String partnerAccountName);
}
