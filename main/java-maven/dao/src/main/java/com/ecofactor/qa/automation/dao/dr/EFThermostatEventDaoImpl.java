package com.ecofactor.qa.automation.dao.dr;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.ecofactor.qa.automation.dao.BaseDaoImpl;
import com.ecofactor.qa.automation.pojo.ThermostatEvent;

/**
 * The Class EFThermostatEventDaoImpl.
 */
public class EFThermostatEventDaoImpl extends BaseDaoImpl<ThermostatEvent> implements
        EFThermostatEventDao {

    /**
     * @param thermostatId
     * @return
     * @see com.ecofactor.qa.automation.dao.dr.EFThermostatEventDao#fetchDetailsByThermostatId(java.lang.Integer)
     */
    @Override
    public List<ThermostatEvent> fetchDetailsByThermostatId(Integer thermostatId,
            Double groupEventId) {

        final Double grpEventIdCompleted = groupEventId + 0.2;
        String sql = "select t from ThermostatEvent t where t.thermostatId = :thermostatId and t.groupEventId in (:groupEventId, :grpEventIdCompleted)";
        Map<String, Object> paramVals = new HashMap<String, Object>();
        paramVals.put("thermostatId", thermostatId);
        paramVals.put("groupEventId", groupEventId);
        paramVals.put("grpEventIdCompleted", grpEventIdCompleted);
        List<ThermostatEvent> attributes = listByQuery(sql, paramVals);
        return attributes;
    }

    /**
     * @return
     * @see com.ecofactor.qa.automation.dao.BaseDaoImpl#getEntityManager()
     */
    public EntityManager getEntityManager() {

        return getRangeDataEntityManager();
    }

}
