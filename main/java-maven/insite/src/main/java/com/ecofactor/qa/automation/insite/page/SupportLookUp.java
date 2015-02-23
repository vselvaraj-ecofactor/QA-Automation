/*
 * SupportLookUp.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.insite.page;

/**
 * The Interface SupportLookUp.
 * @author $Author: vraj $
 * @version $Rev: 33450 $ $Date: 2015-01-09 13:09:49 +0530 (Fri, 09 Jan 2015) $
 */
public interface SupportLookUp extends InsiteAuthenticatedPage {

    /**
     * Search by email.
     * @param textBoxFieldValue the text box field value
     */
    public void searchByEmail(final String textBoxFieldValue);

    /**
     * Search by phone.
     * @param textBoxFieldValue the text box field value
     */
    public void searchByPhone(final String textBoxFieldValue);

    /**
     * Search account look up.
     * @param textBoxFieldName the text box field name
     * @param textBoxFieldValue the text box field value
     */
    public void searchAccountLookUp(final String textBoxFieldName, final String textBoxFieldValue);

    /**
     * Confirm search result value.
     * @param searchResultValue the search result value
     * @return true, if successful
     */
    public boolean confirmSearchResultValue(final String searchResultValue);

    /**
     * Verify installation hardware.
     * @param emailId the email id
     */
    public void verifyInstallationHardware(final String emailId);

    /**
     * Click searched result element.
     * @param searchResultValue the search result value
     */
    public void clickSearchedResultElement(final String searchResultValue);

    /**
     * Disable load shapping.
     * @param email the email
     */
    public void disableLoadShapping(String email);

    /**
     * Click specified email.
     * @param email the email
     */
    public void clickSpecifiedEmail(String email);

    /**
     * Click pop up cancel.
     */
    public void clickPopUpCancel();

    /**
     * Click home owner edit.
     */
    public void clickHomeOwnerEdit();

    /**
     * Click phone number edit.
     */
    public void clickPhoneNumberEdit();

    /**
     * Click energy efficiency refresh.
     */
    public void clickEnergyEfficiencyRefresh();

    /**
     * Click ecp core edit.
     */
    public void clickEcpCoreEdit();

    /**
     * Click user name edit.
     */
    public void clickUserNameEdit();

    /**
     * Click email id edit.
     */
    public void clickEmailIdEdit();

    /**
     * Click electricity rate edit.
     */
    public void clickElectricityRateEdit();

    /**
     * Click gas rate edit.
     */
    public void clickGasRateEdit();

    /**
     * Click proxy login.
     */
    public void clickProxyLogin();

    /**
     * Verify proxy denied.
     * @return true, if successful
     */
    public boolean verifyProxyDenied();

    /**
     * Iterate results and verify empty fields.
     */
    public void iterateResultsAndVerifyEmptyFields();

    /**
     * Iterate results and verify comcast id.
     */
    public void iterateResultsAndVerifyComcastId();

    /**
     * Click top first user.
     */
    public void clickTopFirstUser();

    /**
     * Click energy efficiency.
     */
    public void clickEnergyEfficiencyCheckBox();

    /**
     * Verify no proxy and reset link.
     */
    public void verifyNoProxyAndResetLink();

    /**
     * Verify no export offline thermostat link.
     */
    public void verifyNoExportOfflineThermostatLink();

    /**
     * Click rest password.
     */
    public void clickResetPassword();

    /**
     * Click do Verify Pswd Reset By Proxy Login.
     * @param userName the user name
     * @param password the password
     * @param newPassword the new password
     * @param newAppUrl the new app url
     */
    public void doVerifyPswdResetByNewAppLogin(String userName, String password,
            String newPassword, String newAppUrl);

    /**
     * Enter character to electricity rate.
     * @param name the name
     */
    public void enterCharacterToElectricityRate(String name);

    /**
     * Gets the error message.
     * @return the error message
     */
    public String getErrorMessage();

    /**
     * Enter character to gas rate.
     * @param string the string
     */
    public void enterCharacterToGasRate(String string);

    /**
     * Enter phone number.
     * @param phoneNumber the phone number
     */
    public void enterPhoneNumber(String phoneNumber);

    /**
     * Enter email id.
     * @param email the email
     */
    public void enterEmailId(String email);

    /**
     * Verify error message.
     * @return the string
     */
    public String verifyErrorMessage();

    /**
     * Search by address.
     * @param address the address
     */
    public void searchByAddress(String address);

    /**
     * Click first address.
     * @param address the address
     */
    public void clickFirstAddress(String address);

    /**
     * Click installation date.
     */
    public void clickInstallationDate();

    /**
     * Search by email id.
     * @param textBoxFieldValue the text box field value
     */
    public void searchByEmailId(final String textBoxFieldValue);

    /**
     * Search by phone number.
     * @param textBoxFieldValue the text box field value
     */
    public void searchByPhoneNumber(final String textBoxFieldValue);

    /**
     * Search by id.
     * @param textBoxFieldValue the text box field value
     */
    public void searchById(final String textBoxFieldValue);

    /**
     * Search by street addr.
     * @param textBoxFieldValue the text box field value
     */
    public void searchByStreetAddr(final String textBoxFieldValue);

    /**
     * Checks if is no result displayed.
     * @return true, if is no result displayed
     */
    public boolean isNoResultDisplayed();
    
    /**
     * Check for top first user.
     * @param searchName the search name
     * @return true, if successful
     */
    public boolean checkForTopFirstUser(final String searchName);
    
    /**
     * Checks if is search text displayed.
     * @param searchText the search text
     * @return true, if is search text displayed
     */
    public boolean isSearchTextDisplayed(final String searchText);
    
    /**
     * Checks if is email search text displayed.
     * @param searchText the search text
     * @return true, if is email search text displayed
     */
    public boolean isEmailSearchTextDisplayed(final String searchText);
    
    /**
     * Gets the ecp.
     * @return the ecp
     */
    public String getEcp();
    
    /**
     * Gets the location name.
     * @return the location name
     */
    public String getLocationName();
    
    /**
     * Checks if is location name displayed.
     * @return true, if is location name displayed
     */
    public boolean isLocationNameDisplayed();
}
