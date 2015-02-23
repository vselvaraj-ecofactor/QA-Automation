/*
 * RoleDataProvider.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.insite.data;

import static com.ecofactor.qa.automation.insite.config.RoleTestConfig.*;

import java.lang.reflect.Method;

import org.testng.annotations.DataProvider;

import com.ecofactor.qa.automation.insite.config.RoleTestConfig;
import com.ecofactor.qa.automation.insite.config.UserTestConfig;
import com.google.inject.Inject;

/**
 * The Class RoleDataProvider.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class RoleDataProvider {

    /** The role test config. */
    @Inject
    private static RoleTestConfig roleTestConfig;

    @Inject
    private static UserTestConfig userTestConfig;

    /**
     * Valid login.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "validLogin")
    public static Object[][] validLogin(Method m) {

        Object[][] data = { { roleTestConfig.get(VALID_LOGIN_USER),
                roleTestConfig.get(VALID_LOGIN_PASSWORD) } };
        return data;
    }

    /**
     * Role test config.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "createRole")
    public static Object[][] RoleTestConfig(Method m) {

        Object[][] data = { { roleTestConfig.get(VALID_LOGIN_USER),
                roleTestConfig.get(VALID_LOGIN_PASSWORD),
                roleTestConfig.get(AVAILABLE_PAGES), roleTestConfig.get(ROLE_DESC),
                roleTestConfig.get(PARTNER_TYPE_ADMIN), roleTestConfig.get(UNASSIGNED_PERMISSION)

        } };
        return data;
    }

    /**
     * Installer user.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "installerUser")
    public static Object[][] installerUser(Method m) {

        Object[][] data = { { roleTestConfig.get(INSTALLER_PRIV_USER),
                roleTestConfig.get(VALID_LOGIN_PASSWORD) } };
        return data;
    }

    /**
     * Schedule user.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "scheduleUser")
    public static Object[][] scheduleUser(Method m) {

        Object[][] data = { { roleTestConfig.get(SCHEDULE_PRIV_USER),
                roleTestConfig.get(VALID_LOGIN_PASSWORD) } };
        return data;
    }

    /**
     * Pre config user.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "preConfigUser")
    public static Object[][] preConfigUser(Method m) {

        Object[][] data = { { roleTestConfig.get(PRECONFIG_PRIV_USER),
                roleTestConfig.get(VALID_LOGIN_PASSWORD) } };
        return data;
    }

    /**
     * On site install user.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "onSiteInstallUser")
    public static Object[][] onSiteInstallUser(Method m) {

        Object[][] data = { { roleTestConfig.get(ON_SITE_INSTALL_PRIV_USER),
                roleTestConfig.get(VALID_LOGIN_PASSWORD) } };
        return data;
    }

    /**
     * Schedule pre config user.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "schedulePreConfigUser")
    public static Object[][] schedulePreConfigUser(Method m) {

        Object[][] data = { { roleTestConfig.get(SCHEDULE_PRECONFIG_USER),
                roleTestConfig.get(VALID_LOGIN_PASSWORD) } };
        return data;
    }

    /**
     * Schedule on site install user.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "scheduleOnSiteInstallUser")
    public static Object[][] scheduleOnSiteInstallUser(Method m) {

        Object[][] data = { { roleTestConfig.get(SCHEDULE_ON_SITE_USER),
                roleTestConfig.get(VALID_LOGIN_PASSWORD) } };
        return data;
    }

    /**
     * Pre config on site install user.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "preConfigOnSiteInstallUser")
    public static Object[][] preConfigOnSiteInstallUser(Method m) {

        Object[][] data = { { roleTestConfig.get(PRECONFIG_ON_SITE_USER),
                roleTestConfig.get(VALID_LOGIN_PASSWORD) } };
        return data;
    }

    /**
     * Role for system admin.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "roleForSystemAdmin")
    public static Object[][] roleForSystemAdmin(Method m) {

        Object[][] data = { { roleTestConfig.get(VALID_LOGIN_USER),
                roleTestConfig.get(VALID_LOGIN_PASSWORD),
                roleTestConfig.get(ROLENAME_SYSTEM_ADMIN), roleTestConfig.get(ROLE_DESC),
                roleTestConfig.get(PARTNER_TYPE_ADMIN), roleTestConfig.get(PERMISSION_SYSTEM_ADMIN) } };
        return data;
    }

    /**
     * Role for conservation.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "roleForConservation")
    public static Object[][] roleForConservation(Method m) {

        Object[][] data = { { roleTestConfig.get(VALID_LOGIN_USER),
                roleTestConfig.get(VALID_LOGIN_PASSWORD),
                roleTestConfig.get(ROLENAME_CONSERVATION_PARTNER), roleTestConfig.get(ROLE_DESC),
                roleTestConfig.get(PARTNER_TYPE_CONSERVATION),
                roleTestConfig.get(PERMISSION_CONSERVATION) } };
        return data;
    }

    /**
     * Role for installation.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "roleForInstallation")
    public static Object[][] roleForInstallation(Method m) {

        Object[][] data = { { roleTestConfig.get(VALID_LOGIN_USER),
                roleTestConfig.get(VALID_LOGIN_PASSWORD), roleTestConfig.get(ROLENAME_INSTALLER),
                roleTestConfig.get(ROLE_DESC), roleTestConfig.get(PARTNER_TYPE_INSTALLATION),
                roleTestConfig.get(PERMISSION_INSTALLATION) } };
        return data;
    }

    /**
     * Role for service.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "roleForService")
    public static Object[][] roleForService(Method m) {

        Object[][] data = { { roleTestConfig.get(VALID_LOGIN_USER),
                roleTestConfig.get(VALID_LOGIN_PASSWORD),
                roleTestConfig.get(ROLENAME_SERVICE_PROVIDER), roleTestConfig.get(ROLE_DESC),
                roleTestConfig.get(PARTNER_TYPE_SERVICE), roleTestConfig.get(PERMISSION_SERVICE) } };
        return data;
    }

    /**
     * Role for utility.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "roleForUtility")
    public static Object[][] roleForUtility(Method m) {

        Object[][] data = { { roleTestConfig.get(VALID_LOGIN_USER),
                roleTestConfig.get(VALID_LOGIN_PASSWORD), roleTestConfig.get(ROLENAME_UTILITY),
                roleTestConfig.get(ROLE_DESC), roleTestConfig.get(PARTNER_TYPE_UTILITY),
                roleTestConfig.get(PERMISSION_UTILITY) } };
        return data;
    }

    /**
     * Role for customer care.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "roleForCustomerCareAccountOnly")
    public static Object[][] roleForCustomerCare(Method m) {

        Object[][] data = { { roleTestConfig.get(VALID_LOGIN_USER),
                roleTestConfig.get(VALID_LOGIN_PASSWORD),
                roleTestConfig.get(ROLENAME_CC_ACCOUNT_ONLY),
                roleTestConfig.get(ROLE_DESC_ACCOUNT_LOOKUP),
                roleTestConfig.get(PARTNER_TYPE_CUSTOMER),
                roleTestConfig.get(PERMISSION_CUSTOMER_CARE_ACCOUNT) } };
        return data;
    }

    /**
     * Role for customer care on boarding.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "roleForCustomerCareOnBoarding")
    public static Object[][] roleForCustomerCareOnBoarding(Method m) {

        Object[][] data = { { roleTestConfig.get(VALID_LOGIN_USER),
                roleTestConfig.get(VALID_LOGIN_PASSWORD),
                roleTestConfig.get(ROLENAME_CC_ONBOARDING),
                roleTestConfig.get(ROLE_DESC_ONBOARDING),
                roleTestConfig.get(PARTNER_TYPE_CUSTOMER),
                roleTestConfig.get(PERMISSION_CUSTOMER_CARE_ONBOARDING) } };
        return data;
    }

    /**
     * Role for cc account on boarding.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "roleForCCAccountOnBoarding")
    public static Object[][] roleForCCAccountOnBoarding(Method m) {

        Object[][] data = { { roleTestConfig.get(VALID_LOGIN_USER),
                roleTestConfig.get(VALID_LOGIN_PASSWORD),
                roleTestConfig.get(ROLENAME_CC_ACCOUNT_ONBOARDING),
                roleTestConfig.get(ROLE_DESC_ACCOUNT_ONBOARDING),
                roleTestConfig.get(PARTNER_TYPE_CUSTOMER),
                roleTestConfig.get(PERMISSION_CC_ACCOUNT_ONBOARDING) } };
        return data;
    }

    /**
     * Role for installer.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "roleForInstaller")
    public static Object[][] roleForInstaller(Method m) {

        Object[][] data = { { roleTestConfig.get(VALID_LOGIN_USER),
                roleTestConfig.get(VALID_LOGIN_PASSWORD),
                roleTestConfig.get(ROLENAME_INSTALLATION),
                roleTestConfig.get(ROLE_DESC_INSTALLATION),
                roleTestConfig.get(PARTNER_TYPE_INSTALLATION),
                roleTestConfig.get(PERMISSION_INSTALLER) } };
        return data;
    }

    /**
     * Role for installer scheduling.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "roleForInstallerScheduling")
    public static Object[][] roleForInstallerScheduling(Method m) {

        Object[][] data = { { roleTestConfig.get(VALID_LOGIN_USER),
                roleTestConfig.get(VALID_LOGIN_PASSWORD),
                roleTestConfig.get(ROLENAME_INSTALLER_SCHEDULING),
                roleTestConfig.get(ROLE_DESC_INSTALLER_SCHEDULING),
                roleTestConfig.get(PARTNER_TYPE_INSTALLATION),
                roleTestConfig.get(PERMISSION_INSTALLER_SCHEDULING) } };
        return data;
    }

    /**
     * Role for installer onsite.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "roleForInstallerOnsite")
    public static Object[][] roleForInstallerOnsite(Method m) {

        Object[][] data = { { roleTestConfig.get(VALID_LOGIN_USER),
                roleTestConfig.get(VALID_LOGIN_PASSWORD),
                roleTestConfig.get(ROLENAME_INSTALLER_ONSITE),
                roleTestConfig.get(ROLE_DESC_INSTALLER_ONSITE),
                roleTestConfig.get(PARTNER_TYPE_INSTALLATION),
                roleTestConfig.get(PERMISSION_INSTALLER_ONSITE) } };
        return data;
    }

    /**
     * Role for installer pre config.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "roleForInstallerPreConfig")
    public static Object[][] roleForInstallerPreConfig(Method m) {

        Object[][] data = { { roleTestConfig.get(VALID_LOGIN_USER),
                roleTestConfig.get(VALID_LOGIN_PASSWORD),
                roleTestConfig.get(ROLENAME_INSTALLER_PRECONFIG),
                roleTestConfig.get(ROLE_DESC_INSTALLER_PRECONFIG),
                roleTestConfig.get(PARTNER_TYPE_INSTALLATION),
                roleTestConfig.get(PERMISSION_INSTALLER_PRECONFIG) } };
        return data;
    }

    /**
     * Installer preconfig with scheduling.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "installerPreconfigWithScheduling")
    public static Object[][] installerPreconfigWithScheduling(Method m) {

        Object[][] data = { { roleTestConfig.get(VALID_LOGIN_USER),
                roleTestConfig.get(VALID_LOGIN_PASSWORD),
                roleTestConfig.get(ROLENAME_INS_PRECONFIG_SCHEDULE),
                roleTestConfig.get(ROLE_DESC_INS_PRECONFIG_SCHEDULE),
                roleTestConfig.get(PARTNER_TYPE_INSTALLATION),
                roleTestConfig.get(PERMISSION_INS_PRECONFIG_SCHEDULE) } };
        return data;
    }

    /**
     * Scheduling with onsite install.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "schedulingWithOnsiteInstall")
    public static Object[][] schedulingWithOnsiteInstall(Method m) {

        Object[][] data = { { roleTestConfig.get(VALID_LOGIN_USER),
                roleTestConfig.get(VALID_LOGIN_PASSWORD),
                roleTestConfig.get(ROLENAME_INS_SCHEDULE_ONSITE),
                roleTestConfig.get(ROLE_DESC_INS_SCHEDULE_ONSITE),
                roleTestConfig.get(PARTNER_TYPE_INSTALLATION),
                roleTestConfig.get(PERMISSION_INS_SCHEDULE_ONSITE) } };
        return data;
    }

    /**
     * Preconfig with installer.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "preconfigWithInstaller")
    public static Object[][] preconfigWithInstaller(Method m) {

        Object[][] data = { { roleTestConfig.get(VALID_LOGIN_USER),
                roleTestConfig.get(VALID_LOGIN_PASSWORD),
                roleTestConfig.get(ROLENAME_INS_PRECONFIG_INSTALLER),
                roleTestConfig.get(ROLE_DESC_INS_PRECONFIG_INSTALLER),
                roleTestConfig.get(PARTNER_TYPE_INSTALLATION),
                roleTestConfig.get(PERMISSION_INS_PRECONFIG_INSTALLER) } };
        return data;
    }

    /**
     * Role for eco factor contractor.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "roleForEcoFactorContractor")
    public static Object[][] roleForEcoFactorContractor(Method m) {

        Object[][] data = { { roleTestConfig.get(VALID_LOGIN_USER),
                roleTestConfig.get(VALID_LOGIN_PASSWORD),
                roleTestConfig.get(ROLENAME_ECOFACTOR_CONTRACTOR),
                roleTestConfig.get(ROLE_DESC_ECOFACTOR_CONTRACTOR),
                roleTestConfig.get(PARTNER_TYPE_ADMIN),
                roleTestConfig.get(PERMISSION_ECOFACTOR_CONTRACTOR) } };
        return data;
    }

    /**
     * Role for utility dr.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "roleForUtilityDR")
    public static Object[][] roleForUtilityDR(Method m) {

        Object[][] data = { { roleTestConfig.get(VALID_LOGIN_USER),
                roleTestConfig.get(VALID_LOGIN_PASSWORD), roleTestConfig.get(ROLENAME_UTILITY_DR),
                roleTestConfig.get(ROLE_DESC_UTILITY_DR), roleTestConfig.get(PARTNER_TYPE_UTILITY),
                roleTestConfig.get(PERMISSION_UTILITY_DR) } };
        return data;
    }

    /**
     * Utility dr with account.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "UtilityDRWithAccount")
    public static Object[][] UtilityDRWithAccount(Method m) {

        Object[][] data = { { roleTestConfig.get(VALID_LOGIN_USER),
                roleTestConfig.get(VALID_LOGIN_PASSWORD),
                roleTestConfig.get(ROLENAME_UTILITYDR_ACCOUNT),
                roleTestConfig.get(ROLE_DESC_UTILITYDR_ACCOUNT),
                roleTestConfig.get(PARTNER_TYPE_UTILITY),
                roleTestConfig.get(PERMISSION_UTILITYDR_ACCOUNT) } };
        return data;
    }

    /**
     * Acc look up only privilege.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "accLookUpOnlyPrivilege")
    public static Object[][] accLookUpOnlyPrivilege(Method m) {

        Object[][] data = { { roleTestConfig.get(ACCOUNT_LOOK_UP_USER),
                roleTestConfig.get(VALID_LOGIN_PASSWORD) } };
        return data;
    }

    /**
     * Utility dr privilege.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "utilityDRPrivilege")
    public static Object[][] utilityDRPrivilege(Method m) {

        Object[][] data = { { roleTestConfig.get(UTILITY_DR_PRIVILEGE_USER),
                roleTestConfig.get(VALID_LOGIN_PASSWORD) } };
        return data;
    }

    /**
     * Acc on boarding.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "accOnBoardingUser")
    public static Object[][] accOnBoarding(Method m) {

        Object[][] data = { { roleTestConfig.get(ACCOUNT_ONBOARDING_USER),
                roleTestConfig.get(VALID_LOGIN_PASSWORD),
                userTestConfig.get(UserTestConfig.FIRST_NAME),
                userTestConfig.get(UserTestConfig.LAST_NAME),
                userTestConfig.get(UserTestConfig.EMAIL_URL),
                userTestConfig.get(UserTestConfig.EMAIL_ID),
                userTestConfig.get(UserTestConfig.EMAIL_PSWD),
                userTestConfig.get(UserTestConfig.EMAIL_SUB),
                userTestConfig.get(UserTestConfig.EMAIL),
                userTestConfig.get(UserTestConfig.ACCOUNT_USER_NAME),
                userTestConfig.get(UserTestConfig.ACTIVE_USER),
                userTestConfig.get(UserTestConfig.PARTNER_TYPE),
                userTestConfig.get(UserTestConfig.PARTNER),
                userTestConfig.get(UserTestConfig.STREET_ADD_1),
                userTestConfig.get(UserTestConfig.STREET_ADD_2),
                userTestConfig.get(UserTestConfig.CITY), userTestConfig.get(UserTestConfig.STATE),
                userTestConfig.get(UserTestConfig.ZIP), userTestConfig.get(UserTestConfig.COUNTRY),
                userTestConfig.get(UserTestConfig.MOBILE_PHONE),
                userTestConfig.get(UserTestConfig.HOME_PHONE),
                userTestConfig.get(UserTestConfig.FAX),
                userTestConfig.get(UserTestConfig.AVAILABLE_ROLE),
                userTestConfig.get(UserTestConfig.NEW_PSWD) } };
        return data;
    }

    /**
     * Customer care on board privilege.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "customerCareOnBoardPrivilege")
    public static Object[][] customerCareOnBoardPrivilege(Method m) {

        Object[][] data = { { roleTestConfig.get(CUSTOMER_ONBOARDING_USER),
                roleTestConfig.get(VALID_LOGIN_PASSWORD) } };
        return data;
    }

    /**
     * Comcast privilege.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "comcastPrivilege")
    public static Object[][] comcastPrivilege(Method m) {

        Object[][] data = { { roleTestConfig.get(COMCAST_USER),
                roleTestConfig.get(VALID_LOGIN_PASSWORD),
                userTestConfig.get(UserTestConfig.FIRST_NAME),
                userTestConfig.get(UserTestConfig.LAST_NAME),
                userTestConfig.get(UserTestConfig.EMAIL_URL),
                userTestConfig.get(UserTestConfig.EMAIL_ID),
                userTestConfig.get(UserTestConfig.EMAIL_PSWD),
                userTestConfig.get(UserTestConfig.EMAIL_SUB),
                userTestConfig.get(UserTestConfig.EMAIL),
                userTestConfig.get(UserTestConfig.ACCOUNT_USER_NAME),
                userTestConfig.get(UserTestConfig.ACTIVE_USER),
                userTestConfig.get(UserTestConfig.PARTNER_TYPE),
                userTestConfig.get(UserTestConfig.PARTNER),
                userTestConfig.get(UserTestConfig.STREET_ADD_1),
                userTestConfig.get(UserTestConfig.STREET_ADD_2),
                userTestConfig.get(UserTestConfig.CITY), userTestConfig.get(UserTestConfig.STATE),
                userTestConfig.get(UserTestConfig.ZIP), userTestConfig.get(UserTestConfig.COUNTRY),
                userTestConfig.get(UserTestConfig.MOBILE_PHONE),
                userTestConfig.get(UserTestConfig.HOME_PHONE),
                userTestConfig.get(UserTestConfig.FAX),
                userTestConfig.get(UserTestConfig.AVAILABLE_ROLE),
                userTestConfig.get(UserTestConfig.NEW_PSWD) } };
        return data;
    }

    /**
     * Conservation privilege.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "conservationPrivilege")
    public static Object[][] conservationPrivilege(Method m) {

        Object[][] data = { { roleTestConfig.get(CONSERVATION_PARTNER_USER),
                roleTestConfig.get(VALID_LOGIN_PASSWORD),
                userTestConfig.get(UserTestConfig.FIRST_NAME),
                userTestConfig.get(UserTestConfig.LAST_NAME),
                userTestConfig.get(UserTestConfig.EMAIL_URL),
                userTestConfig.get(UserTestConfig.EMAIL_ID),
                userTestConfig.get(UserTestConfig.EMAIL_PSWD),
                userTestConfig.get(UserTestConfig.EMAIL_SUB),
                userTestConfig.get(UserTestConfig.EMAIL),
                userTestConfig.get(UserTestConfig.ACCOUNT_USER_NAME),
                userTestConfig.get(UserTestConfig.ACTIVE_USER),
                userTestConfig.get(UserTestConfig.PARTNER_TYPE),
                userTestConfig.get(UserTestConfig.PARTNER),
                userTestConfig.get(UserTestConfig.STREET_ADD_1),
                userTestConfig.get(UserTestConfig.STREET_ADD_2),
                userTestConfig.get(UserTestConfig.CITY), userTestConfig.get(UserTestConfig.STATE),
                userTestConfig.get(UserTestConfig.ZIP), userTestConfig.get(UserTestConfig.COUNTRY),
                userTestConfig.get(UserTestConfig.MOBILE_PHONE),
                userTestConfig.get(UserTestConfig.HOME_PHONE),
                userTestConfig.get(UserTestConfig.FAX),
                userTestConfig.get(UserTestConfig.AVAILABLE_ROLE),
                userTestConfig.get(UserTestConfig.NEW_PSWD) } };
        return data;
    }

    /**
     * Nve privilege.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "nvePrivilege")
    public static Object[][] nvePrivilege(Method m) {

        Object[][] data = { { roleTestConfig.get(NVE_PARTNER_USER),
                roleTestConfig.get(VALID_LOGIN_PASSWORD),
                userTestConfig.get(UserTestConfig.FIRST_NAME),
                userTestConfig.get(UserTestConfig.LAST_NAME),
                userTestConfig.get(UserTestConfig.EMAIL_URL),
                userTestConfig.get(UserTestConfig.EMAIL_ID),
                userTestConfig.get(UserTestConfig.EMAIL_PSWD),
                userTestConfig.get(UserTestConfig.EMAIL_SUB),
                userTestConfig.get(UserTestConfig.EMAIL),
                userTestConfig.get(UserTestConfig.ACCOUNT_USER_NAME),
                userTestConfig.get(UserTestConfig.ACTIVE_USER),
                userTestConfig.get(UserTestConfig.PARTNER_TYPE),
                userTestConfig.get(UserTestConfig.PARTNER),
                userTestConfig.get(UserTestConfig.STREET_ADD_1),
                userTestConfig.get(UserTestConfig.STREET_ADD_2),
                userTestConfig.get(UserTestConfig.CITY), userTestConfig.get(UserTestConfig.STATE),
                userTestConfig.get(UserTestConfig.ZIP), userTestConfig.get(UserTestConfig.COUNTRY),
                userTestConfig.get(UserTestConfig.MOBILE_PHONE),
                userTestConfig.get(UserTestConfig.HOME_PHONE),
                userTestConfig.get(UserTestConfig.FAX),
                userTestConfig.get(UserTestConfig.AVAILABLE_ROLE),
                userTestConfig.get(UserTestConfig.NEW_PSWD) } };
        return data;
    }

    /**
     * Admin privilege.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "adminPrivilege")
    public static Object[][] adminPrivilege(Method m) {

        Object[][] data = { { roleTestConfig.get(ADMIN_USER),
                roleTestConfig.get(VALID_LOGIN_PASSWORD), roleTestConfig.get(ADMIN_TSTAT_ID) } };
        return data;
    }
}
