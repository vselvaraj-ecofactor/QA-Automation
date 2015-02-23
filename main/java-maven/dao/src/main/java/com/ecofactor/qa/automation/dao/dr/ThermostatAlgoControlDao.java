/*
 * ThermostatAlgoControlDao.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.dao.dr;

import java.util.List;

import com.ecofactor.common.pojo.ThermostatAlgorithmController;

/**
 * ThermostatAlgoControlDao provides the interface for ThermostatAlgoControl.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public interface ThermostatAlgoControlDao {

    /**
     * Find active event.
     * @param thermostatId the thermostat id
     * @return the thermostat algorithm controller
     */
    public ThermostatAlgorithmController findActiveEvent(Integer thermostatId);

    /**
     * Find active event.
     * @param thermostatId the thermostat id
     * @param algoId the algo id
     * @return the thermostat algorithm controller
     */
    public ThermostatAlgorithmController findActiveEvent(Integer thermostatId, Integer algoId);

    /**
     * Find zero phase active event.
     * @param thermostatId the thermostat id
     * @param algoId the algo id
     * @param baseTemp the base temp
     * @return the thermostat algorithm controller
     */
    public ThermostatAlgorithmController findZeroPhaseActiveEvent(Integer thermostatId,
            Integer algoId, Double baseTemp);

    /**
     * Find first phase active event.
     * @param thermostatId the thermostat id
     * @param algoId the algo id
     * @param baseTemp the base temp
     * @return the thermostat algorithm controller
     */
    public ThermostatAlgorithmController findFirstPhaseActiveEvent(Integer thermostatId,
            Integer algoId, Double baseTemp);

    /**
     * List algo control.
     * @param thermostatId the thermostat id
     * @param algoId the algo id
     * @return the list
     */
    public List<ThermostatAlgorithmController> listActiveAlgoControl(Integer thermostatId,
            int algoId);

    /**
     * List algo control for algorithm.
     * @param thermostatId the thermostat id
     * @param algoId the algo id
     * @return the list
     */
    public List<ThermostatAlgorithmController> listAlgoControlForAlgorithm(Integer thermostatId,
            Integer algoId);

    /**
     * Save thermostat algorithm controller.
     * @param thermostatAlgorithmController the thermostat algorithm controller
     * @return the boolean
     */
    public Boolean saveThermostatAlgorithmController(
            List<ThermostatAlgorithmController> thermostatAlgorithmController);

    /**
     * Update thermostat algorithm controller.
     * @param thermostatAlgorithmController the thermostat algorithm controller
     * @return the boolean
     */
    public Boolean updateThermostatAlgorithmControllerList(
            List<ThermostatAlgorithmController> thermostatAlgorithmController);

    /**
     * Update thermostat algorithm controller.
     * @param thermostatAlgorithmController the thermostat algorithm controller
     * @return the boolean
     */
    public Boolean updateThermostatAlgorithmController(
            ThermostatAlgorithmController thermostatAlgorithmController);

    /**
     * Find spo start.
     * @param thermostatId the thermostat id
     * @param algoId the algo id
     * @return the thermostat algorithm controller
     */
    public ThermostatAlgorithmController findSPOStart(Integer thermostatId, Integer algoId);

    /**
     * update next phase time based on thermostat id.
     * @param thermostatId the thermostat id.
     */
    public void updateNextPhaseTime(Integer thermostatId);
}
