package com.ecofactor.qa.automation.util.test;

import static org.testng.Reporter.log;

import java.util.List;

import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestNGMethod;
import org.testng.TestNGException;
import org.testng.annotations.Guice;

import com.ecofactor.qa.automation.util.DriverConfig;
import com.ecofactor.qa.automation.util.UtilModule;
import com.ecofactor.qa.automation.util.healthcheck.HealthCheck;
import com.ecofactor.qa.automation.util.healthcheck.HealthCheckControl;

import static com.ecofactor.qa.automation.util.DriverConfig.*;

@Guice(modules = { UtilModule.class })
public class JobValidator implements ISuiteListener {

    private static final String ENV_DEV = "DEV";
    private static final String HEALTH_CHECK_TRUE = "true";
    private static final String HEALTHCHECK = "healthcheck";
    private static final String APP_INSITE = "insite";
    private static final String APP_API = "api";
    private static final String APP_ALGORITHM = "algorithm";
    private static final String APP_CONSUMER = "consumer";
    private static final String INSITE_ENVS = "insite.envs";
    private static final String ALGORITHM_ENVS = "algorithm.envs";
    private static final String CONSUMER_ENVS = "consumer.envs";
    private DriverConfig driverConfig = new DriverConfig();

    @Override
    public void onStart(ISuite suite) {

        String packageName = suite.getXmlSuite().toXml();

        String env = System.getProperty(ENV);

        String consumerEnvs = driverConfig.get(CONSUMER_ENVS);
        String algorithmEns = driverConfig.get(ALGORITHM_ENVS);
        String insiteEnvs = driverConfig.get(INSITE_ENVS);
        if (env == null) {
            env = driverConfig.get(ENV);
        }
        boolean isEnvironmentSupported = false;
        String application = "";

        if (packageName != null && packageName.contains(APP_CONSUMER)) {
            application = APP_CONSUMER;
            if (consumerEnvs.contains(env)) {
                isEnvironmentSupported = true;
            }
        } else if (packageName != null && packageName.contains(APP_ALGORITHM)) {
            application = APP_ALGORITHM;
            if (algorithmEns.contains(env)) {
                isEnvironmentSupported = true;
            }

        } else if (packageName != null && packageName.contains(APP_INSITE)) {
            application = APP_INSITE;
            if (insiteEnvs.contains(env)) {
                isEnvironmentSupported = true;
            }
        } else if (packageName != null && packageName.contains(APP_API)) {
            application = APP_API;
            if (insiteEnvs.contains(env)) {
                isEnvironmentSupported = true;
            }
        }

        if (!isEnvironmentSupported) {
            throw new TestNGException("Environment " + env + " not supported for the application "
                    + application.toUpperCase());
        }
        boolean skipTestsForHealthCheck = false;
        String healthcheck = System.getProperty(HEALTHCHECK);
        String page = "";
        if (healthcheck != null && healthcheck.equals(HEALTH_CHECK_TRUE)) {
            if (env.equals(ENV_DEV)) {
                return;
            }
            HealthCheckControl healthCheckControl = new HealthCheckControl();
            if (application.equals(APP_CONSUMER) || application.equals(APP_ALGORITHM)) {
                page = APP_CONSUMER.toUpperCase();
            } else if (application.toUpperCase().equals(APP_INSITE)) {
                page = APP_INSITE.toUpperCase();
            }
            HealthCheck healthCheck = healthCheckControl.getHealthCheckInfo(env, page);
            skipTestsForHealthCheck = (healthCheck == null || healthCheck.isHealthy());
        }
        if (skipTestsForHealthCheck) {
            throw new TestNGException(
                    "Skipping tests because healthcheck is enabled and environment " + env
                            + " is not healthy.");
        }
        List<ITestNGMethod> allMethods = suite.getAllMethods();
        DriverConfig.setLogString("--------------------------------------------", true);
        DriverConfig.setLogString("Automation Test methods (" + allMethods.size() + ")", true);
        DriverConfig.setLogString("--------------------------------------------", true);
        for (ITestNGMethod testNGMethod : allMethods) {
            log(testNGMethod.getMethodName(), true);
        }
        DriverConfig.setLogString("--------------------------------------------", true);
    }

    @Override
    public void onFinish(ISuite suite) {

    }

}
