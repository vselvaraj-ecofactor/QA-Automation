/*
 * AwaySettingsUiPageImpl.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.newapp.page.impl;

import static com.ecofactor.qa.automation.platform.enums.CustomTimeout.*;
import static com.ecofactor.qa.automation.platform.util.Pageutil.*;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.ecofactor.qa.automation.newapp.enums.SetAwayParams;
import com.ecofactor.qa.automation.newapp.page.AwaySettingsUIPage;
import com.ecofactor.qa.automation.newapp.page.MenuPage;
import com.ecofactor.qa.automation.newapp.page.SetAwayPage;
import com.ecofactor.qa.automation.newapp.page.ThermostatPageOps;
import com.ecofactor.qa.automation.platform.enums.CustomLogLevel;
import com.ecofactor.qa.automation.platform.util.LogUtil;
import com.ecofactor.qa.automation.util.WaitUtil;

import com.google.inject.Inject;

/**
 * The Class AwaySettingsUiPageImpl.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class AwaySettingsUiPageImpl extends AbstractAuthenticationPageImpl implements
        AwaySettingsUIPage {

    /** The Constant AWAY_SETTINGS_HEADER. */
    private static final String AWAY_SETTINGS_HEADER = ".settings_label.small_text";

    /** The Constant SETTINGS_ALERT_LABEL. */
    private static final String SETTINGS_ALERT_LABEL = "div.settings_alert_label";

    /** The Constant DURATION. */
    private static final String DURATION = "duration";

    /** The Constant SETTINGS_CONTAINER. */
    private static final String SETTINGS_CONTAINER = "div.settings_content";

    /** The Constant AWAY_SETTINGS. */
    @SuppressWarnings("unused")
    private static final String AWAY_SETTINGS = ".away_settings.clickable.item.grey_arrow";

    /** The set away. */
    @Inject
    private SetAwayPage setAway;

    /** The th page ops. */
    @Inject
    protected ThermostatPageOps thPageOps;

    /** The menu page. */
    @Inject
    private MenuPage menuPage;

    /**
     * Gets the picker element.
     * @param setAwayParam the set away param
     * @return element.
     * @see com.ecofactor.qa.automation.newapp.page.AwaySettingsUIPage#getPickerElement(com.ecofactor.qa.automation.newapp.enums.SetAwayParams)
     */
    @Override
    public WebElement getPickerElement(final SetAwayParams setAwayParam) {

        WaitUtil.tinyWait();
        final boolean setAwayPage = setAway.isSetAwayPageLoaded();

        // setAway.clickAwaySettings();
        if (setAwayPage == true) {

            setAway.clickAwaySettings();
        }/* else {

            thPageOps.clickSettingsMenu();
            menuPage.clickAwayHiglighted();
            setAway.clickAwaySettings();
        }*/

        final WebElement settingsElement = getElement(getDriver(),
                By.cssSelector(SETTINGS_CONTAINER), TINY_TIMEOUT);

        List<WebElement> selectContainer = getElementsBySubElement(getDriver(), settingsElement,
                By.cssSelector("span.select_container"), TINY_TIMEOUT);

        final WebElement setAwayParamElement = getElementBySubElement(getDriver(),
                selectContainer.get(setAwayParam.getValue()), By.tagName("option"), TINY_TIMEOUT);
        return setAwayParamElement;
    }

    /**
     * Gets the picker value.
     * @param setAwayParam the set away param
     * @return Integer.
     * @see com.ecofactor.qa.automation.newapp.page.AwaySettingsUIPage#getPickerValue(com.ecofactor.qa.automation.newapp.enums.SetAwayParams)
     */
    @Override
    public int getPickerValue(final SetAwayParams setAwayParam) {

        return Integer.parseInt(getPickerElement(setAwayParam).getAttribute("value"));

    }

    /**
     * Checks if is sets the away param label displayed.
     * @param setAwayParam the set away param
     * @return true, if is sets the away param label displayed
     * @see com.ecofactor.qa.automation.newapp.page.AwaySettingsUIPage#isSetAwayParamLabelDisplayed(com.ecofactor.qa.automation.newapp.enums.SetAwayParams)
     */
    public boolean isSetAwayParamLabelDisplayed(final SetAwayParams setAwayParam) {

        final WebElement setpointPicker = getElement(getDriver(),
                By.cssSelector("div.settings_content"), TINY_TIMEOUT);
        return getElementBySubElementText(getDriver(), setpointPicker, By.tagName("label"),
                setAwayParam.toString(), TINY_TIMEOUT).isDisplayed();

    }

    /**
     * Checks if is duration label displayed.
     * @return true, if is duration label displayed
     */
    @Override
    public boolean isDurationLabelDisplayed() {

        return getElementByText(getDriver(), By.className("settings_title_label"), DURATION,
                TINY_TIMEOUT).isDisplayed();
    }

    /**
     * Cleanup.
     * @see com.ecofactor.qa.automation.newapp.page.BasePage#cleanup()
     */
    @Override
    public void cleanup() {

        // code

    }

    /**
     * Checks if is page loaded.
     * @return true, if is page loaded
     * @see com.ecofactor.qa.automation.newapp.page.BasePage#isPageLoaded()
     */
    @Override
    public boolean isPageLoaded() {

        LogUtil.setLogString("Verify the Away Settings Page is loaded", true, CustomLogLevel.MEDIUM);
        waitForPageLoaded(getDriver());
        final boolean isHeaderDisplayed = isDisplayed(getDriver(),
                By.cssSelector(AWAY_SETTINGS_HEADER), TINY_TIMEOUT);
        return isHeaderDisplayed;
    }

    /**
     * Checks if is away settings alert displayed.
     * @return true, if is away settings alert displayed
     * @see com.ecofactor.qa.automation.newapp.page.AwaySettingsUIPage#isAwaySettingsAlertDisplayed()
     */
    @Override
    public boolean isAwaySettingsAlertDisplayed() {

        final boolean isAlertDisplayed = isDisplayed(getDriver(),
                By.cssSelector(SETTINGS_ALERT_LABEL), TINY_TIMEOUT);
        return isAlertDisplayed;

    }

}
