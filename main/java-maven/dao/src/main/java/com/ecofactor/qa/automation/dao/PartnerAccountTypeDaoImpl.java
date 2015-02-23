/*
 * PartnerAccountTypeDaoImpl.java
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
import com.ecofactor.common.pojo.PartnerAccountType;
import com.ecofactor.common.pojo.PartnerType;


/**
 * The Class PartnerAccountTypeDaoImpl.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class PartnerAccountTypeDaoImpl extends BaseDaoImpl<PartnerAccountType> implements PartnerAccountTypeDao {

    /**
     * @param partnerAccount
     * @return
     * @see com.ecofactor.qa.automation.dao.PartnerAccountTypeDao#getPartnerAccountTypeByPartnerAcc(com.ecofactor.common.pojo.PartnerAccount)
     */
    @Override
    public PartnerAccountType getPartnerAccountTypeByPartnerAcc(PartnerAccount partnerAccount) {

        String sql = " select e from PartnerAccountType e " + " where  e.partnerAccount =:partnerAccount";
        Map<String, Object> paramVals = new HashMap<String, Object>();
        paramVals.put("partnerAccount", partnerAccount);
        PartnerAccountType user = findByQuery(sql, paramVals);
        return user;
    }

    /**
     * @param partnerAccount
     * @param partnerType
     * @see com.ecofactor.qa.automation.dao.PartnerAccountTypeDao#updatePartnerType(com.ecofactor.common.pojo.PartnerAccount, com.ecofactor.common.pojo.PartnerType)
     */
    @Override
    public boolean updatePartnerType(PartnerAccount partnerAccount, PartnerType partnerType) {

        String sql = " select e from PartnerAccountType e " + " where  e.partnerAccount =:partnerAccount";
        Map<String, Object> paramVals = new HashMap<String, Object>();
        paramVals.put("partnerAccount", partnerAccount);
        PartnerAccountType partnerAccountType = findByQuery(sql, paramVals);
        partnerAccountType.setPartnerType(partnerType);
        System.out.println(partnerType.getId());
        return updateEntity(partnerAccountType);
        
    }

}
