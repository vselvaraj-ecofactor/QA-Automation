package com.ecofactor.qa.automation.pojo;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ef_event_control")
public class EventControl implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "event_control_id")
    private Integer id;

    @Column(name = "ls_program_event_id")
    private Integer lsProgramEventId;

    @Column(name = "algorithm_id")
    private Integer algorithmId;

    @Column(name = "ls_event_control_status")
    private String eventControlStatus;

    @Column(name = "next_phase_time")
    private Calendar nextPhaseTime;

    @Column(name = "date_setup")
    private Calendar dateSetup;

    @Column(name = "last_updated")
    private Calendar lastUpdated;
    public Integer getId() {

        return id;
    }

    public void setId(Integer id) {

        this.id = id;
    }

    public Integer getLsProgramEventId() {

        return lsProgramEventId;
    }

    public void setLsProgramEventId(Integer lsProgramEventId) {

        this.lsProgramEventId = lsProgramEventId;
    }

    public Integer getAlgorithmId() {

        return algorithmId;
    }

    public void setAlgorithmId(Integer algorithmId) {

        this.algorithmId = algorithmId;
    }

    public String getEventControlStatus() {

        return eventControlStatus;
    }

    public void setEventControlStatus(String eventControlStatus) {

        this.eventControlStatus = eventControlStatus;
    }

    public Calendar getNextPhaseTime() {

        return nextPhaseTime;
    }

    public void setNextPhaseTime(Calendar nextPhaseTime) {

        this.nextPhaseTime = nextPhaseTime;
    }

    public Calendar getDateSetup() {

        return dateSetup;
    }

    public void setDateSetup(Calendar dateSetup) {

        this.dateSetup = dateSetup;
    }

    public Calendar getLastUpdated() {

        return lastUpdated;
    }

    public void setLastUpdated(Calendar lastUpdated) {

        this.lastUpdated = lastUpdated;
    }   
}
