/*
 * TestCaseData.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.util.mail;

/**
 * The Class TestCaseData.
 */
public class TestCaseData {

	private String testCaseID;
	private String methodName;
	private String timeStamp;
	private String status;
	private String sprint;

	/**
	 * Gets the test case id.
	 * @return the test case id
	 */
	public String getTestCaseID() {
		return testCaseID;
	}

	/**
	 * Sets the test case id.
	 * @param testCaseID the new test case id
	 */
	public void setTestCaseID(String testCaseID) {
		this.testCaseID = testCaseID;
	}

	/**
	 * Gets the method name.
	 * @return the method name
	 */
	public String getMethodName() {
		return methodName;
	}

	/**
	 * Sets the method name.
	 * @param methodName the new method name
	 */
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	/**
	 * Gets the sprint.
	 * @return the sprint
	 */
	public String getSprint() {
		return sprint;
	}

	/**
	 * Sets the sprint.
	 * @param sprint the new sprint
	 */
	public void setSprint(String sprint) {
		this.sprint = sprint;
	}

	/**
	 * Gets the status.
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 * @param status the new status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Gets the time stamp.
	 * @return the time stamp
	 */
	public String getTimeStamp() {
		return timeStamp;
	}

	/**
	 * Sets the time stamp.
	 * @param timeStamp the new time stamp
	 */
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
}
