/*
 * Gmail.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.util.mail;

import static com.ecofactor.qa.automation.util.PageUtil.*;
import static com.ecofactor.qa.automation.util.WaitUtil.*;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.ecofactor.qa.automation.util.DriverConfig;
import com.google.inject.Inject;

public class Gmail {

    @Inject
    private DriverConfig driverConfig;

    /**
     * Gets the changed password.
     * @param url the url
     * @param emailUserName the email user name
     * @param emailPassword the email password
     * @param subject the subject
     * @param boldIndex the bold index
     * @param pwdIndex the pwd index
     * @return the changed password
     */
    public String getChangedPassword(String url, String emailUserName, String emailPassword,
            String subject, int boldIndex, int pwdIndex) {

        WebDriver driver = DriverConfig.getDriver();
        if (DriverConfig.getDriver() == null) {
            driverConfig.loadDriver();
            driver = DriverConfig.getDriver();
        }

        String tempPassword = null;

        largeWait();
        DriverConfig.setLogString("Load Email portal URL at change password", true);
        driver.navigate().to(url + "" + emailUserName);
        largeWait();

        boolean session = isDisplayedById(driver, "Passwd", MEDIUM_TIMEOUT);
        if (session) {
            DriverConfig.setLogString("Enter username and password for Email(" + emailUserName
                    + "/" + emailPassword + ")", true);
            driver.findElement(By.id("Email")).sendKeys(emailUserName);
            driver.findElement(By.id("Passwd")).sendKeys(emailPassword);

            mediumWait();
            WebElement signInButtonElement = driver.findElement(By.id("signIn"));
            DriverConfig.setLogString("click Sign In button", true);
            signInButtonElement.click();
            largeWait();
            boolean button = isDisplayedById(driver, "no-button", MEDIUM_TIMEOUT);
            if (button) {
                WebElement buttonElement = driver.findElement(By.id("no-button"));
                buttonElement.click();
                mediumWait();
            }
        }

        isDisplayedByCSS(driver, ".aio.UKr6le", MEDIUM_TIMEOUT);
        DriverConfig.setLogString("Click Inbox.", true);
        WebElement inboxElement = DriverConfig.getDriver().findElement(By.partialLinkText("Inbox"));
        inboxElement.click();
        largeWait();

        DriverConfig.setLogString("Click Subject of Email.", true);
        WebElement webElement = driver.findElement(By.cssSelector(".ae4.UI.UJ"));
        List<WebElement> tagElement = webElement.findElements(By.tagName(TAG_BOLD));
        WebElement firstElement = tagElement.get(0);
        if (firstElement != null && firstElement.isDisplayed()
                && firstElement.getText().equalsIgnoreCase(subject)) {
            firstElement.click();
            largeWait();
        }

        DriverConfig.setLogString("Get temporary Password/activation link from Email.", true);
        if (boldIndex == 1) {
            WebElement divBodyElement = driver.findElement(By.xpath("//*[contains(@class,'adO')]"));
            if (pwdIndex == 1) {
                WebElement pwdElement = divBodyElement.findElements(By.tagName(TAG_STRONG)).get(0);
                tempPassword = pwdElement.getText();
                DriverConfig
                        .setLogString("Temporary password got from email " + tempPassword, true);
            } else {
                WebElement pwdElement = divBodyElement.findElements(By.tagName(TAG_ANCHOR)).get(0);
                tempPassword = pwdElement.getText();
                DriverConfig.setLogString("Activation link got from email " + tempPassword, true);
            }
        } else if (boldIndex == 0) {
            DriverConfig.setLogString("Header Tag", true);
            WebElement divBodyElement = driver.findElement(By.className("gs"));
            List<WebElement> headerElement = divBodyElement.findElements(By.tagName("strong"));
            for (WebElement webelement : headerElement) {
                if (webelement.isDisplayed()) {
                    tempPassword = webelement.getText();
                    DriverConfig.setLogString("Temporary password got from email " + tempPassword,
                            true);
                }
            }
        }
        mediumWait();

        deleteFirstMail(driver);

        DriverConfig.setLogString("Click user for Signout option", true);
        boolean value = isDisplayedById(DriverConfig.getDriver(), "gbgs4dn", MEDIUM_TIMEOUT);
        DriverConfig.setLogString("User name displayed for sign out : " + value, true);
        if (value) {
            driver.findElement(By.id("gbgs4dn")).click();
        } else {
            WebElement signOutElement = DriverConfig.getDriver().findElement(
                    By.partialLinkText(emailUserName));
            signOutElement.click();
        }
        smallWait();

        DriverConfig.setLogString("Click Signout", true);
        driver.findElement(By.id("gb_71")).click();

        mediumWait();
        mediumWait();

        return tempPassword;
    }

    /**
     * Delete old mails.
     * @param url the url
     * @param emailUserName the email user name
     * @param emailPassword the email password
     * @param subject the subject
     */
    public void deleteOldMails(String url, String emailUserName, String emailPassword,
            String subject) {

        if (DriverConfig.getDriver() == null) {
            driverConfig.loadDriver();
        }

        largeWait();
        DriverConfig.setLogString("Load Email portal URL.", true);
        DriverConfig.getDriver().navigate().to(url + "" + emailUserName);
        largeWait();

        boolean session = isDisplayedById(DriverConfig.getDriver(), "Passwd", MEDIUM_TIMEOUT);
        if (session) {
            DriverConfig.setLogString("Enter username and password for Email(" + emailUserName
                    + "/" + emailPassword + ")", true);
            DriverConfig.getDriver().findElement(By.id("Passwd")).sendKeys(emailPassword);

            mediumWait();
            WebElement signInButtonElement = DriverConfig.getDriver().findElement(By.id("signIn"));
            DriverConfig.setLogString("click Sign In button", true);
            signInButtonElement.click();
            largeWait();
            boolean button = isDisplayedById(DriverConfig.getDriver(), "no-button", MEDIUM_TIMEOUT);
            if (button) {
                WebElement buttonElement = DriverConfig.getDriver().findElement(By.id("no-button"));
                buttonElement.click();
                mediumWait();
            }
        }
        isDisplayedByCSS(DriverConfig.getDriver(), ".aio.UKr6le", MEDIUM_TIMEOUT);
        deleteFirstMail(DriverConfig.getDriver());

        DriverConfig.setLogString("Click user for Signout option", true);
        boolean value = isDisplayedById(DriverConfig.getDriver(), "gbgs4dn", MEDIUM_TIMEOUT);
        DriverConfig.setLogString("User name displayed for sign out : " + value, true);
        if (value) {
            DriverConfig.getDriver().findElement(By.id("gbgs4dn")).click();
        } else {
            WebElement signOutElement = DriverConfig.getDriver().findElement(
                    By.partialLinkText(emailUserName));
            signOutElement.click();
        }
        smallWait();

        DriverConfig.setLogString("Click Signout", true);
        DriverConfig.getDriver().findElement(By.id("gb_71")).click();

        mediumWait();
        mediumWait();

    }

    /**
     * Delete first mail.
     * @param driver the driver
     */
    public void deleteFirstMail(WebDriver driver) {

        DriverConfig.setLogString("Delete First Mail", true);
        DriverConfig.setLogString("Click Inbox", true);
        WebElement inboxElement = DriverConfig.getDriver().findElement(By.partialLinkText("Inbox"));
        inboxElement.click();
        mediumWait();

        DriverConfig.setLogString("Click Subject of Email.", true);
        boolean subjectDisplayed = isDisplayedByCSS(driver, ".ae4.UI.UJ", MEDIUM_TIMEOUT);
        if (subjectDisplayed) {
            WebElement webElement1 = driver.findElement(By.cssSelector(".ae4.UI.UJ"));
            if (!webElement1.getText().contains("No new mail")) {

                WebElement firstElement2 = null;
                if (webElement1 != null && webElement1.findElements(By.tagName(TAG_BOLD)) != null
                        && webElement1.findElements(By.tagName(TAG_BOLD)).size() > 0) {
                    firstElement2 = webElement1.findElements(By.tagName(TAG_BOLD)).get(0);
                } else {
                    firstElement2 = webElement1.findElements(By.cssSelector("td.yX.xY")).get(0);
                }

                if (firstElement2 != null && firstElement2.isDisplayed()) {
                    firstElement2.click();
                    mediumWait();

                    DriverConfig.setLogString("Delete the Email", true);
                    if (driver.findElements(By.cssSelector("div.ar9.T-I-J3.J-J5-Ji")) != null
                            && driver.findElements(By.cssSelector("div.ar9.T-I-J3.J-J5-Ji")).size() == 2) {
                        DriverConfig.setLogString("Click delete old mails", true);
                        WebElement webElement2 = driver.findElements(
                                By.cssSelector("div.ar9.T-I-J3.J-J5-Ji")).get(1);
                        webElement2.click();
                    } else {
                        DriverConfig.setLogString("Click delete old mails", true);
                        smallWait();
                        WebElement webElement2 = driver.findElements(
                                By.cssSelector("div.ar9.T-I-J3.J-J5-Ji")).get(1);
                        webElement2.click();
                    }

                    smallWait();
                }
            }

        }
    }
}
