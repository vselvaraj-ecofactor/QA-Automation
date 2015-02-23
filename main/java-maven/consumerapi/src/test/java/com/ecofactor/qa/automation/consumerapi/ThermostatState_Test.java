/*
 * ThermostatState_Test.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.consumerapi;

import static com.ecofactor.qa.automation.platform.util.LogUtil.*;

import java.lang.reflect.Method;

import org.apache.http.client.methods.HttpGet;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import com.ecofactor.qa.automation.consumerapi.data.ApiDataProvider;
import com.ecofactor.qa.automation.dao.DaoModule;
import com.ecofactor.qa.automation.util.JsonUtil;
import com.ecofactor.qa.automation.util.UtilModule;
import com.google.inject.Inject;

/**
 * Test class for testing ThermostatState API
 *
 * @author vlobanov
 */
@Guice(modules = { UtilModule.class, DaoModule.class, ApiModule.class })
public class ThermostatState_Test extends AbstractTest {
	@Inject
	ConsumerApiURL consumerApiURL;
	@Inject
	ApiConfig apiconfig;
	
	private static final String HVAC_SYSTEMS = "hvacSystems";

	
	

	
    /**
     * Before method.
     * @param method the method
     * @param param the param
     */
    @BeforeMethod(alwaysRun = true)
    public void beforeMethod(final Method method, final Object[] param) {
        
        logUtil.logStart(method, param, null);
        startTime = System.currentTimeMillis();
        HTTPClient.getSecurityCookieForUser((String) param[0],
                (String) param[1]);
    }
	
	/**
	 * test_set_cool_hvac_mode_to_thermostat.
	 */
	
	@Test(dataProvider = HVAC_SYSTEMS, dataProviderClass = ApiDataProvider.class, priority =1)
	public void test_set_cool_hvac_mode_to_thermostat(final String username, final String password,final String thermostatId) {
		
		final String setcoolmode = ApiConfig.JSON_STATE_VALID_HVAC_MODE_COOL;
	    final String response = HTTPClient.getPatchResponse(apiconfig.get(ApiConfig.THERMOSTAT_STATE_URL)
	            .replaceFirst("THERMOSTATID", thermostatId), apiconfig.get(setcoolmode));
		setLogString("response :'" + response + "'", true);
//	    final JSONObject jsonObject = JsonUtil.parseObject(response);
//	    final JSONArray errors = (JSONArray) jsonObject.get("errors");
//	    final JSONObject obj = (JSONObject) errors.get(0);;
//	    final String msg = (String) obj.get("msg");
//	    final Long code = (Long) obj.get("code");
//
//		final String Response = HttpGet.METHOD_NAME;
//		
//	    
//	    System.out.println(Response);
	
	}
    
	/**
	 * test_set_empty_hvacmode_to_thermostat.
	 */
	@Test(dataProvider = HVAC_SYSTEMS, dataProviderClass = ApiDataProvider.class, priority =2)
	public void test_set_empty_hvacmode_to_thermostat(final String username,final String password, final String thermostatId) {

	    final String emptyhvac = ApiConfig.JSON_STATE_INVALID_HVAC_MODE_EMPTY;
	    final String response = HTTPClient.getPatchResponse(apiconfig.get(ApiConfig.THERMOSTAT_STATE_URL)
                .replaceFirst("THERMOSTATID", thermostatId), apiconfig.get(emptyhvac));
		setLogString("response :'" + response + "'", true);
        final JSONObject jsonObject = JsonUtil.parseObject(response);
        final JSONArray errors = (JSONArray) jsonObject.get("errors");
        final JSONObject obj = (JSONObject) errors.get(0);;
        final String msg = (String) obj.get("msg");
        final Long code = (Long) obj.get("code");
        

		Assert.assertTrue(msg.equalsIgnoreCase("Invalid device hvac mode."));
	    Assert.assertTrue(code == 99007, "Expected error code 99007" + code);
	
	}

	/**
	 * test_set_emptyspace_hvacmode_to_thermostat.
	 */
	@Test(dataProvider = HVAC_SYSTEMS, dataProviderClass = ApiDataProvider.class, priority =3)
	public void test_set_emptyspace_hvacmode_to_thermostat(final String username, final String password,final String thermostatId) {
		
	final String emptyspacehvac = ApiConfig.JSON_STATE_INVALID_HVAC_MODE_EMPTYSPACE;
    final String response = HTTPClient.getPatchResponse(apiconfig.get(ApiConfig.THERMOSTAT_STATE_URL)
            .replaceFirst("THERMOSTATID", thermostatId), apiconfig.get(emptyspacehvac));
	setLogString("response :'" + response + "'", true);
    final JSONObject jsonObject = JsonUtil.parseObject(response);
    final JSONArray errors = (JSONArray) jsonObject.get("errors");
    final JSONObject obj = (JSONObject) errors.get(0);;
    final String msg = (String) obj.get("msg");
    final Long code = (Long) obj.get("code");
    
    Assert.assertTrue(msg.equalsIgnoreCase("Invalid device hvac mode."));
	Assert.assertTrue(code == 99007, "Expected error code 99007" + code);
	}
	
	/**
	 * test_set_null_hvacmode_to_thermostat.
	 */
	@Test(dataProvider = HVAC_SYSTEMS, dataProviderClass = ApiDataProvider.class, priority =4)
	public void test_set_null_hvacmode_to_thermostat(final String username, final String password,final String thermostatId) {
	
		final String nullhvac = ApiConfig.JSON_STATE_INVALID_HVAC_MODE_NULL;
	    final String response = HTTPClient.getPatchResponse(apiconfig.get(ApiConfig.THERMOSTAT_STATE_URL)
	            .replaceFirst("THERMOSTATID", thermostatId), apiconfig.get(nullhvac));
		setLogString("response :'" + response + "'", true);
	    final JSONObject jsonObject = JsonUtil.parseObject(response);
	    final JSONArray errors = (JSONArray) jsonObject.get("errors");
	    final JSONObject obj = (JSONObject) errors.get(0);;
	    final String msg = (String) obj.get("msg");
	    final Long code = (Long) obj.get("code");

	    Assert.assertTrue(msg.equalsIgnoreCase("Invalid device hvac mode."));
		Assert.assertTrue(code == 99007, "Expected error code 99007" + code);
	}
	
	/**
	 * test_set_numbers_hvacmode_to_thermostat.
	 */
	@Test(dataProvider = HVAC_SYSTEMS, dataProviderClass = ApiDataProvider.class, priority =5)
	public void test_set_numbers_hvacmode_to_thermostat(final String username, final String password,final String thermostatId) {
		
		final String numhvac = ApiConfig.JSON_STATE_INVALID_HVAC_MODE_NUMBERS;
	    final String response = HTTPClient.getPatchResponse(apiconfig.get(ApiConfig.THERMOSTAT_STATE_URL)
	            .replaceFirst("THERMOSTATID", thermostatId), apiconfig.get(numhvac));
		setLogString("response :'" + response + "'", true);
	    final JSONObject jsonObject = JsonUtil.parseObject(response);
	    final JSONArray errors = (JSONArray) jsonObject.get("errors");
	    final JSONObject obj = (JSONObject) errors.get(0);;
	    final String msg = (String) obj.get("msg");
	    final Long code = (Long) obj.get("code");

	    Assert.assertTrue(msg.equalsIgnoreCase("Invalid device hvac mode."));
		Assert.assertTrue(code == 99007, "Expected error code 99007" + code);
	}
	
