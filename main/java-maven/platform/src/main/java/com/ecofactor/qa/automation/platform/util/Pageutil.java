/*
 * Pageutil.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.platform.util;

import static com.ecofactor.qa.automation.platform.enums.CustomTimeout.*;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.ecofactor.qa.automation.platform.enums.CustomTimeout;

/**
 * The Class Pageutil.
 * @author $Author: vprasannaa $
 * @version $Rev: 32283 $ $Date: 2014-10-15 12:19:58 +0530 (Wed, 15 Oct 2014) $
 */
public final class Pageutil {

    /**
     * Instantiates a new pageutil.
     */
    private Pageutil() {

    }

    /**
     * Clear the web element data and input new field value
     * @param driver the driver
     * @param locator the locator
     * @param fieldValue the field value
     */
    public static void clearAndInput(final WebDriver driver, final By locator,
            final String fieldValue) {

        final WebElement element = getElement(driver, locator, SHORT_TIMEOUT);
        element.clear();
        element.sendKeys(fieldValue);
    }

    /**
     * Checks if the given element is displayed.
     * @param driver the driver
     * @param locator the locator
     * @param timeout the timeout
     * @return true, if is displayed
     */
    public static boolean isDisplayed(final WebDriver driver, final By locator,
            final CustomTimeout timeout) {

        boolean displayed = true;
        try {
            final WebDriverWait wait = new WebDriverWait(driver, timeout.getValue());
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (TimeoutException te) {
            displayed = false;
        }
        return displayed;
    }

    /**
     * Checks if the given element is clickable.
     * @param driver the driver
     * @param locator the locator
     * @param timeout the timeout
     * @return true, if is clickable
     */
    public static boolean isClickable(final WebDriver driver, final WebElement element,
            final CustomTimeout timeout) {

        boolean displayed = true;
        try {
            final WebDriverWait wait = new WebDriverWait(driver, timeout.getValue());
            wait.until(ExpectedConditions.elementToBeClickable(element));
        } catch (TimeoutException te) {
            displayed = false;
        }
        return displayed;
    }

    /**
     * Checks if the given element is not displayed.
     * @param driver the driver
     * @param locator the locator
     * @param timeout the timeout
     * @return true, if is not displayed
     */
    public static boolean isNotDisplayed(final WebDriver driver, final By locator,
            final CustomTimeout timeout) {

        boolean notDisplayed = true;
        try {
            final WebDriverWait wait = new WebDriverWait(driver, timeout.getValue());
            wait.until(ExpectedConditions.not(ExpectedConditions
                    .visibilityOfElementLocated(locator)));
        } catch (final WebDriverException te) {
            notDisplayed = false;
        }
        return notDisplayed;
    }

    /**
     * Checks if the sub element displayed.
     * @param driver the driver
     * @param subElement the sub element
     * @param locator the locator
     * @param timeout the timeout
     * @return true, if is displayed
     */
    public static boolean isDisplayedBySubElement(final WebDriver driver,
            final WebElement subElement, final By locator, final CustomTimeout timeout) {

        boolean displayed = true;
        try {
            final WebDriverWait wait = new WebDriverWait(driver, timeout.getValue());
            wait.until(ExpectedConditions.visibilityOf(subElement.findElement(locator)));
        } catch (Exception te) {
            displayed = false;
        }
        return displayed;
    }

    /**
     * Checks if is displayed by list.
     * @param driver the driver
     * @param locator the locator
     * @param timeout the timeout
     * @return true, if is displayed by list
     */
    public static boolean isDisplayedByList(final WebDriver driver, final By locator,
            final CustomTimeout timeout) {

        boolean isLoaded = false;
        try {
            isLoaded = new WebDriverWait(driver, timeout.getValue())
                    .until(new ExpectedCondition<Boolean>() {
                        public Boolean apply(final WebDriver driver) {

                            final List<WebElement> elements = driver.findElements(locator);
                            boolean displayed = false;
                            for (final WebElement element : elements) {
                                displayed = element.isDisplayed();
                                if (displayed) {
                                    break;
                                }
                            }
                            return displayed;
                        }
                    });
        } catch (TimeoutException te) {
            isLoaded = false;
        }
        return isLoaded;
    }

    /**
     * Gets the element.
     * @param driver the driver
     * @param locator the locator
     * @param timeout the timeout
     * @return the element
     */
    public static WebElement getElement(final WebDriver driver, final By locator,
            final CustomTimeout timeout) {

        try {
            return new WebDriverWait(driver, timeout.getValue())
                    .until(new ExpectedCondition<WebElement>() {
                        public WebElement apply(final WebDriver driver) {

                            return driver.findElement(locator);
                        }
                    });
        } catch (TimeoutException timeOutException) {
            return null;
        }
    }

    /**
     * Get the list of elements.
     * @param driver the driver
     * @param locator the locator
     * @param timeout the timeout
     * @return the elements
     */
    public static List<WebElement> getElements(final WebDriver driver, final By locator,
            final CustomTimeout timeout) {

        return new WebDriverWait(driver, timeout.getValue())
                .until(new ExpectedCondition<List<WebElement>>() {
                    public List<WebElement> apply(final WebDriver driver) {

                        return driver.findElements(locator);
                    }
                });
    }

    /**
     * Gets the element by text.
     * @param driver the driver
     * @param locator the locator
     * @param textValue the text value
     * @param timeout the timeout
     * @return the element by text
     */
    public static WebElement getElementByText(final WebDriver driver, final By locator,
            final String textValue, final CustomTimeout timeout) {

        WebElement elementAttrValue = null;
        final List<WebElement> elementList = getElements(driver, locator, timeout);
        for (final WebElement webElement : elementList) {
            final String valueAttribute = webElement.getText().trim();
            if (valueAttribute.trim().equalsIgnoreCase(textValue)) {
                elementAttrValue = webElement;
            }
        }
        return elementAttrValue;
    }

    /**
     * Gets the element by the containing text.
     * @param driver the driver
     * @param locator the locator
     * @param textValue the text value
     * @param timeout the timeout
     * @return the element by text contains
     */
    public static WebElement getElementByTextContains(final WebDriver driver, final By locator,
            final String textValue, final CustomTimeout timeout) {

        WebElement elementAttrValue = null;
        final List<WebElement> elementList = getElements(driver, locator, timeout);
        for (final WebElement webElement : elementList) {
            final String valueAttribute = webElement.getText().trim();
            if (webElement.isDisplayed() && webElement.isEnabled()
                    && valueAttribute.trim().contains(textValue)) {
                elementAttrValue = webElement;
            }
        }
        return elementAttrValue;
    }

    /**
     * Gets the elements by text.
     * @param driver the driver
     * @param locator the locator
     * @param textValue the text value
     * @param timeout the timeout
     * @return the elements by text
     */
    public static List<WebElement> getElementsByText(final WebDriver driver, final By locator,
            final String textValue, final CustomTimeout timeout) {

        final List<WebElement> eleList = new ArrayList<>();
        for (final WebElement webElement : getElements(driver, locator, timeout)) {
            final String valueAttribute = webElement.getText().trim();
            if (valueAttribute.trim().equalsIgnoreCase(textValue)) {
                eleList.add(webElement);
            }
        }
        return eleList;
    }

    /**
     * Gets the element by its attr.
     * @param driver the driver
     * @param locator the locator
     * @param attributeName the attribute name
     * @param attributeValue the attribute value
     * @param timeout the timeout
     * @return the element by attr
     */
    public static WebElement getElementByAttr(final WebDriver driver, final By locator,
            final String attributeName, final String attributeValue, final CustomTimeout timeout) {

        WebElement elementAttrValue = null;
        for (final WebElement webElement : getElements(driver, locator, timeout)) {
            final String valueAttribute = webElement.getAttribute(attributeName);
            if (valueAttribute.trim().equalsIgnoreCase(attributeValue)) {
                elementAttrValue = webElement;
            }
        }
        return elementAttrValue;
    }

    /**
     * Gets the element by attr contains.
     * @param driver the driver
     * @param locator the locator
     * @param attributeValue the attribute value
     * @param attributeName the attribute name
     * @param timeout the timeout
     * @return the element by attr contains
     */
    public static WebElement getElementByAttrContains(final WebDriver driver, final By locator,
            final String attributeValue, final String attributeName, final CustomTimeout timeout) {

        WebElement elementAttrValue = null;
        for (final WebElement webElement : getElements(driver, locator, timeout)) {
            final String valueAttribute = webElement.getAttribute(attributeName);
            if (valueAttribute.trim().contains(attributeValue)) {
                elementAttrValue = webElement;
            }
        }
        return elementAttrValue;
    }

    /**
     * Gets the element by sub element.
     * @param driver the driver
     * @param subElement the sub element
     * @param locator the locator
     * @param timeout the timeout
     * @return the element by sub element
     */
    public static WebElement getElementBySubElement(final WebDriver driver,
            final WebElement subElement, final By locator, final CustomTimeout timeout) {

        return new WebDriverWait(driver, timeout.getValue())
                .until(new ExpectedCondition<WebElement>() {
                    public WebElement apply(final WebDriver driver) {

                        if (subElement.findElement(locator) != null) {
                            return subElement.findElement(locator);
                        }
                        return null;
                    }
                });
    }

    /**
     * Gets the element by sub element text.
     * @param driver the driver
     * @param subElement the sub element
     * @param locator the locator
     * @param textValue the text value
     * @param timeout the timeout
     * @return the element by sub element text
     */
    public static WebElement getElementBySubElementText(final WebDriver driver,
            final WebElement subElement, final By locator, final String textValue,
            final CustomTimeout timeout) {

        WebElement elementAttrValue = null;
        final List<WebElement> elementList = getElementsBySubElement(driver, subElement, locator,
                timeout);
        for (final WebElement webElement : elementList) {
            final String valueAttribute = webElement.getText();
            if (valueAttribute.trim().equalsIgnoreCase(textValue)) {
                elementAttrValue = webElement;
            }
        }
        return elementAttrValue;
    }

    /**
     * Gets the element by sub element attr.
     * @param driver the driver
     * @param subElement the sub element
     * @param locator the locator
     * @param attributeName the attribute name
     * @param attributeValue the attribute value
     * @param timeout the timeout
     * @return the element by sub element attr
     */
    public static WebElement getElementBySubElementAttr(final WebDriver driver,
            final WebElement subElement, final By locator, final String attributeName,
            final String attributeValue, final CustomTimeout timeout) {

        WebElement elementAttrValue = null;
        final List<WebElement> elementList = getElementsBySubElement(driver, subElement, locator,
                timeout);
        for (final WebElement webElement : elementList) {
            final String valueAttribute = webElement.getAttribute(attributeName);
            if (valueAttribute.trim().equalsIgnoreCase(attributeValue)) {
                elementAttrValue = webElement;
            }
        }
        return elementAttrValue;
    }    

    /**
     * Gets the elements by sub element.
     * @param driver the driver
     * @param subElement the sub element
     * @param locator the locator
     * @param timeout the timeout
     * @return the elements by sub element
     */
    public static List<WebElement> getElementsBySubElement(final WebDriver driver,
            final WebElement subElement, final By locator, final CustomTimeout timeout) {

        return new WebDriverWait(driver, timeout.getValue())
                .until(new ExpectedCondition<List<WebElement>>() {
                    public List<WebElement> apply(final WebDriver driver) {

                        return subElement.findElements(locator);
                    }
                });
    }

    /**
     * Gets the elements by sub element text.
     * @param driver the driver
     * @param subElement the sub element
     * @param locator the locator
     * @param textValue the text value
     * @param timeout the timeout
     * @return the elements by sub element text
     */
    public static List<WebElement> getElementsBySubElementText(final WebDriver driver,
            final WebElement subElement, final By locator, final String textValue,
            final CustomTimeout timeout) {

        final List<WebElement> eleList = new ArrayList<>();
        final List<WebElement> elementList = getElementsBySubElement(driver, subElement, locator,
                timeout);
        for (final WebElement webElement : elementList) {
            final String valueAttribute = webElement.getText().trim();
            if (valueAttribute.trim().equalsIgnoreCase(textValue)) {
                eleList.add(webElement);
            }
        }
        return eleList;
    }

    /**
     * Execute script by class name.
     * @param name the name
     * @param propertyName the property name
     * @param driver the driver
     * @return the object
     */
    public static Object executeScriptByClassName(final String name, final String propertyName,
            final WebDriver driver) {

        final JavascriptExecutor javascript = (JavascriptExecutor) driver;
        final Object val = javascript
                .executeScript(new StringBuilder(
                        "return window.document.defaultView.getComputedStyle(window.document.getElementsByClassName('")
                        .append(name).append("')[0]).getPropertyValue('").append(propertyName)
                        .append("');").toString());

        return val;
    }

    /**
     * Execute script by class name with pseudo.
     * @param name the name
     * @param propertyName the property name
     * @param driver the driver
     * @return the object
     */
    public static Object executeScriptByClassNameWithPseudo(final String name,
            final String propertyName, final WebDriver driver) {

        Object val;
        try {
            final JavascriptExecutor javascript = (JavascriptExecutor) driver;
            val = javascript
                    .executeScript(new StringBuilder(
                            "return window.document.defaultView.getComputedStyle(window.document.getElementsByClassName('")
                            .append(name).append("')[0], ':before').getPropertyValue('")
                            .append(propertyName).append("');").toString());
        } catch (Exception ex) {
            val = null;
        }

        return val;
    }

    /**
     * Wait for page loaded.
     * @param driver the driver
     */
    public static void waitForPageLoaded(final WebDriver driver) {

        final ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
            public Boolean apply(final WebDriver driver) {

                return ((JavascriptExecutor) driver).executeScript("return document.readyState")
                        .equals("complete");
            }
        };
        final Wait<WebDriver> wait = new WebDriverWait(driver, 120);
        try {
            wait.until(expectation);
        } catch (Throwable error) {
            LogUtil.setLogString("Page load takes more time", true);
        }
    }
}
