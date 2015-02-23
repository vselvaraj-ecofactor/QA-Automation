/*
 * SetAwayTest.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.newapp;

import static com.ecofactor.qa.automation.platform.util.LogUtil.*;
import static com.ecofactor.qa.automation.util.WaitUtil.*;

import org.testng.Assert;
import org.testng.annotations.Guice;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.ecofactor.qa.automation.dao.DaoModule;
import com.ecofactor.qa.automation.dao.ProgramDao;
import com.ecofactor.qa.automation.dao.ThermostatAlgoControlDao;
import com.ecofactor.qa.automation.dao.ThermostatProgramLogDao;
import com.ecofactor.qa.automation.newapp.data.CommonsDataProvider;
import com.ecofactor.qa.automation.newapp.enums.SetAwayParams;
import com.ecofactor.qa.automation.newapp.page.AwaySettingsOpsPage;
import com.ecofactor.qa.automation.newapp.page.AwaySettingsUIPage;
import com.ecofactor.qa.automation.newapp.page.MenuPage;
import com.ecofactor.qa.automation.newapp.page.SavingsPage;
import com.ecofactor.qa.automation.newapp.page.SetAwayPage;
import com.ecofactor.qa.automation.newapp.page.ThermostatPageOps;
import com.ecofactor.qa.automation.newapp.page.TstatControlOpsPage;
import com.ecofactor.qa.automation.newapp.page.validate.db.TstatDBValidation;
import com.ecofactor.qa.automation.platform.annotation.TestPrerequisite;
import com.ecofactor.qa.automation.platform.constants.Groups;
import com.ecofactor.qa.automation.platform.enums.CustomLogLevel;
import com.ecofactor.qa.automation.platform.listener.AutomationTransformer;
import com.ecofactor.qa.automation.platform.listener.RetryEnabledListener;
import com.ecofactor.qa.automation.util.WaitUtil;

import com.google.inject.Inject;

import com.ecofactor.common.pojo.Thermostat;
import com.ecofactor.qa.automation.dao.ThermostatDao;

import java.util.Calendar;
import java.util.List;

import java.util.TimeZone;

/**
 * The Class SetAwayTest.
 * @author $Author: vprasannaa $
 * @version $Rev: 33286 $ $Date: 2014-12-29 17:51:45 +0530 (Mon, 29 Dec 2014) $
 * @version $Rev: 33286 $ $Date: 2014-12-29 17:51:45 +0530 (Mon, 29 Dec 2014) $
 */
@Guice(modules = { MobileModule.class, DaoModule.class })
@Listeners({ RetryEnabledListener.class, AutomationTransformer.class })
public class SetAwayTest extends AbstractTest {

    /** The Constant COULD_NOT_SET_AWAY. */
    private static final String COULD_NOT_SET_AWAY = "Could not Set Away.";

    /** The Constant MULTI_TSTAT_ON_OFF. */
    private static final String MULTI_TSTAT_ON_OFF = "multipleOneOnOneOff";

    /** The Constant AWAY_FAIL_ERROR. */
    private final static String AWAY_FAIL_ERROR = "Set away operation failed for thermostat";

    /** The Constant BACK_FAIL_ERROR. */
    private final static String BACK_FAIL_ERROR = "Cancel away operation failed for thermostat";

    /** The Constant ZERO_MIN. */
    public static final int ZERO_MIN = 0;

    /** The Constant ONE_MIN. */
    public static final int ONE_MIN = 1;

    /** The Constant TWO_MIN. */
    public static final int TWO_MIN = 2;

    /** The Constant THREE_MIN. */
    public static final int THREE_MIN = 3;

    /** The Constant FIFTY_SECONDS. */
    public static final int FIFTY_SECONDS = 50;

    /** The Constant LEFT. */
    private static final String LEFT = "left";

    /** The Constant DEFAULT_USER. */
    private static final String DEFAULT_USER = "defaultUser";

    /** The Constant PAGE_LOAD_ERROR. */
    private static final String PAGE_LOAD_ERROR = "Error Loading Away Page";

    /** The Constant RIGHT. */
    private static final String RIGHT = "right";

    /** The th db validation. */
    @Inject
    private TstatDBValidation thDBValidation;

    /** The th ctrl ops page. */
    @Inject
    private TstatControlOpsPage thCtrlOpsPage;

    /** The savings page. */
    @Inject
    private SavingsPage savingsPage;

    /** The menu page. */
    @Inject
    private MenuPage menuPage;

    /** The away settings ops page. */
    @Inject
    private AwaySettingsOpsPage awaySettingsOpsPage;

    /** The away settings ui page. */
    @Inject
    private AwaySettingsUIPage awaySettingsUIPage;

    /** The thermostat dao. */
    @Inject
    private ThermostatDao thermostatDao;

    /** The thermostat page ops. */
    @Inject
    private ThermostatPageOps thermostatPageOps;

    /** The thermostat algo controll dao. */
    @Inject
    private ThermostatAlgoControlDao thermostatAlgoControllDao;

    /** The thermostat program log dao. */
    @Inject
    private ThermostatProgramLogDao thermostatProgramLogDao;

    /** The program dao. */
    @Inject
    private ProgramDao programDao;

    /** The th db validation. */
    @Inject
    private TstatDBValidation thDbValidation;

    /** The set away. */
    @Inject
    private SetAwayPage setAway;

