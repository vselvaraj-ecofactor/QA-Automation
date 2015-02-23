/*
 * DesktopDriverManager.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.platform;

import static com.ecofactor.qa.automation.platform.util.CheckJenkinsParamUtil.*;
import static com.ecofactor.qa.automation.platform.util.LogUtil.*;

import org.openqa.selenium.WebDriver;
import com.ecofactor.qa.automation.platform.util.SeleniumDriverUtil;

/**
 * The Class DesktopDriverManager is used to create WebDriver instances for different desktop
 * browser types.
 * @author $Author: vprasannaa $
 * @version $Rev: 30955 $ $Date: 2014-06-06 12:32:22 +0530 (Fri, 06 Jun 2014) $
 */
public class DesktopDriverManager {

    public static final String BROWSER = "browser";
    public static final String FIREFOX = "firefox";
    public static final String IE_BROWSER = "ie";
    public static final String IE_BROWSER8 = "ie8";
    public static final String IE_BROWSER9 = "ie9";
    public static final String I_EXPLORER = "iexplorer";
    public static final String VISTAIE8 = "vistaie8";
    public static final String IE_BROWSER10 = "ie10";
    public static final String CHROME = "chrome";
    public static final String SAFARI = "safari";

    /**
     * The driver.
     */
    private static WebDriver driver;

    /**
     * Creates the admin driver.
     */
    public static void createAdminDriver() {

        setLogString("Initialize driver for admin validation.", true);
        final String webBrowser = hasJenkinsAdminBrowserParam() ? System
                .getProperty("adminBrowser") : CHROME;
        driver = SeleniumDriverUtil.createBrowserDriver(webBrowser);
        driver.manage().window().maximize();
    }

    /**
     * Gets the admin driver.
     * @return the admin driver
     */
    public WebDriver getAdminDriver() {

        if (driver == null) {
            createAdminDriver();
        }
        return driver;
    }

    /**
     * Quit admin driver.
     */
    public static void quitAdminDriver() {

        if (driver != null) {
            driver.quit();
        }
    }

}
