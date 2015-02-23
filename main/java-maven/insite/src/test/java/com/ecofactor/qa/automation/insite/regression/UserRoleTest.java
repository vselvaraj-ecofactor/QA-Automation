/*
 * UserRoleTest.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.insite.regression;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Guice;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.ecofactor.qa.automation.dao.DaoModule;
import com.ecofactor.qa.automation.insite.InsiteModule;
import com.ecofactor.qa.automation.insite.data.UserRoleDataProvider;
import com.ecofactor.qa.automation.insite.page.InsiteLogin;
import com.ecofactor.qa.automation.insite.page.UserManagement;
import com.ecofactor.qa.automation.insite.page.UserRoleManagement;
import com.ecofactor.qa.automation.util.RerunFailTestAnalyzer;
import com.ecofactor.qa.automation.util.TestlinkUtil;
import com.ecofactor.qa.automation.util.UtilModule;
import com.ecofactor.qa.automation.util.TestLogUtil;
import com.ecofactor.qa.automation.util.test.JobValidator;
import com.google.inject.Inject;

/**
 * The Class UserRoleTest.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
@Guice(modules = { UtilModule.class, InsiteModule.class, DaoModule.class })
@Listeners(JobValidator.class)
public class UserRoleTest {

    @Inject
    private InsiteLogin insiteLogin;
    @Inject
    private UserRoleManagement userRoleManagement;
    @Inject
    private UserManagement userManagement;
    @Inject
    private TestlinkUtil testLinkUtil;
    @Inject
    private TestLogUtil testLogUtil;
    private static Logger logger = LoggerFactory.getLogger(UserRoleTest.class);
    private long start;

    /**
     * Inits the method.
     * @param param the param
     * @param method the method
     */
	@BeforeMethod(alwaysRun = true)
	public void initMethod(Object[] param, Method method) {

		testLogUtil.logStart(method, param);
		start = System.currentTimeMillis();

		try {
			insiteLogin.loadPage();
			userRoleManagement.loadPage((String) param[0], (String) param[1]);
		} catch (Throwable e) {
			logger.error("Error in before method " + e.getMessage(), true);
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
     * End Class.
     */
    @AfterClass(alwaysRun = true)
    public void endClass() {

        try {
            userRoleManagement.logout();
        } catch (Throwable e) {
            logger.error("Error in after class method " + e.getMessage(), true);
        }
    }

    /**
     * End Suite.
     */
    @AfterSuite(alwaysRun = true)
    public void endSuite() {

        try {
            userRoleManagement.end();
        } catch (Throwable e) {
            logger.error("Error in after suite method " + e.getMessage(), true);
        }
    }

    /**
     * Test ecp roles.
     * @param userId the user id
     * @param password the password
     */
    @Test(dataProvider = "testEcpRoles", dataProviderClass = UserRoleDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = {
            "smoke", "sanity" })
    public void testEcpRoles(String userId, String password) {

        userRoleManagement.testEcpRoles(userId, password);

    }

    /**
     * Test install roles.
     * @param userId the user id
     * @param password the password
     */
    @Test(dataProvider = "testInstallRoles", dataProviderClass = UserRoleDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = {
            "smoke", "sanity" })
    public void testInstallRoles(String userId, String password) {

        userRoleManagement.testInstallRoles(userId, password);

    }

    /**
     * Test service roles.
     * @param userId the user id
     * @param password the password
     */
    @Test(dataProvider = "testServiceRoles", dataProviderClass = UserRoleDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = {
            "smoke", "sanity" })
    public void testServiceRoles(String userId, String password) {

        userRoleManagement.testServiceRoles(userId, password);
    }

    /**
     * Test util roles.
     * @param userId the user id
     * @param password the password
     */
    @Test(dataProvider = "testUtilRoles", dataProviderClass = UserRoleDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = {
            "smoke", "sanity" })
    public void testUtilRoles(String userId, String password) {

        userRoleManagement.testUtilRoles(userId, password);
    }

    /**
     * Test custom roles.
     * @param userId the user id
     * @param password the password
     */
    @Test(dataProvider = "testCustomRoles", dataProviderClass = UserRoleDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = {
            "smoke", "sanity" })
    public void testCustomRoles(String userId, String password) {

        userRoleManagement.testCustomRoles(userId, password);
    }

    /**
     * Test customer care roles.
     * @param userId the user id
     * @param password the password
     */
    @Test(dataProvider = "testCustomerCareRoles", dataProviderClass = UserRoleDataProvider.class, groups = { "smoke",
            "sanity" })
    public void testCustomerCareRoles(String userId, String password) {

        userRoleManagement.testCustomerCareRoles(userId, password);
    }

    /**
     * Test ef admin roles.
     * @param userId the user id
     * @param password the password
     */
    @Test(dataProvider = "testEFAdminRoles", dataProviderClass = UserRoleDataProvider.class, groups = { "smoke",
            "sanity" })
    public void testEFAdminRoles(String userId, String password) {

        userRoleManagement.testEFAdminRoles(userId, password);
    }

    @Test(dataProvider = "testCustomAddPagesUserRoleTest", dataProviderClass = UserRoleDataProvider.class, groups = { "smoke",
            "sanity" })
    public void testCustomAddUserPages(String userId, String password) {

        userRoleManagement.testTwoRolesAddedPagesUser(userId, password);
    }

    @Test(dataProvider = "testCustomRemvePagesUserRoleTest", dataProviderClass = UserRoleDataProvider.class, groups = { "smoke",
            "sanity" })
    public void testCustomRemveUserPages(String userId, String password) {

        userRoleManagement.testTwoRolesRemovedPagesUser(userId, password);
    }

}
