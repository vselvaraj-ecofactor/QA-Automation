/*
 * OnBoardConfig.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.insite.config;

import com.ecofactor.qa.automation.util.BaseConfig;
import com.ecofactor.qa.automation.util.DriverConfig;
import com.google.inject.Inject;

/**
 * The Class OnBoardConfig.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class UploadOneUserConfig extends BaseConfig {

	public static final String CREATED_DATE = "createdDate";
	public static final String PROGRAM_ID = "programId";
    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastname";
    public static final String EMAIL = "email";
    public static final String PHONE = "phone";
    public static final String SERVICE_ADDR = "serviceAddress";
    public static final String SERVICE_CITY = "serviceCity";
    public static final String SERVICE_STATE = "serviceState";
    public static final String SERVICE_ZIP = "serviceZip";
    public static final String SERVICE_COUNTRY = "country";
    public static final String NO_OF_TSTAT = "noOfTstat";

    /**
     * Instantiates a new on board config.
     * @param driverConfig the driver config
     */
    @Inject
    public UploadOneUserConfig(DriverConfig driverConfig) {

        super(driverConfig.get(DriverConfig.ENV));
        load("uploadOneUserTest.properties");
    }
}
