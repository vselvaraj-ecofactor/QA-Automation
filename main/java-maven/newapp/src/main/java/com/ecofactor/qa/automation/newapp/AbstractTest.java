/*
 * AbstractTest.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.newapp;

import static com.ecofactor.qa.automation.platform.util.CheckJenkinsParamUtil.*;
import static com.ecofactor.qa.automation.platform.util.DeviceUtil.*;
import static com.ecofactor.qa.automation.util.WaitUtil.*;

import java.lang.reflect.Method;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.TreeMap;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.ecofactor.qa.automation.dao.util.DataUtil;
import com.ecofactor.qa.automation.newapp.page.HelpOverlayPage;
import com.ecofactor.qa.automation.newapp.page.LoginPage;
import com.ecofactor.qa.automation.newapp.page.MenuPage;
import com.ecofactor.qa.automation.newapp.page.SavingsPage;
import com.ecofactor.qa.automation.newapp.page.SetAwayPage;
import com.ecofactor.qa.automation.newapp.page.ThermostatPageOps;
import com.ecofactor.qa.automation.newapp.page.ThermostatPageUI;
import com.ecofactor.qa.automation.newapp.page.impl.LocationSwitcherOpsPageImpl;
import com.ecofactor.qa.automation.platform.action.UIAction;
import com.ecofactor.qa.automation.platform.enums.CustomLogLevel;
import com.ecofactor.qa.automation.platform.enums.LogSection;
import com.ecofactor.qa.automation.platform.enums.Marker;
import com.ecofactor.qa.automation.platform.exception.DeviceException;
import com.ecofactor.qa.automation.platform.ops.TestOperations;
import com.ecofactor.qa.automation.platform.util.FileUtil;
import com.ecofactor.qa.automation.platform.util.LogUtil;
import com.ecofactor.qa.automation.platform.util.SystemUtil;
import com.ecofactor.qa.automation.util.TestMethodDetails;
import com.ecofactor.qa.automation.util.WaitUtil;
import com.google.inject.Inject;

/**
 * The Class AbstractTest.
 * @author $Author: vprasannaa $
 * @version $Rev: 33583 $ $Date: 2015-02-06 09:44:13 +0530 (Fri, 06 Feb 2015) $
 */
public class AbstractTest {

    /** The mobile config. */
    @Inject
    protected MobileConfig mobileConfig;

    /** The log util. */
    @Inject
    protected LogUtil logUtil;

    /** The login page. */
    @Inject
    protected LoginPage loginPage;

    /** The away page. */
    @Inject
    protected SetAwayPage awayPage;

    /** The test ops. */
    @Inject
    protected TestOperations testOps;

    /** The ui action. */
    @Inject
    protected UIAction uiAction;

    /** The th page ops. */
    @Inject
    protected ThermostatPageOps thPageOps;

    /** The th page ui. */
    @Inject
    protected ThermostatPageUI thPageUI;

    /** The menu page. */
    @Inject
    protected MenuPage menuPage;

    /** The help page. */
    @Inject
    protected HelpOverlayPage helpPage;

    /** The loc page ops. */
    @Inject
    protected LocationSwitcherOpsPageImpl locPageOps;

    /** The savings page. */
    @Inject
    protected SavingsPage savingsPage;

    /**
     * The start time.
     */
    protected long startTime;

    /**
     * Sets the up.
     * @param context the new up
     * @throws DeviceException the device exception
     */
    @BeforeSuite(alwaysRun = true)
    public void setUp(final ITestContext context) throws DeviceException {

        LogUtil.setLogString(LogSection.START, "Setup ", true);
        smallWait();
        testOps.initialize();
        // Display the list of test methods to the console
        final ITestNGMethod[] allMethods = context.getAllTestMethods();
        LogUtil.setLogString("--------------------------------------------", true);
        LogUtil.setLogString("Automation Test methods (" + allMethods.length + ")", true);
        LogUtil.setLogString("--------------------------------------------", true);
        LogUtil.setLogString("", true);
        TreeMap<Integer, TestMethodDetails> testCaseMap = new TreeMap<>();
        for (ITestNGMethod testNGMethod : allMethods) {
            String testCaseId = logUtil.getTestCaseId(testNGMethod, testNGMethod.getMethodName());
            TestMethodDetails testNGDetails = new TestMethodDetails();
            testNGDetails.setTestCaseName(testNGMethod.getMethodName());
            testNGDetails.setTestLinkId(testCaseId);
            testCaseMap.put(testNGMethod.getPriority(), testNGDetails);
        }
        DataUtil.printTestCaseGrid(testCaseMap);

        try {
            testOps.cleanup();
            mediumWait();
            LogUtil.setLogString("Before Webview", true, CustomLogLevel.HIGH);
            testOps.switchToWebView();
            LogUtil.setLogString("After Webview", true, CustomLogLevel.HIGH);
            uiAction.rejectAlert();
        } catch (Exception e) {
            e.printStackTrace();
            if (SystemUtil.isMac()) {
                LogUtil.setLogString("Retrying", true);
                testOps.cleanup();
                largeWait();
                testOps.switchToWebView();
            } else {
                LogUtil.setLogString("\033[41;1mERROR  (" + e.getMessage() + ")", true);
                throw new DeviceException("ERROR  (" + e.getMessage() + ")");
            }
        }
        LogUtil.setLogString(LogSection.END, "Setup Completed", true);

        if (System.getProperty("buildVersion") != null
                && !System.getProperty("buildVersion").isEmpty()) {
            LogUtil.setHeading("APP VERSION  : " + System.getProperty("buildVersion"), true);
            LogUtil.setHeading("BUILD NUMBER : " + System.getProperty("buildCode"), true);
        }
    }

