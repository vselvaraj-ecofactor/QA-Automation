/*
 * ContentBuilder.java
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
public interface AlgoContentBuilder {

	/**
	 * Gets the content.
	 * @param type the type
	 * @param userData the user data
	 * @param env the env
	 * @return the content
	 */
	public String algoGetContent(TestType type, List<AlgoUserData> userData, String env);
}