	/**
	 * test_set_multicomma_hvacmode_to_thermostat.
	 */
	@Test(dataProvider = HVAC_SYSTEMS, dataProviderClass = ApiDataProvider.class, priority =6)
	public void test_set_multicomma_hvacmode_to_thermostat(final String username, final String password,final String thermostatId) {

		final String multicommahvac = ApiConfig.JSON_STATE_INVALID_HVAC_MODE_MULTICOMMASEP;
	    final String response = HTTPClient.getPatchResponse(apiconfig.get(ApiConfig.THERMOSTAT_STATE_URL)
	            .replaceFirst("THERMOSTATID", thermostatId), apiconfig.get(multicommahvac));
		setLogString("response :'" + response + "'", true);
	    final JSONObject jsonObject = JsonUtil.parseObject(response);
	    final JSONArray errors = (JSONArray) jsonObject.get("errors");
	    final JSONObject obj = (JSONObject) errors.get(0);;
	    final String msg = (String) obj.get("msg");
	    final Long code = (Long) obj.get("code");

	    Assert.assertTrue(msg.equalsIgnoreCase("Invalid device hvac mode."));
		Assert.assertTrue(code == 99007, "Expected error code 99007" + code);
	}
	
	/**
	 * test_set_multitab_hvacmode_to_thermostat.
	 */
	@Test(dataProvider = HVAC_SYSTEMS, dataProviderClass = ApiDataProvider.class, priority =7)
	public void test_set_multitab_hvacmode_to_thermostat(final String username, final String password,final String thermostatId) {

		final String multitabhvac = ApiConfig.JSON_STATE_INVALID_HVAC_MODE_MULTITABSEP;
	    final String response = HTTPClient.getPatchResponse(apiconfig.get(ApiConfig.THERMOSTAT_STATE_URL)
	            .replaceFirst("THERMOSTATID", thermostatId), apiconfig.get(multitabhvac));
		setLogString("response :'" + response + "'", true);
	    final JSONObject jsonObject = JsonUtil.parseObject(response);
	    final JSONArray errors = (JSONArray) jsonObject.get("errors");
	    final JSONObject obj = (JSONObject) errors.get(0);;
	    final String msg = (String) obj.get("msg");
	    final Long code = (Long) obj.get("code");

	    Assert.assertTrue(msg.equalsIgnoreCase("Invalid device hvac mode."));
		Assert.assertTrue(code == 99007, "Expected error code 99007" + code);
	}
	
	/**
	 * test_set_noquotes_hvacmode_to_thermostat.
	 */
	@Test(dataProvider = HVAC_SYSTEMS, dataProviderClass = ApiDataProvider.class, priority =8)
	public void test_set_noquotes_hvacmode_to_thermostat(final String username, final String password,final String thermostatId) {
		
		final String noquoteshvac = ApiConfig.JSON_STATE_INVALID_HVAC_MODE_NOQUOTES;
	    final String response = HTTPClient.getPatchResponse(apiconfig.get(ApiConfig.THERMOSTAT_STATE_URL)
	            .replaceFirst("THERMOSTATID", thermostatId), apiconfig.get(noquoteshvac));
		setLogString("response :'" + response + "'", true);
	    final JSONObject jsonObject = JsonUtil.parseObject(response);
	    final JSONArray errors = (JSONArray) jsonObject.get("errors");
	    final JSONObject obj = (JSONObject) errors.get(0);;
	    final String msg = (String) obj.get("msg");
	    final Long code = (Long) obj.get("code");

	    Assert.assertTrue(msg.equalsIgnoreCase("Request must have at least one thermostat update operation."));
		Assert.assertTrue(code == 14015, "Expected error code 14015" + code);
	}
	
	/**
	 * test_set_noquotesemty_hvacmode_to_thermostat.
	 */
	@Test(dataProvider = HVAC_SYSTEMS, dataProviderClass = ApiDataProvider.class, priority =9)
	public void test_set_noquotesempty_hvacmode_to_thermostat(final String username, final String password,final String thermostatId) {

		final String noquotesemptyhvac = ApiConfig.JSON_STATE_INVALID_HVAC_MODE_NOQUOTESEMPTY;
	    final String response = HTTPClient.getPatchResponse(apiconfig.get(ApiConfig.THERMOSTAT_STATE_URL)
	            .replaceFirst("THERMOSTATID", thermostatId), apiconfig.get(noquotesemptyhvac));
		setLogString("response :'" + response + "'", true);
	    final JSONObject jsonObject = JsonUtil.parseObject(response);
	    final JSONArray errors = (JSONArray) jsonObject.get("errors");
	    final JSONObject obj = (JSONObject) errors.get(0);;
	    final String msg = (String) obj.get("msg");
	    final Long code = (Long) obj.get("code");

	    Assert.assertTrue(msg.equalsIgnoreCase("Request must have at least one thermostat update operation."));
		Assert.assertTrue(code == 14015, "Expected error code 14015" + code);
	}
	
	/**
	 * test_set_noquotesnull_hvacmode_to_thermostat.
	 */
	@Test(dataProvider = HVAC_SYSTEMS, dataProviderClass = ApiDataProvider.class, priority =10)
	public void test_set_noquotesnull_hvacmode_to_thermostat(final String username, final String password,final String thermostatId) {
	
		final String noquotesnullhvac = ApiConfig.JSON_STATE_INVALID_HVAC_MODE_NOQUOTESNULL;
	    final String response = HTTPClient.getPatchResponse(apiconfig.get(ApiConfig.THERMOSTAT_STATE_URL)
	            .replaceFirst("THERMOSTATID", thermostatId), apiconfig.get(noquotesnullhvac));
		setLogString("response :'" + response + "'", true);
	    final JSONObject jsonObject = JsonUtil.parseObject(response);
	    final JSONArray errors = (JSONArray) jsonObject.get("errors");
	    final JSONObject obj = (JSONObject) errors.get(0);;
	    final String msg = (String) obj.get("msg");
	    final Long code = (Long) obj.get("code");

	    Assert.assertTrue(msg.equalsIgnoreCase("Request must have at least one thermostat update operation."));
		Assert.assertTrue(code == 14015, "Expected error code 14015" + code);
	}
	
