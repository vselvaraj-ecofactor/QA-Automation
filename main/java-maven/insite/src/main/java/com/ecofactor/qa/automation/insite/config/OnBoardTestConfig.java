/*
 * OnBoardTestConfig.java
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
 * The Class OnBoardTestConfig.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class OnBoardTestConfig extends BaseConfig {

    public static final String BULK_UPLOAD_USERNAME = "bulkUploadUsername";
    public static final String BULK_UPLOAD_PSWD = "bulkUploadPswd";

    public static final String VALID_ECPCORE_ID = "validEcpCoreId";
    public static final String ACTIVATE_NEW_USER_EMAIL_ID = "activateNewUserEmailId";
    public static final String INVALID_ECPCORE_ID = "invalidEcpCore";
    public static final String UPLOAD_PROCESS_FLDR = "uploadProcessFolder";
    public static final String ACTIVATE_SPANISH_USER = "activateSpanishUserEmailId";

    /**
     * Instantiates a new on board test config.
     * @param driverConfig the driver config
     */
    @Inject
    public OnBoardTestConfig(DriverConfig driverConfig) {

        super(driverConfig.get(DriverConfig.ENV));
        load("onBoardTest.properties");
    }
}
