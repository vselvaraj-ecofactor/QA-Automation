/*
 * UtilModule.java
 * Copyright (c) 2012, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.util;

import com.ecofactor.qa.automation.util.mail.*;
import com.google.inject.*;

/**
 * UtilModule is used to configure the utility module components.
 * @author $Author: asaravana $
 * @version $Rev: 20797 $ $Date: 2013-07-10 15:15:41 +0530 (Wed, 10 Jul 2013) $
 */
public class TestDataUtilModule extends AbstractModule {

    /**
     * Configures the utility module components.
     * @see com.google.inject.AbstractModule#configure()
     */
    protected void configure() {

        requestStaticInjection(PageAction.class);
        bind(DriverConfig.class);
        bind(TestLogUtil.class).in(Singleton.class);
        bind(ContentBuilder.class).to(HtmlContentBuilder.class);
        bind(AlgoContentBuilder.class).to(AlgoHtmlContentBuilder.class);
        bind(DeleteUserContentBuilder.class).to(DeleteUserHtmlContentBuilder.class);
        bind(TestLinkContentBuilder.class).to(TestLinkHtmlContentBuilder.class);
        bind(MailUtil.class).in(Singleton.class);
    }
}
