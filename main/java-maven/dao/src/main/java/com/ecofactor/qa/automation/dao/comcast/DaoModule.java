package com.ecofactor.qa.automation.dao.comcast;


import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public class DaoModule extends AbstractModule  {


	@Override
	protected void configure() {

		bind(AggregateHourlyReportDao.class).to(AggregateHourlyReportDaoImpl.class).in(Singleton.class);

	}
}

