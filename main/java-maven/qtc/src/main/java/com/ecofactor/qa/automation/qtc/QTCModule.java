/*
 * QTCModule.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.qtc;

import com.ecofactor.qa.automation.qtc.config.LoginConfig;
import com.ecofactor.qa.automation.qtc.config.SubscriptionConfig;
import com.ecofactor.qa.automation.qtc.config.TaskConfig;
import com.ecofactor.qa.automation.qtc.data.SubscriptionDataProvider;
import com.ecofactor.qa.automation.qtc.page.QTCLogin;
import com.ecofactor.qa.automation.qtc.page.QTCLoginImpl;
import com.ecofactor.qa.automation.qtc.page.TaskPage;
import com.ecofactor.qa.automation.qtc.page.TaskPageImpl;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

/**
 * The Class QTCModule.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class QTCModule extends AbstractModule {

    /**
     * Configure.
     * @see com.google.inject.AbstractModule#configure()
     */
    protected void configure() {

        bind(SubscriptionConfig.class).in(Singleton.class);
        bind(LoginConfig.class).in(Singleton.class);
        bind(TaskConfig.class).in(Singleton.class);
        bind(QTCLogin.class).to(QTCLoginImpl.class);
        bind(TaskPage.class).to(TaskPageImpl.class);

        requestStaticInjection(SubscriptionDataProvider.class);

    }
}
