/*
 * DBValidationInterceptor.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.platform.interceptor;

import static com.ecofactor.qa.automation.platform.util.CheckJenkinsParamUtil.*;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ecofactor.qa.automation.platform.util.LogUtil;
import com.ecofactor.qa.automation.util.DateUtil;

/**
 * The Class DBValidationInterceptor.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class DBValidationInterceptor implements MethodInterceptor {

    /**
     * The log.
     */
    private final Logger log = LoggerFactory.getLogger(DBValidationInterceptor.class);

    /**
     * Invoke.
     * @param invocation the invocation
     * @return the object
     * @throws Throwable the throwable
     * @see org.aopalliance.intercept.MethodInterceptor#invoke(org.aopalliance.intercept.MethodInvocation)
     */
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {

        boolean dbValidationStatus = true;
        if (!canDBValidate()) {
            log.warn(invocation.getMethod().getName() + " is not allowed as DB validation is not supported!", true);
            return null;
        }
        String currentUTCTime = DateUtil.getUTCCurrentTimeStamp();
        LogUtil.setLogString("Starting DB Validation. (UTC Time : " + currentUTCTime + " )", true);
        LogUtil.setLogString("===================================================================", true);
        try {
            return invocation.proceed();
        } catch (Throwable e) {
            dbValidationStatus = false;
            LogUtil.setLogString("Failed : " + e.toString(), true);
            return null;
        } finally {
            LogUtil.setLogString(logStatus(dbValidationStatus), true);
            LogUtil.setLogString("===================================================================", true);
        }
    }

    private String logStatus(boolean dbValidationStatus) {

        String currentUTCTime = DateUtil.getUTCCurrentTimeStamp();
        String statusMessage = dbValidationStatus ? "\033[42;1mDB Validation Success ( UTC Time : " + currentUTCTime + ")" : "\033[41;mDB Validation Failure ( UTC Time : "
                + currentUTCTime + ")";
        return statusMessage;
    }

}
