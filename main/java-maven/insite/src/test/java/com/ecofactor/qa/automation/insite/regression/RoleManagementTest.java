/*
 * RoleManagementTest.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.insite.regression;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

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
import com.ecofactor.qa.automation.insite.data.RoleDataProvider;
import com.ecofactor.qa.automation.insite.page.InsiteLogin;

import com.ecofactor.qa.automation.insite.page.RoleManagement;
import com.ecofactor.qa.automation.util.RerunFailTestAnalyzer;
import com.ecofactor.qa.automation.util.TestLogUtil;
import com.ecofactor.qa.automation.util.UtilModule;
import com.ecofactor.qa.automation.util.test.JobValidator;
import com.google.inject.Inject;

/**
 * The Class RoleManagementTest.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
@Guice(modules = { UtilModule.class, DaoModule.class, InsiteModule.class })
@Listeners(JobValidator.class)
public class RoleManagementTest {

    /** The current date. */
    Date currentDate = new Date();

    /** The formatter. */
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");

    /** The date time stamp. */
    String dateTimeStamp = formatter.format(currentDate);

    /** The insite login. */
    @Inject
    private InsiteLogin insiteLogin;

    /** The role management. */
    @Inject
    private RoleManagement roleManagement;

    /** The test log util. */
    @Inject
    private TestLogUtil testLogUtil;

    /** The logger. */
    private static Logger logger = LoggerFactory.getLogger(RoleManagementTest.class);

    /** The start. */
    private long start;

    /**
     * Inits the method.
     * @param param the param
     * @param method the method
     */
    @BeforeMethod(alwaysRun = true)
    public void initMethod(final Object[] param, final Method method) {

        testLogUtil.logStart(method, param);
        start = System.currentTimeMillis();

        try {
            roleManagement.loadPage((String) param[0], (String) param[1]);
        } catch (Throwable e) {
            logger.error("Error in before method " + e.getMessage());
        }
    }

    /**
     * End method.
     * @param method the method
     */
    @AfterMethod(alwaysRun = true)
    public void endMethod(final Method method) {

        final long duration = (System.currentTimeMillis() - start) / 1000;
        testLogUtil.logEnd(method, duration);
    }

    /**
     * End Class.
     */
    @AfterClass(alwaysRun = true)
    public void endClass() {

        try {
            roleManagement.logout();
        } catch (Throwable e) {
            logger.error("Error in after class " + e.getMessage());
        }
    }

    /**
     * End suite.
     */
    @AfterSuite(alwaysRun = true)
    public void endSuite() {

        try {
            roleManagement.end();
        } catch (Throwable e) {
            logger.error("Error in after suite " + e.getMessage());
        }
    }

    /**
     * Test create new role.
     * @param userName the user name
     * @param password the password
     * @param roleName the role name
     * @param roleDescription the role description
     * @param availablePages the available pages
     * @param unAssignedPermission the un assigned permission
     */
    @Test(dataProvider = "createRole", dataProviderClass = RoleDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "sanity" }, priority = 1)
    public void testCreateNewRole(final String userName, final String password,
            final String roleName, final String roleDescription, final String partnerType,
            final String unAssignedPermission) {

        roleManagement.loadPage(userName, password);
        final int partnerValidType = Integer.parseInt(partnerType);
        roleManagement.createNewRole(roleName, roleDescription, unAssignedPermission,
                partnerValidType);
    }

    /**
     * Ecofactor logo link.
     * @param userId the user id
     * @param password the password
     */
    @Test(dataProvider = "validLogin", dataProviderClass = RoleDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "smoke" }, priority = 2)
    public void headerLogoLink(final String userId, final String password) {

        roleManagement.isLogoDisplayed();
    }

    /**
     * Test the welcome text for the logged in user in the insite home page.
     * @param userId the user id
     * @param password the password
     */
    @Test(dataProvider = "validLogin", dataProviderClass = RoleDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "smoke" }, priority = 3)
    public void welcomeText(final String userId, final String password) {

        roleManagement.verifyWelcomeText(userId);
    }

    /**
     * Test the account link in the insite home page.
     * @param userId the user id
     * @param password the password
     */
    @Test(dataProvider = "validLogin", dataProviderClass = RoleDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "smoke" }, priority = 4)
    public void supportLink(final String userId, final String password) {

        roleManagement.clickSupport();
    }

    /**
     * Installation link.
     * @param userId the user id
     * @param password the password
     */
    @Test(dataProvider = "validLogin", dataProviderClass = RoleDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "smoke" }, priority = 5)
    public void installationLink(final String userId, final String password) {

        roleManagement.clickInstallation();
    }

    /**
     * Installation schedule link.
     * @param userId the user id
     * @param password the password
     */
    @Test(dataProvider = "validLogin", dataProviderClass = RoleDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "smoke" }, priority = 9)
    public void installationScheduleLink(final String userId, final String password) {

        roleManagement.clickScheduling();
    }

    /**
     * Installation pre config link.
     * @param userId the user id
     * @param password the password
     */
    @Test(dataProvider = "validLogin", dataProviderClass = RoleDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "smoke" }, priority = 11)
    public void installationPreConfigLink(final String userId, final String password) {

        roleManagement.clickPreConfiguration();
    }

    /**
     * User management link.
     * @param userId the user id
     * @param password the password
     */
    @Test(dataProvider = "validLogin", dataProviderClass = RoleDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "smoke" }, priority = 8)
    public void userManagementLink(final String userId, final String password) {

        roleManagement.clickUserManagement();
    }

    /**
     * Role management link.
     * @param userId the user id
     * @param password the password
     */
    @Test(dataProvider = "validLogin", dataProviderClass = RoleDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "smoke" }, priority = 6)
    public void roleManagementLink(final String userId, final String password) {

        roleManagement.clickRoleManagement();
    }

    /**
     * Demand side management link.
     * @param userId the user id
     * @param password the password
     */
    @Test(dataProvider = "validLogin", dataProviderClass = RoleDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "smoke" }, priority = 7)
    public void demandSideManagementLink(final String userId, final String password) {

        roleManagement.clickDemandSideManagement();
    }

    /**
     * Test the user logout from a insite authenticated page.
     * @param userId the user id
     * @param password the password
     */
    @Test(dataProvider = "validLogin", dataProviderClass = RoleDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "smoke" }, priority = 10)
    public void logout(final String userId, final String password) {

        roleManagement.logout();
    }

    /**
     * Creates the role for system admin.
     * @param userId the user id
     * @param password the password
     * @param roleName the role name
     * @param roleDescription the role description
     * @param partnerType the partner type
     * @param unAssignedPermission the un assigned permission
     */
    @Test(dataProvider = "roleForSystemAdmin", dataProviderClass = RoleDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "sanity" }, priority = 18)
    public void createRoleForSystemAdmin(final String userId, final String password,
            final String roleName, final String roleDescription, final String partnerType,
            final String unAssignedPermission) {

        roleManagement.loadPage(userId, password);
        final int partnerValidType = Integer.parseInt(partnerType);
        roleManagement.createNewRole(roleName, roleDescription, unAssignedPermission,
                partnerValidType);
        roleManagement.clickTopFirstRole();
        roleManagement.clickPermissionsTab();
        roleManagement.verifyPermissionsMode();
    }

    /**
     * Creates the role for installation partner.
     * @param userId the user id
     * @param password the password
     * @param roleName the role name
     * @param roleDescription the role description
     * @param partnerType the partner type
     * @param unAssignedPermission the un assigned permission
     */
    @Test(dataProvider = "roleForInstallation", dataProviderClass = RoleDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "sanity" }, priority = 19)
    public void createRoleForInstallationPartner(final String userId, final String password,
            final String roleName, final String roleDescription, final String partnerType,
            final String unAssignedPermission) {

        roleManagement.loadPage(userId, password);
        final int partnerValidType = Integer.parseInt(partnerType);
        roleManagement.createNewRole(roleName, roleDescription, unAssignedPermission,
                partnerValidType);
        roleManagement.clickTopFirstRole();
        roleManagement.clickPermissionsTab();
        roleManagement.verifyPermissionsMode();
    }

    /**
     * Creates the role for service provider.
     * @param userId the user id
     * @param password the password
     * @param roleName the role name
     * @param roleDescription the role description
     * @param partnerType the partner type
     * @param unAssignedPermission the un assigned permission
     */
    @Test(dataProvider = "roleForService", dataProviderClass = RoleDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "sanity" }, priority = 28)
    public void createRoleForServiceProvider(final String userId, final String password,
            final String roleName, final String roleDescription, final String partnerType,
            final String unAssignedPermission) {

        roleManagement.loadPage(userId, password);
        final int partnerValidType = Integer.parseInt(partnerType);
        roleManagement.createNewRole(roleName, roleDescription, unAssignedPermission,
                partnerValidType);
        roleManagement.clickTopFirstRole();
        roleManagement.clickPermissionsTab();
        roleManagement.verifyPermissionsMode();
    }

    /**
     * Creates the role for utility partner.
     * @param userId the user id
     * @param password the password
     * @param roleName the role name
     * @param roleDescription the role description
     * @param partnerType the partner type
     * @param unAssignedPermission the un assigned permission
     */
    @Test(dataProvider = "roleForUtility", dataProviderClass = RoleDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "sanity" }, priority = 15)
    public void createRoleForUtilityPartner(final String userId, final String password,
            final String roleName, final String roleDescription, final String partnerType,
            final String unAssignedPermission) {

        roleManagement.loadPage(userId, password);
        final int partnerValidType = Integer.parseInt(partnerType);
        roleManagement.createNewRole(roleName, roleDescription, unAssignedPermission,
                partnerValidType);
        roleManagement.clickTopFirstRole();
        roleManagement.clickPermissionsTab();
        roleManagement.verifyPermissionsMode();
    }

    /* Need to select proper type */
    /**
     * Creates the role for customer care.
     * @param userId the user id
     * @param password the password
     * @param roleName the role name
     * @param roleDescription the role description
     * @param partnerType the partner type
     * @param unAssignedPermission the un assigned permission
     */
    @Test(dataProvider = "roleForCustomerCareAccountOnly", dataProviderClass = RoleDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "sanity" }, priority = 16)
    public void createRoleForCustomerCareAccount(final String userId, final String password,
            final String roleName, final String roleDescription, final String partnerType,
            final String unAssignedPermission) {

        roleManagement.loadPage(userId, password);
        final int partnerValidType = Integer.parseInt(partnerType);
        roleManagement.createNewRole(roleName, roleDescription, unAssignedPermission,
                partnerValidType);
        roleManagement.clickTopFirstRole();
        roleManagement.clickPermissionsTab();
        roleManagement.verifyPermissionsMode();
    }

    /* Need to select proper type */
    /**
     * Creates the role for customer care on boarding.
     * @param userId the user id
     * @param password the password
     * @param roleName the role name
     * @param roleDescription the role description
     * @param partnerType the partner type
     * @param unAssignedPermission the un assigned permission
     */
    @Test(dataProvider = "roleForCustomerCareOnBoarding", dataProviderClass = RoleDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "sanity" }, priority = 17)
    public void createRoleForCustomerCareOnBoarding(final String userId, final String password,
            final String roleName, final String roleDescription, final String partnerType,
            final String unAssignedPermission) {

        roleManagement.loadPage(userId, password);
        final int partnerValidType = Integer.parseInt(partnerType);
        roleManagement.createNewRole(roleName, roleDescription, unAssignedPermission,
                partnerValidType);
        roleManagement.clickTopFirstRole();
        roleManagement.clickPermissionsTab();
        roleManagement.verifyPermissionsMode();
    }

    /* Need to select proper type */
    /**
     * Creates the role for cc account on boarding.
     * @param userId the user id
     * @param password the password
     * @param roleName the role name
     * @param roleDescription the role description
     * @param partnerType the partner type
     * @param unAssignedPermission the un assigned permission
     */
    @Test(dataProvider = "roleForCustomerCareOnBoarding", dataProviderClass = RoleDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "sanity" }, priority = 12)
    public void createRoleForCCAccountOnBoarding(final String userId, final String password,
            final String roleName, final String roleDescription, final String partnerType,
            final String unAssignedPermission) {

        roleManagement.loadPage(userId, password);
        final int partnerValidType = Integer.parseInt(partnerType);
        roleManagement.createNewRole(roleName, roleDescription, unAssignedPermission,
                partnerValidType);
        roleManagement.clickTopFirstRole();
        roleManagement.clickPermissionsTab();
        roleManagement.verifyPermissionsMode();
    }

    @Test(dataProvider = "roleForInstaller", dataProviderClass = RoleDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "sanity" }, priority = 13)
    public void createRoleForInstaller(final String userId, final String password,
            final String roleName, final String roleDescription, final String partnerType,
            final String unAssignedPermission) {

        roleManagement.loadPage(userId, password);
        final int partnerValidType = Integer.parseInt(partnerType);
        roleManagement.createNewRole(roleName, roleDescription, unAssignedPermission,
                partnerValidType);
        roleManagement.clickTopFirstRole();
        roleManagement.clickPermissionsTab();
        roleManagement.verifyPermissionsMode();
    }

    /* Need to select proper type */
    /**
     * Creates the role for installer scheduling.
     * @param userId the user id
     * @param password the password
     * @param roleName the role name
     * @param roleDescription the role description
     * @param partnerType the partner type
     * @param unAssignedPermission the un assigned permission
     */
    @Test(dataProvider = "roleForInstallerScheduling", dataProviderClass = RoleDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "sanity" }, priority = 20)
    public void createRoleForInstallerScheduling(final String userId, final String password,
            final String roleName, final String roleDescription, final String partnerType,
            final String unAssignedPermission) {

        roleManagement.loadPage(userId, password);
        final int partnerValidType = Integer.parseInt(partnerType);
        roleManagement.createNewRole(roleName, roleDescription, unAssignedPermission,
                partnerValidType);
        roleManagement.clickTopFirstRole();
        roleManagement.clickPermissionsTab();
        roleManagement.verifyPermissionsMode();
    }

    /* Need to select proper type */
    /**
     * Creates the role for installer onsite.
     * @param userId the user id
     * @param password the password
     * @param roleName the role name
     * @param roleDescription the role description
     * @param partnerType the partner type
     * @param unAssignedPermission the un assigned permission
     */
    @Test(dataProvider = "roleForInstallerOnsite", dataProviderClass = RoleDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "sanity" }, priority = 21)
    public void createRoleForInstallerOnsite(final String userId, final String password,
            final String roleName, final String roleDescription, final String partnerType,
            final String unAssignedPermission) {

        roleManagement.loadPage(userId, password);
        final int partnerValidType = Integer.parseInt(partnerType);
        roleManagement.createNewRole(roleName, roleDescription, unAssignedPermission,
                partnerValidType);
        roleManagement.clickTopFirstRole();
        roleManagement.clickPermissionsTab();
        roleManagement.verifyPermissionsMode();
    }

    /* Need to select proper type */
    /**
     * Creates the role for installer preconfig.
     * @param userId the user id
     * @param password the password
     * @param roleName the role name
     * @param roleDescription the role description
     * @param partnerType the partner type
     * @param unAssignedPermission the un assigned permission
     */
    @Test(dataProvider = "roleForInstallerPreConfig", dataProviderClass = RoleDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "sanity" }, priority = 22)
    public void createRoleForInstallerPreconfig(final String userId, final String password,
            final String roleName, final String roleDescription, final String partnerType,
            final String unAssignedPermission) {

        roleManagement.loadPage(userId, password);
        final int partnerValidType = Integer.parseInt(partnerType);
        roleManagement.createNewRole(roleName, roleDescription, unAssignedPermission,
                partnerValidType);
        roleManagement.clickTopFirstRole();
        roleManagement.clickPermissionsTab();
        roleManagement.verifyPermissionsMode();
    }

    /* Need to select proper type */
    /**
     * Creates the role for ins_ pre_ scheduling.
     * @param userId the user id
     * @param password the password
     * @param roleName the role name
     * @param roleDescription the role description
     * @param partnerType the partner type
     * @param unAssignedPermission the un assigned permission
     */
    @Test(dataProvider = "installerPreconfigWithScheduling", dataProviderClass = RoleDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "sanity" }, priority = 23)
    public void createRoleForIns_Pre_Scheduling(final String userId, final String password,
            final String roleName, final String roleDescription, final String partnerType,
            final String unAssignedPermission) {

        roleManagement.loadPage(userId, password);
        final int partnerValidType = Integer.parseInt(partnerType);
        roleManagement.createNewRole(roleName, roleDescription, unAssignedPermission,
                partnerValidType);
        roleManagement.clickTopFirstRole();
        roleManagement.clickPermissionsTab();
        roleManagement.verifyPermissionsMode();
    }

    /* Need to select proper type */
    /**
     * Creates the role for ins_ scheduling_ onsite.
     * @param userId the user id
     * @param password the password
     * @param roleName the role name
     * @param roleDescription the role description
     * @param partnerType the partner type
     * @param unAssignedPermission the un assigned permission
     */
    @Test(dataProvider = "schedulingWithOnsiteInstall", dataProviderClass = RoleDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "sanity" }, priority = 24)
    public void createRoleForIns_Scheduling_Onsite(final String userId, final String password,
            final String roleName, final String roleDescription, final String partnerType,
            final String unAssignedPermission) {

        roleManagement.loadPage(userId, password);
        final int partnerValidType = Integer.parseInt(partnerType);
        roleManagement.createNewRole(roleName, roleDescription, unAssignedPermission,
                partnerValidType);
        roleManagement.clickTopFirstRole();
        roleManagement.clickPermissionsTab();
        roleManagement.verifyPermissionsMode();
    }

    /* Need to select proper type */
    /**
     * Creates the role for ins_ preconfig_ install.
     * @param userId the user id
     * @param password the password
     * @param roleName the role name
     * @param roleDescription the role description
     * @param partnerType the partner type
     * @param unAssignedPermission the un assigned permission
     */
    @Test(dataProvider = "preconfigWithInstaller", dataProviderClass = RoleDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "sanity" }, priority = 25)
    public void createRoleForIns_Preconfig_Install(final String userId, final String password,
            final String roleName, final String roleDescription, final String partnerType,
            final String unAssignedPermission) {

        roleManagement.loadPage(userId, password);
        final int partnerValidType = Integer.parseInt(partnerType);
        roleManagement.createNewRole(roleName, roleDescription, unAssignedPermission,
                partnerValidType);
        roleManagement.clickTopFirstRole();
        roleManagement.clickPermissionsTab();
        roleManagement.verifyPermissionsMode();
    }

    /* Need to select proper type */
    /**
     * Creates the role for ecofactor contractor.
     * @param userId the user id
     * @param password the password
     * @param roleName the role name
     * @param roleDescription the role description
     * @param partnerType the partner type
     * @param unAssignedPermission the un assigned permission
     */
    @Test(dataProvider = "roleForEcoFactorContractor", dataProviderClass = RoleDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "sanity" }, priority = 26)
    public void createRoleForEcofactorContractor(final String userId, final String password,
            final String roleName, final String roleDescription, final String partnerType,
            final String unAssignedPermission) {

        roleManagement.loadPage(userId, password);
        final int partnerValidType = Integer.parseInt(partnerType);
        roleManagement.createNewRole(roleName, roleDescription, unAssignedPermission,
                partnerValidType);
        roleManagement.clickTopFirstRole();
        roleManagement.clickPermissionsTab();
        roleManagement.verifyPermissionsMode();
    }

    /* Need to select proper type */
    /**
     * Creates the role for utility dr.
     * @param userId the user id
     * @param password the password
     * @param roleName the role name
     * @param roleDescription the role description
     * @param partnerType the partner type
     * @param unAssignedPermission the un assigned permission
     */
    @Test(dataProvider = "roleForUtilityDR", dataProviderClass = RoleDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "sanity" }, priority = 27)
    public void createRoleForUtilityDR(final String userId, final String password,
            final String roleName, final String roleDescription, final String partnerType,
            final String unAssignedPermission) {

        roleManagement.loadPage(userId, password);
        final int partnerValidType = Integer.parseInt(partnerType);
        roleManagement.createNewRole(roleName, roleDescription, unAssignedPermission,
                partnerValidType);
        roleManagement.clickTopFirstRole();
        roleManagement.clickPermissionsTab();
        roleManagement.verifyPermissionsMode();
    }

    /* Need to select proper type */
    /**
     * Creates the role for utility dr look up.
     * @param userId the user id
     * @param password the password
     * @param roleName the role name
     * @param roleDescription the role description
     * @param partnerType the partner type
     * @param unAssignedPermission the un assigned permission
     */
    @Test(dataProvider = "UtilityDRWithAccount", dataProviderClass = RoleDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "sanity" }, priority = 14)
    public void createRoleForUtilityDRLookUp(final String userId, final String password,
            final String roleName, final String roleDescription, final String partnerType,
            final String unAssignedPermission) {

        roleManagement.loadPage(userId, password);
        final int partnerValidType = Integer.parseInt(partnerType);
        roleManagement.createNewRole(roleName, roleDescription, unAssignedPermission,
                partnerValidType);
        roleManagement.clickTopFirstRole();
        roleManagement.clickPermissionsTab();
        roleManagement.verifyPermissionsMode();
    }

    /**
     * Creates the role for conservation partner.
     * @param userId the user id
     * @param password the password
     * @param roleName the role name
     * @param roleDescription the role description
     * @param partnerType the partner type
     * @param unAssignedPermission the un assigned permission
     */
    @Test(dataProvider = "roleForConservation", dataProviderClass = RoleDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "sanity" }, priority = 29)
    public void createRoleForConservationPartner(final String userId, final String password,
            final String roleName, final String roleDescription, final String partnerType,
            final String unAssignedPermission) {

        roleManagement.loadPage(userId, password);
        final int partnerValidType = Integer.parseInt(partnerType);
        roleManagement.createNewRole(roleName, roleDescription, unAssignedPermission,
                partnerValidType);
        roleManagement.clickTopFirstRole();
        roleManagement.clickPermissionsTab();
        roleManagement.verifyPermissionsMode();
    }
}
