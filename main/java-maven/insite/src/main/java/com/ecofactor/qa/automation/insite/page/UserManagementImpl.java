/*
 * UserManagementImpl.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.insite.page;

import static com.ecofactor.qa.automation.insite.config.InsiteConfig.*;
import static com.ecofactor.qa.automation.insite.config.UserConfig.*;
import static com.ecofactor.qa.automation.util.PageUtil.*;
import static com.ecofactor.qa.automation.util.WaitUtil.*;
import static org.testng.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import com.ecofactor.qa.automation.dao.PartnerAccountUserDao;
import com.ecofactor.qa.automation.insite.config.InsiteConfig;

import com.ecofactor.qa.automation.insite.config.UserConfig;
import com.ecofactor.qa.automation.util.DriverConfig;
import com.ecofactor.qa.automation.util.WaitUtil;
import com.ecofactor.qa.automation.util.mail.GmailForNewUser;
import com.google.inject.Inject;

/**
 * <b>User Management</b>
 * <p>
 * To provide User Management feature such as Create New user, search, Edit the User and Update.
 * </p>
 * @author Aximsoft
 */
public class UserManagementImpl extends InsiteAuthenticatedPageImpl implements UserManagement {

    /** The app config. */
    @Inject
    private InsiteConfig appConfig;

    /** The user config. */
    @Inject
    private UserConfig userConfig;

    /** The partner account user dao. */
    @Inject
    private PartnerAccountUserDao partnerAccountUserDao;

    /** The gmail. */
    @Inject
    private GmailForNewUser gmail;

    /** The logger. */
    private static Logger logger = LoggerFactory.getLogger(UserManagementImpl.class);

    /** The att alt. */
    private static String ATT_ALT = "alt";

    /**
     * Load page.
     * @see com.ecofactor.qa.automation.insite.page.InsiteAuthenticatedPage#loadPage()
     */
    @SuppressWarnings("static-access")
    @Override
    public void loadPage() {

        final String url = appConfig.get(INSITE_URL) + appConfig.get(appConfig.ADMIN_PAGE);
        if (!DriverConfig.getDriver().getCurrentUrl().equalsIgnoreCase(url)) {
            logger.info("select user management.");
            clickUserManagement();
        }
    }

    /**
     * Creates the new user.
     * @param firstName the first name
     * @param lastName the last name
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
     * @see com.ecofactor.qa.automation.insite.page.UserManagement#createNewUser(java.lang.String,
     *      java.lang.String, java.lang.String, java.lang.String, java.lang.String,
     *      java.lang.String, java.lang.String, java.lang.String, java.lang.String,
     *      java.lang.String, java.lang.String, java.lang.String, java.lang.String,
     *      java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    public void createNewUser(final String firstName, final String lastName,
            final String emailAddress, final String accountUserName, final String activeUser,
            final String partnerType, final String partner, final String streetAddress1,
            final String streetAddress2, final String city, final String state, final String zip,
            final String country, final String mobilePhoneNumber, final String homePhoneNumber,
            final String fax, final String availableRole) {

        populateValuesAndCreateNewUser(DriverConfig.getDriver(), firstName, lastName, emailAddress,
                accountUserName, activeUser, partnerType, partner, streetAddress1, streetAddress2,
                city, state, zip, country, mobilePhoneNumber, homePhoneNumber, fax, availableRole);
    }

    /**
     * <b>Populate values to the new User and create it</b>
     * <ol>
     * <li>Fill the textBox values and select the option type.</li>
     * <li>Populate the values in Account, Contact Information and Role tab</li>
     * <li>Finally Click on Save button and verify the alert is popped out.</li>
     * <ol>
     * @param driver the driver
     * @param firstName the first name
     * @param lastName the last name
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
    public void populateValuesAndCreateNewUser(final WebDriver driver, final String firstName,
            final String lastName, final String emailAddress, final String accountUserName,
            final String activeUser, final String partnerType, final String partner,

            final String streetAddress1, final String streetAddress2, final String city,
            final String state, final String zip, final String country,
            final String mobilePhoneNumber, final String homePhoneNumber, final String fax,

            final String availableRole) {

        fillUserDetails(driver, firstName, lastName, emailAddress, accountUserName, activeUser,
                partnerType, partner, streetAddress1, streetAddress2, city, state, zip, country,
                mobilePhoneNumber, homePhoneNumber, fax, availableRole);
        saveUsermanagement();
        DriverConfig.setLogString("New user created successfully '" + accountUserName + "'.", true);
    }

    /**
     * Click top first user.
     * @see com.ecofactor.qa.automation.insite.page.UserManagement#clickTopFirstUser()
     */
    public void clickTopFirstUser() {

        DriverConfig.setLogString("Click top first user.", true);
        mediumWait();
        final WebElement userRow = DriverConfig.getDriver()
                .findElements(By.id(userConfig.get(USER_TABLE))).get(0);
        retrieveElementByAttributeValueContainsForSubElement(DriverConfig.getDriver(), userRow,
                TAG_ANCHOR, ATTR_HREF, "user.html?", MEDIUM_TIMEOUT).click();
    }

    /**
     * Click reset password.
     * @see com.ecofactor.qa.automation.insite.page.UserManagement#clickResetPassword()
     */
    public void clickResetPassword() {

        smallWait();
        DriverConfig.setLogString("Click Reset Password.", true);
        final WebElement resetPswdLink = retrieveElementByTagText(DriverConfig.getDriver(),
                TAG_ANCHOR, "Reset");
        resetPswdLink.click();
        closeAlert(DriverConfig.getDriver());
        closeAlert(DriverConfig.getDriver());
    }

    /**
     * Check reset pswd.
     * @param emailUrl the email url
     * @param emailUserName the email user name
     * @param emailPassword the email password
     * @param subject the subject
     * @param searchUserName the search user name
     * @param resetPswdUserName the reset pswd user name
     * @param newPassword the new password
     * @see com.ecofactor.qa.automation.insite.page.UserManagement#checkResetPswd(java.lang.String,
     *      java.lang.String, java.lang.String, java.lang.String, java.lang.String,
     *      java.lang.String, java.lang.String)
     */
    @Override
    public void checkResetPswd(final String emailUrl, final String emailUserName,
            final String emailPassword, final String subject, final String searchUserName,
            final String resetPswdUserName, final String newPassword) {

        clickResetPassword();
        insiteLogin.logout();
        DriverConfig.setLogString("Get the password.", true);
        final String temporaryPassword = gmail.getChangedPassword(emailUrl, emailUserName,
                emailPassword, subject, 0, 0);
        DriverConfig.setLogString("Change the temporary password to the new one", true);
        insiteLogin.loginWithNewPassword(resetPswdUserName, temporaryPassword, newPassword);
        insiteLogin.verifyLogin(resetPswdUserName);
    }

    /**
     * Click edit.
     * @see com.ecofactor.qa.automation.insite.page.UserManagement#clickEdit()
     */
    public void clickEdit() {

        DriverConfig.setLogString("Click edit user link.", true);
        final WebElement editLink = retrieveElementByTagText(DriverConfig.getDriver(), TAG_ANCHOR,
                "Edit User");
        editLink.click();
        tinyWait();
    }

