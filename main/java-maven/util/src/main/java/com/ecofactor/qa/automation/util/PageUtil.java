/*
 * PageUtil.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.util;

import static com.ecofactor.qa.automation.util.PageUtil.SHORT_TIMEOUT;
import static com.ecofactor.qa.automation.util.PageUtil.isDisplayedById;
import static com.ecofactor.qa.automation.util.PageUtil.isDisplayedByXpath;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



// TODO: Auto-generated Javadoc
/**
 * PageUtil is a utility helper class to perform action or validate data in a page.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class PageUtil {

    /** The logger. */
    private static Logger logger = LoggerFactory.getLogger(PageUtil.class);
    // private static WebDriver driver;

    /** The Constant SHORT_TIMEOUT. */
    public static final int SHORT_TIMEOUT = 30;
    
    /** The Constant MEDIUM_TIMEOUT. */
    public static final int MEDIUM_TIMEOUT = 60;
    
    /** The Constant LONG_TIMEOUT. */
    public static final int LONG_TIMEOUT = 120;
    
    /** The Constant VERY_LONG_TIMEOUT. */
    public static final int VERY_LONG_TIMEOUT = 300;

    /* Tag Names */
    /** The Constant TAG_INPUT. */
    public final static String TAG_INPUT = "input";
    
    /** The Constant TAG_ANCHOR. */
    public final static String TAG_ANCHOR = "a";
    
    /** The Constant TAG_DIV. */
    public final static String TAG_DIV = "div";
    
    /** The Constant TAG_SPAN. */
    public final static String TAG_SPAN = "span";
    
    /** The Constant TAG_LI. */
    public final static String TAG_LI = "li";
    
    /** The Constant TAG_TABLE. */
    public final static String TAG_TABLE = "table";
    
    /** The Constant TAG_BOLD. */
    public final static String TAG_BOLD = "b";
    
    /** The Constant TAG_BUTTON. */
    public final static String TAG_BUTTON = "button";
    
    /** The Constant TAG_TD. */
    public final static String TAG_TD = "td";
    
    /** The Constant TAG_TEXTAREA. */
    public final static String TAG_TEXTAREA = "textarea";
    
    /** The Constant TAG_IFRAME. */
    public final static String TAG_IFRAME = "iframe";
    
    /** The Constant TAG_BTN. */
    public final static String TAG_BTN = "button";
    
    /** The Constant TAG_H4. */
    public final static String TAG_H4 = "h4";
    
    /** The Constant TAG_H2. */
    public final static String TAG_H2 = "h2";
    
    /** The Constant TAG_TBODY. */
    public final static String TAG_TBODY = "tbody";
    
    /** The Constant TAG_STRONG. */
    public final static String TAG_STRONG = "strong";
    
    /** The Constant TAG_IMG. */
    public final static String TAG_IMG = "img";
    
    /** The Constant TAG_UL. */
    public final static String TAG_UL = "ul";
    
    /** The Constant TAG_P. */
    public final static String TAG_P = "p";
    
    /** The Constant TAG_OL. */
    public final static String TAG_OL = "ol";
    
    /** The Constant TAG_LABEL. */
    public final static String TAG_LABEL = "label";

    /* Attribute Names */
    /** The Constant ATTR_VALUE. */
    public final static String ATTR_VALUE = "value";
    
    /** The Constant ATTR_TITLE. */
    public final static String ATTR_TITLE = "title";
    
    /** The Constant ATTR_CLASS. */
    public final static String ATTR_CLASS = "class";
    
    /** The Constant ATTR_ID. */
    public final static String ATTR_ID = "id";
    
    /** The Constant ATTR_HREF. */
    public final static String ATTR_HREF = "href";
    
    /** The Constant ATTR_BLUE. */
    public final static String ATTR_BLUE = "blue";
    
    /** The Constant ATTR_RED. */
    public final static String ATTR_RED = "red";
    
    /** The Constant ATTR_DISABLED. */
    public final static String ATTR_DISABLED = "disabled";
    
    /** The Constant ATTR_TYPE. */
    public final static String ATTR_TYPE = "type";
    
    /** The Constant ATTR_NAME. */
    public final static String ATTR_NAME = "name";
    
    /** The Constant ATTR_SRC. */
    public final static String ATTR_SRC = "src";

    /**
     * Sets the driver.
     *
     * @param driver the driver
     * @param fieldname the fieldname
     * @param timeOut the time out
     * @return true, if is displayed by id
     */

    /**
     * Utility to check weather the particular field is displayed or not.
     * @param driver the driver
     * @param fieldname the fieldname
     * @param timeOut the time out
     * @return boolean
     */
    public static boolean isDisplayedById(final WebDriver driver, final String fieldname, final int timeOut) {

        boolean isLoaded = false;
        try {
            isLoaded = (new WebDriverWait(driver, timeOut)).until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver d) {

                    WebElement element = d.findElement(By.id(fieldname));
                    return element.isDisplayed();
                }
            });
        } catch (TimeoutException te) {
            isLoaded = false;
        }
        return isLoaded;
    }

    /**
     * Utility to check weather the particular field is displayed or not.
     * @param driver the driver
     * @param cssSelector the css selector
     * @param timeOut the time out
     * @return boolean
     */
    public static boolean isDisplayedByCSS(final WebDriver driver, final String cssSelector, final int timeOut) {

        boolean isLoaded = false;

        try {
            isLoaded = (new WebDriverWait(driver, timeOut)).until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver d) {

                    List<WebElement> elements = d.findElements(By.cssSelector(cssSelector));
                    boolean displayed = false;
                    for (WebElement element : elements) {
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
     * Sets the value.
     * @param driver the driver
     * @param cssSelector the css selector
     * @param value the value
     */
    public static void setValueByCSS(WebDriver driver, String cssSelector, String value) {

        if (isDisplayedByCSS(driver, cssSelector, 10)) {
            List<WebElement> inputs = driver.findElements(By.cssSelector(cssSelector));
            for (WebElement input : inputs) {
                if (input.isDisplayed() && input.isEnabled()) {
                    input.clear();
                    input.sendKeys(value);
                }
            }
        }
    }

    /**
     * Sets the value.
     * @param driver the driver
     * @param cssSelector the css selector
     * @param value the value
     * @param index the index
     */
    public static void setValueByCSS(WebDriver driver, String cssSelector, String value, int index) {

        if (isDisplayedByCSS(driver, cssSelector, 10)) {
            List<WebElement> inputs = driver.findElements(By.cssSelector(cssSelector));
            int count = 0;
            for (WebElement input : inputs) {
                if (input.isDisplayed() && input.isEnabled()) {

                    if (count == index) {
                        input.clear();
                        input.sendKeys(value);
                        logger.info("Count " + count + ", " + input.getAttribute("id"));
                        break;
                    }

                    ++count;
                }
            }
        }
    }

    /**
     * Sets the value.
     * @param driver the driver
     * @param id the id
     * @param value the value
     */
    public static void setValue(WebDriver driver, String id, String value) {

        if (isDisplayedById(driver, id, 10)) {
            WebElement input = driver.findElement(By.id(id));
            input.clear();
            input.sendKeys(value);
        }
    }

    /**
     * Utility to check weather the particular field is enabled or not.
     * @param driver the driver
     * @param fieldname the fieldname
     * @param timeOut the time out
     * @return boolean
     */
    public static boolean isEnabledById(final WebDriver driver, final String fieldname, final int timeOut) {

        boolean isLoaded = false;
        isLoaded = (new WebDriverWait(driver, timeOut)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {

                return d.findElement(By.id(fieldname)).isEnabled();
            }
        });
        return isLoaded;
    }

    /**
     * Utility to check weather the particular field is enabled by class name or not.
     * @param driver the driver
     * @param className the class name
     * @param timeOut the time out
     * @return boolean
     */
    public static boolean isEnabledByClassName(final WebDriver driver, final String className, final int timeOut) {

        boolean isLoaded = false;
        isLoaded = (new WebDriverWait(driver, timeOut)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {

                return d.findElement(By.className(className)).isEnabled();
            }
        });
        return isLoaded;
    }

    /**
     * Utility to check weather the particular field is enabled by subelement or not.
     * @param driver the driver
     * @param subElement the sub element
     * @param fieldname the fieldname
     * @param timeOut the time out
     * @return boolean
     */
    public static boolean isEnabledByIdSubElement(final WebDriver driver, final WebElement subElement, final String fieldname, final int timeOut) {

        boolean isLoaded = false;
        isLoaded = (new WebDriverWait(driver, timeOut)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {

                return subElement.findElement(By.id(fieldname)).isEnabled();
            }
        });
        return isLoaded;
    }

    /**
     * Utility to check weather the particular field is displayed or not.
     * @param driver the driver
     * @param tagName the tag name
     * @param timeOut the time out
     * @return boolean
     */
    public static boolean isDisplayedByTagName(final WebDriver driver, final String tagName, final int timeOut) {

        boolean isLoaded = false;
        isLoaded = (new WebDriverWait(driver, timeOut)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {

                return d.findElement(By.tagName(tagName)).isDisplayed();
            }
        });
        return isLoaded;
    }

    /**
     * <p>
     * Utility to check weather the particular field has the specified style class name.
     * </p>
     * @param driver the driver
     * @param fieldname the fieldname
     * @param ClassName the class name
     * @param timeOut the time out
     * @return true, if is element contains class name
     */
    public static boolean isElementContainsClassName(final WebDriver driver, final String fieldname, final String ClassName, final int timeOut) {

        boolean isLoaded = false;
        isLoaded = (new WebDriverWait(driver, timeOut)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {

                return d.findElement(By.id(fieldname)).getAttribute("className").equalsIgnoreCase(ClassName);
            }
        });
        return isLoaded;
    }

    /**
     * Select the option by value.
     * @param formElement the form element
     * @param selectId the select id
     * @param selectValue the select value
     */
    public static void selectOptionByValue(final WebElement formElement, final String selectId, final String selectValue) {

        logger.info("Select id:" + selectId + " ; selectValue" + selectValue);
        Select select = new Select(formElement.findElement(By.id(selectId)));
        select.selectByValue(selectValue);
    }

    /**
     * Select option by value.
     * @param driver the driver
     * @param selectId the select id
     * @param selectValue the select value
     */
    public static void selectOptionByValue(final WebDriver driver, final String selectId, final String selectValue) {

        logger.info("Select id:" + selectId + " ; selectValue" + selectValue);
        Select select = new Select(driver.findElement(By.id(selectId)));
        select.selectByValue(selectValue);
    }

    /**
     * Select option by value.
     * @param driver the driver
     * @param cssSelector the css selector
     * @param selectValue the select value
     */
    public static void selectOptionValueByCSS(final WebDriver driver, final String cssSelector, final String selectValue) {

        logger.info("Select css selector:" + cssSelector + " ; selectValue" + selectValue);
        List<WebElement> elements = driver.findElements(By.cssSelector(cssSelector));
        for (WebElement element : elements) {
            if (element.isDisplayed() && element.isEnabled()) {
                Select select = new Select(element);
                select.selectByValue(selectValue);
            }
        }
    }

    /**
     * Utility to check weather the particular field is displayed or not.
     * @param driver the driver
     * @param fieldname the fieldname
     * @param timeOut the time out
     * @return boolean
     */
    public static boolean isNotDisplayedById(final WebDriver driver, final String fieldname, int timeOut) {

        boolean isLoaded = false;
        isLoaded = (new WebDriverWait(driver, timeOut)).until(ExpectedConditions.invisibilityOfElementLocated(By.id(fieldname)));

        return isLoaded;
    }

    /**
     * Loading the particular task is completed.
     * @param driver the driver
     * @param loadingId the loading id
     * @param timeOut the time out
     */
    public static void loadingProgress(final WebDriver driver, final String loadingId, final int timeOut) {

        DriverConfig.setLogString("Check Progress Indicator", true);
        isDisplayedById(driver, loadingId, timeOut);
        DriverConfig.setLogString("Check No Progress Indicator", true);
        isNotDisplayedById(driver, loadingId, timeOut);
    }

    /**
     * Verify and Load the URL.
     * @param driver the driver
     * @param url the url
     */
    public static void loadURL(final WebDriver driver, final String url) {

        driver.get(url);
    }

    /**
     * Is particular element displayed by class Name. . .
     * @param driver the driver
     * @param fieldname the fieldname
     * @param timeOut the time out
     * @return boolean
     */
    public static boolean isDisplayedByClassName(final WebDriver driver, final String fieldname, final int timeOut) {

        boolean isLoaded = false;
        try {
            isLoaded = (new WebDriverWait(driver, timeOut)).until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver d) {

                    return d.findElement(By.className(fieldname)).isDisplayed();
                }
            });
        } catch (TimeoutException te) {
            isLoaded = false;
        }
        return isLoaded;
    }
    
    
    /**
     * Checks if is displayed by xpath.
     *
     * @param driver the driver
     * @param fieldname the fieldname
     * @param timeOut the time out
     * @return true, if is displayed by xpath
     */
    public static boolean isDisplayedByXpath(final WebDriver driver, final String fieldname, final int timeOut) {

        boolean isLoaded = false;
        try {
            isLoaded = (new WebDriverWait(driver, timeOut)).until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver d) {

                    return d.findElement(By.xpath(fieldname)).isDisplayed();
                }
            });
        } catch (TimeoutException te) {
            isLoaded = false;
        }
        return isLoaded;
    }

    /**
     * Is elements displayed by class Name. . .
     * @param driver the driver
     * @param fieldname the fieldname
     * @param timeOut the time out
     * @return boolean
     */
    public static boolean isMultipleClassNameDisplayed(final WebDriver driver, final String fieldname, final int timeOut) {

        boolean isLoaded = false;
        try {
			isLoaded = (new WebDriverWait(driver, timeOut)).until(new ExpectedCondition<Boolean>() {
			    public Boolean apply(WebDriver d) {

			        List<WebElement> scheduleChartElmtList = driver.findElements(By.className(fieldname));
			        if (scheduleChartElmtList.size() > 0) {
			        	
			            return true;
			        }
			        return false;
			    }
			});
		} catch (TimeoutException te) {
			 isLoaded = false;
		}

        return isLoaded;
    }

    /**
     * Multiple elements displayed by Tag Name.
     * @param driver the driver
     * @param fieldname the fieldname
     * @param timeOut the time out
     * @return boolean
     */
    public static boolean isMultipleTagNameDisplayed(final WebDriver driver, final String fieldname, final int timeOut) {

        boolean isLoaded = false;
        isLoaded = (new WebDriverWait(driver, timeOut)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {

                List<WebElement> elmtList = driver.findElements(By.tagName(fieldname));
                boolean returnValue = false;
                returnValue = elmtList != null && elmtList.size() > 0 ? true : false;
                return returnValue;
            }
        });

        return isLoaded;
    }

    /**
     * Is elements displayed by Tag Name. . .
     * @param driver the driver
     * @param element the element
     * @param fieldname the fieldname
     * @param timeOut the time out
     * @return boolean
     */
    public static boolean isElementTagNameDisplayed(final WebDriver driver, final WebElement element, final String fieldname, final int timeOut) {

        boolean isLoaded = false;
        isLoaded = (new WebDriverWait(driver, timeOut)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {

                List<WebElement> elmtList = element.findElements(By.tagName(fieldname));
                if (elmtList.size() > 0) {
                    return true;
                }
                return false;
            }
        });

        return isLoaded;
    }

    /**
     * Is form elements Id displayed by Tag Name. . .
     * @param driver the driver
     * @param element the element
     * @param fieldname the fieldname
     * @param timeOut the time out
     * @return boolean
     */
    public static boolean isElementIdDisplayed(final WebDriver driver, final WebElement element, final String fieldname, final int timeOut) {

        boolean isLoaded = false;
        isLoaded = (new WebDriverWait(driver, timeOut)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {

                List<WebElement> elmtList = element.findElements(By.id(fieldname));
                if (elmtList.size() > 0) {
                    return true;
                }
                return false;
            }
        });

        return isLoaded;
    }

    /**
     * Is elements displayed by Tag Name. . .
     * @param driver the driver
     * @param element the element
     * @param fieldname the fieldname
     * @param timeOut the time out
     * @return boolean
     */
    public static boolean isFormDisplayedById(final WebDriver driver, final WebElement element, final String fieldname, final int timeOut) {

        boolean isLoaded = false;
        isLoaded = (new WebDriverWait(driver, timeOut)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {

                if (element.findElement(By.id(fieldname)).isDisplayed()) {
                    return true;
                }
                return false;

            }
        });

        return isLoaded;
    }

    /**
     * Checks if is element exists by id.
     * @param driver the driver
     * @param element the element
     * @param fieldname the fieldname
     * @param timeOut the time out
     * @return true, if is element exists by id
     */
    public static boolean isElementExistsById(final WebDriver driver, final WebElement element, final String fieldname, final int timeOut) {

        boolean isLoaded = false;
        try {
            isLoaded = (new WebDriverWait(driver, timeOut)).until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver d) {

                    WebElement element1 = element.findElement(By.id(fieldname));
                    return element1.isDisplayed();
                }
            });
        } catch (TimeoutException te) {
            isLoaded = false;
        }
        return isLoaded;
    }

    /**
     * Checks if is form displayed by class name.
     * @param driver the driver
     * @param element the element
     * @param fieldname the fieldname
     * @param timeOut the time out
     * @return true, if is form displayed by class name
     */
    public static boolean isFormDisplayedByClassName(final WebDriver driver, final WebElement element, final String fieldname, final int timeOut) {

        boolean isLoaded = false;
        isLoaded = (new WebDriverWait(driver, timeOut)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {

                if (element.findElement(By.className(fieldname)).isDisplayed()) {
                    return true;
                }
                return false;

            }
        });

        return isLoaded;
    }

    /**
     * Select the option by text.
     * @param formElement the form element
     * @param selectId the select id
     * @param selectText the select text
     */
    public static void selectOptionByText(final WebElement formElement, final String selectId, final String selectText) {

        logger.info("Select id:" + selectId + " ; selectText" + selectText);
        Select select = new Select(formElement.findElement(By.id(selectId)));
        select.selectByVisibleText(selectText);
    }

    /**
     * Select option by text.
     * @param driver the driver
     * @param selectId the select id
     * @param selectText the select text
     */
    public static void selectOptionByText(final WebDriver driver, final String selectId, final String selectText) {

        logger.info("Select id:" + selectId + " ; selectText" + selectText);
        Select select = new Select(driver.findElement(By.id(selectId)));
        select.selectByVisibleText(selectText);
    }

    /**
     * Select the option by text according to name.
     * @param formElement the form element
     * @param selectId the select id
     * @param selectText the select text
     */
    public static void selectOptionByTextbyName(final WebElement formElement, final String selectId, final String selectText) {

        logger.info("Select id:" + selectId + " ; selectText" + selectText);
        Select select = new Select(formElement.findElement(By.name(selectId)));
        select.selectByVisibleText(selectText);
    }

    /**
     * Check weather the particular page is Loaded.
     * @param driver the driver
     * @param url the url
     * @param timeout the timeout
     * @return boolean
     */
    public static boolean isPageLoaded(final WebDriver driver, final String url, final int timeout) {

        boolean ispageLoaded = (new WebDriverWait(driver, timeout)).until(new ExpectedCondition<Boolean>() {
            boolean loaded;

            public Boolean apply(WebDriver d) {

                if (d.getCurrentUrl().equalsIgnoreCase(url))
                    loaded = true;
                return loaded;
            }
        });

        return ispageLoaded;
    }

    /**
     * Utility to check weather the particular field is displayed by field name and the text value.
     * @param driver the driver
     * @param fieldname the fieldname
     * @param textValue the text value
     * @param timeOut the time out
     * @return boolean
     */
    public static boolean isDisplayedByText(final WebDriver driver, final String fieldname, final String textValue, final int timeOut) {

        boolean isLoaded = false;
        isLoaded = (new WebDriverWait(driver, timeOut)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {

                WebElement element = d.findElement(By.id(fieldname));
                return element.getText().contains(textValue);
            }
        });
        return isLoaded;
    }

    /**
     * Utility to check weather the particular field is displayed by css class name.
     * @param driver the driver
     * @param fieldname the fieldname
     * @param textValue the text value
     * @param timeOut the time out
     * @return boolean
     */
    public static boolean isDisplayedByClassNameText(final WebDriver driver, final String fieldname, final String textValue, final int timeOut) {

        boolean isLoaded = false;
        isLoaded = (new WebDriverWait(driver, timeOut)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {

                WebElement element = d.findElement(By.className(fieldname));
                return element.getText().contains(textValue);
            }
        });
        return isLoaded;
    }

    /**
     * Check according to the link text.
     * @param driver the driver
     * @param fieldname the fieldname
     * @param timeOut the time out
     * @return boolean
     */
    public static boolean isDisplayedByLinkText(final WebDriver driver, final String fieldname, final int timeOut) {

        boolean isLoaded = false;
        isLoaded = (new WebDriverWait(driver, timeOut)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {

                WebElement element = driver.findElement(By.linkText(fieldname));
                return element.isDisplayed();
            }
        });
        return isLoaded;
    }

    /**
     * Check particular web Element text contains the expected text.
     * @param driver the driver
     * @param element the element
     * @param textValue the text value
     * @param timeOut the time out
     * @return boolean
     */
    public static boolean isDisplayedByElementText(final WebDriver driver, final WebElement element, final String textValue, final int timeOut) {

        boolean isLoaded = false;
        isLoaded = (new WebDriverWait(driver, timeOut)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {

                return element.getText().contains(textValue);
            }
        });
        return isLoaded;
    }

    /**
     * <b> verify the particular value is displayed according to the attribute</b>.
     * @param driver the driver
     * @param subElement the sub element
     * @param tagName the tag name
     * @param textValue the text value
     * @return the web element
     */
    /*
     * public static WebElement retrieveElementByAttributeValueTimeOut(final WebDriver driver, final
     * String tagName, final String attributeName, final String attributeValue, int timeOut) {
     * boolean isLoaded = false; isLoaded =(new WebDriverWait(driver, timeOut)).until(new
     * ExpectedCondition<Boolean>() { public Boolean apply(WebDriver d) { boolean returnVal = false;
     * List<WebElement> elementList = driver.findElements(By.tagName(tagName)); for (WebElement
     * webElement : elementList) { String valueAttribute =
     * webElement.getAttribute(attributeName.trim());
     * if(valueAttribute.trim().equalsIgnoreCase(attributeValue)) { element = webElement; returnVal
     * = true; break; } } return returnVal; } }); return element; }
     */

    /**
     * <b> verify the particular value is displayed according to the attribute</b>
     */
    public static WebElement retrieveElementByTagTextForSubElement(final WebDriver driver, final WebElement subElement, final String tagName, final String textValue) {

        WebElement element = null;
        List<WebElement> elementList = subElement.findElements(By.tagName(tagName));

        for (WebElement webElement : elementList) {
            String value = webElement.getText().trim();

            if (value.trim().equalsIgnoreCase(textValue))
                return webElement;
        }

        return element;
    }

    /**
     * <b> verify the particular value is displayed according to the attribute</b>.
     * @param driver the driver
     * @param tagName the tag name
     * @param attributeName the attribute name
     * @param attributeValue the attribute value
     * @return the web element
     */
    public static WebElement retrieveElementByAttributeValue(final WebDriver driver, final String tagName, final String attributeName, final String attributeValue) {

        WebElement element = null;
        List<WebElement> elementList = driver.findElements(By.tagName(tagName));

        for (WebElement webElement : elementList) {
            String valueAttribute = webElement.getAttribute(attributeName.trim());
            if (valueAttribute.trim().equalsIgnoreCase(attributeValue))
                return webElement;
        }

        return element;
    }

    /** The element attr value. */
    static WebElement elementAttrValue = null;

    /**
     * Retrieve element by attribute value.
     * @param driver the driver
     * @param tagName the tag name
     * @param attributeName the attribute name
     * @param attributeValue the attribute value
     * @param timeOut the time out
     * @return the web element
     */
    public static WebElement retrieveElementByAttributeValue(final WebDriver driver, final String tagName, final String attributeName, final String attributeValue, int timeOut) {

        elementAttrValue = null;
        (new WebDriverWait(driver, timeOut)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {

                boolean isLoaded = false;
                List<WebElement> elementList = driver.findElements(By.tagName(tagName));
                for (WebElement webElement : elementList) {
                    String valueAttribute = webElement.getAttribute(attributeName.trim());
                    if (valueAttribute.trim().equalsIgnoreCase(attributeValue)) {
                        elementAttrValue = webElement;
                        isLoaded = true;
                        break;
                    }

                }
                return isLoaded;
            }
        });

        return elementAttrValue;
    }

    /**
     * Contain by attribute value.
     * @param driver the driver
     * @param tagName the tag name
     * @param attributeName the attribute name
     * @param attributeValue the attribute value
     * @param timeOut the time out
     * @return the web element
     */
    public static WebElement containByAttributeValue(final WebDriver driver, final String tagName, final String attributeName, final String attributeValue, int timeOut) {

        elementAttrValue = null;
        (new WebDriverWait(driver, timeOut)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {

                boolean isLoaded = false;
                List<WebElement> elementList = driver.findElements(By.tagName(tagName));
                for (WebElement webElement : elementList) {
                    String valueAttribute = webElement.getAttribute(attributeName.trim());
                    if (valueAttribute.trim().contains(attributeValue)) {
                        elementAttrValue = webElement;
                        isLoaded = true;
                        break;
                    }

                }
                return isLoaded;
            }
        });

        return elementAttrValue;
    }

    /**
     * <b> verify the particular value is displayed according to the attribute</b>.
     * @param driver the driver
     * @param tagName the tag name
     * @param attributeName the attribute name
     * @param attributeValue the attribute value
     * @return the list
     */
    public static List<WebElement> retrieveElementsByAttributeValueList(final WebDriver driver, final String tagName, final String attributeName, final String attributeValue) {

        List<WebElement> elementList = driver.findElements(By.tagName(tagName));
        List<WebElement> listElement = new ArrayList<WebElement>();

        for (WebElement webElement : elementList) {
            String valueAttribute = webElement.getAttribute(attributeName.trim());
            if (valueAttribute.trim().equalsIgnoreCase(attributeValue))
                listElement.add(webElement);
        }

        return listElement;
    }

    /** <b> verify the particular value is displayed according to the attribute</b>. */
    static WebElement elementContainsAttrValue = null;

    /**
     * Retrieve element by contains of attribute value.
     * @param driver the driver
     * @param tagName the tag name
     * @param attributeName the attribute name
     * @param attributeValue the attribute value
     * @param timeOut the time out
     * @return the web element
     */
    public static WebElement retrieveElementByContainsOfAttributeValue(final WebDriver driver, final String tagName, final String attributeName, final String attributeValue,
            final int timeOut) {

        elementContainsAttrValue = null;
        (new WebDriverWait(driver, timeOut)).until(new ExpectedCondition<Boolean>() {

            public Boolean apply(WebDriver d) {

                boolean isLoaded = false;
                List<WebElement> elementList = driver.findElements(By.tagName(tagName));
                for (WebElement webElement : elementList) {
                    String valueAttribute = webElement.getAttribute(attributeName.trim());
                    if (valueAttribute != null && valueAttribute.trim().contains(attributeValue)) {
                        elementContainsAttrValue = webElement;
                        isLoaded = true;
                        break;
                    }
                }
                return isLoaded;
            }
        });

        return elementContainsAttrValue;
    }

    /**
     * Retrieve elements by contains of attribute value.
     * @param driver the driver
     * @param tagName the tag name
     * @param attributeName the attribute name
     * @param attributeValue the attribute value
     * @return the list
     */
    public static List<WebElement> retrieveElementsByContainsOfAttributeValue(final WebDriver driver, final String tagName, final String attributeName, final String attributeValue) {

        List<WebElement> elementListGroup = new ArrayList<WebElement>();
        List<WebElement> elementList = driver.findElements(By.tagName(tagName));
        for (WebElement webElement : elementList) {
            String valueAttribute = webElement.getAttribute(attributeName.trim());
            if (valueAttribute != null && valueAttribute.trim().contains(attributeValue)) {
                elementContainsAttrValue = webElement;
                elementListGroup.add(webElement);
            }
        }

        return elementListGroup;
    }

    /** <b> verify the particular value is displayed according to the attribute</b>. */
    static WebElement subElementEqualsAttrValue = null;

    /**
     * Retrieve element by attribute value for sub element.
     * @param driver the driver
     * @param subElement the sub element
     * @param tagName the tag name
     * @param attributeName the attribute name
     * @param attributeValue the attribute value
     * @param timeOut the time out
     * @return the web element
     */
    public static WebElement retrieveElementByAttributeValueForSubElement(final WebDriver driver, final WebElement subElement, final String tagName, final String attributeName,
            final String attributeValue, final int timeOut) {

        subElementEqualsAttrValue = null;
        (new WebDriverWait(driver, timeOut)).until(new ExpectedCondition<Boolean>() {

            public Boolean apply(WebDriver d) {

                boolean isLoaded = false;
                List<WebElement> elementList = subElement.findElements(By.tagName(tagName));
                for (WebElement webElement : elementList) {
                    String valueAttribute = webElement.getAttribute(attributeName.trim());
                    if (valueAttribute.trim().equalsIgnoreCase(attributeValue)) {
                        subElementEqualsAttrValue = webElement;
                        isLoaded = true;
                        break;
                    }
                }
                return isLoaded;
            }
        });

        return subElementEqualsAttrValue;
    }

    /** <b> verify the particular value is displayed according to the attribute Contains Of</b>. */
    static WebElement subElementContainsAttrValue = null;

    /**
     * Retrieve element by attribute value contains for sub element.
     * @param driver the driver
     * @param subElement the sub element
     * @param tagName the tag name
     * @param attributeName the attribute name
     * @param attributeValue the attribute value
     * @param timeOut the time out
     * @return the web element
     */
    public static WebElement retrieveElementByAttributeValueContainsForSubElement(final WebDriver driver, final WebElement subElement, final String tagName,
            final String attributeName, final String attributeValue, final int timeOut) {

        subElementContainsAttrValue = null;
        try {
            (new WebDriverWait(driver, timeOut)).until(new ExpectedCondition<Boolean>() {

                public Boolean apply(WebDriver d) {

                    boolean isLoaded = false;
                    List<WebElement> elementList = subElement.findElements(By.tagName(tagName));
                    for (WebElement webElement : elementList) {
                        String valueAttribute = webElement.getAttribute(attributeName.trim());
                        if (valueAttribute.trim().contains(attributeValue)) {
                            subElementContainsAttrValue = webElement;
                            isLoaded = true;
                            break;
                        }
                    }
                    return isLoaded;
                }
            });
        } catch (TimeoutException te) {
            
        }

        return subElementContainsAttrValue;
    }

    /**
     * <b> verify the particular value is displayed according to the attribute</b>.
     * @param driver the driver
     * @param rootElement the root element
     * @param tagName the tag name
     * @param attributeName the attribute name
     * @param attributeValue the attribute value
     * @return the web element
     */
    public static WebElement retrieveElementByAttributeValueByPassingElement(final WebDriver driver, final WebElement rootElement, final String tagName,
            final String attributeName, final String attributeValue) {

        WebElement element = null;
        List<WebElement> elementList = rootElement.findElements(By.tagName(tagName));
        for (WebElement webElement : elementList) {
            String valueAttribute = webElement.getAttribute(attributeName.trim());
            if (valueAttribute.trim().equalsIgnoreCase(attributeValue)) {
                return webElement;
            }
        }

        return element;
    }

    /**
     * Retrieve Element according to the Text provided.
     * @param driver the driver
     * @param fieldname the fieldname
     * @param timeOut the time out
     * @return boolean
     */
    public static WebElement retrieveElementByLinkText(final WebDriver driver, final String fieldname, final int timeOut) {

        isDisplayedByLinkText(driver, fieldname, timeOut);
        WebElement element = driver.findElement(By.linkText(fieldname));
        return element;
    }

    /**
     * Retrieve Element according to the Text provided for particular Form or Sub Element.
     * @param driver the driver
     * @param subElement the sub element
     * @param fieldname the fieldname
     * @param timeOut the time out
     * @return boolean
     */
    public static WebElement retrieveElementByLinkTextForSubElement(final WebDriver driver, final WebElement subElement, final String fieldname, final int timeOut) {

        WebElement element = subElement.findElement(By.linkText(fieldname));
        return element;
    }

    /**
     * <b> verify the particular value is displayed according to the attribute</b>.
     * @param driver the driver
     * @param idValue the id value
     * @param textValue the text value
     * @return the web element
     */
    public static WebElement retrieveElementByIdText(final WebDriver driver, final String idValue, final String textValue) {

        WebElement element = null;
        List<WebElement> elementList = driver.findElements(By.id(idValue));
        for (WebElement webElement : elementList) {
            String value = webElement.getText();
            if (value.trim().equalsIgnoreCase(textValue))
                return webElement;
        }

        return element;
    }

    /**
     * Retrieve element by id.
     *
     * @param driver the driver
     * @param idValue the id value
     * @return the web element
     */
    public static WebElement retrieveElementById(final WebDriver driver, final String idValue) {

        WebElement element = null;
        List<WebElement> elementList = driver.findElements(By.id(idValue));
        for (WebElement webElement : elementList) {
            if (webElement != null)
                return webElement;
        }

        return element;
    }

    /** The element tag text. */
    static WebElement elementTagText = null;

    /**
     * Retrieve element by tag text.
     * @param driver the driver
     * @param tagName the tag name
     * @param textValue the text value
     * @param timeOut the time out
     * @return the web element
     */
    public static WebElement retrieveElementByTagText(final WebDriver driver, final String tagName, final String textValue, final int timeOut) {

        elementTagText = null;
        (new WebDriverWait(driver, timeOut)).until(new ExpectedCondition<Boolean>() {

            public Boolean apply(WebDriver d) {

                boolean isLoaded = false;
                List<WebElement> elementList = driver.findElements(By.tagName(tagName));
                for (WebElement webElement : elementList) {
                    String value = webElement.getText();
                    if (value.trim().equalsIgnoreCase(textValue)) {
                        elementTagText = webElement;
                        isLoaded = true;
                        break;
                    }
                }
                return isLoaded;
            }
        });

        /*
         * WebElement element = null; List<WebElement> elementList =
         * driver.findElements(By.tagName(tagName)); for (WebElement webElement : elementList) {
         * String value = webElement.getText(); if(value.trim().equalsIgnoreCase(textValue)) return
         * webElement; }
         */

        return elementTagText;
    }

    /**
     * Contains by tag text.
     * @param driver the driver
     * @param tagName the tag name
     * @param textValue the text value
     * @param timeOut the time out
     * @return the web element
     */
    public static WebElement containsByTagText(final WebDriver driver, final String tagName, final String textValue, final int timeOut) {

        elementTagText = null;
        (new WebDriverWait(driver, timeOut)).until(new ExpectedCondition<Boolean>() {

            public Boolean apply(WebDriver d) {

                boolean isLoaded = false;
                List<WebElement> elementList = driver.findElements(By.tagName(tagName));
                for (WebElement webElement : elementList) {
                    String value = webElement.getText();
                    if (value.trim().contains(textValue)) {
                        elementTagText = webElement;
                        isLoaded = true;
                        break;
                    }
                }
                return isLoaded;
            }
        });

        /*
         * WebElement element = null; List<WebElement> elementList =
         * driver.findElements(By.tagName(tagName)); for (WebElement webElement : elementList) {
         * String value = webElement.getText(); if(value.trim().equalsIgnoreCase(textValue)) return
         * webElement; }
         */

        return elementTagText;
    }

    /**
     * Retrieve element by tag text.
     * @param driver the driver
     * @param tagName the tag name
     * @param textValue the text value
     * @return the web element
     */
    public static WebElement retrieveElementByTagText(final WebDriver driver, final String tagName, final String textValue) {

        WebElement element = null;
        List<WebElement> elementList = driver.findElements(By.tagName(tagName));
        for (WebElement webElement : elementList) {
            String value = webElement.getText();
            if (value.trim().equalsIgnoreCase(textValue))
                return webElement;
        }

        return element;
    }

    /**
     * Retrieve elements by tag text.
     * @param driver the driver
     * @param tagName the tag name
     * @param textValue the text value
     * @return the list
     */
    public static List<WebElement> retrieveElementsByTagText(final WebDriver driver, final String tagName, final String textValue) {

        List<WebElement> elementList = driver.findElements(By.tagName(tagName));
        List<WebElement> listElement = new ArrayList<WebElement>();

        for (WebElement webElement : elementList) {
            String valueAttribute = webElement.getText();
            if (valueAttribute.trim().equalsIgnoreCase(textValue))
                listElement.add(webElement);
        }

        return listElement;
    }

    /**
     * Retrieve sub element by tag text.
     * @param driver the driver
     * @param subElelement the sub elelement
     * @param tagName the tag name
     * @param textValue the text value
     * @param timeOut the time out
     * @return the web element
     */
    public static WebElement retrieveSubElementByTagText(final WebDriver driver, final WebElement subElelement, final String tagName, final String textValue, final int timeOut) {

        elementTagText = null;
        (new WebDriverWait(driver, timeOut)).until(new ExpectedCondition<Boolean>() {

            public Boolean apply(WebDriver d) {

                boolean isLoaded = false;
                List<WebElement> elementList = subElelement.findElements(By.tagName(tagName));
                for (WebElement webElement : elementList) {
                    String value = webElement.getText();
                    if (value.trim().equalsIgnoreCase(textValue)) {
                        elementTagText = webElement;
                        isLoaded = true;
                        break;
                    }
                }
                return isLoaded;
            }
        });

        /*
         * WebElement element = null; List<WebElement> elementList =
         * driver.findElements(By.tagName(tagName)); for (WebElement webElement : elementList) {
         * String value = webElement.getText(); if(value.trim().equalsIgnoreCase(textValue)) return
         * webElement; }
         */

        return elementTagText;
    }

    /**
     * Utility to check weather the particular field is displayed by css class name.
     * @param driver the driver
     * @param fieldname the fieldname
     * @param textValue the text value
     * @param textValue2 the text value2
     * @param timeOut the time out
     * @return boolean
     */
    public static boolean isDisplayedByClassNameForWeeklySchedule(final WebDriver driver, final String fieldname, final String textValue, final String textValue2, final int timeOut) {

        boolean isLoaded = false;
        isLoaded = (new WebDriverWait(driver, timeOut)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {

                boolean outcome = false;
                WebDriverWait wait = new WebDriverWait(driver, timeOut);
                wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className(fieldname)));

                WebElement element = d.findElement(By.className(fieldname));

                if (element.getText().contains(textValue) || element.getText().contains(textValue2)) {
                    outcome = true;
                }
                return outcome;
            }
        });
        return isLoaded;
    }

    /**
     * Checks if is form element clickable by id.
     * @param driver the driver
     * @param fieldname the fieldname
     * @param timeOut the time out
     */
    public static void isFormElementClickableById(final WebDriver driver, final String fieldname, final int timeOut) {

        WebDriverWait wait = new WebDriverWait(driver, timeOut);
        wait.until(ExpectedConditions.elementToBeClickable(By.id(fieldname)));

    }

    /**
     * Close alert.
     *
     * @param driver the driver
     */
    public static void closeAlert(WebDriver driver) {

        try {
            WebDriverWait wait = new WebDriverWait(driver, SHORT_TIMEOUT);
            if (wait.until(ExpectedConditions.alertIsPresent()) != null) {
                driver.switchTo().alert().accept();
            }
        } catch (Exception e) {
            // ignore any exception
        }
    }

    /**
     * Save screenshot for a given test case.
     *
     * @param name the name
     * @param driver the driver
     */
    public static void saveScreenshot(String name, WebDriver driver) {

        try {

            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(srcFile, new File("target\\screenshots\\" + name + ".png"));
            File screenShotFile = new File("target\\screenshots\\" + name + ".png");
            if (screenShotFile.exists()) {
                logger.info("Saved screenshot for " + name + " at " + screenShotFile.getAbsolutePath());
            } else {
                logger.info("Unable to save screenshot for " + name + " at " + screenShotFile.getAbsolutePath());
            }
        } catch (Exception e) {
            logger.error("Error taking screenshot for " + name, e);
        }
    }

    /**
     * Delete screenshot if testcase passed in retry.
     * @param name the name
     * @param driver the driver
     */
    public static void deleteScreenshot(String name, WebDriver driver) {

        try {

            File srcFile = new File("target\\screenshots\\" + name + ".png");
            FileUtils.getFile(srcFile).delete();
            String failureScreenShot = name.concat("_prev");
            srcFile = new File("target\\screenshots\\" + failureScreenShot + ".png");
            FileUtils.getFile(srcFile).delete();

        } catch (Exception e) {
            logger.error("Error deleting screenshot for " + name, e);
        }
    }

    /**
     * Mobile save screenshot.
     * @param name the name
     * @param driver the driver
     */
    public static void mobileSaveScreenshot(String name, WebDriver driver) {

        try {

            WebDriver augmentedDriver = new Augmenter().augment(driver);
            File screenshot = ((TakesScreenshot) augmentedDriver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshot, new File("target\\screenshots\\" + name + ".png"));

            File screenShotFile = new File("target\\screenshots\\" + name + ".png");
            if (screenShotFile.exists()) {
                logger.info("Saved screenshot for " + name + " at " + screenShotFile.getAbsolutePath());
            } else {
                logger.info("Unable to save screenshot for " + name + " at " + screenShotFile.getAbsolutePath());
            }
        } catch (Exception e) {
            logger.error("Error taking screenshot for " + name, e);
        }
    }

    /**
     * Ios save screenshot.
     * @param name the name
     * @param driver the driver
     */
    public static void iosSaveScreenshot(String name, WebDriver driver) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            HashMap<String, String> execObject = new HashMap<String, String>();
            File sourceFile = new File("");
            String filePath = sourceFile.getAbsolutePath() + "/target/screenshots/" + name + ".png";
            execObject.put("file", filePath);
            try {
                js.executeScript("mobile: localScreenshot", execObject);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            logger.error("Error taking screenshot for " + name, e);
        }
    }

    /**
     * Wait for page loaded.
     * @param driver the driver
     */
    public static void waitForPageLoaded(WebDriver driver) {

        ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {

                return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
            }
        };

        Wait<WebDriver> wait = new WebDriverWait(driver, 120);
        try {
            wait.until(expectation);
        } catch (Throwable error) {
            DriverConfig.setLogString("Page load takes more time", true);
        }
    }
    
    /**
     * Clear and input.
     * @param driver the driver
     * @param locator the locator
     * @param fieldValue the field value
     */
    public static void clearAndInput(final WebDriver driver, final By locator,
            final String fieldValue) {

        final WebElement element = driver.findElement(locator);
        element.clear();
        element.sendKeys(fieldValue);
    }
    
    /**
     * Checks if is gmail loaded.
     * @return true, if is gmail loaded
     */
    public static boolean isGmailLoaded(final WebDriver driver) {

        return isDisplayedByClassName(driver, "profile-name", SHORT_TIMEOUT)
                && isDisplayedById(driver, "Passwd", SHORT_TIMEOUT)
                && isDisplayedByXpath(driver,
                        "//h1[contains(text(),'One account. All of Google.')]", SHORT_TIMEOUT);
    }
}
