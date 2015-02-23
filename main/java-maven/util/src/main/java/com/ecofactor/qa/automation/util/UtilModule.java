/*
 * UtilModule.java
 * Copyright (c) 2012, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.util;

import com.ecofactor.qa.automation.util.test.FeatureUtil;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

/**
 * UtilModule is used to configure the utility module components.
 * @author $Author: asaravana $
 * @version $Rev: 23128 $ $Date: 2013-09-19 13:58:47 +0530 (Thu, 19 Sep 2013) $
 */
public class UtilModule extends AbstractModule {

    /**
     * Configures the utility module components.
     * @see com.google.inject.AbstractModule#configure()
     */
    protected void configure() {

        requestStaticInjection(PageAction.class);
        bind(DriverConfig.class).in(Singleton.class);
        bind(TestlinkConfig.class).in(Singleton.class);
        bind(TestlinkUtil.class).in(Singleton.class);
        bind(TestLogUtil.class).in(Singleton.class);
        bind(TestDataUtil.class).in(Singleton.class);
        bind(FeatureUtil.class).in(Singleton.class);
    }
}
