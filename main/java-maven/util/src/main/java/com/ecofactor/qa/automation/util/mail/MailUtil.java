/*
 * MailUtil.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.util.mail;

import static com.ecofactor.qa.automation.util.mail.MailConfig.*;

import java.util.List;

import com.google.inject.Inject;

/**
 * The Class MailUtil.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class MailUtil {

    @Inject
    ContentBuilder contentBuilder;
    @Inject
    AlgoContentBuilder algocontentBuilder;
    @Inject
    DeleteUserContentBuilder deleteUserContentBuilder;
    @Inject
    TestLinkContentBuilder testLinkContentBuilder;
    @Inject
    MailConfig mailConfig;
    @Inject
    MailSender mailSender;

    /**
     * Send mail.
     * @param env the env
     * @param userDatas the user datas
     */
    public void sendMail(TestType type, String env, List<UserData> userDatas) {

		String subject = null;
		String content = contentBuilder.getContent(type, userDatas, env);
		if (type == TestType.CONSUMER || type == TestType.EXCEL || type == TestType.GRIDWITHEXCEL) {
			subject = mailConfig.get(USER_STATUS);
		} else if (type == TestType.ALGO) {
			subject = mailConfig.get(ALGO_USERS_USED);
		} else if (type == TestType.CONSUMERREGRESSION) {
			subject = mailConfig.get(USERS_USED);
		}
		String recepients = mailConfig.get(RECEPIENTS);
		boolean attachExcel = type == TestType.CONSUMER ? false : true;
		mailSender.sendUserStatusMail(content, env, subject, recepients, attachExcel);
	}

    /**
     * Algo send mail.
     * @param type the type
     * @param env the env
     * @param userDatas the user datas
     */
    public void algoSendMail(TestType type, String env, List<AlgoUserData> userDatas) {

		String content = algocontentBuilder.algoGetContent(type, userDatas, env);
		String subject = mailConfig.get(ALGO_AVAILABLE_USERS);
		String recepients = mailConfig.get(RECEPIENTS);
		mailSender.sendUserStatusMail(content, env, subject, recepients, false);
	}

    /**
     * Un used user send mail.
     * @param type the type
     * @param env the env
     * @param userDatas the user datas
     */
    public void unUsedUserSendMail(TestType type, String env, List<UnusedUserData> userDatas) {

		String content = deleteUserContentBuilder.deleteUserContent(type, userDatas, env);
		String subject = mailConfig.get(DELETED_USERS);
		String recepients = mailConfig.get(RECEPIENTS);
		mailSender.sendUserStatusMail(content, env, subject, recepients, false);
	}

	/**
	 * Test linksend mail.
	 * @param type the type
	 * @param env the env
	 * @param testLinkDatas the test link datas
	 */
	public void testLinksendMail(String module, String env, List<TestLinkData> testLinkDatas) {
		String content = testLinkContentBuilder.testLinkContent(module, testLinkDatas);
		String testLink = mailConfig.get(TEST_LINK);
		String subject = testLink + " " + module.toUpperCase() + " ";
		String recepients = mailConfig.get(TEST_LINK_RECEPIENTS);
		mailSender.sendUserStatusMail(content, env, subject, recepients, false);
	}
}
