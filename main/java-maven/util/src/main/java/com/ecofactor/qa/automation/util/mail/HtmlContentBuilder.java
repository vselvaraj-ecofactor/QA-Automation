/*
 * HtmlContentBuilder.java
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

import com.ecofactor.qa.automation.util.DriverConfig;

/**
 * The Class HtmlContentBuilder.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class HtmlContentBuilder implements ContentBuilder {

    boolean algo = false;
    boolean consumerRegression = false;
    boolean isPageLoaded = true;
    HashMap<String, String> map;
    int failure = 0;
    int success = 0;
    int failureCount = 0;
    int successCount = 0;

    /**
     * Gets the content.
     * @param type the type
     * @param userData the user data
     * @return the content
     * @see com.ecofactor.qa.automation.util.mail.ContentBuilder#getContent(java.util.List)
     */
    @Override
    public String getContent(TestType type, List<UserData> userDataList, String env) {

		for (UserData userData : userDataList) {
			isPageLoaded = userData.isPageLoad();
		}
		for (UserData userData : userDataList) {
			if (!userData.isHasFailures()) {
				successCount++;
			}
		}
		for (UserData userData : userDataList) {
			if (userData.isHasFailures()) {
				failureCount++;
			}
		}
        String content = "";
        if (type == TestType.CONSUMER || type == TestType.GRIDWITHEXCEL) {
            boolean hasGoodUsers = false;
            if (isPageLoaded) {
                int index = 1;
                for (UserData userData : userDataList) {
                    if (!userData.isHasFailures()) {
                        if (index == 1) {
                            content += getConsumerMailHeader(type, env, successCount, failureCount );
                            hasGoodUsers = true;
                        }
                        map = new HashMap<String, String>();
                        content += getIndex(userData, index);
                        content += getUserId(userData);
                        content += getLoginContent(userData);
                        content += getThermostatContent(userData);
                        content += appendRow(userData);
                        index++;
                    }

                }

                if (!hasGoodUsers) {
                    if (type == TestType.GRIDWITHEXCEL) {
                        content += ("<p style=\"font-family:trebuchet ms,helvetica,sans-serif;font-size:14\">Hi All, <br/><br/>Please find the attached excel for Consumer Users in "
                                + env + " environment.</p>");
                    } else {
                        content += ("<p style=\"font-family:trebuchet ms,helvetica,sans-serif;font-size:14\">Hi All, <br/><br/>Please find below test results for automated verification of test accounts.</p>");
                    }
                }

                int badIndex = 1;
                for (UserData userData : userDataList) {
                    if (userData.isHasFailures()) {
                        if (badIndex == 1) {
                            content += getBadUser();
                        }
                        map = new HashMap<String, String>();
                        content += getIndex(userData, badIndex);
                        content += getUserId(userData);
                        content += getLoginContent(userData);
                        content += getThermostatContent(userData);
                        content += appendRow(userData);
                        badIndex++;
                    }

                }
            } else {
                content += getPageNotLoadedContent(env);
            }
        } else if (type == TestType.ALGO) {
            algo = true;
            content += getAlgorithmTableHeader();
            if (isPageLoaded) {
                for (UserData userData : userDataList) {
                    map = new HashMap<String, String>();
                    content += getSuiteName(userData);
                    content += getUserId(userData);
                    content += getThermostatContent(userData);
                    content += appendRow(userData);
                }
            } else {
                content += getPageNotLoadedContent(env);
            }
        } else if (type == TestType.CONSUMERREGRESSION) {
            consumerRegression = true;
            content += getConsumerRegressionMailHeader();
            if (isPageLoaded) {
                for (UserData userData : userDataList) {
                    map = new HashMap<String, String>();
                    content += getSuiteName(userData);
                    content += getUserId(userData);
                    content += getThermostatContent(userData);
                    content += appendRow(userData);
                }
            } else {
                content += getPageNotLoadedContent(env);
            }
        } else if (type == TestType.EXCEL) {
            content += getExcelContent(env);
        }
        content += getMailFooter(type);

        return content;
    }

    /**
     * Gets the mail header.
     * @return the mail header
     */
    private String getAlgorithmTableHeader() {

        StringBuilder mailContent = new StringBuilder();
        mailContent.append("<html>");
        mailContent.append("<head></head>");
        mailContent.append("<body>");
        mailContent.append("<p style=\"font-family:trebuchet ms,helvetica,sans-serif;font-size:14\">Hi All, <br/><br/>Please find below results for user used by automation.</p>");
        mailContent.append("<table border=\"1\" width=\"87%\" style=\"font-family:trebuchet ms,helvetica,sans-serif;font-size:12;border-collapse:collapse\">");
        mailContent.append("<tr style=\"font-weight:bold;text-align:center;background-color:#E8E3E1;height:30px\">");
        mailContent
                .append("<td width=\"6%\">Suite</td><td width=\"15%\">User</td><td width=\"5%\">Thermostat</td><td width=\"12%\" colspan=\"2\">Mode</td><td width=\"5%\">Programs</td><td width=\"5%\">Schedule</td><td width=\"5%\">New Schedule UI</td><td width=\"10%\">Algorithm</td><td width=\"10%\">Active algorithm</td><td width=\"14%\">Active Algorithm's Next Phase Time</td><td width=\"5%\">ALGO Running</td>");
        mailContent.append("    </tr>   ");
        return mailContent.toString();
    }

    /**
     * Gets the consumer mail header.
     * @return the consumer mail header
     */
    private String getConsumerMailHeader(TestType type, String env, int successCount, int failureCount) {

        StringBuilder mailContent = new StringBuilder();
        mailContent.append("<html>");
        mailContent.append("<head></head>");
        mailContent.append("<body>");
        if (type == TestType.GRIDWITHEXCEL) {
            mailContent.append("<p style=\"font-family:trebuchet ms,helvetica,sans-serif;font-size:14\">Hi All, <br/><br/>Please find the attached excel for Consumer Users in "
                    + env + " environment.</p>" + getColorIndications(type));
        } else {
            mailContent
                    .append("<p style=\"font-family:trebuchet ms,helvetica,sans-serif;font-size:14\">Hi All, <br/><br/>Please find below test results for automated verification of test accounts.</p>");
        }

        mailContent.append("<p style=\"font-family:trebuchet ms,helvetica,sans-serif;font-size:14\">Below listed good Users, </p>");
        mailContent.append("<table border=\"1\" width=\"90%\" style=\"font-family:trebuchet ms,helvetica,sans-serif;font-size:12;border-collapse:collapse\">");
        mailContent.append("<tr style=\"font-weight:bold;text-align:center;background-color:#E8E3E1;height:30px\">");
        mailContent
                .append("<td width=\"3%\">S.No</td><td width=\"18%\">User Id</td><td width=\"5%\">Login</td><td width=\"8%\">Thermostat</td><td width=\"12%\" colspan=\"2\">Mode</td><td width=\"5%\">Programs</td><td width=\"5%\">Schedule</td><td width=\"5%\">New Schedule UI</td><td width=\"10%\">Algorithm</td><td width=\"10%\">Active algorithm</td><td width=\"14%\">Active Algorithm's Next Phase Time</td>");
        mailContent.append("    </tr>   ");
        return mailContent.toString();
    }

    private String getBadUser() {

        StringBuilder mailContent = new StringBuilder();
        mailContent.append("</table><p style=\"font-family:trebuchet ms,helvetica,sans-serif;font-size:14\">Below listed bad Users, </p>");
        mailContent.append("<table border=\"1\" width=\"90%\" style=\"font-family:trebuchet ms,helvetica,sans-serif;font-size:12;border-collapse:collapse\">");
        mailContent.append("<tr style=\"font-weight:bold;text-align:center;background-color:#E8E3E1;height:30px\">");
        mailContent
                .append("<td width=\"3%\">S.No</td><td width=\"18%\">User Id</td><td width=\"5%\">Login</td><td width=\"8%\">Thermostat</td><td width=\"12%\" colspan=\"2\">Mode</td><td width=\"5%\">Programs</td><td width=\"5%\">Schedule</td><td width=\"5%\">New Schedule UI</td><td width=\"10%\">Algorithm</td><td width=\"10%\">Active algorithm</td><td width=\"14%\">Active Algorithm's Next Phase Time</td>");
        mailContent.append("    </tr>   ");
        return mailContent.toString();
    }

    /**
     * Gets the consumer regression mail header.
     * @return the consumer regression mail header
     */
    private String getConsumerRegressionMailHeader() {

        StringBuilder mailContent = new StringBuilder();
        mailContent.append("<html>");
        mailContent.append("<head></head>");
        mailContent.append("<body>");
        mailContent.append("<p style=\"font-family:trebuchet ms,helvetica,sans-serif;font-size:14\">Hi All, <br/><br/>Please find below results for user used by automation.</p>");
        mailContent.append("<table border=\"1\" width=\"92%\" style=\"font-family:trebuchet ms,helvetica,sans-serif;font-size:12;border-collapse:collapse\">");
        mailContent.append("<tr style=\"font-weight:bold;text-align:center;background-color:#E8E3E1;height:30px\">");
        mailContent
                .append("<td width=\"8%\">Suite</td><td width=\"19%\">User</td><td width=\"8%\">Thermostat</td><td width=\"12%\" colspan=\"2\">Mode</td><td width=\"5%\">Programs</td><td width=\"5%\">Schedule</td><td width=\"5%\">New Schedule UI</td><td width=\"10%\">Algorithm</td><td width=\"10%\">Active algorithm</td><td width=\"15%\">Active Algorithm's Next Phase Time</td>");
        mailContent.append("    </tr>   ");
        return mailContent.toString();
    }

    /**
     * Gets the excel content.
     * @param env the env
     * @return the excel content
     */
    private String getExcelContent(String env) {

        StringBuilder mailContent = new StringBuilder();
        mailContent.append("<html>");
        mailContent.append("<head></head>");
        mailContent.append("<body>");
        mailContent.append("<p style=\"font-family:trebuchet ms,helvetica,sans-serif;font-size:14\">Hi All, <br/><br/>Please find the attached excel for Consumer Users in " + env
                + " environment.</p>");
        return mailContent.toString();
    }

    /**
     * Gets the page not loaded content.
     * @return the page not loaded content
     */
    private String getPageNotLoadedContent(String env) {

        StringBuilder logContent = new StringBuilder();
        logContent.append("<tr style=\"font-weight:bold;text-align:center;background-color:#E8E3E1;height:30px\">");
        logContent.append("<td colspan=\"12\" style=\"background-color:red;font-size:14\">Consumer " + env + " environment is currently down</td>");
        logContent.append("    </tr>   ");
        return logContent.toString();
    }

    /**
     * Gets the suite name.
     * @param userData the user data
     * @return the suite name
     */
    private String getSuiteName(UserData userData) {

        DriverConfig.setLogString("Get suite name for Mail content", true);

        StringBuilder logContent = new StringBuilder();
        List<ThermostatData> thDetails = userData.getThermostatDatas();
        if (thDetails != null && thDetails.size() > 1) {
            logContent.append("<tr><td rowspan=\"" + thDetails.size() + "\"><b>" + "&nbsp;" + userData.getSuiteName() + "</b></td>");
        } else {
            logContent.append("<tr><td><b> " + "&nbsp;" + userData.getSuiteName() + " </b></td>");
        }

        return logContent.toString();
    }

    /**
     * Gets the index.
     * @param userData the user data
     * @param loopVal the loop val
     * @return the index
     */
    private String getIndex(UserData userData, int loopVal) {

        DriverConfig.setLogString("Get Index for Mail content", true);

        StringBuilder logContent = new StringBuilder();
        List<ThermostatData> thDetails = userData.getThermostatDatas();
        if (thDetails != null && thDetails.size() > 1) {
            logContent.append("<tr><td rowspan=\"" + thDetails.size() + "\">" + "&nbsp;" + loopVal + "</td>");
        } else {
            logContent.append("<tr><td> " + "&nbsp;" + loopVal + " </td>");
        }

        return logContent.toString();
    }

    /**
     * Gets the user id.
     * @param userId the user id
     * @return the user id
     */
    private String getUserId(UserData userData) {

        DriverConfig.setLogString("Get user id for Mail content", true);

        StringBuilder logContent = new StringBuilder();
        List<ThermostatData> thDetails = userData.getThermostatDatas();
        if (thDetails != null && thDetails.size() > 1) {
            logContent.append("<td rowspan=\"" + thDetails.size() + "\">" + "&nbsp;" + userData.getUserId() + "</td>");
        } else {
            logContent.append("<td> " + "&nbsp;" + userData.getUserId() + " </td>");
        }

        return logContent.toString();
    }

    /**
     * Gets the login content.
     * @param userData the user data
     * @return the login content
     */
    private String getLoginContent(UserData userData) {

        DriverConfig.setLogString("Login Mail content", true);
        StringBuilder logContent = new StringBuilder();
        List<ThermostatData> thDetails = userData.getThermostatDatas();
        String rowspan = "";
        if (thDetails != null && thDetails.size() > 1) {
            rowspan = " rowspan=\"" + thDetails.size() + "\"";
        }
        if (userData.isLogin()) {
            logContent.append("<td " + rowspan + " style=\"background-color:green\">&nbsp;Success</td>");
            map.put("login", "true");
        } else {
            logContent.append("<td " + rowspan + " style=\"background-color:red;\">&nbsp;Failure </td>");
            map.put("login", "false");
        }
        return logContent.toString();
    }

    /**
     * Gets the thermostat content.
     * @param userData the user data
     * @return the thermostat content
     */
    private String getThermostatContent(UserData userData) {

        DriverConfig.setLogString("Thermostat Mail content", true);
        StringBuilder logContent = new StringBuilder();

        List<ThermostatData> thDetails = userData.getThermostatDatas();
        ThermostatData thermostatData = null;

        if (thDetails != null && !thDetails.isEmpty()) {
            int noOfThermostats = 0;
            while (noOfThermostats < thDetails.size()) {
                thermostatData = thDetails.get(noOfThermostats);
                noOfThermostats++;

                if (thermostatData.isConnected()) {
                    logContent.append("<td style=\"background-color:green;text-indent:10px;font-weight:bold;\">" + thermostatData.getThermostatId() + "</td>");
                    map.put("thermostat", "true");
                } else {
                    logContent.append("<td style=\"background-color:red;text-indent:10px;font-weight:bold;\">" + thermostatData.getThermostatId() + "</td>");
                    map.put("thermostat", "false");
                }

                if (thermostatData.getModes()[0] == "Heat") {
                    logContent.append("<td style=\"background-color:green;text-indent:10px;font-weight:bold;\"> Heat </td>");
                    map.put("heat", "true");
                } else {
                    logContent.append("<td style=\"background-color:red;text-indent:10px;font-weight:bold;\"> Heat </td>");
                    map.put("heat", "false");
                }

                if (thermostatData.getModes()[1] == "Cool") {
                    logContent.append("<td style=\"background-color:green;text-indent:10px;font-weight:bold;\"> Cool </td>");
                    map.put("cool", "true");
                } else {
                    logContent.append("<td style=\"background-color:red;text-indent:10px;font-weight:bold;\"> Cool </td>");
                    map.put("cool", "false");
                }

                logContent.append(getProgramContent(userData));

                logContent.append(getWeeklyScheduleContent(userData));

                logContent.append(getNewScheduleUIContent(userData));

                String algoSub = "";
                for (String algoname : thermostatData.getSubsribedAlgorithms()) {
                    algoSub += algoname + "</br>";
                }

                logContent.append("<td style=\"background-color:green;text-indent:10px;font-weight:bold;text-align:center\"> " + algoSub.trim() + " </td>");

                logContent.append("<td style=\"background-color:green;text-indent:10px;font-weight:bold;\"> " + thermostatData.getActiveAlgorithm() + " </td>");

                logContent.append("<td style=\"background-color:green;text-indent:10px;font-weight:bold;\"> " + thermostatData.getNextPhaseTime() + " </td>");

                if (algo) {
                    if (thermostatData.isValidPhaseTime()) {
                        logContent.append("<td style=\"background-color:green;text-indent:10px;font-weight:bold;\"></td>");
                        map.put("phasetime", "true");
                    } else {
                        logContent.append("<td style=\"background-color:red;text-indent:10px;font-weight:bold;\"></td>");
                        map.put("phasetime", "false");
                    }

                }
                if (thDetails.size() >= 1) {
                    if (noOfThermostats == thDetails.size()) {
                        logContent.append("</tr>");
                    } else {
                        logContent.append("</tr><tr>");
                    }
                }
            }
        } else {

            map.put("Nothermostat", "false");
            logContent.append("<td style=\"background-color:red\">&nbsp;No Thermostat</td>");

            logContent.append("<td style=\"background-color:red\">-</td>");

            logContent.append("<td style=\"background-color:red\">-</td>");

            logContent.append("<td style=\"background-color:red\">-</td>");

            logContent.append("<td style=\"background-color:red\">-</td>");

            logContent.append("<td style=\"background-color:red\">-</td>");

            logContent.append("<td style=\"background-color:red\">-</td>");

            logContent.append("<td style=\"background-color:red\">-</td>");

            logContent.append("<td style=\"background-color:red\">-</td>");
        }

        countFailureAndSuccess(map);
        return logContent.toString();
    }

    /**
     * Count failure and success.
     * @param thmap the thmap
     */
    private void countFailureAndSuccess(HashMap<String, String> thmap) {

        DriverConfig.setLogString("Count failures and success", true);
        if (thmap.containsValue("false")) {
            failure++;
        } else {
            success++;
        }
    }

    /**
     * Gets the program content.
     * @param userData the user data
     * @return the program content
     */
    private String getProgramContent(UserData userData) {

        DriverConfig.setLogString("Program Mail content", true);

        StringBuilder logContent = new StringBuilder();
        List<ThermostatData> thDetails = userData.getThermostatDatas();
        if (thDetails != null && !thDetails.isEmpty()) {

            if (userData.isProgram()) {
                logContent.append("<td style=\"width:10px;background-color:green;text-indent:10px;font-weight:bold;\"></td>");
                map.put("program", "true");
            } else {
                logContent.append("<td style=\"width:10px;background-color:red;text-indent:10px;font-weight:bold;\"> </td>");
                map.put("program", "false");
            }
        } else {
            logContent.append("<td style=\"width:10px;background-color:red;text-indent:10px;font-weight:bold;\"> - </td>");
            map.put("program", "false");
        }

        return logContent.toString();
    }

    private String getNewScheduleUIContent(UserData userData) {

        DriverConfig.setLogString("New Schedule UI Mail content", true);

        StringBuilder logContent = new StringBuilder();
        List<ThermostatData> thDetails = userData.getThermostatDatas();
        if (thDetails != null && !thDetails.isEmpty()) {

            if (userData.isNewScheduleUI()) {
                logContent.append("<td style=\"width:10px;background-color:green;text-indent:10px;font-weight:bold;\"></td>");
                map.put("newScheduleUI", "true");
            } else {
                logContent.append("<td style=\"width:10px;background-color:red;text-indent:10px;font-weight:bold;\"> </td>");
                map.put("newScheduleUI", "false");
            }
        } else {
            logContent.append("<td style=\"width:10px;background-color:red;text-indent:10px;font-weight:bold;\"> - </td>");
            map.put("newScheduleUI", "false");
        }

        return logContent.toString();
    }

    /**
     * Gets the weekly schedule content.
     * @param userData the user data
     * @return the weekly schedule content
     */
    private String getWeeklyScheduleContent(UserData userData) {

        DriverConfig.setLogString("Get WeeklySchedule status for Mail content", true);
        StringBuilder logContent = new StringBuilder();
        List<ThermostatData> thDetails = userData.getThermostatDatas();
        if (thDetails != null && !thDetails.isEmpty()) {
            if (userData.isSchedule()) {
                logContent.append("<td style=\"width:10px;background-color:green;text-indent:10px;font-weight:bold;\"></td>");
                map.put("schedule", "true");
            } else {
                logContent.append("<td style=\"width:10px;background-color:red;text-indent:10px;font-weight:bold;\">  </td>");
                map.put("schedule", "false");
            }
        } else {
            logContent.append("<td style=\"width:10px;background-color:red;text-indent:10px;font-weight:bold;\"> - </td>");
            map.put("schedule", "false");
        }

        return logContent.toString();
    }

    /**
     * Gets the color indications.
     * @return the color indications
     */
    private String getColorIndications(TestType type) {

        DriverConfig.setLogString("Get color identifications for the mail", true);
        StringBuilder logContent = new StringBuilder();
        logContent.append("<table border=\"0\" width=\25%\" style=\"font-family:trebuchet ms,helvetica,sans-serif;font-size:14\">");
        logContent.append("     <tr>");
        logContent.append("         <td width=\"10%\" style=\"background-color:green\"></td>");
        if (type == TestType.CONSUMER || type == TestType.GRIDWITHEXCEL) {
            logContent.append("         <td>" + successCount + " Users Success/ Available</td>");
        } else {
            logContent.append("         <td>" + success + " Users Success/ Available</td>");
        }
        logContent.append("     </tr>");
        logContent.append("     <tr>");
        logContent.append("         <td style=\"background-color:red\"></td>");
        if (type == TestType.CONSUMER || type == TestType.GRIDWITHEXCEL) {
            logContent.append("         <td>" + failureCount + " Users Failure/ Not Available</td>");
        } else {
            logContent.append("         <td>" + failure + " Users Failure/ Not Available</td>");
        }
        logContent.append("     </tr>");
        logContent.append("</table>");
        return logContent.toString();
    }

    /**
     * Gets the mail footer.
     * @return the mail footer
     */
    private String getMailFooter(TestType type) {

        DriverConfig.setLogString("Add mail footer details", true);
        StringBuilder logContent = new StringBuilder();
        if (type == TestType.EXCEL || type == TestType.CONSUMER || type == TestType.GRIDWITHEXCEL) {
            logContent
                    .append("</table><br/><p><span style=\"color:#696969;\"><span style=\"font-family: trebuchet ms,helvetica,sans-serif;font-size:12px\">Thanks &amp; Regards<br />QA Automation Team</span></span></p></body></html>");
        }  else {
            logContent
                    .append("</table><br/> "
                            + getColorIndications(type)
                            + "<br/><p><span style=\"color:#696969;\"><span style=\"font-family: trebuchet ms,helvetica,sans-serif;font-size:12px\">Thanks &amp; Regards<br />QA Automation Team</span></span></p></body></html>");
        }
        return logContent.toString();
    }

    /**
     * Append row.
     * @param userData the user data
     * @return the string
     */
    private String appendRow(UserData userData) {

        StringBuilder logContent = new StringBuilder();
        List<ThermostatData> thDetails = userData.getThermostatDatas();
        if (thDetails == null || thDetails.size() == 0) {
            logContent.append("</tr>");
        }
        return logContent.toString();
    }

}
