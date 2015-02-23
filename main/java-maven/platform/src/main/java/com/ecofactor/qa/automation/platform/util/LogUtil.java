/*
 * LogUtil.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.platform.util;

import static com.ecofactor.qa.automation.platform.util.CheckJenkinsParamUtil.*;
import static com.ecofactor.qa.automation.platform.util.DeviceUtil.*;
import static com.ecofactor.qa.automation.platform.util.JenkinsParamUtil.*;

import java.lang.reflect.Method;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestNGMethod;
import org.testng.Reporter;

import com.ecofactor.qa.automation.platform.enums.CustomLogLevel;
import com.ecofactor.qa.automation.platform.enums.LogSection;
import com.ecofactor.qa.automation.platform.enums.Marker;
import com.ecofactor.qa.automation.util.DateUtil;
import com.ecofactor.qa.automation.util.TestlinkUtil;
import com.ecofactor.qa.automation.util.test.FeatureUtil;
import com.google.inject.Inject;

/**
 * The Class LogUtil. All the operations pertaining to the logging process are being maintained
 * here. This class Initializes the logger, tracks the log messages, sets the header sets the white
 * spaces for the log statements
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class LogUtil {

    @Inject
    private TestlinkUtil testLinkUtil;
    @Inject
    private FeatureUtil featureUtil;
    private static String consoleOutput;
    private static ThreadLocal<String> logStringList = new ThreadLocal<String>();
    public static HashMap<Long, String> reporterLogMap = new HashMap<Long, String>();
    private static final Logger LOGGER = LoggerFactory.getLogger(LogUtil.class);
    private static int noOfSections = 0;
    private static boolean markerEnabled;
    private static final String LINE = "----------------------------------------------------------------------------------------------------------";
    private static final String PROJECT = "Mobile Regression New App";
    private static final String CONSUMER_API_PROJECT = "ConsumerAPI";
    private static final String APPLICATIONS = "Applications";
    private static final String TIMESTAMP_ZONE = "America/Los_Angeles";
    private static final String PLATFORM = "Platform";

    /**
     * Initial set of log statements whenever logging starts.
     * @param method the method
     */
    public void logStart(final Method method) {

        final String currentUTCTime = DateUtil.getUTCCurrentTimeStamp();
        final String currentPSTTime = DateUtil.getCurrentTimeStampForZone(TIMESTAMP_ZONE,
                DateUtil.DATE_FMT_FULL);
        final String name = method.getDeclaringClass().getSimpleName();
        final String test = method.getName();

        setLogString(
                new StringBuilder("Started test \033[43;1m").append(name).append(".").append(test)
                        .append("\033[0m").toString(), true);

        setLogString(
                new StringBuilder("PST Time : ").append(currentPSTTime).append(", UTC Time : ")
                        .append(currentUTCTime).append(", Env: ").append(System.getProperty("env"))
                        .append(", Node: ").append(System.getenv("NODE_NAME")).toString(), true);
    }

    /**
     * Gets the test case id.
     * @param testNGMethod the test ng method
     * @param methodName the method name
     * @return the test case id
     */
    public String getTestCaseId(final ITestNGMethod testNGMethod, final String methodName) {

        String project = null;
        final String absoluteName = testNGMethod.getTestClass().getName();
        if (hasProjectParam()) {
            project = APPLICATIONS;
        } else if (absoluteName.contains("newapp")) {
            project = PROJECT;
        } else if (absoluteName.contains("consumerapi")) {
            project = CONSUMER_API_PROJECT;
        } else if (absoluteName.contains("drapi")) {
            project = PLATFORM;
        }

        final String name = testNGMethod.getTestClass().getRealClass().getSimpleName();
        return testLinkUtil.getTestCaseId(project,
                new StringBuilder(name).append("#").append(methodName).toString());
    }

    /**
     * Initial set of log statements whenever logging starts.
     * @param method the method
     * @param params the params
     * @param deviceId the device id
     */
    public void logStart(final Method method, final Object[] params, final String deviceId) {

        String project = null;
        final String absoluteName = method.getDeclaringClass().getName();
        if (hasProjectParam()) {
            project = APPLICATIONS;
        } else if (absoluteName.contains("newapp")) {
            project = PROJECT;
        } else if (absoluteName.contains("consumerapi")) {
            project = CONSUMER_API_PROJECT;
        } else if (absoluteName.contains("drapi")) {
            project = PLATFORM;
        }

        setLogString("project " + project, true);

        final String currentUTCTime = DateUtil.getUTCCurrentTimeStamp();
        final String currentPSTTime = DateUtil.getCurrentTimeStampForZone(TIMESTAMP_ZONE,
                DateUtil.DATE_FMT_FULL);
        final String test = method.getName();
        final String name = method.getDeclaringClass().getSimpleName();
        final String testCaseId = testLinkUtil.getTestCaseId(project, new StringBuilder(name)
                .append("#").append(test).toString());
        setLogString("", true);
        setLogString(LogSection.START, new StringBuilder("Started test \033[43;1m").append(name)
                .append(".").append(test).append(" [").append(testCaseId).append("]\033[0m")
                .toString(), true);
        final String browser = hasJenkinsDeviceIdParam() ? "" : new StringBuilder(", Browser: ")
                .append(System.getProperty("browser")).toString();
        final String deviceIdParam = hasJenkinsDeviceIdParam() ? new StringBuilder(", Device Id : ")
                .append(deviceId).toString() : " ";
        setLogString(
                new StringBuilder("PST Time : ").append(currentPSTTime).append(", UTC Time : ")
                        .append(currentUTCTime).append(", Env: ").append(System.getProperty("env"))
                        .append(", Node: ").append(System.getenv("NODE_NAME")).append(browser)
                        .append(deviceIdParam).toString(), true);
        if ("Algorithm".equals(project)) {
            setLogString(
                    new StringBuilder("User Name: ").append(params[0]).append(", Thermostat Id: ")
                            .append(params[2]).toString(), true);
        } else if ("Consumer".equals(project)) {
            setLogString(new StringBuilder("User Name: ").append(params[0]).append(", Password: ")
                    .append(params[1]).toString(), true);
        } else if ("ConsumerAPI".equals(project)) {
            final StringBuilder logString = new StringBuilder("User Name: ").append(params[0]);
            if (params.length > 2) {
                logString.append(", Thermostat/Location/Ecp Id: ").append(params[2]);
            }
            setLogString(logString.toString(), true);
        }
    }

    /**
     * Final set of log statements whenever logging ends.
     * @param method the method
     * @param duration the duration
     */
    public void logEnd(final Method method, final long duration) {

        String project = null;
        final String absoluteName = method.getDeclaringClass().getName();
        if (absoluteName.contains("newapp")) {
            project = PROJECT;
        } else if (absoluteName.contains("consumerapi")) {
            project = CONSUMER_API_PROJECT;
        } else if (absoluteName.contains("drapi")) {
            project = PLATFORM;
        }
        final String name = method.getDeclaringClass().getSimpleName();
        final String test = method.getName();
        final String testCaseId = testLinkUtil.getTestCaseId(project, new StringBuilder(name)
                .append("#").append(test).toString());

        setLogString(
                LogSection.END,
                new StringBuilder("Completed test ").append(name).append(".").append(test)
                        .append(" [").append(testCaseId).append("] in \033[46;1m[")
                        .append(duration).append(" secs]\033[0m").toString(), true);

    }

    /**
     * Gets the log string.
     * @return the log string
     */
    public static String getLogString() {

        return logStringList.get();
    }

    /**
     * Sets the log message with console output on or off.
     * @param logStringVal the log string val
     * @param val the val
     */
    public static void setLogString(String logStringVal, final boolean val) {

        if (consoleOutput == null || consoleOutput.equalsIgnoreCase("false")) {
            final String indentLevel = addIndent(getNoOfSections());
            if (markerEnabled) {
                final String logValue = new StringBuilder("  ").append(logStringVal).toString();
                Reporter.log(new StringBuilder(indentLevel).append("\033[1;31m|").append(logValue)
                        .append(addSpace("", LINE.length() - logValue.length() - 1)).append("|")
                        .toString(), true);
            } else {
                Reporter.log(new StringBuilder(indentLevel).append(logStringVal).toString(), val);
            }
        } else if (consoleOutput != null && consoleOutput.equalsIgnoreCase("true")) {
            LOGGER.error(logStringVal);
            logStringVal = new StringBuilder(logStringVal).append("\n").toString();
            if (getLogString() == null) {
                logStringList.set(logStringVal);
                reporterLogMap.put(Thread.currentThread().getId(), logStringVal);
            } else {
                reporterLogMap.put(Thread.currentThread().getId(),
                        new StringBuilder(getLogString()).append(logStringVal).toString());
                logStringList
                        .set(new StringBuilder(getLogString()).append(logStringVal).toString());
            }
        }
    }

    /**
     * Sets the log string with logging level.
     * @param logStringVal the log string val
     * @param val the val
     * @param logLevel the log level
     */
    public static void setLogString(final String logStringVal, final boolean val,
            final CustomLogLevel logLevel) {

        if (getLogLevel().getValue() >= logLevel.getValue()) {
            setLogString(logStringVal, val);
        }
    }

    /**
     * Sets the heading level log statements.
     * @param logStringVal the log string val
     * @param val the val
     */
    public static void setHeading(String logStringVal, final boolean val) {

        final String line = "------------------------------------------------------------------------------------------";
        if (consoleOutput == null || consoleOutput.equalsIgnoreCase("false")) {

            final String[] contentArray = logStringVal.split("\n");
            Reporter.log(line, val);

            final StringBuilder formatString = new StringBuilder();

            for (final String content : contentArray) {
                final String space = addSpace("", line.length() - content.length());
                Reporter.log(formatString.append(space).append("\033[43;1m").append(content)
                        .toString(), val);
                formatString.setLength(0);
            }
            Reporter.log(line, val);
        } else if (consoleOutput != null && consoleOutput.equalsIgnoreCase("true")) {
            
            LOGGER.error(logStringVal);
            logStringVal = new StringBuilder(logStringVal).append("\n").toString();
            if (getLogString() == null) {
                logStringList.set(logStringVal);
                reporterLogMap.put(Thread.currentThread().getId(), logStringVal);
            } else {
                reporterLogMap.put(Thread.currentThread().getId(),
                        new StringBuilder(getLogString()).append(logStringVal).toString());
                logStringList
                        .set(new StringBuilder(getLogString()).append(logStringVal).toString());
            }
        }
    }

    /**
     * Sets the log string with their markers.
     * @param marker the marker
     * @param logStringVal the log string val
     * @param val the val
     */
    public static void setLogString(final Marker marker, final String logStringVal,
            final boolean val) {

        if (marker == Marker.START) {
            markerEnabled = true;
            final String indentLevel = addIndent(getNoOfSections());
            Reporter.log(new StringBuilder("\033[1;31m").append(" WORKAROUND ").toString(), val);
            Reporter.log(new StringBuilder("\033[1;31m").append(indentLevel).append(LINE)
                    .toString(), val);
            setLogString(logStringVal, val);

        } else if (marker == Marker.END) {
            final String indentLevel = addIndent(getNoOfSections());
            setLogString(logStringVal, val);
            Reporter.log(new StringBuilder("\033[1;31m").append(indentLevel).append(LINE)
                    .toString(), val);
            markerEnabled = false;
        }

    }

    /**
     * Sets the log string on the Log Section.
     * @param logSection the log section
     * @param logStringVal the log string val
     * @param val the val
     */
    public static void setLogString(final LogSection logSection, final String logStringVal,
            final boolean val) {

        String indentLevel = addIndent(getNoOfSections());

        if (logSection == LogSection.START) {
            Reporter.log("", val);
            Reporter.log(new StringBuilder(indentLevel).append(logStringVal).toString(), val);
            Reporter.log(new StringBuilder(indentLevel).append(addUnderline(logStringVal.length()))
                    .toString(), val);
            setNoOfSections(noOfSections + 1);
        } else if (logSection == LogSection.END) {
            setNoOfSections(noOfSections - 1);
            indentLevel = addIndent(getNoOfSections());
            Reporter.log(new StringBuilder(indentLevel).append(logStringVal).toString(), val);
            Reporter.log(new StringBuilder(indentLevel).append(addUnderline(logStringVal.length()))
                    .toString(), val);
        }
    }

    /**
     * Adds the indent spacing to the log statements.
     * @param indentLevel the indent level
     * @return the string
     */
    private static String addIndent(int indentLevel) {

        indentLevel += indentLevel;
        final StringBuilder space = new StringBuilder("");
        for (int i = 0; i < indentLevel; i++) {
            space.append(' ');
        }
        return space.toString();
    }

    /**
     * Adds the underline to log statements.
     * @param indentLevel the indent level
     * @return the string
     */
    private static String addUnderline(final int indentLevel) {

        final StringBuilder space = new StringBuilder("");
        for (int i = 0; i < indentLevel; i++) {
            space.append('-');
        }
        return space.toString();
    }

    /**
     * Adds the white space to the logs.
     * @param word the word
     * @param noOfCharac the no of charac
     * @return the string
     */
    public static String addSpace(String word, final int noOfCharac) {

        final int wordLength = word.length();
        final int noOfSpace = noOfCharac - wordLength;
        if (noOfSpace < 0) {
            return word;
        } else {
            final StringBuilder space = new StringBuilder("");
            for (int val = 0; val < noOfSpace; val++) {
                space.append(' ');
            }
            word = word + space;
        }
        return word;
    }

    /**
     * Prints the device info.
     * @param deviceId the device id
     */
    public static void printDeviceInfo(final String deviceId) {

        final String deviceState = getValueFromPropertyFile(new StringBuilder(deviceId).append(
                ".state").toString());

        if (SystemUtil.isMac()) {
            printIOSDeviceInfo(deviceId);
        } else {
            if (deviceState != null && hasJenkinsMobileParams()) {
                if ("Online".equalsIgnoreCase(deviceState)) {
                    printAndroidDeviceInfo(deviceId);
                } else {
                    setLogString(
                            new StringBuilder("Device state is ").append(deviceState)
                                    .append(". Device details are not available.").toString(), true);
                }
            }
        }

    }

    /**
     * Prints the android device info.
     * @param deviceId the device id
     */
    private static void printAndroidDeviceInfo(final String deviceId) {

        setLogString("Display Device Details", true);
        setLogString(
                "=================================================================================",
                true);
        setLogString(
                "|      Device Id       |     Model    |     Brand     |    OS Name    | API level  ",
                true);
        setLogString(
                "=================================================================================",
                true);
        setLogString(
                new StringBuilder("| ")
                        .append(addSpace(deviceId, 21))
                        .append("| ")
                        .append(addSpace(getValueFromPropertyFile(new StringBuilder(deviceId)
                                .append(".Model").toString()), 13))
                        .append("| ")
                        .append(addSpace(getValueFromPropertyFile(new StringBuilder(deviceId)
                                .append(".Brand").toString()), 14))
                        .append("| ")
                        .append(addSpace(getValueFromPropertyFile(new StringBuilder(deviceId)
                                .append(".OsName").toString()), 14))
                        .append("| ")
                        .append(addSpace(getValueFromPropertyFile(new StringBuilder(deviceId)
                                .append(".version").toString()), 8)).toString(), true);
        setLogString(
                "=================================================================================",
                true);
    }

    /**
     * Prints the ios device info.
     * @param deviceId the device id
     */
    private static void printIOSDeviceInfo(final String deviceId) {

        setLogString("Display Device Details", true);
        setLogString(
                "==========================================================================================================",
                true);
        setLogString(
                "|                  Device Id                   |      Device Name     |    Product Type     |  OS Version ",
                true);
        setLogString(
                "==========================================================================================================",
                true);
        setLogString(
                new StringBuilder("| ")
                        .append(addSpace(deviceId, 45))
                        .append("| ")
                        .append(addSpace(getValueFromPropertyFile(new StringBuilder(deviceId)
                                .append(".DeviceName").toString()), 21))
                        .append("| ")
                        .append(addSpace(getValueFromPropertyFile(new StringBuilder(deviceId)
                                .append(".ProductType").toString()), 20))
                        .append("| ")
                        .append(addSpace(getValueFromPropertyFile(new StringBuilder(deviceId)
                                .append(".version").toString()), 11)).toString(), true);
        setLogString(
                "==========================================================================================================",
                true);
    }

    /**
     * Gets the no of sections.
     * @return the no of sections
     */
    public static int getNoOfSections() {

        return noOfSections;
    }

    /**
     * Sets the no of sections.
     * @param noOfSections the new no of sections
     */
    public static void setNoOfSections(final int noOfSections) {

        LogUtil.noOfSections = noOfSections;
    }
}
