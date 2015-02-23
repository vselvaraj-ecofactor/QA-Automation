/*
 * InsiteLoginImpl.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.insite.page;

import static com.ecofactor.qa.automation.insite.config.InsiteConfig.*;
import static com.ecofactor.qa.automation.insite.config.InsiteLoginConfig.*;
import static com.ecofactor.qa.automation.util.PageUtil.*;
import static com.ecofactor.qa.automation.util.WaitUtil.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import com.ecofactor.qa.automation.insite.config.InsiteConfig;
import com.ecofactor.qa.automation.insite.config.InsiteLoginConfig;
import com.ecofactor.qa.automation.util.DriverConfig;
import com.ecofactor.qa.automation.util.WaitUtil;
import com.ecofactor.qa.automation.util.mail.Gmail;
import com.google.inject.Inject;

/**
 * The Class InsiteLoginImpl.
 * @author $Author: vprasannaa $
 * @version $Rev: 33516 $ $Date: 2015-01-19 17:30:19 +0530 (Mon, 19 Jan 2015) $
 */
public class InsiteLoginImpl extends InsitePageImpl implements InsiteLogin {

    /** The login config. */
    @Inject
    private InsiteLoginConfig loginConfig;

    /** The app config. */
    @Inject
    private InsiteConfig appConfig;

    /** The gmail. */
    @Inject
    private Gmail gmail;

    /** The logger. */
    private static Logger logger = LoggerFactory.getLogger(InsiteLoginImpl.class);

