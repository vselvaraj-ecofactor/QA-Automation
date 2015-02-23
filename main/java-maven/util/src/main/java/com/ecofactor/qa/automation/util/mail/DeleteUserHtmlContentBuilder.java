/*
 * DeleteUserHtmlContentBuilder.java
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
 * The Class DeleteUserHtmlContentBuilder.
 */
public class DeleteUserHtmlContentBuilder implements DeleteUserContentBuilder {

    private String env;
    /**
     * Gets the delete user content.
     * @param type the type
     * @param userDataList the user data list
     * @param env the env
     * @return the content
     * @see com.ecofactor.qa.automation.util.mail.ContentBuilder#getContent(java.util.List)
     */
    @Override
    public String deleteUserContent(TestType type, List<UnusedUserData> userData, String env) {

        DriverConfig.setLogString("user " + userData.size(), true);
        this.env=env;
        String content = "";
        int count = 1;
        if (userData != null && userData.size() > 0) {
            content += mailHeader();
            for (UnusedUserData unUsedUser : userData) {
                content += getCount(count) + getUser(unUsedUser);
                count++;
            }
        }
        content += appendRow();
        content += getMailFooter();
        return content;
    }

    /**
     * Gets the user.
     * @param unUsedUser the un used user
     * @return the user
     */
    private String getUser(UnusedUserData unUsedUser) {

        DriverConfig.setLogString("Get user for Mail content", true);
        StringBuilder logContent = new StringBuilder();
        if (unUsedUser.getUser() != null && !unUsedUser.getUser().isEmpty()) {
            logContent.append("<td><b style=\"text-decoration:none;\">" + "&nbsp;" + unUsedUser.getUser() + "</b></td><tr>");
        }
        return logContent.toString();
    }

    private String getCount(int count) {

        DriverConfig.setLogString("Get Serial No", true);
        StringBuilder logContent = new StringBuilder();

        logContent.append("<td> " + count + ".</td>");

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
        mailContent.append("<p style=\"font-family:trebuchet ms,helvetica,sans-serif;font-size:14\">Hi All, <br/><br/>Please find below list of users to be deleted in "+ this.env+" environment.</p>");
        mailContent.append("<table border=\"1\" width=\"25%\" style=\"font-family:trebuchet ms,helvetica,sans-serif;font-size:12;border-collapse:collapse\">");
        mailContent.append("<tr style=\"font-weight:bold;text-align:center;background-color:#E8E3E1;height:30px\">");
        mailContent.append("<td width=\5%\">S No.</td><td width=\15%\">Users to be deleted</td>");
        mailContent.append("    </tr>   ");
        return mailContent.toString();
    }

    /**
     * Gets the mail footer.
     * @return the mail footer
     */
    private String getMailFooter() {

        DriverConfig.setLogString("Add mail footer details", true);
        StringBuilder logContent = new StringBuilder();
        logContent
                .append("</table><br/><p><span style=\"color:#696969;\"><span style=\"font-family: trebuchet ms,helvetica,sans-serif;font-size:12px\">Thanks &amp; Regards<br />QA Automation Team</span></span></p></body></html>");

        return logContent.toString();
    }

    /**
     * Append row.
     * @param algoUserData the algo user data
     * @return the string
     */
    private String appendRow() {

        StringBuilder logContent = new StringBuilder();
        logContent.append("</tr>");
        return logContent.toString();
    }

}
