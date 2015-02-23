/*
 * AdminValidationInterceptor.java
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

import com.ecofactor.qa.automation.platform.DesktopDriverManager;
import com.ecofactor.qa.automation.platform.util.LogUtil;
import com.ecofactor.qa.automation.util.DateUtil;

/**
 * The Class AdminValidationInterceptor.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class AdminValidationInterceptor implements MethodInterceptor {

    /**
     * The log.
     */
    private final Logger log = LoggerFactory.getLogger(AdminValidationInterceptor.class);

    /**
     * Invoke.
     * @param invocation the invocation
     * @return the object
     * @throws Throwable the throwable
     * @see org.aopalliance.intercept.MethodInterceptor#invoke(org.aopalliance.intercept.MethodInvocation)
     */
    @Override
    public Object invoke(final MethodInvocation invocation) throws Throwable {

        boolean adminValidationStatus = true;
        if (!canAdminValidate()) {
            log.warn(invocation.getMethod().getName() + " is not allowed as Admin validation is not supported!", true);
            return null;
        }
        String currentUTCTime = DateUtil.getUTCCurrentTimeStamp();
        LogUtil.setLogString("Starting Admin Validation. (UTC Time : " + currentUTCTime + " )", true);
        LogUtil.setLogString("===================================================================", true);
        DesktopDriverManager.createAdminDriver();
        try {
            return invocation.proceed();
        } catch (Throwable e) {
            LogUtil.setLogString(e.toString(), true);
            return null;
        } finally {
            DesktopDriverManager.quitAdminDriver();
            LogUtil.setLogString("===================================================================", true);
            LogUtil.setLogString(logStatus(adminValidationStatus), true);
        }
    }

    /**
     * Log status.
     * @param adminValidationStatus the admin validation status
     * @return the string
     */
    private String logStatus(boolean adminValidationStatus) {

        String currentUTCTime = DateUtil.getUTCCurrentTimeStamp();
        String statusMessage = adminValidationStatus ? "\033[42;1mAdmin Validation Success ( UTC Time : " + currentUTCTime + ")" : "\033[41;Admin Validation Failure ( UTC Time : "
                + currentUTCTime + ")";
        return statusMessage;
    }

}