    /**
     * Invoke login.
     */
    public void loadPageWithoutAssertion() {

        try {
            if (DriverConfig.getDriver() == null) {
                getDriver();
            }
            DriverConfig.setLogString("Load Login page.", true);
            if (!DriverConfig.getDriver().getCurrentUrl()
                    .equals(appConfig.get(INSITE_URL) + appConfig.get(INSITE_LOGIN_URL))) {
                DriverConfig.getDriver().get(
                        appConfig.get(INSITE_URL) + appConfig.get(INSITE_LOGIN_URL));
                smallWait();
            }
            isDisplayedById(DriverConfig.getDriver(), loginConfig.get(USER_ID), MEDIUM_TIMEOUT);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * Load page.
     * @see com.ecofactor.qa.automation.insite.page.InsitePage#loadPage()
     */
    public void loadPage() {

        if (DriverConfig.getDriver() == null) {
            getDriver();
        }

        DriverConfig.setLogString("Go to Insite Login page " + appConfig.get(INSITE_URL)
                + appConfig.get(INSITE_LOGIN_URL), true);
        if (!DriverConfig.getDriver().getCurrentUrl()
                .equals(appConfig.get(INSITE_URL) + appConfig.get(INSITE_LOGIN_URL))) {
            DriverConfig.getDriver().get(
                    appConfig.get(INSITE_URL) + appConfig.get(INSITE_LOGIN_URL));
            smallWait();
        }
        logger.info("check if user id text box is visible.");
        Assert.assertTrue(
                isDisplayedById(DriverConfig.getDriver(), loginConfig.get(USER_ID), MEDIUM_TIMEOUT),
                "User id text field is not available");
    }

    /**
     * Verify the particular user is already loggedIn, and if not exists make a login to the
     * specified User.
     * @param userName the user name
     * @param password the password
     */
    public void login(final String userName, final String password) {

        DriverConfig.setLogString("Enter login credentials.", true);
        DriverConfig.setLogString(
                "Enter username and password (" + userName + "/" + password + ")", true);
        DriverConfig.getDriver().findElement(By.id(loginConfig.get(USER_ID))).sendKeys(userName);
        DriverConfig.getDriver().findElement(By.id(loginConfig.get(PASSWORD))).sendKeys(password);
        logger.info("before wait.", true);
        WaitUtil.waitUntil(5000);
        DriverConfig.setLogString("click login button.", true);
        WaitUtil.waitUntil(15000);
        DriverConfig.getDriver().findElement(By.id(loginConfig.get(SUBMIT))).click();
        WaitUtil.waitUntil(2000);
    }

    /**
     * Click logout, and navigate to login page.
     */
    public void logout() {

        smallWait();
        logger.info("check if Logout linkis accessible from Insite portal.", true);
        DriverConfig.setLogString("click sign out link.", true);
        WebElement element = retrieveElementByAttributeValue(DriverConfig.getDriver(), TAG_ANCHOR,
                ATTR_TITLE, "Sign out");
        element.click();
        logger.info("check if logout succeded and login page is displayed.", true);
        boolean logoutSuccess = isDisplayedById(DriverConfig.getDriver(), loginConfig.get(USER_ID),
                MEDIUM_TIMEOUT);
        Assert.assertTrue(logoutSuccess,
                "logout failed, USer id text field is not displayed in Login form");
        logger.info("clear the user.", true);
        clearUser();
    }

    /**
     * Very the invalid login message is displayed in the screen.
     */
    public void verifyInvalidLogin() {

        DriverConfig.setLogString("verify invalid login.", true);
        smallWait();
        logger.info("check if Invalid login message is displayed.", true);
        Assert.assertEquals(
                true,
                DriverConfig.getDriver().getPageSource()
                        .contains(loginConfig.get(LOGIN_PAGE_WARNING_MSG_TEXT)),
                "Invalid login messsage is not displayed");
    }

    /**
     * Verify the login is success and the page is redirected properly.
     * @param userName the user name
     */
    public void verifyLogin(final String userName) {

        logger.info("check if signout link in home page is displayed to confirm login success.",
                true);
        WebElement signOutText = retrieveElementByLinkText(DriverConfig.getDriver(),
                loginConfig.get(HOME_PAGE_IDENTIFIER), MEDIUM_TIMEOUT);
        DriverConfig.setLogString("verify login success.", true);
        logger.info("verify the page is redirected to the home page of InsitePortal after login.",
                true);
        Assert.assertEquals(signOutText.getText(), loginConfig.get(HOME_PAGE_IDENTIFIER),
                "Fail to load home page, Sign out text is not available");
        user = userName;
    }

    /**
     * Verify login without assertion.
     * @param userName the user name
     * @see com.ecofactor.qa.automation.insite.page.InsiteLogin#verifyLoginWithoutAssertion(java.lang.String)
     */
    public void verifyLoginWithoutAssertion(final String userName) {

        logger.info("verify the page is redirected to the home page of InsitePortal without assertion.");

        WebElement signOutText = retrieveElementByLinkText(DriverConfig.getDriver(),
                loginConfig.get(HOME_PAGE_IDENTIFIER), MEDIUM_TIMEOUT);
        if (signOutText.getText().equalsIgnoreCase(loginConfig.get(HOME_PAGE_IDENTIFIER)))
            user = userName;

    }

    /**
     * Do a Reset password
     * <ol>
     * <li>Click on Reset Password Link.</li>
     * <li>Password the email address and verification email address</li>
     * <li>Click on Submit button.</li>
     * <li>Accept the alert message pop up.</li>
     * <li>Verify the page is navigated to the login Screen.</li>
     * </ol>
     * @param password the password
     */
    public void doRestPassword(final String password) {

        clickResetPasswordLink();
        DriverConfig.setLogString("Provide the username and click on Reset Password button.", true);
        DriverConfig.setLogString("Enter Your User name " + password, true);
        DriverConfig.setLogString("Re-Enter UserName " + password, true);
        DriverConfig.getDriver().findElement(By.id(loginConfig.get(RESET_PASSWORD_EMAILADDRESS)))
                .sendKeys(password);
        DriverConfig.getDriver()
                .findElement(By.id(loginConfig.get(RESET_PASSWORD_EMAILADDRESS_CHECK)))
                .sendKeys(password);
        DriverConfig.getDriver().findElement(By.id(loginConfig.get(SUBMIT_RESET))).click();
        WaitUtil.waitUntil(SHORT_TIMEOUT);
        logger.info("click ok on alert message.");
        DriverConfig.getDriver().switchTo().alert().accept();
        DriverConfig.setLogString("check if login page is displayed.", true);
        isDisplayedById(DriverConfig.getDriver(), loginConfig.get(USER_ID), SHORT_TIMEOUT);
    }

    /**
     * change new Password..
     * @param newPassword the new password
     */
    public void changeNewPassword(final String newPassword) {

        DriverConfig.setLogString("Enter the new password and confirm it.", true);
        DriverConfig.setLogString("New Password: " + newPassword, true);
        DriverConfig.getDriver().findElement(By.id(loginConfig.get(RESET_PASSWORD)))
                .sendKeys(newPassword);
        DriverConfig.getDriver().findElement(By.id(loginConfig.get(RESET_PASSWORD_PASSWORD_CHECK)))
                .sendKeys(newPassword);
        DriverConfig.getDriver().findElement(By.id(loginConfig.get(SUBMIT_RESET))).click();

        WaitUtil.waitUntil(SHORT_TIMEOUT);
        DriverConfig.setLogString("click ok on alert message.", true);
        DriverConfig.getDriver().switchTo().alert().accept();
        WaitUtil.waitUntil(SHORT_TIMEOUT);
    }

    /**
     * Pass the relevant arguments to get the temporary password send via email.
     * @param emailURL the email url
     * @param emailUserId the email user id
     * @param emailPassword the email password
     * @param mailSubject the mail subject
     * @param boldIndex the bold index
     * @return the changed password
     */
    public String getChangedPassword(final String emailURL, final String emailUserId,
            final String emailPassword, final String mailSubject, int boldIndex) {

        DriverConfig.setLogString("Log on to mail website and get the password.", true);
        String tempPassword = gmail.getChangedPassword(emailURL, emailUserId, emailPassword,
                mailSubject, boldIndex, 0);
        return tempPassword;
    }

    /**
     * Login with the new password received via email.
     * <ol>
     * <li>Do logout, if Login</li>
     * </ol>
     * @param userName the user name
     * @param tempPassword the temp password
     * @param newPassword the new password
     */
    public void loginWithNewPassword(final String userName, final String tempPassword,
            final String newPassword) {

        if (isAuthenticatedUser()) {
            DriverConfig.setLogString("User Login exists, need to logout.", true);
            logout();
        }
        DriverConfig.setLogString("Login with the new received password '" + tempPassword
                + "' found via email.", true);
        DriverConfig.getDriver().get(appConfig.get(INSITE_URL) + appConfig.get(INSITE_LOGIN_URL));
        smallWait();
        isDisplayedById(DriverConfig.getDriver(), loginConfig.get(USER_ID), MEDIUM_TIMEOUT);
        login(userName, tempPassword.trim());

        logger.info("check if password field is displayed.");

        isDisplayedById(DriverConfig.getDriver(), "password", SHORT_TIMEOUT);
        mediumWait();
        DriverConfig.getDriver().findElement(By.id("password")).clear();
        WaitUtil.waitUntil(1000);
        DriverConfig.setLogString("send value to password field.", true);
        DriverConfig.setLogString("Enter the new Password " + newPassword, true);
        DriverConfig.getDriver().findElement(By.id("password")).sendKeys(newPassword);
        DriverConfig.getDriver().findElement(By.id("password-check")).sendKeys(newPassword);
        WaitUtil.waitUntil(1000);
        DriverConfig.setLogString("click ok on submit button.", true);
        DriverConfig.getDriver().findElement(By.id("submit")).click();
        logger.info("click ok on alert message.", true);
        DriverConfig.getDriver().switchTo().alert().accept();
        WaitUtil.waitUntil(1000);
        DriverConfig.setLogString("Verify page is redirected to login screen.", true);
        isDisplayedById(DriverConfig.getDriver(), loginConfig.get(USER_ID), SHORT_TIMEOUT);

        DriverConfig.setLogString("Login with the changed new password: " + newPassword, true);
        login(userName, newPassword);
        WaitUtil.waitUntil(1000);
    }

    /**
     * Verify home page identifier.
     * @see com.ecofactor.qa.automation.insite.page.InsiteLogin#verifyHomePageIdentifier()
     */
    public void verifyHomePageIdentifier() {

        DriverConfig.setLogString("check if home page is loaded after succesful login.", true);
        WebElement signOutText = retrieveElementByLinkText(DriverConfig.getDriver(),
                loginConfig.get(HOME_PAGE_IDENTIFIER), MEDIUM_TIMEOUT);
        Assert.assertEquals(signOutText.getText(), loginConfig.get(HOME_PAGE_IDENTIFIER),
                "Fail to load home page, Sign out text is not available");
    }

    /**
     * Verify login page identifier.
     * @see com.ecofactor.qa.automation.insite.page.InsiteLogin#verifyLoginPageIdentifier()
     */
    public void verifyLoginPageIdentifier() {

        logger.info("check if login page is displayed.", true);
        isDisplayedById(DriverConfig.getDriver(), loginConfig.get(USER_ID), MEDIUM_TIMEOUT);

    }

    /**
     * Checks if is login.
     * @return true, if is login
     * @see com.ecofactor.qa.automation.insite.page.InsiteLogin#isLogin()
     */
    /*
     * public boolean isLogin() { return login; }
     */

    /**
     * Checks if is authenticated user.
     * @return true, if is authenticated user
     * @see com.ecofactor.qa.automation.insite.page.InsiteLogin#isAuthenticatedUser()
     */
    public boolean isAuthenticatedUser() {

        DriverConfig.setLogString("check if user logged in.", true);
        boolean loggedIn = user != null && !"".equals(user) && !"null".equalsIgnoreCase(user);
        DriverConfig.setLogString("User authenticated :" + loggedIn, true);
        return loggedIn;
    }

    /**
     * Checks if is authenticated user.
     * @param userName the user name
     * @return true, if is authenticated user
     * @see com.ecofactor.qa.automation.insite.page.InsiteLogin#isAuthenticatedUser(java.lang.String)
     */
    public boolean isAuthenticatedUser(final String userName) {

        DriverConfig.setLogString("check if required user login already exists.", true);
        boolean identicalUser = user != null && user.equals(userName);
        logger.info(
                "Required user login: " + userName + "/n  authenticated user: " + identicalUser,
                true);
        return identicalUser;
    }

    /**
     * Gets the login user.
     * @return the login user
     * @see com.ecofactor.qa.automation.insite.page.InsiteLogin#getLoginUser()
     */
    public String getLoginUser() {

        DriverConfig.setLogString("return user name.", true);
        return user;
    }

    /**
     * Click reset password link.
     */
    public void clickResetPasswordLink() {

        if (DriverConfig.getDriver() == null) {
            getDriver();
        }

        if (isAuthenticatedUser()) {
            logger.info("User Login exists, need to logout.");
            logout();
        }
        DriverConfig.getDriver().get(appConfig.get(INSITE_URL));
        smallWait();
        logger.info("check if reset password link visible and click it.", true);
        isDisplayedByCSS(DriverConfig.getDriver(), loginConfig.get(RESET_PASSWORD_LINK),
                MEDIUM_TIMEOUT);
        DriverConfig.setLogString("click reset password link.", true);
        DriverConfig.getDriver().findElement(By.cssSelector(loginConfig.get(RESET_PASSWORD_LINK)))
                .click();
        smallWait();
    }

    /**
     * Test reset pswd cancel link.
     * @see com.ecofactor.qa.automation.insite.page.InsiteLogin#testResetPswdCancelLink()
     */
    @Override
    public void testResetPswdCancelLink() {

        clickResetPasswordLink();
        logger.info("check if cancel button available in reset password wizard.");
        isDisplayedByCSS(DriverConfig.getDriver(), loginConfig.get(CANCEL_RESET_PASSWORD),
                MEDIUM_TIMEOUT);
        DriverConfig.setLogString("click cancel button in reset password.", true);
        DriverConfig.getDriver()
                .findElement(By.cssSelector(loginConfig.get(CANCEL_RESET_PASSWORD))).click();
        smallWait();
        isDisplayedByCSS(DriverConfig.getDriver(), loginConfig.get(RESET_PASSWORD_LINK),
                MEDIUM_TIMEOUT);
        DriverConfig.setLogString("check if login page is displayed.", true);
        logger.info("check if reset password wizard is displayed.");
        Assert.assertTrue(
                isDisplayedByCSS(DriverConfig.getDriver(), loginConfig.get(RESET_PASSWORD_LINK),
                        MEDIUM_TIMEOUT), "Reset password link is not available");
    }

    /**
     * Click reset pswd with blank fields.
     * @see com.ecofactor.qa.automation.insite.page.InsiteLogin#clickResetPswdWithBlankFields()
     */
    public void clickResetPswdWithBlankFields() {

        clickResetPasswordLink();
        logger.info("check if submit button available in reset password wizard.", true);
        isDisplayedByCSS(DriverConfig.getDriver(), loginConfig.get(SUBMIT_RESET), MEDIUM_TIMEOUT);
        logger.info("click submit button available in reset password wizard.", true);
        DriverConfig.setLogString("click reset password submit button.", true);
        DriverConfig.getDriver().findElement(By.id(loginConfig.get(SUBMIT_RESET))).click();
        smallWait();
        isDisplayedById(DriverConfig.getDriver(), loginConfig.get(RESET_PASSWORD_BLANK_ERROR_MSG),
                MEDIUM_TIMEOUT);
        DriverConfig.setLogString("check if error message is displayed for blank fields.", true);
        Assert.assertTrue(
                isDisplayedById(DriverConfig.getDriver(),
                        loginConfig.get(RESET_PASSWORD_BLANK_ERROR_MSG), MEDIUM_TIMEOUT),
                "Reset password error message is not displayed");
    }

    /**
     * Click reset pswd with non matching username.
     * @see com.ecofactor.qa.automation.insite.page.InsiteLogin#clickResetPswdWithNonMatchingUsername()
     */
    public void clickResetPswdWithNonMatchingUsername() {

        clickResetPasswordLink();
        DriverConfig.setLogString("Enter different values in user name fields.", true);
        DriverConfig.setLogString("Enter Your Username - username", true);
        DriverConfig.setLogString("Re-Enter Username - usernam", true);
        DriverConfig.getDriver().findElement(By.id(loginConfig.get(RESET_PASSWORD_EMAILADDRESS)))
                .sendKeys("username");
        DriverConfig.getDriver()
                .findElement(By.id(loginConfig.get(RESET_PASSWORD_EMAILADDRESS_CHECK)))
                .sendKeys("usernam");

        logger.info("check if submit button available in reset password wizard.", true);
        isDisplayedByCSS(DriverConfig.getDriver(), loginConfig.get(SUBMIT_RESET), MEDIUM_TIMEOUT);
        DriverConfig.setLogString("click submit button.", true);
        DriverConfig.getDriver().findElement(By.id(loginConfig.get(SUBMIT_RESET))).click();
        smallWait();
        isDisplayedById(DriverConfig.getDriver(),
                loginConfig.get(RESET_PASSWORD_USERNAME_MISMATCH_ERROR_MSG), MEDIUM_TIMEOUT);
        DriverConfig.setLogString("check if error message is displayed.", true);
        Assert.assertTrue(
                isDisplayedById(DriverConfig.getDriver(),
                        loginConfig.get(RESET_PASSWORD_USERNAME_MISMATCH_ERROR_MSG), MEDIUM_TIMEOUT),
                "Error message is not displayed");
    }

    /**
     * Test about ecofactor link.
     * @see com.ecofactor.qa.automation.insite.page.InsiteLogin#testAboutEcofactorLink()
     */
    @Override
    public void testAboutEcofactorLink() {

        if (DriverConfig.getDriver() == null) {
            getDriver();
        }

        if (isAuthenticatedUser()) {
            logger.info("User Login exists, need to logout.");
            logout();
        }
        DriverConfig.setLogString("Load insite URL.", true);
        loadPageWithoutAssertion();
        logger.info("check if 'About Ecofactor' link is available & click it.", true);
        DriverConfig.setLogString("click 'About Ecofactor' link.", true);
        DriverConfig.getDriver()
                .findElement(By.cssSelector(insiteConfig.get(ABOUT_ECOFACTOR_LINK))).click();
        smallWait();
        logger.info("check if 'About Ecofactor' page is displayed.", true);
        Assert.assertTrue(
                DriverConfig.getDriver().getCurrentUrl()
                        .equalsIgnoreCase(insiteConfig.get(ECOFACTOR_URL)), "Url is different");
    }

    /**
     * Clear user.
     * @see com.ecofactor.qa.automation.insite.page.InsiteLogin#clearUser()
     */
    public void clearUser() {

        this.user = null;
    }

    /**
     * Click about ecofactor.
     * @see com.ecofactor.qa.automation.insite.page.InsitePageImpl#clickAboutEcofactor()
     */
    @Override
    public void clickAboutEcofactor() {

        // Auto-generated method stub

    }
}
