package com.ecofactor.qa.automation.pojo;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ef_ls_program_event_report")
public class LSProgramEventReport implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "event_report_id")
    private Integer id;

    @Column(name = "ls_program_event_id")
    private Integer programID;

    @Column(name = "num_expected_locations")
    private Integer numExpectedLocations;

    @Column(name = "num_expected_thermostats")
    private Integer numExpectedThermostats;

    @Column(name = "num_actual_locations")
    private Integer numActualLocations;

    @Column(name = "num_actual_thermostats")
    private Integer numActualThermostats;

    @Column(name = "num_drop_off")
    private Integer numDropOff;

    @Column(name = "cumulative_run_time")
    private Integer cumulativeRunTime;

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

        return programID;
    }

    public void setLsProgramEventId(Integer lsProgramEventId) {

        this.programID = lsProgramEventId;
    }

    public Integer getNumExpectedLocations() {

        return numExpectedLocations;
    }

    public void setNumExpectedLocations(Integer numExpectedLocations) {

        this.numExpectedLocations = numExpectedLocations;
    }

    public Integer getNumExpectedThermostats() {

        return numExpectedThermostats;
    }

    public void setNumExpectedThermostats(Integer numExpectedThermostats) {

        this.numExpectedThermostats = numExpectedThermostats;
    }

    public Integer getNumActualLocations() {

        return numActualLocations;
    }

    public void setNumActualLocations(Integer numActualLocations) {

        this.numActualLocations = numActualLocations;
    }

    public Integer getNumActualThermostats() {

        return numActualThermostats;
    }

    public void setNumActualThermostats(Integer numActualThermostats) {

        this.numActualThermostats = numActualThermostats;
    }

    public Integer getNumDropOff() {

        return numDropOff;
    }

    public void setNumDropOff(Integer numDropOff) {

        this.numDropOff = numDropOff;
    }

    public Integer getCumulativeRunTime() {

        return cumulativeRunTime;
    }

    public void setCumulativeRunTime(Integer cumulativeRunTime) {

        this.cumulativeRunTime = cumulativeRunTime;
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
