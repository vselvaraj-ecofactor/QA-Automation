/*
 * TstatControlUIPageImpl.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.newapp.page.impl;

import static com.ecofactor.qa.automation.platform.constants.HtmlTags.*;
import static com.ecofactor.qa.automation.platform.enums.CustomTimeout.*;
import static com.ecofactor.qa.automation.platform.util.Pageutil.*;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.ecofactor.qa.automation.newapp.page.TstatControlUIPage;
import com.ecofactor.qa.automation.platform.enums.CustomLogLevel;
import com.ecofactor.qa.automation.platform.util.LogUtil;

/**
 * The Class TstatControlUIPageImpl.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class TstatControlUIPageImpl extends AbstractAuthenticationPageImpl implements
        TstatControlUIPage {

    private static final String FAN_ON = "On";
    private static final String SELECTED = "selected";
    private static final String UP_ARROW_BTN = "upArrowButton";
    private static final String DOWN_ARROW_BTN = "downArrowButton";
    private static final String COOL_CONTAINER = "coolContainer";
    private static final String HEAT_CONTAINER = "heatContainer";
    private static final String OFF_CONTAINER = "offContainer";
    private static final String THERMOSTAT_BTN_CONTAINER = "thermostat_button_container";
    private static final String HVAC_MODE_BTN = "thermostatHvacModeButton";
    private static final String TH_TEMPERATURE_SMALL = "thermostatTemperatureSmall";
    private static final String TH_TARGET_SETPOINT = "thermostatTargetSetpoint";
    private static final String BACKGROUND_IMAGE = "background-image";
    private static final String UP_ARROW_BTN_COLOR = "up-red-arrow";
    private static final String DOWN_ARROW_BTN_COLOR = "down-blue-arrow";
    private final static String HEAT_MODE_COLOR = "255, 118, 0";
    private final static String COOL_MODE_COLOR = "0, 179, 227";

    /**
     * Checks if is cool background.
     * @return true, if is cool background
     * @see com.ecofactor.qa.automation.mobile.page.ThermostatControlPage#isCoolBackground()
     */
    @Override
    public boolean isCoolBackground() {

        LogUtil.setLogString("Verify background is Blue", true, CustomLogLevel.MEDIUM);
        boolean isCoolBackground = verifyColorChanges(COOL_CONTAINER);
        return isCoolBackground;
    }

    /**
     * Checks if is heat background.
     * @return true, if is heat background
     * @see com.ecofactor.qa.automation.mobile.page.ThermostatControlPage#isHeatBackground()
     */
    @Override
    public boolean isHeatBackground() {

        LogUtil.setLogString("Verify background is Red", true, CustomLogLevel.MEDIUM);
        boolean isHeatBackground = verifyColorChanges(HEAT_CONTAINER);
        return isHeatBackground;
    }

    /**
     * Checks if is off background.
     * @return true, if is off background
     * @see com.ecofactor.qa.automation.mobile.page.ThermostatControlPage#isOffBackground()
     */
    @Override
    public boolean isOffBackground() {

        LogUtil.setLogString("Verify background is black", true, CustomLogLevel.MEDIUM);
        boolean isOffBackground = verifyColorChanges(OFF_CONTAINER);
        return isOffBackground;
    }

    /**
     * Checks if is fan on.
     * @return true, if is fan on
     * @see com.ecofactor.qa.automation.consumer.page.ConsumerHome#isFanOn()
     */
    @Override
    public boolean isFanOn() {

        WebElement fanOnElement = getElementBySubElementAttr(getDriver(),
                getCurrentThermostatContainer(), By.tagName(TAG_INPUT), ATTR_VALUE, FAN_ON,
                SHORT_TIMEOUT);
        boolean onSelected = fanOnElement.getAttribute("className").contains("selected");
        return onSelected;
    }

    /**
     * Gets the current temperature.
     * @return the current temperature
     * @see com.ecofactor.qa.automation.mobile.page.ThermostatControlPage#getCurrentTemperature()
     */
    @Override
    public String getCurrentTemperature() {

        LogUtil.setLogString("Get Current temperature", true, CustomLogLevel.HIGH);
        isDisplayedBySubElement(getDriver(), getCurrentThermostatContainer(),
                By.className(TH_TEMPERATURE_SMALL), TINY_TIMEOUT);
        WebElement tempDivEle = getElementBySubElement(getDriver(),
                getCurrentThermostatContainer(), By.className(TH_TEMPERATURE_SMALL), MEDIUM_TIMEOUT);
        WebElement currentElement = getElementBySubElement(getDriver(), tempDivEle,
                By.tagName(TAG_SPAN), MEDIUM_TIMEOUT);
        String current = currentElement.getText();
        LogUtil.setLogString("Current temperature : " + current, true, CustomLogLevel.HIGH);
        return current;
    }

    /**
     * Gets the target temperature.
     * @return the target temperature
     * @see com.ecofactor.qa.automation.mobile.page.ThermostatControlPage#getTargetTemperature()
     */
    @Override
    public String getTargetTemperature() {

        LogUtil.setLogString("Get Target temperature", true, CustomLogLevel.HIGH);
        isDisplayedBySubElement(getDriver(), getCurrentThermostatContainer(),
                By.className(TH_TARGET_SETPOINT), TINY_TIMEOUT);
        WebElement tempDivEle = getElementBySubElement(getDriver(),
                getCurrentThermostatContainer(), By.className(TH_TARGET_SETPOINT), MEDIUM_TIMEOUT);
        String target = tempDivEle.getText();
        LogUtil.setLogString("Target temperature : " + target, true, CustomLogLevel.HIGH);
        return target;
    }

    /**
     * Gets the current mode.
     * @return the current mode
     * @see com.ecofactor.qa.automation.mobile.page.ThermostatControlPage#getCurrentMode()
     */
    @Override
    public String getCurrentMode() {

        String currentMode = "";
        LogUtil.setLogString("Get Current Mode", true, CustomLogLevel.HIGH);
        isDisplayedBySubElement(getDriver(), getCurrentThermostatContainer(),
                By.className(THERMOSTAT_BTN_CONTAINER), TINY_TIMEOUT);
        WebElement thermostatElement = getElementBySubElement(getDriver(),
                getCurrentThermostatContainer(), By.cssSelector("." + THERMOSTAT_BTN_CONTAINER),
                TINY_TIMEOUT);
        List<WebElement> thermostatElementList = getElementsBySubElement(getDriver(),
                thermostatElement, By.cssSelector("." + HVAC_MODE_BTN), TINY_TIMEOUT);
        for (WebElement webElement : thermostatElementList) {
            String currentClass = webElement.getAttribute(ATTR_CLASS);
            if (currentClass.contains(SELECTED)) {
                currentMode = webElement.getAttribute(ATTR_VALUE);
                break;
            }
        }
        LogUtil.setLogString("Current Mode is :" + currentMode, true);
        return currentMode;

    }

    /**
     * Gets the available modes.
     * @return the available modes
     */
    public List<String> getAvailableModes() {

        List<String> availableModeList = new ArrayList<String>();
        isDisplayedBySubElement(getDriver(), getCurrentThermostatContainer(),
                By.className(THERMOSTAT_BTN_CONTAINER), TINY_TIMEOUT);
        WebElement thermostatElement = getElementBySubElement(getDriver(),
                getCurrentThermostatContainer(), By.cssSelector("." + THERMOSTAT_BTN_CONTAINER),
                TINY_TIMEOUT);
        List<WebElement> thermostatElementList = getElementsBySubElement(getDriver(),
                thermostatElement, By.cssSelector("." + HVAC_MODE_BTN), TINY_TIMEOUT);
        for (WebElement webElement : thermostatElementList) {
            availableModeList.add(webElement.getAttribute(ATTR_VALUE));
        }
        return availableModeList;
    }

    /**
     * Verify color changes.
     * @param modeContainer the mode container
     * @return true, if successful
     */
    private boolean verifyColorChanges(String modeContainer) {

        boolean iscolorChanged = isDisplayed(getDriver(),
                By.cssSelector("div[class*='" + modeContainer + "']"), MEDIUM_TIMEOUT);
        return iscolorChanged;
    }

    /**
     * Checks if is up arrow btn red.
     * @return true, if is up arrow btn red
     * @see com.ecofactor.qa.automation.mobile.page.ThermostatControlPage#isUpArrowBtnRed()
     */
    public boolean isUpArrowBtnRed() {

        Object upArrowColor = executeScriptByClassName(UP_ARROW_BTN, BACKGROUND_IMAGE, getDriver());
        return String.valueOf(upArrowColor).contains(UP_ARROW_BTN_COLOR);
    }

    /**
     * Checks if is down arrow btn blue.
     * @return true, if is down arrow btn blue
     * @see com.ecofactor.qa.automation.mobile.page.ThermostatControlPage#isdownArrowBtnBlue()
     */
    public boolean isdownArrowBtnBlue() {

        Object downArrowColor = executeScriptByClassName(DOWN_ARROW_BTN, BACKGROUND_IMAGE,
                getDriver());
        return String.valueOf(downArrowColor).contains(DOWN_ARROW_BTN_COLOR);
    }

    /**
     * Gets the opacity value for controls.
     * @return the opacity value for controls
     * @see com.ecofactor.qa.automation.mobile.page.ThermostatControlPage#getOpacityValueForControls()
     */
    @Override
    public Double getOpacityValueForControls() {

        isDisplayed(getDriver(), By.className("thermostat_control_container"), SHORT_TIMEOUT);
        LogUtil.setLogString("Check opacity value for controls.", true, CustomLogLevel.HIGH);
        return getOpacityValueForClass("thermostat_control_container");
    }

    /**
     * Gets the opacityvalue for buttons.
     * @return the opacityvalue for buttons
     * @see com.ecofactor.qa.automation.mobile.page.ThermostatControlPage#getOpacityvalueForButtons()
     */
    @Override
    public Double getOpacityvalueForButtons() {

        final Double opacityValue = 0.40;
        LogUtil.setLogString(
                "Check opacity value of buttons in the thermostat controls container.", true,
                CustomLogLevel.HIGH);
        LogUtil.setLogString("Verify the Button opacity for up arrow is in between 0.40 and 0.1",
                true, CustomLogLevel.LOW);
        Assert.assertTrue(
                (opacityValue > getOpacityValueForClass("upArrowButton") && getOpacityValueForClass("upArrowButton") > 0.1)
                        || getOpacityValueForClass("upArrowButton").equals(opacityValue),
                "Opacity value for buttons differ.");
        LogUtil.setLogString("Verify the Button opacity for down arrow is in between 0.40 and 0.1",
                true, CustomLogLevel.LOW);
        Assert.assertTrue(
                (opacityValue > getOpacityValueForClass("downArrowButton") && getOpacityValueForClass("downArrowButton") > 0.1)
                        || getOpacityValueForClass("upArrowButton").equals(opacityValue),
                "Opacity value for buttons differ.");
        LogUtil.setLogString("Verify the Button opacity for Mode buttons", true,
                CustomLogLevel.MEDIUM);
        opacityValue("thermostat_hvac_mode_container");
        LogUtil.setLogString("Verify the Button opacity for FAN buttons", true,
                CustomLogLevel.MEDIUM);
        opacityValue("thermostat_fan_mode_container");
        LogUtil.setLogString("Verify the Button opacity for Close button", true,
                CustomLogLevel.MEDIUM);
        opacityValue("sectionFour");
        return opacityValue;

    }

    @Override
    public boolean isSavingsEnergyLinkDisplayed() {

        LogUtil.setLogString("Check Savings Link/image is displayed", true, CustomLogLevel.MEDIUM);
        WebElement savingsElement = getElement(getDriver(),
                By.cssSelector(".eeTargetSetpoint.eeActive>img"), MEDIUM_TIMEOUT);

        return savingsElement != null && savingsElement.isDisplayed();
    }

    /**
     * Opacity value.
     * @param containerClassName the container class name
     */
    private void opacityValue(String containerClassName) {

        isDisplayed(getDriver(), By.className(containerClassName), SHORT_TIMEOUT);
        WebElement thermostatControlContainer = getElement(getDriver(),
                By.className(containerClassName), SHORT_TIMEOUT);
        List<WebElement> buttonElements = getElementsBySubElement(getDriver(),
                thermostatControlContainer, By.tagName(TAG_INPUT), SHORT_TIMEOUT);
        int loop = 0;
        for (WebElement element : buttonElements) {
            String classname = element.getAttribute("className");
            if (element.getAttribute(ATTR_TYPE).endsWith("button") && classname != null
                    && !classname.isEmpty()) {
                String classNameSplit = classname.contains(SELECTED) ? classname.split(" ")[0]
                        : classname;
                JavascriptExecutor js = (JavascriptExecutor) getDriver();
                Object val = js
                        .executeScript("return window.document.defaultView.getComputedStyle(window.document.getElementsByClassName('"
                                + classNameSplit
                                + "')['"
                                + loop
                                + "']).getPropertyValue('background-color');");
                loop++;
                String opacity = val.toString();
                opacity = opacity.substring(opacity.lastIndexOf(",") + 1);
                opacity = opacity.substring(0, opacity.indexOf(")"));

                Double currentOpacity = Double.valueOf(opacity.toString().trim());
                if (classname.contains(SELECTED)) {
                    final Double opacityValueSelected = 0.5;
                    LogUtil.setLogString(
                            "Verify the opacity for '" + element.getAttribute(ATTR_VALUE)
                                    + "' (Button selected)", true, CustomLogLevel.LOW);
                    LogUtil.setLogString("Expected opacity in between 0.48 and 0.0", true,
                            CustomLogLevel.HIGH);
                    LogUtil.setLogString("The opacity in UI is :" + currentOpacity, true,
                            CustomLogLevel.HIGH);
                    Assert.assertTrue(
                            (opacityValueSelected > currentOpacity && currentOpacity >= 0.0)
                                    || opacityValueSelected.equals(currentOpacity),
                            "Opacity value for buttons differ.");
                } else {
                    LogUtil.setLogString(
                            "Verify the opacity for '" + element.getAttribute(ATTR_VALUE)
                                    + "' button, (Button Not selected)", true);
                    LogUtil.setLogString("Expected opacity in between 0.0 and 0.40", true,
                            CustomLogLevel.HIGH);
                    LogUtil.setLogString("The opacity in UI is :" + currentOpacity, true,
                            CustomLogLevel.HIGH);
                    final Double opacityValueSelected = 0.40;
                    Assert.assertTrue(
                            (opacityValueSelected > currentOpacity && currentOpacity >= 0.0)
                                    || currentOpacity.equals(opacityValueSelected),
                            "Opacity value for buttons differ.");
                }

            }

        }
    }

    /**
     * Gets the opacity value for class.
     * @param classname the classname
     * @return the opacity value for class
     */
    private Double getOpacityValueForClass(final String classname) {

        Object backgroundCss = executeScriptByClassName(classname, "background", getDriver());
        if (backgroundCss == null || backgroundCss.toString().isEmpty()) {
            backgroundCss = executeScriptByClassName(classname, "background-color", getDriver());
        }
        String opacity = backgroundCss.toString();
        opacity = opacity.substring(opacity.lastIndexOf(",") + 1);
        opacity = opacity.substring(0, opacity.indexOf(")"));
        return Double.valueOf(opacity.toString().trim());
    }

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

        return false;
    }

    /**
     * Gets the rgb for hvac selected button.
     * @return the rgb for hvac selected button
     * @see com.ecofactor.qa.automation.newapp.page.TstatControlUIPage#getRgbForHvacSelectedButton()
     */
    @Override
    public String getRgbForHvacSelectedButton() {

        return getRgb("thermostat_hvac_mode_container", getCurrentMode());
    }

    /**
     * Gets the rgb for fan selected button.
     * @return the rgb for fan selected button
     * @see com.ecofactor.qa.automation.newapp.page.TstatControlUIPage#getRgbForFanSelectedButton()
     */
    @Override
    public String getRgbForFanSelectedButton() {

        return getRgb("thermostat_fan_mode_container", getCurrentMode());
    }

    /**
     * @return
     * @see com.ecofactor.qa.automation.newapp.page.TstatControlUIPage#getRgbForSeperator()
     */
    @Override
    public String getRgbForSeperator() {

        return getRgbForClassName("sectionOne");
    }

    /**
     * Gets the rgb.
     * @param containerClassName the container class name
     * @param mode the mode
     * @return the rgb
     */
    private String getRgb(String containerClassName, String mode) {

        isDisplayed(getDriver(), By.className(containerClassName), SHORT_TIMEOUT);
        WebElement thermostatControlContainer = getElement(getDriver(),
                By.className(containerClassName), SHORT_TIMEOUT);
        List<WebElement> buttonElements = getElementsBySubElement(getDriver(),
                thermostatControlContainer, By.tagName(TAG_INPUT), SHORT_TIMEOUT);
        int loop = 0;
        String colorValue = null;
        for (WebElement element : buttonElements) {
            String classname = element.getAttribute("className");
            if (element.getAttribute(ATTR_TYPE).endsWith("button") && classname != null
                    && !classname.isEmpty()) {
                String classNameSplit = classname.contains(SELECTED) ? classname.split(" ")[0]
                        : classname;
                JavascriptExecutor js = (JavascriptExecutor) getDriver();
                Object val = js
                        .executeScript("return window.document.defaultView.getComputedStyle(window.document.getElementsByClassName('"
                                + classNameSplit
                                + "')['"
                                + loop
                                + "']).getPropertyValue('background-color');");
                loop++;
                String rgbValue = val.toString();
                rgbValue = rgbValue.substring(5, 16);
                if (classname.contains(SELECTED) && mode.equalsIgnoreCase("cool")) {
                    LogUtil.setLogString(
                            "Verify the Color for '" + element.getAttribute(ATTR_VALUE)
                                    + "' (Button selected)", true, CustomLogLevel.LOW);
                    LogUtil.setLogString("Expected Color " + COOL_MODE_COLOR, true,
                            CustomLogLevel.MEDIUM);
                    LogUtil.setLogString("The Color in UI is :" + rgbValue, true,
                            CustomLogLevel.MEDIUM);
                    colorValue = rgbValue;
                } else if (classname.contains(SELECTED) && mode.equalsIgnoreCase("heat")) {
                    LogUtil.setLogString(
                            "Verify the Color for '" + element.getAttribute(ATTR_VALUE)
                                    + "' (Button selected)", true, CustomLogLevel.LOW);
                    LogUtil.setLogString("Expected Color " + HEAT_MODE_COLOR, true,
                            CustomLogLevel.MEDIUM);
                    LogUtil.setLogString("The Color in UI is :" + rgbValue, true,
                            CustomLogLevel.MEDIUM);
                    colorValue = rgbValue;
                }
            }
        }
        return colorValue;

    }

    /**
     * Gets the rgb for class name.
     * @param classname the classname
     * @return the rgb for class name
     */
    private String getRgbForClassName(final String classname) {

        Object backgroundCss = executeScriptByClassName(classname, "border-bottom-color",
                getDriver());
        String rgbValue = backgroundCss.toString();
        return rgbValue.substring(4, 15);
    }
}
