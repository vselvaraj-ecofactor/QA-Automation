/*
 * QTCConfig.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.qtc.config;

import com.ecofactor.qa.automation.util.BaseConfig;
import com.ecofactor.qa.automation.util.DriverConfig;
import com.google.inject.Inject;


/**
 * The Class QTCConfig.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class QTCConfig extends BaseConfig {

    // qtc page url
    public static final String BASE_URL = "baseUrl";


    /**
     * Instantiates a new qTC config.
     * @param driverConfig the driver config
     */
    @Inject
    public QTCConfig(DriverConfig driverConfig) {

        super(driverConfig.get(DriverConfig.ENV));
        load("qtc.properties");
    }
}
