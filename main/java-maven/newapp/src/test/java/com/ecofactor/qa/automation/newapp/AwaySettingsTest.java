/*
 * AwaySettingsTest.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.newapp;

import static com.ecofactor.qa.automation.platform.util.LogUtil.setLogString;

import java.util.Random;
import org.testng.Assert;
import org.testng.annotations.Guice;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import com.ecofactor.qa.automation.dao.DaoModule;
import com.ecofactor.qa.automation.newapp.data.CommonsDataProvider;
import com.ecofactor.qa.automation.newapp.enums.SetAwayParams;
import com.ecofactor.qa.automation.newapp.page.AwaySettingsOpsPage;
import com.ecofactor.qa.automation.newapp.page.AwaySettingsUIPage;
import com.ecofactor.qa.automation.newapp.page.MenuPage;
import com.ecofactor.qa.automation.newapp.page.TstatControlOpsPage;
import com.ecofactor.qa.automation.platform.annotation.TestPrerequisite;
import com.ecofactor.qa.automation.platform.constants.Groups;
import com.ecofactor.qa.automation.platform.enums.CustomLogLevel;
import com.ecofactor.qa.automation.platform.listener.AutomationTransformer;
import com.ecofactor.qa.automation.platform.listener.RetryEnabledListener;
import com.google.inject.Inject;

/**
 * The Class AwaySettingsTest.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
@Guice(modules = { MobileModule.class, DaoModule.class })
@Listeners({ RetryEnabledListener.class, AutomationTransformer.class })
public class AwaySettingsTest extends AbstractTest {

    private static final String DEFAULT_USER = "defaultUser";
    private final static String BACK_FAIL_ERROR = "Cancel away operation failed for thermostat";
    private final static String ERR_PAGE_LOAD = "Away settings page is not loaded.";
    private static final int ONE_MIN = 1;
    private static final String HEAT = "heat";
    private static final String LEFT = "left";

    @Inject
    private AwaySettingsUIPage awaySettingsUIPage;
    @Inject
    private AwaySettingsOpsPage awaySettingsOpsPage;
    @Inject
    private TstatControlOpsPage thCtrlOpsPage;
    @Inject
    private MenuPage menuPage;

    /**
     * row #143 Away duration in days and hours.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = DEFAULT_USER, dataProviderClass = CommonsDataProvider.class, priority = 1)
    public void awayDurationInDaysAndHours(final String userName, final String password) {

        loadPage(userName, password, true);
        checkIdleConditionsForThermostat();
        resetAway(false);
        thPageOps.clickMenu();
        awaySettingsOpsPage.clickAwaySettings();
        awaySettingsUIPage.isPageLoaded();
        setLogString("Verify if away settings duration is displayed in Days and Hours.", true,
                CustomLogLevel.MEDIUM);
        Assert.assertTrue(awaySettingsUIPage.isSetAwayParamLabelDisplayed(SetAwayParams.Days),
                "Away duration days is not displayed.");
        Assert.assertTrue(awaySettingsUIPage.isSetAwayParamLabelDisplayed(SetAwayParams.Hours),
                "Away duration hours is not displayed.");
        setLogString("Away settings duration is displayed in Days and Hours.", true,
                CustomLogLevel.MEDIUM);
    }

    /**
     * Row #158 Away settings ui.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = DEFAULT_USER, dataProviderClass = CommonsDataProvider.class, priority = 2)
    public void awaySettingsUI(final String userName, final String password) {

        loadPage(userName, password, true);
        checkIdleConditionsForThermostat();
        resetAway(false);
        thPageOps.clickMenu();
        Assert.assertTrue(menuPage.isPageLoaded());
        awaySettingsOpsPage.clickAwaySettings();
        Assert.assertTrue(awaySettingsUIPage.isPageLoaded(), "Settings page is not loaded.");
        setLogString("Away settings page is loaded.", true, CustomLogLevel.MEDIUM);
    }

    /**
     * Row #159 APP-216 Checks if is heating cooling and duration displayed.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = DEFAULT_USER, dataProviderClass = CommonsDataProvider.class, priority = 3)
    public void isHeatingCoolingAndDurationDisplayed(final String userName, final String password) {

        loadPage(userName, password, true);
        checkIdleConditionsForThermostat();
        resetAway(false);

        thPageOps.clickMenu();
        Assert.assertTrue(menuPage.isPageLoaded());
        awaySettingsOpsPage.clickAwaySettings();
        Assert.assertTrue(awaySettingsUIPage.isPageLoaded(), ERR_PAGE_LOAD);
        setLogString("Verify if Heating label is displayed.", true, CustomLogLevel.MEDIUM);
        Assert.assertTrue(awaySettingsUIPage.isSetAwayParamLabelDisplayed(SetAwayParams.Heating),
                "Heating label is not displayed in away settings page.");
        setLogString("Heating label is displayed.", true, CustomLogLevel.MEDIUM);
        setLogString("Verify if Cooling label is displayed.", true, CustomLogLevel.MEDIUM);
        Assert.assertTrue(awaySettingsUIPage.isSetAwayParamLabelDisplayed(SetAwayParams.Cooling),
                "Cooling label is not displayed in away settings page.");
        setLogString("Cooling label is displayed.", true, CustomLogLevel.MEDIUM);
        setLogString("Verify if Duration label is displayed.", true, CustomLogLevel.MEDIUM);
        Assert.assertTrue(awaySettingsUIPage.isDurationLabelDisplayed(),
                "Duration label is not displayed in away settings page.");
        setLogString("Duration label is displayed.", true, CustomLogLevel.MEDIUM);
    }

    /**
     * Row#137 Heatto coolto and duration pickers.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = DEFAULT_USER, dataProviderClass = CommonsDataProvider.class, priority = 4)
    public void heattoCooltoAndDurationPickers(final String userName, final String password) {

        loadPage(userName, password, true);
        checkIdleConditionsForThermostat();
        resetAway(false);

        thPageOps.clickMenu();
        Assert.assertTrue(menuPage.isPageLoaded());
        awaySettingsOpsPage.clickAwaySettings();
        Assert.assertTrue(awaySettingsUIPage.isPageLoaded(), ERR_PAGE_LOAD);

        setLogString("Verify if Heat picker is available by clicking heat value.", true,
                CustomLogLevel.MEDIUM);
        awaySettingsOpsPage.clickAwayParamPicker(SetAwayParams.Heating);
        awaySettingsOpsPage.clickAwayParamPicker(SetAwayParams.Heating);
        setLogString("Heat picker is available.", true, CustomLogLevel.MEDIUM);

        setLogString("Verify if Cool picker is available by clicking cool value.", true,
                CustomLogLevel.MEDIUM);
        awaySettingsOpsPage.clickAwayParamPicker(SetAwayParams.Cooling);
        awaySettingsOpsPage.clickAwayParamPicker(SetAwayParams.Cooling);
        setLogString("Cool picker is available.", true, CustomLogLevel.MEDIUM);

        setLogString("Verify if Days picker is available by clicking Days value.", true,
                CustomLogLevel.MEDIUM);
        awaySettingsOpsPage.clickAwayParamPicker(SetAwayParams.Days);
        awaySettingsOpsPage.clickAwayParamPicker(SetAwayParams.Days);
        setLogString("Days picker is available.", true, CustomLogLevel.MEDIUM);

        setLogString("Verify if Hours picker is available by clicking Hours value.", true,
                CustomLogLevel.MEDIUM);
        awaySettingsOpsPage.clickAwayParamPicker(SetAwayParams.Hours);
        awaySettingsOpsPage.clickAwayParamPicker(SetAwayParams.Hours);
        setLogString("Hours picker is available.", true, CustomLogLevel.MEDIUM);
    }

    /**
     * Row#139 Heat cool duration pickers scrollable.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = DEFAULT_USER, dataProviderClass = CommonsDataProvider.class, priority = 5)
    public void heatCoolDurationPickersScrollable(final String userName, final String password) {

        loadPage(userName, password, true);
        checkIdleConditionsForThermostat();
        resetAway(false);

        thPageOps.clickMenu();
        Assert.assertTrue(menuPage.isPageLoaded());
        awaySettingsOpsPage.clickAwaySettings();
        Assert.assertTrue(awaySettingsUIPage.isPageLoaded(), ERR_PAGE_LOAD);

        setLogString("Verify if Heat picker is scrollable by changing heat value.", true,
                CustomLogLevel.MEDIUM);
        awaySettingsOpsPage.setAwayParamPicker(SetAwayParams.Heating, 84);
        setLogString("Heat picker is scrollable.", true, CustomLogLevel.MEDIUM);

        setLogString("Verify if cool picker is scrollable by changing cool value.", true,
                CustomLogLevel.MEDIUM);
        awaySettingsOpsPage.setAwayParamPicker(SetAwayParams.Cooling, 72);
        setLogString("Cool picker is scrollable.", true, CustomLogLevel.MEDIUM);

        setLogString("Verify if Days picker is scrollable by changing Days value.", true,
                CustomLogLevel.MEDIUM);
        awaySettingsOpsPage.setAwayParamPicker(SetAwayParams.Days, 72);
        setLogString("Days picker is scrollable.", true, CustomLogLevel.MEDIUM);

        setLogString("Verify if Hours picker is scrollable by changing Hours value.", true,
                CustomLogLevel.MEDIUM);
        awaySettingsOpsPage.setAwayParamPicker(SetAwayParams.Hours, 14);
        setLogString("Hours picker is scrollable.", true, CustomLogLevel.MEDIUM);
    }

    /**
     * Row #133 Max days99 max hours23.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = DEFAULT_USER, dataProviderClass = CommonsDataProvider.class, priority = 6)
    public void maxDays99MaxHours23(final String userName, final String password) {

        loadPage(userName, password, true);
        checkIdleConditionsForThermostat();
        resetAway(false);

        thPageOps.clickMenu();
        Assert.assertTrue(menuPage.isPageLoaded());
        awaySettingsOpsPage.clickAwaySettings();
        Assert.assertTrue(awaySettingsUIPage.isPageLoaded(), ERR_PAGE_LOAD);

        setLogString("Verify if maximum value for Days picker is 99.", true, CustomLogLevel.MEDIUM);

        Assert.assertFalse(awaySettingsOpsPage.setAwayParamPicker(SetAwayParams.Days, 100),
                "Able to set 100 for Days parameter.");
        setLogString("Unable to set value > 99 for Days picker in away settings.", true,
                CustomLogLevel.MEDIUM);

        setLogString("Verify if maximim value for hours is 23.", true, CustomLogLevel.MEDIUM);
        Assert.assertFalse(awaySettingsOpsPage.setAwayParamPicker(SetAwayParams.Hours, 24),
                "Able to set 24 for Days parameter.");
        setLogString("Unable to set value > 23 for Hours picker in away settings.", true,
                CustomLogLevel.MEDIUM);
    }

    /**
     * Row #129 Max Heat set away limit.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = DEFAULT_USER, dataProviderClass = CommonsDataProvider.class, priority = 7)
    public void maxHeatSetAwayPoint(final String userName, final String password) {

        loadPage(userName, password, true);
        checkIdleConditionsForThermostat();
        resetAway(false);

        thPageOps.clickMenu();
        Assert.assertTrue(menuPage.isPageLoaded());
        awaySettingsOpsPage.clickAwaySettings();
        Assert.assertTrue(awaySettingsUIPage.isPageLoaded(), ERR_PAGE_LOAD);

        setLogString(
                "Verify if maximum set away value for heat picker is 89 by setting the value to 90.",
                true, CustomLogLevel.MEDIUM);

        Assert.assertFalse(awaySettingsOpsPage.setAwayParamPicker(SetAwayParams.Days, 90),
                "Able to set value > 89 as set away value for Heat picker.");
        setLogString("Unable to set heat picker value to 90.", true, CustomLogLevel.MEDIUM);
    }

    /**
     * Row #129 Min Cool set away limit.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = DEFAULT_USER, dataProviderClass = CommonsDataProvider.class, priority = 8)
    public void minHeatSetAwayPoint(final String userName, final String password) {

        loadPage(userName, password, true);
        checkIdleConditionsForThermostat();
        resetAway(false);

        thPageOps.clickMenu();
        Assert.assertTrue(menuPage.isPageLoaded());
        awaySettingsOpsPage.clickAwaySettings();
        Assert.assertTrue(awaySettingsUIPage.isPageLoaded(), ERR_PAGE_LOAD);

        setLogString(
                "Verify if minimum set away value for heat picker is 45 by setting the value to 44.",
                true, CustomLogLevel.MEDIUM);

        Assert.assertFalse(awaySettingsOpsPage.setAwayParamPicker(SetAwayParams.Heating, 44),
                "Able to set value < 45 as set away value for Heat picker.");
        setLogString("Unable to set heat picker value to 44.", true, CustomLogLevel.MEDIUM);
    }

    /**
     * Row #130 Max Cool set away limit.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = DEFAULT_USER, dataProviderClass = CommonsDataProvider.class, priority = 9)
    public void maxCoolSetAwayPoint(final String userName, final String password) {

        loadPage(userName, password, true);
        checkIdleConditionsForThermostat();
        resetAway(false);

        thPageOps.clickMenu();
        Assert.assertTrue(menuPage.isPageLoaded());
        awaySettingsOpsPage.clickAwaySettings();
        Assert.assertTrue(awaySettingsUIPage.isPageLoaded(), ERR_PAGE_LOAD);

        setLogString(
                "Verify if maximum set away value for cool picker is 89 by setting the value to 90.",
                true, CustomLogLevel.MEDIUM);

        Assert.assertFalse(awaySettingsOpsPage.setAwayParamPicker(SetAwayParams.Cooling, 90),
                "Able to set value > 89 as set away value for Cool picker.");
        setLogString("Unable to set heat picker value to 90.", true, CustomLogLevel.MEDIUM);
    }

    /**
     * Row #130 Min Cool set away limit.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = DEFAULT_USER, dataProviderClass = CommonsDataProvider.class, priority = 10)
    public void minCoolSetAwayPoint(final String userName, final String password) {

        loadPage(userName, password, true);
        checkIdleConditionsForThermostat();
        resetAway(false);

        thPageOps.clickMenu();
        Assert.assertTrue(menuPage.isPageLoaded());
        awaySettingsOpsPage.clickAwaySettings();
        Assert.assertTrue(awaySettingsUIPage.isPageLoaded(), ERR_PAGE_LOAD);

        setLogString(
                "Verify if minimum set away value for cool picker is 65 by setting the value to 64.",
                true, CustomLogLevel.MEDIUM);

        Assert.assertFalse(awaySettingsOpsPage.setAwayParamPicker(SetAwayParams.Cooling, 64),
                "Able to set value < 65 as set away value for Cool picker.");

        setLogString("Unable to set cool picker value to 64.", true, CustomLogLevel.MEDIUM);
    }

    /**
     * Row # 152 Update and verify away settings.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = DEFAULT_USER, dataProviderClass = CommonsDataProvider.class, priority = 10)
    public void updateAndVerifyAwaySettings(final String userName, final String password) {

        loadPage(userName, password, true);
        checkIdleConditionsForThermostat();
        resetAway(false);

        setLogString("Step 1: Do set away.", true, CustomLogLevel.MEDIUM);
        thPageOps.setAway();

        setLogString("Step 2: Reset set away.", true, CustomLogLevel.MEDIUM);
        resetAway(false);

        setLogString("Step 3: Edit away settings.", true, CustomLogLevel.MEDIUM);

        thPageOps.clickMenu();
        Assert.assertTrue(menuPage.isPageLoaded());
        awaySettingsOpsPage.clickAwaySettings();
        Assert.assertTrue(awaySettingsUIPage.isPageLoaded(), ERR_PAGE_LOAD);

        int heating = awaySettingsUIPage.getPickerValue(SetAwayParams.Heating);
        int cooling = awaySettingsUIPage.getPickerValue(SetAwayParams.Cooling);

        final Random random = new Random();
        heating = random.nextInt(89 - 45) + 45;
        awaySettingsOpsPage.setAwayParamPicker(SetAwayParams.Heating, heating);
        cooling = random.nextInt(89 - 65) + 65;
        awaySettingsOpsPage.setAwayParamPicker(SetAwayParams.Cooling, cooling);

        setLogString("Step 4: Do set away and verify away starts with latest settings.", true,
                CustomLogLevel.MEDIUM);

        final String actualAwaySettings[] = doAwayReturnSettingsApplied();
        final String targetMode = actualAwaySettings[2].substring(0, 4);
        final String targetValue = actualAwaySettings[2].substring(5, 7);

        setLogString("Mode :" + targetMode + "; target temperature :" + targetValue, true,
                CustomLogLevel.MEDIUM);

        Assert.assertTrue(
                targetMode.equalsIgnoreCase(HEAT) ? (Integer.parseInt(targetValue) == heating)
                        : (Integer.parseInt(targetValue) == cooling),
                "Away failed to start with latest settings.");

        setLogString("Away was started with latest settings.", true, CustomLogLevel.MEDIUM);
    }

    /**
     * Row#14 Edit away settings parameters.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = DEFAULT_USER, dataProviderClass = CommonsDataProvider.class, priority = 5)
    public void editAwaySettingsParameters(final String userName, final String password) {

        loadPage(userName, password, true);
        checkIdleConditionsForThermostat();
        resetAway(false);

        thPageOps.clickMenu();
        Assert.assertTrue(menuPage.isPageLoaded());
        awaySettingsOpsPage.clickAwaySettings();
        Assert.assertTrue(awaySettingsUIPage.isPageLoaded(), ERR_PAGE_LOAD);

        final Random random = new Random();
        final int heating = random.nextInt(89 - 45) + 45;

        setLogString("Verify if able to update Heat picker.", true, CustomLogLevel.MEDIUM);
        awaySettingsOpsPage.setAwayParamPicker(SetAwayParams.Heating, heating);
        setLogString("Updated Heat picker.", true, CustomLogLevel.MEDIUM);

        final int cooling = random.nextInt(89 - 65) + 65;
        setLogString("Verify if able to update cool picker.", true, CustomLogLevel.MEDIUM);
        awaySettingsOpsPage.setAwayParamPicker(SetAwayParams.Cooling, cooling);
        setLogString("Updated Cool picker.", true, CustomLogLevel.MEDIUM);

        final int days = random.nextInt(99 - 1) + 1;
        setLogString("Verify if able to update Days picker.", true, CustomLogLevel.MEDIUM);
        awaySettingsOpsPage.setAwayParamPicker(SetAwayParams.Days, days);
        setLogString("Updated Days picker.", true, CustomLogLevel.MEDIUM);

        final int hours = random.nextInt(23 - 1) + 1;
        setLogString("Verify if able to update Hours picker.", true, CustomLogLevel.MEDIUM);
        awaySettingsOpsPage.setAwayParamPicker(SetAwayParams.Hours, hours);
        setLogString("Updated Hours picker.", true, CustomLogLevel.MEDIUM);
    }

    /**
     * Row # 67(Menu page) Swipe disabled in settingspage.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = DEFAULT_USER, dataProviderClass = CommonsDataProvider.class, priority = 10)
    public void swipeDisabledInSettingspage(final String userName, final String password) {

        loadPage(userName, password, true);
        checkIdleConditionsForThermostat();
        resetAway(false);

        thPageOps.clickMenu();
        Assert.assertTrue(menuPage.isPageLoaded());
        awaySettingsOpsPage.clickAwaySettings();
        Assert.assertTrue(awaySettingsUIPage.isPageLoaded(), ERR_PAGE_LOAD);
        setLogString("Do swipe left and check if settings page is collapsed.", true,
                CustomLogLevel.MEDIUM);
        awaySettingsOpsPage.swipePage(LEFT);

        Assert.assertTrue(awaySettingsUIPage.isPageLoaded(),
                "Able to swipe when away settings page is loaded.");
        setLogString("Swipe event is disabled when settings page is open.", true,
                CustomLogLevel.MEDIUM);
    }

    /**
     * Row # 69(Menu page) Update settings with thermostat disconnected.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "tstatOfflineUser", dataProviderClass = CommonsDataProvider.class, priority = 10)
    public void updateSettingsWithThermostatDisconnected(final String userName,
            final String password) {

        loadPage(userName, password, true);
        Assert.assertTrue(thPageUI.isThermostatOffline(),
                "Please provide user account with disconnected thermostat.");

        thPageOps.clickMenu();
        Assert.assertTrue(menuPage.isPageLoaded());
        awaySettingsOpsPage.clickAwaySettings();
        Assert.assertTrue(awaySettingsUIPage.isPageLoaded(), ERR_PAGE_LOAD);

        setLogString(
                "Verify that user should be able to make changes to the settings even if thermostat is disconnected.",
                true, CustomLogLevel.MEDIUM);

        final Random random = new Random();
        final int heating = random.nextInt(89 - 45) + 45;
        Assert.assertTrue(awaySettingsOpsPage.setAwayParamPicker(SetAwayParams.Heating, heating),
                "Unable to edit heat settings.");
        final int cooling = random.nextInt(89 - 65) + 65;
        Assert.assertTrue(awaySettingsOpsPage.setAwayParamPicker(SetAwayParams.Cooling, cooling),
                "Unable to edit cool settings.");

        setLogString("Edit update away settings was successful, while thermostat is disconnected.",
                true, CustomLogLevel.MEDIUM);

    }

    /**
     * Row # 70(Menu page) Update settings with thermostat in off mode.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = DEFAULT_USER, dataProviderClass = CommonsDataProvider.class, priority = 10)
    public void updateSettingsWithThermostatInOffMode(final String userName, final String password) {

        loadPage(userName, password, true);
        checkIdleConditionsForThermostat();
        resetAway(false);

        thPageOps.openTstatController();
        Assert.assertTrue(thCtrlOpsPage.isPageLoaded(), "Thermostat control page is not opened.");
        thCtrlOpsPage.changeToOff();

        thPageOps.clickMenu();
        Assert.assertTrue(menuPage.isPageLoaded());
        awaySettingsOpsPage.clickAwaySettings();
        Assert.assertTrue(awaySettingsUIPage.isPageLoaded(), ERR_PAGE_LOAD);

        setLogString(
                "Verify that user should be able to make changes to the settings if thermostat is in off mode.",
                true, CustomLogLevel.MEDIUM);

        final Random random = new Random();
        final int heating = random.nextInt(89 - 45) + 45;
        Assert.assertTrue(awaySettingsOpsPage.setAwayParamPicker(SetAwayParams.Heating, heating),
                "Unable to edit heat settings.");
        final int cooling = random.nextInt(89 - 65) + 65;
        Assert.assertTrue(awaySettingsOpsPage.setAwayParamPicker(SetAwayParams.Cooling, cooling),
                "Unable to edit cool settings.");

        setLogString("Edit update away settings was successful, while thermostat is in off mode.",
                true, CustomLogLevel.MEDIUM);

    }

    /**
     * Row#136 Away settings on thermostat page.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = DEFAULT_USER, dataProviderClass = CommonsDataProvider.class, priority = 10)
    public void awaySettingsOnThermostatPage(final String userName, final String password) {

        loadPage(userName, password, true);
        checkIdleConditionsForThermostat();
        resetAway(false);

        Assert.assertTrue(thPageOps.loadAwaySettingsPopup(),
                "Away settings page not loaded from thermostat page.");

        setLogString("Away settings page is loaded.", true, CustomLogLevel.MEDIUM);
    }

    /**
     * Do away return settings applied.
     * @return the string[]
     */
    private String[] doAwayReturnSettingsApplied() {

        awaySettingsOpsPage.clickMenuIcon();
        Assert.assertTrue(menuPage.isPageLoaded());
        awaySettingsOpsPage.clickThermostat();
        Assert.assertTrue(thPageUI.isPageLoaded(), "Thermostat page not loaded.");
        thPageOps.setAway();
        return awayPage.getActualAwaySettings();
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

        thPageUI.isPageLoaded();

        if (thPageUI.isModeOff()) {
            thPageOps.turnSystemOn();
        }

    }

    /**
     * Reset away.
     * @param isMultiTstat the is multi tstat
     */
    @TestPrerequisite
    public void resetAway(final Boolean isMultiTstat) {

        if (awayPage.isPageLoaded()) {
            awayPage.clickAmBack();
        }
        Assert.assertFalse(awayPage.isPageLoaded(), "Away Page did not close in UI.");
        if (!isMultiTstat) {
            Assert.assertFalse(awayPage.waitForErrorMessage(BACK_FAIL_ERROR, ONE_MIN));
        }
    }
}
