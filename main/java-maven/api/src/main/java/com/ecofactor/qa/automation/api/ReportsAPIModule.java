/*
 * ReportsAPIModule.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.api;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

/**
 * ReportsAPIModule configures the dependencies for the Reports API.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class ReportsAPIModule extends AbstractModule {

    /**
     * Configure.
     * @see com.google.inject.AbstractModule#configure()
     */
    @Override
    protected void configure() {

        bind(ReportsAPIConfig.class).in(Singleton.class);
    }
}
