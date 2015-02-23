/*
 * DaoModule.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.dao;

import com.ecofactor.qa.automation.dao.util.DataUtil;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

/**
 * The Class DaoModule.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class DaoModule extends AbstractModule {

    /**
     * @see com.google.inject.AbstractModule#configure()
     */
    protected void configure() {

        requestStaticInjection(DataUtil.class);

        bind(EntityAttributeDao.class).to(EntityAttributeDaoImpl.class).in(Singleton.class);
        bind(ThermostatEventDao.class).to(ThermostatEventDaoImpl.class).in(Singleton.class);
        bind(ThermostatRangeDataDao.class).to(ThermostatRangeDataDaoImpl.class).in(Singleton.class);
        bind(ThermostatAlgorithmDao.class).to(ThermostatAlgorithmDaoImpl.class).in(Singleton.class);
        bind(AlgorithmDao.class).to(AlgorithmDaoImpl.class).in(Singleton.class);
        bind(LSProgramDao.class).to(LSProgramDaoImpl.class).in(Singleton.class);
        bind(LSProgramEventDao.class).to(LSProgramEventDaoImpl.class).in(Singleton.class);
        bind(EFUserDao.class).to(EFUserDaoImpl.class).in(Singleton.class);
        bind(ProgramDao.class).to(ProgramDaoImpl.class).in(Singleton.class);
        bind(EntityAttributeDao.class).to(EntityAttributeDaoImpl.class).in(Singleton.class);
        bind(ThermostatDao.class).to(ThermostatDaoImpl.class).in(Singleton.class);
        bind(ThermostatJobDao.class).to(ThermostatJobDaoImpl.class).in(Singleton.class);
        bind(LSProgramRulesDao.class).to(LSProgramRulesDaoImpl.class).in(Singleton.class);
        bind(RegionDao.class).to(RegionDaoImpl.class).in(Singleton.class);
        bind(ThermostatAlgoControlDao.class).to(ThermostatAlgoControlDaoImpl.class).in(
                Singleton.class);
        bind(PartnerAccountUserDao.class).to(PartnerAccountUserDaoImpl.class).in(Singleton.class);
        bind(ThermostatProgramLogDao.class).to(ThermostatProgramLogDaoImpl.class).in(
                Singleton.class);
        bind(UserLocationStagingDao.class).to(UserLocationStagingDaoImpl.class).in(Singleton.class);
        bind(UploadSessionDao.class).to(UploadSessionDaoImpl.class).in(Singleton.class);
        bind(UserTestDao.class).to(UserTestDaoImpl.class).in(Singleton.class);
        bind(MonthlyReportDao.class).to(MonthlyReportDaoImpl.class).in(Singleton.class);
        bind(DailyReportDao.class).to(DailyReportDaoImpl.class).in(Singleton.class);
        bind(HourlyReportDao.class).to(HourlyReportDaoImpl.class).in(Singleton.class);
        bind(SavingsReportDao.class).to(SavingsReportDaoImpl.class).in(Singleton.class);
        bind(LocationDao.class).to(LocationDaoImpl.class).in(Singleton.class);
        bind(DisconnectedDeviceDao.class).to(DisconnectedDeviceDaoImpl.class).in(Singleton.class);
        bind(com.ecofactor.qa.automation.dao.dr.LSProgramEventDao.class).to(
                com.ecofactor.qa.automation.dao.dr.LSProgramEventDaoImpl.class).in(Singleton.class);
        bind(com.ecofactor.qa.automation.dao.dr.EventControlDao.class).to(
                com.ecofactor.qa.automation.dao.dr.EventControlDaoImpl.class).in(Singleton.class);
        bind(com.ecofactor.qa.automation.dao.dr.LSProgramEventReportDao.class).to(
                com.ecofactor.qa.automation.dao.dr.LSProgramEventReportDaoImpl.class).in(
                Singleton.class);
        bind(com.ecofactor.qa.automation.dao.dr.LSProgramEventLocationDao.class).to(
                com.ecofactor.qa.automation.dao.dr.LSProgramEventLocationDaoImpl.class).in(
                Singleton.class);
        bind(com.ecofactor.qa.automation.dao.dr.ThermostatEventDao.class).to(
                com.ecofactor.qa.automation.dao.dr.ThermostatEventDaoImpl.class)
                .in(Singleton.class);
        bind(com.ecofactor.qa.automation.dao.dr.ThermostatDao.class).to(
                com.ecofactor.qa.automation.dao.dr.ThermostatDaoImpl.class).in(Singleton.class);
        bind(com.ecofactor.qa.automation.dao.dr.EFThermostatEventDao.class).to(
                com.ecofactor.qa.automation.dao.dr.EFThermostatEventDaoImpl.class).in(
                Singleton.class);
        bind(PartnerAccountDao.class).to(PartnerAccountDaoImpl.class).in(Singleton.class);
        bind(PartnerAccountTypeDao.class).to(PartnerAccountTypeDaoImpl.class).in(Singleton.class);
        bind(PartnerTypeDao.class).to(PartnerTypeDaoImpl.class).in(Singleton.class);
    }
}
