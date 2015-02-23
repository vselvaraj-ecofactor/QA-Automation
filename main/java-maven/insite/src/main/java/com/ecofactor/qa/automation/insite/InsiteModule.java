/*
 * InsiteModule.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.insite;

import com.ecofactor.qa.automation.insite.config.InsiteConfig;
import com.ecofactor.qa.automation.insite.config.InsiteLoginConfig;
import com.ecofactor.qa.automation.insite.data.DemandSideDataProvider;
import com.ecofactor.qa.automation.insite.data.ECPCoreDataProvider;
import com.ecofactor.qa.automation.insite.data.InstallationHardwareDataProvider;
import com.ecofactor.qa.automation.insite.data.InsiteLoginDataProvider;
import com.ecofactor.qa.automation.insite.data.OnBoardDataProvider;
import com.ecofactor.qa.automation.insite.data.PartnerDataProvider;
import com.ecofactor.qa.automation.insite.data.RoleDataProvider;
import com.ecofactor.qa.automation.insite.data.InsiteSupportDataProvider;
import com.ecofactor.qa.automation.insite.data.UploadOneUserDataProvider;
import com.ecofactor.qa.automation.insite.data.UserDataProvider;
import com.ecofactor.qa.automation.insite.data.UserRoleDataProvider;
import com.ecofactor.qa.automation.insite.page.DemandSideManagement;
import com.ecofactor.qa.automation.insite.page.DemandSideManagementImpl;
import com.ecofactor.qa.automation.insite.page.ECPCoreManagement;
import com.ecofactor.qa.automation.insite.page.ECPCoreManagementImpl;
import com.ecofactor.qa.automation.insite.page.InsiteInstallationPage;
import com.ecofactor.qa.automation.insite.page.InsiteInstallationPageImpl;
import com.ecofactor.qa.automation.insite.page.InsiteLogin;
import com.ecofactor.qa.automation.insite.page.InsiteLoginImpl;
import com.ecofactor.qa.automation.insite.page.InstallationHardware;
import com.ecofactor.qa.automation.insite.page.InstallationHardwareImpl;
import com.ecofactor.qa.automation.insite.page.OnBoard;
import com.ecofactor.qa.automation.insite.page.OnBoardImpl;
import com.ecofactor.qa.automation.insite.page.PartnerManagement;
import com.ecofactor.qa.automation.insite.page.PartnerManagementImpl;
import com.ecofactor.qa.automation.insite.page.RoleManagement;
import com.ecofactor.qa.automation.insite.page.RoleManagementImpl;
import com.ecofactor.qa.automation.insite.page.SupportLookUp;
import com.ecofactor.qa.automation.insite.page.SupportLookUpImpl;
import com.ecofactor.qa.automation.insite.page.UploadOneUser;
import com.ecofactor.qa.automation.insite.page.UploadOneUserImpl;
import com.ecofactor.qa.automation.insite.page.UserManagement;
import com.ecofactor.qa.automation.insite.page.UserManagementImpl;
import com.ecofactor.qa.automation.insite.page.UserRoleManagement;
import com.ecofactor.qa.automation.insite.page.UserRoleManagementImpl;
import com.ecofactor.qa.automation.insite.prepare.OnBoardPreparationService;
import com.ecofactor.qa.automation.insite.prepare.OnBoardPreparationServiceImpl;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

/**
 * The Class InsiteModule.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class InsiteModule extends AbstractModule {

    /**
     * @see com.google.inject.AbstractModule#configure()
     */
    protected void configure() {

        bind(InsiteConfig.class).in(Singleton.class);
        bind(InsiteLoginConfig.class).in(Singleton.class);
        bind(InsiteLogin.class).to(InsiteLoginImpl.class).in(Singleton.class);
        bind(DemandSideManagement.class).to(DemandSideManagementImpl.class).in(Singleton.class);
        bind(SupportLookUp.class).to(SupportLookUpImpl.class).in(Singleton.class);
        bind(RoleManagement.class).to(RoleManagementImpl.class).in(Singleton.class);
        bind(UserManagement.class).to(UserManagementImpl.class).in(Singleton.class);
        bind(UserRoleManagement.class).to(UserRoleManagementImpl.class).in(Singleton.class);
        bind(InstallationHardware.class).to(InstallationHardwareImpl.class).in(Singleton.class);
        bind(OnBoardPreparationService.class).to(OnBoardPreparationServiceImpl.class);
        bind(UploadOneUser.class).to(UploadOneUserImpl.class);
        bind(OnBoard.class).to(OnBoardImpl.class).in(Singleton.class);
        bind(PartnerManagement.class).to(PartnerManagementImpl.class).in(Singleton.class);
        bind(ECPCoreManagement.class).to(ECPCoreManagementImpl.class);
        bind(InsiteInstallationPage.class).to(InsiteInstallationPageImpl.class).in(Singleton.class);
        requestStaticInjection(DemandSideDataProvider.class);
        requestStaticInjection(InstallationHardwareDataProvider.class);
        requestStaticInjection(InsiteLoginDataProvider.class);
        requestStaticInjection(RoleDataProvider.class);
        requestStaticInjection(InsiteSupportDataProvider.class);
        requestStaticInjection(UserDataProvider.class);
        requestStaticInjection(UserRoleDataProvider.class);
        requestStaticInjection(OnBoardDataProvider.class);
        requestStaticInjection(UploadOneUserDataProvider.class);
        requestStaticInjection(PartnerDataProvider.class);
        requestStaticInjection(ECPCoreDataProvider.class);
    }
}
