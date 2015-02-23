/*
 * MobileUserDataProvider.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.newapp.data;

import java.util.List;

import org.testng.annotations.DataProvider;

import com.ecofactor.qa.automation.newapp.MobileUserTestDataConfig;
import com.google.inject.Inject;

/**
 * The Class MobileUserDataProvider.
 */
public class MobileUserDataProvider {

	@Inject
	private static MobileUserTestDataConfig mobileUserTestDataConfig;

	/**
	 * Mobile users.
	 * @return the object[][]
	 */
	@DataProvider(name = "mobileUsers", parallel = true)
	public static Object[][] mobileUsers() {

		final List<String> userIds = mobileUserTestDataConfig.getConfigProperties();
		final Object[][] userData = new Object[userIds.size()][2];
		int index = 0;
		for (Object[] objects : userData) {

			objects[0] = userIds.get(index);
			objects[1] = mobileUserTestDataConfig.get(userIds.get(index));
			++index;
		}
		return userData;
	}
}
