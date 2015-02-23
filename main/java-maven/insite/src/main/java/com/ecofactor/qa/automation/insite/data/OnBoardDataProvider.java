/*
 * OnBoardDataProvider.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.insite.data;

import java.lang.reflect.Method;

import org.testng.annotations.DataProvider;

import com.ecofactor.qa.automation.insite.config.InsiteLoginTestConfig;
import com.ecofactor.qa.automation.insite.config.OnBoardTestConfig;

import com.google.inject.Inject;

/**
 * The Class OnBoardDataProvider.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class OnBoardDataProvider {

    @Inject
    private static OnBoardTestConfig onBoardTestConfig;
    @Inject
    private static InsiteLoginTestConfig loginTestConfig;

    /**
     * Address verification data.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "validOnboardLogin")
    public static Object[][] validOnboardLogin(Method m) {

        Object[][] data = { { onBoardTestConfig.get(OnBoardTestConfig.BULK_UPLOAD_USERNAME),
                onBoardTestConfig.get(OnBoardTestConfig.BULK_UPLOAD_PSWD) } };
        return data;
    }

    @SuppressWarnings("static-access")
    @DataProvider(name = "validOnboardLoginAndValidLogin")
    public static Object[][] validOnboardLoginAndValidLogin(Method m) {

        Object[][] data = { { onBoardTestConfig.get(OnBoardTestConfig.BULK_UPLOAD_USERNAME),
                onBoardTestConfig.get(OnBoardTestConfig.BULK_UPLOAD_PSWD),
                loginTestConfig.get(loginTestConfig.VALID_LOGIN_USER),
                loginTestConfig.get(loginTestConfig.VALID_LOGIN_PASSWORD)
        } };
        return data;
    }


}
