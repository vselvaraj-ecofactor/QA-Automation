/*
 * TestMethodDetails.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.util;

/**
 * The Class TestMethodDetails.
 * @author $Author: rvinoopraj $
 * @version $Rev: 29060 $ $Date: 2014-03-05 18:40:18 +0530 (Wed, 05 Mar 2014) $
 */
public class TestMethodDetails {

    /**
     * Gets the test case name.
     * @return the test case name
     */
    public String getTestCaseName() {

        return testCaseName;
    }

    /**
     * Sets the test case name.
     * @param testCaseName the new test case name
     */
    public void setTestCaseName(String testCaseName) {

        this.testCaseName = testCaseName;
    }

    /**
     * Gets the test link id.
     * @return the test link id
     */
    public String getTestLinkId() {

        return testLinkId;
    }

    /**
     * Sets the test link id.
     * @param testLinkId the new test link id
     */
    public void setTestLinkId(String testLinkId) {

        this.testLinkId = testLinkId;
    }

    private String testCaseName;
    private String testLinkId;

}
