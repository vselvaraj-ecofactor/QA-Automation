/*
 * InstallationHardwareTest.java
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
import org.testng.annotations.AfterClass;
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
import com.ecofactor.qa.automation.insite.config.InstallationTestConfig;
import com.ecofactor.qa.automation.insite.data.InstallationHardwareDataProvider;
import com.ecofactor.qa.automation.insite.page.InsiteLogin;
import com.ecofactor.qa.automation.insite.page.InstallationHardware;
import com.ecofactor.qa.automation.util.RerunFailTestAnalyzer;
import com.ecofactor.qa.automation.util.TestLogUtil;
import com.ecofactor.qa.automation.util.UtilModule;
import com.ecofactor.qa.automation.util.test.JobValidator;
import com.google.inject.Inject;

/**
 * <b>Installation Hardware</b>
 * <ol>
 * <li>Checks the installation wizard still 6th wizard and iterate until according to the invocation
 * count</li>
 * </ol>
 * .
 * @author Aximsoft
 */
@Guice(modules = { UtilModule.class, DaoModule.class, InsiteModule.class })
@Listeners(JobValidator.class)
public class InstallationHardwareTest {
    @Inject
    private InsiteLogin insiteLogin;
    @Inject
    private InstallationHardware installationHardware;
    @Inject
    private static InstallationTestConfig installationTestConfig;
    @Inject
    private InsiteConfig appConfig;
    @Inject
    private TestLogUtil testLogUtil;
    private static Logger logger = LoggerFactory.getLogger(InstallationHardwareTest.class);
    private long start;

    /**
     * Inits the suite.
     */
    @BeforeSuite(alwaysRun = true)
    public void initSuite() {
        HttpURLConnection  urlConnection=null;
        String insiteURLString = null;
        int status=-1;
        try {
            insiteURLString = appConfig.get(INSITE_URL) + appConfig.get(INSITE_LOGIN_URL);
            URL insiteURL = new URL(insiteURLString);
            urlConnection =(HttpURLConnection) insiteURL.openConnection();
            urlConnection.setReadTimeout(5000);
              status = urlConnection.getResponseCode();
        } catch (IOException e) {
            if (status != HttpURLConnection.HTTP_OK) {
                fail("Unable to connect insite portal '"+insiteURLString+"'. The site is down!");
            }
        }
    }

    /**
     * Inits the method.
     * @param param the param
     */
	@BeforeMethod(alwaysRun = true)
	public void initMethod(Object[] param, Method method) {

		testLogUtil.logStart(method, param);
		start = System.currentTimeMillis();

		try {
			installationHardware.loadPage((String) param[0], (String) param[1]);
		} catch (Throwable e) {
			logger.error("Error in before method " + e.getMessage());
		}
	}

    /**
     * End method.
     * @param m the m
     */
    @AfterMethod(alwaysRun = true)
    public void endMethod(Method method) {

    	long duration = (System.currentTimeMillis() - start) / 1000;
        testLogUtil.logEnd(method, duration);
    }

    /**
     * End Class.
     */
    @AfterClass(alwaysRun = true)
    public void endClass() {

        try {
            installationHardware.logout();
        } catch (Throwable e) {
            logger.error("Error in after class " + e.getMessage());
        }
    }

    /**
     * End Suite.
     */
    @AfterSuite(alwaysRun = true)
    public void endSuite() {

        try {
            installationHardware.end();
        } catch (Throwable e) {
            logger.error("Error in after suite " + e.getMessage());
        }
    }

    /**
     * <ol>
     * <li>Test Installed hardware</li>
     * </ol>
     * .
     * @param userName the user name
     * @param password the password
     * @param streetAddressValue the street address value
     * @param noOfLoops the no of loops
     */
    //@Test(dataProvider = "testInstallationHW", dataProviderClass = InstallationHardwareDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "blocked" })
    public void testInstallationHardware(final String userName, final String password, final String streetAddressValue,
            final String noOfLoops) {

        installationHardware.testInstallationStuff(streetAddressValue);

    }

    /**
     * Ecofactor logo link.
     * @param userId the user id
     * @param password the password
     */
    @Test(dataProvider = "validLogin", dataProviderClass = InstallationHardwareDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = {"smoke"})
    public void headerLogoLink(String userId, String password) {

        installationHardware.isLogoDisplayed();

    }

    /**
     * Test the welcome text for the logged in user in the insite home page.
     * @param userId the user id
     * @param password the password
     */
    @Test(dataProvider = "validLogin", dataProviderClass = InstallationHardwareDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = {"smoke"})
    public void welcomeText(String userId, String password) {

        installationHardware.verifyWelcomeText(userId);

    }

    /**
     * Test the account link in the insite home page.
     * @param userId the user id
     * @param password the password
     */
    @Test(dataProvider = "validLogin", dataProviderClass = InstallationHardwareDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = {"smoke"})
    public void supportLink(String userId, String password) {

        installationHardware.clickSupport();

    }

    /**
     * Installation link.
     * @param userId the user id
     * @param password the password
     */
    @Test(dataProvider = "validLogin", dataProviderClass = InstallationHardwareDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = {"smoke"})
    public void installationLink(String userId, String password) {

        installationHardware.clickInstallation();

    }

    /**
     * Installation schedule link.
     * @param userId the user id
     * @param password the password
     */
    @Test(dataProvider = "validLogin", dataProviderClass = InstallationHardwareDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = {"smoke"})
    public void installationScheduleLink(String userId, String password) {

        installationHardware.clickScheduling();

    }

    /**
     * Installation pre config link.
     * @param userId the user id
     * @param password the password
     */
    @Test(dataProvider = "validLogin", dataProviderClass = InstallationHardwareDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = {"smoke"})
    public void installationPreConfigLink(String userId, String password) {

        installationHardware.clickPreConfiguration();

    }

    /**
     * User management link.
     * @param userId the user id
     * @param password the password
     */
    @Test(dataProvider = "validLogin", dataProviderClass = InstallationHardwareDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = {"smoke"})
    public void userManagementLink(String userId, String password) {

        installationHardware.clickUserManagement();

    }

    /**
     * Role management link.
     * @param userId the user id
     * @param password the password
     */
    @Test(dataProvider = "validLogin", dataProviderClass = InstallationHardwareDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = {"smoke"})
    public void roleManagementLink(String userId, String password) {

        installationHardware.clickRoleManagement();

    }

    /**
     * Demand side management link.
     * @param userId the user id
     * @param password the password
     */
    @Test(dataProvider = "validLogin", dataProviderClass = InstallationHardwareDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = {"smoke"})
    public void demandSideManagementLink(String userId, String password) {

        installationHardware.clickDemandSideManagement();

    }

    /**
     * Test the user logout from a insite authenticated page.
     * @param userId the user id
     * @param password the password
     */
    @Test(dataProvider = "validLogin", dataProviderClass = InstallationHardwareDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = {"smoke"})
    public void logout(String userId, String password) {

        installationHardware.logout();

    }

}