	/**
	 * test_set_singlequotes_hvacmode_to_thermostat.
	 */
	@Test(dataProvider = HVAC_SYSTEMS, dataProviderClass = ApiDataProvider.class, priority =11)
	public void test_set_singlequotes_hvacmode_to_thermostat(final String username, final String password,final String thermostatId) {

		final String singlequoteshvac = ApiConfig.JSON_STATE_INVALID_HVAC_MODE_SINGLEQUOTES;
	    final String response = HTTPClient.getPatchResponse(apiconfig.get(ApiConfig.THERMOSTAT_STATE_URL)
	            .replaceFirst("THERMOSTATID", thermostatId), apiconfig.get(singlequoteshvac));
		setLogString("response :'" + response + "'", true);
	    final JSONObject jsonObject = JsonUtil.parseObject(response);
	    final JSONArray errors = (JSONArray) jsonObject.get("errors");
	    final JSONObject obj = (JSONObject) errors.get(0);;
	    final String msg = (String) obj.get("msg");
	    final Long code = (Long) obj.get("code");
	    
	    Assert.assertTrue(msg.equalsIgnoreCase("Request must have at least one thermostat update operation."));
		Assert.assertTrue(code == 14015, "Expected error code 14015" + code);
	}
	
	/**
	 * test_set_nonexisting_hvacmode_to_thermostat.
	 */
	@Test(dataProvider = HVAC_SYSTEMS, dataProviderClass = ApiDataProvider.class, priority =12)
	public void test_set_nonexisting_hvacmode_to_thermostat(final String username, final String password,final String thermostatId) {

		final String nonexistinghvac = ApiConfig.JSON_STATE_INVALID_HVAC_MODE_NONEXISTING;
	    final String response = HTTPClient.getPatchResponse(apiconfig.get(ApiConfig.THERMOSTAT_STATE_URL)
	            .replaceFirst("THERMOSTATID", thermostatId), apiconfig.get(nonexistinghvac));
		setLogString("response :'" + response + "'", true);
	    final JSONObject jsonObject = JsonUtil.parseObject(response);
	    final JSONArray errors = (JSONArray) jsonObject.get("errors");
	    final JSONObject obj = (JSONObject) errors.get(0);;
	    final String msg = (String) obj.get("msg");
	    final Long code = (Long) obj.get("code");

	    Assert.assertTrue(msg.equalsIgnoreCase("Invalid device hvac mode."));
		Assert.assertTrue(code == 99007, "Expected error code 99007" + code);
	}
	
	/**
	 * test_set_uppercase_hvacmode_to_thermostat.
	 */
	@Test(dataProvider = HVAC_SYSTEMS, dataProviderClass = ApiDataProvider.class, priority =13)
	public void test_set_uppercase_hvacmode_to_thermostat(final String username, final String password,final String thermostatId) {
	
		final String uppercasehvac = ApiConfig.JSON_STATE_INVALID_HVAC_MODE_UPPERCASE;
	    final String response = HTTPClient.getPatchResponse(apiconfig.get(ApiConfig.THERMOSTAT_STATE_URL)
	            .replaceFirst("THERMOSTATID", thermostatId), apiconfig.get(uppercasehvac));
		setLogString("response :'" + response + "'", true);
	    final JSONObject jsonObject = JsonUtil.parseObject(response);
	    final JSONArray errors = (JSONArray) jsonObject.get("errors");
	    final JSONObject obj = (JSONObject) errors.get(0);;
	    final String msg = (String) obj.get("msg");
	    final Long code = (Long) obj.get("code");

	    Assert.assertTrue(msg.equalsIgnoreCase("Invalid device hvac mode."));
		Assert.assertTrue(code == 99007, "Expected error code 99007" + code);
	}
	
	/**
	 * test_set_empty_fanmode_to_thermostat.
	 */
	@Test(dataProvider = HVAC_SYSTEMS, dataProviderClass = ApiDataProvider.class, priority =14)
	public void test_set_empty_fanmode_to_thermostat(final String username,final String password, final String thermostatId) {

		final String emptyfan = ApiConfig.JSON_STATE_INVALID_FAN_MODE_EMPTY;
	    final String response = HTTPClient.getPatchResponse(apiconfig.get(ApiConfig.THERMOSTAT_STATE_URL)
	            .replaceFirst("THERMOSTATID", thermostatId), apiconfig.get(emptyfan));
		setLogString("response :'" + response + "'", true);
	    final JSONObject jsonObject = JsonUtil.parseObject(response);
	    final JSONArray errors = (JSONArray) jsonObject.get("errors");
	    final JSONObject obj = (JSONObject) errors.get(0);;
	    final String msg = (String) obj.get("msg");
	    final Long code = (Long) obj.get("code");

		Assert.assertTrue(msg.equalsIgnoreCase("Invalid device fan mode."));
	    Assert.assertTrue(code == 99013, "Expected error code 99013. Actual error code is :" + code);
	}

	/**
	 * test_set_emptyspace_fanmode_to_thermostat.
	 */
	@Test(dataProvider = HVAC_SYSTEMS, dataProviderClass = ApiDataProvider.class, priority =15)
	public void test_set_emptyspace_fanmode_to_thermostat(final String username, final String password,final String thermostatId) {

		final String emptyspacefan = ApiConfig.JSON_STATE_INVALID_FAN_MODE_EMPTYSPACE;
	    final String response = HTTPClient.getPatchResponse(apiconfig.get(ApiConfig.THERMOSTAT_STATE_URL)
	            .replaceFirst("THERMOSTATID", thermostatId), apiconfig.get(emptyspacefan));
		setLogString("response :'" + response + "'", true);
	    final JSONObject jsonObject = JsonUtil.parseObject(response);
	    final JSONArray errors = (JSONArray) jsonObject.get("errors");
	    final JSONObject obj = (JSONObject) errors.get(0);;
	    final String msg = (String) obj.get("msg");
	    final Long code = (Long) obj.get("code");

		Assert.assertTrue(msg.equalsIgnoreCase("Invalid device fan mode."));
	    Assert.assertTrue(code == 99013, "Expected error code 99013. Actual error code is :" + code);
	}
	
	/**
	 * test_set_null_fanmode_to_thermostat.
	 */
	@Test(dataProvider = HVAC_SYSTEMS, dataProviderClass = ApiDataProvider.class, priority =16)
	public void test_set_null_fanmode_to_thermostat(final String username, final String password,final String thermostatId) {

		final String nullfanmode = ApiConfig.JSON_STATE_INVALID_FAN_MODE_NULL;
	    final String response = HTTPClient.getPatchResponse(apiconfig.get(ApiConfig.THERMOSTAT_STATE_URL)
	            .replaceFirst("THERMOSTATID", thermostatId), apiconfig.get(nullfanmode));
		setLogString("response :'" + response + "'", true);
	    final JSONObject jsonObject = JsonUtil.parseObject(response);
	    final JSONArray errors = (JSONArray) jsonObject.get("errors");
	    final JSONObject obj = (JSONObject) errors.get(0);;
	    final String msg = (String) obj.get("msg");
	    final Long code = (Long) obj.get("code");

		Assert.assertTrue(msg.equalsIgnoreCase("Invalid device fan mode."));
	    Assert.assertTrue(code == 99013, "Expected error code 99013. Actual error code is :" + code);
	}
	
