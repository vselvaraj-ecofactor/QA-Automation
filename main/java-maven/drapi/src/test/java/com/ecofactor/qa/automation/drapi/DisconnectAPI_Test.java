package com.ecofactor.qa.automation.drapi;

import static com.ecofactor.qa.automation.platform.util.LogUtil.*;

import java.lang.reflect.Method;

import org.apache.http.HttpResponse;
import org.testng.Assert;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import com.ecofactor.qa.automation.drapi.data.DRAPIDataProvider;

import com.ecofactor.qa.automation.drapi.HTTPSClient;
import com.ecofactor.qa.automation.dao.DaoModule;
import com.ecofactor.qa.automation.platform.constants.Groups;

import com.ecofactor.qa.automation.util.UtilModule;

/**
 * The Class DRAPI_Test.
 * 
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
@Guice(modules = { UtilModule.class, DaoModule.class, DRApiModule.class })
public class DisconnectAPI_Test extends AbstractTest {

	@BeforeMethod(alwaysRun = true)
	public void beforeMethod(final Method method, final Object[] param) {

		logUtil.logStart(method, param, null);
		startTime = System.currentTimeMillis();
	}

	/**
	 * Test_get_disconnected_gateway_List_NVE
	 */

	@Test(groups = { Groups.SANITY1 }, dataProvider = "disconnectGatewayListNVE", dataProviderClass = DRAPIDataProvider.class, priority = 1)
	public void test_get_disconnected_gateway_list_NVE(
			final String disconnectUrl, final String programID) {

		String url = disconnectUrl;
		url = url.replaceFirst("<program_id>", programID);
		System.out.println(url);
		final HttpResponse response = HTTPSClient.getResponse(url, HTTPSClient
				.getPKCSKeyHttpClient("ecofactorqanve.p12", "ecofactor"));
		Assert.assertTrue(response.getStatusLine().getStatusCode() == 200,
				"Error status: " + response.getStatusLine());
		final String result = HTTPSClient.getResultString(response.getEntity());
		setLogString("response :'" + result + "'", true);

	}

	/**
	 * Test_get_disconnected_gateway_List_CMCSA
	 */

	@Test(groups = { Groups.SANITY1 }, dataProvider = "disconnectGatewayListComcast", dataProviderClass = DRAPIDataProvider.class, priority = 1)
	public void test_get_disconnected_gateway_list_CMCSA(
			final String disconnectUrl, final String programID) {

		String url = disconnectUrl;
		url = url.replaceFirst("<program_id>", programID);
		System.out.println(url);
		final HttpResponse response = HTTPSClient.getResponse(url,
				HTTPSClient.getPKCSKeyHttpClient("Comcast.p12", "ecofactor"));
		Assert.assertTrue(response.getStatusLine().getStatusCode() == 200,
				"Error status: " + response.getStatusLine());
		final String result = HTTPSClient.getResultString(response.getEntity());
		setLogString("response :'" + result + "'", true);

	}

	/**
	 * Test_get_disconnected_gateway_List_ECO
	 */

	@Test(groups = { Groups.SANITY1 }, dataProvider = "disconnectGatewayListEco", dataProviderClass = DRAPIDataProvider.class, priority = 1)
	public void test_get_disconnected_gateway_list_ECO(
			final String disconnectUrl, final String programID) {

		String url = disconnectUrl;
		url = url.replaceFirst("<program_id>", programID);
		System.out.println(url);
		final HttpResponse response = HTTPSClient.getResponse(url, HTTPSClient
				.getPKCSKeyHttpClient("ecofactorcorp.p12", "ecofactor"));
		Assert.assertTrue(response.getStatusLine().getStatusCode() == 200,
				"Error status: " + response.getStatusLine());
		final String result = HTTPSClient.getResultString(response.getEntity());
		setLogString("response :'" + result + "'", true);

	}

	/**
	 * Test_get_disconnected_gateway_List_Default
	 */

	@Test(groups = { Groups.SANITY1 }, dataProvider = "disconnectGatewayListDefault", dataProviderClass = DRAPIDataProvider.class, priority = 1)
	public void test_get_disconnected_gateway_list_Default(
			final String disconnectUrl, final String programID) {

		String url = disconnectUrl;
		url = url.replaceFirst("<program_id>", programID);
		System.out.println(url);
		final HttpResponse response = HTTPSClient.getResponse(url, HTTPSClient
				.getPKCSKeyHttpClient("nopartnercode.p12", "ecofactor"));
		Assert.assertTrue(response.getStatusLine().getStatusCode() == 200,
				"Error status: " + response.getStatusLine());
		final String result = HTTPSClient.getResultString(response.getEntity());
		setLogString("response :'" + result + "'", true);

	}

	/**
	 * Test_get_disconnected_gateway_Blank_Program
	 */

	@Test(groups = { Groups.SANITY1 }, dataProvider = "disconnectGatewayBlankProgram", dataProviderClass = DRAPIDataProvider.class, priority = 1)
	public void test_get_disconnected_gateway_Empty_Program(
			final String disconnectUrl, final String programID) {

		String url = disconnectUrl;
		url = url.replaceFirst("<program_id>", programID);
		System.out.println(url);
		final HttpResponse response = HTTPSClient.getResponse(url, HTTPSClient
				.getPKCSKeyHttpClient("nopartnercode.p12", "ecofactor"));
		Assert.assertTrue(response.getStatusLine().getStatusCode() == 200,
				"Error status: " + response.getStatusLine());
		final String result = HTTPSClient.getResultString(response.getEntity());
		setLogString("response :'" + result + "'", true);

	}

	/**
	 * Test_get_disconnected_gateway_Junk_Program
	 */

	@Test(groups = { Groups.SANITY1 }, dataProvider = "disconnectGatewayJunkProgram", dataProviderClass = DRAPIDataProvider.class, priority = 1)
	public void test_get_disconnected_gateway_Junk_Program(
			final String disconnectUrl, final String programID) {

		String url = disconnectUrl;
		url = url.replaceFirst("<program_id>", programID);
		System.out.println(url);
		final HttpResponse response = HTTPSClient.getResponse(url, HTTPSClient
				.getPKCSKeyHttpClient("nopartnercode.p12", "ecofactor"));
		Assert.assertTrue(response.getStatusLine().getStatusCode() == 500,
				"Error status: " + response.getStatusLine());
		final String result = HTTPSClient.getResultString(response.getEntity());
		setLogString("response :'" + result + "'", true);

	}

	/**
	 * Test_get_disconnected_gateway_Parameter_Testing_Size_Blank
	 */

	@Test(groups = { Groups.SANITY1 }, priority = 1)
	public void test_get_disconnected_gateway_Parameter_Testing_Size_Blank() {

		String url = "https://qa-plat-tc1.ecofactor.com:8454/services/ws/v1.0/gateways?status=disconnected&ls_program=6&start=0&size=";
		System.out.println(url);
		final HttpResponse response = HTTPSClient.getResponse(url, HTTPSClient
				.getPKCSKeyHttpClient("ecofactorcorp.p12", "ecofactor"));
		Assert.assertTrue(response.getStatusLine().getStatusCode() == 500,
				"Error status: " + response.getStatusLine());
		final String result = HTTPSClient.getResultString(response.getEntity());
		setLogString("response :'" + result + "'", true);

	}

	/**
	 * Test_get_disconnected_gateway_Parameter_Testing_Size_Negative_One
	 */

	@Test(groups = { Groups.SANITY1 }, priority = 1)
	public void test_get_disconnected_gateway_Parameter_Testing_Size_Negative_One() {

		String url = "https://qa-plat-tc1.ecofactor.com:8454/services/ws/v1.0/gateways?status=disconnected&ls_program=6&start=0&size=-1";
		System.out.println(url);
		final HttpResponse response = HTTPSClient.getResponse(url, HTTPSClient
				.getPKCSKeyHttpClient("ecofactorcorp.p12", "ecofactor"));
		Assert.assertTrue(response.getStatusLine().getStatusCode() == 500,
				"Error status: " + response.getStatusLine());
		final String result = HTTPSClient.getResultString(response.getEntity());
		setLogString("response :'" + result + "'", true);

	}

	/**
	 * Test_get_disconnected_gateway_Parameter_Testing_Size_String
	 */

	@Test(groups = { Groups.SANITY1 }, priority = 1)
	public void test_get_disconnected_gateway_Parameter_Testing_Size_String() {

		String url = "https://qa-plat-tc1.ecofactor.com:8454/services/ws/v1.0/gateways?status=disconnected&ls_program=6&start=0&size=-1";
		System.out.println(url);
		final HttpResponse response = HTTPSClient.getResponse(url, HTTPSClient
				.getPKCSKeyHttpClient("ecofactorcorp.p12", "ecofactor"));
		Assert.assertTrue(response.getStatusLine().getStatusCode() == 500,
				"Error status: " + response.getStatusLine());
		final String result = HTTPSClient.getResultString(response.getEntity());
		setLogString("response :'" + result + "'", true);

	}

	/**
	 * Test_get_disconnected_gateway_Parameter_Testing_Start_Blank
	 */

	@Test(groups = { Groups.SANITY1 }, priority = 1)
	public void test_get_disconnected_gateway_Parameter_Testing_Start_Blank() {

		String url = "https://qa-plat-tc1.ecofactor.com:8454/services/ws/v1.0/gateways?status=disconnected&ls_program=6&start=&size=5";
		System.out.println(url);
		final HttpResponse response = HTTPSClient.getResponse(url, HTTPSClient
				.getPKCSKeyHttpClient("ecofactorcorp.p12", "ecofactor"));
		Assert.assertTrue(response.getStatusLine().getStatusCode() == 500,
				"Error status: " + response.getStatusLine());
		final String result = HTTPSClient.getResultString(response.getEntity());
		setLogString("response :'" + result + "'", true);

	}

	/**
	 * Test_get_disconnected_gateway_Parameter_Testing_Start_Negative_One
	 */

	@Test(groups = { Groups.SANITY1 }, priority = 1)
	public void test_get_disconnected_gateway_Parameter_Testing_Start_Negative_One() {

		String url = "https://qa-plat-tc1.ecofactor.com:8454/services/ws/v1.0/gateways?status=disconnected&ls_program=6&start=-1&size=5";
		System.out.println(url);
		final HttpResponse response = HTTPSClient.getResponse(url, HTTPSClient
				.getPKCSKeyHttpClient("ecofactorcorp.p12", "ecofactor"));
		Assert.assertTrue(response.getStatusLine().getStatusCode() == 500,
				"Error status: " + response.getStatusLine());
		final String result = HTTPSClient.getResultString(response.getEntity());
		setLogString("response :'" + result + "'", true);

	}

	/**
	 * Test_get_disconnected_gateway_Parameter_Testing_Start_String
	 */

	@Test(groups = { Groups.SANITY1 }, priority = 1)
	public void test_get_disconnected_gateway_Parameter_Testing_Start_String() {

		String url = "https://qa-plat-tc1.ecofactor.com:8454/services/ws/v1.0/gateways?status=disconnected&ls_program=6&start=abc&size=5";
		System.out.println(url);
		final HttpResponse response = HTTPSClient.getResponse(url, HTTPSClient
				.getPKCSKeyHttpClient("ecofactorcorp.p12", "ecofactor"));
		Assert.assertTrue(response.getStatusLine().getStatusCode() == 500,
				"Error status: " + response.getStatusLine());
		final String result = HTTPSClient.getResultString(response.getEntity());
		setLogString("response :'" + result + "'", true);

	}

}
