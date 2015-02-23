/*
 * TaskPage.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.qtc.page;


/**
 * The Interface QTCLogin.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public interface TaskPage extends QTCPage {


    /**
     * Verify task page.
     */
    public void  verifyTaskPage();
    /**
     * Click task.
     */
    public void clickTask();

    /**
     * Select algo subscribe.
     */
    public void selectAlgoSubscribe();

    /**
     * Select csv input.
     */
    public void selectCsvInput();

    /**
     * Click next.
     * @return the web element
     */
    public void clickNext();

    /**
     * Click next2.
     * @return the web element
     */
    public void clickNext2();

    /**
     * Select action.
     * @param actionName the action name
     */
    public void selectAction(String actionName);

    /**
     * Select algo.
     * @param algoName the algo name
     */
    public void selectAlgo(String algoName);

    /**
     * Query csv input.
     * @param thermostatId the thermostat id
     */
    public void queryCsvInput(String thermostatId);

    /**
     * Click verify csv.
     */
    public void clickVerifyCsv();

    /**
     * Start task.
     */
    public void startTask();

    /**
     * Verify in table.
     * @param thermostatId the thermostat id
     * @param algoName the algo name
     */
    public void verifyInTable(Integer thermostatId, String algoName);

    /**
     * Verify not in table.
     * @param thermostatId the thermostat id
     * @param algoName the algo name
     */
    public void verifyNotInTable(Integer thermostatId, String algoName);

    /**
     * Checks if algorithm subscribed.
     * @param thermostatId the thermostat id
     * @param algoName the algo name
     * @return true, if algorithm subscribed
     */
    public boolean isAlgorithmSubscribed(Integer thermostatId, String algoName);

}