    /**
     * Click save.
     * @see com.ecofactor.qa.automation.insite.page.UserManagement#clickSave()
     */
    public void clickSave() {

        DriverConfig.setLogString("Click save link.", true);
        final WebElement saveLink = retrieveElementByTagText(DriverConfig.getDriver(), TAG_ANCHOR,
                "Save");
        saveLink.click();
        tinyWait();
        DriverConfig.getDriver().switchTo().alert().getText()
                .equalsIgnoreCase("User succesfully created / updated");
        DriverConfig.setLogString("User Saved :"
                + DriverConfig.getDriver().switchTo().alert().getText(), true);
        DriverConfig.getDriver().switchTo().alert().accept();
    }

    /**
     * Change account details.
     * @param firstName the first name
     * @param lastName the last name
     * @param emailAddress the email address
     * @param activeUser the active user
     * @see com.ecofactor.qa.automation.insite.page.UserManagement#changeAccountDetails(java.lang.String,
     *      java.lang.String, java.lang.String, java.lang.String)
     */
    public void changeAccountDetails(final String firstName, final String lastName,
            final String emailAddress, final String activeUser) {

        DriverConfig.setLogString("Change account details.", true);
        editFirstName(firstName);
        editLastName(lastName);
        editEmailId(emailAddress);
        if (activeUser != null && !activeUser.isEmpty()) {
            final Select select = new Select(DriverConfig.getDriver().findElement(
                    By.id(userConfig.get(USER_STATUS))));
            DriverConfig.setLogString("Select ActiveUser: " + activeUser, true);
            select.selectByVisibleText(activeUser);
        }
    }

    /**
     * Gets the account details.
     * @return the account details
     * @see com.ecofactor.qa.automation.insite.page.UserManagement#getAccountDetails()
     */
    public Map<String, String> getAccountDetails() {

        DriverConfig.setLogString("Get account details.", true);
        final Map<String, String> accountDetailMap = new HashMap<String, String>();
        final WebElement firstNameElemt = DriverConfig.getDriver().findElement(
                By.id(userConfig.get(FIRST_NAME)));
        accountDetailMap.put(userConfig.get(FIRST_NAME), firstNameElemt.getAttribute(ATTR_VALUE));

        final WebElement lastNameElemt = DriverConfig.getDriver().findElement(
                By.id(userConfig.get(LAST_NAME)));
        accountDetailMap.put(userConfig.get(LAST_NAME), lastNameElemt.getAttribute(ATTR_VALUE));

        final WebElement emailElemt = DriverConfig.getDriver().findElement(
                By.id(userConfig.get(EMAIL_FIELD)));
        accountDetailMap.put(userConfig.get(EMAIL_FIELD), emailElemt.getAttribute(ATTR_VALUE));

        final Select selectElemt = new Select(DriverConfig.getDriver().findElement(
                By.id(userConfig.get(USER_STATUS))));
        accountDetailMap.put(userConfig.get(USER_STATUS), selectElemt.getFirstSelectedOption()
                .getText());

        return accountDetailMap;
    }

    /**
     * Gets the contact details.
     * @return the contact details
     * @see com.ecofactor.qa.automation.insite.page.UserManagement#getContactDetails()
     */
    @Override
    public Map<String, String> getContactDetails() {

        DriverConfig.setLogString("Get Contact details.", true);
        final Map<String, String> contactDetailMap = new HashMap<String, String>();
        final WebElement streetAddress1 = DriverConfig.getDriver().findElement(
                By.id(userConfig.get(STREET_ADDRESS1)));
        contactDetailMap.put(userConfig.get(STREET_ADDRESS1),
                streetAddress1.getAttribute(ATTR_VALUE));
        final WebElement streetAddress2 = DriverConfig.getDriver().findElement(
                By.id(userConfig.get(STREET_ADDRESS2)));
        contactDetailMap.put(userConfig.get(STREET_ADDRESS2),
                streetAddress2.getAttribute(ATTR_VALUE));
        final WebElement city = DriverConfig.getDriver().findElement(By.id(userConfig.get(CITY)));
        contactDetailMap.put(userConfig.get(CITY), city.getAttribute(ATTR_VALUE));
        final WebElement state = DriverConfig.getDriver().findElement(By.id(userConfig.get(STATE)));
        contactDetailMap.put(userConfig.get(STATE), state.getAttribute(ATTR_VALUE));
        final WebElement zip = DriverConfig.getDriver().findElement(By.id(userConfig.get(ZIP)));
        contactDetailMap.put(userConfig.get(ZIP), zip.getAttribute(ATTR_VALUE));
        final WebElement country = DriverConfig.getDriver().findElement(
                By.id(userConfig.get(COUNTRY)));
        contactDetailMap.put(userConfig.get(COUNTRY), country.getAttribute(ATTR_VALUE));
        final WebElement mobileNum = DriverConfig.getDriver().findElement(
                By.id(userConfig.get(MOBILE_NUM)));
        contactDetailMap.put(userConfig.get(MOBILE_NUM), mobileNum.getAttribute(ATTR_VALUE));
        final WebElement phoneNum = DriverConfig.getDriver().findElement(
                By.id(userConfig.get(PHONE_NUM)));
        contactDetailMap.put(userConfig.get(PHONE_NUM), phoneNum.getAttribute(ATTR_VALUE));
        final WebElement faxNum = DriverConfig.getDriver().findElement(
                By.id(userConfig.get(FAX_NUM)));
        contactDetailMap.put(userConfig.get(FAX_NUM), faxNum.getAttribute(ATTR_VALUE));
        return contactDetailMap;
    }

    /**
     * Verify account details.
     * @see com.ecofactor.qa.automation.insite.page.UserManagement#verifyAccountDetails()
     */
    public void verifyAccountDetails() {

        DriverConfig.setLogString("Verify account details.", true);
        final List<WebElement> filterLabelClass = DriverConfig.getDriver().findElements(
                By.className("filterLabel"));
        final List<WebElement> filterInputClass = DriverConfig.getDriver().findElements(
                By.className("filterInput"));
        WebElement filterInput = null;
        int loopVal = 0;
        final List<String> accountFieldList = getAcountFieldList();
        for (final WebElement filterLabel : filterLabelClass) {
            filterInput = filterInputClass.get(loopVal);
            final String labelText = filterLabel.getText();
            final String inputText = filterInput.getText();
            if (accountFieldList.contains(labelText)) {
                logger.info("Label Name : " + labelText);
                logger.info("Input Name : " + inputText);
                assertTrue(inputText != null && !inputText.isEmpty(), "Input name is not available");
            }
            loopVal++;
        }
    }

    /**
     * Verify role details.
     * @see com.ecofactor.qa.automation.insite.page.UserManagement#verifyRoleDetails()
     */
    public void verifyRoleDetails() {

        DriverConfig.setLogString("Verify role details.", true);
        clickRoleTab();
        tinyWait();

        final List<WebElement> assignedRoles = retrieveElementsByContainsOfAttributeValue(
                DriverConfig.getDriver(), TAG_TD, ATTR_CLASS, "userinfo bgnone");
        int roleLoop = 0;
        for (final WebElement webElement : assignedRoles) {
            if (roleLoop == 0) {
                DriverConfig.setLogString("Role Name - " + webElement.getText(), true);
            } else {
                DriverConfig.setLogString("Role Description - " + webElement.getText(), true);
                roleLoop = 0;
            }
            roleLoop++;
        }
    }

