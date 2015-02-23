/*
 * MobileModule.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.newapp;

import static com.ecofactor.qa.automation.platform.util.JenkinsParamUtil.*;
import com.ecofactor.qa.automation.newapp.admin.page.*;
import com.ecofactor.qa.automation.newapp.data.*;
import com.ecofactor.qa.automation.newapp.page.*;
import com.ecofactor.qa.automation.newapp.util.mail.*;
import com.ecofactor.qa.automation.platform.DesktopDriverManager;
import com.ecofactor.qa.automation.platform.action.*;
import com.ecofactor.qa.automation.platform.action.impl.*;
import com.ecofactor.qa.automation.platform.annotation.*;
import com.ecofactor.qa.automation.platform.interceptor.*;
import com.ecofactor.qa.automation.platform.ops.*;
import com.ecofactor.qa.automation.platform.ops.impl.*;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.matcher.Matchers;

/**
 * MobileModule configures the dependencies for the Mobile.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class MobileModule extends AbstractModule {

    /**
     * Configure.
     * @see com.google.inject.AbstractModule#configure()
     */
    @Override
    protected void configure() {

        bindDeviceDependencies();
        // Pages
        bind(MobileConfig.class).in(Singleton.class);
        bind(LoginPage.class).in(Singleton.class);
        bind(HelpOverlayPage.class).in(Singleton.class);
        bind(AdminLoginPage.class).in(Singleton.class);
        // Interceptors
        bindInterceptor(Matchers.any(), Matchers.annotatedWith(DBValidationMethod.class), new DBValidationInterceptor());
        bindInterceptor(Matchers.any(), Matchers.annotatedWith(AdminValidationMethod.class), new AdminValidationInterceptor());
        bindInterceptor(Matchers.any(), Matchers.annotatedWith(TestPrerequisite.class), new PreRequisiteInterceptor());
        // Providers & Static
        requestStaticInjection(CommonsDataProvider.class);
        requestStaticInjection(MobileUserDataProvider.class);
        requestStaticInjection(DesktopDriverManager.class);
        // Others
        bind(DeviceInfoContentBuilder.class).to(DeviceInfoHtmlContentBuilder.class);
    }

    /**
     * Bind device dependencies.
     */
    private void bindDeviceDependencies() {

        if (getDeviceName()!=null&&!getDeviceName().isEmpty()) {
            bindMobileDependencies();
        } else {
            bind(TestOperations.class).to(DesktopOperations.class).in(Singleton.class);
            bind(UIAction.class).to(DesktopUIAction.class).in(Singleton.class);
        }
    }

    /**
     * Bind mobile dependencies.
     */
	private void bindMobileDependencies() {

		if (getDeviceName().contains("Apple")) {
			bind(TestOperations.class).to(IOSOperations.class).in(Singleton.class);
			bind(DeviceInfo.class).to(IOSDeviceInfo.class);
			bind(UIAction.class).to(IOSUIAction.class).in(Singleton.class);
		} else {
			bind(TestOperations.class).to(AndroidOperations.class).in(Singleton.class);
			bind(DeviceInfo.class).to(AndroidDeviceInfo.class);
			bind(UIAction.class).to(AndroidUIAction.class).in(Singleton.class);
		}
		/* Add support for other OS platforms here. */
	}
}
