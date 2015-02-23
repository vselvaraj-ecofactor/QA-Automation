/*
 * RetryEnabledListener.java
 * Copyright (c) 2012, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.platform.listener;

import java.util.List;

import org.testng.ITestContext;
import org.testng.ITestNGMethod;

import com.ecofactor.qa.automation.platform.util.DeviceUtil;

/**
 * The listener interface for receiving retryEnabled events. The class that is interested in
 * processing a retryEnabled event implements this interface, and the object created with that class
 * is registered with a component using the component's
 * <code>addRetryEnabledListener<code> method. When
 * the retryEnabled event occurs, that object's appropriate
 * method is invoked.
 * @see RetryEnabledEvent
 */
public class RetryEnabledListener extends AutomationListener {

    /**
     * On start. Add Retry listener to test methods. Also add missing <code>sanity1</code> group to
     * methods if Mobile.
     * @param ctx the ctx
     * @see com.ecofactor.qa.automation.platform.listener.AutomationListener#onStart(org.testng.ITestContext)
     */
    @Override
    public void onStart(final ITestContext ctx) {

        super.onStart(ctx);
        for (final ITestNGMethod method : ctx.getAllTestMethods()) {
            final List<String> groupList = DeviceUtil.arrayToList(method.getGroups());
            if (groupList == null || !groupList.contains("stub")) {
                method.setRetryAnalyzer(new MobileRetryAnalyzer());
            }
        }
    }

}
