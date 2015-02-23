/*
 * DemandSideDataProvider.java
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

import com.ecofactor.qa.automation.insite.config.UploadOneUserTestConfig;
import com.google.inject.Inject;

/**
 * The Class DemandSideDataProvider.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class UploadOneUserDataProvider {

    @Inject
    private static UploadOneUserTestConfig uploadOneUserTestConfig;

    /**
     * Valid login.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "createUser")
    public static Object[][] createUser(Method m) {

        @SuppressWarnings("static-access")
		Object[][] data = { { uploadOneUserTestConfig.get(uploadOneUserTestConfig.VALID_LOGIN_USER),
        	uploadOneUserTestConfig.get(uploadOneUserTestConfig.VALID_LOGIN_PASSWORD)
        } };
        return data;
    }

}
