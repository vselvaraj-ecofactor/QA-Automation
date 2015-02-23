/*
 * DataService.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.newapp.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;

import com.ecofactor.common.pojo.Algorithm;
import com.ecofactor.common.pojo.Thermostat;
import com.ecofactor.common.pojo.ThermostatAlgorithmController;
import com.ecofactor.common.pojo.ThermostatJob;
import com.ecofactor.common.pojo.timeseries.PartitionedThermostatEvent;
import com.ecofactor.common.pojo.timeseries.PartitionedThermostatRangeData;

// TODO: Auto-generated Javadoc
/**
 * SPOService defines the inteface for data service.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public interface DataService {

    /**
     * Clear.
     */
    public void clear();

    /**
     * Find by algorithm id.
     * @param algoId the algo id
     * @return the algorithm
     */
    public Algorithm findByAlgorithmId(Integer algoId);

    /**
     * Find active algorithm id.
     * @param thermostatId the thermostat id
     * @return the int
     */
    public int findActiveAlgorithmId(Integer thermostatId);

    /**
     * Find bythermostat id.
     * @param thermostatId the thermostat id
     * @return the thermostat
     */
    public Thermostat findBythermostatId(Integer thermostatId);

    /**
     * Find latest thermostat event.
     * @param thermostatId the thermostat id
     * @return the thermostat event
     */
    public PartitionedThermostatEvent findLatestThermostatEvent(Integer thermostatId);

    /**
     * Generate job data for thermostat.
     * @param thermostatId the thermostat id
     * @return the jSON array
     */
    public JSONArray generateJobDataForThermostat(Integer thermostatId);

    /**
     * Gets the algo control list.
     * @param thermostatId the thermostat id
     * @param algoId the algo id
     * @return the algo control list
     */
    public List<ThermostatAlgorithmController> generateAlgoControlList(Integer thermostatId, Integer algoId);

    /**
     * Gets the base temp.
     * @return the base temp
     */
    public Double getBaseTemp();

    /**
     * Gets the current base temp.
     * @param thermostatId the thermostat id
     * @param mode the mode
     * @return the current base temp
     */
    public double getCurrentBaseTemp(Integer thermostatId, String mode);

    /**
     * Gets the job data.
     * @param thermostatId the thermostat id
     * @return the job data
     */
    public JSONArray getJobData(Integer thermostatId);

    /**
     * Gets the last completed job data.
     * @param thermostatId the thermostat id
     * @return the last completed job data
     */
    public JSONArray getLastCompletedJobData(Integer thermostatId);

    /**
     * Gets the last completed thermostat job.
     * @param thermostatId the thermostat id
     * @return the last completed thermostat job
     */
    public ThermostatJob getLastCompletedThermostatJob(Integer thermostatId);

    /**
     * Gets the test.
     * @return the test
     */
    public String getTest();

    /**
     * Inits the.
     * @param testMethod the test method
     * @param mode the mode
     * @param thermostatId the thermostat id
     */
    public void init(String testMethod, String mode, int thermostatId);

    /**
     * Checks if is sPO block active.
     * @param thermostatId the thermostat id
     * @param algoId the algo id
     * @return true, if is sPO block active
     */
    public boolean isSPOBlockActive(Integer thermostatId, int algoId);

    /**
     * List algo phases.
     * @param thermostatId the thermostat id
     * @param algoId the algo id
     * @return the list
     */
    public List<ThermostatAlgorithmController> listActiveAlgoControl(Integer thermostatId, int algoId);

    /**
     * List algo control for algorithm.
     * @param thermostatId the thermostat id
     * @param algoId the algo id
     * @return the list
     */
    public List<ThermostatAlgorithmController> listAlgoControlForAlgorithm(Integer thermostatId, int algoId);

    /**
     * List events.
     * @param thermostatId the thermostat id
     * @param startTime the start time
     * @return the list
     */
    public List<PartitionedThermostatEvent> listEvents(Integer thermostatId, Calendar startTime);

    /**
     * List events.
     * @param thermostatId the thermostat id
     * @param startTime the start time
     * @param endTime the end time
     * @return the list
     */
    public List<PartitionedThermostatEvent> listEvents(Integer thermostatId, Calendar startTime, Calendar endTime);

    /**
     * List events.
     * @param thermostatId the thermostat id
     * @param algoId the algo id
     * @param startTime the start time
     * @return the list
     */
    public List<PartitionedThermostatEvent> listEvents(Integer thermostatId, int algoId, Calendar startTime);

    /**
     * List events.
     * @param thermostatId the thermostat id
     * @param algoId the algo id
     * @param startTime the start time
     * @param endTime the end time
     * @return the list
     */
    public List<PartitionedThermostatEvent> listEvents(Integer thermostatId, int algoId, Calendar startTime, Calendar endTime);

    /**
     * Time for spo block.
     * @param thermostatId the thermostat id
     * @param algoId the algo id
     * @return the long
     */
    public long millisecondsForSPOBlock(Integer thermostatId, int algoId);

    /**
     * Milliseconds for spo block end.
     * @param thermostatId the thermostat id
     * @param algoId the algo id
     * @return the long
     */
    public long millisecondsForSPOBlockEnd(Integer thermostatId, int algoId);


    /**
     * Milliseconds for spo start.
     * @param thermostatId the thermostat id
     * @param algoId the algo id
     * @return the long
     */
    public long millisecondsForSPOStart(Integer thermostatId, int algoId);

    /**
     * Save thermostat algo controll.
     * @param AlgoControlList the algo control list
     * @return true, if successful
     */
    public boolean saveThermostatAlgoController(List<ThermostatAlgorithmController> AlgoControlList);

    /**
     * Update thermostat algo controller.
     * @param algoControlList the algo control list
     * @return true, if successful
     */
    public boolean updateThermostatAlgoControllerList(List<ThermostatAlgorithmController> algoControlList);

    /**
     * Update exclude start and end for st.
     * @param thermostatId the thermostat id
     * @param algoId the algo id
     * @param excludeStartTime the exclude start time
     * @param excludeEndTime the exclude end time
     * @return true, if successful
     */
    public void updateExcludeStartAndEndForST(Integer thermostatId, int algoId, Date excludeStartTime, Date excludeEndTime);

    /**
     * Sets the base temp.
     * @param baseTemp the base temp
     * @return the double
     */
    public void setBaseTemp(Double baseTemp);

    /**
     * Sets the test.
     * @param testName the new test
     */
    public void setTest(String testName);

    /**
     * Wait until event fired for algo.
     * @param thermostatId the thermostat id
     * @param algoId the algo id
     */
    public void waitUntilEventFiredForAlgo(Integer thermostatId, int algoId);

    /**
     * Wait until event processed.
     * @param thermostatId the thermostat id
     * @param algoId the algo id
     */
    public void waitAndVerifyEventProcessed(Integer thermostatId, int algoId);

    /**
     * Wait and verify event processed.
     * @param thermostatId the thermostat id
     * @param algoId the algo id
     * @param startTime the start time
     * @param endTime the end time
     */
    public void verifyEventProcessed(Integer thermostatId, int algoId, Calendar startTime, Calendar endTime);

    /**
     * Update algo control to inactive.
     * @param thermostatId the thermostat id
     * @param algoId the algo id
     */
    public void updateAlgoControlToInactive(Integer thermostatId, int algoId);

    /**
     * Wait until st events fired.
     * @param thermostatId the thermostat id
     * @param algoId the algo id
     */
    public void waitAndVerifySTEventsFired(Integer thermostatId, int algoId);

    /**
     * Wait and verify temperature change.
     * @param thermostatId the thermostat id
     * @param algoId the algo id
     * @param targetTemperature the target temperature
     */
    public void waitAndVerifyTemperatureChange(Integer thermostatId, int algoId, double targetTemperature);

    /**
     * Find active spo algo control.
     * @param thermostatId the thermostat id
     * @param algoId the algo id
     * @return the thermostat algorithm controller
     */
    public ThermostatAlgorithmController findActiveSPOAlgoControl(Integer thermostatId, int algoId);

    /**
     * Verify temperature changes in range data.
     * @param thermostatId the thermostat id
     * @param algoId the algo id
     * @param temperature the temperature
     */
    public void verifyTemperatureChangesInRangeData(int thermostatId, int algoId, double temperature);

    /**
     * Check gateway.
     * @param thermostatId the thermostat id
     */
    public void checkGateway(Integer thermostatId);

    /**
     * List by thermostat and start time range.
     * @param thermostatId the thermostat id
     * @param startTime the start time
     * @param endTime the end time
     * @return the list
     */
    public List<PartitionedThermostatRangeData> listByThermostatAndStartTimeRange(Integer thermostatId, Calendar startTime, Calendar endTime);

    /**
     * Find active event algo control.
     * @param thermostatId the thermostat id
     * @param algoId the algo id
     * @return the thermostat algorithm controller
     */
    public ThermostatAlgorithmController findActiveEventAlgoControl(Integer thermostatId, int algoId);

    /**
     * List algo processed events and time range.
     * @param thermostatId the thermostat id
     * @param startTime the start time
     * @param endTime the end time
     * @return the list
     */
    public List<PartitionedThermostatEvent> listAlgoProcessedEventsAndTimeRange(Integer thermostatId, Calendar startTime, Calendar endTime);

    /**
     * Gets the subscribed algo name.
     * @param thermostatId the thermostat id
     * @return the subscribed algo name
     */
    public String[] getSubscribedAlgoName(Integer thermostatId);

}
