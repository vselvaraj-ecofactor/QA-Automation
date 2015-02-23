/*
 * InsiteConfig.java
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
 * The Class InsiteConfig.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class InsiteConfig extends BaseConfig {

    public static final String INSITE_URL = "insiteUrl";
    public static final String INSITE_LOGIN_USERNAME = "insiteLoginUserName";
    public static final String INSITE_LOGIN_URL = "loginurl";
    public static final String MENU_ID="menuId";
    public static final String ACCOUNT_URL="accountListUrl";
    public static final String SUPPORT_PAGE="supportPage";
    public static final String INSTALLATION_PAGE="installationPage";
    public static final String ADMIN_PAGE="adminPage";
    public static final String ROLE_PAGE="rolePage";
    public static final String DEMANDSIDE_PAGE="demandSitePage";
    public static final String SUB_MENU="subMenu";
    public static final String EF_SMALL_LABEL="efSmallLabel";
    public static final String ABOUT_ECOFACTOR_LINK="aboutEcofactorLink";
    public static final String ECOFACTOR_URL="ecofactorUrl";
    public static final String PRECONFIG_URL="preconfigurationUrl";
    public static final String SCHEDULE_URL="schedulingUrl";
    public static final String ON_BOARD_PAGE="onboardPage";
    public static final String BULK_UPLOAD="bulkUpload";
    public static final String HISTORY_PAGE="historyPage";
    public static final String ERRORS_TO_BE_FIXED_PAGE="errorsToBeFixedPage";
    public static final String ECP_CORE_PAGE="ecpCorePage";
    

    public static final String ADMIN="admin";
    public static final String USER_MNGMNT="userManagement";
    public static final String ROLE_MNGMNT="roleManagement";
    public static final String PARTNER_MNGMNT="partnerManagement";
    public static final String ECP_CORE_MNGMNT="ecpCoreManagement";
    public static final String SUPPORT="support";
    public static final String ACCOUNT_LOOKUP="accountLookUp";
    public static final String INSTALLATION="installation";
    public static final String ONSITE_INSTALLATION="onSiteInstallation";
    public static final String SCHEDULING="scheduling";
    public static final String PRE_CONFIGURATION="preConfiguration";
    public static final String DEMAND_SIDE_MGMNT="demandSideManagement";
    public static final String LOAD_SHAPING="loadShaping";
    public static final String ON_BOARDING="onBoarding";
    public static final String BULK_UPLOADS="bulkUploads";
    public static final String ERRORS_TO_BE_FIXED="errorsToBeFixed";
    public static final String HISTORY="history";
    public static final String UPLOAD_ONE_USER="uploadOneUser";
    public static final String ECOFACTOR="ecofactor";


    /**
     * Instantiates a new insite config.
     * @param driverConfig the driver config
     */
    @Inject
    public InsiteConfig(DriverConfig driverConfig) {

        super(driverConfig.get(DriverConfig.ENV));
        load("insite.properties");
    }

}
