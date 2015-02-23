/*
 * PartnerAccountDaoImpl.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.dao;

import java.util.HashMap;
import java.util.Map;

import com.ecofactor.common.pojo.PartnerAccount;


/**
 * The Class PartnerAccountDaoImpl.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class PartnerAccountDaoImpl extends BaseDaoImpl<PartnerAccount> implements PartnerAccountDao{

    /**
     * Gets the partner account by name.
     * @param partnerAccountName the partner account name
     * @return the partner account by name
     * @see com.ecofactor.qa.automation.dao.PartnerAccountDao#getPartnerAccountByName(java.lang.String)
     */
    @Override
    public PartnerAccount getPartnerAccountByName(String partnerAccountName) {
        
        String sql = " select e from PartnerAccount e " + " where  e.name =:name";
        Map<String, Object> paramVals = new HashMap<String, Object>();
        paramVals.put("name", partnerAccountName);
        PartnerAccount user = findByQuery(sql, paramVals);
        return user;
    }

}
