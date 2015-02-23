/*
 * DeviceInfoHtmlContentBuilder.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.newapp.util.mail;

import java.util.List;
import com.ecofactor.qa.automation.newapp.model.DeviceData;
import com.ecofactor.qa.automation.platform.util.LogUtil;

/**
 * The Class DeviceInfoHtmlContentBuilder - Implementation for HTML Content
 */
public class DeviceInfoHtmlContentBuilder implements DeviceInfoContentBuilder {

	/**
	 * Device info.
	 * @param deviceData the device data
	 * @return the string
	 * @see com.ecofactor.qa.automation.newapp.util.mail.DeviceInfoContentBuilder#deviceInfo(java.util.List)
	 */
	@Override
	public String deviceInfo(List<DeviceData> deviceDatas) {
		final StringBuilder content = new StringBuilder();
		if (deviceDatas != null && deviceDatas.size() > 0) {
			content.append(mailHeader());
			for (final DeviceData deviceData : deviceDatas) {
				content.append(getSlaveName(deviceData));
				content.append(getDeviceName(deviceData));
				content.append(getVersion(deviceData));
				content.append(getDeviceId(deviceData));
				content.append(getDeviceState(deviceData));
			}
		}
		content.append(getMailFooter());
		return content.toString();
	}

	/**
	 * Gets the slave name with html markup.
	 * @param deviceData the device data
	 * @return the slave name
	 */
	private Object getSlaveName(final DeviceData deviceData) {

		LogUtil.setLogString("Get slave name for Mail content", true);
		final StringBuilder logContent = new StringBuilder();
		logContent.append(new StringBuilder("<tr><td> ").append("&nbsp;").append(deviceData.getSlave()).append( " </td>").toString());
		return logContent.toString();
	}

	/**
	 * Gets the device name.
	 * @param deviceData the device data
	 * @return the device name
	 */
	private Object getDeviceName(final DeviceData deviceData) {
		LogUtil.setLogString("Get device name for Mail content", true);
		final StringBuilder logContent = new StringBuilder();
		logContent.append(new StringBuilder("<td> ").append("&nbsp;").append(deviceData.getName()).append( " </td>").toString());
		return logContent.toString();
	}

	/**
	 * Gets the version.
	 * @param deviceData the device data
	 * @return the version
	 */
	private Object getVersion(final DeviceData deviceData) {

		LogUtil.setLogString("Get version for Mail content", true);
		final StringBuilder logContent = new StringBuilder();
		logContent.append(new StringBuilder("<td> ").append("&nbsp;").append(deviceData.getVersion()).append( " </td>").toString());
		return logContent.toString();
	}

	/**
	 * Gets the device id.
	 * @param deviceData the device data
	 * @return the device id
	 */
	private Object getDeviceId(final DeviceData deviceData) {

		LogUtil.setLogString("Get device Id for Mail content", true);
		final StringBuilder logContent = new StringBuilder();
		logContent.append(new StringBuilder("<td> ").append("&nbsp;").append(deviceData.getDeviceId()).append( " </td>").toString());
		return logContent.toString();
	}

	/**
	 * Gets the device state.
	 * @param deviceData the device data
	 * @return the device state
	 */
	private Object getDeviceState(final DeviceData deviceData) {

		LogUtil.setLogString("Get device state for Mail content", true);
		final StringBuilder logContent = new StringBuilder();
		logContent.append(new StringBuilder("<td> ").append("&nbsp;").append(deviceData.getState()).append( " </td></tr>").toString());
		return logContent.toString();
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
		mailContent.append("<p style=\"font-family:trebuchet ms,helvetica,sans-serif;font-size:14\">Hi All, <br/><br/>Please find the connected devices. </p>");
		mailContent.append("<table border=\"1\" width=\"60%\" style=\"font-family:trebuchet ms,helvetica,sans-serif;font-size:14;border-collapse:collapse\">");
		mailContent.append("<tr style=\"font-weight:bold;text-align:center;background-color:#E8E3E1;height:30px\">");
		mailContent.append("<td width=\"10%\">Slave</td><td width=\"15%\">Device Name</td><td width=\"10%\">Version</td><td width=\"15%\">UDID</td><td width=\"10%\">State</td>");
		mailContent.append("    </tr>   ");
		return mailContent.toString();
	}

	/**
	 * Gets the mail footer.
	 * @return the mail footer
	 */
	private String getMailFooter() {

		LogUtil.setLogString("Add mail footer details", true);
		final StringBuilder logContent = new StringBuilder();
		logContent.append("</table><br/><p><span style=\"color:#696969;\"><span style=\"font-family: trebuchet ms,helvetica,sans-serif;font-size:12px\">Thanks &amp; Regards<br />QA Automation Team</span></span></p></body></html>");
		return logContent.toString();
	}
}
