/*
 * PartnerAccountDaoTest.java
 * Copyright (c) 2013, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.dao;

import java.util.List;

import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.ecofactor.common.pojo.*;
import com.ecofactor.common.pojo.PartnerType.PartnerTypeName;
import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * The Class PartnerAccountDaoTest.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class PartnerAccountDaoTest {

	private static Logger LOGGER = LoggerFactory.getLogger(PartnerAccountDaoTest.class);
	private PartnerAccountUserDao partnerAccountUserDao;
	
	private PartnerAccountDao partnerAccountDao;
	private PartnerAccountTypeDao partnerAccountTypeDao;
	private PartnerTypeDao partnerTypeDao;

	/**
	 * Setup.
	 */
	@BeforeClass
	public void setup() {

		Injector injector = Guice.createInjector(new DaoModule());
		partnerAccountUserDao = injector.getInstance(PartnerAccountUserDao.class);
		partnerAccountDao = injector.getInstance(PartnerAccountDao.class);
		partnerAccountTypeDao = injector.getInstance(PartnerAccountTypeDao.class);
		partnerTypeDao = injector.getInstance(PartnerTypeDao.class);
	}

	/**
	 * Test access login.
	 */
	@Test
	public void testAccessLogin() {

		LOGGER.info("Start testAccessLogin");
		int partnerAccountUser = partnerAccountUserDao.getPartnerAccountByAccessLogin("gabtest");
		LOGGER.info("partner id " + partnerAccountUser);
	}

	/**
	 * Find by id.
	 */
	@Test
	public void findById() {

		PartnerAccountUser partnerAccountID = partnerAccountUserDao.findById(100);
		Assert.assertNotNull(partnerAccountID);
	}

	/**
	 * Gets the partner account user by name.
	 * @return the partner account user by name
	 */
	@Test
	public void getPartnerAccountUserByName() {

		PartnerAccountUser user = partnerAccountUserDao.getPartnerAccountUserByName("TestUser TestUser");
	    PartnerAccount partnerAccount = partnerAccountDao.getPartnerAccountByName("Sears Holding Corp");
	    PartnerAccountType partnerAccountType = partnerAccountTypeDao.getPartnerAccountTypeByPartnerAcc(partnerAccount);
	    PartnerType partnerType = partnerTypeDao.getPartnerTypeByName(PartnerTypeName.INSTALLER);
	    partnerAccountTypeDao.updatePartnerType(partnerAccount, partnerType);
	    Assert.assertNotNull(user);
	}

}
