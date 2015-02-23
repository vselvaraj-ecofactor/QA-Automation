/*
 * OnBoardImpl.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.insite.page;

import static com.ecofactor.qa.automation.insite.config.InsiteConfig.*;
import static com.ecofactor.qa.automation.insite.config.OnBoardConfig.*;
import static com.ecofactor.qa.automation.util.PageUtil.MEDIUM_TIMEOUT;
import static com.ecofactor.qa.automation.util.PageUtil.isDisplayedById;
import static com.ecofactor.qa.automation.util.WaitUtil.*;
import static org.testng.Reporter.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import com.ecofactor.common.pojo.UploadSession;
import com.ecofactor.common.pojo.User;
import com.ecofactor.common.pojo.UserLocationStaging;
import com.ecofactor.qa.automation.dao.EFUserDao;
import com.ecofactor.qa.automation.dao.UploadSessionDao;
import com.ecofactor.qa.automation.dao.UserLocationStagingDao;
import com.ecofactor.qa.automation.insite.config.InsiteConfig;
import com.ecofactor.qa.automation.insite.config.InsiteLoginConfig;
import com.ecofactor.qa.automation.insite.config.InsiteLoginTestConfig;
import static com.ecofactor.qa.automation.insite.config.InsiteLoginTestConfig.*;
import com.ecofactor.qa.automation.insite.config.OnBoardConfig;
import com.ecofactor.qa.automation.util.DateUtil;
import com.ecofactor.qa.automation.util.DriverConfig;
import com.ecofactor.qa.automation.util.WaitUtil;
import com.google.inject.Inject;

/**
 * <b>OnBoard<b>
 * <p>
 * Contains basic features of role management tab such as create a new role,
 * search, edit and update.
 * </p>
 * @author Aximsoft
 */
public class OnBoardImpl extends InsiteAuthenticatedPageImpl implements OnBoard {

	@Inject
	private InsiteLoginConfig loginConfig;
	@Inject
	private InsiteConfig appConfig;
	@Inject
	private EFUserDao efUserDao;
	@Inject
	private OnBoardConfig onBoardConfig;
	private WebElement errorMessage;
	private List<String> messageDivs;
	@Inject
	private UploadSessionDao uploadSessionDao;
	@Inject
	private UploadSession uploadSession;
	@Inject
	private UserLocationStaging UserLocationStaging;
	@Inject
	private UserLocationStagingDao userLocationStagingDao;
	@Inject
	private InstallationHardware installationHardware;
	@Inject
	private InsiteLoginTestConfig insiteLoginTestConfig;
	@Inject
	private InsiteLogin insiteLogin;
	private boolean uploadMessageFound = false;

	private File csvFile;
	private String strCSVFile = null;

	Date currentDate = new Date();
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
	String dateTimeStamp = formatter.format(currentDate);

	private static Logger logger = LoggerFactory.getLogger(OnBoardImpl.class);

	/**
	 * Load page.
	 * @see com.ecofactor.qa.automation.insite.page.InsitePage#loadPage()
	 */
	@SuppressWarnings("static-access")
	public void loadPage() {

		String url = appConfig.get(INSITE_URL) + appConfig.get(appConfig.BULK_UPLOAD);
		if (!DriverConfig.getDriver().getCurrentUrl().equalsIgnoreCase(url)) {
			clickBulkUpload();
		}

	}

