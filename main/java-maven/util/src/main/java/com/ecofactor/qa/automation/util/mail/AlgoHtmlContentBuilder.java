/*
 * AlgoHtmlContentBuilder.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.util.mail;

import java.util.List;

import com.ecofactor.qa.automation.util.DriverConfig;

/**
 * The Class HtmlContentBuilder.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class AlgoHtmlContentBuilder implements AlgoContentBuilder {

    boolean algo = false;
    boolean consumerRegression = false;
    boolean isPageLoaded = true;

    /**
     * Gets the content.
     * @param type the type
     * @param userDataList the user data list
     * @param env the env
     * @return the content
     * @see com.ecofactor.qa.automation.util.mail.ContentBuilder#getContent(java.util.List)
     */
    public String algoGetContent(TestType type, List<AlgoUserData> userDataList, String env) {

        String content = "";

        int i = 1;
        if (userDataList != null && userDataList.size() > 0) {
            content += mailHeader();
            for (AlgoUserData algoUserData : userDataList) {
                content += getSNo(algoUserData, i);
                content += getUser(algoUserData);
                content += getThermostat(algoUserData);
                content += getTest(algoUserData);
                content += getEvent(algoUserData);
                content += getSysTime(algoUserData);
                content += appendRow(algoUserData);
                i++;
            }
        } else {
            content += "<br/> <b style=\"font-colot:red\" color=\"red\">Sorry, No thermostats are available active within a hour</b>";
        }
        content += getMailFooter();
        return content;
    }

    public String getSNo(AlgoUserData algoUserData, int i) {

        DriverConfig.setLogString("Saerial no", true);
        StringBuilder logContent = new StringBuilder();
        if (!algoUserData.getThermostat().isEmpty()) {
            if (algoUserData.getEvent() == -901 || algoUserData.getEvent() == -911) {
                logContent.append("<tr style=\"background-color:red\"><td  style=\"background-color:#FFFFFF\"><b>" + "&nbsp;" + i + "&nbsp;</b></td>");
            } else {
                logContent.append("<tr style=\"background-color:green\"><td style=\"background-color:#FFFFFF\"><b>" + "&nbsp;" + i + "&nbsp;</b></td>");
            }

        } else {
            logContent.append("<td></td>");
        }
        return logContent.toString();
    }

    /**
     * Mail header.
     * @return the string
     */
    private String mailHeader() {

        StringBuilder mailContent = new StringBuilder();
        mailContent.append("<html>");
        mailContent.append("<head></head>");
        mailContent.append("<body>");
        mailContent.append("<p style=\"font-family:trebuchet ms,helvetica,sans-serif;font-size:14\">Hi All, <br/><br/>Please find below list of active thermostats.</p>");
        mailContent.append("<table border=\"1\" width=\"55%\" style=\"font-family:trebuchet ms,helvetica,sans-serif;font-size:12;border-collapse:collapse\">");
        mailContent.append("<tr style=\"font-weight:bold;text-align:center;background-color:#E8E3E1;height:30px\">");
        mailContent
                .append("<td width=\"3%\">S.No.</td><td width=\"10%\">User</td><td width=\"15%\">Thermostat</td><td width=\"10%\">Test</td><td width=\"5%\">Event</td><td width=\"15%\">SystemTime UTC</td>");
        mailContent.append("    </tr>   ");
        return mailContent.toString();
    }

    /**
     * Gets the thermostat.
     * @param algoUserData the algo user data
     * @return the thermostat
     */
    private String getThermostat(AlgoUserData algoUserData) {

        DriverConfig.setLogString("Get thermostat for Mail content", true);
        StringBuilder logContent = new StringBuilder();
        if (algoUserData.getThermostat() != null && !algoUserData.getThermostat().isEmpty()) {
            logContent.append("<td><b style=\"color:#000000; text-decoration:none;\">" + "&nbsp;" + algoUserData.getThermostat() + "</b></td>");
        } else {
            logContent.append("<td></td>");
        }
        return logContent.toString();
    }

    /**
     * Gets the user.
     * @param algoUserData the algo user data
     * @return the user
     */
    private String getUser(AlgoUserData algoUserData) {

        DriverConfig.setLogString("Get thermostat user for Mail content", true);
        StringBuilder logContent = new StringBuilder();
        if (algoUserData.getUser() != null && !algoUserData.getUser().isEmpty()) {
            logContent.append("<td style=\"background-color:#FFFFFF\"><b style=\"text-decoration:none;\">" + "&nbsp;" + algoUserData.getUser() + "</b></td>");
        } else {
            logContent.append("<td></td>");
        }
        return logContent.toString();
    }

    /**
     * Gets the test.
     * @param algoUserData the algo user data
     * @return the test
     */
    private String getTest(AlgoUserData algoUserData) {

        DriverConfig.setLogString("Get thermostat test for Mail content", true);
        StringBuilder logContent = new StringBuilder();
        if (algoUserData.getTest() != null && !algoUserData.getTest().isEmpty()) {
            logContent.append("<td><b style=\"color:#000000; text-decoration:none;\">" + "&nbsp;" + algoUserData.getTest() + "</b></td>");
        } else {
            logContent.append("<td></td>");
        }
        return logContent.toString();
    }

    /**
     * Gets the event.
     * @param algoUserData the algo user data
     * @return the event
     */
    private String getEvent(AlgoUserData algoUserData) {

        DriverConfig.setLogString("Get thermostat event for Mail content", true);
        StringBuilder logContent = new StringBuilder();
        logContent.append("<td><b>" + "&nbsp;" + algoUserData.getEvent() + "</b></td>");
        return logContent.toString();
    }

    /**
     * Gets the sys time.
     * @param algoUserData the algo user data
     * @return the sys time
     */
    private String getSysTime(AlgoUserData algoUserData) {

        DriverConfig.setLogString("Get thermostat System Time for Mail content", true);
        StringBuilder logContent = new StringBuilder();
        logContent.append("<td><b>" + "&nbsp;" + algoUserData.getSystemTime() + "</b></td>");
        return logContent.toString();
    }

    /**
     * Gets the color indications.
     * @return the color indications
     */
    private String getColorIndications() {

        DriverConfig.setLogString("Get color identifications for the mail", true);
        StringBuilder logContent = new StringBuilder();
        logContent.append("<br/>");
        logContent.append("<table border=\"0\" width=\25%\" style=\"font-family:trebuchet ms,helvetica,sans-serif;font-size:14\">");
        logContent.append("     <tr>");
        logContent.append("         <td width=\"10%\" style=\"background-color:green\"></td>");
        logContent.append("         <td>Success/ Available</td>");
        logContent.append("     </tr>");
        logContent.append("     <tr>");
        logContent.append("         <td style=\"background-color:red\"></td>");
        logContent.append("         <td>Failure/ Not Available</td>");
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
        logContent
                .append("</table> "
                        + getColorIndications()
                        + "<br/><p><span style=\"color:#696969;\"><span style=\"font-family: trebuchet ms,helvetica,sans-serif;font-size:12px\">Thanks &amp; Regards<br />QA Automation Team</span></span></p></body></html>");

        return logContent.toString();
    }

    /**
     * Append row.
     * @param algoUserData the algo user data
     * @return the string
     */
    private String appendRow(AlgoUserData algoUserData) {

        StringBuilder logContent = new StringBuilder();
        logContent.append("</tr>");
        return logContent.toString();
    }

}
