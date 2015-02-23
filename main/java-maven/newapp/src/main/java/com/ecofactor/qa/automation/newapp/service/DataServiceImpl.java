/*
 * DataServiceImpl.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.newapp.service;

import static com.ecofactor.qa.automation.util.WaitUtil.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import com.ecofactor.common.pojo.Algorithm;
import com.ecofactor.common.pojo.Status;
import com.ecofactor.common.pojo.Thermostat;
import com.ecofactor.common.pojo.ThermostatAlgorithm;
import com.ecofactor.common.pojo.ThermostatAlgorithmController;
import com.ecofactor.common.pojo.ThermostatJob;
import com.ecofactor.common.pojo.ThermostatProgramLog;
import com.ecofactor.common.pojo.timeseries.PartitionedThermostatEvent;
import com.ecofactor.common.pojo.timeseries.PartitionedThermostatRangeData;

import com.ecofactor.qa.automation.dao.AlgorithmDao;
import com.ecofactor.qa.automation.dao.ThermostatAlgoControlDao;
import com.ecofactor.qa.automation.dao.ThermostatAlgorithmDao;
import com.ecofactor.qa.automation.dao.ThermostatDao;
import com.ecofactor.qa.automation.dao.ThermostatEventDao;
import com.ecofactor.qa.automation.dao.ThermostatJobDao;
import com.ecofactor.qa.automation.dao.ThermostatProgramLogDao;
import com.ecofactor.qa.automation.dao.ThermostatRangeDataDao;
import com.ecofactor.qa.automation.dao.util.DataUtil;
import com.ecofactor.qa.automation.util.DateUtil;
import com.ecofactor.qa.automation.util.DriverConfig;
import com.ecofactor.qa.automation.util.mock.MockJobData;
import com.google.inject.Inject;

/**
 * The Class SPODataServiceImpl.
 * 
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class DataServiceImpl extends BaseDataServiceImpl implements DataService {

	/** The logger. */
	private static Logger LOGGER = LoggerFactory
			.getLogger(DataServiceImpl.class);

	/** The tstat prog log dao. */
	@Inject
	protected ThermostatProgramLogDao tstatProgLogDao;

	/** The algorithm dao. */
	@Inject
	private AlgorithmDao algorithmDao;

	@Inject
	private ThermostatAlgorithmDao thAlgorithmDao;

	/** The base temp. */
	private Double baseTemp;

	/** The test name. */
	private String testName;

	/** The thermostat algo control dao. */
	@Inject
	private ThermostatAlgoControlDao thermostatAlgoControlDao;

	/** The thermostat dao. */
	@Inject
	private ThermostatDao thermostatDao;

	/** The thermostat job dao. */
	@Inject
	private ThermostatJobDao thermostatJobDao;

	/** The mock job data builder. */
	@Inject
	private com.ecofactor.qa.automation.newapp.mock.MockJobDataBuilder mockJobDataBuilder;

	/** The thermostat event dao. */
	@Inject
	private ThermostatEventDao thermostatEventDao;

	/** The thermostat range data dao. */
	@Inject
	private ThermostatRangeDataDao thermostatRangeDataDao;

	/**
	 * Clear.
	 * 
	 * @see com.ecofactor.qa.automation.algorithm.service.DataService#clear()
	 */
	@Override
	public void clear() {

	}

	/**
	 * Find active algorithm id.
	 * 
	 * @param thermostatId
	 *            the thermostat id
	 * @return the int
	 * @see com.ecofactor.qa.automation.algorithm.service.DataService#findActiveAlgorithmId(java.lang.Integer)
	 */
	@Override
	public int findActiveAlgorithmId(Integer thermostatId) {

		DriverConfig.setLogString("Get active algorithm Id", true);
		int activeAlgo = 0;
		try {
			PartitionedThermostatEvent thEvent = thermostatEventDao
					.findLatestByThermostat(thermostatId);
			if (thEvent != null) {
				activeAlgo = thEvent.getAlgorithmId();
			}
		} catch (Exception e) {
			DriverConfig.setLogString("Error in getting active algorithm Id:"
					+ e.getMessage(), false);
		}

		return activeAlgo;
	}

	/**
	 * Find bythermostat id.
	 * 
	 * @param thermostatId
	 *            the thermostat id
	 * @return the thermostat
	 * @see com.ecofactor.qa.automation.algorithm.service.DataService#findBythermostatId(java.lang.Integer)
	 */
	@Override
	public Thermostat findBythermostatId(Integer thermostatId) {

		Thermostat thermostat = thermostatDao.findByThermostatId(String
				.valueOf(thermostatId));
		return thermostat;
	}

	/**
	 * Find latest thermostat event.
	 * 
	 * @param thermostatId
	 *            the thermostat id
	 * @return the thermostat event
	 * @see com.ecofactor.qa.automation.algorithm.service.DataService#findLatestThermostatEvent(java.lang.Integer)
	 */
	@Override
	public PartitionedThermostatEvent findLatestThermostatEvent(
			Integer thermostatId) {

		PartitionedThermostatEvent thermostatEvent = thermostatEventDao
				.findLatestByThermostat(thermostatId);
		return thermostatEvent;
	}

	/**
	 * Gets the base temp.
	 * 
	 * @return the base temp
	 * @see com.ecofactor.qa.automation.algorithm.service.DataService#getBaseTemp()
	 */
	@Override
	public Double getBaseTemp() {

		return baseTemp;
	}

	/**
	 * Gets the current base temp.
	 * 
	 * @param thermostatId
	 *            the thermostat id
	 * @param mode
	 *            the mode
	 * @return the current base temp
	 * @see com.ecofactor.qa.automation.algorithm.service.DataService#getCurrentBaseTemp(java.lang.Integer,
	 *      java.lang.String)
	 */
	public double getCurrentBaseTemp(Integer thermostatId, String mode) {

		double baseTemp = 0;
		List<ThermostatProgramLog> programList = tstatProgLogDao
				.listUTCCurrentDayLog(thermostatId, DateUtil.getUTCDayOfWeek());
		for (ThermostatProgramLog tstatProgramLog : programList) {

			Date startTimeUTC = tstatProgramLog.getStartTimeUTC();
			Date endTimeUTC = tstatProgramLog.getEndTimeUTC();

			DateTime startTimeJoda = new DateTime(startTimeUTC);
			DateTime endTimeJoda = new DateTime(endTimeUTC);

			Calendar startCalendar = DateUtil
					.convertTimeToUTCCalendar(startTimeJoda);
			Calendar endCalendar = DateUtil
					.convertTimeToUTCCalendar(endTimeJoda);
			Calendar utcCalendar = DateUtil.getUTCCalendar();

			if (!endCalendar.before(utcCalendar)
					&& !startCalendar.after(utcCalendar)) {

				if (mode.equalsIgnoreCase("Cool")) {
					baseTemp = tstatProgramLog.getCoolSetting();
				} else if (mode.equalsIgnoreCase("Heat")) {
					baseTemp = tstatProgramLog.getHeatSetting();
				}
				break;
			}
		}

		DriverConfig.setLogString("Base Temp from Program Log table : "
				+ baseTemp, true);
		LOGGER.debug("Base Temp from Program Log table : " + baseTemp, true);
		return baseTemp;
	}

	/**
	 * Gets the job data.
	 * 
	 * @param thermostatId
	 *            the thermostat id
	 * @return the job data
	 * @see com.ecofactor.qa.automation.algorithm.service.SPODataService#getJobData(java.lang.Integer)
	 */
	@Override
	public JSONArray getJobData(Integer thermostatId) {

		JSONArray jobData = null;
		DriverConfig.setLogString("Get json Array for Thermostat Id : "
				+ thermostatId, true);
		ThermostatJob thermostatJob = thermostatJobDao
				.findLatestJobData(thermostatId);
		try {
			JSONObject jsonObject = new JSONObject(thermostatJob.getJobData());
			jobData = jsonObject.getJSONArray("EE");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		DataUtil.printSPOJobDataJson(jobData);
		return jobData;
	}

	/**
	 * Generate job data for thermostat.
	 * 
	 * @param thermostatId
	 *            the thermostat id
	 * @return the jSON array
	 */
	public JSONArray generateJobDataForThermostat(Integer thermostatId) {

		Thermostat thermostat = findBythermostatId(thermostatId);
		List<MockJobData> jobDataList = mockJobDataBuilder.build(testName,
				thermostat.getTimezone());
		JSONArray jobData = convertToJSONJobData(jobDataList,
				thermostat.getTimezone());
		return jobData;
	}

	/**
	 * Gets the last completed job data.
	 * 
	 * @param thermostatId
	 *            the thermostat id
	 * @return the last completed job data
	 * @see com.ecofactor.qa.automation.algorithm.service.DataService#getLastCompletedJobData(java.lang.Integer)
	 */
	@Override
	public JSONArray getLastCompletedJobData(Integer thermostatId) {

		JSONArray jobData = null;
		DriverConfig.setLogString("Get json Array for Thermostat Id : "
				+ thermostatId, true);
		ThermostatJob thermostatJob = thermostatJobDao
				.findADayBfrJobData(thermostatId);
		try {
			JSONObject jsonObject = new JSONObject(thermostatJob.getJobData());
			jobData = jsonObject.getJSONArray("EE");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		DataUtil.printSPOJobDataJson(jobData);
		return jobData;
	}

	/**
	 * Gets the last completed thermostat job.
	 * 
	 * @param thermostatId
	 *            the thermostat id
	 * @return the last completed thermostat job
	 * @see com.ecofactor.qa.automation.algorithm.service.DataService#getLastCompletedThermostatJob(java.lang.Integer)
	 */
	@Override
	public ThermostatJob getLastCompletedThermostatJob(Integer thermostatId) {

		DriverConfig.setLogString("Get Thermostat Job for Thermostat Id : "
				+ thermostatId, true);
		ThermostatJob thermostatJob = thermostatJobDao
				.findADayBfrJobData(thermostatId);
		return thermostatJob;
	}

	/**
	 * Gets the test.
	 * 
	 * @return the test
	 * @see com.ecofactor.qa.automation.algorithm.service.DataService#getTest()
	 */
	@Override
	public String getTest() {

		return testName;
	}

	/**
	 * Inits the.
	 * 
	 * @param testMethod
	 *            the test method
	 * @param mode
	 *            the mode
	 * @param thermostatId
	 *            the thermostat id
	 * @see com.ecofactor.qa.automation.algorithm.service.DataService#init()
	 */
	@Override
	public void init(String testMethod, String mode, int thermostatId) {

		this.testName = testMethod;
	}

	/**
	 * Checks if is sPO block active.
	 * 
	 * @param thermostatId
	 *            the thermostat id
	 * @param algoId
	 *            the algo id
	 * @return true, if is sPO block active
	 * @see com.ecofactor.qa.automation.algorithm.service.SPODataService#isSPOBlockActive(java.lang.Integer,
	 *      int)
	 */
	@Override
	public boolean isSPOBlockActive(Integer thermostatId, int algoId) {

		DriverConfig.setLogString("Check SPO is Active Now for Thermostat Id :"
				+ thermostatId, true);
		boolean isSPOActive = false;
		Calendar currentTime = DateUtil.getUTCCalendar();
		JSONArray jsonArray = getJobData(thermostatId);
		boolean isDataChanged = false;
		for (int i = 0; i < jsonArray.length(); i++) {
			try {
				JSONObject jsonObj = jsonArray.getJSONObject(i);
				Calendar startCal = DateUtil
						.getUTCTimeZoneCalendar((String) jsonObj.get("start"));
				Calendar endcal = DateUtil
						.getUTCTimeZoneCalendar((String) jsonObj.get("end"));
				int endHour = endcal.get(Calendar.HOUR_OF_DAY);
				int startHour = startCal.get(Calendar.HOUR_OF_DAY);
				if (isDataChanged) {
					startCal.set(Calendar.DATE, startCal.get(Calendar.DATE) + 1);
					endcal.set(Calendar.DATE, endcal.get(Calendar.DATE) + 1);
				}
				if (startHour > endHour) {
					endcal.set(Calendar.DATE, endcal.get(Calendar.DATE) + 1);
					isDataChanged = true;
				}
				DriverConfig.setLogString(
						"startCal :"
								+ DateUtil.format(startCal,
										DateUtil.DATE_FMT_FULL_TZ), true);
				DriverConfig.setLogString(
						"endcal :"
								+ DateUtil.format(endcal,
										DateUtil.DATE_FMT_FULL_TZ), true);
				if (currentTime.after(startCal) && currentTime.before(endcal)) {
					isSPOActive = true;
					break;
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		return isSPOActive;
	}

	/**
	 * List algo control.
	 * 
	 * @param thermostatId
	 *            the thermostat id
	 * @param algoId
	 *            the algo id
	 * @return the list
	 * @see com.ecofactor.qa.automation.algorithm.service.SPODataService#listAlgoControl(java.lang.Integer,
	 *      int)
	 */
	@Override
	public List<ThermostatAlgorithmController> listActiveAlgoControl(
			Integer thermostatId, int algoId) {

		List<ThermostatAlgorithmController> listAlgoController = thermostatAlgoControlDao
				.listActiveAlgoControl(thermostatId, algoId);
		return listAlgoController;

	}

	/**
	 * List algo control for algorithm.
	 * 
	 * @param thermostatId
	 *            the thermostat id
	 * @param algoId
	 *            the algo id
	 * @return the list
	 * @see com.ecofactor.qa.automation.algorithm.service.DataService#listAlgoControlForAlgorithm(java.lang.Integer,
	 *      int)
	 */
	@Override
	public List<ThermostatAlgorithmController> listAlgoControlForAlgorithm(
			Integer thermostatId, int algoId) {

		List<ThermostatAlgorithmController> listAlgoController = thermostatAlgoControlDao
				.listAlgoControlForAlgorithm(thermostatId, algoId);
		return listAlgoController;

	}

	/**
	 * List events.
	 * 
	 * @param thermostatId
	 *            the thermostat id
	 * @param startTime
	 *            the start time
	 * @return the list
	 * @see com.ecofactor.qa.automation.algorithm.service.DataService#listEvents(java.lang.Integer,
	 *      java.util.Calendar)
	 */
	@Override
	public List<PartitionedThermostatEvent> listEvents(Integer thermostatId,
			Calendar startTime) {

		List<PartitionedThermostatEvent> thEventList = thermostatEventDao
				.listByThermostatAndEventSysTime(thermostatId, startTime);
		return thEventList;
	}

	/**
	 * List events.
	 * 
	 * @param thermostatId
	 *            the thermostat id
	 * @param startTime
	 *            the start time
	 * @param endTime
	 *            the end time
	 * @return the list
	 * @see com.ecofactor.qa.automation.algorithm.service.DataService#listEvents(java.lang.Integer,
	 *      java.util.Calendar, java.util.Calendar)
	 */
	@Override
	public List<PartitionedThermostatEvent> listEvents(Integer thermostatId,
			Calendar startTime, Calendar endTime) {

		List<PartitionedThermostatEvent> thEventList = thermostatEventDao
				.listByThermostatAndEventSysTimeRange(thermostatId, startTime,
						endTime);
		return thEventList;
	}

	/**
	 * List events.
	 * 
	 * @param thermostatId
	 *            the thermostat id
	 * @param algoId
	 *            the algo id
	 * @param startTime
	 *            the start time
	 * @return the list
	 * @see com.ecofactor.qa.automation.algorithm.service.SPODataService#listEvents(java.lang.Integer,
	 *      int, java.util.Calendar)
	 */
	@Override
	public List<PartitionedThermostatEvent> listEvents(Integer thermostatId,
			int algoId, Calendar startTime) {

		List<PartitionedThermostatEvent> events = null;
		List<PartitionedThermostatEvent> eventAlgoList = new ArrayList<PartitionedThermostatEvent>();
		events = thermostatEventDao.listByThermostatAndEventSysTime(
				thermostatId, startTime);

		for (PartitionedThermostatEvent event : events) {
			if (event.getAlgorithmId().equals(algoId))
				eventAlgoList.add(event);
		}

		return eventAlgoList;
	}

	/**
	 * List events.
	 * 
	 * @param thermostatId
	 *            the thermostat id
	 * @param algoId
	 *            the algo id
	 * @param startTime
	 *            the start time
	 * @param endTime
	 *            the end time
	 * @return the list
	 * @see com.ecofactor.qa.automation.algorithm.service.SPODataService#listEvents(java.lang.Integer,
	 *      int, java.util.Calendar, java.util.Calendar)
	 */
	@Override
	public List<PartitionedThermostatEvent> listEvents(Integer thermostatId,
			int algoId, Calendar startTime, Calendar endTime) {

		List<PartitionedThermostatEvent> events = thermostatEventDao
				.listEventsByThermostatAlgorithm(thermostatId, algoId,
						startTime, endTime);
		return events;
	}

	/**
	 * Milliseconds for spo block start.
	 * 
	 * @param thermostatId
	 *            the thermostat id
	 * @param algoId
	 *            the algo id
	 * @return the long
	 * @see com.ecofactor.qa.automation.algorithm.service.SPODataService#millisecondsForSPOBlockStart(java.lang.Integer,
	 *      int)
	 */
	@Override
	public long millisecondsForSPOBlock(Integer thermostatId, int algoId) {

		List<ThermostatAlgorithmController> thAlgoList = thermostatAlgoControlDao
				.listActiveAlgoControl(thermostatId, algoId);
		String spoStatus = "End";
		if (thAlgoList != null && thAlgoList.size() % 2 == 0) {
			spoStatus = "Start";
		}
		DriverConfig.setLogString("Get milliseconds for SPO Block " + spoStatus
				+ " for Thermostat Id : " + thermostatId, true);
		long milliSeconds = 0;

		String currentUTCTime = DateUtil
				.getUTCCurrentTimeStamp(DateUtil.DATE_FMT_FULL_TZ);
		Calendar currentCalendar = DateUtil.parseToUTCCalendar(currentUTCTime,
				DateUtil.DATE_FMT_FULL_TZ);

		ThermostatAlgorithmController thController = thermostatAlgoControlDao
				.findSPOStart(thermostatId, algoId);

		Calendar startTime = thController.getNextPhaseTime();
		String starTimeStamp = DateUtil.format(startTime,
				DateUtil.DATE_FMT_FULL);
		startTime = DateUtil.parseToUTCCalendar(starTimeStamp,
				DateUtil.DATE_FMT_FULL);

		milliSeconds = startTime.getTimeInMillis()
				- currentCalendar.getTimeInMillis();

		return milliSeconds;
	}

	/**
	 * Milliseconds for spo start.
	 * 
	 * @param thermostatId
	 *            the thermostat id
	 * @param algoId
	 *            the algo id
	 * @return the long
	 */
	@Override
	public long millisecondsForSPOStart(Integer thermostatId, int algoId) {

		long milliSeconds = 0;
		DriverConfig.setLogString("Wait for next phase time", true);
		List<ThermostatAlgorithmController> thAlgoList = thermostatAlgoControlDao
				.listActiveAlgoControl(thermostatId, algoId);
		String spoStatus = "End";
		if (thAlgoList != null && thAlgoList.size() % 2 == 0) {

			String currentUTCTime = DateUtil
					.getUTCCurrentTimeStamp(DateUtil.DATE_FMT_FULL_TZ);
			Calendar currentCalendar = DateUtil.parseToUTCCalendar(
					currentUTCTime, DateUtil.DATE_FMT_FULL_TZ);
			spoStatus = "Start";
			DriverConfig.setLogString("Get milliseconds for SPO Block "
					+ spoStatus + " for Thermostat Id : " + thermostatId, true);
			ThermostatAlgorithmController thController = thAlgoList.get(0);
			Calendar startTime = thController.getNextPhaseTime();
			String starTimeStamp = DateUtil.format(startTime,
					DateUtil.DATE_FMT_FULL);
			startTime = DateUtil.parseToUTCCalendar(starTimeStamp,
					DateUtil.DATE_FMT_FULL);
			milliSeconds = startTime.getTimeInMillis()
					- currentCalendar.getTimeInMillis();

		} else {
			DriverConfig.setLogString("SPO already started", true);
		}

		return milliSeconds;
	}

	/**
	 * Milliseconds for spo block end.
	 * 
	 * @param thermostatId
	 *            the thermostat id
	 * @param algoId
	 *            the algo id
	 * @return the long
	 * @see com.ecofactor.qa.automation.algorithm.service.DataService#millisecondsForSPOBlockEnd(java.lang.Integer,
	 *      int)
	 */
	@Override
	public long millisecondsForSPOBlockEnd(Integer thermostatId, int algoId) {

		DriverConfig.setLogString("Calculate SPO end for thermostat : "
				+ thermostatId, true);
		List<ThermostatAlgorithmController> thAlgoList = thermostatAlgoControlDao
				.listActiveAlgoControl(thermostatId, algoId);
		int index = 0;
		DataUtil.printAlgoControlTableGrid(thAlgoList);

		if (thAlgoList != null && thAlgoList.size() % 2 == 0) {
			index = 1;
		}

		long milliSeconds = 0;

		String currentUTCTime = DateUtil
				.getUTCCurrentTimeStamp(DateUtil.DATE_FMT_FULL_TZ);
		Calendar currentCalendar = DateUtil.parseToUTCCalendar(currentUTCTime,
				DateUtil.DATE_FMT_FULL_TZ);

		ThermostatAlgorithmController thController = thAlgoList.get(index);

		Calendar startTime = thController.getNextPhaseTime();
		String starTimeStamp = DateUtil.format(startTime,
				DateUtil.DATE_FMT_FULL);
		startTime = DateUtil.parseToUTCCalendar(starTimeStamp,
				DateUtil.DATE_FMT_FULL);

		milliSeconds = startTime.getTimeInMillis()
				- currentCalendar.getTimeInMillis();

		return milliSeconds;
	}

	/**
	 * Sets the base temp.
	 * 
	 * @param baseTemp
	 *            the new base temp
	 * @see com.ecofactor.qa.automation.algorithm.service.DataService#setBaseTemp(java.lang.Double)
	 */
	@Override
	public void setBaseTemp(Double baseTemp) {

		this.baseTemp = baseTemp;

	}

	/**
	 * Sets the test.
	 * 
	 * @param testName
	 *            the new test
	 * @see com.ecofactor.qa.automation.algorithm.service.DataService#setTest(java.lang.String)
	 */
	@Override
	public void setTest(String testName) {

		this.testName = testName;

	}

	/**
	 * Save thermostat algo controller.
	 * 
	 * @param AlgoControlList
	 *            the algo control list
	 * @return true, if successful
	 * @see com.ecofactor.qa.automation.algorithm.service.DataService#saveThermostatAlgoControll(java.util.List)
	 */
	@Override
	public boolean saveThermostatAlgoController(
			List<ThermostatAlgorithmController> AlgoControlList) {

		DriverConfig.setLogString("Save thermostat algo control list.", true);
		return thermostatAlgoControlDao
				.saveThermostatAlgorithmController(AlgoControlList);
	}

	/**
	 * Find by algorithm id.
	 * 
	 * @param algoId
	 *            the algo id
	 * @return the algorithm
	 * @see com.ecofactor.qa.automation.algorithm.service.DataService#findByAlgorithmId(java.lang.Integer)
	 */
	@Override
	public Algorithm findByAlgorithmId(Integer algoId) {

		Algorithm algorithm = algorithmDao.findById(algoId);
		return algorithm;
	}

	/**
	 * Update thermostat algo controller.
	 * 
	 * @param algoControlList
	 *            the algo control list
	 * @return true, if successful
	 * @see com.ecofactor.qa.automation.algorithm.service.DataService#saveThermostatAlgoControll(java.util.List)
	 */
	@Override
	public boolean updateThermostatAlgoControllerList(
			List<ThermostatAlgorithmController> algoControlList) {

		return thermostatAlgoControlDao
				.updateThermostatAlgorithmControllerList(algoControlList);
	}

	/**
	 * Update thermostat algo controller.
	 * 
	 * @param algoControl
	 *            the algo control
	 * @return true, if successful
	 */
	public boolean updateThermostatAlgoController(
			ThermostatAlgorithmController algoControl) {

		DriverConfig.setLogString("Update thermostat algo control.", true);
		return thermostatAlgoControlDao
				.updateThermostatAlgorithmController(algoControl);
	}

	/**
	 * Update algo control to inactive.
	 * 
	 * @param thermostatId
	 *            the thermostat id
	 * @param algoId
	 *            the algo id
	 * @see com.ecofactor.qa.automation.algorithm.service.DataService#updateAlgoControlToInactive(java.lang.Integer,
	 *      java.lang.Integer)
	 */
	@Override
	public void updateAlgoControlToInactive(Integer thermostatId, int algoId) {

		List<ThermostatAlgorithmController> algoControlList = new ArrayList<ThermostatAlgorithmController>();
		algoControlList = listActiveAlgoControl(thermostatId, algoId);
		for (ThermostatAlgorithmController algoControl : algoControlList) {
			algoControl.setStatus(Status.CANCELED);
		}
		updateThermostatAlgoControllerList(algoControlList);
	}

	/**
	 * Update exclude start and end for st.
	 * 
	 * @param thermostatId
	 *            the thermostat id
	 * @param algoId
	 *            the algo id
	 * @param exlcudeStartTime
	 *            the exlcude start time
	 * @param excludeEndTime
	 *            the exclude end time
	 * @see com.ecofactor.qa.automation.algorithm.service.DataService#updateExcludeStartAndEndForST(java.lang.Integer,
	 *      int, java.util.Date, java.util.Date)
	 */
	@Override
	public void updateExcludeStartAndEndForST(Integer thermostatId, int algoId,
			Date exlcudeStartTime, Date excludeEndTime) {

		ThermostatAlgorithmController thAlgoController = thermostatAlgoControlDao
				.listActiveAlgoControl(thermostatId, algoId).get(0);
		if (thAlgoController != null) {
			thAlgoController.setExcludeStartTimeUtc(exlcudeStartTime);
			thAlgoController.setExcludeEndTimeUtc(excludeEndTime);
		}
		DriverConfig.setLogString("Writing into algo Contorl", true);
		updateThermostatAlgoController(thAlgoController);
	}

	/**
	 * Find active spo algo control.
	 * 
	 * @param thermostatId
	 *            the thermostat id
	 * @param algoId
	 *            the algo id
	 * @return the thermostat algorithm controller
	 * @see com.ecofactor.qa.automation.algorithm.service.DataService#findActiveAlgoControl(java.lang.Integer,
	 *      int)
	 */
	@Override
	public ThermostatAlgorithmController findActiveSPOAlgoControl(
			Integer thermostatId, int algoId) {

		ThermostatAlgorithmController thController = thermostatAlgoControlDao
				.findSPOStart(thermostatId, algoId);
		return thController;
	}

	/**
	 * List by thermostat and start time range.
	 * 
	 * @param thermostatId
	 *            the thermostat id
	 * @param startTime
	 *            the start time
	 * @param endTime
	 *            the end time
	 * @return the list
	 * @see com.ecofactor.qa.automation.algorithm.service.DataService#listByThermostatAndStartTimeRange(java.lang.Integer,
	 *      java.util.Calendar, java.util.Calendar)
	 */
	@Override
	public List<PartitionedThermostatRangeData> listByThermostatAndStartTimeRange(
			Integer thermostatId, Calendar startTime, Calendar endTime) {

		List<PartitionedThermostatRangeData> thermostatRangeData = thermostatRangeDataDao
				.listByThermostatAndStartTimeRange(thermostatId, startTime,
						endTime);
		return thermostatRangeData;

	}

	/**
	 * Verify temperature changes in range data.
	 * 
	 * @param thermostatId
	 *            the thermostat id
	 * @param algoId
	 *            the algo id
	 * @param temperature
	 *            the temperature
	 * @see com.ecofactor.qa.automation.algorithm.service.DataService#verifyTemperatureChangesInRangeData(int,
	 *      java.lang.String, double)
	 */
	@Override
	public void verifyTemperatureChangesInRangeData(int thermostatId,
			int algoId, double temperature) {

		DriverConfig
				.setLogString(
						"Verify temperature changes in range data table for thermostat : "
								+ thermostatId + ", Temperature : "
								+ temperature, true);
		PartitionedThermostatRangeData rangeData = null;

		boolean eventProcessed = false;
		Calendar currentTime = DateUtil.getUTCCalendar();
		Calendar endTime = DateUtil.getUTCCalendar();
		endTime.add(Calendar.MINUTE, 15);
		Algorithm algorithm = algorithmDao.findById(algoId);
		DriverConfig.setLogString(
				"Wait for Thermostat set point change in range data : "
						+ thermostatId + ", Action : " + algorithm.getAction()
						+ ". (Current Time : "
						+ DateUtil.format(currentTime, DateUtil.DATE_FMT)
						+ " Wait for maximum until : "
						+ DateUtil.format(endTime, DateUtil.DATE_FMT) + ")",
				true);
		do {
			mediumWait();
			rangeData = thermostatRangeDataDao
					.findLatestByThermostat(thermostatId);
			if (rangeData != null) {
				if (algorithm.getAction().equalsIgnoreCase("cool_setting")) {
					if (rangeData.getCoolSetting() != null
							&& temperature == rangeData.getCoolSetting()) {
						eventProcessed = true;
					}
				} else {
					if (rangeData.getHeatSetting() != null
							&& temperature == rangeData.getHeatSetting()) {
						eventProcessed = true;
					}
				}
			}
			if (eventProcessed) {
				break;
			}
			currentTime = DateUtil.getUTCCalendar();
		} while (currentTime.before(endTime));

		Assert.assertTrue(eventProcessed,
				"Temperature change issue in range data for thermostat : "
						+ thermostatId);
	}

	/**
	 * Find active event algo control.
	 * 
	 * @param thermostatId
	 *            the thermostat id
	 * @param algoId
	 *            the algo id
	 * @return the thermostat algorithm controller
	 */
	@Override
	public ThermostatAlgorithmController findActiveEventAlgoControl(
			Integer thermostatId, int algoId) {

		ThermostatAlgorithmController thController = thermostatAlgoControlDao
				.findActiveEvent(thermostatId, algoId);
		return thController;
	}

	/**
	 * List algo processed events and time range.
	 * 
	 * @param thermostatId
	 *            the thermostat id
	 * @param startTime
	 *            the start time
	 * @param endTime
	 *            the end time
	 * @return the list
	 */
	@Override
	public List<PartitionedThermostatEvent> listAlgoProcessedEventsAndTimeRange(
			Integer thermostatId, Calendar startTime, Calendar endTime) {

		List<PartitionedThermostatEvent> events = thermostatEventDao
				.listAlgoProcessedEventsByThermostatAndTimeRange(thermostatId,
						startTime, endTime);
		return events;
	}

	/**
	 * Gets the subscribed algo.
	 * 
	 * @param thermostatId
	 *            the thermostat id
	 * @return the subscribed algo
	 */
	public synchronized String[] getSubscribedAlgoName(Integer thermostatId) {

		DriverConfig.setLogString("Get subscribed algorithms", true);
		List<ThermostatAlgorithm> thAlgoList = thAlgorithmDao
				.findByThermostatId(thermostatId);
		List<String> subscribedAlgoList = new ArrayList<String>();
		for (ThermostatAlgorithm thermostatAlgorithm : thAlgoList) {
			subscribedAlgoList
					.add(thermostatAlgorithm.getAlgorithm().getName());
		}
		String[] subscribedAlgo = subscribedAlgoList
				.toArray(new String[subscribedAlgoList.size()]);
		return subscribedAlgo;
	}

}