	/**
	 * test_set_numbers_fanmode_to_thermostat.
	 */
	@Test(dataProvider = HVAC_SYSTEMS, dataProviderClass = ApiDataProvider.class, priority =17)
	public void test_set_numbers_fanmode_to_thermostat(final String username, final String password,final String thermostatId) {

		final String numfanmode = ApiConfig.JSON_STATE_INVALID_FAN_MODE_NUMBERS;
	    final String response = HTTPClient.getPatchResponse(apiconfig.get(ApiConfig.THERMOSTAT_STATE_URL)
	            .replaceFirst("THERMOSTATID", thermostatId), apiconfig.get(numfanmode));
		setLogString("response :'" + response + "'", true);
	    final JSONObject jsonObject = JsonUtil.parseObject(response);
	    final JSONArray errors = (JSONArray) jsonObject.get("errors");
	    final JSONObject obj = (JSONObject) errors.get(0);;
	    final String msg = (String) obj.get("msg");
	    final Long code = (Long) obj.get("code");

		Assert.assertTrue(msg.equalsIgnoreCase("Invalid device fan mode."));
	    Assert.assertTrue(code == 99013, "Expected error code 99013. Actual error code is :" + code);
	}
	
	/**
	 * test_set_multicomma_fanmode_to_thermostat.
	 */
	@Test(dataProvider = HVAC_SYSTEMS, dataProviderClass = ApiDataProvider.class, priority =18)
	public void test_set_multicomma_fanmode_to_thermostat(final String username, final String password,final String thermostatId) {
	
		final String multicommafanmode = ApiConfig.JSON_STATE_INVALID_FAN_MODE_MULTICOMMASEP;
	    final String response = HTTPClient.getPatchResponse(apiconfig.get(ApiConfig.THERMOSTAT_STATE_URL)
	            .replaceFirst("THERMOSTATID", thermostatId), apiconfig.get(multicommafanmode));
		setLogString("response :'" + response + "'", true);
	    final JSONObject jsonObject = JsonUtil.parseObject(response);
	    final JSONArray errors = (JSONArray) jsonObject.get("errors");
	    final JSONObject obj = (JSONObject) errors.get(0);;
	    final String msg = (String) obj.get("msg");
	    final Long code = (Long) obj.get("code");

		Assert.assertTrue(msg.equalsIgnoreCase("Invalid device fan mode."));
	    Assert.assertTrue(code == 99013, "Expected error code 99013. Actual error code is :" + code);
	}
	
	/**
	 * test_set_multitab_fanmode_to_thermostat.
	 */
	@Test(dataProvider = HVAC_SYSTEMS, dataProviderClass = ApiDataProvider.class, priority =19)
	public void test_set_multitab_fanmode_to_thermostat(final String username, final String password,final String thermostatId) {
		
		final String multitabfanmode = ApiConfig.JSON_STATE_INVALID_FAN_MODE_MULTITABSEP;
	    final String response = HTTPClient.getPatchResponse(apiconfig.get(ApiConfig.THERMOSTAT_STATE_URL)
	            .replaceFirst("THERMOSTATID", thermostatId), apiconfig.get(multitabfanmode));
		setLogString("response :'" + response + "'", true);
	    final JSONObject jsonObject = JsonUtil.parseObject(response);
	    final JSONArray errors = (JSONArray) jsonObject.get("errors");
	    final JSONObject obj = (JSONObject) errors.get(0);;
	    final String msg = (String) obj.get("msg");
	    final Long code = (Long) obj.get("code");

		Assert.assertTrue(msg.equalsIgnoreCase("Invalid device fan mode."));
	    Assert.assertTrue(code == 99013, "Expected error code 99013. Actual error code is :" + code);
	}
	
	/**
	 * test_set_noquotes_fanmode_to_thermostat.
	 */
	@Test(dataProvider = HVAC_SYSTEMS, dataProviderClass = ApiDataProvider.class, priority =20)
	public void test_set_noquotes_fanmode_to_thermostat(final String username, final String password,final String thermostatId) {

		final String noquotesfanmode = ApiConfig.JSON_STATE_INVALID_FAN_MODE_NOQUOTES;
	    final String response = HTTPClient.getPatchResponse(apiconfig.get(ApiConfig.THERMOSTAT_STATE_URL)
	            .replaceFirst("THERMOSTATID", thermostatId), apiconfig.get(noquotesfanmode));
		setLogString("response :'" + response + "'", true);
	    final JSONObject jsonObject = JsonUtil.parseObject(response);
	    final JSONArray errors = (JSONArray) jsonObject.get("errors");
	    final JSONObject obj = (JSONObject) errors.get(0);;
	    final String msg = (String) obj.get("msg");
	    final Long code = (Long) obj.get("code");

	    Assert.assertTrue(msg.equalsIgnoreCase("Request must have at least one thermostat update operation."));
		Assert.assertTrue(code == 14015, "Expected error code 14015" + code);
	}
	
	/**
	 * test_set_noquotesemty_fanmode_to_thermostat.
	 */
	@Test(dataProvider = HVAC_SYSTEMS, dataProviderClass = ApiDataProvider.class, priority =21)
	public void test_set_noquotesempty_fanmode_to_thermostat(final String username, final String password,final String thermostatId) {
	
		final String noquotesemptyfanmode = ApiConfig.JSON_STATE_INVALID_FAN_MODE_NOQUOTESEMPTY;
	    final String response = HTTPClient.getPatchResponse(apiconfig.get(ApiConfig.THERMOSTAT_STATE_URL)
	            .replaceFirst("THERMOSTATID", thermostatId), apiconfig.get(noquotesemptyfanmode));
		setLogString("response :'" + response + "'", true);
	    final JSONObject jsonObject = JsonUtil.parseObject(response);
	    final JSONArray errors = (JSONArray) jsonObject.get("errors");
	    final JSONObject obj = (JSONObject) errors.get(0);;
	    final String msg = (String) obj.get("msg");
	    final Long code = (Long) obj.get("code");

	    Assert.assertTrue(msg.equalsIgnoreCase("Request must have at least one thermostat update operation."));
		Assert.assertTrue(code == 14015, "Expected error code 14015" + code);
	}
	
	/**
	 * test_set_noquotesnull_fanmode_to_thermostat.
	 */
	@Test(dataProvider = HVAC_SYSTEMS, dataProviderClass = ApiDataProvider.class, priority =22)
	public void test_set_noquotesnull_fanmode_to_thermostat(final String username, final String password,final String thermostatId) {
	
		final String noquotesnullfanmode = ApiConfig.JSON_STATE_INVALID_FAN_MODE_NOQUOTESNULL;
	    final String response = HTTPClient.getPatchResponse(apiconfig.get(ApiConfig.THERMOSTAT_STATE_URL)
	            .replaceFirst("THERMOSTATID", thermostatId), apiconfig.get(noquotesnullfanmode));
		setLogString("response :'" + response + "'", true);
	    final JSONObject jsonObject = JsonUtil.parseObject(response);
	    final JSONArray errors = (JSONArray) jsonObject.get("errors");
	    final JSONObject obj = (JSONObject) errors.get(0);;
	    final String msg = (String) obj.get("msg");
	    final Long code = (Long) obj.get("code");

	    Assert.assertTrue(msg.equalsIgnoreCase("Request must have at least one thermostat update operation."));
		Assert.assertTrue(code == 14015, "Expected error code 14015" + code);
	}
	
