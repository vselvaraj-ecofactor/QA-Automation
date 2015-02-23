/*
 * BaseDao.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

/**
 * BaseDao provides the interface for base DAO.
 * @param <T> the
 * @author $Author: vprasannaa $
 * @version $Rev: 32901 $ $Date: 2014-11-28 18:08:02 +0530 (Fri, 28 Nov 2014) $
 */
public interface BaseDao<T> {

    /**
     * Gets the entity manager.
     * @return the entity manager
     */
    public EntityManager getEntityManager();

    /**
     * Find by id.
     * @param clazz the clazz
     * @param id the id
     * @return the t
     */
    public T findById(Class<T> clazz, Object id);

    /**
     * Find by query.
     * @param ql the ql
     * @param paramVals the param vals
     * @return the t
     */
    public T findByQuery(String ql, Map<String, Object> paramVals);

    /**
     * List query by limit.
     * @param ql the ql
     * @param paramVals the param vals
     * @param noOfRows the no of rows
     * @return the list
     */
    public List<T> listQueryByLimit(String ql, Map<String, Object> paramVals, int noOfRows);

    /**
     * List by query.
     * @param ql the ql
     * @param paramVals the param vals
     * @return the list
     */
    public List<T> listByQuery(String ql, Map<String, Object> paramVals);

    /**
     * List by report query.
     * @param ql the ql
     * @param paramVals the param vals
     * @return the list
     */
    public List<T> listByReportQuery(String ql, Map<String, Object> paramVals);

    /**
     * Find by native query.
     * @param nql the nql
     * @param paramVals the param vals
     * @param clazz the clazz
     * @return the t
     */
    public T findByNativeQuery(String nql, Map<String, Object> paramVals, Class<T> clazz);

    /**
     * List by native query.
     * @param nql the nql
     * @param paramVals the param vals
     * @param clazz the clazz
     * @return the list
     */
    public List<T> listByNativeQuery(String nql, Map<String, Object> paramVals, Class<T> clazz);

    /**
     * Find by named query.
     * @param name the name
     * @param paramVals the param vals
     * @return the t
     */
    public List<T> findByNamedQuery(String name, Map<String, Object> paramVals);

    /**
     * List by named query.
     * @param name the name
     * @param paramVals the param vals
     * @return the list
     */
    public List<T> listByNamedQuery(String name, Map<String, Object> paramVals);

    /**
     * List string by query.
     * @param name the name
     * @param paramVals the param vals
     * @return the list
     */
    public List<String> listStringByQuery(String name, Map<String, Object> paramVals);

    /**
     * Find by query int.
     * @param name the name
     * @param paramVals the param vals
     * @return the integer
     */
    public Integer findByQueryInt(String name, Map<String, Object> paramVals);

    /**
     * Save entity.
     * @param clazz the clazz
     * @return the boolean
     */
    public Boolean saveEntity(T clazz);

    /**
     * Update entity.
     * @param clazz the clazz
     * @return the boolean
     */
    public Boolean updateEntity(T clazz);


    public int countByNativeQuery(String nql, Map<String, Object> paramVals);
}
