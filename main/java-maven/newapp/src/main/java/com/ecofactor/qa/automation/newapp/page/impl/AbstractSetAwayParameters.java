/*
 * AbstractSetAwayParameters.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.newapp.page.impl;

import static com.ecofactor.qa.automation.platform.enums.CustomTimeout.MEDIUM_TIMEOUT;
import static com.ecofactor.qa.automation.platform.enums.CustomTimeout.SHORT_TIMEOUT;
import static com.ecofactor.qa.automation.platform.enums.CustomTimeout.TINY_TIMEOUT;

import static com.ecofactor.qa.automation.platform.util.Pageutil.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.ecofactor.qa.automation.newapp.enums.SetAwayParams;

/**
 * The Class AbstractSetAwayParameters.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public abstract class AbstractSetAwayParameters extends AbstractAuthenticationPageImpl {

    private static final String CURRENT_VALUE = "div.dw-li.dw-v.dw-sel";
    private static final String DATA_VALUE = "data-val";

    /**
     * Save away settings.
     * @return true, if successful
     */
    protected boolean saveAwaySettings() {

        final WebElement setElement = getElement(getDriver(), By.cssSelector("a.dwb.dwb0.dwb-e"),
                TINY_TIMEOUT);
        getAction().click(setElement);

        return isNotDisplayed(getDriver(), By.cssSelector(CURRENT_VALUE), TINY_TIMEOUT);
    }

    /**
     * Close away settings popup.
     */
    protected boolean cancelAwaySettings() {

        final WebElement cancelElement = getElement(getDriver(),
                By.cssSelector("a.dwb.dwb1.dwb-e"), MEDIUM_TIMEOUT);
        getAction().click(cancelElement);
        return isNotDisplayed(getDriver(), By.cssSelector(CURRENT_VALUE), TINY_TIMEOUT);
    }

    /**
     * Change value.
     * @param value the value
     * @return the integer
     */
    public Integer changeAwayParamValue(final SetAwayParams awayParams, final int value) {

        final int noOfSetPoints = Math.abs(value);

        WebElement containerElement = getElement(getDriver(), By.cssSelector("div.dwwl.dwwl0"),
                TINY_TIMEOUT);

        WebElement currentReadingElement = getElementBySubElement(getDriver(), containerElement,
                By.cssSelector(CURRENT_VALUE), TINY_TIMEOUT);
        String currentReadingValue = currentReadingElement.getAttribute(DATA_VALUE);

        final WebElement upOrDownArrow = value > 0 ? getElementBySubElement(getDriver(),
                containerElement, By.cssSelector("a.dwb-e.dwwb.dwwbm"), SHORT_TIMEOUT)
                : getElementBySubElement(getDriver(), containerElement,
                        By.cssSelector("a.dwb-e.dwwb.dwwbp"), SHORT_TIMEOUT);

        for (int count = 0; count < noOfSetPoints; count++) {
            getAction().click(upOrDownArrow);
        }

        currentReadingElement = getElementBySubElement(getDriver(), containerElement,
                By.cssSelector(CURRENT_VALUE), TINY_TIMEOUT);
        currentReadingValue = currentReadingElement.getAttribute(DATA_VALUE);

        saveAwaySettings();
        return Integer.parseInt(currentReadingValue);
    }

}
