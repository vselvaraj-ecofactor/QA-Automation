/*
 * MobileMailUtil.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.newapp.util.mail;

import static com.ecofactor.qa.automation.util.mail.MailConfig.*;

import java.util.List;
import java.util.Map;

import com.ecofactor.qa.automation.newapp.model.UserData;
import com.ecofactor.qa.automation.newapp.model.DeviceData;
import com.ecofactor.qa.automation.util.mail.MailConfig;
import com.ecofactor.qa.automation.util.mail.MailSender;
import com.ecofactor.qa.automation.util.mail.TestType;
import com.google.inject.Inject;

public class MobileMailUtil {

	@Inject
	private MobileTestDataContentBuilder mobileContent;
	@Inject
	private DeviceInfoContentBuilder deviceInfoContent;
	@Inject
	private MailConfig mailConfig;
	@Inject
	private MailSender mailSender;

	/**
	 * Mobile user send mail.
	 * @param type the type
	 * @param env the env
	 * @param userDatas the user datas
	 */
	public void mobileUserSendMail(final TestType type, final String env, final List<UserData> userDatas) {

		final String content = mobileContent.mobileUserContent(type, userDatas, env);
		final String subject = mailConfig.get(MOBILE);
		final String recepients = mailConfig.get(EF_RECEPIENTS);
		mailSender.sendUserStatusMail(content, env, subject, recepients, false);
	}

	/**
	 * Slave users.
	 * @param type the type
	 * @param env the env
	 * @param map the map
	 */
	public void slaveUsers(final TestType type, final String env, final Map<String, List<String>> map) {

		final String content = mobileContent.slaveUsers(type, map, env);
		final String subject = mailConfig.get(SLAVE);
		final String recepients = mailConfig.get(EF_RECEPIENTS);
		mailSender.sendUserStatusMail(content, env, subject, recepients, false);
	}

	/**
	 * Device details.
	 * @param deviceDatas the device datas
	 * @param environment the environment
	 */
	public void deviceDetails(List<DeviceData> deviceDatas, String environment) {

		final String content = deviceInfoContent.deviceInfo(deviceDatas);
		final String subject = mailConfig.get(DEVICE);
		final String recepients = mailConfig.get(EF_RECEPIENTS);
		mailSender.sendUserStatusMail(content, environment, subject, recepients, false);
	}
}
