package com.ecofactor.qa.automation.pojo;

import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ew_quant_report")
public class EWQuantReport implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "quant_report_id")
    private Integer id;

    @Column(name = "report_type")
    private String reportType;

    @Column(name = "thermostat_id")
    private Integer thermostatId;

    @Column(name = "valid_from")
    private Calendar validFrom;

    @Column(name = "valid_to")
    private Calendar validTo;

    @Column(name = "last_updated")
    private Calendar lastUpdated;

    @Column(name = "report_data")
    private String reportData;
    
    @Column(name = "runtime_hours_heat")
    private Double runtimeHoursHeat;

    @Column(name = "runtime_hours_cool")
    private Double runtimeHoursCool;
    
    public Integer getId() {

        return this.id;
    }

    public void setId(Integer id) {

        this.id = id;
    }

    public String getReportType() {

        return this.reportType;
    }

    public void setReportType(String reportType) {

        this.reportType = reportType;
    }

    public Integer getThermostatId() {

        return this.thermostatId;
    }

    public void setThermostatId(Integer thermostatId) {

        this.thermostatId = thermostatId;
    }

    public Calendar getValidFrom() {

        return this.validFrom;
    }

    public void setValidFrom(Calendar validFrom) {

        this.validFrom = validFrom;
    }

    public Calendar getValidTo() {

        return this.validTo;
    }

    public void setValidTo(Calendar validTo) {

        this.validTo = validTo;
    }

    public Calendar getLastUpdated() {

        return this.lastUpdated;
    }

    public void setLastUpdated(Calendar lastUpdated) {

        this.lastUpdated = lastUpdated;
    }

    public String getReportData() {

        return this.reportData;
    }

    public void setReportData(String reportData) {

        this.reportData = reportData;
    }

    public Double getRuntimeHoursHeat() {
    
        return runtimeHoursHeat;
    }

    public void setRuntimeHoursHeat(Double runtimeHoursHeat) {
    
        this.runtimeHoursHeat = runtimeHoursHeat;
    }

    public Double getRuntimeHoursCool() {
    
        return runtimeHoursCool;
    }

    public void setRuntimeHoursCool(Double runtimeHoursCool) {
    
        this.runtimeHoursCool = runtimeHoursCool;
    }

    public static enum ReportType {
        SAVMONTHLY;
    }
}
