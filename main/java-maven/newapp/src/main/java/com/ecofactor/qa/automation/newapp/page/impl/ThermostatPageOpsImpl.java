/*
 * ThermostatPageOpsImpl.java
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

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.ecofactor.qa.automation.newapp.MobileConfig;
import com.ecofactor.qa.automation.newapp.page.LoginPage;
import com.ecofactor.qa.automation.newapp.page.ThermostatPageOps;
import com.ecofactor.qa.automation.newapp.page.ThermostatPageUI;
import com.ecofactor.qa.automation.platform.enums.CustomLogLevel;
import com.ecofactor.qa.automation.platform.enums.Marker;
import com.ecofactor.qa.automation.util.DateUtil;
import com.ecofactor.qa.automation.util.WaitUtil;
import com.google.inject.Inject;

/**
 * The Class ThermostatPageOpsImpl.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class ThermostatPageOpsImpl extends AbstractAuthenticationPageImpl implements
        ThermostatPageOps {

    /** The Constant SET_AWAY. */
    private static final String SET_AWAY = ".awayButton";

    /** The Constant HELP_CLOSE. */
    private static final String HELP_CLOSE = "closeButton";

    /** The Constant HELP_DIV. */
    private static final String HELP_DIV = "ftux-background";

    /** The Constant SETPOINT_CONTAINER. */
    private static final String SETPOINT_CONTAINER = "div.setPointContainer";

    /** The Constant CURRENT_TEMPERATURE. */
    private static final String CURRENT_TEMPERATURE = "currentTemperature";

    /** The Constant OFF_MODE_BACK. */
    private static final String OFF_MODE_BACK = "offModeBack";

    /** The Constant SETPOINT. */
    private static final String SETPOINT = "div.setPoint";

    /** The Constant LOGOUT. */
    private static final String LOGOUT = "div.menu_row.logout";

    /** The Constant MENU. */
    private static final String MENU = ".menuClick";

    /** The Constant SAVINGS_ENERGY. */
    private static final String SAVINGS_ENERGY = ".savings";

    /** The Constant THERMOSTAT_SWITCHER_ICON. */
    private static final String THERMOSTAT_SWITCHER_ICON = "thermostat_switcher_icon";

    /** The Constant HEAT_COOL_ICON. */
    private static final String HEAT_COOL_ICON = ".controlsIcon";

    /** The Constant TARGET_TEMPERATURE. */
    @SuppressWarnings("unused")
    private static final String TARGET_TEMPERATURE = "div.thermostatTargetSetpoint";

    /** The Constant SETTINGS_ICON. */
    private static final String SETTINGS_ICON = ".menu_row.settings";

    /** The Constant THERMOSTAT_FIELD. */
    private static final String THERMOSTAT_FIELD = ".item.thermostat.first_thermostat";

    /** The Constant THERMOSTAT_FIELD_NAME. */
    private static final String THERMOSTAT_FIELD_NAME = "HALL";

    /** The Constant INSTALLED_THERMOSTAT. */
    private static final String INSTALLED_THERMOSTAT = ".section_label.installed_thermostat";

    /** The Constant SELECT_THERMOSTAT. */
    private static final String SELECT_THERMOSTAT = ".menu_row.thermostat";

    /** The Constant THERMOSTAT_FIELD_VALUE. */
    private static final String THERMOSTAT_FIELD_VALUE = ".name_input.item.first_thermostat";

    /** The Constant MENU_BTN. */
    private static final String MENU_BTN = ".menuClick.light";

    /** The Constant SAVINGS_FOOTER. */
    private static final String SAVINGS_FOOTER = ".savings_footer_container";

    /** The Constant SAVINGS_EENERGY. */
    @SuppressWarnings("unused")
    private static final String SAVINGS_EENERGY = ".fadein_text";

    /** The mobile config. */
    @Inject
    protected MobileConfig mobileConfig;

    /** The login page. */
    @Inject
    protected LoginPage loginPage;

    /** The th page ui. */
    @Inject
    protected ThermostatPageUI thPageUI;

    /**
     * Close help.
     * @see com.ecofactor.qa.automation.newapp.page.ThermostatPage#closeHelp()
     */
    @Override
    public void closeHelp() {

        setLogString("Verify and close help page", true, CustomLogLevel.HIGH);
        final boolean isHelpDisplayed = isDisplayed(getDriver(), By.className(HELP_DIV), THREE_SEC);
        if (isHelpDisplayed || !helpNotDisplayed()) {
            clickNextAndCloseHelp();
        }
    }

    /**
     * Click current temp.
     * @see com.ecofactor.qa.automation.mobile.page.ThermostatPage#clickCurrentTemp()
     */
    @Override
    public void clickCurrentTemp() {

        setLogString("Click CurrentTemperature", true, CustomLogLevel.LOW);
        isDisplayedBySubElement(getDriver(), getCurrentThermostatContainer(),
                By.cssSelector(CURRENT_TEMPERATURE), ATOMIC_TIMEOUT);
        final WebElement currentTemperature = getElementBySubElement(getDriver(),
                getCurrentThermostatContainer(), By.className(CURRENT_TEMPERATURE), ATOMIC_TIMEOUT);

        final boolean isClickable = isClickable(getDriver(), currentTemperature, TINY_TIMEOUT);
        if (isClickable) {
            setLogString("Clicked CurrentTemperature", true, CustomLogLevel.LOW);
            try {
                getAction().click(currentTemperature);
                getAction().rejectAlert();
            } catch (WebDriverException wde) {
                setLogString("Cannot Click CurrentTemperature", true, CustomLogLevel.LOW);
            }
        } else {
            setLogString("Cannot Click Current Temperature", true, CustomLogLevel.LOW);
        }

    }

    /**
     * Sets the away popup closing.
     * @see com.ecofactor.qa.automation.newapp.page.ThermostatPage#setAway()
     */
    @Override
    public void setAway() {

        loadAwaySettingsPopup();

        final WebElement footerElement = getElement(getDriver(),
                By.cssSelector(".footerAwayPicker"), SHORT_TIMEOUT);
        final WebElement awaySettingsPopUp = getElementBySubElement(getDriver(), footerElement,
                By.cssSelector(".ctaButton.setAwaySubmitButton"), SHORT_TIMEOUT);
        awaySettingsPopUp.click();
        // getAction().click(awaySettingsPopUp);
        getAction().rejectAlert();
        if (awaySettingsPopUp != null) {
            // getAction().click(awaySettingsPopUp);
            awaySettingsPopUp.click();
            WaitUtil.oneSec();
            getAction().rejectAlert();
        }
    }

    /**
     * Load away settings popup.
     * @return true, if successful
     */
    @Override
    public boolean loadAwaySettingsPopup() {

        setLogString("Check whether set away popup is already opened", true);
        if (isDisplayed(getDriver(), By.cssSelector("input.ctaButton.setAwayCancelButton"),
                TINY_TIMEOUT)) {
            setLogString("Away settings popup already open. Closing the popup", true);
            final WebElement cancelButton = getElement(getDriver(),
                    By.cssSelector(".ctaButton.setAwayCancelButton"), TINY_TIMEOUT);
            getAction().click(cancelButton);
            getAction().rejectAlert();
        }

        clickSetAway();
        return thPageUI.isAwaySettingsPopUpLoaded();
    }

    /**
     * Turn system on.
     * @see com.ecofactor.qa.automation.mobile.page.ThermostatPage#turnSystemOn()
     */
    @Override
    public void turnSystemOn() {

        setLogString("Turn System ON", true, CustomLogLevel.LOW);
        isDisplayedBySubElement(getDriver(), getCurrentThermostatContainer(),
                By.className(OFF_MODE_BACK), TINY_TIMEOUT);
        final WebElement turnSystemON = getElementBySubElement(getDriver(),
                getCurrentThermostatContainer(), By.className(OFF_MODE_BACK), TINY_TIMEOUT);
        getAction().click(turnSystemON);
        getAction().rejectAlert();
        Assert.assertFalse(
                isDisplayedBySubElement(getDriver(), getCurrentThermostatContainer(),
                        By.className(OFF_MODE_BACK), TINY_TIMEOUT), "Fails to Turn ON System in UI");
    }

    /**
     * Click target.
     * @see com.ecofactor.qa.automation.mobile.page.ThermostatPage#clickTarget()
     */
    @Override
    public void openTstatController() {

        setLogString("Click Target", true, CustomLogLevel.LOW);
        isDisplayedBySubElement(getDriver(), getCurrentThermostatContainer(),
                By.cssSelector(SETPOINT), TINY_TIMEOUT);
        /*
         * final WebElement setPointElement = getElementBySubElement(getDriver(),
         * getCurrentThermostatContainer(), By.cssSelector("div[class*='" + SETPOINT_CONTAINER +
         * "']"), TINY_TIMEOUT);
         */
        final WebElement setPointElement = getElementBySubElement(getDriver(),
                getCurrentThermostatContainer(), By.cssSelector(SETPOINT_CONTAINER), TINY_TIMEOUT);
        WaitUtil.oneSec();
        final boolean isClickable = isClickable(getDriver(), setPointElement, TINY_TIMEOUT);
        waitUntil(FIVE_SECS);
        if (isClickable) {
            try {
                getAction().click(setPointElement);
                WaitUtil.oneSec();
                getAction().rejectAlert();
            } catch (WebDriverException wde) {
                setLogString("Cannot Click Target Temperature", true, CustomLogLevel.LOW);
            }
        } else {
            setLogString("Cannot Click Target Temperature", true, CustomLogLevel.LOW);
        }

    }

    /**
     * Click logout.
     * @see com.ecofactor.qa.automation.mobile.page.ThermostatPage#clickLogout()
     */
    @Override
    public void logout() {

        setLogString("Click Logout", true, CustomLogLevel.LOW);
        final String currentUTCTime = DateUtil.getUTCCurrentTimeStamp();
        final boolean isLogoutIconDisplayed = isDisplayed(getDriver(), By.cssSelector(LOGOUT),
                SHORT_TIMEOUT);
        if (isLogoutIconDisplayed) {
            setLogString("\033[46;1mLOGOUT REQUEST - UTC TIME: " + currentUTCTime + "\033[0m", true);
            final WebElement logoutElement = getElement(getDriver(), By.cssSelector(LOGOUT),
                    SHORT_TIMEOUT);
            getAction().click(logoutElement);
            getAction().rejectAlert();
            loginPage.setLoggedIn(false);
            loginPage.setLoggedInUser(null);
        }
    }

    /**
     * Cleanup. the exception
     * @see com.ecofactor.qa.automation.mobile.page.BasePage#cleanup()
     */
    public void cleanup() {

        setLogString("Cleanup in Thermostat Page.", true, CustomLogLevel.HIGH);
        clickMenu();
        logout();
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
     * Swipe.
     * @param LeftOrRight the left or right
     * @see com.ecofactor.qa.automation.newapp.page.ThermostatPageOps#swipe(java.lang.String)
     */
    @Override
    public void swipe(final String LeftOrRight) {

        /*
         * setLogString("Verify More than one thermostat is available ", true, CustomLogLevel.HIGH);
         * final List<WebElement> thermostatContainer = getElements(getDriver(),
         * By.cssSelector("div.viewPage"), TINY_TIMEOUT);
         * Assert.assertTrue(thermostatContainer.size() > 1, "No.of thermostat :" +
         * thermostatContainer.size() + ".No swipe occurs."); setLogString("Swipe " + LeftOrRight +
         * " for a thermostat ", true, CustomLogLevel.LOW); if (thermostatContainer.size() > 1) {
         * final WebElement page = getElement(getDriver(), By.cssSelector("div.bodyAxisContainer"),
         * TINY_TIMEOUT); if (LeftOrRight.equalsIgnoreCase("right")) {
         * getAction().doSwipeLeft(page); } else { getAction().doSwipeRight(page); } tinyWait(); }
         */
        setLogString("Swipe " + LeftOrRight + " for a thermostat ", true, CustomLogLevel.LOW);
        final WebElement page = getElement(getDriver(), By.cssSelector("div.viewThermostat"),
                TINY_TIMEOUT);

        if (LeftOrRight.equalsIgnoreCase("left")) {

            getAction().doSwipeLeft(page);
        } else {

            getAction().doSwipeRight(page);
        }
        tinyWait();
    }

    /**
     * Drag target to.
     * @param currentTarget the current target
     * @param newTarget the new target
     * @see com.ecofactor.qa.automation.newapp.page.ThermostatPageOps#dragTargetTo(com.ecofactor.qa.automation.platform.enums.DragAction)
     */
    public void dragTargetTo(Integer currentTarget, final Integer newTarget) {

        setLogString("Drag to  " + newTarget, true, CustomLogLevel.LOW);
        WebElement srcElement = null;
        srcElement = getElement(getDriver(), By.cssSelector(".setPoint"), TINY_TIMEOUT);
        Integer diff = newTarget - currentTarget;
        Integer yOffset = 0;
        if (diff == 0) {
            diff = 1;
        } else {
            diff = diff - 2;
        }

        yOffset = -(17 * diff);
        setLogString("Y Offset : " + yOffset, true, CustomLogLevel.HIGH);
        getAction().dragElement(srcElement, 0, yOffset);
        tinyWait();
        getAction().dropElement(srcElement);
        Integer newTargetValue = Integer.valueOf(thPageUI.getTargetTemperature());
        if (newTarget != newTargetValue) {
            currentTarget = Integer.valueOf(thPageUI.getTargetTemperature());
            diff = newTarget - currentTarget;
            yOffset = -(17 * diff);
            getAction().dragElement(srcElement, 0, yOffset);
        }
    }

    /**
     * Drag and drop target to.
     * @param currentTarget the current target
     * @param newTarget the new target
     * @see com.ecofactor.qa.automation.newapp.page.ThermostatPageOps#dropAndDropTargetTo(java.lang.Integer,
     *      java.lang.Integer)
     */
    public void dragAndDropTargetTo(Integer currentTarget, final Integer newTarget) {

        final int maxIteration = 5;
        int loop = 0;
        do {
            setLogString("Drag to  " + newTarget, true, CustomLogLevel.LOW);
            WebElement srcElement = null;
            srcElement = getElement(getDriver(), By.cssSelector(".setPoint"), TINY_TIMEOUT);
            Integer diff = newTarget - currentTarget;
            Integer yOffset = 0;
            if (diff == 0) {
                diff = 1;
            }
            yOffset = -(17 * diff);
            setLogString("yOffset : " + yOffset, true, CustomLogLevel.HIGH);
            getAction().dragElement(srcElement, 0, yOffset);
            getAction().dropElement(srcElement);
            oneSec();
            currentTarget = Integer.valueOf(thPageUI.getTargetTemperature());
            if (loop == maxIteration) {
                break;
            }
            loop++;
        } while (currentTarget != newTarget);

    }

    /**
     * Drag target horizontal.
     * @see com.ecofactor.qa.automation.newapp.page.ThermostatPageOps#dragTargetHorizontal()
     */
    public void dragTargetHorizontal() {

        setLogString("Drag  horizontally ", true, CustomLogLevel.LOW);
        WebElement srcElement = null;
        srcElement = getElement(getDriver(), By.cssSelector(".setPoint"), TINY_TIMEOUT);
        getAction().dragElement(srcElement, -100, 0);
        getAction().dropElement(srcElement);
    }

    /**
     * Drop target to.
     * @see com.ecofactor.qa.automation.newapp.page.ThermostatPageOps#dropTargetTo(com.ecofactor.qa.automation.platform.enums.DragAction)
     */
    public void dropTarget() {

        setLogString("Drop target ", true, CustomLogLevel.HIGH);
        final WebElement dropElement = getElement(getDriver(), By.cssSelector(".setPoint"),
                TINY_TIMEOUT);
        getAction().dropElement(dropElement);

    }

    /**
     * Click Menu.
     * @see com.ecofactor.qa.automation.newapp.page.ThermostatPageOps#clickMenu()
     */
    @Override
    public void clickMenu() {

        setLogString("Click Menu", true, CustomLogLevel.LOW);
        final boolean isMenuDisplayed = isDisplayed(getDriver(), By.cssSelector(MENU), TINY_TIMEOUT);
        getAction().rejectAlert();
        if (isMenuDisplayed) {
            final WebElement menuElement = getElement(getDriver(), By.cssSelector(MENU),
                    SHORT_TIMEOUT);
            menuElement.click();
            // getAction().click(menuElement);
            tinyWait();
            getAction().rejectAlert();
        }
        setLogString("Menu Page is Displayed", true, CustomLogLevel.LOW);
    }

    /**
     * Target temp change value by drag.
     * @param change the change
     * @see com.ecofactor.qa.automation.newapp.page.ThermostatPageUI#targetTempChangeValueByDrag(int)
     */
    @Override
    public void targetTempChangeValueByDrag(final int change) {

        setLogString("Verify Target Temperature Value Change", true, CustomLogLevel.HIGH);
        final WebElement temperatureSlider = getElement(getDriver(),
                By.cssSelector(".setPointContainer.ui-draggable"), TINY_TIMEOUT);
        if (change < 0) {
            getAction().doSwipeUp(temperatureSlider, temperatureSlider.getLocation().getX(),
                    temperatureSlider.getLocation().getY(), -change, 0.5);
        } else {
            getAction().doSwipeDown(temperatureSlider, temperatureSlider.getLocation().getX(),
                    temperatureSlider.getLocation().getY(), change, 0.5);
        }
    }

    /**
     * Click savings.
     * @see com.ecofactor.qa.automation.newapp.page.ThermostatPageOps#clickSavings()
     */
    @Override
    public void clickSavings() {

        setLogString("Click Savings", true, CustomLogLevel.HIGH);
        final WebElement footerContainer = getElement(getDriver(), By.cssSelector(SAVINGS_FOOTER),
                TINY_TIMEOUT);
        final boolean savingsLinkDisplayed = isDisplayedBySubElement(getDriver(), footerContainer,
                By.cssSelector(SAVINGS_ENERGY), TINY_TIMEOUT);
        if (savingsLinkDisplayed) {
            final WebElement savingsElement = getElementBySubElement(getDriver(), footerContainer,
                    By.cssSelector(SAVINGS_ENERGY), TINY_TIMEOUT);
            savingsElement.click();
            getAction().rejectAlert();
        }

    }

    /**
     * Help not displayed.
     * @return true, if successful
     */
    private boolean helpNotDisplayed() {

        final boolean isHelpNotDisplayed = isNotDisplayed(getDriver(), By.className(HELP_DIV),
                TINY_TIMEOUT);
        return isHelpNotDisplayed;
    }

    /**
     * Click close help.
     */
    @SuppressWarnings("unused")
    private void clickCloseHelp() {

        setLogString("Close Help", true, CustomLogLevel.LOW);
        final WebElement closeBack = getElement(getDriver(), By.className(HELP_DIV), TINY_TIMEOUT);
        final WebElement closeElement = getElementBySubElement(getDriver(), closeBack,
                By.className(HELP_CLOSE), TINY_TIMEOUT);
        getAction().click(closeElement);
    }

    /**
     * Click next and close help.
     */
    private void clickNextAndCloseHelp() {

        setLogString(Marker.START, "Click Next and close the help Page", true);
        final WebElement next = getElement(getDriver(), By.className("nextButton"), TINY_TIMEOUT);
        if (next != null) {
            getAction().click(next);
            smallWait();
        }
        final WebElement next2 = getElement(getDriver(), By.className("nextButton"), TINY_TIMEOUT);
        if (next2 != null) {
            getAction().click(next2);
            smallWait();
        }
        final WebElement doneButton = getElement(getDriver(), By.className("doneButton"),
                TINY_TIMEOUT);
        if (doneButton != null) {
            getAction().click(doneButton);
            smallWait();
        }

        setLogString(Marker.END, "Closed Help", true);

    }

    /**
     * Click location switcher.
     * @see com.ecofactor.qa.automation.newapp.page.ThermostatPageOps#clickLocationSwitcher()
     */
    @Override
    public void clickLocationSwitcher() {

        setLogString("Click Location Switcher Icon", true, CustomLogLevel.HIGH);
        final WebElement tsatSwitcherElement = getElement(getDriver(),
                By.className(THERMOSTAT_SWITCHER_ICON), TINY_TIMEOUT);
        getAction().click(tsatSwitcherElement);
        getAction().rejectAlert();
    }

    /**
     * Click controls icon.
     * @see com.ecofactor.qa.automation.newapp.page.ThermostatPageOps#clickControlsIcon()
     */
    @Override
    public void clickControlsIcon() {

        setLogString("Click controls icon.", true, CustomLogLevel.HIGH);
        final WebElement controlsIcon = getElement(getDriver(), By.cssSelector("div.controlsIcon"),
                TINY_TIMEOUT);
        getAction().click(controlsIcon);
        getAction().rejectAlert();
    }

    /**
     * Swipe loc.
     * @param leftOrRight the left or right
     * @see com.ecofactor.qa.automation.newapp.page.ThermostatPageOps#swipeLoc(java.lang.String)
     */
    @Override
    public void swipeLoc(String leftOrRight) {

        setLogString("Swipe " + leftOrRight + " for a Location ", true, CustomLogLevel.LOW);
        final WebElement page = getElement(getDriver(), By.cssSelector("div.viewPage"),
                TINY_TIMEOUT);
        if (leftOrRight.equalsIgnoreCase("left")) {
            getAction().doSwipeLeft(page);
        } else {
            getAction().doSwipeRight(page);
        }
        tinyWait();
    }

    /**
     * click set Away icon.
     * @see com.ecofactor.qa.automation.newapp.page.ThermostatPageOps#clickSetAway()
     */
    @Override
    public void clickSetAway() {

        setLogString("Click Set Away", true, CustomLogLevel.LOW);
        final WebElement awayElement = getElementBySubElement(getDriver(),
                getCurrentThermostatContainer(), By.cssSelector(SET_AWAY), TINY_TIMEOUT);
        getAction().click(awayElement);
        getAction().rejectAlert();
    }

    /**
     * Click heat control icon.
     * @see com.ecofactor.qa.automation.newapp.page.ThermostatPageOps#clickHeatIcon()
     */
    @Override
    public void clickHeatIcon() {

        setLogString("Click Heat Icon", true, CustomLogLevel.LOW);
        final WebElement heatIcon = getElementBySubElement(getDriver(),
                getCurrentThermostatContainer(), By.cssSelector(HEAT_COOL_ICON), TINY_TIMEOUT);
        getAction().click(heatIcon);
        getAction().rejectAlert();

    }

    /**
     * click cool control icon.
     * @see com.ecofactor.qa.automation.newapp.page.ThermostatPageOps#clickCoolIcon()
     */
    @Override
    public void clickCoolIcon() {

        setLogString("Click Cool Icon", true, CustomLogLevel.LOW);
        final WebElement heatIcon = getElementBySubElement(getDriver(),
                getCurrentThermostatContainer(), By.cssSelector(HEAT_COOL_ICON), TINY_TIMEOUT);
        getAction().click(heatIcon);
        getAction().rejectAlert();
    }

    /**
     * click settings icon in menu page.
     * @see com.ecofactor.qa.automation.newapp.page.ThermostatPageOps#clickSettingsIcon()
     */
    @Override
    public void clickSettingsIcon() {

        setLogString("Click Settings Icon", true, CustomLogLevel.LOW);
        final WebElement settingsIcon = getElement(getDriver(), By.cssSelector(SETTINGS_ICON),
                TINY_TIMEOUT);
        WaitUtil.tinyWait();
        settingsIcon.click();
        // getAction().click(settingsIcon);
        getAction().rejectAlert();
        WaitUtil.oneSec();
    }

    /**
     * All kind of settings operation can perform here.
     * @see com.ecofactor.qa.automation.newapp.page.ThermostatPageOps#settingsOperations()
     */
    @Override
    public void settingsOperations() {

        setLogString("Click Thermostat name Field", true, CustomLogLevel.LOW);
        final WebElement thermostatField = getElement(getDriver(),
                By.cssSelector(THERMOSTAT_FIELD), TINY_TIMEOUT);
        getAction().click(thermostatField);
        WaitUtil.oneSec();
        getAction().rejectAlert();

        setLogString("Thermostat Field Name Changed.", true, CustomLogLevel.LOW);
        WaitUtil.oneSec();
        final WebElement fieldValue = getElement(getDriver(),
                By.cssSelector(THERMOSTAT_FIELD_VALUE), TINY_TIMEOUT);
        fieldValue.clear();
        WaitUtil.oneSec();
        fieldValue.sendKeys(THERMOSTAT_FIELD_NAME);
        WaitUtil.fourSec();

        setLogString("Updated Thermostat Field Name.", true, CustomLogLevel.LOW);
        final WebElement thermostatFieldName = getElement(getDriver(),
                By.cssSelector(INSTALLED_THERMOSTAT), TINY_TIMEOUT);
        getAction().click(thermostatFieldName);
        getAction().rejectAlert();

        tinyWait();
        clickSettingsMenu();
    }

    /**
     * Select thermostat icon in menu page.
     * @see com.ecofactor.qa.automation.newapp.page.ThermostatPageOps#clickThermostatIcon()
     */
    @Override
    public void clickThermostatIcon() {

        setLogString("Select Thermostat in Menu", true, CustomLogLevel.LOW);
        final WebElement thermostat = getElement(getDriver(), By.cssSelector(SELECT_THERMOSTAT),
                TINY_TIMEOUT);
        WaitUtil.oneSec();
        thermostat.click();
        // getAction().click(thermostat);
        WaitUtil.oneSec();
        getAction().rejectAlert();
    }

    /**
     * click menu button in settings page.
     * @see com.ecofactor.qa.automation.newapp.page.ThermostatPageOps#clickSettingsMenu()
     */
    @Override
    public void clickSettingsMenu() {

        setLogString("Click Menu in Thermostat", true, CustomLogLevel.LOW);
        final WebElement menuBtn = getElement(getDriver(), By.cssSelector(MENU_BTN), TINY_TIMEOUT);
        WaitUtil.oneSec();
        getAction().click(menuBtn);
        getAction().rejectAlert();
    }

    /**
     * click some other place in same popup.
     * @see com.ecofactor.qa.automation.newapp.page.ThermostatPageOps#clickotherplace()
     */
    @Override
    public void clickotherplace() {

        final WebElement element = getElement(getDriver(),
                By.cssSelector(".thermostat_button_label"), TINY_TIMEOUT);
        isClickable(getDriver(), element, MEDIUM_TIMEOUT);
        // setLogString("isClickable:" + yes, true);
        WaitUtil.tinyWait();

    }

    /**
     * close the savings icon.
     * @see com.ecofactor.qa.automation.newapp.page.ThermostatPageOps#isSavingsClickable()
     */
    @Override
    public void isSavingsClickable() {

        setLogString("Check either savings icon clickable", true, CustomLogLevel.LOW);
        final WebElement savingsElement = getElement(getDriver(), By.cssSelector(SAVINGS_ENERGY),
                TINY_TIMEOUT);
        isClickable(getDriver(), savingsElement, MEDIUM_TIMEOUT);
    }

    /**
     * close set Away icon .
     * @see com.ecofactor.qa.automation.newapp.page.ThermostatPageOps#closeSetAwayIcon()
     */
    @Override
    public void closeSetAwayIcon() {

        setLogString("close setAway Icon", true, CustomLogLevel.LOW);
        final WebElement setAwayIcon = getElement(getDriver(),
                By.cssSelector(".ctaButton.setAwayCancelButton"), MEDIUM_TIMEOUT);
        WaitUtil.oneSec();
        setAwayIcon.click();
        WaitUtil.tinyWait();
        getAction().rejectAlert();
    }
}
