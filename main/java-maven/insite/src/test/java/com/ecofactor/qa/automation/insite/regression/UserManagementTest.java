/*
 * UserManagementTest.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.insite.regression;

import static com.ecofactor.qa.automation.util.WaitUtil.*;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
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

import com.ecofactor.qa.automation.dao.DaoModule;
import com.ecofactor.qa.automation.dao.PartnerAccountUserDao;
import com.ecofactor.qa.automation.insite.InsiteModule;
import com.ecofactor.qa.automation.insite.data.InsiteSupportDataProvider;
import com.ecofactor.qa.automation.insite.data.UserDataProvider;
import com.ecofactor.qa.automation.insite.page.InsiteLogin;
import com.ecofactor.qa.automation.insite.page.PartnerManagement;
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
 * The Class UserManagementTest.
 * @author $Author: vraj $
 * @version $Rev: 33520 $ $Date: 2015-01-20 09:49:39 +0530 (Tue, 20 Jan 2015) $
 */
@Guice(modules = { UtilModule.class, InsiteModule.class, DaoModule.class })
@Listeners(JobValidator.class)
public class UserManagementTest {

    /** The current date. */
    Date currentDate = new Date();

    /** The formatter. */
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");

    /** The date time stamp. */
    String dateTimeStamp = formatter.format(currentDate);

    /** The insite login. */
    @Inject
    private InsiteLogin insiteLogin;

    /** The user management. */
    @Inject
    private UserManagement userManagement;

    /** The partner account user dao. */
    @Inject
    private PartnerAccountUserDao partnerAccountUserDao;

    /** The gmail. */
    @Inject
    private GmailForNewUser gmail;

    /** The test log util. */
    @Inject
    private TestLogUtil testLogUtil;
    
    /** The partner management. */
    @Inject
    private PartnerManagement partnerManagement;

    /** The first name key. */
    private final String FIRST_NAME_KEY = "firstName";

    /** The last name key. */
    private final String LAST_NAME_KEY = "lastName";

    /** The email key. */
    private final String EMAIL_KEY = "email";

    /** The user status key. */
    private final String USER_STATUS_KEY = "userStatus";

    /** The user subject. */
    private final String USER_SUBJECT = "Welcome to EcoFactor Insite - Your Temporary Password";

    /** The resetpassword subject. */
    private final String RESETPASSWORD_SUBJECT = "Your EcoFactor account password has been reset";

    /** The gmail url. */
    private final String GMAIL_URL = "https://www.google.com/accounts/ServiceLoginAuth?continue=http://gmail.google.com/gmail&service=mail&Email=";

    /** The g password. */
    private final String G_PASSWORD = "Axims0ft";

    /** The logger. */
    private static Logger logger = LoggerFactory.getLogger(UserManagementTest.class);

    /** The start. */
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

            if (method.getName() == "testResetPswd" || method.getName() == "testCreateNewUser") {
                gmail.deleteOldMails((String) param[4], (String) param[5], (String) param[6],
                        (String) param[7]);
                insiteLogin.clearUser();
            }

            userManagement.loadPage((String) param[0], (String) param[1]);
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
     * End Class.
     */
    @AfterClass(alwaysRun = true)
    public void endClass() {

        try {
            userManagement.logout();
        } catch (Throwable e) {
            logger.error("Error in after class method " + e.getMessage());
        }
    }

    /**
     * End suite.
     */
    @AfterSuite(alwaysRun = true)
    public void endSuite() {

        try {
            userManagement.end();
        } catch (Throwable e) {
            logger.error("Error in after suite method " + e.getMessage());
        }
    }

    /**
     * <b>Create new User test case</b>
     * <ol>
     * <li>Navigate to User Management page and fill the details</li>
     * <li>Populate the values in Account, Contact Information and Role tab</li>
     * <li>Finally Click on Save button and verify the alert is popped out.</li>
     * <ol>
     * @param userName the user name
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

    @Test(dataProvider = "createUser", dataProviderClass = UserDataProvider.class, groups = { "sanity" }, priority = 4)
    public void testCreateNewUser(final String userName, final String password,
            final String firstName, final String lastName, final String emailUrl,
            final String emailUserName, final String emailPassword, final String subject,
            final String emailAddress, final String accountUserName, final String activeUser,
            final String partnerType, final String partner,

            final String streetAddress1, final String streetAddress2, final String city,
            final String state, final String zip, final String country,
            final String mobilePhoneNumber, final String homePhoneNumber, final String fax,
            final String availableRole, final String newPassword) {

        currentDate = new Date();
        dateTimeStamp = formatter.format(currentDate);
        userManagement.createNewUser(firstName, lastName, emailAddress, accountUserName
                + dateTimeStamp, activeUser, partnerType, partner, streetAddress1, streetAddress2,
                city, state, zip, country, mobilePhoneNumber, homePhoneNumber, fax, availableRole);
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
     * Click reset for create new user.
     * @param userName the user name
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
     */
    @Test(dataProvider = "resetNewUser", dataProviderClass = UserDataProvider.class, groups = { "sanity" })
    public void clickResetForCreateNewUser(final String userName, final String password,
            final String firstName, final String lastName, final String emailUrl,
            final String emailUserName, final String emailPassword, final String subject,
            final String emailAddress, final String accountUserName, final String activeUser,
            final String partnerType, final String partner,

            final String streetAddress1, final String streetAddress2, final String city,
            final String state, final String zip, final String country,
            final String mobilePhoneNumber, final String homePhoneNumber, final String fax,
            final String availableRole) {

        userManagement.checkForUserReset(DriverConfig.getDriver(), firstName, lastName,
                emailAddress, accountUserName, activeUser, partnerType, partner, streetAddress1,
                streetAddress2, city, state, zip, country, mobilePhoneNumber, homePhoneNumber, fax,
                availableRole);
    }

