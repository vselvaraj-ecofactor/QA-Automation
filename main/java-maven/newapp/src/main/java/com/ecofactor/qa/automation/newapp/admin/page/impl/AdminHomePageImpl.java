/*
 * AdminHomePageImpl.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.newapp.admin.page.impl;

import static com.ecofactor.qa.automation.platform.constants.HtmlTags.*;
import static com.ecofactor.qa.automation.platform.enums.CustomTimeout.*;
import static com.ecofactor.qa.automation.platform.util.LogUtil.*;
import static com.ecofactor.qa.automation.platform.util.Pageutil.*;
import static com.ecofactor.qa.automation.util.WaitUtil.*;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.ecofactor.qa.automation.newapp.admin.page.AdminHomePage;
import com.ecofactor.qa.automation.newapp.admin.page.AdminLoginPage;
import com.google.inject.Inject;

/**
 * The Class AdminHomePageImpl.
 */
public class AdminHomePageImpl extends AbstractAdminPageImpl implements	AdminHomePage {

	private static final String DIAG = "diag";
	private static final String ACCOUNTS_NAV = "accountsNav";
	private static final String FORM = "form";
	private static final String CRITERIA = "criteria";
	private static final String SEARCH = "search";
	private static final String ITEM_TABLE = "itemTable";
	private static final String FETCH = "Fetch";
	private static final String RUNNING_DIAG_TEXT = "running_dialog_text";
	private static final String RUNNING_DIAG_BODY = "running_dialog_body";
	private static final String LOGOUT = "logout";

	public WebElement formElement;
	private static final int ZERO = 0;

	@Inject
	public AdminLoginPage adminLogin;

	/**
	 * Cleanup.
	 * @see com.ecofactor.qa.automation.newapp.page.BasePage#cleanup()
	 */
	@Override
	public void cleanup() {
		// TODO Auto-generated method stub

	}

	/**
	 * Checks if is page loaded.
	 * @return true, if checks if is page loaded
	 * @see com.ecofactor.qa.automation.newapp.page.BasePage#isPageLoaded()
	 */
	@Override
	public boolean isPageLoaded() {

		setLogString("Check if login success.", true);
		final boolean pageLoaded = isDisplayed(driverManager.getAdminDriver(), By.id(ACCOUNTS_NAV), SHORT_TIMEOUT);
		getElement(driverManager.getAdminDriver(), By.id(ACCOUNTS_NAV), SHORT_TIMEOUT).click();
		adminLogin.setLoggedIn(pageLoaded);
		driverManager.getAdminDriver().switchTo().frame(0);
		formElement = getElement(driverManager.getAdminDriver(), By.tagName(FORM), TINY_TIMEOUT);
		return pageLoaded;
	}

	/**
	 * Select accountslink.
	 * @see com.ecofactor.qa.automation.newapp.admin.page.AdminHomePage#selectAccountslink()
	 */
	@Override
	public void selectAccountslink() {

		setLogString("Select accounts link.", true);
		getElement(driverManager.getAdminDriver(), By.id(ACCOUNTS_NAV), TINY_TIMEOUT).click();
		mediumWait();
		driverManager.getAdminDriver().switchTo().frame(0);
		formElement = getElement(driverManager.getAdminDriver(), By.tagName(FORM), TINY_TIMEOUT);
	}

	/**
	 * Filter criteria.
	 * @param criteriaString the criteria string
	 * @see com.ecofactor.qa.automation.newapp.admin.page.AdminHomePage#filterCriteria(java.lang.String)
	 */
	public void filterCriteria(String criteriaString) {

		isPageLoaded();
		setLogString("Enter criteria to filter." + criteriaString, true);
		isDisplayed(driverManager.getAdminDriver(), By.name(CRITERIA), SHORT_TIMEOUT);
		final WebElement criteriaText = getElement(driverManager.getAdminDriver(), By.name(CRITERIA), TINY_TIMEOUT);
		criteriaText.sendKeys(criteriaString);
		final WebElement searchbutton = getElementBySubElementAttr(driverManager.getAdminDriver(), formElement, By.tagName(TAG_INPUT), ATTR_VALUE, SEARCH, SHORT_TIMEOUT);
		searchbutton.click();
		smallWait();
	}

	/**
	 * Click filtered record.
	 * @param recordNumber the record number
	 * @see com.ecofactor.qa.automation.newapp.admin.page.AdminHomePage#clickFilteredRecord(int)
	 */
	@Override
	public void clickFilteredRecord(int recordNumber) {

		setLogString("Select the record retrieved for the criteria filtered.", true);
		driverManager.getAdminDriver().switchTo().defaultContent();
		smallWait();
		driverManager.getAdminDriver().switchTo().frame(0);
		final List<WebElement> aElement = getElements(driverManager.getAdminDriver(), By.tagName(TAG_ANCHOR), TINY_TIMEOUT);
		aElement.get(0).click();
		smallWait();
	}

