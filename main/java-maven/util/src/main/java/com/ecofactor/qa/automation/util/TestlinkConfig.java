/*
 * TestlinkConfig.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.util;

/**
 * TestlinkConfig provides the testlink related config.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class TestlinkConfig extends BaseConfig {

    public static final String URL = "url";
    public static final String DEV_KEY = "devKey";

    /**
     * Instantiates a new testlink config.
     */
    public TestlinkConfig() {

        load("testlink.properties");
    }
}
