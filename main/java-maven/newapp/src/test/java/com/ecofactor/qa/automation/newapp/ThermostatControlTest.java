/*
 * ThermostatControlTest.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.newapp;

import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Guice;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.ecofactor.qa.automation.dao.DaoModule;
import com.ecofactor.qa.automation.newapp.data.CommonsDataProvider;
import com.ecofactor.qa.automation.newapp.page.ThermostatPageUI;
import com.ecofactor.qa.automation.newapp.page.TstatControlOpsPage;
import com.ecofactor.qa.automation.newapp.page.TstatControlUIPage;
import com.ecofactor.qa.automation.newapp.page.validate.admin.TstatAdminValidation;
import com.ecofactor.qa.automation.newapp.page.validate.db.TstatDBValidation;
import com.ecofactor.qa.automation.platform.annotation.TestPrerequisite;
import com.ecofactor.qa.automation.platform.constants.Groups;
import com.ecofactor.qa.automation.platform.exception.DeviceException;
import com.ecofactor.qa.automation.platform.listener.AutomationTransformer;
import com.ecofactor.qa.automation.platform.listener.RetryEnabledListener;
import com.ecofactor.qa.automation.platform.util.LogUtil;
import com.ecofactor.qa.automation.util.WaitUtil;
import com.google.inject.Inject;

/**
 * The Class ThermostatControlTest.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
@Guice(modules = { MobileModule.class, DaoModule.class })
@Listeners({ RetryEnabledListener.class, AutomationTransformer.class })
public class ThermostatControlTest extends AbstractTest {

    /** The Constant COOL. */
    private final static String COOL = "Cool";

    /** The Constant HEAT. */
    private final static String HEAT = "Heat";

    /** The Constant OFF. */
    private static final String OFF = "Off";

    /** The Constant MAX_COOL. */
    private final static Integer MAX_COOL = 89;

    /** The Constant MAX_HEAT. */
    private final static Integer MAX_HEAT = 89;

    /** The Constant MIN_COOL. */
    private final static Integer MIN_COOL = 65;

    /** The Constant MIN_HEAT. */
    private final static Integer MIN_HEAT = 45;

    /** The Constant HEAT_MODE_COLOR. */
    private final static String HEAT_MODE_COLOR = "255, 118, 0";

    /** The Constant COOL_MODE_COLOR. */
    private final static String COOL_MODE_COLOR = "0, 179, 227";

    /** The th ctrl ui page. */
    @Inject
    private TstatControlUIPage thCtrlUIPage;

    /** The th ctrl ops page. */
    @Inject
    private TstatControlOpsPage thCtrlOpsPage;

    /** The th db validation. */
    @Inject
    private TstatDBValidation thDbValidation;

    /** The thermostat admin. */
    @Inject
    private TstatAdminValidation thermostatAdmin;

    /** The th page ui. */
    @Inject
    private ThermostatPageUI thPageUI;    

    /**
     * APP-61 Heater turn on.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS, Groups.SANITY2 }, dataProvider = "defaultUser", dataProviderClass = CommonsDataProvider.class, priority = 1)
    public void heaterTurnOn(final String userName, final String password) {

        loadPage(userName, password, true);
        checkCommonPrequisites();
        thPageOps.openTstatController();
        Assert.assertTrue(thCtrlOpsPage.isPageLoaded());
        thCtrlOpsPage.changeToHeat();
        final String thermostatName = thPageUI.getCurrentThermostatName();
        WaitUtil.smallWait();
        processHVACChange(userName, thermostatName, HEAT);
        thCtrlOpsPage.closeThermostatControl();
        Assert.assertFalse(thCtrlOpsPage.isPopUpOpened());
        Assert.assertTrue(thPageUI.isHeaterOn(), "Heater not turned On");
        thDbValidation.verifyHvacState(userName, thermostatName, HEAT);
    }
    
    /**
     * APP-62 Ac turn on.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS, Groups.SANITY2 }, dataProvider = "defaultUser", dataProviderClass = CommonsDataProvider.class, priority = 2)
    public void acTurnOn(final String userName, final String password) {

        loadPage(userName, password, true);
        checkCommonPrequisites();
        final String thermostatName = thPageUI.getCurrentThermostatName();
        thPageOps.openTstatController();
        Assert.assertTrue(thCtrlOpsPage.isPageLoaded());
        thCtrlOpsPage.changeToCool();
        WaitUtil.smallWait();
        processHVACChange(userName, thermostatName, COOL);
        thCtrlOpsPage.closeThermostatControl();
        Assert.assertFalse(thCtrlOpsPage.isPopUpOpened());
        Assert.assertTrue(thPageUI.isAcOn(), "Ac not turned On");
        thDbValidation.verifyHvacState(userName, thermostatName, COOL);
    }

    /**
     * APP-6 Current temperature displayed.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS, Groups.SMOKE }, dataProvider = "defaultUser", dataProviderClass = CommonsDataProvider.class, priority = 3)
    public void currentTemperatureDisplayed(final String userName, final String password) {

        loadPage(userName, password, true);
        checkCommonPrequisites();
        thPageOps.openTstatController();
        Assert.assertTrue(thCtrlOpsPage.isPageLoaded());
        final String thermostatName = thPageUI.getCurrentThermostatName();
        if (thCtrlUIPage.getCurrentMode().equalsIgnoreCase(COOL)) {
            thCtrlOpsPage.changeToCool();
            final String currentTemp = thCtrlUIPage.getCurrentTemperature();
            thDbValidation.verifyCurrentTemp(userName, thermostatName, COOL, currentTemp);
            thermostatAdmin.verifyCurrentTemperature(userName, thermostatName, currentTemp);
        } else if (thCtrlUIPage.getCurrentMode().equalsIgnoreCase(HEAT)) {
            thCtrlOpsPage.changeToHeat();
            final String currentTemp = thCtrlUIPage.getCurrentTemperature();
            thDbValidation.verifyCurrentTemp(userName, thermostatName, HEAT, currentTemp);
            thermostatAdmin.verifyCurrentTemperature(userName, thermostatName, currentTemp);
        }
    }
    
    /**
     * APP-7 Target temperature displayed.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS, Groups.SMOKE }, dataProvider = "defaultUser", dataProviderClass = CommonsDataProvider.class, priority = 4)
    public void targetTemperatureDisplayed(final String userName, final String password) {

        loadPage(userName, password, true);
        checkCommonPrequisites();
        thPageOps.openTstatController();
        Assert.assertTrue(thCtrlOpsPage.isPageLoaded());
        final String thermostatName = thPageUI.getCurrentThermostatName();
        if (thCtrlUIPage.getCurrentMode().equalsIgnoreCase(COOL)) {
            thCtrlOpsPage.changeToCool();
            final String targetTemp = thCtrlUIPage.getTargetTemperature();
            thDbValidation.verifyTargetTemp(userName, thermostatName, COOL, targetTemp);
            thermostatAdmin.verifyTargetTemp(userName, thermostatName, COOL, targetTemp);
        } else if (thCtrlUIPage.getCurrentMode().equalsIgnoreCase(HEAT)) {
            thCtrlOpsPage.changeToHeat();
            final String targetTemp = thCtrlUIPage.getTargetTemperature();
            thDbValidation.verifyTargetTemp(userName, thermostatName, HEAT, targetTemp);
            thermostatAdmin.verifyTargetTemp(userName, thermostatName, HEAT, targetTemp);
        }
    }

    /**
     * APP-8 Off mode.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS, Groups.SANITY2 }, dataProvider = "defaultUser", dataProviderClass = CommonsDataProvider.class, priority = 5)
    public void offMode(final String userName, final String password) {

        loadPage(userName, password, true);
        checkCommonPrequisites();
        thPageOps.openTstatController();
        Assert.assertTrue(thCtrlOpsPage.isPageLoaded());
        thCtrlOpsPage.changeToOff();
        Assert.assertFalse(thPageUI.isTargetTempDisplayed());
        final String tstatName = thPageUI.getCurrentThermostatName();
        thDbValidation.verifyMode(userName, tstatName, OFF);
        thermostatAdmin.verifyMode(userName, tstatName, OFF);
    }

    /**
     * APP-41 Max temp heat.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS, Groups.SANITY2 }, dataProvider = "defaultUser", dataProviderClass = CommonsDataProvider.class, priority = 6)
    public void moreThanBoundaryHeat(final String userName, final String password) {

        loadPage(userName, password, true);
        checkCommonPrequisites();
        final String thermostatName = thPageUI.getCurrentThermostatName();
        thPageOps.openTstatController();
        Assert.assertTrue(thCtrlOpsPage.isPageLoaded());
        thCtrlOpsPage.changeToHeat();
        WaitUtil.smallWait();
        verifyTargetRange(userName, thermostatName, MAX_HEAT);
    }

    /**
     * APP-9 Idle state for heat.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS, Groups.SANITY2 }, dataProvider = "defaultUser", dataProviderClass = CommonsDataProvider.class, priority = 7)
    public void idleStateForHeat(final String userName, final String password) {

        loadPage(userName, password, true);
        checkCommonPrequisites();
        thPageOps.openTstatController();
        Assert.assertTrue(thCtrlOpsPage.isPageLoaded());
        thCtrlOpsPage.changeToHeat();
        WaitUtil.smallWait();
        Assert.assertTrue(thCtrlOpsPage.isPageLoaded());
        final String thermostatName = thPageUI.getCurrentThermostatName();
        final Integer currentTemp = Integer.valueOf(thCtrlUIPage.getCurrentTemperature());
        final Integer targetTemp = Integer.valueOf(thCtrlUIPage.getTargetTemperature());

        if (!currentTemp.equals(targetTemp)) {
            final Integer setpointValue = currentTemp - targetTemp;
            thCtrlOpsPage.setPointChange(setpointValue, currentTemp);
        }

        thCtrlOpsPage.closeThermostatControl();
        WaitUtil.smallWait();

        final String newCurrentTemp = thPageUI.getCurrentTemperature();
        thDbValidation.verifyCurrentTemp(userName, thermostatName, HEAT, newCurrentTemp);
        thermostatAdmin.verifyCurrentTemperature(userName, thermostatName, newCurrentTemp);
        final String newTargetTemp = thPageUI.getTargetTemperature();
        thDbValidation.verifyTargetTemp(userName, thermostatName, HEAT, newTargetTemp);
        thermostatAdmin.verifyTargetTemp(userName, thermostatName, HEAT, targetTemp.toString());
        Assert.assertTrue(newCurrentTemp.equalsIgnoreCase(newTargetTemp),
                "Current Temperature and Target Temperature differs");
        Assert.assertTrue(thPageUI.isIdleState(), "Temeprature is not in Idle State");
    }

    /**
     * APP-19 Idle state for cool.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS, Groups.SANITY2 }, dataProvider = "defaultUser", dataProviderClass = CommonsDataProvider.class, priority = 8)
    public void idleStateForCool(final String userName, final String password) {

        loadPage(userName, password, true);
        checkCommonPrequisites();
        thPageOps.openTstatController();
        Assert.assertTrue(thCtrlOpsPage.isPageLoaded());
        thCtrlOpsPage.changeToCool();
        WaitUtil.smallWait();
        final String thermostatName = thPageUI.getCurrentThermostatName();
        final Integer currentTemp = Integer.valueOf(thCtrlUIPage.getCurrentTemperature());
        final Integer targetTemp = Integer.valueOf(thCtrlUIPage.getTargetTemperature());

        if (!currentTemp.equals(targetTemp)) {
            final Integer setpointValue = currentTemp - targetTemp;
            thCtrlOpsPage.setPointChange(setpointValue, currentTemp);
        }

        thCtrlOpsPage.closeThermostatControl();
        WaitUtil.smallWait();

        final String newCurrentTemp = thPageUI.getCurrentTemperature();
        thDbValidation.verifyCurrentTemp(userName, thermostatName, COOL, newCurrentTemp);
        // thermostatAdmin.verifyCurrentTemperature(userName, thermostatName, newCurrentTemp);
        final String newTargetTemp = thPageUI.getTargetTemperature();
        thDbValidation.verifyTargetTemp(userName, thermostatName, COOL, newTargetTemp);
        // thermostatAdmin.verifyTargetTemp(userName, thermostatName, COOL, targetTemp.toString());
        Assert.assertTrue(newCurrentTemp.equalsIgnoreCase(newTargetTemp),
                "Current Temperature and Target Temperature differs");
        Assert.assertTrue(thPageUI.isIdleState(), "Temeprature is not in Idle State");
    }

    /**
     * APP-38 Min temp cool.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS, Groups.SANITY2 }, dataProvider = "defaultUser", dataProviderClass = CommonsDataProvider.class, priority = 9)
    public void lessThanBoundaryCool(final String userName, final String password) {

        loadPage(userName, password, true);
        checkCommonPrequisites();
        final String thermostatName = thPageUI.getCurrentThermostatName();
        thPageOps.openTstatController();
        Assert.assertTrue(thCtrlOpsPage.isPageLoaded());
        thCtrlOpsPage.changeToCool();
        WaitUtil.smallWait();
        verifyTargetRange(userName, thermostatName, MIN_COOL);
    }

    /**
     * APP-57 Off mode tstat control close.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS, Groups.SANITY2 }, dataProvider = "defaultUser", dataProviderClass = CommonsDataProvider.class, priority = 10)
    public void tstatControlCloseOffMode(final String userName, final String password) {

        loadPage(userName, password, true);
        checkCommonPrequisites();
        thPageOps.openTstatController();
        Assert.assertTrue(thCtrlOpsPage.isPageLoaded());
        thCtrlOpsPage.changeToOff();
        Assert.assertFalse(thCtrlOpsPage.isPopUpOpened());
    }

    /**
     * APP-21 Increase and decrease cool set point.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS, Groups.SANITY2 }, dataProvider = "defaultUser", dataProviderClass = CommonsDataProvider.class, priority = 11)
    public void increaseAndDecreaseCoolSetPoint(final String userName, final String password) {

        loadPage(userName, password, true);
        checkCommonPrequisites();
        final String thermostatName = thPageUI.getCurrentThermostatName();

        thPageOps.openTstatController();
        Assert.assertTrue(thCtrlOpsPage.isPageLoaded());
        thCtrlOpsPage.changeToCool();
        WaitUtil.smallWait();

        increaseAndDecreaseSetPoint(userName, thermostatName);
        thCtrlOpsPage.closeThermostatControl();
    }

    /**
     * APP-50 Cool background.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS, Groups.SMOKE }, dataProvider = "defaultUser", dataProviderClass = CommonsDataProvider.class, priority = 12)
    public void coolBackground(final String userName, final String password) {

        loadPage(userName, password, true);
        checkCommonPrequisites();
        thPageOps.openTstatController();
        Assert.assertTrue(thCtrlOpsPage.isPageLoaded());
        thCtrlOpsPage.changeToCool();
        WaitUtil.smallWait();
        Assert.assertTrue(thCtrlOpsPage.isPageLoaded());
        Assert.assertTrue(thCtrlUIPage.isCoolBackground(), "Cool Mode Color differs");
        thDbValidation.verifyMode(userName, thPageUI.getCurrentThermostatName(), COOL);
        thCtrlOpsPage.closeThermostatControl();
    }

    /**
     * APP-49 Heat background.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS, Groups.SMOKE }, dataProvider = "defaultUser", dataProviderClass = CommonsDataProvider.class, priority = 13)
    public void heatBackground(final String userName, final String password) {

        loadPage(userName, password, true);
        checkCommonPrequisites();
        thPageOps.openTstatController();
        Assert.assertTrue(thCtrlOpsPage.isPageLoaded());
        thCtrlOpsPage.changeToHeat();
        WaitUtil.smallWait();
        Assert.assertTrue(thCtrlOpsPage.isPageLoaded());
        Assert.assertTrue(thCtrlUIPage.isHeatBackground(), "Heat Mode Color differs");
        thDbValidation.verifyMode(userName, thPageUI.getCurrentThermostatName(), HEAT);
    }

    /**
     * APP-51 Off background.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS, Groups.SMOKE }, dataProvider = "defaultUser", dataProviderClass = CommonsDataProvider.class, priority = 14)
    public void offBackground(final String userName, final String password) {

        loadPage(userName, password, true);
        checkCommonPrequisites();
        thPageOps.openTstatController();
        Assert.assertTrue(thCtrlOpsPage.isPageLoaded());
        thCtrlOpsPage.changeToOff();
        Assert.assertTrue(thCtrlUIPage.isOffBackground(), "Off Mode Color differs");
        thDbValidation.verifyMode(userName, thPageUI.getCurrentThermostatName(), OFF);
    }

    /**
     * APP-53 Close btn pressed.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS, Groups.SMOKE }, dataProvider = "defaultUser", dataProviderClass = CommonsDataProvider.class, priority = 15)
    public void closeBtnDisappears(final String userName, final String password) {

        loadPage(userName, password, true);
        checkCommonPrequisites();
        thPageOps.openTstatController();
        Assert.assertTrue(thCtrlOpsPage.isPageLoaded());
        thCtrlOpsPage.closeThermostatControl();
        Assert.assertFalse(thCtrlOpsPage.isPopUpOpened(), "TstatControlPage Not Closed");
    }

    /**
     * APP-45 Check cool mode on control page.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS, Groups.SANITY2 }, dataProvider = "defaultUser", dataProviderClass = CommonsDataProvider.class, priority = 16)
    public void coolModeOnControlPage(final String userName, final String password) {

        loadPage(userName, password, true);
        checkCommonPrequisites();
        thPageOps.openTstatController();
        Assert.assertTrue(thCtrlOpsPage.isPageLoaded());
        thCtrlOpsPage.changeToCool();
        WaitUtil.smallWait();
        Assert.assertTrue("COOL".equalsIgnoreCase(thCtrlUIPage.getCurrentMode()));
        thDbValidation.verifyMode(userName, thPageUI.getCurrentThermostatName(), COOL);
        // thermostatAdmin.verifyMode(userName, thPageUI.getCurrentThermostatName(), COOL);
    }

    /**
     * APP-44 Check heat mode on control page.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS, Groups.SANITY2 }, dataProvider = "defaultUser", dataProviderClass = CommonsDataProvider.class, priority = 17)
    public void heatModeOnControlPage(final String userName, final String password) {

        loadPage(userName, password, true);
        checkCommonPrequisites();
        thPageOps.openTstatController();
        Assert.assertTrue(thCtrlOpsPage.isPageLoaded());
        thCtrlOpsPage.changeToHeat();
        WaitUtil.smallWait();
        Assert.assertTrue("HEAT".equalsIgnoreCase(thCtrlUIPage.getCurrentMode()));
        thDbValidation.verifyMode(userName, thPageUI.getCurrentThermostatName(), HEAT);
        // thermostatAdmin.verifyMode(userName, thPageUI.getCurrentThermostatName(), HEAT);
    }

    /**
     * APP-60 Sequence of set p oint change.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS, Groups.SANITY2 }, dataProvider = "defaultUser", dataProviderClass = CommonsDataProvider.class, priority = 18)
    public void sequenceOfSetPointChange(final String userName, final String password) {

        loadPage(userName, password, true);
        checkCommonPrequisites();
        final String thermostatName = thPageUI.getCurrentThermostatName();
        thPageOps.openTstatController();
        Assert.assertTrue(thCtrlOpsPage.isPageLoaded());
        thCtrlOpsPage.changeToCool();
        WaitUtil.smallWait();

       // thCtrlOpsPage.checkAndUpdateBoundary(4);
        String targetTemp = thCtrlUIPage.getTargetTemperature();
        String newTarget = String.valueOf(Integer.valueOf(targetTemp) + 2);
        thCtrlOpsPage.setPointChange(2, Integer.valueOf(targetTemp) + 2);
        targetTemp = thCtrlUIPage.getTargetTemperature();
        Assert.assertTrue(newTarget.equalsIgnoreCase(targetTemp),
                "Target not reflected after setpoint increase in Cool mode");
        thDbValidation.verifyTargetTemp(userName, thermostatName, COOL, targetTemp);

        thCtrlOpsPage.changeToHeat();
        WaitUtil.smallWait();
       // thCtrlOpsPage.checkAndUpdateBoundary(4);
        targetTemp = thCtrlUIPage.getTargetTemperature();
        newTarget = String.valueOf(Integer.valueOf(targetTemp) + 2);
        thCtrlOpsPage.setPointChange(2, Integer.valueOf(targetTemp) + 2);
        targetTemp = thCtrlUIPage.getTargetTemperature();
        Assert.assertTrue(newTarget.equalsIgnoreCase(targetTemp),
                "Target not reflected after setpoint increase in Heat mode");
        thDbValidation.verifyTargetTemp(userName, thermostatName, HEAT, targetTemp);

        thCtrlOpsPage.changeToCool();
        WaitUtil.smallWait();
       // thCtrlOpsPage.checkAndUpdateBoundary(4);
        targetTemp = thCtrlUIPage.getTargetTemperature();
        newTarget = String.valueOf(Integer.valueOf(targetTemp) - 2);
        thCtrlOpsPage.setPointChange(-2, Integer.valueOf(targetTemp) - 2);
        targetTemp = thCtrlUIPage.getTargetTemperature();
        Assert.assertTrue(newTarget.equalsIgnoreCase(targetTemp),
                "Target not reflected after setpoint decrease in Cool mode");
        thDbValidation.verifyTargetTemp(userName, thermostatName, COOL, targetTemp);

        thCtrlOpsPage.changeToHeat();
        WaitUtil.smallWait();
       // thCtrlOpsPage.checkAndUpdateBoundary(4);
        targetTemp = thCtrlUIPage.getTargetTemperature();
        newTarget = String.valueOf(Integer.valueOf(targetTemp) - 2);
        thCtrlOpsPage.setPointChange(-2, Integer.valueOf(targetTemp) - 2);
        targetTemp = thCtrlUIPage.getTargetTemperature();
        Assert.assertTrue(newTarget.equalsIgnoreCase(targetTemp),
                "Target not reflected after setpoint decrease in Heat mode");
        thDbValidation.verifyTargetTemp(userName, thermostatName, HEAT, targetTemp);
    }

    /**
     * APP-55 Up arrow btn color.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS, Groups.SMOKE }, dataProvider = "defaultUser", dataProviderClass = CommonsDataProvider.class, priority = 19)
    public void upArrowBtnColor(final String userName, final String password) {

        loadPage(userName, password, true);
        checkCommonPrequisites();
        thPageOps.openTstatController();
        Assert.assertTrue(thCtrlOpsPage.isPageLoaded());
        Assert.assertTrue(thCtrlUIPage.isUpArrowBtnRed(), "Color differs");
    }

    /**
     * APP-40 Min temp heat.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS, Groups.SANITY2 }, dataProvider = "defaultUser", dataProviderClass = CommonsDataProvider.class, priority = 20)
    public void lessThanBoundaryHeat(final String userName, final String password) {

        loadPage(userName, password, true);
        checkCommonPrequisites();
        final String thermostatName = thPageUI.getCurrentThermostatName();
        thPageOps.openTstatController();
        Assert.assertTrue(thCtrlOpsPage.isPageLoaded());
        thCtrlOpsPage.changeToHeat();
        WaitUtil.smallWait();
        verifyTargetRange(userName, thermostatName, MIN_HEAT);
    }

    /**
     * APP-56 Down arrow btn color.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS, Groups.SMOKE }, dataProvider = "defaultUser", dataProviderClass = CommonsDataProvider.class, priority = 21)
    public void downArrowBtnColor(final String userName, final String password) {

        loadPage(userName, password, true);
        checkCommonPrequisites();
        thPageOps.openTstatController();
        Assert.assertTrue(thCtrlOpsPage.isPageLoaded());
        Assert.assertTrue(thCtrlUIPage.isdownArrowBtnBlue(), "Color differs");
    }

    /**
     * APP-58 Close tstat control check tstat page.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS, Groups.SMOKE }, dataProvider = "defaultUser", dataProviderClass = CommonsDataProvider.class, priority = 22)
    public void closeTstatControlCheckTstatPage(final String userName, final String password) {

        loadPage(userName, password, true);
        checkCommonPrequisites();
        thPageOps.openTstatController();
        Assert.assertTrue(thCtrlOpsPage.isPageLoaded());
        thCtrlOpsPage.closeThermostatControl();
        Assert.assertTrue(thPageUI.isPageLoaded());
    }

    /**
     * APP-54 Close and launch app.
     * @param userName the user name
     * @param password the password
     * @throws DeviceException the device exception
     */
    @Test(groups = { Groups.SANITY1, Groups.SANITY2, Groups.ANDROID, Groups.IOS }, dataProvider = "defaultUser", dataProviderClass = CommonsDataProvider.class, priority = 23)
    public void closeAndLaunchApp(final String userName, final String password)
            throws DeviceException {

        loadPage(userName, password, true);
        checkCommonPrequisites();
        thPageOps.openTstatController();
        Assert.assertTrue(thCtrlOpsPage.isPageLoaded());
        thCtrlOpsPage.changeToCool();
        WaitUtil.smallWait();
       // thCtrlOpsPage.checkAndUpdateBoundary(2);
        WaitUtil.smallWait();
        thCtrlOpsPage.setPointChange(2, Integer.valueOf(thCtrlUIPage.getTargetTemperature()) + 2);
        WaitUtil.mediumWait();
        thCtrlOpsPage.closeThermostatControl();
        testOps.closeDeviceDriver();
        testOps.loadDeviceDriver();
        testOps.switchToWebView();
    }

    /**
     * APP-39 Max temp cool.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS, Groups.SANITY2 }, dataProvider = "defaultUser", dataProviderClass = CommonsDataProvider.class, priority = 24)
    public void moreThanBoundaryCool(final String userName, final String password) {

        loadPage(userName, password, true);
        checkCommonPrequisites();
        final String thermostatName = thPageUI.getCurrentThermostatName();
        thPageOps.openTstatController();
        Assert.assertTrue(thCtrlOpsPage.isPageLoaded());
        thCtrlOpsPage.changeToCool();
        WaitUtil.smallWait();
        verifyTargetRange(userName, thermostatName, MAX_COOL);
    }

    /**
     * APP-59 Opacity for controls and buttons.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS, Groups.SMOKE }, dataProvider = "defaultUser", dataProviderClass = CommonsDataProvider.class, priority = 25)
    public void opacityForControlsAndButtons(final String userName, final String password) {

        loadPage(userName, password, true);
        checkCommonPrequisites();
        thPageOps.openTstatController();
        Assert.assertTrue(thCtrlOpsPage.isPageLoaded());
        final Double controlOpacity = thCtrlUIPage.getOpacityValueForControls();
        LogUtil.setLogString("Verify the control opacity in between 0.25 and 0.24", true);
        Assert.assertTrue(controlOpacity == 0.25 || controlOpacity > 0.24);
        final Double buttonOpacity = thCtrlUIPage.getOpacityvalueForButtons();
        LogUtil.setLogString("Button Opacity in UI :" + buttonOpacity, true);
        Assert.assertTrue(buttonOpacity >= 0.35 && buttonOpacity <= 0.40);

    }

    /**
     * APP-63 Change on and off.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS, Groups.SMOKE }, dataProvider = "defaultUser", dataProviderClass = CommonsDataProvider.class, priority = 26)
    public void changeOnAndOff(final String userName, final String password) {

        loadPage(userName, password, true);
        checkCommonPrequisites();
        thPageOps.openTstatController();
        Assert.assertTrue(thCtrlOpsPage.isPageLoaded());
        thCtrlOpsPage.changeToCool();
        checkOnOff(COOL, userName);

        thPageOps.openTstatController();
        Assert.assertTrue(thCtrlOpsPage.isPageLoaded());

        thCtrlOpsPage.changeToHeat();
        checkOnOff(HEAT, userName);
    }

    /**
     * APP-64 Fan change on and auto.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS, Groups.SMOKE }, dataProvider = "defaultUser", dataProviderClass = CommonsDataProvider.class, priority = 27)
    public void fanChangeOnAndAuto(final String userName, final String password) {

        loadPage(userName, password, true);
        checkCommonPrequisites();
        thPageOps.openTstatController();
        Assert.assertTrue(thCtrlOpsPage.isPageLoaded());
        thCtrlOpsPage.changeToCool();
        checkFanOnAuto(COOL, userName);
        thCtrlOpsPage.changeToHeat();
        checkFanOnAuto(HEAT, userName);
    }

    /**
     * Label color for heat mode.
     * @param userName the user name
     * @param password the password
     * @the exception
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS, Groups.SMOKE }, dataProvider = "defaultUser", dataProviderClass = CommonsDataProvider.class, priority = 28, enabled = false)
    public void labelColorForHeatMode(final String userName, final String password) {

        loadPage(userName, password, true);
        checkCommonPrequisites();
        thPageOps.openTstatController();
        Assert.assertTrue(thCtrlOpsPage.isPageLoaded());
        thCtrlOpsPage.changeToHeat();
        Assert.assertTrue(
                thCtrlUIPage.getRgbForHvacSelectedButton().equalsIgnoreCase(HEAT_MODE_COLOR),
                "Color Differs");
        Assert.assertTrue(
                thCtrlUIPage.getRgbForFanSelectedButton().equalsIgnoreCase(HEAT_MODE_COLOR),
                "Color Differs");
    }

    /**
     * Label color for cool mode.
     * @param userName the user name
     * @param password the password
     * @the exception
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS, Groups.SMOKE }, dataProvider = "defaultUser", dataProviderClass = CommonsDataProvider.class, priority = 29, enabled = false)
    public void labelColorForCoolMode(final String userName, final String password) {

        loadPage(userName, password, true);
        checkCommonPrequisites();
        thPageOps.openTstatController();
        Assert.assertTrue(thCtrlOpsPage.isPageLoaded());
        thCtrlOpsPage.changeToCool();
        Assert.assertTrue(
                thCtrlUIPage.getRgbForHvacSelectedButton().equalsIgnoreCase(COOL_MODE_COLOR),
                "Color Differs");
        Assert.assertTrue(
                thCtrlUIPage.getRgbForFanSelectedButton().equalsIgnoreCase(COOL_MODE_COLOR),
                "Color Differs");
    }

    /**
     * APP-22 Increase and decrease heat set point.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS, Groups.SANITY2 }, dataProvider = "defaultUser", dataProviderClass = CommonsDataProvider.class, priority = 30)
    public void increaseAndDecreaseHeatSetPoint(final String userName, final String password) {

        loadPage(userName, password, true);
        checkCommonPrequisites();
        final String thermostatName = thPageUI.getCurrentThermostatName();
        thPageOps.openTstatController();
        Assert.assertTrue(thCtrlOpsPage.isPageLoaded());
        thCtrlOpsPage.changeToHeat();
        WaitUtil.smallWait();
        increaseAndDecreaseSetPoint(userName, thermostatName);
        thCtrlOpsPage.closeThermostatControl();
    }

    /**
     * Seperator color.
     * @param userName the user name
     * @param password the password
     */
    // @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS, Groups.SMOKE },
    // dataProvider = "defaultUser", dataProviderClass = CommonsDataProvider.class, priority = 31,
    // enabled = true)
    public void separatorColor(final String userName, final String password) {

        loadPage(userName, password, true);
        checkCommonPrequisites();
        thPageOps.openTstatController();
        Assert.assertTrue(thCtrlOpsPage.isPageLoaded());
        thCtrlOpsPage.changeToCool();
        LogUtil.setLogString("Verify the Color for Seperator", true);
        LogUtil.setLogString("Expected Color in Cool Mode " + COOL_MODE_COLOR, true);
        LogUtil.setLogString("The Color in UI is :" + thCtrlUIPage.getRgbForSeperator(), true);
        Assert.assertTrue(thCtrlUIPage.getRgbForSeperator().equalsIgnoreCase(COOL_MODE_COLOR),
                "Color Differs");
        thCtrlOpsPage.changeToHeat();
        LogUtil.setLogString("Expected Color in Heat Mode " + HEAT_MODE_COLOR, true);
        LogUtil.setLogString("The Color in UI is :" + thCtrlUIPage.getRgbForSeperator(), true);
        Assert.assertTrue(thCtrlUIPage.getRgbForSeperator().equalsIgnoreCase(HEAT_MODE_COLOR),
                "Color Differs");
    }

    /**
     * Off mode message.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS, Groups.SMOKE }, dataProvider = "defaultUser", dataProviderClass = CommonsDataProvider.class, priority = 32)
    public void offModeMessage(final String userName, final String password) {

        loadPage(userName, password, true);
        checkCommonPrequisites();
        thPageOps.openTstatController();
        Assert.assertTrue(thCtrlOpsPage.isPageLoaded());
        thCtrlOpsPage.changeToOff();
        Assert.assertFalse(thPageUI.isTargetTempDisplayed());
        String msg = thPageUI.getOffModeMessage();
        LogUtil.setLogString("Message :" + msg, true);
        LogUtil.setLogString("Verify the mode off message is displayed", true);
        Assert.assertTrue(msg.contains("Thermostat Mode is off"));
    }

    /**
     * APP-278 Verify either schedule message is display or not.
     * @param userName the user name.
     * @param password the password.
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS, Groups.SMOKE }, dataProvider = "defaultUser", dataProviderClass = CommonsDataProvider.class, priority = 33)
    public void scheduleMessage(final String userName, final String password) {

        loadPage(userName, password, true);
        checkCommonPrequisites();
        thPageOps.openTstatController();
        Assert.assertTrue(thCtrlOpsPage.isPageLoaded());
        Assert.assertTrue(thCtrlOpsPage.displayScheduleMessage());
        thCtrlOpsPage.closeThermostatControl();
    }

    /**
     * APP-279 Verify cool label color.
     * @param userName the user name.
     * @param password the password.
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS, Groups.SMOKE }, dataProvider = "defaultUser", dataProviderClass = CommonsDataProvider.class, priority = 34)
    public void coolLabelColor(final String userName, final String password) {

        loadPage(userName, password, true);
        checkCommonPrequisites();
        String mode = thPageUI.getCurrentMode();
        if (mode.equalsIgnoreCase(HEAT)) {

            thPageOps.openTstatController();
            Assert.assertTrue(thCtrlOpsPage.isPageLoaded());
            thCtrlOpsPage.changeToCool();
            mode = thPageUI.getCurrentMode();
            thCtrlOpsPage.labelColor(mode);
        } else if (mode.equalsIgnoreCase(COOL)) {

            thPageOps.openTstatController();
            Assert.assertTrue(thCtrlOpsPage.isPageLoaded());
            thCtrlOpsPage.labelColor(mode);
        }
    }

    /**
     * APP-280 Verify heat label color.
     * @param userName the user name.
     * @param password the password.
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS, Groups.SMOKE }, dataProvider = "defaultUser", dataProviderClass = CommonsDataProvider.class, priority = 35)
    public void heatLabelColor(final String userName, final String password) {

        loadPage(userName, password, true);
        checkCommonPrequisites();
        String mode = thPageUI.getCurrentMode();
        if (mode.equalsIgnoreCase(COOL)) {

            thPageOps.openTstatController();
            Assert.assertTrue(thCtrlOpsPage.isPageLoaded());
            thCtrlOpsPage.changeToHeat();
            mode = thPageUI.getCurrentMode();
            thCtrlOpsPage.labelColor(mode);
        } else if (mode.equalsIgnoreCase(HEAT)) {

            thPageOps.openTstatController();
            Assert.assertTrue(thCtrlOpsPage.isPageLoaded());
            thCtrlOpsPage.labelColor(mode);
        }
    }

    /**
     * APP-281 Verify horizontal separator line cool mode.
     * @param userName the user name.
     * @param password the password.
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS, Groups.SMOKE }, dataProvider = "defaultUser", dataProviderClass = CommonsDataProvider.class, priority = 36)
    public void separatorLineCoolMode(final String userName, final String password) {

        loadPage(userName, password, true);
        checkCommonPrequisites();
        String mode = thPageUI.getCurrentMode();
        if (mode.equalsIgnoreCase(COOL)) {

            thPageOps.openTstatController();
            Assert.assertTrue(thCtrlOpsPage.isPageLoaded());
            thCtrlOpsPage.separatorLine(mode);
        } else if (mode.equalsIgnoreCase(HEAT)) {

            thPageOps.openTstatController();
            Assert.assertTrue(thCtrlOpsPage.isPageLoaded());
            thCtrlOpsPage.changeToCool();
            mode = thPageUI.getCurrentMode();
            thCtrlOpsPage.separatorLine(mode);
        }
    }

    /**
     * APP-282 Verify horizontal separator line cool mode.
     * @param userName the user name.
     * @param password the password.
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS, Groups.SMOKE }, dataProvider = "defaultUser", dataProviderClass = CommonsDataProvider.class, priority = 37)
    public void separatorLineHeatMode(final String userName, final String password) {

        loadPage(userName, password, true);
        checkCommonPrequisites();
        String mode = thPageUI.getCurrentMode();
        if (mode.equalsIgnoreCase(HEAT)) {

            thPageOps.openTstatController();
            Assert.assertTrue(thCtrlOpsPage.isPageLoaded());
            thCtrlOpsPage.separatorLine(mode);
        } else if (mode.equalsIgnoreCase(COOL)) {

            thPageOps.openTstatController();
            Assert.assertTrue(thCtrlOpsPage.isPageLoaded());
            thCtrlOpsPage.changeToHeat();
            mode = thPageUI.getCurrentMode();
            thCtrlOpsPage.separatorLine(mode);
        }
    }

    /**
     * APP-48 Heat and cool user.
     * @param userName the user name.
     * @param password the password.
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS, Groups.SMOKE }, dataProvider = "defaultUser", dataProviderClass = CommonsDataProvider.class, priority = 38)
    public void heatAndCoolUser(final String userName, final String password) {

        loadPage(userName, password, true);
        checkCommonPrequisites();
        thPageOps.openTstatController();
        Assert.assertTrue(thCtrlOpsPage.isPageLoaded());
        String mode = thPageUI.getCurrentMode();
        if (mode.equalsIgnoreCase(COOL)) {

            thCtrlOpsPage.verifyModesEnable(mode);
        } else if (mode.equalsIgnoreCase(HEAT)) {

            thCtrlOpsPage.verifyModesEnable(mode);
        }
    }

    /**
     * verify thermostat control closed if anywhere click.
     * @param userName the user name.
     * @param password the password.
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS}, dataProvider = "defaultUser", dataProviderClass = CommonsDataProvider.class, priority = 39)
    public void tstCtrlClosesClickAny(final String userName, final String password) {

        loadPage(userName, password, true);
        checkCommonPrequisites();
        thPageOps.openTstatController();
        Assert.assertTrue(thCtrlOpsPage.isPageLoaded());
        thPageOps.clickSetAway();
    }
    
    /**
     * APP-46 Heat only user.
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS, Groups.STUB,
            Groups.SMOKE }, priority = 40)
    public void heatOnlyUser() {

        LogUtil.setLogString("Dependency on APPS-6417!", true);
        throw new SkipException("Dependency on APPS-6417!");
    }

    /**
     * APP-42 Thermostat actions at shutdown.
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS, Groups.STUB,
            Groups.SANITY2 }, priority = 41)
    public void thermostatActionsAtShutdown() {

        LogUtil.setLogString("Dependency on 6186!", true);
        throw new SkipException("Dependency on 6186!");
    }

    /**
     * APP-43 Check ifsetpoint reverts back.
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS, Groups.STUB,
            Groups.SANITY2 }, priority = 42)
    public void checkIfsetpointRevertsBack() {

        LogUtil.setLogString("Dependency on 6186!", true);
        throw new SkipException("Dependency on 6186!");
    }

    /**
     * APP-52 Verify error messages.
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS, Groups.STUB,
            Groups.SMOKE }, priority = 43)
    public void verifyErrorMessages() {

        LogUtil.setLogString("Dependency on APPS-6434!", true);
        throw new SkipException("Dependency on APPS-6434!");
    }

    /**
     * APP-47 Cool only user.
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS, Groups.STUB,
            Groups.SMOKE }, priority = 44)
    public void coolOnlyUser() {

        LogUtil.setLogString("Dependency on APPS-6417!", true);
        throw new SkipException("Dependency on APPS-6417!");
    }

    /**
     * Verify target range.
     * @param userName the user name
     * @param thermostatName the thermostat name
     * @param newTarget the new target
     */
    private void verifyTargetRange(final String userName, final String thermostatName,
            final Integer newTarget) {

        //thCtrlOpsPage.checkAndUpdateBoundary(3);
        WaitUtil.smallWait();
        final Integer targetTemp = Integer.valueOf(thCtrlUIPage.getTargetTemperature());
        final Integer setpointValue = newTarget - targetTemp;
        thCtrlOpsPage.setPointChange(setpointValue, newTarget);
        final int setpointChange = newTarget <= 65 ? -1 : 1;
        thCtrlOpsPage.setPointChange(setpointChange);
        final String mode = thCtrlUIPage.getCurrentMode();
        final String changedTarget = thCtrlUIPage.getTargetTemperature();
        LogUtil.setLogString("Verify target temperature does not go beyond " + newTarget
                + ", The target in UI is : " + changedTarget, true);
        Assert.assertEquals(newTarget.toString(), changedTarget.toString(),
                "Target temperature differs");
        thDbValidation.verifyTargetTemp(userName, thermostatName, mode, String.valueOf(newTarget));
        // thermostatAdmin.verifyTargetTemp(userName, thermostatName, mode,
        // String.valueOf(newTarget));
    }

    /**
     * Increase and decrease set point.
     * @param userName the user name
     * @param thermostatName the thermostat name
     */
    private void increaseAndDecreaseSetPoint(final String userName, final String thermostatName) {

       // thCtrlOpsPage.checkAndUpdateBoundary(3);
        final Integer targetTemp = Integer.valueOf(thCtrlUIPage.getTargetTemperature());
        final String mode = thCtrlUIPage.getCurrentMode();
        thCtrlOpsPage.setPointChange(1);
        thDbValidation.verifyTargetTemp(userName, thermostatName, mode,
                String.valueOf(targetTemp + 1));
        // thermostatAdmin.verifyTargetTemp(userName, thermostatName, mode,
        // String.valueOf(targetTemp + 1));
        final Integer newtargetTemp = Integer.valueOf(thCtrlUIPage.getTargetTemperature());
        thCtrlOpsPage.setPointChange(-1);
        thDbValidation.verifyTargetTemp(userName, thermostatName, mode,
                String.valueOf(newtargetTemp - 1));
        // thermostatAdmin.verifyTargetTemp(userName, thermostatName, mode,
        // String.valueOf(newtargetTemp - 1));
    }

    /**
     * Check on off.
     * @param mode the mode
     * @param userName the user name
     */
    private void checkOnOff(final String mode, final String userName) {

        thCtrlOpsPage.changeToOff();
        Assert.assertTrue(thPageUI.isModeOff(), "Change to mode off not reflected in UI.");
        thDbValidation.verifyMode(userName, thPageUI.getCurrentThermostatName(), OFF);
        // thermostatAdmin.verifyMode(userName, thPageUI.getCurrentThermostatName(), OFF);

        thPageOps.turnSystemOn();
        Assert.assertTrue(thPageUI.getCurrentMode().equalsIgnoreCase(mode),
                "Turn on system not reflected  in UI.");
        thDbValidation.verifyMode(userName, thPageUI.getCurrentThermostatName(), mode);
        // thermostatAdmin.verifyMode(userName, thPageUI.getCurrentThermostatName(), mode);
    }

    /**
     * Check fan on off.
     * @param mode the mode
     * @param userName the user name
     */
    private void checkFanOnAuto(final String mode, final String userName) {

        String fanMode = thCtrlUIPage.isFanOn() ? "On" : "Auto";
        thDbValidation.verifyFanMode(userName, thPageUI.getCurrentThermostatName(), fanMode);
        // thermostatAdmin.verifyFanMode(userName, thPageUI.getCurrentThermostatName(), fanMode);

        if (fanMode.equalsIgnoreCase("ON")) {
            thCtrlOpsPage.clickFanAuto();
            Assert.assertFalse(thCtrlUIPage.isFanOn(), "Click fan auto is not updated in UI.");
            fanMode = "Auto";
        } else {
            thCtrlOpsPage.clickFanOn();
            Assert.assertTrue(thCtrlUIPage.isFanOn(), "Click fan on is not updated in UI.");
            fanMode = "On";
        }

        thDbValidation.verifyFanMode(userName, thPageUI.getCurrentThermostatName(), fanMode);
        // thermostatAdmin.verifyFanMode(userName, thPageUI.getCurrentThermostatName(), fanMode);
    }

    /**
     * Process hvac change.
     * @param userName the user name
     * @param thermostatName the thermostat name
     * @param currentMode the current mode
     * @return the string
     */
    private void processHVACChange(final String userName, final String thermostatName,
            final String currentMode) {

        final Integer currentTemp = Integer.valueOf(thCtrlUIPage.getCurrentTemperature());
        final Integer targetTemp = Integer.valueOf(thCtrlUIPage.getTargetTemperature());
        hvacStateBoundary(currentMode, currentTemp);
        if ((currentMode.equalsIgnoreCase(HEAT) && targetTemp >= currentTemp)
                || (currentMode.equalsIgnoreCase(COOL) && targetTemp <= currentTemp)) {
            hvacStateOps(userName, thermostatName, currentMode, currentTemp, targetTemp, false);
        }
        hvacStateOps(userName, thermostatName, currentMode, currentTemp, targetTemp, true);
    }

    /**
     * Hvac state boundary.
     * @param mode the mode
     * @param currentTemp the current temp
     */
    private void hvacStateBoundary(final String mode, final Integer currentTemp) {

        if (mode.equalsIgnoreCase(COOL) && currentTemp < 68) {
            Assert.fail("Current Temperature is Less than 68");
        } else if (mode.equalsIgnoreCase(HEAT) && currentTemp > 86) {

            Assert.fail("Current Temperature is Greater than 86");
        }
    }

    /**
     * Hvac state ops.
     * @param userName the user name
     * @param thermostatName the thermostat name
     * @param currentMode the current mode
     * @param currentTemp the current temp
     * @param targetTemp the target temp
     * @param activate the activate
     */
    private void hvacStateOps(final String userName, final String thermostatName,
            final String currentMode, final Integer currentTemp, final Integer targetTemp,
            final boolean activate) {

       // thCtrlOpsPage.checkAndUpdateBoundary(3);
        final int difference = activate ? -3 : 3;
        final int setTarget = currentMode.equalsIgnoreCase(HEAT) ? currentTemp - difference
                : currentTemp + difference;
        final int changeSetPoint = setTarget - targetTemp;
        WaitUtil.smallWait();
        thCtrlOpsPage.setPointChange(changeSetPoint, setTarget);
        final String changedTarget = thCtrlUIPage.getTargetTemperature();
        Assert.assertTrue(Integer.parseInt(changedTarget) == setTarget,
                "Target temperature differs");
        thDbValidation.verifyTargetTemp(userName, thermostatName, currentMode,
                String.valueOf(setTarget));
    }

    /**
     * Check idle conditions for thermostat.
     */
    @TestPrerequisite
    public void checkCommonPrequisites() {

        Assert.assertFalse(thPageUI.isThermostatOffline(),
                "Unable to do thermostat related UI changes since thermostat is offline.");
        if (thCtrlOpsPage.isPopUpOpened()) {
            thCtrlOpsPage.closeThermostatControl();
        }

        if (awayPage.isPageLoaded()) {
            LogUtil.setLogString("Wait 30 seconds and click Imback", true);
            WaitUtil.largeWait();
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
}
