/*
 * MailConfig.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.util.mail;

import com.ecofactor.qa.automation.util.BaseConfig;

/**
 * The Class MailConfig.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class MailConfig extends BaseConfig {

	public static final String SMTP_HOST = "smtp.host";
	public static final String SMTP_PORT = "smtp.port";
	public static final String SMTP_USER = "smtp.user";
	public static final String SMTP_PASSWORD = "smtp.password";
	public static final String RECEPIENTS = "recepients";
	public static final String SEND_MAIL = "send.mail";
	public static final String DATE_FORMAT = "date.format";
	public static final String SUBJECT = "subject";
	public static final String DRIVER = "selenium.driver";
	public static final String USER_STATUS = "userStatus";
	public static final String ALGO_STATUS = "algoStatus";
	public static final String USERS_USED = "usersUsed";
	public static final String ALGO_USERS_USED="algoUsersUsed";
	public static final String MOBILE = "mobile";
	public static final String USERS_USED_RECEPIENTS = "usersUsedRecepients";
	public static final String ALGO_AVAILABLE_USERS = "algoAvailableActiveUsers";
	public static final String DELETED_USERS = "usersToBeDeleted";
	public static final String TEST_LINK = "testlink";
	public static final String TEST_LINK_RECEPIENTS = "testlinkrecepients";
	public static final String SLAVE = "slave";
	public static final String DEVICE = "device";
	public static final String EF_RECEPIENTS = "ef_recepients";
	public static final String AXIM_RECEPIENTS = "axim_recepients";


	/**
     * Instantiates a new mail config.
     */
	public MailConfig() {

		load("mail.properties");
	}
}
