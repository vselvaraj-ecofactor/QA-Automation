/*
 * DesktopUIAction.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.platform.action.impl;

import static com.ecofactor.qa.automation.platform.util.LogUtil.*;
import static com.ecofactor.qa.automation.util.WaitUtil.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ecofactor.qa.automation.platform.enums.CustomLogLevel;
import com.ecofactor.qa.automation.platform.enums.CustomTimeout;
import com.ecofactor.qa.automation.platform.enums.Marker;
import static com.ecofactor.qa.automation.platform.util.Pageutil.*;

/**
 * The Class DesktopUIAction.
 * @author $Author: vprasannaa $
 * @version $Rev: 32085 $ $Date: 2014-09-22 18:50:18 +0530 (Mon, 22 Sep 2014) $
 */
public class DesktopUIAction extends AbstractUIAction {

    /**
     * The Constant LOGGER.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(DesktopUIAction.class);

    /**
     * The Constant SHORT_TIMEOUT.
     */
    public static final int SHORT_TIMEOUT = 30;

    /**
     * Do swipe left.
     * @param element the element
     * @param xCoordinate the x coordinate
     * @param yCoordinate the y coordinate
     * @param distance the distance
     * @param speed the speed
     * @see com.ecofactor.qa.automation.mobile.action.UIAction#doSwipeLeft(org.openqa.selenium.WebElement,
     *      double, double, double, double)
     */
    @Override
    public void doSwipeLeft(final WebElement element, final double xCoordinate,
            final double yCoordinate, final double distance, final double speed) {

        // TODO Auto-generated method stub

    }

    /**
     * Do swipe right.
     * @param element the element
     * @param xCoordinate the x coordinate
     * @param yCoordinate the y coordinate
     * @param distance the distance
     * @param speed the speed
     * @see com.ecofactor.qa.automation.mobile.action.UIAction#doSwipeRight(org.openqa.selenium.WebElement,
     *      double, double, double, double)
     */
    @Override
    public void doSwipeRight(final WebElement element, final double xCoordinate,
            final double yCoordinate, final double distance, final double speed) {

        // TODO Auto-generated method stub

    }

    /**
     * Do swipe up.
     * @param element the element
     * @param xCoordinate the x coordinate
     * @param yCoordinate the y coordinate
     * @param distance the distance
     * @param speed the speed
     * @see com.ecofactor.qa.automation.mobile.action.UIAction#doSwipeUp(org.openqa.selenium.WebElement,
     *      double, double, double, double)
     */
    @Override
    public void doSwipeUp(final WebElement element, final double xCoordinate,
            final double yCoordinate, final double distance, final double speed) {

        setLogString("Swipe Up", true, CustomLogLevel.HIGH);

        new Actions(driverOps.getDeviceDriver()).dragAndDropBy(element, 0, (int) -distance).build()
                .perform();
    }

    /**
     * Do swipe down.
     * @param element the element
     * @param xCoordinate the x coordinate
     * @param yCoordinate the y coordinate
     * @param distance the distance
     * @param speed the speed
     * @see com.ecofactor.qa.automation.mobile.action.UIAction#doSwipeDown(org.openqa.selenium.WebElement,
     *      double, double, double, double)
     */
    @Override
    public void doSwipeDown(final WebElement element, final double xCoordinate,
            final double yCoordinate, final double distance, final double speed) {

        setLogString("Swipe Down", true, CustomLogLevel.HIGH);
        new Actions(driverOps.getDeviceDriver()).dragAndDropBy(element, 0, (int) distance).build()
                .perform();

    }

    /**
     * Accept alert.
     * @see com.ecofactor.qa.automation.mobile.action.UIAction#acceptAlert()
     */
    @Override
    public void acceptAlert() {

        try {
            final WebDriverWait wait = new WebDriverWait(driverOps.getDeviceDriver(), SHORT_TIMEOUT);
            if (wait.until(ExpectedConditions.alertIsPresent()) != null) {
                driverOps.getDeviceDriver().switchTo().alert().accept();
            }
        } catch (final Exception e) {
            LOGGER.error("Error in acceptAlert(). Cause ::: " + e);
        }
    }

    /**
     * Do Tap.
     * @param element the element
     * @see com.ecofactor.qa.automation.platform.action.UIAction#doTap(org.openqa.selenium.WebElement)
     */
    @Override
    public void doTap(WebElement element) {

        element.click();
    }

    /**
     * Drag element.
     * @param srcElement the src element
     * @param xOffset the x offset
     * @param yOffset the y offset
     * @see com.ecofactor.qa.automation.platform.action.UIAction#dragElement(org.openqa.selenium.WebElement,
     *      org.openqa.selenium.WebElement)
     */
    @Override
    public void dragElement(final WebElement srcElement, final Integer xOffset,
            final Integer yOffset) {

        setLogString("Drag element..", true, CustomLogLevel.HIGH);
        final Actions builder = new Actions(driverOps.getDeviceDriver());
        oneSec();
        builder.clickAndHold(srcElement).build().perform();
        oneSec();
        builder.moveByOffset(xOffset, yOffset).build().perform();

    }

    /**
     * Drop element.
     * @param srcElement the src element
     * @see com.ecofactor.qa.automation.platform.action.UIAction#dropElement(org.openqa.selenium.WebElement)
     */
    @Override
    public void dropElement(final WebElement srcElement) {

        setLogString("Drop element..", true, CustomLogLevel.HIGH);
        final Actions builder = new Actions(driverOps.getDeviceDriver());
        builder.release(srcElement).build().perform();
        oneSec();

    }

    /**
     * Reject alert.
     * @see com.ecofactor.qa.automation.platform.action.UIAction#rejectAlert()
     */
    @Override
    public void rejectAlert() {

    }

    /**
     * @see com.ecofactor.qa.automation.platform.action.UIAction#clickTryAgain()
     */
    @Override
    public void clickTryAgain() {

        setLogString(Marker.START, "Verify if error page loaded with try again and logout button.", true);

        try {
            if (isDisplayed(driverOps.getDeviceDriver(),
                    By.cssSelector("div.retryButton.ctaButton"), CustomTimeout.TINY_TIMEOUT)) {
                setLogString("Click Try again.", true);
                getElement(driverOps.getDeviceDriver(),
                        By.cssSelector("div.retryButton.ctaButton"), CustomTimeout.TINY_TIMEOUT)
                        .click();
            }
        } catch (Exception ex) {
            setLogString("No error page displayed.", true);
        }
        setLogString(Marker.END, "Completed", true);

    }

    @Override
    public int getWindowWidth(WebElement element) {

        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getWindowHeight(WebElement element) {

        // TODO Auto-generated method stub
        return 0;
    }

}
