/*
 * TestLogListener.java
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
 * TestLogListener implements the TestListener.
 * @author $Author: dramkumar $
 * @version $Rev: 31221 $
 */
public class TestLogListener implements ITestListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestLogListener.class);
    String screenshot = System.getProperty("screenshot");
    /**
     * On finish.
     * @param ctx the ctx
     * @see org.testng.ITestListener#onFinish(org.testng.ITestContext)
     */
    @Override
    public void onFinish(ITestContext ctx) {

    }

    /**
     * On start.
     * @param ctx the ctx
     * @see org.testng.ITestListener#onStart(org.testng.ITestContext)
     */
    @Override
    public void onStart(ITestContext ctx) {

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
     * @param iTestResult the i test result
     * @see org.testng.ITestListener#onTestFailure(org.testng.ITestResult)
     */
    @Override
    public void onTestFailure(ITestResult result) {

        //LOGGER.error("\033[41;1mFailed "  + iTestResult.getName() + "\033[0m \033[1m" + System.getProperty(DriverConfig.ENV) + " " + System.getProperty(DriverConfig.BROWSER) + "\033[0m");
        DriverConfig.setLogString("\033[41;1mFailed  " + System.getProperty(DriverConfig.ENV) + " " + System.getProperty(DriverConfig.BROWSER) + " " + result.getName(), true);
        WaitUtil.mediumWait();
        WaitUtil.smallWait();

        final Throwable thrown = result.getThrowable();
        DriverConfig.setLogString("Cause::: " + thrown, true);
        thrown.printStackTrace();

		if (screenshot == null || screenshot.equalsIgnoreCase("true")) {
        PageUtil.saveScreenshot(result.getMethod().getTestClass().getName()
                .substring(result.getMethod().getTestClass().getName().lastIndexOf('.') + 1)
                + "." + result.getName(), DriverConfig.getDriver());
        }
    }

    /**
     * On test skipped.
     * @param iTestResult the i test result
     * @see org.testng.ITestListener#onTestSkipped(org.testng.ITestResult)
     */
    @Override
    public void onTestSkipped(ITestResult iTestResult) {

        //LOGGER.error("\033[44;1mSkipped " + iTestResult.getName() + "\033[0m \033[1m" + System.getProperty(DriverConfig.ENV) + " " + System.getProperty(DriverConfig.BROWSER) + "\033[0m" );
        DriverConfig.setLogString("\033[44;1mSkipped " + System.getProperty(DriverConfig.ENV) + " " + System.getProperty(DriverConfig.BROWSER) + " " + iTestResult.getName(), true);
    }

    /**
     * On test start.
     * @param iTestResult the i test result
     * @see org.testng.ITestListener#onTestStart(org.testng.ITestResult)
     */
    @Override
    public void onTestStart(ITestResult iTestResult) {

    }

    /**
     * On test success.
     * @param iTestResult the i test result
     * @see org.testng.ITestListener#onTestSuccess(org.testng.ITestResult)
     */
    @Override
    public void onTestSuccess(ITestResult iTestResult) {

        //LOGGER.error("\033[42;1mPassed " + iTestResult.getName() + "\033[0m \033[1m" + System.getProperty(DriverConfig.ENV) + " " + System.getProperty(DriverConfig.BROWSER) + "\033[0m");
        DriverConfig.setLogString("\033[42;1mPassed " + System.getProperty(DriverConfig.ENV) + " " + System.getProperty(DriverConfig.BROWSER) + " " + iTestResult.getName(), true);
    }
}
