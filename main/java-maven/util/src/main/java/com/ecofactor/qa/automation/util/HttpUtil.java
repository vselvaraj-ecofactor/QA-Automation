/*
 * HttpUtil.java
 * Copyright (c) 2013, KITS, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of KITS
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * KITS.
 */
package com.ecofactor.qa.automation.util;

import java.util.Map;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

/**
 * HttpUtil is a helper utility class to perform http request.
 * @author $Author: rvinoopraj $
 * @version $Rev: 23556 $ $Date: 2013-10-01 15:06:24 +0530 (Tue, 01 Oct 2013) $
 */
public final class HttpUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpUtil.class);

    /**
     * Gets the.
     * @param url the url
     * @return the http response
     */
    public static String get(String url, Map<String, String> params, int expectedStatus) {

        String content = null;
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet request = new HttpGet(url);
        URIBuilder builder = new URIBuilder(request.getURI());

        Set<String> keys = params.keySet();
        for(String key:keys) {
            builder.addParameter(key, params.get(key));
        }

        HttpResponse response = null;
        try {
            request.setURI(builder.build());
            DriverConfig.setLogString("URL " + request.getURI().toString(), true);
            response = httpClient.execute(request);
            content = IOUtils.toString(response.getEntity().getContent());
            DriverConfig.setLogString("Content " + content, true);
            Assert.assertEquals(response.getStatusLine().getStatusCode(), expectedStatus);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        } finally {
            request.releaseConnection();
        }

        return content;
    }
}
