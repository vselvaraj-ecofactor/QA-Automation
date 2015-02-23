package com.ecofactor.qa.automation.util.healthcheck;

import static com.ecofactor.qa.automation.util.healthcheck.HealthCheckConfig.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.apache.log4j.Logger;
import org.json.JSONObject;

public class HealthCheckControl {

	static final Logger logger = Logger.getLogger(HealthCheckControl.class);

    private HealthCheckConfig healthCheckConfig;

	public HealthCheck getHealthCheckInfo(String environment, String page) {
		String urlString = null;
		HealthCheck healthCheck = null;
		healthCheckConfig = new HealthCheckConfig();
		try {
			if (environment.equals(ENV_QA_APPS)) {
				if (page.equals(PAGE_CONSUMER)) {
					urlString = healthCheckConfig.get(APPS_CONSUMER_URL);
				} else if (page.equals(PAGE_INSITE)) {
					urlString = healthCheckConfig.get(APPS_INSITE_URL);
				}
			} else if (environment.equals(ENV_QA_PLAT)) {
				if (page.equals(PAGE_CONSUMER)) {
					urlString = healthCheckConfig.get(PLAT_CONSUMER_URL);
				} else if (page.equals(PAGE_INSITE)) {
					urlString = healthCheckConfig.get(PLAT_INSITE_URL);
				}
			} else if (environment.equals(ENV_STAGE)) {
				if (page.equals(PAGE_CONSUMER)) {
					urlString = healthCheckConfig.get(STAGE_CONSUMER_URL);
				} else if (page.equals(PAGE_INSITE)) {
					urlString = healthCheckConfig.get(STAGE_INSITE_URL);
				}
			}
			URL url = new URL(urlString);
			URLConnection connection = url.openConnection();

			String line;
			StringBuilder builder = new StringBuilder();
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			while((line = reader.readLine()) != null) {
			 builder.append(line);
			}

			JSONObject json = new JSONObject(builder.toString());
			healthCheck = new HealthCheck();
			healthCheck.setDatabaseActive(json.getBoolean(DATABASE));
			healthCheck.setDigiActive(json.getBoolean(DIGI));
			healthCheck.setEnvironment(json.getString(ENVIRONMENT));
			healthCheck.setHealthy(json.getBoolean(HEALTHY));
			healthCheck.setJbossActive(json.getBoolean(JBOSS));
			healthCheck.setMailActive(json.getBoolean(MAIL));
			healthCheck.setRevision(json.getString(REVISION));
			healthCheck.setServiceName(json.getString(SERVICE_NAME));
			if ( json.has(SALES_FORCE)) {
				healthCheck.setSalesForceActive(json.getBoolean(SALES_FORCE));
			}
			if ( json.has(YAHOO)) {
				healthCheck.setYahooActive(json.getBoolean(YAHOO));
			}


		} catch (Exception exception) {
			exception.printStackTrace();
			logger.error("Exception Occured in method getHealthCheckInfo()-->" + exception.getMessage());
		}
		return healthCheck;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {

		HealthCheckControl healthCheckControl = new HealthCheckControl();
		HealthCheck healthCheck = healthCheckControl.getHealthCheckInfo(ENV_QA_APPS, PAGE_CONSUMER);
		logger.info("Health Check" + healthCheck);
	}
}
