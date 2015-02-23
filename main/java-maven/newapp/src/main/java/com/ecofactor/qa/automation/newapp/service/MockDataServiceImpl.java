/*
 * MockDataServiceImpl.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.newapp.service;

import static org.testng.Reporter.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ecofactor.common.pojo.Algorithm;
import com.ecofactor.common.pojo.Thermostat;
import com.ecofactor.common.pojo.ThermostatAlgorithmController;
import com.ecofactor.common.pojo.ThermostatJob;
import com.ecofactor.common.pojo.timeseries.PartitionedThermostatEvent;
import com.ecofactor.common.pojo.timeseries.PartitionedThermostatRangeData;
import com.ecofactor.qa.automation.newapp.mock.MockJobDataBuilder;
import com.ecofactor.qa.automation.newapp.mock.MockJobDataConfig;
import com.ecofactor.qa.automation.newapp.mock.MockConsumer;
import com.ecofactor.qa.automation.newapp.mock.MockEventListener;
import com.ecofactor.qa.automation.dao.util.DataUtil;
import com.ecofactor.qa.automation.util.DateUtil;
import com.ecofactor.qa.automation.util.WaitUtil;
import com.ecofactor.qa.automation.util.mock.MockEvent;
import com.ecofactor.qa.automation.util.mock.MockEventType;
import com.ecofactor.qa.automation.util.mock.MockJobData;
import com.google.inject.Inject;

/**
 * MockDataService .
 * 
 * @author $Author: rvinoopraj $
 * @version $Rev: 30018 $ $Date: 2014-04-17 16:18:39 +0530 (Thu, 17 Apr 2014) $
 */
