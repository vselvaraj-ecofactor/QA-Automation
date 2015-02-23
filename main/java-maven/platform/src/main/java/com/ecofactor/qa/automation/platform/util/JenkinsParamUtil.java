/*
 * JenkinsParamUtil.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.platform.util;

import java.util.Locale;

import com.ecofactor.qa.automation.platform.enums.CustomLogLevel;

/**
 * The Class JenkinsParamUtil.
 * @author $Author: vprasannaa $
 * @version $Rev: 31241 $ $Date: 2014-06-20 18:35:34 +0530 (Fri, 20 Jun 2014) $
 */
public final class JenkinsParamUtil {

    /**
     * Instantiates a new jenkins param util.
     */
    private JenkinsParamUtil() {

        // Empty constructor.
    }

    /**
     * Gets the jenkins job name.
     * @return the jenkins job name
     */
    public static CustomLogLevel getLogLevel() {

        final String logLevel = System.getProperty("logLevel");
        return logLevel == null ? CustomLogLevel.HIGH : CustomLogLevel.valueOf(logLevel
                .toUpperCase(Locale.getDefault()));
    }

    /**
     * Gets the selenium hub host.
     * @return the selenium hub host
     */
    public static String getSeleniumHubHost() {

        return System.getProperty("seleniumHubHost");
    }

    /**
     * Gets the selenium hub port.
     * @return the selenium hub port
     */
    public static String getSeleniumHubPort() {

        return System.getProperty("seleniumHubPort");
    }

    /**
     * Gets the device name.
     * @return the device name
     */
    public static String getDeviceName() {

        return System.getProperty("deviceName");
    }

    /**
     * Gets the app release id.
     * @return the app release id
     */
    public static String getAppReleaseId() {

        return System.getProperty("appReleaseId");
    }

    /**
     * Gets the platform name.
     * @return the platform name
     */
    public static String getPlatformName() {

        return System.getProperty("platform");
    }

    /**
     * Gets the version.
     * @return the version
     */
    public static String getVersion() {

        return System.getProperty("version");
    }

    /**
     * Gets the browser.
     * @return the browser
     */
    public static String getBrowser() {

        return System.getProperty("browser");
    }

    /**
     * Gets the project.
     * @return the project
     */
    public static String getProject() {

        return System.getProperty("project");
    }
}