	/**
	 * Upload csv.
	 * @param csvPath the csv path
	 * @param testScenario the test scenario
	 * @see com.ecofactor.qa.automation.insite.page.OnBoard#uploadCSV(java.lang.String, java.lang.String)
	 */
	@Override
	public void verifyFileUpload(String csvPath, String testScenario) {
		DriverConfig.setLogString("Verify if the file upload is successfull."+csvPath, true);
		csvFile = new File(csvPath);
		strCSVFile = csvPath;
		largeWait();

		switch (testScenario) {
		case "SAME_EMAIL_OVERWRITES_OLD_ACCOUNT":
			verifySameEmailOverridesOldAccount();
			break;
		case "ADD_THERMOSTAT":
		case "ACTIVATE_NEW_USER":

		case "BULK_UPLOAD_ADDRESS_LOOKUP":

		case "BLANK_NON_MANDATORY_COLUMN":
			verifyBlankFields();
			break;
		case "DUPLICATE_EMAIL":
			verifyDuplicateEmail();
			break;
		case "DUPLICATE_ACCOUNT":
			verifyDuplicateAccount();
			break;
		case "INVALID_ECP_CORE_ID":
			verifyInvalidECPCoreId();
			break;
		case "DIFFERENT_EMAIL_DIFFERENT_ADDRESS":
		case "DIFFERENT_EMAIL_DIFFERENT_ADDRESS_ABREV":
			verifyDiffEmailDiffAddr();
			break;

		case "SAME_NAME_EMAIL_DIFFERENT_ADDR":
			verifySameNameEmailDiffAddr();
			break;
		case "DIFFERENT_EMAIL_SAME_ADDR":
			verifyDiffEmailSameAddr();
			break;
		case "SAME_EMAIL_SAME_ADDR":
			verifySameEmailSameAddr();
			break;
		case "SAME_EMAIL_SAME_ADDR_CAPITALIZED":
			verifySameEmailSameAddrCapitalized();
			break;
		case "ERROR_REPORTING":
			verifyErrorReporting();
			break;
		// negative scenarios
		case "BLANK_PROGRAMID":
			verifyblankProgramId();
			break;		/*
		 * case "EXISTING_USER": verifyExistingUser(); break;
		 */
		case "FUTURE_CREATED_DATE":
			verifyFutureCreatedDate();
			break;
		case "PAST_INSTALLATION_DATE":
			verifyPastInstallationDate();
			break;
		case "BLANK_ESID":
			verifyBlankESID();
			break;
		case "CHARACHTERS_IN_ESID":
			verifyCharachtersInESID();
			break;
		case "NUMBERS_IN_ESID":
			verifyNumbersInESID();
			break;
		case "CHARACHTERS_IN_PGMID":
			verifyCharachtersInPgmId();
			break;		/*
		 * case "CHARACHTERS_IN_PHONENO": verifyCharachtersInPhoneNo(); break;
		 */
		case "CHARACHTERS_IN_ZIPCODE":
			verifyCharachtersInZipCode();
			break;
		case "NUMBERS_IN_STATE":
			verifyNumbersInState();
			break;
		case "CHARACHTERS_IN_NOTSTATS":
			verifyCharachtersInNoTstats();
			break;
		case "CHARACHTERS_IN_AVG_PRICE":
			verifyCharachtersInAvgPrice();
			break;
			/*
		 * case "INVALID_COUNTRY_CODE_LENGTH": verifyInvalidCountryCodeLength();
		 * break;
		 */
		case "INVALID_EMAIL_ID":
			verifyInvalidEmailId();
			break;
		/*
		 * case "OTHER_LANGUAGES": verifyOtherLanguages(); break;
		 */
		case "BLANK_MANDATORY_FIELD":
			verifyBlankMandatoryField();
			break;

		case "ADD_ADDITIONAL_COLUMN":
			verifyAddAdditionalColumn();
			break;
		case "FIELD_POSITIONS_CHANGED":
			verifyFieldPositionChangedInCSV();
			break;
			//PRE CONFIG
		case "INVALID_ISTALLATIONDATE":
			verifyInvalidInstallationDate();
			break;
		case "BLANK_INSTALLATION_TIME":
			verifyBlankInstallationTime();
			break;
		case "BLANK_PRE_CONFIG":
			verifyBlankPreconfig();
			break;
		case "PRE_CONFIG_WITHTEXT":
			verifyPreconfigWithText();
			break;
		case "INSTALLATION_TIME_WITHOUT_PRECONFIG":
			verifyBlankInstallationTimeWithoutPreConfig();
			break;
		case "INSTALLATION_TIME_WITH_PRECONFIGTEXT":
			verifyBlankInstallationTimeWithPreConfigText();
			break;
		case "PRECONFIG_MANDATORY":
			verifyFieldInstallationTime();
			break;
		case "INVALID_INSTALLATION_TIME":
			verifyinvalidInstallationTimeFormat();
			break;
		}
	}

	/**
	 * Verify field position changed in csv.
	 */
	private void verifyFieldPositionChangedInCSV() {
		UploadSession uploadSession1 = new UploadSession();
		uploadSession1 = uploadSessionDao.findByFileName(csvFile.getName());

		if (uploadSession1.getStatus().toString().equalsIgnoreCase("FAILED")) {
			Assert.assertTrue(true);
		} else {
			DriverConfig.setLogString("Failed to verify file upload with fields mis placed.", true);
			Assert.assertTrue(false);
		}

	}

	/**
	 * Verify add additional column.
	 */
	private void verifyAddAdditionalColumn() {
		UploadSession uploadSession1 = new UploadSession();
		uploadSession1 = uploadSessionDao.findByFileName(csvFile.getName());
		UserLocationStaging userLocationStaging = userLocationStagingDao.findByUploadSessionId(uploadSession1.getId()).get(0);

		if (userLocationStaging.getStatusMessage().contains("User imported Successfully.")) {
			Assert.assertTrue(true);
		} else {
			DriverConfig.setLogString("Failed to verify file upload with blank email", true);
			Assert.assertTrue(false);
		}
	}

	/**
	 * Verify blank mandatory field.
	 */
	private void verifyBlankMandatoryField() {
		UploadSession uploadSession1 = new UploadSession();
		uploadSession1 = uploadSessionDao.findByFileName(csvFile.getName());
		UserLocationStaging userLocationStaging = userLocationStagingDao.findByUploadSessionId(uploadSession1.getId()).get(0);

		if (userLocationStaging.getStatusMessage().contains("Email address cannot be null")) {
			Assert.assertTrue(true);
		} else {
			DriverConfig.setLogString("Failed to verify file upload with blank email", true);
			Assert.assertTrue(false);
		}
	}

	/**
	 * Verify invalid email id.
	 */
	private void verifyInvalidEmailId() {

		UploadSession uploadSession1 = new UploadSession();
		uploadSession1 = uploadSessionDao.findByFileName(csvFile.getName());
		UserLocationStaging userLocationStaging = userLocationStagingDao.findByUploadSessionId(uploadSession1.getId()).get(0);

		if (userLocationStaging.getStatusMessage().contains("Invalid Email Address")) {
			Assert.assertTrue(true);
		} else {
			DriverConfig.setLogString("Failed to verify file upload with invalid email id", true);
			Assert.assertTrue(false);
		}

	}

	/**
	 * Verify charachters in avg price.
	 */
	private void verifyCharachtersInAvgPrice() {

		UploadSession uploadSession1 = new UploadSession();
		uploadSession1 = uploadSessionDao.findByFileName(csvFile.getName());
		UserLocationStaging userLocationStaging = userLocationStagingDao.findByUploadSessionId(uploadSession1.getId()).get(0);

		if (userLocationStaging.getStatusMessage().contains("Average Price is non-numeric.")) {
			Assert.assertTrue(true);
		} else {
			DriverConfig.setLogString("Failed to verify file upload with characters in average price", true);
			Assert.assertTrue(false);
		}

	}

