package com.ecofactor.qa.automation.dao.dr;

import java.text.ParseException;

import java.util.Calendar;

import java.util.HashMap;
import java.util.Map;

import com.ecofactor.qa.automation.dao.BaseDaoImpl;
import com.ecofactor.qa.automation.pojo.EventControl;
import com.ecofactor.qa.automation.util.DateUtil;

public class EventControlDaoImpl extends BaseDaoImpl<EventControl> implements EventControlDao {

    /**
     * Update status as INACTIVE.
     * @param eventProgramId the program event id.
     * @see com.ecofactor.qa.automation.dao.dr.EventControlDao#updateStatus(java.lang.Integer)
     */
    @Override
    public void updateStatus(final Integer eventProgramId, String status) {

        final String sql = "select e from EventControl e where e.lsProgramEventId= :ls_program_event_id";
        final Map<String, Object> paramVals = new HashMap<String, Object>();
        paramVals.put("ls_program_event_id", eventProgramId);
        EventControl eControl = findByQuery(sql, paramVals);
        eControl.setEventControlStatus(status);
        updateEntity(eControl);

    }

    /**
     * Update nextPhaseTime based on program event id.
     * @param eventProgramId the program event id.
     * @see com.ecofactor.qa.automation.dao.dr.EventControlDao#updateNextPhaseTime(java.lang.Integer)
     */
    @Override
    public void updateNextPhaseTime(final Integer eventProgramId) {

        final String sql = "select e from EventControl e where e.lsProgramEventId= :ls_program_event_id";
        final Map<String, Object> paramVals = new HashMap<String, Object>();
        paramVals.put("ls_program_event_id", eventProgramId);
        EventControl eControl = findByQuery(sql, paramVals);
        Calendar currentTime = DateUtil.addTimeToUTCCalendar();
        eControl.setNextPhaseTime(currentTime);
        updateEntity(eControl);
        // System.out.println("s" + s);
    }

    /**
     * Fetch the next Phase time.
     * @param eventProgramId the event program id.
     * @return dateTime.
     * @throws ParseException
     * @see com.ecofactor.qa.automation.dao.dr.EventControlDao#fetchNextPhaseTime(java.lang.Integer)
     */
    @Override
    public String fetchNextPhaseTime(final Integer eventProgramId) throws ParseException {

        final String sql = "select e from EventControl e where e.lsProgramEventId= :ls_program_event_id";
        final Map<String, Object> paramVals = new HashMap<String, Object>();
        paramVals.put("ls_program_event_id", eventProgramId);
        EventControl eControl = findByQuery(sql, paramVals);
        String phaseTime = eControl.getNextPhaseTime().toString();
        /*
         * SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
         * sdf.setTimeZone(TimeZone.getTimeZone("UTC")); Date nextPhaseTime = sdf.parse(phaseTime);
         * SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-mm-dd' 'HH:mm:ssZ");
         * sdformat.setTimeZone(TimeZone.getTimeZone("UTC")); String actualNextPhaseTime =
         * sdformat.format(nextPhaseTime);
         */
        return phaseTime;
    }

    /**
     * Fetch the status for specified program event id.
     * @param eventProgramId the program event id.
     * @return String.
     * @see com.ecofactor.qa.automation.dao.dr.EventControlDao#fetchStatus(java.lang.Integer)
     */
    @Override
    public String fetchStatus(Integer eventProgramId) {

        final String sql = "select e from EventControl e where e.lsProgramEventId= :ls_program_event_id";
        final Map<String, Object> paramVals = new HashMap<String, Object>();
        paramVals.put("ls_program_event_id", eventProgramId);
        EventControl eControl = findByQuery(sql, paramVals);
        final String status = eControl.getEventControlStatus().toString();
        return status;
    }
}
