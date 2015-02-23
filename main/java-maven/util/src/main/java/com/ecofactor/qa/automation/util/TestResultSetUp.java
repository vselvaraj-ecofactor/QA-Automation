/*
 * TestResultSetUp.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.util;

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

/**
 * TestResultSetUp implements the test listener.
 * @author $Author: dramkumar $
 * @version $Rev: 24484 $ $Date: 2013-10-23 16:04:34 +0530 (Wed, 23 Oct 2013) $
 */
public class TestResultSetUp implements ITestListener, ISuiteListener {

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
     * On test failure.
     * @param tr the tr
     * @see org.testng.ITestListener#onTestFailure(org.testng.ITestResult)
     */
    public void onTestFailure(ITestResult tr) {

        if (!failList.contains(tr.getMethod().toString())) {
            failList.add(tr.getMethod().toString());
            if (failMap.get(testName) != null) {
                failVal = failMap.get(testName) + 1;
            } else {
                failVal = 1;
            }

            failMap.put(testName, failVal);
        }
    }

    /**
     * On test skipped.
     * @param tr the tr
     * @see org.testng.ITestListener#onTestSkipped(org.testng.ITestResult)
     */
    public void onTestSkipped(ITestResult tr) {

        if (!failList.contains(tr.getMethod().toString())) {
            failList.add(tr.getMethod().toString());
            if (failMap.get(testName) != null) {
                failVal = failMap.get(testName) + 1;
            } else {
                failVal = 1;
            }

            failMap.put(testName, failVal);
        }

        if (!skipList.contains(tr.getMethod().toString())) {
            skipList.add(tr.getMethod().toString());
            if (skipMap.get(suiteName) != null) {
                skipVal = skipMap.get(suiteName) + 1;
            } else {
                skipVal = 1;
            }
            skipMap.put(suiteName, skipVal);
        }
    }

    /**
     * On test success.
     * @param tr the tr
     * @see org.testng.ITestListener#onTestSuccess(org.testng.ITestResult)
     */
    public void onTestSuccess(ITestResult tr) {

        if (!passList.contains(tr.getMethod().toString())) {
            passList.add(tr.getMethod().toString());
            if (passMap.get(testName) != null) {
                passVal = passMap.get(testName) + 1;
            } else {
                passVal = 1;
            }

            if (failList.contains(tr.getMethod().toString())) {
                int failVal = failMap.get(testName);
                failMap.put(testName, failVal - 1);
            }

            passMap.put(testName, passVal);
        }
    }

    /**
     * On start.
     * @param testContext the test context
     * @see org.testng.ITestListener#onStart(org.testng.ITestContext)
     */
    @Override
    public void onStart(ITestContext testContext) {

        this.testName = testContext.getName();
        reportItem = new SuiteReportItem();
        reportItem.setName(this.testName);
        reportItem.setStartTime(DateUtil.getUTCCurrentTimeStamp());
        reportItem.setBrowser(System.getProperty("browser"));
    }

    /**
     * On finish.
     * @param testContext the test context
     * @see org.testng.ITestListener#onFinish(org.testng.ITestContext)
     */
    @Override
    public void onFinish(ITestContext testContext) {

        reportItem.setEndTime(DateUtil.getUTCCurrentTimeStamp());
        final int pass = passMap.get(this.testName) != null ? passMap.get(this.testName) : 0;
        final int fail = failMap.get(this.testName) != null ? failMap.get(this.testName) : 0;
        final int skip = skipMap.get(this.testName) != null ? skipMap.get(this.testName) : 0;
        final int total = pass + fail + skip;
        reportItem.setTotalTests(total);
        reportItem.setPassedTests(pass);
        reportItem.setFailedTests(fail);

        String env = System.getProperty("env") != null ? System.getProperty("env").toLowerCase() : "qa";
        String application = null;
        LOGGER.info("Suite name : " + this.suiteName);
        if (this.suiteName.toLowerCase().indexOf("consumer") != -1) {
            application = "consumer";
        } else if (this.suiteName.toLowerCase().indexOf("insite") != -1) {
            application = "insite";
        } else if (this.suiteName.toLowerCase().contains("spo")) {
            application = "spo";
        } else if (this.suiteName.toLowerCase().contains("sawtooth")) {
            application = "st";
        } else if (this.suiteName.toLowerCase().contains("api reports")) {
            application = "api";
        }  else if (this.suiteName.toLowerCase().contains("opsscript")) {
            application = "opsscript";
        } else if (this.suiteName.toLowerCase().contains("comcast")) {
            application = "comcast";
        }

        System.out.println("App " + application + ", Suite " + suiteName + ", Test Name  : " + this.testName);
        reportItem.setModuleName(application);
        final ObjectMapper jsonMapper = new ObjectMapper();
        Calendar currentTime = DateUtil.getUTCCalendar();
        try {
            DriverConfig.setLogString("Create json for relevant run ("+reportItem.getName()+"), Current Time : " + DateUtil.format(currentTime, DateUtil.DATE_FMT_FULL), true);
            File file = new File("target\\json\\" + env + "\\" + application + "\\" + reportItem.getName() + ".json");
            DriverConfig.setLogString("Json Path " + "target\\json\\" + env + "\\" + application + "\\" + reportItem.getName() + ".json ", true);
            file.getParentFile().mkdirs();
            jsonMapper.writeValue(file, reportItem);
            LOGGER.info("Json File " + file.getAbsolutePath() + ", exists " + file.exists());
        } catch (IOException e) {
            LOGGER.error("Exception while converting test result into JSON for monitoring.", e);
        }
        currentTime = DateUtil.getUTCCalendar();
        DriverConfig.setLogString("Created json for ("+reportItem.getName()+") , Current Time : " + DateUtil.format(currentTime, DateUtil.DATE_FMT_FULL), true);
    }

    /**
     * On finish.
     * @param arg0 the arg0
     */
    public void onFinish(ISuite arg0) {

    }

    /**
     * On start.
     * @param isuite the isuite
     */
    public void onStart(ISuite isuite) {

        setSuiteName(isuite.getName());
    }

    /**
     * Sets the suite name.
     * @param suitName the new suite name
     */
    private void setSuiteName(String suitName) {

        this.suiteName = suitName;
    }

    /**
     * On test start.
     * @param result the result
     * @see org.testng.ITestListener#onTestStart(org.testng.ITestResult)
     */
    @Override
    public void onTestStart(ITestResult result) {

    }

    /**
     * On test failed but within success percentage.
     * @param result the result
     * @see org.testng.ITestListener#onTestFailedButWithinSuccessPercentage(org.testng.ITestResult)
     */
    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

    }
}
