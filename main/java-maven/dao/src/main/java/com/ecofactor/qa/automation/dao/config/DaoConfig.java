/*
 * DaoConfig.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.dao.config;

import com.ecofactor.qa.automation.util.BaseConfig;
import com.ecofactor.qa.automation.util.DriverConfig;
import com.google.inject.Inject;

/**
 * DaoConfig provides the environment specific configuration for the database.
 * @author $Author: rvinoopraj $
 * @version $Rev: 24430 $ $Date: 2013-10-22 18:59:23 +0530 (Tue, 22 Oct 2013) $
 */
public class DaoConfig extends BaseConfig {

    public static final String DRIVER = "driver";
    public static final String DIALECT = "dialect";
    public static final String URL = "url";
    public static final String USER = "user";
    public static final String PASSWORD = "password";
    public static final String UPDATE_USER = "updateUser";
    public static final String UPDATE_PASSWORD = "updatePassword";
    public static final String SHOW_SQL = "showSQL";
    public static final String FORMAT_SQL = "formatSQL";
    public static final String REPORTS_URL = "reports.url";
    public static final String RANGE_DATA_URL = "rangeDataUrl";
    public static final String RANGE_DATA_USER = "rangeDataUser";
    public static final String RANGE_DATA_PASSWORD = "rangeDataPassword";

    public static final String EF_USER_URL = "efUserUrl";
    public static final String EF_USER_USERNAME = "efUserUsername";
    public static final String EF_USER_PASSWORD = "efUserPassword";

    public static final String COMCAST_UI_REPORT_URL = "comcastUIReportUrl";
    public static final String COMCAST_UI_REPORT_USERNAME = "comcastUIReportUsername";
    public static final String COMCAST_UI_REPORT_PASSWORD = "comcastUIReportPassword";

    public static final String OPS_SCRIPT_URL = "opsScriptUrl";
    public static final String OPS_SCRIPT_USERNAME = "opsScriptUsername";
    public static final String OPS_SCRIPT_PASSWORD = "opsScriptPassword";

    /**
     * Instantiates a new dao config.
     */
    @Inject
    public DaoConfig(DriverConfig driverConfig) {

        super(driverConfig.get(DriverConfig.ENV));
        load("dao.properties");
    }
}
