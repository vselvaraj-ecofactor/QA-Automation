/*
 * DRApiModule.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.consumerapi.dr;

import com.ecofactor.qa.automation.consumerapi.data.DRAPIDataProvider;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

/**
 * The Class DRApiModule.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class DRApiModule extends AbstractModule {

    /**
     * Configure.
     * @see com.google.inject.AbstractModule#configure()
     */
    @Override
    protected void configure() {

        bind(DRApiConfig.class).in(Singleton.class);
        requestStaticInjection(DRAPIDataProvider.class);
    }
}