	/**
	 * Verify charachters in no tstats.
	 */
	private void verifyCharachtersInNoTstats() {
		UploadSession uploadSession = new UploadSession();
		uploadSession = uploadSessionDao.findByFileName(csvFile.getName());

		if (uploadSession.getStatus().toString().equalsIgnoreCase("FAILED")) {
			Assert.assertTrue(true);
		} else {
			DriverConfig.setLogString("Failed to verify file upload with characters in number of thermostats fiels.", true);
			Assert.assertTrue(false);
		}
	}

	/**
	 * Verify numbers in state.
	 */
	private void verifyNumbersInState() {
		UploadSession uploadSession1 = new UploadSession();
		uploadSession1 = uploadSessionDao.findByFileName(csvFile.getName());
		UserLocationStaging userLocationStaging = userLocationStagingDao.findByUploadSessionId(uploadSession1.getId()).get(0);

		if (userLocationStaging.getStatusMessage().contains("Address Validation Failed")) {
			Assert.assertTrue(true);
		} else {
			DriverConfig.setLogString("Failed to verify file upload with numbers in state", true);
			Assert.assertTrue(false);
		}

	}

	/**
	 * Verify charachters in zip code.
	 */
	private void verifyCharachtersInZipCode() {
		UploadSession uploadSession1 = new UploadSession();
		uploadSession1 = uploadSessionDao.findByFileName(csvFile.getName());
		UserLocationStaging userLocationStaging = userLocationStagingDao.findByUploadSessionId(uploadSession1.getId()).get(0);

		if (userLocationStaging.getStatusMessage().contains("Address Validation Failed")) {
			Assert.assertTrue(true);
		} else {
			DriverConfig.setLogString("Failed to verify file upload with characters in zipcode.", true);
			Assert.assertTrue(false);
		}
	}

	/**
	 * Verify charachters in pgm id.
	 */
	private void verifyCharachtersInPgmId() {
		UploadSession uploadSession = new UploadSession();
		uploadSession = uploadSessionDao.findByFileName(csvFile.getName());

		if (uploadSession.getStatus().toString().equalsIgnoreCase("FAILED")) {
			Assert.assertTrue(true);
		} else {
			DriverConfig.setLogString("Failed to verify file upload with characterd in program Id.", true);
			Assert.assertTrue(false);
		}

	}

	/**
	 * Verify numbers in esid.
	 */
	private void verifyNumbersInESID() {
		UploadSession uploadSession1 = new UploadSession();
		uploadSession1 = uploadSessionDao.findByFileName(csvFile.getName());
		UserLocationStaging userLocationStaging = userLocationStagingDao.findByUploadSessionId(uploadSession1.getId()).get(0);

		if (userLocationStaging.getStatusMessage().contains("Error Code: 1001")) {
			Assert.assertTrue(true);
		} else {
			DriverConfig.setLogString("Failed to verify file upload without installation time.", true);
			Assert.assertTrue(false);
		}

	}

	/**
	 * Verify charachters in esid.
	 */
	private void verifyCharachtersInESID() {
		UploadSession uploadSession1 = new UploadSession();
		uploadSession1 = uploadSessionDao.findByFileName(csvFile.getName());
		UserLocationStaging userLocationStaging = userLocationStagingDao.findByUploadSessionId(uploadSession1.getId()).get(0);

		if (userLocationStaging.getStatusMessage().contains("Error Code: 1001")) {
			Assert.assertTrue(true);
		} else {
			DriverConfig.setLogString("Failed to verify file upload without installation time.", true);
			Assert.assertTrue(false);
		}

	}

	/**
	 * Verify blank esid.
	 */
	private void verifyBlankESID() {
		UploadSession uploadSession1 = new UploadSession();
		uploadSession1 = uploadSessionDao.findByFileName(csvFile.getName());
		UserLocationStaging userLocationStaging = userLocationStagingDao.findByUploadSessionId(uploadSession1.getId()).get(0);

		if (userLocationStaging.getStatusMessage().contains("Warning: Installation time was not found for following users")) {
			Assert.assertTrue(true);
		} else {
			DriverConfig.setLogString("Failed to verify file upload without installation time.", true);
			Assert.assertTrue(false);
		}

	}

	/**
	 * Verify past installation date.
	 */
	private void verifyPastInstallationDate() {

		UploadSession uploadSession1 = new UploadSession();
		uploadSession1 = uploadSessionDao.findByFileName(csvFile.getName());
		UserLocationStaging userLocationStaging = userLocationStagingDao.findByUploadSessionId(uploadSession1.getId()).get(0);

		if (userLocationStaging.getStatusMessage().contains("Installation date is in the past.")) {
			Assert.assertTrue(true);
		} else {
			DriverConfig.setLogString("Failed to verify file upload with blank email id.", true);
			Assert.assertTrue(false);
		}
	}

	/**
	 * Verify future created date.
	 */
	private void verifyFutureCreatedDate() {
		UploadSession uploadSession1 = new UploadSession();
		uploadSession1 = uploadSessionDao.findByFileName(csvFile.getName());
		UserLocationStaging userLocationStaging = userLocationStagingDao.findByUploadSessionId(uploadSession1.getId()).get(0);

		if (userLocationStaging.getStatusMessage().contains("Created Date is in Future")) {
			Assert.assertTrue(true);
		} else {
			DriverConfig.setLogString("Failed to verify file upload with current date", true);
			Assert.assertTrue(false);
		}
	}

