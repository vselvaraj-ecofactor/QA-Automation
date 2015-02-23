/*
 * LoginTest.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.insite.regression;

import static com.ecofactor.qa.automation.insite.config.InsiteConfig.*;
import static org.testng.Assert.*;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Guice;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.ecofactor.qa.automation.dao.DaoModule;
import com.ecofactor.qa.automation.insite.InsiteModule;
import com.ecofactor.qa.automation.insite.config.InsiteConfig;
import com.ecofactor.qa.automation.insite.data.InsiteLoginDataProvider;
import com.ecofactor.qa.automation.insite.page.InsiteLogin;
import com.ecofactor.qa.automation.util.DriverConfig;
import com.ecofactor.qa.automation.util.RerunFailTestAnalyzer;
import com.ecofactor.qa.automation.util.TestLogUtil;
import com.ecofactor.qa.automation.util.UtilModule;
import com.ecofactor.qa.automation.util.mail.GmailForNewUser;
import com.ecofactor.qa.automation.util.test.JobValidator;
import com.google.inject.Inject;

/**
 * <b>Login test of insitePortal webSite, which contains valid and invalid login check.</b>
 * @author Aximsoft
 */
@Guice(modules = { UtilModule.class, InsiteModule.class, DaoModule.class })
@Listeners(JobValidator.class)
public class LoginTest {

    /** The insite login. */
    @Inject
    private InsiteLogin insiteLogin;

    /** The gmail for new user. */
    @Inject
    private GmailForNewUser gmailForNewUser;

    /** The app config. */
    @Inject
    private InsiteConfig appConfig;

    /** The test log util. */
    @Inject
    private TestLogUtil testLogUtil;

    /** The logger. */
    private static Logger logger = LoggerFactory.getLogger(LoginTest.class);

    /** The start. */
    private long start;

    /**
     * Inits the suite.
     */
    @BeforeSuite(alwaysRun = true)
    public void initSuite() {

        HttpURLConnection urlConnection = null;
        String insiteURLString = null;
        int status = -1;
        try {
            insiteURLString = appConfig.get(INSITE_URL) + appConfig.get(INSITE_LOGIN_URL);
            URL insiteURL = new URL(insiteURLString);
            urlConnection = (HttpURLConnection) insiteURL.openConnection();
            urlConnection.setReadTimeout(5000);
            status = urlConnection.getResponseCode();
        } catch (IOException e) {
            if (status != HttpURLConnection.HTTP_OK) {
                fail("Unable to connect insite portal '" + insiteURLString + "'. The site is down!");
            }
        }
    }

    /**
     * Inits the method.
     * @param param the param
     * @param method the method
     */
    @BeforeMethod(alwaysRun = true)
    public void initMethod(Object[] param, Method method) {

        testLogUtil.logStart(method);
        start = System.currentTimeMillis();

        try {
            if (method.getName() == "testResetPasswordVerificationViaEmail"
                    || method.getName() == "passwordVerifyInEmail") {
                gmailForNewUser.deleteOldMails((String) param[1], (String) param[2],
                        (String) param[3], (String) param[4]);
            }
            if (insiteLogin.isAuthenticatedUser()) {
                DriverConfig.setLogString("user login exists, logout existing user.", true);
                insiteLogin.logout();
            }
            insiteLogin.loadPage();
        } catch (Throwable e) {
            logger.error("Error in before method " + e.getMessage());
        }
    }

    /**
     * End method.
     * @param method the method
     */
    @AfterMethod(alwaysRun = true)
    public void endMethod(Method method) {

        long duration = (System.currentTimeMillis() - start) / 1000;
        testLogUtil.logEnd(method, duration);
    }

    /**
     * End suite.
     */
    @AfterSuite(alwaysRun = true)
    public void endSuite() {

        try {
            insiteLogin.end();
        } catch (Throwable e) {
            logger.error("Error in after suite " + e.getMessage());
        }
    }

