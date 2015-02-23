/*
 * TestLinkHtmlContentBuilder.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.util.mail;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ecofactor.qa.automation.util.DriverConfig;

/**
 * The Class DeleteUserHtmlContentBuilder.
 */
public class TestLinkHtmlContentBuilder implements TestLinkContentBuilder {

	private int success = 0;
	private int failure = 0;
	private int notRun = 0;
	private int count = 0;
	HashMap<String, Integer> map;
	HashMap<String, HashMap<String, Integer>> testMap = new HashMap<String, HashMap<String, Integer>>();

	/**
	 * Test link content.
	 * @param type the type
	 * @param testLinkData the test link data
	 * @return the string
	 */
	@Override
	public String testLinkContent(String module, List<TestLinkData> testLinkDatas) {
		String content = "";

		if (testLinkDatas != null && testLinkDatas.size() > 0) {
			content += mailHeader(module);
			for (TestLinkData testLinkData : testLinkDatas) {
				map = new HashMap<String, Integer>();
				content += getprojectName(testLinkData);
				content += getTestPlan(testLinkData);
				content += getTestCaseDetails(testLinkData);
				content += appendRow(testLinkData);
			}
		} else {
			content += "<br/> <b style=\"font-colot:red\" color=\"red\">Sorry, Connectivity Issue / Data not found</b>";
		}
		content += getMailFooter();
		content += resultHeader();
		content += result(testMap);
		return content;
	}

	/**
	 * Gets the test case details.
	 * @param testLinkData the test link data
	 * @return the test case details
	 */
	private String getTestCaseDetails(TestLinkData testLinkData) {
		DriverConfig.setLogString("Test case Details", true);
		StringBuilder logContent = new StringBuilder();

		List<TestCaseData> thDetails = testLinkData.getTestCaseData();
		TestCaseData testCaseData = null;

		if (thDetails != null && !thDetails.isEmpty()) {
			int noOfTestCases = 0;
			int testPlanSuccess = 0;
			int testPlanFailures = 0;
			int testPlanNotRun = 0;
			while (noOfTestCases < thDetails.size()) {
				testCaseData = thDetails.get(noOfTestCases);
				noOfTestCases++;
				count++;
				if (testCaseData.getTestCaseID() != null && !testCaseData.getTestCaseID().isEmpty()) {
					logContent.append("<td style=\"background-color:green\"><b style=\"text-decoration:none;\">" + "&nbsp;" + testCaseData.getTestCaseID() + "</b></td>");
				}

				if (testCaseData.getMethodName() != null && !testCaseData.getMethodName().isEmpty()) {
					logContent.append("<td style=\"background-color:green\"><b style=\"text-decoration:none;\">" + "&nbsp;" + testCaseData.getMethodName() + "</b></td>");
				}

				if (testCaseData.getStatus() != null && !testCaseData.getMethodName().toString().isEmpty()) {
					if (testCaseData.getStatus().toString().equalsIgnoreCase("p")) {
						logContent.append("<td style=\"background-color:green\"><b style=\"text-decoration:none;\">" + "&nbsp; Passed </b></td>");
						success++;
						testPlanSuccess++;
					} else if (testCaseData.getStatus().toString().equalsIgnoreCase("f")) {
						logContent.append("<td style=\"background-color:red\"><b style=\"text-decoration:none;\">" + "&nbsp; Failed </b></td>");
						failure++;
						testPlanFailures++;
					} else {
						logContent.append("<td style=\"background-color:yellow\"><b style=\"text-decoration:none;\">" + "&nbsp; " + testCaseData.getStatus() + " </b></td>");
						notRun++;
						testPlanNotRun++;
					}
				}

				if (testCaseData.getTimeStamp() != null && !testCaseData.getTimeStamp().isEmpty()) {
					logContent.append("<td style=\"background-color:green\"><b style=\"text-decoration:none;\">" + "&nbsp;" + testCaseData.getTimeStamp() + "</b></td>");
				}

				if (testCaseData.getSprint() != null && !testCaseData.getSprint().isEmpty()) {
					logContent.append("<td style=\"background-color:green\"><b style=\"text-decoration:none;\">" + "&nbsp;" + testCaseData.getSprint() + "</b></td>");
				}

				if (thDetails.size() >= 1) {
					if (noOfTestCases == thDetails.size()) {
						logContent.append("</tr>");
					} else {
						logContent.append("</tr><tr>");
					}
				}
			}
			map.put("NotRun", testPlanNotRun);
			map.put("Failure", testPlanFailures);
			map.put("Success", testPlanSuccess);
			testMap.put(testLinkData.getTestPlan(), map);
		}
		return logContent.toString();
	}