	/**
	 * test_set_singlequotes_fanmode_to_thermostat.
	 */
	@Test(dataProvider = HVAC_SYSTEMS, dataProviderClass = ApiDataProvider.class, priority =23)
	public void test_set_singlequotes_fanmode_to_thermostat(final String username, final String password,final String thermostatId) {

		final String singlequotesfanmode = ApiConfig.JSON_STATE_INVALID_FAN_MODE_SINGLEQUOTES;
	    final String response = HTTPClient.getPatchResponse(apiconfig.get(ApiConfig.THERMOSTAT_STATE_URL)
	            .replaceFirst("THERMOSTATID", thermostatId), apiconfig.get(singlequotesfanmode));
		setLogString("response :'" + response + "'", true);
	    final JSONObject jsonObject = JsonUtil.parseObject(response);
	    final JSONArray errors = (JSONArray) jsonObject.get("errors");
	    final JSONObject obj = (JSONObject) errors.get(0);;
	    final String msg = (String) obj.get("msg");
	    final Long code = (Long) obj.get("code");

	    Assert.assertTrue(msg.equalsIgnoreCase("Request must have at least one thermostat update operation."));
		Assert.assertTrue(code == 14015, "Expected error code 14015" + code);
	}
	
	/**
	 * test_set_nonexisting_fanmode_to_thermostat.
	 */
	@Test(dataProvider = HVAC_SYSTEMS, dataProviderClass = ApiDataProvider.class, priority =24)
	public void test_set_nonexisting_fanmode_to_thermostat(final String username, final String password,final String thermostatId) {
		
		final String nonexistingfanmode = ApiConfig.JSON_STATE_INVALID_FAN_MODE_NONEXISTING;
	    final String response = HTTPClient.getPatchResponse(apiconfig.get(ApiConfig.THERMOSTAT_STATE_URL)
	            .replaceFirst("THERMOSTATID", thermostatId), apiconfig.get(nonexistingfanmode));
		setLogString("response :'" + response + "'", true);
	    final JSONObject jsonObject = JsonUtil.parseObject(response);
	    final JSONArray errors = (JSONArray) jsonObject.get("errors");
	    final JSONObject obj = (JSONObject) errors.get(0);;
	    final String msg = (String) obj.get("msg");
	    final Long code = (Long) obj.get("code");

		Assert.assertTrue(msg.equalsIgnoreCase("Invalid device fan mode."));
	    Assert.assertTrue(code == 99013, "Expected error code 99013. Actual error code is :" + code);
	}
	
	/**
	 * test_set_uppercase_fanmode_to_thermostat.
	 */
	@Test(dataProvider = HVAC_SYSTEMS, dataProviderClass = ApiDataProvider.class, priority =25)
	public void test_set_uppercase_fanmode_to_thermostat(final String username, final String password,final String thermostatId) {
		
		final String uppercasefanmode = ApiConfig.JSON_STATE_INVALID_FAN_MODE_UPPERCASE;
	    final String response = HTTPClient.getPatchResponse(apiconfig.get(ApiConfig.THERMOSTAT_STATE_URL)
	            .replaceFirst("THERMOSTATID", thermostatId), apiconfig.get(uppercasefanmode));
		setLogString("response :'" + response + "'", true);
	    final JSONObject jsonObject = JsonUtil.parseObject(response);
	    final JSONArray errors = (JSONArray) jsonObject.get("errors");
	    final JSONObject obj = (JSONObject) errors.get(0);;
	    final String msg = (String) obj.get("msg");
	    final Long code = (Long) obj.get("code");

		Assert.assertTrue(msg.equalsIgnoreCase("Invalid device fan mode."));
	    Assert.assertTrue(code == 99013, "Expected error code 99013. Actual error code is :" + code);
	}
	
	/**
	 * test_set_invalid_coolsetpoint_empty_to_thermostat.
	 */
	@Test(dataProvider = HVAC_SYSTEMS, dataProviderClass = ApiDataProvider.class, priority =26)
	public void test_set_invalid_coolsetpoint_empty_to_thermostat(final String username, final String password,final String thermostatId) {
	
		final String emptycoolsetpoint = ApiConfig.JSON_STATE_INVALID_COOLSETPOINT_EMPTY;
	    final String response = HTTPClient.getPatchResponse(apiconfig.get(ApiConfig.THERMOSTAT_STATE_URL)
	            .replaceFirst("THERMOSTATID", thermostatId), apiconfig.get(emptycoolsetpoint));
		setLogString("response :'" + response + "'", true);
	    final JSONObject jsonObject = JsonUtil.parseObject(response);
	    final JSONArray errors = (JSONArray) jsonObject.get("errors");
	    final JSONObject obj = (JSONObject) errors.get(0);;
	    final String msg = (String) obj.get("msg");
	    final Long code = (Long) obj.get("code");

		Assert.assertTrue(msg.equalsIgnoreCase("Request must have at least one thermostat update operation."));
	    Assert.assertTrue(code == 14015, "Expected error code 14015. Actual error code is :" + code);
	}
	
	/**
	 * test_set_invalid_coolsetpoint_zero_to_thermostat.
	 */
	@Test(dataProvider = HVAC_SYSTEMS, dataProviderClass = ApiDataProvider.class, priority =27)
	public void test_set_invalid_coolsetpoint_zero_to_thermostat(final String username, final String password,final String thermostatId) {
		
		final String coolsetpointzero = ApiConfig.JSON_STATE_INVALID_COOLSETPOINT_ZERO;
	    final String response = HTTPClient.getPatchResponse(apiconfig.get(ApiConfig.THERMOSTAT_STATE_URL)
	            .replaceFirst("THERMOSTATID", thermostatId), apiconfig.get(coolsetpointzero));
		setLogString("response :'" + response + "'", true);
	    final JSONObject jsonObject = JsonUtil.parseObject(response);
	    final JSONArray errors = (JSONArray) jsonObject.get("errors");
	    final JSONObject obj = (JSONObject) errors.get(0);;
	    final String msg = (String) obj.get("msg");
	    final Long code = (Long) obj.get("code");

		Assert.assertTrue(msg.equalsIgnoreCase("Cool setpoint out of range."));
	    Assert.assertTrue(code == 15005, "Expected error code 15005. Actual error code is :" + code);
	}
	
	/**
	 * test_set_invalid_coolsetpoint_null_to_thermostat.
	 */
	@Test(dataProvider = HVAC_SYSTEMS, dataProviderClass = ApiDataProvider.class, priority =28)
	public void test_set_invalid_coolsetpoint_null_to_thermostat(final String username, final String password,final String thermostatId) {
		
		final String nullcoolsetpoint = ApiConfig.JSON_STATE_INVALID_COOLSETPOINT_NULL;
	    final String response = HTTPClient.getPatchResponse(apiconfig.get(ApiConfig.THERMOSTAT_STATE_URL)
	            .replaceFirst("THERMOSTATID", thermostatId), apiconfig.get(nullcoolsetpoint));
		setLogString("response :'" + response + "'", true);
	    final JSONObject jsonObject = JsonUtil.parseObject(response);
	    final JSONArray errors = (JSONArray) jsonObject.get("errors");
	    final JSONObject obj = (JSONObject) errors.get(0);;
	    final String msg = (String) obj.get("msg");
	    final Long code = (Long) obj.get("code");

		Assert.assertTrue(msg.equalsIgnoreCase("Cool setpoint out of range."));
	    Assert.assertTrue(code == 15005, "Expected error code 15005. Actual error code is :" + code);
	}
	
