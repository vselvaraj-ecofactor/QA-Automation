/*
 * SettingsTest.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.newapp;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Guice;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.ecofactor.qa.automation.dao.DaoModule;
import com.ecofactor.qa.automation.newapp.data.CommonsDataProvider;
import com.ecofactor.qa.automation.newapp.page.MenuPage;
import com.ecofactor.qa.automation.newapp.page.SettingsPage;
import com.ecofactor.qa.automation.newapp.page.ThermostatPageUI;
import com.ecofactor.qa.automation.newapp.page.validate.db.TstatDBValidation;
import com.ecofactor.qa.automation.platform.annotation.TestPrerequisite;
import com.ecofactor.qa.automation.platform.constants.Groups;
import com.ecofactor.qa.automation.platform.exception.DeviceException;
import com.ecofactor.qa.automation.platform.listener.AutomationTransformer;
import com.ecofactor.qa.automation.platform.listener.RetryEnabledListener;
import com.ecofactor.qa.automation.platform.util.LogUtil;
import com.ecofactor.qa.automation.util.DriverConfig;
import com.ecofactor.qa.automation.util.WaitUtil;
import com.google.inject.Inject;

/**
 * The Class SettingsTest.
 */
@Guice(modules = { MobileModule.class, DaoModule.class })
@Listeners({AutomationTransformer.class })
public class SettingsTest extends AbstractTest {

    /** The menu page. */
    @Inject
    private MenuPage menuPage;

    /** The settings page. */
    @Inject
    private SettingsPage settingsPage;

    /** The th page ui. */
    @Inject
    protected ThermostatPageUI thPageUI;

    /** The driver config. */
    @Inject
    private DriverConfig driverConfig;

    /** The th db validation. */
    @Inject
    private TstatDBValidation thDbValidation;

    /** The Constant PRIVACY_LINK. */
    @SuppressWarnings("unused")
    private static final String PRIVACY_LINK = "https://my.ecofactor.com/consumer/privacy.html";

    /** The Constant USER_AGREEMENT_LINK. */
    @SuppressWarnings("unused")
    private static final String USER_AGREEMENT_LINK = "https://my.ecofactor.com/consumer/useragreement.html";

    /** The Constant CURRENT_PASSWORD. */
    private static final String CURRENT_PASSWORD = "ecofacto";

    /** The Constant CORRECT_CURRENT_PASSWORD. */
    private static final String CORRECT_CURRENT_PASSWORD = "ecofactor";

    /** The Constant NEW_PASSWORD. */
    private static final String NEW_PASSWORD = "ecofact";

    /** The Constant REPEAT_PASSWORD. */
    private static final String REPEAT_PASSWORD = "ecofact";

    /** The Constant CORRECT_PASSWORD. */
    private static final String CORRECT_PASSWORD = "ecofactor1";

    /** The Constant ERROR_MESSAGE. */
    private static final String ERROR_MESSAGE = "Errors occurred while changing password.";

    /** The Constant PASSWORD_CREATION_MESSAGE. */
    private static final String PASSWORD_CREATION_MESSAGE = "Password successfully changed.";

