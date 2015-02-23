/*
 * AndroidUIAction.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.platform.action.impl;

import static com.ecofactor.qa.automation.platform.util.LogUtil.*;
import static com.ecofactor.qa.automation.platform.util.TestCaseUtil.*;
import static com.ecofactor.qa.automation.util.WaitUtil.*;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.touch.TouchActions;

import com.ecofactor.qa.automation.platform.enums.Marker;
import com.ecofactor.qa.automation.util.WaitUtil;

/**
 * The Class AndroidUIAction.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class AndroidUIAction extends AbstractMobileUIAction {

    /**
     * Accept alert.
     * @see com.ecofactor.qa.automation.mobile.action.impl.AbstractMobileUIAction#acceptAlert()
     */
    @Override
    public void acceptAlert() {

        driverOps.switchToNative();
        setLogString("Click OK button", true);
        driverOps.getDeviceDriver().findElement(By.linkText("OK")).click();
        mediumWait();

    }

    /**
     * Swipe.
     * @param element the element
     * @param xCoordinate the x coordinate
     * @param yCoordinate the y coordinate
     * @param distance the distance
     * @param speed the speed
     * @param direction the direction
     * @see com.ecofactor.qa.automation.mobile.action.impl.AbstractMobileUIAction#swipe(org.openqa.selenium.WebElement,
     *      double, double, double, double,
     *      com.ecofactor.qa.automation.mobile.action.impl.AbstractUIAction.Direction)
     */
    @Override
    protected void swipe(final WebElement element, final double xCoordinate,
            final double yCoordinate, final double distance, final double speed,
            final Direction direction) {

        final int xOffset = (int) (getEndX(xCoordinate, distance, direction) - xCoordinate);
        final int yOffset = (int) (getEndY(yCoordinate, distance, direction) - yCoordinate);
        final int speedValue = (int) speed;
        final TouchActions flick = new TouchActions(driverOps.getDeviceDriver()).flick(element,
                xOffset, yOffset, speedValue);
        flick.perform();
        setLogString("Save mobile swipe screenshot for test: " + getTestName(), true);
    }

    /**
     * swipe the window at window size.
     * @param element
     * @param xCoordinate
     * @param yCoordinate
     * @param speed
     * @param direction
     * @see com.ecofactor.qa.automation.platform.action.impl.AbstractMobileUIAction#swipe(org.openqa.selenium.WebElement,
     *      double, double, double,
     *      com.ecofactor.qa.automation.platform.action.impl.AbstractUIAction.Direction)
     */
    @Override
    protected void swipe(final WebElement element, final double xCoordinate,
            final double yCoordinate, final double speed, final Direction direction) {

        final int xOffset = (int) (getEndX(xCoordinate, getWindowWidth(element), direction));

        final int yOffset = (int) (getEndY(yCoordinate, getWindowHeight(element), direction));
        final int speedValue = (int) speed;

        final TouchActions flick = new TouchActions(driverOps.getDeviceDriver()).flick(element,
                xOffset, yOffset, speedValue);
        flick.perform();
        setLogString("Save mobile swipe screenshot for test: " + getTestName(), true);

    }

    /**
     * Do Tap.
     * @param element the element
     * @see com.ecofactor.qa.automation.platform.action.UIAction#doTap(org.openqa.selenium.WebElement)
     */
    @Override
    public void doTap(final WebElement element) {

        element.click();       
    }

    /**
     * Click.
     * @param element the element
     * @see com.ecofactor.qa.automation.platform.action.impl.AbstractUIAction#click(org.openqa.selenium.WebElement)
     */
    @Override
    public void click(final WebElement element) {

        saveTestFlowScreenShot();
        element.click();
    }

    /**
     * Do swipe down.
     * @param element the element
     * @param xCoordinate the xCoordinate
     * @param yCoordinate the yCoordinate
     * @param distance the distance
     * @param speed the speed
     * @see com.ecofactor.qa.automation.platform.action.UIAction#doSwipeDown(org.openqa.selenium.WebElement,
     *      double, double, double, double)
     */
    @Override
    public void doSwipeDown(WebElement element, double xCoordinate, double yCoordinate,
            double distance, double speed) {

        setLogString("Drag and Drop in Android is Currently InProgress....", true);
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
    public void dragElement(WebElement srcElement, Integer xOffset, Integer yOffset) {

        setLogString("Drag in Android is Currently InProgress....", true);
    }

    /**
     * Drop element.
     * @param srcElement the src element
     * @see com.ecofactor.qa.automation.platform.action.UIAction#dropElement(org.openqa.selenium.WebElement)
     */
    @Override
    public void dropElement(WebElement srcElement) {

        setLogString("Drop in Android is Currently InProgress....", true);
    }

    /**
     * Do swipe up.
     * @param element the element
     * @param xCoordinate the xCoordinate
     * @param yCoordinate the yCoordinate
     * @param distance the distance
     * @param speed the speed
     * @see com.ecofactor.qa.automation.mobile.action.MobileUIAction#doSwipeUp(org.openqa.selenium.WebElement,
     *      double, double, double, double)
     */
    @Override
    public void doSwipeUp(final WebElement element, final double xCoordinate,
            final double yCoordinate, final double distance, final double speed) {

        setLogString("Drag and Drop in Android is Currently InProgress....", true);
    }

    /**
     * Reject alert.
     * @see com.ecofactor.qa.automation.platform.action.UIAction#rejectAlert()
     */
    @Override
    public void rejectAlert() {

        setLogString(Marker.START, "Close Native alerts", true);
        setLogString("Check and close alert if any.", true);
        try {
            driverOps.switchToNative();
            if (driverOps.getDeviceDriver().findElement(By.linkText("Cancel")) != null) {
                setLogString("Click Cancel button", true);
                driverOps.getDeviceDriver().findElement(By.linkText("Cancel")).click();
            }
        } catch (Exception ex) {
            setLogString("No alert popup.", true);
        } finally {

            driverOps.switchToWebView();
        }
        setLogString(Marker.END, "Completed", true);
    }

    /**
     * Click try again button that occurs after login success.
     * @see com.ecofactor.qa.automation.platform.action.UIAction#clickTryAgain()
     */
    @Override
    public void clickTryAgain() {

        // TODO Auto-generated method stub

    }

    /**
     * get window width.
     * @param element
     * @return integer.
     * @see com.ecofactor.qa.automation.platform.action.UIAction#getWindowWidth(org.openqa.selenium.WebElement)
     */
    @Override
    public int getWindowWidth(final WebElement element) {

        saveTestFlowScreenShot();
        JavascriptExecutor js = null;
        if (driverOps.getDeviceDriver() instanceof JavascriptExecutor) {

            js = (JavascriptExecutor) driverOps.getDeviceDriver();
        }
        final String javaScript = "return screen.width";
        long widthValue = (long) js.executeScript(javaScript);
        final int width = (int) widthValue;
        setLogString("width" + width, true);
        return width;
    }

    /**
     * get window height.
     * @param element
     * @return integer.
     * @see com.ecofactor.qa.automation.platform.action.UIAction#getWindowHeight(org.openqa.selenium.WebElement)
     */
    @Override
    public int getWindowHeight(WebElement element) {

        saveTestFlowScreenShot();
        JavascriptExecutor js = null;
        if (driverOps.getDeviceDriver() instanceof JavascriptExecutor) {

            js = (JavascriptExecutor) driverOps.getDeviceDriver();
        }
        final String javaScript = "return screen.height";
        long heightValue = (long) js.executeScript(javaScript);
        final int height = (int) heightValue;
        setLogString("height" + height, true);
        return height;
    }

    /**
     * Do swipe left.
     * @param element the element
     * @see com.ecofactor.qa.automation.mobile.action.UIAction#doSwipeLeft(org.openqa.selenium.WebElement)
     */
    @Override
    public void doSwipeLeft(final WebElement element) {

        saveTestFlowScreenShot();
        swipe(element, element.getLocation().x, element.getLocation().y, 1.0, Direction.LEFT);
    }

}
