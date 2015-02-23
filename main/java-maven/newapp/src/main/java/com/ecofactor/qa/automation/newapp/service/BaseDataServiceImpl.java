/*
 * BaseDataServiceImpl.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
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
import java.util.List;
import java.util.TimeZone;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;

import com.ecofactor.common.pojo.Algorithm;
import com.ecofactor.common.pojo.Status;
import com.ecofactor.common.pojo.ThermostatAlgorithmController;
import com.ecofactor.common.pojo.timeseries.PartitionedThermostatEvent;
import com.ecofactor.qa.automation.dao.util.DataUtil;
import com.ecofactor.qa.automation.util.DateUtil;
import com.ecofactor.qa.automation.util.DriverConfig;
import com.ecofactor.qa.automation.util.mock.MockJobData;

/**
 * The Class BaseDataServiceImpl.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public abstract class BaseDataServiceImpl implements DataService {

    private final static int SPO_COOL = 190;
    private final static int SPO_HEAT = 191;
    private final static String ACTOR_SPO = "SPO";



    /**
     * Wait until event fired for algo.
     * @param thermostatId the thermostat id
     * @param algoId the algo id
     * @see com.ecofactor.qa.automation.algorithm.service.DataService#waitUntilEventFiredForAlgo(java.lang.Integer,
     *      int)
     */
    public void waitUntilEventFiredForAlgo(Integer thermostatId, int algoId) {

        DriverConfig.setLogString("Wait for event Thermostat : " + thermostatId + ", Algo Id : " + algoId, true);
        long nextPhaseWait = 0;
        int count = 0;
        do {
            largeWait();
            PartitionedThermostatEvent event = findLatestThermostatEvent(thermostatId);
            if (event != null && event.getAlgorithmId() == algoId) {
                nextPhaseWait = 1;
            } else {
                nextPhaseWait = -1;
            }
            count++;
        } while (nextPhaseWait < 0 && count <= 40);
    }

    /**
     * @param thermostatId
     * @param algoId
     * @param startTime
     * @param endTime
     * @see com.ecofactor.qa.automation.algorithm.service.DataService#verifyEventProcessed(java.lang.Integer,
     *      int, java.util.Calendar, java.util.Calendar)
     */
    public void verifyEventProcessed(Integer thermostatId, int algoId, Calendar startTime, Calendar endTime) {

        DriverConfig.setLogString("Wait for event Thermostat : " + thermostatId + ", Algo Id : " + algoId + ", Start : " + DateUtil.format(startTime, DateUtil.DATE_FMT_FULL_TZ)
                + " End : " + DateUtil.format(endTime, DateUtil.DATE_FMT_FULL_TZ), true);
        boolean eventProcessed = false;

        List<PartitionedThermostatEvent> events = listEvents(thermostatId, startTime, endTime);
        if (events != null) {
            DataUtil.printEventTableGrid(events);
            for (PartitionedThermostatEvent event : events) {
                if (event.getAlgorithmId().intValue() == algoId) {
                    if (algoId == -20 || event.getStatus() == 1) {
                        eventProcessed = true;
                        break;
                    }
                }
            }
        }

        Assert.assertTrue(eventProcessed, "Processed Event not found for algo " + algoId);
    }

    /**
     * @param thermostatId
     * @param algoId
     * @see com.ecofactor.qa.automation.algorithm.service.DataService#waitAndVerifyEventProcessed(java.lang.Integer,
     *      int)
     */
    public void waitAndVerifyEventProcessed(Integer thermostatId, int algoId) {

        DriverConfig.setLogString("Wait and verify algorithm event " + algoId + " fired for Thermostat : " + thermostatId, true);
        boolean eventProcessed = false;

        Algorithm algorithm = findByAlgorithmId(algoId);
        Integer phaseDuration = (int) ((2.5) * (int) (algorithm.getPhaseDuration()));
        String startTimeString = DateUtil.getUTCCurrentTimeStamp();
        Calendar startTime = DateUtil.parseToCalendar(startTimeString, DateUtil.DATE_FMT_FULL);

        String endTimeString = DateUtil.getUTCCurrentTimeStamp();
        Calendar endTime = DateUtil.parseToCalendar(endTimeString, DateUtil.DATE_FMT_FULL);

        if (algoId == 190 || algoId == 191) {
            endTime.add(Calendar.MINUTE, 15);
        } else {
            endTime.add(Calendar.SECOND, phaseDuration);
        }

        String currentTimeString = DateUtil.getUTCCurrentTimeStamp();
        Calendar currentTime = DateUtil.parseToCalendar(currentTimeString, DateUtil.DATE_FMT_FULL);
        DriverConfig.setLogString("Current Time : " + DateUtil.format(currentTime, DateUtil.DATE_FMT_FULL) + ", Wait until : " + DateUtil.format(endTime, DateUtil.DATE_FMT_FULL)
                + " to check algorithm events in event table", true);

        do {
            smallWait();
            List<PartitionedThermostatEvent> events = listEvents(thermostatId, startTime, endTime);
            if (events != null) {
                for (PartitionedThermostatEvent event : events) {
                    if (event.getAlgorithmId().intValue() == algoId) {
                        if (algoId == -20 || event.getStatus() == 1) {
                            DataUtil.printEventTableGrid(events);
                            eventProcessed = true;
                            break;
                        }
                    }
                }
            }

            if (eventProcessed) {
                break;
            }

            currentTimeString = DateUtil.getUTCCurrentTimeStamp();
            currentTime = DateUtil.parseToCalendar(currentTimeString, DateUtil.DATE_FMT_FULL);

        } while (currentTime.before(endTime));

        Assert.assertTrue(eventProcessed, "Processed Event not found for algo " + algoId);
    }

    /**
     * Wait and verify temperature change.
     * @param thermostatId the thermostat id
     * @param algoId the algo id
     * @param targetTemperature the target temperature
     */
    public void waitAndVerifyTemperatureChange(Integer thermostatId, int algoId, double targetTemperature) {

        DriverConfig.setLogString("Wait for event Thermostat : " + thermostatId + ", Algo Id : " + algoId + ", Desired Target : " + targetTemperature, true);
        boolean eventProcessed = false;
        largeWait();

        Algorithm algorithm = findByAlgorithmId(algoId);
        Integer phaseDuration = (int) ((3) * (int) (algorithm.getPhaseDuration()));
        String startTimeString = DateUtil.getUTCCurrentTimeStamp();
        Calendar startTime = DateUtil.parseToCalendar(startTimeString, DateUtil.DATE_FMT_FULL);

        String endTimeString = DateUtil.getUTCCurrentTimeStamp();
        Calendar endTime = DateUtil.parseToCalendar(endTimeString, DateUtil.DATE_FMT_FULL);

        if (algoId == 190 || algoId == 191) {
            endTime.add(Calendar.MINUTE, 15);
        } else {
            endTime.add(Calendar.SECOND, phaseDuration);
        }

        DriverConfig.setLogString("Wait for Maximum time till : " + DateUtil.format(endTime, DateUtil.DATE_FMT_FULL), true);

        String currentTimeString = DateUtil.getUTCCurrentTimeStamp();
        Calendar currentTime = DateUtil.parseToCalendar(currentTimeString, DateUtil.DATE_FMT_FULL);
        do {
            smallWait();
            List<PartitionedThermostatEvent> events = listEvents(thermostatId, startTime, endTime);
            if (events != null) {
                for (PartitionedThermostatEvent event : events) {
                    if (event.getAlgorithmId().intValue() == algoId) {
                        if ((algoId == -20 || event.getStatus() == 1) && event.getNewSetting() == targetTemperature) {
                            DataUtil.printEventTableGrid(events);
                            eventProcessed = true;
                            break;
                        }
                    }
                }
            }

            if (eventProcessed) {
                break;
            }

            currentTimeString = DateUtil.getUTCCurrentTimeStamp();
            currentTime = DateUtil.parseToCalendar(currentTimeString, DateUtil.DATE_FMT_FULL);

        } while (currentTime.before(endTime));

        Assert.assertTrue(eventProcessed, "Processed Event not found for algo " + algoId);
    }

    /**
     * Wait and verify st events fired.
     * @param thermostatId the thermostat id
     * @param algoId the algo id
     * @see com.ecofactor.qa.automation.algorithm.service.DataService#waitAndVerifySTEventsFired(java.lang.Integer,
     *      int)
     */
    public void waitAndVerifySTEventsFired(Integer thermostatId, int algoId) {

        DriverConfig.setLogString("Wait for Thermostat : " + thermostatId + ", Algo Id : " + algoId + " in algo control table", true);

        boolean isNextPhaseFuture = false;
        Algorithm algorithm = findByAlgorithmId(algoId);
        Integer phaseDuration = (int) ((2.5) * (int) (algorithm.getPhaseDuration()));
        Calendar endTime = DateUtil.getUTCCalendar();
        endTime.add(Calendar.SECOND, phaseDuration);

        Calendar currentTime = DateUtil.getUTCCalendar();
        do {
            DriverConfig.setLogString("Current time : " + DateUtil.format(currentTime, DateUtil.DATE_FMT_FULL_TZ) +", " + "End time : " + DateUtil.format(endTime, DateUtil.DATE_FMT_FULL_TZ), true);
            largeWait();

            verifyAlgorithmStatus(thermostatId, algoId);

            List<ThermostatAlgorithmController> activeAlgoControlList = listActiveAlgoControl(thermostatId, algoId);
            if (activeAlgoControlList != null && activeAlgoControlList.size() > 0) {
                currentTime = DateUtil.getUTCCalendar();
                ThermostatAlgorithmController algoController = activeAlgoControlList.get(0);
                Calendar nextPhaseCalendar = algoController.getNextPhaseTime();
                String nextPhaseTimeStamp = DateUtil.format(nextPhaseCalendar, DateUtil.DATE_FMT_FULL);
                nextPhaseCalendar = DateUtil.parseToUTCCalendar(nextPhaseTimeStamp, DateUtil.DATE_FMT_FULL);

                DriverConfig.setLogString("Next Phase time : " + DateUtil.format(nextPhaseCalendar, DateUtil.DATE_FMT_FULL_TZ), true);
                if (nextPhaseCalendar.after(currentTime)) {
                    isNextPhaseFuture = true;
                    break;
                }
            }
            currentTime = DateUtil.getUTCCalendar();
        } while (currentTime.before(endTime));

        if (!isNextPhaseFuture) {
            DriverConfig.setLogString("ST Events shows past time for thermostat : " + thermostatId, true);
        }
        Assert.assertTrue(isNextPhaseFuture, "Waited for a period and next phase time shows past record for thermostat: " + thermostatId);
    }

    /**
     * Convert to json job data.
     * @param jobDataList the job data list
     * @param timezone the timezone
     * @return the jSON array
     */
    protected JSONArray convertToJSONJobData(List<MockJobData> jobDataList, String timezone) {

        JSONArray jobData = new JSONArray();
        int i = 0;
        for (MockJobData mockJobData : jobDataList) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("start", DateUtil.format(mockJobData.getStart(), "HH:mm:ss"));
                jsonObject.put("end", DateUtil.format(mockJobData.getEnd(), "HH:mm:ss"));
                jsonObject.put("moBlackOut", mockJobData.getBlackout());
                Calendar cuttOffTime = (Calendar) Calendar.getInstance(TimeZone.getTimeZone(timezone)).clone();
                cuttOffTime.set(Calendar.HOUR_OF_DAY, 0);
                cuttOffTime.set(Calendar.MINUTE, 0);
                cuttOffTime.set(Calendar.SECOND, 0);
                if (mockJobData.getCutoff() == null) {
                    jsonObject.put("moCutoff", DateUtil.format(cuttOffTime, "HH:mm:ss"));
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
        return jobData;
    }

    /**
     * Creates the algo control list.
     * @param jobData the job data
     * @param thermostatId the thermostat id
     * @param algoId the algo id
     * @return the list
     */
    protected List<ThermostatAlgorithmController> createAlgoControlList(JSONArray jobData, Integer thermostatId, Integer algoId) {

        Algorithm algorithm = findByAlgorithmId(algoId);
        List<ThermostatAlgorithmController> algoControlList = new ArrayList<ThermostatAlgorithmController>();

        int moBlackOut;
        double moRecovery;
        double deltaEE = 0;
        Calendar utcCalendar = null;
        Calendar startTime = null;
        Calendar endTime = null;
        Calendar moCutoffTime = null;
        JSONObject jsonObj;

        for (int i = 0; i < jobData.length(); i++) {

            try {

                jsonObj = jobData.getJSONObject(i);
                startTime = DateUtil.getUTCTimeZoneCalendar((String) jsonObj.get("start"));
                endTime = DateUtil.getUTCTimeZoneCalendar((String) jsonObj.get("end"));
                moCutoffTime = DateUtil.getUTCTimeZoneCalendar((String) jsonObj.get("moCutoff"));

                moBlackOut = jsonObj.getInt("moBlackOut");
                moRecovery = jsonObj.getInt("moRecovery");
                deltaEE = jsonObj.getDouble("deltaEE");

                ThermostatAlgorithmController algoControlPhase1 = new ThermostatAlgorithmController();
                algoControlPhase1.setStatus(Status.ACTIVE);
                algoControlPhase1.setThermostatId(thermostatId);
                algoControlPhase1.setAlgorithmId(algoId);

                String utcCalendarTimeStamp = DateUtil.format(startTime, DateUtil.DATE_FMT_FULL);
                utcCalendar = DateUtil.parseToCalendar(utcCalendarTimeStamp, DateUtil.DATE_FMT_FULL);

                // Next phase time
                algoControlPhase1.setNextPhaseTime(utcCalendar);

                // Execution start time.
                Calendar executionStartTime = (Calendar) utcCalendar.clone();
                executionStartTime.add(Calendar.MINUTE, -3);
                algoControlPhase1.setExecutionStartTimeUTC(executionStartTime);

                String endutcCalendarTimeStamp = DateUtil.format(endTime, DateUtil.DATE_FMT_FULL);
                Calendar endutcCalendar = DateUtil.parseToCalendar(endutcCalendarTimeStamp, DateUtil.DATE_FMT_FULL);

                // Execution end time.
                algoControlPhase1.setExecutionEndTimeUTC(endutcCalendar);

                String cuttoffutcCalendarTimeStamp = DateUtil.format(moCutoffTime, DateUtil.DATE_FMT_FULL);
                Calendar cutOffutcCalendar = DateUtil.parseToCalendar(cuttoffutcCalendarTimeStamp, DateUtil.DATE_FMT_FULL);

                // MO cut off time.
                algoControlPhase1.setMoCutOff(cutOffutcCalendar);

                algoControlPhase1.setPhase0Spt(deltaEE);
                algoControlPhase1.setPrevSpt(0.0);
                algoControlPhase1.setMoBlackOut(moBlackOut);
                algoControlPhase1.setMoRecovery(moRecovery);
                algoControlPhase1.setAlgorithm(algorithm);
                if (algoId == SPO_COOL || algoId == SPO_HEAT) {
                    algoControlPhase1.setActor(ACTOR_SPO);
                }

                algoControlList.add(algoControlPhase1);
                // second row
                ThermostatAlgorithmController algoControlPhase2 = new ThermostatAlgorithmController();

                algoControlPhase2 = algoControlPhase1.clone();

                endutcCalendarTimeStamp = DateUtil.format(endTime, DateUtil.DATE_FMT_FULL);
                endutcCalendar = DateUtil.parseToCalendar(endutcCalendarTimeStamp, DateUtil.DATE_FMT_FULL);

                if (algoId == SPO_COOL || algoId == SPO_HEAT) {
                    algoControlPhase2.setActor(ACTOR_SPO);
                }
                // Execution end time.
                algoControlPhase2.setExecutionStartTimeUTC(endutcCalendar);
                algoControlPhase2.setExecutionEndTimeUTC(null);
                algoControlPhase2.setNextPhaseTime(endutcCalendar);
                algoControlPhase2.setPhase0Spt(0.0);
                algoControlPhase2.setAlgorithm(algorithm);
                algoControlPhase2.setStatus(Status.ACTIVE);
                algoControlList.add(algoControlPhase2);

            } catch (JSONException | CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
        return algoControlList;
    }

    /**
     * Generate algo control list.
     * @param thermostatId the thermostat id
     * @param algoId the algo id
     * @return the list
     * @see com.ecofactor.qa.automation.algorithm.service.DataService#generateAlgoControlList(java.lang.Integer,
     *      java.lang.String)
     */
    @Override
    public List<ThermostatAlgorithmController> generateAlgoControlList(Integer thermostatId, Integer algoId) {

        JSONArray jobData = generateJobDataForThermostat(thermostatId);
        DataUtil.printMockSPOJobDataJson(jobData);
        List<ThermostatAlgorithmController> algoControlList = createAlgoControlList(jobData, thermostatId, algoId);
        return algoControlList;
    }

    /**
     * @param thermostatId
     * @see com.ecofactor.qa.automation.algorithm.service.DataService#checkGateway(java.lang.Integer)
     */
    @Override
    public void checkGateway(Integer thermostatId) {

        DriverConfig.setLogString("Check Any Gateway issues for thermostat: " + thermostatId, true);
        PartitionedThermostatEvent thEvent = findLatestThermostatEvent(thermostatId);
        if (thEvent.getAlgorithmId() == -911 || thEvent.getAlgorithmId() == -901) {
            Assert.fail("Gateway Issues in thermostat:" + thermostatId);
        }
    }

    /**
     * Verify algorithm status.
     * @param thermostatId the thermostat id
     * @param algoId the algo id
     */
    private void verifyAlgorithmStatus(Integer thermostatId, Integer algoId)
    {
        DriverConfig.setLogString("Verify Algo control status for thermostat :" + thermostatId, true);
        List<ThermostatAlgorithmController> algoControlList = listAlgoControlForAlgorithm(thermostatId, algoId);
        for (ThermostatAlgorithmController thermostatAlgorithmController : algoControlList) {

            DriverConfig.setLogString("Status for thermostat "+thermostatId+", "+ thermostatAlgorithmController.getStatus() + " in algo control", true);
            if(thermostatAlgorithmController.getStatus().toString().equalsIgnoreCase("PENDING"))
            {
                DriverConfig.setLogString("Status PENDING for thermostat :" + thermostatId + " in algo control", true);
                Assert.fail("Status PENDING for thermostat :" + thermostatId);
            }
        }
    }
}