    /**
     * Check assigned role details.
     * @param assignedRole the assigned role
     * @see com.ecofactor.qa.automation.insite.page.UserManagement#checkAssignedRoleDetails()
     */
    @Override
    public void checkAssignedRoleDetails(final Map<String, String> assignedRole) {

        DriverConfig.setLogString("verify assigned roles", true);
        for (Map.Entry<String, String> entry : assignedRole.entrySet()) {
            DriverConfig.setLogString(entry.getKey() + " " + entry.getValue(), true);
        }
    }

    /**
     * Search by name.
     * @param name the name
     * @see com.ecofactor.qa.automation.insite.page.UserManagement#searchByName(java.lang.String)
     */
    public void searchByName(final String name) {

        DriverConfig.setLogString("Search user by name.", true);
        final WebElement textBoxElement = DriverConfig.getDriver().findElement(By.id("fusername"));
        textBoxElement.sendKeys(name);
        DriverConfig.setLogString("Enter username in search text box: " + name, true);

        final WebElement buttonElement = retrieveElementByContainsOfAttributeValue(
                DriverConfig.getDriver(), TAG_INPUT, ATTR_VALUE, "Find", MEDIUM_TIMEOUT);
        buttonElement.click();
    }

    /**
     * Search by email.
     * @param emailId the email id
     * @see com.ecofactor.qa.automation.insite.page.UserManagement#searchByEmail(java.lang.String)
     */
    public void searchByEmail(final String emailId) {

        DriverConfig.setLogString("Search user by email.", true);
        final WebElement textBoxElement = DriverConfig.getDriver().findElement(By.id("findemail"));
        textBoxElement.sendKeys(emailId);
        DriverConfig.setLogString("Enter email id in search text box: " + emailId, true);
        final WebElement buttonElement = retrieveElementByContainsOfAttributeValue(
                DriverConfig.getDriver(), TAG_INPUT, ATTR_VALUE, "Find", MEDIUM_TIMEOUT);
        buttonElement.click();
    }

    /**
     * Verify save button.
     */
    public void verifySaveButton() {

        final WebElement saveLink = retrieveElementByTagText(DriverConfig.getDriver(), TAG_ANCHOR,
                "Save");
        assertTrue(saveLink != null, "Save button is not available");
    }

    /**
     * <p>
     * As the search results is displayed, this method help to point out the particular user and
     * will click on the Edit button of the selected user.
     * </p>
     * @param accountUserName the account user name
     */
    public void verifyUserAndClickEditButton(final String accountUserName) {

        DriverConfig
                .setLogString(
                        "Verify the particular user is listed in the SearchResult and click edit if found.",
                        true);
        DriverConfig.setLogString("Verify search result pagination is displayed.", true);
        retrieveElementByLinkText(DriverConfig.getDriver(), "First", MEDIUM_TIMEOUT);
        final List<WebElement> searchResultList = DriverConfig.getDriver().findElements(
                By.id("user"));
        boolean searchResultDisplayed = false;
        DriverConfig.setLogString("check if search result displays required user(s).", true);
        for (final WebElement webElement : searchResultList) {
            if (webElement.getText() != null && webElement.getText().contains(accountUserName)) {
                final WebElement anchorElemt = webElement.findElement(By.tagName(TAG_ANCHOR));
                anchorElemt.click();
                searchResultDisplayed = true;
                break;
            }
        }
        if (!searchResultDisplayed) {
            DriverConfig.setLogString("The Search result not found.", true);
        }
    }

    /**
     * <p>
     * Click on save button and verify the required mesaage is displayed in the alert. And check the
     * page is recirected to properly.
     * </p>
     */
    public void saveUsermanagement() {

        smallWait();
        DriverConfig.setLogString("Click Save and Verify the page is redirected properly.", true);
        final WebElement saveElement = retrieveElementByLinkText(DriverConfig.getDriver(), "Save",
                SHORT_TIMEOUT);
        saveElement.click();
        (new WebDriverWait(DriverConfig.getDriver(), LONG_TIMEOUT + LONG_TIMEOUT))
                .until(ExpectedConditions.alertIsPresent());
        DriverConfig.setLogString("check user created message displayed.", true);
        // commented to close alert.
        // assertEquals(true,
        // DriverConfig.getDriver().switchTo().alert().getText().equalsIgnoreCase(userConfig.get(USER_SAVED_ALERT_MSG)),
        // "User created message is not available");
        logger.info("click ok on alert message.", true);
        // fix to close the invalid alert message issue
        closeAlert(DriverConfig.getDriver());

        retrieveElementByLinkText(DriverConfig.getDriver(), "First", MEDIUM_TIMEOUT);
        DriverConfig.setLogString(
                "check if user management page is displayed after user creation.", true);
        assertEquals(true, DriverConfig.getDriver().getCurrentUrl().contains("usermgmt.html"),
                "Url is different");
    }

    /**
     * Load insite url.
     * @see com.ecofactor.qa.automation.insite.page.UserManagement#loadInsiteURL()
     */
    public void loadInsiteURL() {

        DriverConfig.getDriver().get(appConfig.get(INSITE_URL));
        waitUntil(SHORT_TIMEOUT);
    }

    /**
     * Click about ecofactor.
     * @see com.ecofactor.qa.automation.insite.page.InsitePageImpl#clickAboutEcofactor()
     */
    @Override
    public void clickAboutEcofactor() {

        // Auto-generated method stub

    }

    /**
     * Iterate first page and verify nve user.
     * @param partnerId the partner id
     * @see com.ecofactor.qa.automation.insite.page.UserManagement#iterateFirstPageAndVerifyNVEUser(int)
     */
    @Override
    public void iterateFirstPageAndVerifyNVEUser(final int partnerId) {

        DriverConfig.setLogString("Iterate Search Results", true);
        final List<WebElement> resultElements = DriverConfig.getDriver()
                .findElements(By.id("user"));
        for (final WebElement webElement : resultElements) {
            final List<WebElement> userInfo = webElement.findElements(By.className("userinfo"));
            Assert.assertTrue(!userInfo.get(2).getText().trim().isEmpty(), "Userinfo is different");
            final String userName = userInfo.get(2).getText().trim();
            logger.info("userPartnerId: " + userName);
            final int userPartnerId = partnerAccountUserDao
                    .getPartnerAccountByAccessLogin(userName);
            logger.info("userPartnerId: " + userName + ":" + userPartnerId);
            Assert.assertTrue(userPartnerId == partnerId, "User partnerId is different");
        }
    }

    /**
     * Search Given username.
     * @param userName the user name
     */
    @Override
    public void searchGivenUser(final String userName) {

        logger.info("find search text box is displayed.");
        isDisplayedById(DriverConfig.getDriver(), userConfig.get(SEARCH_USER_NAME), MEDIUM_TIMEOUT);

        DriverConfig.setLogString("Send value to the 'Find user by Name' text box.", true);
        DriverConfig.setLogString("Search given user: " + userName, true);
        DriverConfig.getDriver().findElement(By.id(userConfig.get(SEARCH_USER_NAME)))
                .sendKeys(userName);
        tinyWait();
        DriverConfig.setLogString("Click on Find button.", true);
        final WebElement findButtonElement = retrieveElementByAttributeValue(
                DriverConfig.getDriver(), TAG_INPUT, ATTR_VALUE, userConfig.get(FIND_BUTTON));
        findButtonElement.click();
        tinyWait();
    }