	/**
	 * Verifyblank program id.
	 */
	private void verifyblankProgramId() {

		UploadSession uploadSession = new UploadSession();
		uploadSession = uploadSessionDao.findByFileName(csvFile.getName());

		if (uploadSession.getStatus().toString().equalsIgnoreCase("FAILED")) {
			Assert.assertTrue(true);
		} else {
			DriverConfig.setLogString("Failed to verify file upload with blank email id.", true);
			Assert.assertTrue(false);
		}
	}

	/**
	 * Verify field installation time.
	 */
	private void verifyFieldInstallationTime() {

		UploadSession uploadSession1 = new UploadSession();
		uploadSession1 = uploadSessionDao.findByFileName(csvFile.getName());
		UserLocationStaging userLocationStaging = userLocationStagingDao.findByUploadSessionId(uploadSession1.getId()).get(0);

		DriverConfig.setLogString("Status message: " + userLocationStaging.getStatusMessage(), true);
		if (userLocationStaging.getStatusMessage().toString().contains("User imported Successfully.")) {
			Assert.assertTrue(true);
		} else {
			DriverConfig.setLogString("Failed to upload ", true);
			Assert.assertTrue(false);
		}
	}

	/**
	 * Verify invalid installation date.
	 */
	private void verifyInvalidInstallationDate() {

		UploadSession uploadSession1 = new UploadSession();
		uploadSession1 = uploadSessionDao.findByFileName(csvFile.getName());
		UserLocationStaging userLocationStaging = userLocationStagingDao.findByUploadSessionId(uploadSession1.getId()).get(0);

		DriverConfig.setLogString("Status message: " + userLocationStaging.getStatusMessage(), true);
		if (userLocationStaging.getStatusMessage().toString().contains("Installation date/time was in invalid format.")) {
			Assert.assertTrue(true);
		} else {
			DriverConfig.setLogString("Failed to upload with invalid date format.", true);
			Assert.assertTrue(false);
		}
	}

	/**
	 * Verify blank installation time.
	 */
	private void verifyBlankInstallationTime() {

		UploadSession uploadSession = new UploadSession();
		uploadSession = uploadSessionDao.findByFileName(csvFile.getName());

		DriverConfig.setLogString("Status: " + uploadSession.getStatus(), true);
		if (uploadSession.getStatus().toString().equalsIgnoreCase("SUCCESS")) {
			Assert.assertTrue(true);
		} else {
			DriverConfig.setLogString(
					"Failed to upload with blank installation time.", true);
			Assert.assertTrue(false);
		}
	}

	/**
	 * Verify blank preconfig.
	 */
	private void verifyBlankPreconfig() {

		UploadSession uploadSession1 = new UploadSession();
		uploadSession1 = uploadSessionDao.findByFileName(csvFile.getName());
		UserLocationStaging userLocationStaging = userLocationStagingDao.findByUploadSessionId(uploadSession1.getId()).get(0);

		DriverConfig.setLogString("Status message: " + userLocationStaging.getStatusMessage(), true);
		if (userLocationStaging.getStatusMessage().toString().contains("Preconfig value is empty[Value must be Yes/No}")) {
			Assert.assertTrue(true);
		} else {
			DriverConfig.setLogString("Failed to upload with blank preconfig.", true);
			Assert.assertTrue(false);
		}
	}

	/**
	 * Verify preconfig with text.
	 */
	private void verifyPreconfigWithText() {

		UploadSession uploadSession1 = new UploadSession();
		uploadSession1 = uploadSessionDao.findByFileName(csvFile.getName());
		UserLocationStaging userLocationStaging = userLocationStagingDao.findByUploadSessionId(uploadSession1.getId()).get(0);

		DriverConfig.setLogString("Status message: " + userLocationStaging.getStatusMessage(), true);
		if (userLocationStaging.getStatusMessage().toString().contains("Preconfig value is invalid[Value must be Yes/No}.")) {
			Assert.assertTrue(true);
		} else {
			DriverConfig.setLogString("Failed to upload with text in preconfig.", true);
			Assert.assertTrue(false);
		}
	}

	/**
	 * Verify blank installation time without pre config.
	 */
	private void verifyBlankInstallationTimeWithoutPreConfig() {

		UploadSession uploadSession = new UploadSession();
		uploadSession = uploadSessionDao.findByFileName(csvFile.getName());

		DriverConfig.setLogString("Status: " + uploadSession.getStatus(), true);
		if (uploadSession.getStatus().toString().equalsIgnoreCase("SUCCESS")) {
			Assert.assertTrue(true);
		} else {
			DriverConfig.setLogString("Failed to upload with blank installation time.", true);
			Assert.assertTrue(false);
		}
	}

	/**
	 * Verify blank installation time with pre config text.
	 */
	private void verifyBlankInstallationTimeWithPreConfigText() {

		UploadSession uploadSession = new UploadSession();
		uploadSession = uploadSessionDao.findByFileName(csvFile.getName());

		DriverConfig.setLogString("Status: " + uploadSession.getStatus(), true);
		if (uploadSession.getStatus().toString().equalsIgnoreCase("SUCCESS")) {
			Assert.assertTrue(true);
		} else {
			DriverConfig.setLogString("Failed to upload with blank installation time.", true);
			Assert.assertTrue(false);
		}
	}

