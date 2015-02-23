/*
 * PreRequisiteInterceptor.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.platform.interceptor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ecofactor.qa.automation.platform.enums.CustomLogLevel;
import com.ecofactor.qa.automation.platform.util.LogUtil;

/**
 * The Class PreRequisiteInterceptor.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class PreRequisiteInterceptor implements MethodInterceptor {

    /**
     * The log.
     */
    private final Logger log = LoggerFactory.getLogger(PreRequisiteInterceptor.class);

    /**
     * Invoke.
     * @param invocation the invocation
     * @return the object
     * @throws Throwable the throwable
     * @see org.aopalliance.intercept.MethodInterceptor#invoke(org.aopalliance.intercept.MethodInvocation)
     */
    @Override
    public Object invoke(final MethodInvocation invocation) throws Throwable {

        LogUtil.setLogString("Method specific prerequisite Starts", true, CustomLogLevel.HIGH);
        LogUtil.setLogString("===================================================================",
                true, CustomLogLevel.HIGH);
        try {
            return invocation.proceed();
        } catch (final Throwable e) {
            LogUtil.setLogString("Failed : " + e.toString(), true);
            return null;
        } finally {
            LogUtil.setLogString("Method specific prerequisite Ends", true, CustomLogLevel.HIGH);
            LogUtil.setLogString(
                    "===================================================================", true,
                    CustomLogLevel.HIGH);
        }
    }

}
