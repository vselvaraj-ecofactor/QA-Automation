package com.ecofactor.qa.automation.util.healthcheck;

import com.ecofactor.qa.automation.util.BaseConfig;
import com.google.inject.Inject;

public class HealthCheckConfig extends BaseConfig {

    public static final String REVISION = "revision";
    public static final String MAIL = "mail";
    public static final String ENVIRONMENT = "environment";
    public static final String DIGI = "digi";
    public static final String JBOSS = "jboss";
    public static final String DATABASE = "database";
    public static final String SERVICE_NAME = "serviceName";

    public static final String YAHOO = "yahoo";
    public static final String SALES_FORCE = "salesforce";

    public static final String HEALTHY = "healthy";

    public static final String APPS_CONSUMER_URL = "appsConsumerUrl";
    public static final String APPS_INSITE_URL = "appsInsiteUrl";

    public static final String PLAT_CONSUMER_URL = "platConsumerUrl";
    public static final String PLAT_INSITE_URL = "platInsiteUrl";

    public static final String STAGE_CONSUMER_URL = "stageConsumerUrl";
    public static final String STAGE_INSITE_URL = "stageInsiteUrl";

    public static final String ENV_QA_APPS = "QA-APPS";
    public static final String ENV_QA_PLAT = "QA-PLAT";
    public static final String ENV_STAGE = "STAGE";

    public static final String PAGE_CONSUMER = "CONSUMER";
    public static final String PAGE_INSITE = "INSITE";

    /**
     * Initiates the new HealthCheckConfig
     */
    @Inject
    public HealthCheckConfig() {

        load("healthcheck.properties");
    }

}
