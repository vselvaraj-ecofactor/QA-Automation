/*
 * TestLinkContentBuilder.java
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
 * The Interface Test Link ContentBuilder.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public interface TestLinkContentBuilder {

	/**
	 * Test link content.
	 * @param type the type
	 * @param testLinkData the test link data
	 * @return the string
	 */
	public String testLinkContent(String module, List<TestLinkData> testLinkData);
}
