/*
 * TaskConfig.java
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
 * The Class TaskConfig.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class TaskConfig extends BaseConfig {

    public static final String TASK_LINK = "taskLink";
    public static final String ALGO_SUBSCRIBE = "algoSubscribe";
    public static final String CSV_INPUT = "csvInput";
    public static final String ACTION = "action";
    public static final String ALGO = "algo";
    public static final String QUERY_CSV_INPUT = "queryCsvInput";
    public static final String NEXT = "next";
    public static final String VERIFY = "verify";
    public static final String START_TASK = "startTask";
    public static final String OUTPUT = "workOutput";

    /**
     * Instantiates a new task config.
     * @param driverConfig the driver config
     */
    @Inject
    public TaskConfig(DriverConfig driverConfig) {

        super(driverConfig.get(DriverConfig.ENV));
        load("task.properties");
    }
}
