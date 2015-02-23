/*
 * ApiModule.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.consumerapi;

import com.ecofactor.qa.automation.consumerapi.data.ApiDataProvider;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

/**
 * The Class ApiModule.
 */
public class ApiModule extends AbstractModule {

	/**
	 * Configure.
	 * @see com.google.inject.AbstractModule#configure()
	 */
	@Override
	protected void configure() {
		bind(ApiConfig.class).in(Singleton.class);
		requestStaticInjection(ApiDataProvider.class);
	}
}
