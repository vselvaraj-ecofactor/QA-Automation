/*
 * AbstractMobileOperations.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.platform.ops.impl;

import static com.ecofactor.qa.automation.platform.util.DeviceUtil.*;
import static com.ecofactor.qa.automation.platform.util.LogUtil.*;
import static com.ecofactor.qa.automation.platform.util.TestCaseUtil.*;
import static com.ecofactor.qa.automation.util.WaitUtil.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ecofactor.qa.automation.platform.enums.LogSection;
import com.ecofactor.qa.automation.platform.exception.DeviceException;
import com.ecofactor.qa.automation.platform.ops.AppiumOperations;
import com.ecofactor.qa.automation.platform.ops.DeviceOperations;
import com.ecofactor.qa.automation.platform.util.SystemUtil;

/**
 * The Class AbstractMobileOperations.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public abstract class AbstractMobileOperations extends AbstractDriverOperations implements
        DeviceOperations, AppiumOperations {

    /**
     * The Constant DEFAULT_PORT.
     */
    protected static final int DEFAULT_PORT = 4723;

    /**
     * The device props.
     */
    protected Properties deviceProps = new Properties();

    /**
     * The Constant LOGGER.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractMobileOperations.class);

    /**
     * Gets the device list.
     * @return the device list
     * @see com.ecofactor.qa.automation.mobile.ops.DeviceOperations#getDeviceList()
     */
    @Override
    public String[] getDeviceList() {

        return getDeviceIds();
    }

    /**
     * Gets the device info.
     * @param deviceId the device id
     * @return the device info
     * @see com.ecofactor.qa.automation.mobile.ops.DeviceOperations#getDeviceInfo(java.lang.String)
     */
    @Override
    public String[] getDeviceInfo(final String deviceId) {

        final List<String> commands = createCmdForDeviceInfo(deviceId);
        return runCommands(commands);
    }

    /**
     * Creates the cmd for device info.
     * @param deviceId the device id
     * @return the list
     */
    protected abstract List<String> createCmdForDeviceInfo(final String deviceId);

    /**
     * Gets the device props map.
     * @param deviceId the device id
     * @param deviceVersionMap the device version map
     * @return the device props map
     * @throws DeviceException the device exception
     */
    protected abstract void getDevicePropsMap(final String deviceId,
            final Map<String, String> deviceVersionMap) throws DeviceException;

    /**
     * Creates the device mapping.
     * @param deviceId the device id
     * @throws DeviceException the device exception
     * @see com.ecofactor.qa.automation.mobile.ops.DeviceOperations#createDeviceMapping()
     */
    @Override
    public void createDeviceMapping(final String deviceId) throws DeviceException {

        final ConcurrentHashMap<String, String> deviceVersionMap = new ConcurrentHashMap<>();
        getDevicePropsMap(deviceId, deviceVersionMap);
        try {
            addPortToProperties(deviceVersionMap);
            writePropertiesToFile(deviceProps);
        } catch (final IOException e) {
            LOGGER.info("Error in Create device mapping", e);
        }
    }

    /**
     * Adds the port to properties.
     * @param devices the devices
     * @throws IOException Signals that an I/O exception has occurred.
     */
    protected void addPortToProperties(final Map<String, String> devices) throws IOException {

        final List<String> deviceVersions = new ArrayList<>();
        for (final String deviceId : devices.keySet()) {
            deviceVersions.add(deviceProps.getProperty(devices.get(deviceId) + ".Version"));
        }
        Collections.sort(deviceVersions);
        int port = -1;
        for (final String key : deviceVersions) {
            deviceProps.put(devices.get(key), String.valueOf(DEFAULT_PORT + ++port));
        }
    }

    /**
     * Clean and start appium.
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws DeviceException the device exception
     */
    protected void cleanAndStartAppium() throws IOException, DeviceException {

        stopAppiumServer();
        final String forceValue = System.getProperty("force");
        if (forceValue == null || forceValue.equalsIgnoreCase("true")) {
            createDeviceMapping(getDeviceIdParam());
            startAppiumServer();
        } else {
            final File file = new File("deviceDetails.properties");
            if (file.exists()) {
                startAppiumServer();
            }
        }
    }

    /**
     * Creates the cmd for device ids.
     * @return the list
     */
    protected abstract String[] getDeviceIds();

    /**
     * Verify appium server is running.
     */
    protected abstract void verifyAppiumServerIsRunning();

    /**
     * Close.
     * @see com.ecofactor.qa.automation.mobile.ops.TestOperations#close()
     */
    @Override
    public void close() {

        super.close();
        stopAppiumServer();
    }

    /**
     * Initialize.
     * @throws DeviceException the device exception
     * @see com.ecofactor.qa.automation.mobile.ops.TestOperations#initialize()
     */
    @Override
    public void initialize() throws DeviceException {

        super.initialize();
        final String nodeName = (System.getenv("NODE_NAME") == null ? "SLAVE:" + "Not Found"
                + "\033[0m" : "SLAVE:" + System.getenv("NODE_NAME").toUpperCase());
        setHeading("DEVICE: " + System.getenv("deviceNumber") + "\n" + nodeName, true);
        setHeading(
                "Device Id:" + getDeviceIdParam() + "\n" + "Parameters :(DB="
                        + System.getenv("dbValidate") + ",Admin=" + System.getenv("adminValidate")
                        + ",Screenshot=" + System.getenv("saveAllScreens") + ")", true);

        processDeviceId();
        // Display Device Information table to the console
        printDeviceInfo(getDeviceIdParam());
        stopAppiumServer();
        startAppiumServer();
        verifyAppiumServerIsRunning();
    }

    /**
     * Process device id.
     * @throws DeviceException the device exception
     */
    protected void processDeviceId() throws DeviceException {

        setLogString(LogSection.START, "Verify Device Connections", true);
        final String deviceId = getDeviceIdParam();
        final List<String> deviceIds = arrayToList(getDeviceList());

        setLogString(LogSection.START, "Connected Device Ids:", true);
        int count = 0;
        for (final String deviceIdValue : deviceIds) {
            setLogString(++count + ". " + deviceIdValue, true);
        }

        if (deviceIds != null && deviceIds.contains(deviceId)) {
            createDeviceMapping(deviceId);
            mediumWait();
            setLogString("Device " + deviceId + " is Connected", true);
            setLogString(LogSection.END, "Device online", true);
        } else {
            setErrorMsg("\033[41;1mMobile device with ID = '"
                    + deviceId
                    + "' is not connected at the moment. Please reconnect mobile device and try again.");
            hasErrors = true;
            setLogString(LogSection.END, "Device Connection issues", true);
            setLogString(getErrorMsg(), true);
            throw new DeviceException(getErrorMsg());
        }

        setLogString(LogSection.END, "Device Connection Completed", true);
    }

    /**
     * Cleanup.
     * @throws DeviceException the device exception
     * @see com.ecofactor.qa.automation.mobile.ops.TestOperations#cleanup()
     */
    @Override
    public void cleanup() throws DeviceException {

        closeDeviceDriver();
        super.cleanup();
        loadDeviceDriver();
        displayAppInfo();
    }

    /**
     * Gets the appium host url.
     * @return the appium host url
     */
    protected String getAppiumHostUrl() {

        final StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append("http://").append(SystemUtil.getHostName()).append(":")
                .append(getMobilePortParam()).append("/wd/hub/");
        return urlBuilder.toString();
    }
}