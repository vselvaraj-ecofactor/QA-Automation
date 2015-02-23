/*
 * TestLinkData.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.util.mail;

import java.util.List;

/**
 * The Class TestLinkData.
 */
public class TestLinkData {

	private String project;
	private String testPlan;
	private List<TestCaseData> testCaseData;

	/**
	 * Gets the project.
	 * @return the project
	 */
	public String getProject() {
		return project;
	}

	/**
	 * Sets the project.
	 * @param project the new project
	 */
	public void setProject(String project) {
		this.project = project;
	}

	/**
	 * Gets the test plan.
	 * @return the test plan
	 */
	public String getTestPlan() {
		return testPlan;
	}

	/**
	 * Sets the test plan.
	 * @param testPlan the new test plan
	 */
	public void setTestPlan(String testPlan) {
		this.testPlan = testPlan;
	}

	/**
	 * Gets the test case data.
	 * @return the test case data
	 */
	public List<TestCaseData> getTestCaseData() {
		return testCaseData;
	}

	/**
	 * Sets the test case data.
	 * @param testCaseData the new test case data
	 */
	public void setTestCaseData(List<TestCaseData> testCaseData) {
		this.testCaseData = testCaseData;
	}
}
