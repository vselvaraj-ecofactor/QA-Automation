package com.ecofactor.qa.automation.dao;

import static org.testng.Assert.*;

import java.text.ParseException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.ecofactor.common.pojo.ThermostatAlgorithm;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class ThermostatAlgorithmDaoTest {

	private ThermostatAlgorithmDao thermostatAlgoDao;
	private final static Logger LOGGER = LoggerFactory.getLogger(ThermostatAlgorithmDaoTest.class);

	@BeforeClass
	public void setup() {

		Injector injector = Guice.createInjector(new DaoModule());
		thermostatAlgoDao = injector.getInstance(ThermostatAlgorithmDao.class);
	}

	/**
	 * List by string.
	 * @throws ParseException the parse exception
	 */
	@Test
	public void listByString() throws ParseException
	{
		List<String> thermostatEvents = thermostatAlgoDao.listBySubscribedAlgo(2735);
		assertNotNull(thermostatEvents);

	}

	/**
	 * Select active algorithm.
	 * @throws ParseException the parse exception
	 */
	@Test
	public void selectActiveAlgorithm() throws ParseException
	{
		List<ThermostatAlgorithm> thAlgoList = thermostatAlgoDao.findByThermostatId(1052);
		assertNotNull(thAlgoList);
		assertNotNull(thAlgoList.size() > 0);
	}

}
