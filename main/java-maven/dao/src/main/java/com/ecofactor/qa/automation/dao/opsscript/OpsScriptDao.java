/*
 * OpsScriptDao.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.dao.opsscript;

/**
 * The Interface OpsScriptDao.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public interface OpsScriptDao {

    /**
     * Algo subscription count.
     * @param algoId the algo id
     * @return the integer
     */
    public Integer algoSubscriptionCount(String[] algoId);

    /**
     * Job count of the day.
     * @return the integer
     */
    public Integer jobCountOfTheDay();

    /**
     * Comcast algorithm count.
     * @param algoId the algo id
     * @param ecpCoreId the ecp core id
     * @return the integer
     */
    public Integer comcastAlgorithmCount(String[] algoId, Integer ecpCoreId);

    /**
     * Comcast thermostat job count.
     * @param ecpCoreId the ecp core id
     * @return the integer
     */
    public Integer comcastThermostatJobCount(Integer ecpCoreId);

    /**
     * Non comcast algorithm count.
     * @param algoId the algo id
     * @param ecpCoreId the ecp core id
     * @return the integer
     */
    public Integer nonComcastAlgorithmCount(String[] algoId, Integer ecpCoreId);

    /**
     * Non comcast thermostat job count.
     * @param algoId the algo id
     * @param ecpCoreId the ecp core id
     * @return the integer
     */
    public Integer nonComcastThermostatJobCount(Integer ecpCoreId);


}
