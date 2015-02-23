/*
 * ScreenShotListener.java
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
 * The listener interface for receiving screenShot events.
 * The class that is interested in processing a screenShot
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addScreenShotListener<code> method. When
 * the screenShot event occurs, that object's appropriate
 * method is invoked.
 * @see ScreenShotEvent
 */
public class ScreenShotListener implements ITestListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScreenShotListener.class);
    
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

    	WaitUtil.mediumWait();
        PageUtil.saveScreenshot(result.getMethod().getTestClass().getName()
                .substring(result.getMethod().getTestClass().getName().lastIndexOf('.') + 1)
                + "." + result.getName(), DriverConfig.getDriver());

    }

    /**
     * On test skipped.
     * @param iTestResult the i test result
     * @see org.testng.ITestListener#onTestSkipped(org.testng.ITestResult)
     */
    @Override
    public void onTestSkipped(ITestResult iTestResult) {

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

        DriverConfig.setLogString("\033[42;1mPassed " + System.getProperty(DriverConfig.ENV) + " " + System.getProperty(DriverConfig.BROWSER) + " " + iTestResult.getName(), true);
    }
}
