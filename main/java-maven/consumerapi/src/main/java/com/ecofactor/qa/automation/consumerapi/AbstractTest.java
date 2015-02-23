/*
 * AbstractTest.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.consumerapi;

import static com.ecofactor.qa.automation.platform.util.LogUtil.*;

import java.lang.reflect.Method;

import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.Response;

import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.ecofactor.qa.automation.consumerapi.ApiConfig;
import com.ecofactor.qa.automation.platform.enums.LogSection;
import com.ecofactor.qa.automation.platform.exception.DeviceException;
import com.ecofactor.qa.automation.platform.util.LogUtil;
import com.google.inject.Inject;

/**
 * The Class AbstractTest.
 */
public class AbstractTest {

    @Inject
    protected LogUtil logUtil;
    @Inject
    protected ApiConfig apiConfig;

    /**
     * The security cookie.
     */
    protected Cookie securityCookie;

    /**
     * The start time.
     */
    protected long startTime;

    /**
     * Sets the up.
     * @param context the new up
     * @throws DeviceException the device exception
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
        securityCookie = Authenticator.getSecurityCookieForUser((String) param[0],
                (String) param[1]);
        setLogString("securityCookie :'" + securityCookie + "'", true);

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
     * Display api response.
     * @param response the response
     * @param module the module
     */
    public void displayAPIResponse(final Response response, final String module) {

        setLogString("response :'" + response + "'", true);
        setLogString(module + " data :", true);
        setLogString(response.readEntity(String.class), true);
    }
}
