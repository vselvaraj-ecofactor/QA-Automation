/*
 * DeleteUserContentBuilder.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.util.mail;

import java.util.List;

/**
 * The Interface ContentBuilder.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public interface DeleteUserContentBuilder {

	/**
	 * Delete user content.
	 * @param type the type
	 * @param userData the user data
	 * @param env the env
	 * @return the string
	 */
	public String deleteUserContent(TestType type, List<UnusedUserData> userData, String env);
}
