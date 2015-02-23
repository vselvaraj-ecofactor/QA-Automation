/*
 * RerunFailTestAnalyzer.java
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
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

/**
 * RerunFailTestAnalyzer is used to rerun the failed test and saves the screenshot in case of a
 * failure in the rerun.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class RerunFailTestAnalyzer implements IRetryAnalyzer {

    private int count = 1;
    public static final int MAX_RETRY_COUNT = 2;
    private static Logger LOGGER = LoggerFactory.getLogger(RerunFailTestAnalyzer.class);

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
            LOGGER.error("\033[45;1mRetry "  + result.getName() + "\033[0m");
            return true;
        } else {
            /*PageUtil.saveScreenshot(result.getMethod().getTestClass().getName()
                    .substring(result.getMethod().getTestClass().getName().lastIndexOf('.') + 1)
                    + "." + result.getName(), DriverConfig.getDriver());*/
            return false;
        }
    }


}