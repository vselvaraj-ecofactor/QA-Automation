/*
 * OpsScriptDaoImpl.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.dao.opsscript;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;

import com.ecofactor.qa.automation.dao.BaseDaoImpl;
import com.ecofactor.qa.automation.util.DriverConfig;

/**
 * The Class OpsScriptDaoImpl.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
@SuppressWarnings("rawtypes")
public class OpsScriptDaoImpl extends BaseDaoImpl implements OpsScriptDao {

    /**
     * Algo subscription count.
     * @param algoId the algo id
     * @return the integer
     * @see com.ecofactor.qa.automation.dao.opsscript.OpsScriptDao#algoSubscriptionCount(java.lang.Integer[])
     */
    @SuppressWarnings("unchecked")
    @Override
    public Integer algoSubscriptionCount(String[] algoId) {

        String algoStringValue = loopAlgoIdToString(algoId);
        String query = "SELECT COUNT(DISTINCT(thermostat_id)) FROM ef_thermostat_algorithm WHERE algorithm_id IN (" + algoStringValue
                + ") AND thermostat_algorithm_status = 'ACTIVE';";
        DriverConfig.setLogString(query, true);
        Map<String, Object> paramVals = new HashMap<String, Object>();

        Integer count = countByNativeQuery(query, paramVals);
        return count;
    }

    /**
     * Job count of the day.
     * @return the integer
     * @see com.ecofactor.qa.automation.dao.opsscript.OpsScriptDao#jobCountOfTheDay()
     */
    @SuppressWarnings("unchecked")
    @Override
    public Integer jobCountOfTheDay() {

        String query = "SELECT COUNT(*) FROM ef_thermostat_job WHERE end_date >= DATE(NOW() + INTERVAL 1 DAY) AND end_date < DATE(NOW() + INTERVAL 2 DAY)";
        Map<String, Object> paramVals = new HashMap<String, Object>();
        DriverConfig.setLogString(query, true);
        Integer jobCount = countByNativeQuery(query, paramVals);
        return jobCount;
    }

    /**
     * Comcast algorithm count.
     * @param algoId the algo id
     * @param ecpCoreId the ecp core id
     * @return the integer
     * @see com.ecofactor.qa.automation.dao.opsscript.OpsScriptDao#comcastAlgorithmCount(java.lang.String[],
     *      java.lang.Integer)
     */
    public Integer comcastAlgorithmCount(String[] algoId, Integer ecpCoreId) {

        String algoStringValue = loopAlgoIdToString(algoId);
        String query = "SELECT COUNT(DISTINCT(thermostat_id)) FROM ef_thermostat_algorithm WHERE algorithm_id IN (" + algoStringValue + ") "
                + "AND thermostat_algorithm_status = 'ACTIVE' AND thermostat_id " + "in (select thermostat_id from ef_thermostat where thermostat_status = 'ACTIVE' "
                + "and location_id in (select location_id from ef_ecp_core_location where ecp_core_id in (" + ecpCoreId + ")))";
        DriverConfig.setLogString(query, true);
        Map<String, Object> paramVals = new HashMap<String, Object>();
        @SuppressWarnings("unchecked")
        Integer comcastCount = countByNativeQuery(query, paramVals);
        return comcastCount;
    }

    /**
     * Comcast thermostat job count.
     * @param ecpCoreId the ecp core id
     * @return the integer
     * @see com.ecofactor.qa.automation.dao.opsscript.OpsScriptDao#comcastThermostatJobCount(java.lang.Integer)
     */
    public Integer comcastThermostatJobCount(Integer ecpCoreId) {

        String query = "SELECT COUNT(DISTINCT(thermostat_id)) FROM ef_thermostat_job WHERE end_date >= DATE(NOW() + INTERVAL 1 DAY) "
                + "AND end_date < DATE(NOW() + INTERVAL 2 DAY) AND thermostat_id " + "in (select thermostat_id from ef_thermostat where thermostat_status = 'ACTIVE' "
                + "and location_id in (select location_id from ef_ecp_core_location " + "where ecp_core_id in (" + ecpCoreId + ")));";
        DriverConfig.setLogString(query, true);
        Map<String, Object> paramVals = new HashMap<String, Object>();
        @SuppressWarnings("unchecked")
        Integer comcastCount = countByNativeQuery(query, paramVals);
        return comcastCount;
    }

    /**
     * Non comcast algorithm count.
     * @param algoId the algo id
     * @param ecpCoreId the ecp core id
     * @return the integer
     * @see com.ecofactor.qa.automation.dao.opsscript.OpsScriptDao#nonComcastAlgorithmCount(java.lang.String[],
     *      java.lang.Integer)
     */
    public Integer nonComcastAlgorithmCount(String[] algoId, Integer ecpCoreId) {

        String algoStringValue = loopAlgoIdToString(algoId);
        String query = "SELECT COUNT(DISTINCT(thermostat_id)) FROM ef_thermostat_algorithm WHERE algorithm_id IN (" + algoStringValue + ") "
                + "AND thermostat_algorithm_status = 'ACTIVE' " + "AND thermostat_id in (select thermostat_id from ef_thermostat " + "where thermostat_status = 'ACTIVE' "
                + "and location_id in (select location_id from ef_ecp_core_location where ecp_core_id NOT in (" + ecpCoreId + ")));";
        DriverConfig.setLogString(query, true);
        Map<String, Object> paramVals = new HashMap<String, Object>();
        @SuppressWarnings("unchecked")
        Integer nonComcastCount = countByNativeQuery(query, paramVals);
        return nonComcastCount;
    }

    /**
     * Non comcast thermostat job count.
     * @param ecpCoreId the ecp core id
     * @return the integer
     * @see com.ecofactor.qa.automation.dao.opsscript.OpsScriptDao#nonComcastThermostatJobCount(java.lang.Integer)
     */
    public Integer nonComcastThermostatJobCount(Integer ecpCoreId) {

        String query = "SELECT COUNT(DISTINCT(thermostat_id)) FROM ef_thermostat_job WHERE end_date >= DATE(NOW() + INTERVAL 1 DAY) "
                + "AND end_date < DATE(NOW() + INTERVAL 2 DAY) AND thermostat_id " + "in (select thermostat_id from ef_thermostat where thermostat_status = 'ACTIVE' "
                + "and location_id in (select location_id from ef_ecp_core_location " + "where ecp_core_id NOT in (" + ecpCoreId + ")));";
        DriverConfig.setLogString(query, true);
        Map<String, Object> paramVals = new HashMap<String, Object>();
        @SuppressWarnings("unchecked")
        Integer comcastCount = countByNativeQuery(query, paramVals);
        return comcastCount;
    }

    /**
     * Loop algo id to string.
     * @param algoId the algo id
     * @return the string
     */
    private String loopAlgoIdToString(String[] algoId) {

        String algoStringValue = "";
        if (algoId != null && algoId.length == 1) {
            algoStringValue = algoId[0].toString();
        } else {
            for (int loop = 0; loop < algoId.length; loop++) {

                if (loop != algoId.length - 1) {
                    algoStringValue += algoId[loop].toString() + ",";
                } else {
                    algoStringValue += algoId[loop].toString();
                }
            }
        }

        return algoStringValue;
    }

    /**
     * Gets the entity manager.
     * @return the entity manager
     * @see com.ecofactor.qa.automation.dao.BaseDaoImpl#getEntityManager()
     */
    @Override
    public EntityManager getEntityManager() {

        return getOpsScriptEntityManager();
    }

}