    /**
     * Assign role to User.
     * @param userName the user name
     * @return assigned role
     */
    @Override
    public String assignRoleToUser(final String userName) {

        clickRoleTab();

        DriverConfig.setLogString("check if available roles element is displayed.", true);
        isDisplayedById(DriverConfig.getDriver(), userConfig.get(AVAILABLE_ROLES), MEDIUM_TIMEOUT);

        final Select select = new Select(DriverConfig.getDriver().findElement(
                By.id(userConfig.get(AVAILABLE_ROLES))));
        select.deselectAll();
        DriverConfig.setLogString("select required role.", true);
        final List<WebElement> optionsList = select.getOptions();
        final String firstRole = optionsList.get(0).getText();
        DriverConfig.setLogString("Role Assigned to the user :" + firstRole, true);
        select.selectByVisibleText(firstRole);
        tinyWait();
        DriverConfig.setLogString("click on add button.", true);
        DriverConfig.getDriver().findElement(By.id("add")).click();
        return firstRole;
    }

    /**
     * Verify the role is assigned and reassign the role.
     * @param assignedRole the assigned role
     */
    @Override
    public void verifyAndReAssignRole(final String assignedRole) {

        clickRoleTab();

        DriverConfig.setLogString("check if assigned roles element is displayed.", true);
        DriverConfig.setLogString("Assigned Role " + assignedRole, true);
        isDisplayedById(DriverConfig.getDriver(), userConfig.get(ASSIGNED_ROLES), MEDIUM_TIMEOUT);

        final Select select = new Select(DriverConfig.getDriver().findElement(
                By.id(userConfig.get(ASSIGNED_ROLES))));

        final List<WebElement> optionsList = select.getOptions();
        for (final WebElement webelement : optionsList) {
            if (webelement.getText().equalsIgnoreCase(assignedRole)) {
                Assert.assertTrue(true);
                DriverConfig.setLogString("Assigned role is verified, and the role is :"
                        + webelement.getText(), true);
            }

        }

        DriverConfig.setLogString("ReAssign the selected Role", true);
        select.deselectAll();
        DriverConfig.setLogString("select required role " + assignedRole, true);
        select.selectByVisibleText(assignedRole);
        DriverConfig.setLogString("click on remove button.", true);
        DriverConfig.getDriver().findElement(By.id("remove")).click();
        smallWait();
    }

    /**
     * Check for user reset.
     * @param driver the driver
     * @param firstName the first name
     * @param lastName the last name
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
     * @see com.ecofactor.qa.automation.insite.page.UserManagement#checkForUserReset(org.openqa.selenium.WebDriver,
     *      java.lang.String, java.lang.String, java.lang.String, java.lang.String,
     *      java.lang.String, java.lang.String, java.lang.String, java.lang.String,
     *      java.lang.String, java.lang.String, java.lang.String, java.lang.String,
     *      java.lang.String, java.lang.String, java.lang.String, java.lang.String,
     *      java.lang.String)
     */
    @Override
    public void checkForUserReset(WebDriver driver, final String firstName, final String lastName,
            final String emailAddress, final String accountUserName, final String activeUser,
            final String partnerType, final String partner, final String streetAddress1,
            final String streetAddress2, final String city, final String state, final String zip,
            final String country, final String mobilePhoneNumber, final String homePhoneNumber,
            final String fax, final String availableRole) {

        fillUserDetails(driver, firstName, lastName, emailAddress, accountUserName, activeUser,
                partnerType, partner, streetAddress1, streetAddress2, city, state, zip, country,
                mobilePhoneNumber, homePhoneNumber, fax, availableRole);
        clickReset();
    }

    /**
     * click reset link.
     * @see com.ecofactor.qa.automation.insite.page.UserManagement#clickReset()
     */
    @Override
    public void clickReset() {

        DriverConfig.setLogString("Click Reset in user page ", true);
        WaitUtil.smallWait();
        final WebElement resetLink = retrieveElementByTagText(DriverConfig.getDriver(), TAG_ANCHOR,
                userConfig.get(RESET));
        resetLink.click();
    }

    /**
     * check for cancel the user.
     * @param driver the driver
     * @param firstName the first name
     * @param lastName the last name
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
     * @see com.ecofactor.qa.automation.insite.page.UserManagement#checkForUserCancel(org.openqa.selenium.WebDriver,
     *      java.lang.String, java.lang.String, java.lang.String, java.lang.String,
     *      java.lang.String, java.lang.String, java.lang.String, java.lang.String,
     *      java.lang.String, java.lang.String, java.lang.String, java.lang.String,
     *      java.lang.String, java.lang.String, java.lang.String, java.lang.String,
     *      java.lang.String)
     */
    @Override
    public void checkForUserCancel(WebDriver driver, final String firstName, final String lastName,
            final String emailAddress, final String accountUserName, final String activeUser,
            final String partnerType, final String partner, final String streetAddress1,
            final String streetAddress2, final String city, final String state, final String zip,
            final String country, final String mobilePhoneNumber, final String homePhoneNumber,
            final String fax, final String availableRole) {

        fillUserDetails(driver, firstName, lastName, emailAddress, accountUserName, activeUser,
                partnerType, partner, streetAddress1, streetAddress2, city, state, zip, country,
                mobilePhoneNumber, homePhoneNumber, fax, availableRole);
        clickCancel();
    }

    /**
     * click cancel.
     * @see com.ecofactor.qa.automation.insite.page.UserManagement#clickCancel()
     */
    @Override
    public void clickCancel() {

        DriverConfig.setLogString("Click Cancel in user page ", true);
        WaitUtil.smallWait();
        final WebElement resetLink = retrieveElementByTagText(DriverConfig.getDriver(), TAG_ANCHOR,
                userConfig.get(CANCEL));
        resetLink.click();
    }

    /**
     * Gets the user save error.
     * @return the user save error
     * @see com.ecofactor.qa.automation.insite.page.UserManagement#getUserSaveError()
     */
    @Override
    public String getUserSaveError() {

        DriverConfig.setLogString("Check for Error.", true);
        WaitUtil.mediumWait();
        final String creationAlert = DriverConfig.getDriver().switchTo().alert().getText();
        DriverConfig.getDriver().switchTo().alert().accept();
        DriverConfig.setLogString("Alert Text : " + creationAlert, true);
        return creationAlert;
    }

    /**
     * Fill new user click save.
     * @param firstName the first name
     * @param lastName the last name
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
     * @see com.ecofactor.qa.automation.insite.page.UserManagement#fillNewUserClickSave(java.lang.String,
     *      java.lang.String, java.lang.String, java.lang.String, java.lang.String,
     *      java.lang.String, java.lang.String, java.lang.String, java.lang.String,
     *      java.lang.String, java.lang.String, java.lang.String, java.lang.String,
     *      java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public void fillNewUserClickSave(String firstName, final String lastName,
            final String emailAddress, final String accountUserName, final String activeUser,
            final String partnerType, final String partner, final String streetAddress1,
            final String streetAddress2, final String city, final String state, final String zip,
            final String country, final String mobilePhoneNumber, final String homePhoneNumber,
            final String fax, final String availableRole) {

        fillUserDetails(DriverConfig.getDriver(), firstName, lastName, emailAddress,
                accountUserName, activeUser, partnerType, partner, streetAddress1, streetAddress2,
                city, state, zip, country, mobilePhoneNumber, homePhoneNumber, fax, availableRole);
        WaitUtil.tinyWait();

        DriverConfig.setLogString("Click save link.", true);
        final WebElement saveLink = retrieveElementByTagText(DriverConfig.getDriver(), TAG_ANCHOR,
                "Save");
        saveLink.click();
    }

    /**
     * Click name label.
     * @see com.ecofactor.qa.automation.insite.page.UserManagement#clickNameLabel()
     */
    @Override
    public void clickNameLabel() {

        DriverConfig.setLogString("Click Label Name", true);
        final WebElement nameLabel = retrieveElementByAttributeValue(DriverConfig.getDriver(),
                TAG_ANCHOR, "title", "First Name");
        nameLabel.click();
    }