    /**
     * APPS-101 Away set point and default away settings.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = DEFAULT_USER, dataProviderClass = CommonsDataProvider.class, priority = 1)
    public void defaultAwaySetPoint(final String userName, final String password) {

        loadPage(userName, password, true);
        checkIdleConditionsForThermostat();
        resetAway(false);
        final String mode = thPageUI.getCurrentMode();
        gotoAwaySettingsFromHome();
        final String heatValue = Integer.toString(awaySettingsUIPage
                .getPickerValue(SetAwayParams.Heating));
        final String coolValue = Integer.toString(awaySettingsUIPage
                .getPickerValue(SetAwayParams.Cooling));
        final Integer hours = Integer.valueOf(awaySettingsUIPage
                .getPickerValue(SetAwayParams.Hours));
        final Integer days = Integer.valueOf(awaySettingsUIPage.getPickerValue(SetAwayParams.Days));

        setLogString("Existing Heat & Cool Settings are: Heating = '" + heatValue
                + "' and Cooling = '" + coolValue + "' and Hours = '" + hours + "' and Days = '"
                + days + "'.", true, CustomLogLevel.MEDIUM);

        String settingsTemp;
        if ("heat".equals(mode)) {
            awaySettingsOpsPage.setAwayParamPicker(
                    SetAwayParams.Heating,
                    manipulatedValue(Integer.valueOf(awaySettingsUIPage
                            .getPickerValue(SetAwayParams.Heating)), 45, 86));
            settingsTemp = Integer.toString(awaySettingsUIPage
                    .getPickerValue(SetAwayParams.Heating));
        } else {
            awaySettingsOpsPage.setAwayParamPicker(
                    SetAwayParams.Cooling,
                    manipulatedValue(Integer.valueOf(awaySettingsUIPage
                            .getPickerValue(SetAwayParams.Cooling)), 65, 89));
            settingsTemp = Integer.toString(awaySettingsUIPage
                    .getPickerValue(SetAwayParams.Cooling));
        }
        setLogString("New Settings for " + mode + " mode = '" + settingsTemp + "'.", true,
                CustomLogLevel.MEDIUM);

        awaySettingsOpsPage.clickBack();
        awaySettingsOpsPage.clickMenuIcon();
        menuPage.clickThermostatMenuItem();
        thermostatPageOps.loadAwaySettingsPopup();

        final List<Integer> thermostatIds = thDbValidation.getThermostatListForUser(userName);
        final Thermostat thermostat = thDbValidation.getThermostatById(thermostatIds.get(0)
                .toString());

        Calendar currentDateTime = Calendar.getInstance(TimeZone.getTimeZone(thermostat
                .getTimezone()));

        currentDateTime.set(Calendar.HOUR, currentDateTime.get(Calendar.HOUR) - 1);
        setLogString("Check that its not possible to set past hour for set away end time.", true);

        awayPage.setAwayDate(currentDateTime);
        awayPage.setAwayTime(currentDateTime);
        awayPage.clickSetAwayButton();
        setLogString("Unable to set past time and date for set away end time.", true);
    }

    /**
     * APPS-64.Validate the Time value for Away APPS-70.Validates whether the Time value is in the
     * past and the toast gets displayed
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = DEFAULT_USER, dataProviderClass = CommonsDataProvider.class, priority = 2)
    public void checkTimeForAway(final String userName, final String password) {

        loadPage(userName, password, true);
        checkIdleConditionsForThermostat();
        resetAway(true);
        thermostatPageOps.loadAwaySettingsPopup();

        final List<Integer> thermostatIds = thDbValidation.getThermostatListForUser(userName);
        final Thermostat thermostat = thDbValidation.getThermostatById(thermostatIds.get(0)
                .toString());

        Calendar currentDateTime = Calendar.getInstance(TimeZone.getTimeZone(thermostat
                .getTimezone()));

        currentDateTime.set(Calendar.HOUR, currentDateTime.get(Calendar.HOUR) - 1);
        setLogString("Check that its not possible to set past hour for set away end time.", true);

        awayPage.setAwayDate(currentDateTime);
        awayPage.setAwayTime(currentDateTime);
        awayPage.clickSetAwayButton();
        setLogString("Unable to set past time for set away end time.", true);
    }

    /**
     * APPS-63.Validate the Date value for Away
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = DEFAULT_USER, dataProviderClass = CommonsDataProvider.class, priority = 3)
    public void checkDateForAway(final String userName, final String password) {

        loadPage(userName, password, true);
        checkIdleConditionsForThermostat();
        resetAway(true);
        thermostatPageOps.loadAwaySettingsPopup();

        final List<Integer> thermostatIds = thDbValidation.getThermostatListForUser(userName);
        final Thermostat thermostat = thDbValidation.getThermostatById(thermostatIds.get(0)
                .toString());

        Calendar currentDateTime = Calendar.getInstance(TimeZone.getTimeZone(thermostat
                .getTimezone()));

        currentDateTime.set(Calendar.DATE, currentDateTime.get(Calendar.DATE) - 5);
        setLogString("Check that its not possible to set past date for set away end date.", true);
        awayPage.setAwayTime(currentDateTime);
        awayPage.setAwayDate(currentDateTime);
        awayPage.clickSetAwayButton();

        setLogString("Unable to set past date for set away end date.", true);
        awayPage.displayActualSetAwayInfo();
        resetAway(true);
    }

    /**
     * Row #131. Apply away settings temp. APPS-102. Away settings values are applied if no changes
     * are made to the values while setting Away
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = DEFAULT_USER, dataProviderClass = CommonsDataProvider.class, priority = 4)
    public void applyAwaySettingsTemp(final String userName, final String password) {

        loadPage(userName, password, true);
        checkIdleConditionsForThermostat();
        resetAway(false);
        final String mode = thPageUI.getCurrentMode();
        gotoAwaySettingsFromHome();
        final String heatValue = Integer.toString(awaySettingsUIPage
                .getPickerValue(SetAwayParams.Heating));
        final String coolValue = Integer.toString(awaySettingsUIPage
                .getPickerValue(SetAwayParams.Cooling));

        setLogString("Existing Heat & Cool Settings are: Heating = '" + heatValue
                + "' and Cooling = '" + coolValue + "'.", true, CustomLogLevel.MEDIUM);
        String settingsTemp;
        if ("heat".equals(mode)) {
            awaySettingsOpsPage.setAwayParamPicker(
                    SetAwayParams.Heating,
                    manipulatedValue(Integer.valueOf(awaySettingsUIPage
                            .getPickerValue(SetAwayParams.Heating)), 45, 86));
            settingsTemp = Integer.toString(awaySettingsUIPage
                    .getPickerValue(SetAwayParams.Heating));
        } else {
            awaySettingsOpsPage.setAwayParamPicker(
                    SetAwayParams.Cooling,
                    manipulatedValue(Integer.valueOf(awaySettingsUIPage
                            .getPickerValue(SetAwayParams.Cooling)), 65, 89));
            settingsTemp = Integer.toString(awaySettingsUIPage
                    .getPickerValue(SetAwayParams.Cooling));
        }
        setLogString("New Settings for " + mode + " mode = '" + settingsTemp + "'.", true,
                CustomLogLevel.MEDIUM);
        awaySettingsOpsPage.clickBack();
        awaySettingsOpsPage.clickMenuIcon();
        thPageOps.clickThermostatIcon();
    }

    /**
     * APPS-83 Click outside away settingspopup.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = DEFAULT_USER, dataProviderClass = CommonsDataProvider.class, priority = 5)
    public void clickOutsideAwaySettingspopup(final String userName, final String password) {

        loadPage(userName, password, true);
        checkIdleConditionsForThermostat();
        resetAway(true);

        Assert.assertTrue(thPageOps.loadAwaySettingsPopup(),
                "Away settings popup is not loaded in thermostat page.");

        thPageOps.clickControlsIcon();

        Assert.assertTrue(thPageUI.isAwaySettingsPopUpLoaded(),
                "Away settings popup was dismissed in thermostat page when clicked outside away settings popup.");

        setLogString(
                "Away settings popup is not dismissed in thermostat page when clicked outside away settings popup.",
                true);
    }

    /**
     * APPS-73 Footer buttons on away popup open.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = DEFAULT_USER, dataProviderClass = CommonsDataProvider.class, priority = 6)
    public void footerButtonsOnAwayPopupOpen(final String userName, final String password) {

        loadPage(userName, password, true);
        checkIdleConditionsForThermostat();
        resetAway(true);

        Assert.assertTrue(thPageOps.loadAwaySettingsPopup(),
                "Away settings popup is not loaded in thermostat page.");

        setLogString("Check if set away icon click dismisses Away pop up page.", true);
        thPageOps.loadAwaySettingsPopup();

        Assert.assertTrue(thPageUI.isAwaySettingsPopUpLoaded(),
                "Away settings popup was dismissed in thermostat page when set away icon is clicked.");

        setLogString(
                "Away settings popup is not dismissed in thermostat page when setaway icon clicked.",
                true);

        thPageOps.clickSavings();
        thPageOps.clickSettingsMenu();
        thPageOps.clickThermostatIcon();
        setLogString(
                "Away settings popup is not dismissed in thermostat page when savings icon clicked.",
                true);
    }

    /**
     * APPS-74 Boundary values for heat.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = DEFAULT_USER, dataProviderClass = CommonsDataProvider.class, priority = 7)
    public void boundaryValuesForHeat(final String userName, final String password) {

        loadPage(userName, password, true);
        checkIdleConditionsForThermostat();
        resetAway(true);

        thPageOps.openTstatController();
        thCtrlOpsPage.changeToHeat();
        thCtrlOpsPage.closeThermostatControl();

        Assert.assertTrue(thPageOps.loadAwaySettingsPopup(),
                "Away settings popup is not loaded in thermostat page.");

        Assert.assertFalse(awayPage.setAwayParamPicker(SetAwayParams.Heating, 44),
                "Able to set value < 45 as set away value for Heat picker.");

        setLogString("The Lower boundary for heat set point is 45.", true);

        Assert.assertFalse(awayPage.setAwayParamPicker(SetAwayParams.Heating, 90),
                "Able to set value > 89 as set away value for Heat picker.");

        setLogString("The Upper boundary for heat set point is 89.", true);
    }

    /**
     * APPS-74 Boundary values for cool.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = DEFAULT_USER, dataProviderClass = CommonsDataProvider.class, priority = 8)
    public void boundaryValuesForCool(final String userName, final String password) {

        loadPage(userName, password, true);
        checkIdleConditionsForThermostat();
        resetAway(true);

        thPageOps.openTstatController();
        thCtrlOpsPage.changeToCool();
        WaitUtil.smallWait();
        thCtrlOpsPage.closeThermostatControl();

        Assert.assertTrue(thPageOps.loadAwaySettingsPopup(),
                "Away settings popup is not loaded in thermostat page.");

        Assert.assertFalse(awayPage.setAwayParamPicker(SetAwayParams.Cooling, 64),
                "Able to set value < 65 as set away value for Cool picker.");

        setLogString("The Lower boundary for cool set point is 65.", true);

        Assert.assertFalse(awayPage.setAwayParamPicker(SetAwayParams.Cooling, 90),
                "Able to set value > 89 as set away value for Cool picker.");

        setLogString("The Upper boundary for cool set point is 89.", true);
    }

    /**
     * row #8 Check away popup is present in Away Mode. APPS-200 Set the user in away mode and
     * verify DB and UI
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, enabled = true, dataProvider = DEFAULT_USER, dataProviderClass = CommonsDataProvider.class, priority = 9)
    public void checkAwayPopupInAwayMode(final String userName, final String password) {

        loadPage(userName, password, true);
        checkIdleConditionsForThermostat();
        thPageOps.clickSetAway();
        setAway.clickSetButton();
        setAway.clickAmBack();
        Assert.assertTrue(thPageUI.isPageLoaded());
    }

    /**
     * APPS-75.Check whether frequent opening / closing of set away menu not getting into funky
     * state
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = DEFAULT_USER, dataProviderClass = CommonsDataProvider.class, priority = 10)
    public void openCloseAwayFrequently(final String userName, final String password) {

        loadPage(userName, password, true);

        for (int i = 1; i <= 4; i++) {
            thermostatPageOps.loadAwaySettingsPopup();
            awayPage.closeAwaySettingsPopup();
        }
    }

    /**
     * APPS-70 Error msg for past away settings.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = DEFAULT_USER, dataProviderClass = CommonsDataProvider.class, priority = 11)
    public void errorMsgForPastAwaySettings(final String userName, final String password) {

        loadPage(userName, password, true);
        checkIdleConditionsForThermostat();
        resetAway(true);

        Assert.assertTrue(thPageOps.loadAwaySettingsPopup(),
                "Away settings popup is not loaded in thermostat page.");

        final List<Integer> thermostatIds = thermostatDao.getThermostatIdForUser(userName);
        final Thermostat thermostat = thermostatDao.findByThermostatId(thermostatIds.get(0)
                .toString());
        final Calendar currentDateTime = Calendar.getInstance(TimeZone.getTimeZone(thermostat
                .getTimezone()));

        currentDateTime.set(Calendar.MINUTE, currentDateTime.get(Calendar.MINUTE) - 10);
        setLogString("Provide past date time for set away and check if toast message pops up.",
                true);

        awayPage.setAwayDate(currentDateTime);
        awayPage.setAwayTime(currentDateTime);
        awayPage.clickSetAwayButton();
    }

    /**
     * APPS-59 Away ui elements.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = DEFAULT_USER, dataProviderClass = CommonsDataProvider.class, priority = 12)
    public void awayUIElements(final String userName, final String password) {

        loadPage(userName, password, true);
        checkIdleConditionsForThermostat();
        resetAway(true);

        Assert.assertTrue(thPageOps.loadAwaySettingsPopup(),
                "Away settings popup is not loaded in thermostat page.");

        Assert.assertTrue(awayPage.isDatePickerDisplayed(), "Away date picker is not displayed.");
        Assert.assertTrue(awayPage.isTimePickerDisplayed(), "Away time picker is not displayed.");
    }

    /**
     * APPS-1 Cancel away by mo.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = DEFAULT_USER, dataProviderClass = CommonsDataProvider.class, priority = 13)
    public void cancelAwayByMOOneThermostat(final String userName, final String password) {

        loadPage(userName, password, true);
        checkIdleConditionsForThermostat();
        resetAway(true);

        Assert.assertTrue(thPageOps.loadAwaySettingsPopup(),
                "Away settings popup is not loaded in thermostat page.");

        final List<Integer> thermostatIds = thDbValidation.getThermostatListForUser(userName);
        final Thermostat thermostat = thDbValidation.getThermostatById(thermostatIds.get(0)
                .toString());

        awayPage.clickSetAwayButton();
        waitUntil(THIRTY_SECS);

        setLogString("Check if Thermostat program log table was updated for set away", true);
        thDBValidation.verifyAlgorithmInAlgoControl(thermostat.getId(), -20);

        thDbValidation.verifyProgramForAlgorithm(thermostat.getId(), -20);

        waitUntil(THIRTY_SECS);
       // awayPage.clickAmBack();
        //waitUntil(THIRTY_SECS);

        setLogString("Check if Thermostat program log table was updated for I'm Back.", true);
        thDBValidation.isProgramTypeActiveInProgramLog(thermostat.getId(), -20, false);

        setLogString("Thermostat program log table verified for Im back.", true);
    }

    /**
     * APPS-72 Dismiss away popup on back btn click.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.ANDROID }, dataProvider = DEFAULT_USER, dataProviderClass = CommonsDataProvider.class, priority = 14)
    public void dismissAwayPopupOnBackBtnClick(final String userName, final String password) {

        loadPage(userName, password, true);
        checkIdleConditionsForThermostat();
        resetAway(true);

        Assert.assertTrue(thPageOps.loadAwaySettingsPopup(),
                "Away settings popup is not loaded in thermostat page.");

        testOps.getDeviceDriver().navigate().back();

        Assert.assertFalse(thPageUI.isAwaySettingsPopUpLoaded(),
                "Away settings popup still displayed when back button clicked.");
    }

    /**
     * APP-199. Away ui for multiple thermostat.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, enabled = false, dataProvider = "multiThermostatUser", dataProviderClass = CommonsDataProvider.class, priority = 14)
    public void awayUIForMultipleThermostat(final String userName, final String password) {

        loadPage(userName, password, true);
        checkIdleConditionsForThermostat();
        String currTstatName = thPageUI.getCurrentThermostatName();
        final int noOfThermostats = thPageUI.getNoOfThermostats();

        doSetAwayVerifyDBAndUI(userName, currTstatName);

        setLogString("Wait for 30 seconds and verify that away status does not change.", true,
                CustomLogLevel.MEDIUM);
        largeWait();
        setLogString("Current thermostat name: " + currTstatName, true, CustomLogLevel.MEDIUM);
        Assert.assertTrue(awayPage.isPageLoaded());
        Assert.assertFalse(awayPage.waitForErrorMessage(AWAY_FAIL_ERROR, ONE_MIN));

        for (int i = 0; i < noOfThermostats - 1; i++) {
            thPageOps.swipe(LEFT);
            currTstatName = thPageUI.getCurrentThermostatName();
            setLogString("Current thermostat name: " + currTstatName, true, CustomLogLevel.MEDIUM);
            Assert.assertTrue(awayPage.isPageLoaded());
            Assert.assertFalse(awayPage.waitForErrorMessage(AWAY_FAIL_ERROR, ONE_MIN));
        }

        doCancelAwayVerifyUI(false);

        setLogString("Current thermostat name: " + currTstatName, true, CustomLogLevel.MEDIUM);
        Assert.assertFalse(awayPage.isPageLoaded());
        Assert.assertFalse(awayPage.waitForErrorMessage(BACK_FAIL_ERROR, ONE_MIN));

        for (int i = 0; i < noOfThermostats - 1; i++) {
            thPageOps.swipe(LEFT);
            currTstatName = thPageUI.getCurrentThermostatName();
            setLogString("Current thermostat name: " + currTstatName, true, CustomLogLevel.MEDIUM);
            Assert.assertFalse(awayPage.isPageLoaded());
            Assert.assertFalse(awayPage.waitForErrorMessage(BACK_FAIL_ERROR, ONE_MIN));
        }
    }

    /**
     * row#11 Ui changes onaway mode end.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, enabled = false, dataProvider = DEFAULT_USER, dataProviderClass = CommonsDataProvider.class, priority = 16)
    public void uiChangesOnawayModeEnd(final String userName, final String password) {

        loadPage(userName, password, true);
        checkIdleConditionsForThermostat();
        final String currTstatName = thPageUI.getCurrentThermostatName();
        final String currTstatMode = thPageUI.getCurrentMode();
        final Integer initialTarget = Integer.valueOf(thPageUI.getTargetTemperature());

        doSetAwayVerifyDBAndUI(userName, currTstatName);
        final Integer targetAtAway = Integer.valueOf(thPageUI.getTargetTemperature());
        Assert.assertFalse(targetAtAway.equals(initialTarget),
                "Target temperature was not applied correctly.");

        doCancelAwayVerifyUI(false);
        final Integer targetAtImBack = Integer.valueOf(thPageUI.getTargetTemperature());

        setLogString("Verify if target temperature is reverted to previous state after i'm back.",
                true, CustomLogLevel.MEDIUM);
        Assert.assertTrue(initialTarget.equals(targetAtImBack),
                "Target temperature not changed after im back.");

        Assert.assertTrue(thPageUI.isTargetTempDisplayed(), "Target temperature not displayed.");

        setLogString("Verify HVAC mode after i'm back.", true, CustomLogLevel.MEDIUM);
        Assert.assertTrue(thPageUI.getCurrentMode().contentEquals(currTstatMode),
                "HVAC mode for current interval not displayed.");
    }

    /**
     * row #13 Cancel away by clicking i am back.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, enabled = false, dataProvider = DEFAULT_USER, dataProviderClass = CommonsDataProvider.class, priority = 17)
    public void cancelAway(final String userName, final String password) {

        loadPage(userName, password, true);
        checkIdleConditionsForThermostat();
        final String currTstatName = thPageUI.getCurrentThermostatName();
        doSetAwayVerifyDBAndUI(userName, currTstatName);
        doCancelAwayVerifyUI(false);
    }

    /**
     * row #66 Continuous Set/Cancel Away.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, enabled = false, dataProvider = DEFAULT_USER, dataProviderClass = CommonsDataProvider.class, priority = 18)
    public void continuousAwayUpdate(final String userName, final String password) {

        loadPage(userName, password, true);
        checkIdleConditionsForThermostat();
        final String currTstatName = thPageUI.getCurrentThermostatName();

        for (int i = 0; i < 3; i++) {
            doSetAwayVerifyDBAndUI(userName, currTstatName);
            doCancelAwayVerifyUI(false);
        }
    }

    /**
     * Row #29 On line off line tstat away.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, enabled = false, dataProvider = MULTI_TSTAT_ON_OFF, dataProviderClass = CommonsDataProvider.class, priority = 19)
    public void onLineOffLineTstatAway(final String userName, final String password) {

        loadPage(userName, password, true);
        resetAway(true);
        goToOnlineOfflineTstat(false, CustomLogLevel.LOW);
        setAway();
        setLogString("Check AWAY status for Online Thermostat in DB.", true, CustomLogLevel.LOW);
        thDBValidation.isTstatStatusAwayInDB(userName, thPageUI.getCurrentThermostatName(), true);
        setLogString("Verify Away is not set for Offline Thermostat.", true, CustomLogLevel.LOW);
        setLogString("Check Set Away Error message for Offline Thermostat.", true,
                CustomLogLevel.MEDIUM);
        Assert.assertTrue(awayPage.waitForErrorMessage(COULD_NOT_SET_AWAY, ONE_MIN),
                "Away Failed Message is not displayed.");
        goToOnlineOfflineTstat(true, CustomLogLevel.LOW);
        setLogString("Check Away popup is not displayed for Offline Thermostat.", true,
                CustomLogLevel.MEDIUM);
        Assert.assertFalse(awayPage.isPageLoaded(), "Away Page did not close in UI.");
        setLogString("Check AWAY status for Offline Thermostat in DB.", true, CustomLogLevel.LOW);
        thDBValidation.isTstatStatusAwayInDB(userName, thPageUI.getCurrentThermostatName(), false);
        goToOnlineOfflineTstat(false, CustomLogLevel.LOW);
        doCancelAwayVerifyUI(true);
        thDBValidation.isTstatStatusAwayInDB(userName, thPageUI.getCurrentThermostatName(), false);
    }

    /**
     * Row # 31 Away ui for multiplelocations.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, enabled = false, dataProvider = "multipleLocationDynamic", dataProviderClass = CommonsDataProvider.class, priority = 20)
    public void awayUIForMultiplelocations(final String userName, final String password) {

        loadPage(userName, password, true);
        checkIdleConditionsForThermostat();
        final int noOfLocations = thPageUI.getNoOfLocations();
        final int noOfThermostats = thPageUI.getNoOfThermostats();
        final String currentThermostatName = thPageUI.getCurrentThermostatName();
        final String awayLocation = thPageUI.getCurrentLocationName();
        String currentLocation = null;

        setLogString("No of Locations: " + noOfLocations, true);
        setLogString("No of thermostats: " + thPageUI.getNoOfThermostats(), true);
        setLogString(
                "Current Location/thermostat : " + currentLocation + "/"
                        + thPageUI.getCurrentThermostatName(), true);
        setLogString("Perform set away in location: " + awayLocation
                + " and verify if other locations are still active.", true);

        doSetAwayVerifyDBAndUI(userName, currentThermostatName);

        for (int i = 0; i < noOfThermostats; i++) {
            thPageOps.swipe(LEFT);
            smallWait();
            currentLocation = thPageUI.getCurrentLocationName();

            setLogString(
                    "Current Location/thermostat : " + currentLocation + "/"
                            + thPageUI.getCurrentThermostatName(), true);
            setLogString("Is Current thermostat location away: " + awayPage.isPageLoaded(), true);

            if (!currentLocation.contains(awayLocation)) {
                Assert.assertFalse(awayPage.isPageLoaded(),
                        "Away Page is applied to other locations also.");
            }
        }
    }

    /**
     * Menu click.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.ANDROID, Groups.IOS }, enabled = false, dataProvider = DEFAULT_USER, dataProviderClass = CommonsDataProvider.class, priority = 21)
    public void menuClick(final String userName, final String password) {

        loadPage(userName, password, true);

        if (!awayPage.isPageLoaded()) {
            thPageOps.setAway();
        }

        if (!menuPage.isPageLoaded()) {
            thPageOps.clickMenu();
        }
        Assert.assertTrue(menuPage.isPageLoaded(),
                "Error Loading Menu Page after clicking the menu icon");
        menuPage.clickThermostatMenuItem();
        Assert.assertTrue(thPageUI.isPageLoaded(), "Error Loading Thermostat Page");

    }

    /**
     * Target temp not clickable.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.ANDROID, Groups.IOS }, enabled = false, dataProvider = DEFAULT_USER, dataProviderClass = CommonsDataProvider.class, priority = 22)
    public void targetTempNotClickable(final String userName, final String password) {

        loadPage(userName, password, true);

        if (!awayPage.isPageLoaded()) {
            thPageOps.setAway();
        }

        thPageOps.openTstatController();

        Assert.assertTrue(awayPage.isPageLoaded(), PAGE_LOAD_ERROR);
        awayPage.clickAmBack();

        thPageOps.openTstatController();
        Assert.assertTrue(thCtrlOpsPage.isPageLoaded(), "Thermostat control page is not loaded");
        thCtrlOpsPage.closeThermostatControl();

    }

    /**
     * Nothing clickable.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.ANDROID, Groups.IOS }, enabled = false, dataProvider = DEFAULT_USER, dataProviderClass = CommonsDataProvider.class, priority = 23)
    public void nothingClickable(final String userName, final String password) {

        loadPage(userName, password, true);

        if (!awayPage.isPageLoaded()) {
            thPageOps.setAway();
        }
        thPageOps.openTstatController();
        thPageOps.clickCurrentTemp();
        thPageOps.clickSavings();

        Assert.assertTrue(awayPage.isPageLoaded(), PAGE_LOAD_ERROR);
        awayPage.clickAmBack();

    }

    /**
     * Savings pop up not accessible.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.ANDROID, Groups.IOS }, enabled = false, dataProvider = DEFAULT_USER, dataProviderClass = CommonsDataProvider.class, priority = 24)
    public void savingsPopUpNotAccessible(final String userName, final String password) {

        loadPage(userName, password, true);

        if (!awayPage.isPageLoaded()) {
            thPageOps.setAway();
        }
        thPageOps.clickSavings();

        Assert.assertTrue(awayPage.isPageLoaded(), PAGE_LOAD_ERROR);
        awayPage.clickAmBack();

    }

    /**
     * Row # 173 End set away only through im back.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, enabled = false, dataProvider = DEFAULT_USER, dataProviderClass = CommonsDataProvider.class, priority = 25)
    public void endSetAwayOnlyThroughImBack(final String userName, final String password) {

        loadPage(userName, password, true);
        checkIdleConditionsForThermostat();
        resetAway(false);
        setLogString("Do set away and verify 'set away' not ends on clicking set away again.",
                true, CustomLogLevel.MEDIUM);
        setAway();
        thPageOps.setAway();
        Assert.assertTrue(awayPage.isPageLoaded(), "Away Popup closed by clicking set away again.");
        setLogString(
                "Verify Set away, current temperature, target temperature and savings link are not clickable in away mode.",
                true, CustomLogLevel.MEDIUM);
        setLogString("Now do im back and check if set away ends.", true, CustomLogLevel.MEDIUM);
        doCancelAwayVerifyUI(false);
    }

    /**
     * row #132 Check AwaySettings disabled in Away Mode.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, enabled = false, dataProvider = DEFAULT_USER, dataProviderClass = CommonsDataProvider.class, priority = 26)
    public void checkAwaySettingsInAwayMode(final String userName, final String password) {

        loadPage(userName, password, true);
        checkIdleConditionsForThermostat();
        final String currTstatName = thPageUI.getCurrentThermostatName();
        doSetAwayVerifyDBAndUI(userName, currTstatName);
        thPageOps.clickMenu();
        awaySettingsOpsPage.clickAwaySettings();
        awaySettingsUIPage.isPageLoaded();
        Assert.assertTrue(awaySettingsUIPage.isAwaySettingsAlertDisplayed(), "Alert not displayed!");

    }

    /**
     * Row #21 Verify away settings temp.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, enabled = false, dataProvider = DEFAULT_USER, dataProviderClass = CommonsDataProvider.class, priority = 27)
    public void verifyAwaySettingsTemp(final String userName, final String password) {

        loadPage(userName, password, true);
        resetAway(false);
        final String mode = thPageUI.getCurrentMode();
        gotoAwaySettingsFromHome();
        final String settingsTemp = ("cool".equals(mode)) ? Integer.toString(awaySettingsUIPage
                .getPickerValue(SetAwayParams.Cooling)) : Integer.toString(awaySettingsUIPage
                .getPickerValue(SetAwayParams.Heating));
        gotoHomeFromAwaySettings();
        verifyTargetHasAwaySettings(userName, settingsTemp);
    }

    /**
     * Row#10 Navigate pages while in away.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.ANDROID, Groups.IOS }, enabled = false, dataProvider = "multipleLocationDynamic", dataProviderClass = CommonsDataProvider.class, priority = 28)
    public void navigatePagesWhileInAway(final String userName, final String password) {

        loadPage(userName, password, true);
        checkIdleConditionsForThermostat();
        setLogString("Do set away and check if thermostat-locations are swipable.", true,
                CustomLogLevel.MEDIUM);
        setAway();

        thPageOps.clickSavings();
        Assert.assertFalse(savingsPage.isPageLoaded(),
                "Savings page is loaded when away in progress.");

        final int noOfThermostats = thPageUI.getNoOfThermostats();
        final String firstThermostatName = thPageUI.getCurrentThermostatName();
        final String firstLocation = thPageUI.getCurrentLocationName();
        boolean locationsSwipable = false;

        for (int i = 0; i < noOfThermostats - 1; i++) {
            thPageOps.swipe(LEFT);
            final String currTstatName = thPageUI.getCurrentThermostatName();
            setLogString(
                    "Current Thermostat - Location: " + currTstatName + "-"
                            + thPageUI.getCurrentLocationName(), true, CustomLogLevel.MEDIUM);
            Assert.assertFalse(firstThermostatName.contentEquals(currTstatName),
                    "Unable to swipe thermostat pages during set away.");
            if (!firstLocation.contentEquals(thPageUI.getCurrentLocationName())) {
                locationsSwipable = true;
                break;
            }
        }
        Assert.assertTrue(locationsSwipable, "Locations are not swipable during set away!");
    }

    /**
     * APPS-66.Set Away After waiting for 5 minutes idle
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER }, dataProvider = DEFAULT_USER, dataProviderClass = CommonsDataProvider.class, priority = 29, enabled = false)
    public void setAwayAfterWait(final String userName, final String password) {

        loadPage(userName, password, true);
        thermostatPageOps.loadAwaySettingsPopup();
        Assert.assertTrue(awayPage.waitAndSetAway(),
                "Unable to set away within one hour limit after wait");

    }

    /**
     * APPS-67 Awaypopup closes on multi thermostat swipe.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, enabled = false, dataProvider = "multiThermostatUser", dataProviderClass = CommonsDataProvider.class, priority = 30)
    public void awaypopupClosesOnMultiThermostatSwipe(final String userName, final String password) {

        loadPage(userName, password, true);
        checkIdleConditionsForThermostat();
        resetAway(true);

        setLogString("Current Thermostat - Location : " + thPageUI.getCurrentThermostatName() + "-"
                + thPageUI.getCurrentLocationName()

        , true);

        Assert.assertTrue(thPageOps.loadAwaySettingsPopup(),
                "Away settings popup is not loaded in thermostat page.");

        thPageOps.swipe(LEFT);
        checkIfAwayPopUpNotLoaded();

        setLogString("Return to previous thermostat.", true);
        thPageOps.swipe(RIGHT);

        checkIfAwayPopUpNotLoaded();
    }

    /**
     * APPS-68 Awaypopup closes on multi locations swipe.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, enabled = false, dataProvider = "multipleLocationDynamic", dataProviderClass = CommonsDataProvider.class, priority = 31)
    public void awaypopupClosesOnMultiLocationSwipe(final String userName, final String password) {

        loadPage(userName, password, true);
        checkIdleConditionsForThermostat();
        resetAway(true);

        final int noOfThermostats = thPageUI.getNoOfThermostats();

        setLogString("Current Thermostat - Location : " + thPageUI.getCurrentThermostatName()
                + " - " + thPageUI.getCurrentLocationName(), true, CustomLogLevel.MEDIUM);

        Assert.assertTrue(thPageOps.loadAwaySettingsPopup(),
                "Away settings popup is not loaded in thermostat page.");

        setLogString("Away settings pop up is loaded.", true);

        thPageOps.swipe(LEFT);
        checkIfAwayPopUpNotLoaded();

        for (int thermostatCount = 1; thermostatCount < noOfThermostats - 1; thermostatCount++) {

            thPageOps.swipe(LEFT);
            checkIfAwayPopUpNotLoaded();
        }

        thPageOps.swipe(LEFT);

        checkIfAwayPopUpNotLoaded();
    }

    /**
     * APPS-2 Cancel away by mo.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, enabled = false, dataProvider = "multiThermostatUser", dataProviderClass = CommonsDataProvider.class, priority = 33)
    public void cancelAwayByMOMultiThermostat(final String userName, final String password) {

        loadPage(userName, password, true);
        checkIdleConditionsForThermostat();
        resetAway(true);

        Assert.assertTrue(thPageOps.loadAwaySettingsPopup(),
                "Away settings popup is not loaded in thermostat page.");

        final List<Integer> thermostatIds = thDbValidation.getThermostatListForUser(userName);
        final Thermostat thermostat = thDbValidation.getThermostatById(thermostatIds.get(0)
                .toString());

        awayPage.clickSetAwayButton();
        waitUntil(THIRTY_SECS);

        thDBValidation.verifyAlgorithmInAlgoControl(thermostat.getId(), -20);

        setLogString("Check if Thermostat program log table was updated for set away", true);
        thDBValidation.isProgramTypeActiveInProgramLog(thermostat.getId(), -20, true);

        thDbValidation.verifyProgramForAlgorithm(thermostat.getId(), -20);

        awayPage.clickAmBack();
        waitUntil(THIRTY_SECS);

        setLogString("Check if Thermostat program log table was updated for I'm Back.", true);
        thDBValidation.isProgramTypeActiveInProgramLog(thermostat.getId(), -20, false);

        setLogString("Thermostat program log table verified for Im back.", true);
    }

    /**
     * Check if away pop up not loaded.
     */
    private void checkIfAwayPopUpNotLoaded() {

        setLogString("Current thermostat - location : '" + thPageUI.getCurrentThermostatName()
                + "-" + thPageUI.getCurrentLocationName() + "'.", true);
        Assert.assertFalse(thPageUI.isAwaySettingsPopUpLoaded(),
                "Away setings pop up loaded in thermostat page.");
        setLogString("Away settings popup is not displayed on the current thermostat page.", true);
    }

