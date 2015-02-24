/*
 * SupportAccountLookUpTest.java
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
import com.ecofactor.qa.automation.insite.InsiteModule;
import com.ecofactor.qa.automation.insite.data.InsiteSupportDataProvider;

import com.ecofactor.qa.automation.insite.page.SupportLookUp;
import com.ecofactor.qa.automation.util.DriverConfig;
import com.ecofactor.qa.automation.util.RerunFailTestAnalyzer;
import com.ecofactor.qa.automation.util.TestLogUtil;
import com.ecofactor.qa.automation.util.UtilModule;

import com.ecofactor.qa.automation.util.mail.GmailForNewUser;
import com.ecofactor.qa.automation.util.test.JobValidator;
import com.google.inject.Inject;

/**
 * <b>SupportAccountLookUp</b>
 * <p>
 * Test methods to check the Account LookUp functionality. <br/>
 * Some of the features such as search by email Id, search by phone No,
 * verifying the userDetail etc.
 * </p>
 * *
 * 
 * @author Aximsoft
 */
@Guice(modules = { UtilModule.class, DaoModule.class, InsiteModule.class })
@Listeners(JobValidator.class)
public class SupportAccountLookUpTest {

	/** The support look up. */
	@Inject
	private SupportLookUp supportLookUp;

	/** The gmail. */
	@Inject
	private GmailForNewUser gmail;

	/** The test log util. */
	@Inject
	private TestLogUtil testLogUtil;

	/** The logger. */
	private static Logger logger = LoggerFactory
			.getLogger(SupportAccountLookUpTest.class);

	/** The start. */
	private long start;

	/**
	 * Inits the method.
	 * 
	 * @param param
	 *            the param
	 * @param method
	 *            the method
	 */
	@BeforeMethod(alwaysRun = true)
	public void initMethod(Object[] param, Method method) {

		testLogUtil.logStart(method, param);
		start = System.currentTimeMillis();

		try {
			if (method.getName() == "testResetPasswordViaAdmin") {
				gmail.deleteOldMails((String) param[4], (String) param[5],
						(String) param[6], (String) param[7]);
			}
			supportLookUp.loadPage((String) param[0], (String) param[1]);
		} catch (Throwable e) {
			logger.error("Error in before method " + e.getMessage(), true);
		}
	}

	/**
	 * End method.
	 * 
	 * @param method
	 *            the method
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
			supportLookUp.logout();
		} catch (Throwable e) {
			logger.error("Error in after class method  " + e.getMessage(), true);
		}
	}

	/**
	 * End suite.
	 */
	@AfterSuite(alwaysRun = true)
	public void endSuite() {

		try {
			supportLookUp.end();
		} catch (Throwable e) {
			logger.error("Error in after suite method  " + e.getMessage(), true);
		}
	}

	/**
	 * <b>Test Case to search the particular email record by entering the email
	 * value, and verify the search result is displayed.</b>
	 * <ol>
	 * <li>Pass the email address via suite parameter and click on FindButton.</li>
	 * <li>Verify the particular emailId is displayed.</li>
	 * </ol>
	 * 
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 * @param emailId
	 *            the email id
	 */

