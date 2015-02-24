/*
 * UploadOneUserTest.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.insite.regression;

import java.lang.reflect.Method;
import java.util.Calendar;

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
import com.ecofactor.qa.automation.insite.data.UploadOneUserDataProvider;

import com.ecofactor.qa.automation.insite.page.UploadOneUser;
import com.ecofactor.qa.automation.util.DateUtil;
import com.ecofactor.qa.automation.util.DriverConfig;
import com.ecofactor.qa.automation.util.RerunFailTestAnalyzer;
import com.ecofactor.qa.automation.util.TestLogUtil;
import com.ecofactor.qa.automation.util.UtilModule;
import com.ecofactor.qa.automation.util.test.JobValidator;
import com.google.inject.Inject;

/**
 * The Class UploadOneUserTest.
 * 
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
@Guice(modules = { UtilModule.class, InsiteModule.class, DaoModule.class })
@Listeners(JobValidator.class)
public class UploadOneUserTest {

	@Inject
	private UploadOneUser uploadOneUser;

	@Inject
	private TestLogUtil testLogUtil;
	private static final Logger logger = LoggerFactory
			.getLogger(UploadOneUserTest.class);
	private long start;
	private String emailId = null;

	/**
	 * Inits the method.
	 * 
	 * @param param
	 *            the param
	 * @param method
	 *            the method
	 */
	@BeforeMethod(alwaysRun = true)
	public void beforeMethod(Object[] param, Method method) {

		testLogUtil.logStart(method, param);
		start = System.currentTimeMillis();
		emailId = "user_" + System.currentTimeMillis() + "@gmail.com";

		try {
			uploadOneUser.loadPage((String) param[0], (String) param[1]);
		} catch (Throwable t) {
			logger.error(t.getMessage(), t);
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
			uploadOneUser.logout();
		} catch (Throwable e) {
			logger.error("Error in after class " + e.getMessage(), true);
		}
	}

	/**
	 * End suite.
	 */
	@AfterSuite(alwaysRun = true)
	public void endSuite() {

		try {
			uploadOneUser.end();
		} catch (Throwable e) {
			logger.error("Error in after suite " + e.getMessage(), true);
		}
	}

	/**
	 * Test upload one user.
	 * 
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 */
	@Test(dataProvider = "createUser", dataProviderClass = UploadOneUserDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "sanity" })
	public void testUploadOneUser(final String userName, final String password) {

		uploadOneUser.populateForm(emailId, "205");
		uploadOneUser.validateAndSubmitForm();

	}

	/**
	 * Test past installation date.
	 * 
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 */
	@Test(dataProvider = "createUser", dataProviderClass = UploadOneUserDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "negativescenario" })
	public void testPastInstallationDate(final String userName,
			final String password) {

		Calendar installationDate = DateUtil.getUTCCalendar();
		installationDate.add(Calendar.DATE, -2);
		DriverConfig.setLogString("installation date provided :"
				+ installationDate, true);

		uploadOneUser.populateForm(emailId, "PastInstallationDate",
				DateUtil.format(installationDate, "MM/dd/yyyy"));
		uploadOneUser.validateAndSubmitForm("PastInstallationDate");
	}

	/**
	 * Test future created date.
	 * 
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 */
	@Test(dataProvider = "createUser", dataProviderClass = UploadOneUserDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "negativescenario" })
	public void testFutureCreatedDate(final String userName,
			final String password) {

		Calendar createdDate = DateUtil.getUTCCalendar();
		createdDate.add(Calendar.DATE, +2);
		DriverConfig.setLogString("Created date provided : " + createdDate,
				true);

		uploadOneUser.populateForm(emailId, "FutureCreatedDate",
				DateUtil.format(createdDate, "MM/dd/yyyy"));
		uploadOneUser.validateAndSubmitForm("FutureCreatedDate");
	}

	/**
	 * Test country code length.
	 * 
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 */
	@Test(dataProvider = "createUser", dataProviderClass = UploadOneUserDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "negativescenario" })
	public void testCountryCodeLength(final String userName,
			final String password) {

		uploadOneUser.populateForm(emailId, "CountryCodeLength",
				"United States");
		uploadOneUser.validateAndSubmitForm("CountryCodeLength");
	}

	/**
	 * Test invalid email id.
	 * 
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 */
	@Test(dataProvider = "createUser", dataProviderClass = UploadOneUserDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "negativescenario" })
	public void testInvalidEmailId(final String userName, final String password) {

		uploadOneUser.populateForm(emailId, "InvalidEmailId", "abs.com");
		uploadOneUser.validateAndSubmitForm("InvalidEmailId");
	}

	/**
	 * Test invalid program id.
	 * 
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 */
	@Test(dataProvider = "createUser", dataProviderClass = UploadOneUserDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "negativescenario" })
	public void testInvalidProgramId(final String userName,
			final String password) {

		uploadOneUser.populateForm(emailId, "InvalidProgramId", "-901");
		uploadOneUser.validateAndSubmitForm("InvalidProgramId");
	}

	/**
	 * Test other languages.
	 * 
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 */
	/*
	 * @Test(dataProvider = "createUser", dataProviderClass =
	 * UploadOneUserDataProvider.class, retryAnalyzer =
	 * RerunFailTestAnalyzer.class, groups = { "negativescenario" }) public void
	 * testOtherLanguages(final String userName, final String password) {
	 * 
	 * uploadOneUser.populateForm(emailId, "OtherLanguages", "Latin");
	 * uploadOneUser.validateAndSubmitForm("OtherLanguages"); }
	 */

	/**
	 * Test blank mandatory field.
	 * 
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 */
	@Test(dataProvider = "createUser", dataProviderClass = UploadOneUserDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "negativescenario" })
	public void testBlankMandatoryField(final String userName,
			final String password) {

		uploadOneUser.populateForm(emailId, "BlankMandatoryField", "");
		uploadOneUser.validateAndSubmitForm("BlankMandatoryField");
	}

	/**
	 * Test charachters in program id.
	 * 
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 */
	@Test(dataProvider = "createUser", dataProviderClass = UploadOneUserDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "negativescenario" })
	public void testCharachtersInProgramId(final String userName,
			final String password) {

		uploadOneUser.populateForm(emailId, "CharachtersInProgramId",
				"programid");
		uploadOneUser.validateAndSubmitForm("CharachtersInProgramId");
	}

	/**
	 * Test charachters in phone no.
	 * 
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 */
	/*
	 * @Test(dataProvider = "createUser", dataProviderClass =
	 * UploadOneUserDataProvider.class, retryAnalyzer =
	 * RerunFailTestAnalyzer.class, groups = { "negativescenario" }) public void
	 * testCharachtersInPhoneNo(final String userName, final String password) {
	 * 
	 * uploadOneUser.populateForm(emailId, "CharachtersInPhoneNo","phoneno");
	 * uploadOneUser.validateAndSubmitForm("CharachtersInPhoneNo"); }
	 */

	/**
	 * Test characthers in zip code.
	 * 
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 */
	/*
	 * @Test(dataProvider = "createUser", dataProviderClass =
	 * UploadOneUserDataProvider.class, retryAnalyzer =
	 * RerunFailTestAnalyzer.class, groups = { "negativescenario" }) public void
	 * testCharacthersInZipCode(final String userName, final String password) {
	 * 
	 * uploadOneUser.populateForm(emailId, "CharacthersInZipCode", "ZipCode");
	 * uploadOneUser.validateAndSubmitForm("CharacthersInZipCode"); }
	 */

	/**
	 * Test numbers in state field.
	 * 
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 */
	/*
	 * @Test(dataProvider = "createUser", dataProviderClass =
	 * UploadOneUserDataProvider.class, retryAnalyzer =
	 * RerunFailTestAnalyzer.class, groups = { "negativescenario" }) public void
	 * testNumbersInStateField(final String userName, final String password) {
	 * 
	 * uploadOneUser.populateForm(emailId, "NumbersInStateField", "123");
	 * uploadOneUser.validateAndSubmitForm("NumbersInStateField"); }
	 */

	/**
	 * Test state code length.
	 * 
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 */
	/*
	 * @Test(dataProvider = "createUser", dataProviderClass =
	 * UploadOneUserDataProvider.class, retryAnalyzer =
	 * RerunFailTestAnalyzer.class, groups = { "negativescenario" }) public void
	 * testStateCodeLength(final String userName, final String password) {
	 * 
	 * uploadOneUser.populateForm(emailId, "StateCodeLength", "CALIFORNIA");
	 * uploadOneUser.validateAndSubmitForm("StateCodeLength"); }
	 */

	/**
	 * Test charachters in no of tstat field.
	 * 
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 */
	@Test(dataProvider = "createUser", dataProviderClass = UploadOneUserDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "negativescenario" })
	public void testCharachtersInNoOfTSTATField(final String userName,
			final String password) {

		uploadOneUser.populateForm(emailId, "CharachtersInNoOfTSTATField",
				"TSTATS");
		uploadOneUser.validateAndSubmitForm("CharachtersInNoOfTSTATField");
	}

	/**
	 * Test charachters in avg price.
	 * 
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 */
	@Test(dataProvider = "createUser", dataProviderClass = UploadOneUserDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "negativescenario" })
	public void testCharachtersInAvgPrice(final String userName,
			final String password) {

		uploadOneUser
				.populateForm(emailId, "CharachtersInAvgPrice", "AvgPrice");
		uploadOneUser.validateAndSubmitForm("CharachtersInAvgPrice");
	}
}