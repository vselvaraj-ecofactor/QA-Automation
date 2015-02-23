package com.ecofactor.qa.automation.dao.dr;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.ecofactor.common.pojo.timeseries.PartitionedThermostatEvent;
import com.ecofactor.qa.automation.dao.BaseDaoImpl;

/**
 * ThermostatEventDaoImpl is the dao implementation for thermostat events.
 * @author $Author: $
 * @version $Rev: 23883 $ $Date: 2013-10-08 18:03:59 +0530 (Tue, 08 Oct 2013) $
 */
public class ThermostatEventDaoImpl extends BaseDaoImpl<PartitionedThermostatEvent> implements
        ThermostatEventDao {

    // Event Type
    // USER(0), ALGO(1), ADMIN(2), LS(3), Null(255);

    // Action Integer Values
    // heat_setting(0), cool_setting(1), program(2), startEvent(3), endEvent(4), off(5), Null(255);
    // ---------------------

    // Status Integer values
    // PENDING(0), PROCESSED(1), FAILED(2), ANALYZED(3), SKIPPED(4), DELAYED(5), SCHEDULED(6),
    // COMPLETED(7), NOT_ACK(8), Null(255);
    // ---------------------

    /**
     * List Thermostat event based on group event id.
     * @param groupEventId the groupEventId.
     * @return the list.
     * @see com.ecofactor.qa.automation.dao.dr.ThermostatEventDao#listThermostatEventByGroupEventId(java.lang.Double)
     */
    @Override
    public List<PartitionedThermostatEvent> listThermostatEventByGroupEventId(Double groupEventId) {

        String ql = "SELECT te FROM PartitionedThermostatEvent te WHERE te.group_event_id = :group_event_id";
        Map<String, Object> paramVals = new HashMap<String, Object>();
        paramVals.put("group_event_id", groupEventId);

        List<PartitionedThermostatEvent> thermostatEvents = listByQuery(ql, paramVals);
        return thermostatEvents;
    }

    /**
     * List Thermostat event based on group event id with start and end time.
     * @param groupEventId the groupEventId
     * @param startTime the start time
     * @param endTime the end time
     * @return the list.
     * @see com.ecofactor.qa.automation.dao.dr.ThermostatEventDao#listEventbyGroupIdwithStartEndTime(java.lang.Double,
     *      java.util.Calendar, java.util.Calendar)
     */
    @Override
    public List<PartitionedThermostatEvent> listEventbyGroupIdwithStartEndTime(Double groupEventId,
            Calendar startTime, Calendar endTime) {

        String ql = "SELECT te FROM PartitionedThermostatEvent te WHERE te.group_event_id = :group_event_id AND te.id.eventSysTime BETWEEN :startTime AND :endTime order by te.id.eventSysTime asc";
        Map<String, Object> paramVals = new HashMap<String, Object>();
        paramVals.put("group_event_id", groupEventId);
        paramVals.put("startTime", startTime);
        paramVals.put("endTime", endTime);

        List<PartitionedThermostatEvent> thermostatEvents = listByQuery(ql, paramVals);
        return thermostatEvents;
    }

    /**
     * List Thermostat event based on thermostat id with start and end time.
     * @param thermostatId the thermostatId
     * @param startTime the start time
     * @param endTime the end time
     * @return the list.
     * @see com.ecofactor.qa.automation.dao.dr.ThermostatEventDao#listByThermostatIdwithStartandEndTime(java.lang.Integer,
     *      java.util.Calendar, java.util.Calendar)
     */
    @Override
    public List<PartitionedThermostatEvent> listByThermostatIdwithStartandEndTime(
            Integer thermostatId, Calendar startTime, Calendar endTime) {

        String ql = "SELECT te FROM PartitionedThermostatEvent te WHERE te.thermostat_id = :thermostat_id AND te.id.eventSysTime BETWEEN :startTime AND :endTime order by te.id.eventSysTime asc";
        Map<String, Object> paramVals = new HashMap<String, Object>();
        paramVals.put("thermostat_id", thermostatId);
        paramVals.put("startTime", startTime);
        paramVals.put("endTime", endTime);

        List<PartitionedThermostatEvent> thermostatEvents = listByQuery(ql, paramVals);
        return thermostatEvents;
    }

    /**
     * List Thermostat event based on algorithm Id with start and end time.
     * @param algorithmId the algorithmId
     * @param startTime the start time
     * @param endTime the end time
     * @return the list.
     * @see com.ecofactor.qa.automation.dao.dr.ThermostatEventDao#listByAlgoIdwithStartandEndTime(java.lang.Integer,
     *      java.util.Calendar, java.util.Calendar)
     */
    @Override
    public List<PartitionedThermostatEvent> listByAlgoIdwithStartandEndTime(Integer algorithmId,
            Calendar startTime, Calendar endTime) {

        String ql = "SELECT te FROM PartitionedThermostatEvent te WHERE te.algorithm_id = :algorithm_id AND te.id.eventSysTime BETWEEN :startTime AND :endTime order by te.id.eventSysTime asc";
        Map<String, Object> paramVals = new HashMap<String, Object>();
        paramVals.put("algorithm_id", algorithmId);
        paramVals.put("startTime", startTime);
        paramVals.put("endTime", endTime);

        List<PartitionedThermostatEvent> thermostatEvents = listByQuery(ql, paramVals);
        return thermostatEvents;
    }

    /**
     * List Thermostat event based on algorithm id with group event id.
     * @param algorithmId the algorithmId
     * @param groupEventId the groupEventId
     * @return the list.
     * @see com.ecofactor.qa.automation.dao.dr.ThermostatEventDao#listByAlgoIdwithGroupEventId(java.lang.Integer,
     *      java.lang.Double)
     */
    @Override
    public List<PartitionedThermostatEvent> listByAlgoIdwithGroupEventId(Integer algorithmId,
            Double groupEventId) {

        String ql = "SELECT te FROM PartitionedThermostatEvent te WHERE te.algorithm_id = :algorithm_id AND te.group_event_id = :group_event_id";
        Map<String, Object> paramVals = new HashMap<String, Object>();
        paramVals.put("algorithm_id", algorithmId);
        paramVals.put("group_event_id", groupEventId);
        List<PartitionedThermostatEvent> thermostatEvents = listByQuery(ql, paramVals);
        return thermostatEvents;
    }

    /**
     * List Thermostat event based on algorithm id, group id with start and end time.
     * @param algorithmId the algorithmId
     * @param groupEventId the groupEventId
     * @param startTime the start time
     * @param endTime the end time
     * @return the list.
     * @see com.ecofactor.qa.automation.dao.dr.ThermostatEventDao#listByAlgoIdGroupIdwithStartandEndTime(java.lang.Integer,
     *      java.lang.Double, java.util.Calendar, java.util.Calendar)
     */
    @Override
    public List<PartitionedThermostatEvent> listByAlgoIdGroupIdwithStartandEndTime(
            Integer algorithmId, Double groupEventId, Calendar startTime, Calendar endTime) {

        String ql = "SELECT te FROM PartitionedThermostatEvent te WHERE te.algorithm_id = :algorithm_id AND te.group_event_id = :group_event_id te.id.eventSysTime BETWEEN :startTime AND :endTime order by te.id.eventSysTime asc";
        Map<String, Object> paramVals = new HashMap<String, Object>();
        paramVals.put("algorithm_id", algorithmId);
        paramVals.put("group_event_id", groupEventId);
        paramVals.put("startTime", startTime);
        paramVals.put("endTime", endTime);
        List<PartitionedThermostatEvent> thermostatEvents = listByQuery(ql, paramVals);
        return thermostatEvents;
    }

    /**
     * List all details based on thermostat id.
     * @param thermostatId the thermostat id.
     * @return list.
     * @see com.ecofactor.qa.automation.dao.dr.ThermostatEventDao#std210DR(java.lang.Integer)
     */
    @Override
    public List<PartitionedThermostatEvent> std210DR(int thermostatId, Double grpId) {

        String sql = "SELECT te FROM PartitionedThermostatEvent te WHERE te.thermostatId= :thermostatId AND te.groupEventId= :programEventId";
        Map<String, Object> paramVals = new HashMap<String, Object>();
        paramVals.put("thermostatId", thermostatId);
        paramVals.put("programEventId", grpId);
        List<PartitionedThermostatEvent> pTStatEvents = listByQuery(sql, paramVals);
        return pTStatEvents;
    }

    /**
     * List all details based on thermostat id.
     * @param thermostatId the thermostat id.
     * @return list.
     * @see com.ecofactor.qa.automation.dao.dr.ThermostatEventDao#setPointEDR(java.lang.Integer)
     */
    @Override
    public List<PartitionedThermostatEvent> setPointEDR(int thermostatId) {

        String sql = "SELECT te FROM PartitionedThermostatEvent te WHERE te.thermostatId= :thermostatId";
        Map<String, Object> paramVals = new HashMap<String, Object>();
        paramVals.put("thermostatId", thermostatId);
        List<PartitionedThermostatEvent> pTStatEvents = listByQuery(sql, paramVals);
        return pTStatEvents;
    }

    /**
     * Fetch group event id as program event id.
     * @param programEventId the program event id.
     * @return list.
     * @see com.ecofactor.qa.automation.dao.dr.ThermostatEventDao#listByGroupId(java.lang.Integer)
     */
    @Override
    public  int listByGroupId(final double programEventId) {

        final String sql = "select te from PartitionedThermostatEvent te where te.groupEventId= :programEventId";
        Map<String, Object> paramVals = new HashMap<String, Object>();
        paramVals.put("programEventId", programEventId);
        List<PartitionedThermostatEvent> pTStatEvents = listByQuery(sql, paramVals);
        final int thermostatId = pTStatEvents.get(0).getThermostatId().intValue();
        return thermostatId;
    }

    /**
     * @return
     * @see com.ecofactor.qa.automation.dao.BaseDaoImpl#getEntityManager()
     */
    public EntityManager getEntityManager() {

        return getRangeDataEntityManager();
    }
}
