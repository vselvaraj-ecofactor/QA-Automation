/*
 * LocationSwitcherOpsPageImpl.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.newapp.page.impl;

import static com.ecofactor.qa.automation.platform.enums.CustomTimeout.*;
import static com.ecofactor.qa.automation.platform.util.Pageutil.*;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.ecofactor.qa.automation.newapp.page.LocationSwitcherOpsPage;
import com.ecofactor.qa.automation.platform.enums.CustomLogLevel;
import com.ecofactor.qa.automation.platform.util.LogUtil;

/**
 * The Class LocationSwitcherOpsPageImpl.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class LocationSwitcherOpsPageImpl extends AbstractAuthenticationPageImpl implements
        LocationSwitcherOpsPage {

    private static final String THERMOSTAT_ROW = "div.thermostat_row";
    private static final String THERMOSTAT_ID = "thermostatid";
    private static final String CLOSE_BTN = "div.ctaButton.close_button";
    private static final String LOCATION_ROW = ".location_row";
    private static final String LOCATION_SWITCHER_BACKGROUND = "thermostat_switcher_modal_background";

    /**
     * Cleanup.
     * @see com.ecofactor.qa.automation.newapp.page.BasePage#cleanup()
     */
    @Override
    public void cleanup() {

    }

    /**
     * Checks if is page loaded.
     * @return true, if is page loaded
     * @see com.ecofactor.qa.automation.newapp.page.BasePage#isPageLoaded()
     */
    @Override
    public boolean isPageLoaded() {

        LogUtil.setLogString("Verify the Location Switcher Pop up is loaded", true,
                CustomLogLevel.LOW);
        boolean isLoaded = isDisplayed(getDriver(), By.cssSelector(CLOSE_BTN), SHORT_TIMEOUT);
        LogUtil.setLogString(isLoaded ? "Location Switcher popup is opened"
                : "Location Switcher popup is not opened", true, CustomLogLevel.LOW);
        return isLoaded;
    }

    /**
     * Select tstat by id.
     * @param tstatId the tstat id
     * @see com.ecofactor.qa.automation.newapp.page.LocationSwitcherOpsPage#selectTstatById(Integer)
     */
    @Override
    public void selectTstatById(Integer tstatId) {

        LogUtil.setLogString("Select Thermostat", true, CustomLogLevel.HIGH);
        final WebElement tstatElement = getElementByAttr(getDriver(),
                By.cssSelector(THERMOSTAT_ROW), THERMOSTAT_ID, tstatId.toString(), SHORT_TIMEOUT);
        getAction().click(tstatElement);
        getAction().rejectAlert();
    }

    /**
     * Click close.
     * @see com.ecofactor.qa.automation.newapp.page.LocationSwitcherOpsPage#clickClose()
     */
    @Override
    public void clickClose() {

        LogUtil.setLogString("Click Location Switcher Close Button", true, CustomLogLevel.HIGH);
        WebElement closeBtn = getElement(getDriver(), By.cssSelector(CLOSE_BTN), SHORT_TIMEOUT);
        getAction().click(closeBtn);
        getAction().rejectAlert();
    }

    /**
     * Click location.
     * @param locationName the location name
     * @see com.ecofactor.qa.automation.newapp.page.LocationSwitcherOpsPage#clickLocation(java.lang.String)
     */
    @Override
    public void clickLocation(String locationName) {

        List<WebElement> rowList = getElements(getDriver(), By.cssSelector(LOCATION_ROW),
                SHORT_TIMEOUT);
        for (WebElement webElement : rowList) {
            if (webElement.getText().trim().equalsIgnoreCase(locationName.trim())) {
                getAction().click(webElement);
                getAction().rejectAlert();
                break;
            }
        }
    }

    @Override
    public void clickBackground() {

        LogUtil.setLogString("Click Location Switcher Background", true, CustomLogLevel.HIGH);
        final WebElement page = getElement(getDriver(), By.className(LOCATION_SWITCHER_BACKGROUND),
                TINY_TIMEOUT);
        getAction().click(page);
        getAction().rejectAlert();

    }

}