	/**
	 * Verifyinvalid installation time format.
	 */
	private void verifyinvalidInstallationTimeFormat() {

		UploadSession uploadSession1 = new UploadSession();
		uploadSession1 = uploadSessionDao.findByFileName(csvFile.getName());
		List<com.ecofactor.common.pojo.UserLocationStaging> userLocationStaging = userLocationStagingDao.findByUploadSessionId(uploadSession1.getId());

		DriverConfig.setLogString("Status message: " + userLocationStaging.get(0).getStatusMessage(), true);
		if (userLocationStaging.get(0).getStatusMessage().toString().contains("User imported Successfully.")) {
			Assert.assertTrue(true);
		}
		DriverConfig.setLogString("Status message: " + userLocationStaging.get(1).getStatusMessage(), true);
		if (userLocationStaging.get(1).getStatusMessage().toString().contains("Installation date/time was in invalid format.")) {
			Assert.assertTrue(true);
		}
		else {
			DriverConfig.setLogString("Failed to upload with invalid installation time format.", true);
			Assert.assertTrue(false);
		}
	}

	/**
	 * Check file errors.
	 */
	public void checkFileErrors() {

		largeWait();
		errorMessage = DriverConfig.getDriver().findElement(By.id("filenameError"));

		if (errorMessage.getText() != null)
			logger.info("found error!" + errorMessage.getText());

	}

	/**
	 * verification for Same Email Overwrites Old Account.
	 * @see com.ecofactor.qa.automation.insite.page.OnBoard#uploadCSVAndGetFields(java.lang.String,
	 *      java.lang.String)
	 */

	@Override
	public void verifySameEmailOverridesOldAccount() {

		String[] filePaths = strCSVFile.split(",");

		uploadAndFetchData(filePaths[0]);

		UploadSession uploadSession1 = new UploadSession();
		uploadSession1 = uploadSessionDao.findByFileName(csvFile.getName());
		UserLocationStaging userLocationStaging1 = userLocationStagingDao.findByUploadSessionId(uploadSession1.getId()).get(0);

		if (userLocationStaging1.getStatusMessage().toString().contains("User imported Successfully.")) {
			Assert.assertTrue(true);
		} else {
			DriverConfig.setLogString("Failed to verify Different Email and Different address upload.", true);
			Assert.assertTrue(false);
		}

		loadPage();

		uploadAndFetchData(filePaths[1]);

		UploadSession uploadSession2 = new UploadSession();
		uploadSession2 = uploadSessionDao.findByFileName(csvFile.getName());
		UserLocationStaging userLocationStaging2 = userLocationStagingDao.findByUploadSessionId(uploadSession2.getId()).get(0);

		logger.info("userLocationStaging2.getStatusMessage():" + userLocationStaging2.getStatusMessage());
		logger.info("userLocationStaging1.getId(): " + userLocationStaging1.getId() + "; userLocationStaging2.getId(): " + userLocationStaging2.getId());

		if (userLocationStaging2.getStatusMessage().toString().contains("User information cannot be uploaded. User already exists in the system.")) {
			DriverConfig.setLogString("verified Same Email Overrides Old Account.", true);
			Assert.assertTrue(userLocationStaging2.getStatusMessage().toString().contains("User information cannot be uploaded. User already exists in the system."));
		} else {
			DriverConfig.setLogString("unable to verify Same Email Overrides Old Account.", true);
			Assert.assertTrue(userLocationStaging2.getStatusMessage().toString().contains("User information cannot be uploaded. User already exists in the system."));
		}

		Assert.assertTrue(userLocationStaging1.getId() == userLocationStaging2.getId(), "Id is different");

	}

	/**
	 * Verify diff email diff addr.
	 */
	public void verifyDiffEmailDiffAddr() {
		DriverConfig.setLogString("Different email, different addresses are acceptable.", true);
		checkFileErrors();
		if (isDisplayedById(DriverConfig.getDriver(), "success", MEDIUM_TIMEOUT)) {
			DriverConfig.setLogString("Uploaded successfully.", true);
			Assert.assertTrue(true);
		} else {
			DriverConfig.setLogString("Upload failed.", true);
			logger.info(getErrorDivMessage());
			Assert.assertTrue(false);
		}
		smallWait();
		List<UserLocationStaging> userLocationStaging = getUserLocationStaging();

		for (UserLocationStaging userLoc : userLocationStaging) {
			if (userLoc.getStatusMessage().toString().contains("User imported Successfully.")) {

				Assert.assertTrue(true);
			} else {
				DriverConfig.setLogString("Failed to verify Different Email and Different address upload.", true);
				Assert.assertTrue(false);
			}
		}
		DriverConfig.setLogString("Different Email and Different address upload verified successfully.", true);
	}

	/**
	 * Verify same name email diff addr.
	 */
	public void verifySameNameEmailDiffAddr() {
		DriverConfig.setLogString("Same name, email with different addresses are acceptable.", true);
		checkFileErrors();
		if (isDisplayedById(DriverConfig.getDriver(), "success", MEDIUM_TIMEOUT)) {
			DriverConfig.setLogString("Uploaded successfully.", true);
			Assert.assertTrue(true);
		} else {
			DriverConfig.setLogString("Upload failed.", true);
			logger.info(getErrorDivMessage());
			Assert.assertTrue(false);
		}
		smallWait();
		List<UserLocationStaging> userLocationStaging = getUserLocationStaging();
		for (UserLocationStaging ULS : userLocationStaging) {
			if (ULS.getStatusMessage().toString().contains("User updated Successfully.")) {
				DriverConfig.setLogString("Same name, same Email and Different address upload verified successfully.", true);
				Assert.assertTrue(true);
			} else {
				DriverConfig.setLogString("Failed to verify Same name, same Email and Different address upload.", true);
				Assert.assertTrue(false);
			}
		}
	}