	/**
	 * test_set_invalid_coolsetpoint_boundarylow_to_thermostat.
	 */
	@Test(dataProvider = HVAC_SYSTEMS, dataProviderClass = ApiDataProvider.class, priority =29)
	public void test_set_invalid_coolsetpoint_boundarylow_to_thermostat(final String username, final String password,final String thermostatId) {
	
		final String coolsetpointboundarylow = ApiConfig.JSON_STATE_INVALID_COOLSETPOINT_BOUNDARYLOW;
	    final String response = HTTPClient.getPatchResponse(apiconfig.get(ApiConfig.THERMOSTAT_STATE_URL)
	            .replaceFirst("THERMOSTATID", thermostatId), apiconfig.get(coolsetpointboundarylow));
		setLogString("response :'" + response + "'", true);
	    final JSONObject jsonObject = JsonUtil.parseObject(response);
	    final JSONArray errors = (JSONArray) jsonObject.get("errors");
	    final JSONObject obj = (JSONObject) errors.get(0);;
	    final String msg = (String) obj.get("msg");
	    final Long code = (Long) obj.get("code");

		Assert.assertTrue(msg.equalsIgnoreCase("Cool setpoint out of range."));
	    Assert.assertTrue(code == 15005, "Expected error code 15005. Actual error code is :" + code);
	}
	
	/**
	 * test_set_invalid_coolsetpoint_boundaryhigh_to_thermostat.
	 */
	@Test(dataProvider = HVAC_SYSTEMS, dataProviderClass = ApiDataProvider.class, priority =30)
	public void test_set_invalid_coolsetpoint_boundaryhigh_to_thermostat(final String username, final String password,final String thermostatId) {
	
		final String coolsetpointboundaryhigh = ApiConfig.JSON_STATE_INVALID_COOLSETPOINT_BOUNDARYHIGH;
	    final String response = HTTPClient.getPatchResponse(apiconfig.get(ApiConfig.THERMOSTAT_STATE_URL)
	            .replaceFirst("THERMOSTATID", thermostatId), apiconfig.get(coolsetpointboundaryhigh));
		setLogString("response :'" + response + "'", true);
	    final JSONObject jsonObject = JsonUtil.parseObject(response);
	    final JSONArray errors = (JSONArray) jsonObject.get("errors");
	    final JSONObject obj = (JSONObject) errors.get(0);;
	    final String msg = (String) obj.get("msg");
	    final Long code = (Long) obj.get("code");

		Assert.assertTrue(msg.equalsIgnoreCase("Cool setpoint out of range."));
	    Assert.assertTrue(code == 15005, "Expected error code 15005. Actual error code is :" + code);
	}
	
	/**
	 * test_set_invalid_coolsetpoint_decimalcomma_to_thermostat.
	 */
	@Test(dataProvider = HVAC_SYSTEMS, dataProviderClass = ApiDataProvider.class, priority =31)
	public void test_set_invalid_coolsetpoint_decimalcomma_to_thermostat(final String username, final String password,final String thermostatId) {

		final String decimalcommacoolsetpoint = ApiConfig.JSON_STATE_INVALID_COOLSETPOINT_DECIMALCOMMA;
	    final String response = HTTPClient.getPatchResponse(apiconfig.get(ApiConfig.THERMOSTAT_STATE_URL)
	            .replaceFirst("THERMOSTATID", thermostatId), apiconfig.get(decimalcommacoolsetpoint));
		setLogString("response :'" + response + "'", true);
	    final JSONObject jsonObject = JsonUtil.parseObject(response);
	    final JSONArray errors = (JSONArray) jsonObject.get("errors");
	    final JSONObject obj = (JSONObject) errors.get(0);;
	    final String msg = (String) obj.get("msg");
	    final Long code = (Long) obj.get("code");

		Assert.assertTrue(msg.equalsIgnoreCase("Request must have at least one thermostat update operation."));
	    Assert.assertTrue(code == 14015, "Expected error code 14015. Actual error code is :" + code);
	}
	
	/**
	 * test_set_invalid_coolsetpoint_decimaldot_to_thermostat.
	 */
	@Test(dataProvider = HVAC_SYSTEMS, dataProviderClass = ApiDataProvider.class, priority =32)
	public void test_set_invalid_coolsetpoint_decimaldot_to_thermostat(final String username, final String password,final String thermostatId) {
		
		final String decimaldotcoolsetpoint = ApiConfig.JSON_STATE_INVALID_COOLSETPOINT_DECIMALDOT;
	    final String response = HTTPClient.getPatchResponse(apiconfig.get(ApiConfig.THERMOSTAT_STATE_URL)
	            .replaceFirst("THERMOSTATID", thermostatId), apiconfig.get(decimaldotcoolsetpoint));
		setLogString("response :'" + response + "'", true);
	    final JSONObject jsonObject = JsonUtil.parseObject(response);
	    final JSONArray errors = (JSONArray) jsonObject.get("errors");
	    final JSONObject obj = (JSONObject) errors.get(0);;
	    final String msg = (String) obj.get("msg");
	    final Long code = (Long) obj.get("code");

		Assert.assertTrue(msg.equalsIgnoreCase("Cool setpoint out of range."));
	    Assert.assertTrue(code == 15005, "Expected error code 15005. Actual error code is :" + code);
	}
	
	/**
	 * test_set_invalid_coolsetpoint_doublequotes_to_thermostat.
	 */
	@Test(dataProvider = HVAC_SYSTEMS, dataProviderClass = ApiDataProvider.class, priority =33)
	public void test_set_invalid_coolsetpoint_doublequotes_to_thermostat(final String username, final String password,final String thermostatId) {

		final String doublequotescoolsetpoint = ApiConfig.JSON_STATE_INVALID_COOLSETPOINT_DOUBLEQUOTES;
	    final String response = HTTPClient.getPatchResponse(apiconfig.get(ApiConfig.THERMOSTAT_STATE_URL)
	            .replaceFirst("THERMOSTATID", thermostatId), apiconfig.get(doublequotescoolsetpoint));
		setLogString("response :'" + response + "'", true);
	    final JSONObject jsonObject = JsonUtil.parseObject(response);
	    final JSONArray errors = (JSONArray) jsonObject.get("errors");
	    final JSONObject obj = (JSONObject) errors.get(0);;
	    final String msg = (String) obj.get("msg");
	    final Long code = (Long) obj.get("code");

		Assert.assertTrue(msg.equalsIgnoreCase("Cool setpoint out of range."));
	    Assert.assertTrue(code == 15005, "Expected error code 15005. Actual error code is :" + code);
	}
	