    /**
     * Check settings is applied.
     * @param userName the user name
     * @param settingsTemp the settings temp
     */
    private void verifyTargetHasAwaySettings(final String userName, final String settingsTemp) {

        final String currTstatName = thPageUI.getCurrentThermostatName();
        doSetAwayVerifyDBAndUI(userName, currTstatName);
        final String targetTemp = thPageUI.getTargetTemperature();
        setLogString("Verify Away Settings Temperature is applied to Target temperature.", true);
        Assert.assertTrue(targetTemp.equals(settingsTemp),
                "The Away settings temperature is not applied.");
    }

    /**
     * Goto home from away settings.
     */
    private void gotoHomeFromAwaySettings() {

        setLogString("Go to Home page from Away Settings page.", true, CustomLogLevel.MEDIUM);
        awaySettingsOpsPage.clickBack();
        awaySettingsOpsPage.clickMenuIcon();
        Assert.assertTrue(menuPage.isPageLoaded(), "Menu Page is not loaded.");
        menuPage.clickThermostatMenuItem();
        Assert.assertTrue(thPageUI.isPageLoaded(), "Thermostat Page is not loaded.");
    }

    /**
     * Goto away settings from home.
     */
    private void gotoAwaySettingsFromHome() {

        setLogString("Go to Away Settings page from Home page.", true, CustomLogLevel.MEDIUM);
        menuPage.clickMenuIcon();
        Assert.assertTrue(menuPage.isPageLoaded(), "Menu Page is not loaded.");
        menuPage.clickAwayTemperature();
        // awaySettingsOpsPage.clickAwaySettings();
        Assert.assertTrue(awaySettingsUIPage.isPageLoaded(), "Away Settings Page is not loaded.");
    }

