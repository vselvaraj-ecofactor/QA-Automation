/*
 * PartnerManagement.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.insite.page;

import org.openqa.selenium.WebDriver;

/**
 * The Interface PartnerManagement.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public interface PartnerManagement extends InsiteAuthenticatedPage {

    /**
     * Load page.
     * @see com.ecofactor.qa.automation.insite.page.InsiteAuthenticatedPage#loadPage()
     */
    public void loadPage();

    /**
     * Click new partner.
     */
    public void clickNewPartner();

    /**
     * Click cancel.
     */
    public void clickCancel();

    /**
     * Click reset.
     */
    public void clickReset();

    /**
     * Fill partner details.
     * @param driver the driver
     * @param partnerName the partner name
     * @param emailId the email id
     * @param activeUser the active user
     * @param streetAddress1 the street address1
     * @param streetAddress2 the street address2
     * @param city the city
     * @param state the state
     * @param zip the zip
     * @param country the country
     * @param primaryContactName the primary contact name
     * @param phoneNumber the phone number
     * @param availablePartnerType the available partner type
     */
    public void fillPartnerDetails(WebDriver driver, final String partnerName,
            final String emailId, final String activeUser, final String streetAddress1,
            final String streetAddress2, final String city, final String state, final String zip,
            final String country, final String primaryContactName, final String phoneNumber,
            final String availablePartnerType);

    /**
     * Populate partner details.
     * @param driver the driver
     * @param partnerName the partner name
     * @param emailId the email id
     * @param activeUser the active user
     * @param streetAddress1 the street address1
     * @param streetAddress2 the street address2
     * @param city the city
     * @param state the state
     * @param zip the zip
     * @param country the country
     * @param primaryContactName the primary contact name
     * @param phoneNumber the phone number
     * @param availablePartnerType the available partner type
     */
    public void populatePartnerDetails(WebDriver driver, final String partnerName,
            final String emailId, final String activeUser, final String streetAddress1,
            final String streetAddress2, final String city, final String state, final String zip,
            final String country, final String primaryContactName, final String phoneNumber,
            final String availablePartnerType);

    /**
     * Click save.
     */
    public void clickSave();

    /**
     * Check for partner name alert.
     */
    public void checkForPartnerNameAlert();

    /**
     * Check for partner type alert.
     */
    public void checkForPartnerTypeAlert();

    /**
     * Check for partner name and type alert.
     */
    public void checkForPartnerNameAndTypeAlert();

    /**
     * Fill details without partner name.
     * @param driver the driver
     * @param emailId the email id
     * @param activeUser the active user
     * @param streetAddress1 the street address1
     * @param streetAddress2 the street address2
     * @param city the city
     * @param state the state
     * @param zip the zip
     * @param country the country
     * @param primaryContactName the primary contact name
     * @param phoneNumber the phone number
     * @param availablePartnerType the available partner type
     */
    public void fillDetailsWithoutPartnerName(WebDriver driver, final String emailId,
            final String activeUser, final String streetAddress1, final String streetAddress2,
            final String city, final String state, final String zip, final String country,
            final String primaryContactName, final String phoneNumber,
            final String availablePartnerType);

    /**
     * Fill details without partner type.
     * @param driver the driver
     * @param partnerName the partner name
     * @param emailId the email id
     * @param activeUser the active user
     * @param streetAddress1 the street address1
     * @param streetAddress2 the street address2
     * @param city the city
     * @param state the state
     * @param zip the zip
     * @param country the country
     * @param primaryContactName the primary contact name
     * @param phoneNumber the phone number
     */
    public void fillDetailsWithoutPartnerType(WebDriver driver, final String partnerName,
            final String emailId, final String activeUser, final String streetAddress1,
            final String streetAddress2, final String city, final String state, final String zip,
            final String country, final String primaryContactName, final String phoneNumber);

    /**
     * Fill details without partner name type.
     * @param driver the driver
     * @param emailId the email id
     * @param activeUser the active user
     * @param streetAddress1 the street address1
     * @param streetAddress2 the street address2
     * @param city the city
     * @param state the state
     * @param zip the zip
     * @param country the country
     * @param primaryContactName the primary contact name
     * @param phoneNumber the phone number
     */
    public void fillDetailsWithoutPartnerNameType(WebDriver driver, final String emailId,
            final String activeUser, final String streetAddress1, final String streetAddress2,
            final String city, final String state, final String zip, final String country,
            final String primaryContactName, final String phoneNumber);

    /**
     * Click contact information.
     */
    public void clickContactInformation();

    /**
     * Click partner types.
     */
    public void clickPartnerTypes();

    /**
     * Click account tab.
     */
    public void clickAccountTab();

    /**
     * Checks if is account tab displayed.
     * @return true, if is account tab displayed
     */
    public boolean isAccountTabDisplayed();

    /**
     * Checks if is contact tab displayed.
     * @return true, if is contact tab displayed
     */
    public boolean isContactTabDisplayed();

    /**
     * Checks if is partner types tab displayed.
     * @return true, if is partner types tab displayed
     */
    public boolean isPartnerTypesTabDisplayed();

    /**
     * Checks if is account tab highlighted.
     * @return true, if is account tab highlighted
     */
    public boolean isAccountTabHighlighted();

    /**
     * Checks if is contact tab highlighted.
     * @return true, if is contact tab highlighted
     */
    public boolean isContactTabHighlighted();

    /**
     * Checks if is partner types tab highlighted.
     * @return true, if is partner types tab highlighted
     */
    public boolean isPartnerTypesTabHighlighted();

    /**
     * Click find.
     */
    public void clickFind();

    /**
     * Search by name.
     * @param partnerName the partner name
     */
    public void searchByName(final String partnerName);

    /**
     * Search by email.
     * @param partnerEmail the partner email
     */
    public void searchByEmail(final String partnerEmail);

    /**
     * Gets the result message.
     * @return the result message
     */
    public String getResultMessage();

    /**
     * Checks if is displayed top first partner.
     * @param partnerName the partner name
     * @return true, if is displayed top first partner
     */
    public void isDisplayedTopFirstPartner(final String partnerName);

    /**
     * Click next.
     */
    public void clickNext();

    /**
     * Click previous.
     */
    public void clickPrevious();

    /**
     * Click first.
     */
    public void clickFirst();

    /**
     * Click last.
     */
    public void clickLast();

    /**
     * Checks if is first page displayed.
     * @return true, if is first page displayed
     */
    public boolean isFirstPageDisplayed();

    /**
     * Checks if is partner creation saved.
     * @return true, if is partner creation saved
     */
    public void checkForPartnerCreationAlert();

    /**
     * Checks if is second page displayed.
     * @return true, if is second page displayed
     */
    public boolean isSecondPageDisplayed();

    /**
     * Checks if is last pag displayed.
     * @return true, if is last pag displayed
     */
    public boolean isLastPageDisplayed();

    /**
     * Fill partner types.
     * @param driver the driver
     * @param partnerType the partner type
     */
    public void fillPartnerTypes(final WebDriver driver, final String partnerType);

    /**
     * Enter partner name.
     * @param partnerName the partner name
     */
    public void enterPartnerName(final String partnerName);

    /**
     * Enter partner email id.
     * @param partnerEmailId the partner email id
     */
    public void enterPartnerEmailId(final String partnerEmailId);

    /**
     * Enter partner status.
     * @param partnerStatus the partner status
     */
    public void enterPartnerStatus(final String partnerStatus);

    /**
     * Enter partner address1.
     * @param partnerAddress1 the partner address1
     */
    public void enterPartnerAddress1(final String partnerAddress1);

    /**
     * Enter partner address2.
     * @param partnerAddress2 the partner address2
     */
    public void enterPartnerAddress2(final String partnerAddress2);

    /**
     * Enter partner city.
     * @param partnerCity the partner city
     */
    public void enterPartnerCity(final String partnerCity);

    /**
     * Enter partner country.
     * @param partnerCountry the partner country
     */
    public void enterPartnerCountry(final String partnerCountry);

    /**
     * Enter primary contact number.
     * @param primaryContactNumber the primary contact number
     */
    public void enterPrimaryContactNumber(final String primaryContactNumber);

    /**
     * Enter partner phone number.
     * @param phoneNumber the phone number
     */
    public void enterPartnerPhoneNumber(final String phoneNumber);

    /**
     * Enter partner state.
     * @param partnerState the partner state
     */
    public void enterPartnerState(final String partnerState);

    /**
     * Enter partner zip.
     * @param partnerZip the partner zip
     */
    public void enterPartnerZip(final String partnerZip);

    /**
     * Save without email id.
     * @param driver the driver
     * @param partnerName the partner name
     * @param activeUser the active user
     * @param streetAddress1 the street address1
     * @param streetAddress2 the street address2
     * @param city the city
     * @param state the state
     * @param zip the zip
     * @param country the country
     * @param primaryContactName the primary contact name
     * @param phoneNumber the phone number
     * @param partnerType the partner type
     */
    public void saveWithoutEmailId(final WebDriver driver, final String partnerName,
            final String activeUser, final String streetAddress1, final String streetAddress2,
            final String city, final String state, final String zip, final String country,
            final String primaryContactName, final String phoneNumber, final String partnerType);

    /**
     * Save without street address one.
     * @param driver the driver
     * @param partnerName the partner name
     * @param emailId the email id
     * @param activeUser the active user
     * @param city the city
     * @param state the state
     * @param zip the zip
     * @param country the country
     * @param primaryContactName the primary contact name
     * @param phoneNumber the phone number
     * @param availablePartnerType the available partner type
     */
    public void saveWithoutStreetAddress(WebDriver driver, final String partnerName,
            final String emailId, final String activeUser, final String city, final String state,
            final String zip, final String country, final String primaryContactName,
            final String phoneNumber, final String availablePartnerType);

    /**
     * Save without street address two.
     * @param driver the driver
     * @param partnerName the partner name
     * @param emailId the email id
     * @param activeUser the active user
     * @param streetAddress1 the street address2
     * @param streetAddress2 the street address2
     * @param state the state
     * @param zip the zip
     * @param country the country
     * @param primaryContactName the primary contact name
     * @param phoneNumber the phone number
     * @param availablePartnerType the available partner type
     */
    public void saveWithoutCity(WebDriver driver, final String partnerName, final String emailId,
            final String activeUser, final String streetAddress1, final String streetAddress2,
            final String state, final String zip, final String country,
            final String primaryContactName, final String phoneNumber,
            final String availablePartnerType);

    /**
     * Save without state.
     * @param driver the driver
     * @param partnerName the partner name
     * @param emailId the email id
     * @param activeUser the active user
     * @param streetAddress1 the street address1
     * @param streetAddress2 the street address2
     * @param city the city
     * @param zip the zip
     * @param country the country
     * @param primaryContactName the primary contact name
     * @param phoneNumber the phone number
     * @param availablePartnerType the available partner type
     */
    public void saveWithoutState(WebDriver driver, final String partnerName, final String emailId,
            final String activeUser, final String streetAddress1, final String streetAddress2,
            final String city, final String zip, final String country,
            final String primaryContactName, final String phoneNumber,
            final String availablePartnerType);

    /**
     * Save without zip.
     * @param driver the driver
     * @param partnerName the partner name
     * @param emailId the email id
     * @param activeUser the active user
     * @param streetAddress1 the street address1
     * @param streetAddress2 the street address2
     * @param city the city
     * @param state the state
     * @param country the country
     * @param primaryContactName the primary contact name
     * @param phoneNumber the phone number
     * @param availablePartnerType the available partner type
     */
    public void saveWithoutZip(WebDriver driver, final String partnerName, final String emailId,
            final String activeUser, final String streetAddress1, final String streetAddress2,
            final String city, final String state, final String country,
            final String primaryContactName, final String phoneNumber,
            final String availablePartnerType);

    /**
     * Save without country.
     * @param driver the driver
     * @param partnerName the partner name
     * @param emailId the email id
     * @param activeUser the active user
     * @param streetAddress1 the street address1
     * @param streetAddress2 the street address2
     * @param city the city
     * @param state the state
     * @param zip the zip
     * @param primaryContactName the primary contact name
     * @param phoneNumber the phone number
     * @param availablePartnerType the available partner type
     */
    public void saveWithoutCountry(WebDriver driver, final String partnerName,
            final String emailId, final String activeUser, final String streetAddress1,
            final String streetAddress2, final String city, final String state, final String zip,
            final String primaryContactName, final String phoneNumber,
            final String availablePartnerType);

    /**
     * Save without primary contact name.
     * @param driver the driver
     * @param partnerName the partner name
     * @param emailId the email id
     * @param activeUser the active user
     * @param streetAddress1 the street address1
     * @param streetAddress2 the street address2
     * @param city the city
     * @param state the state
     * @param zip the zip
     * @param country the country
     * @param phoneNumber the phone number
     * @param availablePartnerType the available partner type
     */
    public void saveWithoutPrimaryContactName(WebDriver driver, final String partnerName,
            final String emailId, final String activeUser, final String streetAddress1,
            final String streetAddress2, final String city, final String state, final String zip,
            final String country, final String phoneNumber, final String availablePartnerType);

    /**
     * Save without phone number.
     * @param driver the driver
     * @param partnerName the partner name
     * @param emailId the email id
     * @param activeUser the active user
     * @param streetAddress1 the street address1
     * @param streetAddress2 the street address2
     * @param city the city
     * @param state the state
     * @param zip the zip
     * @param country the country
     * @param primaryContactName the primary contact name
     * @param availablePartnerType the available partner type
     */
    public void saveWithoutPhoneNumber(WebDriver driver, final String partnerName,
            final String emailId, final String activeUser, final String streetAddress1,
            final String streetAddress2, final String city, final String state, final String zip,
            final String country, final String primaryContactName, final String availablePartnerType);

}