	/**
	 * Gets the project name.
	 * @param testLinkData the test link data
	 * @return the project name
	 */
	private String getprojectName(TestLinkData testLinkData) {
		DriverConfig.setLogString("Get suite name for Mail content", true);
		StringBuilder logContent = new StringBuilder();
		List<TestCaseData> thDetails = testLinkData.getTestCaseData();
		if (thDetails != null && thDetails.size() > 1) {
			logContent.append("<tr><td rowspan=\"" + thDetails.size() + "\"><b>" + "&nbsp;" + testLinkData.getProject() + "</b></td>");
		} else {
			logContent.append("<tr><td><b> " + "&nbsp;" + testLinkData.getProject() + " </b></td>");
		}
		return logContent.toString();
	}

	/**
	 * Gets the test plan.
	 * @param testLinkData the test link data
	 * @return the test plan
	 */
	private String getTestPlan(TestLinkData testLinkData) {
		DriverConfig.setLogString("Get suite name for Mail content", true);

		StringBuilder logContent = new StringBuilder();
		List<TestCaseData> thDetails = testLinkData.getTestCaseData();
		if (thDetails != null && thDetails.size() > 1) {
			logContent.append("<td rowspan=\"" + thDetails.size() + "\"><b>" + "&nbsp;" + testLinkData.getTestPlan() + "</b></td>");
		} else {
			logContent.append("<tr><td><b> " + "&nbsp;" + testLinkData.getTestPlan() + " </b></td>");
		}

		return logContent.toString();
	}

	/**
	 * Mail header.
	 * @return the string
	 */
	private String mailHeader(String module) {

		StringBuilder mailContent = new StringBuilder();
		mailContent.append("<html>");
		mailContent.append("<head></head>");
		mailContent.append("<body>");
		mailContent.append("<p style=\"font-family:trebuchet ms,helvetica,sans-serif;font-size:14\">Hi All, <br/><br/>Please find the " + module + " Test Link details.</p>");
		mailContent.append("<table border=\"1\" width=\"100%\" style=\"font-family:trebuchet ms,helvetica,sans-serif;font-size:12;border-collapse:collapse\">");
		mailContent.append("<tr style=\"font-weight:bold;text-align:center;background-color:#E8E3E1;height:30px\">");
		mailContent.append("<td width=\"5%\">Project</td><td width=\"20%\">Test Plan</td><td width=\"10%\">Test Case Id</td><td width=\"20%\">Method Name</td><td width=\"10%\">Status</td><td width=\"25%\">Last Execution Date</td><td width=\"10%\">Latest Sprint</td>");
		mailContent.append(" </tr>");
		return mailContent.toString();
	}


	private String resultHeader() {
		StringBuilder mailContent = new StringBuilder();
		mailContent.append("</br>");
		mailContent.append("<html>");
		mailContent.append("<head></head>");
		mailContent.append("<body>");
		mailContent.append("<table border=\"1\" width=\"40%\" style=\"font-family:trebuchet ms,helvetica,sans-serif;font-size:12;border-collapse:collapse\">");
		mailContent.append("<tr style=\"font-weight:bold;text-align:center;background-color:#E8E3E1;height:30px\">");
		mailContent.append("<td width=\"10%\">Test Plan</td><td width=\"10%\">Not Run</td><td width=\"10%\">Failures</td><td width=\"10%\">Success</td>");
		mailContent.append(" </tr>");
		return mailContent.toString();
	}

