package com.ecofactor.qa.automation.api;

import com.ecofactor.qa.automation.util.BaseConfig;
import com.ecofactor.qa.automation.util.DriverConfig;
import com.google.inject.Inject;

public class HttpsReportsAPIConfig extends BaseConfig {

	    public static final String START_DATE = "startDate";
	    public static final String END_DATE = "endDate";
	    public static final String INTERVAL = "interval";
	    public static final String ACCUMULATED = "accumulated";
	    public static final String API = "api.url";
	    public static final String LOCATION_USAGE = "location.usage.url";
	    public static final String USER_USAGE = "user.usage.url";
	    public static final String LOCATION_SAVINGS = "location.savings.url";
	    public static final String USER_SAVINGS = "user.savings.url";
	    public static final String LOCATION_ID = "location.id";
	    public static final String USER_ID = "user.id";
	    public static final String ERROR_MSG = "error.msg";
	    public static final String USER_ERROR = "userId.error";
	    public static final String LOCATION_ERROR = "locationId.error";
	    public static final String USAGE_START_DATE_ERROR = "usage.startDate.error";
	    public static final String USAGE_END_DATE_ERROR = "usage.endDate.error";
	    public static final String USAGE_INTERVAL_ERROR = "usage.interval.error";
	    public static final String USAGE_INTERVAL_INVALID = "usage.interval.invalid.error";
	    public static final String SVG_START_DATE_ERROR = "savings.startDate.error";
	    public static final String SVG_END_DATE_ERROR = "savings.endDate.error";
	    public static final String INTERNAL_ERROR = "internal.error";
	    public static final String YEAR = "year";
	    public static final String MONTH = "month";
	    public static final String DAY = "day";
	    public static final String HOUR = "hour";

	    /**
	     * Instantiates a new report api config.
	     */
	    @Inject
	    public HttpsReportsAPIConfig(DriverConfig driverConfig) {

	        super(driverConfig.get(DriverConfig.ENV));
	        load("httpsapi.properties");
	    }
}
