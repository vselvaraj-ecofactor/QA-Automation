/*
 * AlgoScreenshotAnalyzer.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

/**
 * RerunFailTestAnalyzer is used to rerun the failed test and saves the screenshot in case of a
 * failure in the rerun.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class AlgoScreenshotAnalyzer implements ITestListener {

    private static Logger LOGGER = LoggerFactory.getLogger(AlgoScreenshotAnalyzer.class);
    /**
     * On test failure.
     * @param result the result
     * @see org.testng.ITestListener#onTestFailure(org.testng.ITestResult)
     */
    @Override
    public void onTestFailure(ITestResult result) {


       PageUtil.saveScreenshot(result.getMethod().getTestClass().getName().substring(result.getMethod().getTestClass().getName().lastIndexOf('.') + 1) + "." + result.getName(), DriverConfig.getDriver());

    }

    /**
     * On test start.
     * @param result the result
     * @see org.testng.ITestListener#onTestStart(org.testng.ITestResult)
     */
    public void onTestStart(ITestResult result) {

    }

    /**
     * On test success.
     * @param result the result
     * @see org.testng.ITestListener#onTestSuccess(org.testng.ITestResult)
     */
    @Override
    public void onTestSuccess(ITestResult result) {

    }

    /**
     * On test skipped.
     * @param result the result
     * @see org.testng.ITestListener#onTestSkipped(org.testng.ITestResult)
     */
    public void onTestSkipped(ITestResult result) {

    }

    /**
     * On test failed but within success percentage.
     * @param result the result
     * @see org.testng.ITestListener#onTestFailedButWithinSuccessPercentage(org.testng.ITestResult)
     */
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

    }

    /**
     * On start.
     * @param context the context
     * @see org.testng.ITestListener#onStart(org.testng.ITestContext)
     */
    public void onStart(ITestContext context) {

    }

    /**
     * On finish.
     * @param context the context
     * @see org.testng.ITestListener#onFinish(org.testng.ITestContext)
     */
    public void onFinish(ITestContext context) {

    }
}