    /**
     * <b> Test Login with valid credentials </b> Check login with userName and parameters and check
     * the home page is populated.
     * @param userId the user id
     * @param password the password
     */
    @Test(dataProvider = "testValidLoginCredentials", dataProviderClass = InsiteLoginDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "sanity" })
    public void testValidLoginCredentials(final String userId, final String password) {

        insiteLogin.login(userId, password);
        insiteLogin.verifyHomePageIdentifier();

    }

    /**
     * <b> Test InsitePortal Login with valid credentials </b> Check login with userName and
     * parameters and check the home page is populated.
     * @param userName the user name
     * @param password the password
     */
    @Test(dataProvider = "testInValidLoginCredentials", dataProviderClass = InsiteLoginDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "validation" })
    public void testInValidLoginCredentials(final String userName, final String password) {

        insiteLogin.login(userName, password);
        insiteLogin.verifyInvalidLogin();
    }

    /**
     * <b>Test Case to check the new password created by user is able to login in the site.</b>
     * @param userName the user name
     * @param emailURL the email url
     * @param emailUserId the email user id
     * @param emailPassword the email password
     * @param mailSubject the mail subject
     * @param newPassword the new password
     */
    @Test(dataProvider = "testResetPasswordVerificationViaEmail", dataProviderClass = InsiteLoginDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "sanity" })
    public void testResetPasswordVerificationViaEmail(final String userName, final String emailURL,
            final String emailUserId, final String emailPassword, final String mailSubject,
            final String newPassword) {

        insiteLogin.doRestPassword(userName);
        String tempPassword = gmailForNewUser.getChangedPassword(emailURL, emailUserId,
                emailPassword, mailSubject, 0, 0);
        insiteLogin.loginWithNewPassword(userName, tempPassword, newPassword);
        insiteLogin.verifyHomePageIdentifier();
        insiteLogin.logout();
    }

    /**
     * Test resetpassword cancel link.
     */
    @Test(retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "ui" })
    public void testResetpasswordCancelLink() {

        insiteLogin.testResetPswdCancelLink();
    }

    /**
     * Test resetpassword with blank username.
     */
    @Test(retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "validation" })
    public void testResetpasswordWithBlankUsername() {

        insiteLogin.clickResetPswdWithBlankFields();

    }

    /**
     * Password verify in email.
     * @param userName the user name
     * @param emailURL the email url
     * @param emailUserId the email user id
     * @param emailPassword the email password
     * @param mailSubject the mail subject
     * @param newPassword the new password
     */
    @Test(dataProvider = "testResetPasswordVerificationViaEmail", dataProviderClass = InsiteLoginDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "sanity" })
    public void passwordVerifyInEmail(final String userName, final String emailURL,
            final String emailUserId, final String emailPassword, final String mailSubject,
            final String newPassword) {

        insiteLogin.doRestPassword(userName);
        String tempPassword = gmailForNewUser.getChangedPassword(emailURL, emailUserId,
                emailPassword, mailSubject, 0, 0);
        Assert.assertNotNull(tempPassword, "Temporary password is not available");
        Assert.assertFalse(tempPassword.isEmpty(), "Temporary password is available");
    }

    /**
     * Test resetpassword with non matching username.
     */
    @Test(retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "validation" })
    public void testResetpasswordWithNonMatchingUsername() {

        insiteLogin.clickResetPswdWithNonMatchingUsername();

    }

    /**
     * Comcast credentials.
     * @param userId the user id
     * @param password the password
     */
    @Test(dataProvider = "comcastCredentials", dataProviderClass = InsiteLoginDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "sanity" })
    public void comcastCredentials(final String userId, final String password) {

        insiteLogin.login(userId, password);
        insiteLogin.verifyHomePageIdentifier();
        insiteLogin.logout();
    }

    /**
     * Test about ecofactor link.
     */
    @Test(retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "ui" })
    public void testAboutEcofactorLink() {

        insiteLogin.testAboutEcofactorLink();
    }
}