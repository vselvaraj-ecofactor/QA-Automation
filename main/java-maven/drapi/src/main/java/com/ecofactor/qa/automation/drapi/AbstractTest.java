/*
 * AbstractTest.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.drapi;

import static com.ecofactor.qa.automation.platform.util.LogUtil.*;

import java.lang.reflect.Method;

import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.ecofactor.qa.automation.drapi.pojo.Error;
import com.ecofactor.qa.automation.drapi.pojo.ErrorMessage;
import com.ecofactor.qa.automation.platform.enums.LogSection;
import com.ecofactor.qa.automation.platform.util.LogUtil;
import com.ecofactor.qa.automation.util.JsonUtil;
import com.google.inject.Inject;

/**
 * The Class AbstractTest.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class AbstractTest {

    @Inject
    protected LogUtil logUtil;
    @Inject
    protected DRApiConfig apiConfig;

    /**
     * The start time.
     */
    protected long startTime;

    /**
     * Sets the up.
     * @param context the new up
     */
    @BeforeSuite(alwaysRun = true)
    public void setUp(final ITestContext context) {

        setLogString(LogSection.START, "Before Suite. ", true);
    }

    /**
     * Before method.
     * @param method the method
     * @param param the param
     */
    @BeforeMethod(alwaysRun = true)
    public void beforeMethod(final Method method, final Object[] param) {

        logUtil.logStart(method, param, null);
        startTime = System.currentTimeMillis();
    }

    /**
     * Tear down.
     * @param method the method
     */
    @AfterMethod(alwaysRun = true)
    public void tearDown(final Method method) {

        final long duration = (System.currentTimeMillis() - startTime) / 1000;
        logUtil.logEnd(method, duration);
    }

    /**
     * After suite.
     */
    @AfterSuite(alwaysRun = true)
    public void afterSuite() {

    }

    /**
     * Gets the error msg.
     * @param errorResponse the error response
     * @return the error msg
     */
    public Error getErrorMsg(final String errorResponse) {

        return JsonUtil.getObject(errorResponse, Error.class);
    }

    /**
     * Checks if is error present.
     * @param error the error
     * @param code the code
     * @return true, if is error present
     */
    public boolean isErrorPresent(final Error error, final int code) {

        return isErrorPresent(error, code, null);
    }

    /**
     * Checks if is error present.
     * @param error the error
     * @param code the code
     * @param msg the msg
     * @return true, if is error present
     */
    public boolean isErrorPresent(final Error error, final int code, final String msg) {

        for (final ErrorMessage errorMsg : error.getErrors()) {
            if (errorMsg.getCode() == code
                    && (msg == null || errorMsg.getMsg().equalsIgnoreCase(msg))) {
                return true;
            }
        }
        return false;
    }
}
