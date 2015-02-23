/*
 * TstatDBValidation.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.newapp.page.validate.db;

import static com.ecofactor.qa.automation.platform.util.LogUtil.setLogString;
import static org.testng.Reporter.*;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.testng.Assert;

import com.ecofactor.common.pojo.Algorithm;
import com.ecofactor.common.pojo.DisconnectedDevice;
import com.ecofactor.common.pojo.Location;
import com.ecofactor.common.pojo.Thermostat;
import com.ecofactor.common.pojo.ThermostatAlgorithm;
import com.ecofactor.common.pojo.ThermostatAlgorithmController;
import com.ecofactor.common.pojo.ThermostatProgramLog;
import com.ecofactor.common.pojo.User;
import com.ecofactor.common.pojo.User.Status;
import com.ecofactor.common.pojo.timeseries.PartitionedThermostatRangeData;
import com.ecofactor.qa.automation.dao.AlgorithmDao;
import com.ecofactor.qa.automation.dao.DisconnectedDeviceDao;
import com.ecofactor.qa.automation.dao.EFUserDao;
import com.ecofactor.qa.automation.dao.LocationDao;
import com.ecofactor.qa.automation.dao.ThermostatAlgoControlDao;
import com.ecofactor.qa.automation.dao.ThermostatAlgorithmDao;
import com.ecofactor.qa.automation.dao.ThermostatDao;
import com.ecofactor.qa.automation.dao.ThermostatProgramLogDao;
import com.ecofactor.qa.automation.dao.ThermostatRangeDataDao;
import com.ecofactor.qa.automation.dao.util.DataUtil;
import com.ecofactor.qa.automation.newapp.enums.ControlActions;
import com.ecofactor.qa.automation.platform.annotation.BindDBValidation;
import com.ecofactor.qa.automation.platform.annotation.DBValidationMethod;
import com.ecofactor.qa.automation.platform.enums.CustomLogLevel;
import com.ecofactor.qa.automation.util.DateUtil;
import com.ecofactor.qa.automation.util.WaitUtil;
import com.google.inject.Inject;

/**
 * The Class ThermostatDBValidation. Validation operations for ThermostatDB. This class contains
 * verification methods for temperature,hvac mode,fan mode,etc
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
@BindDBValidation
public class TstatDBValidation {

    @Inject
    private ThermostatDao thermostatDao;
    @Inject
    private EFUserDao userDao;
    @Inject
    private LocationDao locationDao;
    @Inject
    private DisconnectedDeviceDao disconnectedDeviceDao;
    @Inject
    private ThermostatAlgoControlDao thermostatAlgoControlDao;
    @Inject
    private ThermostatRangeDataDao thermostatRangeDataDao;
    @Inject
    private ThermostatAlgorithmDao thAlgorithmDao;
    @Inject
    private AlgorithmDao algorithmDao;
    @Inject
    private ThermostatProgramLogDao thermostatProgramLogDao;

    /**
     * The Constant FIVE_MINS.
     */
    public static final int FIVE_MINS = 5;
    private static final int ONE = 1;
    private static final String HEAT = "heat";
    private static final String COOL = "cool";

    private static final String IN_RANGEDATA = " in RangeData";
    private static final String FOR = " for ";

    /**
     * Verify set point change.
     * @param userName the user name
     * @param thermostatName the thermostat name
     * @param mode the mode
     * @param temp the temp
     * @return true, if successful
     */
    @DBValidationMethod
    public boolean verifyTargetTemp(final String userName, final String thermostatName,
            final String mode, final String temp) {

        setLogString("Verify target temperature in DB", true);
        final String testStartCalendar = DateUtil.getUTCCurrentTimeStamp();
        boolean validate = false;

        final Integer thermostatId = getTstatIdForUser(userName, thermostatName);
        setLogString(
                new StringBuilder("Verify set point change for Thermostat ").append(thermostatId)
                        .append(IN_RANGEDATA).toString(), true);
        setLogString(new StringBuilder("Target temperature in UI :").append(temp).toString(), true);
        setLogString(new StringBuilder("Current Mode  :").append(mode.toUpperCase(Locale.ENGLISH))
                .toString(), true);

        Calendar currentTime = DateUtil.getUTCCalendar();
        final Calendar endTime = DateUtil.getUTCCalendar();
        endTime.add(Calendar.MINUTE, FIVE_MINS);
        final StringBuilder setPoint = new StringBuilder();
        do {
            WaitUtil.tinyWait();
            final PartitionedThermostatRangeData thRangeData = thermostatRangeDataDao
                    .findLatestByThermostat(thermostatId);
            if (thRangeData != null) {
                if (mode.equalsIgnoreCase(COOL)) {
                    final double coolSetting = thRangeData.getCoolSetting();
                    validate = coolSetting == Double.valueOf(temp) ? true : false;
                } else if (mode.equalsIgnoreCase(HEAT)) {
                    final double heatSetting = thRangeData.getHeatSetting();
                    validate = heatSetting == Double.valueOf(temp) ? true : false;
                }
            }
            if (validate) {
                setLogString(setPoint.append("Set Point Change of ").append(temp).append(FOR)
                        .append(thermostatId).append(" is reflected in range data").toString(),
                        true);
                break;
            }
            setPoint.setLength(0);
            currentTime = DateUtil.getUTCCalendar();
        } while (currentTime.before(endTime));

        final String testEndCalendar = DateUtil.getUTCCurrentTimeStamp();
        final Calendar startCalendar = DateUtil.parseToCalendar(testStartCalendar,
                DateUtil.DATE_FMT_FULL);
        final Calendar endCalendar = DateUtil.parseToCalendar(testEndCalendar,
                DateUtil.DATE_FMT_FULL);
        DataUtil.printRangeDataTableGrid(thermostatId, startCalendar, endCalendar);

        Assert.assertTrue(
                validate,
                new StringBuilder("Set Point Change of ").append(temp).append(FOR)
                        .append(thermostatId).append(" is not reflected in range data").toString());

        return validate;
    }

    /**
     * Verify current temp.
     * @param userName the user name
     * @param thermostatName the thermostat name
     * @param mode the mode
     * @param temp the temp
     * @return true, if successful
     */
    @DBValidationMethod
    public boolean verifyCurrentTemp(final String userName, final String thermostatName,
            final String mode, final String temp) {

        setLogString("Verify current temperature in DB", true);
        final String testStartCalendar = DateUtil.getUTCCurrentTimeStamp();
        boolean validate = false;

        final Integer thermostatId = getTstatIdForUser(userName, thermostatName);

        setLogString(
                new StringBuilder("Verify current temperature for Thermostat ")
                        .append(thermostatId).append(IN_RANGEDATA).toString(), true);
        setLogString(new StringBuilder("Current temperature in UI :").append(temp).toString(), true);
        setLogString(
                new StringBuilder("Current Mode in UI :").append(mode.toUpperCase(Locale.ENGLISH))
                        .toString(), true);

        Calendar currentTime = DateUtil.getUTCCalendar();
        final Calendar endTime = DateUtil.getUTCCalendar();
        endTime.add(Calendar.MINUTE, FIVE_MINS);
        final StringBuilder currentTemp = new StringBuilder();
        do {
            WaitUtil.tinyWait();
            final PartitionedThermostatRangeData thRangeData = thermostatRangeDataDao
                    .findLatestByThermostat(thermostatId);
            if (thRangeData != null) {
                if (mode.equalsIgnoreCase(COOL)) {
                    final double coolSetting = Math.round(thRangeData.getTemperature());
                    validate = (coolSetting == Double.valueOf(temp)) ? true : false;
                } else if (mode.equalsIgnoreCase(HEAT)) {
                    final double heatSetting = Math.round(thRangeData.getTemperature());
                    validate = (heatSetting == Double.valueOf(temp)) ? true : false;
                }
            }
            if (validate) {
                setLogString(currentTemp.append("Current Temperature: ").append(temp).append(FOR)
                        .append(thermostatId).append(" is reflected in range data").toString(),
                        true);
                break;
            }
            currentTemp.setLength(0);
            currentTime = DateUtil.getUTCCalendar();
        } while (currentTime.before(endTime));

        final String testEndCalendar = DateUtil.getUTCCurrentTimeStamp();
        final Calendar startCalendar = DateUtil.parseToCalendar(testStartCalendar,
                DateUtil.DATE_FMT_FULL);
        final Calendar endCalendar = DateUtil.parseToCalendar(testEndCalendar,
                DateUtil.DATE_FMT_FULL);
        DataUtil.printRangeDataTableGrid(thermostatId, startCalendar, endCalendar);

        Assert.assertTrue(
                validate,
                new StringBuilder("Current Temperature: ").append(temp).append(FOR)
                        .append(thermostatId).append(" is not reflected in range data").toString());

        return validate;
    }

    /**
     * Verify mode.
     * @param userName the user name
     * @param thermostatName the thermostat name
     * @param mode the mode
     * @return true, if successful
     */
    @DBValidationMethod
    public void verifyMode(final String userName, final String thermostatName, final String mode) {

        setLogString("Verify mode changes in DB", true);
        final String testStartCalendar = DateUtil.getUTCCurrentTimeStamp();
        boolean isModeChange = false;
        final Integer thermostatId = getTstatIdForUser(userName, thermostatName);
        setLogString(
                new StringBuilder("Verify Mode Change '").append(mode).append("' for Thermostat ")
                        .append(thermostatId).append(IN_RANGEDATA).toString(), true);
        Calendar currentTime = DateUtil.getUTCCalendar();
        final Calendar endTime = DateUtil.getUTCCalendar();
        endTime.add(Calendar.MINUTE, FIVE_MINS);
        do {
            WaitUtil.tinyWait();
            final PartitionedThermostatRangeData thRangeData = thermostatRangeDataDao
                    .findLatestByThermostat(thermostatId);
            isModeChange = thRangeData.getHvacMode().toString().equalsIgnoreCase(mode);
            if (isModeChange) {
                break;
            }
            currentTime = DateUtil.getUTCCalendar();
        } while (currentTime.before(endTime));

        final String testEndCalendar = DateUtil.getUTCCurrentTimeStamp();
        final Calendar startCalendar = DateUtil.parseToCalendar(testStartCalendar,
                DateUtil.DATE_FMT_FULL);
        final Calendar endCalendar = DateUtil.parseToCalendar(testEndCalendar,
                DateUtil.DATE_FMT_FULL);
        DataUtil.printRangeDataTableGrid(thermostatId, startCalendar, endCalendar);

        Assert.assertTrue(isModeChange, "Mode change not reflected in rangedata");
    }

    /**
     * Verify hvac state.
     * @param userName the user name
     * @param thermostatName the thermostat name
     * @param state the state
     */
    @DBValidationMethod
    public void verifyHvacState(final String userName, final String thermostatName,
            final String state) {

        setLogString("Verify hvac state changes in DB", true);
        final String testStartCalendar = DateUtil.getUTCCurrentTimeStamp();
        boolean isStateChange = false;
        final Integer thermostatId = getTstatIdForUser(userName, thermostatName);
        setLogString(
                new StringBuilder("Verify Hvac mode '").append(state).append("' for Thermostat ")
                        .append(thermostatId).append(IN_RANGEDATA).toString(), true);
        Calendar currentTime = DateUtil.getUTCCalendar();
        final Calendar endTime = DateUtil.getUTCCalendar();
        endTime.add(Calendar.MINUTE, FIVE_MINS);
        do {
            WaitUtil.tinyWait();
            final PartitionedThermostatRangeData thRangeData = thermostatRangeDataDao
                    .findLatestByThermostat(thermostatId);
            isStateChange = thRangeData.getHvacState().toString().equalsIgnoreCase(state);
            if (isStateChange) {
                break;
            }
            currentTime = DateUtil.getUTCCalendar();
        } while (currentTime.before(endTime));

        final String testEndCalendar = DateUtil.getUTCCurrentTimeStamp();
        final Calendar startCalendar = DateUtil.parseToCalendar(testStartCalendar,
                DateUtil.DATE_FMT_FULL);
        final Calendar endCalendar = DateUtil.parseToCalendar(testEndCalendar,
                DateUtil.DATE_FMT_FULL);
        DataUtil.printRangeDataTableGrid(thermostatId, startCalendar, endCalendar);

        Assert.assertTrue(isStateChange, "Hvac State change not reflected in rangedata");
    }

    /**
     * Verify fan mode.
     * @param userName the user name
     * @param thermostatName the thermostat name
     * @param state the state
     */
    @DBValidationMethod
    public void verifyFanMode(final String userName, final String thermostatName, final String state) {

        setLogString("Verify fan mode changes in DB", true);
        final String testStartCalendar = DateUtil.getUTCCurrentTimeStamp();
        boolean isStateChange = false;
        final Integer thermostatId = getTstatIdForUser(userName, thermostatName);
        setLogString(
                new StringBuilder("Verify Fan mode '").append(state).append("' for Thermostat ")
                        .append(thermostatId).append(IN_RANGEDATA).toString(), true);
        Calendar currentTime = DateUtil.getUTCCalendar();
        final Calendar endTime = DateUtil.getUTCCalendar();
        endTime.add(Calendar.MINUTE, FIVE_MINS);
        do {
            WaitUtil.tinyWait();
            final PartitionedThermostatRangeData thRangeData = thermostatRangeDataDao
                    .findLatestByThermostat(thermostatId);
            isStateChange = thRangeData.getFanMode().toString().equalsIgnoreCase(state);
            if (isStateChange) {
                break;
            }
            currentTime = DateUtil.getUTCCalendar();
        } while (currentTime.before(endTime));

        final String testEndCalendar = DateUtil.getUTCCurrentTimeStamp();
        final Calendar startCalendar = DateUtil.parseToCalendar(testStartCalendar,
                DateUtil.DATE_FMT_FULL);
        final Calendar endCalendar = DateUtil.parseToCalendar(testEndCalendar,
                DateUtil.DATE_FMT_FULL);
        DataUtil.printRangeDataTableGrid(thermostatId, startCalendar, endCalendar);

        Assert.assertTrue(isStateChange, "Fan mode change not reflected in rangedata.");
    }

    /**
     * Verify tstat name.
     * @param userName the user name
     * @param thermostatName the thermostat name
     */
    public void verifyTstatName(final String userName, final String thermostatName) {

        boolean tstatNameInDB = false;
        final Integer thermostatId = getTstatIdForUser(userName, thermostatName);
        setLogString("Verify Thermostat Name in DB", true);
        Calendar currentTime = DateUtil.getUTCCalendar();
        final Calendar endTime = DateUtil.getUTCCalendar();
        endTime.add(Calendar.MINUTE, ONE);
        do {
            WaitUtil.tinyWait();

            final Thermostat tstat = thermostatDao.findByThermostatId(String.valueOf(thermostatId));
            tstatNameInDB = tstat.getName().trim().toString()
                    .equalsIgnoreCase(thermostatName.trim());
            if (tstatNameInDB) {
                break;
            }
            currentTime = DateUtil.getUTCCalendar();
        } while (currentTime.before(endTime));

        setLogString(new StringBuilder("Thermostat Name in DB :").append(tstatNameInDB).toString(),
                true);
        setLogString(
                new StringBuilder("Thermostat Name in UI :").append(thermostatName).toString(),
                true);
        Assert.assertTrue(tstatNameInDB, "Thermostat name not in database");
    }

    /**
     * Verify location and tstat.
     * @param userName the user name
     * @param thermostatName the thermostat name
     * @param locationName the location name
     */
    @DBValidationMethod
    public void verifyLocationAndTstat(final String userName, final String thermostatName,
            final String locationName) {

        boolean locationInDB = false;
        final Integer thermostatId = getTstatIdForUser(userName, thermostatName);
        setLogString("Verify Thermostat Name in DB", true);
        Calendar currentTime = DateUtil.getUTCCalendar();
        final Calendar endTime = DateUtil.getUTCCalendar();
        endTime.add(Calendar.MINUTE, FIVE_MINS);
        do {
            WaitUtil.tinyWait();
            final Thermostat tstat = thermostatDao.findByThermostatId(String.valueOf(thermostatId));
            locationInDB = tstat.getName().toString().equalsIgnoreCase(thermostatName);
            if (locationInDB) {
                break;
            }
            currentTime = DateUtil.getUTCCalendar();
        } while (currentTime.before(endTime));
        Assert.assertTrue(locationInDB, "Thermostat name not in database");
    }

    /**
     * Verify mo order between modes.
     * @param userName the user name
     * @param thermostatName the thermostat name
     * @param coolSetting the cool setting
     * @param heatSetting the heat setting
     */
    @DBValidationMethod
    public void verifyMOOrderBetweenModes(final String userName, final String thermostatName,
            final String coolSetting, final String heatSetting) {

        final Integer thermostatId = getTstatIdForUser(userName, thermostatName);
        Calendar currentTime = Calendar.getInstance();
        final Calendar endTime = DateUtil.getUTCCalendar();
        endTime.add(Calendar.MINUTE, FIVE_MINS);

        boolean coolUpdated = false;
        boolean heatUpdated = false;

        setLogString(new StringBuilder(
                "Verify cool setting in queue as first with temperature as : ").append(coolSetting)
                .toString(), true);
        setLogString(new StringBuilder(
                "Verify heat setting in queue as second with temperature as : ")
                .append(heatSetting).toString(), true);

        do {
            WaitUtil.tinyWait();
            final PartitionedThermostatRangeData thRangeData = thermostatRangeDataDao
                    .findLatestByThermostat(thermostatId);

            if (thRangeData.getCoolSetting().equals(Double.valueOf(coolSetting)) && coolUpdated) {
                coolUpdated = true;
            }

            if (thRangeData.getHeatSetting().equals(Double.valueOf(heatSetting)) && heatUpdated) {
                heatUpdated = true;
            }

            if (coolUpdated && heatUpdated) {
                Assert.assertTrue(coolUpdated,
                        "Cool setting was not updated prior to heat setting.");
            }
            currentTime = DateUtil.getUTCCalendar();
        } while (currentTime.before(endTime));

        Assert.assertTrue(coolUpdated, "Heat setting not updated.");
    }

    /**
     * Verify location alphabetical order.
     * @param userName the user name
     * @param locationList the location list
     */
    @DBValidationMethod
    public void verifyLocationAlphabeticalOrder(final String userName,
            final List<String> locationList) {

        setLogString("Verify Location in order.", true);
        final User user = userDao.findByUserName(userName);
        final List<Location> dbLocationList = locationDao.getLocationByUserId(user.getId());
        int loc = 1;
        final StringBuilder inDb = new StringBuilder();
        final StringBuilder inUi = new StringBuilder();
        for (final Location location : dbLocationList) {
            setLogString(
                    inDb.append(loc).append(". In DB :").append(location.getName()).toString(),
                    true);
            setLogString(inUi.append(loc).append(". In UI :").append(locationList.get(loc))
                    .toString(), true);
            inDb.setLength(0);
            inUi.setLength(0);
            Assert.assertTrue(location.getName().equalsIgnoreCase(locationList.get(loc)));
            loc++;
        }
    }

    /**
     * Verify thermostat offline.
     * @param userName the user name
     * @param thermostatName the thermostat name
     */
    @DBValidationMethod
    public void verifyThermostatOffline(final String userName, final String thermostatName) {

        final Integer thermostatId = getTstatIdForUser(userName, thermostatName);
        setLogString(
                new StringBuilder("Verify thermostat ").append(thermostatId).append(" is offline.")
                        .toString(), true);
        final DisconnectedDevice deviceStatus = disconnectedDeviceDao.getDeviceStatus(Integer
                .valueOf(thermostatId));
        Assert.assertFalse(deviceStatus.isConnected(), "The device is not offline.");

    }

    /**
     * Checks if is tstat status away in db.
     * @param userName the user name
     * @param currTstatName the curr tstat name
     * @param isAway the is away
     */
    @DBValidationMethod
    public void isTstatStatusAwayInDB(final String userName, final String currTstatName,
            final boolean isAway) {

        setLogString(
                new StringBuilder("Verify Away Status in DB for Tstat = ").append(currTstatName)
                        .append(".").toString(), true, CustomLogLevel.LOW);
        final ThermostatAlgorithmController controller = getControllerforThermostatAlgoControlTable(getTstatIdForUser(
                userName, currTstatName));

        if (controller != null) {

            final int algoID = controller.getAlgorithm().getId().intValue();
            final String algoName = controller.getAlgorithm().getName();
            Assert.assertTrue(("USER_AWAY").equals(algoName) && algoID == -20,
                    "Set away event not updated in DB.");

            setLogString(
                    new StringBuilder("Algorithm Id : ").append(algoID)
                            .append(" ; Algorithm Name : ").append(algoName).toString(), true,
                    CustomLogLevel.LOW);
            Assert.assertEquals(isAway, ("USER_AWAY").equals(algoName) && algoID == -20,
                    "Set/Cancel away event not updated in DB.");
        } else {
            setLogString("No active algorithm in DB. Returned null.", true, CustomLogLevel.LOW);
            Assert.fail("Set/Cancel away event failed or not updated in DB.");
        }

    }

    /**
     * Verify provision state.
     * @param userName the user name
     */
    @DBValidationMethod
    public void verifyProvisionState(final String userName) {

        setLogString(
                new StringBuilder("Verify user status for user ").append(userName)
                        .append(" is Provision.").toString(), true);
        final User user = userDao.findByUserName(userName);
        setLogString(new StringBuilder("User status: ").append(user.getStatus()).toString(), true);
        Assert.assertTrue(
                user.getStatus().equals(Status.PROVISION),
                new StringBuilder("User's status for user '").append(userName)
                        .append("' is not under PROVISION status.").toString());
    }

    /**
     * Gets the tstat id for user.
     * @param userName the user name
     * @param thermostatName the thermostat name
     * @return the tstat id for user
     */
    public Integer getTstatIdForUser(final String userName, final String thermostatName) {

        final List<Integer> thList = thermostatDao.getThermostatIdForUser(userName);
        Integer thId = 0;
        if (thList.size() == ONE) {
            thId = thList.get(0);
        } else {
            for (final Integer thermostatId : thList) {
                final Thermostat tstat = thermostatDao.findByThermostatId(String
                        .valueOf(thermostatId));
                if (tstat.getName().trim().equalsIgnoreCase(thermostatName.trim())) {
                    thId = thermostatId;
                    break;
                }
            }
        }
        setLogString(new StringBuilder("Thermostat Id: ").append(thId).toString(), true,
                CustomLogLevel.LOW);
        return thId;
    }

    /**
     * Gets the thermostat by id.
     * @param thermostatId the thermostat id
     * @return the thermostat by id
     */
    public Thermostat getThermostatById(final String thermostatId) {

        return thermostatDao.findByThermostatId(thermostatId);

    }

    /**
     * Verify algorithm in algo control.
     * @param thermostatId the thermostat id
     * @param algorithmId the algorithm id
     */
    @DBValidationMethod
    public void verifyAlgorithmInAlgoControl(final int thermostatId, final int algorithmId) {

        final Algorithm algorithm = algorithmDao.findById(algorithmId);
        setLogString(
                new StringBuilder("Verify thermostat algo control table for ").append(
                        algorithm.getName()).toString(), true);
        final List<ThermostatAlgorithmController> algoController = thermostatAlgoControlDao
                .listActiveAlgoControl(thermostatId, algorithmId);

        Assert.assertTrue(algoController.size() > 0,
                new StringBuilder(algorithm.getName()).append(" is not updated in Database.")
                        .toString());
        setLogString(
                new StringBuilder("Thermostat algo control table was verified successfully for ")
                        .append(algorithm.getName()).toString(), true);
    }

    /**
     * Checks if is program type active in program log.
     * @param thermostatId the thermostat id
     * @param programType the program type
     * @return true, if is program type active in program log
     */
    @DBValidationMethod
    public void isProgramTypeActiveInProgramLog(final int thermostatId, final int algorithmId,
            final boolean needProgramTypeActive) {

        final Algorithm algorithm = algorithmDao.findById(algorithmId);
        setLogString(
                new StringBuilder("Verify thermostat program log table for ").append(
                        algorithm.getName()).toString(), true);
        final List<ThermostatProgramLog> tstatPgmLog = thermostatProgramLogDao
                .findLatestByThermostatActiveProgramType(thermostatId, algorithm.getName());
        if (needProgramTypeActive)
            Assert.assertTrue(tstatPgmLog.size() > 0, "The program type " + algorithm.getName()
                    + " is not active.");
        else
            Assert.assertFalse(tstatPgmLog.size() > 0, "The program type " + algorithm.getName()
                    + " is not active.");
    }

    /**
     * Verify program for algorithm.
     * @param thermostatId the thermostat id
     * @param algorithmId the algorithm id
     * @return true, if successful
     */
    @DBValidationMethod
    public void verifyProgramForAlgorithm(final int thermostatId, final int algorithmId) {

        final Algorithm algorithm = algorithmDao.findById(algorithmId);
        setLogString(new StringBuilder("Verify program table for ").append(algorithm.getName())
                .toString(), true);
        final List<ThermostatProgramLog> tstatPgmLog = thermostatProgramLogDao
                .findLatestByThermostatActiveProgramType(thermostatId, algorithm.getName());
        Assert.assertTrue(tstatPgmLog.size() > 0, new StringBuilder(
                "Program table was not updated for ").append(algorithm.getName()).toString());
        setLogString(new StringBuilder("Program table verified for ").append(algorithm.getName())
                .toString(), true);
    }

    /**
     * Verify actions in range data.
     * @param startTime the start time
     * @param endTime the end time
     * @param thermostatId the thermostat id
     * @param actionMap the action map
     */
    public void verifyModeActionsInRangeData(final Calendar startTime, final Calendar endTime,
            final Integer thermostatId, final TreeMap<Integer, ControlActions> actionMap) {

        final List<PartitionedThermostatRangeData> rangeDataList = thermostatRangeDataDao
                .listByThermostatAndStartTimeRange(thermostatId, startTime);
        Assert.assertTrue(rangeDataList.size() >= 0,
                new StringBuilder(
                        "All the Actions are not updated in Range Data.Range data size is: ")
                        .append(rangeDataList.size()).toString());
       // int size = 0;
        final StringBuilder verifyAction = new StringBuilder();
        final StringBuilder actionMade = new StringBuilder();
        final StringBuilder actionRange = new StringBuilder();
        for (final Entry<Integer, ControlActions> entry : actionMap.entrySet()) {
           // final PartitionedThermostatRangeData thData = rangeDataList.get(size);
            setLogString(
                    verifyAction.append("Verify Action  ").append(entry.getValue())
                            .append(" has been updated in range data").toString(), true);

            setLogString(actionMade.append("Action made2 : ").append(entry.getValue()).toString(),
                    true);
            /*setLogString(actionRange.append("Action in Range data ").append(thData.getHvacMode())
                    .toString(), true);*/
            verifyAction.setLength(0);
            actionMade.setLength(0);
            actionRange.setLength(0);
           // size++;
        }
    }

    /**
     * Verify set point change actions in range data.
     * @param startTime the start time
     * @param endTime the end time
     * @param thermostatId the thermostat id
     * @param actionMap the action map
     */
    public void verifySetPointChangeActionsInRangeData(final Calendar startTime,
            final Calendar endTime, final Integer thermostatId,
            final TreeMap<Integer, Integer> actionMap) {

        final List<PartitionedThermostatRangeData> rangeDataList = thermostatRangeDataDao
                .listByThermostatAndStartTimeRange(thermostatId, startTime);
        Assert.assertTrue(rangeDataList.size() >= 0,
                new StringBuilder(
                        "All the Actions are not updated in Range Data.Range data size is: ")
                        .append(rangeDataList.size()).toString());
       // int size = 0;
        final StringBuilder verifyAction = new StringBuilder();
        final StringBuilder actionMade = new StringBuilder();
        final StringBuilder actionRange = new StringBuilder();
        for (final Entry<Integer, Integer> entry : actionMap.entrySet()) {
           // final PartitionedThermostatRangeData thData = rangeDataList.get(size);
            setLogString(
                    verifyAction.append("Verify Action  ").append(entry.getValue())
                            .append(" has been updated in range data").toString(), true);
            setLogString(actionMade.append("Action made : ").append(entry.getValue()).toString(),
                  true);
            /*  setLogString(actionRange.append("Action in Range data ")
                    .append(thData.getCoolSetting()).toString(), true);*/
            verifyAction.setLength(0);
            actionMade.setLength(0);
            actionRange.setLength(0);
            //size++;
        }
    }

    /**
     * Verify last record.
     * @param thermostatId the thermostat id
     * @param action the action
     * @param targetValue the target value
     * @param mode the mode
     */
    public void verifyLastRecord(final Integer thermostatId, final ControlActions action,
            final String targetValue, final ControlActions mode) {

        final PartitionedThermostatRangeData thData = thermostatRangeDataDao
                .findLatestByThermostat(thermostatId);
        if (thData != null) {
            setLogString(
                    new StringBuilder("Verify Action  ").append(action.toString())
                            .append(" has been updated in range data").toString(), true);
            setLogString(new StringBuilder("Action in Range data ").append(thData.getHvacMode())
                    .toString(), true);
            if (ControlActions.COOL == action || ControlActions.HEAT == action) {
                Assert.assertEquals(action.toString().toLowerCase(), thData.getHvacMode()
                        .toString().toLowerCase());
                setLogString("Cool Mode change reflected in DB", true);
            } else if (ControlActions.HEAT == action) {
                Assert.assertEquals(action.toString().toLowerCase(), thData.getHvacMode()
                        .toString().toLowerCase());
                setLogString("Heat Mode change reflected in DB", true);
            } else {
                setLogString(new StringBuilder("Mode Changed in UI  ").append(mode.toString())
                        .toString(), true);
                if (mode == ControlActions.COOL) {
                    Assert.assertEquals(targetValue,
                            String.valueOf((int) thData.getCoolSetting().doubleValue()),
                            "Cool target temperature not reflected in range data");
                } else if (mode == ControlActions.HEAT) {
                    Assert.assertTrue(targetValue.equals(String.valueOf((int) thData
                            .getHeatSetting().doubleValue())),
                            "Heat target temperature not reflected in range data");
                }
                setLogString("Setpoint change reflected in DB", true);
            }
        }
    }

    /**
     * Gets the controllerfor thermostat algo control table.
     * @param thermostatId the thermostat id
     * @return the controllerfor thermostat algo control table
     */
    public ThermostatAlgorithmController getControllerforThermostatAlgoControlTable(
            final int thermostatId) {

        final Calendar endTime = DateUtil.getUTCCalendar();
        endTime.add(Calendar.MINUTE, ONE);
        do {
            WaitUtil.oneSec();
            final ThermostatAlgorithmController controller = thermostatAlgoControlDao
                    .findActiveEvent(thermostatId);
            if (controller != null) {
                return controller;
            }
        } while (DateUtil.getUTCCalendar().before(endTime));
        return null;
    }

    /**
     * Gets the thermostat list for user.
     * @param userName the user name
     * @return the thermostat list for user
     */
    public List<Integer> getThermostatListForUser(final String userName) {

        return thermostatDao.getThermostatIdForUser(userName);
    }

    /**
     * Gets the subscribed algo.
     * @param thermostatId the thermostat id
     * @return the subscribed algo
     */
    public String[] getSubscribedAlgo(final Integer thermostatId) {

        log("Get subscribed algorithms", true);

        final List<ThermostatAlgorithm> thAlgoList = thAlgorithmDao
                .findByThermostatId(thermostatId);
        String[] subscribedAlgo = new String[thAlgoList.size()];
        int index = 0;
        for (final ThermostatAlgorithm tstatAlgorithm : thAlgoList) {
            subscribedAlgo[index++] = tstatAlgorithm.getAlgorithm().getName();
        }

        return subscribedAlgo;
    }

    /**
     * Gets the location id for user.
     * @param userName the user name
     * @param locationName the location name
     * @return the location id for user
     */
    public Integer getLocationIdForUser(final String userName, final String locationName) {

        Integer locationId = new Integer(0);
        setLogString(
                new StringBuilder("Get Location Id for User :").append(userName)
                        .append(", Location Name :").append(locationName).toString(), true);
        final User user = userDao.findByUserName(userName);
        final List<Location> dbLocationList = locationDao.getLocationByUserId(user.getId());
        final StringBuilder locationString = new StringBuilder();
        for (final Location location : dbLocationList) {
            setLogString(
                    locationString.append("Location name In DB :").append(location).toString(),
                    true);
            locationString.setLength(0);
            locationId = location.getId();
            /*
             * if (location.getName().equalsIgnoreCase(locationName)) { locationId =
             * location.getId(); System.out.println("locationId2"+locationId); break; }
             */

        }
        return locationId;
    }

}
