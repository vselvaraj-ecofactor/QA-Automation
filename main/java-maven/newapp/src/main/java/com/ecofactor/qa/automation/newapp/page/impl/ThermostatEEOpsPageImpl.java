/*
 * ThermostatEEOpsPageImpl.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.newapp.page.impl;

import static com.ecofactor.qa.automation.platform.enums.CustomTimeout.*;
import static com.ecofactor.qa.automation.platform.util.LogUtil.*;
import static com.ecofactor.qa.automation.platform.util.Pageutil.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.ecofactor.qa.automation.newapp.page.ThermostatEEOpsPage;
import com.ecofactor.qa.automation.platform.enums.CustomLogLevel;
import com.ecofactor.qa.automation.util.WaitUtil;

/**
 * The Class ThermostatEEOpsPageImpl.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class ThermostatEEOpsPageImpl extends AbstractAuthenticationPageImpl implements
        ThermostatEEOpsPage {

    private static final String CLOSE_BTN = "closeHelpButton";
    private static final String SAVINGS_ENERGY = ".fadein_text";

    /**
     * Click close.
     * @see com.ecofactor.qa.automation.newapp.page.ThermostatEEOpsPage#clickClose()
     */
    @Override
    public void clickClose() {

        setLogString("Click Close button in EE", true);
        WebElement closeBtnElement = getElement(getDriver(), By.className(CLOSE_BTN), TINY_TIMEOUT);
        if (closeBtnElement != null && closeBtnElement.isDisplayed()) {
            getAction().click(closeBtnElement);
        }

    }

    /**
     * Cleanup.
     * @see com.ecofactor.qa.automation.newapp.page.BasePage#cleanup()
     */
    @Override
    public void cleanup() {

    }

    /**
     * Checks if is page loaded.
     * @return true, if is page loaded
     * @see com.ecofactor.qa.automation.newapp.page.BasePage#isPageLoaded()
     */
    @Override
    public boolean isPageLoaded() {

        return false;
    }

    /**
     * Click Savings Energy Efficient Link.
     * @see com.ecofactor.qa.automation.newapp.page.ThermostatPageOps#clickSavingsEELink()
     */
    @Override
    public void clickSavingsEELink() {

        setLogString("Click Savings EE Link", true, CustomLogLevel.LOW);
        final WebElement savingsEELink = getElement(getDriver(), By.cssSelector(SAVINGS_ENERGY),
                THREE_SEC);
        savingsEELink.click();
        WaitUtil.oneSec();
        //getAction().rejectAlert();
    }

}
