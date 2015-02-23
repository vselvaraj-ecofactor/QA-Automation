/*
 * ECPCoreManagement.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.insite.page;

import com.ecofactor.common.pojo.PartnerType.PartnerTypeName;

/**
 * The Interface ECPCoreManagement.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public interface ECPCoreManagement extends InsiteAuthenticatedPage {

    /**
     * Select conservation partner.
     * @param conservationPartner the conservation partner
     */
    public void selectConservationPartner(String conservationPartner);

    /**
     * Select installers.
     * @param installers the installers
     */
    public void selectInstallers(String installers);

    /**
     * Select service providers.
     * @param serviceProviders the service providers
     */
    public void selectServiceProviders(String serviceProviders);

    /**
     * Select utilities.
     * @param utilities the utilities
     */
    public void selectUtilities(String utilities);

    /**
     * Select customer cares.
     * @param customercares the customercares
     */
    public void selectCustomerCares(String customercares);

    /**
     * Select gateway model.
     * @param gatewayModel the gateway model
     */
    public void selectGatewayModel(String gatewayModel);

    /**
     * Select thermostat model.
     * @param thermostatModel the thermostat model
     */
    public void selectThermostatModel(String thermostatModel);
    
    /**
     * Fill ecp name.
     * @param ecpName the ecp name
     */
    public void fillEcpName(String ecpName);
    
    /**
     * Click save.
     */
    public void clickSave();
    
    /**
     * Click cancel.
     */
    public void clickCancel();
    
    /**
     * Gets the alert message.
     * @return the alert message
     */
    public String getAlertMessage();
    
    /**
     * Checks if is service provider available.
     * @param serviceProvider the service provider
     * @return true, if is service provider available
     */
    public boolean isServiceProviderAvailable(String serviceProvider);
    
    /**
     * Checks if is utility.
     * @param utility the utility
     * @return true, if is utility
     */
    public boolean isUtilityAvailable(String utility);
    
    /**
     * Checks if is conservation partner.
     * @param conservationPartner the conservation partner
     * @return true, if is conservation partner
     */
    public boolean isConservationPartnerAvailable(String conservationPartner);
    
    /**
     * Checks if is installer.
     * @param installer the installer
     * @return true, if is installer
     */
    public boolean isInstallerAvailable(String installer);
    
    /**
     * Checks if is customer care.
     * @param serviceProvider the service provider
     * @return true, if is customer care
     */
    public boolean isCustomerCareAvailable(String customerCare);
    
    /**
     * Check and update db.
     * @param partnerName the partner name
     * @param partnerTypeName the partner type name
     * @return true, if successful
     */
    public boolean checkAndUpdateDB(String partnerName, PartnerTypeName partnerTypeName);

}
