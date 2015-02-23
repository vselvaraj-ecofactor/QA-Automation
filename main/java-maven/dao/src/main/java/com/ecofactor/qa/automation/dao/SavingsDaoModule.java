/*
 * SavingsDaoModule.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.dao;

import com.ecofactor.qa.automation.dao.ecp.EcpCoreEnergyPricingDao;
import com.ecofactor.qa.automation.dao.ecp.EcpCoreEnergyPricingDaoImpl;
import com.ecofactor.qa.automation.dao.ecp.EcpCoreLocationDao;
import com.ecofactor.qa.automation.dao.ecp.EcpCoreLocationDaoImpl;
import com.ecofactor.qa.automation.dao.hvac.HvacComponentDao;
import com.ecofactor.qa.automation.dao.hvac.HvacComponentDaoImpl;
import com.ecofactor.qa.automation.dao.hvac.HvacModelDao;
import com.ecofactor.qa.automation.dao.hvac.HvacModelDaoImpl;
import com.ecofactor.qa.automation.dao.hvac.ThermostatHvacStageDao;
import com.ecofactor.qa.automation.dao.hvac.ThermostatHvacStageDaoImpl;
import com.ecofactor.qa.automation.dao.quant.SavingsReportDao;
import com.ecofactor.qa.automation.dao.quant.SavingsReportDaoImpl;
import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.Singleton;

/**
 * The Class SavingsDaoModule.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class SavingsDaoModule extends AbstractModule {

    /**
     * Configure.
     * @see com.google.inject.AbstractModule#configure()
     */
    @Override
    protected void configure() {

        bind(HvacComponentDao.class).to(HvacComponentDaoImpl.class).in(Singleton.class);
        bind(HvacModelDao.class).to(HvacModelDaoImpl.class).in(Singleton.class);
        bind(ThermostatDao.class).to(ThermostatDaoImpl.class).in(Singleton.class);
        bind(ThermostatHvacStageDao.class).to(ThermostatHvacStageDaoImpl.class).in(Singleton.class);
        bind(EcpCoreLocationDao.class).to(EcpCoreLocationDaoImpl.class).in(Singleton.class);
        bind(LocationDao.class).to(LocationDaoImpl.class).in(Singleton.class);
        bind(EFUserDao.class).to(EFUserDaoImpl.class).in(Singleton.class);
        bind(EcpCoreEnergyPricingDao.class).to(EcpCoreEnergyPricingDaoImpl.class).in(Singleton.class);
        bind(SavingsReportDao.class).to(SavingsReportDaoImpl.class).in(Scopes.SINGLETON);

    }

}
