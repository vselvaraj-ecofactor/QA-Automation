/*
 * ConsumerPageImpl.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.insite.page;

import static com.ecofactor.qa.automation.insite.config.InsiteConfig.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ecofactor.qa.automation.insite.config.InsiteConfig;
import com.ecofactor.qa.automation.util.DriverConfig;
import com.ecofactor.qa.automation.util.PageAction;
import com.google.inject.Inject;

/**
 * ConsumerPageImpl implements the ConsumerPage interface to perform common footer actions on a
 * consumer portal page.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public abstract class InsitePageImpl extends PageAction implements InsitePage {

    @Inject
    protected InsiteConfig insiteConfig;
    protected String user=null;
    private static Logger logger = LoggerFactory.getLogger(InsitePageImpl.class);
    @Override
    public void clickAboutEcofactor() {
        logger.info("check if About Ecofactor link displayed.", true);
        WebElement aboutEcofactorLink = DriverConfig.getDriver().findElement(By.cssSelector(insiteConfig.get(ABOUT_ECOFACTOR_LINK)));
        DriverConfig.setLogString("Click About Ecofactor", true);
        aboutEcofactorLink.click();

    }


}
