package com.ecofactor.qa.automation.dao;

import com.ecofactor.common.pojo.ThermostatJob;

public interface ThermostatJobDao   extends BaseDao<ThermostatJob> 
{
    
    public ThermostatJob findADayBfrJobData(Integer thermostatId);
    
    public ThermostatJob findLatestJobData(Integer thermostatId);

}
