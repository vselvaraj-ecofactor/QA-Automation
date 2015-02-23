/*
 * UserTestDaoImpl.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.dao;

import static com.ecofactor.qa.automation.dao.config.DaoConfig.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.ecofactor.qa.automation.dao.config.DaoConfig;
import com.google.inject.Inject;

/**
 * The Class UserTestDaoImpl.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class UserTestDaoImpl implements UserTestDao {

    @Inject
    private DaoConfig daoConfig;
    private EntityManagerFactory ecoFactor;
    private EntityManagerFactory ecoFactorEfts;

    /**
     * Gets the entity manager.
     * @return the entity manager
     */
    public EntityManager getEntityManager() {

        if(ecoFactor == null) {
            Map<String, Object> ecofactorConfig = new HashMap<String, Object>();
            ecofactorConfig.put("hibernate.dialect", daoConfig.get(DIALECT));
            ecofactorConfig.put("hibernate.connection.driver_class", daoConfig.get(DRIVER));
            ecofactorConfig.put("hibernate.connection.username", daoConfig.get(USER));
            ecofactorConfig.put("hibernate.connection.password", daoConfig.get(PASSWORD));
            ecofactorConfig.put("hibernate.connection.url", daoConfig.get(URL));
            ecofactorConfig.put("hibernate.show_sql", daoConfig.get(SHOW_SQL));
            ecofactorConfig.put("hibernate.format_sql", daoConfig.get(FORMAT_SQL));
            ecoFactor = Persistence.createEntityManagerFactory("ecofactorPU", ecofactorConfig);
        }

        return ecoFactor.createEntityManager();
    }


    /**
     * Gets the efts entity manager.
     * @return the efts entity manager
     */
    public EntityManager getEftsEntityManager()
    {
        Map<String, Object> ecofactorConfig = new HashMap<String, Object>();
        ecofactorConfig.put("hibernate.dialect", daoConfig.get(DIALECT));
        ecofactorConfig.put("hibernate.connection.driver_class", daoConfig.get(DRIVER));
        ecofactorConfig.put("hibernate.connection.username", daoConfig.get(RANGE_DATA_USER));
        ecofactorConfig.put("hibernate.connection.password", daoConfig.get(RANGE_DATA_PASSWORD));
        ecofactorConfig.put("hibernate.connection.url", daoConfig.get(RANGE_DATA_URL));
        ecofactorConfig.put("hibernate.show_sql", daoConfig.get(SHOW_SQL));
        ecofactorConfig.put("hibernate.format_sql", daoConfig.get(FORMAT_SQL));
        ecoFactorEfts = Persistence.createEntityManagerFactory("ecofactorPU", ecofactorConfig);

        return ecoFactorEfts.createEntityManager();
    }

    /**
     * Gets the active thermostats.
     * @return the active thermostats
     * @see com.ecofactor.qa.automation.dao.UserTestDao#getActiveThermostats()
     */
    @SuppressWarnings("unchecked")
    @Override
    public Map<String, String> getActiveThermostats() {

        EntityManager em = getEftsEntityManager();
        String nativeQuery =
                "  select distinct(thermostat_id)"
                  + "      from ef_thermostat_event "
                  + "      where event_sys_time > date_sub(now(), interval 1 hour) and event_sys_time < now() order by thermostat_event_id desc";
        Query query = em.createNativeQuery(nativeQuery);
        List<Object[]> results = query.getResultList();
        Map<String, String> userThermostatMap = new HashMap<String, String>();
        for(Object result : results) {
        	EntityManager emObj = getEntityManager();
            String nativeQuery1 =
            "select t.thermostat_id, u.user_name"
			+ " from ef_thermostat t, ef_location l, ef_user u"
			+ " where t.location_id = l.location_id and l.user_id = u.user_id"
			+ " and t.thermostat_id =" + result + "";
            Query query1 = emObj.createNativeQuery(nativeQuery1);
            List<Object[]> results1 = query1.getResultList();
            for(Object[] resultObj : results1) {
            	userThermostatMap.put(resultObj[0].toString(), resultObj[1].toString());
            }
        }
        return userThermostatMap;
    }
}