	/**
	 * Verify diff email same addr.
	 */
	public void verifyDiffEmailSameAddr() {
		DriverConfig.setLogString("Different  email same addresses are rejected.", true);
		checkFileErrors();
		if (DriverConfig.getDriver().findElement(By.id("success")).isDisplayed()) {
			DriverConfig.setLogString("Uploaded successfully. Testcase failed.", true);
			Assert.assertTrue(false);
		} else {
			DriverConfig.setLogString("Upload failed as expected.", true);
			logger.info(getErrorDivMessage());
			Assert.assertFalse(false);
		}
		smallWait();
		List<UserLocationStaging> userLocationStaging = getUserLocationStaging();

		if (userLocationStaging.size() > 1) {
			if (userLocationStaging.get(1).getStatusMessage().equalsIgnoreCase("User information cannot be uploaded. User already exists in the system.")) {
				DriverConfig.setLogString("Upload status:" + userLocationStaging.get(1).getStatusMessage(), true);
				Assert.assertTrue(true);
			} else if ("User imported Successfully.".equalsIgnoreCase(userLocationStaging.get(1).getStatusMessage())) {
				DriverConfig.setLogString("Upload status:" + userLocationStaging.get(1).getStatusMessage(), true);
				Assert.assertTrue(false);
			}
		}
	}

	/**
	 * Verify same email same addr.
	 */
	public void verifySameEmailSameAddr() {
		DriverConfig.setLogString("Same email, same addresses are rejected.", true);
		checkFileErrors();
		if (DriverConfig.getDriver().findElement(By.id("success")).isDisplayed()) {
			DriverConfig.setLogString("Uploaded successfully. Testcase failed.", true);
			Assert.assertTrue(false);
		} else {
			DriverConfig.setLogString("Upload failed as expected.", true);
			logger.info(getErrorDivMessage());
			Assert.assertTrue(true);
		}
		smallWait();
		List<UserLocationStaging> userLocationStaging = getUserLocationStaging();
		UserLocationStaging userLocStaging = userLocationStaging.get(0);

		// get status message
		if (userLocStaging.getStatusMessage().toString().contains("User imported Successfully.")) {
			DriverConfig.setLogString("Failed to verify same email same address upload.", true);
			Assert.assertTrue(false);
		} else {
			DriverConfig.setLogString("Same email same address upload verified successfully.", true);
			Assert.assertTrue(true);
		}

	}

	/**
	 * Verify same email same addr capitalized.
	 */
	public void verifySameEmailSameAddrCapitalized() {
		DriverConfig.setLogString("Same email, same addresses capitalized are acceptable.", true);
		checkFileErrors();
		if (DriverConfig.getDriver().findElement(By.id("success")).isDisplayed()) {
			DriverConfig.setLogString("Uploaded successfully. Testcase failed.", true);
			Assert.assertTrue(false);
		} else {
			DriverConfig.setLogString("Upload failed as expected.", true);
			logger.info(getErrorDivMessage());
			Assert.assertTrue(true);
		}
		smallWait();
		List<UserLocationStaging> userLocationStaging = getUserLocationStaging();

		for (UserLocationStaging ULS : userLocationStaging) {
			DriverConfig.setLogString("" + ULS.getUploadSession().getId(), true);
			if (ULS.getStatusMessage().toString().contains("User imported Successfully.")) {
				DriverConfig.setLogString("Failed to verify same email same address capitalized upload.", true);
				Assert.assertTrue(false);
			} else {
				DriverConfig.setLogString("Same email same address capitalized upload verified successfully.", true);
				Assert.assertTrue(true);
			}

		}
		// get status message
	}

	/**
	 * Verify error reporting.
	 */
	public void verifyErrorReporting() {
		DriverConfig.setLogString("Invalid data for location uploads should report errors.", true);
		checkFileErrors();
		if (DriverConfig.getDriver().findElement(By.id("success")).isDisplayed()) {
			DriverConfig.setLogString("Uploaded successfully. Testcase failed.", true);
			Assert.assertTrue(false);
		} else {
			DriverConfig.setLogString("Upload failed as expected.", true);
			logger.info(getErrorDivMessage());
			Assert.assertTrue(true);
		}
		smallWait();
		List<UserLocationStaging> userLocationStaging = getUserLocationStaging();

		for (UserLocationStaging userLoc : userLocationStaging) {
			// get status message
			if (userLoc.getStatusMessage().toString().contains("User imported Successfully.")) {
				DriverConfig.setLogString("Error reporting failed to verify successfully.", true);
				Assert.assertTrue(false);
			} else if (userLoc.getStatusMessage().toString().contains("Validator Errors found: Invalid Email Address")) {
				DriverConfig.setLogString("Error reporting verified successfully.", true);
				Assert.assertTrue(true);
			}
		}
	}

	/**
	 * Verify blank fields.
	 */
	public void verifyBlankFields() {
		smallWait();
		DriverConfig.setLogString("Non mandatory blank fields are acceptable.", true);
		checkFileErrors();
		if (DriverConfig.getDriver().findElement(By.id("success")).isDisplayed()) {
			DriverConfig.setLogString("Uploaded successfully.", true);
			Assert.assertTrue(true);
		} else {
			DriverConfig.setLogString("Upload failed.", true);
			logger.info(getErrorDivMessage());
			Assert.assertTrue(false);
		}
		smallWait();
		List<UserLocationStaging> userLocationStaging = getUserLocationStaging();
		for (UserLocationStaging userLoc : userLocationStaging) {

			if (userLoc.getStatusMessage().toString().contains("User imported Successfully."))
				uploadMessageFound = true;

		}
		if (uploadMessageFound) {
			DriverConfig.setLogString("Blank fields for non mandatory fields verified successfully.", true);
			Assert.assertTrue(true);
		} else {
			DriverConfig.setLogString("Failed to Verify Blank fields for non mandatory fields.", true);
			Assert.assertTrue(false);
		}
	}

