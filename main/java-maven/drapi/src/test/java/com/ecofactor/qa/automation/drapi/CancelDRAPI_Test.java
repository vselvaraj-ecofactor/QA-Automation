package com.ecofactor.qa.automation.drapi;
import static com.ecofactor.qa.automation.platform.util.LogUtil.*;

import java.util.Calendar;

import org.apache.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import com.ecofactor.qa.automation.drapi.data.DRAPIDataProvider;
import com.ecofactor.qa.automation.drapi.DRApiConfig;
import com.ecofactor.qa.automation.drapi.DRApiModule;
import com.ecofactor.qa.automation.drapi.HTTPSClient;
import com.ecofactor.qa.automation.dao.DaoModule;
import com.ecofactor.qa.automation.platform.constants.Groups;
import com.ecofactor.qa.automation.util.DateUtil;
import com.ecofactor.qa.automation.util.UtilModule;
import com.google.inject.Inject;

/**
 * The Class DRAPI_Test.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
@Guice(modules = { UtilModule.class, DaoModule.class, DRApiModule.class })
public class CancelDRAPI_Test extends AbstractTest {
    
    @Inject
    private static DRApiConfig apiConfig;
    
    @Test(groups = { Groups.SANITY1 }, dataProvider = "cancelDREventNVE", dataProviderClass = DRAPIDataProvider.class, priority = 1)
    public void drCancelEventForNVE(final String drUrl, final String programID, final String eventID,
            final String targetType, final String targetALLJson, final String cancel_url,final String cancel_dr_json) throws JSONException 
            {
        
        long timeStamp = System.currentTimeMillis();
        String createUrl = drUrl;

        createUrl = createUrl.replaceFirst("<program_id>", programID)
                .replaceFirst("<event_id>", eventID + timeStamp)
                .replaceFirst("<target_type>", targetType).replaceFirst("<target_all>", "true");

        String json = commonJson(createUrl, targetALLJson);

        final HttpResponse response = HTTPSClient.postResponse(createUrl, json,
                HTTPSClient.getPKCSKeyHttpClient("ecofactorqanve.p12", "ecofactor"));
        
        final String result = HTTPSClient.getResultString(response.getEntity());
        System.out.println(result);
        JSONObject jsonObj = new JSONObject(result);
        String eventId = (String) jsonObj.get("event_id");
        System.out.println(eventId);
        
        String cancelURL = cancel_url;
        
        cancelURL = cancelURL.replaceFirst("<program_id>", programID).replaceFirst("<PARTNER_EVENT_ID>", eventId);
        System.out.println(cancelURL);
        String cancel_json = cancel_dr_json;
        System.out.println(cancel_json);
        
        final HttpResponse cancel_response = HTTPSClient.putResponse(cancelURL, cancel_json,
                HTTPSClient.getPKCSKeyHttpClient("ecofactorqanve.p12", "ecofactor"));
        Assert.assertTrue(cancel_response.getStatusLine().getStatusCode() == 200, "Error Status:"
                + cancel_response.getStatusLine());
        
        
    }
    
    @Test(groups = { Groups.SANITY1 }, dataProvider = "cancelDREventComCAST", dataProviderClass = DRAPIDataProvider.class, priority = 1)
    public void drCancelEventForComcast(final String drUrl, final String programID, final String eventID,
            final String targetType, final String targetALLJson, final String cancel_url,final String cancel_dr_json) throws JSONException 
            {
        
        long timeStamp = System.currentTimeMillis();
        String createUrl = drUrl;

        createUrl = createUrl.replaceFirst("<program_id>", programID)
                .replaceFirst("<event_id>", eventID + timeStamp)
                .replaceFirst("<target_type>", targetType).replaceFirst("<target_all>", "true");

        String json = commonJson(createUrl, targetALLJson);

        final HttpResponse response = HTTPSClient.postResponse(createUrl, json,
                HTTPSClient.getPKCSKeyHttpClient("Comcast.p12", "ecofactor"));
        
        final String result = HTTPSClient.getResultString(response.getEntity());
        System.out.println(result);
        JSONObject jsonObj = new JSONObject(result);
        String eventId = (String) jsonObj.get("event_id");
        System.out.println(eventId);
        
        String cancelURL = cancel_url;
        
        cancelURL = cancelURL.replaceFirst("<program_id>", programID).replaceFirst("<PARTNER_EVENT_ID>", eventId);
        System.out.println(cancelURL);
        String cancel_json = cancel_dr_json;
        System.out.println(cancel_json);
        
        final HttpResponse cancel_response = HTTPSClient.putResponse(cancelURL, cancel_json,
                HTTPSClient.getPKCSKeyHttpClient("Comcast.p12", "ecofactor"));
        Assert.assertTrue(cancel_response.getStatusLine().getStatusCode() == 200, "Error Status:"
                + cancel_response.getStatusLine());
        
        
        
    }
    
    /*@Test(groups = { Groups.SANITY1 }, dataProvider = "cancelDREventECO", dataProviderClass = DRAPIDataProvider.class, priority = 1)
    public void drCancelEventForECO(final String drUrl, final String programID, final String eventID,
            final String targetType, final String targetALLJson, final String cancel_url,final String cancel_dr_json) throws JSONException 
            {
        
        long timeStamp = System.currentTimeMillis();
        String createUrl = drUrl;

        createUrl = createUrl.replaceFirst("<program_id>", programID)
                .replaceFirst("<event_id>", eventID + timeStamp)
                .replaceFirst("<target_type>", targetType).replaceFirst("<target_all>", "true");

        String json = commonJson(createUrl, targetALLJson);

        final HttpResponse response = HTTPSClient.postResponse(createUrl, json,
                HTTPSClient.getPKCSKeyHttpClient("ecofactorcorp.p12", "ecofactor"));
        
        final String result = HTTPSClient.getResultString(response.getEntity());
        System.out.println(result);
        JSONObject jsonObj = new JSONObject(result);
        String eventId = (String) jsonObj.get("event_id");
        System.out.println(eventId);
        
      
        
        
    }*/
    
    @Test(groups = { Groups.SANITY1 }, dataProvider = "cancelDREventDefault", dataProviderClass = DRAPIDataProvider.class, priority = 1)
    public void drCancelEventForDefault(final String drUrl, final String programID,final String targetType, final String targetALLJson, final String cancel_url,final String cancel_dr_json) throws JSONException 
            {
        
        long timeStamp = System.currentTimeMillis();
        String createUrl = drUrl;

        createUrl = createUrl.replaceFirst("<program_id>", programID)
                .replaceFirst("<target_type>", targetType).replaceFirst("<target_all>", "true");

        String json = commonJson(createUrl, targetALLJson);

        final HttpResponse response = HTTPSClient.postResponse(createUrl, json,
                HTTPSClient.getPKCSKeyHttpClient("nopartnercode.p12", "ecofactor"));
        
        final String result = HTTPSClient.getResultString(response.getEntity());
        System.out.println(result);
        JSONObject jsonObj = new JSONObject(result);
        String eventId = (String) jsonObj.get("event_id");
        System.out.println(eventId);
        
        String cancelURL = cancel_url;
        
        cancelURL = cancelURL.replaceFirst("<program_id>", programID).replaceFirst("<PARTNER_EVENT_ID>", eventId);
        System.out.println(cancelURL);
        String cancel_json = cancel_dr_json;
        System.out.println(cancel_json);
        
        final HttpResponse cancel_response = HTTPSClient.putResponse(cancelURL, cancel_json,
                HTTPSClient.getPKCSKeyHttpClient("nopartnercode.p12", "ecofactor"));
        Assert.assertTrue(cancel_response.getStatusLine().getStatusCode() == 200, "Error Status:"
                + cancel_response.getStatusLine());
        
        
        
    }

    
    @Test(groups = { Groups.SANITY1 }, dataProvider = "cancelAlreadyCancelledEvent", dataProviderClass = DRAPIDataProvider.class, priority = 1)
    public void drCancelAlreadyCancelledEvent( final String programID, final String event_id, final String cancel_url,final String cancel_dr_json) throws JSONException 
            {
         String cancelURL = cancel_url;
        String eventId = event_id;
        System.out.println(eventId);
        
        cancelURL = cancelURL.replaceFirst("<program_id>", programID).replaceFirst("<PARTNER_EVENT_ID>", eventId);
        System.out.println(cancelURL);
        String cancel_json = cancel_dr_json;
        System.out.println(cancel_json);
        
        final HttpResponse cancel_response = HTTPSClient.putResponse(cancelURL, cancel_json,
                HTTPSClient.getPKCSKeyHttpClient("nopartnercode.p12", "ecofactor"));
        Assert.assertTrue(cancel_response.getStatusLine().getStatusCode() == 400, "Error Status:"
                + cancel_response.getStatusLine());
        final String resultValueString = HTTPSClient.getResultString(cancel_response.getEntity());
        setLogString("response :'" + resultValueString + "'", true);
        
        JSONObject jsonObj = new JSONObject(resultValueString);
        JSONArray jArray = jsonObj.getJSONArray("errors");
        JSONObject jObj = jArray.getJSONObject(0);
        String mesg = (String) jObj.get("msg");
        int code = (int) jObj.get("code");
        Assert.assertEquals(mesg, "Event already cancelled.");
        Assert.assertEquals(code, 11004);
        
        
    }
    
    @Test(groups = { Groups.SANITY1 }, dataProvider = "cancelNonExistingEvent", dataProviderClass = DRAPIDataProvider.class, priority = 1)
    public void drCancelNonExistingEvent( final String programID, final String event_id, final String cancel_url,final String cancel_dr_json) throws JSONException 
            {
         String cancelURL = cancel_url;
        String eventId = event_id;
        System.out.println(eventId);
        
        cancelURL = cancelURL.replaceFirst("<program_id>", programID).replaceFirst("<PARTNER_EVENT_ID>", eventId);
        System.out.println(cancelURL);
        String cancel_json = cancel_dr_json;
        System.out.println(cancel_json);
        
        final HttpResponse cancel_response = HTTPSClient.putResponse(cancelURL, cancel_json,
                HTTPSClient.getPKCSKeyHttpClient("nopartnercode.p12", "ecofactor"));
        Assert.assertTrue(cancel_response.getStatusLine().getStatusCode() == 404, "Error Status:"
                + cancel_response.getStatusLine());
        final String resultValueString = HTTPSClient.getResultString(cancel_response.getEntity());
        setLogString("response :'" + resultValueString + "'", true);
        
        JSONObject jsonObj = new JSONObject(resultValueString);
        JSONArray jArray = jsonObj.getJSONArray("errors");
        JSONObject jObj = jArray.getJSONObject(0);
        String mesg = (String) jObj.get("msg");
        int code = (int) jObj.get("code");
       // System.out.println( jObj.get("msg"));
       // System.out.println( jObj.get("code"));
        Assert.assertEquals(mesg, "Entity mapping is missing.");
        Assert.assertEquals(code, 11019);
        
        
    }
    
    @Test(groups = { Groups.SANITY1 }, dataProvider = "cancelBadJson", dataProviderClass = DRAPIDataProvider.class, priority = 1)
    public void drcancelBadJson(final String drUrl, final String programID,final String targetType, final String targetALLJson, final String cancel_url,final String cancel_bad_json, final String cancel_dr_json) throws JSONException 
    {
        long timeStamp = System.currentTimeMillis();
        String createUrl = drUrl;

        createUrl = createUrl.replaceFirst("<program_id>", programID)
                .replaceFirst("<target_type>", targetType).replaceFirst("<target_all>", "true");

        String json = commonJson(createUrl, targetALLJson);

        final HttpResponse response = HTTPSClient.postResponse(createUrl, json,
                HTTPSClient.getPKCSKeyHttpClient("nopartnercode.p12", "ecofactor"));
        
        final String result = HTTPSClient.getResultString(response.getEntity());
        System.out.println(result);
        JSONObject jsonObjresult = new JSONObject(result);
        String eventId = (String) jsonObjresult.get("event_id");
        System.out.println(eventId);
        
         String cancelURL = cancel_url;
       // String eventId = event_id;
        System.out.println(eventId);
        
        cancelURL = cancelURL.replaceFirst("<program_id>", programID).replaceFirst("<PARTNER_EVENT_ID>", eventId);
        System.out.println(cancelURL);
        String cancel_json = cancel_bad_json;
        System.out.println(cancel_json);
        
        final HttpResponse cancel_response = HTTPSClient.putResponse(cancelURL, cancel_json,
                HTTPSClient.getPKCSKeyHttpClient("nopartnercode.p12", "ecofactor"));
        Assert.assertTrue(cancel_response.getStatusLine().getStatusCode() == 500, "Error Status:"
                + cancel_response.getStatusLine());
        final String resultValueString = HTTPSClient.getResultString(cancel_response.getEntity());
        setLogString("response :'" + resultValueString + "'", true);
        
        String cancel_good_json = cancel_dr_json;
        
        final HttpResponse cancel_response1 = HTTPSClient.putResponse(cancelURL, cancel_good_json,
                HTTPSClient.getPKCSKeyHttpClient("nopartnercode.p12", "ecofactor"));
        Assert.assertTrue(cancel_response1.getStatusLine().getStatusCode() == 200, "Error Status:"
                + cancel_response.getStatusLine());
        
        
    }

    /**
     * String value is common for all test cases.
     * @param url is url
     * @param targetJson is targetJson
     * @return String
     */
    public String commonJson(final String url, final String targetJson) {

        String json = targetJson;
        json = json.replaceFirst("<start_time>",
                Long.toString(DateUtil.addToUTCMilliSeconds(Calendar.MINUTE, 10)))
                .replaceFirst("<end_time>",
                        Long.toString(DateUtil.addToUTCMilliSeconds(Calendar.MINUTE, 20)));
        setLogString("URL Values of the API \n" + url + "\n" + json, true);
        return json;
    }
}
