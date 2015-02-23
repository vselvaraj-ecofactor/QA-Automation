/*
 * SuiteReportItem.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.util;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonPropertyOrder;

/**
 * The Class SuiteReportItem.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
@JsonPropertyOrder({ "name", "totalTests", "passedTests", "failedTests", "startTime", "endTime", "duration", "browser" })
public class SuiteReportItem {

    private String name;
    private Integer totalTests;
    private Integer passedTests;
    private Integer failedTests;
    private String startTime;
    private String endTime;
    private String moduleName;
    private Long duration;
    private String browser;

    /**
     * Gets the browser.
     * @return the browser
     */
    public String getBrowser() {

        return browser;
    }

    /**
     * Sets the browser.
     * @param browser the new browser
     */
    public void setBrowser(String browser) {

        this.browser = browser;
    }

    /**
     * Sets the duration.
     * @param duration the new duration
     */
    public void setDuration(long duration) {

        this.duration = duration;
    }

    /**
     * Gets the duration.
     * @return the duration
     */
    public long getDuration() {

        Date start = DateUtil.parseToUTCDate(startTime, DateUtil.DATE_FMT_FULL);
        Date end = DateUtil.parseToUTCDate(endTime, DateUtil.DATE_FMT_FULL);
        duration = DateUtil.differenceInMinutes(start, end);
        return duration;
    }

    /**
     * Gets the name.
     * @return the name
     */
    public String getName() {

        return name;
    }

    /**
     * Sets the name.
     * @param name the new name
     */
    public void setName(String name) {

        this.name = name;
    }

    /**
     * Gets the total tests.
     * @return the total tests
     */
    public Integer getTotalTests() {

        return totalTests;
    }

    /**
     * Sets the total tests.
     * @param totalTests the new total tests
     */
    public void setTotalTests(Integer totalTests) {

        this.totalTests = totalTests;
    }

    /**
     * Gets the passed tests.
     * @return the passed tests
     */
    public Integer getPassedTests() {

        return passedTests;
    }

    /**
     * Sets the passed tests.
     * @param passedTests the new passed tests
     */
    public void setPassedTests(Integer passedTests) {

        this.passedTests = passedTests;
    }

    /**
     * Gets the failed tests.
     * @return the failed tests
     */
    public Integer getFailedTests() {

        return failedTests;
    }

    /**
     * Sets the failed tests.
     * @param failedTests the new failed tests
     */
    public void setFailedTests(Integer failedTests) {

        this.failedTests = failedTests;
    }

    /**
     * Gets the start time.
     * @return the start time
     */
    public String getStartTime() {

        return startTime;
    }

    /**
     * Sets the start time.
     * @param startTime the new start time
     */
    public void setStartTime(String startTime) {

        this.startTime = startTime;
    }

    /**
     * Gets the end time.
     * @return the end time
     */
    public String getEndTime() {

        return endTime;
    }

    /**
     * Sets the end time.
     * @param endTime the new end time
     */
    public void setEndTime(String endTime) {

        this.endTime = endTime;
    }

    /**
     * Gets the module name.
     * @return the module name
     */
    public String getModuleName() {

        return moduleName;
    }

    /**
     * Sets the module name.
     * @param moduleName the new module name
     */
    public void setModuleName(String moduleName) {

        this.moduleName = moduleName;
    }
}
