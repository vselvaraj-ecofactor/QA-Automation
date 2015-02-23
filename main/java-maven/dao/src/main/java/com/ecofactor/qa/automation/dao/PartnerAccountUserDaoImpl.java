/*
 * PartnerAccountUserDaoImpl.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */

package com.ecofactor.qa.automation.dao;

import java.util.HashMap;
import java.util.Map;

import com.ecofactor.common.pojo.PartnerAccountUser;

/**
 * The Class PartnerAccountUserDaoImpl.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class PartnerAccountUserDaoImpl extends BaseDaoImpl<PartnerAccountUser> implements PartnerAccountUserDao {

    /**
     * Gets the partner account user by name.
     * @param partnerAccountUserName the partner account user name
     * @return the partner account user by name
     * @see com.ecofactor.qa.automation.dao.PartnerAccountUserDao#getPartnerAccountUserByName(java.lang.String)
     */
    @Override
    public PartnerAccountUser getPartnerAccountUserByName(String partnerAccountUserName) {

        String sql = " select e from PartnerAccountUser e " + " where  e.userName =:usrName";
        Map<String, Object> paramVals = new HashMap<String, Object>();
        paramVals.put("usrName", partnerAccountUserName);
        PartnerAccountUser user = findByQuery(sql, paramVals);
        return user;
    }

    @Override
    public int getPartnerAccountByAccessLogin(String accessLogin) {

        String sql = " select partner.partnerAccount.id from PartnerAccountUser partner where  partner.accessLogin = :accessLogin";
        Map<String, Object> paramVals = new HashMap<String, Object>();
        paramVals.put("accessLogin", accessLogin);
        int value = findByQueryInt(sql, paramVals);
        return value;

}

    @Override
    public PartnerAccountUser findById(int id) {

        String sql = " select partner.partnerAccount.id from PartnerAccountUser partner  where  partner.id =:id";
        Map<String, Object> paramVals = new HashMap<String, Object>();
        paramVals.put("id", id);
        PartnerAccountUser user = findByQuery(sql, paramVals);
        return user;
    }
}
