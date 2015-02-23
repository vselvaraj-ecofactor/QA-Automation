/*
 * QTCLoginImpl.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.qtc.page;

import static com.ecofactor.qa.automation.qtc.config.LoginConfig.*;
import static com.ecofactor.qa.automation.qtc.config.QTCConfig.*;
import static com.ecofactor.qa.automation.util.PageUtil.*;
import static com.ecofactor.qa.automation.util.WaitUtil.*;
import static org.testng.Assert.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.ecofactor.qa.automation.qtc.config.LoginConfig;
import com.ecofactor.qa.automation.qtc.config.QTCConfig;
import com.ecofactor.qa.automation.util.DriverConfig;
import com.ecofactor.qa.automation.util.PageAction;
import com.google.inject.Inject;

/**
 * The Class QTCLoginImpl.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class QTCLoginImpl extends PageAction implements QTCLogin {

	@Inject
	private LoginConfig loginConfig;
	@Inject
	private QTCConfig qtcConfig;

	/**
	 * Enter login.
	 * @param userName the user name
	 * @param password the password
	 * @see com.ecofactor.qa.automation.qtc.page.QTCLogin#enterLogin(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public void enterLogin(String userName, String password) {

		largeWait();

		//boolean qtcPage = isDisplayedByClassName(DriverConfig.getDriver(), loginConfig.get(USER_NAME), SHORT_TIMEOUT);
		//assertTrue(qtcPage, "Page is not loading");
		WebElement userField = DriverConfig.getDriver().findElement(By.name(loginConfig.get(USER_NAME)));
		WebElement passwordField = DriverConfig.getDriver().findElement(By.name(loginConfig.get(PASSWORD)));
		assertNotNull(userField);
		assertNotNull(passwordField);

		DriverConfig.setLogString("Enter username and password", true);
		userField.sendKeys(userName);
		passwordField.sendKeys(password);

	}

	/**
	 * @param userName
	 * @param password
	 * @see com.ecofactor.qa.automation.qtc.page.QTCLogin#login(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public void login(String userName, String password) {

		DriverConfig.setLogString("Do login for "+ userName, true);
		enterLogin(userName, password);

		DriverConfig.setLogString("Verify submit button, ", true);
		WebElement submitBtn = retrieveElementByAttributeValue(DriverConfig.getDriver(), TAG_INPUT, ATTR_VALUE, loginConfig.get(SUBMIT));
		assertNotNull(submitBtn);

		DriverConfig.setLogString("Click the Login button", true);
		submitBtn.click();

		mediumWait();
		DriverConfig.getDriver().get(DriverConfig.getDriver().getCurrentUrl());
	}

	/**
	 * @see com.ecofactor.qa.automation.qtc.page.QTCPage#loadPage()
	 */
	@Override
	public void loadPage() {

		if (DriverConfig.getDriver() == null) {
			getDriver();
		}

		DriverConfig.setLogString("Go to QTC Login page : " + qtcConfig.get(BASE_URL) + loginConfig.get(LOGIN_URL), true);
		DriverConfig.getDriver().get(qtcConfig.get(BASE_URL) + loginConfig.get(LOGIN_URL));

		mediumWait();

		waitForPageLoaded(DriverConfig.getDriver());

		mediumWait();
	}


}
