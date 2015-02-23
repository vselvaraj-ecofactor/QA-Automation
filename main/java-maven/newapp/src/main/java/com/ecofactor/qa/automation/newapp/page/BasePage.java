/*
 * BasePage.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.newapp.page;

import org.openqa.selenium.WebDriver;

import com.ecofactor.qa.automation.platform.action.UIAction;

/**
 * The Interface BasePage.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public interface BasePage {

    /**
     * Cleanup.
     */
    void cleanup();

    /**
     * Checks if is page loaded.
     * @return true, if is page loaded
     */
    boolean isPageLoaded();

    /**
     * Sets the driver.
     * @param driver the new driver
     */
    void setDriver(final WebDriver driver);

    /**
     * Gets the driver.
     * @return the driver
     */
    WebDriver getDriver();

    /**
     * Gets the toast error message.
     * @return the toast error message
     */
    String getToastErrorMessage();

    /**
     * Gets the action.
     * @return the action
     */
    UIAction getAction();

    /**
     * Sets the action.
     * @param action the new action
     */
    void setAction(final UIAction action);
}
