/*
 * LoginTest.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.newapp;

import static com.ecofactor.qa.automation.newapp.MobileConfig.*;

import org.testng.Assert;
import org.testng.annotations.Guice;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.ecofactor.common.pojo.User;
import com.ecofactor.qa.automation.dao.DaoModule;
import com.ecofactor.qa.automation.dao.EFUserDao;
import com.ecofactor.qa.automation.newapp.data.CommonsDataProvider;
import com.ecofactor.qa.automation.platform.constants.Groups;
import com.ecofactor.qa.automation.platform.exception.DeviceException;
import com.ecofactor.qa.automation.platform.listener.AutomationTransformer;
import com.ecofactor.qa.automation.platform.listener.RetryEnabledListener;
import com.ecofactor.qa.automation.platform.util.LogUtil;
import com.ecofactor.qa.automation.util.WaitUtil;
import com.google.inject.Inject;

/**
 * The Class LoginTest.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
@Guice(modules = { MobileModule.class, DaoModule.class })
@Listeners({ RetryEnabledListener.class, AutomationTransformer.class })
public class LoginTest extends AbstractTest {

    /** The Constant ERROR_MESSAGE. */
    private static final String ERROR_MESSAGE = "Invalid email or password";

    /** The Constant INVALID_ERROR_MESSAGE. */
    private static final String INVALID_ERROR_MESSAGE = "Invalid error message";

    /** The Constant LOGGED_OUT. */
    private static final String LOGGED_OUT = "Logged Out";

    /** The ef user dao. */
    @Inject
    private EFUserDao efUserDao;

    /**
     * APP-12 Valid login.
     * @param userName the user name
     * @param password the password
     */
    @Test(dataProvider = "validLogin", dataProviderClass = CommonsDataProvider.class, groups = {
            Groups.SANITY1, Groups.SANITY2, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, priority = 1)
    public void validLogin(final String userName, final String password) {

        loadPage(userName, password, true);
    }

    /**
     * APP-14 Invalid login.
     * @param userName the user name
     * @param password the password
     */
    @Test(dataProvider = "invalidUser", dataProviderClass = CommonsDataProvider.class, groups = {
            Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, priority = 2)
    public void invalidLogin(final String userName, final String password) {

        loadPage(userName, password, false);

        final String errorMsgInPortal = loginPage.getErrorMessage();
        Assert.assertTrue(errorMsgInPortal.trim().equalsIgnoreCase(ERROR_MESSAGE),
                "Error message differs");
    }

    /**
     * APP-15 Login with un registered user.
     * @param userName the user name
     * @param password the password
     */
    @Test(dataProvider = "unregisteredUser", dataProviderClass = CommonsDataProvider.class, groups = {
            Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, priority = 3)
    public void loginWithUnRegisteredUser(final String userName, final String password) {

        loadPage(userName, password, false);
        Assert.assertTrue(loginPage.getErrorMessage().equalsIgnoreCase(ERROR_MESSAGE),
                INVALID_ERROR_MESSAGE);
    }

    /**
     * APP-16 Blank user name and password.
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, priority = 4)
    public void blankUserNameAndPassword() {

        loadPage("", "", false);
        Assert.assertTrue(loginPage.getErrorMessage().equalsIgnoreCase(ERROR_MESSAGE),
                INVALID_ERROR_MESSAGE);
    }

    /**
     * * APP-17 Login with user id.
     * @param userName the user name
     * @param password the password
     */
    @Test(dataProvider = "validLogin", dataProviderClass = CommonsDataProvider.class, groups = {
            Groups.SANITY1, Groups.SANITY2, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, priority = 5)
    public void loginWithUserId(final String userName, final String password) {

        LogUtil.setLogString("Get userId for the User: " + userName, true);
        final User userId = efUserDao.findByUserName(userName);
        loadPage(String.valueOf(userId.getId()), password, false);
        Assert.assertTrue(loginPage.getErrorMessage().equalsIgnoreCase(ERROR_MESSAGE),
                INVALID_ERROR_MESSAGE);
    }

    /**
     * APP-18 Verify error message font and color.
     * @param userName the user name
     * @param password the password
     */
    @Test(dataProvider = "invalidUser", dataProviderClass = CommonsDataProvider.class, groups = {
            Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, priority = 6)
    public void verifyErrorMsgFontAndColor(final String userName, final String password) {

        loadPage(userName, password, false);
        Assert.assertTrue(loginPage.getErrorMessage().equalsIgnoreCase(ERROR_MESSAGE),
                INVALID_ERROR_MESSAGE);
        Assert.assertTrue(
                loginPage.getErrorTextColor().equalsIgnoreCase(mobileConfig.get(FONT_COLOR)),
                "Error message color has changed");
        String fontFamily = loginPage.getErrorTextFont();
        Assert.assertTrue(fontFamily.contains("Arial") || fontFamily.contains("sans-serif"),
                "Error message font has changed");
    }

    /**
     * APP-29 Skip password.
     * @param userName the user name
     * @param password the password
     */
    @Test(dataProvider = "validLogin", dataProviderClass = CommonsDataProvider.class, groups = {
            Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, priority = 7)
    public void skipPassword(final String userName, final String password) {

        loadPage(userName, "", false);
        Assert.assertTrue(loginPage.getErrorMessage().equalsIgnoreCase(ERROR_MESSAGE),
                INVALID_ERROR_MESSAGE);
    }

    /**
     * APP-30 Invalid password.
     * @param userName the user name
     * @param password the password
     */
    @Test(dataProvider = "invalidPassword", dataProviderClass = CommonsDataProvider.class, groups = {
            Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, priority = 8)
    public void invalidPasswordSeveralTimes(final String userName, final String password) {

        int i = 0;
        while (i < 3) {
            loadPage(userName, password, false);
            Assert.assertTrue(loginPage.getErrorMessage().equalsIgnoreCase(ERROR_MESSAGE),
                    INVALID_ERROR_MESSAGE);
            i++;
        }
    }

    /**
     * APP-31 User name with upper and lower case.
     * @param userName the user name
     * @param password the password
     */
    @Test(dataProvider = "validLogin", dataProviderClass = CommonsDataProvider.class, groups = {
            Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, priority = 9)
    public void userNameWithUpperAndLowerCase(final String userName, final String password) {

        loadPage(userName, password, true);
        thPageUI.isPageLoaded();
        doLogout();

        loadPage(userName.toUpperCase(), password, true);
        thPageUI.isPageLoaded();
    }

    /**
     * APP-36 User name with special characters.
     * @param userName the user name
     * @param password the password
     */
    @Test(dataProvider = "specialCharUserName", dataProviderClass = CommonsDataProvider.class, groups = {
            Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, priority = 10)
    public void userNameWithSpecialCharacters(String userName, String password) {

        loadPage(userName, password, false);
        Assert.assertTrue(loginPage.getErrorMessage().equalsIgnoreCase(ERROR_MESSAGE),
                INVALID_ERROR_MESSAGE);
    }

    /**
     * APP-37 User name with mixed case.
     * @param userName the user name
     * @param password the password
     */
    @Test(dataProvider = "validLogin", dataProviderClass = CommonsDataProvider.class, groups = {
            Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, priority = 11)
    public void userNameWithMixedCase(String userName, String password) {

        loadPage(changeCase(userName), password, true);
        thPageUI.isPageLoaded();
    }

    /**
     * APP-34 Restart mobile app.
     * @param userName the user name
     * @param password the password
     * @throws DeviceException the device exception
     */
    @Test(dataProvider = "validLogin", dataProviderClass = CommonsDataProvider.class, groups = {
            Groups.SANITY1, Groups.ANDROID, Groups.IOS }, priority = 12)
    public void restartMobileApp(final String userName, final String password)
            throws DeviceException {

        loadPage(userName, password, true);
        thPageUI.isPageLoaded();
        WaitUtil.largeWait();
        WaitUtil.mediumWait();
        testOps.closeDeviceDriver();
        testOps.loadDeviceDriver();
        WaitUtil.mediumWait();
        testOps.switchToWebView();
        WaitUtil.smallWait();
        uiAction.rejectAlert();
        Assert.assertTrue(thPageUI.isPageLoaded(), "Thermostat Page is not loaded");
    }

    /**
     * APP-28 Re install app.
     * @param userName the user name
     * @param password the password
     * @throws DeviceException the device exception
     */
    @Test(dataProvider = "validLogin", dataProviderClass = CommonsDataProvider.class, groups = {
            Groups.SANITY1, Groups.ANDROID, Groups.IOS }, priority = 13)
    public void reInstallApp(final String userName, final String password) throws DeviceException {

        loadPage(userName, password, true);
        loginPage.setLoggedIn(false);
        testOps.cleanup();
        WaitUtil.mediumWait();
        testOps.switchToWebView();
        WaitUtil.smallWait();
        uiAction.rejectAlert();
        loadPage(userName, password, true);
        thPageUI.isPageLoaded();
    }

    /**
     * APP-79 Logout actions.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "validLogin", dataProviderClass = CommonsDataProvider.class, priority = 14)
    public void logoutUser(final String userName, final String password) {

        loadPage(userName, password, true);
        thPageUI.isPageLoaded();
        doLogout();
        Assert.assertTrue(loginPage.isPageLoaded(), "Login Page Not Loaded");
    }

    /**
     * APP-81 Logout and Login actions.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "validLogin", dataProviderClass = CommonsDataProvider.class, priority = 15)
    public void logoutAndLogin(final String userName, final String password) {

        loadPage(userName, password, true);
        thPageUI.isPageLoaded();
        doLogout();
        loadPage(userName, password, true);
        Assert.assertTrue(thPageUI.isPageLoaded(), "Login Failed after Logout");
    }

    /*
     * Error message back ground color.
     * @param userName the user name
     * @param password the password
     */
    /**
     * Error message back ground color.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "invalidUser", dataProviderClass = CommonsDataProvider.class, priority = 16)
    public void errorMessageBackGroundColor(final String userName, final String password) {

        loadPage(userName, password, false);
        Assert.assertTrue(loginPage.getErrorMessage().equalsIgnoreCase(ERROR_MESSAGE),
                INVALID_ERROR_MESSAGE);
        Assert.assertTrue(
                loginPage.getErrorTextBackgroundColor().equalsIgnoreCase(
                        mobileConfig.get(ERROR_BGCOLOR)),
                "Error message back ground color has changed.");
    }

    /**
     * Error message in top of login box.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "validLogin", dataProviderClass = CommonsDataProvider.class, priority = 17)
    public void logoutMessageInTopOfLoginBox(final String userName, final String password) {

        loadPage(userName, password, false);
        thPageUI.isPageLoaded();
        doLogout();
        Assert.assertTrue(loginPage.getErrorMessage().equalsIgnoreCase(LOGGED_OUT),
                "'Logged out' message missing.");
        Assert.assertTrue(loginPage.isErrorInfoPositionTopOfLoginBox(),
                "'Logged out' info message is not found in top of login box.");
    }

    /**
     * Invalid credentials message in top of login box.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "invalidUser", dataProviderClass = CommonsDataProvider.class, priority = 18)
    public void invalidCredentialsMessageInTopOfLoginBox(final String userName,
            final String password) {

        loadPage(userName, password, false);
        Assert.assertTrue(loginPage.getErrorMessage().equalsIgnoreCase(ERROR_MESSAGE),
                INVALID_ERROR_MESSAGE);
        Assert.assertTrue(loginPage.isErrorInfoPositionTopOfLoginBox(),
                "error message is not found in top of login box.");
    }

    /**
     * Test to verify the background color of Login Page.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "validLogin", dataProviderClass = CommonsDataProvider.class, priority = 19)
    public void verifyLoginPageBackGround(final String userName, final String password) {

        loadPage(userName, password, true);
        thPageUI.isPageLoaded();
        doLogout();
        Assert.assertTrue(loginPage.isPageLoaded(), "Login page not loaded!");
        Assert.assertTrue(loginPage.isLoginBackgroundColorValid(), "Background color invalid");
    }

    /**
     * APP-10 User name with sixty char.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS, Groups.STUB }, dataProvider = "sixtyCharLogin", dataProviderClass = CommonsDataProvider.class, priority = 20)
    public void userNameWithSixtyChar(final String userName, final String password) {

        loadPage(userName, password, true);
    }

    /**
     * APP-218 Test to verify the dimension of user name and password text boxes.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "validLogin", dataProviderClass = CommonsDataProvider.class, priority = 21)
    public void verifyInputDimension(final String userName, final String password) {

        dimensions(userName, password);
    }

    /**
     * APP-261 Click remember mail button.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "validLogin", dataProviderClass = CommonsDataProvider.class, priority = 22)
    public void clickRememberMe(final String userName, final String password) {

        clickRememberMail(userName, password);
    }

    /**
     * APP-267 check for forgot password page.
     * @param userName the user name
     * @param password the password
     */

    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "validLogin", dataProviderClass = CommonsDataProvider.class, priority = 23)
    public void plusSignUserName(final String userName, final String password) {

        loadPage(plusSign(userName), password, true);
    }

    /**
     * APP-268 check for forgot password page.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "firstSpaceLast", dataProviderClass = CommonsDataProvider.class, priority = 24)
    public void firstNameSpaceLastName(final String userName, final String password) {

        loadPage(userName, password, false);
        Assert.assertTrue(loginPage.getErrorMessage().equalsIgnoreCase(ERROR_MESSAGE),
                INVALID_ERROR_MESSAGE);
        Assert.assertTrue(loginPage.isErrorInfoPositionTopOfLoginBox(),
                "error message is not found in top of login box.");
    }

    /**
     * APP-263 check for forgot password page.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "validLogin", dataProviderClass = CommonsDataProvider.class, priority = 25)
    public void forgotPasswordPage(final String userName, final String password) {

        forgotPage(userName, password);
    }

    /**
     * APP-265 check for back button is clicked.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "validLogin", dataProviderClass = CommonsDataProvider.class, priority = 26)
    public void backToLogin(final String userName, final String password) {

        backButton(userName, password);
    }

    /**
     * APP-269 check either user name is in userName field after logged out.
     * @param userName the user name.
     * @param password the password.
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS, Groups.STUB }, dataProvider = "validLogin", dataProviderClass = CommonsDataProvider.class, priority = 27)
    public void afterLoggedOut(final String userName, final String password) {

        loadPage(userName, password, true);
        doLogout();
    }

    /**
     * APP-264 check for fetching the version name.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "validLogin", dataProviderClass = CommonsDataProvider.class, priority = 28)
    public void versionName(final String userName, final String password) {

        versionNameDetails(userName, password);
    }

    /**
     * APP-266 check for space in leading and trailing of user name.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS }, dataProvider = "leadingtrailingusername", dataProviderClass = CommonsDataProvider.class, priority = 29)
    public void leadingTrailingUserName(final String userName, final String password) {

        loadPage(userName, password, true);
    }

    /**
     * APP-33 Verify hyper links.
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID, Groups.IOS, Groups.STUB }, priority = 30)
    public void verifyHyperLinks() {

        verifyingLinks();
    }

    /**
     * APP-262 check for retrieving the password from mail.
     * @param userName the user name
     * @param password the password
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER, Groups.ANDROID }, dataProvider = "forgotPassword", dataProviderClass = CommonsDataProvider.class, priority = 31)
    public void forgotPassword(final String userName, final String password) {

        forgotPagePassword(userName, password);
    }

    /**
     * Change case.
     * @param userName the user name
     * @return the string
     */
    private String changeCase(final String userName) {

        String newValue = "";
        int lengthOfvalue = userName.length();
        if (lengthOfvalue > 4) {
            String upperCase = userName.substring(0, 4).toUpperCase();
            String remainingText = userName.substring(4, lengthOfvalue).toLowerCase();
            newValue = upperCase + remainingText;
        }
        return newValue;
    }

    /**
     * Plus sign.
     * @param userName the user name
     * @return the string
     */
    private String plusSign(final String userName) {

        if (userName.contains("+")) {
            return userName;
        } else
            return null;
    }
}