    /**
     * APPS-294 Check for accessing settings page.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "settingsPage", dataProviderClass = CommonsDataProvider.class, priority = 1)
    public void checkForAccessingSettingsPage(final String userName, final String password) {

        loadPage(userName, password, true);
        menuPage.clickMenuIcon();
        settingsPage.clickMenuSettings();
        Assert.assertTrue(settingsPage.isPageLoaded(), "Settings Page is not loaded");
    }

    /**
     * APPS-295 Check settings page for dual locations.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "dualLocation", dataProviderClass = CommonsDataProvider.class, priority = 2)
    public void checkSettingsPageForDualLocations(final String userName, final String password) {

        loadPage(userName, password, true);
        thPageOps.clickLocationSwitcher();
        LogUtil.setLogString("No Of Locations : " + thPageUI.getNoOfLocations().toString(), true);
        locPageOps.clickClose();
        thPageUI.getCurrentLocationName().trim();
        thPageUI.getCurrentThermostatName();
        menuPage.clickMenuIcon();
        settingsPage.clickMenuSettings();
        Assert.assertTrue(settingsPage.isPageLoaded(), "Settings Page is not loaded");
        settingsPage.getThermostatName();
    }

    /**
     * APPS-296 Check changes in dual locations.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "dualLocation", dataProviderClass = CommonsDataProvider.class, priority = 3)
    public void checkChangesInDualLocations(final String userName, final String password) {

        loadPage(userName, password, true);
        final String tstName = thPageUI.getCurrentThermostatName();
        getLocationAndTstNames();
        menuPage.clickMenuIcon();
        settingsPage.clickMenuSettings();
        Assert.assertTrue(settingsPage.isPageLoaded(), "Settings Page is not loaded");
        settingsPage.changeThermostatName();
        menuPage.clickThermostatMenuItem();
        final String changeTstName = thPageUI.getCurrentThermostatName();
        Assert.assertFalse(tstName.equalsIgnoreCase(changeTstName),
                "Thermostat Name has not been changed");
    }

    /**
     * APPS-308 Change thermostat name.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "settingsPage", dataProviderClass = CommonsDataProvider.class, priority = 4)
    public void changeThermostatName(final String userName, final String password) {

        loadPage(userName, password, true);
        thPageUI.getCurrentThermostatName();
        menuPage.clickMenuIcon();
        settingsPage.clickMenuSettings();
        Assert.assertTrue(settingsPage.isPageLoaded(), "Settings Page is not loaded");
        settingsPage.changeThermostatName();
        menuPage.clickThermostatMenuItem();
        thPageUI.getCurrentThermostatName();
    }

    /**
     * APPS-302. Close and launch app.
     * @param userName the user name
     * @param password the password
     * @throws DeviceException
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "settingsPage", dataProviderClass = CommonsDataProvider.class, priority = 5)
    public void closeAndLaunchAppWithSettingsPage(final String userName, final String password)
            throws DeviceException {

        loadPage(userName, password, true);
        resetAwayCheckModeOff();
        testOps.closeDeviceDriver();
        testOps.loadDeviceDriver();
        testOps.switchToWebView();
        Assert.assertTrue(thPageUI.isPageLoaded(), "Thermostat page loaded on return to the app");
        Assert.assertFalse(settingsPage.isPageLoaded(), "Settings page loaded on return to the app");
    }

    /**
     * APPS-311 Change sixty char thermostat name.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "settingsPage", dataProviderClass = CommonsDataProvider.class, priority = 6)
    public void changeSixtyCharThermostatName(final String userName, final String password) {

        loadPage(userName, password, true);
        thPageUI.getCurrentThermostatName();
        menuPage.clickMenuIcon();
        settingsPage.clickMenuSettings();
        Assert.assertTrue(settingsPage.isPageLoaded(), "Settings Page is not loaded");
        settingsPage.changeSixtyCharTstName();
        menuPage.clickThermostatMenuItem();
    }

    /**
     * APPS-320 Check for thermostat installed.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "provisionalStatus", dataProviderClass = CommonsDataProvider.class, priority = 7)
    public void checkForThermostatInstalled(final String userName, final String password) {

        loadPage(userName, password, true);
        thPageUI.getCurrentThermostatName();
        thDbValidation.verifyProvisionState(userName);
        Assert.assertTrue(thPageUI.isThermostatNotInstalled(),
                "Thermostat not installed message was missing in the thermostat page.");
    }

    /**
     * APPS-303 Check for no installation in location.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "provisionalStatus", dataProviderClass = CommonsDataProvider.class, priority = 8)
    public void checkForNoInstallationInLocation(final String userName, final String password) {

        loadPage(userName, password, true);
        thPageUI.getCurrentThermostatName();
        thPageUI.getCurrentLocationName().trim();
        Assert.assertTrue(thPageUI.isThermostatNotInstalled(),
                "Thermostat not installed message was missing in the thermostat page.");
    }

    /**
     * APPS-304 Verify tst sorting.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "locationSwitcherMultiTstat", dataProviderClass = CommonsDataProvider.class, priority = 9)
    public void verifyTstSorting(final String userName, final String password) {

        loadPage(userName, password, true);
        menuPage.clickMenuIcon();
        settingsPage.clickMenuSettings();
        Assert.assertTrue(settingsPage.isPageLoaded(), "Settings Page is not loaded");
        settingsPage.verifyTstNames();
    }

    /**
     * APPS-297 Verify faq section.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "settingsPage", dataProviderClass = CommonsDataProvider.class, priority = 10)
    public void verifyFAQSection(final String userName, final String password) {

        loadPage(userName, password, true);
        menuPage.clickMenuIcon();
        settingsPage.clickMenuSettings();
        Assert.assertTrue(settingsPage.isPageLoaded(), "Settings Page is not loaded");
        Assert.assertTrue(settingsPage.isFAQClickable(), "FAQ tab is not clickable");
    }

    /**
     * APPS-478 Change password tab.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "settingsPage", dataProviderClass = CommonsDataProvider.class, priority = 11)
    public void changePasswordTab(final String userName, final String password) {

        loadPage(userName, password, true);
        menuPage.clickMenuIcon();
        settingsPage.clickMenuSettings();
        Assert.assertTrue(settingsPage.isPageLoaded(), "Settings Page is not loaded");
        settingsPage.clickPasswordTab();
        settingsPage.checkChangePasswordTab();
    }

    /**
     * APPS-479 Check text fields in password tab.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "settingsPage", dataProviderClass = CommonsDataProvider.class, priority = 12)
    public void checkFieldsInPasswordTab(final String userName, final String password) {

        loadPage(userName, password, true);
        menuPage.clickMenuIcon();
        settingsPage.clickMenuSettings();
        Assert.assertTrue(settingsPage.isPageLoaded(), "Settings Page is not loaded");
        settingsPage.clickPasswordTab();
        Assert.assertTrue(settingsPage.VerifyFieldsInPasswordTab(),
                "Fields are not in a proper way");
        settingsPage.clickBack();
        Assert.assertTrue(settingsPage.isPageLoaded(), "Settings Page is not loaded");
    }

    /**
     * APPS-480 Check for incorrect password.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "settingsPage", dataProviderClass = CommonsDataProvider.class, priority = 13)
    public void checkForIncorrectPassword(final String userName, final String password) {

        loadPage(userName, password, true);
        menuPage.clickMenuIcon();
        settingsPage.clickMenuSettings();
        Assert.assertTrue(settingsPage.isPageLoaded(), "Settings Page is not loaded");
        settingsPage.clickPasswordTab();
        settingsPage.fillChangePasswordFields(CURRENT_PASSWORD, NEW_PASSWORD, REPEAT_PASSWORD);
        settingsPage.clickChangePasswordBtn();
        final String errorMessage = settingsPage.getToastErrorMessage();
        Assert.assertTrue(errorMessage.equalsIgnoreCase(ERROR_MESSAGE), "Error message is not same");
        settingsPage.clickBack();
        Assert.assertTrue(settingsPage.isPageLoaded(), "Settings Page is not loaded");
    }

    /**
     * APPS-493. Verify with old password.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "settingsPage", dataProviderClass = CommonsDataProvider.class, priority = 14)
    public void verifyWithOldPassword(final String userName, final String password) {

        loadPage(userName, password, true);
        menuPage.clickMenuIcon();
        settingsPage.clickMenuSettings();
        Assert.assertTrue(settingsPage.isPageLoaded(), "Settings Page is not loaded");
        settingsPage.clickPasswordTab();
        settingsPage.fillChangePasswordFields(CURRENT_PASSWORD, NEW_PASSWORD, REPEAT_PASSWORD);
        settingsPage.clickChangePasswordBtn();
        settingsPage.getPasswordRequirementErrorMsg();
        settingsPage.clickBack();
        Assert.assertTrue(settingsPage.isPageLoaded(), "Settings Page is not loaded");
    }

    /**
     * APPS-495. Verify passwd in db.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "passwordUser", dataProviderClass = CommonsDataProvider.class, priority = 15)
    public void verifyPasswdInDB(final String userName, final String password) {

        loadPage(userName, password, true);
        menuPage.clickMenuIcon();
        settingsPage.clickMenuSettings();
        Assert.assertTrue(settingsPage.isPageLoaded(), "Settings Page is not loaded");
        settingsPage.clickPasswordTab();
        settingsPage.fillChangePasswordFields(CORRECT_CURRENT_PASSWORD, CORRECT_PASSWORD,
                CORRECT_PASSWORD);
        settingsPage.clickChangePasswordBtn();
        Assert.assertTrue(
                PASSWORD_CREATION_MESSAGE.equalsIgnoreCase(settingsPage.getToastErrorMessage()),
                "password is not created");
        settingsPage.clickBack();
        Assert.assertTrue(settingsPage.isPasswordUpdated(userName, password, CORRECT_PASSWORD),
                "Password has not been updated");
        Assert.assertTrue(settingsPage.isPasswordUpdated(userName), "Password is not updated");
    }

    /**
     * APPS-494. Clear tokens in login.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "passwordUser", dataProviderClass = CommonsDataProvider.class, priority = 16)
    public void getLastUpdatedTime(final String userName, final String password) {

        loadPage(userName, password, true);
        menuPage.clickMenuIcon();
        settingsPage.clickMenuSettings();
        Assert.assertTrue(settingsPage.isPageLoaded(), "Settings Page is not loaded");
        settingsPage.clickPasswordTab();
        settingsPage.fillChangePasswordFields(CORRECT_CURRENT_PASSWORD, NEW_PASSWORD,
                REPEAT_PASSWORD);
        settingsPage.clickChangePasswordBtn();
        Assert.assertTrue(
                PASSWORD_CREATION_MESSAGE.equalsIgnoreCase(settingsPage.getToastErrorMessage()),
                "password is not created");
        settingsPage.clickBack();
        settingsPage.getLastUpdatedTime(userName);
        Assert.assertTrue(settingsPage.isPasswordUpdated(userName), "Password is not updated");
    }

    /**
     * APPS-325 Non nve user.
     * @param userName the user name/
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "settingsPage", dataProviderClass = CommonsDataProvider.class, priority = 17)
    public void nonNVEUserForLinks(final String userName, final String password) {

        loadPage(userName, password, true);
        menuPage.clickMenuIcon();
        settingsPage.clickMenuSettings();
        Assert.assertTrue(settingsPage.isPageLoaded(), "Settings Page is not loaded");
        Assert.assertTrue(settingsPage.isPrivacyLinkClickable(), "Privacy Link is not clickable");
        Assert.assertTrue(settingsPage.isUserAggrementClickable(),
                "User Agreement Link is not clickable");
    }

    /**
     * Reset away check mode off.
     */
    @TestPrerequisite
    public void resetAwayCheckModeOff() {

        if (awayPage.isPageLoaded()) {
            awayPage.clickAmBack();
        }

        if (!menuPage.isPageLoaded() && thPageUI.isModeOff()) {
            thPageOps.turnSystemOn();
        }

        if (!menuPage.isPageLoaded()) {

            menuPage.clickMenuIcon();
        }
        WaitUtil.smallWait();
        Assert.assertTrue(menuPage.isPageLoaded(),
                "Error Loading Menu Page after clicking the menu icon");
        settingsPage.clickMenuSettings();
        Assert.assertTrue(settingsPage.isPageLoaded(), "Settings Page is not loaded");
    }

