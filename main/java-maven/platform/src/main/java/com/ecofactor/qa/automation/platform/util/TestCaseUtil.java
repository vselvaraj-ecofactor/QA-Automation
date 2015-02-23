/*
 * TestCaseUtil.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.platform.util;

/**
 * The Class TestCaseUtil.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public final class TestCaseUtil {
    
    /**
     * The suite name.
     */
    public static String suiteName;

    /**
     * The test name.
     */
    public static String testName;

    /**
     * The has errors.
     */
    public static boolean hasErrors;

    /**
     * The error msg.
     */
    public static String errorMsg = "";

    /**
     * Instantiates a new test case util.
     */
    private TestCaseUtil() {
        
        //Private constructor.
    }
    
    /**
     * Gets the test name.
     * @return the test name
     */
    public static String getTestName() {

        return testName;
    }

    /**
     * Sets the test name.
     * @param testName the new test name
     */
    public static void setTestName(final String testName) {

        TestCaseUtil.testName = testName;
    }

    /**
     * Checks if is checks for errors.
     * @return true, if is checks for errors
     */
    public static boolean isHasErrors() {

        return hasErrors;
    }

    /**
     * Sets the checks for errors.
     * @param hasErrors the new checks for errors
     */
    public static void setHasErrors(final boolean hasErrors) {

        TestCaseUtil.hasErrors = hasErrors;
    }

    /**
     * Gets the error msg.
     * @return the error msg
     */
    public static String getErrorMsg() {

        return errorMsg;
    }

    /**
     * Sets the error msg.
     * @param errorMsg the new error msg
     */
    public static void setErrorMsg(final String errorMsg) {

        TestCaseUtil.errorMsg = errorMsg;
    }

    /**
     * Gets the suite name.
     * @return the suite name
     */
    public static String getSuiteName() {

        return suiteName;
    }

    /**
     * Sets the suite name.
     * @param suiteName the new suite name
     */
    public static void setSuiteName(final String suiteName) {

        TestCaseUtil.suiteName = suiteName;
    }

    /**
     * Reset test case data.
     */
    public static void resetTestCaseData() {

        setHasErrors(false);
        setErrorMsg(null);
        setSuiteName(null);
        setTestName(null);
    }

}
