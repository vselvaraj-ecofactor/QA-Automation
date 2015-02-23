package com.ecofactor.qa.automation.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.IRetryAnalyzer;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class WebDriverListener implements IRetryAnalyzer, ITestListener {

    private static int failedCount = 1;
    private int count = 1;
    public static final int MAX_RETRY_COUNT = 2;
    private static Logger LOGGER = LoggerFactory.getLogger(WebDriverListener.class);

    /**
     * Retry.
     * @param result the result
     * @return true, if successful
     * @see org.testng.IRetryAnalyzer#retry(org.testng.ITestResult)
     */
    public boolean retry(ITestResult result) {

        count++;
        if (count <= MAX_RETRY_COUNT) {

            result.setStatus(ITestResult.SKIP);
            LOGGER.error("\033[45;1mRetry " + result.getName() + "\033[0m");
            return true;
        } else {
            PageUtil.saveScreenshot(
                    result.getMethod().getTestClass().getName().substring(result.getMethod().getTestClass().getName().lastIndexOf('.') + 1) + "." + result.getName(),
                    DriverConfig.getDriver());
            return false;
        }
    }


    @Override
    public void onTestStart(ITestResult result) {

        // TODO Auto-generated method stub

    }

    @Override
    public void onTestSuccess(ITestResult result) {

       // DriverConfig.closeDriver();
    }

    @Override
    public void onTestFailure(ITestResult result) {
/*
        failedCount++;
        if (failedCount>2) {
            System.out.println("Close");
            DriverConfig.closeDriver();
        }
*/
    }

    @Override
    public void onTestSkipped(ITestResult result) {

        // TODO Auto-generated method stub

    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

        // TODO Auto-generated method stub

    }

    @Override
    public void onStart(ITestContext context) {

        // TODO Auto-generated method stub

    }

    @Override
    public void onFinish(ITestContext context) {

        // TODO Auto-generated method stub

    }

    }
