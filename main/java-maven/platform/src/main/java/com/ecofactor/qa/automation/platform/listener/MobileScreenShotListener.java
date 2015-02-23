/*
 * MobileScreenShotListener.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.platform.listener;

import static com.ecofactor.qa.automation.platform.util.TestCaseUtil.*;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.ecofactor.qa.automation.platform.annotation.BindOSOperation;
import com.ecofactor.qa.automation.platform.exception.DeviceException;
import com.ecofactor.qa.automation.platform.ops.TestOperations;
import com.ecofactor.qa.automation.platform.util.LogUtil;
import com.ecofactor.qa.automation.util.DriverConfig;
import com.ecofactor.qa.automation.util.PageUtil;
import com.ecofactor.qa.automation.util.WaitUtil;

/**
 * The listener interface for receiving screenShot events. The class that is interested in
 * processing a screenShot event implements this interface, and the object created with that class
 * is registered with a component using the component's
 * <code>addScreenShotListener<code> method. When
 * the screenShot event occurs, that object's appropriate
 * method is invoked.
 * @see ScreenShotEvent
 */
public class MobileScreenShotListener implements ITestListener {

    /**
     * The mobile ops.
     */
    private static TestOperations operations;

    /**
     * Sets the mobile ops.
     * @param mobileOperations the new mobile ops
     */
    @BindOSOperation
    public static void setOperations(final TestOperations mobileOperations) {

        synchronized(operations) {
            operations = mobileOperations;
        }
    }

    /**
     * On finish.
     * @param ctx the ctx
     * @see org.testng.ITestListener#onFinish(org.testng.ITestContext)
     */
    @Override
    public void onFinish(final ITestContext ctx) {

        // Empty implementation.
    }

    /**
     * On start.
     * @param ctx the ctx
     * @see org.testng.ITestListener#onStart(org.testng.ITestContext)
     */
    @Override
    public void onStart(final ITestContext ctx) {

        setSuiteName(ctx.getSuite().getName());
    }

    /**
     * On test failed but within success percentage.
     * @param result the result
     * @see org.testng.ITestListener#onTestFailedButWithinSuccessPercentage(org.testng.ITestResult)
     */
    @Override
    public void onTestFailedButWithinSuccessPercentage(final ITestResult result) {

        // Empty implementation.
    }

    /**
     * On test failure.
     * @param result the result
     * @see org.testng.ITestListener#onTestFailure(org.testng.ITestResult)
     */
    @Override
    public void onTestFailure(final ITestResult result) {

        LogUtil.setLogString("\033[41;1mFailed  " + System.getProperty(DriverConfig.ENV)
                + ", " + result.getName(), true);
        WaitUtil.mediumWait();
        try {
            operations.takeScreenShot(result.getMethod().getTestClass().getName()
                    .substring(result.getMethod().getTestClass().getName().lastIndexOf('.') + 1)
                    + "." + result.getName());
        } catch (final DeviceException e) {
            e.printStackTrace();
        }
    }

    /**
     * On test skipped.
     * @param iTestResult the i test result
     * @see org.testng.ITestListener#onTestSkipped(org.testng.ITestResult)
     */
    @Override
    public void onTestSkipped(final ITestResult iTestResult) {

        LogUtil.setLogString("\033[44;1mSkipped " + System.getProperty(DriverConfig.ENV) + " "
                + iTestResult.getName(), true);
    }

    /**
     * On test start.
     * @param iTestResult the i test result
     * @see org.testng.ITestListener#onTestStart(org.testng.ITestResult)
     */
    @Override
    public void onTestStart(final ITestResult iTestResult) {

        resetTestCaseData();
        setTestName(iTestResult.getMethod().getMethodName());
    }

    /**
     * On test success.
     * @param iTestResult the i test result
     * @see org.testng.ITestListener#onTestSuccess(org.testng.ITestResult)
     */
    @Override
    public void onTestSuccess(final ITestResult iTestResult) {

        String testClass = iTestResult.getTestClass().getName();
        testClass = testClass.substring(testClass.lastIndexOf('.') + 1, testClass.length());
        PageUtil.deleteScreenshot(testClass + "." + iTestResult.getMethod(),
                operations.getDeviceDriver());
        LogUtil.setLogString("\033[42;1mPassed " + System.getProperty(DriverConfig.ENV) + " "
                + iTestResult.getName(), true);
    }
}