	/**
	 * test_set_invalid_coolsetpoint_singlequotes_to_thermostat.
	 */
	@Test(dataProvider = HVAC_SYSTEMS, dataProviderClass = ApiDataProvider.class, priority =34)
	public void test_set_invalid_coolsetpoint_singlequotes_to_thermostat(final String username, final String password,final String thermostatId) {
		
		final String singleqoutescoolsetpoint = ApiConfig.JSON_STATE_INVALID_COOLSETPOINT_SINGLEQUOTES;
	    final String response = HTTPClient.getPatchResponse(apiconfig.get(ApiConfig.THERMOSTAT_STATE_URL)
	            .replaceFirst("THERMOSTATID", thermostatId), apiconfig.get(singleqoutescoolsetpoint));
		setLogString("response :'" + response + "'", true);
	    final JSONObject jsonObject = JsonUtil.parseObject(response);
	    final JSONArray errors = (JSONArray) jsonObject.get("errors");
	    final JSONObject obj = (JSONObject) errors.get(0);;
	    final String msg = (String) obj.get("msg");
	    final Long code = (Long) obj.get("code");

		Assert.assertTrue(msg.equalsIgnoreCase("Request must have at least one thermostat update operation."));
	    Assert.assertTrue(code == 14015, "Expected error code 14015. Actual error code is :" + code);
	}
	
	/**
	 * test_set_heat_hvac_mode_to_thermostat.
	 */
	
	@Test(dataProvider = HVAC_SYSTEMS, dataProviderClass = ApiDataProvider.class, priority =35)
	public void test_set_heat_hvac_mode_to_thermostat(final String username, final String password,final String thermostatId) {
		
		final String setheatmode = ApiConfig.JSON_STATE_VALID_HVAC_MODE_HEAT;
	    final String response = HTTPClient.getPatchResponse(apiconfig.get(ApiConfig.THERMOSTAT_STATE_URL)
	            .replaceFirst("THERMOSTATID", thermostatId), apiconfig.get(setheatmode));
		setLogString("response :'" + response + "'", true);
//	    final JSONObject jsonObject = JsonUtil.parseObject(response);
//	    final JSONArray errors = (JSONArray) jsonObject.get("errors");
//	    final JSONObject obj = (JSONObject) errors.get(0);;
//	    final String msg = (String) obj.get("msg");
//	    final Long code = (Long) obj.get("code");
//
//		final String Response = HttpGet.METHOD_NAME;
//		
//	    
//	    System.out.println(Response);
	
	}
	/**
	 * test_set_invalid_heatsetpoint_empty_to_thermostat.
	 */
	@Test(dataProvider = HVAC_SYSTEMS, dataProviderClass = ApiDataProvider.class, priority =36)
	public void test_set_invalid_heatsetpoint_empty_to_thermostat(final String username, final String password,final String thermostatId) {

		final String emptyheatsetpoint = ApiConfig.JSON_STATE_INVALID_HEATSETPOINT_EMPTY;
	    final String response = HTTPClient.getPatchResponse(apiconfig.get(ApiConfig.THERMOSTAT_STATE_URL)
	            .replaceFirst("THERMOSTATID", thermostatId), apiconfig.get(emptyheatsetpoint));
		setLogString("response :'" + response + "'", true);
	    final JSONObject jsonObject = JsonUtil.parseObject(response);
	    final JSONArray errors = (JSONArray) jsonObject.get("errors");
	    final JSONObject obj = (JSONObject) errors.get(0);;
	    final String msg = (String) obj.get("msg");
	    final Long code = (Long) obj.get("code");

		Assert.assertTrue(msg.equalsIgnoreCase("Request must have at least one thermostat update operation."));
	    Assert.assertTrue(code == 14015, "Expected error code 14015. Actual error code is :" + code);
	}
	
	/**
	 * test_set_invalid_heatsetpoint_zero_to_thermostat.
	 */
	@Test(dataProvider = HVAC_SYSTEMS, dataProviderClass = ApiDataProvider.class, priority =37)
	public void test_set_invalid_heatsetpoint_zero_to_thermostat(final String username, final String password,final String thermostatId) {
	
		final String heatsetpointzero = ApiConfig.JSON_STATE_INVALID_HEATSETPOINT_ZERO;
	    final String response = HTTPClient.getPatchResponse(apiconfig.get(ApiConfig.THERMOSTAT_STATE_URL)
	            .replaceFirst("THERMOSTATID", thermostatId), apiconfig.get(heatsetpointzero));
		setLogString("response :'" + response + "'", true);
	    final JSONObject jsonObject = JsonUtil.parseObject(response);
	    final JSONArray errors = (JSONArray) jsonObject.get("errors");
	    final JSONObject obj = (JSONObject) errors.get(0);;
	    final String msg = (String) obj.get("msg");
	    final Long code = (Long) obj.get("code");

		Assert.assertTrue(msg.equalsIgnoreCase("Heat setpoint out of range."));
	    Assert.assertTrue(code == 15004, "Expected error code 15004. Actual error code is :" + code);
	}
	
	/**
	 * test_set_invalid_heatsetpoint_null_to_thermostat.
	 */
	@Test(dataProvider = HVAC_SYSTEMS, dataProviderClass = ApiDataProvider.class, priority =38)
	public void test_set_invalid_heatsetpoint_null_to_thermostat(final String username, final String password,final String thermostatId) {

		final String heatsetpointnull = ApiConfig.JSON_STATE_INVALID_HEATSETPOINT_NULL;
	    final String response = HTTPClient.getPatchResponse(apiconfig.get(ApiConfig.THERMOSTAT_STATE_URL)
	            .replaceFirst("THERMOSTATID", thermostatId), apiconfig.get(heatsetpointnull));
		setLogString("response :'" + response + "'", true);
	    final JSONObject jsonObject = JsonUtil.parseObject(response);
	    final JSONArray errors = (JSONArray) jsonObject.get("errors");
	    final JSONObject obj = (JSONObject) errors.get(0);;
	    final String msg = (String) obj.get("msg");
	    final Long code = (Long) obj.get("code");

		Assert.assertTrue(msg.equalsIgnoreCase("Heat setpoint out of range."));
	    Assert.assertTrue(code == 15004, "Expected error code 15004. Actual error code is :" + code);
	}
	
	/**
	 * test_set_invalid_heatsetpoint_boundarylow_to_thermostat.
	 */
	@Test(dataProvider = HVAC_SYSTEMS, dataProviderClass = ApiDataProvider.class, priority =39)
	public void test_set_invalid_heatsetpoint_boundarylow_to_thermostat(final String username, final String password,final String thermostatId) {
	
		final String heatsetpointboundarylow = ApiConfig.JSON_STATE_INVALID_HEATSETPOINT_BOUNDARYLOW;
	    final String response = HTTPClient.getPatchResponse(apiconfig.get(ApiConfig.THERMOSTAT_STATE_URL)
	            .replaceFirst("THERMOSTATID", thermostatId), apiconfig.get(heatsetpointboundarylow));
		setLogString("response :'" + response + "'", true);
	    final JSONObject jsonObject = JsonUtil.parseObject(response);
	    final JSONArray errors = (JSONArray) jsonObject.get("errors");
	    final JSONObject obj = (JSONObject) errors.get(0);;
	    final String msg = (String) obj.get("msg");
	    final Long code = (Long) obj.get("code");

		Assert.assertTrue(msg.equalsIgnoreCase("Heat setpoint out of range."));
	    Assert.assertTrue(code == 15004, "Expected error code 15004. Actual error code is :" + code);
	}
	
