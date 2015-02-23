/*
 * OutlookMail.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.util.mail;

import static com.ecofactor.qa.automation.util.PageUtil.ATTR_CLASS;
import static com.ecofactor.qa.automation.util.PageUtil.MEDIUM_TIMEOUT;
import static com.ecofactor.qa.automation.util.PageUtil.TAG_BOLD;
import static com.ecofactor.qa.automation.util.PageUtil.TAG_BUTTON;
import static com.ecofactor.qa.automation.util.PageUtil.TAG_SPAN;
import static com.ecofactor.qa.automation.util.PageUtil.retrieveElementByAttributeValue;
import static com.ecofactor.qa.automation.util.PageUtil.retrieveElementByTagText;
import static com.ecofactor.qa.automation.util.PageUtil.retrieveElementsByTagText;
import static com.ecofactor.qa.automation.util.WaitUtil.largeWait;
import static com.ecofactor.qa.automation.util.WaitUtil.mediumWait;
import static com.ecofactor.qa.automation.util.WaitUtil.smallWait;
import static org.testng.Reporter.log;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.ecofactor.qa.automation.util.DriverConfig;
import com.google.inject.Inject;

public class OutlookMail {

    @Inject
    private DriverConfig driverConfig;

    /**
     * Gets the changed password.
     * @param driver the driver
     * @param url the url
     * @param emailUserName the email user name
     * @param emailPassword the email password
     * @param subject the subject
     * @param boldIndex the bold index
     * @return the changed password
     */
        public String getChangedPassword(String url, String emailUserName, String emailPassword, String subject, int boldIndex) {

        WebDriver driver = driverConfig.getDriver();

        DriverConfig.setLogString("Load Email portal URL.", true);
        driver.navigate().to(url);
        largeWait();

        DriverConfig.setLogString("Enter Username/Password for Email.", true);
        driver.findElement(By.id("cred_userid_inputtext")).sendKeys(emailUserName);
        driver.findElement(By.id("cred_password_inputtext")).sendKeys(emailPassword);

		smallWait();
		DriverConfig.setLogString("Click Login.", true);
		WebElement signInButtonElement = driver.findElement(By.cssSelector("#cred_sign_in_button"));
		DriverConfig.setLogString("send keys to login", true);
		signInButtonElement.sendKeys(Keys.RETURN);
		largeWait();
		DriverConfig.setLogString("click btn", true);
		signInButtonElement.click();
		largeWait();

        DriverConfig.setLogString("Click Inbox.", true);
        WebElement outLookMenu = retrieveElementByTagText(driver, TAG_SPAN, "Outlook");
        if (outLookMenu != null && outLookMenu.isDisplayed()) {
            outLookMenu.click();
            mediumWait();
        }

        DriverConfig.setLogString("Click Subject of Email.", true);
        List<WebElement> subElements = retrieveElementsByTagText(driver, TAG_SPAN, subject);
        if (subElements.size() > 0) {
            subElements.get(0).click();
            mediumWait();
        }
        largeWait();

        DriverConfig.setLogString("Get temporary Password from Email.", true);
        WebElement divBodyElement = driver.findElement(By.id("Item.MessagePartBody"));
        mediumWait();
        WebElement pwdElement = divBodyElement.findElements(By.tagName(TAG_BOLD)).get(boldIndex);
        String tempPassword = pwdElement.getText();
        DriverConfig.setLogString("Temporary password got from email " + tempPassword, true);

        DriverConfig.setLogString("Click user for Signout option", true);
        WebElement menuElement = retrieveElementByAttributeValue(driver, TAG_BUTTON, ATTR_CLASS, "button _hl_2 _hl_e");
        menuElement.click();
        smallWait();

        DriverConfig.setLogString("Click Signout", true);
        WebElement signOut = retrieveElementByTagText(driver, TAG_SPAN, "sign out", MEDIUM_TIMEOUT);
        signOut.click();
        smallWait();

        return tempPassword;
    }

}
