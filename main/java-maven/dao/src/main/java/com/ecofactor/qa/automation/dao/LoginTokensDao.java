/*
 * LoginTokensDao.java
 * Copyright (c) 2012, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.dao;

import java.util.List;

import com.ecofactor.qa.automation.pojo.LoginTokens;

// TODO: Auto-generated Javadoc
/**
 * The Interface LoginTokensDao.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public interface LoginTokensDao extends BaseDao<LoginTokens> {

    /**
     * Gets the details by user name.
     * @param userName the user name
     * @return the details by user name
     */
    public List<LoginTokens> getDetailsByUserName(final String userName);

    /**
     * Update null by user name.
     * @param userName the user name
     * @return true, if successful
     */
    public boolean updateNullByUserName(final String userName);

    /**
     * Find by user name.
     * @param userName the user name
     * @return the login tokens
     */
    public LoginTokens findByUserName(final String userName);
}
