/*
 * AdminDialoguePageImpl.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.newapp.admin.page.impl;

import static com.ecofactor.qa.automation.platform.constants.HtmlTags.*;
import static com.ecofactor.qa.automation.platform.enums.CustomTimeout.*;
import static com.ecofactor.qa.automation.platform.util.LogUtil.*;
import static com.ecofactor.qa.automation.platform.util.Pageutil.*;
import static com.ecofactor.qa.automation.util.WaitUtil.*;

import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.ecofactor.qa.automation.newapp.admin.page.AdminDialoguePage;

/**
 * The Class AdminDialoguePageImpl.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class AdminDialoguePageImpl extends AbstractAdminPageImpl implements AdminDialoguePage {

    private WebDriver popup;
    private Set<String> windowids;
    private static final String F_VAL_ID = "f_val";
    private static final String C_VAL_ID = "c_val";
    private static final String FROM_F_ID = "from_f";
    private static final String TO_F_ID = "to_f";
    private static final String F2_VAL_ID = "f2_val";
    private static final String HEX2_VAL_ID = "hex2_val";
    private static final String FROM_HEX2_ID = "from_hex2";
    private static final String TO_HEX2_ID = "to_hex2";
    private static final String DEC_VAL_ID = "dec_val";
    private static final String HEX_VAL_ID = "hex_val";
    private static final String FROM_HEX_ID = "from_hex";
    private static final String TO_HEX_ID = "to_hex";
    private static final String ATTRIBUTE = "attribute";
    private static final String READ_BTN = "read_btn";
    private static final String BLOCK = "myblock";
    private static final String FORM = "myform";
    private static final String CLEAR_BTN = "clearbtn";

    /**
     * The Enum selectOption.
     */
    public enum SelectOption {

        TEMP(1), COOL_SETPOINT(2), HEAT_SETPOINT(3), HOLD_MODE(4), HVAC_MODE(5), HVAC_STATE(6), FAN_MODE(7), FAN_STATE(8);

        private SelectOption(final int option) {

            this.option = option;
        }

        private int option;

        public int getOption() {

            return option;
        }

        public void setOption(final int option) {

            this.option = option;
        }
    }

    /**
     * The Enum FanMode.
     */
    public enum FanMode {

        ON(4), AUTO(5);
        private FanMode(final int mode) {

            this.mode = mode;
        }

        private int mode;

        public int getMode() {

            return mode;
        }

        public void setMode(final int mode) {

            this.mode = mode;
        }
    }

    /**
     * The Enum HvacState.
     */
    public enum HvacState {

        OFF(0), COOL(3), HEAT(4);
        private HvacState(final int state) {

            this.state = state;
        }

        private int state;

        public int getState() {

            return state;
        }

        public void setState(final int state) {

            this.state = state;
        }
    }

    /**
     * clean up.
     * @see com.ecofactor.qa.automation.newapp.page.BasePage#cleanup()
     */
    @Override
    public void cleanup() {

        // TODO Auto-generated method stub

    }

    /**
     * Select drop down value.
     * @param value the value
     * @see com.ecofactor.qa.automation.newapp.admin.page.AdminDialoguePage#selectDropdownValue(java.lang.String)
     */
    @Override
    public void selectDropdownValue(String value) {

        setLogString("Select value: " + value, true);
        final WebElement dropDownListBox = getElement(popup, By.id(ATTRIBUTE), TINY_TIMEOUT);
        final Select droplist = new Select(dropDownListBox);
        droplist.selectByVisibleText(value);
        tinyWait();
    }

    /**
     * @param value
     * @see com.ecofactor.qa.automation.newapp.admin.page.AdminDialoguePage#selectDropdownValue(com.ecofactor.qa.automation.mobile.admin.page.impl.AdminDialoguePageImpl.SelectOption)
     */
    @Override
    public void selectDropdownValue(SelectOption value) {

        setLogString("Select value: " + value.toString(), true);
        final WebElement dropDownListBox = getElement(popup, By.id("attribute"), TINY_TIMEOUT);
        final Select droplist = new Select(dropDownListBox);
        droplist.selectByValue(value.toString());
        tinyWait();
    }

    /**
     * Click Read.
     * @see com.ecofactor.qa.automation.newapp.admin.page.AdminDialoguePage#clickRead()
     */
    @Override
    public void clickRead() {

        setLogString("Click Read", true);
        final WebElement readElement = getElement(popup, By.id(READ_BTN), TINY_TIMEOUT);
        readElement.click();
        smallWait();
    }

    /**
     * Read temperature.
     * @return the string
     * @see com.ecofactor.qa.automation.newapp.admin.page.AdminDialoguePage#readTemperature()
     */
    @Override
    public String readTemperature() {

        setLogString("Read temperature", true);
        final WebElement tempElement = getElements(popup, By.className(BLOCK), SHORT_TIMEOUT).get(1);
        final String value = getElementBySubElement(popup, tempElement, By.tagName(TAG_SPAN), SHORT_TIMEOUT).getText();
        final String[] tempValue = value.split(":");
        setLogString("Hexa decimal value: " + tempValue[1].trim(), true);
        return tempValue[1].trim();
    }

    /**
     * Close dialogue window.
     * @see com.ecofactor.qa.automation.newapp.admin.page.AdminDialoguePage#closeDialogueWindow()
     */
    @Override
    public void closeDialogueWindow() {

        setLogString("Close dialogue window", true);
        final Iterator<String> iter = windowids.iterator();
        if (iter.hasNext()) {
            // switch to main window
            setLogString("Switch to main window", true);
            driverManager.getAdminDriver().switchTo().window((String) iter.next());
        }
        tinyWait();
    }

    /**
     * Checks if is page loaded.
     * @return true, if is page loaded
     * @see com.ecofactor.qa.automation.newapp.page.BasePage#isPageLoaded()
     */
    @Override
    public boolean isPageLoaded() {

        setLogString("Check the dialogue is loaded", true);
        windowids = driverManager.getAdminDriver().getWindowHandles();
        final int windowSize = windowids.size();
        boolean isFormDisplayed = false;
        if (windowSize > 1) {
        	final Iterator<String> iter = windowids.iterator();
            iter.next();
            setLogString("Switch to new window ", true);
            popup = driverManager.getAdminDriver().switchTo().window((String) iter.next());
            smallWait();
            isFormDisplayed = isDisplayed(popup, By.id(FORM), MEDIUM_TIMEOUT);
            Assert.assertTrue(isFormDisplayed, "Dialogue is not displayed");
        }
        return isFormDisplayed;
    }

    /**
     * Hexa to fahrenheit.
     * @param hexafieldValue the hexafield value
     * @return the string
     * @see com.ecofactor.qa.automation.newapp.admin.page.AdminDialoguePage#hexaToFahrenheit(java.lang.String)
     */
    @Override
    public String hexaToFahrenheit(String hexafieldValue) {

    	final double fahrenheitVal = Double.parseDouble(convertedTempValue(hexafieldValue, HEX2_VAL_ID, FROM_HEX2_ID, F2_VAL_ID));
        setLogString("Target temperature from admin tool: " + Math.round(fahrenheitVal), true);
        return String.valueOf(Math.round(fahrenheitVal));
    }

    /**
     * Fahrenheit to hexa.
     * @param fahrenheitFieldValue the fahrenheit field value
     * @return the string
     * @see com.ecofactor.qa.automation.newapp.admin.page.AdminDialoguePage#fahrenheitToHexa(java.lang.String)
     */
    @Override
    public String fahrenheitToHexa(String fahrenheitFieldValue) {

        return convertedTempValue(fahrenheitFieldValue, F2_VAL_ID, TO_HEX2_ID, HEX2_VAL_ID);
    }

    /**
     * @param hexafieldValue
     * @return
     * @see com.ecofactor.qa.automation.newapp.admin.page.AdminDialoguePage#hexaToDecimal(java.lang.String)
     */
    @Override
    public String hexaToDecimal(String hexafieldValue) {

    	final double decimalVal = Double.parseDouble(convertedTempValue(hexafieldValue, HEX_VAL_ID, FROM_HEX_ID, DEC_VAL_ID));
        return String.valueOf(Math.round(decimalVal));
    }

    /**
     * @param decimalFieldValue
     * @return
     * @see com.ecofactor.qa.automation.newapp.admin.page.AdminDialoguePage#decimalToHexa(java.lang.String)
     */
    @Override
    public String decimalToHexa(String decimalFieldValue) {

        return convertedTempValue(decimalFieldValue, DEC_VAL_ID, TO_HEX_ID, HEX_VAL_ID);
    }

    /**
     * @param fahrenheitFieldValue
     * @return
     * @see com.ecofactor.qa.automation.newapp.admin.page.AdminDialoguePage#fahrenheitToCelsius(java.lang.String)
     */
    @Override
    public String fahrenheitToCelsius(String fahrenheitFieldValue) {

    	final double fahrenVal = Double.parseDouble(convertedTempValue(fahrenheitFieldValue, F_VAL_ID, FROM_F_ID, C_VAL_ID));
        return String.valueOf(Math.round(fahrenVal));
    }

    /**
     * @param celsiusFieldValue
     * @return
     * @see com.ecofactor.qa.automation.newapp.admin.page.AdminDialoguePage#celsiusToFahrenheit(java.lang.String)
     */
    @Override
    public String celsiusToFahrenheit(String celsiusFieldValue) {

    	final double celsiusVal = Double.parseDouble(convertedTempValue(celsiusFieldValue, C_VAL_ID, TO_F_ID, F_VAL_ID));
        return String.valueOf(Math.round(celsiusVal));
    }

    /**
     * click clear button
     * @see com.ecofactor.qa.automation.newapp.admin.page.AdminDialoguePage#clickClearBtn()
     */
    @Override
    public void clickClearBtn() {

        setLogString("Click clear results", true);
        final WebElement readElement = getElement(popup, By.id(CLEAR_BTN), TINY_TIMEOUT);
        readElement.click();
        smallWait();

    }

    /**
     * Converted temp value.
     * @param fieldValue the field value
     * @param sourceField the source field
     * @param convertButton the convert button
     * @param destinationField the destination field
     * @return the string
     */
    private String convertedTempValue(final String fieldValue, final String sourceField, final String convertButton, final String destinationField) {

        isDisplayed(popup, By.id(sourceField), SHORT_TIMEOUT);
        clearAndInput(popup, By.id(sourceField), fieldValue);
        final WebElement conversionBtn = getElement(popup, By.id(convertButton), SHORT_TIMEOUT);
        conversionBtn.click();
        final JavascriptExecutor jsExecutor = (JavascriptExecutor) popup;
        final Object val = jsExecutor.executeScript("return window.document.getElementById('" + destinationField + "').value;");
        return val.toString();
    }
}