    /**
     * Manipulated value.
     * @param changeSetting the change setting
     * @param lowValue the low value
     * @param highValue the high value
     * @return the int
     */
    private int manipulatedValue(final int changeSetting, final int lowValue, final int highValue) {

        final int deltaLow = changeSetting - lowValue;
        final int deltaHigh = highValue - changeSetting;
        final int valueToSet = deltaLow >= deltaHigh ? lowValue + 5 : highValue - 5;
        return valueToSet;
    }

    /**
     * Go to online offline tstat.
     * @param isOffline the is offline
     * @param logLevel the log level
     */
    private void goToOnlineOfflineTstat(final boolean isOffline, final CustomLogLevel logLevel) {

        setLogString("Navigate to " + (isOffline ? "Offline" : "Online") + " Thermostat.", true,
                logLevel);
        for (int i = 0; i < thPageUI.getNoOfThermostats(); i++) {
            if (thPageUI.isThermostatOffline() == isOffline) {
                break;
            }
            thPageOps.swipe(LEFT);
        }
    }

    /**
     * Do set away verify db and ui.
     * @param userName the user name
     * @param currTstatName the curr tstat name
     */
    private void doSetAwayVerifyDBAndUI(final String userName, final String currTstatName) {

        setAway();
        awayPage.displayActualSetAwayInfo();
        Assert.assertFalse(awayPage.waitForErrorMessage(AWAY_FAIL_ERROR, ONE_MIN),
                "Away Failed Message is displayed.");
        thDBValidation.isTstatStatusAwayInDB(userName, currTstatName, true);
    }

