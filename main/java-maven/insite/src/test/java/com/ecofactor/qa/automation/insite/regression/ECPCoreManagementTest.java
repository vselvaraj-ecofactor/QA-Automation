/*
 * ECPCoreManagementTest.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.insite.regression;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Guice;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.ecofactor.common.pojo.PartnerType.PartnerTypeName;
import com.ecofactor.qa.automation.dao.DaoModule;

import com.ecofactor.qa.automation.insite.InsiteModule;
import com.ecofactor.qa.automation.insite.data.ECPCoreDataProvider;
import com.ecofactor.qa.automation.insite.page.ECPCoreManagement;
import com.ecofactor.qa.automation.insite.page.PartnerManagement;
import com.ecofactor.qa.automation.util.DriverConfig;
import com.ecofactor.qa.automation.util.RerunFailTestAnalyzer;
import com.ecofactor.qa.automation.util.TestLogUtil;
import com.ecofactor.qa.automation.util.UtilModule;
import com.ecofactor.qa.automation.util.test.JobValidator;
import com.google.inject.Inject;

/**
 * The Class ECPCoreManagementTest.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
@Guice(modules = { UtilModule.class, DaoModule.class, InsiteModule.class })
@Listeners(JobValidator.class)
public class ECPCoreManagementTest {

    /** The current date. */
    Date currentDate = new Date();

    /** The formatter. */
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");

    /** The date time stamp. */
    String dateTimeStamp = formatter.format(currentDate);

    @Inject
    private ECPCoreManagement ecpCoreManagement;

    @Inject
    private PartnerManagement partnerManagement;

    /** The test log util. */
    @Inject
    private TestLogUtil testLogUtil;

    /** The logger. */
    private static Logger logger = LoggerFactory.getLogger(RoleManagementTest.class);

    /** The start. */
    private long start;

    /**
     * Inits the method.
     * @param param the param
     * @param method the method
     */
    @BeforeMethod(alwaysRun = true)
    public void initMethod(final Object[] param, final Method method) {

        testLogUtil.logStart(method, param);
        start = System.currentTimeMillis();

        try {
            ecpCoreManagement.loadPage((String) param[0], (String) param[1]);
        } catch (Throwable e) {
            logger.error("Error in before method " + e.getMessage());
        }
    }

    /**
     * End method.
     * @param method the method
     */
    @AfterMethod(alwaysRun = true)
    public void endMethod(final Method method) {

        final long duration = (System.currentTimeMillis() - start) / 1000;
        testLogUtil.logEnd(method, duration);
    }

    /**
     * End Class.
     */
    @AfterClass(alwaysRun = true)
    public void endClass() {

        try {
            ecpCoreManagement.logout();
        } catch (Throwable e) {
            logger.error("Error in after class " + e.getMessage());
        }
    }

    /**
     * End suite.
     */
    @AfterSuite(alwaysRun = true)
    public void endSuite() {

        try {
            ecpCoreManagement.end();
        } catch (Throwable e) {
            logger.error("Error in after suite " + e.getMessage());
        }
    }

    /**
     * Test create new ecp.
     * @param userName the user name
     * @param password the password
     * @param ecpName the ecp name
     * @param conservPartner the conserv partner
     * @param installers the installers
     * @param serviceProviders the service providers
     * @param utilities the utilities
     * @param customerCares the customer cares
     * @param gatewayModel the gateway model
     * @param tstatModel the tstat model
     */
    @Test(dataProvider = "createNewEcp", dataProviderClass = ECPCoreDataProvider.class, retryAnalyzer = RerunFailTestAnalyzer.class, groups = { "sanity" }, priority = 1)
    public void testCreateNewEcp(final String userName, final String password,
            final String ecpName, final String conservPartner, final String installers,
            final String serviceProviders, final String utilities, final String customerCares,
            final String gatewayModel, final String tstatModel) {

        ecpCoreManagement.fillEcpName(ecpName + dateTimeStamp);
        ecpCoreManagement.selectConservationPartner(conservPartner);
        ecpCoreManagement.selectInstallers(installers);
        ecpCoreManagement.selectServiceProviders(serviceProviders);
        ecpCoreManagement.selectUtilities(utilities);
        ecpCoreManagement.selectCustomerCares(customerCares);
        ecpCoreManagement.selectGatewayModel(gatewayModel);
        ecpCoreManagement.selectThermostatModel(tstatModel);
        ecpCoreManagement.clickSave();
        Assert.assertTrue(
                ecpCoreManagement.getAlertMessage().contains("EcpCore succesfully created"),
                "Ecp Core Creation Failed");

    }

    /**
     * Test create ecp without name.
     * @param userName the user name
     * @param password the password
     * @param ecpName the ecp name
     * @param conservPartner the conserv partner
     * @param installers the installers
     * @param serviceProviders the service providers
     * @param utilities the utilities
     * @param customerCares the customer cares
     * @param gatewayModel the gateway model
     * @param tstatModel the tstat model
     */
    @Test(dataProvider = "createNewEcpWithoutName", dataProviderClass = ECPCoreDataProvider.class, groups = { "sanity" }, priority = 2)
    public void testCreateEcpWithoutName(final String userName, final String password,
            final String ecpName, final String conservPartner, final String installers,
            final String serviceProviders, final String utilities, final String customerCares,
            final String gatewayModel, final String tstatModel) {

        ecpCoreManagement.fillEcpName(ecpName);
        ecpCoreManagement.selectConservationPartner(conservPartner);
        ecpCoreManagement.selectInstallers(installers);
        ecpCoreManagement.selectServiceProviders(serviceProviders);
        ecpCoreManagement.selectUtilities(utilities);
        ecpCoreManagement.selectCustomerCares(customerCares);
        ecpCoreManagement.selectGatewayModel(gatewayModel);
        ecpCoreManagement.selectThermostatModel(tstatModel);
        ecpCoreManagement.clickSave();
        Assert.assertTrue(
                ecpCoreManagement.getAlertMessage().contains("EcpCore succesfully created"),
                "Ecp Core Creation Failed");

    }

    /**
     * Skip installer.
     * @param userName the user name
     * @param password the password
     * @param ecpName the ecp name
     * @param conservPartner the conserv partner
     * @param installers the installers
     * @param serviceProviders the service providers
     * @param utilities the utilities
     * @param customerCares the customer cares
     * @param gatewayModel the gateway model
     * @param tstatModel the tstat model
     */
    @Test(dataProvider = "skipInstallers", dataProviderClass = ECPCoreDataProvider.class, groups = { "sanity" }, priority = 3)
    public void skipInstaller(final String userName, final String password, final String ecpName,
            final String conservPartner, final String installers, final String serviceProviders,
            final String utilities, final String customerCares, final String gatewayModel,
            final String tstatModel) {

        ecpCoreManagement.fillEcpName(ecpName + dateTimeStamp);
        ecpCoreManagement.selectConservationPartner(conservPartner);
        ecpCoreManagement.selectInstallers(installers);
        ecpCoreManagement.selectServiceProviders(serviceProviders);
        ecpCoreManagement.selectUtilities(utilities);
        ecpCoreManagement.selectCustomerCares(customerCares);
        ecpCoreManagement.selectGatewayModel(gatewayModel);
        ecpCoreManagement.selectThermostatModel(tstatModel);
        ecpCoreManagement.clickSave();
        Assert.assertTrue(
                ecpCoreManagement.getAlertMessage().contains(
                        "Cannot save EcpCore without a installer."),
                "Expected alert message is not displayed");

    }

    /**
     * Skip utility.
     * @param userName the user name
     * @param password the password
     * @param ecpName the ecp name
     * @param conservPartner the conserv partner
     * @param installers the installers
     * @param serviceProviders the service providers
     * @param utilities the utilities
     * @param customerCares the customer cares
     * @param gatewayModel the gateway model
     * @param tstatModel the tstat model
     */
    @Test(dataProvider = "skipUtility", dataProviderClass = ECPCoreDataProvider.class, groups = { "sanity" }, priority = 4)
    public void skipUtility(final String userName, final String password, final String ecpName,
            final String conservPartner, final String installers, final String serviceProviders,
            final String utilities, final String customerCares, final String gatewayModel,
            final String tstatModel) {

        ecpCoreManagement.fillEcpName(ecpName + dateTimeStamp);
        ecpCoreManagement.selectConservationPartner(conservPartner);
        ecpCoreManagement.selectInstallers(installers);
        ecpCoreManagement.selectServiceProviders(serviceProviders);
        ecpCoreManagement.selectUtilities(utilities);
        ecpCoreManagement.selectCustomerCares(customerCares);
        ecpCoreManagement.selectGatewayModel(gatewayModel);
        ecpCoreManagement.selectThermostatModel(tstatModel);
        ecpCoreManagement.clickSave();
        Assert.assertTrue(
                ecpCoreManagement.getAlertMessage().contains(
                        "Cannot save EcpCore without a utility partner."),
                "Expected alert message is not displayed");

    }

    /**
     * Skip service.
     * @param userName the user name
     * @param password the password
     * @param ecpName the ecp name
     * @param conservPartner the conserv partner
     * @param installers the installers
     * @param serviceProviders the service providers
     * @param utilities the utilities
     * @param customerCares the customer cares
     * @param gatewayModel the gateway model
     * @param tstatModel the tstat model
     */
    @Test(dataProvider = "skipService", dataProviderClass = ECPCoreDataProvider.class, groups = { "sanity" }, priority = 5)
    public void skipService(final String userName, final String password, final String ecpName,
            final String conservPartner, final String installers, final String serviceProviders,
            final String utilities, final String customerCares, final String gatewayModel,
            final String tstatModel) {

        ecpCoreManagement.fillEcpName(ecpName + dateTimeStamp);
        ecpCoreManagement.selectConservationPartner(conservPartner);
        ecpCoreManagement.selectInstallers(installers);
        ecpCoreManagement.selectServiceProviders(serviceProviders);
        ecpCoreManagement.selectUtilities(utilities);
        ecpCoreManagement.selectCustomerCares(customerCares);
        ecpCoreManagement.selectGatewayModel(gatewayModel);
        ecpCoreManagement.selectThermostatModel(tstatModel);
        ecpCoreManagement.clickSave();
        Assert.assertTrue(
                ecpCoreManagement.getAlertMessage().contains(
                        "Cannot save EcpCore without a service provider."),
                "Expected alert message is not displayed");

    }

    /**
     * Skip customer care.
     * @param userName the user name
     * @param password the password
     * @param ecpName the ecp name
     * @param conservPartner the conserv partner
     * @param installers the installers
     * @param serviceProviders the service providers
     * @param utilities the utilities
     * @param customerCares the customer cares
     * @param gatewayModel the gateway model
     * @param tstatModel the tstat model
     */
    @Test(dataProvider = "skipCustomerCare", dataProviderClass = ECPCoreDataProvider.class, groups = { "sanity" }, priority = 6)
    public void skipCustomerCare(final String userName, final String password,
            final String ecpName, final String conservPartner, final String installers,
            final String serviceProviders, final String utilities, final String customerCares,
            final String gatewayModel, final String tstatModel) {

        ecpCoreManagement.fillEcpName(ecpName + dateTimeStamp);
        ecpCoreManagement.selectConservationPartner(conservPartner);
        ecpCoreManagement.selectInstallers(installers);
        ecpCoreManagement.selectServiceProviders(serviceProviders);
        ecpCoreManagement.selectUtilities(utilities);
        ecpCoreManagement.selectCustomerCares(customerCares);
        ecpCoreManagement.selectGatewayModel(gatewayModel);
        ecpCoreManagement.selectThermostatModel(tstatModel);
        ecpCoreManagement.clickSave();
        Assert.assertTrue(
                ecpCoreManagement.getAlertMessage().contains(
                        "Cannot save EcpCore without a customer care partner."),
                "Expected alert message is not displayed");

    }

    /**
     * Test assign partner to service provider.
     * @param userName the user name
     * @param password the password
     * @param partnerName the partner name
     * @param emailId the email id
     * @param activeUser the active user
     * @param streetAddress1 the street address1
     * @param streetAddress2 the street address2
     * @param city the city
     * @param state the state
     * @param zip the zip
     * @param country the country
     * @param primaryContactName the primary contact name
     * @param phoneNumber the phone number
     * @param availablePartnerType the available partner type
     * @param ecpName the ecp name
     * @param conservPartner the conserv partner
     * @param installers the installers
     * @param serviceProviders the service providers
     * @param utilities the utilities
     * @param customerCares the customer cares
     * @param gatewayModel the gateway model
     * @param tstatModel the tstat model
     */
    @Test(dataProvider = "assignToServiceProvider", dataProviderClass = ECPCoreDataProvider.class, groups = { "sanity" }, priority = 7)
    public void testAssignPartnerToServiceProvider(final String userName, final String password,
            final String partnerName, final String emailId, final String activeUser,
            final String streetAddress1, final String streetAddress2, final String city,
            final String state, final String zip, final String country,
            final String primaryContactName, final String phoneNumber,
            final String availablePartnerType, final String ecpName, final String conservPartner,
            final String installers, final String serviceProviders, final String utilities,
            final String customerCares, final String gatewayModel, final String tstatModel) {

        createPartner(partnerName, emailId, activeUser, streetAddress1, streetAddress2, city,
                state, zip, country, primaryContactName, phoneNumber, availablePartnerType);
        Assert.assertTrue(ecpCoreManagement.checkAndUpdateDB(partnerName + dateTimeStamp,
                PartnerTypeName.SERVICE_PROVIDER), "Partner Type Not Updated");
        ecpCoreManagement.clickECPCoreManagement();
        Assert.assertTrue(
                ecpCoreManagement.isServiceProviderAvailable(partnerName + dateTimeStamp),
                "Partner Not Available");
    }

    /**
     * Test assign partner to utilities.
     * @param userName the user name
     * @param password the password
     * @param partnerName the partner name
     * @param emailId the email id
     * @param activeUser the active user
     * @param streetAddress1 the street address1
     * @param streetAddress2 the street address2
     * @param city the city
     * @param state the state
     * @param zip the zip
     * @param country the country
     * @param primaryContactName the primary contact name
     * @param phoneNumber the phone number
     * @param availablePartnerType the available partner type
     * @param ecpName the ecp name
     * @param conservPartner the conserv partner
     * @param installers the installers
     * @param serviceProviders the service providers
     * @param utilities the utilities
     * @param customerCares the customer cares
     * @param gatewayModel the gateway model
     * @param tstatModel the tstat model
     */
    @Test(dataProvider = "assignToUtilities", dataProviderClass = ECPCoreDataProvider.class, groups = { "sanity" }, priority = 8)
    public void testAssignPartnerToUtilities(final String userName, final String password,
            final String partnerName, final String emailId, final String activeUser,
            final String streetAddress1, final String streetAddress2, final String city,
            final String state, final String zip, final String country,
            final String primaryContactName, final String phoneNumber,
            final String availablePartnerType, final String ecpName, final String conservPartner,
            final String installers, final String serviceProviders, final String utilities,
            final String customerCares, final String gatewayModel, final String tstatModel) {

        createPartner(partnerName, emailId, activeUser, streetAddress1, streetAddress2, city,
                state, zip, country, primaryContactName, phoneNumber, availablePartnerType);
        Assert.assertTrue(ecpCoreManagement.checkAndUpdateDB(partnerName + dateTimeStamp,
                PartnerTypeName.UTILITY), "Partner Type Not Updated");
        ecpCoreManagement.clickECPCoreManagement();
        Assert.assertTrue(ecpCoreManagement.isUtilityAvailable(partnerName + dateTimeStamp),
                "Partner Not Available");
    }

    /**
     * Test assign partner to conservation partner.
     * @param userName the user name
     * @param password the password
     * @param partnerName the partner name
     * @param emailId the email id
     * @param activeUser the active user
     * @param streetAddress1 the street address1
     * @param streetAddress2 the street address2
     * @param city the city
     * @param state the state
     * @param zip the zip
     * @param country the country
     * @param primaryContactName the primary contact name
     * @param phoneNumber the phone number
     * @param availablePartnerType the available partner type
     * @param ecpName the ecp name
     * @param conservPartner the conserv partner
     * @param installers the installers
     * @param serviceProviders the service providers
     * @param utilities the utilities
     * @param customerCares the customer cares
     * @param gatewayModel the gateway model
     * @param tstatModel the tstat model
     */
    @Test(dataProvider = "assignToConservationPartner", dataProviderClass = ECPCoreDataProvider.class, groups = { "sanity" }, priority = 9)
    public void testAssignPartnerToConservationPartner(final String userName,
            final String password, final String partnerName, final String emailId,
            final String activeUser, final String streetAddress1, final String streetAddress2,
            final String city, final String state, final String zip, final String country,
            final String primaryContactName, final String phoneNumber,
            final String availablePartnerType, final String ecpName, final String conservPartner,
            final String installers, final String serviceProviders, final String utilities,
            final String customerCares, final String gatewayModel, final String tstatModel) {

        createPartner(partnerName, emailId, activeUser, streetAddress1, streetAddress2, city,
                state, zip, country, primaryContactName, phoneNumber, availablePartnerType);
        Assert.assertTrue(ecpCoreManagement.checkAndUpdateDB(partnerName + dateTimeStamp,
                PartnerTypeName.CONSERVATION_PARTNER), "Partner Type Not Updated");
        ecpCoreManagement.clickECPCoreManagement();
        Assert.assertTrue(
                ecpCoreManagement.isConservationPartnerAvailable(partnerName + dateTimeStamp),
                "Partner Not Available");
    }

    /**
     * Test assign partner to installer.
     * @param userName the user name
     * @param password the password
     * @param partnerName the partner name
     * @param emailId the email id
     * @param activeUser the active user
     * @param streetAddress1 the street address1
     * @param streetAddress2 the street address2
     * @param city the city
     * @param state the state
     * @param zip the zip
     * @param country the country
     * @param primaryContactName the primary contact name
     * @param phoneNumber the phone number
     * @param availablePartnerType the available partner type
     * @param ecpName the ecp name
     * @param conservPartner the conserv partner
     * @param installers the installers
     * @param serviceProviders the service providers
     * @param utilities the utilities
     * @param customerCares the customer cares
     * @param gatewayModel the gateway model
     * @param tstatModel the tstat model
     */
    @Test(dataProvider = "assignToInstaller", dataProviderClass = ECPCoreDataProvider.class, groups = { "sanity" }, priority = 10)
    public void testAssignPartnerToInstaller(final String userName, final String password,
            final String partnerName, final String emailId, final String activeUser,
            final String streetAddress1, final String streetAddress2, final String city,
            final String state, final String zip, final String country,
            final String primaryContactName, final String phoneNumber,
            final String availablePartnerType, final String ecpName, final String conservPartner,
            final String installers, final String serviceProviders, final String utilities,
            final String customerCares, final String gatewayModel, final String tstatModel) {

        createPartner(partnerName, emailId, activeUser, streetAddress1, streetAddress2, city,
                state, zip, country, primaryContactName, phoneNumber, availablePartnerType);
        Assert.assertTrue(ecpCoreManagement.checkAndUpdateDB(partnerName + dateTimeStamp,
                PartnerTypeName.INSTALLER), "Partner Type Not Updated");
        ecpCoreManagement.clickECPCoreManagement();
        Assert.assertTrue(ecpCoreManagement.isInstallerAvailable(partnerName + dateTimeStamp),
                "Partner Not Available");
    }

    /**
     * Test assign partner to customer care.
     * @param userName the user name
     * @param password the password
     * @param partnerName the partner name
     * @param emailId the email id
     * @param activeUser the active user
     * @param streetAddress1 the street address1
     * @param streetAddress2 the street address2
     * @param city the city
     * @param state the state
     * @param zip the zip
     * @param country the country
     * @param primaryContactName the primary contact name
     * @param phoneNumber the phone number
     * @param availablePartnerType the available partner type
     * @param ecpName the ecp name
     * @param conservPartner the conserv partner
     * @param installers the installers
     * @param serviceProviders the service providers
     * @param utilities the utilities
     * @param customerCares the customer cares
     * @param gatewayModel the gateway model
     * @param tstatModel the tstat model
     */
    @Test(dataProvider = "assignToCustomerCare", dataProviderClass = ECPCoreDataProvider.class, groups = { "sanity" }, priority = 11)
    public void testAssignPartnerToCustomerCare(final String userName, final String password,
            final String partnerName, final String emailId, final String activeUser,
            final String streetAddress1, final String streetAddress2, final String city,
            final String state, final String zip, final String country,
            final String primaryContactName, final String phoneNumber,
            final String availablePartnerType, final String ecpName, final String conservPartner,
            final String installers, final String serviceProviders, final String utilities,
            final String customerCares, final String gatewayModel, final String tstatModel) {

        createPartner(partnerName, emailId, activeUser, streetAddress1, streetAddress2, city,
                state, zip, country, primaryContactName, phoneNumber, availablePartnerType);
        Assert.assertTrue(ecpCoreManagement.checkAndUpdateDB(partnerName + dateTimeStamp,
                PartnerTypeName.CUSTOMER_CARE), "Partner Type Not Updated");
        ecpCoreManagement.clickECPCoreManagement();
        Assert.assertTrue(ecpCoreManagement.isCustomerCareAvailable(partnerName + dateTimeStamp),
                "Partner Not Available");
    }

    /**
     * Creates the partner.
     * @param partnerName the partner name
     * @param emailId the email id
     * @param activeUser the active user
     * @param streetAddress1 the street address1
     * @param streetAddress2 the street address2
     * @param city the city
     * @param state the state
     * @param zip the zip
     * @param country the country
     * @param primaryContactName the primary contact name
     * @param phoneNumber the phone number
     * @param availablePartnerType the available partner type
     */
    private void createPartner(final String partnerName, final String emailId,
            final String activeUser, final String streetAddress1, final String streetAddress2,
            final String city, final String state, final String zip, final String country,
            final String primaryContactName, final String phoneNumber,
            final String availablePartnerType) {

        partnerManagement.clickPartnerManagement();
        partnerManagement.clickNewPartner();
        partnerManagement.fillPartnerDetails(DriverConfig.getDriver(), partnerName + dateTimeStamp,
                emailId, activeUser, streetAddress1, streetAddress2, city, state, zip, country,
                primaryContactName, phoneNumber, availablePartnerType);
        partnerManagement.clickSave();
        partnerManagement.checkForPartnerCreationAlert();
    }
}
