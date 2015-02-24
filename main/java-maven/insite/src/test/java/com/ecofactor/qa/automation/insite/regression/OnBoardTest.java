/*
 * OnBoardTest.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.insite.regression;

import static com.ecofactor.qa.automation.util.PageUtil.*;

import java.lang.reflect.Method;

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
import com.ecofactor.qa.automation.insite.data.OnBoardDataProvider;

import com.ecofactor.qa.automation.insite.page.InstallationHardware;
import com.ecofactor.qa.automation.insite.page.OnBoard;
import com.ecofactor.qa.automation.insite.prepare.OnBoardPreparationService;
import com.ecofactor.qa.automation.util.DriverConfig;
import com.ecofactor.qa.automation.util.RerunFailTestAnalyzer;
import com.ecofactor.qa.automation.util.TestLogUtil;
import com.ecofactor.qa.automation.util.UtilModule;
import com.ecofactor.qa.automation.util.WaitUtil;
import com.ecofactor.qa.automation.util.test.JobValidator;
import com.google.inject.Inject;

/**
 * OnBoard test of insitePortal webSite, which contains valid and invalid login
 * check.</b>
 * 
 * @author Aximsoft
 */
@Guice(modules = { UtilModule.class, InsiteModule.class, DaoModule.class })
@Listeners(JobValidator.class)
public class OnBoardTest {

	/** The on board. */
	@Inject
	private OnBoard onBoard;

	/** The on board preparation service. */
	@Inject
	private OnBoardPreparationService onBoardPreparationService;

	/** The installation hardware. */
	@Inject
	private InstallationHardware installationHardware;

	/** The test log util. */
	@Inject
	private TestLogUtil testLogUtil;

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory
			.getLogger(OnBoardTest.class);

	/** The start. */
	private long start;

	/** The csv file path. */
	private String csvFilePath;

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

