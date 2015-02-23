/*
 * UploadOneUserImpl.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.insite.page;

import static com.ecofactor.qa.automation.insite.config.InsiteConfig.*;
import static com.ecofactor.qa.automation.util.PageUtil.*;
import static com.ecofactor.qa.automation.util.WaitUtil.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import com.ecofactor.qa.automation.insite.config.InsiteConfig;
import com.ecofactor.qa.automation.insite.config.InsiteLoginConfig;
import com.ecofactor.qa.automation.insite.config.UploadOneUserConfig;
import com.ecofactor.qa.automation.util.DateUtil;
import com.ecofactor.qa.automation.util.DriverConfig;
import com.ecofactor.qa.automation.util.PageUtil;
import com.google.inject.Inject;

/**
 * The Class UploadOneUserImpl.
 *
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class UploadOneUserImpl extends InsiteAuthenticatedPageImpl implements UploadOneUser {

	@Inject
	private InsiteLoginConfig loginConfig;
	@Inject
	private InsiteConfig appConfig;
	@Inject
	private UploadOneUserConfig uploadOneUserConfig;
	private static Logger logger = LoggerFactory.getLogger(UploadOneUserImpl.class);

	/**
	 * Load page.
	 *
	 * @see com.ecofactor.qa.automation.insite.page.InsiteAuthenticatedPage#loadPage()
	 */
	@SuppressWarnings("static-access")
	@Override
	public void loadPage() {

		String url = appConfig.get(INSITE_URL) + appConfig.get(appConfig.UPLOAD_ONE_USER);
		if (!DriverConfig.getDriver().getCurrentUrl().equalsIgnoreCase(url)) {
			clickUploadOneUser();
		}
	}

	/**
	 * Populate form.
	 *
	 * @param emailId the email id
	 * @see com.ecofactor.qa.automation.insite.page.UploadOneUser#fillFormData()
	 */
	@SuppressWarnings("static-access")
	@Override
	public void populateForm(String emailId, String ecpId) {

		largeWait();
		WebElement formElement = DriverConfig.getDriver().findElement(By.id("upload_form"));
		formElement.findElement(By.id("programId")).sendKeys(ecpId);
		DriverConfig.getDriver().findElement(By.id(uploadOneUserConfig.get(uploadOneUserConfig.EMAIL))).sendKeys(emailId);
		fillFieldsWithDefaultValues();
	}

	/**
	 * Fill fields with default values.
	 */
	private void fillFieldsWithDefaultValues(){

		Date date = DateUtil.getUTCDate();
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
		String timeStamp = dateFormat.format(date);

		String appender = timeStamp;
		long appenderKey = System.currentTimeMillis();

		DriverConfig.getDriver().findElement(By.id(uploadOneUserConfig.get(UploadOneUserConfig.CREATED_DATE))).sendKeys(appender);
		DriverConfig.getDriver().findElement(By.id(uploadOneUserConfig.get(UploadOneUserConfig.FIRST_NAME))).sendKeys("user_" + appenderKey);
		DriverConfig.getDriver().findElement(By.id(uploadOneUserConfig.get(UploadOneUserConfig.LAST_NAME))).sendKeys("user_" + appenderKey);

		DriverConfig.getDriver().findElement(By.id(uploadOneUserConfig.get(UploadOneUserConfig.PHONE))).sendKeys("744744");
		DriverConfig.getDriver().findElement(By.id(uploadOneUserConfig.get(UploadOneUserConfig.SERVICE_ADDR))).sendKeys("120 East Duane Avenue " + appenderKey);
		DriverConfig.getDriver().findElement(By.id(uploadOneUserConfig.get(UploadOneUserConfig.SERVICE_CITY))).sendKeys("Sunnyvale");
		DriverConfig.getDriver().findElement(By.id(uploadOneUserConfig.get(UploadOneUserConfig.SERVICE_STATE))).sendKeys("CA");
		DriverConfig.getDriver().findElement(By.id(uploadOneUserConfig.get(UploadOneUserConfig.SERVICE_ZIP))).sendKeys("94085");

		DriverConfig.getDriver().findElement(By.id("addressCheck")).click();
		DriverConfig.getDriver().findElement(By.id(uploadOneUserConfig.get(UploadOneUserConfig.SERVICE_COUNTRY))).sendKeys("US");
		DriverConfig.getDriver().findElement(By.id(uploadOneUserConfig.get(UploadOneUserConfig.NO_OF_TSTAT))).sendKeys("1");
	}

	/**
	 * @param emailId
	 * @param fieldName
	 * @param fieldValue
	 * @see com.ecofactor.qa.automation.insite.page.UploadOneUser#populateForm(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	public void populateForm(String emailId, String fieldName, String fieldValue) {

		largeWait();

		DriverConfig.setLogString("Fill in form fields to upload a user.", true);
		DriverConfig.setLogString("Test specific field Name: " + fieldName + "; field value: " + fieldValue, true);

		WebElement formElement = DriverConfig.getDriver().findElement(By.id("upload_form"));
		formElement.findElement(By.id("programId")).sendKeys("205");
		DriverConfig.getDriver().findElement(By.id(uploadOneUserConfig.get(UploadOneUserConfig.EMAIL))).sendKeys(emailId);
		fillFieldsWithDefaultValues();

		switch (fieldName) {
		case "PastInstallationDate":
			PageUtil.setValue(DriverConfig.getDriver(), "installationDate", fieldValue);
			break;
		case "FutureCreatedDate":
			PageUtil.setValue(DriverConfig.getDriver(), uploadOneUserConfig.get(UploadOneUserConfig.CREATED_DATE), fieldValue);
			break;
		case "CountryCodeLength":
			PageUtil.setValue(DriverConfig.getDriver(), "country", fieldValue);
			break;
		case "InvalidEmailId":
			PageUtil.setValue(DriverConfig.getDriver(), uploadOneUserConfig.get(UploadOneUserConfig.EMAIL), fieldValue);
			break;
		case "InvalidProgramId":
			PageUtil.setValue(DriverConfig.getDriver(), "programId", fieldValue);
			break;
		case "OtherLanguages":
			PageUtil.setValue(DriverConfig.getDriver(), "language", fieldValue);
			break;
		case "BlankMandatoryField":
			PageUtil.setValue(DriverConfig.getDriver(), "programId", fieldValue);
			break;
		case "CharachtersInProgramId":
			PageUtil.setValue(DriverConfig.getDriver(), "programId", fieldValue);
			break;
		case "CharachtersInPhoneNo":
			PageUtil.setValue(DriverConfig.getDriver(), uploadOneUserConfig.get(UploadOneUserConfig.PHONE), fieldValue);
			break;
		case "CharacthersInZipCode":
			PageUtil.setValue(DriverConfig.getDriver(), uploadOneUserConfig.get(UploadOneUserConfig.SERVICE_ZIP), fieldValue);
			break;
		case "NumbersInStateField":
			PageUtil.setValue(DriverConfig.getDriver(), uploadOneUserConfig.get(UploadOneUserConfig.SERVICE_STATE), fieldValue);
			break;
		case "StateCodeLength":
			PageUtil.setValue(DriverConfig.getDriver(), uploadOneUserConfig.get(UploadOneUserConfig.SERVICE_STATE), fieldValue);
			break;
		case "CharachtersInNoOfTSTATField":
			PageUtil.setValue(DriverConfig.getDriver(), uploadOneUserConfig.get(UploadOneUserConfig.NO_OF_TSTAT), fieldValue);
			break;
		case "CharachtersInAvgPrice":
			PageUtil.setValue(DriverConfig.getDriver(), "averagePrice", fieldValue);
			Assert.assertTrue(DriverConfig.getDriver().findElement(By.id("averagePrice")).getText().equalsIgnoreCase(""));
			break;
		default:
			break;
		}

		//DriverConfig.getDriver().findElement(By.id("addressCheck")).click();
	}

	/**
	 * Validate and submit form.
	 *
	 * @see com.ecofactor.qa.automation.insite.page.UploadOneUser#validateAndSubmitForm()
	 */
	@Override
	public void validateAndSubmitForm() {

		DriverConfig.getDriver().findElement(By.id("uploadUser")).click();
		closeAlert(DriverConfig.getDriver());
		smallWait();

	}

	/**
	 * @param fieldName
	 * @see com.ecofactor.qa.automation.insite.page.UploadOneUser#validateAndSubmitForm(java.lang.String)
	 */
	@Override
	public void validateAndSubmitForm(String fieldName) {

		DriverConfig.setLogString("Validate form data and click upload user.", true);
		WebElement error = null;

		DriverConfig.getDriver().findElement(By.id("uploadUser")).click();
		largeWait();

		switch (fieldName) {
		case "PastInstallationDate":
			Assert.assertTrue(DriverConfig.getDriver().findElement(By.id("uploadError")).getText().equalsIgnoreCase("Validator Errors found: Installation date is in the past."));
			break;
		case "FutureCreatedDate":
			Assert.assertTrue(DriverConfig.getDriver().findElement(By.id("uploadError")).getText().equalsIgnoreCase("Validator Errors found: Created Date is in Future"));
			break;
		case "CountryCodeLength":
			Assert.assertTrue(DriverConfig.getDriver().findElement(By.id("uploadError")).getText().equalsIgnoreCase("Validator Errors found: Country Code Invalid"));
			break;
		case "InvalidEmailId":
			Assert.assertTrue(DriverConfig.getDriver().findElement(By.id("uploadError")).getText().equalsIgnoreCase("Please fill in all required fields."));
			error = PageUtil.retrieveElementByTagText(DriverConfig.getDriver(), TAG_SPAN, "Invalid email address");
			Assert.assertTrue(error.getAttribute("htmlfor").equalsIgnoreCase("email"));
			break;
		case "InvalidProgramId":
			Assert.assertTrue(DriverConfig.getDriver().findElement(By.id("uploadError")).getText().trim().equalsIgnoreCase("Invalid Program Id"));
			break;
		case "OtherLanguages":
			Assert.assertTrue(DriverConfig.getDriver().findElement(By.id("uploadError")).getText().equalsIgnoreCase("Please fill in all required fields."));
			break;
		case "BlankMandatoryField":
			Assert.assertTrue(DriverConfig.getDriver().findElement(By.id("uploadError")).getText().equalsIgnoreCase("Please fill in all required fields."));
			error = PageUtil.retrieveElementByTagText(DriverConfig.getDriver(), TAG_SPAN, "Enter Program Id");
			Assert.assertTrue(error.getAttribute("htmlfor").equalsIgnoreCase("programId"));
			break;
		case "CharachtersInProgramId":
			Assert.assertTrue(DriverConfig.getDriver().findElement(By.id("uploadError")).getText().equalsIgnoreCase("Please fill in all required fields."));
			error = PageUtil.retrieveElementByTagText(DriverConfig.getDriver(), TAG_SPAN, "Enter Program Id");
			Assert.assertTrue(error.getAttribute("htmlfor").equalsIgnoreCase("programId"));
			break;
		case "CharachtersInPhoneNo":
			Assert.assertTrue(DriverConfig.getDriver().findElement(By.id("uploadError")).getText().equalsIgnoreCase("Please fill in all required fields."));
			break;
		case "CharacthersInZipCode":
			Assert.assertTrue(DriverConfig.getDriver().findElement(By.id("uploadError")).getText().equalsIgnoreCase("Please fill in all required fields."));
			break;
		case "NumbersInStateField":
			Assert.assertTrue(DriverConfig.getDriver().findElement(By.id("uploadError")).getText().equalsIgnoreCase("Please fill in all required fields."));
			break;
		case "StateCodeLength":
			Assert.assertTrue(DriverConfig.getDriver().findElement(By.id("uploadError")).getText().equalsIgnoreCase("Please fill in all required fields."));
			break;
		case "CharachtersInNoOfTSTATField":
			Assert.assertTrue(DriverConfig.getDriver().findElement(By.id("uploadError")).getText().equalsIgnoreCase("Please fill in all required fields."));
			error = PageUtil.retrieveElementByTagText(DriverConfig.getDriver(), TAG_SPAN, "Enter Number of Thermostats");
			Assert.assertTrue(error.getAttribute("htmlfor").equalsIgnoreCase("numberOfThermostats"));
			break;
		case "CharachtersInAvgPrice":
			closeAlert(DriverConfig.getDriver());
			break;
		default:
			break;
		}

	}

}
