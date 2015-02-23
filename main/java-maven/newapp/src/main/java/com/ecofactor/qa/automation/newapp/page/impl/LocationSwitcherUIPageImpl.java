/*
 * LocationSwitcherUIPageImpl.java
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

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.ecofactor.qa.automation.newapp.enums.TemperatureType;
import com.ecofactor.qa.automation.newapp.page.LocationSwitcherUIPage;
import com.ecofactor.qa.automation.platform.enums.CustomLogLevel;
import com.ecofactor.qa.automation.platform.util.LogUtil;

/**
 * The Class LocationSwitcherUIPageImpl.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class LocationSwitcherUIPageImpl extends AbstractAuthenticationPageImpl implements
        LocationSwitcherUIPage {

    private static final String THERMOSTAT_SWITCHER_ICON = "thermostat_switcher_icon";
    private static final String THERMOSTAT_SWITCHER_CONTAINER = "thermostat_switcher_container";
    private static final String LOCATION_ROW = "location_row";
    private static final String THERMOSTAT_ROW = "div.thermostat_row";
    private static final String THERMOSTAT_NAME = "ts_menu_thermostat_name";
    private static final String THERMOSTAT_STATUS = "thermostat_status";
    private static final String THERMOSTAT_ID = "thermostatid";
    private static final String LOCATION_ID = "locationid";
    private static final String CLASS = "class";
    private static final String BACKGROUND_IMG = "background-image";
    private static final String CLOSE_BTN = "div.ctaButton.close_button";

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

        LogUtil.setLogString("Verify the Location Switcher Page is loaded", true,
                CustomLogLevel.LOW);
        boolean isLoaded = isDisplayed(getDriver(), By.cssSelector(CLOSE_BTN), SHORT_TIMEOUT);
        if (isLoaded) {
            LogUtil.setLogString("Location Switcher page opened", true, CustomLogLevel.LOW);
        }
        return isLoaded;
    }

    /**
     * Gets the no of locations.
     * @return the no of locations
     * @see com.ecofactor.qa.automation.newapp.page.LocationSwitcherUIPage#getNoOfLocations()
     */
    @Override
    public int getNoOfLocations() {

        LogUtil.setLogString("Get number of locations", true, CustomLogLevel.LOW);
        final WebElement thermostatSwitcherContainer = getElement(getDriver(),
                By.className(THERMOSTAT_SWITCHER_CONTAINER), SHORT_TIMEOUT);
        final List<WebElement> locationElements = getElementsBySubElement(getDriver(),
                thermostatSwitcherContainer, By.className(LOCATION_ROW), SHORT_TIMEOUT);
        return locationElements != null ? locationElements.size() : 0;
    }

    /**
     * Gets the location names.
     * @return the location names
     * @see com.ecofactor.qa.automation.newapp.page.LocationSwitcherUIPage#getLocationNames()
     */
    @Override
    public List<String> getLocationNames() {

        LogUtil.setLogString("Get location names list", true, CustomLogLevel.LOW);
        final WebElement thermostatSwitcherContainer = getElement(getDriver(),
                By.className(THERMOSTAT_SWITCHER_CONTAINER), SHORT_TIMEOUT);
        final List<WebElement> locationElements = getElementsBySubElement(getDriver(),
                thermostatSwitcherContainer, By.className(LOCATION_ROW), SHORT_TIMEOUT);
        final List<String> locNames = new ArrayList<String>();
        for (final WebElement element : locationElements) {
            locNames.add(element.getText());
        }
        return locNames;
    }

    /**
     * Gets the tstat name by id.
     * @param tstatId the tstat id
     * @return the tstat name by id
     * @see com.ecofactor.qa.automation.newapp.page.LocationSwitcherUIPage#getTstatNameById(Integer)
     */
    @Override
    public String getTstatNameById(final Integer tstatId) {

        LogUtil.setLogString("Get Thermostat Name for tstatId :" + tstatId, true,
                CustomLogLevel.LOW);
        final WebElement tstatElement = getElementByAttr(getDriver(),
                By.cssSelector(THERMOSTAT_ROW), THERMOSTAT_ID, tstatId.toString(), SHORT_TIMEOUT);
        final WebElement tstatNameElement = getElementBySubElement(getDriver(), tstatElement,
                By.className(THERMOSTAT_NAME), SHORT_TIMEOUT);
        return tstatNameElement.getText();
    }

    /**
     * Gets the temp by tstat id.
     * @param tstatId the tstat id
     * @param tempType the temp type
     * @return the temp by tstat id
     * @see com.ecofactor.qa.automation.newapp.page.LocationSwitcherUIPage#getTempByTstatId(Integer,
     *      java.lang.String)
     */
    @Override
    public String getTempByTstatId(final Integer tstatId, final TemperatureType tempType) {

        LogUtil.setLogString("Get Temperature for tstatId :" + tstatId, true, CustomLogLevel.LOW);
        final WebElement tstatElement = getElementByAttr(getDriver(),
                By.cssSelector(THERMOSTAT_ROW), THERMOSTAT_ID, tstatId.toString(), SHORT_TIMEOUT);
        final WebElement statusElement = getElementBySubElement(getDriver(), tstatElement,
                By.className(THERMOSTAT_STATUS), SHORT_TIMEOUT);
        final String statusText = statusElement.getText();
        String temperature = "";
        if (statusText != null && !statusText.isEmpty() && !statusText.equals("--")) {
            temperature = getTemperature(tempType, temperature, statusText);
        } else if (statusText != null && !statusText.isEmpty() && statusText.equals("--")) {
            temperature = "--";
        }
        LogUtil.setLogString("The Temperature is :" + temperature, true, CustomLogLevel.LOW);
        return temperature;
    }

    /**
     * Gets the tstat namesin location.
     * @param locId the loc id
     * @return the tstat namesin location
     * @see com.ecofactor.qa.automation.newapp.page.LocationSwitcherUIPage#getTstatNamesinLocation(Integer)
     */
    @Override
    public List<String> getTstatNamesinLocation(final Integer locId) {

        LogUtil.setLogString("Get Thermostat Names for location id :" + locId, true,
                CustomLogLevel.LOW);
        final WebElement thermostatSwitcherContainer = getElement(getDriver(),
                By.className(THERMOSTAT_SWITCHER_CONTAINER), SHORT_TIMEOUT);
        final List<WebElement> tstatName = getElementsBySubElement(getDriver(),
                thermostatSwitcherContainer, By.cssSelector(THERMOSTAT_ROW), SHORT_TIMEOUT);
        final List<String> tstatNames = new ArrayList<String>();
        for (final WebElement element1 : tstatName) {
            final String locId1 = element1.getAttribute(LOCATION_ID);
            if (locId1.equalsIgnoreCase(String.valueOf(locId))) {
                final WebElement tstatName1 = getElementBySubElement(getDriver(), element1,
                        By.className(THERMOSTAT_NAME), SHORT_TIMEOUT);
                tstatNames.add(tstatName1.getText());
            }
        }
        return tstatNames;
    }

    /**
     * Gets the tstat id for location.
     * @param locId the loc id
     * @return the tstat id for location
     * @see com.ecofactor.qa.automation.newapp.page.LocationSwitcherUIPage#getTstatIdForLocation(java.lang.Integer)
     */
    @Override
    public List<Integer> getTstatIdForLocation(final Integer locId) {

        LogUtil.setLogString("Get Thermostat Id for location id :" + locId, true,
                CustomLogLevel.LOW);
        final WebElement thermostatSwitcherContainer = getElement(getDriver(),
                By.className(THERMOSTAT_SWITCHER_CONTAINER), SHORT_TIMEOUT);
        final List<WebElement> tstatName = getElementsBySubElement(getDriver(),
                thermostatSwitcherContainer, By.cssSelector(THERMOSTAT_ROW), SHORT_TIMEOUT);
        final List<Integer> tstatIdList = new ArrayList<Integer>();
        for (final WebElement element1 : tstatName) {
            final String locId1 = element1.getAttribute(LOCATION_ID);
            if (locId1.equalsIgnoreCase(String.valueOf(locId))) {
                final Integer tstatId = Integer.valueOf(element1.getAttribute(THERMOSTAT_ID));
                tstatIdList.add(tstatId);
            }
        }
        return tstatIdList;
    }

    /**
     * Gets the mode by tstat id.
     * @param tstatId the tstat id
     * @return the mode by tstat id
     * @see com.ecofactor.qa.automation.newapp.page.LocationSwitcherUIPage#getModeByTstatId(Integer)
     */
    @Override
    public String getModeByTstatId(final Integer tstatId) {

        LogUtil.setLogString("Get Mode for thermostat :" + tstatId, true, CustomLogLevel.LOW);
        final WebElement tstatElement = getElementByAttr(getDriver(),
                By.cssSelector(THERMOSTAT_ROW), THERMOSTAT_ID, tstatId.toString(), SHORT_TIMEOUT);
        String className = tstatElement.getAttribute(CLASS);
        Object val = null;
        if (className != null && className.contains(" ")) {
            int spaceIndex = className.lastIndexOf(" ");
            className = className.substring(spaceIndex, className.length());
            LogUtil.setLogString("Get the icon displayed for thermostat " + tstatId, true,
                    CustomLogLevel.LOW);
            val = executeScriptByClassName(className, BACKGROUND_IMG, getDriver());
            LogUtil.setLogString("Icon Displayed " + val.toString(), true, CustomLogLevel.LOW);
        } else {
            val = executeScriptByClassName(className, BACKGROUND_IMG, getDriver());
            if (val == null) {
                val = "";
            }
            LogUtil.setLogString("Icon Displayed " + val.toString(), true, CustomLogLevel.LOW);
        }
        return getMode(val.toString());
    }

    /**
     * Gets the text color.
     * @return the text color
     * @see com.ecofactor.qa.automation.newapp.page.LocationSwitcherUIPage#getTextColor()
     */
    @Override
    public String getTextColor() {

        LogUtil.setLogString("Get Text Color", true, CustomLogLevel.LOW);
        final Object backgroundCss = executeScriptByClassName(THERMOSTAT_SWITCHER_ICON, "color",
                getDriver());
        return backgroundCss.toString();
    }

    /**
     * Gets the location opacity value.
     * @return the location opacity value
     * @see com.ecofactor.qa.automation.newapp.page.LocationSwitcherUIPage#getLocationOpacityValue()
     */
    @Override
    public Double getLocationOpacityValue() {

        LogUtil.setLogString("Get Location opacity value", true, CustomLogLevel.LOW);
        Object backgroundCss = executeScriptByClassName(LOCATION_ROW, "background", getDriver());
        if (backgroundCss == null || backgroundCss.toString().isEmpty()) {
            backgroundCss = executeScriptByClassName(LOCATION_ROW, "background-color", getDriver());
        }
        String opacity = backgroundCss.toString();
        opacity = opacity.substring(opacity.lastIndexOf(",") + 1);
        opacity = opacity.substring(0, opacity.indexOf(")"));
        return Double.valueOf(opacity);

    }

    /**
     * Gets the rangeof temperature.
     * @param tstatId the tstat id
     * @return the rangeof temperature
     * @see com.ecofactor.qa.automation.newapp.page.LocationSwitcherUIPage#getRangeofTemperature(java.lang.Integer)
     */
    @Override
    public String getRangeofTemperature(Integer tstatId) {

        LogUtil.setLogString("Get Range of Temperature for thermostat :" + tstatId, true,
                CustomLogLevel.LOW);
        final WebElement tstatElement = getElementByAttr(getDriver(),
                By.cssSelector(THERMOSTAT_ROW), THERMOSTAT_ID, tstatId.toString(), SHORT_TIMEOUT);
        final WebElement tstatStatusElement = getElementBySubElement(getDriver(), tstatElement,
                By.className(THERMOSTAT_STATUS), SHORT_TIMEOUT);
        String statusText = tstatStatusElement.getText();
        if (statusText.equalsIgnoreCase("--")) {

            LogUtil.setLogString("The Range is " + statusText, true, CustomLogLevel.LOW);
        } else if (statusText != null && !statusText.isEmpty()
                && !statusText.equalsIgnoreCase("--")) {
            statusText = getRange(statusText);
            LogUtil.setLogString("The Range is " + statusText, true, CustomLogLevel.LOW);
        }

        return statusText;
    }

    /**
     * Gets the temperature.
     * @param tempType the temp type
     * @param temperature the temperature
     * @param statusText the status text
     * @return the temperature
     */
    private String getTemperature(final TemperatureType tempType, String temperature,
            final String statusText) {

        System.out.println("tempType.toString()==" + tempType.toString());
        if (tempType.toString().equalsIgnoreCase("currentTemperature")) {
            temperature = statusText.split(" ")[0];
        } else if (tempType.toString().equalsIgnoreCase("targetTempeature")) {
            temperature = statusText.split(" ")[2];
        }
        return temperature;
    }

    /**
     * Gets the mode.
     * @param backgroundImg the background img
     * @return the mode
     */
    private String getMode(String backgroundImg) {

        if (backgroundImg.contains("heat")) {
            backgroundImg = "HEAT";
        } else if (backgroundImg.contains("cool")) {
            backgroundImg = "COOL";
        } else {
            backgroundImg = "OFF";
        }
        return backgroundImg;
    }

    /**
     * Gets the range.
     * @param statusText the status text
     * @return the range
     */
    private String getRange(String statusText) {

        statusText = statusText.split(" ")[1];
        if (statusText.equalsIgnoreCase(">")) {
            statusText = "Greater Than";
        } else if (statusText.equalsIgnoreCase("<")) {
            statusText = "Lesser Than";
        } else if (statusText.equalsIgnoreCase("-")) {
            statusText = "Equal To";
        }
        return statusText;
    }

}