    /**
     * Checks if is sorted ascending.
     * @return true, if is sorted ascending
     * @see com.ecofactor.qa.automation.insite.page.UserManagement#isSortedAscending()
     */
    @Override
    public boolean isSortedAscending() {

        final String firstLetter = getFirstLetterOfName();

        return firstLetter.equalsIgnoreCase("a") || firstLetter.equalsIgnoreCase("b")
                || firstLetter.equalsIgnoreCase("c") || firstLetter.equalsIgnoreCase("d");
    }

    /**
     * Checks if is sorted descending.
     * @return true, if is sorted descending
     * @see com.ecofactor.qa.automation.insite.page.UserManagement#isSortedDescending()
     */
    @Override
    public boolean isSortedDescending() {

        final String firstLetter = getFirstLetterOfName();
        return firstLetter.equalsIgnoreCase("u") || firstLetter.equalsIgnoreCase("v")
                || firstLetter.equalsIgnoreCase("w") || firstLetter.equalsIgnoreCase("x")
                || firstLetter.equalsIgnoreCase("y") || firstLetter.equalsIgnoreCase("z");
    }

    /**
     * Checks if is role available.
     * @param role the role
     * @return true, if is role available
     * @see com.ecofactor.qa.automation.insite.page.UserManagement#isRoleAvailable(java.lang.String)
     */
    @Override
    public boolean isRoleAvailable(final String role) {

        DriverConfig.setLogString("Check Role : " + role + " displayed", true);
        final WebElement createNewUserLink = retrieveElementByLinkText(DriverConfig.getDriver(),
                userConfig.get(CREATE_NEW_USER), MEDIUM_TIMEOUT);
        createNewUserLink.click();
        clickRoleTab();
        return isDisplayedByXpath(DriverConfig.getDriver(),
                "//select[@id='availableRoles']/option[contains(text(),'" + role + "')]",
                SHORT_TIMEOUT);
    }

    /**
     * Gets the current page no.
     * @return the current page no
     * @see com.ecofactor.qa.automation.insite.page.UserManagement#getCurrentPageNo()
     */
    @Override
    public int getCurrentPageNo() {

        WebElement element = DriverConfig.getDriver().findElement(
                By.cssSelector("input.pagedisplay.ef_smallLabel.ieFix"));
        String pageNo = element.getAttribute(ATTR_VALUE).split(" ")[0];
        DriverConfig.setLogString("Current Page No : " + pageNo, true);
        return Integer.valueOf(pageNo);
    }

    /**
     * Gets the last page no.
     * @return the last page no
     * @see com.ecofactor.qa.automation.insite.page.UserManagement#getLastPageNo()
     */
    @Override
    public int getLastPageNo() {

        WebElement element = DriverConfig.getDriver().findElement(
                By.cssSelector("input.pagedisplay.ef_smallLabel.ieFix"));
        String pageNo = element.getAttribute(ATTR_VALUE).split(" ")[0];
        DriverConfig.setLogString("Last Page No : " + pageNo, true);
        return Integer.valueOf(pageNo);
    }

    /**
     * Edits the first name.
     * @param firstName the first name
     * @see com.ecofactor.qa.automation.insite.page.UserManagement#editFirstName()
     */
    @Override
    public void editFirstName(final String firstName) {

        DriverConfig.setLogString("Edit First Name.", true);
        if (firstName != null && !firstName.isEmpty()) {
            final WebElement firstNameElemt = DriverConfig.getDriver().findElement(
                    By.id(userConfig.get(FIRST_NAME)));
            firstNameElemt.clear();
            DriverConfig.setLogString("Enter Firstname: " + firstName, true);
            firstNameElemt.sendKeys(firstName);
        }
    }

    /**
     * Edits the last name.
     * @param lastName the last name
     * @see com.ecofactor.qa.automation.insite.page.UserManagement#editLastName(java.lang.String)
     */
    @Override
    public void editLastName(final String lastName) {

        DriverConfig.setLogString("Edit Last Name", true);
        if (lastName != null && !lastName.isEmpty()) {
            final WebElement firstNameElemt = DriverConfig.getDriver().findElement(
                    By.id(userConfig.get(LAST_NAME)));
            firstNameElemt.clear();
            DriverConfig.setLogString("Enter LastName: " + lastName, true);
            firstNameElemt.sendKeys(lastName);
        }
    }

    /**
     * Edits the email id.
     * @param emailId the email id
     * @see com.ecofactor.qa.automation.insite.page.UserManagement#editEmailId(java.lang.String)
     */
    @Override
    public void editEmailId(final String emailId) {

        DriverConfig.setLogString("Edit emailId", true);
        if (emailId != null && !emailId.isEmpty()) {
            final WebElement emailElemt = DriverConfig.getDriver().findElement(
                    By.id(userConfig.get(EMAIL_FIELD)));
            emailElemt.clear();
            DriverConfig.setLogString("Enter Email Address: " + emailId, true);
            emailElemt.sendKeys(emailId);
        }
    }

    /**
     * Edits the street address1.
     * @param streetAddress the street address
     * @see com.ecofactor.qa.automation.insite.page.UserManagement#editStreetAddress1(java.lang.String)
     */
    @Override
    public void editStreetAddress1(final String streetAddress) {

        DriverConfig.setLogString("Edit streetAddress", true);
        if (streetAddress != null && !streetAddress.isEmpty()) {
            final WebElement emailElemt = DriverConfig.getDriver().findElement(
                    By.id(userConfig.get(STREET_ADDRESS1)));
            emailElemt.clear();
            DriverConfig.setLogString("Enter streetAddress: " + streetAddress, true);
            emailElemt.sendKeys(streetAddress);
        }
    }

    /**
     * Edits the street address2.
     * @param streetAddress the street address
     * @see com.ecofactor.qa.automation.insite.page.UserManagement#editStreetAddress2(java.lang.String)
     */
    @Override
    public void editStreetAddress2(String streetAddress) {

        DriverConfig.setLogString("Edit streetAddress", true);
        if (streetAddress != null && !streetAddress.isEmpty()) {
            final WebElement emailElemt = DriverConfig.getDriver().findElement(
                    By.id(userConfig.get(STREET_ADDRESS2)));
            emailElemt.clear();
            DriverConfig.setLogString("Enter streetAddress: " + streetAddress, true);
            emailElemt.sendKeys(streetAddress);
        }
    }

    /**
     * Checks if is sub menu displayed.
     * @param subMenu the sub menu
     * @return true, if is sub menu displayed
     * @see com.ecofactor.qa.automation.insite.page.RoleManagement#isSubMenuDisplayed(java.lang.String)
     */
    @Override
    public boolean isSubMenuDisplayed(final String subMenu) {

        DriverConfig.setLogString("SubMenu " + subMenu, true);
        boolean isdisplayed = false;
        final List<WebElement> subMenus = DriverConfig.getDriver().findElements(
                By.xpath("//*[@id='submenu']/a"));
        isdisplayed = checkMenu(subMenu, isdisplayed, subMenus);
        return isdisplayed;
    }

