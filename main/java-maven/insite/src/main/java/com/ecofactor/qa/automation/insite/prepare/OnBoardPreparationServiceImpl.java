/*
 * OnBoardPreparationServiceImpl.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.insite.prepare;

import static com.ecofactor.qa.automation.insite.config.OnBoardTestConfig.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ecofactor.qa.automation.insite.config.OnBoardConfig;
import com.ecofactor.qa.automation.insite.config.OnBoardTestConfig;
import com.ecofactor.qa.automation.util.DateUtil;
import com.ecofactor.qa.automation.util.DriverConfig;
import com.ecofactor.qa.automation.util.WaitUtil;
import com.google.inject.Inject;

/**
 * The Class OnBoardPreparationServiceImpl.
 *
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class OnBoardPreparationServiceImpl implements OnBoardPreparationService {
	private static final Logger logger = LoggerFactory.getLogger(OnBoardPreparationServiceImpl.class);
	private StringBuilder fileContent;
	private String preparedFilePath;
	long key = 0;

	@Inject
	private OnBoardConfig onBoardConfig;
	@Inject
	private OnBoardTestConfig onBoardTestConfig;

	/**
	 * The Enum csvFields.
	 * @author $Author:$
	 * @version $Rev:$ $Date:$
	 */
	public enum csvFields {
		PROGRAM_ID, CREATED_DATE, ESID, FIRST_NAME, LAST_NAME, LANGUAGE, EMAIL, PHONE, OTHER_PHONE, SERVICE_ADDRESS, SERVICE_CITY, SERVICE_STATE, SERVICE_ZIP_CODE, MAILING_ADDRESS, MAILING_CITY, MAILING_STATE, MAILING_ZIP, COUNTRY, LOCATION_NAME, NUMBER_OF_THERMOSTATS, THERMOSTAT_NAMES, AVERAGE_PRICE, INSTALLATION_DATE
	};

	/**
	 * Prepare.
	 * @param method the method
	 * @return the string
	 * @throws ParseException
	 * @see com.ecofactor.qa.automation.insite.prepare.OnBoardPreparationService#prepare(java.lang.String)
	 */
	@Override
	public String prepare(String method) {
		getAppendKey();
		fileContent = new StringBuilder();
		switch (method) {
		case "testAddThermostat":
		case "testRemoveThermostat":
		case "testThermostatNameCanBeSaved":
		case "testBulkUploadAddressLookup":
		case "testUploadBlankNonmandatoryColumns":
			preparedFilePath = prepareBlankFields(method);
			break;
		case "testDuplicateEmail":
			preparedFilePath = prepareDuplicateEmail(method);
			break;
		case "testDuplicateAccount":
			preparedFilePath = prepareDuplicateAccount(method);
			break;
		case "testInvalidEcpCoreId":
			preparedFilePath = prepareInvalidECPCoreId(method);
			break;
		case "ACTIVATE_NEW_USER":
			preparedFilePath = prepareActivateNewUser(method);
			break;
		case "SPANISH_USER":
			preparedFilePath = prepareActivateNewUser(method);
			break;
		case "testSameEmailOverwritesOldAccount":
			preparedFilePath = prepareSameEmailOverwritesOldAccount(method);
			break;
		case "testDifferentAddrDifferentEmail":
			preparedFilePath = prepareDifferentAddrDifferentEmail(method);
			break;
		case "testDifferentEmailDifferentAddrAbrev":
			preparedFilePath = prepareDifferentEmailDifferentAddrAbrev(method);
			break;
		case "testSameNameEmailDifferentAddr":
			preparedFilePath = prepareSameNameEmailDifferentAddr(method);
			break;
		case "testDifferentEmailSameAddr":
			preparedFilePath = prepareDiffEmailSameAddr(method);
			break;
		case "testSameEmailSameAddr":
			preparedFilePath = prepareSameEmailSameAddr(method);
			break;
		case "testSameEmailSameAddrCapitalized":
			preparedFilePath = prepareSameEmailSameAddrCapitalized(method);
			break;
		case "testErrorReporting":
			preparedFilePath = prepareErrorReporting(method);
			break;

		// negative scenarios
		case "testBlankProgramId":
			preparedFilePath = prepareBlankPgmId();
			break;
		/*
		 * case: "EXISTING_USER": break;
		 */
		case "testFutureCreatedDate":
			preparedFilePath = prepareFutureCreateDate();
			break;
		case "testPastInstallationDate":
			preparedFilePath = preparePastInstallDate();
			break;
		case "testBlankESID":
			preparedFilePath = prepareBlankESID();
			break;
		case "testCharachtersInESID":
			preparedFilePath = prepareCharInESID();
			break;
		case "testNumbersInESID":
			preparedFilePath = prepareNumInESID();
			break;
		case "testCharachtersInProgramId":
			preparedFilePath = prepareCharInPGMID();
			break;
		case "testCharachtersInPhoneNo":
			preparedFilePath = prepareCharInPhoneNo();
			break;
		case "testCharachtersInZipCode":
			preparedFilePath = prepareCharInZipCode();
			break;
		case "testNumbersInStateField":
			preparedFilePath = prepareNumInState();
			break;
		case "testCharachtersInNoTSTATS":
			preparedFilePath = prepareCharInNoTSTATS();
			break;
		case "testCharachtersInAvgPrice":
			preparedFilePath = prepareCharInAvgPrice();
			break;
		case "testInvalidCountryCodeLength":
			preparedFilePath = prepareInvalidCountryCodeLen();
			break;
		case "testInvalidEmailID":
			preparedFilePath = prepareInvalidEmail();
			break;
		case "testOtherLanguages":
			preparedFilePath = prepareOtherLang();
			break;
		case "testBlankMandatoryField":
			preparedFilePath = prepareBlankMandatory();
			break;
		case "testAddAdditionalColumn":
			preparedFilePath = prepareAdditionalColumn();
			break;
		case "testFieldPositionChangedInCSV":
			preparedFilePath = prepareFieldPositionChange();
			break;
		case "testInvalidInstallationDate":
			preparedFilePath = prepareWithInvalidInstallationDate();
			break;
		case "testBlankInstallationTime":
			preparedFilePath = prepareWithBlankInstallationTime();
			break;
		case "testBlankPreConfig":
			preparedFilePath = prepareWithBlankPreConfig();
			break;
		case "testPreConfigWithText":
			preparedFilePath = prepareWithTextInPreConfig();
			break;
		case "testBlankInstallionTimeWithoutPreconfig":
			preparedFilePath = prepareWithBlankInstallionTimeWithoutPreconfig();
			break;
		case "testBlankInstallionTimeWithPreconfigText":
			preparedFilePath = prepareWithBlankInstallionTimeWithPreconfigText();
			break;
		case "testValidPreconfigWithInstallationTime":
			preparedFilePath = prepareWithPreconfigAndInstallationTime();
			break;
		case "testInvalidInstallationTimeFormat":
			preparedFilePath = prepareWithInvalidInstallationTimeFormat();
			break;

		}
		return preparedFilePath;
	}

	/**
	 * Prepare field position change.
	 * @return the string
	 */
	private String prepareFieldPositionChange() {
		String userEmail = "";
		String records = null;
		try {
			Date date = DateUtil.getUTCDate();
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.ENGLISH);
			String timeStamp = dateFormat.format(date);

			Calendar localTime = DateUtil.getLocalCalendar("America/Los_Angeles");
			String localTimeString = DateUtil.format(localTime, "HH:mm");

			userEmail = "ram_upload" + timeStamp + "@ecofactor.com";

			records = "NO," + DateUtil.format(DateUtil.getUTCDate(), DateUtil.DATE_ONLY_FMT) + ",1.01E+21,Ram,Durai,EN," + userEmail + ",2813760042,2813760040," + timeStamp
					+ " East Duane Avenue,Sunnyvale,CA,94085,947 East Duane Avenue,Sunnyvale,CA,94085,US,Woodside Road,1,St,0.124,"
					+ DateUtil.format(DateUtil.getUTCDate(), DateUtil.DATE_ONLY_FMT) + ","+localTimeString+",205";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return createCSV(records);
	}

	/**
	 * Prepare additional column.
	 * @return the string
	 */
	private String prepareAdditionalColumn() {

		String userEmail = "";
		String records = null;

		try {

			Date date = DateUtil.getUTCDate();
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.ENGLISH);
			String timeStamp = dateFormat.format(date);

			Calendar localTime = DateUtil.getLocalCalendar("America/Los_Angeles");
			String localTimeString = DateUtil.format(localTime, "HH:mm");

			userEmail = "ram_upload" + timeStamp + "@ecofactor.com";

			records = "205," + DateUtil.format(DateUtil.getUTCDate(), DateUtil.DATE_ONLY_FMT) + ",1.01E+21,Ram,Durai,EN," + userEmail + ",2813760042,2813760040," + timeStamp
					+ " East Duane Avenue,Sunnyvale,CA,94085,947 East Duane Avenue,Sunnyvale,CA,94085,US,Woodside Road,1,St,0.124,"
					+ DateUtil.format(DateUtil.getUTCDate(), DateUtil.DATE_ONLY_FMT) + "," + localTimeString + ",NO,extracolumn";

		} catch (Exception e) {
			e.printStackTrace();
		}

		return createCSV(records);
	}

	/**
	 * Prepare blank mandatory.
	 * @return the string
	 */
	private String prepareBlankMandatory() {
		String userEmail = "";
		Date date = DateUtil.getUTCDate();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.ENGLISH);
		String timeStamp = dateFormat.format(date);

		String records = "205," + DateUtil.format(DateUtil.getUTCDate(), DateUtil.DATE_ONLY_FMT) + ",1.01E+21,Ram,Durai,EN," + userEmail + ",2813760042,2813760040," + timeStamp
				+ " East Duane Avenue,Sunnyvale,CA,94085,947 East Duane Avenue,Sunnyvale,CA,94085,US,Woodside Road,1,St,0.124,"
				+ DateUtil.format(DateUtil.getUTCDate(), DateUtil.DATE_ONLY_FMT) + "";

		return createCSV(records);
	}

	/**
	 * Prepare other lang.
	 * @return the string
	 */
	private String prepareOtherLang() {
		String userEmail = "";
		Date date = DateUtil.getUTCDate();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.ENGLISH);
		String timeStamp = dateFormat.format(date);
		userEmail = "ram_upload" + timeStamp + "@ecofactor.com";

		String records = "205," + DateUtil.format(DateUtil.getUTCDate(), DateUtil.DATE_ONLY_FMT) + ",1.01E+21,Ram,Durai,hi," + userEmail + ",2813760042,2813760040," + timeStamp
				+ " East Duane Avenue,Sunnyvale,CA,94085,947 East Duane Avenue,Sunnyvale,CA,94085,US,Woodside Road,1,St,0.124,"
				+ DateUtil.format(DateUtil.getUTCDate(), DateUtil.DATE_ONLY_FMT) + "";

		return createCSV(records);
	}

	/**
	 * Prepare invalid email.
	 * @return the string
	 */
	private String prepareInvalidEmail() {
		String userEmail = "";
		Date date = DateUtil.getUTCDate();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.ENGLISH);
		String timeStamp = dateFormat.format(date);
		userEmail = "ram_upload" + timeStamp + "ecofactor.com";

		String records = "205," + DateUtil.format(DateUtil.getUTCDate(), DateUtil.DATE_ONLY_FMT) + ",1.01E+21,Ram,Durai,EN," + userEmail + ",2813760042,2813760040," + timeStamp
				+ " East Duane Avenue,Sunnyvale,CA,94085,947 East Duane Avenue,Sunnyvale,CA,94085,US,Woodside Road,1,St,0.124,"
				+ DateUtil.format(DateUtil.getUTCDate(), DateUtil.DATE_ONLY_FMT) + "";

		return createCSV(records);
	}

	/**
	 * Prepare invalid country code len.
	 * @return the string
	 */
	private String prepareInvalidCountryCodeLen() {
		String userEmail = "";
		Date date = DateUtil.getUTCDate();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.ENGLISH);
		String timeStamp = dateFormat.format(date);
		userEmail = "ram_upload" + timeStamp + "@ecofactor.com";

		String records = "205," + DateUtil.format(DateUtil.getUTCDate(), DateUtil.DATE_ONLY_FMT) + ",1.01E+21,Ram,Durai,EN," + userEmail + ",2813760042,2813760040," + timeStamp
				+ " East Duane Avenue,Sunnyvale,CA,94085,947 East Duane Avenue,Sunnyvale,CA,94085,UnitedStates,Woodside Road,1,St,0.124,"
				+ DateUtil.format(DateUtil.getUTCDate(), DateUtil.DATE_ONLY_FMT) + "";

		return createCSV(records);
	}

	/**
	 * Prepare char in avg price.
	 * @return the string
	 */
	private String prepareCharInAvgPrice() {
		String userEmail = "";
		Date date = DateUtil.getUTCDate();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.ENGLISH);
		String timeStamp = dateFormat.format(date);
		userEmail = "ram_upload" + timeStamp + "@ecofactor.com";

		String records = "205," + DateUtil.format(DateUtil.getUTCDate(), DateUtil.DATE_ONLY_FMT) + ",1.01E+21,Ram,Durai,EN," + userEmail + ",2813760042,2813760040," + timeStamp
				+ " East Duane Avenue,Sunnyvale,CA,94085,947 East Duane Avenue,Sunnyvale,CA,94085,US,Woodside Road,1,St,avgprice,"
				+ DateUtil.format(DateUtil.getUTCDate(), DateUtil.DATE_ONLY_FMT) + "";

		return createCSV(records);
	}

	/**
	 * Prepare char in no tstats.
	 * @return the string
	 */
	private String prepareCharInNoTSTATS() {
		String userEmail = "";
		Date date = DateUtil.getUTCDate();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.ENGLISH);
		String timeStamp = dateFormat.format(date);
		userEmail = "ram_upload" + timeStamp + "@ecofactor.com";

		String records = "205," + DateUtil.format(DateUtil.getUTCDate(), DateUtil.DATE_ONLY_FMT) + ",1.01E+21,Ram,Durai,EN," + userEmail + ",2813760042,2813760040," + timeStamp
				+ " East Duane Avenue,Sunnyvale,CA,94085,947 East Duane Avenue,Sunnyvale,CA,94085,US,Woodside Road,nooftstats,St,0.124,"
				+ DateUtil.format(DateUtil.getUTCDate(), DateUtil.DATE_ONLY_FMT) + "";

		return createCSV(records);
	}

	/**
	 * Prepare num in state.
	 * @return the string
	 */
	private String prepareNumInState() {
		String userEmail = "";
		Date date = DateUtil.getUTCDate();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.ENGLISH);
		String timeStamp = dateFormat.format(date);
		userEmail = "ram_upload" + timeStamp + "@ecofactor.com";

		String records = "205," + DateUtil.format(DateUtil.getUTCDate(), DateUtil.DATE_ONLY_FMT) + ",1.01E+21,Ram,Durai,EN," + userEmail + ",2813760042,2813760040," + timeStamp
				+ " East Duane Avenue,Sunnyvale,121,94085,947 East Duane Avenue,Sunnyvale,CA,94085,US,Woodside Road,1,St,0.124,"
				+ DateUtil.format(DateUtil.getUTCDate(), DateUtil.DATE_ONLY_FMT) + "";

		return createCSV(records);
	}

	/**
	 * Prepare char in zip code.
	 * @return the string
	 */
	private String prepareCharInZipCode() {
		String userEmail = "";
		Date date = DateUtil.getUTCDate();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.ENGLISH);
		String timeStamp = dateFormat.format(date);
		userEmail = "ram_upload" + timeStamp + "@ecofactor.com";

		String records = "205," + DateUtil.format(DateUtil.getUTCDate(), DateUtil.DATE_ONLY_FMT) + ",1.01E+21,Ram,Durai,EN," + userEmail + ",2813760042,2813760040," + timeStamp
				+ " East Duane Avenue,Sunnyvale,CA,zipcode,947 East Duane Avenue,Sunnyvale,CA,94085,US,Woodside Road,1,St,0.124,"
				+ DateUtil.format(DateUtil.getUTCDate(), DateUtil.DATE_ONLY_FMT) + "";

		return createCSV(records);
	}

	/**
	 * Prepare char in phone no.
	 * @return the string
	 */
	private String prepareCharInPhoneNo() {
		String userEmail = "";
		Date date = DateUtil.getUTCDate();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.ENGLISH);
		String timeStamp = dateFormat.format(date);
		userEmail = "ram_upload" + timeStamp + "@ecofactor.com";

		String records = "205," + DateUtil.format(DateUtil.getUTCDate(), DateUtil.DATE_ONLY_FMT) + ",1.01E+21,Ram,Durai,EN," + userEmail + ",phoneno,2813760040," + timeStamp
				+ " East Duane Avenue,Sunnyvale,CA,94085,947 East Duane Avenue,Sunnyvale,CA,94085,US,Woodside Road,1,St,0.124,"
				+ DateUtil.format(DateUtil.getUTCDate(), DateUtil.DATE_ONLY_FMT) + "";

		return createCSV(records);
	}

	/**
	 * Prepare char in pgmid.
	 * @return the string
	 */
	private String prepareCharInPGMID() {
		String userEmail = "";
		Date date = DateUtil.getUTCDate();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.ENGLISH);
		String timeStamp = dateFormat.format(date);
		userEmail = "ram_upload" + timeStamp + "@ecofactor.com";

		String records = "pgmid," + DateUtil.format(DateUtil.getUTCDate(), DateUtil.DATE_ONLY_FMT) + ",1.01E+21,Ram,Durai,EN," + userEmail + ",2813760042,2813760040," + timeStamp
				+ " East Duane Avenue,Sunnyvale,CA,94085,947 East Duane Avenue,Sunnyvale,CA,94085,US,Woodside Road,1,St,0.124,"
				+ DateUtil.format(DateUtil.getUTCDate(), DateUtil.DATE_ONLY_FMT) + "";

		return createCSV(records);
	}

	/**
	 * Prepare num in esid.
	 * @return the string
	 */
	private String prepareNumInESID() {
		String userEmail = "";
		Date date = DateUtil.getUTCDate();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.ENGLISH);
		String timeStamp = dateFormat.format(date);
		userEmail = "ram_upload" + timeStamp + "@ecofactor.com";

		String records = "205," + DateUtil.format(DateUtil.getUTCDate(), DateUtil.DATE_ONLY_FMT) + ",21,Ram,Durai,EN," + userEmail + ",2813760042,2813760040," + timeStamp
				+ " East Duane Avenue,Sunnyvale,CA,94085,947 East Duane Avenue,Sunnyvale,CA,94085,US,Woodside Road,1,St,0.124,"
				+ DateUtil.format(DateUtil.getUTCDate(), DateUtil.DATE_ONLY_FMT) + ", " + DateUtil.format(DateUtil.getUTCDate(), "HH:mm") + ",yes";

		return createCSV(records);
	}

	/**
	 * Prepare char in esid.
	 * @return the string
	 */
	private String prepareCharInESID() {
		String userEmail = "";
		Date date = DateUtil.getUTCDate();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.ENGLISH);
		String timeStamp = dateFormat.format(date);
		userEmail = "ram_upload" + timeStamp + "@ecofactor.com";

		String records = "205," + DateUtil.format(DateUtil.getUTCDate(), DateUtil.DATE_ONLY_FMT) + ",if1.01E+21,Ram,Durai,EN," + userEmail + ",2813760042,2813760040," + timeStamp
				+ " East Duane Avenue,Sunnyvale,CA,94085,947 East Duane Avenue,Sunnyvale,CA,94085,US,Woodside Road,1,St,0.124,"
				+ DateUtil.format(DateUtil.getUTCDate(), DateUtil.DATE_ONLY_FMT) + ", " + DateUtil.format(DateUtil.getUTCDate(), "HH:mm") + ",yes";

		return createCSV(records);
	}

	/**
	 * Prepare blank esid.
	 * @return the string
	 */
	private String prepareBlankESID() {

		String userEmail = "";
		Date date = DateUtil.getUTCDate();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.ENGLISH);
		String timeStamp = dateFormat.format(date);
		userEmail = "ram_upload" + timeStamp + "@ecofactor.com";

		String records = "205," + DateUtil.format(DateUtil.getUTCDate(), DateUtil.DATE_ONLY_FMT) + ",,Ram,Durai,EN," + userEmail + ",2813760042,2813760040," + timeStamp
				+ " East Duane Avenue,Sunnyvale,CA,94085,947 East Duane Avenue,Sunnyvale,CA,94085,US,Woodside Road,1,St,0.124,"
				+ DateUtil.format(DateUtil.getUTCDate(), DateUtil.DATE_ONLY_FMT) + "";

		return createCSV(records);
	}

	/**
	 * Prepare past install date.
	 * @return the string
	 */
	private String preparePastInstallDate() {

		String userEmail = "";
		Date date = DateUtil.getUTCDate();

		Calendar installDate = DateUtil.getUTCCalendar();
		installDate.add(Calendar.DATE, -4);

		String installationDate = DateUtil.format(installDate, DateUtil.DATE_ONLY_FMT);// DateUtil.format(date,
																						// DateUtil.DATE_ONLY_FMT);
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.ENGLISH);
		String timeStamp = dateFormat.format(date);
		userEmail = "ram_upload" + timeStamp + "@ecofactor.com";

		String records = "205," + DateUtil.format(DateUtil.getUTCDate(), DateUtil.DATE_ONLY_FMT) + ",1.01E+21,Ram,Durai,EN," + userEmail + ",2813760042,2813760040," + timeStamp
				+ " East Duane Avenue,Sunnyvale,CA,94085,947 East Duane Avenue,Sunnyvale,CA,94085,US,Woodside Road,1,St,0.124," + installationDate + "";

		return createCSV(records);
	}

	/**
	 * Prepare future create date.
	 * @return the string
	 */
	private String prepareFutureCreateDate() {
		String userEmail = "";
		Date date = DateUtil.getUTCDate();

		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, 2);

		String createdDate = DateUtil.format(calendar, DateUtil.DATE_ONLY_FMT);
		DriverConfig.setLogString("createdDate" + createdDate, true);
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.ENGLISH);
		String timeStamp = dateFormat.format(date);
		userEmail = "ram_upload" + timeStamp + "@ecofactor.com";

		String records = "205," + createdDate + ",1.01E+21,Ram,Durai,EN," + userEmail + ",2813760042,2813760040," + timeStamp
				+ " East Duane Avenue,Sunnyvale,CA,94085,947 East Duane Avenue,Sunnyvale,CA,94085,US,Woodside Road,1,St,0.124," + createdDate + "";

		return createCSV(records);
	}

	/**
	 * Prepare blank pgm id.
	 * @return the string
	 */
	private String prepareBlankPgmId() {

		String userEmail = "";
		Date date = DateUtil.getUTCDate();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.ENGLISH);
		String timeStamp = dateFormat.format(date);
		userEmail = "ram_upload" + timeStamp + "@ecofactor.com";

		String records = "," + DateUtil.format(DateUtil.getUTCDate(), DateUtil.DATE_ONLY_FMT) + ",1.01E+21,Ram,Durai,EN," + userEmail + ",2813760042,2813760040," + timeStamp
				+ " East Duane Avenue,Sunnyvale,CA,94085,947 East Duane Avenue,Sunnyvale,CA,94085,US,Woodside Road,1,St,0.124,"
				+ DateUtil.format(DateUtil.getUTCDate(), DateUtil.DATE_ONLY_FMT) + "";

		return createCSV(records);
	}

	/**
	 * Prepare with preconfig and installation time.
	 * @return the string
	 */
	private String prepareWithPreconfigAndInstallationTime() {

		String userEmail = "";
		Date date = DateUtil.getUTCDate();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.ENGLISH);
		String timeStamp = dateFormat.format(date);
		userEmail = "ram_upload" + timeStamp + "@ecofactor.com";

		String records = "205," + DateUtil.format(DateUtil.getUTCDate(), DateUtil.DATE_ONLY_FMT) + ",1.01E+21,Ram,Durai,EN," + userEmail + ",2813760042,2813760040," + timeStamp
				+ " East Duane Avenue,Sunnyvale,CA,94085,947 East Duane Avenue,Sunnyvale,CA,94085,US,Woodside Road,1,St,0.124,"
				+ DateUtil.format(DateUtil.getUTCDate(), DateUtil.DATE_ONLY_FMT) + "," + DateUtil.format(DateUtil.getUTCDate(), "HH:mm") + ",yes";

		return createCSV(records);
	}

	/**
	 * Prepare with invalid installation date.
	 * @return the string
	 */
	private String prepareWithInvalidInstallationDate() {

		String userEmail = "";
		Date date = DateUtil.getUTCDate();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.ENGLISH);
		String timeStamp = dateFormat.format(date);
		userEmail = "ram_upload" + timeStamp + "@ecofactor.com";

		String records = "205," + DateUtil.format(DateUtil.getUTCDate(), DateUtil.DATE_ONLY_FMT) + ",1.01E+21,Ram,Durai,EN," + userEmail + ",2813760042,2813760040," + timeStamp
				+ " East Duane Avenue,Sunnyvale,CA,94085,947 East Duane Avenue,Sunnyvale,CA,94085,US,Woodside Road,1,St,0.124,"
				+ DateUtil.format(DateUtil.getUTCDate(), DateUtil.DATE_FMT_FULL_TZ) + "," + DateUtil.format(DateUtil.getUTCDate(), "HH:mm") + ",yes";

		return createCSV(records);
	}

	/**
	 * Prepare with blank installation time.
	 * @return the string
	 */
	private String prepareWithBlankInstallationTime() {

		String userEmail = "";
		Date date = DateUtil.getUTCDate();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.ENGLISH);
		String timeStamp = dateFormat.format(date);
		userEmail = "ram_upload" + timeStamp + "@ecofactor.com";

		String records = "205," + DateUtil.format(DateUtil.getUTCDate(), DateUtil.DATE_ONLY_FMT) + ",1.01E+21,Ram,Durai,EN," + userEmail + ",2813760042,2813760040," + timeStamp
				+ " East Duane Avenue,Sunnyvale,CA,94085,947 East Duane Avenue,Sunnyvale,CA,94085,US,Woodside Road,1,St,0.124,"
				+ DateUtil.format(DateUtil.getUTCDate(), DateUtil.DATE_ONLY_FMT) + "," + " " + ",yes";

		return createCSV(records);
	}

	/**
	 * Prepare with blank pre config.
	 * @return the string
	 */
	private String prepareWithBlankPreConfig() {

		String userEmail = "";
		Date date = DateUtil.getUTCDate();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.ENGLISH);
		String timeStamp = dateFormat.format(date);
		userEmail = "ram_upload" + timeStamp + "@ecofactor.com";

		String records = "205," + DateUtil.format(DateUtil.getUTCDate(), DateUtil.DATE_ONLY_FMT) + ",1.01E+21,Ram,Durai,EN," + userEmail + ",2813760042,2813760040," + timeStamp
				+ " East Duane Avenue,Sunnyvale,CA,94085,947 East Duane Avenue,Sunnyvale,CA,94085,US,Woodside Road,1,St,0.124,"
				+ DateUtil.format(DateUtil.getUTCDate(), DateUtil.DATE_ONLY_FMT) + "," + DateUtil.format(DateUtil.getUTCDate(), "HH:mm") + ", ";

		return createCSV(records);
	}

	/**
	 * Prepare with text in pre config.
	 * @return the string
	 */
	private String prepareWithTextInPreConfig() {

		String userEmail = "";
		Date date = DateUtil.getUTCDate();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.ENGLISH);
		String timeStamp = dateFormat.format(date);
		userEmail = "ram_upload" + timeStamp + "@ecofactor.com";

		String records = "205," + DateUtil.format(DateUtil.getUTCDate(), DateUtil.DATE_ONLY_FMT) + ",1.01E+21,Ram,Durai,EN," + userEmail + ",2813760042,2813760040," + timeStamp
				+ " East Duane Avenue,Sunnyvale,CA,94085,947 East Duane Avenue,Sunnyvale,CA,94085,US,Woodside Road,1,St,0.124,"
				+ DateUtil.format(DateUtil.getUTCDate(), DateUtil.DATE_ONLY_FMT) + "," + DateUtil.format(DateUtil.getUTCDate(), "HH:mm") + ",Preconfig";

		return createCSV(records);
	}

	/**
	 * Prepare with blank installion time without preconfig.
	 * @return the string
	 */
	private String prepareWithBlankInstallionTimeWithoutPreconfig() {

		String userEmail = "";
		Date date = DateUtil.getUTCDate();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.ENGLISH);
		String timeStamp = dateFormat.format(date);
		userEmail = "ram_upload" + timeStamp + "@ecofactor.com";

		String records = "205," + DateUtil.format(DateUtil.getUTCDate(), DateUtil.DATE_ONLY_FMT) + ",1.01E+21,Ram,Durai,EN," + userEmail + ",2813760042,2813760040," + timeStamp
				+ " East Duane Avenue,Sunnyvale,CA,94085,947 East Duane Avenue,Sunnyvale,CA,94085,US,Woodside Road,1,St,0.124,"
				+ DateUtil.format(DateUtil.getUTCDate(), DateUtil.DATE_ONLY_FMT) + ", ";

		return createCSV(records);
	}

	/**
	 * Prepare with blank installion time with preconfig text.
	 * @return the string
	 */
	private String prepareWithBlankInstallionTimeWithPreconfigText() {

		String userEmail = "";
		Date date = DateUtil.getUTCDate();
		DateFormat dateFormat = new SimpleDateFormat("yyMMddHHmmss", Locale.ENGLISH);
		String timeStamp = dateFormat.format(date);
		userEmail = "ram_upload" + timeStamp + "@ecofactor.com";

		String records = "205," + DateUtil.format(DateUtil.getUTCDate(), DateUtil.DATE_ONLY_FMT) + ",1.01E+21,Ram,Durai,EN," + userEmail
				+ ",23422,87687,120 East Duane Avenue "	+ timeStamp + ",Sunnyvale,CA,94085,120 East Duane Avenue " + timeStamp + ",Sunnyvale,CA,94085,US,Dining,1,87687,0.124,"
				+ DateUtil.format(DateUtil.getUTCDate(), DateUtil.DATE_ONLY_FMT) + ", " + ",Preconfig";

		return createCSV(records);
	}

	/**
	 * Prepare with invalid installation time format.
	 * @return the string
	 */
	private String prepareWithInvalidInstallationTimeFormat() {
		String userEmail = "";
		Date date = DateUtil.getUTCDate();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.ENGLISH);
		String timeStamp = dateFormat.format(date);
		userEmail = "ram_upload" + timeStamp + "@ecofactor.com";

		StringBuffer records = new StringBuffer();
		records.append("205," + DateUtil.format(DateUtil.getUTCDate(), DateUtil.DATE_ONLY_FMT) + ",1.01E+21,Ram,Durai,EN," + userEmail
				+ ",23422,87687,120 East Duane Avenue "	+ timeStamp + ",Sunnyvale,CA,94085,120 East Duane Avenue " + timeStamp + ",Sunnyvale,CA,94085,US,Dining,1,87687,0.124,"
				+ DateUtil.format(DateUtil.getUTCDate(), DateUtil.DATE_ONLY_FMT) + "," + DateUtil.format(DateUtil.getUTCDate(), "HH:mm") + ",yes" + "\n");
		records.append("205," + DateUtil.format(DateUtil.getUTCDate(), DateUtil.DATE_ONLY_FMT) + ",1.01E+21,Ram,Durai,EN," + "ram_upload099" + timeStamp + "@ecofactor.com" + ",2813760042,2813760040," + timeStamp
				+ " 120 East Duane Avenue 1379492116164,Sunnyvale,CA,94085,947 120 East Duane Avenue 1379492116164,Sunnyvale,CA,94085,US,Woodside Road,1,St,0.124,"
				+ DateUtil.format(DateUtil.getUTCDate(), DateUtil.DATE_ONLY_FMT) + "," + DateUtil.format(DateUtil.getUTCDate(), "HH:mm:ss") + ",yes");
		return createCSV(records.toString());
	}

	/**
	 * Prepare different addr different email.
	 * @param testMethod the test method
	 * @return the string
	 */
	public String prepareDifferentAddrDifferentEmail(String testMethod) {

		return buildContent(testMethod, 2, null, null, null, null);

	}

	/**
	 * Prepare different email different addr abrev.
	 * @param testMethod the test method
	 * @return the string
	 */
	public String prepareDifferentEmailDifferentAddrAbrev(String testMethod) {

		return buildContent(testMethod, 2, null, new String[] { "123 My Street" + key, "123 My St" + key }, null, null);

	}

	/**
	 * Prepare same name email different addr.
	 * @param testMethod the test method
	 * @return the string
	 */
	public String prepareSameNameEmailDifferentAddr(String testMethod) {

		return buildContent(testMethod, 2, new String[] { "UserTest_" + key + "@ecofactor.com", "UserTest_" + key + "@ecofactor.com" }, null, new String[] { "User_" + key,
				"User_" + key }, new String[] { "User_" + key, "User_" + key });
	}

	/**
	 * Prepare diff email same addr.
	 * @param testMethod the test method
	 * @return the string
	 */
	public String prepareDiffEmailSameAddr(String testMethod) {

		return buildContent(testMethod, 2, null, new String[] { " 123 My St " + key, " 123 My St " + key }, null, null);
	}

	/**
	 * Prepare same email same addr.
	 * @param testMethod the test method
	 * @return the string
	 */
	public String prepareSameEmailSameAddr(String testMethod) {

		return buildContent(testMethod, 2, new String[] { "UserTest_" + key + "@ecofactor.com", "UserTest_" + key + "@ecofactor.com" }, new String[] { " 123 My St " + key,
				" 123 My St " + key }, null, null);
	}

	/**
	 * Prepare same email same addr capitalized.
	 * @param testMethod the test method
	 * @return the string
	 */
	public String prepareSameEmailSameAddrCapitalized(String testMethod) {

		return buildContent(testMethod, 2, new String[] { "UserTest_" + key + "@ecofactor.com", "UserTest_" + key + "@ecofactor.com" }, new String[] { "123 MY ST " + key,
				"123 My St " + key }, null, null);
	}

	/**
	 * Prepare error reporting.
	 * @param testMethod the test method
	 * @return the string
	 */
	public String prepareErrorReporting(String testMethod) {

		return buildContent(testMethod, 1, new String[] { "UserTest_" + key + "ecofactorcom" }, null, null, null);

	}

	/**
	 * Prepare same email overwrites old account.
	 * @param testMethod the test method
	 * @return the string
	 */
	public String prepareSameEmailOverwritesOldAccount(String testMethod) {

		String[] emailId = { "userTest_" + System.currentTimeMillis() + "@ecofactor.com" };
		String filePath = buildContent(testMethod, 1, emailId, null, null, null);
		fileContent = new StringBuilder();
		return filePath + "," + buildContent(testMethod, 1, emailId, null, null, null);
	}

	/**
	 * Prepare activate new user.
	 * @param testScenario the test scenario
	 * @return the string
	 */
	public String prepareActivateNewUser(String testScenario) {

		return buildContent(testScenario, 1, null, null, null, null);
	}

	/**
	 * Prepare blank fields.
	 * @param testMethod the test method
	 * @return the string
	 */
	public String prepareBlankFields(String testMethod) {

		return buildContent(testMethod, 1, null, null, null, null);
	}

	/**
	 * Prepare invalid ecp core id.
	 * @param testMethod the test method
	 * @return the string
	 */
	public String prepareInvalidECPCoreId(String testMethod) {

		return buildContent(testMethod, 1, null, null, null, null);
	}

	/**
	 * Prepare duplicate email.
	 * @param testMethod the test method
	 * @return the string
	 */
	public String prepareDuplicateEmail(String testMethod) {

		return buildContent(testMethod, 2, new String[] { "UserTest_" + key + "@ecofactor.com", "UserTest_" + key + "@ecofactor.com" }, null, null, null);
	}

	/**
	 * Prepare duplicate account.
	 * @param testMethod the test method
	 * @return the string
	 */
	public String prepareDuplicateAccount(String testMethod) {

		return buildContent(testMethod, 2, new String[] { "UserTest_" + key + "@ecofactor.com", "UserTest_" + key + "@ecofactor.com" }, new String[] { "123 My St " + key,
				"123 My St " + key }, null, null);
	}

	/**
	 * Builds the content.
	 * @param testScenario the test scenario
	 * @param noOfRecords the no of records
	 * @param emailId the email id
	 * @param address the address
	 * @param firstName the first name
	 * @param lastName the last name
	 * @return the string
	 */
	public String buildContent(String testScenario, int noOfRecords, String[] emailId, String[] address, String[] firstName, String[] lastName) {

		return buildData(testScenario, noOfRecords, emailId, address, firstName, lastName);

	}

	/**
	 * Builds the data.
	 * @param testScenario the test scenario
	 * @param noOfRecords the no of records
	 * @param emailId the email id
	 * @param address the address
	 * @param firstName the first name
	 * @param lastName the last name
	 * @return the string
	 */
	public String buildData(String testScenario, int noOfRecords, String[] emailId, String[] address, String[] firstName, String[] lastName) {

		int recordsCount = 0;
		getAppendKey();
		while (recordsCount < noOfRecords) {

			for (csvFields csvFld : csvFields.values()) {

				logger.info("records count: " + recordsCount + "; csvFld:" + csvFld + "::" + key);
				switch (csvFld.toString()) {
				case "PROGRAM_ID":
					String ecpCore = testScenario.equalsIgnoreCase("testInvalidEcpCoreId") ? onBoardTestConfig.get(INVALID_ECPCORE_ID) : onBoardTestConfig.get(VALID_ECPCORE_ID);
					fileContent.append(ecpCore + ",");
					logger.info("Program id / Ecp core id: " + ecpCore);
					break;
				case "CREATED_DATE":
					fileContent.append(DateUtil.format(DateUtil.getUTCDate(), DateUtil.DATE_ONLY_FMT) + ",");
					logger.info("Created Date: " + DateUtil.format(DateUtil.getUTCDate(), DateUtil.DATE_ONLY_FMT));
					break;
				case "ESID":
					fileContent.append("1.01E+21" + ",");
					logger.info("ESID: " + "1.01E+21");
					break;
				case "FIRST_NAME":
					String strName = null;
					strName = (firstName == null) ? "User_" + key : firstName[recordsCount];
					fileContent.append(strName + ",");
					logger.info("First Name: " + strName);
					break;
				case "LAST_NAME":
					String lstName = null;
					lstName = (lastName == null) ? "User_" + key : lastName[recordsCount];
					fileContent.append(lstName + ",");
					logger.info("Last Name: " + lstName);
					break;
				case "LANGUAGE":
					String language = null;
					if (testScenario.equalsIgnoreCase("SPANISH_USER")) {
						language = "ES";
					} else {
						language = "EN";
					}
					fileContent.append(language + ",");
					logger.info("Language: " + language);
					break;
				case "EMAIL":
					String email = "";
					if (testScenario.equalsIgnoreCase("ACTIVATE_NEW_USER")) {
						email = onBoardTestConfig.get(ACTIVATE_NEW_USER_EMAIL_ID);
					} else if (testScenario.equalsIgnoreCase("SPANISH_USER")) {
						email = onBoardTestConfig.get(ACTIVATE_SPANISH_USER);
					} else {
						email = (emailId == null) ? "User_" + key + "@ecofactor.com" : emailId[recordsCount];
					}

					fileContent.append(email + ",");
					logger.info("Email: " + email);
					break;
				case "PHONE":
					fileContent.append("23422" + ",");
					logger.info("Phone: " + "23422");
					break;
				case "OTHER_PHONE":
					fileContent.append("87687" + ",");
					logger.info("Other Phone: " + "87687");
					break;
				case "SERVICE_ADDRESS":
					String serviceAddr = null;
					serviceAddr = (address == null) ? 120 + " East Duane Avenue " + key : address[recordsCount];
					fileContent.append(serviceAddr + ",");
					logger.info("Service address: " + serviceAddr);
					break;
				case "SERVICE_CITY":
					fileContent.append("Sunnyvale" + ",");
					logger.info("Service city: " + "Sunnyvale");
					break;
				case "SERVICE_STATE":
					fileContent.append("CA" + ",");
					logger.info("Service state: " + "CA");
					break;
				case "SERVICE_ZIP_CODE":
					fileContent.append("94085" + ",");
					logger.info("Service zip code: " + "94085");
					break;
				case "MAILING_ADDRESS":
					String mailAddr = (address == null) ? 120 + " East Duane Avenue " + key : address[recordsCount];
					fileContent.append(mailAddr + ",");
					logger.info("Mailing address: " + mailAddr);
					break;
				case "MAILING_CITY":
					fileContent.append("Sunnyvale" + ",");
					logger.info("Mailing city: " + "Sunnyvale");
					break;
				case "MAILING_STATE":
					fileContent.append("CA" + ",");
					logger.info("Mailing state: " + "CA");
					break;
				case "MAILING_ZIP":
					fileContent.append("94085" + ",");
					logger.info("Mailing zip: " + "94085");
					break;
				case "COUNTRY":
					fileContent.append("US" + ",");
					logger.info("Country: " + "US");
					break;
				case "LOCATION_NAME":
					fileContent.append("Dining" + ",");
					logger.info("Location name : " + "Dining");
					break;
				case "NUMBER_OF_THERMOSTATS":
					fileContent.append("1" + ",");
					logger.info("Number of thermostats : " + 1);
					break;
				case "THERMOSTAT_NAMES":
					fileContent.append("87687" + ",");
					logger.info("Thermostat name : " + "87687");
					break;
				case "AVERAGE_PRICE":
					fileContent.append("0.124" + ",");
					logger.info("Average price : " + "0.124");
					break;
				case "INSTALLATION_DATE":
					fileContent.append(DateUtil.format(DateUtil.getUTCDate(), DateUtil.DATE_ONLY_FMT));
					fileContent.append("\n");
					logger.info("Installation Date : " + DateUtil.format(DateUtil.getUTCDate(), DateUtil.DATE_ONLY_FMT));
					recordsCount++;
					WaitUtil.waitUntil(1000);
					getAppendKey();
					break;
				}
			}
		}
		return createCSV(fileContent.toString());
	}

	/**
	 * Creates the csv.
	 * @param fileContents the file contents
	 * @return the string
	 */
	private String createCSV(String fileContents) {
		getAppendKey();
		File inputCSVFile = new File(new File(".").getAbsolutePath() + "\\target\\uploadFile_" + key + ".csv");
		FileWriter fw;
		try {
			fw = new FileWriter(inputCSVFile.getAbsoluteFile());

			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(fileContents);
			bw.close();

			logger.info("File created: " + inputCSVFile.getPath());

		} catch (IOException e) {
			e.printStackTrace();
		}
		return inputCSVFile.getAbsoluteFile().toString();
	}

	/**
	 * Gets the append key.
	 * @return the append key
	 */
	private void getAppendKey() {

		key = System.currentTimeMillis();
	}
}
