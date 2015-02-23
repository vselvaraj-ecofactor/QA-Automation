/*
 * BaseDaoImpl.java
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
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.ecofactor.qa.automation.dao.config.DaoConfig;
import com.ecofactor.qa.automation.util.DriverConfig;
import com.google.inject.Inject;

/**
 * BaseDaoImpl implements the BaseDao interface.
 * @param <T> the
 * @author $Author: vprasannaa $
 * @version $Rev: 32901 $ $Date: 2014-11-28 18:08:02 +0530 (Fri, 28 Nov 2014) $
 */
public abstract class BaseDaoImpl<T> implements BaseDao<T> {

    @Inject
    private DaoConfig daoConfig;
    protected static EntityManagerFactory ecoFactor;
    protected static EntityManagerFactory ecoFactorUpdate;
    protected static EntityManagerFactory reports;
    protected static EntityManagerFactory ecofactorRangeData;
    protected static EntityManagerFactory ecofactorUserUpdate;
    protected static EntityManagerFactory comcastUIReport;
    protected static EntityManagerFactory opsScript;

    /**
     * Inits the ecofactor.
     */
    protected void initEcofactor() {

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

    /**
     * Inits the ecofactor update.
     */
    protected void initEcofactorUpdate() {

        Map<String, Object> ecofactorConfig = new HashMap<String, Object>();
        ecofactorConfig.put("hibernate.dialect", daoConfig.get(DIALECT));
        ecofactorConfig.put("hibernate.connection.driver_class", daoConfig.get(DRIVER));
        ecofactorConfig.put("hibernate.connection.username", daoConfig.get(UPDATE_USER));
        ecofactorConfig.put("hibernate.connection.password", daoConfig.get(UPDATE_PASSWORD));
        ecofactorConfig.put("hibernate.connection.url", daoConfig.get(URL));
        ecofactorConfig.put("hibernate.show_sql", daoConfig.get(SHOW_SQL));
        ecofactorConfig.put("hibernate.format_sql", daoConfig.get(FORMAT_SQL));
        ecoFactorUpdate = Persistence.createEntityManagerFactory("ecofactorPU", ecofactorConfig);
    }

    /**
     * Inits the reports.
     */
    protected void initReports() {

        Map<String, Object> ecofactorConfig = new HashMap<String, Object>();
        ecofactorConfig.put("hibernate.dialect", daoConfig.get(DIALECT));
        ecofactorConfig.put("hibernate.connection.driver_class", daoConfig.get(DRIVER));
        ecofactorConfig.put("hibernate.connection.username", daoConfig.get(USER));
        ecofactorConfig.put("hibernate.connection.password", daoConfig.get(PASSWORD));
        ecofactorConfig.put("hibernate.connection.url", daoConfig.get(REPORTS_URL));
        ecofactorConfig.put("hibernate.show_sql", daoConfig.get(SHOW_SQL));
        ecofactorConfig.put("hibernate.format_sql", daoConfig.get(FORMAT_SQL));
        reports = Persistence.createEntityManagerFactory("ecofactorPU", ecofactorConfig);
    }

    /**
     * Inits the range data.
     */
    protected void initRangeData() {

        Map<String, Object> ecofactorConfig = new HashMap<String, Object>();
        ecofactorConfig.put("hibernate.dialect", daoConfig.get(DIALECT));
        ecofactorConfig.put("hibernate.connection.driver_class", daoConfig.get(DRIVER));
        ecofactorConfig.put("hibernate.connection.username", daoConfig.get(RANGE_DATA_USER));
        ecofactorConfig.put("hibernate.connection.password", daoConfig.get(RANGE_DATA_PASSWORD));
        ecofactorConfig.put("hibernate.connection.url", daoConfig.get(RANGE_DATA_URL));
        ecofactorConfig.put("hibernate.show_sql", daoConfig.get(SHOW_SQL));
        ecofactorConfig.put("hibernate.format_sql", daoConfig.get(FORMAT_SQL));
        ecofactorRangeData = Persistence.createEntityManagerFactory("ecofactorPU", ecofactorConfig);
    }

    /**
     * Inits the ecofactor user update.
     */
    protected void initEcofactorUserUpdate() {

        Map<String, Object> ecofactorConfig = new HashMap<String, Object>();
        ecofactorConfig.put("hibernate.dialect", daoConfig.get(DIALECT));
        ecofactorConfig.put("hibernate.connection.driver_class", daoConfig.get(DRIVER));
        ecofactorConfig.put("hibernate.connection.username", daoConfig.get(EF_USER_USERNAME));
        ecofactorConfig.put("hibernate.connection.password", daoConfig.get(EF_USER_PASSWORD));
        ecofactorConfig.put("hibernate.connection.url", daoConfig.get(EF_USER_URL));
        ecofactorConfig.put("hibernate.show_sql", daoConfig.get(SHOW_SQL));
        ecofactorConfig.put("hibernate.format_sql", daoConfig.get(FORMAT_SQL));
        ecofactorUserUpdate = Persistence
                .createEntityManagerFactory("ecofactorPU", ecofactorConfig);
    }

    /**
     * Inits the comcastuireport.
     */
    protected void initComcastUIReport() {

        Map<String, Object> ecofactorConfig = new HashMap<String, Object>();
        ecofactorConfig.put("hibernate.dialect", daoConfig.get(DIALECT));
        ecofactorConfig.put("hibernate.connection.driver_class", daoConfig.get(DRIVER));
        ecofactorConfig.put("hibernate.connection.username",
                daoConfig.get(COMCAST_UI_REPORT_USERNAME));
        ecofactorConfig.put("hibernate.connection.password",
                daoConfig.get(COMCAST_UI_REPORT_PASSWORD));
        ecofactorConfig.put("hibernate.connection.url", daoConfig.get(COMCAST_UI_REPORT_URL));
        ecofactorConfig.put("hibernate.show_sql", daoConfig.get(SHOW_SQL));
        ecofactorConfig.put("hibernate.format_sql", daoConfig.get(FORMAT_SQL));
        comcastUIReport = Persistence.createEntityManagerFactory("ecofactorPU", ecofactorConfig);
    }

    /**
     * Inits the ops script.
     */
    protected void initOpsScript() {

        Map<String, Object> ecofactorConfig = new HashMap<String, Object>();
        ecofactorConfig.put("hibernate.dialect", daoConfig.get(DIALECT));
        ecofactorConfig.put("hibernate.connection.driver_class", daoConfig.get(DRIVER));
        ecofactorConfig.put("hibernate.connection.username", daoConfig.get(OPS_SCRIPT_USERNAME));
        ecofactorConfig.put("hibernate.connection.password", daoConfig.get(OPS_SCRIPT_PASSWORD));
        ecofactorConfig.put("hibernate.connection.url", daoConfig.get(OPS_SCRIPT_URL));
        ecofactorConfig.put("hibernate.show_sql", daoConfig.get(SHOW_SQL));
        ecofactorConfig.put("hibernate.format_sql", daoConfig.get(FORMAT_SQL));
        opsScript = Persistence.createEntityManagerFactory("ecofactorPU", ecofactorConfig);
    }

    /**
     * Gets the entity manager.
     * @return the entity manager
     */
    public EntityManager getEntityManager() {

        if (ecoFactor == null) {
            initEcofactor();
        }

        return ecoFactor.createEntityManager();
    }

    /**
     * Gets the entity manager.
     * @return the entity manager
     */
    public EntityManager getUpdateEntityManager() {

        if (ecoFactorUpdate == null) {
            initEcofactorUpdate();
        }

        return ecoFactorUpdate.createEntityManager();
    }

    /**
     * Gets the reports entity manager.
     * @return the reports entity manager
     */
    public EntityManager getReportsEntityManager() {

        if (reports == null) {
            initReports();
        }

        return reports.createEntityManager();
    }

    /**
     * Gets the range data entity manager.
     * @return the range data entity manager
     */
    public EntityManager getRangeDataEntityManager() {

        if (ecofactorRangeData == null) {
            initRangeData();
        }

        return ecofactorRangeData.createEntityManager();
    }

    /**
     * Update user entity manager.
     * @return the entity manager
     */
    public EntityManager getUpdateUserEntityManager() {

        if (ecofactorUserUpdate == null) {
            initEcofactorUserUpdate();
        }

        return ecofactorUserUpdate.createEntityManager();
    }

    /**
     * Gets the ops script entity manager.
     * @return the ops script entity manager
     */
    public EntityManager getOpsScriptEntityManager() {

        if (opsScript == null) {
            initOpsScript();
        }

        return opsScript.createEntityManager();
    }

    public EntityManager getComcastUIReportEntityManager() {

        if (comcastUIReport == null) {
            initComcastUIReport();
        }

        return comcastUIReport.createEntityManager();
    }

    /**
     * Find by id.
     * @param clazz the clazz
     * @param id the id
     * @return the t
     * @see com.ecofactor.qa.automation.dao.BaseDao#findById(java.lang.Class, java.lang.Object)
     */
    public T findById(Class<T> clazz, Object id) {

        T entity = getEntityManager().find(clazz, id);
        return entity;
    }

    /**
     * Find by query.
     * @param ql the ql
     * @param paramVals the param vals
     * @return the t
     * @see com.ecofactor.qa.automation.dao.BaseDao#findByQuery(java.lang.String, java.util.Map)
     */
    @SuppressWarnings("unchecked")
    public T findByQuery(String ql, Map<String, Object> paramVals) {

        T record = null;
        try {
            Query query = getEntityManager().createQuery(ql);
            query.setMaxResults(1);
            setParams(query, paramVals);
            record = (T) query.getSingleResult();
        } catch (Exception exc) {
            DriverConfig.setLogString("Error in fetching DAO result - " + exc.getMessage(), true);
        }
        return record;
    }

    /**
     * List query by limit.
     * @param ql the ql
     * @param paramVals the param vals
     * @param noOfRows the no of rows
     * @return the list
     * @see com.ecofactor.qa.automation.dao.BaseDao#listQueryByLimit(java.lang.String,
     *      java.util.Map, int)
     */
    @SuppressWarnings("unchecked")
    public List<T> listQueryByLimit(String ql, Map<String, Object> paramVals, int noOfRows) {

        List<T> record = null;
        try {
            Query query = getEntityManager().createQuery(ql);
            query.setMaxResults(noOfRows);
            setParams(query, paramVals);
            record = (List<T>) query.getResultList();
        } catch (Exception exc) {
            exc.printStackTrace();
            DriverConfig.setLogString("Error in fetching DAO result - " + exc.getMessage(), true);
        }
        return record;
    }

    /**
     * List by query.
     * @param ql the ql
     * @param paramVals the param vals
     * @return the list
     * @see com.ecofactor.qa.automation.dao.BaseDao#listByQuery(java.lang.String, java.util.Map)
     */
    @SuppressWarnings("unchecked")
    public List<T> listByQuery(String ql, Map<String, Object> paramVals) {

        List<T> record = null;
        try {
            Query query = getEntityManager().createQuery(ql);
            setParams(query, paramVals);
            record = (List<T>) query.getResultList();
        } catch (Exception exc) {
            exc.printStackTrace();
            DriverConfig.setLogString("Error in fetching DAO result - " + exc.getMessage(), true);
        }
        return record;
    }

    /**
     * List by report query.
     * @param ql the ql
     * @param paramVals the param vals
     * @return the list
     * @see com.ecofactor.qa.automation.dao.BaseDao#listByReportQuery(java.lang.String,
     *      java.util.Map)
     */
    @SuppressWarnings("unchecked")
    public List<T> listByReportQuery(String ql, Map<String, Object> paramVals) {

        List<T> record = null;
        try {
            Query query = getReportsEntityManager().createQuery(ql);
            setParams(query, paramVals);
            record = (List<T>) query.getResultList();
        } catch (Exception exc) {
            exc.printStackTrace();
            DriverConfig.setLogString("Error in fetching DAO result - " + exc.getMessage(), true);
        }
        return record;
    }

    @SuppressWarnings("unchecked")
    public List<T> listByComcastUIReportQuery(String ql, Map<String, Object> paramVals) {

        List<T> record = null;
        try {
            Query query = getComcastUIReportEntityManager().createQuery(ql);
            setParams(query, paramVals);
            record = (List<T>) query.getResultList();
        } catch (Exception exc) {
            exc.printStackTrace();
            DriverConfig.setLogString("Error in fetching DAO result - " + exc.getMessage(), true);
        }
        return record;
    }

    /**
     * Find by native query.
     * @param nql the nql
     * @param paramVals the param vals
     * @param clazz the clazz
     * @return the t
     * @see com.ecofactor.qa.automation.dao.BaseDao#findByNativeQuery(java.lang.String,
     *      java.util.Map, java.lang.Class)
     */
    @SuppressWarnings("unchecked")
    public T findByNativeQuery(String nql, Map<String, Object> paramVals, Class<T> clazz) {

        T record = null;
        try {
            Query query = getEntityManager().createNativeQuery(nql, clazz);
            query.setMaxResults(1);
            setParams(query, paramVals);
            record = (T) query.getSingleResult();
        } catch (Exception exc) {
            exc.printStackTrace();
            DriverConfig.setLogString("Error in fetching DAO result - " + exc.getMessage(), true);
        }
        return record;
    }

    /**
     * Count by native query.
     * @param nql the nql
     * @param paramVals the param vals
     * @return the int
     * @see com.ecofactor.qa.automation.dao.BaseDao#countByNativeQuery(java.lang.String,
     *      java.util.Map)
     */
    public int countByNativeQuery(String nql, Map<String, Object> paramVals) {

        Query query = getEntityManager().createNativeQuery(nql);
        Integer results = 0;
        String resultString = "";
        if (query.getResultList() != null && query.getResultList().size() > 0) {
            resultString = query.getResultList().get(0).toString();
        }
        results = Integer.valueOf(resultString);
        return results;
    }

    /**
     * List by native query.
     * @param nql the nql
     * @param paramVals the param vals
     * @param clazz the clazz
     * @return the list
     * @see com.ecofactor.qa.automation.dao.BaseDao#listByNativeQuery(java.lang.String,
     *      java.util.Map, java.lang.Class)
     */
    @SuppressWarnings("unchecked")
    public List<T> listByNativeQuery(String nql, Map<String, Object> paramVals, Class<T> clazz) {

        List<T> record = null;
        try {
            Query query = getEntityManager().createNativeQuery(nql, clazz);
            setParams(query, paramVals);
            record = (List<T>) query.getResultList();
        } catch (Exception exc) {
            exc.printStackTrace();
            DriverConfig.setLogString("Error in fetching DAO result - " + exc.getMessage(), true);
        }
        return record;
    }

    /**
     * Find by named query.
     * @param name the name
     * @param paramVals the param vals
     * @return the t
     * @see com.ecofactor.qa.automation.dao.BaseDao#findByNamedQuery(java.lang.String,
     *      java.util.Map)
     */    
    @SuppressWarnings("unchecked")
    public List<T> findByNamedQuery(String name, Map<String, Object> paramVals) {

        List<T> record = null;
        try {
            Query query = getEntityManager().createNamedQuery(name);
            query.setMaxResults(1);
            setParams(query, paramVals);
            record = (List<T>) query.getResultList();
        } catch (Exception exc) {
            exc.printStackTrace();
            DriverConfig.setLogString("Error in fetching DAO result - " + exc.getMessage(), true);
        }
        return record;
    }

    /**
     * List by named query.
     * @param name the name
     * @param paramVals the param vals
     * @return the list
     * @see com.ecofactor.qa.automation.dao.BaseDao#listByNamedQuery(java.lang.String,
     *      java.util.Map)
     */
    @SuppressWarnings("unchecked")
    public List<T> listByNamedQuery(String name, Map<String, Object> paramVals) {

        List<T> record = null;
        try {
            Query query = getEntityManager().createNamedQuery(name);
            setParams(query, paramVals);
            record = (List<T>) query.getResultList();
        } catch (Exception exc) {
            exc.printStackTrace();
            DriverConfig.setLogString("Error in fetching DAO result - " + exc.getMessage(), true);
        }
        return record;
    }

    /**
     * Sets the params.
     * @param query the query
     * @param paramVals the param vals
     */
    private void setParams(Query query, Map<String, Object> paramVals) {

        Set<String> params = paramVals.keySet();
        for (String param : params) {
            query.setParameter(param, paramVals.get(param));
        }
    }

    /**
     * Gets the numerical result.
     * @param name the name
     * @param paramVals the param vals
     * @return the numerical result
     */
    public Object getNumericalResult(String name, Map<String, Object> paramVals) {

        Object obj = null;
        Query query = getEntityManager().createQuery(name);
        setParams(query, paramVals);
        try {
            obj = (Object) query.getSingleResult();
        } catch (Exception exc) {
            DriverConfig.setLogString("Error in fetching DAO result - " + exc.getMessage(), true);
        }
        return obj;
    }

    /**
     * List string by query.
     * @param ql the ql
     * @param paramVals the param vals
     * @return the list
     * @see com.ecofactor.qa.automation.dao.BaseDao#listStringByQuery(java.lang.String,
     *      java.util.Map)
     */
    @SuppressWarnings("unchecked")
    public List<String> listStringByQuery(String ql, Map<String, Object> paramVals) {

        List<String> record = null;
        try {
            Query query = getEntityManager().createQuery(ql);
            setParams(query, paramVals);
            record = (List<String>) query.getResultList();
        } catch (Exception exc) {
            DriverConfig.setLogString("Error in fetching DAO result - " + exc.getMessage(), true);
        }
        return record;
    }

    /**
     * Find by query int.
     * @param ql the ql
     * @param paramVals the param vals
     * @return the integer
     * @see com.ecofactor.qa.automation.dao.BaseDao#findByQueryInt(java.lang.String, java.util.Map)
     */
    public Integer findByQueryInt(String ql, Map<String, Object> paramVals) {

        Integer value = 0;
        try {
            Query query = getEntityManager().createQuery(ql);
            query.setMaxResults(1);
            setParams(query, paramVals);
            value = (Integer) query.getSingleResult();
        } catch (Exception exc) {
            DriverConfig.setLogString("Error in fetching DAO result - " + exc.getMessage(), true);
        }
        return value;
    }

    /**
     * Save entity object.
     * @param clazz the clazz
     * @return the boolean
     */
    public Boolean saveEntity(T clazz) {

        Boolean saved = false;

        try {
            EntityManager em = getUpdateEntityManager();
            em.getTransaction().begin();
            em.persist(clazz);
            saved = true;
            em.getTransaction().commit();
        } catch (Exception exc) {
            exc.printStackTrace();
            DriverConfig.setLogString("Error in saving object " + exc.getMessage(), true);
            saved = false;
        }
        return saved;
    }

    /**
     * Save entity object.
     * @param clazz the clazz
     * @return the boolean
     */
    public Boolean updateEntity(T clazz) {

        Boolean saved = false;

        try {
            EntityManager em = getUpdateEntityManager();
            em.getTransaction().begin();
            em.merge(clazz);
            saved = true;
            em.getTransaction().commit();
        } catch (Exception exc) {
            exc.printStackTrace();
            DriverConfig.setLogString("Error in saving object " + exc.getMessage(), true);
            saved = false;
        }
        return saved;
    }

    /**
     * Update user entity.
     * @param clazz the clazz
     * @return the boolean
     */
    public Boolean updateUserEntity(T clazz) {

        Boolean saved = false;

        try {
            EntityManager em = getUpdateUserEntityManager();
            em.getTransaction().begin();
            em.merge(clazz);
            saved = true;
            em.getTransaction().commit();
        } catch (Exception exc) {
            exc.printStackTrace();
            DriverConfig.setLogString("Error in saving efuser object " + exc.getMessage(), true);
            saved = false;
        }
        return saved;
    }
}
