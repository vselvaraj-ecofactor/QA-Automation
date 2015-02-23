/*
 * ThermostatAlgoControlDaoImpl.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.dao.dr;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ecofactor.common.pojo.ThermostatAlgorithmController;
import com.ecofactor.qa.automation.dao.BaseDaoImpl;

/**
 * ThermostatAlgoControlDaoImpl implements the ThermostatAlgoControlDao interface.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class ThermostatAlgoControlDaoImpl extends BaseDaoImpl<ThermostatAlgorithmController>
        implements ThermostatAlgoControlDao {

    /**
     * Find active event.
     * @param thermostatId the thermostat id
     * @return the thermostat algorithm controller
     * @see com.ecofactor.qa.automation.dao.ThermostatAlgoControlDao#findActiveEvent(java.lang.Integer)
     */
    public ThermostatAlgorithmController findActiveEvent(Integer thermostatId) {

        ThermostatAlgorithmController thermostatAlgoCtrl = null;
        String query = "SELECT tac FROM ThermostatAlgorithmController tac WHERE tac.thermostatId = :thermostatId and tac.status = 'ACTIVE' order by tac.id desc";
        Map<String, Object> paramVals = new HashMap<String, Object>();
        paramVals.put("thermostatId", thermostatId);
        thermostatAlgoCtrl = findByQuery(query, paramVals);
        return thermostatAlgoCtrl;
    }

    /**
     * Find active event.
     * @param thermostatId the thermostat id
     * @param algoId the algo id
     * @return the thermostat algorithm controller
     * @see com.ecofactor.qa.automation.dao.ThermostatAlgoControlDao#findActiveEvent(java.lang.Integer,
     *      java.lang.Integer)
     */
    public ThermostatAlgorithmController findActiveEvent(Integer thermostatId, Integer algoId) {

        ThermostatAlgorithmController thermostatAlgoCtrl = null;
        String query = "SELECT tac FROM ThermostatAlgorithmController tac WHERE tac.thermostatId = :thermostatId AND tac.status = 'ACTIVE' and tac.algorithm.id =:algoId";
        Map<String, Object> paramVals = new HashMap<String, Object>();
        paramVals.put("thermostatId", thermostatId);
        paramVals.put("algoId", algoId);
        thermostatAlgoCtrl = findByQuery(query, paramVals);
        return thermostatAlgoCtrl;
    }

    /**
     * @param thermostatId
     * @param algoId
     * @param baseTemp
     * @return
     * @see com.ecofactor.qa.automation.dao.ThermostatAlgoControlDao#findZeroPhaseActiveEvent(java.lang.Integer,
     *      java.lang.Integer, java.lang.Double)
     */
    public ThermostatAlgorithmController findZeroPhaseActiveEvent(Integer thermostatId,
            Integer algoId, Double baseTemp) {

        ThermostatAlgorithmController thermostatAlgoCtrl = null;
        String query = "SELECT tac FROM ThermostatAlgorithmController tac WHERE tac.thermostatId = :thermostatId AND tac.status = 'ACTIVE' and tac.algorithm.id =:algoId and tac.phase = 0 and tac.phase0Spt = :baseTemp";
        Map<String, Object> paramVals = new HashMap<String, Object>();
        paramVals.put("thermostatId", thermostatId);
        paramVals.put("algoId", algoId);
        paramVals.put("baseTemp", baseTemp);
        thermostatAlgoCtrl = findByQuery(query, paramVals);
        return thermostatAlgoCtrl;
    }

    /**
     * @param thermostatId
     * @param algoId
     * @param baseTemp
     * @return
     * @see com.ecofactor.qa.automation.dao.ThermostatAlgoControlDao#findFirstPhaseActiveEvent(java.lang.Integer,
     *      java.lang.Integer, java.lang.Double)
     */
    public ThermostatAlgorithmController findFirstPhaseActiveEvent(Integer thermostatId,
            Integer algoId, Double baseTemp) {

        ThermostatAlgorithmController thermostatAlgoCtrl = null;
        String query = "SELECT tac FROM ThermostatAlgorithmController tac WHERE tac.thermostatId = :thermostatId AND tac.status = 'ACTIVE' and tac.algorithm.id =:algoId and tac.phase = 1 and tac.prevSpt = :baseTemp";
        Map<String, Object> paramVals = new HashMap<String, Object>();
        paramVals.put("thermostatId", thermostatId);
        paramVals.put("algoId", algoId);
        paramVals.put("baseTemp", baseTemp);
        thermostatAlgoCtrl = findByQuery(query, paramVals);
        return thermostatAlgoCtrl;
    }

    /**
     * @param thermostatId
     * @param algoId
     * @return
     * @see com.ecofactor.qa.automation.dao.ThermostatAlgoControlDao#listAlgoControl(java.lang.Integer,
     *      int)
     */
    @Override
    public List<ThermostatAlgorithmController> listActiveAlgoControl(Integer thermostatId,
            int algoId) {

        List<ThermostatAlgorithmController> thermostatAlgoCtrl = null;
        String query = "SELECT tac FROM ThermostatAlgorithmController tac WHERE tac.thermostatId = :thermostatId AND tac.status = 'ACTIVE' and tac.algorithm.id =:algoId order by tac.id asc";
        Map<String, Object> paramVals = new HashMap<String, Object>();
        paramVals.put("thermostatId", thermostatId);
        paramVals.put("algoId", algoId);
        thermostatAlgoCtrl = listByQuery(query, paramVals);
        return thermostatAlgoCtrl;
    }

    /**
     * @param thermostatAlgorithmController
     * @return
     * @see com.ecofactor.qa.automation.dao.ThermostatAlgoControlDao#saveThermostatAlgorithmController(java.util.List)
     */
    @Override
    public Boolean saveThermostatAlgorithmController(
            List<ThermostatAlgorithmController> thermostatAlgorithmController) {

        Boolean entitySaved = false;

        for (ThermostatAlgorithmController TstatAlgoController : thermostatAlgorithmController) {
            entitySaved = saveEntity(TstatAlgoController);
        }
        return entitySaved;
    }

    /**
     * @param thermostatAlgorithmController
     * @return
     * @see com.ecofactor.qa.automation.dao.ThermostatAlgoControlDao#updateThermostatAlgorithmController(java.util.List)
     */
    @Override
    public Boolean updateThermostatAlgorithmControllerList(
            List<ThermostatAlgorithmController> thermostatAlgorithmController) {

        Boolean entitySaved = false;

        for (ThermostatAlgorithmController TstatAlgoController : thermostatAlgorithmController) {
            entitySaved = updateEntity(TstatAlgoController);
        }
        return entitySaved;
    }

    /**
     * Update thermostat algorithm controller.
     * @param thermostatAlgorithmController the thermostat algorithm controller
     * @return the boolean
     * @see com.ecofactor.qa.automation.dao.ThermostatAlgoControlDao#updateThermostatAlgorithmController(com.ecofactor.common.pojo.ThermostatAlgorithmController)
     */
    @Override
    public Boolean updateThermostatAlgorithmController(
            ThermostatAlgorithmController thermostatAlgorithmController) {

        Boolean entitySaved = updateEntity(thermostatAlgorithmController);
        return entitySaved;
    }

    /**
     * Find spo start.
     * @param thermostatId the thermostat id
     * @param algoId the algo id
     * @return the thermostat algorithm controller
     * @see com.ecofactor.qa.automation.dao.ThermostatAlgoControlDao#findSPOStart(java.lang.Integer,
     *      java.lang.Integer)
     */
    public ThermostatAlgorithmController findSPOStart(Integer thermostatId, Integer algoId) {

        String query = "SELECT tac FROM ThermostatAlgorithmController tac WHERE tac.thermostatId = :thermostatId and tac.status = 'ACTIVE' and tac.algorithm.id =:algoId order by tac.id asc";
        Map<String, Object> paramVals = new HashMap<String, Object>();
        paramVals.put("thermostatId", thermostatId);
        paramVals.put("algoId", algoId);
        List<ThermostatAlgorithmController> thermostatAlgoCtrl = listQueryByLimit(query, paramVals,
                1);
        return thermostatAlgoCtrl.get(0);
    }

    @Override
    public List<ThermostatAlgorithmController> listAlgoControlForAlgorithm(Integer thermostatId,
            Integer algoId) {

        String query = "SELECT tac FROM ThermostatAlgorithmController tac WHERE tac.thermostatId = :thermostatId and tac.algorithm.id =:algoId order by tac.id asc";
        Map<String, Object> paramVals = new HashMap<String, Object>();
        paramVals.put("thermostatId", thermostatId);
        paramVals.put("algoId", algoId);
        List<ThermostatAlgorithmController> thermostatAlgoCtrl = listByQuery(query, paramVals);
        return thermostatAlgoCtrl;
    }

    /**
     * update next phase time based on thermostat id.
     * @param thermostatId the thermostat id.
     * @see com.ecofactor.qa.automation.dao.dr.ThermostatAlgoControlDao#updateNextPhaseTime(java.lang.Integer)
     */
    @Override
    public void updateNextPhaseTime(Integer thermostatId) {

        String query = "update ThermostatAlgorithmController set next_phase_time = now() where  thermostatId = :thermostatId";
        Map<String, Object> paramVals = new HashMap<String, Object>();
        paramVals.put("thermostatId", thermostatId);
        findByQuery(query, paramVals);
    }
}
