/*
 * IOSUIAction.java
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

import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.touch.TouchActions;

/**
 * The Class IOSUIAction.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class IOSUIAction extends AbstractMobileUIAction {

    /**
     * Swipe.
     * @param element the element
     * @param xCoordinate the xCoordinate
     * @param yCoordinate the yCoordinate
     * @param distance the distance
     * @param speed the speed
     * @param direction the direction
     * @see com.ecofactor.qa.automation.mobile.action.impl.AbstractMobileUIAction#swipe(double,
     *      double, double, double,
     *      com.ecofactor.qa.automation.mobile.action.AbstractMobileUIAction.Direction)
     */
    @SuppressWarnings("serial")
    @Override
    protected void swipe(final WebElement element, final double xCoordinate,
            final double yCoordinate, final double distance, final double speed,
            final Direction direction) {
        
        final double endX = getEndX(xCoordinate, distance, direction);
        final double endY = getEndY(yCoordinate, distance, direction);

        ((JavascriptExecutor) driverOps.getDeviceDriver()).executeScript("mobile: swipe",
                new HashMap<String, Double>() {
                    {
                        put("touchCount", 1.0);
                        put("startX", xCoordinate);
                        put("startY", yCoordinate);
                        put("endX", endX);
                        put("endY", endY);
                        put("duration", speed);
                    }
                });
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

        final double xOffset = (double) (getEndX(xCoordinate, getWindowWidth(element), direction));

        final double yOffset = (double) (getEndY(yCoordinate, getWindowHeight(element), direction));
        final double speedValue = (double) speed;

        /*
         * final TouchActions flick = new TouchActions(driverOps.getDeviceDriver()).flick(element,
         * xOffset, yOffset, speedValue); flick.perform();
         * setLogString("Save mobile swipe screenshot for test: " + getTestName(), true);
         */
        ((JavascriptExecutor) driverOps.getDeviceDriver()).executeScript("mobile: swipe",
                new HashMap<String, Double>() {
                    {
                        put("touchCount", 1.0);
                        put("startX", xOffset);
                        put("startY", yOffset);
                        put("endX", 0.0);
                        put("endY", 0.0);
                        put("duration", speedValue);
                    }
                });
    }

    /**
     * Accept alert.
     * @see com.ecofactor.qa.automation.mobile.action.impl.AbstractMobileUIAction#acceptAlert()
     */
    @Override
    public void acceptAlert() {

        driverOps.switchToNative();
        setLogString("Click OK button", true);
        driverOps.getDeviceDriver().findElement(By.name("OK")).click();
        mediumWait();
    }

    /**
     * Do Tap.
     * @param element the element
     * @see com.ecofactor.qa.automation.platform.action.UIAction#doTap(org.openqa.selenium.WebElement)
     */
    @Override
    public void doTap(final WebElement element) {

        final JavascriptExecutor js = (JavascriptExecutor) driverOps.getDeviceDriver();
        final HashMap<String, Double> tapObject = new HashMap<String, Double>();
        tapObject.put("x", (double) element.getLocation().getX());
        tapObject.put("y", (double) element.getLocation().getY());
        js.executeScript("mobile: tap", tapObject);
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
    public void doSwipeDown(final WebElement element, final double xCoordinate,
            final double yCoordinate, final double distance, final double speed) {

        setLogString("Swipe Down", true);
        swipe(element, xCoordinate, yCoordinate, distance, speed, Direction.DOWN);
    }

    /**
     * Drag element.
     * @param srcElement the src element
     * @param desElement the des element
     * @see com.ecofactor.qa.automation.platform.action.UIAction#dragElement(org.openqa.selenium.WebElement,
     *      org.openqa.selenium.WebElement)
     */
    @Override
    public void dragElement(final WebElement srcElement, final Integer xOffset,
            final Integer yOffset) {

        setLogString("Click and Hold in IOS is Currently InProgress....", true);
    }

    /**
     * Drop element.
     * @param srcElement the src element
     * @see com.ecofactor.qa.automation.platform.action.UIAction#dropElement(org.openqa.selenium.WebElement)
     */
    @Override
    public void dropElement(final WebElement srcElement) {

        setLogString("Drop in IOS is Currently InProgress....", true);
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

        setLogString("Swipe Up", true);
        swipe(element, xCoordinate, yCoordinate, distance, speed, Direction.UP);

    }

    /**
     * @see com.ecofactor.qa.automation.platform.action.UIAction#rejectAlert()
     */
    @Override
    public void rejectAlert() {

        // Not required to be handled in IOS.
    }

    /**
     * @see com.ecofactor.qa.automation.platform.action.UIAction#clickTryAgain()
     */
    @Override
    public void clickTryAgain() {

        // Auto-generated method stub

    }

    @Override
    public int getWindowWidth(WebElement element) {

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
        swipe(element, getWindowWidth(element), (getWindowHeight(element)) / 2,
                getWindowWidth(element), 1.0, Direction.RIGHT);
    }   
   
}