		try {
			csvFilePath = onBoardPreparationService.prepare(method.getName());
			onBoard.loadPage((String) param[0], (String) param[1]);
			WaitUtil.waitUntil(SHORT_TIMEOUT);
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
			onBoard.logout();
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
			onBoard.end();
		} catch (Throwable e) {
			logger.error("Error in after suite " + e.getMessage(), true);
		}
	}

	/**
	 * Test upload blank nonmandatory columns.
	 * 
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 */
	@Test(dataProvider = "validOnboardLogin", dataProviderClass = OnBoardDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "validation" })
	public void testUploadBlankNonmandatoryColumns(final String userName,
			final String password) {

		onBoard.uploadAndSubmitFile(csvFilePath);
		onBoard.verifyFileUpload(csvFilePath, "BLANK_NON_MANDATORY_COLUMN");

	}

	/**
	 * Test duplicate account.
	 * 
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 */
	@Test(dataProvider = "validOnboardLogin", dataProviderClass = OnBoardDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "validation" })
	public void testDuplicateAccount(final String userName,
			final String password) {

		onBoard.uploadAndSubmitFile(csvFilePath);
		onBoard.verifyFileUpload(csvFilePath, "DUPLICATE_ACCOUNT");

	}

	/**
	 * Test duplicate email.
	 * 
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 */
	@Test(dataProvider = "validOnboardLogin", dataProviderClass = OnBoardDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "validation" })
	public void testDuplicateEmail(final String userName, final String password) {

		onBoard.uploadAndSubmitFile(csvFilePath);
		onBoard.verifyFileUpload(csvFilePath, "DUPLICATE_EMAIL");

	}

	/**
	 * Test invalid ecp core id.
	 * 
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 */
	@Test(dataProvider = "validOnboardLogin", dataProviderClass = OnBoardDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "validation" })
	public void testInvalidEcpCoreId(final String userName,
			final String password) {

		onBoard.uploadAndSubmitFile(csvFilePath);
		onBoard.verifyFileUpload(csvFilePath, "INVALID_ECP_CORE_ID");

	}

	/**
	 * Test same email overwrites old account.
	 * 
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 */
	@Test(dataProvider = "validOnboardLogin", dataProviderClass = OnBoardDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "validation" })
	public void testSameEmailOverwritesOldAccount(final String userName,
			final String password) {

		logger.info("csvFilePath: " + csvFilePath);
		onBoard.verifyFileUpload(csvFilePath,
				"SAME_EMAIL_OVERWRITES_OLD_ACCOUNT");

	}

	/**
	 * testDifferentAddrDifferentEmail.
	 * 
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 */
	@Test(dataProvider = "validOnboardLogin", dataProviderClass = OnBoardDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "validation" })
	public void testDifferentAddrDifferentEmail(final String userName,
			final String password) {

		logger.info("DIFFERENT_EMAIL_DIFFERENT_ADDRESS: " + csvFilePath);
		onBoard.uploadAndSubmitFile(csvFilePath);
		onBoard.verifyFileUpload(csvFilePath,
				"DIFFERENT_EMAIL_DIFFERENT_ADDRESS");

	}

	/**
	 * Test different email different addr abrev.
	 * 
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 */
	@Test(dataProvider = "validOnboardLogin", dataProviderClass = OnBoardDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "validation" })
	public void testDifferentEmailDifferentAddrAbrev(final String userName,
			final String password) {

		logger.info("DIFFERENT_EMAIL_DIFFERENT_ADDRESS_ABREV: " + csvFilePath);
		onBoard.uploadAndSubmitFile(csvFilePath);
		onBoard.verifyFileUpload(csvFilePath,
				"DIFFERENT_EMAIL_DIFFERENT_ADDRESS_ABREV");

	}

	/**
	 * Test same name email different addr.
	 * 
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 */
	@Test(dataProvider = "validOnboardLogin", dataProviderClass = OnBoardDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "validation" })
	public void testSameNameEmailDifferentAddr(final String userName,
			final String password) {

		logger.info("SAME_NAME_EMAIL_DIFFERENT_ADDR: " + csvFilePath);
		onBoard.uploadAndSubmitFile(csvFilePath);
		onBoard.verifyFileUpload(csvFilePath, "SAME_NAME_EMAIL_DIFFERENT_ADDR");

	}

	/**
	 * Test different email same addr.
	 * 
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 */
	@Test(dataProvider = "validOnboardLogin", dataProviderClass = OnBoardDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "validation" })
	public void testDifferentEmailSameAddr(final String userName,
			final String password) {

		logger.info("DIFFERENT_EMAIL_SAME_ADDR: " + csvFilePath);
		onBoard.uploadAndSubmitFile(csvFilePath);
		onBoard.verifyFileUpload(csvFilePath, "DIFFERENT_EMAIL_SAME_ADDR");

	}

	/**
	 * Test same email same addr.
	 * 
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 */
	@Test(dataProvider = "validOnboardLogin", dataProviderClass = OnBoardDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "validation" })
	public void testSameEmailSameAddr(final String userName,
			final String password) {

		logger.info("SAME_EMAIL_SAME_ADDR: " + csvFilePath);
		onBoard.uploadAndSubmitFile(csvFilePath);
		onBoard.verifyFileUpload(csvFilePath, "SAME_EMAIL_SAME_ADDR");

	}

	/**
	 * Test same email same addr capitalized.
	 * 
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 */
	@Test(dataProvider = "validOnboardLogin", dataProviderClass = OnBoardDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "validation" })
	public void testSameEmailSameAddrCapitalized(final String userName,
			final String password) {

		logger.info("SAME_EMAIL_SAME_ADDR_CAPITALIZED: " + csvFilePath);
		onBoard.uploadAndSubmitFile(csvFilePath);
		onBoard.verifyFileUpload(csvFilePath,
				"SAME_EMAIL_SAME_ADDR_CAPITALIZED");

	}

	/**
	 * Test error reporting.
	 * 
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 */
	@Test(dataProvider = "validOnboardLogin", dataProviderClass = OnBoardDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "validation" })
	public void testErrorReporting(final String userName, final String password) {

		logger.info("ERROR_REPORTING: " + csvFilePath);
		onBoard.uploadAndSubmitFile(csvFilePath);
		onBoard.verifyFileUpload(csvFilePath, "ERROR_REPORTING");

	}

	/**
	 * Test bulk upload address lookup.
	 * 
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 */
	@Test(dataProvider = "validOnboardLogin", dataProviderClass = OnBoardDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "validation" })
	public void testBulkUploadAddressLookup(final String userName,
			final String password) {

		logger.info("BULK UPLOAD ADDRESS LOOKUP: " + csvFilePath);
		onBoard.uploadAndSubmitFile(csvFilePath);
		onBoard.verifyFileUpload(csvFilePath, "BULK_UPLOAD_ADDRESS_LOOKUP");
		onBoard.logout();
		onBoard.verifyAddressLookUp(csvFilePath);

	}

	/**
	 * Test thermostat name can be saved.
	 * 
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 */
	@Test(dataProvider = "validOnboardLogin", dataProviderClass = OnBoardDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "validation" })
	public void testThermostatNameCanBeSaved(final String userName,
			final String password) {

		logger.info("THERMOSTAT NAMES: " + csvFilePath);
		onBoard.uploadAndSubmitFile(csvFilePath);
		onBoard.verifyFileUpload(csvFilePath, "THERMOSTAT_NAMES_SAVED");
		onBoard.logout();
		onBoard.verifyAddressLookUp(csvFilePath);
		installationHardware.saveThermostatName("TestThermostat");
		installationHardware.logout();
		onBoard.verifyAddressLookUp(csvFilePath);
		installationHardware.verifyThermostatName("TestThermostat");
	}

	/**
	 * Test add thermostat.
	 * 
	 * @param onBoardUserName
	 *            the on board user name
	 * @param onBoardpassword
	 *            the on boardpassword
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 */
	@Test(dataProvider = "validOnboardLoginAndValidLogin", dataProviderClass = OnBoardDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "validation" })
	public void testAddThermostat(final String onBoardUserName,
			final String onBoardpassword, final String userName,
			final String password) {
		String thermostatName = "Thermostat1";
		addThermostatForUploadedLocation(userName, password, thermostatName);
		onBoard.logout();
		onBoard.verifyAddressLookUp(csvFilePath);
		installationHardware.verifyThermostatName(thermostatName);
	}

	/**
	 * Test remove thermostat.
	 * 
	 * @param onBoardUserName
	 *            the on board user name
	 * @param onBoardpassword
	 *            the on boardpassword
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 */
	@Test(dataProvider = "validOnboardLoginAndValidLogin", dataProviderClass = OnBoardDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "validation" })
	public void testRemoveThermostat(final String onBoardUserName,
			final String onBoardpassword, final String userName,
			final String password) {

		String thermostatName = "Thermostat1";
		addThermostatForUploadedLocation(userName, password, thermostatName);
		onBoard.logout();
		onBoard.verifyAddressLookUp(csvFilePath);
		installationHardware.verifyThermostatName(thermostatName);

		// Remove & verify added tstat.
		String tstatRemoved = installationHardware.removeThermostat();
		installationHardware.verifyNoThermostatWithName(tstatRemoved);
	}

	/**
	 * Adds the thermostat for uploaded location.
	 * 
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 * @param thermostatName
	 *            the thermostat name
	 */
	private void addThermostatForUploadedLocation(String userName,
			String password, String thermostatName) {

		// Uplaod generated file verify the location upload.
		logger.info("THERMOSTAT NAMES: " + csvFilePath);
		onBoard.uploadAndSubmitFile(csvFilePath);
		onBoard.verifyFileUpload(csvFilePath, "ADD_THERMOSTAT");
		onBoard.logout();
		onBoard.verifyAddressLookUp(csvFilePath);
		installationHardware.addThermostat(thermostatName);
	}

	/**
	 * Upload valid file.
	 * 
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 */
	@Test(dataProvider = "validOnboardLogin", dataProviderClass = OnBoardDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "validation" })
	public void uploadValidFile(String userName, String password) {

		try {

			String filePath = onBoard.generateFilepath("csv");

			String uploadUserName = onBoard
					.generateValidCSV(1, filePath, "205");

			onBoard.uploadAndSubmitFile(filePath);

			onBoard.verifyUser(uploadUserName);

		} catch (Exception e) {
			logger.error(e.getMessage());
		}

	}

	/**
	 * Upload duplicate file.
	 * 
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 */
	@Test(dataProvider = "validOnboardLogin", dataProviderClass = OnBoardDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "validation" })
	public void uploadDuplicateFile(String userName, String password) {

		try {

			String filePath = onBoard.generateFilepath("csv");

			String uploadUserName = onBoard
					.generateValidCSV(1, filePath, "205");

			onBoard.uploadAndSubmitFile(filePath);

			onBoard.verifyUser(uploadUserName);

			onBoard.clickBulkUpload();

			onBoard.uploadAndSubmitFile(filePath);

			onBoard.verifyDuplicateFile();

		} catch (Exception e) {
			logger.error(e.getMessage());
		}

	}

	/**
	 * Upload invalid file.
	 * 
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 */
	@Test(dataProvider = "validOnboardLogin", dataProviderClass = OnBoardDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "validation" })
	public void uploadInvalidFile(String userName, String password) {

		try {

			String filePath = onBoard.generateFilepath("txt");

			onBoard.generateValidCSV(1, filePath, "205");

			onBoard.uploadAndSubmitFile(filePath);

			onBoard.verifyInvalidCsv();

		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	/**
	 * Upload1000 records.
	 * 
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 */
	@Test(dataProvider = "validOnboardLogin", dataProviderClass = OnBoardDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "validation" })
	public void upload1000Records(String userName, String password) {

		try {

			String filePath = onBoard.generateFilepath("txt");

			onBoard.generateValidCSV(1000, filePath, "205");

			onBoard.uploadAndSubmitFile(filePath);

			// onBoard.verifyInvalidCsv();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

	}

	/**
	 * Test blank program id.
	 * 
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 */
	@Test(dataProvider = "validOnboardLogin", dataProviderClass = OnBoardDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "negativescenario" }, priority = 1)
	public void testBlankProgramId(final String userName, final String password) {

		DriverConfig.setLogString("BULK UPLOAD ADDRESS LOOKUP: " + csvFilePath,
				true);
		onBoard.uploadAndSubmitFile(csvFilePath);
		onBoard.verifyFileUpload(csvFilePath, "BLANK_PROGRAMID");
		onBoard.logout();

	}

	/*
	 * @Test(dataProvider = "validOnboardLogin", dataProviderClass =
	 * OnBoardDataProvider.class,retryAnalyzer = RerunFailTestAnalyzer.class,
	 * groups = {"negativescenario" }) public void testExistingUser(final String
	 * userName, final String password) {
	 * 
	 * logger.info("BULK UPLOAD ADDRESS LOOKUP: " + csvFilePath);
	 * onBoard.uploadAndSubmitFile(csvFilePath);
	 * onBoard.verifyFileUpload(csvFilePath, "EXISTING_USER"); onBoard.logout();
	 * 
	 * }
	 */

	/**
	 * Test future created date.
	 * 
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 */
	@Test(dataProvider = "validOnboardLogin", dataProviderClass = OnBoardDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "negativescenario" }, priority = 2)
	public void testFutureCreatedDate(final String userName,
			final String password) {

		logger.info("BULK UPLOAD ADDRESS LOOKUP: " + csvFilePath);
		onBoard.uploadAndSubmitFile(csvFilePath);
		onBoard.verifyFileUpload(csvFilePath, "FUTURE_CREATED_DATE");
		onBoard.logout();
	}

	/**
	 * Test past installation date.
	 * 
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 */
	@Test(dataProvider = "validOnboardLogin", dataProviderClass = OnBoardDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "negativescenario" }, priority = 3)
	public void testPastInstallationDate(final String userName,
			final String password) {

		logger.info("BULK UPLOAD ADDRESS LOOKUP: " + csvFilePath);
		onBoard.uploadAndSubmitFile(csvFilePath);
		onBoard.verifyFileUpload(csvFilePath, "PAST_INSTALLATION_DATE");
		onBoard.logout();
	}

	/**
	 * Test blank esid.
	 * 
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 */
	@Test(dataProvider = "validOnboardLogin", dataProviderClass = OnBoardDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "negativescenario" }, priority = 4)
	public void testBlankESID(final String userName, final String password) {

		logger.info("BULK UPLOAD ADDRESS LOOKUP: " + csvFilePath);
		onBoard.uploadAndSubmitFile(csvFilePath);
		onBoard.verifyFileUpload(csvFilePath, "BLANK_ESID");
		onBoard.logout();
	}

	/**
	 * Test charachters in esid.
	 * 
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 */
	@Test(dataProvider = "validOnboardLogin", dataProviderClass = OnBoardDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "negativescenario" }, priority = 5)
	public void testCharachtersInESID(final String userName,
			final String password) {

		logger.info("BULK UPLOAD ADDRESS LOOKUP: " + csvFilePath);
		onBoard.uploadAndSubmitFile(csvFilePath);
		onBoard.verifyFileUpload(csvFilePath, "CHARACHTERS_IN_ESID");
		onBoard.logout();
	}

	/**
	 * Test numbers in esid.
	 * 
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 */
	@Test(dataProvider = "validOnboardLogin", dataProviderClass = OnBoardDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "negativescenario" }, priority = 6)
	public void testNumbersInESID(final String userName, final String password) {

		logger.info("BULK UPLOAD ADDRESS LOOKUP: " + csvFilePath);
		onBoard.uploadAndSubmitFile(csvFilePath);
		onBoard.verifyFileUpload(csvFilePath, "NUMBERS_IN_ESID");
		onBoard.logout();
	}

	/**
	 * Test charachters in program id.
	 * 
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 */
	@Test(dataProvider = "validOnboardLogin", dataProviderClass = OnBoardDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "negativescenario" }, priority = 7)
	public void testCharachtersInProgramId(final String userName,
			final String password) {

		logger.info("BULK UPLOAD ADDRESS LOOKUP: " + csvFilePath);
		onBoard.uploadAndSubmitFile(csvFilePath);
		onBoard.verifyFileUpload(csvFilePath, "CHARACHTERS_IN_PGMID");
		onBoard.logout();
	}

	/*
	 * @Test(dataProvider = "validOnboardLogin", dataProviderClass =
	 * OnBoardDataProvider.class,retryAnalyzer = RerunFailTestAnalyzer.class,
	 * groups = {"negativescenario" }) public void
	 * testCharachtersInPhoneNo(final String userName, final String password) {
	 * 
	 * logger.info("BULK UPLOAD ADDRESS LOOKUP: " + csvFilePath);
	 * onBoard.uploadAndSubmitFile(csvFilePath);
	 * onBoard.verifyFileUpload(csvFilePath, "CHARACHTERS_IN_PHONENO");
	 * onBoard.logout(); }
	 */

	/**
	 * Test charachters in no tstats.
	 * 
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 */
	@Test(dataProvider = "validOnboardLogin", dataProviderClass = OnBoardDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "negativescenario" }, priority = 8)
	public void testCharachtersInNoTSTATS(final String userName,
			final String password) {

		logger.info("BULK UPLOAD ADDRESS LOOKUP: " + csvFilePath);
		onBoard.uploadAndSubmitFile(csvFilePath);
		onBoard.verifyFileUpload(csvFilePath, "CHARACHTERS_IN_NOTSTATS");
		onBoard.logout();
	}

	/**
	 * Test charachters in avg price.
	 * 
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 */
	@Test(dataProvider = "validOnboardLogin", dataProviderClass = OnBoardDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "negativescenario" }, priority = 9)
	public void testCharachtersInAvgPrice(final String userName,
			final String password) {

		logger.info("BULK UPLOAD ADDRESS LOOKUP: " + csvFilePath);
		onBoard.uploadAndSubmitFile(csvFilePath);
		onBoard.verifyFileUpload(csvFilePath, "CHARACHTERS_IN_AVG_PRICE");
		onBoard.logout();
	}

	/*
	 * @Test(dataProvider = "validOnboardLogin", dataProviderClass =
	 * OnBoardDataProvider.class,retryAnalyzer = RerunFailTestAnalyzer.class,
	 * groups = {"negativescenario" }) public void
	 * testInvalidCountryCodeLength(final String userName, final String
	 * password) {
	 * 
	 * logger.info("BULK UPLOAD ADDRESS LOOKUP: " + csvFilePath);
	 * onBoard.uploadAndSubmitFile(csvFilePath);
	 * onBoard.verifyFileUpload(csvFilePath, "INVALID_COUNTRY_CODE_LENGTH");
	 * onBoard.logout(); }
	 */

	/**
	 * Test invalid email id.
	 * 
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 */
	@Test(dataProvider = "validOnboardLogin", dataProviderClass = OnBoardDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "negativescenario" }, priority = 10)
	public void testInvalidEmailID(final String userName, final String password) {

		logger.info("BULK UPLOAD ADDRESS LOOKUP: " + csvFilePath);
		onBoard.uploadAndSubmitFile(csvFilePath);
		onBoard.verifyFileUpload(csvFilePath, "INVALID_EMAIL_ID");
		onBoard.logout();
	}

	/*
	 * @Test(dataProvider = "validOnboardLogin", dataProviderClass =
	 * OnBoardDataProvider.class,retryAnalyzer = RerunFailTestAnalyzer.class,
	 * groups = {"negativescenario" }) public void testOtherLanguages(final
	 * String userName, final String password) {
	 * 
	 * logger.info("BULK UPLOAD ADDRESS LOOKUP: " + csvFilePath);
	 * onBoard.uploadAndSubmitFile(csvFilePath);
	 * onBoard.verifyFileUpload(csvFilePath, "OTHER_LANGUAGES");
	 * onBoard.logout(); }
	 */

	/**
	 * Test blank mandatory field.
	 * 
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 */
	@Test(dataProvider = "validOnboardLogin", dataProviderClass = OnBoardDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "negativescenario" }, priority = 11)
	public void testBlankMandatoryField(final String userName,
			final String password) {

		logger.info("BULK UPLOAD ADDRESS LOOKUP: " + csvFilePath);
		onBoard.uploadAndSubmitFile(csvFilePath);
		onBoard.verifyFileUpload(csvFilePath, "BLANK_MANDATORY_FIELD");
		onBoard.logout();
	}

	/**
	 * Test add additional column.
	 * 
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 */
	@Test(dataProvider = "validOnboardLogin", dataProviderClass = OnBoardDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "negativescenario" }, priority = 12)
	public void testAddAdditionalColumn(final String userName,
			final String password) {

		logger.info("BULK UPLOAD ADDRESS LOOKUP: " + csvFilePath);
		onBoard.uploadAndSubmitFile(csvFilePath);
		onBoard.verifyFileUpload(csvFilePath, "ADD_ADDITIONAL_COLUMN");
		onBoard.logout();
	}

	/**
	 * Test field position changed in csv.
	 * 
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 */
	@Test(dataProvider = "validOnboardLogin", dataProviderClass = OnBoardDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "negativescenario" }, priority = 13)
	public void testFieldPositionChangedInCSV(final String userName,
			final String password) {

		logger.info("BULK UPLOAD ADDRESS LOOKUP: " + csvFilePath);
		onBoard.uploadAndSubmitFile(csvFilePath);
		onBoard.verifyFileUpload(csvFilePath, "FIELD_POSITIONS_CHANGED");
		onBoard.logout();
	}

	/**
	 * Test invalid installation date.
	 * 
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 */
	@Test(dataProvider = "validOnboardLogin", dataProviderClass = OnBoardDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "negativescenario" }, priority = 14)
	public void testInvalidInstallationDate(final String userName,
			final String password) {

		logger.info("BULK UPLOAD ADDRESS LOOKUP: " + csvFilePath);
		onBoard.uploadAndSubmitFile(csvFilePath);
		onBoard.verifyFileUpload(csvFilePath, "INVALID_ISTALLATIONDATE");
		onBoard.logout();
	}

	/**
	 * Test blank installation time.
	 * 
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 */
	@Test(dataProvider = "validOnboardLogin", dataProviderClass = OnBoardDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "negativescenario" }, priority = 15)
	public void testBlankInstallationTime(final String userName,
			final String password) {

		logger.info("BULK UPLOAD ADDRESS LOOKUP: " + csvFilePath);
		onBoard.uploadAndSubmitFile(csvFilePath);
		onBoard.verifyFileUpload(csvFilePath, "BLANK_INSTALLATION_TIME");
		onBoard.logout();
	}

	/**
	 * Test blank pre config.
	 * 
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 */
	@Test(dataProvider = "validOnboardLogin", dataProviderClass = OnBoardDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "negativescenario" }, priority = 16)
	public void testBlankPreConfig(final String userName, final String password) {

		logger.info("BULK UPLOAD ADDRESS LOOKUP: " + csvFilePath);
		onBoard.uploadAndSubmitFile(csvFilePath);
		onBoard.verifyFileUpload(csvFilePath, "BLANK_PRE_CONFIG");
		onBoard.logout();
	}

	/**
	 * Test pre config wtith text.
	 * 
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 */
	@Test(dataProvider = "validOnboardLogin", dataProviderClass = OnBoardDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "negativescenario" }, priority = 17)
	public void testPreConfigWithText(final String userName,
			final String password) {

		logger.info("BULK UPLOAD ADDRESS LOOKUP: " + csvFilePath);
		onBoard.uploadAndSubmitFile(csvFilePath);
		onBoard.verifyFileUpload(csvFilePath, "PRE_CONFIG_WITHTEXT");
		onBoard.logout();
	}

	/**
	 * Test blank installion time without preconfig.
	 * 
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 */
	@Test(dataProvider = "validOnboardLogin", dataProviderClass = OnBoardDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "negativescenario" }, priority = 18)
	public void testBlankInstallionTimeWithoutPreconfig(final String userName,
			final String password) {

		logger.info("BULK UPLOAD ADDRESS LOOKUP: " + csvFilePath);
		onBoard.uploadAndSubmitFile(csvFilePath);
		onBoard.verifyFileUpload(csvFilePath,
				"INSTALLATION_TIME_WITHOUT_PRECONFIG");
		onBoard.logout();
	}

	/**
	 * Test blank installion time with preconfig text.
	 * 
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 */
	@Test(dataProvider = "validOnboardLogin", dataProviderClass = OnBoardDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "negativescenario" }, priority = 19)
	public void testBlankInstallionTimeWithPreconfigText(final String userName,
			final String password) {

		logger.info("BULK UPLOAD ADDRESS LOOKUP: " + csvFilePath);
		onBoard.uploadAndSubmitFile(csvFilePath);
		onBoard.verifyFileUpload(csvFilePath,
				"INSTALLATION_TIME_WITH_PRECONFIGTEXT");
		onBoard.logout();
	}

	/**
	 * Test valid preconfig with installation time.
	 * 
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 */
	@Test(dataProvider = "validOnboardLogin", dataProviderClass = OnBoardDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "negativescenario" }, priority = 20)
	public void testValidPreconfigWithInstallationTime(final String userName,
			final String password) {

		logger.info("BULK UPLOAD ADDRESS LOOKUP: " + csvFilePath);
		onBoard.uploadAndSubmitFile(csvFilePath);
		onBoard.verifyFileUpload(csvFilePath, "PRECONFIG_MANDATORY");
		onBoard.logout();
	}

	/**
	 * Test invalid installation time format.
	 * 
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 */
	@Test(dataProvider = "validOnboardLogin", dataProviderClass = OnBoardDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "negativescenario" }, priority = 21)
	public void testInvalidInstallationTimeFormat(final String userName,
			final String password) {

		logger.info("BULK UPLOAD ADDRESS LOOKUP: " + csvFilePath);
		onBoard.uploadAndSubmitFile(csvFilePath);
		onBoard.verifyFileUpload(csvFilePath, "INVALID_INSTALLATION_TIME");
		onBoard.logout();
	}

	/**
	 * Test charachters in zip code.
	 * 
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 */

	@Test(dataProvider = "validOnboardLogin", dataProviderClass = OnBoardDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "negativescenario" }, priority = 22)
	public void testCharachtersInZipCode(final String userName,
			final String password) {

		logger.info("BULK UPLOAD ADDRESS LOOKUP: " + csvFilePath);
		onBoard.uploadAndSubmitFile(csvFilePath);
		onBoard.verifyFileUpload(csvFilePath, "CHARACHTERS_IN_ZIPCODE");
		onBoard.logout();
	}

	/*
	 * Test numbers in state field.
	 * 
	 * @param userName the user name
	 * 
	 * @param password the password
	 */
	/**
	 * Test numbers in state field.
	 * 
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 */
	@Test(dataProvider = "validOnboardLogin", dataProviderClass = OnBoardDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "negativescenario" }, priority = 23)
	public void testNumbersInStateField(final String userName,
			final String password) {

		logger.info("BULK UPLOAD ADDRESS LOOKUP: " + csvFilePath);
		onBoard.uploadAndSubmitFile(csvFilePath);
		onBoard.verifyFileUpload(csvFilePath, "NUMBERS_IN_STATE");
		onBoard.logout();
	}
}