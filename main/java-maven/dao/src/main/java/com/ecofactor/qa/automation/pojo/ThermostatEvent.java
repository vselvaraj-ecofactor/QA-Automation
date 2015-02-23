package com.ecofactor.qa.automation.pojo;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ef_thermostat_event")
public class ThermostatEvent implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "thermostat_event_id")
    private BigInteger id;

    @Column(name = "thermostat_id")
    private Integer thermostatId;

    @Column(name = "algorithm_id")
    private Integer algorithmId;

    @Column(name = "group_event_id")
    private double groupEventId;

    @Column(name = "old_setting")
    private Integer oldSetting;

    @Column(name = "new_setting")
    private Integer newSetting;

    @Column(name = "event_phase")
    private Integer eventPhase;

    @Column(name = "event_type")
    private String eventType;

    @Column(name = "event_status")
    private String eventStatus;

    @Column(name = "action")
    private String action;

    public String getAction() {

        return action;
    }

    public void setAction(String action) {

        this.action = action;
    }

    public BigInteger getId() {

        return id;
    }

    public void setId(BigInteger id) {

        this.id = id;
    }

    public Integer getThermostatId() {

        return thermostatId;
    }

    public void setThermostatId(Integer thermostatId) {

        this.thermostatId = thermostatId;
    }

    public Integer getAlgorithmId() {

        return algorithmId;
    }

    public void setAlgorithmId(Integer algorithmId) {

        this.algorithmId = algorithmId;
    }

    public double getGroupEventId() {

        return groupEventId;
    }

    public void setGroupEventId(double groupEventId) {

        this.groupEventId = groupEventId;
    }

    public Integer getOldSetting() {

        return oldSetting;
    }

    public void setOldSetting(Integer oldSetting) {

        this.oldSetting = oldSetting;
    }

    public Integer getNewSetting() {

        return newSetting;
    }

    public void setNewSetting(Integer newSetting) {

        this.newSetting = newSetting;
    }

    public Integer getEventPhase() {

        return eventPhase;
    }

    public void setEventPhase(Integer eventPhase) {

        this.eventPhase = eventPhase;
    }

    public String getEventType() {

        return eventType;
    }

    public void setEventType(String eventType) {

        this.eventType = eventType;
    }

    public String getEventStatus() {

        return eventStatus;
    }

    public void setEventStatus(String eventStatus) {

        this.eventStatus = eventStatus;
    }

}
