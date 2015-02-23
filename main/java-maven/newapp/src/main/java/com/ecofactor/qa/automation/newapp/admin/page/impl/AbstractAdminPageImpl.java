/*
 * AbstractBasePageImpl.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.newapp.admin.page.impl;

import org.openqa.selenium.WebDriver;

import com.ecofactor.qa.automation.newapp.page.BasePage;
import com.ecofactor.qa.automation.newapp.page.impl.AbstractBasePageImpl;
import com.ecofactor.qa.automation.platform.DesktopDriverManager;
import com.google.inject.Inject;

/**
 * The Class BasePageImpl.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public abstract class AbstractAdminPageImpl extends AbstractBasePageImpl implements BasePage {

    /**
     * The driver manager.
     */
    @Inject
    protected DesktopDriverManager driverManager;

    /**
     * Gets the driver.
     * @return the driver
     */
    public WebDriver getDriver() {

        return driverManager.getAdminDriver();
    }
}
