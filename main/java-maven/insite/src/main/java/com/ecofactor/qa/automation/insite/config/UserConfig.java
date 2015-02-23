/*
 * UserConfig.java
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
 * The Class UserConfig.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class UserConfig extends BaseConfig {
    public final static String ADMIN_TAB = "adminTabValue";
    public final static String CREATE_NEW_USER = "createNewUserValue";
    public final static String USER_TABLE = "user";

    public final static String FIRST_NAME = "firstName";
    public final static String LAST_NAME = "lastName";
    public final static String EMAIL_FIELD = "email";
    public final static String ACCESS_LOGIN = "accessLogin";
    public final static String USER_STATUS = "userStatus";
    public final static String PARTNER_TYPE = "partnerType";
    public final static String PARTNER_ID = "partnerId";
    public final static String STREET_ADDRESS1 = "streetAddress1";
    public final static String STREET_ADDRESS2 = "streetAddress2";
    public final static String CITY = "city";
    public final static String STATE = "state";
    public final static String ZIP = "zip";
    public final static String COUNTRY = "country";
    public final static String MOBILE_NUM = "mobileNum";
    public final static String PHONE_NUM = "phoneNum";
    public final static String FAX_NUM = "faxNum";
    public final static String AVAILABLE_ROLES = "availableRoles";
    public final static String ASSIGNED_ROLES = "assignedRoles";
    public final static String DEFAULT_PARTNER_ID = "defaultPartnerIdValue";
    public final static String USER_SAVED_ALERT_MSG = "userSavedAlertMessage";

    public final static String SEARCH_USER_NAME = "searchUserNameField";
    public final static String FIND_BUTTON = "findButtonValue";

    public final static String ROLE_RESCRIPTION = "roleDescription";
    public final static String AVAILABLE_PAGES = "availablePages";
    public final static String UNASSIGNED_PERMISSION = "unAssignedPermissions";

    public final static String RESET = "reset";
    public final static String CANCEL = "cancel";
    public final static String ADDRESS = "findAddress";
    public final static String FIND_ADDRESS = "clickFindSchedule";
    public final static String USER_INFORMATION_TABLE = "userInformation";
    public final static String ROLES = "role";
    public final static String CONTACT_DETAILS_TAB = "contactDetailTab";
    public final static String ACCOUNT_DETAILS_TAB = "accountDetailsTab";

    /**
     * Instantiates a new user config.
     * @param driverConfig the driver config
     */
    @Inject
    public UserConfig(DriverConfig driverConfig) {

        super(driverConfig.get(DriverConfig.ENV));
        load("userManagement.properties");
    }
}
