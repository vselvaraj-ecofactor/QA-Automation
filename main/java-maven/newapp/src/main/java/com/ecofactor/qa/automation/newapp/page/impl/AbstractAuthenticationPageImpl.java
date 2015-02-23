/*
 * AbstractAuthenticationPageImpl.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.newapp.page.impl;

import static com.ecofactor.qa.automation.platform.enums.CustomTimeout.*;
import static com.ecofactor.qa.automation.platform.util.LogUtil.*;
import static com.ecofactor.qa.automation.platform.util.Pageutil.*;
import static com.ecofactor.qa.automation.util.WaitUtil.*;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

/**
 * The Class AbstractAuthenticationPageImpl.
 * @author $Author: vprasannaa $
 * @version $Rev: 32914 $ $Date: 2014-12-02 18:33:58 +0530 (Tue, 02 Dec 2014) $
 */
public abstract class AbstractAuthenticationPageImpl extends AbstractBasePageImpl {

    /** The Constant TSTAT_CONTAINER. */
    private static final String TSTAT_CONTAINER = "div.thermostat_container";

    /** The Constant ERROR_MESSAGE. */
    private static final String ERROR_MESSAGE = "div.toastMessage";

    /** The Constant TARGET_TEMPERATURE. */
    @SuppressWarnings("unused")
    private static final String TARGET_TEMPERATURE = "thermostatTargetSetpoint";

    /** The Constant THERMOSTAT_BUTTON_CONTAINER. */
    private static final String THERMOSTAT_BUTTON_CONTAINER = ".thermostat_button_container";

    /**
     * Gets the current thermostat container.
     * @return the current thermostat container
     */
    public WebElement getCurrentThermostatContainer() {

        // isDisplayed(getDriver(), By.cssSelector(TSTAT_CONTAINER), TINY_TIMEOUT);
        final List<WebElement> elementList = getElements(getDriver(),
                By.cssSelector(TSTAT_CONTAINER), TINY_TIMEOUT);
        for (final WebElement webElement : elementList) {
            return webElement;

        }
        return null;
    }

    /**
     * Gets the current thermostat Button container.
     * @return the current thermostat button container.
     */
    public WebElement getThermostatButtonContainer() {

        final List<WebElement> elementList = getElements(getDriver(),
                By.cssSelector(THERMOSTAT_BUTTON_CONTAINER), TINY_TIMEOUT);
        for (final WebElement webElement : elementList) {
            return webElement;

        }
        return null;
    }

    /**
     * Gets the toast error message.
     * @return the toast error message
     * @see com.ecofactor.qa.automation.newapp.page.BasePage#getToastErrorMessage()
     */
    public String getToastErrorMessage() {

        tinyWait();
        final WebElement errorElement = getElementBySubElement(getDriver(),
                getCurrentThermostatContainer(), By.cssSelector(ERROR_MESSAGE), TINY_TIMEOUT);
        if (errorElement.isDisplayed()) {
            final String thStatusMsg = errorElement.getText().toString();
            setLogString("Thermostat Toast Error message:" + thStatusMsg, true);
            return thStatusMsg;
        }
        return null;
    }

    /**
     * Click.
     * @param element the element
     */
    protected void click(WebElement element) {

        JavascriptExecutor executor = (JavascriptExecutor) getDriver();
        executor.executeScript("arguments[0].click();", element);
    }
}
