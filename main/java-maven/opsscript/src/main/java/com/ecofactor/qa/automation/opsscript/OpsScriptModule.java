/*
 * ConsumerModule.java
 * Copyright (c) 2012, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.opsscript;

import com.ecofactor.qa.automation.opsscript.data.OpsScriptDataProvider;
import com.google.inject.AbstractModule;

/**
 * ConsumerModule configures the components provided by the consumer module.
 * @author $Author: rvinoopraj $
 * @version $Rev: 22929 $ $Date: 2013-09-13 18:23:23 +0530 (Fri, 13 Sep 2013) $
 */
public class OpsScriptModule extends AbstractModule {

    /**
     * Configure.
     * @see com.google.inject.AbstractModule#configure()
     */
    protected void configure() {

        requestStaticInjection(OpsScriptDataProvider.class);

    }

}
