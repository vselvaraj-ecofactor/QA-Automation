package com.ecofactor.qa.automation.dao;

import static org.testng.Assert.*;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.ecofactor.common.pojo.timeseries.PartitionedThermostatEvent;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class MulipleDBPersistenceTest {

    private ThermostatEventDao thermostatEventDao;

    @BeforeClass
    public void setup() {

        Injector injector = Guice.createInjector(new DaoModule());
        thermostatEventDao = injector.getInstance(ThermostatEventDao.class);
    }

    @Test
    public void findLatestThermostatEvent() {

        PartitionedThermostatEvent thermostatEvent = thermostatEventDao.findLatestByThermostat(2713);
        assertNotNull(thermostatEvent);
    }
}
