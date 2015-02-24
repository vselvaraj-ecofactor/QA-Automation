/*
 * DemandSideManagementTest.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.insite.regression;

import static com.ecofactor.qa.automation.insite.config.InsiteConfig.*;
import static com.ecofactor.qa.automation.util.DateUtil.*;
import static com.ecofactor.qa.automation.util.WaitUtil.*;
import static org.testng.Assert.*;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Guice;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.ecofactor.common.pojo.EcpCoreLSEvent;
import com.ecofactor.common.pojo.EcpCoreLSProgram;
import com.ecofactor.common.pojo.LoadShapingEventReport;
import com.ecofactor.common.pojo.Status;

import com.ecofactor.qa.automation.dao.DaoModule;

import com.ecofactor.qa.automation.dao.LSProgramDao;
import com.ecofactor.qa.automation.dao.LSProgramEventDao;

import com.ecofactor.qa.automation.dao.ThermostatEventDao;

import com.ecofactor.qa.automation.insite.InsiteModule;
import com.ecofactor.qa.automation.insite.config.InsiteConfig;
import com.ecofactor.qa.automation.insite.data.DemandSideDataProvider;
import com.ecofactor.qa.automation.insite.page.DemandSideManagement;

import com.ecofactor.qa.automation.util.DriverConfig;
import com.ecofactor.qa.automation.util.RerunFailTestAnalyzer;
import com.ecofactor.qa.automation.util.TestLogUtil;
import com.ecofactor.qa.automation.util.UtilModule;
import com.ecofactor.qa.automation.util.WaitUtil;
import com.ecofactor.qa.automation.util.test.JobValidator;
import com.google.inject.Inject;

/**
 * The Class DemandSideManagementTest.
 * 
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
@Guice(modules = { UtilModule.class, DaoModule.class, InsiteModule.class })
@Listeners(JobValidator.class)
public class DemandSideManagementTest {

	/** The logger. */
	private static Logger logger = LoggerFactory
			.getLogger(DemandSideManagementTest.class);

	/** The app config. */
	@Inject
	private InsiteConfig appConfig;

	/** The demand side management. */
	@Inject
	private DemandSideManagement demandSideManagement;

	/** The ls program dao. */
	@Inject
	private LSProgramDao lsProgramDao;

	/** The ls program event dao. */
	@Inject
	private LSProgramEventDao lsProgramEventDao;

	/** The thermostat event dao. */
	@Inject
	private ThermostatEventDao thermostatEventDao;

	/** The test log util. */
	@Inject
	private TestLogUtil testLogUtil;

	/** The db ecp core ls program. */
	private EcpCoreLSProgram dbEcpCoreLSProgram = new EcpCoreLSProgram();

	/** The ecp core ls event. */
	private EcpCoreLSEvent ecpCoreLSEvent = new EcpCoreLSEvent();

	/** The program properties map. */
	private Map<String, Object> programPropertiesMap = null;

	/** The event details. */
	private Map<String, Object> eventDetails = null;

	/** The updated event details. */
	private Map<String, Object> updatedEventDetails = null;

	/** The date format. */
	private String dateFormat = "yyyy-MM-dd HH:mm:ss";

	/** The formatter. */
	private DateFormat formatter;

	/** The program id. */
	private int programId;

	/** The start. */
	private long start;

	/**
	 * Inits the suite.
	 */
	@BeforeSuite(alwaysRun = true)
	public void initSuite() {

		HttpURLConnection urlConnection = null;
		String insiteURLString = null;
		int status = -1;
		try {
			insiteURLString = appConfig.get(INSITE_URL)
					+ appConfig.get(INSITE_LOGIN_URL);
			URL insiteURL = new URL(insiteURLString);
			urlConnection = (HttpURLConnection) insiteURL.openConnection();
			urlConnection.setReadTimeout(5000);
			status = urlConnection.getResponseCode();
		} catch (IOException e) {
			if (status != HttpURLConnection.HTTP_OK) {
				fail("Unable to connect insite portal '" + insiteURLString
						+ "'. The site is down!");
			}
		}
	}

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
			demandSideManagement.loadPage((String) param[0], (String) param[1]);
		} catch (Throwable e) {
			logger.error("Error in before method " + e.getMessage());
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
			demandSideManagement.logout();
		} catch (Throwable e) {
			logger.error("Error in after class " + e.getMessage());
		}
	}

	/**
	 * End Suite.
	 */
	@AfterSuite(alwaysRun = true)
	public void endSuite() {

		try {
			demandSideManagement.end();
		} catch (Throwable e) {
			logger.error("Error in after suite " + e.getMessage(), true);
		}
	}

	/**
	 * <ol>
	 * <li>Navigate to the particular program by iterating the pagination.</li>
	 * <li>Click on the program, and create a new event with special characters
	 * with maximum length</li>
	 * <li>Identify the length of the event name is not more than 50 characters</li>
	 * </ol>
	 * 
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 * @param programeName
	 *            the programe name
	 * @param eventName
	 *            the event name
	 * @throws ParseException
	 *             the parse exception
	 */
	@Test(dataProvider = "eventNameMetadataInput", dataProviderClass = DemandSideDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "sanity" })
	public void testMetaDataVerificationForLSEventName(final String userName,
			final String password, final String programeName,
			final String eventName) throws ParseException {

		demandSideManagement.testMetaDataVerificationForLSEventName(
				programeName, eventName);
	}

	/**
	 * <ol>
	 * <li>Navigate to the particular program by iterating the pagination.</li>
	 * <li>Click on the program, and create a new event with special characters
	 * included in event description</li>
	 * <li>Save as draft.</li>
	 * </ol>
	 * 
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 * @param programeName
	 *            the programe name
	 * @param eventName
	 *            the event name
	 * @param eventDescription
	 *            the event description
	 * @throws ParseException
	 *             the parse exception
	 */
	@Test(dataProvider = "eventDescMetadataInput", dataProviderClass = DemandSideDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "sanity" })
	public void testMetaDataVerificationForLSEventDescription(
			final String userName, final String password,
			final String programeName, final String eventName,
			final String eventDescription) throws ParseException {

		demandSideManagement.testMetaDataVerificationForLSEventDescription(
				programeName, eventName, eventDescription);
	}

	/**
	 * testEventStartDateAndDuration This method verifies whether the event
	 * start time and duration are displayed in UI based corresponding to data
	 * in database.
	 * 
	 * @param username
	 *            the username
	 * @param password
	 *            the password
	 * @param programName
	 *            the program name
	 * @throws ParseException
	 *             the parse exception
	 */
	@Test(dataProvider = "eventStartDate", dataProviderClass = DemandSideDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "sanity" })
	public void testEventStartDateAndDuration(final String username,
			final String password, final String programName)
			throws ParseException {

		eventDetails = new HashMap<String, Object>();

		eventDetails = demandSideManagement.initiateEventCreation(programName,
				"60", "DRAFT", false);

		if (!eventDetails.isEmpty()) {
			WaitUtil.waitUntil(30000);

			formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
			WaitUtil.waitUntil(150000);
			ecpCoreLSEvent = lsProgramEventDao.listByEventName(
					eventDetails.get("eventName").toString(), "DRAFT").get(0);

			DriverConfig.setLogString("Event Name saved in DB as :"
					+ ecpCoreLSEvent.getName(), true);
			logger.info("Event start Date saved in DB as (UTC time) :"
					+ formatter.format(ecpCoreLSEvent.getStartDate().getTime()));
			logger.info(
					"Event End Date saved in DB as (UTC time):"
							+ formatter.format(ecpCoreLSEvent.getEndDate()
									.getTime()), true);
			logger.info(
					"The Event TimeZone is:" + ecpCoreLSEvent.getTimeZone(),
					true);

			Date d1 = ecpCoreLSEvent.getEndDate().getTime();
			Date d2 = ecpCoreLSEvent.getStartDate().getTime();

			long difference = (d1.getTime() / (60 * 1000))
					- (d2.getTime() / (60 * 1000));

			DriverConfig
					.setLogString(
							"verify if Event Duration was correctly updated in Database.",
							true);
			Assert.assertTrue(
					Long.toString(difference).equalsIgnoreCase(
							eventDetails.get("eventDuration").toString()),
					"Event duration is different");

			DriverConfig
					.setLogString(
							"verify if Event Start Date was saved correctly in database.",
							true);
			logger.info(
					"Converted UTC time(start time) :"
							+ demandSideManagement.getUTCTime(
									ecpCoreLSEvent.getTimeZone(),
									(Calendar) eventDetails.get("eventDate"))
							+ ". UTC Time from DB: "
							+ formatter.format(
									ecpCoreLSEvent.getStartDate().getTime())
									.trim(), true);
			Assert.assertTrue(
					demandSideManagement
							.getUTCTime(ecpCoreLSEvent.getTimeZone(),
									(Calendar) eventDetails.get("eventDate"))
							.trim()
							.equalsIgnoreCase(
									formatter.format(
											ecpCoreLSEvent.getStartDate()
													.getTime()).trim()),
					"Time is different");
		}
	}

	/**
	 * testSaveEvent This method is to verify that if the save draft function is
	 * working fine.
	 * 
	 * @param username
	 *            the username
	 * @param password
	 *            the password
	 * @param programName
	 *            the program name
	 * @throws ParseException
	 *             the parse exception
	 */
	@Test(dataProvider = "eventSave", dataProviderClass = DemandSideDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "sanity" })
	public void testSaveEvent(final String username, final String password,
			final String programName) throws ParseException {

		eventDetails = new HashMap<String, Object>();

		eventDetails = demandSideManagement.initiateEventCreation(programName,
				"60", "DRAFT", false);
		if (!eventDetails.isEmpty()) {
			WaitUtil.waitUntil(10000);

			ecpCoreLSEvent = lsProgramEventDao.listByEventName(
					eventDetails.get("eventName").toString(), "DRAFT").get(0);
			DriverConfig.setLogString(
					"check if Event Name '" + ecpCoreLSEvent.getName()
							+ "' is saved in database as draft", true);
			Assert.assertTrue(ecpCoreLSEvent.getStatus().toString()
					.equalsIgnoreCase("DRAFT"), "Event status is different");
		}
	}

	/**
	 * testSaveAndScheduleEvent This method is verify if the save schedule
	 * function is working fine.
	 * 
	 * @param username
	 *            the username
	 * @param password
	 *            the password
	 * @param programName
	 *            the program name
	 * @throws ParseException
	 *             the parse exception
	 */
	@Test(dataProvider = "eventSchedule", dataProviderClass = DemandSideDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "blocked" })
	public void testSaveAndScheduleEvent(final String username,
			final String password, final String programName)
			throws ParseException {

		eventDetails = new HashMap<String, Object>();

		eventDetails = demandSideManagement.initiateEventCreation(programName,
				"60", Status.SCHEDULED.toString(), false);
		WaitUtil.waitUntil(150000);
		formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
		ecpCoreLSEvent = lsProgramEventDao.listByEventName(
				eventDetails.get("eventName").toString(),
				Status.SCHEDULED.toString()).get(0);
		DriverConfig.setLogString("check the event status is 'scheduled'.",
				true);
		Assert.assertTrue(ecpCoreLSEvent.getStatus().equals(Status.SCHEDULED),
				"Event status is different");
	}

	/**
	 * testEditEventAndSaveDraft This test method verifies that edit and save to
	 * draft event is working fine. The event status should be draft.
	 * 
	 * @param username
	 *            the username
	 * @param password
	 *            the password
	 * @param programName
	 *            the program name
	 * @throws ParseException
	 *             the parse exception
	 */
	@Test(dataProvider = "editEventDraft", dataProviderClass = DemandSideDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "sanity" })
	public void testEditEventAndSaveDraft(final String username,
			final String password, final String programName)
			throws ParseException {

		editDraftEvent(programName, Status.DRAFT.toString());
	}

	/**
	 * testEditEventAndSaveSchedule This test method verifies that edit and save
	 * schedule event is working fine. The event status should be scheduled.
	 * 
	 * @param username
	 *            the username
	 * @param password
	 *            the password
	 * @param programName
	 *            the program name
	 * @throws ParseException
	 *             the parse exception
	 */
	@Test(dataProvider = "editEventScheduled", dataProviderClass = DemandSideDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "blocked" })
	public void testEditEventAndSaveSchedule(final String username,
			final String password, final String programName)
			throws ParseException {

		editDraftEvent(programName, Status.SCHEDULED.toString());
	}

	/**
	 * Test edit event before it starts.
	 * 
	 * @param username
	 *            the username
	 * @param password
	 *            the password
	 * @param programName
	 *            the program name
	 * @throws ParseException
	 *             the parse exception
	 */
	@Test(dataProvider = "editEventBeforeStart", dataProviderClass = DemandSideDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "blocked" })
	public void testEditEventBeforeItStarts(final String username,
			final String password, final String programName)
			throws ParseException {

		eventDetails = new HashMap<String, Object>();
		eventDetails = demandSideManagement.initiateEventCreation(programName,
				"60", Status.SCHEDULED.toString(), false);
		demandSideManagement.selectEventLink(eventDetails.get("eventName")
				.toString());
		demandSideManagement.tryEditingScheduledEvent();
	}

	/**
	 * testEventMetaDataVerification Test method to verify Event meta data like
	 * like ECP_Core, Event Description,Start & End date, Modified By, Last
	 * Modified date and Status.
	 * 
	 * @param username
	 *            the username
	 * @param password
	 *            the password
	 * @param programName
	 *            the program name
	 * @throws ParseException
	 *             the parse exception
	 */
	@Test(dataProvider = "eventMetaData", dataProviderClass = DemandSideDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "sanity" })
	public void testEventMetaDataVerification(final String username,
			final String password, final String programName)
			throws ParseException {

		eventDetails = new HashMap<String, Object>();

		eventDetails = demandSideManagement.initiateEventCreation(programName,
				"60", Status.DRAFT.toString(), false);
		WaitUtil.waitUntil(15000);
		ecpCoreLSEvent = lsProgramEventDao.listByEventName(
				eventDetails.get("eventName").toString(),
				Status.DRAFT.toString()).get(0);
		DriverConfig.setLogString("Verify last Modified By.", true);
		Assert.assertTrue(
				ecpCoreLSEvent.getLastModifiedBy().equalsIgnoreCase(username),
				"User is different");

		DriverConfig.setLogString("Verify Event Description.", true);
		Assert.assertTrue(
				ecpCoreLSEvent.getDescription().equalsIgnoreCase(
						eventDetails.get("eventDesc").toString()),
				"Event description is different");

		SimpleDateFormat UTCDateFormat = new SimpleDateFormat(dateFormat);
		UTCDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

		SimpleDateFormat dateFormatLocal = new SimpleDateFormat(dateFormat);
		Date UTCDate = dateFormatLocal
				.parse(UTCDateFormat.format(((Calendar) eventDetails
						.get("lastUpdated")).getTime()));

		long minutes = differenceInMinutes(ecpCoreLSEvent.getLastUpdated(),
				UTCDate);
		DriverConfig.setLogString("Verif Last Updated Time.", true);
		Assert.assertTrue(minutes <= 2, "Minute is greater than 2");

		DriverConfig.setLogString("Verify ECPCoreID.", true);
		dbEcpCoreLSProgram = ecpCoreLSEvent.getEcpCoreLSProgram();
		Assert.assertTrue(
				dbEcpCoreLSProgram.getEcpCoreId().equals(
						ecpCoreLSEvent.getEcpCoreId()),
				"ECPCoreID is different");
	}

	/**
	 * testDraftEventIsNotScheduled Test method to verify that draft events is
	 * not scheduled.
	 * 
	 * @param username
	 *            the username
	 * @param password
	 *            the password
	 * @throws ParseException
	 *             the parse exception
	 */
	@Test(dataProvider = "draftEventNotScheduled", dataProviderClass = DemandSideDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "validation" })
	public void testDraftEventIsNotScheduled(final String username,
			final String password) throws ParseException {

		DriverConfig
				.setLogString(
						"Assure there is no thermostat events in database for draft events with start date as past date.",
						true);
		Calendar curDate = Calendar.getInstance();
		EcpCoreLSEvent event = lsProgramEventDao.listByStartDateAndStatus(
				curDate, Status.DRAFT);
		if (thermostatEventDao.findThermostatEventByEventID(
				(double) event.getEcpCoreId()).size() == 0) {
			Assert.assertTrue(
					thermostatEventDao.findThermostatEventByEventID(
							(double) event.getEcpCoreId()).size() == 0,
					"Records in DB for Draft event");
		}
	}

	/**
	 * testLeadTimeToLSPrecool This test method verifies the lead time need for
	 * event creation with precool option. The default lead time required for
	 * precool event is 4hours.
	 * 
	 * @param username
	 *            the username
	 * @param password
	 *            the password
	 * @param programName
	 *            the program name
	 * @throws ParseException
	 *             the parse exception
	 */
	@Test(dataProvider = "leadTimeLSPrecool", dataProviderClass = DemandSideDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "blocked" })
	public void testLeadTimeToLSPrecool(final String username,
			final String password, final String programName)
			throws ParseException {

		String DATEFORMAT = "MM/dd/yyyy hh:mm a";
		SimpleDateFormat localDateFormat = new SimpleDateFormat(DATEFORMAT);
		DriverConfig
				.setLogString("get ls program details from Database.", true);
		dbEcpCoreLSProgram = lsProgramDao.getEcpCoreLSProgram(
				demandSideManagement.getProgramIdFromUI(programName)).get(0);
		demandSideManagement.verifyStartTimeTooEarlierError(dbEcpCoreLSProgram);

		Calendar eventDate = Calendar.getInstance();
		eventDate.setTimeZone(TimeZone.getTimeZone(dbEcpCoreLSProgram
				.getTimeZone()));
		eventDate.add(Calendar.HOUR, +4);
		eventDate.add(Calendar.MINUTE, +15);
		WaitUtil.waitUntil(5000);
		logger.info("event date:" + localDateFormat.format(eventDate.getTime()));
		logger.info("Minimum Start hour :"
				+ dbEcpCoreLSProgram.getMinStartHour());
		logger.info("Maximum End hour :" + dbEcpCoreLSProgram.getMaxEndHour());

		String strEventStartDate = demandSideManagement
				.getValidEventStartTime(eventDate,
						dbEcpCoreLSProgram.getTimeZone(), dbEcpCoreLSProgram);
		logger.info("Valid start time strEventStartDate: " + strEventStartDate);
		String inputEventName = demandSideManagement.createEvent(false,
				strEventStartDate, "60", Status.DRAFT.toString(), true, null);
		largeWait();

		DriverConfig.setLogString(
				"check if Event saved succesfully in Database.", true);
		if (lsProgramEventDao.listByEventName(inputEventName,
				Status.DRAFT.toString().toUpperCase()).size() > 0)
			DriverConfig.setLogString("Event saved succesfully in Database.",
					true);
		DriverConfig.setLogString(
				"check if Event saved succesfully in Database as draft.", true);
		Assert.assertTrue(
				lsProgramEventDao.listByEventName(inputEventName,
						Status.DRAFT.toString()).size() > 0,
				"Event is not saved in Database as draft");

	}

	/**
	 * Testcancel event in draft.
	 * 
	 * @param username
	 *            the username
	 * @param password
	 *            the password
	 * @param programName
	 *            the program name
	 * @throws ParseException
	 *             the parse exception
	 * @throws InterruptedException
	 *             the interrupted exception
	 */
	@Test(dataProvider = "cancelEventDraft", dataProviderClass = DemandSideDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "sanity" })
	public void testcancelEventInDraft(final String username,
			final String password, final String programName)
			throws ParseException, InterruptedException {

		eventDetails = demandSideManagement.initiateEventCreation(programName,
				"60", Status.DRAFT.toString(), false);
		WaitUtil.waitUntil(10000);
		ecpCoreLSEvent = lsProgramEventDao.listByEventName(
				eventDetails.get("eventName").toString(),
				Status.DRAFT.toString()).get(0);
		logger.info("Event Name saved in DB as :" + ecpCoreLSEvent.getName(),
				true);
		logger.info("The Event Status is:" + ecpCoreLSEvent.getStatus());
		logger.info("check if event status is saved as draft.", true);
		Assert.assertTrue(ecpCoreLSEvent.getStatus().toString()
				.equalsIgnoreCase(Status.DRAFT.toString()),
				"Event status is different");

		demandSideManagement.selectEventLink(ecpCoreLSEvent.getName());
		demandSideManagement.testcancelEvent(Status.DRAFT.toString());

		WaitUtil.waitUntil(5000);
		ecpCoreLSEvent = lsProgramEventDao.listByEventName(
				eventDetails.get("eventName").toString(),
				Status.DELETED.toString()).get(0);

		DriverConfig.setLogString("get event status from database.", true);
		if (lsProgramEventDao.listByEventName(ecpCoreLSEvent.getName(),
				Status.DELETED.toString()).size() > 0) {
			logger.info("Event Deleted successfully.");
		} else {
			logger.info("Event not Deleted.");
		}
		DriverConfig.setLogString(
				"check if event status is updated as deleted.", true);
		Assert.assertTrue(
				lsProgramEventDao.listByEventName(ecpCoreLSEvent.getName(),
						Status.DELETED.toString()).size() > 0,
				"Failed to update event status as deleted");

	}

	/**
	 * Testcancel event scheduled.
	 * 
	 * @param username
	 *            the username
	 * @param password
	 *            the password
	 * @param programName
	 *            the program name
	 * @throws ParseException
	 *             the parse exception
	 * @throws InterruptedException
	 *             the interrupted exception
	 */
	@Test(dataProvider = "cancelEventScheduled", dataProviderClass = DemandSideDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "sanity" })
	public void testcancelEventScheduled(final String username,
			final String password, final String programName)
			throws ParseException, InterruptedException {

		DriverConfig.setLogString("get event details from DB.", true);

		eventDetails = demandSideManagement.initiateEventCreation(programName,
				"60", Status.SCHEDULED.toString(), false);
		WaitUtil.waitUntil(10000);
		ecpCoreLSEvent = lsProgramEventDao.listByEventName(
				eventDetails.get("eventName").toString(),
				Status.SCHEDULED.toString()).get(0);
		logger.info("Event Name saved in DB as :" + ecpCoreLSEvent.getName());
		logger.info("The Event Status is:" + ecpCoreLSEvent.getStatus());
		logger.info("check if the event status was verified as "
				+ Status.SCHEDULED.toString());
		Assert.assertTrue(ecpCoreLSEvent.getStatus().toString()
				.equalsIgnoreCase(Status.SCHEDULED.toString()),
				"Event status is different");

		demandSideManagement.selectEventLink(ecpCoreLSEvent.getName());
		demandSideManagement.testcancelEvent(Status.SCHEDULED.toString());

		WaitUtil.waitUntil(30000);
		if (lsProgramEventDao.listByEventName(ecpCoreLSEvent.getName(),
				Status.DELETED.toString()).size() > 0) {
			logger.info("Event Deleted successfully.");
		} else {
			logger.info("Event not Deleted.");
		}
		DriverConfig.setLogString("check if event status is cancelled.", true);
		Assert.assertTrue(
				lsProgramEventDao.listByEventName(ecpCoreLSEvent.getName(),
						Status.CANCELED.toString()).size() > 0,
				"Event status is different");

	}

	/**
	 * Test completed event report.
	 * 
	 * @param username
	 *            the username
	 * @param password
	 *            the password
	 * @param programName
	 *            the program name
	 * @throws ParseException
	 *             the parse exception
	 */
	@Test(dataProvider = "completedEventReport", dataProviderClass = DemandSideDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "blocked" })
	public void testCompletedEventReport(final String username,
			final String password, final String programName)
			throws ParseException {

		LoadShapingEventReport loadShapingEventReport = new LoadShapingEventReport();
		loadShapingEventReport = demandSideManagement
				.completedEventStatusView(programName);
		WaitUtil.waitUntil(2000);
		verifyEventReports(loadShapingEventReport);
	}

	/**
	 * Test event list pagination if list less than10.
	 * 
	 * @param username
	 *            the username
	 * @param password
	 *            the password
	 * @param programName
	 *            the program name
	 * @throws ParseException
	 *             the parse exception
	 */
	@Test(dataProvider = "eventListLessThan10", dataProviderClass = DemandSideDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "blocked" })
	public void testEventListPaginationIfListLessThan10(final String username,
			final String password, final String programName)
			throws ParseException {

		demandSideManagement.testEventPaginationLimit(programName, "60",
				Status.DRAFT.toString(), false, 10);

	}

	/**
	 * Test event list pagination if list greater than10.
	 * 
	 * @param username
	 *            the username
	 * @param password
	 *            the password
	 * @param programName
	 *            the program name
	 * @throws ParseException
	 *             the parse exception
	 */
	@Test(dataProvider = "eventListMoreThan10", dataProviderClass = DemandSideDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "blocked" })
	public void testEventListPaginationIfListGreaterThan10(
			final String username, final String password,
			final String programName) throws ParseException {

		demandSideManagement.testEventPaginationLimit(programName, "60",
				Status.DRAFT.toString(), false, 11);

	}

	/**
	 * verifyProgramProperties Verify that the program properties listed in the
	 * section of the insite is listed correctly.
	 * 
	 * @param username
	 *            the username
	 * @param password
	 *            the password
	 * @param programName
	 *            the program name
	 * @throws ParseException
	 *             the parse exception
	 */
	@Test(dataProvider = "programProperties", dataProviderClass = DemandSideDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "validation" })
	public void verifyProgramProperties(String username, String password,
			String programName) throws ParseException {

		programPropertiesMap = demandSideManagement
				.getSpecificProgramDetails(programName);
		programId = Integer.parseInt(programPropertiesMap.get("ProgramId")
				.toString());
		dbEcpCoreLSProgram = lsProgramDao.getEcpCoreLSProgram(programId).get(0);
		demandSideManagement.verifyDisplayedProperties(programPropertiesMap,
				dbEcpCoreLSProgram, "");

	}

	/**
	 * testCancelEvent This method verifies that clicking cancel button while
	 * creating new event doesn't saves the event in database.
	 * 
	 * @param username
	 *            the username
	 * @param password
	 *            the password
	 * @param programName
	 *            the program name
	 * @throws ParseException
	 *             the parse exception
	 */
	@Test(dataProvider = "cancelEvent", dataProviderClass = DemandSideDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "ui" })
	public void testCancelEvent(final String username, final String password,
			final String programName) throws ParseException {

		eventDetails = new HashMap<String, Object>();

		eventDetails = demandSideManagement.initiateEventCreation(programName,
				"60", Status.CANCELED.toString(), false);
		WaitUtil.waitUntil(10000);
		DriverConfig.setLogString("check if event status is cancelled.", true);
		Assert.assertTrue(
				lsProgramEventDao.listByEventName(
						eventDetails.get("eventName").toString(),
						Status.CANCELED.toString()).size() == 0,
				"Event status is different");

	}

	/**
	 * verifyEventTimeSetForPastTime Verify that the LS Event creation does not
	 * accept past date time.
	 * 
	 * @param username
	 *            the username
	 * @param password
	 *            the password
	 * @throws ParseException
	 *             the parse exception
	 */
	@Test(dataProvider = "validLogin", dataProviderClass = DemandSideDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "validation" })
	public void testEventTimeSetForPastTime(String username, String password)
			throws ParseException {

		checkEventTimeSetForPastTime();

	}

	/**
	 * testEventStatusFilter This test method verifies that the event status
	 * filter shows up list of events with selected event status.
	 * 
	 * @param username
	 *            the username
	 * @param password
	 *            the password
	 * @param programName
	 *            the program name
	 * @throws ParseException
	 *             the parse exception
	 */
	@Test(dataProvider = "eventStatusFilter", dataProviderClass = DemandSideDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "ui" })
	public void testEventStatusFilter(final String username,
			final String password, final String programName)
			throws ParseException {

		demandSideManagement.testEventStatusFilter(programName);

	}

	/**
	 * Test events and groups are accessible.
	 * 
	 * @param username
	 *            the username
	 * @param password
	 *            the password
	 * @throws ParseException
	 *             the parse exception
	 */
	@Test(dataProvider = "validLogin", dataProviderClass = DemandSideDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "ui" })
	public void testEventsAndGroupsAreAccessible(final String username,
			final String password) throws ParseException {

		programPropertiesMap = demandSideManagement
				.getFirstDisplayedProgramDetails();
		Assert.assertTrue(demandSideManagement.checkEventsAndGroupsLink(),
				"Events or group link is not available");

	}

	/**
	 * Test all programs are clickable.
	 * 
	 * @param username
	 *            the username
	 * @param password
	 *            the password
	 * @throws ParseException
	 *             the parse exception
	 */
	@Test(dataProvider = "validLogin", dataProviderClass = DemandSideDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "ui" })
	public void testAllProgramsAreClickable(final String username,
			final String password) throws ParseException {

		List<String> programNames = demandSideManagement.collectProgramNames();
		demandSideManagement.checkIfProgramsAccesible(programNames);
	}

	/**
	 * Test all programs displayed properly.
	 * 
	 * @param username
	 *            the username
	 * @param password
	 *            the password
	 * @throws ParseException
	 *             the parse exception
	 */
	@Test(dataProvider = "validLogin", dataProviderClass = DemandSideDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "validation" })
	public void testAllProgramsDisplayedProperly(final String username,
			final String password) throws ParseException {

		List<EcpCoreLSProgram> programNamesFromDB = lsProgramDao
				.getAllEcpCoreLSProgram();
		List<String> programNames = demandSideManagement
				.iterateProgramPageList();
		for (EcpCoreLSProgram lsProgram : programNamesFromDB) {
			if (programNames.contains(lsProgram.getName()))
				Assert.assertTrue(programNames.contains(lsProgram.getName()),
						"Program name is not available");
		}

	}

	/**
	 * Test group list displayed are valid.
	 * 
	 * @param username
	 *            the username
	 * @param password
	 *            the password
	 * @param programName
	 *            the program name
	 * @throws ParseException
	 *             the parse exception
	 */
	@Test(dataProvider = "groupList", dataProviderClass = DemandSideDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "validation" })
	public void testGroupListDisplayedAreValid(String username,
			String password, String programName) throws ParseException {

		int programid = 0;
		programid = demandSideManagement.getProgramIdFromUI(programName);
		demandSideManagement.testAvailableZipcodesAreValid(programid,
				programName);
	}

	/**
	 * Ecofactor logo link.
	 * 
	 * @param userId
	 *            the user id
	 * @param password
	 *            the password
	 */
	@Test(dataProvider = "validLogin", dataProviderClass = DemandSideDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "smoke" })
	public void headerLogoLink(String userId, String password) {

		demandSideManagement.isLogoDisplayed();
	}

	/**
	 * Test the welcome text for the logged in user in the insite home page.
	 * 
	 * @param userId
	 *            the user id
	 * @param password
	 *            the password
	 */
	@Test(dataProvider = "validLogin", dataProviderClass = DemandSideDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "smoke" })
	public void welcomeText(String userId, String password) {

		demandSideManagement.verifyWelcomeText(userId);
	}

	/**
	 * Test the account link in the insite home page.
	 * 
	 * @param userId
	 *            the user id
	 * @param password
	 *            the password
	 */
	@Test(dataProvider = "validLogin", dataProviderClass = DemandSideDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "smoke" })
	public void supportLink(String userId, String password) {

		demandSideManagement.clickSupport();
	}

	/**
	 * Installation link.
	 * 
	 * @param userId
	 *            the user id
	 * @param password
	 *            the password
	 */
	@Test(dataProvider = "validLogin", dataProviderClass = DemandSideDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "smoke" })
	public void installationLink(String userId, String password) {

		demandSideManagement.clickInstallation();
	}

	/**
	 * Installation schedule link.
	 * 
	 * @param userId
	 *            the user id
	 * @param password
	 *            the password
	 */
	@Test(dataProvider = "validLogin", dataProviderClass = DemandSideDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "smoke" })
	public void installationScheduleLink(String userId, String password) {

		demandSideManagement.clickScheduling();
	}

	/**
	 * Installation pre config link.
	 * 
	 * @param userId
	 *            the user id
	 * @param password
	 *            the password
	 */
	@Test(dataProvider = "validLogin", dataProviderClass = DemandSideDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "smoke" })
	public void installationPreConfigLink(String userId, String password) {

		demandSideManagement.clickPreConfiguration();
	}

	/**
	 * User management link.
	 * 
	 * @param userId
	 *            the user id
	 * @param password
	 *            the password
	 */
	@Test(dataProvider = "validLogin", dataProviderClass = DemandSideDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "smoke" })
	public void userManagementLink(String userId, String password) {

		demandSideManagement.clickUserManagement();
	}

	/**
	 * Role management link.
	 * 
	 * @param userId
	 *            the user id
	 * @param password
	 *            the password
	 */
	@Test(dataProvider = "validLogin", dataProviderClass = DemandSideDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "smoke" })
	public void roleManagementLink(String userId, String password) {

		demandSideManagement.clickRoleManagement();
	}

	/**
	 * Demand side management link.
	 * 
	 * @param userId
	 *            the user id
	 * @param password
	 *            the password
	 */
	@Test(dataProvider = "validLogin", dataProviderClass = DemandSideDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "smoke" })
	public void demandSideManagementLink(String userId, String password) {

		demandSideManagement.clickDemandSideManagement();
	}

	/**
	 * Test the user logout from a insite authenticated page.
	 * 
	 * @param userId
	 *            the user id
	 * @param password
	 *            the password
	 */
	@Test(dataProvider = "validLogin", dataProviderClass = DemandSideDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "smoke" })
	public void logout(String userId, String password) {

		demandSideManagement.logout();
	}

	/**
	 * EditDraftEvent This Method creates new event, checks if the event has
	 * been saved in database and verify the edited data is saved correctly in
	 * database.
	 * 
	 * @param programName
	 *            the program name
	 * @param mode
	 *            the mode
	 * @throws ParseException
	 *             the parse exception
	 */
	public void editDraftEvent(final String programName, final String mode)
			throws ParseException {

		eventDetails = new HashMap<String, Object>();
		eventDetails = demandSideManagement.initiateEventCreation(programName,
				"60", Status.DRAFT.toString(), false);
		WaitUtil.waitUntil(120000);
		demandSideManagement.filterStatus("DRAFT");
		demandSideManagement.selectEventLink(eventDetails.get("eventName")
				.toString());
		updatedEventDetails = demandSideManagement.editEvent(mode);

		WaitUtil.waitUntil(20000);
		ecpCoreLSEvent = lsProgramEventDao.listByEventName(
				eventDetails.get("eventName").toString(), mode).get(0);

		DriverConfig.setLogString("Verify edited data from database.", true);
		if (ecpCoreLSEvent != null) {
			DriverConfig.setLogString("check event description.", true);
			Assert.assertTrue(updatedEventDetails.get("eventDesc").toString()
					.equalsIgnoreCase(ecpCoreLSEvent.getDescription()),
					"Event description is different");
			DriverConfig.setLogString("Mode " + mode, true);
			if (mode.equalsIgnoreCase(Status.SCHEDULED.toString())) {
				DriverConfig.setLogString(
						"check if event status is scheduled.", true);
				Assert.assertTrue(
						ecpCoreLSEvent.getStatus().equals(Status.SCHEDULED),
						"Event status is different");
			} else {
				DriverConfig.setLogString("check if event status is draft.",
						true);
				Assert.assertTrue(ecpCoreLSEvent.getStatus().toString()
						.equalsIgnoreCase(Status.DRAFT.toString()),
						"Event status is different");
			}

		}
	}

	/**
	 * verifyEventTimeSetForPastTime Method to validate event creation with past
	 * date. The event should not be created and there should be a validation
	 * message thrown.
	 * 
	 * @throws ParseException
	 *             the parse exception
	 */
	public void checkEventTimeSetForPastTime() throws ParseException {

		programPropertiesMap = demandSideManagement
				.getFirstDisplayedProgramDetails();
		programId = Integer.parseInt(programPropertiesMap.get("ProgramId")
				.toString());
		formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
		dbEcpCoreLSProgram = lsProgramDao.getEcpCoreLSProgram(programId).get(0);
		demandSideManagement
				.checkErrorMsgForEventCreationWithPastStartDate(dbEcpCoreLSProgram);
	}

	/**
	 * Verify event reports.
	 * 
	 * @param loadShapingEventReport
	 *            the load shaping event report
	 */
	private void verifyEventReports(
			LoadShapingEventReport loadShapingEventReport) {

		EcpCoreLSEvent ecpCoreLSEvent;
		ecpCoreLSEvent = lsProgramEventDao.listByEventName(
				loadShapingEventReport.getLoadShapingEvent().getName(),
				loadShapingEventReport.getLoadShapingEvent().getStatus()
						.toString()).get(0);
		LoadShapingEventReport loadShapingEventReportDb = ecpCoreLSEvent
				.getLoadShapingEventReports().get(0);
		DriverConfig.setLogString("check if event name displayed correctly",
				true);
		Assert.assertTrue(
				loadShapingEventReportDb
						.getLoadShapingEvent()
						.getName()
						.equalsIgnoreCase(
								loadShapingEventReport.getLoadShapingEvent()
										.getName()), "Event name is different");
		DriverConfig.setLogString("check if program name displayed correctly",
				true);
		Assert.assertTrue(
				loadShapingEventReportDb
						.getLoadShapingEvent()
						.getEcpCoreLSProgram()
						.getName()
						.equalsIgnoreCase(
								loadShapingEventReport.getLoadShapingEvent()
										.getEcpCoreLSProgram().getName()),
				"Program name is different");
		DriverConfig.setLogString(
				"check if no of expected locations displayed correctly", true);
		Assert.assertTrue(loadShapingEventReportDb.getNumExpectedLocations()
				.equals(loadShapingEventReport.getNumExpectedLocations()),
				"Expected locations are different");
		DriverConfig.setLogString(
				"check if no of actual locations displayed correctly", true);
		Assert.assertTrue(loadShapingEventReportDb.getNumActualLocations()
				.equals(loadShapingEventReport.getNumActualLocations()),
				"Actual locations are different");
	}
}