    /**
     * Before method.
     * @param method the method
     * @param param the param
     */
    @BeforeMethod(alwaysRun = true)
    public void beforeMethod(final Method method, final Object[] param) {

        final String deviceId = getDeviceIdParam();
        logUtil.logStart(method, param, deviceId);
        startTime = System.currentTimeMillis();

        if (hasJenkinsSaveAllScreensParam() && uiAction.getCustomScreenshotFolder() != null) {
            final Path customFolder = Paths.get(System.getProperty("user.dir"),
                    uiAction.getCustomScreenshotFolder(), "screenshots", method.getName());

            if (customFolder.toFile().exists()) {
                FileUtil.deleteFolder(customFolder.toFile());
            }
        }
    }

    /**
     * Tear down.
     * @param method the method
     */
    @AfterMethod(alwaysRun = true)
    public void tearDown(final Method method) {

        final long duration = (System.currentTimeMillis() - startTime) / 1000;
        logUtil.logEnd(method, duration);
    }

    /**
     * After suite.
     */
    @AfterSuite(alwaysRun = true)
    public void afterSuite() {

        testOps.close();
        uiAction.clearCustomScreenshotFolder();
        smallWait();
    }

    /**
     * Do logout.
     */
    protected void doLogout() {

        if (!menuPage.isPageLoaded()) {
            if (savingsPage.isPageLoaded()) {
                savingsPage.clickMenu();
            } else {
                menuPage.clickMenuIcon();
            }
        }
        Assert.assertTrue(menuPage.isPageLoaded(), "Menu Page is not displayed");
        menuPage.clickLogoutMenuItem();

        if (!loginPage.isPageLoaded()) {
            LogUtil.setLogString(Marker.START, "Verify the page is redirected to Login Screen",
                    true);
            thPageUI.isPageLoaded();
            if (!menuPage.isPageLoaded()) {
                thPageOps.clickMenu();
            }
            Assert.assertTrue(menuPage.isPageLoaded(), "Menu Page is not displayed");
            menuPage.clickLogoutMenuItem();
            LogUtil.setLogString(Marker.END, "Completed", true);
        } else {
            loginPage.setLoggedIn(false);
        }
        Assert.assertTrue(loginPage.isPageLoaded(), "Login Page is not loaded");
    }

    /**
     * Load page.
     * @param userName the user name
     * @param password the password
     * @param doAssert the do assert
     * @param retainHelpPage the retain help page
     */
    protected void loadPage(final String userName, final String password, final boolean doAssert,
            final boolean... retainHelpPage) {

        LogUtil.setLogString(LogSection.START, "Prerequisite Starts", true);
        login(userName, password, doAssert, retainHelpPage.length > 0 ? retainHelpPage[0] : false);
        // if(hasJenkinsBrowserParam())
        // uiAction.clickTryAgain();
        LogUtil.setLogString(LogSection.END, "Prerequisite Ends", true);
    }

    /**
     * Login.
     * @param userName the user name
     * @param password the password
     * @param doAssert the do assert
     * @param retainHelpPage the retain help page
     */
    private void login(final String userName, final String password, final boolean doAssert,
            final boolean retainHelpPage) {

        if (!loginPage.isLoggedIn()
                || (loginPage.isLoggedIn() && !loginPage.getLoggedInUser().equalsIgnoreCase(
                        userName))) {
            doLogin(userName, password, doAssert, retainHelpPage);
        }
        checkForHelpPage(retainHelpPage);
    }

