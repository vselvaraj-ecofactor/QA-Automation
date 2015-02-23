/*
 * LoginDataProvider.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.insite.data;

import static com.ecofactor.qa.automation.insite.config.InsiteLoginTestConfig.*;

import java.lang.reflect.Method;

import org.testng.annotations.DataProvider;

import com.ecofactor.qa.automation.insite.config.InsiteLoginTestConfig;
import com.google.inject.Inject;

/**
 * The Class LoginDataProvider.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class InsiteLoginDataProvider {

    @Inject
    private static InsiteLoginTestConfig loginTestConfig;

    /**
     * Valid login.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "testValidLoginCredentials")
    public static Object[][] validLogin(Method m) {

        Object[][] data = { { loginTestConfig.get(VALID_LOGIN_USER), loginTestConfig.get(VALID_LOGIN_PASSWORD) } };
        return data;
    }

    /**
     * In valid login.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "testInValidLoginCredentials")
    public static Object[][] testInValidLoginCredentials(Method m) {

        Object[][] data = { { loginTestConfig.get(INVALID_LOGIN_USER), loginTestConfig.get(INVALID_LOGIN_PASSWORD) } };
        return data;
    }

    /**
     * Reset password verification via email.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "testResetPasswordVerificationViaEmail")
    public static Object[][] resetPasswordVerificationViaEmail(Method m) {

        Object[][] data = { { loginTestConfig.get(CHANGE_PASSWORD_USERNAME),
                loginTestConfig.get(CHANGE_PASSWORD_EMAILURL), loginTestConfig.get(CHANGE_PASSWORD_EMAILID),
                loginTestConfig.get(CHANGE_PASSWORD_EMAIL_PWD), loginTestConfig.get(CHANGE_PASSWORD_SUBJECT),
                loginTestConfig.get(CHANGE_PASSWORD_NEW_PWD)
                 } };
        return data;
    }

    /**
     * Comcast credentials.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "comcastCredentials")
    public static Object[][] comcastCredentials(Method m) {
        Object[][] data = { { loginTestConfig.get(COMCAST_LOGIN_USER), loginTestConfig.get(COMCAST_LOGIN_PASSWORD) } };
        return data;
    }

}