    /**
     * Click cancel for create new user.
     * @param userName the user name
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
     */
    @Test(dataProvider = "resetNewUser", dataProviderClass = UserDataProvider.class, groups = { "sanity" })
    public void clickCancelForCreateNewUser(final String userName, final String password,
            final String firstName, final String lastName, final String emailUrl,
            final String emailUserName, final String emailPassword, final String subject,
            final String emailAddress, final String accountUserName, final String activeUser,
            final String partnerType, final String partner,

            final String streetAddress1, final String streetAddress2, final String city,
            final String state, final String zip, final String country,
            final String mobilePhoneNumber, final String homePhoneNumber, final String fax,
            final String availableRole) {

        userManagement.checkForUserCancel(DriverConfig.getDriver(), firstName, lastName,
                emailAddress, accountUserName, activeUser, partnerType, partner, streetAddress1,
                streetAddress2, city, state, zip, country, mobilePhoneNumber, homePhoneNumber, fax,
                availableRole);
    }

    /**
     * Test reset pswd.
     * @param userName the user name
     * @param password the password
     * @param searchName the search name
     * @param resetPswdUserName the reset pswd user name
     * @param emailUrl the email url
     * @param emailUserName the email user name
     * @param emailPassword the email password
     * @param subject the subject
     * @param newPassword the new password
     */
    @Test(dataProvider = "resetPswd", dataProviderClass = UserDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "sanity" }, priority = 3)
    public void testResetPswd(final String userName, final String password,
            final String searchName, final String resetPswdUserName, final String emailUrl,
            final String emailUserName, final String emailPassword, final String subject,
            final String newPassword) {

        userManagement.searchByName(searchName);
        userManagement.clickTopFirstUser();
        userManagement.checkResetPswd(GMAIL_URL, emailUserName, G_PASSWORD, RESETPASSWORD_SUBJECT,
                searchName, resetPswdUserName, newPassword);

    }

    /**
     * Verify account details.
     * @param userName the user name
     * @param password the password
     */
    @Test(dataProvider = "validLoginForUI", dataProviderClass = UserDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "ui" })
    public void verifyAccountDetails(final String userName, final String password,
            final String searchEmailId) {

        userManagement.searchByEmail(searchEmailId);
        tinyWait();
        userManagement.clickTopFirstUser();
        userManagement.verifyAccountDetails();
    }

    /**
     * Verify role details.
     * @param userName the user name
     * @param password the password
     */
    @Test(dataProvider = "validLoginForUI", dataProviderClass = UserDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "ui" })
    public void verifyRoleDetails(final String userName, final String password,
            final String searchEmailId) {

        userManagement.searchByEmail(searchEmailId);
        tinyWait();
        userManagement.clickTopFirstUser();
        userManagement.verifyRoleDetails();
    }

    /**
     * Verify save link.
     * @param userName the user name
     * @param password the password
     */
    @Test(dataProvider = "validLoginForUI", dataProviderClass = UserDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "ui" })
    public void verifySaveLink(final String userName, final String password,
            final String searchEmailId) {

        userManagement.searchByEmail(searchEmailId);
        tinyWait();
        userManagement.clickTopFirstUser();
        userManagement.clickEdit();
        userManagement.clickSave();
    }

    /**
     * Edits the and save.
     * @param userName the user name
     * @param password the password
     * @param firstName the first name
     * @param lastName the last name
     * @param emailAddress the email address
     * @param activeUser the active user
     */
    @Test(dataProvider = "editUserAccountDetails", dataProviderClass = UserDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "sanity" }, priority = 1)
    public void editAndSave(final String userName, final String password, final String searchEmail,
            final String firstName, final String lastName, final String emailAddress,
            final String activeUser) {

        userManagement.searchByEmail(searchEmail);
        tinyWait();
        userManagement.clickTopFirstUser();
        userManagement.clickEdit();
        Map<String, String> accountDetailMap = userManagement.getAccountDetails();
        userManagement.changeAccountDetails(firstName, lastName, emailAddress, activeUser);
        userManagement.clickSave();
        userManagement.searchByEmail(searchEmail);
        tinyWait();
        userManagement.clickTopFirstUser();
        userManagement.clickEdit();
        userManagement.changeAccountDetails(accountDetailMap.get(FIRST_NAME_KEY),
                accountDetailMap.get(LAST_NAME_KEY), accountDetailMap.get(EMAIL_KEY),
                accountDetailMap.get(USER_STATUS_KEY));
        userManagement.clickSave();
    }

    /**
     * Click edit.
     * @param userName the user name
     * @param password the password
     */
    @Test(dataProvider = "validLogin", dataProviderClass = UserDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "ui" })
    public void clickEdit(final String userName, final String password) {

        tinyWait();
        userManagement.clickTopFirstUser();
        userManagement.clickEdit();
        tinyWait();
        userManagement.verifySaveButton();
    }

    /**
     * Verify nve user.
     * @param userName the user name
     * @param password the password
     */
    @Test(dataProvider = "supportTabForNVEsuer", dataProviderClass = InsiteSupportDataProvider.class, groups = { "sanity" })
    public void verifyNVEUser(final String userName, final String password) {

        final int partnerId = partnerAccountUserDao.getPartnerAccountByAccessLogin(userName);
        userManagement.iterateFirstPageAndVerifyNVEUser(partnerId);
    }

    /**
     * Test assign role to existing user.
     * @param userName the user name
     * @param password the password
     * @param accountSearchName the account search name
     * @param accountUserName the account user name
     */
    @Test(dataProvider = "assignRoleToUser", dataProviderClass = UserDataProvider.class, groups = { "sanity" }, priority = 2)
    public void testAssignRoleToExistingUser(final String userName, final String password,
            final String accountSearchName, final String accountUserName) {

        userManagement.searchGivenUser(accountSearchName);
        userManagement.verifyUserAndClickEditButton(accountSearchName);
        String assignedRole = userManagement.assignRoleToUser(accountSearchName);
        userManagement.saveUsermanagement();
        userManagement.searchGivenUser(accountSearchName);
        userManagement.verifyUserAndClickEditButton(accountSearchName);
        userManagement.verifyAndReAssignRole(assignedRole);
        userManagement.saveUsermanagement();
    }

    /**
     * Ecofactor logo link.
     * @param userId the user id
     * @param password the password
     */
    @Test(dataProvider = "validLogin", dataProviderClass = UserDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "smoke" })
    public void headerLogoLink(final String userId, final String password) {

        userManagement.isLogoDisplayed();
    }

    /**
     * Test the welcome text for the logged in user in the insite home page.
     * @param userId the user id
     * @param password the password
     */
    @Test(dataProvider = "validLogin", dataProviderClass = UserDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "smoke" })
    public void welcomeText(final String userId, final String password) {

        userManagement.verifyWelcomeText(userId);
    }

    /**
     * Test the account link in the insite home page.
     * @param userId the user id
     * @param password the password
     */
    @Test(dataProvider = "validLogin", dataProviderClass = UserDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "smoke" })
    public void supportLink(final String userId, final String password) {

        userManagement.clickSupport();
    }

    /**
     * Installation link.
     * @param userId the user id
     * @param password the password
     */
    @Test(dataProvider = "validLogin", dataProviderClass = UserDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "smoke" })
    public void installationLink(final String userId, final String password) {

        userManagement.clickInstallation();
    }

    /**
     * Installation schedule link.
     * @param userId the user id
     * @param password the password
     */
    @Test(dataProvider = "validLogin", dataProviderClass = UserDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "smoke" })
    public void installationScheduleLink(final String userId, final String password) {

        userManagement.clickScheduling();
    }

    /**
     * Installation pre config link.
     * @param userId the user id
     * @param password the password
     */
    @Test(dataProvider = "validLogin", dataProviderClass = UserDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "smoke" })
    public void installationPreConfigLink(final String userId, final String password) {

        userManagement.clickPreConfiguration();
    }

    /**
     * User management link.
     * @param userId the user id
     * @param password the password
     */
    @Test(dataProvider = "validLogin", dataProviderClass = UserDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "smoke" })
    public void userManagementLink(final String userId, final String password) {

        userManagement.clickUserManagement();

    }

    /**
     * Role management link.
     * @param userId the user id
     * @param password the password
     */
    @Test(dataProvider = "validLogin", dataProviderClass = UserDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "smoke" })
    public void roleManagementLink(final String userId, final String password) {

        userManagement.clickRoleManagement();

    }

    /**
     * Demand side management link.
     * @param userId the user id
     * @param password the password
     */
    @Test(dataProvider = "validLogin", dataProviderClass = UserDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "smoke" })
    public void demandSideManagementLink(final String userId, final String password) {

        userManagement.clickDemandSideManagement();

    }

    /**
     * Test the user logout from a insite authenticated page.
     * @param userId the user id
     * @param password the password
     */
    @Test(dataProvider = "validLogin", dataProviderClass = UserDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "smoke" })
    public void logout(final String userId, final String password) {

        userManagement.logout();

    }

    /**
     * Find valid email address.
     * @param userName the user name
     * @param password the password
     * @param emailAddress the email address
     */
    @Test(dataProvider = "findValidEmail", dataProviderClass = UserDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "sanity" })
    public void findValidEmailAddress(final String userName, final String password,
            final String emailAddress) {

        userManagement.searchByEmail(emailAddress);
        Assert.assertTrue(userManagement.isEmailDisplayedInSearchGrid(emailAddress),
                "Email ID not found in Search Grid");
    }

    /**
     * Find partial email address.
     * @param userName the user name
     * @param password the password
     * @param emailAddress the email address
     */
    @Test(dataProvider = "findPartialEmail", dataProviderClass = UserDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "sanity" })
    public void findPartialEmailAddress(final String userName, final String password,
            final String emailAddress) {

        userManagement.searchByEmail(emailAddress);
        Assert.assertTrue(userManagement.isEmailDisplayedInSearchGrid(emailAddress),
                "Partial Email ID not found in Search Grid");
    }

    /**
     * Find valid user name.
     * @param userName the user name
     * @param password the password
     * @param findUserName the find user name
     */
    @Test(dataProvider = "findValidUserName", dataProviderClass = UserDataProvider.class, groups = { "sanity" })
    public void findValidUserName(final String userName, final String password,
            final String findUserName) {

        userManagement.searchByName(findUserName);
        Assert.assertTrue(userManagement.isUserNameDisplayedInSearchGrid(findUserName),
                "Username not found in Search Grid");
    }

    /**
     * Find partial user name.
     * @param userName the user name
     * @param password the password
     * @param findUserName the find user name
     */
    @Test(dataProvider = "findPartialUserName", dataProviderClass = UserDataProvider.class, groups = { "sanity" })
    public void findPartialUserName(final String userName, final String password,
            final String findUserName) {

        userManagement.searchByName(findUserName);
        Assert.assertTrue(userManagement.isUserNameDisplayedInSearchGrid(findUserName),
                "Partial Username not found in Search Grid");
    }

    /**
     * Sort names in search list.
     * @param userName the user name
     * @param password the password
     */
    @Test(dataProvider = "validLogin", dataProviderClass = UserDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "sanity" })
    public void sortNamesInSearchList(final String userName, final String password) {

        userManagement.clickNameLabel();
        Assert.assertTrue(userManagement.isSortedAscending(), "Names Not Sorted in Ascending");
        userManagement.clickNameLabel();
        Assert.assertTrue(userManagement.isSortedDescending(), "Names Not Sorted in Descending");

    }
    
    /**
     * Test first link.
     * @param userName the user name
     * @param password the password
     */
    @Test(dataProvider = "validLogin", dataProviderClass = UserDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "sanity" })
    public void testFirstLink(String userName, String password) {

        partnerManagement.clickFirst();
        Assert.assertTrue(userManagement.getCurrentPageNo() == 1, "Pagination differs");
        
    }

    /**
     * Test previous link.
     * @param userName the user name
     * @param password the password
     */
    @Test(dataProvider = "validLogin", dataProviderClass = UserDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "sanity" })
    public void testPreviousLink(String userName, String password) {

        partnerManagement.clickNext();
        Assert.assertTrue(userManagement.getCurrentPageNo() == 2, "Pagination differs");
        partnerManagement.clickPrevious();
        Assert.assertTrue(userManagement.getCurrentPageNo() == 1, "Pagination differs");

    }

    /**
     * Test next link.
     * @param userName the user name
     * @param password the password
     */
    @Test(dataProvider = "validLogin", dataProviderClass = UserDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "sanity" })
    public void testNextLink(String userName, String password) {

        partnerManagement.clickNext();
        Assert.assertTrue(userManagement.getCurrentPageNo() == 2, "Pagination differs");
    }

    /**
     * Test last link.
     * @param userName the user name
     * @param password the password
     */
    @Test(dataProvider = "validLogin", dataProviderClass = UserDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "sanity" })
    public void testLastLink(String userName, String password) {

        partnerManagement.clickLast();
        Assert.assertTrue(userManagement.getCurrentPageNo() == userManagement.getLastPageNo(),
                "Pagination differs");
    }

}