	/**
	 * Gets the required thermostat container.
	 * @param thermostat the thermostat
	 * @return the required thermostat container
	 */
	private WebElement getRequiredThermostatContainer(final String thermostat) {

		setLogString("Get current temperature for thermostat : " + thermostat, true);
		WebElement thermostatTable = null;
		setLogString("Get thermostats.", true);
		driverManager.getAdminDriver().switchTo().defaultContent();
		smallWait();
		driverManager.getAdminDriver().switchTo().frame(1);
		setLogString("Retrieve the thermostat data from the filtered data.", true);

		final WebElement bodyElement = getElement(driverManager.getAdminDriver(), By.tagName(TAG_BODY), TINY_TIMEOUT);
		final List<WebElement> itemTables = getElementsBySubElement(driverManager.getAdminDriver(), bodyElement, By.className(ITEM_TABLE), TINY_TIMEOUT);

		for (final WebElement itemTable : itemTables) {
			final List<WebElement> testThermostat = getElementsBySubElementText(driverManager.getAdminDriver(), itemTable, By.tagName(TAG_TD), thermostat.trim(), SHORT_TIMEOUT);
			if (testThermostat.size() > ZERO) {
				thermostatTable = itemTable;
				break;
			}
		}
		return thermostatTable;
	}

	/**
	 * Gets the current temperature.
	 * @param thermostatName thermostat name
	 * @return the current temperature
	 * @see com.ecofactor.qa.automation.newapp.admin.page.AdminHomePage#getCurrentTemperature(java.lang.String)
	 */
	@Override
	public String getCurrentTemperature(String thermostatName) {

		final WebElement thermostatTable = getRequiredThermostatContainer(thermostatName);

		setLogString("Fetch current temperature.", true);
		final WebElement fetchTemperature = getElementBySubElementAttr(driverManager.getAdminDriver(), thermostatTable, By.tagName(TAG_INPUT), ATTR_VALUE, FETCH, MEDIUM_TIMEOUT);
		mediumWait();
		fetchTemperature.click();
		mediumWait();
		setLogString("Fetch clicked!", true);
		smallWait();
		final WebElement bodyElement = getElement(driverManager.getAdminDriver(), By.tagName(TAG_BODY), TINY_TIMEOUT);
		final WebElement tempElement = getElementBySubElement(driverManager.getAdminDriver(), bodyElement, By.id(RUNNING_DIAG_TEXT), TINY_TIMEOUT);
		String temperature = tempElement.getText();
		setLogString("Current temperature from admin tool: " + tempElement.getText(), true);
		temperature = temperature.substring(temperature.indexOf(": ", 0) + 1, temperature.indexOf(" (")).replaceAll("F", "").replaceAll("C", "");
		setLogString("Close the fetch", true);
		final WebElement runningBodyElement = getElementBySubElement(driverManager.getAdminDriver(), bodyElement, By.id(RUNNING_DIAG_BODY), TINY_TIMEOUT);
		getElementBySubElement(driverManager.getAdminDriver(), runningBodyElement, By.tagName(TAG_INPUT), TINY_TIMEOUT).click();
		smallWait();
		return temperature.trim();
	}

	/**
	 * logout.
	 * @see com.ecofactor.qa.automation.newapp.admin.page.AdminHomePage#logout()
	 */
	@Override
	public void logout() {

		setLogString("Click logout", true);
		driverManager.getAdminDriver().switchTo().defaultContent();
		final WebElement logoutLink = getElementByText(driverManager.getAdminDriver(), By.tagName(TAG_ANCHOR), LOGOUT, SHORT_TIMEOUT);
		logoutLink.click();
		tinyWait();
	}

	/**
	 * Click dialogue link.
	 * @param thermostatName thermostat name
	 * @see com.ecofactor.qa.automation.newapp.admin.page.AdminHomePage#clickDiagLink(java.lang.String)
	 */
	@Override
	public void clickDiagLink(String thermostatName) {

		setLogString("Click dialogue link", true);
		final WebElement thermostatTable = getRequiredThermostatContainer(thermostatName);
		setLogString("Click diag link", true);
		final WebElement dialogueElement = getElementBySubElementText(driverManager.getAdminDriver(), thermostatTable, By.tagName(TAG_ANCHOR), DIAG, MEDIUM_TIMEOUT);
		dialogueElement.click();
		mediumWait();
	}
}
