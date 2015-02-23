package com.ecofactor.qa.automation.insite.config;

import com.ecofactor.qa.automation.util.BaseConfig;
import com.ecofactor.qa.automation.util.DriverConfig;
import com.google.inject.Inject;

public class ECPConfig extends BaseConfig {

    public final static String ECP_CORE_NAME_FIELD = "ecpNameField";
    public final static String ECP_CONSERV_PARTNER = "ecpConservationPartner";
    public final static String ECP_INSTALLERS = "ecpInsatllers";
    public final static String ECP_SERVICE_PROVIDERS = "ecpServiceProviders";
    public final static String ECP_UTILITIES = "ecpUtilities";
    public final static String ECP_CUSTOMER_CARES = "ecpCustomerCares";
    public final static String ECP_GATEWAY_MODEL = "ecpGatewayModel";
    public final static String ECP_TSTAT_MODEL = "ecpThermostatModel";
    
    
    @Inject
    public ECPConfig(DriverConfig driverConfig) {

        super(driverConfig.get(DriverConfig.ENV));
        load("Ecp.properties");
    }

}
