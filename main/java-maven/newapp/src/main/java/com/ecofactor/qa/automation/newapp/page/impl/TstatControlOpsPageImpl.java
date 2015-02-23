/*
 * TstatControlOpsPageImpl.java
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
import static com.ecofactor.qa.automation.platform.util.Pageutil.*;
import static com.ecofactor.qa.automation.util.WaitUtil.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.ecofactor.qa.automation.newapp.page.ThermostatPageOps;
import com.ecofactor.qa.automation.newapp.page.ThermostatPageUI;
import com.ecofactor.qa.automation.newapp.page.TstatControlOpsPage;
import com.ecofactor.qa.automation.newapp.page.TstatControlUIPage;
import com.ecofactor.qa.automation.platform.enums.CustomLogLevel;
import com.ecofactor.qa.automation.platform.enums.Marker;
import com.ecofactor.qa.automation.platform.util.LogUtil;
import com.ecofactor.qa.automation.util.DriverConfig;
import com.ecofactor.qa.automation.util.WaitUtil;
import com.google.inject.Inject;

/**
 * The Class TstatControlOpsPageImpl.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class TstatControlOpsPageImpl extends AbstractAuthenticationPageImpl implements
        TstatControlOpsPage {

    /** The Constant HEAT. */
    private static final String HEAT = "heat";

    /** The Constant COOL. */
    private static final String COOL = "cool";

    /** The Constant OFF. */
    private static final String OFF = "off";

    /** The Constant AUTO. */
    private static final String AUTO = "auto";

    /** The Constant FAN_ON. */
    private static final String FAN_ON = "on";

    /** The Constant UP_ARROW_BTN. */
    @SuppressWarnings("unused")
    private static final String UP_ARROW_BTN = "div.upArrowClickArea";

    /** The Constant DOWN_ARROW_BTN. */
    @SuppressWarnings("unused")
    private static final String DOWN_ARROW_BTN = "div.downArrowClickArea";

    /** The Constant TSTAT_CONTROLS_CLOSE_BTN. */
    private static final String TSTAT_CONTROLS_CLOSE_BTN = "thermostatControlsCloseButton";

    /** The Constant COOL_CONTAINER. */
    private static final String COOL_CONTAINER = "coolContainer";

    /** The Constant HEAT_CONTAINER. */
    private static final String HEAT_CONTAINER = "heatContainer";

    /** The Constant HVAC_MODE_BTN. */
    private static final String HVAC_MODE_BTN = "thermostatHvacModeButton";

    // private static final String THERMOSTAT_CONTAINER = "thermostat_container";

    /** The Constant SETPOINT_CONTAINER. */
    private static final String SETPOINT_CONTAINER = "div.ViewThermostatControls.ui-dialog-content.ui-widget-content";

    /** The Constant CURRENT_TEMPERATURE. */
    private static final String CURRENT_TEMPERATURE = "currentTemperature";

    /** The Constant THERMOSTAT_LABEL. */
    private static final String THERMOSTAT_LABEL = ".thermostatText";

    /** The Constant THERMOSTAT_SCHEDULE_LABEL. */
    private static final String THERMOSTAT_SCHEDULE_LABEL = ".scheduledTemp";

    /** The Constant COOL_ON_AUTO. */
    private static final String COOL_ON_AUTO = ".thermostat_control_container input[type=button].toggleButton";

    /** The Constant COOL_HEAT_BACKCOLOR. */
    private static final String COOL_HEAT_BACKCOLOR = "background-color";

    /** The Constant ACTAUL_COLOR. */
    private static final String ACTAUL_COLOR = "rgba(255, 255, 255, 0)";

    /** The Constant HEAT_MODE_COLOR. */
    private final static String HEAT_MODE_COLOR = "255, 118, 0";

    /** The Constant COOL_MODE_COLOR. */
    private final static String COOL_MODE_COLOR = "0, 179, 227";

    /** The th container. */
    private WebElement thContainer = null;

    /** The Constant MAX_COOL. */
    private final static Integer MAX_COOL = 89;

    /** The Constant MAX_HEAT. */
    private final static Integer MAX_HEAT = 89;

    /** The Constant MIN_COOL. */
    private final static Integer MIN_COOL = 65;

    /** The Constant MIN_HEAT. */
    private final static Integer MIN_HEAT = 45;

    /** The th ctrl ui page. */
    @Inject
    protected TstatControlUIPage thCtrlUIPage;

    /** The th page ops. */
    @Inject
    protected ThermostatPageOps thPageOps;

    /** The th page ui. */
    @Inject
    private ThermostatPageUI thPageUI;

    /** The th ctrl ops page. */
    @Inject
    private TstatControlOpsPage thCtrlOpsPage;

    /**
     * Checks if is page loaded.
     * @return true, if is page loaded
     * @see com.ecofactor.qa.automation.mobile.page.BasePage#isPageLoaded()
     */
    @Override
    public boolean isPageLoaded() {

        LogUtil.setLogString("Verify the Thermostat Control Page is loaded", true,
                CustomLogLevel.LOW);
        final boolean isPopUpOpened = isPopUpOpened();
        getAction().rejectAlert();
        boolean isLoaded;
        if (!isPopUpOpened) {
            LogUtil.setLogString(
                    Marker.START,
                    "Control page not opened by click on target temp, So click current temp as an workaround",
                    true);
            clickCurrentTemp();
            isLoaded = isDisplayed(getDriver(), By.className(TSTAT_CONTROLS_CLOSE_BTN),
                    TINY_TIMEOUT);
            LogUtil.setLogString(Marker.END, "Control page loaded", true);
        } else {
            LogUtil.setLogString("Tstat Control page opened", true, CustomLogLevel.HIGH);
            isLoaded = true;
        }
        return isLoaded;
    }

    /**
     * Change To Heat.
     */
    @Override
    public void changeToHeat() {

        LogUtil.setLogString("Change to heat mode", true, CustomLogLevel.LOW);
        if (isModeHeat()) {
            clickModeCool();
        }
        clickModeHeat();
    }

    /**
     * Change To Cool.
     */
    @Override
    public void changeToCool() {

        LogUtil.setLogString("Change to cool mode", true, CustomLogLevel.LOW);
        if (isModeCool()) {

            clickModeHeat();
        }
        clickModeCool();
    }

    /**
     * Change to off.
     * @see com.ecofactor.qa.automation.mobile.page.ThermostatControlPage#changeToOff()
     */
    @Override
    public void changeToOff() {

        LogUtil.setLogString("Change to OFF mode", true, CustomLogLevel.LOW);
        clickModeOff();
    }

    /**
     * Click fan on.
     */
    @Override
    public void clickFanOn() {

        LogUtil.setLogString("Click Fan On", true, CustomLogLevel.LOW);
        WebElement fanOnElement = getElementBySubElementAttr(getDriver(),
                getCurrentThermostatContainer(), By.tagName(TAG_INPUT), ATTR_VALUE, FAN_ON,
                SHORT_TIMEOUT);
        getAction().click(fanOnElement);
    }

    /**
     * Click fan on.
     */
    @Override
    public void clickFanAuto() {

        LogUtil.setLogString("Click Fan Auto", true, CustomLogLevel.LOW);
        WebElement fanAutoElement = getElementBySubElementAttr(getDriver(),
                getCurrentThermostatContainer(), By.tagName(TAG_INPUT), ATTR_VALUE, AUTO,
                SHORT_TIMEOUT);
        getAction().click(fanAutoElement);
    }

    /**
     * Click Close Button.
     */
    @Override
    public void closeThermostatControl() {

        LogUtil.setLogString("Click Close button", true, CustomLogLevel.LOW);
        /*
         * isDisplayedBySubElement(getDriver(), getCurrentThermostatContainer(),
         * By.className(TSTAT_CONTROLS_CLOSE_BTN), TINY_TIMEOUT);
         */
        final WebElement closeBtnElement = getElementBySubElement(getDriver(),
                getCurrentThermostatContainer(), By.className(TSTAT_CONTROLS_CLOSE_BTN),
                TINY_TIMEOUT);
        // WaitUtil.fourSec();
        thPageOps.clickotherplace();
        closeBtnElement.click();
        // closeBtnElement.sendKeys(Keys.RETURN);
        // getAction().click(closeBtnElement);
        getAction().rejectAlert();
        thContainer = null;
    }

    /**
     * Sets the point change.
     * @param setPoint the new point change
     * @see com.ecofactor.qa.automation.mobile.page.ThermostatControlPage#setPointChange(java.lang.Integer)
     */
    public void setPointChange(final Integer setPoint) {

        WaitUtil.smallWait();
        if (setPoint > 0) {
            increaseSetPoint(setPoint);
        } else {
            decreaseSetPoint(setPoint);
        }
        getToastErrorMessage();
    }

    /**
     * Increase set point.
     * @param setPoint the set point
     */
    private void increaseSetPoint(final Integer setPoint) {

        LogUtil.setLogString("Increase set point of: " + setPoint, true, CustomLogLevel.LOW);
        /*
         * WebElement upElement = getElement(getDriver(), By.className("upArrowButton"),
         * TINY_TIMEOUT);
         */
        // System.out.println("upElement" + upElement.getAttribute("class"));
        for (int i = 0; i < setPoint; i++) {

            getElement(getDriver(), By.className("upArrowButton"), TINY_TIMEOUT).click();
            LogUtil.setLogString("Clicked: " + i, true, CustomLogLevel.LOW);
            waitUntil(300);

            // upElement.click();

        }
    }

    /**
     * Decrease set point.
     * @param setPoint the set point
     */
    private void decreaseSetPoint(final Integer setPoint) {

        LogUtil.setLogString("Decrease set point of: " + setPoint, true, CustomLogLevel.LOW);

        /*
         * WebElement downArrowElement = getElementBySubElement(getDriver(),
         * getTstatSetPointContainer(), By.className("downArrowButton"), TINY_TIMEOUT);
         */

        for (int i = 0; i < -setPoint; i++) {

            getElement(getDriver(), By.className("downArrowButton"), TINY_TIMEOUT).click();
            LogUtil.setLogString("Clicked: " + i, true, CustomLogLevel.LOW);
            waitUntil(300);

        }
    }

    /**
     * Temporary method. Which we rechanged again.
     * @param url the url
     * @param params the params
     * @param expectedStatus the expected status
     * @return the e eapi
     * @see com.ecofactor.qa.automation.newapp.page.TstatControlOpsPage#getEEapi(java.lang.String,
     *      java.util.Map, int)
     */
    public String getEEapi(String url, Map<String, String> params, int expectedStatus) {

        String content = null;
        HttpGet request = new HttpGet(url);
        URIBuilder builder = new URIBuilder(request.getURI());

        if (params != null) {
            final Set<String> keys = params.keySet();
            for (String key : keys) {
                builder.addParameter(key, params.get(key));
            }
        }
        try {
            request.setURI(builder.build());
            DriverConfig.setLogString("URL " + request.getURI().toString(), true);
            getDriver().navigate().to(request.getURI().toString());
            largeWait();
            content = getDriver().findElement(By.tagName("Body")).getText();
            DriverConfig.setLogString("Content: " + content, true);
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            request.releaseConnection();
        }

        return content;

    }

    /**
     * Sets the point change.
     * @param setPoint the set point
     * @param expectedValue the expected value
     * @see com.ecofactor.qa.automation.newapp.page.ThermostatControlPage#setPointChange(java.lang.Integer,
     *      java.lang.Integer)
     */
    public void setPointChange(Integer setPoint, Integer expectedValue) {

        boolean markerEnabled = false;
        int count = 0;
        Integer targetTemp;
        do {
            try {
                setPointChange(setPoint);               
            } catch (Exception e) {
                LogUtil.setLogString("Work Around", true);                             
            }
            count++;
            thCtrlOpsPage.closeThermostatControl();
            WaitUtil.oneSec();
            thPageOps.clickControlsIcon();  
            targetTemp = Integer.valueOf(thCtrlUIPage.getTargetTemperature());
            setPoint = expectedValue - targetTemp;                      
           // waitUntil(300);  
            setPointChange(setPoint);           
            if (setPoint != 0) {
                markerEnabled = true;
                if (markerEnabled && count == 1) {
                    LogUtil.setLogString(Marker.START, "Iteration:" + count
                            + ". Iterate to achieve the Target temperature.", true);
                }
                LogUtil.setLogString("Wait 20 Seconds for verifying the target temparture in UI",
                        true, CustomLogLevel.HIGH);
                WaitUtil.mediumWait();
                targetTemp = Integer.valueOf(thCtrlUIPage.getTargetTemperature());
                setPoint = expectedValue - targetTemp;
            }
        } while (targetTemp != expectedValue && count <= 7);

        if (markerEnabled) {
            LogUtil.setLogString(Marker.END, "Iteration Completed", true);
            markerEnabled = false;
        }
    }

    /**
     * Click mode heat.
     */
    public void clickModeHeat() {

        LogUtil.setLogString("Click mode heat", true, CustomLogLevel.MEDIUM);
        isDisplayedBySubElement(getDriver(), getCurrentThermostatContainer(),
                By.className(HVAC_MODE_BTN), TINY_TIMEOUT);
        WebElement heatModeElement = getCurrentThermostatContainer();
        List<WebElement> thermostatElementList = getElementsBySubElement(getDriver(),
                heatModeElement, By.className(HVAC_MODE_BTN), TINY_TIMEOUT);
        for (WebElement webElement : thermostatElementList) {
            if (webElement.getAttribute(ATTR_VALUE).equalsIgnoreCase(HEAT)) {
                getAction().click(webElement);
                break;
            }
        }
        getAction().rejectAlert();
        verifyIdleState();
        LogUtil.setLogString("Check color changed to Red", true, CustomLogLevel.MEDIUM);
        Assert.assertTrue(verifyColorChanges(HEAT_CONTAINER), "color is not changed");
    }

    /**
     * Click mode cool.
     */
    public void clickModeCool() {

        LogUtil.setLogString("Click mode Cool", true, CustomLogLevel.MEDIUM);
        WebElement heatModeElement = getCurrentThermostatContainer();
        List<WebElement> thermostatElementList = getElementsBySubElement(getDriver(),
                heatModeElement, By.className(HVAC_MODE_BTN), MEDIUM_TIMEOUT);
        for (WebElement webElement : thermostatElementList) {
            if (webElement.getAttribute(ATTR_VALUE).equalsIgnoreCase(COOL)) {
                WaitUtil.oneSec();
                webElement.click();
                tinyWait();
                // getAction().click(webElement);
                break;
            }
        }
        getAction().rejectAlert();
        verifyIdleState();
        LogUtil.setLogString("Check color changed to blue", true, CustomLogLevel.MEDIUM);
        Assert.assertTrue(verifyColorChanges(COOL_CONTAINER), "color is not changed to blue");
    }

    /**
     * Click mode off.
     */
    public void clickModeOff() {

        LogUtil.setLogString("Click mode off", true, CustomLogLevel.LOW);
        WebElement offModeElement = getCurrentThermostatContainer();
        List<WebElement> thermostatElementList = getElementsBySubElement(getDriver(),
                offModeElement, By.className(HVAC_MODE_BTN), TINY_TIMEOUT);
        for (WebElement webElement : thermostatElementList) {
            if (webElement.getAttribute(ATTR_VALUE).equalsIgnoreCase(OFF)) {
                getAction().click(webElement);
                break;
            }
        }
    }

    /**
     * Cleanup.
     * @see com.ecofactor.qa.automation.newapp.page.BasePage#cleanup()
     */
    @Override
    public void cleanup() {

    }

    /**
     * Checks if is pop up opened.
     * @return true, if is pop up opened
     * @see com.ecofactor.qa.automation.newapp.page.TstatControlOpsPage#isPopUpOpened()
     */
    @Override
    public boolean isPopUpOpened() {

        LogUtil.setLogString(
                "Check thermostat control page is opened and not closed after few seconds", true,
                CustomLogLevel.LOW);
        WaitUtil.tinyWait();
        boolean isDisplayed = isDisplayedBySubElement(getDriver(), getCurrentThermostatContainer(),
                By.className(TSTAT_CONTROLS_CLOSE_BTN), TINY_TIMEOUT);
        return isDisplayed;
    }

    /**
     * Check and update boundary. If the setpoint change is nearest to the boundary, then the
     * requiredChange of setpoint will be applied
     * @param requiredChange the required change
     */
    @Override
    public void checkAndUpdateBoundary(Integer requiredChange) {

        LogUtil.setLogString("Check set point boundary condition for target temperature", true,
                CustomLogLevel.MEDIUM);
        final Integer targetTemp = Integer.valueOf(thCtrlUIPage.getTargetTemperature());
        if (targetTemp == MAX_COOL || targetTemp == MAX_HEAT || targetTemp >= 86) {
            setPointChange(-requiredChange);
        } else if (targetTemp == MIN_COOL || targetTemp == MIN_HEAT || targetTemp <= 75) {
            setPointChange(requiredChange);
        }
    }

    /**
     * Click savings icon.
     * @see com.ecofactor.qa.automation.newapp.page.TstatControlOpsPage#clickSavingsIcon()
     */
    @Override
    public void clickSavingsIcon() {

        LogUtil.setLogString("Click Savings Icon", true, CustomLogLevel.MEDIUM);
        WebElement savingsElement = getElement(getDriver(),
                By.cssSelector(".eeTargetSetpoint.eeActive>img"), MEDIUM_TIMEOUT);
        savingsElement.click();
    }

    /**
     * Click current temp.
     */
    private void clickCurrentTemp() {

        LogUtil.setLogString("Click CurrentTemperature", true, CustomLogLevel.MEDIUM);
        isDisplayedBySubElement(getDriver(), getCurrentThermostatContainer(),
                By.className(CURRENT_TEMPERATURE), TINY_TIMEOUT);
        WebElement setPointElement = getElementBySubElement(getDriver(),
                getCurrentThermostatContainer(), By.className(CURRENT_TEMPERATURE), TINY_TIMEOUT);
        try {
            getAction().click(setPointElement);
        } catch (Exception e) {
            LogUtil.setLogString("Cannot click current temperature (Exception : " + e.getMessage()
                    + ")", true);
        }
    }

    /**
     * Checks if is mode heat.
     * @return true, if is mode heat
     */
    private boolean isModeHeat() {

        boolean isHeatMode = isGivenModeSelected(HEAT);
        return isHeatMode;
    }

    /**
     * Checks if is mode heat.
     * @return true, if is mode heat
     */
    private boolean isModeCool() {

        boolean isCoolMode = isGivenModeSelected(COOL);
        return isCoolMode;
    }

    /**
     * Checks if is given mode selected.
     * @param mode the mode
     * @return true, if is given mode selected
     */
    private boolean isGivenModeSelected(String mode) {

        boolean selected = false;
        WebElement element = getElementBySubElementAttr(getDriver(),
                getCurrentThermostatContainer(), By.tagName(TAG_INPUT), ATTR_VALUE, mode,
                MEDIUM_TIMEOUT);
        if (element.getAttribute("class").contains("selected")) {
            String modeValue = element.getAttribute("value").toLowerCase();
            if (modeValue.equalsIgnoreCase(mode)) {
                LogUtil.setLogString(mode + " mode is selected", true, CustomLogLevel.MEDIUM);
                selected = true;
            }
        }
        return selected;
    }

    /**
     * Verify idle state.
     */
    private void verifyIdleState() {

        LogUtil.setLogString("Verify the Current mode is in idle state", true,
                CustomLogLevel.MEDIUM);
        String currentTemp = thCtrlUIPage.getCurrentTemperature();
        String targetTemp = thCtrlUIPage.getTargetTemperature();
        if (currentTemp != null && currentTemp.length() >= 1
                && currentTemp.equalsIgnoreCase(targetTemp)) {
            LogUtil.setLogString("Set point change of 1", true);
            WaitUtil.tinyWait();
            setPointChange(1);
        }
    }

    /**
     * Verify color changes.
     * @param modeContainer the mode container
     * @return true, if successful
     */
    private boolean verifyColorChanges(String modeContainer) {

        boolean iscolorChanged = isDisplayed(getDriver(),
                By.cssSelector("div[class*='" + modeContainer + "']"), MEDIUM_TIMEOUT);
        thContainer = null;
        return iscolorChanged;
    }

    /**
     * Gets the tstat set point container.
     * @return the tstat set point container
     */
    @SuppressWarnings("unused")
    private WebElement getTstatSetPointContainer() {

        if (thContainer == null) {

            /*
             * final List<WebElement> currentContainer = getElements(getDriver(),
             * By.cssSelector(THERMOSTAT_CONTAINER), TINY_TIMEOUT);
             */
            final List<WebElement> currentContainer = getElements(getDriver(),
                    By.cssSelector(SETPOINT_CONTAINER), TINY_TIMEOUT);

            for (final WebElement webElement : currentContainer) {
                thContainer = webElement;
                break;
                /*
                 * if (webElement.isDisplayed() &&
                 * !webElement.getAttribute(ATTR_CLASS).contains("hidden")) { thContainer =
                 * webElement; break; }
                 */
            }
        }
        return thContainer;
    }

    /**
     * verify either schedule message is display or not.
     * @return true, if schedule message is display.
     * @see com.ecofactor.qa.automation.newapp.page.TstatControlOpsPage#displayScheduleMessage()
     */
    @Override
    public boolean displayScheduleMessage() {

        LogUtil.setLogString("Verify Schedule message is display.", true, CustomLogLevel.MEDIUM);
        final boolean tstsLabel = isDisplayed(getDriver(), By.cssSelector(THERMOSTAT_LABEL),
                TINY_TIMEOUT);
        if (tstsLabel == true) {

            isDisplayed(getDriver(), By.cssSelector(THERMOSTAT_SCHEDULE_LABEL), TINY_TIMEOUT);
            final WebElement scheduleLabel = getElement(getDriver(),
                    By.cssSelector(THERMOSTAT_SCHEDULE_LABEL), TINY_TIMEOUT);

            LogUtil.setLogString("Shcedule Message:" + scheduleLabel.getText(), true,
                    CustomLogLevel.MEDIUM);
        }
        return true;
    }

    /**
     * Verify the color of Cool or heat label.
     * @param mode the mode
     * @return true, if property color value and declared value is matched.
     * @see com.ecofactor.qa.automation.newapp.page.TstatControlOpsPage#coolColor()
     */
    @Override
    public boolean labelColor(String mode) {

        LogUtil.setLogString("Verify Cool or Heat label color.", true, CustomLogLevel.MEDIUM);

        String coolColor = "";
        String heatColor = "";
        final JavascriptExecutor javascript = (JavascriptExecutor) getDriver();

        if (mode.equalsIgnoreCase(COOL)) {

            LogUtil.setLogString("Verify Cool label color.", true, CustomLogLevel.MEDIUM);
            Object cool = javascript
                    .executeScript("return window.getComputedStyle(document.querySelectorAll('"
                            + COOL_ON_AUTO + "')[0],null).getPropertyValue('" + COOL_HEAT_BACKCOLOR
                            + "')");
            coolColor = cool.toString();
        } else if (mode.equalsIgnoreCase(HEAT)) {

            LogUtil.setLogString("Verify Heat label color.", true, CustomLogLevel.MEDIUM);
            Object heat = javascript
                    .executeScript("return window.getComputedStyle(document.querySelectorAll('"
                            + COOL_ON_AUTO + "')[0],null).getPropertyValue('" + COOL_HEAT_BACKCOLOR
                            + "')");
            heatColor = heat.toString();
        }

        Object on = javascript
                .executeScript("return window.getComputedStyle(document.querySelectorAll('"
                        + COOL_ON_AUTO + "')[0],null).getPropertyValue('" + COOL_HEAT_BACKCOLOR
                        + "')");
        String onColor = on.toString();

        Object auto = javascript
                .executeScript("return window.getComputedStyle(document.querySelectorAll('"
                        + COOL_ON_AUTO + "')[0],null).getPropertyValue('" + COOL_HEAT_BACKCOLOR
                        + "')");
        String autoColor = auto.toString();

        LogUtil.setLogString("Color Value:" + ACTAUL_COLOR, true);
        if (coolColor.equals(ACTAUL_COLOR) || onColor.equals(ACTAUL_COLOR)
                || autoColor.equals(ACTAUL_COLOR) || heatColor.equals(ACTAUL_COLOR)) {
            return true;
        }
        return false;

    }

    /**
     * verify color of separator line in cool or heat value.
     * @param mode the mode.
     * @return true, if property color value and declared value is matched.
     * @see com.ecofactor.qa.automation.newapp.page.TstatControlOpsPage#separatorLine(java.lang.String)
     */
    @Override
    public boolean separatorLine(String mode) {

        LogUtil.setLogString("Verify Separator line color in Heat or Cool mode.", true,
                CustomLogLevel.MEDIUM);

        if (mode.equalsIgnoreCase(HEAT)) {

            LogUtil.setLogString("Expected Color in Heat Mode " + HEAT_MODE_COLOR, true);
            LogUtil.setLogString("The Color in UI is :" + thCtrlUIPage.getRgbForSeperator(), true);
            Assert.assertTrue(thCtrlUIPage.getRgbForSeperator().equalsIgnoreCase(HEAT_MODE_COLOR),
                    "Color Differs");
        } else if (mode.equalsIgnoreCase(COOL)) {

            LogUtil.setLogString("Expected Color in Cool Mode " + COOL_MODE_COLOR, true);
            LogUtil.setLogString("The Color in UI is :" + thCtrlUIPage.getRgbForSeperator(), true);
            Assert.assertTrue(thCtrlUIPage.getRgbForSeperator().equalsIgnoreCase(COOL_MODE_COLOR),
                    "Color Differs");
        }

        return false;
    }

    /**
     * Verify modes were enable in thermostat control popup.
     * @param mode the mode.
     * @see com.ecofactor.qa.automation.newapp.page.TstatControlOpsPage#verifyModesEnable(java.lang.String)
     */
    @Override
    public void verifyModesEnable(String mode) {

        LogUtil.setLogString("Verify Modes COOL/HEAT/OFF Display.", true, CustomLogLevel.MEDIUM);

        if (mode.equalsIgnoreCase(COOL)) {

            LogUtil.setLogString("HEAT mode is Enabled.", true);
            clickModeHeat();
            mode = thPageUI.getCurrentMode();
            if (mode.equalsIgnoreCase(HEAT)) {

                LogUtil.setLogString("OFF mode is Enabled.", true);
                clickModeOff();
                thPageOps.turnSystemOn();
            }
        } else if (mode.equalsIgnoreCase(HEAT)) {

            LogUtil.setLogString("COOL mode is Enabled.", true);
            clickModeCool();
            mode = thPageUI.getCurrentMode();
            if (mode.equalsIgnoreCase(COOL)) {

                LogUtil.setLogString("OFF mode is Enabled.", true);
                clickModeOff();
                thPageOps.turnSystemOn();
            }
        }
    }
}
