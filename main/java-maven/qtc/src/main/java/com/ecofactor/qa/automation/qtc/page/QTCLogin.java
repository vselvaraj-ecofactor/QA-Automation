/*
 * QTCLogin.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.qtc.page;


/**
 * The Interface QTCLogin.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public interface QTCLogin extends QTCPage {

    /**
     * Enter login.
     * @param userId the user id
     * @param password the password
     */
    public void enterLogin(String userId, String password);

    /**
     * Login.
     * @param userId the user id
     * @param password the password
     */
    public void login(String userId, String password);
}