	/**
	 * test_set_invalid_heatsetpoint_boundaryhigh_to_thermostat.
	 */
	@Test(dataProvider = HVAC_SYSTEMS, dataProviderClass = ApiDataProvider.class, priority =40)
	public void test_set_invalid_heatsetpoint_boundaryhigh_to_thermostat(final String username, final String password,final String thermostatId) {
		
		final String heatsetpointboundaryhigh = ApiConfig.JSON_STATE_INVALID_HEATSETPOINT_BOUNDARYHIGH;
	    final String response = HTTPClient.getPatchResponse(apiconfig.get(ApiConfig.THERMOSTAT_STATE_URL)
	            .replaceFirst("THERMOSTATID", thermostatId), apiconfig.get(heatsetpointboundaryhigh));
		setLogString("response :'" + response + "'", true);
	    final JSONObject jsonObject = JsonUtil.parseObject(response);
	    final JSONArray errors = (JSONArray) jsonObject.get("errors");
	    final JSONObject obj = (JSONObject) errors.get(0);;
	    final String msg = (String) obj.get("msg");
	    final Long code = (Long) obj.get("code");

		Assert.assertTrue(msg.equalsIgnoreCase("Heat setpoint out of range."));
	    Assert.assertTrue(code == 15004, "Expected error code 15004. Actual error code is :" + code);
	}
	
	/**
	 * test_set_invalid_heatsetpoint_decimalcomma_to_thermostat.
	 */
	@Test(dataProvider = HVAC_SYSTEMS, dataProviderClass = ApiDataProvider.class, priority =41)
	public void test_set_invalid_heatsetpoint_decimalcomma_to_thermostat(final String username, final String password,final String thermostatId) {
	
		final String heatsetpointdecimalcomma = ApiConfig.JSON_STATE_INVALID_HEATSETPOINT_DECIMALCOMMA;
	    final String response = HTTPClient.getPatchResponse(apiconfig.get(ApiConfig.THERMOSTAT_STATE_URL)
	            .replaceFirst("THERMOSTATID", thermostatId), apiconfig.get(heatsetpointdecimalcomma));
		setLogString("response :'" + response + "'", true);
	    final JSONObject jsonObject = JsonUtil.parseObject(response);
	    final JSONArray errors = (JSONArray) jsonObject.get("errors");
	    final JSONObject obj = (JSONObject) errors.get(0);;
	    final String msg = (String) obj.get("msg");
	    final Long code = (Long) obj.get("code");

		Assert.assertTrue(msg.equalsIgnoreCase("Request must have at least one thermostat update operation."));
	    Assert.assertTrue(code == 14015, "Expected error code 14015. Actual error code is :" + code);
	}
	
	/**
	 * test_set_invalid_heatsetpoint_decimaldot_to_thermostat.
	 */
	@Test(dataProvider = HVAC_SYSTEMS, dataProviderClass = ApiDataProvider.class, priority =42)
	public void test_set_invalid_heatsetpoint_decimaldot_to_thermostat(final String username, final String password,final String thermostatId) {
	
		final String heatsetpointdecimaldot = ApiConfig.JSON_STATE_INVALID_HEATSETPOINT_DECIMALDOT;
	    final String response = HTTPClient.getPatchResponse(apiconfig.get(ApiConfig.THERMOSTAT_STATE_URL)
	            .replaceFirst("THERMOSTATID", thermostatId), apiconfig.get(heatsetpointdecimaldot));
		setLogString("response :'" + response + "'", true);
	    final JSONObject jsonObject = JsonUtil.parseObject(response);
	    final JSONArray errors = (JSONArray) jsonObject.get("errors");
	    final JSONObject obj = (JSONObject) errors.get(0);;
	    final String msg = (String) obj.get("msg");
	    final Long code = (Long) obj.get("code");

		Assert.assertTrue(msg.equalsIgnoreCase("Heat setpoint out of range."));
	    Assert.assertTrue(code == 15005, "Expected error code 15005. Actual error code is :" + code);
	}
	
	/**
	 * test_set_invalid_heatsetpoint_doublequotes_to_thermostat.
	 */
	@Test(dataProvider = HVAC_SYSTEMS, dataProviderClass = ApiDataProvider.class, priority =43)
	public void test_set_invalid_heatsetpoint_doublequotes_to_thermostat(final String username, final String password,final String thermostatId) {

		final String heatsetpointdoublequotes = ApiConfig.JSON_STATE_INVALID_HEATSETPOINT_DOUBLEQUOTES;
	    final String response = HTTPClient.getPatchResponse(apiconfig.get(ApiConfig.THERMOSTAT_STATE_URL)
	            .replaceFirst("THERMOSTATID", thermostatId), apiconfig.get(heatsetpointdoublequotes));
		setLogString("response :'" + response + "'", true);
	    final JSONObject jsonObject = JsonUtil.parseObject(response);
	    final JSONArray errors = (JSONArray) jsonObject.get("errors");
	    final JSONObject obj = (JSONObject) errors.get(0);;
	    final String msg = (String) obj.get("msg");
	    final Long code = (Long) obj.get("code");

		Assert.assertTrue(msg.equalsIgnoreCase("Heat setpoint out of range."));
	    Assert.assertTrue(code == 15005, "Expected error code 15005. Actual error code is :" + code);
	}
	
	/**
	 * test_set_invalid_heatsetpoint_singlequotes_to_thermostat.
	 */
	@Test(dataProvider = HVAC_SYSTEMS, dataProviderClass = ApiDataProvider.class, priority =44)
	public void test_set_invalid_heatsetpoint_singlequotes_to_thermostat(final String username, final String password,final String thermostatId) {
		
		final String heatsetpointsinglequotes = ApiConfig.JSON_STATE_INVALID_HEATSETPOINT_SINGLEQUOTES;
	    final String response = HTTPClient.getPatchResponse(apiconfig.get(ApiConfig.THERMOSTAT_STATE_URL)
	            .replaceFirst("THERMOSTATID", thermostatId), apiconfig.get(heatsetpointsinglequotes));
		setLogString("response :'" + response + "'", true);
	    final JSONObject jsonObject = JsonUtil.parseObject(response);
	    final JSONArray errors = (JSONArray) jsonObject.get("errors");
	    final JSONObject obj = (JSONObject) errors.get(0);;
	    final String msg = (String) obj.get("msg");
	    final Long code = (Long) obj.get("code");

		Assert.assertTrue(msg.equalsIgnoreCase("Request must have at least one thermostat update operation."));
	    Assert.assertTrue(code == 14015, "Expected error code 14015. Actual error code is :" + code);
	}

}