    /**
     * Checks if is menu displayed.
     * @param menu the menu
     * @return true, if is menu displayed
     * @see com.ecofactor.qa.automation.insite.page.RoleManagement#isMenuDisplayed(java.lang.String)
     */
    @Override
    public boolean isMenuDisplayed(final String menu) {

        DriverConfig.setLogString("Menu " + menu, true);
        boolean isdisplayed = false;
        final List<WebElement> menus = DriverConfig.getDriver().findElements(
                By.xpath("//*[@id='menu']/li/a"));
        isdisplayed = checkMenu(menu, isdisplayed, menus);
        return isdisplayed;
    }

    /**
     * Gets the menu.
     * @return the menu
     * @see com.ecofactor.qa.automation.insite.page.RoleManagement#getMenu()
     */
    @Override
    public List<WebElement> getMenu() {

        final List<WebElement> menus = DriverConfig.getDriver().findElements(
                By.xpath("//*[@id='menu']/li/a"));
        if (menus != null) {
            for (final WebElement webElement : menus) {
                DriverConfig.setLogString("Menu :" + webElement.getText(), true);
            }
        }
        return menus;
    }

    /**
     * Gets the sub menu.
     * @return the sub menu
     * @see com.ecofactor.qa.automation.insite.page.RoleManagement#getSubMenu()
     */
    @Override
    public List<WebElement> getSubMenu() {

        final List<WebElement> subMenus = DriverConfig.getDriver().findElements(
                By.xpath("//*[@id='submenu']/a"));
        if (subMenus != null) {
            for (final WebElement webElement : subMenus) {
                DriverConfig.setLogString("SubMenu :" + webElement.getText(), true);
            }
        }
        return subMenus;
    }

    /**
     * Edits the city.
     * @param city the city
     * @see com.ecofactor.qa.automation.insite.page.UserManagement#editCity(java.lang.String)
     */
    @Override
    public void editCity(final String city) {

        DriverConfig.setLogString("Edit city", true);
        if (city != null && !city.isEmpty()) {
            final WebElement emailElemt = DriverConfig.getDriver().findElement(
                    By.id(userConfig.get(CITY)));
            emailElemt.clear();
            DriverConfig.setLogString("Enter city: " + city, true);
            emailElemt.sendKeys(city);
        }
    }

    /**
     * Edits the state.
     * @param state the state
     * @see com.ecofactor.qa.automation.insite.page.UserManagement#editState(java.lang.String)
     */
    @Override
    public void editState(final String state) {

        DriverConfig.setLogString("Edit state", true);
        if (state != null && !state.isEmpty()) {
            final WebElement emailElemt = DriverConfig.getDriver().findElement(
                    By.id(userConfig.get(STATE)));
            emailElemt.clear();
            DriverConfig.setLogString("Enter state: " + state, true);
            emailElemt.sendKeys(state);
        }
    }

    /**
     * Edits the zip.
     * @param zip the zip
     * @see com.ecofactor.qa.automation.insite.page.UserManagement#editZip(java.lang.String)
     */
    @Override
    public void editZip(final String zip) {

        DriverConfig.setLogString("Edit zip", true);
        if (zip != null && !zip.isEmpty()) {
            final WebElement emailElemt = DriverConfig.getDriver().findElement(
                    By.id(userConfig.get(ZIP)));
            emailElemt.clear();
            DriverConfig.setLogString("Enter zip: " + zip, true);
            emailElemt.sendKeys(zip);
        }
    }

    /**
     * Edits the country.
     * @param country the country
     * @see com.ecofactor.qa.automation.insite.page.UserManagement#editCountry(java.lang.String)
     */
    @Override
    public void editCountry(final String country) {

        DriverConfig.setLogString("Edit country", true);
        if (country != null && !country.isEmpty()) {
            final WebElement emailElemt = DriverConfig.getDriver().findElement(
                    By.id(userConfig.get(COUNTRY)));
            emailElemt.clear();
            DriverConfig.setLogString("Enter country: " + country, true);
            emailElemt.sendKeys(country);
        }
    }

    /**
     * Edits the mobile phone number.
     * @param mobilePhoneNumber the mobile phone number
     * @see com.ecofactor.qa.automation.insite.page.UserManagement#editMobilePhoneNumber(java.lang.String)
     */
    @Override
    public void editMobilePhoneNumber(final String mobilePhoneNumber) {

        DriverConfig.setLogString("Edit mobilePhoneNumber", true);
        if (mobilePhoneNumber != null && !mobilePhoneNumber.isEmpty()) {
            final WebElement emailElemt = DriverConfig.getDriver().findElement(
                    By.id(userConfig.get(MOBILE_NUM)));
            emailElemt.clear();
            DriverConfig.setLogString("Enter mobilePhoneNumber: " + mobilePhoneNumber, true);
            emailElemt.sendKeys(mobilePhoneNumber);
        }
    }

    /**
     * Edits the home phone number.
     * @param homePhoneNumber the home phone number
     * @see com.ecofactor.qa.automation.insite.page.UserManagement#editHomePhoneNumber(java.lang.String)
     */
    @Override
    public void editHomePhoneNumber(final String homePhoneNumber) {

        DriverConfig.setLogString("Edit homePhoneNumber", true);
        if (homePhoneNumber != null && !homePhoneNumber.isEmpty()) {
            final WebElement emailElemt = DriverConfig.getDriver().findElement(
                    By.id(userConfig.get(PHONE_NUM)));
            emailElemt.clear();
            DriverConfig.setLogString("Enter homePhoneNumber: " + homePhoneNumber, true);
            emailElemt.sendKeys(homePhoneNumber);
        }
    }

    /**
     * Edits the fax.
     * @param fax the fax
     * @see com.ecofactor.qa.automation.insite.page.UserManagement#editFax(java.lang.String)
     */
    @Override
    public void editFax(final String fax) {

        DriverConfig.setLogString("Edit fax", true);
        if (fax != null && !fax.isEmpty()) {
            final WebElement emailElemt = DriverConfig.getDriver().findElement(
                    By.id(userConfig.get(FAX_NUM)));
            emailElemt.clear();
            DriverConfig.setLogString("Enter fax: " + fax, true);
            emailElemt.sendKeys(fax);
        }
    }

    /**
     * Click contact details tab.
     * @see com.ecofactor.qa.automation.insite.page.UserManagement#clickContactDetailsTab()
     */
    @Override
    public void clickContactDetailsTab() {

        DriverConfig.setLogString("Click Contact Details tab", true);
        DriverConfig.getDriver().findElement(By.id(userConfig.get(CONTACT_DETAILS_TAB))).click();
    }

    /**
     * Change contact details.
     * @param streetAddress1 the street address1
     * @param streetAddress2 the street address2
     * @param city the city
     * @param state the state
     * @param zip the zip
     * @param country the country
     * @param mobilePhoneNum the mobile phone num
     * @param pnoneNumber the pnone number
     * @param faxNumber the fax number
     * @see com.ecofactor.qa.automation.insite.page.UserManagement#changeContactDetails(java.lang.String,
     *      java.lang.String, java.lang.String, java.lang.String, java.lang.String,
     *      java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public void changeContactDetails(String streetAddress1, String streetAddress2, String city,
            String state, String zip, String country, String mobilePhoneNum, String pnoneNumber,
            String faxNumber) {

        DriverConfig.setLogString("Enter contact details", true);
        editStreetAddress1(streetAddress1);
        editStreetAddress2(streetAddress2);
        editCity(city);
        editCountry(country);
        editMobilePhoneNumber(mobilePhoneNum);
        editHomePhoneNumber(pnoneNumber);
        editFax(faxNumber);
    }

    /**
     * Click role tab.
     * @see com.ecofactor.qa.automation.insite.page.UserManagement#clickRoleTab()
     */
    @Override
    public void clickRoleTab() {

        DriverConfig.setLogString("Click Role tab", true);
        final WebElement roleTab = DriverConfig.getDriver().findElement(
                By.id(userConfig.get(ROLES)));
        roleTab.click();
    }

