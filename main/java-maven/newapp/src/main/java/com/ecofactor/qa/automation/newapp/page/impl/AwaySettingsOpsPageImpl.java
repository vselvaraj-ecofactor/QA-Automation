/*
 * AwaySettingsOpsPageImpl.java
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

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.ecofactor.qa.automation.newapp.enums.SetAwayParams;
import com.ecofactor.qa.automation.newapp.page.AwaySettingsOpsPage;
import com.ecofactor.qa.automation.newapp.page.AwaySettingsUIPage;
import com.ecofactor.qa.automation.platform.enums.CustomLogLevel;
import com.ecofactor.qa.automation.platform.util.LogUtil;
import com.ecofactor.qa.automation.util.WaitUtil;
import com.google.inject.Inject;

/**
 * The Class AwaySettingsOpsPageImpl.
 * @author $Author: vprasannaa $
 * @version $Rev: 33046 $ $Date: 2014-12-11 16:05:31 +0530 (Thu, 11 Dec 2014) $
 */
public class AwaySettingsOpsPageImpl extends AbstractSetAwayParameters implements
        AwaySettingsOpsPage {

    /** The Constant MENU_AWAY_SETTINGS. */
    private static final String MENU_AWAY_SETTINGS = "div.menu_row.settings";

    /** The Constant MENU_THERMOSTAT. */
    private static final String MENU_THERMOSTAT = "div.menu_row.thermostat";

    /** The Constant DATA_VALUE. */
    private static final String DATA_VALUE = "data-val";

    /** The Constant MENU_AWAY. */
    private static final String MENU_AWAY = ".menuClick.light";

    /** The Constant CURRENT_VALUE. */
    private static final String CURRENT_VALUE = "div.dw-li.dw-v.dw-sel";

    /** The Constant LEFT. */
    private static final CharSequence LEFT = "left";

    /** The away settings ui page. */
    @Inject
    private AwaySettingsUIPage awaySettingsUIPage;

    /**
     * Sets the away param picker.
     * @param setAwayParams the set away params
     * @param expectedValue the expected value
     * @return true, if successful
     * @see com.ecofactor.qa.automation.newapp.page.AwaySettingsOpsPage#setAwayParam(com.ecofactor.qa.automation.newapp.enums.SetAwayParams,
     *      int)
     */
    @Override
    public boolean setAwayParamPicker(final SetAwayParams setAwayParams, final int expectedValue) {

        LogUtil.setLogString("Set " + setAwayParams + " as :" + expectedValue, true);

        final WebElement clickValue = getElement(getDriver(),
                By.cssSelector(".away_setting_picker"), TINY_TIMEOUT);
        WaitUtil.oneSec();
        getAction().click(clickValue);
        getAction().rejectAlert();

        final WebElement currentReadingElement = getElement(getDriver(),
                By.cssSelector(CURRENT_VALUE), TINY_TIMEOUT);

        final int currentReadingValue = Integer.parseInt(currentReadingElement
                .getAttribute(DATA_VALUE));

        final int setPointValue = currentReadingValue > expectedValue ? -(currentReadingValue - expectedValue)
                : expectedValue - currentReadingValue;

        return changeAwayParamValue(setAwayParams, setPointValue).equals(expectedValue);
    }

    /**
     * Click away param picker.
     * @param setAwayParam the set away param
     * @return true, if successful
     * @see com.ecofactor.qa.automation.newapp.page.AwaySettingsOpsPage#clickAwayParam(com.ecofactor.qa.automation.newapp.enums.SetAwayParams)
     */
    @Override
    public boolean clickAwayParamPicker(final SetAwayParams setAwayParam) {

        final WebElement pickerElement = awaySettingsUIPage.getPickerElement(setAwayParam);
        Assert.assertTrue(isClickable(getDriver(), pickerElement, MEDIUM_TIMEOUT), setAwayParam
                + " field is not clickable.");
        getAction().click(pickerElement);
        return isDisplayed(getDriver(), By.cssSelector(CURRENT_VALUE), TINY_TIMEOUT);
    }

    /**
     * Click away settings.
     * @see com.ecofactor.qa.automation.newapp.page.AwaySettingsOpsPage#setCoolTemp(java.lang.String)
     */
    @Override
    public void clickAwaySettings() {

        LogUtil.setLogString("Switch to Away Settings", true);
        isDisplayed(getDriver(), By.cssSelector(MENU_AWAY_SETTINGS), TINY_TIMEOUT);
        getAction().rejectAlert();
        final WebElement menuAwaySettingsElement = getElement(getDriver(),
                By.cssSelector(MENU_AWAY_SETTINGS), TINY_TIMEOUT);
        WaitUtil.oneSec();
        getAction().click(menuAwaySettingsElement);
        getAction().rejectAlert();
    }

    /**
     * Click thermostat.
     * @see com.ecofactor.qa.automation.newapp.page.AwaySettingsOpsPage#setCoolTemp(java.lang.String)
     */
    @Override
    public void clickThermostat() {

        LogUtil.setLogString("Switch to Thermostat Page", true);
        isDisplayed(getDriver(), By.cssSelector(MENU_THERMOSTAT), TINY_TIMEOUT);
        final WebElement menuThermostatElement = getElement(getDriver(),
                By.cssSelector(MENU_THERMOSTAT), TINY_TIMEOUT);
        getAction().click(menuThermostatElement);
    }

    /**
     * Click menu icon.
     * @see com.ecofactor.qa.automation.newapp.page.AwaySettingsOpsPage#clickMenuIcon()
     */
    public void clickMenuIcon() {

        WaitUtil.tinyWait();
        LogUtil.setLogString("Click Menu in away settings", true);
        getAction().rejectAlert();
        final WebElement menuAwayElement = getElement(getDriver(), By.cssSelector(MENU_AWAY),
                TINY_TIMEOUT);
        WaitUtil.oneSec();
        menuAwayElement.click();
        // getAction().click(menuAwayElement);
        getAction().rejectAlert();
    }

    /**
     * Cleanup.
     * @see com.ecofactor.qa.automation.newapp.page.BasePage#cleanup()
     */
    @Override
    public void cleanup() {

        LogUtil.setLogString("Cleanup in Away Settings Page.", true);
    }

    /**
     * Checks if is page loaded.
     * @return true, if is page loaded
     * @see com.ecofactor.qa.automation.newapp.page.BasePage#isPageLoaded()
     */
    @Override
    public boolean isPageLoaded() {

        return false;
    }

    /**
     * Swipe page.
     * @param direction the direction
     * @see com.ecofactor.qa.automation.newapp.page.AwaySettingsOpsPage#swipePage(java.lang.String)
     */
    @Override
    public void swipePage(final String direction) {

        final WebElement settingsContent = getElement(getDriver(),
                By.cssSelector("div.settings_content"), TINY_TIMEOUT);

        LogUtil.setLogString("Swipe " + direction, true, CustomLogLevel.MEDIUM);

        if (direction.contains(LEFT)) {

            getAction().doSwipeLeft(settingsContent);
        } else {

            getAction().doSwipeRight(settingsContent);

        }
    }

    /**
     * click back button to reach settings page.
     * @see com.ecofactor.qa.automation.newapp.page.AwaySettingsOpsPage#clickBack()
     */
    @Override
    public void clickBack() {

        LogUtil.setLogString("Click Back", true, CustomLogLevel.MEDIUM);
        final WebElement backElement = getElement(getDriver(), By.cssSelector(".back"),
                TINY_TIMEOUT);
        getAction().click(backElement);
        getAction().rejectAlert();

    }

}
