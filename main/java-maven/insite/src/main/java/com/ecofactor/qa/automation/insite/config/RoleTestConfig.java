/*
 * RoleTestConfig.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.insite.config;

import com.ecofactor.qa.automation.util.BaseConfig;
import com.ecofactor.qa.automation.util.DriverConfig;
import com.google.inject.Inject;

/**
 * The Class RoleTestConfig.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class RoleTestConfig extends BaseConfig {

    public static final String VALID_LOGIN_USER = "validLogin_userId";
    public static final String VALID_LOGIN_PASSWORD = "validLogin_password";

    public static final String ROLENAME_SYSTEM_ADMIN = "roleNameForSystemAdmin";
    public static final String ROLENAME_CONSERVATION_PARTNER = "roleNameConservation";
    public static final String ROLENAME_INSTALLER = "roleNameInstaller";
    public static final String ROLENAME_SERVICE_PROVIDER = "roleNameServiceProvider";
    public static final String ROLENAME_UTILITY = "roleNameUtility";
    public static final String ROLENAME_CC_ACCOUNT_ONLY = "roleNameCCAccount";
    public static final String ROLENAME_CC_ONBOARDING = "roleNameCCOnboarding";
    public static final String ROLENAME_CC_ACCOUNT_ONBOARDING = "roleNameCCAccOnBoarding";
    public static final String ROLENAME_INSTALLER_SCHEDULING = "roleNameInstallerScheduling";
    public static final String ROLENAME_INSTALLER_ONSITE = "roleNameInstallerOnsite";
    public static final String ROLENAME_INSTALLER_PRECONFIG = "roleNameInstallerPreconfig";
    public static final String ROLENAME_INS_PRECONFIG_SCHEDULE = "roleNamePreConfigSchedule";
    public static final String ROLENAME_INS_SCHEDULE_ONSITE = "roleNameScheduleOnsite";
    public static final String ROLENAME_INS_PRECONFIG_INSTALLER = "roleNamePreconfigWithInstaller";
    public static final String ROLENAME_ECOFACTOR_CONTRACTOR = "roleNameEcofactorContractor";
    public static final String ROLENAME_UTILITY_DR = "roleNameUtilityDR";
    public static final String ROLENAME_UTILITYDR_ACCOUNT = "roleNameUtilityDrAccount";

    public static final String ROLE_DESC = "roleDescription";
    public static final String ROLE_DESC_ACCOUNT_LOOKUP = "roleDescriptionAccount";
    public static final String ROLE_DESC_ONBOARDING = "roleDescriptionOnBoarding";
    public static final String ROLE_DESC_ACCOUNT_ONBOARDING = "roleDescriptionCCAccOnBoarding";
    public static final String ROLE_DESC_INSTALLER_SCHEDULING = "roleDescInstallerScheduling";
    public static final String ROLE_DESC_INSTALLER_ONSITE = "roleDescInstallerOnsite";
    public static final String ROLE_DESC_INSTALLER_PRECONFIG = "roleDescInstallerPreconfig";
    public static final String ROLE_DESC_INS_PRECONFIG_SCHEDULE = "roleDescInsPreconfigSchedule";
    public static final String ROLE_DESC_INS_SCHEDULE_ONSITE = "roleDescInsSchedulingOnsite";
    public static final String ROLE_DESC_INS_PRECONFIG_INSTALLER = "roleDescInsPreconfigInstaller";
    public static final String ROLE_DESC_ECOFACTOR_CONTRACTOR = "roleDescEcofactorContractor";
    public static final String ROLE_DESC_UTILITY_DR = "roleDescUtilityDr";
    public static final String ROLE_DESC_UTILITYDR_ACCOUNT = "roleDescUtilityDRAccount";
    public static final String AVAILABLE_PAGES = "availablePages";

    public static final String UNASSIGNED_PERMISSION = "unAssignedPermission";

    public static final String PERMISSION_SYSTEM_ADMIN = "systemAdminPermission";
    public static final String PERMISSION_CONSERVATION = "conservationPermission";
    public static final String PERMISSION_INSTALLATION = "installationPermission";
    public static final String PERMISSION_SERVICE = "servicePermission";
    public static final String PERMISSION_UTILITY = "utilityPermission";
    public static final String PERMISSION_CUSTOMER_CARE_ACCOUNT = "customerPermissionAccount";
    public static final String PERMISSION_CUSTOMER_CARE_ONBOARDING = "customerPermissionOnBoarding";
    public static final String PERMISSION_CC_ACCOUNT_ONBOARDING = "ccPermissionAccountOnBoarding";
    public static final String PERMISSION_INSTALLER_SCHEDULING = "installerSchedulingPermission";
    public static final String PERMISSION_INSTALLER_ONSITE = "installerOnsitePermission";
    public static final String PERMISSION_INSTALLER_PRECONFIG = "installerPreconfigPermission";
    public static final String PERMISSION_INS_PRECONFIG_SCHEDULE = "installerPreconfigSchedule";
    public static final String PERMISSION_INS_SCHEDULE_ONSITE = "permissionSchedulingOnsite";
    public static final String PERMISSION_INS_PRECONFIG_INSTALLER = "permissionPreconfigInstaller";
    public static final String PERMISSION_ECOFACTOR_CONTRACTOR = "permissionEcofactorContractor";
    public static final String PERMISSION_UTILITY_DR = "permissionUtilityDr";
    public static final String PERMISSION_UTILITYDR_ACCOUNT = "permissionUtilityDrAccount";

    public static final String PARTNER_TYPE_ADMIN = "partnerTypeAdmin";
    public static final String PARTNER_TYPE_CONSERVATION = "partnerTypeConservation";
    public static final String PARTNER_TYPE_INSTALLATION = "partnerTypeInstallation";
    public static final String PARTNER_TYPE_SERVICE = "partnerTypeService";
    public static final String PARTNER_TYPE_UTILITY = "partnerTypeUtitlity";
    public static final String PARTNER_TYPE_CUSTOMER = "partnerTypeCustomer";

    public static final String INSTALLER_PRIV_USER = "installerPrivilegeUser";
    public static final String SCHEDULE_PRIV_USER = "schedulePrivilegeUser";
    public static final String PRECONFIG_PRIV_USER = "preConfigPrivilegeUser";

    public static final String ON_SITE_INSTALL_PRIV_USER = "onSiteInstallUser";
    public static final String SCHEDULE_PRECONFIG_USER = "schedulePreConfigUser";
    public static final String SCHEDULE_ON_SITE_USER = "scheduleOnSiteInstallUser";
    public static final String PRECONFIG_ON_SITE_USER = "preConfigOnsiteInstallUser";

    public static final String ACCOUNT_LOOK_UP_USER = "accLookUpOnlyUser";
    public static final String ACCOUNT_ONBOARDING_USER = "accOnBoardingUser";
    public static final String UTILITY_DR_PRIVILEGE_USER = "utilityDRPrivilegeUser";
    public static final String CUSTOMER_ONBOARDING_USER = "custCareOnBoardingUser";

    public static final String COMCAST_USER = "comcastUser";
    public static final String CONSERVATION_PARTNER_USER = "conservationPartnerUser";
    public static final String NVE_PARTNER_USER = "nvePartnerUser";

    public static final String ROLENAME_INSTALLATION ="roleNameInstaller";
    public static final String ROLE_DESC_INSTALLATION = "roleDescInstaller";
    public static final String PERMISSION_INSTALLER="installerPermission";

    public static final String ADMIN_USER = "adminUser";
    public static final String ADMIN_TSTAT_ID = "tstatId";
    



    /**
     * Instantiates a new role test config.
     * @param driverConfig the driver config
     */
    @Inject
    public RoleTestConfig(DriverConfig driverConfig) {

        super(driverConfig.get(DriverConfig.ENV));
        load("roleTest.properties");
    }

}