    /**
     * Click account details tab.
     * @see com.ecofactor.qa.automation.insite.page.UserManagement#clickAccountDetailsTab()
     */
    @Override
    public void clickAccountDetailsTab() {

        isDisplayedById(DriverConfig.getDriver(), userConfig.get(ACCOUNT_DETAILS_TAB),
                SHORT_TIMEOUT);
        DriverConfig.setLogString("click accout details tab.", true);
        waitUntil(SHORT_TIMEOUT);
        DriverConfig.getDriver().findElement(By.id(userConfig.get(ACCOUNT_DETAILS_TAB))).click();
    }

    /**
     * Gets the role details.
     * @return the role details
     * @see com.ecofactor.qa.automation.insite.page.UserManagement#getRoleDetails()
     */
    @Override
    public Map<String, String> getRoleDetails() {

        Map<String, String> assignedRole = new HashMap<String, String>();
        DriverConfig.setLogString("Verify role details.", true);
        clickRoleTab();
        tinyWait();

        final List<WebElement> assignedRoles = retrieveElementsByContainsOfAttributeValue(
                DriverConfig.getDriver(), TAG_TD, ATTR_CLASS, "userinfo bgnone");
        int roleLoop = 0;
        for (final WebElement webElement : assignedRoles) {
            if (roleLoop == 0) {
                // DriverConfig.setLogString("Role Name - " + webElement.getText(), true);
                assignedRole.put("Role Name - ", webElement.getText());
            } else {
                // DriverConfig.setLogString("Role Description - " + webElement.getText(), true);
                assignedRole.put("Role Description - ", webElement.getText());
                roleLoop = 0;
            }
            roleLoop++;
        }
        return assignedRole;
    }

    /**
     * click edit icon.
     */
    public void clickEditIcon() {

        final List<WebElement> editList = retrieveElementsByAttributeValueList(
                DriverConfig.getDriver(), TAG_IMG, ATT_ALT, "Edit");
        editList.get(0).click();
        smallWait();
    }

    /**
     * Gets error message.
     * @return Error Message
     */
    public String getErrorMessage() {

        DriverConfig.setLogString("Check for error message", true);
        String validationMsg = "";
        final List<WebElement> errorLabels = retrieveElementsByAttributeValueList(
                DriverConfig.getDriver(), TAG_DIV, ATTR_CLASS, "ERROR");
        for (final WebElement webElement : errorLabels) {
            validationMsg += webElement.getText();
        }
        DriverConfig.setLogString("Error message : " + validationMsg, true);
        return validationMsg;
    }

    /**
     * Checks if is email displayed in search grid.
     * @param emailId the email id
     * @return true, if is email displayed in search grid
     * @see com.ecofactor.qa.automation.insite.page.UserManagement#isEmailDisplayedInSearchGrid(java.lang.String)
     */
    public boolean isEmailDisplayedInSearchGrid(final String emailId) {

        DriverConfig.setLogString("Given email id is displayed in grid", true);
        mediumWait();
        return isDisplayedByXpath(DriverConfig.getDriver(), "//td[contains(text(),'" + emailId
                + "')]", SHORT_TIMEOUT);
    }

    /**
     * Checks if is user name displayed in search grid.
     * @param findUserName the find user name
     * @return true, if is user name displayed in search grid
     * @see com.ecofactor.qa.automation.insite.page.UserManagement#isUserNameDisplayedInSearchGrid(java.lang.String)
     */
    public boolean isUserNameDisplayedInSearchGrid(final String findUserName) {

        DriverConfig.setLogString("Given username is displayed in grid", true);
        mediumWait();

        return isDisplayedByXpath(DriverConfig.getDriver(), "//td[contains(text(),'" + findUserName
                + "')]", SHORT_TIMEOUT);
    }

    /**
     * Checks if is no result displayed.
     * @return true, if is no result displayed
     * @see com.ecofactor.qa.automation.insite.page.UserManagement#isNoResultDisplayed()
     */
    public boolean isNoResultDisplayed() {

        DriverConfig.setLogString("Check No Result Found Text displayed", true);
        return isDisplayedByCSS(DriverConfig.getDriver(), ".ef_smallLabel>strong", SHORT_TIMEOUT);
    }

