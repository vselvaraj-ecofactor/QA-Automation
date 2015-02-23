/*
 * GmailForNewUser.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.util.mail;

import static com.ecofactor.qa.automation.util.PageUtil.MEDIUM_TIMEOUT;
import static com.ecofactor.qa.automation.util.PageUtil.SHORT_TIMEOUT;
import static com.ecofactor.qa.automation.util.PageUtil.TAG_ANCHOR;
import static com.ecofactor.qa.automation.util.PageUtil.TAG_BOLD;
import static com.ecofactor.qa.automation.util.PageUtil.TAG_STRONG;
import static com.ecofactor.qa.automation.util.PageUtil.clearAndInput;
import static com.ecofactor.qa.automation.util.PageUtil.isDisplayedByCSS;
import static com.ecofactor.qa.automation.util.PageUtil.isDisplayedById;
import static com.ecofactor.qa.automation.util.WaitUtil.largeWait;
import static com.ecofactor.qa.automation.util.WaitUtil.mediumWait;
import static com.ecofactor.qa.automation.util.WaitUtil.smallWait;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import com.ecofactor.qa.automation.util.DriverConfig;

public class GmailForNewUser {

   // @Inject
    //private DriverConfig driverConfig;

    WebDriver gmailDriver = null;
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

       if(gmailDriver == null){
           gmailDriver = new ChromeDriver();
       }
        

        String tempPassword = null;

        largeWait();
        DriverConfig.setLogString("Load Email portal URL at change password", true);
        DriverConfig.setLogString("URL : "+ url + "" + emailUserName , true);
        gmailDriver.navigate().to(url + "" + emailUserName);
        largeWait();

        boolean session = isDisplayedById(gmailDriver, "Passwd", MEDIUM_TIMEOUT);
        if (session) {
            DriverConfig.setLogString("Enter username and password for Email(" + emailUserName
                    + "/" + emailPassword + ")", true);
            if (isDisplayedById(gmailDriver, "reauthEmail", SHORT_TIMEOUT)
                    && gmailDriver.findElement(By.id("reauthEmail")).getText()
                            .equalsIgnoreCase(emailUserName)) {
                clearAndInput(gmailDriver, By.id("Passwd"), emailPassword);
            } else {
                clearAndInput(gmailDriver, By.id("Email"), emailUserName);
                clearAndInput(gmailDriver, By.id("Passwd"), emailPassword);
            }

            mediumWait();
            WebElement signInButtonElement = gmailDriver.findElement(By.id("signIn"));
            DriverConfig.setLogString("click Sign In button", true);
            signInButtonElement.click();
            largeWait();
            boolean button = isDisplayedById(gmailDriver, "no-button", MEDIUM_TIMEOUT);
            if (button) {
                WebElement buttonElement = gmailDriver.findElement(By.id("no-button"));
                buttonElement.click();
                mediumWait();
            }
        }

        isDisplayedByCSS(gmailDriver, ".aio.UKr6le", MEDIUM_TIMEOUT);
        DriverConfig.setLogString("Click Inbox.", true);
        WebElement inboxElement = gmailDriver.findElement(By.partialLinkText("Inbox"));
        inboxElement.click();
        largeWait();

        DriverConfig.setLogString("Click Subject of Email." , true);
        DriverConfig.setLogString("Subject of Email : "+ subject , true);
        WebElement webElement = gmailDriver.findElement(By.cssSelector(".ae4.UI.UJ"));
        List<WebElement> tagElement = webElement.findElements(By.tagName(TAG_BOLD));
        WebElement firstElement = tagElement.get(0);
        if (firstElement != null && firstElement.isDisplayed()
                && firstElement.getText().equalsIgnoreCase(subject)) {
            firstElement.click();
            largeWait();
        }

        DriverConfig.setLogString("Get temporary Password/activation link from Email.", true);
        if (boldIndex == 1) {
            WebElement divBodyElement = gmailDriver.findElement(By.xpath("//*[contains(@class,'adO')]"));
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
            WebElement divBodyElement = gmailDriver.findElement(By.className("gs"));
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

        deleteFirstMail(gmailDriver);

        DriverConfig.setLogString("Click user for Signout option", true);
        boolean value = isDisplayedById(gmailDriver, "gbgs4dn", MEDIUM_TIMEOUT);
        DriverConfig.setLogString("User name displayed for sign out : " + value, true);
        if (value) {
            gmailDriver.findElement(By.id("gbgs4dn")).click();
        } else {
            WebElement signOutElement = gmailDriver.findElement(
                    By.partialLinkText(emailUserName));
            signOutElement.click();
        }
        smallWait();

        DriverConfig.setLogString("Click Signout", true);
        gmailDriver.findElement(By.id("gb_71")).click();

        mediumWait();
        mediumWait();
        gmailDriver.close();
        gmailDriver.quit();
        gmailDriver = null;
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

        
        if (gmailDriver == null) {
           // driverConfig.loadDriver();
           gmailDriver = new ChromeDriver();
        }
        
        largeWait();
        DriverConfig.setLogString("Load Email portal URL.", true);
        DriverConfig.setLogString("URL : "+ url, true);
        gmailDriver.navigate().to(url);
        // driver.navigate().to(url + "" + emailUserName);
        largeWait();

        boolean session = isDisplayedById(gmailDriver, "Passwd", MEDIUM_TIMEOUT);
        if (session) {
            DriverConfig.setLogString("Enter username and password for Email(" + emailUserName
                    + "/" + emailPassword + ")", true);
            gmailDriver.findElement(By.id("Email")).sendKeys(emailUserName);

            gmailDriver.findElement(By.id("Passwd")).sendKeys(emailPassword);

            mediumWait();
            WebElement signInButtonElement = gmailDriver.findElement(By.id("signIn"));
            DriverConfig.setLogString("click Sign In button", true);
            signInButtonElement.click();
            largeWait();
            boolean button = isDisplayedById(gmailDriver, "no-button", MEDIUM_TIMEOUT);
            if (button) {
                WebElement buttonElement = gmailDriver.findElement(By.id("no-button"));
                buttonElement.click();
                mediumWait();
            }
        }
        isDisplayedByCSS(gmailDriver, ".aio.UKr6le", MEDIUM_TIMEOUT);
        deleteFirstMail(gmailDriver);

        DriverConfig.setLogString("Click user for Signout option", true);
        boolean value = isDisplayedById(gmailDriver, "gbgs4dn", MEDIUM_TIMEOUT);
        DriverConfig.setLogString("User name displayed for sign out : " + value, true);
        if (value) {
            gmailDriver.findElement(By.id("gbgs4dn")).click();
        } else {
            WebElement signOutElement = gmailDriver.findElement(
                    By.partialLinkText(emailUserName));
            signOutElement.click();
        }
        smallWait();

        DriverConfig.setLogString("Click Signout", true);
        gmailDriver.findElement(By.id("gb_71")).click();

        mediumWait();
        mediumWait();
        
        gmailDriver.close();
        gmailDriver.quit();
        gmailDriver = null;
    }

    /**
     * Delete first mail.
     * @param driver the driver
     */
    public void deleteFirstMail(WebDriver driver) {

        
        DriverConfig.setLogString("Delete First Mail", true);
        DriverConfig.setLogString("Click Inbox", true);
        WebElement inboxElement = driver.findElement(By.partialLinkText("Inbox"));
        inboxElement.click();
        mediumWait();

        DriverConfig.setLogString("Click Subject of Email.", true);
        // boolean subjectDisplayed=isDisplayedByTagName(driver, TAG_BOLD, MEDIUM_TIMEOUT);
        boolean subjectDisplayed = isDisplayedByCSS(driver, ".ae4.UI.UJ", MEDIUM_TIMEOUT);
        if (subjectDisplayed) {
            WebElement webElement1 = driver.findElement(By.cssSelector(".ae4.UI.UJ"));
            // WebElement webElement1 = driver.findElement(By.xpath(".//*[span]"));
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
