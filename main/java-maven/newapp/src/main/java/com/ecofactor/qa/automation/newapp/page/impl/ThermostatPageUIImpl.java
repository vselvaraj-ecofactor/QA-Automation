/*
 * ThermostatPageUIImpl.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.newapp.page.impl;

import static com.ecofactor.qa.automation.platform.constants.HtmlTags.*;
import static com.ecofactor.qa.automation.platform.enums.CustomTimeout.*;
import static com.ecofactor.qa.automation.platform.util.LogUtil.*;
import static com.ecofactor.qa.automation.platform.util.Pageutil.*;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.joda.time.LocalTime;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.ecofactor.qa.automation.newapp.MobileConfig;
import com.ecofactor.qa.automation.newapp.enums.HvacModes;
import com.ecofactor.qa.automation.newapp.page.LoginPage;
import com.ecofactor.qa.automation.newapp.page.ThermostatPageUI;
import com.ecofactor.qa.automation.platform.enums.CustomLogLevel;
import com.ecofactor.qa.automation.platform.enums.CustomTimeout;
import com.ecofactor.qa.automation.util.WaitUtil;
import com.google.inject.Inject;

/**
 * The Class ThermostatPageUIImpl.
 * @author $Author: vprasannaa $
 * @version $Rev: 32864 $ $Date: 2014-11-25 18:53:13 +0530 (Tue, 25 Nov 2014) $
 */
