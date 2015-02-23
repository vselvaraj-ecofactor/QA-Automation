/*
 * AutomationListener.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.platform.listener;

import static com.ecofactor.qa.automation.platform.util.CheckJenkinsParamUtil.*;
import static com.ecofactor.qa.automation.platform.util.TestCaseUtil.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.ecofactor.qa.automation.platform.annotation.BindOSOperation;
import com.ecofactor.qa.automation.platform.enums.Marker;
import com.ecofactor.qa.automation.platform.ops.TestOperations;
import com.ecofactor.qa.automation.platform.util.LogUtil;
import com.ecofactor.qa.automation.util.DateUtil;
import com.ecofactor.qa.automation.util.DriverConfig;
import com.ecofactor.qa.automation.util.PageUtil;
import com.ecofactor.qa.automation.util.SuiteReportItem;
import com.ecofactor.qa.automation.util.TestResultSetUp;

/**
 * The listener interface for receiving automation events. The class that is interested in
 * processing a automation event implements this interface, and the object created with that class
 * is registered with a component using the component's
 * <code>addAutomationListener<code> method. When
 * the automation event occurs, that object's appropriate
 * method is invoked.
 * @see AutomationEvent
 */
public class AutomationListener implements ISuiteListener, ITestListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestResultSetUp.class);

    private String suiteName = "";
    private String testName = "";
    public static Map<String, Integer> passMap = new HashMap<String, Integer>();
    public static Map<String, Integer> skipMap = new HashMap<String, Integer>();
    public static Map<String, Integer> failMap = new HashMap<String, Integer>();
    public static Map<String, Integer> totalResult = new HashMap<String, Integer>();

    public static List<String> passList = new ArrayList<String>();
    public static List<String> skipList = new ArrayList<String>();
    public static List<String> failList = new ArrayList<String>();

    static int skipVal = 0;
    static int passVal = 0;
    static int failVal = 0;

    private SuiteReportItem reportItem;
    /**
     * The mobile ops.
     */
    protected static TestOperations operations;

    /**
     * On start.
     * @param suite the suite
     * @see org.testng.ISuiteListener#onStart(org.testng.ISuite)
     */
    @Override
    public void onStart(ISuite suite) {

    }

    /**
     * On finish.
     * @param suite the suite
     * @see org.testng.ISuiteListener#onFinish(org.testng.ISuite)
     */
    @Override
    public void onFinish(ISuite suite) {

    }

    /**
     * Sets the mobile ops.
     * @param mobileOperations the new mobile ops
     */
    @BindOSOperation
    public static void setOperations(TestOperations mobileOperations) {

        operations = mobileOperations;
    }

    /**
     * On finish.
     * @param ctx the ctx
     * @see org.testng.ITestListener#onFinish(org.testng.ITestContext)
     */
    @Override
    public void onFinish(ITestContext ctx) {

        applyChanges();
    }

    /**
     * On start.
     * @param ctx the ctx
     * @see org.testng.ITestListener#onStart(org.testng.ITestContext)
     */
    @Override
    public void onStart(ITestContext ctx) {

        setSuiteName(ctx.getSuite().getName());
        this.testName = ctx.getName();
        reportItem = new SuiteReportItem();
        reportItem.setName(this.testName);
        reportItem.setStartTime(DateUtil.getUTCCurrentTimeStamp());
        reportItem.setBrowser(System.getProperty("browser"));
    }

    /**
     * On test failed but within success percentage.
     * @param result the result
     * @see org.testng.ITestListener#onTestFailedButWithinSuccessPercentage(org.testng.ITestResult)
     */
    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

    }

    /**
     * On test failure.
     * @param result the result
     * @see org.testng.ITestListener#onTestFailure(org.testng.ITestResult)
     */
    @Override
    public void onTestFailure(ITestResult result) {

        LogUtil.setLogString("\033[41;1mFailed  " + System.getProperty(DriverConfig.ENV) + ", "
                + result.getName(), true);

        if (!failList.contains(result.getMethod().toString())) {
            failList.add(result.getMethod().toString());
            failVal = failMap.get(testName) == null ? 1 : failMap.get(testName) + 1;
            ;
            failMap.put(testName, failVal);
        }

        final Throwable thrown = result.getThrowable();
        LogUtil.setLogString("Cause::: " + thrown, true);
        thrown.printStackTrace();
        final StackTraceElement[] outTrace = new StackTraceElement[0];
        thrown.setStackTrace(outTrace);
        LogUtil.setNoOfSections(0);
        LogUtil.setLogString(Marker.END, "", true);

        // WaitUtil.mediumWait();
        try {
            operations.takeScreenShot(result.getMethod().getTestClass().getName()
                    .substring(result.getMethod().getTestClass().getName().lastIndexOf('.') + 1)
                    + "." + result.getName());
        } catch (Exception e) {
            LogUtil.setLogString("Screenshot Failed." + e, true);
        }
    }

    /**
     * On test skipped.
     * @param iTestResult the i test result
     * @see org.testng.ITestListener#onTestSkipped(org.testng.ITestResult)
     */
    @Override
    public void onTestSkipped(ITestResult iTestResult) {

        LogUtil.setLogString("\033[44;1mSkipped " + System.getProperty(DriverConfig.ENV) + " "
                + iTestResult.getName(), true);
        LogUtil.setLogString(
                "----------------------------------------------------------------------------------------",
                true);
        LogUtil.setLogString("", true);
        if (!failList.contains(iTestResult.getMethod().toString())) {
            failList.add(iTestResult.getMethod().toString());
            if (failMap.get(testName) != null) {
                failVal = failMap.get(testName) + 1;
            } else {
                failVal = 1;
            }

            failMap.put(testName, failVal);
        }

        if (!skipList.contains(iTestResult.getMethod().toString())) {
            skipList.add(iTestResult.getMethod().toString());
            if (skipMap.get(suiteName) != null) {
                skipVal = skipMap.get(suiteName) + 1;
            } else {
                skipVal = 1;
            }
            skipMap.put(suiteName, skipVal);
        }
    }

    /**
     * On test start.
     * @param iTestResult the i test result
     * @see org.testng.ITestListener#onTestStart(org.testng.ITestResult)
     */
    @Override
    public void onTestStart(ITestResult iTestResult) {

        resetTestCaseData();
        setTestName(iTestResult.getMethod().getMethodName());
    }

    /**
     * On test success.
     * @param iTestResult the i test result
     * @see org.testng.ITestListener#onTestSuccess(org.testng.ITestResult)
     */
    @Override
    public void onTestSuccess(ITestResult iTestResult) {

        String testClass = iTestResult.getTestClass().getName();
        testClass = testClass.substring(testClass.lastIndexOf(".") + 1, testClass.length());
        PageUtil.deleteScreenshot(testClass + "." + iTestResult.getMethod(),
                operations.getDeviceDriver());
        LogUtil.setLogString("\033[42;1mPassed " + System.getProperty(DriverConfig.ENV) + " "
                + iTestResult.getName(), true);
        LogUtil.setNoOfSections(0);
        if (!passList.contains(iTestResult.getMethod().toString())) {
            passList.add(iTestResult.getMethod().toString());
            if (passMap.get(testName) != null) {
                passVal = passMap.get(testName) + 1;
            } else {
                passVal = 1;
            }

            if (failList.contains(iTestResult.getMethod().toString())) {
                int failVal = failMap.get(testName);
                failMap.put(testName, failVal - 1);
            }

            passMap.put(testName, passVal);
        }
    }

    private void applyChanges() {

        reportItem.setEndTime(DateUtil.getUTCCurrentTimeStamp());
        final int pass = passMap.get(this.testName) != null ? passMap.get(this.testName) : 0;
        final int fail = failMap.get(this.testName) != null ? failMap.get(this.testName) : 0;
        final int skip = skipMap.get(this.testName) != null ? skipMap.get(this.testName) : 0;
        final int total = pass + fail + skip;
        reportItem.setTotalTests(total);
        reportItem.setPassedTests(pass);
        reportItem.setFailedTests(fail);

        String env = System.getProperty("env") != null ? System.getProperty("env").toLowerCase()
                : "qa";
        String application = null;
        LOGGER.info("Suite name : " + this.suiteName);
        application = System.getProperty("planName");
        if (application == null || application.isEmpty()) {
            application = hasJenkinsMobileParams() ? System
                    .getProperty("deviceId") : System.getProperty("browser");
        } else if (!hasJenkinsMobileParams()) {
            application = System.getProperty("browser");
        }

        if (application.contains(" ")) {
            application = application.replaceAll(" ", "_");
        }

        if (application.contains("#")) {
            application = application.replaceAll("#", "_");
        }

        System.out.println("App " + application + ", Suite " + suiteName + ", Test Name  : "
                + this.testName);
        reportItem.setModuleName(application);
        final ObjectMapper jsonMapper = new ObjectMapper();
        Calendar currentTime = DateUtil.getUTCCalendar();
        try {
            DriverConfig.setLogString("Create json for relevant run (" + reportItem.getName()
                    + "), Current Time : " + DateUtil.format(currentTime, DateUtil.DATE_FMT_FULL),
                    true);
            File file = new File("target/json/" + env + "/" + application + "/"
                    + reportItem.getName() + ".json");
            DriverConfig.setLogString("Json Path " + "target/json/" + env + "/" + application + "/"
                    + reportItem.getName() + ".json ", true);
            file.getParentFile().mkdirs();
            jsonMapper.writeValue(file, reportItem);
            LOGGER.info("Json File " + file.getAbsolutePath() + ", exists " + file.exists());
        } catch (IOException e) {
            LOGGER.error("Exception while converting test result into JSON for monitoring.", e);
        }
        currentTime = DateUtil.getUTCCalendar();
        DriverConfig.setLogString("Created json for (" + reportItem.getName()
                + ") , Current Time : " + DateUtil.format(currentTime, DateUtil.DATE_FMT_FULL),
                true);
    }
}
