/*
 * AdminLoginPage.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.newapp.admin.page;

import com.ecofactor.qa.automation.newapp.admin.page.impl.AdminLoginPageImpl;
import com.google.inject.ImplementedBy;

/**
 * The Interface AdminLoginPage.
 */
@ImplementedBy(value = AdminLoginPageImpl.class)
public interface AdminLoginPage {

	/**
	 * Login.
	 */
	void login();

	/**
	 * Checks if is logged in.
	 * @return true, if is logged in
	 */
	boolean isLoggedIn();

	/**
	 * Sets the logged in.
	 * @param loggedIn the new logged in
	 */
	void setLoggedIn(boolean loggedIn);
}
