/*
 * OpsScriptDaoModule.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.dao.opsscript;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

/**
 * The Class OpsScriptDaoModule.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class OpsScriptDaoModule extends AbstractModule {

    /**
     * Configure.
     * @see com.google.inject.AbstractModule#configure()
     */
    @Override
    protected void configure() {

        bind(OpsScriptDao.class).to(OpsScriptDaoImpl.class).in(Singleton.class);
    }
}
