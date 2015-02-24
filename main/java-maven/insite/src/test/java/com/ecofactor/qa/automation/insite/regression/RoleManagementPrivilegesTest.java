/*
 * RoleManagementPrivilegesTest.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.insite.regression;

import static com.ecofactor.qa.automation.util.WaitUtil.tinyWait;

import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Guice;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.ecofactor.common.pojo.EcpCoreLSEvent;
import com.ecofactor.common.pojo.Status;
import com.ecofactor.qa.automation.dao.DaoModule;
import com.ecofactor.qa.automation.dao.LSProgramEventDao;
import com.ecofactor.qa.automation.insite.InsiteModule;

import static com.ecofactor.qa.automation.insite.config.InsiteConfig.*;

import com.ecofactor.qa.automation.insite.config.InsiteConfig;
import com.ecofactor.qa.automation.insite.data.RoleDataProvider;
import com.ecofactor.qa.automation.insite.page.DemandSideManagement;
import com.ecofactor.qa.automation.insite.page.InsiteLogin;
import com.ecofactor.qa.automation.insite.page.OnBoard;
import com.ecofactor.qa.automation.insite.page.RoleManagement;
import com.ecofactor.qa.automation.insite.page.SupportLookUp;
import com.ecofactor.qa.automation.insite.page.UploadOneUser;
import com.ecofactor.qa.automation.insite.page.UserManagement;
import com.ecofactor.qa.automation.util.DriverConfig;
import com.ecofactor.qa.automation.util.RerunFailTestAnalyzer;
import com.ecofactor.qa.automation.util.TestLogUtil;
import com.ecofactor.qa.automation.util.UtilModule;
import com.ecofactor.qa.automation.util.WaitUtil;
import com.ecofactor.qa.automation.util.mail.GmailForNewUser;
import com.ecofactor.qa.automation.util.test.JobValidator;
import com.google.inject.Inject;

/**
 * The Class RoleManagementPrivilegesTest.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
@Guice(modules = { UtilModule.class, DaoModule.class, InsiteModule.class })
@Listeners(JobValidator.class)
public class RoleManagementPrivilegesTest {

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

    @Inject
    private DemandSideManagement demandSideManagement;

    /** The test log util. */
    @Inject
    private TestLogUtil testLogUtil;

    /** The logger. */
    private static Logger logger = LoggerFactory.getLogger(RoleManagementTest.class);

    /** The start. */
    private long start;

    /** The user subject. */
    private final String USER_SUBJECT = "Welcome to EcoFactor Insite - Your Temporary Password";

    /** The gmail url. */
    private final String GMAIL_URL = "https://www.google.com/accounts/ServiceLoginAuth?continue=http://gmail.google.com/gmail&service=mail&Email=";

    /** The g password. */
    private final String G_PASSWORD = "Axims0ft";

    @Inject
    private OnBoard onBoard;

    @Inject
    private GmailForNewUser gmail;

    @Inject
    private UserManagement userManagement;

    @Inject
    private UploadOneUser uploadOneUser;

    @Inject
    private InsiteConfig insiteConfig;

    @Inject
    private SupportLookUp supportLookUp;

    @Inject
    private LSProgramEventDao lsProgramEventDao;

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
            if (method.getName() == "accLookUpOnBoardPrivilege"
                    || method.getName() == "comcastPrivilege"
                    || method.getName() == "conservationPrivilege"
                    || method.getName() == "nvePartnerPrivilege") {
                gmail.deleteOldMails((String) param[4], (String) param[5], (String) param[6],
                        (String) param[7]);
                insiteLogin.clearUser();
            }
            insiteLogin.loadPage();
            insiteLogin.login((String) param[0], (String) param[1]);

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
     * Installer privilege.
     * @param userId the user id
     * @param password the password
     * @throws ParseException the parse exception
     */
    @Test(dataProvider = "installerUser", dataProviderClass = RoleDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "sanity" })
    public void installerPrivilege(String userId, String password) {

        Assert.assertFalse(roleManagement.getMenu().size() > 1, "Another Menu Available");
        Assert.assertTrue(roleManagement.isMenuDisplayed(insiteConfig.get(INSTALLATION)),
                "SubMenu Not Displayed");
        Assert.assertTrue(roleManagement.isSubMenuDisplayed(insiteConfig.get(PRE_CONFIGURATION)),
                "SubMenu Not Displayed");
        Assert.assertTrue(roleManagement.isSubMenuDisplayed(insiteConfig.get(ONSITE_INSTALLATION)),
                "SubMenu Not Displayed");
        Assert.assertTrue(roleManagement.isSubMenuDisplayed(insiteConfig.get(SCHEDULING)),
                "SubMenu Not Displayed");
    }

    /**
     * Installer schedule privilege.
     * @param userId the user id
     * @param password the password
     */
    @Test(dataProvider = "scheduleUser", dataProviderClass = RoleDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "sanity" })
    public void installerSchedulePrivilege(String userId, String password) {

        Assert.assertFalse(roleManagement.getMenu().size() > 1, "Another Menu Available");
        Assert.assertTrue(roleManagement.isMenuDisplayed(insiteConfig.get(INSTALLATION)),
                "Menu Not Displayed");
        Assert.assertFalse(roleManagement.getSubMenu().size() > 1, "Another SubMenu Available");
        Assert.assertTrue(roleManagement.isSubMenuDisplayed(insiteConfig.get(SCHEDULING)),
                "SubMenu Not Displayed");
        roleManagement.clickTopFirstUser();
    }

    /**
     * Installer onsite install privilege.
     * @param userId the user id
     * @param password the password
     */
    @Test(dataProvider = "onSiteInstallUser", dataProviderClass = RoleDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "sanity" })
    public void installerOnsiteInstallPrivilege(String userId, String password) {

        Assert.assertFalse(roleManagement.getMenu().size() > 1, "Another Menu Available");
        Assert.assertTrue(roleManagement.isMenuDisplayed(insiteConfig.get(INSTALLATION)),
                "Menu Not Displayed");
        Assert.assertFalse(roleManagement.getSubMenu().size() > 1, "Another SubMenu Available");
        Assert.assertTrue(roleManagement.isSubMenuDisplayed(insiteConfig.get(ONSITE_INSTALLATION)),
                "SubMenu Not Displayed");
    }

    /**
     * Installer pre config privilege.
     * @param userId the user id
     * @param password the password
     */
    @Test(dataProvider = "preConfigUser", dataProviderClass = RoleDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "sanity" })
    public void installerPreConfigPrivilege(String userId, String password) {

        Assert.assertFalse(roleManagement.getMenu().size() > 1, "Another Menu Available");
        Assert.assertTrue(roleManagement.isMenuDisplayed(insiteConfig.get(INSTALLATION)),
                "Menu Not Displayed");
        Assert.assertFalse(roleManagement.getSubMenu().size() > 1, "Another SubMenu Available");
        Assert.assertTrue(roleManagement.isSubMenuDisplayed(insiteConfig.get(PRE_CONFIGURATION)),
                "SubMenu Not Displayed");
    }

    /**
     * Pre config and schedule privilege.
     * @param userId the user id
     * @param password the password
     */
    @Test(dataProvider = "schedulePreConfigUser", dataProviderClass = RoleDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "sanity" })
    public void preConfigAndSchedulePrivilege(String userId, String password) {

        Assert.assertFalse(roleManagement.getMenu().size() > 1, "Another Menu Available");
        Assert.assertTrue(roleManagement.isMenuDisplayed(insiteConfig.get(INSTALLATION)),
                "Menu Not Displayed");
        Assert.assertFalse(roleManagement.getSubMenu().size() > 2, "Another SubMenu Available");
        Assert.assertTrue(roleManagement.isSubMenuDisplayed(insiteConfig.get(SCHEDULING)),
                "SubMenu Not Displayed");
        Assert.assertTrue(roleManagement.isSubMenuDisplayed(insiteConfig.get(PRE_CONFIGURATION)),
                "SubMenu Not Displayed");
    }

    /**
     * Schedule and onsite install privilege.
     * @param userId the user id
     * @param password the password
     */
    @Test(dataProvider = "scheduleOnSiteInstallUser", dataProviderClass = RoleDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "sanity" })
    public void scheduleAndOnsiteInstallPrivilege(String userId, String password) {

        Assert.assertFalse(roleManagement.getMenu().size() > 1, "Another Menu Available");
        Assert.assertTrue(roleManagement.isMenuDisplayed(insiteConfig.get(INSTALLATION)),
                "Menu Not Displayed");
        Assert.assertFalse(roleManagement.getSubMenu().size() > 2, "Another SubMenu Available");
        Assert.assertTrue(roleManagement.isSubMenuDisplayed(insiteConfig.get(SCHEDULING)),
                "SubMenu Not Displayed");
        Assert.assertTrue(roleManagement.isSubMenuDisplayed(insiteConfig.get(ONSITE_INSTALLATION)),
                "SubMenu Not Displayed");
    }

    /**
     * Pre config and onsite install privilege.
     * @param userId the user id
     * @param password the password
     */
    @Test(dataProvider = "preConfigOnSiteInstallUser", dataProviderClass = RoleDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "sanity" })
    public void preConfigAndOnsiteInstallPrivilege(String userId, String password) {

        Assert.assertFalse(roleManagement.getMenu().size() > 1, "Another Menu Available");
        Assert.assertTrue(roleManagement.isMenuDisplayed(insiteConfig.get(INSTALLATION)),
                "Menu Not Displayed");
        Assert.assertFalse(roleManagement.getSubMenu().size() > 2, "Another SubMenu Available");
        Assert.assertTrue(roleManagement.isSubMenuDisplayed(insiteConfig.get(PRE_CONFIGURATION)),
                "SubMenu Not Displayed");
        Assert.assertTrue(roleManagement.isSubMenuDisplayed(insiteConfig.get(ONSITE_INSTALLATION)),
                "SubMenu Not Displayed");
    }

    /**
     * Acc look up only privilege.
     * @param userId the user id
     * @param password the password
     */
    @Test(dataProvider = "accLookUpOnlyPrivilege", dataProviderClass = RoleDataProvider.class, groups = { "sanity" })
    public void accLookUpOnlyPrivilege(String userId, String password) {

        Assert.assertFalse(roleManagement.getMenu().size() > 1, "Another Menu Available");
        Assert.assertTrue(roleManagement.isMenuDisplayed(insiteConfig.get(SUPPORT)),
                "Menu Not Displayed");
        Assert.assertFalse(roleManagement.getSubMenu().size() > 1, "Another SubMenu Available");
        Assert.assertTrue(roleManagement.isSubMenuDisplayed(insiteConfig.get(ACCOUNT_LOOKUP)),
                "SubMenu Not Displayed");
        roleManagement.clickFind();
        Assert.assertTrue(roleManagement.checkEcp(), "Another ECP Available");
    }

    /**
     * Utility dr privilege.
     * @param userId the user id
     * @param password the password
     * @throws ParseException the parse exception
     */
    @Test(dataProvider = "utilityDRPrivilege", dataProviderClass = RoleDataProvider.class, groups = { "sanity" })
    public void utilityDRPrivilege(String userId, String password) throws ParseException {

        Assert.assertFalse(roleManagement.getMenu().size() > 1, "Another Menu Available");
        Assert.assertTrue(roleManagement.isMenuDisplayed(insiteConfig.get(DEMAND_SIDE_MGMNT)),
                "Menu Not Displayed");
        Assert.assertFalse(roleManagement.getSubMenu().size() > 1, "Another SubMenu Available");
        Assert.assertTrue(roleManagement.isSubMenuDisplayed(insiteConfig.get(LOAD_SHAPING)),
                "SubMenu Not Displayed");

        Map<String, Object> eventDetails = new HashMap<String, Object>();
        eventDetails = demandSideManagement.initiateEventCreation("QA Test Aximsoft 2014 New",
                "60", "DRAFT", false);
        if (!eventDetails.isEmpty()) {
            WaitUtil.waitUntil(10000);

            List<EcpCoreLSEvent> ecpCoreLSEvent = lsProgramEventDao.listByEventName(eventDetails
                    .get("eventName").toString(), "DRAFT");
            DriverConfig.setLogString("check if Event Name '" + ecpCoreLSEvent.get(0).getName()
                    + "' is saved in database as draft", true);
            Assert.assertTrue(ecpCoreLSEvent.get(0).getStatus().toString()
                    .equalsIgnoreCase("DRAFT"), "Event status is different");
        }

    }

    /**
     * Acc look up on board privilege.
     * @param userId the user id
     * @param password the password
     * @param firstName the first name
     * @param lastName the last name
     * @param emailUrl the email url
     * @param emailUserName the email user name
     * @param emailPassword the email password
     * @param subject the subject
     * @param emailAddress the email address
     * @param accountUserName the account user name
     * @param activeUser the active user
     * @param partnerType the partner type
     * @param partner the partner
     * @param streetAddress1 the street address1
     * @param streetAddress2 the street address2
     * @param city the city
     * @param state the state
     * @param zip the zip
     * @param country the country
     * @param mobilePhoneNumber the mobile phone number
     * @param homePhoneNumber the home phone number
     * @param fax the fax
     * @param availableRole the available role
     * @param newPassword the new password
     * @throws ParseException the parse exception
     */
    @Test(dataProvider = "accOnBoardingUser", dataProviderClass = RoleDataProvider.class, groups = { "sanity" })
    public void accLookUpOnBoardPrivilege(String userId, String password, final String firstName,
            final String lastName, final String emailUrl, final String emailUserName,
            final String emailPassword, final String subject, final String emailAddress,
            final String accountUserName, final String activeUser, final String partnerType,
            final String partner,

            final String streetAddress1, final String streetAddress2, final String city,
            final String state, final String zip, final String country,
            final String mobilePhoneNumber, final String homePhoneNumber, final String fax,
            final String availableRole, final String newPassword) {

        Assert.assertFalse(roleManagement.getMenu().size() > 3, "Another Menu Available");
        Assert.assertTrue(roleManagement.isMenuDisplayed(insiteConfig.get(SUPPORT)),
                "Menu Not Displayed");
        Assert.assertTrue(roleManagement.isMenuDisplayed(insiteConfig.get(ON_BOARDING)),
                "Menu Not Displayed");
        Assert.assertTrue(roleManagement.isMenuDisplayed(insiteConfig.get(ADMIN)),
                "Menu Not Displayed");
        Assert.assertFalse(roleManagement.getSubMenu().size() > 1, "Another SubMenu Available");
        Assert.assertTrue(roleManagement.isSubMenuDisplayed(insiteConfig.get(ACCOUNT_LOOKUP)),
                "SubMenu Not Displayed");
        roleManagement.clickMenu(insiteConfig.get(ON_BOARDING));
        Assert.assertFalse(roleManagement.getSubMenu().size() > 4, "Another SubMenu Available");
        Assert.assertTrue(roleManagement.isSubMenuDisplayed(insiteConfig.get(ERRORS_TO_BE_FIXED)),
                "SubMenu Not Displayed");
        Assert.assertTrue(roleManagement.isSubMenuDisplayed(insiteConfig.get(BULK_UPLOADS)),
                "SubMenu Not Displayed");
        Assert.assertTrue(roleManagement.isSubMenuDisplayed(insiteConfig.get(HISTORY)),
                "SubMenu Not Displayed");
        Assert.assertTrue(roleManagement.isSubMenuDisplayed(insiteConfig.get(UPLOAD_ONE_USER)),
                "SubMenu Not Displayed");
        roleManagement.clickMenu(insiteConfig.get(ADMIN));
        Assert.assertFalse(roleManagement.getSubMenu().size() > 1, "Another SubMenu Available");
        Assert.assertTrue(roleManagement.isSubMenuDisplayed(insiteConfig.get(USER_MNGMNT)),
                "SubMenu Not Displayed");

        roleManagement.clickMenu(insiteConfig.get(ON_BOARDING));
        roleManagement.clickSubMenu(insiteConfig.get(BULK_UPLOADS));
        String filePath = onBoard.generateFilepath("csv");

        String uploadUserName = onBoard.generateValidCSV(1, filePath, "199");

        onBoard.uploadAndSubmitFile(filePath);

        onBoard.verifyUser(uploadUserName);

        roleManagement.clickSubMenu(insiteConfig.get(UPLOAD_ONE_USER));
        uploadOneUser.populateForm(emailAddress, "199");
        uploadOneUser.validateAndSubmitForm();

        roleManagement.clickMenu(insiteConfig.get(ADMIN));

        currentDate = new Date();
        dateTimeStamp = formatter.format(currentDate);
        userManagement.createNewUser(firstName, lastName, emailAddress, accountUserName
                + dateTimeStamp, activeUser, partnerType, "", streetAddress1, streetAddress2, city,
                state, zip, country, mobilePhoneNumber, homePhoneNumber, fax, availableRole);
        userManagement.logout();
        String temporaryPassword = gmail.getChangedPassword(GMAIL_URL, emailUserName, G_PASSWORD,
                USER_SUBJECT, 0, 0);
        logger.info("Temporary password received-" + temporaryPassword, true);
        userManagement.loadInsiteURL();
        if (insiteLogin.isAuthenticatedUser()) {
            insiteLogin.logout();
        }
        tinyWait();
        DriverConfig.setLogString("Login with the temporary password.", true);
        insiteLogin.login(accountUserName + dateTimeStamp, temporaryPassword);

        insiteLogin.changeNewPassword(newPassword);
        WaitUtil.waitUntil(60000);

        DriverConfig.setLogString("Login with the changed new password, username:"
                + accountUserName + dateTimeStamp + ", password:" + newPassword, true);
        insiteLogin.login(accountUserName + dateTimeStamp, newPassword);
        insiteLogin.verifyLogin(accountUserName + dateTimeStamp);
        insiteLogin.logout();
        insiteLogin.login(userId, password);
        roleManagement.clickFind();
        Assert.assertTrue(roleManagement.checkEcp(), "Another ECP Available");

    }

    /**
     * Cust care on board privilege.
     * @param userId the user id
     * @param password the password
     */
    @Test(dataProvider = "customerCareOnBoardPrivilege", dataProviderClass = RoleDataProvider.class, groups = { "sanity" })
    public void custCareOnBoardPrivilege(String userId, String password) {

        Assert.assertFalse(roleManagement.getMenu().size() > 1, "Another Menu Available");
        Assert.assertTrue(roleManagement.isMenuDisplayed(insiteConfig.get(ON_BOARDING)),
                "Menu Not Displayed");
        Assert.assertFalse(roleManagement.getSubMenu().size() > 4, "Another SubMenu Available");
        Assert.assertTrue(roleManagement.isSubMenuDisplayed(insiteConfig.get(ERRORS_TO_BE_FIXED)),
                "SubMenu Not Displayed");
        Assert.assertTrue(roleManagement.isSubMenuDisplayed(insiteConfig.get(BULK_UPLOADS)),
                "SubMenu Not Displayed");
        Assert.assertTrue(roleManagement.isSubMenuDisplayed(insiteConfig.get(HISTORY)),
                "SubMenu Not Displayed");
        Assert.assertTrue(roleManagement.isSubMenuDisplayed(insiteConfig.get(UPLOAD_ONE_USER)),
                "SubMenu Not Displayed");

        roleManagement.clickSubMenu(insiteConfig.get(BULK_UPLOADS));
        String filePath = onBoard.generateFilepath("csv");

        String uploadUserName = onBoard.generateValidCSV(1, filePath, "199");

        onBoard.uploadAndSubmitFile(filePath);

        onBoard.verifyUser(uploadUserName);

        roleManagement.clickSubMenu(insiteConfig.get(UPLOAD_ONE_USER));
        uploadOneUser.populateForm("user_" + System.currentTimeMillis() + "@gmail.com", "199");
        uploadOneUser.validateAndSubmitForm();
        roleManagement.clickSubMenu("History");
        Assert.assertTrue(
                roleManagement.checkUploadedFileInHistory(insiteConfig.get(UPLOAD_ONE_USER)),
                "File Name Not Displayed");
        String[] fileName = filePath.split("/");
        for (int i = 0; i < fileName.length; i++) {
            if (fileName[i].contains("bulkUploadFile")) {
                Assert.assertTrue(roleManagement.checkUploadedFileInHistory(fileName[i]),
                        "File Name Not Displayed");
                break;
            }
        }
    }

    /**
     * Comcast privilege.
     * @param userId the user id
     * @param password the password
     * @param firstName the first name
     * @param lastName the last name
     * @param emailUrl the email url
     * @param emailUserName the email user name
     * @param emailPassword the email password
     * @param subject the subject
     * @param emailAddress the email address
     * @param accountUserName the account user name
     * @param activeUser the active user
     * @param partnerType the partner type
     * @param partner the partner
     * @param streetAddress1 the street address1
     * @param streetAddress2 the street address2
     * @param city the city
     * @param state the state
     * @param zip the zip
     * @param country the country
     * @param mobilePhoneNumber the mobile phone number
     * @param homePhoneNumber the home phone number
     * @param fax the fax
     * @param availableRole the available role
     * @param newPassword the new password
     */
    @Test(dataProvider = "comcastPrivilege", dataProviderClass = RoleDataProvider.class, groups = { "sanity" })
    public void comcastPrivilege(String userId, String password, final String firstName,
            final String lastName, final String emailUrl, final String emailUserName,
            final String emailPassword, final String subject, final String emailAddress,
            final String accountUserName, final String activeUser, final String partnerType,
            final String partner,

            final String streetAddress1, final String streetAddress2, final String city,
            final String state, final String zip, final String country,
            final String mobilePhoneNumber, final String homePhoneNumber, final String fax,
            final String availableRole, final String newPassword) {

        Assert.assertFalse(roleManagement.getMenu().size() > 5, "Another Menu Available");
        Assert.assertTrue(roleManagement.isMenuDisplayed(insiteConfig.get(SUPPORT)),
                "Menu Not Displayed");
        Assert.assertTrue(roleManagement.isMenuDisplayed(insiteConfig.get(ON_BOARDING)),
                "Menu Not Displayed");
        Assert.assertTrue(roleManagement.isMenuDisplayed(insiteConfig.get(DEMAND_SIDE_MGMNT)),
                "Menu Not Displayed");
        Assert.assertTrue(roleManagement.isMenuDisplayed(insiteConfig.get(ADMIN)),
                "Menu Not Displayed");
        Assert.assertTrue(roleManagement.isMenuDisplayed(insiteConfig.get(INSTALLATION)),
                "Menu Not Displayed");

        Assert.assertFalse(roleManagement.getSubMenu().size() > 1, "Another SubMenu Available");
        Assert.assertTrue(roleManagement.isSubMenuDisplayed(insiteConfig.get(ACCOUNT_LOOKUP)),
                "SubMenu Not Displayed");
        roleManagement.clickMenu(insiteConfig.get(ON_BOARDING));
        Assert.assertFalse(roleManagement.getSubMenu().size() > 3, "Another SubMenu Available");
        Assert.assertTrue(roleManagement.isSubMenuDisplayed(insiteConfig.get(HISTORY)),
                "SubMenu Not Displayed");
        Assert.assertTrue(roleManagement.isSubMenuDisplayed(insiteConfig.get(ERRORS_TO_BE_FIXED)),
                "SubMenu Not Displayed");
        Assert.assertTrue(roleManagement.isSubMenuDisplayed(insiteConfig.get(BULK_UPLOADS)),
                "SubMenu Not Displayed");
        roleManagement.clickMenu(insiteConfig.get(DEMAND_SIDE_MGMNT));
        Assert.assertFalse(roleManagement.getSubMenu().size() > 1, "Another SubMenu Available");
        Assert.assertTrue(roleManagement.isSubMenuDisplayed(insiteConfig.get(LOAD_SHAPING)),
                "SubMenu Not Displayed");
        roleManagement.clickMenu(insiteConfig.get(ADMIN));
        Assert.assertFalse(roleManagement.getSubMenu().size() > 1, "Another SubMenu Available");
        Assert.assertTrue(roleManagement.isSubMenuDisplayed(insiteConfig.get(USER_MNGMNT)),
                "SubMenu Not Displayed");
        roleManagement.clickMenu(insiteConfig.get(INSTALLATION));
        Assert.assertFalse(roleManagement.getSubMenu().size() > 3, "Another SubMenu Available");
        Assert.assertTrue(roleManagement.isSubMenuDisplayed(insiteConfig.get(PRE_CONFIGURATION)),
                "SubMenu Not Displayed");
        Assert.assertTrue(roleManagement.isSubMenuDisplayed(insiteConfig.get(ONSITE_INSTALLATION)),
                "SubMenu Not Displayed");
        Assert.assertTrue(roleManagement.isSubMenuDisplayed(insiteConfig.get(SCHEDULING)),
                "SubMenu Not Displayed");
        roleManagement.clickMenu(insiteConfig.get(SUPPORT));
        roleManagement.clickFind();
        supportLookUp.clickTopFirstUser();
        supportLookUp.clickEnergyEfficiencyCheckBox();
        Assert.assertTrue(supportLookUp.isLocationNameDisplayed(), "Location Name Not Displayed");
        roleManagement.clickMenu(insiteConfig.get(ADMIN));

        currentDate = new Date();
        dateTimeStamp = formatter.format(currentDate);
        userManagement.createNewUser(firstName, lastName, emailAddress, accountUserName
                + dateTimeStamp, activeUser, partnerType, "", streetAddress1, streetAddress2, city,
                state, zip, country, mobilePhoneNumber, homePhoneNumber, fax, availableRole);
        userManagement.logout();
        String temporaryPassword = gmail.getChangedPassword(GMAIL_URL, emailUserName, G_PASSWORD,
                USER_SUBJECT, 0, 0);
        logger.info("Temporary password received-" + temporaryPassword, true);
        userManagement.loadInsiteURL();
        if (insiteLogin.isAuthenticatedUser()) {
            insiteLogin.logout();
        }
        tinyWait();
        DriverConfig.setLogString("Login with the temporary password.", true);
        insiteLogin.login(accountUserName + dateTimeStamp, temporaryPassword);

        insiteLogin.changeNewPassword(newPassword);
        WaitUtil.waitUntil(60000);

        DriverConfig.setLogString("Login with the changed new password, username:"
                + accountUserName + dateTimeStamp + ", password:" + newPassword, true);
        insiteLogin.login(accountUserName + dateTimeStamp, newPassword);
        insiteLogin.verifyLogin(accountUserName + dateTimeStamp);
    }

    /**
     * Conservation privilege.
     * @param userId the user id
     * @param password the password
     * @param firstName the first name
     * @param lastName the last name
     * @param emailUrl the email url
     * @param emailUserName the email user name
     * @param emailPassword the email password
     * @param subject the subject
     * @param emailAddress the email address
     * @param accountUserName the account user name
     * @param activeUser the active user
     * @param partnerType the partner type
     * @param partner the partner
     * @param streetAddress1 the street address1
     * @param streetAddress2 the street address2
     * @param city the city
     * @param state the state
     * @param zip the zip
     * @param country the country
     * @param mobilePhoneNumber the mobile phone number
     * @param homePhoneNumber the home phone number
     * @param fax the fax
     * @param availableRole the available role
     * @param newPassword the new password
     * @throws ParseException the parse exception
     */
    @Test(dataProvider = "conservationPrivilege", dataProviderClass = RoleDataProvider.class, groups = { "sanity" })
    public void conservationPrivilege(String userId, String password, final String firstName,
            final String lastName, final String emailUrl, final String emailUserName,
            final String emailPassword, final String subject, final String emailAddress,
            final String accountUserName, final String activeUser, final String partnerType,
            final String partner,

            final String streetAddress1, final String streetAddress2, final String city,
            final String state, final String zip, final String country,
            final String mobilePhoneNumber, final String homePhoneNumber, final String fax,
            final String availableRole, final String newPassword) throws ParseException {

        Assert.assertFalse(roleManagement.getMenu().size() > 5, "Another Menu Available");
        Assert.assertTrue(roleManagement.isMenuDisplayed(insiteConfig.get(SUPPORT)),
                "Menu Not Displayed");
        Assert.assertTrue(roleManagement.isMenuDisplayed(insiteConfig.get(ON_BOARDING)),
                "Menu Not Displayed");
        Assert.assertTrue(roleManagement.isMenuDisplayed(insiteConfig.get(DEMAND_SIDE_MGMNT)),
                "Menu Not Displayed");
        Assert.assertTrue(roleManagement.isMenuDisplayed(insiteConfig.get(ADMIN)),
                "Menu Not Displayed");
        Assert.assertTrue(roleManagement.isMenuDisplayed(insiteConfig.get(INSTALLATION)),
                "Menu Not Displayed");

        Assert.assertFalse(roleManagement.getSubMenu().size() > 1, "Another SubMenu Available");
        Assert.assertTrue(roleManagement.isSubMenuDisplayed(insiteConfig.get(ACCOUNT_LOOKUP)),
                "SubMenu Not Displayed");
        roleManagement.clickMenu(insiteConfig.get(ON_BOARDING));
        Assert.assertFalse(roleManagement.getSubMenu().size() > 3, "Another SubMenu Available");
        Assert.assertTrue(roleManagement.isSubMenuDisplayed(insiteConfig.get(HISTORY)),
                "SubMenu Not Displayed");
        Assert.assertTrue(roleManagement.isSubMenuDisplayed(insiteConfig.get(ERRORS_TO_BE_FIXED)),
                "SubMenu Not Displayed");
        Assert.assertTrue(roleManagement.isSubMenuDisplayed(insiteConfig.get(BULK_UPLOADS)),
                "SubMenu Not Displayed");
        roleManagement.clickMenu(insiteConfig.get(DEMAND_SIDE_MGMNT));
        Assert.assertFalse(roleManagement.getSubMenu().size() > 1, "Another SubMenu Available");
        Assert.assertTrue(roleManagement.isSubMenuDisplayed(insiteConfig.get(LOAD_SHAPING)),
                "SubMenu Not Displayed");
        roleManagement.clickMenu(insiteConfig.get(ADMIN));
        Assert.assertFalse(roleManagement.getSubMenu().size() > 1, "Another SubMenu Available");
        Assert.assertTrue(roleManagement.isSubMenuDisplayed(insiteConfig.get(USER_MNGMNT)),
                "SubMenu Not Displayed");
        roleManagement.clickMenu(insiteConfig.get(INSTALLATION));
        Assert.assertFalse(roleManagement.getSubMenu().size() > 3, "Another SubMenu Available");
        Assert.assertTrue(roleManagement.isSubMenuDisplayed(insiteConfig.get(PRE_CONFIGURATION)),
                "SubMenu Not Displayed");
        Assert.assertTrue(roleManagement.isSubMenuDisplayed(insiteConfig.get(ONSITE_INSTALLATION)),
                "SubMenu Not Displayed");
        Assert.assertTrue(roleManagement.isSubMenuDisplayed(insiteConfig.get(SCHEDULING)),
                "SubMenu Not Displayed");
        roleManagement.clickMenu(insiteConfig.get(SUPPORT));
        roleManagement.clickFind();
        tinyWait();
        roleManagement.clickTopFirstUser();
        Assert.assertTrue(supportLookUp.getEcp().contains("199"), "ECP Not Available");
        roleManagement.clickMenu(insiteConfig.get(ON_BOARDING));
        roleManagement.clickSubMenu(insiteConfig.get(BULK_UPLOADS));
        String filePath = onBoard.generateFilepath("csv");

        String uploadUserName = onBoard.generateValidCSV(1, filePath, "199");

        onBoard.uploadAndSubmitFile(filePath);

        onBoard.verifyUser(uploadUserName);

        roleManagement.clickMenu(insiteConfig.get(DEMAND_SIDE_MGMNT));
        Map<String, Object> eventDetails = new HashMap<String, Object>();

        eventDetails = demandSideManagement.initiateEventCreation("QA Test Summer 2014_1", "60",
                Status.SCHEDULED.toString(), false);
        WaitUtil.waitUntil(150000);
        formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
        /* List<EcpCoreLSEvent> ecpCoreLSEvent = */lsProgramEventDao.listByEventName(eventDetails
                .get("eventName").toString(), Status.ACTIVE.toString());
        DriverConfig.setLogString("Verifying with DB.", true);
        demandSideManagement.switchdefaultContent();
        // DriverConfig.setLogString("check the event status is 'scheduled'.", true);
        // Assert.assertTrue(ecpCoreLSEvent.get(0).getStatus().equals(Status.ACTIVE),
        // "Event status is different");
        DriverConfig.setLogString("Verified.", true);
        roleManagement.clickUserManagement();
        Assert.assertFalse(userManagement.isRoleAvailable("Ecofactor System Admin"),
                "Role Should Not Be Displayed");
        roleManagement.clickMenu(insiteConfig.get(ADMIN));
        currentDate = new Date();
        dateTimeStamp = formatter.format(currentDate);
        userManagement.createNewUser(firstName, lastName, emailAddress, accountUserName
                + dateTimeStamp, activeUser, partnerType, "", streetAddress1, streetAddress2, city,
                state, zip, country, mobilePhoneNumber, homePhoneNumber, fax, availableRole);
        userManagement.logout();
        String temporaryPassword = gmail.getChangedPassword(GMAIL_URL, emailUserName, G_PASSWORD,
                USER_SUBJECT, 0, 0);
        logger.info("Temporary password received-" + temporaryPassword, true);
        userManagement.loadInsiteURL();
        if (insiteLogin.isAuthenticatedUser()) {
            insiteLogin.logout();
        }
        tinyWait();
        DriverConfig.setLogString("Login with the temporary password.", true);
        insiteLogin.login(accountUserName + dateTimeStamp, temporaryPassword);

        insiteLogin.changeNewPassword(newPassword);
        WaitUtil.waitUntil(60000);

        DriverConfig.setLogString("Login with the changed new password, username:"
                + accountUserName + dateTimeStamp + ", password:" + newPassword, true);
        insiteLogin.login(accountUserName + dateTimeStamp, newPassword);
        insiteLogin.verifyLogin(accountUserName + dateTimeStamp);

    }

    /**
     * Nve partner privilege.
     * @param userId the user id
     * @param password the password
     * @param firstName the first name
     * @param lastName the last name
     * @param emailUrl the email url
     * @param emailUserName the email user name
     * @param emailPassword the email password
     * @param subject the subject
     * @param emailAddress the email address
     * @param accountUserName the account user name
     * @param activeUser the active user
     * @param partnerType the partner type
     * @param partner the partner
     * @param streetAddress1 the street address1
     * @param streetAddress2 the street address2
     * @param city the city
     * @param state the state
     * @param zip the zip
     * @param country the country
     * @param mobilePhoneNumber the mobile phone number
     * @param homePhoneNumber the home phone number
     * @param fax the fax
     * @param availableRole the available role
     * @param newPassword the new password
     * @throws ParseException the parse exception
     */
    @Test(dataProvider = "nvePrivilege", dataProviderClass = RoleDataProvider.class, groups = { "sanity" })
    public void nvePartnerPrivilege(String userId, String password, final String firstName,
            final String lastName, final String emailUrl, final String emailUserName,
            final String emailPassword, final String subject, final String emailAddress,
            final String accountUserName, final String activeUser, final String partnerType,
            final String partner,

            final String streetAddress1, final String streetAddress2, final String city,
            final String state, final String zip, final String country,
            final String mobilePhoneNumber, final String homePhoneNumber, final String fax,
            final String availableRole, final String newPassword) throws ParseException {

        Assert.assertFalse(roleManagement.getMenu().size() > 5, "Another Menu Available");
        Assert.assertTrue(roleManagement.isMenuDisplayed(insiteConfig.get(SUPPORT)),
                "Menu Not Displayed");
        Assert.assertTrue(roleManagement.isMenuDisplayed(insiteConfig.get(ON_BOARDING)),
                "Menu Not Displayed");
        Assert.assertTrue(roleManagement.isMenuDisplayed(insiteConfig.get(DEMAND_SIDE_MGMNT)),
                "Menu Not Displayed");
        Assert.assertTrue(roleManagement.isMenuDisplayed(insiteConfig.get(ADMIN)),
                "Menu Not Displayed");
        Assert.assertTrue(roleManagement.isMenuDisplayed(insiteConfig.get(INSTALLATION)),
                "Menu Not Displayed");

        Assert.assertFalse(roleManagement.getSubMenu().size() > 1, "Another SubMenu Available");
        Assert.assertTrue(roleManagement.isSubMenuDisplayed(insiteConfig.get(ACCOUNT_LOOKUP)),
                "SubMenu Not Displayed");
        roleManagement.clickMenu(insiteConfig.get(ON_BOARDING));
        Assert.assertFalse(roleManagement.getSubMenu().size() > 4, "Another SubMenu Available");
        Assert.assertTrue(roleManagement.isSubMenuDisplayed(insiteConfig.get(HISTORY)),
                "SubMenu Not Displayed");
        Assert.assertTrue(roleManagement.isSubMenuDisplayed(insiteConfig.get(ERRORS_TO_BE_FIXED)),
                "SubMenu Not Displayed");
        Assert.assertTrue(roleManagement.isSubMenuDisplayed(insiteConfig.get(BULK_UPLOADS)),
                "SubMenu Not Displayed");
        Assert.assertTrue(roleManagement.isSubMenuDisplayed(insiteConfig.get(UPLOAD_ONE_USER)),
                "SubMenu Not Displayed");
        roleManagement.clickMenu(insiteConfig.get(DEMAND_SIDE_MGMNT));
        Assert.assertFalse(roleManagement.getSubMenu().size() > 1, "Another SubMenu Available");
        Assert.assertTrue(roleManagement.isSubMenuDisplayed(insiteConfig.get(LOAD_SHAPING)),
                "SubMenu Not Displayed");
        roleManagement.clickMenu(insiteConfig.get(ADMIN));
        Assert.assertFalse(roleManagement.getSubMenu().size() > 1, "Another SubMenu Available");
        Assert.assertTrue(roleManagement.isSubMenuDisplayed(insiteConfig.get(USER_MNGMNT)),
                "SubMenu Not Displayed");

        roleManagement.clickMenu(insiteConfig.get(SUPPORT));
        roleManagement.clickFind();
        roleManagement.clickTopFirstUser();
        tinyWait();
        Assert.assertTrue(supportLookUp.getEcp().contains("205"), "ECP Not Available");
        roleManagement.clickMenu(insiteConfig.get(ON_BOARDING));
        roleManagement.clickSubMenu(insiteConfig.get(BULK_UPLOADS));
        String filePath = onBoard.generateFilepath("csv");

        String uploadUserName = onBoard.generateValidCSV(1, filePath, "205");

        onBoard.uploadAndSubmitFile(filePath);

        onBoard.verifyUser(uploadUserName);

        roleManagement.clickMenu(insiteConfig.get(DEMAND_SIDE_MGMNT));
        Map<String, Object> eventDetails = new HashMap<String, Object>();

        eventDetails = demandSideManagement.initiateEventCreation("NVE Testing LS 2014", "60",
                Status.SCHEDULED.toString(), false);
        WaitUtil.waitUntil(150000);
        formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
        /* List<EcpCoreLSEvent> ecpCoreLSEvent = */lsProgramEventDao.listByEventName(eventDetails
                .get("eventName").toString(), Status.ACTIVE.toString());
        demandSideManagement.switchdefaultContent();
        // DriverConfig.setLogString("check the event status is 'scheduled'.", true);
        // Assert.assertTrue(ecpCoreLSEvent.get(0).getStatus().equals(Status.ACTIVE),
        // "Event status is different");

        roleManagement.clickMenu(insiteConfig.get(ADMIN));
        Assert.assertFalse(userManagement.isRoleAvailable("Ecofactor System Admin"),
                "Role Should Not Be Displayed");
        roleManagement.clickMenu(insiteConfig.get(ADMIN));
        currentDate = new Date();
        dateTimeStamp = formatter.format(currentDate);
        userManagement.createNewUser(firstName, lastName, emailAddress, accountUserName
                + dateTimeStamp, activeUser, partnerType, "", streetAddress1, streetAddress2, city,
                state, zip, country, mobilePhoneNumber, homePhoneNumber, fax, availableRole);
        userManagement.logout();
        String temporaryPassword = gmail.getChangedPassword(GMAIL_URL, emailUserName, G_PASSWORD,
                USER_SUBJECT, 0, 0);
        logger.info("Temporary password received-" + temporaryPassword, true);
        userManagement.loadInsiteURL();
        if (insiteLogin.isAuthenticatedUser()) {
            insiteLogin.logout();
        }
        tinyWait();
        DriverConfig.setLogString("Login with the temporary password.", true);
        insiteLogin.login(accountUserName + dateTimeStamp, temporaryPassword);

        insiteLogin.changeNewPassword(newPassword);
        WaitUtil.waitUntil(60000);

        DriverConfig.setLogString("Login with the changed new password, username:"
                + accountUserName + dateTimeStamp + ", password:" + newPassword, true);
        insiteLogin.login(accountUserName + dateTimeStamp, newPassword);
        insiteLogin.verifyLogin(accountUserName + dateTimeStamp);

    }

    /**
     * Admin privilege.
     * @param userId the user id
     * @param password the password
     * @param tstatId the tstat id
     */
    @Test(dataProvider = "adminPrivilege", dataProviderClass = RoleDataProvider.class, groups = { "sanity" })
    public void adminPrivilege(String userId, String password, String tstatId) {

        Assert.assertTrue(roleManagement.isMenuDisplayed(insiteConfig.get(SUPPORT)),
                "Menu Not Displayed");
        Assert.assertTrue(roleManagement.isMenuDisplayed(insiteConfig.get(ON_BOARDING)),
                "Menu Not Displayed");
        Assert.assertTrue(roleManagement.isMenuDisplayed(insiteConfig.get(DEMAND_SIDE_MGMNT)),
                "Menu Not Displayed");
        Assert.assertTrue(roleManagement.isMenuDisplayed(insiteConfig.get(ADMIN)),
                "Menu Not Displayed");
        Assert.assertTrue(roleManagement.isMenuDisplayed(insiteConfig.get(INSTALLATION)),
                "Menu Not Displayed");
        Assert.assertTrue(roleManagement.isMenuDisplayed(insiteConfig.get(ECOFACTOR)),
                "Menu Not Displayed");

        roleManagement.clickMenu(insiteConfig.get(ADMIN));
        Assert.assertTrue(roleManagement.isSubMenuDisplayed(insiteConfig.get(USER_MNGMNT)),
                "SubMenu Not Displayed");
        Assert.assertTrue(roleManagement.isSubMenuDisplayed(insiteConfig.get(ROLE_MNGMNT)),
                "SubMenu Not Displayed");
        Assert.assertTrue(roleManagement.isSubMenuDisplayed(insiteConfig.get(PARTNER_MNGMNT)),
                "SubMenu Not Displayed");
        Assert.assertTrue(roleManagement.isSubMenuDisplayed(insiteConfig.get(ECP_CORE_MNGMNT)),
                "SubMenu Not Displayed");

        roleManagement.clickMenu(insiteConfig.get(DEMAND_SIDE_MGMNT));
        Assert.assertTrue(roleManagement.isSubMenuDisplayed(insiteConfig.get(LOAD_SHAPING)),
                "SubMenu Not Displayed");

        roleManagement.clickMenu(insiteConfig.get(ON_BOARDING));
        Assert.assertTrue(roleManagement.isSubMenuDisplayed(insiteConfig.get(HISTORY)),
                "SubMenu Not Displayed");
        Assert.assertTrue(roleManagement.isSubMenuDisplayed(insiteConfig.get(ERRORS_TO_BE_FIXED)),
                "SubMenu Not Displayed");
        Assert.assertTrue(roleManagement.isSubMenuDisplayed(insiteConfig.get(BULK_UPLOADS)),
                "SubMenu Not Displayed");
        Assert.assertTrue(roleManagement.isSubMenuDisplayed(insiteConfig.get(UPLOAD_ONE_USER)),
                "SubMenu Not Displayed");

        roleManagement.clickMenu(insiteConfig.get(ECOFACTOR));
        roleManagement.setTstatId(tstatId);
        roleManagement.clickSubscribe();
        tinyWait();
        roleManagement.getResult();
        roleManagement.clickUnSubscribe();
        tinyWait();
        roleManagement.getResult();

    }

}
