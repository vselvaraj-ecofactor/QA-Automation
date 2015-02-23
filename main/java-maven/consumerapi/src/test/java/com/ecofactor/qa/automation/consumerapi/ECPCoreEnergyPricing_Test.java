/*
 * ECPCoreEnergyPricing_Test.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.consumerapi;

import static com.ecofactor.qa.automation.platform.util.LogUtil.setLogString;

import javax.ws.rs.core.Response;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import com.ecofactor.qa.automation.consumerapi.data.ApiDataProvider;
import com.ecofactor.qa.automation.platform.constants.Groups;
import com.ecofactor.qa.automation.platform.enums.CustomLogLevel;
import com.ecofactor.qa.automation.util.JsonUtil;
import com.ecofactor.qa.automation.util.UtilModule;
import com.google.inject.Inject;

/**
 * Test class for testing Location Runtime Savings API
 * @author npaila
 */
@Guice(modules = { UtilModule.class, ApiModule.class })
public class ECPCoreEnergyPricing_Test extends AbstractTest {

    @Inject
    private ConsumerApiURL consumerApiURL;

    /**
     * APPS-246 Energy pricing on valid ecp core.
     * @param username the username
     * @param password the password
     * @param locationId the location id
     */
    @Test(groups = { Groups.SANITY1, Groups.BROWSER }, dataProvider = "validecpcore", dataProviderClass = ApiDataProvider.class, priority = 1)
    public void energyPricingOnValidEcpCore(final String username, final String password,
            final String ecpcoreId) {

        setLogString("Verify energy pricing for valid ecp core id.", true);
        final Response response = consumerApiURL.getECPCoreEnergySavings(ecpcoreId, securityCookie);
        setLogString("Response :'" + response + "'", true);

        final String content = response.readEntity(String.class);

        setLogString("Json Response:", true, CustomLogLevel.MEDIUM);
        setLogString(content, true, CustomLogLevel.MEDIUM);

        final JSONObject jsonObject = JsonUtil.parseObject(content);

        final JSONArray gasJsonArray = (JSONArray) jsonObject.get("gas");
        final JSONArray electricJsonArray = (JSONArray) jsonObject.get("electric");

        Assert.assertTrue(!gasJsonArray.isEmpty() || !electricJsonArray.isEmpty(),
                "Energy savings not exists for given ecp core.");

        setLogString("Verified energy pricing for valid ecp core id.", true);
    }

}