public class ThermostatPageUIImpl extends AbstractAuthenticationPageImpl implements
        ThermostatPageUI {

    private static final String SETPOINT = ".setPoint";
    private static final String MENU = "menuClick";
    // private static final String THERMOSTAT_CONTAINER = "div.thermostat_container";
    private static final String THERMOSTAT_CONTAINER = "div.thermostat_switcher_container";
    private static final String THERMOSTAT_CONTAINER_NAME = "div.ts_menu_thermostat_name";
    private static final String CURRENT_TEMPERATURE = "currentTemperature";
    private static final String LOCATION_NAME = "location_name";
    private static final String TH_AXIS_CONTAINER = "temperature_axis_container";
    private static final String OFF_MODE_BACK = "div.modeDialog.offModeBox";
    private static final String LEFT_NEUTRAL = "leftTempDiff neutralPosition";
    private static final String UNUSED_BTM_CONTAINER = "unused_bottom_container";
    private static final String UNUSED_TOP_CONTAINER = "unused_top_container";
    private static final String TSTAT_HEADER = "div.thermostatHeader";
    private static final String LEFT_TEMP_BOTTOM_POSITION = ".leftTempDiff.bottomPosition";
    private static final String LEFT_TEMP_TOP_POSITION = ".leftTempDiff.topPosition";
    private static final String RIGHT_TEMP_BOTTOM_POSITION = ".rightTempDiff.bottomPosition";
    private static final String RIGHT_TEMP_TOP_POSITION = ".rightTempDiff.topPosition";
    private static final String LOCATION_CONTAINER = "div.thermostat_switcher_container";
    private static final String LOCATION_CONTAINER_NAME = "div.location_row";
    private static final String SLIDING_TEMPERATURE_CONTAINER = "sliding_temperature_container";
    private static final String SAVINGS = "div.savingsAmount";
    private static final String HOME_ICON = "away_footer_container";
    private static final String OFFLINE = "not connected";
    private static final String THERMOSTAT_NOT_INSTALLED = "Thermostat is not installed";
    private static final String THERMOSTAT_NOT_CONNECTED = "Thermostat not connected";
    private static final String ERROR_MODEBOX = "div.modeDialog.errorModeBox";
    private static final String MODEL_LABEL = "modeLabel";
    private static final String CURRENT_LINE_AXIS = "div.leftDiffBorder";
    private static final String TARGET_LINE_AXIS = "div.rightDiffBorder";
    private static final String SETPOINT_CONTAINER = ".setPointContainer";
    private static final String ERROR_MODE_INFO = "errorModeInfo";
    // private static final String SAVINGS_ENERGY = ".fadein_text.savingsLink";
    private static final String SAVINGS_ENERGY = ".fadein_text";
    private static final String SAVINGS_ENERGY_FADE = ".fadein_text";
    private static final String BOTTOM_POSITION = "bottomPosition";
    private static final String NEUTRAL_POSITION = "neutralPosition";
    private static final String TOP_POSITION = "topPosition";
    private static final String LEFT_TEMP_DIFF = "leftTempDiff";
    private static final String RIGHT_TEMP_DIFF = "rightTempDiff";
    private static final String THERMOSTAT_SWITCHER_ICON = "thermostat_switcher_icon";
    private static final String SAVINGS_AMOUNT = "savingsAmount";
    private static final String BACKGROUND_IMAGE = "background-image";
    private static final String GRADIENT_BACKGROUND = "thermostatGradientBackground";
    private static final String THERMOSTAT_NAME_HEADER = "div.thermostat_name.small_text";
    private static final String OUTSIDE_TEMPERATURE = "div.outsideTemperature";
    private static final String TARGET_CIRCLE = "div.setPointInfo";

    @Inject
    protected LoginPage loginPage;
    @Inject
    protected ThermostatPageUI thPageUI;
    @Inject
    protected MobileConfig mobileConfig;
    private LocalTime pageLoadTimeOnLogin;

    /**
     * Checks if is page loaded.
     * @return true, if is page loaded
     * @see com.ecofactor.qa.automation.mobile.page.BasePage#isPageLoaded()
     */
    @Override
    public boolean isPageLoaded() {

        getAction().rejectAlert();
        getAction().clickTryAgain();
        setLogString("Verify Thermostat Page Loaded.", true, CustomLogLevel.LOW);
        isDisplayed(getDriver(), By.className(UNUSED_BTM_CONTAINER), TINY_TIMEOUT);
        isDisplayed(getDriver(), By.className(UNUSED_TOP_CONTAINER), TINY_TIMEOUT);
        loginPage.setLoggedIn(true);
        setPageLoadTimeOnLogin(new LocalTime());
        return isDisplayed(getDriver(), By.className(TH_AXIS_CONTAINER), TINY_TIMEOUT)
                && isDisplayed(getDriver(), By.className(LOCATION_NAME), TINY_TIMEOUT)
                && isDisplayed(getDriver(), By.className(MENU), TINY_TIMEOUT);
    }

    /**
     * Checks if is heater on.
     * @return true, if is heater on
     */
    public boolean isHeaterOn() {

        setLogString("Is Heater ON", true, CustomLogLevel.HIGH);
        final WebElement leftLine = getElement(getDriver(),
                By.cssSelector(LEFT_TEMP_BOTTOM_POSITION), SHORT_TIMEOUT);
        final WebElement rightLine = getElement(getDriver(),
                By.cssSelector(RIGHT_TEMP_TOP_POSITION), SHORT_TIMEOUT);
        return leftLine != null && rightLine != null;
    }

    /**
     * Checks if is ac on.
     * @return true, if is ac on
     */
    public boolean isAcOn() {

        setLogString("Is Cooler ON", true, CustomLogLevel.HIGH);
        final WebElement leftLine = getElement(getDriver(), By.cssSelector(LEFT_TEMP_TOP_POSITION),
                SHORT_TIMEOUT);
        final WebElement rightLine = getElement(getDriver(),
                By.cssSelector(RIGHT_TEMP_BOTTOM_POSITION), SHORT_TIMEOUT);
        return leftLine != null && rightLine != null;
    }

    /**
     * Checks if is mode off.
     * @return true, if is mode off
     * @see com.ecofactor.qa.automation.mobile.page.ThermostatPage#isModeOff()
     */
    @Override
    public boolean isModeOff() {

        setLogString("Verify the mode is off.", true, CustomLogLevel.MEDIUM);
        final WebElement currentMode = getElementBySubElement(getDriver(),
                getCurrentThermostatContainer(), By.cssSelector(OFF_MODE_BACK), TINY_TIMEOUT);
        return (currentMode != null && currentMode.isDisplayed()) ? true : false;
    }

    /**
     * Gets the no of locations.
     * @return the no of locations
     * @see com.ecofactor.qa.automation.mobile.page.ThermostatPage#getNoOfLocations()
     */
    @Override
    public Integer getNoOfLocations() {

        isDisplayed(getDriver(), By.cssSelector(LOCATION_CONTAINER), TINY_TIMEOUT);
        final List<WebElement> locationContainer = getElements(getDriver(),
                By.cssSelector(LOCATION_CONTAINER_NAME), TINY_TIMEOUT);
        return locationContainer.size();
    }

    /**
     * Checks if is savings displayed.
     * @return true, if is savings displayed
     * @see com.ecofactor.qa.automation.mobile.page.ThermostatPage#isSavingsDisplayed()
     */
    public boolean isSavingsDisplayed() {

        setLogString("Check Savings Icon is displayed", true, CustomLogLevel.MEDIUM);
        return isDisplayed(getDriver(), By.cssSelector(SAVINGS), TINY_TIMEOUT);
    }

    /**
     * Checks if is home icon displayed.
     * @return true, if is home icon displayed
     * @see com.ecofactor.qa.automation.mobile.page.ThermostatPage#isHomeIconDisplayed()
     */
    @Override
    public boolean isHomeIconDisplayed() {

        setLogString("Check Home Icon is displayed", true, CustomLogLevel.MEDIUM);
        return isDisplayed(getDriver(), By.className(HOME_ICON), TINY_TIMEOUT);
    }

    /**
     * Checks if is logout icon displayed.
     * @return true, if is logout icon displayed
     * @see com.ecofactor.qa.automation.mobile.page.ThermostatPage#isLogoutIconDisplayed()
     */
    @Override
    public boolean isMenuIconDisplayed() {

        setLogString("Check Menu icon is displayed", true, CustomLogLevel.MEDIUM);
        return isDisplayed(getDriver(), By.className(MENU), TINY_TIMEOUT);
    }

    /**
     * Checks if is target temp displayed.
     * @return true, if is target temp displayed
     * @see com.ecofactor.qa.automation.mobile.page.ThermostatPage#isTargetTempDisplayed()
     */
    @Override
    public boolean isTargetTempDisplayed() {

        setLogString("Check target temperature is displayed.", true, CustomLogLevel.MEDIUM);
        final boolean isTargetTempDisplayed = isDisplayedBySubElement(getDriver(),
                getCurrentThermostatContainer(), By.cssSelector(SETPOINT), TINY_TIMEOUT);
        setLogString("Target temperature displayed : " + isTargetTempDisplayed, true,
                CustomLogLevel.MEDIUM);
        return isTargetTempDisplayed;
    }

    /**
     * Checks if is current temp displayed.
     * @return true, if is current temp displayed
     * @see com.ecofactor.qa.automation.newapp.page.ThermostatPageUI#isCurrentTempDisplayed()
     */
    @Override
    public boolean isCurrentTempDisplayed() {

        setLogString("Check current temperature is displayed.", true, CustomLogLevel.MEDIUM);
        final boolean isTargetTempDisplayed = isDisplayedBySubElement(getDriver(),
                getCurrentThermostatContainer(), By.cssSelector("div.currentTemperature"),
                TINY_TIMEOUT);
        return isTargetTempDisplayed;
    }

    /**
     * Checks if is inside temp displayed.
     * @return true, if is inside temp displayed
     * @see com.ecofactor.qa.automation.newapp.page.ThermostatPageUI#isInsideTempDisplayed()
     */
    @Override
    public boolean isInsideTempDisplayed() {

        setLogString("Check inside temperature is displayed.", true, CustomLogLevel.MEDIUM);
        final boolean isInsideTempDisplayed = isDisplayedBySubElement(getDriver(),
                getCurrentThermostatContainer(), By.cssSelector("div.currentTemperature"),
                TINY_TIMEOUT);
        final WebElement insideTemp = getElement(getDriver(),
                By.cssSelector("div.currentTemperature"), TINY_TIMEOUT);
        setLogString("Inside temperature is :" + insideTemp.getText(), true, CustomLogLevel.MEDIUM);
        return isInsideTempDisplayed;
    }

    /**
     * Checks if is idle state.
     * @return true, if is idle state
     * @see com.ecofactor.qa.automation.mobile.page.ThermostatPage#isIdleState()
     */
    @Override
    public boolean isIdleState() {

        setLogString("Verify the state is neutral", true, CustomLogLevel.HIGH);
        final WebElement currentState = getElementByAttr(getDriver(), By.tagName(TAG_DIV),
                ATTR_CLASS, LEFT_NEUTRAL, SHORT_TIMEOUT);
        return currentState != null;
    }

    /**
     * Checks if is long dashes not displayed.
     * @return true, if is long dashes not displayed
     * @see com.ecofactor.qa.automation.newapp.page.ThermostatPageUI#isLongDashesNotDisplayed()
     */
    public boolean isLongDashesNotDisplayed() {

        setLogString(
                "Verify the long dashes between current and target temperature is not displayed",
                true, CustomLogLevel.MEDIUM);
        final boolean leftValue = isNotDisplayed(getDriver(),
                By.cssSelector(LEFT_TEMP_BOTTOM_POSITION), TINY_TIMEOUT);
        final boolean rightValue = isDisplayed(getDriver(),
                By.cssSelector(RIGHT_TEMP_TOP_POSITION), TINY_TIMEOUT);
        return leftValue && !rightValue ? true : false;
    }

    /**
     * Checks if is thermostat not installed.
     * @return true, if is thermostat not installed
     * @see com.ecofactor.qa.automation.newapp.page.ThermostatPage#isThermostatNotInstalled()
     */
    @Override
    public boolean isThermostatNotInstalled() {

        setLogString("Check if thermostat is not installed.", true, CustomLogLevel.HIGH);
        final String thStatusMsg = getTstatStatusMessage();
        return thStatusMsg != null && thStatusMsg.contains(THERMOSTAT_NOT_INSTALLED);
    }

    /**
     * Checks if is thermostat not connected.
     * @return true, if is thermostat not connected
     * @see com.ecofactor.qa.automation.newapp.page.ThermostatPageUI#isThermostatNotConnected()
     */
    @Override
    public boolean isThermostatNotConnected() {

        setLogString("Check if thermostat is not installed.", true, CustomLogLevel.HIGH);
        final String thStatusMsg = getTstatStatusMessage();
        return thStatusMsg != null && thStatusMsg.contains(THERMOSTAT_NOT_CONNECTED);
    }

    /**
     * Checks if is thermostat offline.
     * @return true, if is thermostat offline
     * @see com.ecofactor.qa.automation.mobile.page.ThermostatPage#isThermostatOffline()
     */
    @Override
    public boolean isThermostatOffline() {

        waitForPageLoaded(getDriver());
        setLogString("Check if thermostat is offline.", true, CustomLogLevel.HIGH);
        final String thStatusMsg = getTstatStatusMessage();
        return thStatusMsg != null && thStatusMsg.contains(OFFLINE);
    }

    /**
     * Checks if is target clickable.
     * @return true, if is target clickable
     * @see com.ecofactor.qa.automation.newapp.page.ThermostatPageUI#isTargetClickable()
     */
    @Override
    public boolean isTargetClickable() {

        setLogString("Is target clickable", true, CustomLogLevel.HIGH);
        final WebElement setPointElement = getElementBySubElement(getDriver(),
                getCurrentThermostatContainer(), By.cssSelector(SETPOINT_CONTAINER), TINY_TIMEOUT);
        return isClickable(getDriver(), setPointElement, TINY_TIMEOUT);
    }

    /**
     * Checks if is learn more clickable.
     * @return true, if is learn more clickable
     * @see com.ecofactor.qa.automation.newapp.page.ThermostatPageUI#isLearnMoreClickable()
     */
    @Override
    public boolean isLearnMoreClickable() {

        setLogString("Is Leran More button clickable", true, CustomLogLevel.HIGH);
        final WebElement errorModeInfoLink = getElement(getDriver(), By.className(ERROR_MODE_INFO),
                SHORT_TIMEOUT);
        return isClickable(getDriver(), errorModeInfoLink, TINY_TIMEOUT);
    }

    /**
     * Gets the page load time on login.
     * @return the page load time on login
     * @see com.ecofactor.qa.automation.mobile.page.ThermostatPage#getPageLoadTimeOnLogin()
     */
    @Override
    public LocalTime getPageLoadTimeOnLogin() {

        return this.pageLoadTimeOnLogin;
    }

    /**
     * Sets the page load time on login.
     * @param endTime the new page load time on login
     * @see com.ecofactor.qa.automation.mobile.page.ThermostatPage#setPageLoadTimeOnLogin(org.joda.time.LocalTime)
     */
    @Override
    public void setPageLoadTimeOnLogin(LocalTime endTime) {

        this.pageLoadTimeOnLogin = new LocalTime(endTime);
    }

    /**
     * Gets the no of thermostats.
     * @return the no of thermostats
     * @see com.ecofactor.qa.automation.mobile.page.ThermostatPage#getNoOfThermostats()
     */
    @Override
    public Integer getNoOfThermostats() {

        isDisplayed(getDriver(), By.cssSelector(THERMOSTAT_CONTAINER), TINY_TIMEOUT);
        final List<WebElement> thermostatContainer = getElements(getDriver(),
                By.cssSelector(THERMOSTAT_CONTAINER_NAME), TINY_TIMEOUT);
        return thermostatContainer.size();
    }

    /**
     * Gets the current location name.
     * @return the current location name
     * @see com.ecofactor.qa.automation.mobile.page.ThermostatPage#getCurrentLocationName()
     */
    @Override
    public String getCurrentLocationName() {

        String name = "";
        final List<WebElement> themostatNameList = getElements(getDriver(),
                By.cssSelector(TSTAT_HEADER), TINY_TIMEOUT);
        for (final WebElement webElement : themostatNameList) {
            if (webElement.isDisplayed()) {
                final WebElement themostatName = getElementBySubElement(getDriver(),
                        getCurrentThermostatContainer(), By.className(LOCATION_NAME), TINY_TIMEOUT);
                final String locationName = themostatName.getText();
                if (locationName.contains("-")) {
                    final int tstaNameIndex = locationName.lastIndexOf("-");
                    name = locationName.substring(tstaNameIndex + 1, locationName.length());
                    setLogString("Current Location Name : " + name, true, CustomLogLevel.HIGH);
                }
                break;
            }
        }
        return name;
    }

    /**
     * Gets the current thermostat name.
     * @return the current thermostat name
     */
    @Override
    public String getCurrentThermostatName() {

        String name = "";
        final WebElement locationName = getElementBySubElement(getDriver(),
                getCurrentThermostatContainer(), By.className(LOCATION_NAME), TINY_TIMEOUT);

        final String tstDetails = locationName.getText();
        if (tstDetails.contains("-")) {
            final int tstaNameIndex = tstDetails.lastIndexOf("-");
            name = tstDetails.substring(0, tstaNameIndex);
            setLogString("Current Thermostat Name : " + name, true, CustomLogLevel.HIGH);
        }
        return name;
    }

    /**
     * Gets the current mode.
     * @return the current mode
     * @return, current mode
     */
    @Override
    public String getCurrentMode() {

        setLogString("Get current mode", true, CustomLogLevel.HIGH);
        final String currentMode = getCurrentThermostatContainer().getAttribute(ATTR_CLASS);
        String mode = "";
        if (currentMode.contains("heatContainer")) {
            mode = "heat";
        } else if (currentMode.contains("coolContainer")) {
            mode = "cool";
        } else {
            mode = "off";
        }
        setLogString("Current mode: " + mode, true, CustomLogLevel.HIGH);
        return mode;
    }

    /**
     * Gets the current temperature.
     * @return the current temperature
     * @see com.ecofactor.qa.automation.mobile.page.ThermostatPage#getCurrentTemperature()
     */
    @Override
    public String getCurrentTemperature() {

        setLogString("Get current temperature", true, CustomLogLevel.HIGH);
        isDisplayedBySubElement(getDriver(), getCurrentThermostatContainer(),
                By.className(CURRENT_TEMPERATURE), TINY_TIMEOUT);
        final WebElement currentTempElement = getElementBySubElement(getDriver(),
                getCurrentThermostatContainer(), By.className(CURRENT_TEMPERATURE), TINY_TIMEOUT);
        Assert.assertNotNull(currentTempElement.getText(), "Current Temperature is not displayed");
        setLogString("Current Temperature: " + currentTempElement.getText(), true,
                CustomLogLevel.HIGH);
        final String currentTemperature = currentTempElement.getText().toString();
        return currentTemperature;
    }

    /**
     * Gets the target temperature.
     * @return the target temperature
     * @see com.ecofactor.qa.automation.mobile.page.ThermostatPage#getTargetTemperature()
     */
    @Override
    public String getTargetTemperature() {

        setLogString("Check target temperature is displayed", true, CustomLogLevel.HIGH);
        WaitUtil.oneSec();
        String targetTemperature = getElementBySubElement(getDriver(),
                getCurrentThermostatContainer(), By.cssSelector(SETPOINT), TINY_TIMEOUT).getText();
        /*
         * System.out.println("temp" + getElementBySubElement(getDriver(),
         * getCurrentThermostatContainer(), By.cssSelector(SETPOINT), TINY_TIMEOUT).getText());
         */
        WaitUtil.fourSec();
        setLogString("Target Temperature :" + targetTemperature, true, CustomLogLevel.HIGH);
        return targetTemperature;
    }

    /**
     * @param expectedTemp
     * @return
     * @see com.ecofactor.qa.automation.newapp.page.ThermostatPageUI#isTargetTemperatureChanged(java.lang.String)
     */
    public boolean isTargetTemperatureChanged(final String expectedTemp) {

        setLogString("Check target temperature is changed to : " + expectedTemp, true,
                CustomLogLevel.HIGH);
        final boolean targetReflected = new WebDriverWait(getDriver(),
                CustomTimeout.VERY_LONG_TIMEOUT.getValue()).until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(final WebDriver driver) {

                final WebElement targetElement = getElementBySubElement(getDriver(),
                        getCurrentThermostatContainer(), By.cssSelector(SETPOINT), TINY_TIMEOUT);
                if (targetElement.getText().equalsIgnoreCase(expectedTemp)) {
                    return true;
                }
                return false;
            }
        });
        return targetReflected;
    }

    /**
     * Checks if is target dashed line displayed.
     * @return true, if is target dashed line displayed
     * @see com.ecofactor.qa.automation.newapp.page.ThermostatPageUI#isTargetDashedLineDisplayed()
     */
    @Override
    public boolean isTargetDashedLineDisplayed() {

        setLogString("Check target temperature dahsed line displayed in central axis", true,
                CustomLogLevel.HIGH);
        return isDisplayed(getDriver(), By.cssSelector(TARGET_LINE_AXIS), TINY_TIMEOUT);
    }

    /**
     * Checks if is current dashed line displayed.
     * @return true, if is current dashed line displayed
     * @see com.ecofactor.qa.automation.newapp.page.ThermostatPageUI#isCurrentDashedLineDisplayed()
     */
    @Override
    public boolean isCurrentDashedLineDisplayed() {

        setLogString("Check Current temperature dahsed line displayed in central axis", true,
                CustomLogLevel.HIGH);
        return isDisplayed(getDriver(), By.cssSelector(CURRENT_LINE_AXIS), TINY_TIMEOUT);
    }

    /**
     * Checks if is under savings energy.
     * @return true, if is under savings energy
     * @see com.ecofactor.qa.automation.newapp.page.ThermostatPageUI#isUnderSavingsEnergy()
     */
    @Override
    public boolean isUnderSavingsEnergy() {

        setLogString("Is Swirl Icon Displayed and under Savings Energy ?", true, CustomLogLevel.LOW);
        final Object backgroundCss = executeScriptByClassNameWithPseudo(
                "setPointFromEnergyEfficiency", "background-image", getDriver());
        return backgroundCss != null && backgroundCss.toString().contains("target_blue.png");
    }

    /**
     * Checks if is savings energy link displayed.
     * @return true, if is savings energy link displayed
     * @see com.ecofactor.qa.automation.newapp.page.ThermostatPageUI#isSavingsEnergyLinkDisplayed()
     */
    @Override
    public boolean isSavingsEnergyLinkDisplayed() {

        setLogString("Is Savings Enegry Link Displayed ?", true, CustomLogLevel.LOW);
        return isDisplayed(getDriver(), By.cssSelector(SAVINGS_ENERGY), MEDIUM_TIMEOUT);
    }

    /**
     * Checks if is savings energy link clickable.
     * @return true, if is savings energy link clickable
     * @see com.ecofactor.qa.automation.newapp.page.ThermostatPageUI#isSavingsEnergyLinkClickable()
     */
    @Override
    public boolean isSavingsEnergyLinkClickable() {

        setLogString("Is Savings Enegry Link Clickable ?", true, CustomLogLevel.LOW);
        final WebElement savingsLink = getElement(getDriver(), By.cssSelector(SAVINGS_ENERGY),
                MEDIUM_TIMEOUT);
        return isClickable(getDriver(), savingsLink, MEDIUM_TIMEOUT);
    }

    /**
     * Checks if is schedule temp displayed.
     * @return true, if is schedule temp displayed
     * @see com.ecofactor.qa.automation.newapp.page.ThermostatPageUI#isScheduleTempDisplayed()
     */
    public boolean isScheduleTempDisplayed() {

        setLogString("Is Savings Enegry Schedule Temperature displayed ?", true, CustomLogLevel.LOW);
        return isDisplayed(getDriver(), By.cssSelector(SAVINGS_ENERGY_FADE), MEDIUM_TIMEOUT);

    }

    /**
     * Cleanup.
     * @see com.ecofactor.qa.automation.newapp.page.BasePage#cleanup()
     */
    @Override
    public void cleanup() {

    }

    /**
     * Gets the sliding temperature.
     * @return the sliding temperature
     * @see com.ecofactor.qa.automation.newapp.page.ThermostatPageUI#getSlidingTemperature()
     */
    public String getSlidingTemperature() {

        setLogString("Get Sliding Target Temperature", true, CustomLogLevel.MEDIUM);
        final JavascriptExecutor javascriptExecutor = (JavascriptExecutor) getDriver();
        final Object val = javascriptExecutor
                .executeScript("return window.document.defaultView.getComputedStyle(window.document.getElementsByClassName('"
                        + SLIDING_TEMPERATURE_CONTAINER + "')[0]).getPropertyValue('font-size');");
        return val.toString();

    }

    /**
     * Current temperature drag.
     * @param change the change
     * @see com.ecofactor.qa.automation.newapp.page.ThermostatPageUI#currentTemperatureDrag(int)
     */
    @Override
    public void currentTemperatureDrag(int change) {

        final WebElement temperatureSlider = getElement(getDriver(),
                By.cssSelector(".currentContainer"), TINY_TIMEOUT);
        getAction().doSwipeDown(temperatureSlider, temperatureSlider.getLocation().getX(),
                temperatureSlider.getLocation().getY(), change, 0.5);
    }

    /**
     * Gets the temperature content by position.
     * @param position the position
     * @return the temperature content by position
     * @see com.ecofactor.qa.automation.mobile.page.ThermostatPage#getTemperatureContentByPosition(java.lang.String)
     */
    @Override
    public String getTemperatureContentByPosition(String position) {

        final WebElement tempDifferenceContainer = getElement(getDriver(),
                By.className("temperatureDiffContainer"), TINY_TIMEOUT);
        final List<WebElement> divElements = getElementsBySubElement(getDriver(),
                tempDifferenceContainer, By.tagName("div"), TINY_TIMEOUT);

        WebElement requiredContainer = null;
        for (final WebElement element : divElements) {
            if (element.getAttribute("class").startsWith(position)) {
                requiredContainer = element;
                break;
            }
        }
        WebElement temperatureInfo = getElementBySubElementText(getDriver(), requiredContainer,
                By.tagName(TAG_DIV), "INSIDE", SHORT_TIMEOUT);
        temperatureInfo = (temperatureInfo != null) ? temperatureInfo : getElementBySubElementText(
                getDriver(), requiredContainer, By.tagName(TAG_DIV), "TARGET", SHORT_TIMEOUT);
        return temperatureInfo.getText();
    }

    /**
     * Checks if is temp position relative.
     * @param currentTemp the current temp
     * @param targetTemp the target temp
     * @return true, if is temp position relative
     * @see com.ecofactor.qa.automation.newapp.page.ThermostatPage#isTempPositionRelative(int, int)
     */
    @Override
    public boolean isTempPositionRelative(int currentTemp, int targetTemp) {

        boolean relative = false;
        final WebElement currentTempElement = getElement(getDriver(),
                By.cssSelector("div[class*='" + LEFT_TEMP_DIFF + "']"), TINY_TIMEOUT);
        final WebElement targetTempElement = getElement(getDriver(),
                By.cssSelector("div[class*='" + RIGHT_TEMP_DIFF + "']"), TINY_TIMEOUT);

        setLogString("Current temperature now :" + currentTempElement.getAttribute("className"),
                true, CustomLogLevel.HIGH);
        setLogString("Target temperature now :" + targetTempElement.getAttribute("className"),
                true, CustomLogLevel.HIGH);

        final String currentposition = currentTempElement.getAttribute("className");
        final String targetposition = targetTempElement.getAttribute("className");

        if (currentTemp > targetTemp) {
            relative = (currentposition.contains(TOP_POSITION) && targetposition
                    .contains(BOTTOM_POSITION)) ? true : false;
        } else if (targetTemp > currentTemp) {
            relative = (currentposition.contains(BOTTOM_POSITION) && targetposition
                    .contains(TOP_POSITION)) ? true : false;
        } else {
            relative = (currentposition.contains(NEUTRAL_POSITION) && targetposition
                    .contains(NEUTRAL_POSITION)) ? true : false;
        }
        return relative;
    }

    /**
     * Checks if is background color gradient.
     * @param mode the mode
     * @return true, if is background color gradient
     * @see com.ecofactor.qa.automation.newapp.page.ThermostatPage#isBackgroundColorGradient(java.lang.String)
     */
    @Override
    public boolean isBackgroundColorGradient(final String mode) {

        final String gradientValue = getHvacModeGradient(mode);
        if (gradientValue != null && !gradientValue.isEmpty()) {
            if (mode.equalsIgnoreCase("Heat") && gradientValue.contains("heating")) {
                return true;
            } else if (mode.equalsIgnoreCase("Cool") && gradientValue.contains("cooling")) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if is mode color reflected in screen.
     * @param mode the mode
     * @return true, if is mode color reflected in screen
     * @see com.ecofactor.qa.automation.newapp.page.ThermostatPage#isModeColorReflectedInScreen(java.lang.String)
     */
    @Override
    public boolean isModeColorReflectedInScreen(final String mode) {

        final String value = getHvacModeGradient(mode);
        final HvacModes hvacMode = HvacModes.valueOf(mode.toUpperCase());
        setLogString(
                "Verify if '" + mode + "' mode's thermostatGradientBackground has css value : "
                        + hvacMode.getGradientValue(), true, CustomLogLevel.HIGH);
        setLogString(mode.toUpperCase()
                + " mode's thermostatGradientBackground has css value as : " + value.toString(),
                true, CustomLogLevel.HIGH);
        Assert.assertTrue(
                mode.equalsIgnoreCase("off") ? value.contains("rgb(109") : value.contains(mode
                        .toLowerCase()),
                "Current thermostat container dosen't have gradient background.");
        if (HvacModes.IDLE == hvacMode) {
            return !HvacModes.COOL.getGradientValue().contains(value)
                    && !HvacModes.HEAT.getGradientValue().contains(value)
                    && !HvacModes.OFF.getGradientValue().contains(value);
        }
        return value.contains(hvacMode.getGradientValue().toLowerCase());

    }

    /**
     * Checks if is click learn more link redirects new window.
     * @return true, if is click learn more link redirects new window
     * @see com.ecofactor.qa.automation.newapp.page.ThermostatPage#isClickLearnMoreLinkRedirectsNewWindow()
     */
    @Override
    public boolean isClickLearnMoreLinkRedirectsNewWindow() {

        boolean pageRedirected = false;
        final String parentWindowHandle = getDriver().getWindowHandle();
        final WebElement errorModeInfoLink = getElement(getDriver(), By.className(ERROR_MODE_INFO),
                SHORT_TIMEOUT);
        setLogString("Click on error mode info.", true, CustomLogLevel.LOW);
        errorModeInfoLink.click();
        getAction().rejectAlert();
        // tinyWait();

        final Set<String> windowids = getDriver().getWindowHandles();
        final Iterator<String> iter = windowids.iterator();
        iter.next();
        setLogString("Check if new pop up window opens.", true, CustomLogLevel.LOW);
        setLogString("Switch to other window and verify the title", true, CustomLogLevel.HIGH);
        final WebDriver popup = getDriver().switchTo().window((String) iter.next());

        setLogString("Check URL is :http://ecofactor.uservoice.com/", true, CustomLogLevel.HIGH);
        setLogString("URL is :" + popup.getCurrentUrl(), true, CustomLogLevel.HIGH);

        pageRedirected = popup.getCurrentUrl().contains(
                mobileConfig.get(MobileConfig.LEARN_MORE_LINK)) ? true : false;
        popup.close();
        getDriver().switchTo().window(parentWindowHandle);
        return pageRedirected;
    }

    /**
     * Gets the tstat status message.
     * @return the tstat status message
     */
    private String getTstatStatusMessage() {

        String thStatusMsg = null;
        final WebElement errorElement = getElementBySubElement(getDriver(),
                getCurrentThermostatContainer(), By.cssSelector(ERROR_MODEBOX), TINY_TIMEOUT);
        if (errorElement.isDisplayed()) {
            final WebElement modeLabelelement = getElementBySubElement(getDriver(), errorElement,
                    By.className(MODEL_LABEL), ATOMIC_TIMEOUT);
            thStatusMsg = getElementBySubElement(getDriver(), modeLabelelement, By.tagName(TAG_P),
                    ATOMIC_TIMEOUT).getText();
            setLogString("Thermostat status message:" + thStatusMsg, true, CustomLogLevel.HIGH);
        }
        return thStatusMsg;
    }

    /**
     * Gets the tstat status message.
     * @return the tstat status message
     */
    @Override
    public String getOffModeMessage() {

        String thStatusMsg = null;
        isDisplayedBySubElement(getDriver(), getCurrentThermostatContainer(),
                By.cssSelector(ERROR_MODEBOX), TINY_TIMEOUT);
        final WebElement offModeElement = getElementBySubElement(getDriver(),
                getCurrentThermostatContainer(), By.cssSelector(".modeLabel"), TINY_TIMEOUT);
        if (offModeElement != null) {
            thStatusMsg = offModeElement.getText();
        }
        return thStatusMsg;
    }

    /**
     * Gets the hvac mode gradient.
     * @param mode the mode
     * @return the hvac mode gradient
     */
    private String getHvacModeGradient(final String mode) {

        final WebElement gradientElement = mode.equalsIgnoreCase("off") ? getCurrentThermostatContainer()
                : getElementBySubElement(getDriver(), getCurrentThermostatContainer(),
                        By.className(GRADIENT_BACKGROUND), TINY_TIMEOUT);

        final Object value = executeScriptByClassName(gradientElement.getAttribute("class"),
                mode.equalsIgnoreCase("off") ? "color" : "background-image", getDriver());
        setLogString("Hvac Mode Gradient Value :" + value.toString(), true, CustomLogLevel.MEDIUM);
        return value != null ? value.toString() : null;
    }

    /**
     * Checks if is location switcher displayed.
     * @return true, if is location switcher displayed
     * @see com.ecofactor.qa.automation.newapp.page.ThermostatPageUI#isLocationSwitcherDisplayed()
     */
    @Override
    public boolean isLocationSwitcherDisplayed() {

        setLogString("Check Location Switcher Icon is displayed.", true, CustomLogLevel.MEDIUM);
        boolean isTstatSwitcher = isDisplayed(getDriver(), By.className(THERMOSTAT_SWITCHER_ICON),
                TINY_TIMEOUT);
        setLogString(isTstatSwitcher ? "Location Switcher Icon is displayed."
                : "Location Switcher is Not displayed.", true, CustomLogLevel.MEDIUM);
        return isTstatSwitcher;
    }

    /**
     * Checks if is away settings pop up loaded.
     * @return true, if is away settings pop up loaded
     */
    @Override
    public boolean isAwaySettingsPopUpLoaded() {

        setLogString("Verify if Away Setting popup is loaded in thermostat page.", true,
                CustomLogLevel.MEDIUM);
        return isDisplayed(getDriver(), By.cssSelector("input.ctaButton.setAwaySubmitButton"),
                SHORT_TIMEOUT);
    }

    /**
     * Checks if is off mode dialog displayed.
     * @return true, if is off mode dialog displayed
     * @see com.ecofactor.qa.automation.newapp.page.ThermostatPageUI#isOffModeDialogDisplayed()
     */
    @Override
    public boolean isOffModeDialogDisplayed() {

        setLogString("Verify the off mode dialog displayed", true, CustomLogLevel.MEDIUM);
        final WebElement dialogElement = getElementBySubElement(getDriver(),
                getCurrentThermostatContainer(), By.cssSelector(".modeDialog.offModeBox"),
                SHORT_TIMEOUT);
        final String style = dialogElement.getAttribute("style");
        if (style.contains("visibility: hidden")) {
            return false;
        }
        return true;
    }

    /**
     * @return
     * @see com.ecofactor.qa.automation.newapp.page.ThermostatPageUI#isNotInstalledDialogDisplayed()
     */
    @Override
    public boolean isNotInstalledDialogDisplayed() {

        setLogString("Verify the \"Not Installed\" dialog displayed", true, CustomLogLevel.MEDIUM);

        final WebElement dialogElement = getElementBySubElement(getDriver(),
                getCurrentThermostatContainer(), By.cssSelector(".modeDialog.errorModeBox"),
                SHORT_TIMEOUT);
        final String style = dialogElement.getAttribute("style");
        if (style.contains("visibility: hidden")) {
            return false;
        }
        return true;
    }

    /**
     * Get Savings Amount
     * @return savings amount
     * @see com.ecofactor.qa.automation.newapp.page.ThermostatPageUI#getSavingsAmount()
     */
    @Override
    public String getSavingsAmount() {

        setLogString("Get Savings Amount", true, CustomLogLevel.HIGH);
        final WebElement savingsElement = getElementBySubElement(getDriver(),
                getCurrentThermostatContainer(), By.className(SAVINGS_AMOUNT), TINY_TIMEOUT);
        setLogString("Savings Amount " + savingsElement.getText(), true, CustomLogLevel.HIGH);
        return savingsElement.getText().substring(1).isEmpty() ? "0" : savingsElement.getText()
                .substring(1);
    }

    /**
     * Check either back ground image is display.
     * @param mode the mode.
     * @return true, if background image is displayed.
     * @see com.ecofactor.qa.automation.newapp.page.ThermostatPageUI#isBackgroundImageDisplayed(java.lang.String)
     */
    @Override
    public boolean isBackgroundImageDisplayed(final String mode) {

        final Object imageObj = executeScriptByClassName(GRADIENT_BACKGROUND, BACKGROUND_IMAGE,
                getDriver());
        if (imageObj != null && !imageObj.toString().isEmpty()) {
            if (mode.equalsIgnoreCase("Heat") && imageObj.toString().contains("heating")) {
                setLogString("HeatImage Displayed in Tstat", true);
                return true;
            } else if (mode.equalsIgnoreCase("Cool") && imageObj.toString().contains("cooling")) {
                setLogString("CoolImage Displayed in Tstat", true);
                return true;
            }
        }
        return false;

    }

    /**
     * Check weather the thermostat name displayed in header.
     * @return true, if thermostat name displayed in header.
     * @see com.ecofactor.qa.automation.newapp.page.ThermostatPageUI#isThermostatHeaderDisplayed()
     */
    @Override
    public boolean isThermostatHeaderDisplayed() {

        isDisplayed(getDriver(), By.cssSelector(THERMOSTAT_NAME_HEADER), MEDIUM_TIMEOUT);
        /*
         * final WebElement thermostatName = getElement(getDriver(),
         * By.cssSelector(THERMOSTAT_NAME_HEADER), MEDIUM_TIMEOUT);
         */
        final String thermostatHeader = thPageUI.getCurrentThermostatName();
        setLogString("Thermostat Header Name:" + thermostatHeader, true);
        final String locationName = thPageUI.getCurrentLocationName();
        setLogString("Location Name:" + locationName, true);
        return true;
    }

    /**
     * Fetch the current outside temperature from thermostat.
     * @return String.
     * @see com.ecofactor.qa.automation.newapp.page.ThermostatPageUI#fetchOutsideTemperature()
     */
    @Override
    public String fetchOutsideTemperature() {

        setLogString("Fetch Current Outside Temperature", true);
        final WebElement outSideTemp = getElement(getDriver(), By.cssSelector(OUTSIDE_TEMPERATURE),
                MEDIUM_TIMEOUT);
        final String outSideTemperature = outSideTemp.getText();
        setLogString("OutSide Temperature:" + outSideTemp.getText(), true);
        return outSideTemperature;
    }

    /**
     * verify either target circle get display in thermostat page or not.
     * @return true, if circle is dsiplay in thermostat page.
     * @see com.ecofactor.qa.automation.newapp.page.ThermostatPageUI#checkTargetCircle()
     */
    @Override
    public boolean checkTargetCircle() {

        setLogString("Check for Target Circle", true);
        // final WebElement targetCircle=getElement(getDriver(), By.cssSelector(TARGET_CIRCLE),
        // MEDIUM_TIMEOUT);
        isDisplayed(getDriver(), By.cssSelector(TARGET_CIRCLE), MEDIUM_TIMEOUT);
        return true;
    }
}
