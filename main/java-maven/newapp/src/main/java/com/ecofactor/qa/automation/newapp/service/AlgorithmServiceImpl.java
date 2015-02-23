/*
 * AlgorithmServiceImpl.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.newapp.service;

import java.util.List;

import org.testng.Assert;

import com.ecofactor.common.pojo.ThermostatAlgorithmController;
import com.ecofactor.qa.automation.dao.util.DataUtil;
import com.ecofactor.qa.automation.qtc.page.QTCLogin;

import com.google.inject.Inject;

/**
 * The Class AlgorithmServiceImpl.
 * 
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class AlgorithmServiceImpl implements AlgorithmService {

	@Inject
	private DataService dataService;
	@Inject
	protected QTCLogin qtcLogin;

	/**
	 * Creates the algo control.
	 * 
	 * @param thermostatId
	 *            the thermostat id
	 * @param algoId
	 *            the algo id
	 */
	public void createAlgoControl(Integer thermostatId, Integer algoId) {

		dataService.updateAlgoControlToInactive(thermostatId, 190);
		dataService.updateAlgoControlToInactive(thermostatId, 191);
		List<ThermostatAlgorithmController> algoControlList = dataService
				.generateAlgoControlList(thermostatId, algoId);
		Assert.assertEquals(true, algoControlList != null);
		DataUtil.printAlgoControlTableGrid(algoControlList);
		dataService.saveThermostatAlgoController(algoControlList);
	}
}
