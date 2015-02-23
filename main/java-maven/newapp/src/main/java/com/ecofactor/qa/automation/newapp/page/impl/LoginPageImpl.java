/*
 * LoginPageImpl.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.newapp.page.impl;

import static com.ecofactor.qa.automation.newapp.MobileConfig.GMAIL_URL;
import static com.ecofactor.qa.automation.platform.enums.CustomTimeout.*;
import static com.ecofactor.qa.automation.platform.util.Pageutil.*;

import org.joda.time.LocalTime;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ecofactor.qa.automation.newapp.MobileConfig;
import com.ecofactor.qa.automation.newapp.page.LoginPage;
import com.ecofactor.qa.automation.platform.enums.CustomLogLevel;
import com.ecofactor.qa.automation.platform.enums.LogSection;
import com.ecofactor.qa.automation.platform.util.LogUtil;

import com.ecofactor.qa.automation.util.DateUtil;
import com.ecofactor.qa.automation.util.WaitUtil;
import com.ecofactor.qa.automation.util.mail.Gmail;
import com.google.inject.Inject;

/**
 * The Class LoginPageImpl.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class LoginPageImpl extends AbstractBasePageImpl implements LoginPage {

    /** The Constant PASSWORD. */
    private static final String PASSWORD = "password";

    /** The Constant USERNAME. */
    private static final String USERNAME = "username";

    /** The Constant LOGIN_SUBMIT. */
    private static final String LOGIN_SUBMIT = "login_submit";

    /** The Constant LOGIN_RESPONSE. */
    private static final String LOGIN_RESPONSE = "login_response_text";

    /** The Constant FONT_COLOR. */
    private static final String FONT_COLOR = "color";

    /** The Constant FONT_FAMILY. */
    private static final String FONT_FAMILY = "font-family";

    /** The Constant LOADING_MESSAGE. */
    private static final String LOADING_MESSAGE = "loadingMessage";

    /** The Constant GREY_COLOR. */
    private static final String GREY_COLOR = "rgb(61, 72, 81)";

    /** The Constant LOGIN_VIEW. */
    private static final String LOGIN_VIEW = "LoginView";

    /** The Constant BACKGROUND_COLOR. */
    private static final String BACKGROUND_COLOR = "background-color";

    /** The Constant TRUE. */
    private static final String TRUE = "true";

    /** The Constant AUTOFOCUS. */
    private static final String AUTOFOCUS = "autofocus";

    /** The Constant LOGIN_FORM. */
    private static final String LOGIN_FORM = "loginForm";

    /** The Constant LOGIN_RESP_BOX. */
    private static final String LOGIN_RESP_BOX = "login_response_box";

    /** The Constant HEIGHT. */
    private static final String HEIGHT = "height";

    /** The Constant WIDTH. */
    private static final String WIDTH = "width";

    /** The Constant HEIGHT_VALUE. */
    private static final String HEIGHT_VALUE = "35px";

    /** The Constant WIDTH_VALUE. */
    private static final String WIDTH_VALUE = "217px";

    /** The Constant CHECKBOX_NAME. */
    private static final String CHECKBOX_NAME = "rememberUser";

    /** The Constant NEED_HELP_LINK. */
    private static final String NEED_HELP_LINK = "div.login_link.login_help_link";

    /** The Constant BACK_BUTTON. */
    private static final String BACK_BUTTON = "div.clickable.closeResetPasswordButton";

    /** The Constant PRIVACYLINK. */
    private static final String PRIVACYLINK = "Privacy & Terms";

    /** The Constant VERSION_NAME. */
    private static final String VERSION_NAME = "version";

    /** The Constant SEND_SUBMIT. */
    private static final String SEND_SUBMIT = "submit";

    /** The Constant G_PASSWORD. */
    private static final String G_PASSWORD = "Axims0ft123";

    /** The Constant G_SUBJECT. */
    private static final String G_SUBJECT = "Your Temporary Password";

    /** The Constant USERNAME_FIELD. */
    private static final String USERNAME_FIELD = "input[type=email]";

    /** The Constant PASSWORD_FIELD. */
    private static final String PASSWORD_FIELD = "input[type=password]";

    /**
     * The Constant LOGGER.
     */
    @SuppressWarnings("unused")
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginPageImpl.class);

    /**
     * The logged in.
     */
    private boolean loggedIn;

    /**
     * The logged user.
     */
    private String loggedInUser;

    /**
     * The login start time.
     */
    private LocalTime loginStartTime = null;

    /** The mobile config. */
    @Inject
    protected MobileConfig mobileConfig;

    /** The gmail. */
    @Inject
    protected Gmail gmail;

    /**
     * Entering the userName and password in fields.
     * @param userName the user name
     * @param password the password
     * @see com.ecofactor.qa.automation.newapp.page.LoginPage#userNamePasswordFields(java.lang.String,
     *      java.lang.String)
     */
    @Override
    public void userNamePasswordFields(String userName, String password) {

        getAction().rejectAlert();
        LogUtil.setLogString(LogSection.START, "Login Starts", true);
        LogUtil.setLogString("Check Username textField is displayed", true, CustomLogLevel.HIGH);
        WebElement userElement = getElement(getDriver(), By.name(USERNAME), TINY_TIMEOUT);
        WaitUtil.waitUntil(300);
        userElement.sendKeys(userName);
        // clearAndInput(getDriver(), By.name(USERNAME), userName);
        LogUtil.setLogString("Enter Username :" + userName, true, CustomLogLevel.LOW);

        LogUtil.setLogString("Check Password field is displayed", true, CustomLogLevel.HIGH);
        clearAndInput(getDriver(), By.name(PASSWORD), password);
        LogUtil.setLogString("Enter Password :" + password, true, CustomLogLevel.LOW);
    }

    /**
     * Click the submit button.
     * @param userName the user name.
     * @see com.ecofactor.qa.automation.newapp.page.LoginPage#clickSubmit(java.lang.String)
     */
    @Override
    public void clickSubmit(String userName) {

        LogUtil.setLogString("Click Login Button", true, CustomLogLevel.LOW);
        final WebElement loginBtn = getElement(getDriver(), By.className(LOGIN_SUBMIT),
                TINY_TIMEOUT);
        getAction().click(loginBtn);
        WaitUtil.tinyWait();
       // getAction().rejectAlert();
        String currentUTCTime = DateUtil.getUTCCurrentTimeStamp();
        LogUtil.setLogString("\033[46;1mLOGIN REQUEST - UTC TIME: " + currentUTCTime + "\033[0m",
                true, CustomLogLevel.LOW);
        setLoginStartTime(new LocalTime());
        setLoggedIn(isLoadingIconNotDisplayed());
        setLoggedInUser(userName);
        currentUTCTime = DateUtil.getUTCCurrentTimeStamp();
        LogUtil.setLogString(LogSection.END, "Login " + (isLoggedIn() ? "Succeeded" : "Failed"),
                true);
    }

    /**
     * Login.
     * @param userName the user name
     * @param password the password
     * @see com.ecofactor.qa.automation.mobile.page.LoginPage#login(java.lang.String,
     *      java.lang.String)
     */
    @Override
    public void login(final String userName, final String password) {

        userNamePasswordFields(userName, password);
        clickSubmit(userName);
    }

    /**
     * Click login.
     * @param userName the user name
     * @param password the password
     * @see com.ecofactor.qa.automation.newapp.page.LoginPage#clickLogin(java.lang.String)
     */
    public void clickLogin(String userName, String password) {

        LogUtil.setLogString("Enter Username :" + userName, true);
        clearAndInput(getDriver(), By.name(USERNAME), userName);

        LogUtil.setLogString("Enter Password :" + password, true);
        clearAndInput(getDriver(), By.name(PASSWORD), password);

        LogUtil.setLogString("Click Login", true);
        final WebElement loginBtn = getElement(getDriver(), By.className(LOGIN_SUBMIT),
                TINY_TIMEOUT);
        getAction().click(loginBtn);
        WaitUtil.oneSec();
        getAction().rejectAlert();
        setLoggedIn(isLoadingIconNotDisplayed());
        setLoggedInUser(userName);
    }

    /**
     * Checks if is logged in.
     * @return true, if is logged in
     * @see com.ecofactor.qa.automation.mobile.page.LoginPage#isLoggedIn()
     */
    @Override
    public boolean isLoggedIn() {

        return loggedIn;
    }

    /**
     * Checks if is logged in.
     * @param userName the user name
     * @return true, if is logged in
     * @see com.ecofactor.qa.automation.mobile.page.LoginPage#isLoggedIn(java.lang.String)
     */
    @Override
    public boolean isLoggedIn(final String userName) {

        return loggedIn && !isPageLoaded();
    }

    /**
     * Sets the logged in.
     * @param loggedIn the new logged in
     * @see com.ecofactor.qa.automation.mobile.page.LoginPage#setLoggedIn(boolean)
     */
    public void setLoggedIn(final boolean loggedIn) {

        this.loggedIn = loggedIn;
    }

    /**
     * Checks if is not login page.
     * @return true, if is not login page
     */
    private boolean isNotLoginPage() {

        return isNotDisplayed(getDriver(), By.name(USERNAME), TINY_TIMEOUT)
                && isNotDisplayed(getDriver(), By.name(PASSWORD), TINY_TIMEOUT)
                && isNotDisplayed(getDriver(), By.className(LOGIN_SUBMIT), TINY_TIMEOUT);
    }

    /**
     * Cleanup.
     * @see com.ecofactor.qa.automation.mobile.page.BasePage#cleanup()
     */
    @Override
    public void cleanup() {

        LogUtil.setLogString("Cleanup in Login Page.", true, CustomLogLevel.HIGH);
    }

    /**
     * Checks if is page loaded.
     * @return true, if is page loaded
     * @see com.ecofactor.qa.automation.mobile.page.BasePage#isPageLoaded()
     */
    @Override
    public boolean isPageLoaded() {

        LogUtil.setLogString("Verify the Login Page is loaded", true, CustomLogLevel.MEDIUM);
        waitForPageLoaded(getDriver());
        isLoadingIconNotDisplayed();
        return isDisplayed(getDriver(), By.className(LOGIN_SUBMIT), TINY_TIMEOUT);
    }

    /**
     * Gets the error message.
     * @return the error message
     * @see com.ecofactor.qa.automation.mobile.page.LoginPage#getErrorMessage()
     */
    @Override
    public String getErrorMessage() {

        String errorMsg = "";
        isDisplayed(getDriver(), By.className(LOGIN_RESPONSE), MEDIUM_TIMEOUT);
        final WebElement loginResponse = getDriver().findElement(By.className(LOGIN_RESPONSE));
        if (!loginResponse.getText().isEmpty()) {
            LogUtil.setLogString("Error Message : " + loginResponse.getText(), true,
                    CustomLogLevel.LOW);
            errorMsg = loginResponse.getText();
        }
        return errorMsg;
    }

    /**
     * Gets the error text color.
     * @return the error text color
     * @see com.ecofactor.qa.automation.mobile.page.LoginPage#getErrorTextColor()
     */
    @Override
    public String getErrorTextColor() {

        LogUtil.setLogString("Get error message font color", true, CustomLogLevel.HIGH);
        isDisplayed(getDriver(), By.className(LOGIN_RESPONSE), MEDIUM_TIMEOUT);
        Object val = executeScriptByClassName(LOGIN_RESPONSE, FONT_COLOR, getDriver());
        LogUtil.setLogString(
                "Verify if error message has font color :"
                        + mobileConfig.get(MobileConfig.FONT_COLOR), true);
        LogUtil.setLogString("Error message has font color as :" + val, true);
        return String.valueOf(val);
    }

    /**
     * Gets the error text font.
     * @return the error text font
     * @see com.ecofactor.qa.automation.mobile.page.LoginPage#getErrorTextFont()
     */
    @Override
    public String getErrorTextFont() {

        LogUtil.setLogString("Get error message font style", true, CustomLogLevel.HIGH);
        isDisplayed(getDriver(), By.className(LOGIN_RESPONSE), MEDIUM_TIMEOUT);
        Object val = executeScriptByClassName(LOGIN_RESPONSE, FONT_FAMILY, getDriver());
        LogUtil.setLogString(
                "Verify if error message has font family :"
                        + mobileConfig.get(MobileConfig.FONT_FAMILY), true);
        LogUtil.setLogString("Error message has font family as :" + val, true);
        return String.valueOf(val);
    }

    /**
     * Gets the login start time.
     * @return the login start time
     * @see com.ecofactor.qa.automation.mobile.page.LoginPage#getLoginStartTime()
     */
    public LocalTime getLoginStartTime() {

        return loginStartTime;
    }

    /**
     * Sets the login start time.
     * @param loginStartTime the new login start time
     */
    public void setLoginStartTime(final LocalTime loginStartTime) {

        this.loginStartTime = loginStartTime;
    }

    /**
     * Gets the logged in user.
     * @return the logged in user
     */
    public String getLoggedInUser() {

        return loggedInUser;
    }

    /**
     * Sets the logged in user.
     * @param loggedInUser the new logged in user
     */
    public void setLoggedInUser(final String loggedInUser) {

        this.loggedInUser = loggedInUser;
    }

    /**
     * Checks if is loading icon not displayed.
     * @return true, if is loading icon not displayed
     */
    public boolean isLoadingIconNotDisplayed() {

        return isNotDisplayed(getDriver(), By.className(LOADING_MESSAGE), MEDIUM_TIMEOUT)
                && isNotLoginPage();
    }

    /**
     * verifies the background color for the login screen.
     * @return true, if is login background color valid
     */
    @Override
    public boolean isLoginBackgroundColorValid() {

        isDisplayed(getDriver(), By.className(LOGIN_VIEW), MEDIUM_TIMEOUT);
        Object val = executeScriptByClassName(LOGIN_VIEW, BACKGROUND_COLOR, getDriver());
        LogUtil.setLogString("Background color :" + val, true);
        return String.valueOf(val).equalsIgnoreCase(GREY_COLOR);

    }

    /**
     * verifies the default focus for the login screen is with username.
     * @return true, if is default focus with username
     */
    @Override
    public boolean isDefaultFocusWithUsername() {

        WaitUtil.mediumWait();
        isDisplayed(getDriver(), By.name(USERNAME), MEDIUM_TIMEOUT);
        String value = getElement(getDriver(), By.name(USERNAME), TINY_TIMEOUT).getAttribute(
                AUTOFOCUS);
        LogUtil.setLogString("Autofocus:" + value, true);
        return value.equalsIgnoreCase(TRUE);
    }

    /**
     * Checks if is error info position top of login box.
     * @return true, if is error info position top of login box
     * @see com.ecofactor.qa.automation.newapp.page.LoginPage#isErrorInfoPositionTopOfLoginBox()
     */
    @Override
    public boolean isErrorInfoPositionTopOfLoginBox() {

        LogUtil.setLogString("Check if error info message is in top of login box.", true,
                CustomLogLevel.HIGH);
        boolean isErrorInfoPositionTop = false;
        isDisplayed(getDriver(), By.className(LOGIN_RESP_BOX), MEDIUM_TIMEOUT);
        WebElement errorMsg = getElement(getDriver(), By.className(LOGIN_RESP_BOX), MEDIUM_TIMEOUT);
        WebElement formElement = getElement(getDriver(), By.className(LOGIN_FORM), MEDIUM_TIMEOUT);
        isErrorInfoPositionTop = (errorMsg.getLocation().y < formElement.getLocation().y) ? true
                : false;
        return isErrorInfoPositionTop;
    }

    /**
     * Gets the error text background color.
     * @return the error text background color
     * @see com.ecofactor.qa.automation.newapp.page.LoginPage#getErrorTextBackgroundColor()
     */
    @Override
    public String getErrorTextBackgroundColor() {

        LogUtil.setLogString("Get error text background color", true, CustomLogLevel.HIGH);
        isDisplayed(getDriver(), By.className(LOGIN_RESP_BOX), MEDIUM_TIMEOUT);
        Object val = executeScriptByClassName(LOGIN_RESP_BOX, BACKGROUND_COLOR, getDriver());
        LogUtil.setLogString(
                "Verify if error text background color is :"
                        + mobileConfig.get(MobileConfig.ERROR_BGCOLOR), true, CustomLogLevel.HIGH);
        LogUtil.setLogString("Error message back ground color :" + val, true, CustomLogLevel.HIGH);
        return String.valueOf(val);
    }

    /**
     * validates the dimension of the text boxes.
     * @return the status of the text box validation
     * @see com.ecofactor.qa.automation.newapp.page.LoginPage#validateTextBoxDimension()
     */
    @Override
    public boolean validateTextBoxDimension() {

        final JavascriptExecutor javascript = (JavascriptExecutor) getDriver();

        Object userHeight = javascript
                .executeScript("return window.getComputedStyle(document.querySelectorAll('"
                        + USERNAME_FIELD + "')[0],null).getPropertyValue('" + HEIGHT + "')");
        String usernameHeight = userHeight.toString();

        Object userWidth = javascript
                .executeScript("return window.getComputedStyle(document.querySelectorAll('"
                        + USERNAME_FIELD + "')[0], null).getPropertyValue('" + WIDTH + "')");
        String usernameWidth = userWidth.toString();

        Object passwordHeight = javascript
                .executeScript("return window.getComputedStyle(document.querySelectorAll('"
                        + PASSWORD_FIELD + "')[0], null).getPropertyValue('" + HEIGHT + "')");
        String pwdHeight = passwordHeight.toString();

        Object passwordWidth = javascript
                .executeScript("return window.getComputedStyle(document.querySelectorAll('"
                        + PASSWORD_FIELD + "')[0], null).getPropertyValue('" + WIDTH + "')");
        String pwdWidth = passwordWidth.toString();

        String dimension = "[" + usernameHeight + "-" + usernameWidth + "," + pwdHeight + "-"
                + pwdWidth + "]";
        LogUtil.setLogString("Dimension of the username and password text boxes:" + dimension, true);
        if (usernameHeight.equals(HEIGHT_VALUE) && usernameWidth.equals(WIDTH_VALUE)) {
            if (pwdHeight.equals(HEIGHT_VALUE) && pwdWidth.equals(WIDTH_VALUE)) {
                return true;
            }
        }
        return false;
    }

    /**
     * validates the check box is clicked or not of remember Mail.
     * @see com.ecofactor.qa.automation.newapp.page.LoginPage#clickRememberEvent(java.lang.String,
     *      java.lang.String)
     */
    @Override
    public void clickRememberEvent() {

        WebElement checkElement = getElement(getDriver(), By.name(CHECKBOX_NAME), MEDIUM_TIMEOUT);
        if (!checkElement.isSelected()) {

            LogUtil.setLogString("Click Remember Mail", true);
            getAction().click(checkElement);
            WaitUtil.tinyWait();
            getAction().rejectAlert();
        } else if ((checkElement.getAttribute("checked")) != null) {

            LogUtil.setLogString("UnCheck if Remember Mail Clicked already!", true);
            getAction().click(checkElement);
            WaitUtil.tinyWait();
            /* getAction().rejectAlert(); */
            if (!checkElement.isSelected()) {

                LogUtil.setLogString("Click Remember Mail", true);
                getAction().click(checkElement);
                WaitUtil.tinyWait();
                getAction().rejectAlert();
            }
        }
    }

    /**
     * verify either need help link clicked or not.
     * @see com.ecofactor.qa.automation.newapp.page.LoginPage#clickNeedHelp()
     */
    @Override
    public void clickNeedHelp() {

        LogUtil.setLogString("Check for Need Help Hyper Link", true);

        LogUtil.setLogString("Password Page", true);
        final WebElement needForHelpLink = getElement(getDriver(), By.cssSelector(NEED_HELP_LINK),
                MEDIUM_TIMEOUT);
        getAction().click(needForHelpLink);
        WaitUtil.tinyWait();
        getAction().rejectAlert();
    }

    /**
     * verify either back button clicked or not.
     * @see com.ecofactor.qa.automation.newapp.page.LoginPage#clickBackButton()
     */
    @Override
    public void clickBackButton() {

        LogUtil.setLogString("Click Back button", true);
        final WebElement backButton = getElement(getDriver(), By.cssSelector(BACK_BUTTON),
                MEDIUM_TIMEOUT);
        getAction().click(backButton);
        WaitUtil.tinyWait();
        getAction().rejectAlert();
        LogUtil.setLogString("Back to Login Page", true);
    }

    /**
     * verify either hyper links were working or not.
     * @see com.ecofactor.qa.automation.newapp.page.LoginPage#verifyLinks()
     */
    @Override
    public void verifyLinks() {

        LogUtil.setLogString("Check for Hyper Links", true);

        clickNeedHelp();
        clickBackButton();

        if (isPageLoaded()) {

            LogUtil.setLogString("Check for Privacy & Terms Hyper Link", true);
            final WebElement privacyLink = getElement(getDriver(), By.linkText(PRIVACYLINK),
                    MEDIUM_TIMEOUT);
            isClickable(getDriver(), privacyLink, MEDIUM_TIMEOUT);
            // getAction().click(privacyLink);
        }
    }

    /**
     * fetching the version Name.
     * @return string.
     * @see com.ecofactor.qa.automation.newapp.page.LoginPage#versionName()
     */
    @Override
    public String versionName() {

        LogUtil.setLogString("Fetch version Name", true);

        clickNeedHelp();
        final WebElement versionElement = getElement(getDriver(), By.className(VERSION_NAME),
                MEDIUM_TIMEOUT);
        final String version_name = versionElement.getText();
        LogUtil.setLogString("Version Name:" + version_name, true);
        return version_name;
    }

    /**
     * Verify either the send button is clicked or not.
     * @see com.ecofactor.qa.automation.newapp.page.LoginPage#clickSendButton()
     */
    @Override
    public void clickSendButton() {

        LogUtil.setLogString("Click Send", true);
        clickNeedHelp();
        final WebElement sendButton = getElement(getDriver(), By.className(SEND_SUBMIT),
                MEDIUM_TIMEOUT);
        getAction().click(sendButton);
        LogUtil.setLogString("Mail Sent", true);
        WaitUtil.tinyWait();
        getAction().rejectAlert();
    }

    /**
     * Fetch password from gmail.
     * @param userName the user name.
     * @return String.
     * @see com.ecofactor.qa.automation.newapp.page.LoginPage#fetchPassword(java.lang.String)
     */
    @Override
    public String fetchPassword(String userName) {

        final String newPassword = gmail.getChangedPassword(GMAIL_URL, userName, G_PASSWORD,
                G_SUBJECT, 1, 1);
        return newPassword;
    }
}