	/**
	 * Verify duplicate email.
	 */
	public void verifyDuplicateEmail() {
		DriverConfig.setLogString("Duplicate email ids for location uploads are rejected.", true);
		checkFileErrors();
		if (DriverConfig.getDriver().findElement(By.id("success")).isDisplayed()) {
			DriverConfig.setLogString("Uploaded successfully.", true);
			Assert.assertTrue(false);
		} else {
			DriverConfig.setLogString("Upload failed.", true);
			logger.info(getErrorDivMessage());
			Assert.assertTrue(true);
		}
		smallWait();
		List<UserLocationStaging> userLocationStaging = getUserLocationStaging();

		for (UserLocationStaging ULS : userLocationStaging) {
			if (ULS.getStatusMessage().toString().contains("Duplicate record found for email ")) {
				uploadMessageFound = true;
				break;
			}
		}

		if (uploadMessageFound) {
			DriverConfig.setLogString("Duplicate mail verified successfully", true);
			Assert.assertTrue(true);
		} else {
			DriverConfig.setLogString("Failed to Verify duplicate email id.", true);
			Assert.assertTrue(false);
		}
	}

	/**
	 * Verify invalid ecp core id.
	 */
	public void verifyInvalidECPCoreId() {
		DriverConfig.setLogString("Invalid ECP Core ids are rejected.", true);
		checkFileErrors();
		if (DriverConfig.getDriver().findElement(By.id("success")).isDisplayed()) {
			DriverConfig.setLogString("Uploaded successfully.", true);
			Assert.assertTrue(false);
		} else {
			DriverConfig.setLogString("Upload failed.", true);
			DriverConfig.setLogString("Invalid/Blank ECP core id verified successfully", true);
			logger.info(getErrorDivMessage());
			Assert.assertTrue(true);
		}
		smallWait();
		UploadSession uploadSession = new UploadSession();

		uploadSession = uploadSessionDao.findByFileName(csvFile.getName());
		Assert.assertTrue(uploadSession.getStatus().toString().endsWith("FAILED"), "Status doesn't endswith failed");

		List<UserLocationStaging> userLocationStaging = userLocationStagingDao.findByUploadSessionId(uploadSession.getId());
		Assert.assertTrue(userLocationStaging.isEmpty(), "user location is staging is not empty");

	}

	/**
	 * Verify duplicate account.
	 */
	public void verifyDuplicateAccount() {
		DriverConfig.setLogString("Duplicate account is rejected.", true);
		checkFileErrors();
		if (DriverConfig.getDriver().findElement(By.id("success")).isDisplayed()) {
			DriverConfig.setLogString("Uploaded successfully", true);
			Assert.assertTrue(false);
		} else {
			DriverConfig.setLogString("Upload failed.", true);
			logger.info(getErrorDivMessage());
			Assert.assertTrue(true);
		}
		smallWait();
		List<UserLocationStaging> userLocationStaging = getUserLocationStaging();

		for (UserLocationStaging ULS : userLocationStaging) {
			logger.info("userLocStaging.getStatusMessage(): " + ULS.getStatusMessage());

			if (ULS.getStatusMessage().toString().contains("Duplicate record found for email"))
				uploadMessageFound = true;

		}

		if (uploadMessageFound) {
			DriverConfig.setLogString("Duplicate account verified successfully", true);
			Assert.assertTrue(true);
		} else {
			DriverConfig.setLogString("Failed to Verify duplicate account.", true);
			Assert.assertTrue(false);
		}
	}

	/**
	 * Generate filepath.
	 * @return the string
	 */
	public String generateFilepath() {
		String filepath = "";

		File currentFile = new File("");
		Date date = DateUtil.getUTCDate();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.ENGLISH);
		String uploadFile = "bulkUploadFile-" + dateFormat.format(date) + ".csv";

		String currentPath = currentFile.getAbsolutePath();

		currentPath = currentPath.replaceAll("\\\\", "/");

		currentPath = currentPath.substring(0, currentPath.lastIndexOf("/"));

		filepath = currentPath + "/insite/target/bulkUpload/" + uploadFile;