    /**
     * Gets the location and tst names.
     * @return the location and tst names
     */
    private void getLocationAndTstNames() {

        thPageOps.clickLocationSwitcher();
        Integer noOfThermostat = thPageUI.getNoOfThermostats();
        LogUtil.setLogString("No Of Thermostats : " + noOfThermostat.toString(), true);
        LogUtil.setLogString("No Of Locations : " + thPageUI.getNoOfLocations().toString(), true);
        locPageOps.clickClose();
        Assert.assertTrue(noOfThermostat > 1, "There is no multiple thermostats");

        List<String> locationList = new ArrayList<>();
        List<String> thermostatList = new ArrayList<>();
        for (int i = 0; i < noOfThermostat; i++) {

            String locationName = thPageUI.getCurrentLocationName().trim();
            String thermostatName = thPageUI.getCurrentThermostatName();
            if (!locationList.contains(locationName)) {
                locationList.add(locationName);
            }
            if (!thermostatList.contains(thermostatName)) {
                thermostatList.add(thermostatName);
            }
            thPageOps.swipe("left");
        }
    }
    
    /**
     * Verify tstat name change toast.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "settingsPage", dataProviderClass = CommonsDataProvider.class, priority = 8)
    public void verifyTstatNameChangeToast(final String userName, final String password) {

        loadPage(userName, password, true);
        menuPage.clickMenuIcon();
        settingsPage.clickMenuSettings();
        Assert.assertTrue(settingsPage.isPageLoaded(), "Settings Page is not loaded");
        settingsPage.changeThermostatName();
        Assert.assertTrue(settingsPage.getToastErrorMessage() != null && settingsPage.getToastErrorMessage().contains("Thermostat name cannot be updated. Try again."),"Toast not appeared");
        
    }
    
    /**
     * Verify change password not match.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "settingsPage", dataProviderClass = CommonsDataProvider.class, priority = 8)
    public void verifyChangePasswordNotMatch(final String userName, final String password) {

        loadPage(userName, password, true);
        menuPage.clickMenuIcon();
        settingsPage.clickMenuSettings();
        Assert.assertTrue(settingsPage.isPageLoaded(), "Settings Page is not loaded");
        Assert.assertTrue(settingsPage.verifyPasswordTabDisplayed(),"Password Tab Not Displayed");
        settingsPage.clickPasswordTab();
        Assert.assertTrue(settingsPage.VerifyFieldsInPasswordTab(), "Password Fields not Displayed");
        settingsPage.fillChangePasswordFields(password, password+"1",password);
        settingsPage.clickChangePasswordBtn();
        Assert.assertTrue(settingsPage.getConfirmPwdErrorMsg() != null && settingsPage.getConfirmPwdErrorMsg().equalsIgnoreCase("Passwords must match"),"Error differs");
        
    }
    
    /**
     * Verify successfully change passwd.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "passwordUser", dataProviderClass = CommonsDataProvider.class, priority = 8)
    public void verifySuccessfullyChangePasswd(final String userName, final String password) {
       
        try {
            loadPage(userName, password, true);
            menuPage.clickMenuIcon();
            settingsPage.clickMenuSettings();
            Assert.assertTrue(settingsPage.isPageLoaded(), "Settings Page is not loaded");
            Assert.assertTrue(settingsPage.verifyPasswordTabDisplayed(),"Password Tab Not Displayed");
            settingsPage.clickPasswordTab();
            Assert.assertTrue(settingsPage.VerifyFieldsInPasswordTab(), "Password Fields not Displayed");
            settingsPage.fillChangePasswordFields(password, password+"1",password+"1");
            settingsPage.clickChangePasswordBtn();
            Assert.assertTrue(settingsPage.getToastErrorMessage() != null && settingsPage.getToastErrorMessage().equalsIgnoreCase("Password successfully changed"),"Toast not appeared");
        } finally {
                Assert.assertTrue(settingsPage.isPasswordUpdated(userName), "Password is not updated");
        }
    }
    
    /**
     * Verify password requirements.
     * @param userName the user name
     * @param password the password
     * @param diffPwds the diff pwds
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "diffPasswordCheck", dataProviderClass = CommonsDataProvider.class, priority = 8)
    public void verifyPasswordRequirements(final String userName, final String password, final List<String> diffPwds) {

        try {
            loadPage(userName, password, true);
            menuPage.clickMenuIcon();
            settingsPage.clickMenuSettings();
            Assert.assertTrue(settingsPage.isPageLoaded(), "Settings Page is not loaded");
            Assert.assertTrue(settingsPage.verifyPasswordTabDisplayed(), "Password Tab Not Displayed");
            settingsPage.clickPasswordTab();
            Assert.assertTrue(settingsPage.VerifyFieldsInPasswordTab(), "Password Fields not Displayed");
            for (int i = 0; i < diffPwds.size(); i++) {
                if (i == 0) {
                    settingsPage.fillChangePasswordFields(password, diffPwds.get(i), diffPwds.get(i));
                } else {
                    settingsPage.fillChangePasswordFields(diffPwds.get(i-1), diffPwds.get(i), diffPwds.get(i));
                }

                settingsPage.clickChangePasswordBtn();
                if(!diffPwds.get(i).equalsIgnoreCase("ecofactor")){
                    Assert.assertTrue(
                            settingsPage.getToastErrorMessage() != null
                                    && settingsPage.getToastErrorMessage().equalsIgnoreCase(
                                            "Password successfully changed"), "Toast not appeared");
                }else {
                    Assert.assertTrue(
                            settingsPage.getInvalidPwdErrorMsg() != null
                                    && settingsPage.getInvalidPwdErrorMsg().equalsIgnoreCase(
                                            "Password must be at least 8 characters long and contain both letters and numbers"), "Error Message differs");
                    settingsPage.clickBack();
                }
               
                settingsPage.clickPasswordTab();
            }
        }finally {
                Assert.assertTrue(settingsPage.isPasswordUpdated(userName), "Password is not updated");
        }

    }
    
    /**
     * Verify settings back btn.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "settingsPage", dataProviderClass = CommonsDataProvider.class, priority = 8)
    public void verifySettingsBackBtn(final String userName, final String password) {

        loadPage(userName, password, true);
        menuPage.clickMenuIcon();
        settingsPage.clickMenuSettings();
        Assert.assertTrue(settingsPage.isPageLoaded(), "Settings Page is not loaded");
        Assert.assertFalse(settingsPage.isBackBtnDisplayed(),"Button displayed");
        
    }
}
