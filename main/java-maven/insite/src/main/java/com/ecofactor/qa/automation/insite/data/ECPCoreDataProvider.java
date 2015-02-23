/*
 * ECPCoreDataProvider.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.insite.data;

import static com.ecofactor.qa.automation.insite.config.ECPTestConfig.*;

import java.lang.reflect.Method;

import org.testng.annotations.DataProvider;

import com.ecofactor.qa.automation.insite.config.ECPTestConfig;
import com.ecofactor.qa.automation.insite.config.PartnerTestConfig;
import com.google.inject.Inject;

/**
 * The Class ECPCoreDataProvider.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class ECPCoreDataProvider {

    @Inject
    private static ECPTestConfig ecpTestConfig;

    @Inject
    private static PartnerTestConfig partnerTestConfig;

    /**
     * Valid login.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "createNewEcp")
    public static Object[][] createNewEcp(Method m) {

        Object[][] data = { { ecpTestConfig.get(VALID_LOGIN_USER),
                ecpTestConfig.get(VALID_LOGIN_PASSWORD), ecpTestConfig.get(ECP_CORE_NAME),
                ecpTestConfig.get(CONSERV_PARTNER), ecpTestConfig.get(INSTALLERS),
                ecpTestConfig.get(SERVICE_PROVIDERS), ecpTestConfig.get(UTILITIES),
                ecpTestConfig.get(CUSTOMER_CARES), ecpTestConfig.get(GATEWAY_MODEL),
                ecpTestConfig.get(TSTAT_MODEL) } };
        return data;
    }

    /**
     * Creates the new ecp without name.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "createNewEcpWithoutName")
    public static Object[][] createNewEcpWithoutName(Method m) {

        Object[][] data = { { ecpTestConfig.get(VALID_LOGIN_USER),
                ecpTestConfig.get(VALID_LOGIN_PASSWORD), ecpTestConfig.get(EMPTY_STRING),
                ecpTestConfig.get(CONSERV_PARTNER), ecpTestConfig.get(INSTALLERS),
                ecpTestConfig.get(SERVICE_PROVIDERS), ecpTestConfig.get(UTILITIES),
                ecpTestConfig.get(CUSTOMER_CARES), ecpTestConfig.get(GATEWAY_MODEL),
                ecpTestConfig.get(TSTAT_MODEL) } };
        return data;
    }

    /**
     * Skip installers.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "skipInstallers")
    public static Object[][] skipInstallers(Method m) {

        Object[][] data = { { ecpTestConfig.get(VALID_LOGIN_USER),
                ecpTestConfig.get(VALID_LOGIN_PASSWORD), ecpTestConfig.get(ECP_CORE_NAME),
                ecpTestConfig.get(CONSERV_PARTNER), ecpTestConfig.get(EMPTY_STRING),
                ecpTestConfig.get(SERVICE_PROVIDERS), ecpTestConfig.get(UTILITIES),
                ecpTestConfig.get(CUSTOMER_CARES), ecpTestConfig.get(GATEWAY_MODEL),
                ecpTestConfig.get(TSTAT_MODEL) } };
        return data;
    }

    /**
     * Skip utility.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "skipUtility")
    public static Object[][] skipUtility(Method m) {

        Object[][] data = { { ecpTestConfig.get(VALID_LOGIN_USER),
                ecpTestConfig.get(VALID_LOGIN_PASSWORD), ecpTestConfig.get(ECP_CORE_NAME),
                ecpTestConfig.get(CONSERV_PARTNER), ecpTestConfig.get(INSTALLERS),
                ecpTestConfig.get(SERVICE_PROVIDERS), ecpTestConfig.get(EMPTY_STRING),
                ecpTestConfig.get(CUSTOMER_CARES), ecpTestConfig.get(GATEWAY_MODEL),
                ecpTestConfig.get(TSTAT_MODEL) } };
        return data;
    }


    /**
     * Skip service.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "skipService")
    public static Object[][] skipService(Method m) {

        Object[][] data = { { ecpTestConfig.get(VALID_LOGIN_USER),
                ecpTestConfig.get(VALID_LOGIN_PASSWORD), ecpTestConfig.get(ECP_CORE_NAME),
                ecpTestConfig.get(CONSERV_PARTNER), ecpTestConfig.get(INSTALLERS),
                ecpTestConfig.get(EMPTY_STRING), ecpTestConfig.get(UTILITIES),
                ecpTestConfig.get(CUSTOMER_CARES), ecpTestConfig.get(GATEWAY_MODEL),
                ecpTestConfig.get(TSTAT_MODEL) } };
        return data;
    }

    /**
     * Skip customer care.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "skipCustomerCare")
    public static Object[][] skipCustomerCare(Method m) {

        Object[][] data = { { ecpTestConfig.get(VALID_LOGIN_USER),
                ecpTestConfig.get(VALID_LOGIN_PASSWORD), ecpTestConfig.get(ECP_CORE_NAME),
                ecpTestConfig.get(CONSERV_PARTNER), ecpTestConfig.get(INSTALLERS),
                ecpTestConfig.get(SERVICE_PROVIDERS), ecpTestConfig.get(UTILITIES),
                ecpTestConfig.get(EMPTY_STRING), ecpTestConfig.get(GATEWAY_MODEL),
                ecpTestConfig.get(TSTAT_MODEL) } };
        return data;
    }

    /**
     * Assign to service provider.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "assignToServiceProvider")
    public static Object[][] assignToServiceProvider(Method m) {

        Object[][] data = { { ecpTestConfig.get(VALID_LOGIN_USER),
                ecpTestConfig.get(VALID_LOGIN_PASSWORD),
                partnerTestConfig.get(PartnerTestConfig.PARTNER_NAME),
                partnerTestConfig.get(PartnerTestConfig.EMAIL),
                partnerTestConfig.get(PartnerTestConfig.DATA_ACTIVE_PARTNER),
                partnerTestConfig.get(PartnerTestConfig.STREET_ADDRESS_1),
                partnerTestConfig.get(PartnerTestConfig.STREET_ADDRESS_2),
                partnerTestConfig.get(PartnerTestConfig.CITY),
                partnerTestConfig.get(PartnerTestConfig.STATE),
                partnerTestConfig.get(PartnerTestConfig.ZIP),
                partnerTestConfig.get(PartnerTestConfig.COUNTRY),
                partnerTestConfig.get(PartnerTestConfig.PRIMARY_CONTACT_NAME),
                partnerTestConfig.get(PartnerTestConfig.PHONE_NUMBER),
                partnerTestConfig.get(PartnerTestConfig.DATA_TYPE_VALUE_4),
                ecpTestConfig.get(ECP_CORE_NAME), ecpTestConfig.get(CONSERV_PARTNER),
                ecpTestConfig.get(INSTALLERS), ecpTestConfig.get(SERVICE_PROVIDERS),
                ecpTestConfig.get(UTILITIES), ecpTestConfig.get(CUSTOMER_CARES),
                ecpTestConfig.get(GATEWAY_MODEL), ecpTestConfig.get(TSTAT_MODEL) } };
        return data;
    }

    /**
     * Assign to utilities.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "assignToUtilities")
    public static Object[][] assignToUtilities(Method m) {

        Object[][] data = { { ecpTestConfig.get(VALID_LOGIN_USER),
                ecpTestConfig.get(VALID_LOGIN_PASSWORD),
                partnerTestConfig.get(PartnerTestConfig.PARTNER_NAME),
                partnerTestConfig.get(PartnerTestConfig.EMAIL),
                partnerTestConfig.get(PartnerTestConfig.DATA_ACTIVE_PARTNER),
                partnerTestConfig.get(PartnerTestConfig.STREET_ADDRESS_1),
                partnerTestConfig.get(PartnerTestConfig.STREET_ADDRESS_2),
                partnerTestConfig.get(PartnerTestConfig.CITY),
                partnerTestConfig.get(PartnerTestConfig.STATE),
                partnerTestConfig.get(PartnerTestConfig.ZIP),
                partnerTestConfig.get(PartnerTestConfig.COUNTRY),
                partnerTestConfig.get(PartnerTestConfig.PRIMARY_CONTACT_NAME),
                partnerTestConfig.get(PartnerTestConfig.PHONE_NUMBER),
                partnerTestConfig.get(PartnerTestConfig.DATA_TYPE_VALUE_5),
                ecpTestConfig.get(ECP_CORE_NAME), ecpTestConfig.get(CONSERV_PARTNER),
                ecpTestConfig.get(INSTALLERS), ecpTestConfig.get(SERVICE_PROVIDERS),
                ecpTestConfig.get(UTILITIES), ecpTestConfig.get(CUSTOMER_CARES),
                ecpTestConfig.get(GATEWAY_MODEL), ecpTestConfig.get(TSTAT_MODEL) } };
        return data;
    }

    /**
     * Assign to conservation partner.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "assignToConservationPartner")
    public static Object[][] assignToConservationPartner(Method m) {

        Object[][] data = { { ecpTestConfig.get(VALID_LOGIN_USER),
                ecpTestConfig.get(VALID_LOGIN_PASSWORD),
                partnerTestConfig.get(PartnerTestConfig.PARTNER_NAME),
                partnerTestConfig.get(PartnerTestConfig.EMAIL),
                partnerTestConfig.get(PartnerTestConfig.DATA_ACTIVE_PARTNER),
                partnerTestConfig.get(PartnerTestConfig.STREET_ADDRESS_1),
                partnerTestConfig.get(PartnerTestConfig.STREET_ADDRESS_2),
                partnerTestConfig.get(PartnerTestConfig.CITY),
                partnerTestConfig.get(PartnerTestConfig.STATE),
                partnerTestConfig.get(PartnerTestConfig.ZIP),
                partnerTestConfig.get(PartnerTestConfig.COUNTRY),
                partnerTestConfig.get(PartnerTestConfig.PRIMARY_CONTACT_NAME),
                partnerTestConfig.get(PartnerTestConfig.PHONE_NUMBER),
                partnerTestConfig.get(PartnerTestConfig.DATA_TYPE_VALUE_2),
                ecpTestConfig.get(ECP_CORE_NAME), ecpTestConfig.get(CONSERV_PARTNER),
                ecpTestConfig.get(INSTALLERS), ecpTestConfig.get(SERVICE_PROVIDERS),
                ecpTestConfig.get(UTILITIES), ecpTestConfig.get(CUSTOMER_CARES),
                ecpTestConfig.get(GATEWAY_MODEL), ecpTestConfig.get(TSTAT_MODEL) } };
        return data;
    }

    /**
     * Assign to installer.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "assignToInstaller")
    public static Object[][] assignToInstaller(Method m) {

        Object[][] data = { { ecpTestConfig.get(VALID_LOGIN_USER),
                ecpTestConfig.get(VALID_LOGIN_PASSWORD),
                partnerTestConfig.get(PartnerTestConfig.PARTNER_NAME),
                partnerTestConfig.get(PartnerTestConfig.EMAIL),
                partnerTestConfig.get(PartnerTestConfig.DATA_ACTIVE_PARTNER),
                partnerTestConfig.get(PartnerTestConfig.STREET_ADDRESS_1),
                partnerTestConfig.get(PartnerTestConfig.STREET_ADDRESS_2),
                partnerTestConfig.get(PartnerTestConfig.CITY),
                partnerTestConfig.get(PartnerTestConfig.STATE),
                partnerTestConfig.get(PartnerTestConfig.ZIP),
                partnerTestConfig.get(PartnerTestConfig.COUNTRY),
                partnerTestConfig.get(PartnerTestConfig.PRIMARY_CONTACT_NAME),
                partnerTestConfig.get(PartnerTestConfig.PHONE_NUMBER),
                partnerTestConfig.get(PartnerTestConfig.DATA_TYPE_VALUE_3),
                ecpTestConfig.get(ECP_CORE_NAME), ecpTestConfig.get(CONSERV_PARTNER),
                ecpTestConfig.get(INSTALLERS), ecpTestConfig.get(SERVICE_PROVIDERS),
                ecpTestConfig.get(UTILITIES), ecpTestConfig.get(CUSTOMER_CARES),
                ecpTestConfig.get(GATEWAY_MODEL), ecpTestConfig.get(TSTAT_MODEL) } };
        return data;
    }

    /**
     * Assign to customer care.
     * @param m the m
     * @return the object[][]
     */
    @DataProvider(name = "assignToCustomerCare")
    public static Object[][] assignToCustomerCare(Method m) {

        Object[][] data = { { ecpTestConfig.get(VALID_LOGIN_USER),
                ecpTestConfig.get(VALID_LOGIN_PASSWORD),
                partnerTestConfig.get(PartnerTestConfig.PARTNER_NAME),
                partnerTestConfig.get(PartnerTestConfig.EMAIL),
                partnerTestConfig.get(PartnerTestConfig.DATA_ACTIVE_PARTNER),
                partnerTestConfig.get(PartnerTestConfig.STREET_ADDRESS_1),
                partnerTestConfig.get(PartnerTestConfig.STREET_ADDRESS_2),
                partnerTestConfig.get(PartnerTestConfig.CITY),
                partnerTestConfig.get(PartnerTestConfig.STATE),
                partnerTestConfig.get(PartnerTestConfig.ZIP),
                partnerTestConfig.get(PartnerTestConfig.COUNTRY),
                partnerTestConfig.get(PartnerTestConfig.PRIMARY_CONTACT_NAME),
                partnerTestConfig.get(PartnerTestConfig.PHONE_NUMBER),
                partnerTestConfig.get(PartnerTestConfig.DATA_TYPE_VALUE_6),
                ecpTestConfig.get(ECP_CORE_NAME), ecpTestConfig.get(CONSERV_PARTNER),
                ecpTestConfig.get(INSTALLERS), ecpTestConfig.get(SERVICE_PROVIDERS),
                ecpTestConfig.get(UTILITIES), ecpTestConfig.get(CUSTOMER_CARES),
                ecpTestConfig.get(GATEWAY_MODEL), ecpTestConfig.get(TSTAT_MODEL) } };
        return data;
    }

}
