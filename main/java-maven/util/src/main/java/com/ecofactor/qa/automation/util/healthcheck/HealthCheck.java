package com.ecofactor.qa.automation.util.healthcheck;

public class HealthCheck {
	
	private String revision;
	private boolean mailActive;
	private String environment;
	private boolean digiActive;
	private boolean jbossActive;
	private boolean databaseActive;
	private String serviceName;
	private boolean healthy;
	private boolean yahooActive;
	private boolean salesForceActive;
	
	public String getRevision() {
		return revision;
	}
	public void setRevision(String revision) {
		this.revision = revision;
	}
	public boolean isMailActive() {
		return mailActive;
	}
	public void setMailActive(boolean mailActive) {
		this.mailActive = mailActive;
	}
	public String getEnvironment() {
		return environment;
	}
	public void setEnvironment(String environment) {
		this.environment = environment;
	}
	public boolean isDigiActive() {
		return digiActive;
	}
	public void setDigiActive(boolean digiActive) {
		this.digiActive = digiActive;
	}
	public boolean isJbossActive() {
		return jbossActive;
	}
	public void setJbossActive(boolean jbossActive) {
		this.jbossActive = jbossActive;
	}
	public boolean isDatabaseActive() {
		return databaseActive;
	}
	public void setDatabaseActive(boolean databaseActive) {
		this.databaseActive = databaseActive;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public boolean isHealthy() {
		return healthy;
	}
	
	
	public void setHealthy(boolean healthy) {
		this.healthy = healthy;
	}
	public boolean isYahooActive() {
		return yahooActive;
	}
	public void setYahooActive(boolean yahooActive) {
		this.yahooActive = yahooActive;
	}
	public boolean isSalesForceActive() {
		return salesForceActive;
	}
	public void setSalesForceActive(boolean salesForceActive) {
		this.salesForceActive = salesForceActive;
	}
}
