/*
 * AdminDialoguePage.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.newapp.admin.page;

import com.ecofactor.qa.automation.newapp.admin.page.impl.AdminDialoguePageImpl;
import com.ecofactor.qa.automation.newapp.admin.page.impl.AdminDialoguePageImpl.SelectOption;
import com.google.inject.ImplementedBy;

/**
 * The Interface AdminDialoguePage.
 *
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
@ImplementedBy(value = AdminDialoguePageImpl.class)
public interface AdminDialoguePage {

	/**
	 * Select drop down value.
	 * @param value the value
	 */
	void selectDropdownValue(String value);

	/**
	 * Select dropdown value.
	 * @param value the value
	 */
	void selectDropdownValue(SelectOption value);

	/**
	 * Click read.
	 */
	void clickRead();

	/**
	 * Read temperature.
	 * @return the string
	 */
	String readTemperature();

	/**
	 * Close dialogue window.
	 */
	void closeDialogueWindow();

	/**
	 * Checks if is page loaded.
	 * @return true, if is page loaded
	 */
	boolean isPageLoaded();

	/**
	 * Hexa to fahrenheit.
	 * @param hexafieldValue the hexafield value
	 * @return the string
	 */
	String hexaToFahrenheit(String hexafieldValue);

	/**
	 * Fahrenheit to hexa.
	 * @param fahrenheitFieldValue the fahrenheit field value
	 * @return the string
	 */
	String fahrenheitToHexa(String fahrenheitFieldValue);

	/**
	 * Hexa to decimal.
	 * @param hexafieldValue the hexafield value
	 * @return the string
	 */
	String hexaToDecimal(String hexafieldValue);

	/**
	 * Decimal to hexa.
	 * @param decimalFieldValue the decimal field value
	 * @return the string
	 */
	String decimalToHexa(String decimalFieldValue);

	/**
	 * Fahrenheit to celsius.
	 * @param fahrenheitFieldValue the fahrenheit field value
	 * @return the string
	 */
	String fahrenheitToCelsius(String fahrenheitFieldValue);

	/**
	 * Celsius to fahrenheit.
	 * @param celsiusFieldValue the celsius field value
	 * @return the string
	 */
	String celsiusToFahrenheit(String celsiusFieldValue);

	/**
	 * Click clear btn.
	 */
	void clickClearBtn();
}
