/*
 * MockJobDataConfig.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.newapp.mock;

import com.ecofactor.qa.automation.util.BaseConfig;
import com.ecofactor.qa.automation.util.DriverConfig;
import com.google.inject.Inject;

/**
 * The Class MockJobDataConfig.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class MockJobDataConfig extends BaseConfig {

    public static final String BASE_TEMP = "baseTemp";
    public static final String NEW_BASE_TEMP = "newBaseTemp";
    public static final String BASE_TEMP_HEAT = "baseTempHeat";
    public static final String NEW_BASE_TEMP_HEAT = "newBaseTempHeat";
    public static final String TEST_BLOCKS = ".blocks";
    public static final String TEST_DURATION = ".duration";
    public static final String TEST_GAP = ".gap";
    public static final String TEST_POSITION = ".position";
    public static final String TEST_BACK = ".back";
    public static final String TEST_NEXT = ".next";
    public static final String TEST_BASE_TEMP = ".baseTemp";
    public static final String TEST_BLACKOUT = ".blackout";
    public static final String TEST_RECOVERY = ".recovery";

    /**
     * Instantiates a new mock job data config.
     */
    @Inject
    public MockJobDataConfig(DriverConfig driverConfig) {
        super(driverConfig.get(DriverConfig.ENV));
        load("mockJob.properties");
    }
}
