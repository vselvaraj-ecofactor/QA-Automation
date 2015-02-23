package com.ecofactor.qa.automation.dao.dr;

import java.util.List;

import com.ecofactor.qa.automation.dao.BaseDao;
import com.ecofactor.qa.automation.pojo.ThermostatEvent;

/**
 * The Interface EFThermostatEventDao.
 */
public interface EFThermostatEventDao extends BaseDao<ThermostatEvent> {

    /**
     * Fetch details by thermostat id.
     *
     * @param thermostatId the thermostat id
     * @param groupEventId the group event id
     * @return the thermostat event
     */
    public List<ThermostatEvent> fetchDetailsByThermostatId(final Integer thermostatId,
            final Double groupEventId);

}
