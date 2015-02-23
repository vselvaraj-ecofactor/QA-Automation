/*
 * InstallationHardwareDataProvider.java
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

import com.ecofactor.qa.automation.insite.config.InstallationTestConfig;
import com.google.inject.Inject;

/**
 * The Class InstallationHardwareDataProvider.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class InstallationHardwareDataProvider {

    @Inject
    private static InstallationTestConfig installationTestConfig;

    /**
     * Valid login.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "validLogin")
    public static Object[][] validLogin(Method m) {

        Object[][] data = { { installationTestConfig.get(InstallationTestConfig.USER_SUPPORT_USRNAME),
                installationTestConfig.get(InstallationTestConfig.USER_SUPPORT_PSWD) } };
        return data;
    }

    /**
     * Test installation hw.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "testInstallationHW")
    public static Object[][] testInstallationHW(Method m) {

        Object[][] data = { { installationTestConfig.get(InstallationTestConfig.USER_SUPPORT_USRNAME),
                installationTestConfig.get(InstallationTestConfig.USER_SUPPORT_PSWD),
                installationTestConfig.get(InstallationTestConfig.INSTALLATION_HW_Street_Addr),
                installationTestConfig.get(InstallationTestConfig.INSTALLATION_HW_LOOP) } };
        return data;
    }

}
