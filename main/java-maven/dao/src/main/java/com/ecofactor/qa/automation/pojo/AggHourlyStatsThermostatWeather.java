package com.ecofactor.qa.automation.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.ecofactor.common.pojo.analytics.StatsThermostatWeather;

@Entity
@Table(name = "agg_hourly_ew_stats_thermostat_weather")
public class AggHourlyStatsThermostatWeather extends StatsThermostatWeather
		implements java.io.Serializable {
	@Id
	@Column(name = "hour")
	private Integer hour;
	@Column(name = "day")
	private Integer day;
	@Column(name = "month")
	private Integer month;
	@Column(name = "year")
	private Integer year;

	public AggHourlyStatsThermostatWeather() {

	}

	public Integer getHour() {

		return hour;
	}

	public void setHour(Integer hour) {

		this.hour = hour;
	}

	public Integer getDay() {

		return day;
	}

	public void setDay(Integer day) {

		this.day = day;
	}

	public Integer getMonth() {

		return month;
	}

	public void setMonth(Integer month) {

		this.month = month;
	}

	public Integer getYear() {

		return year;
	}

	public void setYear(Integer year) {

		this.year = year;
	}

	@Override
	public String toString() {

		String returnValue = "AggHourlyStatsThermostatWeather:\n";
		returnValue += super.toString();
		return returnValue;
	}
}
