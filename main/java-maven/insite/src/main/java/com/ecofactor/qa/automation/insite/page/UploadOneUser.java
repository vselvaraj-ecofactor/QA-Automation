/*
 * UploadOneUser.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.insite.page;

/**
 * The Interface UploadOneUser.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public interface UploadOneUser extends InsiteAuthenticatedPage {

    /**
	 * Fill form data.
	 * @param emailId the email id
	 */
    public void populateForm(String emailId,String ecpId);

    /**
	 * Fill form data.
	 *
	 * @param emailId the email id
	 * @param fieldName the field name
	 * @param fieldValue the field value
	 */
    public void populateForm(String emailId, String fieldName, String fieldValue);

    /**
	 * Validate and submit form.
	 */
    public void validateAndSubmitForm();

    /**
	 * Validate and submit form.
	 *
	 * @param fieldName the field name
 	 */
    public void validateAndSubmitForm(String fieldName);

}
