/*
 * RoleConfig.java
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
 * The Class RoleConfig.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class RoleConfig extends BaseConfig {
    public final static String ADMIN_TAB = "adminTabValue";
    public final static String ROLE_MANAGEMENT_VAL = "roleManagementValue";
    public final static String CREATE_NEW_ROLE_VAL = "createNewRoleValue";

    public final static String ROLE_DISPLAY_NAME = "roleDisplayNameField";
    public final static String ROLE_DESCRIPTION = "roleDescriptionField";
    public final static String ADMIN_AVAILABLE_ROLES = "adminAvailableRolesField";
    public final static String UNASSIGNED_PERMISSIONS = "unAssignedPermissionField";

    public final static String ROLE_NAME = "roleNameField";
    public final static String FIND_BUTTON = "findButtonValue";
    public final static String SEARCH_RESULT_CLASS = "searchResultClass";
    public final static String PARTNER_TYPE_ID = "partnerTypeField";
    public final static String PARTNER_TYPE = "partnerType";

    public final static String PARTNER_SYSTEM_ADMIN = "partnerSystemAdmin";
    public final static String PARTNER_CONSERVATION = "partnerConservation";
    public final static String PARTNER_INSTALLATION = "partnerInstallation";
    public final static String PARTNER_SERVICE = "partnerService";
    public final static String PARTNER_UTILITY = "partnerUtility";
    public final static String PARTNER_CUSTOMER = "partnerCustomer";
    public final static String USER_TABLE = "user";

    /**
     * Instantiates a new role config.
     * @param driverConfig the driver config
     */
    @Inject
    public RoleConfig(DriverConfig driverConfig) {

        super(driverConfig.get(DriverConfig.ENV));
        load("roleManagement.properties");
    }
}