		return filepath;
	}

	/**
	 * Generate valid csv.
	 * @param noOfValidRecords the no of valid records
	 * @param path the path
	 * @return the string
	 * @see com.ecofactor.qa.automation.insite.page.OnBoard#generateValidCSV(int,
	 *      java.lang.String)
	 */
	public String generateValidCSV(int noOfValidRecords, String path, String ecpId) {

		String userEmail = "";
		try {
			Date date = DateUtil.getUTCDate();
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.ENGLISH);
			String timeStamp = dateFormat.format(date);

			userEmail = "vinoopraj+upload" + timeStamp + "@ecofactor.com";

			String records = ecpId+"," + DateUtil.format(DateUtil.getUTCDate(), DateUtil.DATE_ONLY_FMT) + ",1.01E+21,Vinoop,Raj,EN," + userEmail + ",2813760042,2813760040,"
					+ timeStamp + " East Duane Avenue,Sunnyvale,CA,94085,947 East Duane Avenue,Sunnyvale,CA,94085,US,Woodside Road,1,St,0.124,"
					+ DateUtil.format(DateUtil.getUTCDate(), DateUtil.DATE_ONLY_FMT) + "";

			for (int loopVal = 0; loopVal < noOfValidRecords; loopVal++) {
				records += "\n";
			}

			String content = records;

			File file = new File(path);

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();
		} catch (Exception e) {
			DriverConfig.setLogString("error", true);
			e.printStackTrace();
		}
		return userEmail;
	}

	/**
	 * Upload and submit file.
	 * @param filePath the file path
	 * @see com.ecofactor.qa.automation.insite.page.OnBoard#uploadAndSubmitFile(java.lang.String)
	 */
	public void uploadAndSubmitFile(String filePath) {
		largeWait();
		DriverConfig.setLogString("Upload the file prepared and click submit."+filePath, true);
		WebElement csvFile = DriverConfig.getDriver().findElement(By.id(onBoardConfig.get(FILE_INPUT)));
		csvFile.sendKeys(filePath);
		DriverConfig.getDriver().findElement(By.id(onBoardConfig.get(FILE_SUBMIT))).submit();
	}

	/**
	 * Upload and fetch data.
	 * @param filePath the file path
	 */
	private void uploadAndFetchData(String filePath) {
		smallWait();
		csvFile = new File(filePath);
		uploadAndSubmitFile(filePath);
		WaitUtil.waitUntil(10000);

	}

	/**
	 * Gets the error div message.
	 * @return the error div message
	 */
	public String getErrorDivMessage() {

		String message = null;
		messageDivs = new ArrayList<String>();
		messageDivs.add("errorDownload");
		messageDivs.add("incompleteError");
		messageDivs.add("invalidEcpCore");
		messageDivs.add("incompleteInUseError");

		for (String msgDiv : messageDivs) {
			logger.info("Div Displayed: " + msgDiv);
			if (DriverConfig.getDriver().findElement(By.id(msgDiv)).isDisplayed()) {
				message = DriverConfig.getDriver().findElement(By.id(msgDiv)).getText();
				logger.info("Message Displayed: " + message, true);
				break;
			}

		}

		return message;
	}

	/**
	 * Gets the user location staging.
	 * @return the user location staging
	 */
	private List<UserLocationStaging> getUserLocationStaging() {

		UploadSession uploadSession = new UploadSession();
		uploadSession = uploadSessionDao.findByFileName(csvFile.getName());
		List<UserLocationStaging> userLocationStaging = userLocationStagingDao.findByUploadSessionId(uploadSession.getId());
		return userLocationStaging;
	}

	/**
	 * Verify user.
	 * @param userName the user name
	 * @see com.ecofactor.qa.automation.insite.page.OnBoard#verifyUser(java.lang.String)
	 */
	public void verifyUser(String userName) {

		DriverConfig.setLogString("Verify User " + userName, true);
		User user = efUserDao.findByUserName(userName);
		Assert.assertNotNull(user);
	}

	/**
	 * Generate filepath.
	 * @param extension the extension
	 * @return the string
	 */
	public String generateFilepath(String extension) {
		String filepath = "";

		File currentFile = new File("");
		Date date = DateUtil.getUTCDate();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.ENGLISH);
		String uploadFile = "bulkUploadFile-" + dateFormat.format(date) + "." + extension;

		String currentPath = currentFile.getAbsolutePath();

		currentPath = currentPath.replaceAll("\\\\", "/");

		currentPath = currentPath.substring(0, currentPath.lastIndexOf("/"));

		filepath = currentPath + "/insite/target/" + uploadFile;

		return filepath;
	}

	/**
	 * Verify duplicate file.
	 * @return true, if successful
	 * @see com.ecofactor.qa.automation.insite.page.OnBoard#verifyDuplicateFile()
	 */
	public boolean verifyDuplicateFile() {
		smallWait();
		boolean hasErrors = false;
		WebElement errorElement = DriverConfig.getDriver().findElement(By.id("filenameError"));
		if (errorElement != null) {
			if (errorElement.getText().equalsIgnoreCase("Filename must be unique.")) {
				hasErrors = true;
			}
		}

		return hasErrors;
	}

	/**
	 * Verify invalid csv.
	 * @return true, if successful
	 * @see com.ecofactor.qa.automation.insite.page.OnBoard#verifyInvalidCsv()
	 */
	public boolean verifyInvalidCsv() {
		smallWait();
		boolean hasErrors = false;
		List<WebElement> strongList = DriverConfig.getDriver().findElements(By.tagName("strong"));
		for (WebElement webElement : strongList) {

			if (webElement.isDisplayed() && webElement.getText().equalsIgnoreCase("Invalid CSV Format.")) {
				hasErrors = true;
			}
		}
		return hasErrors;
	}

	/**
	 * @param fileName
	 * @see com.ecofactor.qa.automation.insite.page.OnBoard#verifyAddressLookUp(java.lang.String)
	 */
	@Override
	public void verifyAddressLookUp(String fileName) {
		csvFile = new File(fileName);
		smallWait();
		insiteLogin.login(insiteLoginTestConfig.get(VALID_LOGIN_USER), insiteLoginTestConfig.get(VALID_LOGIN_PASSWORD));
		clickInstallation();
		List<UserLocationStaging> userLocationStaging = getUserLocationStaging();
		UserLocationStaging userLocStaging = userLocationStaging.get(0);
		smallWait();
		installationHardware.checkLookUpAddress(userLocStaging.getServiceAddress());
	}

}
