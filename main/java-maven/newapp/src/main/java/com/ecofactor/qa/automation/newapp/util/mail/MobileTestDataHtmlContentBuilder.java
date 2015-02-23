/*
 * MobileTestDataHtmlContentBuilder.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.newapp.util.mail;

import java.util.List;
import java.util.Map;

import com.ecofactor.qa.automation.newapp.model.ThermostatData;
import com.ecofactor.qa.automation.newapp.model.UserData;
import com.ecofactor.qa.automation.platform.util.LogUtil;
import com.ecofactor.qa.automation.util.mail.TestType;

/**
 * The Class MobileTestDataHtmlContentBuilder.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class MobileTestDataHtmlContentBuilder implements MobileTestDataContentBuilder {

    private int failureCount;
    private int successCount;

    /**
     * Mobile user content.
     * @param type the type
     * @param userDatas the user datas
     * @param env the env
     * @return the string
     * @see com.ecofactor.qa.automation.newapp.util.mail.MobileTestDataContentBuilder#mobileUserContent(com.ecofactor.qa.automation.util.mail.TestType,
     *      java.util.List, java.lang.String)
     */
    @Override
    public String mobileUserContent(final TestType type, final List<UserData> userDatas,
            final String env) {

        for (final UserData userData : userDatas) {
            if (!userData.isHasFailures()) {
                successCount++;
            }
        }
        for (final UserData userData : userDatas) {
            if (userData.isHasFailures()) {
                failureCount++;
            }
        }
        final StringBuilder content = new StringBuilder();
        int index = 1;
        if (userDatas != null && userDatas.size() > 0) {
            content.append(mailHeader());
            for (final UserData userData : userDatas) {
                content.append(getSNo(userData, index));
                content.append(getUser(userData));
                content.append(getLoginContent(userData));
                content.append(getLocationNo(userData));
                content.append(getNoOfThermostats(userData));
                content.append(getThermostat(userData));
                content.append(appendRow(userData));
                index++;
            }
        }
        content.append(getMailFooter());
        return content.toString();
    }

    /**
     * Slave users.
     * @param type the type
     * @param myMap the my map
     * @param env the env
     * @return the string
     * @see com.ecofactor.qa.automation.newapp.util.mail.MobileTestDataContentBuilder#slaveUsers(com.ecofactor.qa.automation.util.mail.TestType,
     *      java.util.Map, java.lang.String)
     */
    @Override
    public String slaveUsers(final TestType type, final Map<String, List<String>> myMap,
            final String env) {

        final StringBuilder content = new StringBuilder();
        content.append(slaveUserMailHeader());
        for (final String key : myMap.keySet()) {
            content.append(getSlave(key));
            List<String> value = myMap.get(key);
            content.append(getUserCount(value));
            content.append(getUsers(value));
        }
        content.append(getMailFooter());
        return content.toString();
    }

    /**
     * Mail header.
     * @return the string
     */
    private String mailHeader() {

        final StringBuilder mailContent = new StringBuilder();
        mailContent.append("<html>");
        mailContent.append("<head></head>");
        mailContent.append("<body>");
        mailContent
                .append("<p style=\"font-family:trebuchet ms,helvetica,sans-serif;font-size:14\">Hi All, <br/><br/>Please find below results for user used by automation. </p>"
                        + getColorIndications());
        mailContent
                .append("<table border=\"1\" width=\"100%\" style=\"font-family:trebuchet ms,helvetica,sans-serif;font-size:12;border-collapse:collapse\">");
        mailContent
                .append("<tr style=\"font-weight:bold;text-align:center;background-color:#E8E3E1;height:30px\">");
        mailContent
                .append("<td width=\"3%\">S.No</td><td width=\"20%\">User Name</td><td width=\"7%\">Login</td><td width=\"10%\">No. Of Loc</td><td width=\"10%\">No. Of Tstat</td><td width=\"7%\">Thermostat</td><td width=\"10%\">Thermostat Name</td><td width=\"8%\" colspan=\"2\">Mode</td><td width=\"5%\">Data Collection</td><td width=\"5%\">Savings</td><td width=\"10%\">Algorithm</td><td width=\"10%\">Location</td>");
        mailContent.append("    </tr>   ");
        return mailContent.toString();
    }

    /**
     * Gets the s no.
     * @param userData the user data
     * @param index the index
     * @return the sno
     */
    private String getSNo(final UserData userData, final int index) {

        LogUtil.setLogString("Get Index for Mail content", true);
        final StringBuilder logContent = new StringBuilder();
        final List<ThermostatData> thDetails = userData.getThermostatDatas();
        if (thDetails != null && thDetails.size() > 1) {
            logContent.append("<tr><td rowspan=\"" + thDetails.size() + "\">" + "&nbsp;" + index
                    + "</td>");
        } else {
            logContent.append("<tr><td> " + "&nbsp;" + index + " </td>");
        }
        return logContent.toString();
    }

    /**
     * Gets the user.
     * @param userData the user data
     * @return the user
     */
    private String getUser(final UserData userData) {

        LogUtil.setLogString("Get user id for Mail content", true);
        final StringBuilder logContent = new StringBuilder();
        final List<ThermostatData> thDetails = userData.getThermostatDatas();
        if (thDetails != null && thDetails.size() > 1) {
            logContent.append("<td rowspan=\"" + thDetails.size() + "\">" + "&nbsp;"
                    + userData.getUserName() + "</td>");
        } else {
            logContent.append("<td> " + "&nbsp;" + userData.getUserName() + " </td>");
        }
        return logContent.toString();
    }

    /**
     * Gets the login content.
     * @param userData the user data
     * @return the login content
     */
    private String getLoginContent(final UserData userData) {

        LogUtil.setLogString("Login Mail content", true);
        final StringBuilder logContent = new StringBuilder();
        final List<ThermostatData> thDetails = userData.getThermostatDatas();
        String rowspan = "";
        if (thDetails != null && thDetails.size() > 1) {
            rowspan = " rowspan=\"" + thDetails.size() + "\"";
        }
        if (userData.isLogin()) {
            logContent.append("<td " + rowspan
                    + " style=\"background-color:green\">&nbsp;Success</td>");
        } else {
            logContent.append("<td " + rowspan
                    + " style=\"background-color:red;\">&nbsp;Failure </td>");
        }
        return logContent.toString();
    }

    /**
     * Gets the location no.
     * @param userData the user data
     * @return the location no
     */
    private String getLocationNo(final UserData userData) {

        LogUtil.setLogString("Location content", true);
        final StringBuilder logContent = returnDataValue(userData,
                String.valueOf(userData.getNoOfLocation()));

        return logContent.toString();
    }

    /**
     * Gets the no of thermostats.
     * @param userData the user data
     * @return the no of thermostats
     */
    private String getNoOfThermostats(final UserData userData) {

        LogUtil.setLogString("No. of Thermostat content", true);
        final StringBuilder logContent = returnDataValue(userData,
                String.valueOf(userData.getNoOfThermostats()));

        return logContent.toString();
    }

    /**
     * Return data value.
     * @param userData the user data
     * @param actualvalue the actualvalue
     * @return the string builder
     */
    private StringBuilder returnDataValue(final UserData userData, String actualvalue) {

        final StringBuilder logContent = new StringBuilder();
        final List<ThermostatData> thDetails = userData.getThermostatDatas();
        String rowspan = "";
        if (thDetails != null && thDetails.size() > 1) {
            rowspan = " rowspan=\"" + thDetails.size() + "\"";
        }

        if (thDetails == null || thDetails.size() == 0) {
            logContent.append("<td " + rowspan + " style=\"background-color:red;\">&nbsp;-</td>");
        } else {

            logContent.append("<td " + rowspan + " style=\"background-color:green\">&nbsp;"
                    + actualvalue + "</td>");
        }
        return logContent;
    }

    /**
     * Gets the thermostat.
     * @param userData the user data
     * @return the thermostat
     */
    private String getThermostat(final UserData userData) {

        LogUtil.setLogString("Thermostat Mail content", true);
        final StringBuilder logContent = new StringBuilder();

        final List<ThermostatData> thDetails = userData.getThermostatDatas();
        ThermostatData thermostatData = null;

        if (thDetails != null && !thDetails.isEmpty()) {
            int noOfThermostats = 0;
            while (noOfThermostats < thDetails.size()) {
                thermostatData = thDetails.get(noOfThermostats);
                noOfThermostats++;

                if (thermostatData.isConnected()) {
                    logContent
                            .append("<td style=\"background-color:green;text-indent:10px;font-weight:bold;\">"
                                    + thermostatData.getThermostatId() + "</td>");
                    logContent
                            .append("<td style=\"background-color:green;text-indent:10px;font-weight:bold;\">"
                                    + thermostatData.getThermostatName() + "</td>");
                } else {
                    logContent
                            .append("<td style=\"background-color:red;text-indent:10px;font-weight:bold;\">"
                                    + thermostatData.getThermostatId() + "</td>");
                    logContent
                            .append("<td style=\"background-color:red;text-indent:10px;font-weight:bold;\">"
                                    + thermostatData.getThermostatName() + "</td>");
                }

                if (thermostatData.getModes()[0] == "Heat" && thermostatData.getThermostatId() != 0) {
                    logContent
                            .append("<td style=\"background-color:green;text-indent:10px;font-weight:bold;\"> Heat </td>");
                } else {
                    logContent
                            .append("<td style=\"background-color:red;text-indent:10px;font-weight:bold;\"> Heat </td>");
                }

                if (thermostatData.getModes()[1] == "Cool" && thermostatData.getThermostatId() != 0) {
                    logContent
                            .append("<td style=\"background-color:green;text-indent:10px;font-weight:bold;\"> Cool </td>");
                } else {
                    logContent
                            .append("<td style=\"background-color:red;text-indent:10px;font-weight:bold;\"> Cool </td>");
                }

                if (thermostatData.isDataCollection()) {
                    logContent
                            .append("<td style=\"background-color:green;text-indent:10px;font-weight:bold;\"> Yes </td>");
                } else {
                    logContent
                            .append("<td style=\"background-color:red;text-indent:10px;font-weight:bold;\"> No </td>");
                }

                if (thermostatData.getSavingsValue() != null
                        && !thermostatData.getSavingsValue().isEmpty()) {
                    logContent
                            .append("<td style=\"background-color:green;text-indent:10px;font-weight:bold;\"> "
                                    + thermostatData.getSavingsValue() + " </td>");
                } else {
                    logContent
                            .append("<td style=\"background-color:red;text-indent:10px;font-weight:bold;\"> - </td>");
                }

                String algoSub = "";
                if (thermostatData.getSubsribedAlgorithms() != null) {
                    for (String algoname : thermostatData.getSubsribedAlgorithms()) {
                        algoSub += algoname + "</br>";
                    }
                }

                logContent
                        .append("<td style=\"background-color:green;text-indent:10px;font-weight:bold;text-align:center\"> "
                                + algoSub.trim() + " </td>");

                logContent
                        .append("<td style=\"background-color:green;text-indent:10px;font-weight:bold;text-align:center\"> "
                                + thermostatData.getLocationName() + " </td>");

                if (thDetails.size() >= 1) {
                    if (noOfThermostats == thDetails.size()) {
                        logContent.append("</tr>");
                    } else {
                        logContent.append("</tr><tr>");
                    }
                }
            }
        } else {
            logContent.append("<td style=\"background-color:red\">&nbsp;No Thermostat</td>");
            logContent.append("<td style=\"background-color:red\">-</td>");
            logContent.append("<td style=\"background-color:red\">-</td>");
            logContent.append("<td style=\"background-color:red\">-</td>");
            logContent.append("<td style=\"background-color:red\">-</td>");
            logContent.append("<td style=\"background-color:red\">-</td>");
            logContent.append("<td style=\"background-color:red\">-</td>");
            logContent.append("<td style=\"background-color:red\">-</td>");

        }
        return logContent.toString();
    }

    /**
     * Gets the mail footer.
     * @return the mail footer
     */
    private String getMailFooter() {

        LogUtil.setLogString("Add mail footer details", true);
        final StringBuilder logContent = new StringBuilder();
        logContent
                .append("</table><br/><p><span style=\"color:#696969;\"><span style=\"font-family: trebuchet ms,helvetica,sans-serif;font-size:12px\">Thanks &amp; Regards<br />QA Automation Team</span></span></p></body></html>");
        return logContent.toString();
    }

    /**
     * Gets the color indications.
     * @return the color indications
     */
    private String getColorIndications() {

        LogUtil.setLogString("Get color identifications for the mail", true);
        final StringBuilder logContent = new StringBuilder();
        logContent
                .append("<table border=\"0\" width=\25%\" style=\"font-family:trebuchet ms,helvetica,sans-serif;font-size:14\">");
        logContent.append("     <tr>");
        logContent.append("         <td width=\"10%\" style=\"background-color:green\"></td>");
        logContent.append("         <td>" + successCount + " Users Success/ Available</td>");
        logContent.append("     </tr>");
        logContent.append("     <tr>");
        logContent.append("         <td style=\"background-color:red\"></td>");
        logContent.append("         <td>" + failureCount + " Failure/ Not Available</td>");
        logContent.append("     </tr>");
        logContent.append("</table>");
        logContent.append("<br/>");
        return logContent.toString();
    }

    /**
     * Append row.
     * @param userData the user data
     * @return the string
     */
    private String appendRow(final UserData userData) {

        final StringBuilder logContent = new StringBuilder();
        final List<ThermostatData> thDetails = userData.getThermostatDatas();
        if (thDetails == null || thDetails.size() == 0) {
            logContent.append("</tr>");
        }
        return logContent.toString();

    }

    /**
     * Gets the user count.
     * @param value the value
     * @return the user count
     */
    private String getUserCount(final List<String> value) {

        int dummy = 0;
        int valid = 0;
        LogUtil.setLogString("User count", true);
        final StringBuilder logContent = new StringBuilder();
        if (value != null && !value.isEmpty()) {
            for (final String users : value) {
                final int count = users.contains("dummy") ? dummy++ : valid++;
                LogUtil.setLogString("Count :" + count, true);
            }
        }
        logContent
                .append("<td style=\"font-family:trebuchet ms,helvetica,sans-serif;font-size:14;\"> "
                        + "&nbsp;"
                        + " Dummy Users : "
                        + dummy
                        + " <br/>"
                        + "&nbsp;&nbsp;"
                        + "Valid Users : " + valid + " </td>");
        return logContent.toString();
    }

    /**
     * Gets the slave.
     * @param key the key
     * @return the slave
     */
    private String getSlave(final String key) {

        LogUtil.setLogString("Get Slaves ", true);
        final StringBuilder logContent = new StringBuilder();
        if (key != null && !key.isEmpty()) {
            logContent
                    .append("<tr><td style=\"font-family:trebuchet ms,helvetica,sans-serif;font-size:14;\"> "
                            + "&nbsp;" + key + " </td>");
        }
        return logContent.toString();
    }

    /**
     * Gets the users.
     * @param value the value
     * @return the users
     */
    private String getUsers(final List<String> value) {

        LogUtil.setLogString("Slave Users", true);
        final StringBuilder logContent = new StringBuilder();
        int index = 1;

        if (value != null && !value.isEmpty()) {
            logContent
                    .append("<td style=\"font-family:trebuchet ms,helvetica,sans-serif;font-size:14;\">");
            for (String users : value) {

                String keyProp = users;
                final int lastIndexOfSlash = keyProp.lastIndexOf("/");
                keyProp = users.substring(lastIndexOfSlash + 1, keyProp.length() - 1);
                users = users.substring(0, lastIndexOfSlash);

                logContent.append("<table width=\"100%\">");
                logContent.append("<tr>");

                if (index == value.size()) {
                    logContent
                            .append("<td style=\"border-right-style:solid;border-right-width:1px;\" width=\"50%\">"
                                    + index + ". " + "&nbsp;" + users + "</td>");
                    logContent.append("<td width=\"50%\">" + keyProp + "</td>");
                } else {
                    logContent
                            .append("<td style=\"border-right-style:solid;border-right-width:1px;border-bottom-style:solid;border-bottom-width:1px\" width=\"50%\">"
                                    + index + ". " + "&nbsp;" + users + "</td>");
                    logContent
                            .append("<td style=\"border-bottom-style:solid;border-bottom-width:1px\" width=\"50%\">"
                                    + keyProp + "</td>");
                }
                logContent.append("</tr>");
                logContent.append("</table>");
                index++;
            }
            logContent.append("</td>");
            logContent.append("</tr>");
        }
        return logContent.toString();
    }

    /**
     * Slave user mail header.
     * @return the string
     */
    private String slaveUserMailHeader() {

        final StringBuilder mailContent = new StringBuilder();
        mailContent.append("<html>");
        mailContent.append("<head></head>");
        mailContent.append("<body>");
        mailContent
                .append("<p style=\"font-family:trebuchet ms,helvetica,sans-serif;font-size:14\">Hi All, <br/><br/>Please find the users allocated to Slaves. </p>");
        mailContent
                .append("<table border=\"1\" width=\"100%\" style=\"font-family:trebuchet ms,helvetica,sans-serif;font-size:14;border-collapse:collapse\">");
        mailContent
                .append("<tr style=\"font-weight:bold;text-align:center;background-color:#E8E3E1;height:30px\">");
        mailContent
                .append("<td width=\"10%\">Slaves</td><td width=\"15%\">User Count</td><td width=\"70%\">Users</td>");
        mailContent.append("    </tr>   ");
        return mailContent.toString();
    }
}
