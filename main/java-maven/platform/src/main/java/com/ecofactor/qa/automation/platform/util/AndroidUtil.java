/*
 * AndroidUtil.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.platform.util;

import static com.ecofactor.qa.automation.platform.util.DeviceUtil.*;
import static com.ecofactor.qa.automation.platform.util.LogUtil.*;
import static com.ecofactor.qa.automation.util.WaitUtil.*;

import com.ecofactor.qa.automation.platform.enums.LogSection;

/**
 * The Class AndroidUtil.This class handles all the android related adb processes, wifi status,
 * appium status ,etc
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public final class AndroidUtil {

    /**
     * Instantiates a new android util.
     */
    private AndroidUtil() {

    }

    /**
     * Method to handle Log initialization error.
     * @param exception the exception
     */
    public static void logInitializationError(final Exception exception) {

        final StringBuilder apiVersionProperty = new StringBuilder();
        apiVersionProperty.append(getDeviceIdParam());
        apiVersionProperty.append(".Version");
        final String apiVersion = getValueFromPropertyFile(apiVersionProperty.toString());
        if (apiVersion != null && !apiVersion.isEmpty() && Integer.valueOf(apiVersion) < 11) {

            final StringBuilder apiLevelMessage = new StringBuilder(
                    "The API Level for the connected Device is ");
            apiLevelMessage.append(apiVersion);
            apiLevelMessage.append(", So it may throw some exception.");
            setLogString(LogSection.END, apiLevelMessage.toString(), true);
        }
        final StringBuilder errorMessage = new StringBuilder("\033[41;1m Error in initialize (");
        errorMessage.append(exception.getMessage());
        errorMessage.append(')');

        setLogString(LogSection.END, errorMessage.toString(), true);
    }

    /**
     * Kill existing process.
     */
    public static void killExistingProcess() {

        setLogString("Kill adb devices", true);
        startProcessBuilder(arrayToList("taskkill", "/IM", "adb.exe", "/F"));
    }

    /**
     * Kiil process.
     * @param pid the pid
     */
    public static void killProcess(final String pid) {

        startProcessBuilder(arrayToList("Taskkill", "/PID", pid, "/F", "/T"));
        smallWait();
    }

    /**
     * Gets the wi fi connection state.
     * @return the wi fi connection state
     */
    public static String getWiFiConnectionState() {

        final String[] result = runCommands(arrayToList("adb", "-s", getDeviceIdParam(), "shell",
                "dumpsys", "connectivity"));
        for (final String lineValue : result) {
            if (lineValue.contains("NetworkInfo: type: WIFI[")
                    || lineValue.contains("NetworkInfo: type: WIFI(")) {
                final String state = lineValue.substring(lineValue.indexOf("state") - 1).trim();
                final String connectionStatus = state.substring(0, state.indexOf(',')).trim();
                return connectionStatus.split(":")[1].trim();
            }
        }
        return null;
    }

    /**
     * Checks whether the appium server running or not.
     * @param command the command
     * @return true, if is appium server running
     */
    public static boolean isAppiumServerRunning(final String command) {

        final String[] pid = runCommands(arrayToList("wmic", "process", "where",
                "name=\"node.exe\"", "get", "processid"));
        if (pid == null || pid.length == 0) {
            return false;
        }
        boolean isExists = false;
        for (final String entry : pid) {
            final String processId = entry.split("\t")[0].trim();
            stopProcess(command, processId);
            isExists = !processId.isEmpty() || isExists;
        }
        killExistingProcess();
        return isExists;
    }

    /**
     * Wrapper method to kill process
     * @param command the command
     * @param processId the process id
     */
    public static void stopProcess(final String command, final String processId) {

        if (command.contains("Stop")) {
            killProcess(processId);
        }
    }
}