public class MockDataServiceImpl extends BaseDataServiceImpl implements
		DataService, MockEventListener {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(MockDataServiceImpl.class);
	private static final Integer SPO_COOL = 190;
	private static final Integer SPO_HEAT = 191;
	private static final Integer ST3_COOL = 110;
	private static final Integer ST3_HEAT = 111;
	private static final Integer SET_AWAY = -20;
	private static final Integer MANUAL_OVERRIDE = -10;
	private static final String COOL = "Cool";
	private static final String HEAT = "Heat";
	@Inject
	private MockConsumer mockConsumer;
	@Inject
	private MockJobDataBuilder mockJobDataBuilder;
	@Inject
	private MockJobDataConfig mockJobDataConfig;
	private String testName;
	private String mode;
	private Integer thermostatId;
	private Double baseTemp;
	private JSONArray jobData;
	private List<MockJobData> jobDataList;
	private Map<Calendar, PartitionedThermostatEvent> timeEventMap;
	private Calendar testStartUTCTime;
	private List<ThermostatAlgorithmController> algoControlList;
	private Calendar moBlackoutEndUTCTime;

	/**
	 * Instantiates a new mock data service impl.
	 */
	public MockDataServiceImpl() {

		algoControlList = new ArrayList<ThermostatAlgorithmController>();
		timeEventMap = new TreeMap<Calendar, PartitionedThermostatEvent>();
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
		this.mode = mode;
		this.thermostatId = thermostatId;
		getJobData(thermostatId);
		loadEvents(thermostatId);

		baseTemp = mockJobDataConfig.getDouble(MockJobDataConfig.BASE_TEMP);
		mockConsumer.addEventListener(this);
	}

	/**
	 * Clear.
	 * 
	 * @see com.ecofactor.qa.automation.algorithm.service.DataService#clear()
	 */
	@Override
	public void clear() {

		jobData = null;
		jobDataList = null;
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

		int algoId = 0;
		int position = getCurrentPosition(thermostatId);
		if (position >= 10 || position == 0 || position > jobDataList.size()) {
			algoId = (mode.equals(COOL)) ? ST3_COOL : ST3_HEAT;
		} else if (position > 0 && position <= jobDataList.size()) {
			algoId = (mode.equals(COOL)) ? SPO_COOL : SPO_HEAT;
		}

		return algoId;
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

		Thermostat thermostat = new Thermostat();
		thermostat.setId(thermostatId);
		thermostat.setTimezone("Asia/Calcutta");
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

		loadEvents(thermostatId);
		Set<Calendar> times = timeEventMap.keySet();
		Calendar lastTime = null;
		for (Calendar time : times) {
			lastTime = time;
		}

		return timeEventMap.get(lastTime);
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
	@Override
	public double getCurrentBaseTemp(Integer thermostatId, String mode) {

		return baseTemp;
	}

	/**
	 * Gets the job data.
	 * 
	 * @param thermostatId
	 *            the thermostat id
	 * @return the job data
	 * @see com.ecofactor.qa.automation.algorithm.service.DataService#getJobData(java.lang.Integer)
	 */
	@Override
	public JSONArray getJobData(Integer thermostatId) {

		if (jobData == null) {
			log("Get json Array for Thermostat Id : " + thermostatId, true);
			Thermostat thermostat = findBythermostatId(thermostatId);
			jobDataList = mockJobDataBuilder.build(testName,
					thermostat.getTimezone());
			jobData = new JSONArray();
			int i = 0;
			for (MockJobData mockJobData : jobDataList) {
				try {
					JSONObject jsonObject = new JSONObject();
					jsonObject
							.put("start", DateUtil.format(
									mockJobData.getStart(), "HH:mm:ss"));
					jsonObject.put("end",
							DateUtil.format(mockJobData.getEnd(), "HH:mm:ss"));
					jsonObject.put("moBlackOut", mockJobData.getBlackout());
					Calendar cuttOffTime = (Calendar) Calendar.getInstance(
							TimeZone.getTimeZone(thermostat.getTimezone()))
							.clone();
					cuttOffTime.set(Calendar.HOUR_OF_DAY, 0);
					cuttOffTime.set(Calendar.MINUTE, 0);
					cuttOffTime.set(Calendar.SECOND, 0);
					if (mockJobData.getCutoff() == null) {
						jsonObject.put("moCutoff",
								DateUtil.format(cuttOffTime, "HH:mm:ss"));
					} else {
						jsonObject.put("moCutoff", mockJobData.getCutoff());
					}

					jsonObject.put("moRecovery", mockJobData.getRecovery());
					jsonObject.put("deltaEE", mockJobData.getDelta());
					jobData.put(i, jsonObject);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				i++;
			}
			log("Json Array : " + jobData, true);
			DataUtil.printMockSPOJobDataJson(jobData);
		}

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

		return getJobData(thermostatId);
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

		ThermostatJob thermostatJob = null;
		JSONArray jsonArray = getJobData(thermostatId);
		for (int i = 0; i < jsonArray.length(); i++) {
			try {
				thermostatJob = new ThermostatJob();
				Calendar calendar = DateUtil.getUTCCalendar();
				JSONObject jsonObj = jsonArray.getJSONObject(i);

				thermostatJob.setThermostatId(thermostatId);
				thermostatJob.setJobData(jsonObj.toString());

				thermostatJob.setStartDate(calendar);
				thermostatJob.setEndDate(calendar);

				thermostatJob.setJobType("SPO");
				thermostatJob.setAlgorithmId(190);
				thermostatJob.setJobStatus(ThermostatJob.Status.PROCESSED);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
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
	 * Handle mock event.
	 * 
	 * @param me
	 *            the me
	 * @see com.ecofactor.qa.automation.consumer.page.MockEventListener#handleMockEvent(com.ecofactor.qa.automation.algorithm.mock.MockEvent)
	 */
	@Override
	public void handleMockEvent(MockEvent me) {

		List<PartitionedThermostatEvent> events = null;
		Calendar start = (Calendar) me.getProperty("start");
		if (start != null) {
			start.add(Calendar.SECOND, 45);
		}
		Calendar end = null;
		Integer value = null;
		Double oldValue = null;
		Double newValue = null;

		if (testStartUTCTime == null) {
			testStartUTCTime = DateUtil.getUTCCalendar();
		}

		switch (me.getType()) {
		case COOL_MODE:
			mode = "Cool";
			break;
		case HEAT_MODE:
			mode = "Heat";
			break;
		case SET_AWAY:
			events = getSetAwayEvents(thermostatId, start);
			break;
		case IM_BACK:
			loadAlgoResumeEvents(thermostatId);
			break;
		case SETPOINT_INCREASE:
			value = (Integer) me.getProperty("value");
			events = getSetPointIncrease(thermostatId, start, value);
			break;
		case SETPOINT_DECREASE:
			value = (Integer) me.getProperty("value");
			events = getSetPointDecrease(thermostatId, start, value);
			break;
		case PROGRAM_CHANGE:
			oldValue = (Double) me.getProperty("old");
			newValue = (Double) me.getProperty("new");
			events = getProgramChangeEvents(thermostatId, start, oldValue,
					newValue);
			break;
		case SPO:
			int position = (Integer) me.getProperty("position");
			events = getSPOEvents(thermostatId, position);
			break;
		case ST:
			end = (Calendar) me.getProperty("end");
			events = getSTEvents(thermostatId, start, end);
			break;
		}

		loadEvents(events);
		printMockThermostatEvents(me);
	}

	/**
	 * Load algo resume events.
	 * 
	 * @param thermostatId
	 *            the thermostat id
	 */
	private void loadAlgoResumeEvents(Integer thermostatId) {

		int current = getCurrentPosition(thermostatId);
		if (current > 0 && !(current > jobDataList.size())) {
			MockJobData block = jobDataList.get(current - 1);

			Algorithm algorithm = new Algorithm();
			if (mode.equals(COOL)) {
				algorithm.setId(SPO_COOL);
			} else if (mode.equals(HEAT)) {
				algorithm.setId(SPO_HEAT);
			}

			int phase = 0;

			PartitionedThermostatEvent thermostatEvent = new PartitionedThermostatEvent();
			thermostatEvent.setAlgorithmId(algorithm.getId());
			thermostatEvent.setThermostatId(thermostatId);
			thermostatEvent.setPhase(phase);

			Calendar utc = DateUtil.getUTCCalendar();
			thermostatEvent.setDeltaEE(Double.valueOf("" + block.getDelta()));
			WaitUtil.hugeWait();
			utc.add(Calendar.MINUTE, 2);
			thermostatEvent.getId().setEventSysTime(utc);
			thermostatEvent.setOldSetting(baseTemp);
			thermostatEvent.setNewSetting(baseTemp + block.getDelta());
			thermostatEvent.setStatus(1);
			timeEventMap.put(utc, thermostatEvent);
		} else {
			loadSPOEvents(thermostatId);
			loadSTEvents(thermostatId);
		}
	}

	/**
	 * Checks if is sPO block active.
	 * 
	 * @param thermostatId
	 *            the thermostat id
	 * @param algoId
	 *            the algo id
	 * @return true, if is sPO block active
	 * @see com.ecofactor.qa.automation.algorithm.service.DataService#isSPOBlockActive(java.lang.Integer,
	 *      int)
	 */
	@Override
	public boolean isSPOBlockActive(Integer thermostatId, int algoId) {

		log("Check if SPO is active for thermostat :" + thermostatId, true);
		boolean spoActive = false;
		Thermostat thermostat = findBythermostatId(thermostatId);
		Calendar currentTime = (Calendar) Calendar.getInstance(
				TimeZone.getTimeZone(thermostat.getTimezone())).clone();

		JSONArray jsonArray = getJobData(thermostatId);
		for (int i = 0; i < jsonArray.length(); i++) {
			try {
				JSONObject jsonObj = jsonArray.getJSONObject(i);
				Calendar startCal = DateUtil
						.getTimeZoneCalendar((String) jsonObj.get("start"),
								thermostat.getTimezone());
				Calendar endcal = DateUtil.getTimeZoneCalendar(
						(String) jsonObj.get("end"), thermostat.getTimezone());

				if (currentTime.after(startCal) && currentTime.before(endcal)) {
					spoActive = true;
					break;
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		return spoActive;
	}

	/**
	 * List algo control.
	 * 
	 * @param thermostatId
	 *            the thermostat id
	 * @param algoId
	 *            the algo id
	 * @return the list
	 * @see com.ecofactor.qa.automation.algorithm.service.DataService#listAlgoControl(java.lang.Integer,
	 *      int)
	 */
	@Override
	public List<ThermostatAlgorithmController> listActiveAlgoControl(
			Integer thermostatId, int algoId) {

		List<ThermostatAlgorithmController> algoControlList = generateAlgoControlList(
				thermostatId, algoId);
		return algoControlList;
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

		loadEvents(thermostatId);
		List<PartitionedThermostatEvent> events = new ArrayList<PartitionedThermostatEvent>();
		Calendar startTimeUTC = DateUtil.copyToUTC(startTime);
		Set<Calendar> timeSet = timeEventMap.keySet();
		for (Calendar time : timeSet) {
			if (time.after(startTimeUTC)) {
				events.add(timeEventMap.get(time));
			}
		}

		return events;
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

		loadEvents(thermostatId);
		List<PartitionedThermostatEvent> events = new ArrayList<PartitionedThermostatEvent>();
		Set<Calendar> timeSet = timeEventMap.keySet();
		Calendar startTimeUTC = DateUtil.copyToUTC(startTime);
		Calendar endTimeUTC = DateUtil.copyToUTC(endTime);
		for (Calendar time : timeSet) {
			LOGGER.info("Checking event for "
					+ DateUtil.format(time, DateUtil.DATE_FMT_FULL_TZ) + " "
					+ timeEventMap.get(time).getAlgorithmId() + ", "
					+ DateUtil.format(startTimeUTC, DateUtil.DATE_FMT_FULL_TZ)
					+ ", "
					+ DateUtil.format(endTimeUTC, DateUtil.DATE_FMT_FULL_TZ));
			if (time.after(startTimeUTC) && time.before(endTimeUTC)) {
				events.add(timeEventMap.get(time));
				LOGGER.info("Including event for "
						+ DateUtil.format(time, DateUtil.DATE_FMT_FULL_TZ));
			}
		}

		return events;
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
	 * @see com.ecofactor.qa.automation.algorithm.service.DataService#listEvents(java.lang.Integer,
	 *      int, java.util.Calendar)
	 */
	@Override
	public List<PartitionedThermostatEvent> listEvents(Integer thermostatId,
			int algoId, Calendar startTime) {

		loadEvents(thermostatId);
		List<PartitionedThermostatEvent> events = new ArrayList<PartitionedThermostatEvent>();
		Calendar startTimeUTC = DateUtil.copyToUTC(startTime);
		Set<Calendar> timeSet = timeEventMap.keySet();
		for (Calendar time : timeSet) {
			PartitionedThermostatEvent event = timeEventMap.get(time);
			if (time.after(startTimeUTC) && event.getAlgorithmId() == algoId) {
				events.add(event);
			}
		}

		return events;
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
	 * @see com.ecofactor.qa.automation.algorithm.service.DataService#listEvents(java.lang.Integer,
	 *      int, java.util.Calendar, java.util.Calendar)
	 */
	@Override
	public List<PartitionedThermostatEvent> listEvents(Integer thermostatId,
			int algoId, Calendar startTime, Calendar endTime) {

		loadEvents(thermostatId);
		List<PartitionedThermostatEvent> events = new ArrayList<PartitionedThermostatEvent>();
		Set<Calendar> timeSet = timeEventMap.keySet();
		for (Calendar time : timeSet) {
			PartitionedThermostatEvent event = timeEventMap.get(time);
			if (time.after(startTime) && time.before(endTime)
					&& event.getAlgorithmId() == algoId) {
				events.add(event);
			}
		}

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
	 * @see com.ecofactor.qa.automation.algorithm.service.DataService#millisecondsForSPOBlockStart(java.lang.Integer,
	 *      int)
	 */
	@Override
	public long millisecondsForSPOBlock(Integer thermostatId, int algoId) {

		int position = getCurrentPosition(thermostatId);
		long milliSeconds = 0;
		Calendar currentTime = Calendar.getInstance();

		if (position == 0) {
			log("Get milliseconds for SPO Block Start for Thermostat Id : "
					+ thermostatId, true);
			MockJobData job = jobDataList.get(position);
			milliSeconds = job.getStart().getTimeInMillis()
					- currentTime.getTimeInMillis();
		} else if (position <= jobDataList.size()) {
			log("Get milliseconds for SPO Block End for Thermostat Id : "
					+ thermostatId, true);
			MockJobData job = jobDataList.get(position - 1);
			milliSeconds = job.getEnd().getTimeInMillis()
					- currentTime.getTimeInMillis();
		}

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
	 * Gets the current position.
	 * 
	 * @param thermostatId
	 *            the thermostat id
	 * @return the current position
	 */
	private int getCurrentPosition(Integer thermostatId) {

		Thermostat thermostat = findBythermostatId(thermostatId);
		Calendar current = DateUtil.getCurrentTimeZoneCalendar(thermostat
				.getTimezone());
		int currentPosition = 0;
		int block = 0;
		int gap = 0;
		for (MockJobData jobData : jobDataList) {
			++block;
			if (current.after(jobData.getStart())) {
				++currentPosition;
			}

			if (current.after(jobData.getEnd())) {
				if (block == mockJobDataConfig.getInt(testName
						+ MockJobDataConfig.TEST_BLOCKS)) {
					++currentPosition;
				} else {
					if (currentPosition > gap) {
						++gap;
					}
				}
			}
		}

		if (gap == currentPosition) {
			currentPosition = gap * 10;
		}

		return currentPosition;
	}

	/**
	 * Gets the program change events.
	 * 
	 * @param thermostatId
	 *            the thermostat id
	 * @param startTime
	 *            the start time
	 * @param oldBaseTemp
	 *            the old base temp
	 * @param newBaseTemp
	 *            the new base temp
	 * @return the program change events
	 */
	private List<PartitionedThermostatEvent> getProgramChangeEvents(
			Integer thermostatId, Calendar startTime, Double oldBaseTemp,
			Double newBaseTemp) {

		Algorithm algorithm = new Algorithm();
		algorithm.setId(findActiveAlgorithmId(thermostatId));
		int phase = 0;

		List<PartitionedThermostatEvent> events = new ArrayList<PartitionedThermostatEvent>();

		PartitionedThermostatEvent thermostatEvent = new PartitionedThermostatEvent();
		thermostatEvent.setAlgorithmId(algorithm.getId());
		thermostatEvent.setThermostatId(thermostatId);
		thermostatEvent.setPhase(phase);
		thermostatEvent.getId().setEventSysTime(startTime);
		thermostatEvent.setOldSetting(oldBaseTemp);
		thermostatEvent.setStatus(1);
		thermostatEvent.setNewSetting(newBaseTemp);
		thermostatEvent.setDeltaEE(newBaseTemp - oldBaseTemp);
		events.add(thermostatEvent);

		return events;
	}

	/**
	 * Gets the sets the away events.
	 * 
	 * @param thermostatId
	 *            the thermostat id
	 * @param startTime
	 *            the start time
	 * @return the sets the away events
	 */
	private List<PartitionedThermostatEvent> getSetAwayEvents(
			Integer thermostatId, Calendar startTime) {

		Algorithm algorithm = new Algorithm();
		algorithm.setId(-20);
		int phases[] = { 50, 30, 10, 0 };

		List<PartitionedThermostatEvent> events = new ArrayList<PartitionedThermostatEvent>();
		for (int phaseval : phases) {
			PartitionedThermostatEvent thermostatEvent = new PartitionedThermostatEvent();
			thermostatEvent.setStatus(1);
			thermostatEvent.setAlgorithmId(algorithm.getId());
			thermostatEvent.setThermostatId(thermostatId);
			thermostatEvent.setPhase(phaseval);
			thermostatEvent.getId().setEventSysTime(startTime);

			if (phaseval == 50) {
				thermostatEvent.setOldSetting(Double.valueOf("63.0"));
				thermostatEvent.setNewSetting(Double.valueOf("65.0"));
			} else {
				thermostatEvent.setOldSetting(null);
				thermostatEvent.setNewSetting(null);
			}

			events.add(thermostatEvent);
		}

		return events;
	}

	/**
	 * Gets the sets the point decrease.
	 * 
	 * @param thermostatId
	 *            the thermostat id
	 * @param startTime
	 *            the start time
	 * @param changeVal
	 *            the change val
	 * @return the sets the point decrease
	 */
	private List<PartitionedThermostatEvent> getSetPointDecrease(
			Integer thermostatId, Calendar startTime, int changeVal) {

		setMOBlackoutEnd(thermostatId);

		Algorithm algorithm = new Algorithm();
		algorithm.setId(MANUAL_OVERRIDE);
		int phase = 0;

		List<PartitionedThermostatEvent> events = new ArrayList<PartitionedThermostatEvent>();
		for (int i = 1; i <= -changeVal; i++) {
			PartitionedThermostatEvent thermostatEvent = new PartitionedThermostatEvent();
			thermostatEvent.setAlgorithmId(algorithm.getId());
			thermostatEvent.setThermostatId(thermostatId);
			thermostatEvent.setPhase(phase);
			thermostatEvent.getId().setEventSysTime(startTime);
			thermostatEvent.setDeltaEE((double) -i);

			Double setPointChange = getBaseTemp() + Double.valueOf(i);

			thermostatEvent.setOldSetting(getBaseTemp());
			thermostatEvent.setNewSetting(setPointChange);
			thermostatEvent.setStatus(1);
			events.add(thermostatEvent);
		}

		return events;
	}

	/**
	 * Gets the sets the point increase.
	 * 
	 * @param thermostatId
	 *            the thermostat id
	 * @param startTime
	 *            the start time
	 * @param changeVal
	 *            the change val
	 * @return the sets the point increase
	 */
	private List<PartitionedThermostatEvent> getSetPointIncrease(
			Integer thermostatId, Calendar startTime, int changeVal) {

		setMOBlackoutEnd(thermostatId);

		Algorithm algorithm = new Algorithm();
		algorithm.setId(MANUAL_OVERRIDE);
		int phase = 0;

		List<PartitionedThermostatEvent> events = new ArrayList<PartitionedThermostatEvent>();
		for (int i = 1; i <= changeVal; i++) {
			PartitionedThermostatEvent thermostatEvent = new PartitionedThermostatEvent();
			thermostatEvent.setAlgorithmId(algorithm.getId());
			thermostatEvent.setThermostatId(thermostatId);
			thermostatEvent.setPhase(phase);
			thermostatEvent.getId().setEventSysTime(startTime);
			thermostatEvent.setDeltaEE((double) i);

			Double setPointChange = getBaseTemp() + Double.valueOf(i);

			thermostatEvent.setOldSetting(getBaseTemp());
			thermostatEvent.setNewSetting(setPointChange);
			thermostatEvent.setStatus(1);
			events.add(thermostatEvent);
		}

		return events;
	}

	/**
	 * Gets the sPO events.
	 * 
	 * @param thermostatId
	 *            the thermostat id
	 * @param position
	 *            the position
	 * @return the sPO events
	 */
	private List<PartitionedThermostatEvent> getSPOEvents(Integer thermostatId,
			int position) {

		Calendar currentUTCTime = DateUtil.getUTCCalendar();

		List<PartitionedThermostatEvent> events = new ArrayList<PartitionedThermostatEvent>();
		LOGGER.info("Blackout End/current "
				+ DateUtil.format(moBlackoutEndUTCTime,
						DateUtil.DATE_FMT_FULL_TZ) + " , "
				+ DateUtil.format(currentUTCTime, DateUtil.DATE_FMT_FULL_TZ));
		if (moBlackoutEndUTCTime == null
				|| currentUTCTime.after(moBlackoutEndUTCTime)) {

			for (int i = 0; position > 0 && i < jobDataList.size(); ++i) {

				MockJobData block = jobDataList.get(i);

				for (int j = 0; position > (i + j) && j < 2; ++j) {

					Algorithm algorithm = new Algorithm();
					if (mode.equals("Cool")) {
						algorithm.setId(SPO_COOL);
					} else if (mode.equals("Heat")) {
						algorithm.setId(SPO_HEAT);
					}

					PartitionedThermostatEvent thermostatEvent = new PartitionedThermostatEvent();
					thermostatEvent.setAlgorithmId(algorithm.getId());
					thermostatEvent.setThermostatId(thermostatId);
					thermostatEvent.setPhase(0);

					Calendar utc = null;
					if (j == 0) {
						if (moBlackoutEndUTCTime != null) {
							utc = currentUTCTime;
						} else {
							utc = DateUtil.getUTCCalendar(block.getStart());
						}
						thermostatEvent.setDeltaEE(Double.valueOf(""
								+ block.getDelta()));
					} else {
						utc = DateUtil.getUTCCalendar(block.getEnd());
						thermostatEvent.setDeltaEE(0.0);
					}

					utc.add(Calendar.SECOND, 45);
					thermostatEvent.getId().setEventSysTime(utc);
					thermostatEvent.setOldSetting(baseTemp);
					if (mode.equals("Cool")) {
						thermostatEvent.setNewSetting(baseTemp
								+ block.getDelta());
					} else if (mode.equals("Heat")) {
						thermostatEvent.setNewSetting(baseTemp
								- block.getDelta());
					}
					thermostatEvent.setStatus(1);
					events.add(thermostatEvent);

					moBlackoutEndUTCTime = null;
				}
			}
		}

		return events;
	}

	/**
	 * Gets the sT events.
	 * 
	 * @param thermostatId
	 *            the thermostat id
	 * @param startTime
	 *            the start time
	 * @param endTime
	 *            the end time
	 * @return the sT events
	 */
	private List<PartitionedThermostatEvent> getSTEvents(Integer thermostatId,
			Calendar startTime, Calendar endTime) {

		List<PartitionedThermostatEvent> events = new ArrayList<PartitionedThermostatEvent>();
		int i = 0;
		while (startTime.before(endTime)) {

			startTime.add(Calendar.MINUTE, 4);

			Algorithm algorithm = new Algorithm();
			if (mode.equals("Cool")) {
				algorithm.setId(ST3_COOL);
			} else if (mode.equals("Heat")) {
				algorithm.setId(ST3_HEAT);
			}

			PartitionedThermostatEvent thermostatEvent = new PartitionedThermostatEvent();
			thermostatEvent.setAlgorithmId(algorithm.getId());
			thermostatEvent.setThermostatId(thermostatId);
			thermostatEvent.setPhase(i);
			thermostatEvent.getId().setEventSysTime(
					(Calendar) startTime.clone());
			thermostatEvent.setDeltaEE((double) i);
			thermostatEvent.setOldSetting(baseTemp);
			if (mode.equals("Cool")) {
				thermostatEvent.setNewSetting(baseTemp + i);
			} else if (mode.equals("Heat")) {
				thermostatEvent.setNewSetting(baseTemp - i);
			}

			thermostatEvent.setStatus(1);
			events.add(thermostatEvent);

			i = i + 1;
			i = i % 3;
		}

		return events;
	}

	/**
	 * Load events.
	 * 
	 * @param events
	 *            the events
	 */
	private void loadEvents(List<PartitionedThermostatEvent> events) {

		if (events != null && events.size() > 0) {
			for (PartitionedThermostatEvent event : events) {
				PartitionedThermostatEvent existing = timeEventMap.get(event
						.getId().getEventSysTime());
				if (existing == null
						|| event.getAlgorithmId() != existing.getAlgorithmId()) {
					if (existing != null) {
						event.getId().getEventSysTime().add(Calendar.SECOND, 1);
					}
					timeEventMap.put(event.getId().getEventSysTime(), event);
				}
			}
		}

		events = null;
	}

	/**
	 * Load spo events.
	 * 
	 * @param thermostatId
	 *            the thermostat id
	 */
	private void loadSPOEvents(Integer thermostatId) {

		Set<Calendar> times = timeEventMap.keySet();
		Calendar lastTime = null;
		for (Calendar time : times) {
			lastTime = time;
		}

		boolean generated = false;
		if (!timeEventMap.isEmpty()) {
			if (mode.equals("Cool")) {
				generated = timeEventMap.get(lastTime).getAlgorithmId() == SPO_COOL;
			} else if (mode.equals("Heat")) {
				generated = timeEventMap.get(lastTime).getAlgorithmId() == SPO_HEAT;
			}
		}

		if (timeEventMap.isEmpty() || !generated) {
			int position = getCurrentPosition(thermostatId);
			if (position >= 10 && position > jobDataList.size()) {
				position /= 10;
			}

			MockEvent me = new MockEvent(this, MockEventType.SPO);
			me.setProperty("position", position);
			mockConsumer.fireEvent(me);
		}
	}

	/**
	 * Load st events.
	 * 
	 * @param thermostatId
	 *            the thermostat id
	 */
	private void loadSTEvents(Integer thermostatId) {

		Set<Calendar> times = timeEventMap.keySet();
		Calendar lastTime = null;
		for (Calendar time : times) {
			lastTime = time;
		}

		boolean generated = false;
		if (!timeEventMap.isEmpty()) {
			if (mode.equals("Cool")) {
				generated = timeEventMap.get(lastTime).getAlgorithmId() == ST3_COOL;
			} else if (mode.equals("Heat")) {
				generated = timeEventMap.get(lastTime).getAlgorithmId() == ST3_HEAT;
			}
		}

		if (timeEventMap.isEmpty() || !generated) {
			int position = getCurrentPosition(thermostatId);
			int gap = (position >= 10) ? position / 10 : 0;

			Calendar endTime = DateUtil.getUTCCalendar();
			Calendar startTime = null;

			if (position == 0) {
				startTime = (Calendar) endTime.clone();
				startTime.add(Calendar.MINUTE, -10);
			} else if (position > jobDataList.size()) {
				startTime = jobDataList.get(jobDataList.size() - 1).getEnd();
				startTime = DateUtil.getUTCCalendar(startTime);
			} else if (gap > 0) {
				startTime = jobDataList.get(gap - 1).getEnd();
				startTime = DateUtil.getUTCCalendar(startTime);
			}

			if (startTime != null) {
				MockEvent me = new MockEvent(this, MockEventType.ST);
				me.setProperty("start", startTime);
				me.setProperty("end", endTime);
				mockConsumer.fireEvent(me);
			}
		}
	}

	/**
	 * Load events.
	 * 
	 * @param thermostatId
	 *            the thermostat id
	 */
	private void loadEvents(Integer thermostatId) {

		if (jobData != null) {
			Set<Calendar> times = timeEventMap.keySet();
			Calendar lastTime = null;
			for (Calendar time : times) {
				lastTime = time;
			}

			if (timeEventMap.isEmpty()
					|| timeEventMap.get(lastTime).getAlgorithmId() != SET_AWAY) {
				loadSPOEvents(thermostatId);
				loadSTEvents(thermostatId);
			}
		}
	}

	/**
	 * Prints the mock thermostat events.
	 * 
	 * @param me
	 *            the me
	 */
	private void printMockThermostatEvents(MockEvent me) {

		Set<Calendar> times = timeEventMap.keySet();
		LOGGER.info("Mock Event processed is " + me.getType().toString());
		LOGGER.info("Time                        , Algorithm");
		LOGGER.info("---------------------------------------");
		for (Calendar time : times) {
			LOGGER.info(DateUtil.format(time, DateUtil.DATE_FMT_FULL_TZ) + ", "
					+ timeEventMap.get(time).getAlgorithmId());
		}
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

		log("Save thermostat algo control list.", true);
		return algoControlList.addAll(AlgoControlList);
	}

	/**
	 * Generate job data for thermostat.
	 * 
	 * @param thermostatId
	 *            the thermostat id
	 * @return the jSON array
	 * @see com.ecofactor.qa.automation.algorithm.service.DataService#generateJobDataForThermostat(java.lang.Integer,
	 *      java.lang.String)
	 */
	@Override
	public JSONArray generateJobDataForThermostat(Integer thermostatId) {

		return getJobData(thermostatId);
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

		Algorithm algorithm = new Algorithm();
		algorithm.setId(algoId);
		algorithm.setPhaseDuration(120);

		return algorithm;
	}

	/**
	 * Update thermostat algo controller.
	 * 
	 * @param AlgoControlList
	 *            the algo control list
	 * @return true, if successful
	 * @see com.ecofactor.qa.automation.algorithm.service.DataService#updateThermostatAlgoController(java.util.List)
	 */
	@Override
	public boolean updateThermostatAlgoControllerList(
			List<ThermostatAlgorithmController> AlgoControlList) {

		log("Update thermostat algo control list.", true);
		algoControlList.clear();
		return algoControlList.addAll(AlgoControlList);
	}

	/**
	 * Update algo control to inactive.
	 * 
	 * @param thermostatId
	 *            the thermostat id
	 * @param algoId
	 *            the algo id
	 * @see com.ecofactor.qa.automation.algorithm.service.DataService#updateAlgoControlToInactive(java.lang.Integer,
	 *      int)
	 */
	@Override
	public void updateAlgoControlToInactive(Integer thermostatId, int algoId) {

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

	}

	/**
	 * Sets the mO blackout end.
	 * 
	 * @param thermostatId
	 *            the new mO blackout end
	 */
	private void setMOBlackoutEnd(Integer thermostatId) {

		int block = getCurrentPosition(thermostatId);
		if (block > 0 && block <= jobDataList.size()) {
			MockJobData job = jobDataList.get(block - 1);
			int secs = job.getBlackout();
			moBlackoutEndUTCTime = DateUtil.getUTCCalendar();
			moBlackoutEndUTCTime.add(Calendar.SECOND, secs);
		}
	}

	/**
	 * Find active spo algo control.
	 * 
	 * @param thermostatId
	 *            the thermostat id
	 * @param algoId
	 *            the algo id
	 * @return the thermostat algorithm controller
	 * @see com.ecofactor.qa.automation.algorithm.service.DataService#findActiveSPOAlgoControl(java.lang.Integer,
	 *      int)
	 */
	@Override
	public ThermostatAlgorithmController findActiveSPOAlgoControl(
			Integer thermostatId, int algoId) {

		ThermostatAlgorithmController algoControl = null;
		int position = getCurrentPosition(thermostatId);
		if (position >= 0 && position < algoControlList.size()) {
			algoControl = algoControlList.get(position - 1);
		}

		return algoControl;
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

		return null;
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
	 *      int, double)
	 */
	@Override
	public void verifyTemperatureChangesInRangeData(int thermostatId,
			int algoId, double temperature) {

	}

	/**
	 * Find active event algo control.
	 * 
	 * @param thermostatId
	 *            the thermostat id
	 * @param algoId
	 *            the algo id
	 * @return the thermostat algorithm controller
	 * @see com.ecofactor.qa.automation.algorithm.service.DataService#findActiveEventAlgoControl(java.lang.Integer,
	 *      int)
	 */
	@Override
	public ThermostatAlgorithmController findActiveEventAlgoControl(
			Integer thermostatId, int algoId) {

		return null;
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
	 * @see com.ecofactor.qa.automation.algorithm.service.DataService#listAlgoProcessedEventsAndTimeRange(java.lang.Integer,
	 *      java.util.Calendar, java.util.Calendar)
	 */
	@Override
	public List<PartitionedThermostatEvent> listAlgoProcessedEventsAndTimeRange(
			Integer thermostatId, Calendar startTime, Calendar endTime) {

		return null;
	}

	/**
	 * Gets the subscribed algo name.
	 * 
	 * @param thermostatId
	 *            the thermostat id
	 * @return the subscribed algo name
	 * @see com.ecofactor.qa.automation.algorithm.service.DataService#getSubscribedAlgoName(java.lang.Integer)
	 */
	@Override
	public String[] getSubscribedAlgoName(Integer thermostatId) {

		return null;
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

		return 0;
	}

	@Override
	public List<ThermostatAlgorithmController> listAlgoControlForAlgorithm(
			Integer thermostatId, int algoId) {

		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long millisecondsForSPOStart(Integer thermostatId, int algoId) {

		// TODO Auto-generated method stub
		return 0;
	}
}
