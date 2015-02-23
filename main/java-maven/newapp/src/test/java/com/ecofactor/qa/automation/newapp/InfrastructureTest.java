/*
 * InfrastructureTest.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.newapp;

import static com.ecofactor.qa.automation.newapp.MobileConfig.*;
import static com.ecofactor.qa.automation.util.WaitUtil.*;

import java.util.Calendar;
import java.util.TreeMap;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Guice;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.ecofactor.qa.automation.dao.DaoModule;
import com.ecofactor.qa.automation.dao.util.DataUtil;
import com.ecofactor.qa.automation.newapp.data.CommonsDataProvider;
import com.ecofactor.qa.automation.newapp.enums.ControlActions;
import com.ecofactor.qa.automation.newapp.page.ThermostatPageUI;
import com.ecofactor.qa.automation.newapp.page.TstatControlOpsPage;
import com.ecofactor.qa.automation.newapp.page.TstatControlUIPage;
import com.ecofactor.qa.automation.newapp.page.validate.admin.TstatAdminValidation;
import com.ecofactor.qa.automation.newapp.page.validate.db.TstatDBValidation;
import com.ecofactor.qa.automation.platform.annotation.TestPrerequisite;
import com.ecofactor.qa.automation.platform.constants.Groups;
import com.ecofactor.qa.automation.platform.enums.LogSection;
import com.ecofactor.qa.automation.platform.listener.AutomationTransformer;
import com.ecofactor.qa.automation.platform.listener.RetryEnabledListener;
import com.ecofactor.qa.automation.platform.util.LogUtil;
import com.ecofactor.qa.automation.platform.util.SeleniumDriverUtil;
import com.ecofactor.qa.automation.util.DateUtil;
import com.google.inject.Inject;

/**
 * The Class InfrastructureTest.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
@Guice(modules = { MobileModule.class, DaoModule.class })
@Listeners({ RetryEnabledListener.class, AutomationTransformer.class })
public class InfrastructureTest extends AbstractTest {

    /** The Constant COOL. */
    private final static String COOL = "Cool";

    /** The Constant HEAT. */
    private final static String HEAT = "Heat";

    /** The Constant MAX_COOL. */
    private final static Integer MAX_COOL = 89;

    /** The Constant MAX_HEAT. */
    private final static Integer MAX_HEAT = 89;

    /** The th ctrl ui page. */
    @Inject
    private TstatControlUIPage thCtrlUIPage;

    /** The th ctrl ops page. */
    @Inject
    private TstatControlOpsPage thCtrlOpsPage;

    /** The th db validation. */
    @Inject
    private TstatDBValidation thDbValidation;

    /** The th page ui. */
    @Inject
    private ThermostatPageUI thPageUI;

    /** The thermostat admin. */
    @Inject
    private TstatAdminValidation thermostatAdmin;

    /**
     * APP-96 Sets the point change within a minute.
     * @param userName the user name
     * @param password the password
     * @throws Exception the exception
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "infrastructureDB", dataProviderClass = CommonsDataProvider.class, priority = 5)
    public void continuousSetPointSwitchVerificationInDB(final String userName,
            final String password, final int tstId) throws Exception {

        loadPage(userName, password, true);
        checkIdleConditionsForThermostat();
        thPageUI.getCurrentThermostatName();
        thPageOps.openTstatController();
        Assert.assertTrue(thCtrlOpsPage.isPageLoaded(), "Thermostat control page is not loaded");
        setCoolAndHeatTargetTemp();
        final Integer thermostatId = tstId;
        LogUtil.setLogString("thermostatId : " + thermostatId, true);

        // Set point Changes
        final String currentUTCTimeForSetPoint = DateUtil
                .getUTCCurrentTimeStamp(DateUtil.DATE_FMT_FULL);
        Calendar startTimeForSetPoint = DateUtil.parseToCalendar(currentUTCTimeForSetPoint,
                DateUtil.DATE_FMT_FULL);
        LogUtil.setLogString(
                "Start time : " + DateUtil.format(startTimeForSetPoint, DateUtil.DATE_FMT_FULL_TZ),
                true);

        final TreeMap<Integer, Integer> moActionMap = doSetpointIteration();

        LogUtil.setLogString(
                "Completed UI changes, Wait 5 minutes to verify the relevant changes in DB", true);
        waitUntil(FIVE_MINS);

        String utcStartTime = DateUtil.getUTCCurrentTimeStamp(DateUtil.DATE_FMT_FULL);
        Calendar utcEndTime = DateUtil.parseToCalendar(utcStartTime, DateUtil.DATE_FMT_FULL);
        LogUtil.setLogString(
                "End time : " + DateUtil.format(utcEndTime, DateUtil.DATE_FMT_FULL_TZ), true);

        thDbValidation.verifySetPointChangeActionsInRangeData(startTimeForSetPoint, utcEndTime,
                thermostatId, moActionMap);

        DataUtil.printRangeDataTableGrid(thermostatId, startTimeForSetPoint, utcEndTime);
    }

    /**
     * APP-68 Mode change within a minute.
     * @param userName the user name
     * @param password the password
     * @throws Exception the exception
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "infrastructureDB", dataProviderClass = CommonsDataProvider.class, priority = 6)
    public void continuousModeSwitchVerificationInDB(final String userName, final String password,
            final int tstId) throws Exception {

        loadPage(userName, password, true);
        checkIdleConditionsForThermostat();
        thPageUI.getCurrentThermostatName();
        thPageOps.openTstatController();
        Assert.assertTrue(thCtrlOpsPage.isPageLoaded(), "Thermostat control page is not loaded");
        setCoolAndHeatTargetTemp();

        final String currentUTCTime = DateUtil.getUTCCurrentTimeStamp(DateUtil.DATE_FMT_FULL);
        Calendar startTime = DateUtil.parseToCalendar(currentUTCTime, DateUtil.DATE_FMT_FULL);
        LogUtil.setLogString(
                "Start time : " + DateUtil.format(startTime, DateUtil.DATE_FMT_FULL_TZ), true);

        final TreeMap<Integer, ControlActions> modeActionMap = doModeIteration();

        final Integer thermostatId = tstId;
        LogUtil.setLogString("thermostatId : " + thermostatId, true);

        LogUtil.setLogString(
                "Completed UI changes, Wait 5 minutes to verify the relevant changes in DB", true);
        waitUntil(FIVE_MINS);

        String utcTime = DateUtil.getUTCCurrentTimeStamp(DateUtil.DATE_FMT_FULL);
        Calendar endTime = DateUtil.parseToCalendar(utcTime, DateUtil.DATE_FMT_FULL);
        LogUtil.setLogString("End time : " + DateUtil.format(endTime, DateUtil.DATE_FMT_FULL_TZ),
                true);

        thDbValidation
                .verifyModeActionsInRangeData(startTime, endTime, thermostatId, modeActionMap);
        DataUtil.printRangeDataTableGrid(thermostatId, startTime, endTime);

    }

    /**
     * APP-65 Hvac mode changes.
     * @param userName the user name
     * @param password the password
     * @throws Exception the exception
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "defaultUser", dataProviderClass = CommonsDataProvider.class, priority = 3)
    public void hvacModeChanges(final String userName, final String password) throws Exception {

        loadPage(userName, password, true);
        checkIdleConditionsForThermostat();
        thPageOps.openTstatController();
        thCtrlOpsPage.isPageLoaded();
        for (int i = 0; i < 5; i++) {
            thCtrlOpsPage.changeToHeat();
            thCtrlOpsPage.changeToCool();
        }
        thCtrlOpsPage.closeThermostatControl();
    }

    /**
     * APP-67 Mo change order for heat and cool.
     * @param userName the user name
     * @param password the password
     * @throws Exception the exception
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "defaultUser", dataProviderClass = CommonsDataProvider.class, priority = 4)
    public void moChangeOrderForHeatAndCool(final String userName, final String password)
            throws Exception {

        loadPage(userName, password, true);
        checkIdleConditionsForThermostat();
        thPageOps.openTstatController();
        thCtrlOpsPage.isPageLoaded();
        presetForIncreaseSetPoint(2);
        thCtrlOpsPage.setPointChange(1);
        final String coolSetting = thCtrlUIPage.getTargetTemperature();

        thCtrlOpsPage.changeToHeat();
        presetForIncreaseSetPoint(2);
        thCtrlOpsPage.setPointChange(1);
        final String heatSetting = thCtrlUIPage.getTargetTemperature();
        thCtrlOpsPage.closeThermostatControl();

        thDbValidation.verifyMOOrderBetweenModes(userName, thPageUI.getCurrentThermostatName(),
                coolSetting, heatSetting);
    }

    /**
     * APP-66 Sets the point and mode change for every second.
     * @param userName the user name
     * @param password the password
     * @throws Exception the exception
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "defaultUser", dataProviderClass = CommonsDataProvider.class, priority = 1)
    public void setPointAndModeChangeForEverySecond(final String userName, final String password)
            throws Exception {

        loadPage(userName, password, true);
        checkIdleConditionsForThermostat();
        final String thermostatName = thPageUI.getCurrentThermostatName();
        thPageOps.openTstatController();
        thCtrlOpsPage.isPageLoaded();
        thCtrlOpsPage.changeToCool();
        thCtrlOpsPage.checkAndUpdateBoundary(3);

        Calendar startTime = DateUtil.getUTCCalendar();
        Calendar endTime = (Calendar) startTime.clone();
        endTime.add(Calendar.SECOND, 15);

        Calendar currentTime = DateUtil.getUTCCalendar();
        ControlActions actionMade = ControlActions.COOL;
        LogUtil.setLogString(LogSection.START,
                "Core Start Time :" + DateUtil.format(startTime, DateUtil.DATE_FMT_FULL_TZ), true);
        LogUtil.setLogString(
                "Core Ending Time :" + DateUtil.format(endTime, DateUtil.DATE_FMT_FULL_TZ), true);
        ControlActions lastMode = ControlActions.COOL;
        int i = 0;
        do {
            LogUtil.setLogString(
                    "Current Time :" + DateUtil.format(currentTime, DateUtil.DATE_FMT_FULL_TZ),
                    true);
            if (startTime.after(currentTime)) {
                break;
            } else {
                LogUtil.setLogString("Do Changes", true);
                if (i % 2 == 0) {
                    actionMade = ControlActions.HEAT;
                    lastMode = ControlActions.HEAT;
                    thCtrlOpsPage.clickModeHeat();
                    oneSec();
                    actionMade = ControlActions.MO_INCREASE;
                    thCtrlOpsPage.setPointChange(1);
                } else {
                    lastMode = ControlActions.COOL;
                    actionMade = ControlActions.COOL;
                    thCtrlOpsPage.clickModeCool();
                    oneSec();
                    actionMade = ControlActions.MO_DECREASE;
                    thCtrlOpsPage.setPointChange(-1);
                }
            }
            i++;
            currentTime = DateUtil.getUTCCalendar();
        } while (currentTime.before(endTime));

        LogUtil.setLogString(
                "End Time :" + DateUtil.format(currentTime, DateUtil.DATE_FMT_FULL_TZ), true);
        LogUtil.setLogString("Last action made, " + actionMade.toString(), true);

        LogUtil.setLogString(LogSection.END,
                "Completed UI changes, Wait 3 minutes to verify the relevant changes in DB", true);
        threeMinutesWait();

        String targetTemp = "";
        if (actionMade == ControlActions.MO_DECREASE || actionMade == ControlActions.MO_INCREASE) {
            targetTemp = thCtrlUIPage.getTargetTemperature();
        }

        final Integer thermostatId = thDbValidation.getTstatIdForUser(userName, thermostatName);
        thDbValidation.verifyLastRecord(thermostatId, actionMade, targetTemp, lastMode);

    }

    /**
     * APP-76 Verify user changes in device desktop.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "defaultUser", dataProviderClass = CommonsDataProvider.class, priority = 2)
    public void verifyUserChangesInDeviceAndDesktop(final String userName, final String password) {

        loadPage(userName, password, true);
        checkIdleConditionsForThermostat();
        final String thermostatName = thPageUI.getCurrentThermostatName();
        thDbValidation.verifyMode(userName, thermostatName, COOL);
        thermostatAdmin.verifyMode(userName, thermostatName, COOL);
    }

    /**
     * Preset set point.
     * @param setPointchange the set pointchange
     * @throws Exception the exception
     */
    private void presetForIncreaseSetPoint(final Integer setPointchange) throws Exception {

        final Integer targetTemp = Integer.valueOf(thCtrlUIPage.getTargetTemperature());
        thCtrlOpsPage.checkAndUpdateBoundary(3);
        if (thCtrlUIPage.getCurrentMode().equalsIgnoreCase(COOL) && (targetTemp == MAX_COOL)) {
            thCtrlOpsPage.setPointChange(-setPointchange);
        } else if (thCtrlUIPage.getCurrentMode().equalsIgnoreCase(HEAT) && (targetTemp == MAX_HEAT)) {
            thCtrlOpsPage.setPointChange(-setPointchange);
        }
    }

    /**
     * Creates the driver.
     * @return the web driver
     */
    @SuppressWarnings("unused")
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
        final JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("return window.location.reload();");
        mediumWait();
    }

    /**
     * Verify idle state.
     * @throws Exception the exception
     */
    private void verifyIdleState() throws Exception {

        if (thPageUI.isIdleState()) {
            LogUtil.setLogString(
                    "Thermostat is in idle state.Increase target temperatrure to change the state.",
                    true);
            thPageOps.openTstatController();
            Assert.assertTrue(thCtrlOpsPage.isPageLoaded());
            thCtrlOpsPage.setPointChange(3);
            thCtrlOpsPage.closeThermostatControl();
        }
    }

    /**
     * Do setpoint iteration.
     * @return the tree map
     * @throws Exception the exception
     */
    private TreeMap<Integer, Integer> doSetpointIteration() throws Exception {

        final TreeMap<Integer, Integer> actionMap = new TreeMap<>();
        LogUtil.setLogString("Do setpoint Changes up and down 4 times", true);
        for (int i = 0; i < 4; i++) {
            final String currentUTCTime = DateUtil.getUTCCurrentTimeStamp(DateUtil.DATE_FMT_FULL);
            Calendar startTime = DateUtil.parseToCalendar(currentUTCTime, DateUtil.DATE_FMT_FULL);
            LogUtil.setLogString(
                    "Time Now: " + DateUtil.format(startTime, DateUtil.DATE_FMT_FULL_TZ), true);
            final String targetTemp = thCtrlUIPage.getTargetTemperature();
            if (i % 2 == 0) {
                thCtrlOpsPage.setPointChange(1);
                waitUntil(ONE_MIN);
                actionMap.put(i, Integer.valueOf(targetTemp) + 1);
            }
            if (i % 2 == 1) {
                thCtrlOpsPage.setPointChange(-1);
                waitUntil(ONE_MIN);
                actionMap.put(i, Integer.valueOf(targetTemp) - 1);
            }
        }
        return actionMap;
    }

    /**
     * Do mode iteration.
     * @return the tree map
     * @throws Exception the exception
     */
    private TreeMap<Integer, ControlActions> doModeIteration() throws Exception {

        final TreeMap<Integer, ControlActions> actionMap = new TreeMap<>();
        LogUtil.setLogString("Do Mode switches 4 times", true);
        for (int i = 0; i < 4; i++) {
            final String currentUTCTime = DateUtil.getUTCCurrentTimeStamp(DateUtil.DATE_FMT_FULL);
            Calendar startTime = DateUtil.parseToCalendar(currentUTCTime, DateUtil.DATE_FMT_FULL);
            LogUtil.setLogString(
                    "Time Now: " + DateUtil.format(startTime, DateUtil.DATE_FMT_FULL_TZ), true);

            if (i % 2 == 0) {
                thCtrlOpsPage.clickModeHeat();
                waitUntil(ONE_MIN);
                actionMap.put(i, ControlActions.HEAT);
            }
            if (i % 2 == 1) {
                thCtrlOpsPage.clickModeCool();
                waitUntil(ONE_MIN);
                actionMap.put(i, ControlActions.COOL);
            }
        }
        return actionMap;
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
            Assert.assertTrue(thCtrlOpsPage.isPageLoaded());
            thCtrlOpsPage.setPointChange(1);
            thCtrlOpsPage.closeThermostatControl();
        }

    }

    /**
     * Sets the cool and heat target temp.
     * @throws Exception the exception
     */
    private void setCoolAndHeatTargetTemp() throws Exception {

        thCtrlOpsPage.changeToHeat();
        verifyIdleState();
        // thCtrlOpsPage.checkAndUpdateBoundary(3);
        final Integer currentTemp = Integer.valueOf(thCtrlUIPage.getCurrentTemperature());
        final Integer targetTemp = Integer.valueOf(thCtrlUIPage.getTargetTemperature());

        if (currentTemp <= targetTemp) {
            final Integer setpointValue = currentTemp - targetTemp;
            thCtrlOpsPage.setPointChange(setpointValue - 2, currentTemp - 2);
            mediumWait();
        }

        thCtrlOpsPage.changeToCool();
        verifyIdleState();
        // thCtrlOpsPage.checkAndUpdateBoundary(3);
        final Integer coolTargetTemp = Integer.valueOf(thCtrlUIPage.getTargetTemperature());

        if (currentTemp >= coolTargetTemp) {
            final Integer setpointValue = currentTemp - coolTargetTemp;
            thCtrlOpsPage.setPointChange(setpointValue + 2, currentTemp + 2);
            mediumWait();
        }
    }
}