    /**
     * <p>
     * Send Account details for the New User
     * </p>
     * .
     * @param driver the driver
     * @param firstName the first name
     * @param lastName the last name
     * @param emailAddress the email address
     * @param accountUserName the account user name
     * @param activeUser the active user
     * @param partnerType the partner type
     * @param partner the partner
     */
    private void sendAccountDetails(final WebDriver driver, final String firstName,
            final String lastName, final String emailAddress, final String accountUserName,
            final String activeUser, final String partnerType, final String partner) {

        DriverConfig.setLogString(
                "Click on Account Pane and send account details to the required fields.", true);
        logger.info("check accout details tab is displayed.");
        clickAccountDetailsTab();
        isDisplayedById(driver, userConfig.get(FIRST_NAME), MEDIUM_TIMEOUT);
        DriverConfig.setLogString("Enter first name as " + firstName, true);
        driver.findElement(By.id(userConfig.get(FIRST_NAME))).sendKeys(firstName);
        DriverConfig.setLogString("Enter last name as " + lastName, true);
        driver.findElement(By.id(userConfig.get(LAST_NAME))).sendKeys(lastName);
        DriverConfig.setLogString("Enter email address " + emailAddress, true);
        driver.findElement(By.id(userConfig.get(EMAIL_FIELD))).sendKeys(emailAddress);
        DriverConfig.setLogString("Enter user name " + accountUserName, true);
        driver.findElement(By.id(userConfig.get(ACCESS_LOGIN))).sendKeys(accountUserName);
        DriverConfig.setLogString("select user status ", true);
        final Select select1 = new Select(driver.findElement(By.id(userConfig.get(USER_STATUS))));
        select1.selectByVisibleText(activeUser);

        /*
         * Select select2 = new Select(driver.findElement(By.id(userConfig.get(PARTNER_TYPE))));
         * select2.selectByVisibleText(partnerType);
         */
        DriverConfig.setLogString("check partner element displayed.", true);
        final boolean partnerElement = isDisplayedById(driver, userConfig.get(PARTNER_ID),
                MEDIUM_TIMEOUT);

        (new WebDriverWait(driver, SHORT_TIMEOUT)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {

                boolean outcome = false;
                outcome = !d.getPageSource().contains(userConfig.get(DEFAULT_PARTNER_ID)) ? true
                        : false;
                return outcome;
            }
        });
        if (!partnerElement) {
            final WebElement partnerText = driver.findElement(By
                    .xpath("//td[contains(text(),'Partner')]/following-sibling::td"));
            DriverConfig.setLogString("Default Partner :" + partnerText.getText(), true);
        }
        if (!partner.isEmpty()) {
            final Select select3 = new Select(driver.findElement(By.id(userConfig.get(PARTNER_ID))));
            select3.selectByVisibleText(partner);
        }
    }

    /**
     * Gets the first letter of name.
     * @return the first letter of name
     */
    private String getFirstLetterOfName() {

        DriverConfig.setLogString("get first Letter of Name.", true);
        final WebElement userRow = DriverConfig.getDriver()
                .findElements(By.id(userConfig.get(USER_TABLE))).get(0);
        final String letter = retrieveElementByAttributeValueContainsForSubElement(
                DriverConfig.getDriver(), userRow, TAG_ANCHOR, ATTR_HREF, "user.html?",
                MEDIUM_TIMEOUT).getText().substring(0, 1);
        DriverConfig.setLogString("first Letter of Name : " + letter, true);
        return letter;
    }

    /**
     * Fill user details.
     * @param driver the driver
     * @param firstName the first name
     * @param lastName the last name
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
    private void fillUserDetails(final WebDriver driver, final String firstName,
            final String lastName, final String emailAddress, final String accountUserName,
            final String activeUser, final String partnerType, final String partner,
            final String streetAddress1, final String streetAddress2, final String city,
            final String state, final String zip, final String country,
            final String mobilePhoneNumber, final String homePhoneNumber, final String fax,
            final String availableRole) {

        DriverConfig.setLogString("Create New user.", true);
        final WebElement createNewUserLink = retrieveElementByLinkText(driver,
                userConfig.get(CREATE_NEW_USER), MEDIUM_TIMEOUT);
        createNewUserLink.click();

        sendAccountDetails(driver, firstName, lastName, emailAddress, accountUserName, activeUser,
                partnerType, partner);
        sendContactInformation(driver, streetAddress1, streetAddress2, city, state, zip, country,
                mobilePhoneNumber, homePhoneNumber, fax);

        sendRoleDetails(driver, availableRole);
    }

    /**
     * <p>
     * Click Contact Information and send realted values to the New user
     * </p>
     * .
     * @param driver the driver
     * @param streetAddress1 the street address1
     * @param streetAddress2 the street address2
     * @param city the city
     * @param state the state
     * @param zip the zip
     * @param country the country
     * @param mobilePhoneNumber the mobile phone number
     * @param homePhoneNumber the home phone number
     * @param fax the fax
     */
    private void sendContactInformation(final WebDriver driver, final String streetAddress1,
            final String streetAddress2, final String city, final String state, final String zip,
            final String country, final String mobilePhoneNumber, final String homePhoneNumber,
            final String fax) {

        DriverConfig.setLogString("Send Contact information details to the ContactInformation Tab",
                true);
        DriverConfig.setLogString("select corresponding tab.", true);
        clickContactDetailsTab();
        DriverConfig.setLogString("check if street addres1 is displayed.", true);
        isDisplayedById(driver, userConfig.get(STREET_ADDRESS1), MEDIUM_TIMEOUT);
        DriverConfig.setLogString("Enter street address1 as " + streetAddress1, true);
        driver.findElement(By.id(userConfig.get(STREET_ADDRESS1))).sendKeys(streetAddress1);
        DriverConfig.setLogString("Enter street address2 as " + streetAddress2, true);
        driver.findElement(By.id(userConfig.get(STREET_ADDRESS2))).sendKeys(streetAddress2);
        DriverConfig.setLogString("Enter city " + city, true);
        driver.findElement(By.id(userConfig.get(CITY))).sendKeys(city);
        DriverConfig.setLogString("Enter state " + state, true);
        driver.findElement(By.id(userConfig.get(STATE))).sendKeys(state);
        DriverConfig.setLogString("Enter zip " + zip, true);
        driver.findElement(By.id(userConfig.get(ZIP))).sendKeys(zip);
        DriverConfig.setLogString("Enter country " + country, true);
        driver.findElement(By.id(userConfig.get(COUNTRY))).sendKeys(country);
        DriverConfig.setLogString("Enter mobile no " + mobilePhoneNumber, true);
        driver.findElement(By.id(userConfig.get(MOBILE_NUM))).sendKeys(mobilePhoneNumber);
        DriverConfig.setLogString("Enter home phone " + homePhoneNumber, true);
        driver.findElement(By.id(userConfig.get(PHONE_NUM))).sendKeys(homePhoneNumber);
        DriverConfig.setLogString("Enter fax " + fax, true);
        driver.findElement(By.id(userConfig.get(FAX_NUM))).sendKeys(fax);
    }

    /**
     * <p>
     * Click Role tab, and select the available roles.
     * </p>
     * @param driver the driver
     * @param availableRole the available role
     */
    private void sendRoleDetails(final WebDriver driver, final String availableRole) {

        DriverConfig
                .setLogString(
                        "Click on Role tab and Send the Role details to the selection, and add it to the pane.",
                        true);

        isDisplayedById(driver, "roles", SHORT_TIMEOUT);
        clickRoleTab();
        DriverConfig.setLogString("check if available roles element is displayed.", true);
        isDisplayedById(driver, userConfig.get(AVAILABLE_ROLES), MEDIUM_TIMEOUT);
        DriverConfig.setLogString("unselect existing selection.", true);
        removeAllToAvailablePane(driver);

        final Select select = new Select(driver.findElement(By.id(userConfig.get(AVAILABLE_ROLES))));
        select.deselectAll();
        DriverConfig.setLogString("select required role " + availableRole, true);
        if (!availableRole.isEmpty()) {
            select.selectByVisibleText(availableRole);
            DriverConfig.setLogString("click on add button.", true);
            driver.findElement(By.id("add")).click();
        }

    }

    /**
     * <p>
     * Remove all values from the assigned panel to the available panel and verify there is no value
     * in the assigned pane.
     * </p>
     * @param driver the driver
     */
    private void removeAllToAvailablePane(final WebDriver driver) {

        DriverConfig.setLogString(
                "Remome all values from the assigned Pane and moving to available pane", true);
        final Select select = new Select(driver.findElement(By.id(userConfig.get(ASSIGNED_ROLES))));

        final List<WebElement> optionsList = select.getOptions();

        for (final WebElement webElement : optionsList) {
            select.selectByVisibleText(webElement.getText());
            driver.findElement(By.id("remove")).click();
        }

        final List<WebElement> optionsList2 = select.getOptions();
        (new WebDriverWait(driver, SHORT_TIMEOUT)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {

                boolean outcome = false;
                outcome = optionsList2.size() == 0 ? true : false;
                return outcome;
            }
        });
    }

    /**
     * Gets the acount field list.
     * @return the acount field list
     */
    private List<String> getAcountFieldList() {

        final List<String> accountFieldList = new ArrayList<>();
        accountFieldList.add("First Name");
        accountFieldList.add("User Status");
        accountFieldList.add("Last Name");
        accountFieldList.add("Partner");
        accountFieldList.add("Email Address");
        accountFieldList.add("Username");
        accountFieldList.add("Password");

        return accountFieldList;
    }

    /**
     * Check menu.
     * @param menu the menu
     * @param isdisplayed the isdisplayed
     * @param menus the menus
     * @return true, if successful
     */
    private boolean checkMenu(final String menu, boolean isdisplayed, final List<WebElement> menus) {

        if (menus != null) {
            for (final WebElement webElement : menus) {
                if (webElement.getText().equalsIgnoreCase(menu) && webElement.isDisplayed()) {

                    isdisplayed = true;
                    break;
                }
            }
        }
        return isdisplayed;
    }
}
