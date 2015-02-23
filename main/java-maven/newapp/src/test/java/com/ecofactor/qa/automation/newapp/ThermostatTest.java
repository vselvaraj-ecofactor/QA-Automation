/*
 * ThermostatTest.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.newapp;

import static com.ecofactor.qa.automation.newapp.MobileConfig.*;
import static com.ecofactor.qa.automation.util.WaitUtil.*;

import org.joda.time.LocalTime;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Guice;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.ecofactor.qa.automation.dao.DaoModule;
import com.ecofactor.qa.automation.newapp.data.CommonsDataProvider;
import com.ecofactor.qa.automation.newapp.enums.ControlActions;
import com.ecofactor.qa.automation.newapp.page.MenuPage;
import com.ecofactor.qa.automation.newapp.page.SavingsPage;
import com.ecofactor.qa.automation.newapp.page.TstatControlOpsPage;
import com.ecofactor.qa.automation.newapp.page.TstatControlUIPage;
import com.ecofactor.qa.automation.newapp.page.validate.admin.TstatAdminValidation;
import com.ecofactor.qa.automation.newapp.page.validate.db.TstatDBValidation;
import com.ecofactor.qa.automation.platform.action.impl.DesktopUIAction;
import com.ecofactor.qa.automation.platform.annotation.TestPrerequisite;
import com.ecofactor.qa.automation.platform.constants.Groups;
import com.ecofactor.qa.automation.platform.enums.CustomLogLevel;
import com.ecofactor.qa.automation.platform.enums.LogSection;
import com.ecofactor.qa.automation.platform.exception.DeviceException;
import com.ecofactor.qa.automation.platform.listener.AutomationTransformer;
import com.ecofactor.qa.automation.platform.listener.RetryEnabledListener;
import com.ecofactor.qa.automation.platform.util.LogUtil;
import com.ecofactor.qa.automation.platform.util.SeleniumDriverUtil;
import com.google.inject.Inject;

/**
 * The Class ThermostatTest.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
@Guice(modules = { MobileModule.class, DaoModule.class })
@Listeners({ RetryEnabledListener.class, AutomationTransformer.class })
public class ThermostatTest extends AbstractTest {

    /** The Constant COOL. */
    private static final String COOL = "cool";

    /** The Constant HEAT. */
    private static final String HEAT = "heat";

    /** The Constant OFF. */
    private static final String OFF = "off";

    /** The Constant MAX_COOL. */
    private final static Integer MAX_COOL = 89;

    /** The Constant MAX_HEAT. */
    private final static Integer MAX_HEAT = 89;

    /** The Constant MIN_COOL. */
    private final static Integer MIN_COOL = 65;

    /** The Constant MIN_HEAT. */
    private final static Integer MIN_HEAT = 45;

    /** The Constant LEFT. */
    private static final String LEFT = "left";

    /** The Constant IDLE. */
    @SuppressWarnings("unused")
    private static final String IDLE = "idle";

    /** The Constant DECREASE. */
    private static final String DECREASE = "DECREASE";

    /** The Constant INCREASE. */
    private static final String INCREASE = "INCREASE";

    /** The Constant NEUTRAL. */
    private static final String NEUTRAL = "NEUTRAL";

    /** The th ctrl ui page. */
    @Inject
    private TstatControlUIPage thCtrlUIPage;

    /** The th ctrl ops page. */
    @Inject
    private TstatControlOpsPage thCtrlOpsPage;

    /** The menu page. */
    @Inject
    private MenuPage menuPage;

    /** The savings page. */
    @Inject
    private SavingsPage savingsPage;

    /** The th db validation. */
    @Inject
    private TstatDBValidation thDbValidation;

    /** The thermostat admin. */
    @Inject
    private TstatAdminValidation thermostatAdmin;

    /** The counter start time. */
    @SuppressWarnings("unused")
    private LocalTime counterStartTime;

    /** The counter end time. */
    @SuppressWarnings("unused")
    private LocalTime counterEndTime;

    /** The duration. */
    @SuppressWarnings("unused")
    private long duration;

    /**
     * APP-88 Verify thermostat offline.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "tstatOfflineUser", dataProviderClass = CommonsDataProvider.class, priority = 1)
    public void thermostatOffline(final String userName, final String password) {

        loadPage(userName, password, true);

        thPageUI.getCurrentThermostatName();
        Assert.assertTrue(thPageUI.isThermostatOffline(), "Unable to verify offline background.");
    }

    /**
     * Row#230 Menu clickable if thermostat offline.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "tstatOfflineUser", dataProviderClass = CommonsDataProvider.class, priority = 2)
    public void menuClickableIfthermostatOffline(final String userName, final String password) {

        loadPage(userName, password, true);

        Assert.assertTrue(thPageUI.isThermostatOffline(), "Unable to verify offline background.");
        thPageOps.clickMenu();
        Assert.assertTrue(menuPage.isPageLoaded(),
                "Unable to click menu when thermostat is offline.");
        LogUtil.setLogString("Menu is clickable when thermostat is disconnected.", true,
                CustomLogLevel.MEDIUM);
        menuPage.clickThermostatMenuItem();
    }

    /**
     * Row#232 Close launch app when thermostat offline.
     * @param userName the user name
     * @param password the password
     * @throws DeviceException the device exception
     */
    @Test(groups = { Groups.SANITY1, Groups.ANDROID, Groups.IOS }, dataProvider = "tstatOfflineUser", dataProviderClass = CommonsDataProvider.class, priority = 3)
    public void closeLaunchAppWhenThermostatOffline(final String userName, final String password)
            throws DeviceException {

        loadPage(userName, password, true);

        Assert.assertTrue(thPageUI.isThermostatOffline(), "Unable to verify offline background.");
        LogUtil.setLogString("Close and launch app and check if it is still offline.", true,
                CustomLogLevel.MEDIUM);

        testOps.cleanup();
        testOps.switchToWebView();
        loginPage.setLoggedIn(false);
        loginPage.setLoggedInUser(null);
        loadPage(userName, password, true);
        Assert.assertTrue(thPageUI.isThermostatOffline(),
                "Unable to verify thermostat offline after close and launch app.");
        LogUtil.setLogString("Thermostat is still offline after close and launch app.", true,
                CustomLogLevel.MEDIUM);
    }

    /**
     * APP-97 Thermostats functional if one is offline.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "multipleOneOnOneOff", dataProviderClass = CommonsDataProvider.class, priority = 4)
    public void thermostatsFunctionalIfOneIsOffline(final String userName, final String password) {

        validateMultipleOneOnOneOff(userName, password, true);
    }

    /**
     * Row#234 Offline thermostat features.
     * @param userName the user name
     * @param password the password
     * @throws DeviceException the device exception
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "tstatOfflineUser", dataProviderClass = CommonsDataProvider.class, priority = 5)
    public void OfflineThermostatFeatures(final String userName, final String password)
            throws DeviceException {

        loadPage(userName, password, true);

        Assert.assertTrue(thPageUI.isThermostatOffline(), "Unable to verify offline background.");

        LogUtil.setLogString("Check if thermostat background is black.", true,
                CustomLogLevel.MEDIUM);
        Assert.assertTrue(thPageUI.isModeColorReflectedInScreen(OFF),
                "Off mode color is not reflected in screen background.");
        LogUtil.setLogString("Thermostat background is black.", true, CustomLogLevel.MEDIUM);

        Assert.assertFalse(thPageUI.isTargetTempDisplayed(),
                "Failed to verify target temperature is not displayed.");
        LogUtil.setLogString("Target temperature is not displayed.", true, CustomLogLevel.MEDIUM);

        LogUtil.setLogString("Check if current temperature is displayed.", true,
                CustomLogLevel.MEDIUM);
        Assert.assertFalse(thPageUI.isCurrentTempDisplayed(),
                "Failed to verify current temperature is not displayed.");
        LogUtil.setLogString("Current temperature is not displayed.", true, CustomLogLevel.MEDIUM);

        LogUtil.setLogString("Check if target dashed line is displayed.", true,
                CustomLogLevel.MEDIUM);
        Assert.assertFalse(thPageUI.isTargetDashedLineDisplayed(),
                "Failed to verify target dashed line/axis is not displayed.");
        LogUtil.setLogString("Target dashed line/axis is not displayed.", true,
                CustomLogLevel.MEDIUM);
    }

    /**
     * Row#238 Inside temperature displayed.
     * @param userName the user name
     * @param password the password
     * @throws DeviceException the device exception
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "defaultUser", dataProviderClass = CommonsDataProvider.class, priority = 6)
    public void insideTemperatureDisplayed(final String userName, final String password)
            throws DeviceException {

        loadPage(userName, password, true);
        checkIdleConditionsForThermostat();
        Assert.assertTrue(thPageUI.isInsideTempDisplayed(), "Inside temperature is not displayed.");
        LogUtil.setLogString("Inside temperature is displayed.", true, CustomLogLevel.MEDIUM);
    }

    /**
     * APP-89 Verify no action at thermostat offline.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "tstatOfflineUser", dataProviderClass = CommonsDataProvider.class, priority = 7)
    public void noActionAtThermostatOffline(final String userName, final String password) {

        loadPage(userName, password, true);
        Assert.assertTrue(thPageUI.isPageLoaded(), "Thermostat Page is not loaded");
        thPageUI.getCurrentThermostatName();
        Assert.assertTrue(thPageUI.isThermostatOffline(), "Unable to verify offline background.");
        Assert.assertFalse(thPageUI.isTargetClickable(),
                "Clicked Target Temperature when thermostat is in offline.");
    }

    /**
     * APP-90 Offline error message.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER }, dataProvider = "tstatOfflineUser", dataProviderClass = CommonsDataProvider.class, priority = 8)
    public void offlineErrorMessage(final String userName, final String password) {

        loadPage(userName, password, true);
        thPageUI.getCurrentThermostatName();
        Assert.assertTrue(thPageUI.isThermostatOffline(), "Unable to verify offline background.");
        thPageUI.isClickLearnMoreLinkRedirectsNewWindow();
    }

    /**
     * APP-91 Switch thermostats in one is offline.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "multipleOneOnOneOff", dataProviderClass = CommonsDataProvider.class, priority = 9)
    public void switchOtherThermostatsIfOneIsOffline(final String userName, final String password) {

        validateMultipleOneOnOneOff(userName, password, false);
    }

    /**
     * APP-101 Provisional thermostat ui accesible.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "provisionalStatus", dataProviderClass = CommonsDataProvider.class, priority = 10)
    public void provisionalThermostatUIAccesible(final String userName, final String password) {

        loadPage(userName, password, true);
        Assert.assertTrue(thPageUI.isThermostatNotInstalled(),
                "Thermostat not installed message was missing in the thermostat page.");
        thDbValidation.verifyProvisionState(userName);
    }

    /**
     * APP-103 Heat back ground.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "validLogin", dataProviderClass = CommonsDataProvider.class, priority = 11)
    public void heatBackGround(final String userName, final String password) {

        loadPage(userName, password, true);
        checkIdleConditionsForThermostat();
        thPageOps.openTstatController();
        Assert.assertTrue(thCtrlOpsPage.isPageLoaded(), "Thermostat control page is not loaded");
        thCtrlOpsPage.changeToHeat();

        final Integer currentTemp = Integer.parseInt(thCtrlUIPage.getCurrentTemperature());
        final Integer targetTemp = Integer.parseInt(thCtrlUIPage.getTargetTemperature());
        checkStateForHeatingOrCooling(ControlActions.HEAT, currentTemp, targetTemp);

        thCtrlOpsPage.closeThermostatControl();
        final String mode = thPageUI.getCurrentMode();
        if (mode.equalsIgnoreCase(HEAT)) {
            Assert.assertTrue(thPageUI.isBackgroundImageDisplayed(HEAT),
                    "Background is not gradient in heat mode.");
        } else if (mode.equalsIgnoreCase(COOL)) {
            Assert.assertTrue(thPageUI.isBackgroundImageDisplayed(COOL),
                    "Background is not gradient in cool mode.");
        }
    }

    /**
     * APP-102 Cool back ground.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "validLogin", dataProviderClass = CommonsDataProvider.class, priority = 12)
    public void coolBackGround(final String userName, final String password) {

        loadPage(userName, password, true);
        checkIdleConditionsForThermostat();
        thPageOps.openTstatController();
        Assert.assertTrue(thCtrlOpsPage.isPageLoaded(), "Thermostat control page is not loaded");
        thCtrlOpsPage.changeToCool();

        final Integer currentTemp = Integer.parseInt(thCtrlUIPage.getCurrentTemperature());
        final Integer targetTemp = Integer.parseInt(thCtrlUIPage.getTargetTemperature());
        checkStateForHeatingOrCooling(ControlActions.COOL, currentTemp, targetTemp);

        thCtrlOpsPage.closeThermostatControl();
        final String mode = thPageUI.getCurrentMode();
        if (mode.equalsIgnoreCase(HEAT)) {
            Assert.assertTrue(thPageUI.isBackgroundImageDisplayed(HEAT),
                    "Background is not gradient in heat mode.");
        } else if (mode.equalsIgnoreCase(COOL)) {
            Assert.assertTrue(thPageUI.isBackgroundImageDisplayed(COOL),
                    "Background is not gradient in cool mode.");
        }
    }

    /**
     * APP-105 Off back ground.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "defaultUser", dataProviderClass = CommonsDataProvider.class, priority = 13)
    public void offBackGround(final String userName, final String password) {

        loadPage(userName, password, true);
        checkIdleConditionsForThermostat();
        thPageOps.openTstatController();
        Assert.assertTrue(thCtrlOpsPage.isPageLoaded(), "Thermostat control page is not loaded");
        thCtrlOpsPage.changeToOff();
        Assert.assertFalse(thPageUI.isBackgroundImageDisplayed(OFF),
                "Off mode color is not reflected in screen background.");
    }

    /**
     * APP-99 Drag beyond boundaries for cool.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "defaultUser", dataProviderClass = CommonsDataProvider.class, enabled = false, priority = 14)
    public void dragBeyondBoundariesForCool(final String userName, final String password) {

        loadPage(userName, password, true);
        checkIdleConditionsForThermostat();
        preRequisite(COOL);

        Integer targetTemp = Integer.valueOf(thPageUI.getTargetTemperature());

        int iteration = 0;
        while (!targetTemp.equals(MAX_COOL) && iteration <= 3) {
            iteration += 1;
            thPageOps.targetTempChangeValueByDrag(-150);
            targetTemp = Integer.valueOf(thPageUI.getTargetTemperature());
        }

        Assert.assertTrue(targetTemp.equals(MAX_COOL));

        thPageOps.targetTempChangeValueByDrag(-10);
        Integer targetTempAtExtremes = Integer.valueOf(thPageUI.getTargetTemperature());

        Assert.assertFalse(targetTempAtExtremes.equals(MAX_COOL + 1),
                "Able to drag beyond MAX cool temperature.");

        iteration = 0;
        targetTemp = Integer.valueOf(thPageUI.getTargetTemperature());
        while (!targetTemp.equals(MIN_COOL) && iteration <= 3) {
            iteration += 1;
            thPageOps.targetTempChangeValueByDrag(200);
            targetTemp = Integer.valueOf(thPageUI.getTargetTemperature());
        }

        Assert.assertTrue(targetTemp.equals(MIN_COOL));
        thPageOps.targetTempChangeValueByDrag(10);
        targetTempAtExtremes = Integer.valueOf(thPageUI.getTargetTemperature());

        Assert.assertFalse(targetTempAtExtremes.equals(MIN_COOL - 1),
                "Able to drag beyond MIN cool temperature.");
    }

    /**
     * APP-100 Drag beyond boundaries for heat.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "defaultUser", dataProviderClass = CommonsDataProvider.class, enabled = false, priority = 15)
    public void dragBeyondBoundariesForHeat(final String userName, final String password) {

        loadPage(userName, password, true);
        checkIdleConditionsForThermostat();
        preRequisite(HEAT);

        int iteration = 0;
        Integer targetTemp = Integer.valueOf(thPageUI.getTargetTemperature());
        while (!targetTemp.equals(MAX_HEAT) && iteration <= 3) {
            iteration += 1;
            thPageOps.targetTempChangeValueByDrag(-150);
            targetTemp = Integer.valueOf(thPageUI.getTargetTemperature());
        }

        Assert.assertTrue(targetTemp.equals(MAX_HEAT));
        Integer targetTempAtExtremes = Integer.valueOf(thPageUI.getTargetTemperature());

        thPageOps.targetTempChangeValueByDrag(-10);
        Assert.assertFalse(targetTempAtExtremes.equals(MAX_HEAT + 1),
                "Able to drag beyond MAX cool temperature.");

        iteration = 0;
        targetTemp = Integer.valueOf(thPageUI.getTargetTemperature());
        while (!targetTemp.equals(MIN_HEAT) && iteration <= 3) {
            iteration += 1;
            thPageOps.targetTempChangeValueByDrag(200);
            targetTemp = Integer.valueOf(thPageUI.getTargetTemperature());
        }

        Assert.assertTrue(targetTemp.equals(MIN_HEAT));
        targetTempAtExtremes = Integer.valueOf(thPageUI.getTargetTemperature());

        thPageOps.targetTempChangeValueByDrag(10);
        Assert.assertFalse(targetTempAtExtremes.equals(MIN_HEAT - 1),
                "Able to drag beyond MIN cool temperature.");
    }

    /**
     * APP-121 Current temp in ui and db.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "validLogin", dataProviderClass = CommonsDataProvider.class, priority = 16)
    public void currentTempInUIAndDB(final String userName, final String password) {

        loadPage(userName, password, true);
        checkIdleConditionsForThermostat();
        final String thermostatName = thPageUI.getCurrentThermostatName();
        preRequisite(COOL);
        String currentTemp = thPageUI.getCurrentTemperature();
        thDbValidation.verifyCurrentTemp(userName, thermostatName, thPageUI.getCurrentMode(),
                currentTemp);
        thermostatAdmin.verifyCurrentTemperature(userName, thermostatName, currentTemp);

        preRequisite(HEAT);
        currentTemp = thPageUI.getCurrentTemperature();
        thDbValidation.verifyCurrentTemp(userName, thermostatName, thPageUI.getCurrentMode(),
                currentTemp);
        thermostatAdmin.verifyCurrentTemperature(userName, thermostatName, currentTemp);
    }

    /**
     * APP-1 Verify current temperature displayed left.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "defaultUser", dataProviderClass = CommonsDataProvider.class, priority = 17)
    public void verifyCurrentTemperatureDisplayedLeft(final String userName, final String password) {

        loadPage(userName, password, true);
        checkIdleConditionsForThermostat();
        Assert.assertTrue("INSIDE".equalsIgnoreCase(thPageUI
                .getTemperatureContentByPosition("left")));
        thPageUI.getCurrentTemperature();
    }

    /**
     * APP-2 Verify target temperature displayed right.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "defaultUser", dataProviderClass = CommonsDataProvider.class, priority = 18)
    public void verifyTargetTemperatureDisplayedRight(final String userName, final String password) {

        loadPage(userName, password, true);
        checkIdleConditionsForThermostat();
        Assert.assertTrue("TARGET".equalsIgnoreCase(thPageUI
                .getTemperatureContentByPosition("right")));
        thPageUI.getTargetTemperature();
    }

    /**
     * APP-27 Verify heat mo.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "validLogin", dataProviderClass = CommonsDataProvider.class, priority = 19)
    public void verifyHeatMo(final String userName, final String password) {

        loadPage(userName, password, true);
        checkIdleConditionsForThermostat();
        final String thermostatName = thPageUI.getCurrentThermostatName();
        LogUtil.setLogString("Current Thermostat Name :" + thermostatName, true);
        thPageOps.openTstatController();
        Assert.assertTrue(thCtrlOpsPage.isPageLoaded(), "Thermostat control page is not loaded");

        thCtrlOpsPage.changeToHeat();
        final String currentTemp = thCtrlUIPage.getCurrentTemperature();
        thDbValidation.verifyCurrentTemp(userName, thermostatName, HEAT, currentTemp);
      //  thermostatAdmin.verifyCurrentTemperature(userName, thermostatName, currentTemp);

      //  thCtrlOpsPage.checkAndUpdateBoundary(3);
        thCtrlOpsPage.setPointChange(1, Integer.valueOf(thCtrlUIPage.getTargetTemperature()) + 1);
        thCtrlOpsPage.closeThermostatControl();

        Assert.assertTrue(thPageUI.isPageLoaded(), "Thermostat page is not loaded");
        final String targetTemp = thPageUI.getTargetTemperature();
        thDbValidation.verifyTargetTemp(userName, thermostatName, HEAT, targetTemp);
       // thermostatAdmin.verifyTargetTemp(userName, thermostatName, HEAT, targetTemp);
    }

    /**
     * APP-32 Verify cool mo.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "defaultUser", dataProviderClass = CommonsDataProvider.class, priority = 20)
    public void verifyCoolMo(final String userName, final String password) {

        loadPage(userName, password, true);
        checkIdleConditionsForThermostat();
        LogUtil.setLogString("Logged in user: " + System.getProperty("userName"), true);
        final String thermostatName = thPageUI.getCurrentThermostatName();
        LogUtil.setLogString("Current Thermostat Name :" + thermostatName, true);
        thPageOps.openTstatController();
        Assert.assertTrue(thCtrlOpsPage.isPageLoaded(), "Thermostat control page is not loaded");

        thCtrlOpsPage.changeToCool();
        final String currentTemp = thCtrlUIPage.getCurrentTemperature();
        thDbValidation.verifyCurrentTemp(userName, thermostatName, COOL, currentTemp);
        thermostatAdmin.verifyCurrentTemperature(userName, thermostatName, currentTemp);

       // thCtrlOpsPage.checkAndUpdateBoundary(3);
        thCtrlOpsPage.setPointChange(1, Integer.valueOf(thCtrlUIPage.getTargetTemperature()) + 1);
        thCtrlOpsPage.closeThermostatControl();

        Assert.assertTrue(thPageUI.isPageLoaded(), "Thermostat page is not loaded");
        final String targetTemp = thPageUI.getTargetTemperature();
        thDbValidation.verifyTargetTemp(userName, thermostatName, COOL, targetTemp);
        thermostatAdmin.verifyTargetTemp(userName, thermostatName, COOL, targetTemp);
    }

    /**
     * APP-124 Tap on target temperature.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "defaultUser", dataProviderClass = CommonsDataProvider.class, priority = 21)
    public void tapOnTargetTemperature(final String userName, final String password) {

        loadPage(userName, password, true);
        checkIdleConditionsForThermostat();
        thPageOps.openTstatController();
        Assert.assertTrue(thCtrlOpsPage.isPageLoaded(), "Thermostat control page is not loaded");
    }

    /**
     * APP-115 Dashed line on central axis.
     * @param userName the user name
     * @param password the password APP-115
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "defaultUser", dataProviderClass = CommonsDataProvider.class, priority = 22)
    public void dashedLineOnCentralAxis(final String userName, final String password) {

        loadPage(userName, password, true);
        checkIdleConditionsForThermostat();
        Assert.assertEquals(thPageUI.isTargetDashedLineDisplayed(), true);
        Assert.assertEquals(thPageUI.isCurrentDashedLineDisplayed(), true);
    }

    /**
     * APP-130 Boundary values for cool.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "defaultUser", dataProviderClass = CommonsDataProvider.class, priority = 23, enabled = false)
    public void boundaryValuesForCool(final String userName, final String password) {

        loadPage(userName, password, true);
        checkIdleConditionsForThermostat();
        final String thermostatName = thPageUI.getCurrentThermostatName();
        preRequisite(COOL);

        thPageOps.openTstatController();
        Assert.assertTrue(thCtrlOpsPage.isPageLoaded(), "Thermostat control page is not loaded");

        LogUtil.setLogString("Change to Max target in cool.", true);
        int setPointChange = MAX_COOL - Integer.parseInt(thCtrlUIPage.getTargetTemperature());
        thCtrlOpsPage.setPointChange(setPointChange, MAX_COOL);
        Assert.assertTrue(MAX_COOL.equals(Integer.parseInt(thCtrlUIPage.getTargetTemperature())));

        thCtrlOpsPage.setPointChange(1);
        thCtrlOpsPage.closeThermostatControl();

        Assert.assertTrue(MAX_COOL.equals(Integer.parseInt(thPageUI.getTargetTemperature())));
        thDbValidation.verifyTargetTemp(userName, thermostatName, COOL,
                thPageUI.getTargetTemperature());
        thermostatAdmin.verifyTargetTemp(userName, thermostatName, COOL,
                thPageUI.getTargetTemperature());

        thPageOps.openTstatController();
        Assert.assertTrue(thCtrlOpsPage.isPageLoaded(), "Thermostat control page is not loaded");

        LogUtil.setLogString("Change to Min target in cool.", true);
        setPointChange = MIN_COOL - Integer.parseInt(thCtrlUIPage.getTargetTemperature());
        thCtrlOpsPage.setPointChange(setPointChange, MIN_COOL);
        Assert.assertTrue(MIN_COOL.equals(Integer.parseInt(thCtrlUIPage.getTargetTemperature())));

        thCtrlOpsPage.setPointChange(-1);
        thCtrlOpsPage.closeThermostatControl();

        Assert.assertTrue(MIN_COOL.equals(Integer.parseInt(thPageUI.getTargetTemperature())));
        thDbValidation.verifyTargetTemp(userName, thermostatName, COOL,
                thPageUI.getTargetTemperature());
        thermostatAdmin.verifyTargetTemp(userName, thermostatName, COOL,
                thPageUI.getTargetTemperature());
    }

    /**
     * APP-131 Boundary values for heat.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "defaultUser", dataProviderClass = CommonsDataProvider.class, priority = 24, enabled = false)
    public void boundaryValuesForHeat(final String userName, final String password) {

        loadPage(userName, password, true);
        checkIdleConditionsForThermostat();
        final String thermostatName = thPageUI.getCurrentThermostatName();
        preRequisite(HEAT);

        thPageOps.openTstatController();
        Assert.assertTrue(thCtrlOpsPage.isPageLoaded(), "Thermostat control page is not loaded");

        LogUtil.setLogString("Change to Max target in heat.", true);
        int setPointChange = MAX_HEAT - Integer.parseInt(thCtrlUIPage.getTargetTemperature());
        thCtrlOpsPage.setPointChange(setPointChange, MAX_HEAT);
        Assert.assertTrue(MAX_HEAT.equals(Integer.parseInt(thCtrlUIPage.getTargetTemperature())));

        thCtrlOpsPage.setPointChange(1);
        thCtrlOpsPage.closeThermostatControl();

        Assert.assertTrue(MAX_HEAT.equals(Integer.parseInt(thPageUI.getTargetTemperature())));
        thDbValidation.verifyTargetTemp(userName, thermostatName, HEAT,
                thPageUI.getTargetTemperature());
        thermostatAdmin.verifyTargetTemp(userName, thermostatName, HEAT,
                thPageUI.getTargetTemperature());

        thPageOps.openTstatController();
        Assert.assertTrue(thCtrlOpsPage.isPageLoaded(), "Thermostat control page is not loaded");

        LogUtil.setLogString("Change to Min target in heat.", true);
        setPointChange = MIN_HEAT - Integer.parseInt(thCtrlUIPage.getTargetTemperature());
        thCtrlOpsPage.setPointChange(setPointChange, MIN_HEAT);
        Assert.assertTrue(MIN_HEAT.equals(Integer.parseInt(thCtrlUIPage.getTargetTemperature())));

        thCtrlOpsPage.setPointChange(-1);
        thCtrlOpsPage.closeThermostatControl();

        Assert.assertTrue(MIN_HEAT.equals(Integer.parseInt(thPageUI.getTargetTemperature())));
        thDbValidation.verifyTargetTemp(userName, thermostatName, HEAT,
                thPageUI.getTargetTemperature());
        thermostatAdmin.verifyTargetTemp(userName, thermostatName, HEAT,
                thPageUI.getTargetTemperature());
    }

    /**
     * APP-104 Idle back ground.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS, Groups.STUB }, dataProvider = "defaultUser", dataProviderClass = CommonsDataProvider.class, priority = 25)
    public void idleBackGround(final String userName, final String password) {

        loadPage(userName, password, true);

        thPageOps.openTstatController();
        Assert.assertTrue(thCtrlOpsPage.isPageLoaded(), "Thermostat control page is not loaded");
        thCtrlOpsPage.setPointChange(1, Integer.parseInt(thCtrlUIPage.getCurrentTemperature()));
        String mode = thPageUI.getCurrentMode();
        Assert.assertTrue(thPageUI.isModeColorReflectedInScreen(mode),
                "Background color got changed");
    }

    /**
     * APP-120 Compare position current equals target.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "validLogin", dataProviderClass = CommonsDataProvider.class, priority = 26)
    public void comparePositionCurrentEqualsTarget(final String userName, final String password) {

        loadPage(userName, password, true);
        checkIdleConditionsForThermostat();
        String mode = thPageUI.getCurrentMode();

        int currentTemp = Integer.valueOf(thPageUI.getCurrentTemperature());
        int targetTemp = Integer.valueOf(thPageUI.getTargetTemperature());

        if (currentTemp != targetTemp) {
            LogUtil.setLogString("Current temperature != target temperature, equalise both.", true);
            changeTarget(currentTemp, targetTemp, NEUTRAL);
        }

        currentTemp = Integer.valueOf(thPageUI.getCurrentTemperature());
        targetTemp = Integer.valueOf(thPageUI.getTargetTemperature());

        LogUtil.setLogString("Check if target and current temperatures are in neutral position.",
                true);
        Assert.assertTrue(thPageUI.isTempPositionRelative(currentTemp, targetTemp));

        mode = (thPageUI.getCurrentMode().equalsIgnoreCase(COOL)) ? HEAT : COOL;
        preRequisite(mode);

        currentTemp = Integer.parseInt(thPageUI.getCurrentTemperature());
        targetTemp = Integer.parseInt(thPageUI.getTargetTemperature());

        if (currentTemp != targetTemp) {
            LogUtil.setLogString("Current temperature != target temperature, equalise both.", true);
            changeTarget(currentTemp, targetTemp, NEUTRAL);
        }

        currentTemp = Integer.parseInt(thPageUI.getCurrentTemperature());
        targetTemp = Integer.parseInt(thPageUI.getTargetTemperature());

        LogUtil.setLogString("Compare if target and current temperatures are in neutral position.",
                true);
        Assert.assertTrue(thPageUI.isTempPositionRelative(currentTemp, targetTemp));
    }

    /**
     * APP-25 Verify target change.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "validLogin", dataProviderClass = CommonsDataProvider.class, priority = 27)
    public void verifyUIChange(final String userName, final String password) {

        loadPage(userName, password, true);
        checkIdleConditionsForThermostat();
        verifyTargetChange();
        thCtrlOpsPage.changeToCool();
        thCtrlOpsPage.changeToHeat();
        thCtrlOpsPage.changeToOff();
        thPageOps.turnSystemOn();
        Assert.assertTrue(thPageUI.isTargetTempDisplayed(),
                "Target temperature is not displayed in thermostat page");
    }

    /**
     * APP-118 Compare position current high target low.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "defaultUser", dataProviderClass = CommonsDataProvider.class, priority = 28)
    public void comparePositionCurrentHighTargetLow(final String userName, final String password) {

        loadPage(userName, password, true);
        checkIdleConditionsForThermostat();
        String mode = thPageUI.getCurrentMode();

        int currentTemp = Integer.valueOf(thPageUI.getCurrentTemperature());
        int targetTemp = Integer.valueOf(thPageUI.getTargetTemperature());

        if (currentTemp < targetTemp) {
            LogUtil.setLogString(
                    "Current temperature < target temperature, so decrease target temperature and verify the position.",
                    true);
            changeTarget(currentTemp, targetTemp, DECREASE);
        }

        currentTemp = Integer.parseInt(thPageUI.getCurrentTemperature());
        targetTemp = Integer.parseInt(thPageUI.getTargetTemperature());

        LogUtil.setLogString(
                "Check if position of current temperature is in top and target temperature is in bottom.",
                true);
        Assert.assertTrue(thPageUI.isTempPositionRelative(currentTemp,
                Integer.parseInt(thPageUI.getTargetTemperature())));

        mode = (thPageUI.getCurrentMode().equalsIgnoreCase(COOL)) ? HEAT : COOL;
        preRequisite(mode);

        currentTemp = Integer.parseInt(thPageUI.getCurrentTemperature());
        targetTemp = Integer.parseInt(thPageUI.getTargetTemperature());

        if (currentTemp < targetTemp) {
            LogUtil.setLogString(
                    "Current temperature < target temperature, so decrease target temperature and verify the position.",
                    true);
            changeTarget(currentTemp, targetTemp, DECREASE);
        }

        currentTemp = Integer.parseInt(thPageUI.getCurrentTemperature());
        targetTemp = Integer.parseInt(thPageUI.getTargetTemperature());

        LogUtil.setLogString(
                "Check if position of current temperature is in top and target temperature is in bottom.",
                true);
        Assert.assertTrue(thPageUI.isTempPositionRelative(currentTemp, targetTemp));
    }

    /**
     * APP-119 Compare position current low target high.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "defaultUser", dataProviderClass = CommonsDataProvider.class, priority = 29)
    public void comparePositionCurrentLowTargetHigh(final String userName, final String password) {

        loadPage(userName, password, true);
        checkIdleConditionsForThermostat();
        String mode = thPageUI.getCurrentMode();

        int currentTemp = Integer.valueOf(thPageUI.getCurrentTemperature());
        int targetTemp = Integer.valueOf(thPageUI.getTargetTemperature());

        if (currentTemp > targetTemp) {
            LogUtil.setLogString(
                    "Current temperature > target temperature, so increase target temperature and verify the position.",
                    true);
            changeTarget(currentTemp, targetTemp, INCREASE);
        }

        currentTemp = Integer.valueOf(thPageUI.getCurrentTemperature());
        targetTemp = Integer.valueOf(thPageUI.getTargetTemperature());

        LogUtil.setLogString(
                "Check if position of current temperature is in bottom and target temperature is in top.",
                true);
        Assert.assertTrue(thPageUI.isTempPositionRelative(currentTemp, targetTemp));

        mode = (thPageUI.getCurrentMode().equalsIgnoreCase(COOL)) ? HEAT : COOL;
        preRequisite(mode);

        currentTemp = Integer.valueOf(thPageUI.getCurrentTemperature());
        targetTemp = Integer.valueOf(thPageUI.getTargetTemperature());

        if (currentTemp > targetTemp) {
            LogUtil.setLogString(
                    "Current temperature > target temperature, so increase target temperature and verify the position.",
                    true);
            changeTarget(currentTemp, targetTemp, INCREASE);
        }

        currentTemp = Integer.valueOf(thPageUI.getCurrentTemperature());
        targetTemp = Integer.valueOf(thPageUI.getTargetTemperature());

        LogUtil.setLogString(
                "Check if position of current temperature is in bottom and target temperature is in top.",
                true);
        Assert.assertTrue(thPageUI.isTempPositionRelative(currentTemp, targetTemp));
    }

    /**
     * APP-274 Click Heat icon.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS, Groups.STUB }, dataProvider = "defaultUser", dataProviderClass = CommonsDataProvider.class, priority = 30)
    public void heatIcon(final String userName, final String password) {

        loadPage(userName, password, true);
        thPageOps.clickControlsIcon();
        String mode = thCtrlUIPage.getCurrentMode();
        thCtrlOpsPage.closeThermostatControl();
        if (mode.equalsIgnoreCase(HEAT)) {

            thPageOps.clickHeatIcon();
        } else {

            thPageOps.clickControlsIcon();
            thCtrlOpsPage.changeToHeat();
            thCtrlOpsPage.closeThermostatControl();
            thPageOps.clickHeatIcon();
        }
    }

    /**
     * APP-273 Click Cool icon.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS, Groups.STUB }, dataProvider = "defaultUser", dataProviderClass = CommonsDataProvider.class, priority = 31)
    public void coolIcon(final String userName, final String password) {

        loadPage(userName, password, true);
        String mode = thCtrlUIPage.getCurrentMode();
        thCtrlOpsPage.closeThermostatControl();
        if (mode.equalsIgnoreCase(COOL)) {

            thPageOps.clickCoolIcon();
        } else {

            thPageOps.clickControlsIcon();
            thCtrlOpsPage.changeToCool();
            thCtrlOpsPage.closeThermostatControl();
            thPageOps.clickCoolIcon();
        }
    }

    /**
     * APP-275 Check thermostat Header.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS, Groups.STUB }, dataProvider = "defaultUser", dataProviderClass = CommonsDataProvider.class, priority = 32)
    public void thermostatHeader(final String userName, final String password) {

        loadPage(userName, password, true);
        Assert.assertTrue(thPageUI.isThermostatHeaderDisplayed(), "Thermostat Header not displayed");
    }

    /**
     * APP-270 Check for outside temperature.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS, Groups.STUB }, dataProvider = "defaultUser", dataProviderClass = CommonsDataProvider.class, priority = 33)
    public void outSideTemperature(final String userName, final String password) {

        loadPage(userName, password, true);
        thPageUI.fetchOutsideTemperature();
    }

    /**
     * APP-276 Check for outside temperature.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS, Groups.STUB }, dataProvider = "defaultUser", dataProviderClass = CommonsDataProvider.class, priority = 34)
    public void menuPage(final String userName, final String password) {

        loadPage(userName, password, true);
        LogUtil.setLogString("Check for Menu page displayed.", true);
        thPageOps.clickMenu();
    }

    /**
     * APP-277 Check for outside temperature.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS, Groups.STUB }, dataProvider = "defaultUser", dataProviderClass = CommonsDataProvider.class, priority = 35)
    public void thermostatPage(final String userName, final String password) {

        loadPage(userName, password, true);
        goToThermostatPage();
    }

    /**
     * APP-271 Click setAway icon.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS, Groups.STUB }, dataProvider = "defaultUser", dataProviderClass = CommonsDataProvider.class, priority = 36)
    public void setAwayIcon(final String userName, final String password) {

        loadPage(userName, password, true);
        thPageOps.clickSetAway();
    }

    /**
     * APP-272 Click savings icon.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS, Groups.STUB }, dataProvider = "defaultUser", dataProviderClass = CommonsDataProvider.class, priority = 37)
    public void savingsIcon(final String userName, final String password) {

        loadPage(userName, password, true);
        thPageOps.isSavingsClickable();
    }

    /**
     * APP-116 Drang and drop dashes.
     * @param userName the user name
     * @param password the password APP-116
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER }, dataProvider = "defaultUser", dataProviderClass = CommonsDataProvider.class, priority = 38)
    public void dragAndDropDashes(final String userName, final String password) {

        loadPage(userName, password, true);
        checkIdleConditionsForThermostat();
        thPageUI.getToastErrorMessage();
        final Integer target = Integer.valueOf(thPageUI.getTargetTemperature());
        thPageOps.dragTargetTo(target, 85);
        LogUtil.setLogString("Check Current temperature is not displayed", true);
        Assert.assertEquals(thPageUI.isCurrentDashedLineDisplayed(), true);
        thPageOps.dropTarget();
        Assert.assertEquals(thPageUI.isLongDashesNotDisplayed(), false);
        final Integer newtarget = Integer.valueOf(thPageUI.getTargetTemperature());
        Assert.assertNotEquals(target, newtarget, "Target temperature remains in same state");
    }

    /**
     * APP-123 Drag target temp horizontally.
     * @param userName the user name
     * @param password the password APP-123
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, }, dataProvider = "defaultUser", dataProviderClass = CommonsDataProvider.class, priority = 39)
    public void dragTargetTempHorizontally(final String userName, final String password) {

        loadPage(userName, password, true);
        checkIdleConditionsForThermostat();
        final Integer targetTemp = Integer.valueOf(thPageUI.getTargetTemperature());
        thPageOps.dragTargetHorizontal();
        final Integer newtarget = Integer.valueOf(thPageUI.getTargetTemperature());
        Assert.assertEquals(targetTemp, newtarget, "Target temperature not remains in same state");
    }

    /**
     * APP-125 Drag up heat mode.
     * @param userName the user name
     * @param password the password APP-125
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER }, dataProvider = "defaultUser", dataProviderClass = CommonsDataProvider.class, priority = 40)
    public void dragUpHeatMode(final String userName, final String password) {

        loadPage(userName, password, true);
        checkIdleConditionsForThermostat();
        final String thermostatName = thPageUI.getCurrentThermostatName();
        thPageOps.openTstatController();
        Assert.assertTrue(thCtrlOpsPage.isPageLoaded(), "Thermostat control page is not loaded");
        thCtrlOpsPage.changeToHeat();
        thCtrlOpsPage.checkAndUpdateBoundary(5);
        thCtrlOpsPage.closeThermostatControl();
        final Integer target = Integer.valueOf(thPageUI.getTargetTemperature());
        thPageOps.dragAndDropTargetTo(target, target + 3);
        final Integer newtarget = Integer.valueOf(thPageUI.getTargetTemperature());
        Assert.assertTrue(newtarget > target, "Target temperature remains in same state");
        thDbValidation.verifyTargetTemp(userName, thermostatName, HEAT, String.valueOf(target + 3));
        thermostatAdmin
                .verifyTargetTemp(userName, thermostatName, HEAT, String.valueOf(target + 3));
    }

    /**
     * APP-126 Drag up cool mode.
     * @param userName the user name
     * @param password the password APP-126
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER }, dataProvider = "defaultUser", dataProviderClass = CommonsDataProvider.class, priority = 41)
    public void dragUpCoolMode(final String userName, final String password) {

        loadPage(userName, password, true);
        checkIdleConditionsForThermostat();
        final String thermostatName = thPageUI.getCurrentThermostatName();
        thPageOps.openTstatController();
        Assert.assertTrue(thCtrlOpsPage.isPageLoaded(), "Thermostat control page is not loaded");
        thCtrlOpsPage.changeToCool();
        thCtrlOpsPage.checkAndUpdateBoundary(5);
        thCtrlOpsPage.closeThermostatControl();
        final Integer target = Integer.valueOf(thPageUI.getTargetTemperature());
        thPageOps.dragAndDropTargetTo(target, target + 3);
        final Integer newtarget = Integer.valueOf(thPageUI.getTargetTemperature());
        Assert.assertTrue(newtarget > target, "Target temperature remains in same state");
        thDbValidation.verifyTargetTemp(userName, thermostatName, COOL, String.valueOf(target + 3));
        thermostatAdmin
                .verifyTargetTemp(userName, thermostatName, COOL, String.valueOf(target + 3));
    }

    /**
     * APP-127 Drag down heat mode.
     * @param userName the user name
     * @param password the password APP-127
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER }, dataProvider = "defaultUser", dataProviderClass = CommonsDataProvider.class, priority = 42)
    public void dragDownHeatMode(final String userName, final String password) {

        loadPage(userName, password, true);
        checkIdleConditionsForThermostat();
        final String thermostatName = thPageUI.getCurrentThermostatName();
        thPageOps.openTstatController();
        Assert.assertTrue(thCtrlOpsPage.isPageLoaded(), "Thermostat control page is not loaded");
        thCtrlOpsPage.changeToHeat();
        thCtrlOpsPage.checkAndUpdateBoundary(5);
        thCtrlOpsPage.closeThermostatControl();
        final Integer target = Integer.valueOf(thPageUI.getTargetTemperature());
        thPageOps.dragAndDropTargetTo(target, target - 3);
        final Integer newtarget = Integer.valueOf(thPageUI.getTargetTemperature());
        Assert.assertTrue(newtarget < target, "Target temperature remains in same state");
        thDbValidation.verifyTargetTemp(userName, thermostatName, HEAT, String.valueOf(target - 3));
        thermostatAdmin
                .verifyTargetTemp(userName, thermostatName, HEAT, String.valueOf(target - 3));
    }

    /**
     * APP-128 Drag down cool mode.
     * @param userName the user name
     * @param password the password APP-128
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER }, dataProvider = "defaultUser", dataProviderClass = CommonsDataProvider.class, priority = 43)
    public void dragDownCoolMode(final String userName, final String password) {

        loadPage(userName, password, true);
        checkIdleConditionsForThermostat();
        final String thermostatName = thPageUI.getCurrentThermostatName();
        thPageOps.openTstatController();
        Assert.assertTrue(thCtrlOpsPage.isPageLoaded(), "Thermostat control page is not loaded");
        thCtrlOpsPage.changeToCool();
        thCtrlOpsPage.checkAndUpdateBoundary(5);
        thCtrlOpsPage.closeThermostatControl();
        final Integer target = Integer.valueOf(thPageUI.getTargetTemperature());
        thPageOps.dragAndDropTargetTo(target, target - 3);
        final Integer newtarget = Integer.valueOf(thPageUI.getTargetTemperature());
        Assert.assertTrue(newtarget < target, "Target temperature remains in same state");
        thDbValidation.verifyTargetTemp(userName, thermostatName, COOL, String.valueOf(target - 3));
        thermostatAdmin
                .verifyTargetTemp(userName, thermostatName, COOL, String.valueOf(target - 3));
    }

    /**
     * APP-129 Large font on drag.
     * @param userName the user name
     * @param password the password APP-129
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER }, dataProvider = "defaultUser", dataProviderClass = CommonsDataProvider.class, priority = 44)
    public void largeFontOnDrag(final String userName, final String password) {

        loadPage(userName, password, true);
        checkIdleConditionsForThermostat();
        thPageOps.openTstatController();
        Assert.assertTrue(thCtrlOpsPage.isPageLoaded(), "Thermostat control page is not loaded");
        thCtrlOpsPage.checkAndUpdateBoundary(5);
        thCtrlOpsPage.closeThermostatControl();
        final Integer target = Integer.valueOf(thPageUI.getTargetTemperature());
        thPageOps.dragTargetTo(target, target + 3);
        final String slidingTargetTemp = thPageUI.getSlidingTemperature();
        Assert.assertEquals("112px", slidingTargetTemp,
                "Sliding Target temperature Font changed from 112px");
        thPageOps.dropTarget();
    }

    /**
     * APP-85 Test Method for Current Temperature Slider Value change.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.IOS }, dataProvider = "defaultUser", dataProviderClass = CommonsDataProvider.class, priority = 45)
    public void currentTemperatureValueChangeByDrag(final String userName, final String password) {

        loadPage(userName, password, true);
        checkIdleConditionsForThermostat();
        final String beforeTemperature = thPageUI.getCurrentTemperature();
        thPageUI.currentTemperatureDrag(50);
        final String afterTemperature = thPageUI.getCurrentTemperature();
        Assert.assertTrue(beforeTemperature.equals(afterTemperature),
                "Current Temperature updated!");
    }

    /**
     * APP-86 Test Method for Target Temperature Slider Value change.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.IOS }, dataProvider = "defaultUser", dataProviderClass = CommonsDataProvider.class, priority = 46)
    public void targetTemperaturePositionChangeByDrag(final String userName, final String password) {

        loadPage(userName, password, true);
        checkIdleConditionsForThermostat();
        String beforeTemperature = thPageUI.getTargetTemperature();
        changeTargetTempByDrag(beforeTemperature);
        beforeTemperature = thPageUI.getTargetTemperature();
        thPageOps.targetTempChangeValueByDrag(100);
        final String afterTemperature = thPageUI.getTargetTemperature();
        Assert.assertTrue(beforeTemperature.equals(afterTemperature),
                "Target Temperature not getting updated!");
    }

    /**
     * Max and min target cool.
     * @param userName the user name
     * @param password the password APP-99
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER }, dataProvider = "defaultUser", dataProviderClass = CommonsDataProvider.class, priority = 47)
    public void maxAndMinTargetCool(final String userName, final String password) {

        loadPage(userName, password, true);
        checkIdleConditionsForThermostat();
        thPageOps.openTstatController();
        Assert.assertTrue(thCtrlOpsPage.isPageLoaded(), "Thermostat control page is not loaded");
        thCtrlOpsPage.changeToCool();
        thCtrlOpsPage.closeThermostatControl();
        Integer target = Integer.valueOf(thPageUI.getTargetTemperature());
        thPageOps.dragAndDropTargetTo(target, 90);
        target = Integer.valueOf(thPageUI.getTargetTemperature());
        Assert.assertTrue(target == 89 || target < 89, "Target Value greater than 89");
        thPageOps.dragAndDropTargetTo(target, 65);
        target = Integer.valueOf(thPageUI.getTargetTemperature());
        Assert.assertTrue(target == 65 || target > 65, "Target Value lesser than 65");
    }

    /**
     * Max and min target heat.
     * @param userName the user name
     * @param password the password APP-100
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER }, dataProvider = "defaultUser", dataProviderClass = CommonsDataProvider.class, priority = 48)
    public void maxAndMinTargetHeat(final String userName, final String password) {

        loadPage(userName, password, true);
        checkIdleConditionsForThermostat();
        thPageOps.openTstatController();
        Assert.assertTrue(thCtrlOpsPage.isPageLoaded(), "Thermostat control page is not loaded");
        thCtrlOpsPage.changeToHeat();
        thCtrlOpsPage.closeThermostatControl();
        Integer target = Integer.valueOf(thPageUI.getTargetTemperature());
        thPageOps.dragAndDropTargetTo(target, 90);
        target = Integer.valueOf(thPageUI.getTargetTemperature());
        Assert.assertTrue(target == 89 || target < 89, "Target Value greater than 89");
        thPageOps.dragAndDropTargetTo(target, 45);
        target = Integer.valueOf(thPageUI.getTargetTemperature());
        Assert.assertTrue(target == 45 || target > 45, "Target Value lesser than 45");
    }

    /**
     * Click savings link multiple times.
     * @param userName the user name
     * @param password the password
     */
    // @Test(groups = { Groups.SANITY1, Groups.ANDROID, Groups.IOS }, enabled = false, dataProvider
    // = "defaultUser", dataProviderClass = CommonsDataProvider.class, priority = 49)
    public void clickSavingsLinkMultipleTimes(final String userName, final String password) {

        loadPage(userName, password, true);
        checkIdleConditionsForThermostat();
        LogUtil.setLogString(
                "Click Savings icon 5 times and check no popups is opned while click on the same link",
                true);
        for (int i = 0; i < 5; i++) {
            thPageOps.clickSavings();
            Assert.assertTrue(savingsPage.isPageLoaded(),
                    "Popup is not opened after click on the Savings link");
            thPageOps.clickSavings();
            Assert.assertFalse(savingsPage.isPageLoaded(),
                    "Popup is not closed after click on the Savings link twice");
        }

        Assert.assertFalse(savingsPage.isPageLoaded(),
                "Popup is not closed after click on the Savings link twice");
    }

    /**
     * APP-134 Cool set point changes in device and desktop.
     * @param userName the user name
     * @param password the password
     */
    // @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider =
    // "defaultUser", dataProviderClass = CommonsDataProvider.class, priority = 50)
    public void coolSetPointChangesInDeviceAndDesktop(final String userName, final String password) {

        setPointChangesinDB(userName, password, COOL);
    }

    /**
     * APP-138 Heat set point changes in device and desktop.
     * @param userName the user name
     * @param password the password
     */
    // @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider =
    // "defaultUser", dataProviderClass = CommonsDataProvider.class, priority = 51)
    public void heatSetPointChangesInDeviceAndDesktop(final String userName, final String password) {

        setPointChangesinDB(userName, password, HEAT);
        // verifySetPointChangesInDeviceAndDesktop(userName, password, HEAT);
    }

    /**
     * APP-26 Verify celcius temp.
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS, Groups.STUB }, priority = 52)
    public void verifyCelcius() {

        LogUtil.setLogString("Skipping - Dependancy on APPS-6416!", true);
        throw new SkipException("Skipping - Dependancy on APPS-6416!");
    }

    /**
     * APP-23 Verify thermostat page load time.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS, Groups.STUB }, dataProvider = "defaultUser", dataProviderClass = CommonsDataProvider.class, priority = 52)
    public void verifyThermostatpageLoadTime(final String userName, final String password) {

        LogUtil.setLogString("Skipping - Dependency on APPS-6925", true);
        throw new SkipException("Skipping - Dependency on APPS-6925");
    }

    /**
     * APP-24 Verify temperature content load time.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS, Groups.STUB }, dataProvider = "defaultUser", dataProviderClass = CommonsDataProvider.class, priority = 53)
    public void verifyTemperatureContentLoadTime(final String userName, final String password) {

        LogUtil.setLogString("Skipping - Dependency on APPS-6925", true);
        throw new SkipException("Skipping - Dependency on APPS-6925");
    }

    /**
     * Creates the driver.
     * @return the web driver
     */
    private WebDriver createDriver() {

        LogUtil.setLogString("Create New webdriver", true);
        LogUtil.setLogString("Browser : " + System.getProperty("browser"), true);
        final WebDriver driver = SeleniumDriverUtil.createBrowserDriver(System
                .getProperty("browser"));
        driver.manage().window().maximize();
        final String url = mobileConfig.get(ECOFACTOR_URL);
        LogUtil.setLogString("Load Url: " + url, true);
        driver.navigate().to(url);
        smallWait();
        return driver;
    }

    /**
     * Refresh page.
     * @param driver the driver
     */
    @SuppressWarnings("unused")
    private void refreshPage(final WebDriver driver) {

        LogUtil.setLogString("Refresh page", true);
        final JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("return window.location.reload();");
        mediumWait();
    }

    /**
     * Check state for heating or cooling. If you need to set cooling, set mode to cool and pass
     * cool as param. If you need to set heating, set mode to heat and pass heat as param.
     * @param cntrlActions the cntrl actions
     * @param currentTemp the current temp
     * @param targetTemp the target temp
     */
    private void checkStateForHeatingOrCooling(final ControlActions cntrlActions,
            final int currentTemp, final int targetTemp) {

        int changeValue = 0;
        if (cntrlActions.equals(ControlActions.COOL)) {
            if (currentTemp < targetTemp) {
                changeValue = targetTemp - currentTemp;
                changeValue -= 1;
                thCtrlOpsPage.setPointChange(-changeValue, currentTemp - 1);
            }
        } else {
            if (targetTemp < currentTemp) {
                changeValue = currentTemp - targetTemp;
                changeValue += 1;
                thCtrlOpsPage.setPointChange(changeValue, currentTemp + 1);
            }
        }
    }

    /**
     * Change target temp by drag.
     * @param beforeTemperature the before temperature
     */
    private void changeTargetTempByDrag(final String beforeTemperature) {

        if (Integer.parseInt(beforeTemperature) == MAX_COOL
                || Integer.parseInt(beforeTemperature) == MAX_HEAT) {
            thPageOps.targetTempChangeValueByDrag(50);
        } else if (Integer.parseInt(beforeTemperature) == MIN_COOL
                || Integer.parseInt(beforeTemperature) == MIN_HEAT) {
            thPageOps.targetTempChangeValueByDrag(-50);
        }
    }

    /**
     * Verify target change.
     */
    private void verifyTargetChange() {

        String currrentTarget = thPageUI.getTargetTemperature();
        LogUtil.setLogString("Current target temperature : " + currrentTarget, true);
        thPageOps.openTstatController();
        Assert.assertTrue(thCtrlOpsPage.isPageLoaded(), "Thermostat control page is not loaded");
       // thCtrlOpsPage.checkAndUpdateBoundary(4);
        currrentTarget = thCtrlUIPage.getTargetTemperature();
        thCtrlOpsPage.setPointChange(1, Integer.valueOf(currrentTarget) + 1);
        Assert.assertFalse(currrentTarget.equalsIgnoreCase(thCtrlUIPage.getTargetTemperature()));
        thCtrlOpsPage.setPointChange(-1, Integer.valueOf(currrentTarget));
        Assert.assertTrue(currrentTarget.equalsIgnoreCase(thCtrlUIPage.getTargetTemperature()));
    }

    /**
     * Pre requisite.
     * @param mode the mode
     */
    private void preRequisite(final String mode) {

        thPageOps.openTstatController();
        Assert.assertTrue(thCtrlOpsPage.isPageLoaded(), "Thermostat control page is not loaded");

        if (!thPageUI.getCurrentMode().equalsIgnoreCase(mode)) {

            if (mode.contains(COOL)) {
                thCtrlOpsPage.changeToCool();
            } else if (mode.contains(HEAT)) {
                thCtrlOpsPage.changeToHeat();
            } else {
                thCtrlOpsPage.changeToOff();
            }
        }

        LogUtil.setLogString("Check boundary conditions.", true);
        thCtrlOpsPage.checkAndUpdateBoundary(4);
        thCtrlOpsPage.closeThermostatControl();
    }

    /**
     * Check idle conditions for thermostat.
     */
    @TestPrerequisite
    public void checkIdleConditionsForThermostat() {

        Assert.assertFalse(thPageUI.isThermostatOffline(),
                "Unable to do thermostat related UI changes since thermostat is offline.");
        if (thCtrlOpsPage.isPopUpOpened()) {
            thCtrlOpsPage.closeThermostatControl();
            Assert.assertTrue(thPageUI.isPageLoaded(), "Thermostat page is not loaded");
        }

        if (awayPage.isPageLoaded()) {
            LogUtil.setLogString("Wait 30 seconds and click Imback", true);
            largeWait();
            awayPage.clickAmBack();
        }

        thPageUI.isPageLoaded();

        if (thPageUI.isModeOff()) {
            thPageOps.turnSystemOn();
        }

        if (thPageUI.isIdleState()) {
            LogUtil.setLogString(
                    "Thermostat is in idle state.Increase target temperatrure to change the state.",
                    true);
            thPageOps.openTstatController();
            Assert.assertTrue(thCtrlOpsPage.isPageLoaded(), "Thermostat control page is not loaded");
            thCtrlOpsPage.setPointChange(1);
            thCtrlOpsPage.closeThermostatControl();
            Assert.assertTrue(thPageUI.isPageLoaded(), "Thermostat page is not loaded");
        }
    }

    /**
     * Validate multiple one on one off.
     * @param userName the user name
     * @param password the password
     * @param checkFunctionality the check functionality
     */
    private void validateMultipleOneOnOneOff(final String userName, final String password,
            final boolean checkFunctionality) {

        loadPage(userName, password, true);
        thPageUI.isPageLoaded();
        thPageOps.clickLocationSwitcher();
        Integer noOfThermostat = thPageUI.getNoOfThermostats();
        locPageOps.clickClose();

        Assert.assertTrue(noOfThermostat > 1,
                "Provide user accounts with multiple thermostats with any one disconnected.");
        boolean disconnectedThermostat = false;

        String[] thermostatName = new String[noOfThermostat];
        for (int i = 1; i <= noOfThermostat; i++) {
            smallWait();
            thermostatName[i - 1] = thPageUI.getCurrentThermostatName();
            LogUtil.setLogString(
                    "Thermostat " + i + ". Name : " + thPageUI.getCurrentThermostatName()
                            + "; isDisconnected: " + thPageUI.isThermostatOffline(), true);

            if (thPageUI.isThermostatOffline()) {
                disconnectedThermostat = true;
            } else {
                thPageUI.getCurrentThermostatName();
                if (checkFunctionality) {
                    checkThermostatsFunctionalities();
                }
            }
            thPageOps.swipe(LEFT);
        }

        Assert.assertTrue(disconnectedThermostat,
                "Provide any one thermostat with disconnected status.");
        Assert.assertTrue(!thermostatName[0].equalsIgnoreCase(thermostatName[1]),
                "Unable to swipe to remaining thermostats if one is offline.");
    }

    /**
     * Check thermostats functionalities.
     */
    private void checkThermostatsFunctionalities() {

        thPageOps.openTstatController();
        Assert.assertTrue(thCtrlOpsPage.isPageLoaded(), "Thermostat control page is not loaded");
        thCtrlOpsPage.changeToCool();
        thCtrlOpsPage.changeToHeat();
        thCtrlOpsPage.changeToOff();
        thPageOps.turnSystemOn();
    }

    /**
     * Verify set point changes in device and desktop.
     * @param userName the user name
     * @param password the password
     * @param mode the mode
     */
    @SuppressWarnings("unused")
    private void verifySetPointChangesInDeviceAndDesktop(final String userName,
            final String password, final String mode) {

        loadPage(userName, password, true);

        LogUtil.setLogString("Ensure " + mode + " mode.", true);
        preRequisite(mode);

        WebDriver driver = null;
        try {
            final String thermostatName = thPageUI.getCurrentThermostatName();
            loginPage.setLoggedIn(false);

            LogUtil.setLogString(LogSection.START, "New Browser verfication starts", true);
            driver = createDriver();
            loginPage.setDriver(driver);
            loginPage.setAction(new DesktopUIAction());
            thPageOps.setDriver(driver);
            thPageOps.setAction(new DesktopUIAction());
            thPageUI.setDriver(driver);
            thPageUI.setAction(new DesktopUIAction());
            helpPage.setDriver(driver);
            helpPage.setAction(new DesktopUIAction());
            loadPage(userName, password, true);
            loginPage.setDriver(null);

            thCtrlOpsPage.setDriver(driver);

            thPageOps.openTstatController();
            thCtrlOpsPage.isPageLoaded();

            LogUtil.setLogString("Make set point changes in new browser", true);

            thCtrlOpsPage.setAction(new DesktopUIAction());
            thCtrlOpsPage.setPointChange(1);
            thCtrlOpsPage.closeThermostatControl();

            Integer changedTarget = Integer.valueOf(thPageUI.getTargetTemperature());
            thPageOps.setDriver(null);
            thCtrlOpsPage.setDriver(null);
            thDbValidation.verifyTargetTemp(userName, thermostatName, thPageUI.getCurrentMode(),
                    thPageUI.getTargetTemperature());
            thermostatAdmin.verifyTargetTemp(userName, thermostatName, thPageUI.getCurrentMode(),
                    thPageUI.getTargetTemperature());
            LogUtil.setLogString(LogSection.END, "New Browser verification ends", true);
            thPageUI.setDriver(null);
            thCtrlOpsPage.setAction(null);
            thPageOps.setAction(null);
            thPageUI.setAction(null);
            loginPage.setAction(null);

            // refreshPage(thPageUI.getDriver());
            LogUtil.setLogString("Check set point change is reflected in mobile/browser.", true);
            Assert.assertTrue(thPageUI.isTargetTemperatureChanged(String.valueOf(changedTarget)),
                    "Target temperature differs");
            thPageOps.openTstatController();
            thCtrlOpsPage.isPageLoaded();
            LogUtil.setLogString("Change setpoint  in mobile/browser", true);
            thCtrlOpsPage.setPointChange(-1);
            thCtrlOpsPage.closeThermostatControl();
            thDbValidation.verifyTargetTemp(userName, thermostatName, thPageUI.getCurrentMode(),
                    thPageUI.getTargetTemperature());
            thermostatAdmin.verifyTargetTemp(userName, thermostatName, thPageUI.getCurrentMode(),
                    thPageUI.getTargetTemperature());

            changedTarget = Integer.valueOf(thPageUI.getTargetTemperature());

            LogUtil.setLogString(LogSection.START, "New Browser verfication starts", true);
            // refreshPage(driver);
            largeWait();
            thPageUI.setDriver(driver);
            LogUtil.setLogString("Check set point is changed in new browser", true);
            Assert.assertTrue(Integer.valueOf(thPageUI.getTargetTemperature())
                    .equals(changedTarget), "Target temperature differs");
            thPageUI.setDriver(null);
            LogUtil.setLogString(LogSection.END, "New Browser verfication ends", true);

        } finally {
            loginPage.setDriver(null);
            thPageOps.setDriver(null);
            thCtrlOpsPage.setAction(null);
            thCtrlOpsPage.setDriver(null);
            thPageUI.setDriver(null);
            thPageOps.setAction(null);
            thPageUI.setAction(null);
            loginPage.setAction(null);

            if (driver != null) {
                LogUtil.setLogString("Quit driver for new browser", true);
                driver.quit();
            }
        }
    }

    /**
     * Change target.
     * @param currentTemp the current temp
     * @param targetTemp the target temp
     * @param changeRequired the change required
     */
    private void changeTarget(final int currentTemp, final int targetTemp,
            final String changeRequired) {

        thPageOps.openTstatController();
        Assert.assertTrue(thCtrlOpsPage.isPageLoaded(), "Thermostat control page is not loaded");
        clickAnyWhere();

        if (changeRequired.equalsIgnoreCase("increase")) {

            LogUtil.setLogString("Increase target temperature.", true);
            thCtrlOpsPage.setPointChange(((currentTemp - targetTemp) + 1), currentTemp + 1);

        } else if (changeRequired.equalsIgnoreCase("decrease")) {

            LogUtil.setLogString("Decrease target temperature.", true);
            thCtrlOpsPage.setPointChange(-((targetTemp - currentTemp) + 1), currentTemp - 1);
        } else {
            LogUtil.setLogString("Make target and current temperatures equal.", true);

            if (currentTemp > targetTemp) {
                thCtrlOpsPage.setPointChange((currentTemp - targetTemp), currentTemp);
            } else if (targetTemp > currentTemp) {
                thCtrlOpsPage.setPointChange(-(targetTemp - currentTemp), currentTemp);
            }
        }
        thCtrlOpsPage.closeThermostatControl();
    }

    /**
     * Click any where.
     */
    private void clickAnyWhere() {

        // Assert.assertTrue(thCtrlOpsPage.isPageLoaded(), "Thermostat control page is not loaded");
        thPageOps.clickotherplace();
    }

    /**
     * Sets the point changesin db.
     * @param userName the user name
     * @param password the password
     * @param mode the mode
     */
    private void setPointChangesinDB(final String userName, final String password, final String mode) {

        loadPage(userName, password, true);
        LogUtil.setLogString("Ensure " + mode + " mode.", true);
        thPageOps.clickControlsIcon();
        thPageUI.getCurrentMode();
        if (!thPageUI.getCurrentMode().equalsIgnoreCase(mode)) {

            if (mode.contains(COOL)) {
                thCtrlOpsPage.changeToCool();
            } else if (mode.contains(HEAT)) {
                thCtrlOpsPage.changeToHeat();
            } else {
                thCtrlOpsPage.changeToOff();
            }
        }
        thCtrlOpsPage.closeThermostatControl();
        final String thermostatName = thPageUI.getCurrentThermostatName();
        String currentTemp = thPageUI.getCurrentTemperature();
        thDbValidation.verifyCurrentTemp(userName, thermostatName, thPageUI.getCurrentMode(),
                currentTemp);
        thermostatAdmin.verifyCurrentTemperature(userName, thermostatName, currentTemp);
    }

}
