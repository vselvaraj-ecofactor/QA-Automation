/*
 * AbstractBasePageImpl.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.newapp.page.impl;

import org.openqa.selenium.WebDriver;

import com.ecofactor.qa.automation.newapp.page.BasePage;
import com.ecofactor.qa.automation.platform.action.UIAction;
import com.ecofactor.qa.automation.platform.ops.TestOperations;
import com.google.inject.Inject;

/**
 * The Class BasePageImpl.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public abstract class AbstractBasePageImpl implements BasePage {

    /**
     * The ui action.
     */
    @Inject
    protected UIAction uiAction;

    /**
     * The test ops.
     */
    @Inject
    protected TestOperations testOps;

    /**
     * The driver.
     */
    private WebDriver driver;

    /**
     * The action.
     */
    private UIAction action;

    /**
     * Gets the action.
     * @return the action
     */
    @Override
    public UIAction getAction() {
    
        return action == null ? uiAction : action;
    }

    /**
     * Sets the action.
     * @param action the new action
     */
    @Override
    public void setAction(final UIAction action) {
    
        this.action = action;
    }

    /**
     * Gets the driver.
     * @return the driver
     */
    @Override
    public WebDriver getDriver() {

        return driver == null ? testOps.getDeviceDriver() : driver;
    }

    /**
     * Sets the driver.
     * @param driver the new driver
     */
    @Override
    public void setDriver(final WebDriver driver) {

        this.driver = driver;
    }

    /**
     * Gets the toast error message.
     * @return the toast error message
     */
    public String getToastErrorMessage() {

        return "";
    }

}