    /**
     * Do login.
     * @param userName the user name
     * @param password the password
     * @param doAssert the do assert
     * @param retainHelpPage the retain help page
     */
    private void doLogin(final String userName, final String password, final boolean doAssert,
            final boolean retainHelpPage) {

        if (loginPage.isLoggedIn()) {
            doLogout();
        } else {
            loginPage.isPageLoaded();
        }

        System.setProperty("userName", userName);
        System.setProperty("password", password);

       /* testOps.switchToNative();
        testOps.switchToWebView();
        testOps.switchToNative();
        testOps.switchToWebView();*/
        loginPage.login(userName, password);

        if (doAssert) {

            if (!loginPage.isLoggedIn()) {
                LogUtil.setLogString(Marker.START, "Click again the Login button", true);
                loginPage.clickLogin(userName, password);
                LogUtil.setLogString(Marker.END, "Completed", true);
            }

            Assert.assertTrue(loginPage.isLoggedIn(), "Login Failed");
        }
    }

    /**
     * Check for help page.
     * @param retainHelpPage the retain help page
     */
    private void checkForHelpPage(final boolean retainHelpPage) {

        if (loginPage.isLoggedIn()) {
            LogUtil.setLogString("Logged In User: " + loginPage.getLoggedInUser(), true);
            if (!retainHelpPage) {
                helpPage.goToThermostatPage();
            }
        }
    }

    /**
     * Check for remember user name or mail.
     * @param userName the user name.
     * @param password the password.
     */
    protected void clickRememberMail(final String userName, final String password) {

        if (loginPage.isLoggedIn()) {
            doLogout();
        } else {
            loginPage.isPageLoaded();
        }
        loginPage.userNamePasswordFields(userName, password);
        loginPage.clickRememberEvent();
        loginPage.clickSubmit(userName);
        helpPage.goToThermostatPage();
        doLogout();
    }

    /**
     * verify either hyper links were working or not.
     */
    protected void verifyingLinks() {

        if (loginPage.isLoggedIn()) {
            doLogout();
        } else {
            loginPage.isPageLoaded();
        }

        loginPage.verifyLinks();
    }

    /**
     * verify forgot password page is loaded.
     * @param userName the user name.
     * @param password the password.
     */
    protected void forgotPage(final String userName, final String password) {

        if (loginPage.isLoggedIn()) {
            doLogout();
        } else {
            loginPage.isPageLoaded();
        }
        loginPage.userNamePasswordFields(userName, password);
        loginPage.clickNeedHelp();
        loginPage.clickBackButton();
    }

    /**
     * verify either back button is clicked or not.
     * @param userName the user name.
     * @param password the password.
     */
    protected void backButton(final String userName, final String password) {

        if (loginPage.isLoggedIn()) {
            doLogout();
        } else {
            loginPage.isPageLoaded();
        }
        loginPage.userNamePasswordFields(userName, password);
        loginPage.clickNeedHelp();
        loginPage.clickBackButton();
    }

    /**
     * Fetching the version name details.
     * @param userName the user name.
     * @param password the password.
     */
    protected void versionNameDetails(final String userName, final String password) {

        if (loginPage.isLoggedIn()) {
            doLogout();
        } else {
            loginPage.isPageLoaded();
        }
        loginPage.userNamePasswordFields(userName, password);
        LogUtil.setLogString("Current Version Name:" + loginPage.versionName(), true);
        loginPage.clickBackButton();
    }

    /**
     * Fetch password.
     * @param userName the user name.
     * @param password the password.
     */
    protected void forgotPagePassword(final String userName, final String password) {

        if (loginPage.isLoggedIn()) {
            doLogout();
        } else {
            loginPage.isPageLoaded();
        }
        loginPage.userNamePasswordFields(userName, password);
        loginPage.clickSendButton();
        WaitUtil.tinyWait();
        final String newPassword = loginPage.fetchPassword(userName);
        LogUtil.setLogString("Current Password:" + newPassword, true);
        loginPage.clickBackButton();
        if (loginPage.isPageLoaded()) {

            loadPage(userName, newPassword, true);
        }
    }

    /**
     * validating the dimensions of text boxes in input f.
     * @param userName the user name.
     * @param password the password.
     */
    protected void dimensions(final String userName, final String password) {

        if (loginPage.isLoggedIn()) {
            doLogout();
        } else {
            loginPage.isPageLoaded();
        }
        Assert.assertTrue(loginPage.validateTextBoxDimension(),
                "Text boxes are not in standard dimension");
    }

    /**
     * Verify either thermostat page loaded or not.
     */
    protected void goToThermostatPage() {

        LogUtil.setLogString("Check for Thermostat Page Loaded", true);
        thPageOps.clickMenu();
    }

    /**
     * Rename the thermostat name and updating.
     */
    protected void renameThermostatName() {

        LogUtil.setLogString("Rename the Thermostat Name", true);
        thPageOps.settingsOperations();
    }

}