	private String result(HashMap<String, HashMap<String, Integer>> testMap) {
		StringBuilder mailContent = new StringBuilder();
		for (Map.Entry<String, HashMap<String, Integer>> entry : testMap.entrySet()) {
			mailContent.append("<tr><td style=\"background-color:green\"><b style=\"text-decoration:none;\">" + "&nbsp;" + entry.getKey() + "</b></td>");
			HashMap<String, Integer> value = entry.getValue();
			for (Map.Entry<String, Integer> entry1 : value.entrySet()) {
				if(entry1.getKey().equalsIgnoreCase("Success")){
					mailContent.append("<td style=\"background-color:green\"><b style=\"text-decoration:none;\">" + "&nbsp;" + entry1.getValue() + "</b></td>");
				}
				if(entry1.getKey().equalsIgnoreCase("Failure")){
					mailContent.append("<td style=\"background-color:red\"><b style=\"text-decoration:none;\">" + "&nbsp;" + entry1.getValue() + "</b></td>");
				}
				if(entry1.getKey().equalsIgnoreCase("NotRun")){
					mailContent.append("<td style=\"background-color:yellow\"><b style=\"text-decoration:none;\">" + "&nbsp;" + entry1.getValue() + "</b></td>");
				}
			}
		}
		mailContent.append("</tr></table> "
				+ getColorIndications()
				+ "<br/><p><span style=\"color:#696969;\"><span style=\"font-family: trebuchet ms,helvetica,sans-serif;font-size:12px\">Thanks &amp; Regards<br />QA Automation Team</span></span></p></body></html>");
		return mailContent.toString();
	}

	/**
	 * Gets the color indications.
	 * @return the color indications
	 */
	private String getColorIndications() {

		DriverConfig.setLogString("Get color identifications for the mail", true);
		StringBuilder logContent = new StringBuilder();
		logContent.append("<br/>");
		logContent.append("<table border=\"0\" width=\30%\" style=\"font-family:trebuchet ms,helvetica,sans-serif;font-size:14\">");
		logContent.append("     <tr>");
		logContent.append("         <td width=\"10%\" style=\"background-color:green\"></td>");
		logContent.append("         <td>Total number of test cases : " + count + "</td>");
		logContent.append("     </tr>");
		logContent.append("     <tr>");
		logContent.append("         <td width=\"10%\" style=\"background-color:green\"></td>");
		logContent.append("         <td>Number of Success : " + success + "</td>");
		logContent.append("     </tr>");
		logContent.append("     <tr>");
		logContent.append("         <td style=\"background-color:red\"></td>");
		logContent.append("         <td>Number of Failures : " + failure + "</td>");
		logContent.append("     </tr>");
		logContent.append("     <tr>");
		logContent.append("         <td style=\"background-color:yellow\"></td>");
		logContent.append("         <td>Not Run : " + notRun + "</td>");
		logContent.append("     </tr>");
		logContent.append("</table>");
		return logContent.toString();
	}

	/**
	 * Gets the mail footer.
	 * @return the mail footer
	 */
	private String getMailFooter() {

		DriverConfig.setLogString("Add mail footer details", true);
		StringBuilder logContent = new StringBuilder();
		logContent.append("</table></body></html>");
		return logContent.toString();
	}

	/**
	 * Append row.
	 * @param algoUserData the algo user data
	 * @return the string
	 */
	private String appendRow(TestLinkData testLinkData) {

		StringBuilder logContent = new StringBuilder();
		List<TestCaseData> thDetails = testLinkData.getTestCaseData();
		if (thDetails == null || thDetails.size() == 0) {
			logContent.append("</tr>");
		}
		return logContent.toString();
	}

}
