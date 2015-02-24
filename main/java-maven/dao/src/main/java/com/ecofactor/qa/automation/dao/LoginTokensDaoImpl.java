/*
 * LoginTokensDaoImpl.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ecofactor.qa.automation.pojo.LoginTokens;

/**
 * The Class LoginTokensDaoImpl.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class LoginTokensDaoImpl extends BaseDaoImpl<LoginTokens> implements LoginTokensDao {

    /**
     * Gets the details by user name.
     * @param userName the user name
     * @return the details by user name
     * @see com.ecofactor.qa.automation.dao.LoginTokensDao#getDetailsByUserName(java.lang.String)
     */
    @Override
    public List<LoginTokens> getDetailsByUserName(final String userName) {

        String sql = "select t from LoginTokens t where t.username = :userName";
        Map<String, Object> paramVals = new HashMap<String, Object>();
        paramVals.put("userName", userName);
        List<LoginTokens> results = listByQuery(sql, paramVals);
        return results;
    }

    /**
     * Update null by user name.
     * @param userName the user name
     * @return true, if successful
     * @see com.ecofactor.qa.automation.dao.LoginTokensDao#updateNullByUserName(java.lang.String)
     */
    @Override
    public boolean updateNullByUserName(final String userName) {

        String sql = "select t from LoginTokens t where t.username = :userName";
        Map<String, Object> paramVals = new HashMap<String, Object>();
        paramVals.put("userName", userName);
        List<LoginTokens> results = listByQuery(sql, paramVals);
        final String nullValue = " ";
        boolean updated = false;
        for (LoginTokens token : results) {
            token.setToken(nullValue);
            updated = updateEntity(token);
        }
        return updated;
    }

    /**
     * Find by user name.
     * @param userName the user name
     * @return the login tokens
     * @see com.ecofactor.qa.automation.dao.LoginTokensDao#findByUserName(java.lang.String)
     */
    @Override
    public LoginTokens findByUserName(final String userName) {

        String sql = "select t from LoginTokens t where t.username = :userName";
        Map<String, Object> paramVals = new HashMap<String, Object>();
        paramVals.put("userName", userName);
        LoginTokens results = findByQuery(sql, paramVals);
        return results;
    }

}
