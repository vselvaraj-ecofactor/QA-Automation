package com.ecofactor.qa.automation.opsscript.data;

import static com.ecofactor.qa.automation.opsscript.config.OpsTestConfig.*;

import org.testng.annotations.DataProvider;

import com.ecofactor.qa.automation.opsscript.config.OpsTestConfig;
import com.google.inject.Inject;

public class OpsScriptDataProvider {

    @Inject
    private static OpsTestConfig opsTestConfig;

    @DataProvider(name = "algoSubscriptionCount")
    public static Object[][] algoSubscriptionCount() {

        String[] algoArray = opsTestConfig.get(SUBSCRIPTION_ALGOS).split(",");
        Object[][] data = { { algoArray, Integer.valueOf(opsTestConfig.get(SUBSCRIPTION_CALCULATION_NUMBER)) } };
        return data;
    }

    @DataProvider(name = "comcastOPSVerification")
    public static Object[][] comcastOPSVerification() {

        String[] algoArray = opsTestConfig.get(COMCAST_SUB_ALGOS).split(",");
        Object[][] data = { { algoArray,Integer.valueOf(opsTestConfig.get(COMCAST_ECP_CORE)),  Integer.valueOf(opsTestConfig.get(COMCAST_CALCULATION_NUMBER)) } };
        return data;
    }


    @DataProvider(name = "nonComcastOPSVerification")
    public static Object[][] nonComcastOPSVerification() {

        String[] algoArray = opsTestConfig.get(NON_COMCAST_SUB_ALGOS).split(",");
        Object[][] data = { { algoArray,Integer.valueOf(opsTestConfig.get(NON_COMCAST_ECP_CORE)),  Integer.valueOf(opsTestConfig.get(NON_COMCAST_CALCULATION_NUMBER)) } };
        return data;
    }

}
