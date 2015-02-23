/*
 * MobileTestDataContentBuilder.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.newapp.util.mail;

import java.util.List;
import java.util.Map;

import com.ecofactor.qa.automation.newapp.model.UserData;
import com.ecofactor.qa.automation.util.mail.TestType;
import com.google.inject.ImplementedBy;

/**
 * The Interface MobileTestDataContentBuilder.
 */
@ImplementedBy(value = MobileTestDataHtmlContentBuilder.class)
public interface MobileTestDataContentBuilder {

	/**
	 * Mobile user content.
	 * @param type the type
	 * @param userData the user data
	 * @param env the env
	 * @return the string
	 */
	String mobileUserContent(TestType type, List<UserData> userData, String env);

	/**
	 * Slave users.
	 * @param type the type
	 * @param map the map
	 * @param env the env
	 * @return the string
	 */
	String slaveUsers(TestType type, Map<String, List<String>> map, String env);
}
