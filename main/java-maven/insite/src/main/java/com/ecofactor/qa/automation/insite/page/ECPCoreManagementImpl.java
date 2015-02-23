/*
 * ECPCoreManagementImpl.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.insite.page;

import static com.ecofactor.qa.automation.insite.config.ECPConfig.ECP_CONSERV_PARTNER;
import static com.ecofactor.qa.automation.insite.config.ECPConfig.ECP_CORE_NAME_FIELD;
import static com.ecofactor.qa.automation.insite.config.ECPConfig.ECP_CUSTOMER_CARES;
import static com.ecofactor.qa.automation.insite.config.ECPConfig.ECP_GATEWAY_MODEL;
import static com.ecofactor.qa.automation.insite.config.ECPConfig.ECP_INSTALLERS;
import static com.ecofactor.qa.automation.insite.config.ECPConfig.ECP_SERVICE_PROVIDERS;
import static com.ecofactor.qa.automation.insite.config.ECPConfig.ECP_TSTAT_MODEL;
import static com.ecofactor.qa.automation.insite.config.ECPConfig.ECP_UTILITIES;
import static com.ecofactor.qa.automation.insite.config.InsiteConfig.INSITE_URL;
import static com.ecofactor.qa.automation.util.PageUtil.SHORT_TIMEOUT;
import static com.ecofactor.qa.automation.util.PageUtil.TAG_ANCHOR;
import static com.ecofactor.qa.automation.util.PageUtil.clearAndInput;
import static com.ecofactor.qa.automation.util.PageUtil.isDisplayedById;
import static com.ecofactor.qa.automation.util.PageUtil.retrieveElementByTagText;
import static com.ecofactor.qa.automation.util.PageUtil.selectOptionByText;
import static com.ecofactor.qa.automation.util.WaitUtil.tinyWait;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import com.ecofactor.common.pojo.PartnerAccount;
import com.ecofactor.common.pojo.PartnerType;
import com.ecofactor.common.pojo.PartnerType.PartnerTypeName;
import com.ecofactor.qa.automation.dao.PartnerAccountDao;
import com.ecofactor.qa.automation.dao.PartnerAccountTypeDao;
import com.ecofactor.qa.automation.dao.PartnerTypeDao;
import com.ecofactor.qa.automation.insite.config.ECPConfig;
import com.ecofactor.qa.automation.insite.config.InsiteConfig;
import com.ecofactor.qa.automation.util.DriverConfig;
import com.ecofactor.qa.automation.util.WaitUtil;
import com.google.inject.Inject;

/**
 * The Class ECPCoreManagementImpl.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class ECPCoreManagementImpl extends InsiteAuthenticatedPageImpl implements ECPCoreManagement {

    /** The app config. */
    @Inject
    private InsiteConfig appConfig;

    /** The dynamic role name. */
    public String dynamicRoleNames = "TestRole";

    /**
     * The role config.
     */
    @Inject
    private ECPConfig ecpConfig;

    /** The logger. */
    @SuppressWarnings("unused")
    private static Logger logger = LoggerFactory.getLogger(RoleManagementImpl.class);

    /** The current date. */
    Date currentDate = new Date();

    /** The formatter. */
    public SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");

    /** The date time stamp. */
    public String dateTimeStamp = formatter.format(currentDate);

    @Inject
    private PartnerAccountDao partnerAccountDao;

    @Inject
    private PartnerAccountTypeDao partnerAccountTypeDao;

    @Inject
    private PartnerTypeDao partnerTypeDao;

    /**
     * Load page.
     * @see com.ecofactor.qa.automation.insite.page.InsitePage#loadPage()
     */
    @SuppressWarnings("static-access")
    @Override
    public void loadPage() {

        String url = appConfig.get(INSITE_URL) + appConfig.get(appConfig.ECP_CORE_PAGE);
        if (!DriverConfig.getDriver().getCurrentUrl().equalsIgnoreCase(url)) {
            clickECPCoreManagement();
        }

    }

    /**
     * Select conservation partner.
     * @param conservationPartner the conservation partner
     * @see com.ecofactor.qa.automation.insite.page.ECPCoreManagement#selectConservationPartner(java.lang.String)
     */
    @Override
    public void selectConservationPartner(String conservationPartner) {

        if (!conservationPartner.isEmpty()) {

            DriverConfig.setLogString("Select Conservation Partner : " + conservationPartner, true);
            Assert.assertTrue(
                    isDisplayedById(DriverConfig.getDriver(), ecpConfig.get(ECP_CONSERV_PARTNER),
                            SHORT_TIMEOUT), "ConservationPartner Not Displayed");
            selectOptionByText(DriverConfig.getDriver(), ecpConfig.get(ECP_CONSERV_PARTNER),
                    conservationPartner);
        }
    }

    /**
     * Select installers.
     * @param installers the installers
     * @see com.ecofactor.qa.automation.insite.page.ECPCoreManagement#selectInstallers(java.lang.String)
     */
    @Override
    public void selectInstallers(String installers) {

        if (!installers.isEmpty()) {
            DriverConfig.setLogString("Select Installers Partner : " + installers, true);
            Assert.assertTrue(
                    isDisplayedById(DriverConfig.getDriver(), ecpConfig.get(ECP_INSTALLERS),
                            SHORT_TIMEOUT), "Installers Not Displayed");
            selectOptionByText(DriverConfig.getDriver(), ecpConfig.get(ECP_INSTALLERS), installers);
        }

    }

    /**
     * Select service providers.
     * @param serviceProviders the service providers
     * @see com.ecofactor.qa.automation.insite.page.ECPCoreManagement#selectServiceProviders(java.lang.String)
     */
    @Override
    public void selectServiceProviders(String serviceProviders) {

        if (!serviceProviders.isEmpty()) {
            DriverConfig.setLogString("Select Service Providers  : " + serviceProviders, true);
            Assert.assertTrue(
                    isDisplayedById(DriverConfig.getDriver(), ecpConfig.get(ECP_SERVICE_PROVIDERS),
                            SHORT_TIMEOUT), "Service Providers Not Displayed");
            selectOptionByText(DriverConfig.getDriver(), ecpConfig.get(ECP_SERVICE_PROVIDERS),
                    serviceProviders);
        }

    }

    /**
     * Select utilities.
     * @param utilities the utilities
     * @see com.ecofactor.qa.automation.insite.page.ECPCoreManagement#selectUtilities(java.lang.String)
     */
    @Override
    public void selectUtilities(String utilities) {

        if (!utilities.isEmpty()) {
            DriverConfig.setLogString("Select Utilities : " + utilities, true);
            Assert.assertTrue(
                    isDisplayedById(DriverConfig.getDriver(), ecpConfig.get(ECP_SERVICE_PROVIDERS),
                            SHORT_TIMEOUT), "Utilities Not Displayed");
            selectOptionByText(DriverConfig.getDriver(), ecpConfig.get(ECP_UTILITIES), utilities);
        }

    }

    /**
     * Select customer cares.
     * @param customercares the customercares
     * @see com.ecofactor.qa.automation.insite.page.ECPCoreManagement#selectCustomerCares(java.lang.String)
     */
    @Override
    public void selectCustomerCares(String customercares) {

        if (!customercares.isEmpty()) {
            DriverConfig.setLogString("Select Customer Cares : " + customercares, true);
            Assert.assertTrue(
                    isDisplayedById(DriverConfig.getDriver(), ecpConfig.get(ECP_CUSTOMER_CARES),
                            SHORT_TIMEOUT), "Customer Cares Not Displayed");
            selectOptionByText(DriverConfig.getDriver(), ecpConfig.get(ECP_CUSTOMER_CARES),
                    customercares);
        }
    }

    /**
     * Select gateway model.
     * @param gatewayModel the gateway model
     * @see com.ecofactor.qa.automation.insite.page.ECPCoreManagement#selectGatewayModel(java.lang.String)
     */
    @Override
    public void selectGatewayModel(String gatewayModel) {

        if (!gatewayModel.isEmpty()) {
            DriverConfig.setLogString("Select GateWay Model : " + gatewayModel, true);
            Assert.assertTrue(
                    isDisplayedById(DriverConfig.getDriver(), ecpConfig.get(ECP_GATEWAY_MODEL),
                            SHORT_TIMEOUT), "Gateway Model Not Displayed");
            selectOptionByText(DriverConfig.getDriver(), ecpConfig.get(ECP_GATEWAY_MODEL),
                    gatewayModel);
        }

    }

    /**
     * Select thermostat model.
     * @param thermostatModel the thermostat model
     * @see com.ecofactor.qa.automation.insite.page.ECPCoreManagement#selectThermostatModel(java.lang.String)
     */
    @Override
    public void selectThermostatModel(String thermostatModel) {

        if (!thermostatModel.isEmpty()) {

            DriverConfig.setLogString("Select Tstat Model : " + thermostatModel, true);
            Assert.assertTrue(
                    isDisplayedById(DriverConfig.getDriver(), ecpConfig.get(ECP_TSTAT_MODEL),
                            SHORT_TIMEOUT), "Thermostat Model Not Displayed");
            selectOptionByText(DriverConfig.getDriver(), ecpConfig.get(ECP_TSTAT_MODEL),
                    thermostatModel);
        }

    }

    /**
     * Fill ecp name.
     * @param ecpName the ecp name
     * @see com.ecofactor.qa.automation.insite.page.ECPCoreManagement#fillEcpName(java.lang.String)
     */
    @Override
    public void fillEcpName(String ecpName) {

        DriverConfig.setLogString("Ecp Name : " + ecpName, true);
        Assert.assertTrue(
                isDisplayedById(DriverConfig.getDriver(), ecpConfig.get(ECP_CORE_NAME_FIELD),
                        SHORT_TIMEOUT), "Ecp Core Name Field Not Displayed");
        clearAndInput(DriverConfig.getDriver(), By.id(ecpConfig.get(ECP_CORE_NAME_FIELD)), ecpName);

    }

    /**
     * @see com.ecofactor.qa.automation.insite.page.ECPCoreManagement#clickSave()
     */
    @Override
    public void clickSave() {

        DriverConfig.setLogString("Click save link.", true);
        WebElement saveLink = retrieveElementByTagText(DriverConfig.getDriver(), TAG_ANCHOR, "Save");
        saveLink.click();

        tinyWait();

    }

    /**
     * @see com.ecofactor.qa.automation.insite.page.ECPCoreManagement#clickCancel()
     */
    @Override
    public void clickCancel() {

        DriverConfig.setLogString("Click Cancel in Ecp Page ", true);
        WaitUtil.smallWait();
        final WebElement cancelLink = retrieveElementByTagText(DriverConfig.getDriver(),
                TAG_ANCHOR, "Cancel");
        cancelLink.click();

    }

    /**
     * Gets the alert message.
     * @return the alert message
     * @see com.ecofactor.qa.automation.insite.page.ECPCoreManagement#getAlertMessage()
     */
    @Override
    public String getAlertMessage() {

        tinyWait();
        String alertText = DriverConfig.getDriver().switchTo().alert().getText();
        DriverConfig.setLogString("Alert Text : " + alertText, true);
        DriverConfig.getDriver().switchTo().alert().accept();
        return alertText;
    }

    /**
     * Checks if is service provider available.
     * @param serviceProvider the service provider
     * @return true, if is service provider available
     * @see com.ecofactor.qa.automation.insite.page.ECPCoreManagement#isServiceProviderAvailable(java.lang.String)
     */
    @Override
    public boolean isServiceProviderAvailable(String serviceProvider) {

        DriverConfig
                .setLogString("Check Service Provider : " + serviceProvider + "available", true);
        tinyWait();
        boolean isavailable = false;
        List<WebElement> serviceProviders = DriverConfig.getDriver().findElements(
                By.xpath("//*[@id='" + ecpConfig.get(ECP_SERVICE_PROVIDERS + "']/option")));
        for (WebElement webElement : serviceProviders) {
            if (webElement.getText().equalsIgnoreCase(serviceProvider)) {
                isavailable = true;
            }
        }

        return isavailable;
    }

    /**
     * @param utility
     * @return
     * @see com.ecofactor.qa.automation.insite.page.ECPCoreManagement#isUtilityAvailable(java.lang.String)
     */
    @Override
    public boolean isUtilityAvailable(String utility) {

        DriverConfig.setLogString("Check Utility : " + utility + "available", true);
        tinyWait();
        boolean isavailable = false;
        List<WebElement> utilities = DriverConfig.getDriver().findElements(
                By.xpath("//*[@id='" + ecpConfig.get(ECP_UTILITIES + "']/option")));
        for (WebElement webElement : utilities) {
            if (webElement.getText().equalsIgnoreCase(utility)) {
                isavailable = true;
            }
        }

        return isavailable;
    }

    /**
     * @param conservationPartner
     * @return
     * @see com.ecofactor.qa.automation.insite.page.ECPCoreManagement#isConservationPartnerAvailable(java.lang.String)
     */
    @Override
    public boolean isConservationPartnerAvailable(String conservationPartner) {

        DriverConfig.setLogString("Check Conservation Partner : " + conservationPartner
                + "available", true);
        tinyWait();
        boolean isavailable = false;
        List<WebElement> conservPartner = DriverConfig.getDriver().findElements(
                By.xpath("//*[@id='" + ecpConfig.get(ECP_CONSERV_PARTNER + "']/option")));
        for (WebElement webElement : conservPartner) {
            if (webElement.getText().equalsIgnoreCase(conservationPartner)) {
                isavailable = true;
            }
        }

        return isavailable;
    }

    /**
     * @param installer
     * @return
     * @see com.ecofactor.qa.automation.insite.page.ECPCoreManagement#isInstallerAvailable(java.lang.String)
     */
    @Override
    public boolean isInstallerAvailable(String installer) {

        DriverConfig.setLogString("Check Installer : " + installer + "available", true);
        tinyWait();
        boolean isavailable = false;
        List<WebElement> installers = DriverConfig.getDriver().findElements(
                By.xpath("//*[@id='" + ecpConfig.get(ECP_INSTALLERS + "']/option")));
        for (WebElement webElement : installers) {
            if (webElement.getText().equalsIgnoreCase(installer)) {
                isavailable = true;
            }
        }

        return isavailable;

    }

    /**
     * @param customerCare
     * @return
     * @see com.ecofactor.qa.automation.insite.page.ECPCoreManagement#isCustomerCareAvailable(java.lang.String)
     */
    @Override
    public boolean isCustomerCareAvailable(String customerCare) {

        DriverConfig.setLogString("Check Customer Care : " + customerCare + "available", true);
        tinyWait();
        boolean isavailable = false;
        List<WebElement> customerCares = DriverConfig.getDriver().findElements(
                By.xpath("//*[@id='" + ecpConfig.get(ECP_CUSTOMER_CARES + "']/option")));
        for (WebElement webElement : customerCares) {
            if (webElement.getText().equalsIgnoreCase(customerCare)) {
                isavailable = true;
            }
        }

        return isavailable;
    }

    /**
     * @param partnerName
     * @param partnerTypeName
     * @return
     * @see com.ecofactor.qa.automation.insite.page.ECPCoreManagement#checkAndUpdateDB(java.lang.String,
     *      com.ecofactor.common.pojo.PartnerType.PartnerTypeName)
     */
    @Override
    public boolean checkAndUpdateDB(String partnerName, PartnerTypeName partnerTypeName) {

        DriverConfig.setLogString("Check and Update DB ", true);
        PartnerAccount partnerAccount = partnerAccountDao.getPartnerAccountByName(partnerName);
        Assert.assertNotNull(partnerAccount);
        PartnerType partnerType = partnerTypeDao.getPartnerTypeByName(partnerTypeName);
        Assert.assertNotNull(partnerType);
        return partnerAccountTypeDao.updatePartnerType(partnerAccount, partnerType);
    }

}