    /**
     * Do cancel away verify ui.
     * @param isMultiTstat the is multi tstat
     */
    private void doCancelAwayVerifyUI(final Boolean isMultiTstat) {

        awayPage.clickAmBack();
        Assert.assertFalse(awayPage.isPageLoaded(), "Away Page did not close in UI.");
        if (!isMultiTstat) {
            Assert.assertFalse(awayPage.waitForErrorMessage(BACK_FAIL_ERROR, ONE_MIN));
        }
    }

    /**
     * Reset away.
     * @param isMultiTstat the is multi tstat
     */
    @TestPrerequisite
    public void resetAway(final Boolean isMultiTstat) {

        if (awaySettingsUIPage.isPageLoaded()) {
            awaySettingsOpsPage.clickMenuIcon();
        }
        if (menuPage.isPageLoaded()) {
            menuPage.clickThermostatMenuItem();
        }
        if (awayPage.isPageLoaded()) {
            awayPage.clickAmBack();
        }
        if (!isMultiTstat) {
            Assert.assertFalse(awayPage.waitForErrorMessage(BACK_FAIL_ERROR, ONE_MIN));
        }
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

        if (thPageUI.isAwaySettingsPopUpLoaded()) {
            awayPage.closeAwaySettingsPopup();
        }

        thPageUI.isPageLoaded();

        if (thPageUI.isModeOff()) {
            thPageOps.turnSystemOn();
        }

    }

    /**
     * Set away.
     */
    public void setAway() {

        if (!awayPage.isPageLoaded()) {
            thPageOps.setAway();
        }
        Assert.assertTrue(awayPage.isPageLoaded(),
                "Away Popup is not Visible for " + thPageUI.getCurrentThermostatName());
    }
}
