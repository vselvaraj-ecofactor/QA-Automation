/*
 * DataUtil.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.dao.util;

import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ecofactor.common.pojo.ThermostatAlgorithmController;
import com.ecofactor.common.pojo.ThermostatRangeData;
import com.ecofactor.common.pojo.timeseries.PartitionedThermostatEvent;
import com.ecofactor.common.pojo.timeseries.PartitionedThermostatRangeData;
import com.ecofactor.qa.automation.dao.ThermostatEventDao;
import com.ecofactor.qa.automation.dao.ThermostatRangeDataDao;
import com.ecofactor.qa.automation.util.DateUtil;
import com.ecofactor.qa.automation.util.DriverConfig;
import com.ecofactor.qa.automation.util.TestMethodDetails;
import com.google.inject.Inject;

/**
 * The Class DataUtil.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class DataUtil {

    @Inject
    private static ThermostatRangeDataDao thRangeDataDao;

    @Inject
    private static ThermostatEventDao thEventDao;

    /**
     * Prints the event table grid.
     * @param thermostatId the thermostat id
     * @param startTime the start time
     * @param endTime the end time
     */
    public static void printEventTableGrid(Integer thermostatId, Calendar startTime,
            Calendar endTime) {

        List<PartitionedThermostatEvent> eventList = thEventDao
                .listByThermostatAndEventSysTimeRange(thermostatId, startTime, endTime);
        if (eventList != null && !eventList.isEmpty()) {
            DriverConfig.setLogString("Event Table Grid", true);
            DriverConfig.setLogString(
                    "SQL QUERY: Select * from ef_thermostat_event where thermostat_id = "
                            + thermostatId + " AND event_sys_time BETWEEN '"
                            + DateUtil.format(startTime, DateUtil.DATE_FMT) + "' AND '"
                            + DateUtil.format(endTime, DateUtil.DATE_FMT) + "'", true);
            DriverConfig
                    .setLogString(
                            "=======================================================================================================================",
                            true);
            DriverConfig
                    .setLogString(
                            "| Thermostat Id |    Event SysTime        | Algorithm Id | Event Phase  | Old Setting | New Setting  |      Status     ",
                            true);
            DriverConfig
                    .setLogString(
                            "=======================================================================================================================",
                            true);
            int loopVal = 0;
            for (PartitionedThermostatEvent thermostatEvent : eventList) {
                if (loopVal != 0) {
                    DriverConfig
                            .setLogString(
                                    "=======================================================================================================================",
                                    true);
                }

                Double oldSetting = thermostatEvent.getOldSetting();
                Double newSetting = thermostatEvent.getNewSetting();
                String oldSettingString = "-";
                String newSettingString = "-";
                if (oldSetting != null) {
                    oldSettingString = thermostatEvent.getOldSetting().toString();
                }

                if (newSetting != null) {
                    newSettingString = thermostatEvent.getNewSetting().toString();
                }

                DriverConfig.setLogString(
                        "| "
                                + addSpace(thermostatEvent.getThermostatId().toString(), 14)
                                + "| "
                                + addSpace(DateUtil.format(thermostatEvent.getId()
                                        .getEventSysTime(), DateUtil.DATE_FMT_FULL), 24) + "| "
                                + addSpace(thermostatEvent.getAlgorithmId().toString(), 13) + "| "
                                + addSpace(thermostatEvent.getPhase().toString(), 13) + "| "
                                + addSpace(oldSettingString, 12) + "| "
                                + addSpace(newSettingString, 13) + "|"
                                + addSpace(thermostatEvent.getStatus().toString(), 14) + "|", true);

                loopVal++;
            }
            DriverConfig
                    .setLogString(
                            "=======================================================================================================================",
                            true);
        }
    }

    /**
     * Prints the range data table grid.
     * @param thermostatId the thermostat id
     * @param startTime the start time
     * @param endTime the end time
     */
    public static void printRangeDataTableGrid(Integer thermostatId, Calendar startTime,
            Calendar endTime) {

        List<PartitionedThermostatRangeData> thRangeDataList = thRangeDataDao
                .listByThermostatAndStartTimeRange(thermostatId, startTime);
        if (thRangeDataList != null && !thRangeDataList.isEmpty()) {
            DriverConfig.setLogString("Range Data Table Grid", true);
            DriverConfig.setLogString(
                    "SQL QUERY: Select * from ef_thermostat_range_data where thermostat_id = "
                            + thermostatId + " AND start_time > "
                            + DateUtil.format(startTime, DateUtil.DATE_FMT)
                            + " ORDER BY start_time DESC", true);
            DriverConfig
                    .setLogString(
                            "======================================================================================",
                            true);
            DriverConfig
                    .setLogString(
                            "| Thermostat Id |      Start Time          |  Cool | Heat  | Hvac State | Hvac Mode  |",
                            true);
            DriverConfig
                    .setLogString(
                            "======================================================================================",
                            true);
            int loopVal = 0;
            for (PartitionedThermostatRangeData thermostatRangedata : thRangeDataList) {
                if (loopVal != 0) {
                    DriverConfig
                            .setLogString(
                                    "======================================================================================",
                                    true);
                }

                DriverConfig.setLogString(
                        "| "
                                + addSpace(
                                        thermostatRangedata.getId().getThermostatId().toString(),
                                        14)
                                + "| "
                                + addSpace(DateUtil.format(thermostatRangedata.getId()
                                        .getStartTime(), DateUtil.DATE_FMT_FULL), 25) + "| "
                                + addSpace(thermostatRangedata.getCoolSetting().toString(), 6)
                                + "| "
                                + addSpace(thermostatRangedata.getHeatSetting().toString(), 6)
                                + "| " + addSpace(thermostatRangedata.getHvacState().name(), 11)
                                + "| " + addSpace(thermostatRangedata.getHvacMode().name(), 11)
                                + "|", true);

                loopVal++;
            }
            DriverConfig
                    .setLogString(
                            "======================================================================================",
                            true);
        }

    }

    /**
     * Prints the range data table grid.
     * @param tstatRangeData the tstat range data
     */
    public static void printRangeDataTableGrid(List<ThermostatRangeData> tstatRangeData) {

        if (tstatRangeData != null && !tstatRangeData.isEmpty()) {
            DriverConfig.setLogString("Range Data Table Grid", true);
            DriverConfig
                    .setLogString(
                            "======================================================================================",
                            true);
            DriverConfig
                    .setLogString(
                            "| Thermostat Id |      Start Time          |  Cool | Heat  | Hvac State | Hvac Mode  |",
                            true);
            DriverConfig
                    .setLogString(
                            "======================================================================================",
                            true);
            int loopVal = 0;
            for (ThermostatRangeData thermostatRangedata : tstatRangeData) {
                if (loopVal != 0) {
                    DriverConfig
                            .setLogString(
                                    "======================================================================================",
                                    true);
                }

                DriverConfig.setLogString(
                        "| "
                                + addSpace(thermostatRangedata.getThermostatId().toString(), 14)
                                + "| "
                                + addSpace(DateUtil.format(thermostatRangedata.getStartTime(),
                                        DateUtil.DATE_FMT_FULL), 25) + "| "
                                + addSpace(thermostatRangedata.getCoolSetting().toString(), 6)
                                + "| "
                                + addSpace(thermostatRangedata.getHeatSetting().toString(), 6)
                                + "| " + addSpace(thermostatRangedata.getHvacState().name(), 11)
                                + "| " + addSpace(thermostatRangedata.getHvacMode().name(), 11)
                                + "|", true);

                loopVal++;
            }
            DriverConfig
                    .setLogString(
                            "======================================================================================",
                            true);
        }

    }

    /**
     * Prints the event table grid.
     * @param events the events
     */
    public static void printEventTableGrid(List<PartitionedThermostatEvent> events) {

        if (events != null && !events.isEmpty()) {
            DriverConfig.setLogString("Event Table Grid", true);
            DriverConfig
                    .setLogString(
                            "======================================================================================================",
                            true);
            DriverConfig
                    .setLogString(
                            "| Thermostat Id |    Event SysTime        | Algorithm Id | Event Phase  | Old Setting | New Setting  |",
                            true);
            DriverConfig
                    .setLogString(
                            "======================================================================================================",
                            true);
            int loopVal = 0;
            for (PartitionedThermostatEvent thermostatEvent : events) {
                if (loopVal != 0) {
                    DriverConfig
                            .setLogString(
                                    "======================================================================================================",
                                    true);
                }

                Double oldSetting = thermostatEvent.getOldSetting();
                Double newSetting = thermostatEvent.getNewSetting();
                String oldSettingString = "-";
                String newSettingString = "-";
                if (oldSetting != null) {
                    oldSettingString = thermostatEvent.getOldSetting().toString();
                }

                if (newSetting != null) {
                    newSettingString = thermostatEvent.getNewSetting().toString();
                }

                DriverConfig.setLogString(
                        "| "
                                + addSpace(thermostatEvent.getThermostatId().toString(), 14)
                                + "| "
                                + addSpace(DateUtil.format(thermostatEvent.getId()
                                        .getEventSysTime(), DateUtil.DATE_FMT_FULL), 24) + "| "
                                + addSpace(thermostatEvent.getAlgorithmId().toString(), 13) + "| "
                                + addSpace(thermostatEvent.getPhase().toString(), 13) + "| "
                                + addSpace(oldSettingString, 12) + "| "
                                + addSpace(newSettingString, 13) + "|", true);

                loopVal++;
            }
            DriverConfig
                    .setLogString(
                            "======================================================================================================",
                            true);
        }
    }

    /**
     * Prints the algo control table grid.
     * @param events the events
     */
    public static void printAlgoControlTableGrid(List<ThermostatAlgorithmController> events) {

        if (events != null && !events.isEmpty()) {
            DriverConfig.setLogString("Algo Control Table Grid", true);
            DriverConfig
                    .setLogString(
                            "--------------------------------------------------------------------------------------------------------------------------------------------------------------",
                            true);
            DriverConfig
                    .setLogString(
                            "| Thermostat Id |    Next Phase Time        | Algorithm Id | Setting Phase 0  | Thermostat Algo Status | Execution Start Time UTC  | Execution End Time UTC   |",
                            true);
            DriverConfig
                    .setLogString(
                            "--------------------------------------------------------------------------------------------------------------------------------------------------------------",
                            true);
            int loopVal = 0;
            for (ThermostatAlgorithmController thermostatAlgorithmController : events) {
                if (loopVal != 0) {
                    DriverConfig
                            .setLogString(
                                    "---------------------------------------------------------------------------------------------------------------------------------------------------------------",
                                    true);
                }

                String phase = "";
                String status = "ACTIVE";
                String algoId = "";

                if (thermostatAlgorithmController.getPhase0Spt() != null)
                    phase = thermostatAlgorithmController.getPhase0Spt().toString();
                if (thermostatAlgorithmController.getStatus() != null)
                    status = thermostatAlgorithmController.getStatus().toString();
                if (thermostatAlgorithmController.getAlgorithmId() != null)
                    algoId = thermostatAlgorithmController.getAlgorithmId().toString();

                StringBuilder thermostatAlgoControl = new StringBuilder();

                thermostatAlgoControl.append("| ");
                thermostatAlgoControl.append(addSpace(thermostatAlgorithmController
                        .getThermostatId().toString(), 14)
                        + "|");
                thermostatAlgoControl.append(addSpace(DateUtil.format(
                        thermostatAlgorithmController.getNextPhaseTime(), DateUtil.DATE_FMT_FULL),
                        27)
                        + "| ");
                thermostatAlgoControl.append(addSpace(algoId, 13) + "| ");
                thermostatAlgoControl.append(addSpace(phase, 16) + "| " + addSpace(status, 24)
                        + "| ");
                thermostatAlgoControl.append(addSpace(DateUtil.format(
                        thermostatAlgorithmController.getExecutionStartTimeUTC(),
                        DateUtil.DATE_FMT_FULL), 26)
                        + "|");
                if (thermostatAlgorithmController.getExecutionEndTimeUTC() == null) {
                    thermostatAlgoControl.append(addSpace("", 26) + "|");
                } else {
                    thermostatAlgoControl.append(addSpace(DateUtil.format(
                            thermostatAlgorithmController.getExecutionEndTimeUTC(),
                            DateUtil.DATE_FMT_FULL), 26)
                            + "|");
                }

                DriverConfig.setLogString(thermostatAlgoControl.toString(), true);

                loopVal++;
            }
            DriverConfig
                    .setLogString(
                            "---------------------------------------------------------------------------------------------------------------------------------------------------------------",
                            true);
        }
    }

    /**
     * Prints the spo job data json.
     * @param jsonArray the json array
     */
    public static void printMockSPOJobDataJson(JSONArray jsonArray) {

        DriverConfig.setLogString("Json Grid", true);
        DriverConfig
                .setLogString(
                        "=============================================================================================================================",
                        true);
        DriverConfig
                .setLogString(
                        "|        Start Time         |         End Time         |  Delta EE  |  MO BlackOut  |        MO Cuttoff        | MO Recovery |",
                        true);
        DriverConfig
                .setLogString(
                        "=============================================================================================================================",
                        true);
        int loopVal = 0;
        for (int i = 0; i < jsonArray.length(); i++) {
            try {

                if (loopVal != 0) {
                    DriverConfig
                            .setLogString(
                                    "=============================================================================================================================",
                                    true);
                }
                JSONObject jsonObj = jsonArray.getJSONObject(i);
                String startCal = (String) jsonObj.get("start");
                String endCal = (String) jsonObj.get("end");
                Double deltaEE = (Double) jsonObj.get("deltaEE");
                Integer moBlackOut = (Integer) jsonObj.get("moBlackOut");
                String moCutoff = (String) jsonObj.get("moCutoff");
                Integer moRecovery = (Integer) jsonObj.get("moRecovery");

                DriverConfig.setLogString(
                        "| " + addSpace(startCal, 26) + "| " + addSpace(endCal, 25) + "| "
                                + addSpace(deltaEE.toString(), 11) + "| "
                                + addSpace(moBlackOut.toString(), 14) + "| "
                                + addSpace(moCutoff, 25) + "| "
                                + addSpace(moRecovery.toString(), 12) + "|", true);
                loopVal++;

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        DriverConfig
                .setLogString(
                        "=============================================================================================================================",
                        true);
    }

    /**
     * Prints the spo job data json.
     * @param jsonArray the json array
     */
    public static void printSPOJobDataJson(JSONArray jsonArray) {

        DriverConfig.setLogString("Json Grid", true);
        DriverConfig
                .setLogString(
                        "=============================================================================================================================",
                        true);
        DriverConfig
                .setLogString(
                        "|        Start Time         |         End Time         |  Delta EE  |  MO BlackOut  |        MO Cuttoff        | MO Recovery |",
                        true);
        DriverConfig
                .setLogString(
                        "=============================================================================================================================",
                        true);
        int loopVal = 0;
        for (int i = 0; i < jsonArray.length(); i++) {
            try {

                if (loopVal != 0) {
                    DriverConfig
                            .setLogString(
                                    "=============================================================================================================================",
                                    true);
                }
                JSONObject jsonObj = jsonArray.getJSONObject(i);
                String startCal = (String) jsonObj.get("start");
                String endcal = (String) jsonObj.get("end");
                Double deltaEE = (Double) jsonObj.get("deltaEE");
                Integer moBlackOut = (Integer) jsonObj.get("moBlackOut");
                String moCutoff = (String) jsonObj.get("moCutoff");
                Double moRecovery = (Double) jsonObj.get("moRecovery");

                DriverConfig.setLogString(
                        "| " + addSpace(startCal, 26) + "| " + addSpace(endcal, 25) + "| "
                                + addSpace(deltaEE.toString(), 11) + "| "
                                + addSpace(moBlackOut.toString(), 14) + "| "
                                + addSpace(moCutoff, 25) + "| "
                                + addSpace(moRecovery.toString(), 12) + "|", true);
                loopVal++;

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        DriverConfig
                .setLogString(
                        "=============================================================================================================================",
                        true);
    }

    /**
     * Prints the utcspo job data json.
     * @param jsonArray the json array
     * @param timeZone the time zone
     */
    public static void printUTCSPOJobDataJson(JSONArray jsonArray, String timeZone) {

        DriverConfig.setLogString("Json Grid", true);
        DriverConfig
                .setLogString(
                        "=============================================================================================================================",
                        true);
        DriverConfig
                .setLogString(
                        "|        Start Time         |         End Time         |  Delta EE  |  MO BlackOut  |        MO Cuttoff        | MO Recovery |",
                        true);
        DriverConfig
                .setLogString(
                        "=============================================================================================================================",
                        true);
        int loopVal = 0;
        for (int i = 0; i < jsonArray.length(); i++) {
            try {

                if (loopVal != 0) {
                    DriverConfig
                            .setLogString(
                                    "=============================================================================================================================",
                                    true);
                }
                JSONObject jsonObj = jsonArray.getJSONObject(i);
                String startCal = (String) jsonObj.get("start");
                String endcal = (String) jsonObj.get("end");
                Double deltaEE = (Double) jsonObj.get("deltaEE");
                Integer moBlackOut = (Integer) jsonObj.get("moBlackOut");
                String moCutoff = (String) jsonObj.get("moCutoff");
                Double moRecovery = (Double) jsonObj.get("moRecovery");

                DriverConfig.setLogString(
                        "| " + addSpace(startCal, 26) + "| " + addSpace(endcal, 25) + "| "
                                + addSpace(deltaEE.toString(), 11) + "| "
                                + addSpace(moBlackOut.toString(), 14) + "| "
                                + addSpace(moCutoff, 25) + "| "
                                + addSpace(moRecovery.toString(), 12) + "|", true);
                loopVal++;

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        DriverConfig
                .setLogString(
                        "=============================================================================================================================",
                        true);
    }

    /**
     * Prints the test case grid.
     * @param testCaseMap the test case map
     */
    public static void printTestCaseGrid(TreeMap<Integer, TestMethodDetails> testCaseMap) {

        try {
            DriverConfig.setLogString("Test Case Grid", true);
            DriverConfig
                    .setLogString(
                            "========================================================================================================",
                            true);
            DriverConfig
                    .setLogString(
                            "|   S.No.  |                   Test Case Name                               |           ID             |",
                            true);
            DriverConfig
                    .setLogString(
                            "========================================================================================================",
                            true);
            Iterator<Entry<Integer, TestMethodDetails>> testCaseIterator = testCaseMap.entrySet()
                    .iterator();
            Integer serialNo = 1;
            while (testCaseIterator.hasNext()) {
                Entry<Integer, TestMethodDetails> pairs = testCaseIterator.next();
                TestMethodDetails testNgDetails = pairs.getValue();
                String testMethod = testNgDetails.getTestCaseName();
                String value = testNgDetails.getTestLinkId();
                if (value == null || value.isEmpty()) {
                    value = "--";
                }
                DriverConfig.setLogString("| " + addSpace(serialNo.toString(), 9) + "| "
                        + addSpace(testMethod, 63) + "| " + addSpace(value, 25) + "| ", true);
                serialNo++;
            }
            DriverConfig
                    .setLogString(
                            "========================================================================================================",
                            true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Adds the space.
     * @param word the word
     * @param noOfCharac the no of charac
     * @return the string
     */
    public static String addSpace(String word, int noOfCharac) {

        int wordLength = word.length();
        int noOfSpace = noOfCharac - wordLength;
        if (noOfSpace < 0) {
            return word;
        } else {
            String space = "";
            for (int val = 0; val < noOfSpace; val++) {
                space += " ";
            }
            word = word + space;
        }
        return word;
    }
}
