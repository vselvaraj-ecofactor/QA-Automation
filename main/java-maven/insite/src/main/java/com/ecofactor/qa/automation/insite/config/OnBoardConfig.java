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
public class OnBoardConfig extends BaseConfig {

    public final static String FILE_INPUT = "fileInput";
    public final static String FILE_SUBMIT = "fileSubmit";

    /**
     * Instantiates a new on board config.
     * @param driverConfig the driver config
     */
    @Inject
    public OnBoardConfig(DriverConfig driverConfig) {

        super(driverConfig.get(DriverConfig.ENV));
        load("onBoard.properties");
    }
}
