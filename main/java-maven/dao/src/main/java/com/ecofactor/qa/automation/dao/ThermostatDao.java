package com.ecofactor.qa.automation.dao;

import java.util.List;

import com.ecofactor.common.pojo.Thermostat;

public interface ThermostatDao extends BaseDao<Thermostat> {

    Thermostat findByThermostatId(String thermostatId);

    List<Thermostat> findThermostatsByEcpCoreAndRuleString(Integer ecpCoreId, String ruleString);

    List<Integer> getThermostatIdForUser(String userName);

    List<Thermostat> listThermostatsByLocationId(Integer locationId, Integer thermostatId);

    List<Thermostat> listThermostatsByLocation(Integer locationId);
}
