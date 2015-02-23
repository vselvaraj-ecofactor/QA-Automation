/*
 * HttpUtil.java
 * Copyright (c) 2013, KITS, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of KITS
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * KITS.
 */
package com.ecofactor.qa.automation.util;

import static com.ecofactor.qa.automation.util.WaitUtil.*;

import java.util.Map;
import java.util.Set;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * HttpUtil is a helper utility class to perform http request.
 * @author $Author: rvinoopraj $
 * @version $Rev: 23070 $ $Date: 2013-09-18 15:18:51 +0530 (Wed, 18 Sep 2013) $
 */
public final class HttpsUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(HttpsUtil.class);
	private static WebDriver driver;

	/**
	 * Gets the.
	 * @param url the url
	 * @return the https response
	 */
	public static String get(String url, Map<String, String> params, int expectedStatus) {

		String content = null;
		HttpGet request = new HttpGet(url);
		URIBuilder builder = new URIBuilder(request.getURI());

		if(params!=null)
		{
    		Set<String> keys = params.keySet();
    		for (String key : keys) {
    			builder.addParameter(key, params.get(key));
    		}
    	}

		try {
			request.setURI(builder.build());
            DriverConfig.setLogString("URL " + request.getURI().toString(), true);
            driver.navigate().to(request.getURI().toString());
            tinyWait();
            content = driver.findElement(By.tagName("Body")).getText();
            DriverConfig.setLogString("Content: " + content, true);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		} finally {
			request.releaseConnection();
		}

		return content;
	}

	/**
	 * Load driver.
	 */
	public static void loadDriver() {

		/*driver = LocalDriverFactory.createInstance(LocalDriverFactory.FIREFOX);
		driver.manage().window().maximize();
		WaitUtil.veryHugeWait();*/
		ProfilesIni firefoxProfile = new ProfilesIni();
		FirefoxProfile profile = firefoxProfile.getProfile("default");
		driver = new FirefoxDriver(profile);
		driver.manage().window().maximize();
		largeWait();
		/*WebDriver driver = LocalDriverFactory.createInstance(LocalDriverFactory.FIREFOX);
         LocalDriverManager.setWebDriver(driver);
        LocalDriverManager.getDriver().manage().window().maximize();
        DriverConfig.setLogString("Wait for few seconds after driver initialize", true);*/

	}

	/**
	 * Close driver.
	 */
	public static void closeDriver() {

		driver.quit();
	}
}
