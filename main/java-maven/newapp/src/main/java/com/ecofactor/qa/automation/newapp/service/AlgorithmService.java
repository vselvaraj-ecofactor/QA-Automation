/*
 * AlgorithmService.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.newapp.service;

/**
 * The Interface AlgorithmService.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public interface AlgorithmService {

   
    /**
     * Creates the algo control.
     * @param thermostatId the thermostat id
     * @param algoId the algo id
     */
    public void createAlgoControl(Integer thermostatId, Integer algoId);
   

}
