/*
 * ThermostatEEUIPageImpl.java
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
import static com.ecofactor.qa.automation.platform.util.LogUtil.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.ecofactor.qa.automation.newapp.page.ThermostatEEUIPage;
import com.ecofactor.qa.automation.platform.enums.CustomLogLevel;

/**
 * The Class ThermostatEEUIPageImpl.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class ThermostatEEUIPageImpl extends AbstractAuthenticationPageImpl implements
        ThermostatEEUIPage {

    /** The Constant CLOSE_BTN. */
    private static final String CLOSE_BTN = "closeHelpButton";

    /** The Constant EE_DESCRIPTION_PNG. */
    private static final String EE_DESCRIPTION_PNG = "EE-description.png";

    /** The Constant BACKGROUND_IMAGE. */
    private static final String BACKGROUND_IMAGE = "background-image";

    /** The Constant IMAGE_CONTAINER. */
    private static final String IMAGE_CONTAINER = "imageContainer";

    /** The Constant TEXT_CONTAINER. */
    private static final String TEXT_CONTAINER = "textContainer";

    /**
     * Checks if is close button displayed.
     * @return true, if is close button displayed
     * @see com.ecofactor.qa.automation.newapp.page.ThermostatEEUIPage#isCloseButtonDisplayed()
     */
    @Override
    public boolean isCloseButtonDisplayed() {

        setLogString("is Close Button Displayed in EE ?", true);
        WebElement closeBtnElement = getElement(getDriver(), By.className(CLOSE_BTN), TINY_TIMEOUT);
        if (closeBtnElement != null && closeBtnElement.isDisplayed()) {
            setLogString("Close Button is displayed", true);
            return true;
        }
        return false;
    }

    /**
     * Checks if is image displayed.
     * @return true, if is image displayed
     * @see com.ecofactor.qa.automation.newapp.page.ThermostatEEUIPage#isImageDisplayed()
     */
    @Override
    public boolean isImageDisplayed() {

        setLogString("is Image Displayed in EE ?", true);
        Object imageObj = executeScriptByClassName(IMAGE_CONTAINER, BACKGROUND_IMAGE, getDriver());
        if (imageObj != null && imageObj.toString().contains(EE_DESCRIPTION_PNG)) {
            setLogString("Image Displayed in EE", true);
            return true;
        }
        return false;
    }

    /**
     * Checks if is content displayed.
     * @return true, if is content displayed
     * @see com.ecofactor.qa.automation.newapp.page.ThermostatEEUIPage#isContentDisplayed()
     */
    @Override
    public boolean isContentDisplayed() {

        setLogString("is EE Description Content present?", true);
        WebElement contentElement = getElement(getDriver(), By.className(TEXT_CONTAINER),
                TINY_TIMEOUT);
        if (contentElement != null) {

            WebElement paragraph = getElementBySubElement(getDriver(), contentElement,
                    By.tagName("p"), TINY_TIMEOUT);
            if (paragraph != null && paragraph.getText() != null
                    && paragraph.getText().length() > 0) {
                setLogString("EE Description is displayed", true);
                setLogString("EE Description is:\n" + contentElement.getText(), true,
                        CustomLogLevel.HIGH);
                return true;
            }
        }
        return false;
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

}
