/*
 * TaskPageImpl.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.qtc.page;

import static com.ecofactor.qa.automation.qtc.config.TaskConfig.*;
import static com.ecofactor.qa.automation.util.PageUtil.*;
import static com.ecofactor.qa.automation.util.WaitUtil.*;
import static org.testng.Assert.*;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.ecofactor.qa.automation.dao.ThermostatAlgorithmDao;
import com.ecofactor.qa.automation.qtc.config.TaskConfig;
import com.ecofactor.qa.automation.util.DriverConfig;
import com.ecofactor.qa.automation.util.PageAction;
import com.google.inject.Inject;

/**
 * The Class TaskPageImpl.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class TaskPageImpl extends PageAction implements TaskPage {

    @Inject
    protected TaskConfig taskConfig;

    @Inject
    private ThermostatAlgorithmDao thAlgorithmDao;
    @Inject
    private QTCLogin qtcLogin;

    /**
     * Click task.
     * @see com.ecofactor.qa.automation.qtc.page.TaskPage#clickTask()
     */
    @Override
    public void clickTask() {

        DriverConfig.setLogString("Click Task Link", true);
        tinyWait();
        WebElement taskElement = retrieveElementByLinkText(DriverConfig.getDriver(), taskConfig.get(TASK_LINK), SHORT_TIMEOUT);
        taskElement.click();

    }

    /**
     * Verify task page.
     */
    public void verifyTaskPage() {

        DriverConfig.setLogString("Verify Task Page", true);
        mediumWait();
        WebElement taskElement = retrieveElementByLinkText(DriverConfig.getDriver(), taskConfig.get(TASK_LINK), SHORT_TIMEOUT);
        assertTrue(taskElement!=null, "Login Failed or admin page not loaded");

    }

    /**
     * Select algo subscribe.
     * @see com.ecofactor.qa.automation.qtc.page.TaskPage#selectAlgoSubscribe()
     */
    @Override
    public void selectAlgoSubscribe() {

        DriverConfig.setLogString("Select Algo Subscribe", true);
        WebElement algoSubscribe = retrieveElementByAttributeValue(DriverConfig.getDriver(), TAG_INPUT, ATTR_VALUE, taskConfig.get(ALGO_SUBSCRIBE), SHORT_TIMEOUT);
        algoSubscribe.click();
    }

    /**
     * Select csv input.
     * @see com.ecofactor.qa.automation.qtc.page.TaskPage#selectCsvInput()
     */
    @Override
    public void selectCsvInput() {

        DriverConfig.setLogString("Select csv input", true);
        WebElement csvInput = retrieveElementByAttributeValue(DriverConfig.getDriver(), TAG_INPUT, ATTR_VALUE, taskConfig.get(CSV_INPUT), SHORT_TIMEOUT);
        csvInput.click();
    }

    /**
     * Click next.
     * @return the web element
     * @see com.ecofactor.qa.automation.qtc.page.TaskPage#clickNext()
     */
    @Override
    public void clickNext() {

        DriverConfig.setLogString("Click Next", true);

        smallWait();
        WebElement nextButton = retrieveElementByAttributeValue(DriverConfig.getDriver(), TAG_INPUT, ATTR_VALUE, taskConfig.get(NEXT), SHORT_TIMEOUT);
        nextButton.click();
    }

    /**
     * Click next2.
     * @see com.ecofactor.qa.automation.qtc.page.TaskPage#clickNext2()
     */
    public void clickNext2() {

        DriverConfig.setLogString("Click Next", true);

        smallWait();

        WebElement taskContainer = DriverConfig.getDriver().findElement(By.id("taskConfigContainer"));

        List<WebElement> omput = taskContainer.findElements(By.tagName("input"));
        for (WebElement webElement : omput) {
            if (webElement.getAttribute(ATTR_VALUE).equalsIgnoreCase(taskConfig.get(NEXT))) {
                webElement.click();
                break;
            }
        }
    }

    /**
     * Select action.
     * @param actionName the action name
     * @see com.ecofactor.qa.automation.qtc.page.TaskPage#selectAction(java.lang.String)
     */
    public void selectAction(String actionName) {

        DriverConfig.setLogString("Select Action :" + actionName, true);

        tinyWait();
        Select selectAction = new Select(DriverConfig.getDriver().findElement(By.name(taskConfig.get(ACTION))));
        selectAction.selectByVisibleText(actionName);
    }

    /**
     * Select algo.
     * @param algoName the algo name
     * @see com.ecofactor.qa.automation.qtc.page.TaskPage#selectAlgo(java.lang.String)
     */
    public void selectAlgo(String algoName) {

        DriverConfig.setLogString("Select algoname : " + algoName, true);

        Select selectAlgo = new Select(DriverConfig.getDriver().findElement(By.name(taskConfig.get(ALGO))));
        selectAlgo.selectByVisibleText(algoName);
    }

    /**
     * Query csv input.
     * @param thermostatId the thermostat id
     * @see com.ecofactor.qa.automation.qtc.page.TaskPage#queryCsvInput(java.lang.String)
     */
    public void queryCsvInput(String thermostatId) {

        DriverConfig.setLogString("query CSV Input", true);

        DriverConfig.getDriver().findElement(By.id(taskConfig.get(QUERY_CSV_INPUT))).sendKeys("thermostat_id\n" + thermostatId);
    }

    /**
     * Click verify csv.
     * @see com.ecofactor.qa.automation.qtc.page.TaskPage#clickVerifyCsv()
     */
    public void clickVerifyCsv() {

        DriverConfig.setLogString("Click verify csv", true);

        WebElement verifyButton = retrieveElementByAttributeValue(DriverConfig.getDriver(), TAG_INPUT, ATTR_VALUE, taskConfig.get(VERIFY), SHORT_TIMEOUT);
        verifyButton.click();

        tinyWait();
        DriverConfig.getDriver().switchTo().alert().accept();
    }

    /**
     * Start task.
     * @see com.ecofactor.qa.automation.qtc.page.TaskPage#startTask()
     */
    public void startTask() {

        DriverConfig.setLogString("Click Start button", true);

        WebElement startTaskBtn = retrieveElementByAttributeValue(DriverConfig.getDriver(), TAG_INPUT, ATTR_VALUE, taskConfig.get(START_TASK), SHORT_TIMEOUT);
        startTaskBtn.click();

        tinyWait();
        DriverConfig.getDriver().switchTo().alert().accept();
    }

    /**
     * Verify in table.
     * @param thermostatId the thermostat id
     * @param algoName the algo name
     * @see com.ecofactor.qa.automation.qtc.page.TaskPage#verifyInTable(java.lang.Integer,
     *      java.lang.String)
     */
    public void verifyInTable(Integer thermostatId, String algoName) {

        DriverConfig.setLogString("Verify records in table for thermostat : " + thermostatId, true);
        smallWait();
        List<String> thAlgoList = thAlgorithmDao.listBySubscribedAlgo(thermostatId);
        assertTrue(thAlgoList.contains(algoName));
        DriverConfig.setLogString("Algorithm is completed successfully", true);
    }

    /**
     * Verify not in table.
     * @param thermostatId the thermostat id
     * @param algoName the algo name
     * @see com.ecofactor.qa.automation.qtc.page.TaskPage#verifyNotInTable(java.lang.Integer,
     *      java.lang.String)
     */
    public void verifyNotInTable(Integer thermostatId, String algoName) {

        DriverConfig.setLogString("Verify records not in table for thermostat : " + thermostatId, true);

        smallWait();
        List<String> thAlgoList = thAlgorithmDao.listBySubscribedAlgo(thermostatId);
        assertTrue(!thAlgoList.contains(algoName));
    }

    /**
     * @param thermostatId
     * @param algoName
     * @return
     * @see com.ecofactor.qa.automation.qtc.page.TaskPage#isAlgorithmSubscribed(java.lang.Integer,
     *      java.lang.String)
     */
    public boolean isAlgorithmSubscribed(Integer thermostatId, String algoName) {

        DriverConfig.setLogString("Check algorithm " + algoName + " is subscribed", true);
        smallWait();
        List<String> thAlgoList = thAlgorithmDao.listBySubscribedAlgo(thermostatId);
        return thAlgoList.contains(algoName);

    }

    /**
     * Load page.
     * @see com.ecofactor.qa.automation.qtc.page.QTCPage#loadPage()
     */
    @Override
    public void loadPage() {

    }

}
