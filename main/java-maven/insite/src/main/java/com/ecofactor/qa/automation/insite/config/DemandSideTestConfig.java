/*
 * DemandSideTestConfig.java
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
 * The Class DemandSideTestConfig.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class DemandSideTestConfig extends BaseConfig {

    public static final String USER_SUPPORT_USRNAME = "userSupportInsite";
    public static final String USER_SUPPORT_PSWD = "password";
    public static final String PROGRAM_NAME = "programName";
    public static final String DRAFT_NAME = "draftEvent";

    /**
     * The Constant NEW_PROGRAM.
     */
    public static final String NEW_PROGRAM = "newlyCreatedProgramName";
    public static final String META_DATA_PGM = "metaDataEvent_programeName";
    public static final String META_DATA_EVENT = "metaDataEvent_eventName";

    public static final String META_DATA_EVENT_DESC_PGM = "metaDataEvent_Des_programeName";
    public static final String META_DATA_EVENT_DESC_EVENT_NAME = "metaDataEvent_Des_eventName";
    public static final String META_DATA_EVENT_DESC_EVENT_DESC = "metaDataEvent_Des_eventDescription";

    /**
     * Instantiates a new demand side test config.
     * @param driverConfig the driver config
     */
    @Inject
    public DemandSideTestConfig(DriverConfig driverConfig) {

        super(driverConfig.get(DriverConfig.ENV));
        load("demandSideManagementTest.properties");
    }
}
