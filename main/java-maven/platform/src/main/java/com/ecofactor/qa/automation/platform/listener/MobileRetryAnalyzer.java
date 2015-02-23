/*
 * MobileRetryAnalyzer.java
 * Copyright (c) 2012, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.platform.listener;

import java.io.File;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import static com.ecofactor.qa.automation.platform.util.TestCaseUtil.*;

/**
 * The Class MobileRetryAnalyzer.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class MobileRetryAnalyzer implements IRetryAnalyzer {

    /**
     * The count.
     */
    private int count = 1;
    
    public static final int MAX_RETRY_COUNT = 2;
    private static final Logger LOGGER = LoggerFactory.getLogger(MobileRetryAnalyzer.class);

    /**
     * Retry.
     * @param result the result
     * @return true, if successful
     * @see org.testng.IRetryAnalyzer#retry(org.testng.ITestResult)
     */
    public boolean retry(final ITestResult result) {

        count++;
        if (count <= MAX_RETRY_COUNT) {
            LOGGER.error("\033[45;1mRetry " + result.getName() + "\033[0m");
            final File screenshotsFolder = new File("target" + File.separator + "screenshots"
                    + File.separator + getTestName());
            if (screenshotsFolder.exists()) {
                final File[] files = screenshotsFolder.listFiles();
                for (final File screenshotFile : files) {
                    screenshotFile.delete();
                }
            }
            final String fileToDelete = result.getMethod().getTestClass().getName()
                    .substring(result.getMethod().getTestClass().getName().lastIndexOf('.') + 1)
                    + "." + result.getName();

            final File failureScreenShot = new File("target" + File.separator + "screenshots"
                    + File.separator + fileToDelete + ".png");
            if (failureScreenShot.exists()) {
                failureScreenShot.delete();
            }
            return true;
        }
        return false;
    }
}
