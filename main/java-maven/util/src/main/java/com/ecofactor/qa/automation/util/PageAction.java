/*
 * PageAction.java
 * Copyright (c) 2012, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.util;

import org.openqa.selenium.WebDriver;

import com.google.inject.Inject;

/**
 * PageAction is an abstract base class that provides the web driver to sub classes for initiating
 * any action in the page.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public abstract class PageAction {

    @Inject
    protected static DriverConfig driverConfig;

    /**
     * End.
     */
    public void end() {

        DriverConfig.closeDriver();
    }

    @SuppressWarnings("static-access")
    public WebDriver getDriver() {

        driverConfig.loadDriver();
        return driverConfig.getDriver();
    }
}
