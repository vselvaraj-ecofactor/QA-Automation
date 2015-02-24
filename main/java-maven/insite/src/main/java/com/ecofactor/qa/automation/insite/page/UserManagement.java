/*
 * UserManagement.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.insite.page;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * The Interface UserManagement.
 * @author $Author: vprasannaa $
 * @version $Rev: 33623 $ $Date: 2015-02-20 11:54:43 +0530 (Fri, 20 Feb 2015) $
 */
public interface UserManagement extends InsiteAuthenticatedPage {

    /**
     * Load page.
     * @param userId the user id
     * @param password the password
     * @see com.ecofactor.qa.automation.insite.page.InsiteAuthenticatedPageImpl#loadPage(java.lang.String,
     *      java.lang.String)
     */
    public void loadPage(final String userId, final String password);

    /**
     * Load page.
     * @see com.ecofactor.qa.automation.insite.page.InsiteAuthenticatedPage#loadPage()
     */
    public void loadPage();

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
     */
    public void createNewUser(final String firstName, final String lastName,
            final String emailAddress, final String accountUserName, final String activeUser,
            final String partnerType, final String partner, final String streetAddress1,
            final String streetAddress2, final String city, final String state, final String zip,
            final String country, final String mobilePhoneNumber, final String homePhoneNumber,
            final String fax, final String availableRole);

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
            final String availableRole);

    /**
     * Click top first user.
     */
    public void clickTopFirstUser();

    /**
     * Click edit.
     */
    public void clickEdit();

    /**
     * Click reset password.
     */
    public void clickResetPassword();

    /**
     * Check reset pswd.
     * @param emailUrl the email url
     * @param emailUserName the email user name
     * @param emailPassword the email password
     * @param subject the subject
     * @param searchUserName the search user name
     * @param resetPswdUserName the reset pswd user name
     * @param newPassword the new password
     */
    public void checkResetPswd(final String emailUrl, final String emailUserName,
            final String emailPassword, final String subject, final String searchUserName,
            final String resetPswdUserName, final String newPassword);

    /**
     * Click save.
     */
    public void clickSave();

    /**
     * Verify account details.
     */
    public void verifyAccountDetails();

    /**
     * Change account details.
     * @param firstName the first name
     * @param lastName the last name
     * @param emailAddress the email address
     * @param activeUser the active user
     */
    public void changeAccountDetails(final String firstName, final String lastName,
            final String emailAddress, final String activeUser);

    /**
     * Gets the account details.
     * @return the account details
     */
    public Map<String, String> getAccountDetails();

    /**
     * Verify role details.
     */
    public void verifyRoleDetails();

    /**
     * Search by email.
     * @param emailId the email id
     */
    public void searchByEmail(final String emailId);

    /**
     * Search by name.
     * @param name the name
     */
    public void searchByName(final String name);

    /**
     * Load insite url.
     */
    public void loadInsiteURL();

    /**
     * Verify save button.
     */
    public void verifySaveButton();

    /**
     * Checks if is email displayed in search grid.
     * @param emailId the email id
     * @return true, if is email displayed in search grid
     */
    public boolean isEmailDisplayedInSearchGrid(final String emailId);

    /**
     * Checks if is user name displayed in search grid.
     * @param userName the user name
     * @return true, if is user name displayed in search grid
     */
    public boolean isUserNameDisplayedInSearchGrid(final String userName);

    /**
     * Iterate first page and verify nve user.
     * @param partnerId the partner id
     */
    public void iterateFirstPageAndVerifyNVEUser(final int partnerId);

    /**
     * Search given user.
     * @param string the string
     */
    public void searchGivenUser(final String string);

    /**
     * Assign role to user.
     * @param string the string
     * @return the string
     */
    public String assignRoleToUser(final String string);

    /**
     * Verify and re assign role.
     * @param string the string
     */
    public void verifyAndReAssignRole(final String string);

    /**
     * Verify user and click edit button.
     * @param string the string
     */
    public void verifyUserAndClickEditButton(final String string);

    /**
     * Save usermanagement.
     */
    public void saveUsermanagement();

    /**
     * Click edit icon.
     */
    public void clickEditIcon();

    /**
     * Gets the error message.
     * @return the error message
     */
    public String getErrorMessage();

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
     */
    public void checkForUserReset(final WebDriver driver, final String firstName,
            final String lastName, final String emailAddress, final String accountUserName,
            final String activeUser, final String partnerType, final String partner,
            final String streetAddress1, final String streetAddress2, final String city,
            final String state, final String zip, final String country,
            final String mobilePhoneNumber, final String homePhoneNumber, final String fax,
            final String availableRole);

    /**
     * Click reset.
     */
    public void clickReset();

    /**
     * Check for user cancel.
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
    public void checkForUserCancel(WebDriver driver, final String firstName, final String lastName,
            final String emailAddress, final String accountUserName, final String activeUser,
            final String partnerType, final String partner, final String streetAddress1,
            final String streetAddress2, final String city, final String state, final String zip,
            final String country, final String mobilePhoneNumber, final String homePhoneNumber,
            final String fax, final String availableRole);

    /**
     * Click cancel.
     */
    public void clickCancel();

    /**
     * Gets the user save error.
     * @return the user save error
     */
    public String getUserSaveError();

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
     */
    public void fillNewUserClickSave(final String firstName, final String lastName,
            final String emailAddress, final String accountUserName, final String activeUser,
            final String partnerType, final String partner, final String streetAddress1,
            final String streetAddress2, final String city, final String state, final String zip,
            final String country, final String mobilePhoneNumber, final String homePhoneNumber,
            final String fax, final String availableRole);

    /**
     * Checks if is no result displayed.
     * @return true, if is no result displayed
     */
    public boolean isNoResultDisplayed();

    /**
     * Click name label.
     */
    public void clickNameLabel();

    /**
     * Checks if is sorted ascending.
     * @return true, if is sorted ascending
     */
    public boolean isSortedAscending();

    /**
     * Checks if is sorted descending.
     * @return true, if is sorted descending
     */
    public boolean isSortedDescending();

    /**
     * Checks if is role available.
     * @param role the role
     * @return true, if is role available
     */
    public boolean isRoleAvailable(final String role);

    /**
     * Gets the current page no.
     * @return the current page no
     */
    public int getCurrentPageNo();

    /**
     * Gets the last page no.
     * @return the last page no
     */
    public int getLastPageNo();

    /**
     * Edits the first name.
     * @param firstName the first name
     */
    public void editFirstName(final String firstName);

    /**
     * Edits the last name.
     * @param lastName the last name
     */
    public void editLastName(final String lastName);

    /**
     * Edits the email id.
     * @param emailId the email id
     */
    public void editEmailId(final String emailId);

    /**
     * Edits the street address1.
     * @param streetAddress the street address
     */
    public void editStreetAddress1(final String streetAddress);

    /**
     * Edits the street address2.
     * @param streetAddress the street address
     */
    public void editStreetAddress2(final String streetAddress);

    /**
     * Checks if is menu displayed.
     * @param menu the menu
     * @return true, if is menu displayed
     */
    public boolean isMenuDisplayed(final String menu);

    /**
     * Checks if is sub menu displayed.
     * @param subMenu the sub menu
     * @return true, if is sub menu displayed
     */
    public boolean isSubMenuDisplayed(final String subMenu);

    /**
     * Gets the menu.
     * @return the menu
     */
    public List<WebElement> getMenu();

    /**
     * Gets the sub menu.
     * @return the sub menu
     */
    public List<WebElement> getSubMenu();

    /**
     * Edits the city.
     * @param city the city
     */
    public void editCity(final String city);

    /**
     * Edits the state.
     * @param state the state
     */
    public void editState(final String state);

    /**
     * Edits the zip.
     * @param zip the zip
     */
    public void editZip(final String zip);

    /**
     * Edits the country.
     * @param country the country
     */
    public void editCountry(final String country);

    /**
     * Edits the mobile phone number.
     * @param mobilePhoneNumber the mobile phone number
     */
    public void editMobilePhoneNumber(final String mobilePhoneNumber);

    /**
     * Edits the home phone number.
     * @param homePhoneNumber the home phone number
     */
    public void editHomePhoneNumber(final String homePhoneNumber);

    /**
     * Edits the fax.
     * @param fax the fax
     */
    public void editFax(final String fax);

    /**
     * Click contact details tab.
     */
    public void clickContactDetailsTab();

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
     */
    public void changeContactDetails(final String streetAddress1, final String streetAddress2,
            final String city, final String state, final String zip, final String country,
            final String mobilePhoneNum, final String pnoneNumber, final String faxNumber);

    /**
     * Click role tab.
     */
    public void clickRoleTab();

    /**
     * Gets the account details.
     * @return the account details
     */
    public Map<String, String> getContactDetails();

    /**
     * Click account details tab.
     */
    public void clickAccountDetailsTab();

    /**
     * Check assigned role details.
     * @param roleDetails the role details
     * @return the map
     */
    public void checkAssignedRoleDetails(final Map<String, String> roleDetails);

    /**
     * Gets the role details.
     * @return the role details
     */
    public Map<String, String> getRoleDetails();
}