	@Test(dataProvider = "searchByEmailId", dataProviderClass = InsiteSupportDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "sanity" }, priority = 1)
	public void testSearchRecordsByEmailId(final String userName,
			final String password, final String emailId) {

		supportLookUp.searchByEmail(emailId);

	}

	/**
	 * <b>Test Case to search the particular PhoneNo. record by entering the
	 * PhoneNo value, and verify the search result is displayed.</b>
	 * <ol>
	 * <li>Pass the PhoneNo. via suite parameter and click on FindButton.</li>
	 * <li>Verify the particular PhoneNo is displayed in the SearchResult.</li>
	 * </ol>
	 * 
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 * @param phoneNo
	 *            the phone no
	 */
	@Test(dataProvider = "searchByPhoneNo", dataProviderClass = InsiteSupportDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "sanity" }, priority = 2)
	public void testSearchRecordsByPhoneNo(final String userName,
			final String password, final String phoneNo) {

		supportLookUp.searchByPhone(phoneNo);

	}

	/**
	 * <b>Test case to check the Thermostat connectivity in the Installed
	 * hardware menu</b>.
	 * 
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 * @param emailId
	 *            the email id
	 */
	@Test(dataProvider = "testThermostatConnection", dataProviderClass = InsiteSupportDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "sanity" }, priority = 3)
	public void testThermostatConnectivityForInstalledHardware(
			final String userName, final String password, final String emailId) {

		supportLookUp.searchByEmail(emailId);
		supportLookUp.verifyInstallationHardware(emailId);

	}

	/**
	 * Verify account and location info.
	 * 
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 * @param searchEmailId
	 *            the search email id
	 */
	@Test(dataProvider = "verifyAccountAndLocationInfo", dataProviderClass = InsiteSupportDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "sanity" }, priority = 4)
	public void verifyAccountAndLocationInfo(final String userName,
			final String password, final String searchEmailId) {

		supportLookUp.searchByEmail(searchEmailId);

		supportLookUp.clickSpecifiedEmail(searchEmailId);

		supportLookUp.clickHomeOwnerEdit();

		supportLookUp.clickPopUpCancel();

		supportLookUp.clickPhoneNumberEdit();

		supportLookUp.clickPopUpCancel();

		supportLookUp.clickEnergyEfficiencyRefresh();

		supportLookUp.clickEcpCoreEdit();

		supportLookUp.clickPopUpCancel();

		supportLookUp.clickUserNameEdit();

		supportLookUp.clickPopUpCancel();

		supportLookUp.clickEmailIdEdit();

		supportLookUp.clickPopUpCancel();

		supportLookUp.clickElectricityRateEdit();

		supportLookUp.clickPopUpCancel();

		supportLookUp.clickGasRateEdit();

		supportLookUp.clickPopUpCancel();

	}

	/**
	 * Deny proxy login.
	 * 
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 * @param searchEmailId
	 *            the search email id
	 */
	@Test(dataProvider = "denyProxyLogin", dataProviderClass = InsiteSupportDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "validation" }, priority = 5)
	public void denyProxyLogin(final String userName, final String password,
			final String searchEmailId) {

		supportLookUp.searchByEmail(searchEmailId);

		supportLookUp.clickSpecifiedEmail(searchEmailId);

		supportLookUp.clickProxyLogin();

		Assert.assertTrue(supportLookUp.verifyProxyDenied(),
				"Proxy Login not allowed' message is not displayed");
	}

	/**
	 * Support tab for nv esuer.
	 * 
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 */
	@Test(dataProvider = "supportTabForNVEsuer", dataProviderClass = InsiteSupportDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "validation" }, priority = 6)
	public void supportTabForNVEsuer(final String userName,
			final String password) {

		supportLookUp.clickSupport();

	}

	/**
	 * Comcast email and name empty verification.
	 * 
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 */
	@Test(dataProvider = "comcastCrednetials", dataProviderClass = InsiteSupportDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "validation" }, priority = 7)
	public void comcastEmailAndNameEmptyVerification(final String userName,
			final String password) {

		supportLookUp.searchByEmail("");

		tinyWait();

		supportLookUp.iterateResultsAndVerifyEmptyFields();

	}

	/**
	 * Comcast id verification.
	 * 
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 */
	@Test(dataProvider = "comcastCrednetials", dataProviderClass = InsiteSupportDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "validation" }, priority = 8)
	public void comcastIdVerification(final String userName,
			final String password) {

		supportLookUp.searchByEmail("");

		tinyWait();

		supportLookUp.iterateResultsAndVerifyComcastId();

	}

	/**
	 * Comcast account and location info.
	 * 
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 */
	@Test(dataProvider = "comcastCrednetials", dataProviderClass = InsiteSupportDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "validation" }, priority = 9)
	public void comcastAccountAndLocationInfo(final String userName,
			final String password) {

		supportLookUp.searchByEmail("");

		tinyWait();

		supportLookUp.clickTopFirstUser();

	}

	/**
	 * Comcast energy efficiency check.
	 * 
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 */
	@Test(dataProvider = "comcastCrednetials", dataProviderClass = InsiteSupportDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "validation" }, priority = 10)
	public void comcastEnergyEfficiencyCheck(final String userName,
			final String password) {

		supportLookUp.searchByEmail("");

		tinyWait();

		supportLookUp.clickTopFirstUser();

		tinyWait();

		// Click dual times to check the click is working properly
		supportLookUp.clickEnergyEfficiencyCheckBox();

		tinyWait();

		supportLookUp.clickEnergyEfficiencyCheckBox();
	}

	/**
	 * Comcast proxy login and reset pwd.
	 * 
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 */
	@Test(dataProvider = "comcastCrednetials", dataProviderClass = InsiteSupportDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "validation" }, priority = 11)
	public void comcastProxyLoginAndResetPwd(final String userName,
			final String password) {

		supportLookUp.searchByEmail("");

		tinyWait();

		supportLookUp.clickTopFirstUser();

		tinyWait();

		supportLookUp.verifyNoProxyAndResetLink();
	}

	/**
	 * Comcast export offline thermostats.
	 * 
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 */
	@Test(dataProvider = "comcastCrednetials", dataProviderClass = InsiteSupportDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "validation" }, priority = 12)
	public void comcastExportOfflineThermostats(final String userName,
			final String password) {

		supportLookUp.verifyNoExportOfflineThermostatLink();
	}

	/**
	 * Ecofactor logo link.
	 * 
	 * @param userId
	 *            the user id
	 * @param password
	 *            the password
	 */
	@Test(dataProvider = "validLogin", dataProviderClass = InsiteSupportDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "smoke" }, priority = 13)
	public void headerLogoLink(final String userId, final String password) {

		supportLookUp.isLogoDisplayed();

	}

	/**
	 * Test the welcome text for the logged in user in the insite home page.
	 * 
	 * @param userId
	 *            the user id
	 * @param password
	 *            the password
	 */
	@Test(dataProvider = "validLogin", dataProviderClass = InsiteSupportDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "smoke" }, priority = 14)
	public void welcomeText(final String userId, final String password) {

		supportLookUp.verifyWelcomeText(userId);
	}

	/**
	 * Test the account link in the insite home page.
	 * 
	 * @param userId
	 *            the user id
	 * @param password
	 *            the password
	 */
	@Test(dataProvider = "validLogin", dataProviderClass = InsiteSupportDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "smoke" }, priority = 15)
	public void supportLink(final String userId, final String password) {

		supportLookUp.clickSupport();
	}

	/**
	 * Installation link.
	 * 
	 * @param userId
	 *            the user id
	 * @param password
	 *            the password
	 */
	@Test(dataProvider = "validLogin", dataProviderClass = InsiteSupportDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "smoke" }, priority = 16)
	public void installationLink(final String userId, final String password) {

		supportLookUp.clickInstallation();
	}

	/**
	 * Installation schedule link.
	 * 
	 * @param userId
	 *            the user id
	 * @param password
	 *            the password
	 */
	@Test(dataProvider = "validLogin", dataProviderClass = InsiteSupportDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "smoke" }, priority = 20)
	public void installationScheduleLink(final String userId,
			final String password) {

		supportLookUp.clickScheduling();

	}

	/**
	 * Installation pre config link.
	 * 
	 * @param userId
	 *            the user id
	 * @param password
	 *            the password
	 */
	@Test(dataProvider = "validLogin", dataProviderClass = InsiteSupportDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "smoke" }, priority = 22)
	public void installationPreConfigLink(final String userId,
			final String password) {

		supportLookUp.clickPreConfiguration();
		supportLookUp.logout();
	}

	/**
	 * User management link.
	 * 
	 * @param userId
	 *            the user id
	 * @param password
	 *            the password
	 */
	@Test(dataProvider = "validLogin", dataProviderClass = InsiteSupportDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "smoke" }, priority = 19)
	public void userManagementLink(final String userId, final String password) {

		supportLookUp.clickUserManagement();
	}

	/**
	 * Role management link.
	 * 
	 * @param userId
	 *            the user id
	 * @param password
	 *            the password
	 */
	@Test(dataProvider = "validLogin", dataProviderClass = InsiteSupportDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "smoke" }, priority = 17)
	public void roleManagementLink(final String userId, final String password) {

		supportLookUp.clickRoleManagement();
	}

	/**
	 * Demand side management link.
	 * 
	 * @param userId
	 *            the user id
	 * @param password
	 *            the password
	 */
	@Test(dataProvider = "validLogin", dataProviderClass = InsiteSupportDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "smoke" }, priority = 18)
	public void demandSideManagementLink(final String userId,
			final String password) {

		supportLookUp.clickDemandSideManagement();
	}

	/**
	 * Test the user logout from a insite authenticated page.
	 * 
	 * @param userId
	 *            the user id
	 * @param password
	 *            the password
	 */
	@Test(dataProvider = "validLogin", dataProviderClass = InsiteSupportDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "smoke" }, priority = 21)
	public void logout(final String userId, final String password) {

		supportLookUp.logout();
	}

	/**
	 * In valid electricty rate.
	 * 
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 * @param searchEmailId
	 *            the search email id
	 * @param msg
	 *            the msg
	 */
	@Test(dataProvider = "verifyElectricityRate", dataProviderClass = InsiteSupportDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "negative" }, priority = 23)
	public void inValidElectrictyRate(final String userName,
			final String password, final String searchEmailId, final String msg) {

		supportLookUp.searchByEmail(searchEmailId);

		supportLookUp.clickSpecifiedEmail(searchEmailId);

		supportLookUp.clickElectricityRateEdit();

		supportLookUp.enterCharacterToElectricityRate("abc");

		Assert.assertTrue(supportLookUp.getErrorMessage().equalsIgnoreCase(msg));

		supportLookUp.clickPopUpCancel();
	}

	/**
	 * In valid gas rate.
	 * 
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 * @param searchEmailId
	 *            the search email id
	 * @param msg
	 *            the msg
	 */
	@Test(dataProvider = "verifyGasRate", dataProviderClass = InsiteSupportDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "negative" }, priority = 24)
	public void inValidGasRate(final String userName, final String password,
			final String searchEmailId, final String msg) {

		supportLookUp.searchByEmail(searchEmailId);

		supportLookUp.clickSpecifiedEmail(searchEmailId);

		supportLookUp.clickGasRateEdit();

		supportLookUp.enterCharacterToGasRate("abc");

		Assert.assertTrue(supportLookUp.getErrorMessage().equalsIgnoreCase(msg));

		supportLookUp.clickPopUpCancel();
	}

	/**
	 * Test more than10 digits phone number.
	 * 
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 * @param searchEmailId
	 *            the search email id
	 * @param invalidPhoneNumber
	 *            the invalid phone number
	 */
	@Test(dataProvider = "verifyPhoneNumber", dataProviderClass = InsiteSupportDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "negative" }, priority = 25)
	public void moreThan10DigitPhoneNumber(final String userName,
			final String password, final String searchEmailId,
			final String invalidPhoneNumber) {

		supportLookUp.searchByEmail(searchEmailId);

		supportLookUp.clickSpecifiedEmail(searchEmailId);

		supportLookUp.clickPhoneNumberEdit();

		supportLookUp.enterPhoneNumber(invalidPhoneNumber);

		String errorMessage = supportLookUp.verifyErrorMessage();

		Assert.assertTrue(
				errorMessage.equalsIgnoreCase("Invalid phone number"),
				"Error message is not displayed");

		supportLookUp.clickPopUpCancel();
	}

	/*
	 * @Test(dataProvider = "verifyEmailID", dataProviderClass =
	 * InsiteSupportDataProvider.class, retryAnalyzer =
	 * RerunFailTestAnalyzer.class, groups = { "negative" }) public void
	 * testInvalidEmailID(String userName, String password, String
	 * searchEmailId, String msg) { supportLookUp.searchByEmail(searchEmailId);
	 * supportLookUp.clickSpecifiedEmail(searchEmailId);
	 * supportLookUp.clickEmailIdEdit();
	 * supportLookUp.enterEmailId("no @,.com");
	 * Assert.assertTrue(supportLookUp.getErrorMessage().equalsIgnoreCase(msg));
	 * supportLookUp.clickPopUpCancel(); }
	 */

	// Blocked - Past Installation Date and Time accepted.
	/*
	 * @Test(dataProvider = "pastInstallationDate", dataProviderClass =
	 * InsiteSupportDataProvider.class, retryAnalyzer =
	 * RerunFailTestAnalyzer.class, groups = { "negative" }) public void
	 * testPastInstallationDate(String userName, String password, String
	 * address, String date, String msg) { supportLookUp.clickScheduling();
	 * supportLookUp.searchByAddress(address);
	 * supportLookUp.clickFirstAddress(address);
	 * supportLookUp.clickInstallationDate();
	 * Assert.assertTrue(supportLookUp.getErrorMessage().equalsIgnoreCase(msg));
	 * supportLookUp.clickPopUpCancel(); }
	 */

	/**
	 * Test invalid email id.
	 * 
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 * @param searchEmailId
	 *            the search email id
	 */
	@Test(dataProvider = "searchNonExsistingEmail", dataProviderClass = InsiteSupportDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "sanity" }, priority = 26)
	public void testNonExsistingEmailId(final String userName,
			final String password, final String searchEmailId) {

		supportLookUp.searchByEmailId(searchEmailId);
		Assert.assertTrue(supportLookUp.isNoResultDisplayed(), "Not Displayed");
	}

	/**
	 * Test non existing phone number.
	 * 
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 * @param searchPhoneNo
	 *            the search phone no
	 */
	@Test(dataProvider = "searchNonExsistingPhoneNo", dataProviderClass = InsiteSupportDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "sanity" }, priority = 27)
	public void testNonExsistingPhoneNumber(final String userName,
			final String password, final String searchPhoneNo) {

		supportLookUp.searchByPhoneNumber(searchPhoneNo);
		Assert.assertTrue(supportLookUp.isNoResultDisplayed(), "Not Displayed");
	}

	/**
	 * Test non existing id.
	 * 
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 * @param searchId
	 *            the search id
	 */
	@Test(dataProvider = "searchNonExsistingId", dataProviderClass = InsiteSupportDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "sanity" }, priority = 28)
	public void testNonExsistingId(final String userName,
			final String password, final String searchId) {

		supportLookUp.searchById(searchId);
		Assert.assertTrue(supportLookUp.isNoResultDisplayed(), "Not Displayed");
	}

	/**
	 * Test non existing street addr.
	 * 
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 * @param searchStreetAddress
	 *            the search street address
	 */
	@Test(dataProvider = "searchNonExsistingStreet", dataProviderClass = InsiteSupportDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "sanity" }, priority = 29)
	public void testNonExsistingStreetAddr(final String userName,
			final String password, final String searchStreetAddress) {

		supportLookUp.searchByStreetAddr(searchStreetAddress);
		Assert.assertTrue(supportLookUp.isNoResultDisplayed(), "Not Displayed");
	}

	/**
	 * Search by valid email.
	 * 
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 * @param searchEmail
	 *            the search Email
	 */
	@Test(dataProvider = "searchByValidEmail", dataProviderClass = InsiteSupportDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "sanity" }, priority = 30)
	public void searchByValidEmail(final String userName,
			final String password, final String searchEmail) {

		supportLookUp.searchByEmailId(searchEmail);
		tinyWait();
		supportLookUp.confirmSearchResultValue(searchEmail);
	}

	/**
	 * Search by valid phone number.
	 * 
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 * @param searchPhoneNo
	 *            the search phone no
	 */
	@Test(dataProvider = "searchByValidPhoneNo", dataProviderClass = InsiteSupportDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "sanity" }, priority = 31)
	public void searchByValidPhoneNumber(final String userName,
			final String password, final String searchPhoneNo) {

		supportLookUp.searchByPhoneNumber(searchPhoneNo);
		tinyWait();
		supportLookUp.confirmSearchResultValue(searchPhoneNo);
	}

	/**
	 * Search by valid street address.
	 * 
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 * @param searchStreetAddress
	 *            the search street address
	 */
	@Test(dataProvider = "searchByValidStreetAddress", dataProviderClass = InsiteSupportDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "sanity" }, priority = 32)
	public void searchByValidStreetAddress(final String userName,
			final String password, final String searchStreetAddress) {

		supportLookUp.searchByStreetAddr(searchStreetAddress);
		tinyWait();
		supportLookUp.confirmSearchResultValue(searchStreetAddress);
	}

	/**
	 * Search by valid id.
	 * 
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 * @param searchId
	 *            the search id
	 */
	@Test(dataProvider = "searchByValidId", dataProviderClass = InsiteSupportDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "sanity" }, priority = 33)
	public void searchByValidId(final String userName, final String password,
			final String searchId) {

		supportLookUp.searchById(searchId);
		tinyWait();
		supportLookUp.confirmSearchResultValue(searchId);
	}

	/**
	 * Search by partial email.
	 * 
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 * @param email
	 *            the email
	 * @param searchEmail
	 *            the search email
	 */
	@Test(dataProvider = "searchByPartialEmail", dataProviderClass = InsiteSupportDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "sanity" }, priority = 34)
	public void searchByPartialEmail(final String userName,
			final String password, final String email, final String searchEmail) {

		supportLookUp.searchByEmailId(searchEmail);
		tinyWait();
		Assert.assertTrue(supportLookUp.isEmailSearchTextDisplayed(email),
				"Search User is not displayed");
	}

	/**
	 * Search by partial phone number.
	 * 
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 * @param phoneNo
	 *            the phone no
	 * @param searchPhoneNo
	 *            the search phone no
	 */
	@Test(dataProvider = "searchByPartialPhoneNo", dataProviderClass = InsiteSupportDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "sanity" }, priority = 35)
	public void searchByPartialPhoneNumber(final String userName,
			final String password, final String phoneNo,
			final String searchPhoneNo) {

		supportLookUp.searchByPhoneNumber(searchPhoneNo);
		tinyWait();
		Assert.assertTrue(supportLookUp.isSearchTextDisplayed(phoneNo),
				"Search User is not displayed");
	}

	/**
	 * Search by partial street address.
	 * 
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 * @param streetAddress
	 *            the street address
	 * @param searchStreetAddress
	 *            the search street address
	 */
	@Test(dataProvider = "searchByPartialStreetAddress", dataProviderClass = InsiteSupportDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "sanity" }, priority = 36)
	public void searchByPartialStreetAddress(final String userName,
			final String password, final String streetAddress,
			final String searchStreetAddress) {

		supportLookUp.searchByStreetAddr(searchStreetAddress);
		tinyWait();
		Assert.assertTrue(supportLookUp.isSearchTextDisplayed(streetAddress),
				"Search User is not displayed");
	}

	/**
	 * <ol>
	 * <li>Login via Admin and reset the password for the specified user and
	 * check it in consumer portal.</li>
	 * </ol>
	 * 
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 * @param searchEmailId
	 *            the search email id
	 * @param searchUserName
	 *            the search user name
	 * @param emailUrl
	 *            the email url
	 * @param emailUserName
	 *            the email user name
	 * @param emailPassword
	 *            the email password
	 * @param subject
	 *            the subject
	 * @param newAppUrl
	 *            the new app url
	 * @param newPassword
	 *            the new password
	 */
	@Test(dataProvider = "resetPswd", dataProviderClass = InsiteSupportDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "sanity" }, priority = 38)
	public void testResetPasswordViaAdmin(final String userName,
			final String password, final String searchEmailId,
			final String searchUserName, final String emailUrl,
			final String emailUserName, final String emailPassword,
			final String subject, final String newAppUrl,
			final String newPassword) {

		supportLookUp.searchByEmail(searchEmailId);
		supportLookUp.clickSearchedResultElement(searchEmailId);
		supportLookUp.clickResetPassword();
		supportLookUp.logout();
		DriverConfig.setLogString("Get the password.", true);
		final String temporaryPassword = gmail.getChangedPassword(emailUrl,
				emailUserName, emailPassword, subject, 0, 0);
		/*
		 * DriverConfig.setLogString(
		 * "proxy login for the user with new temporay password sent via eamil."
		 * , true); insiteLogin.loadPage(); insiteLogin.login(userName,
		 * password); supportLookUp.clickSupport();
		 * supportLookUp.searchByEmail(searchEmailId);
		 * supportLookUp.clickSearchedResultElement(searchEmailId);
		 * supportLookUp.clickProxyLogin();
		 */
		smallWait();
		supportLookUp.doVerifyPswdResetByNewAppLogin(searchEmailId,
				temporaryPassword, newPassword, newAppUrl);
	}

}
