/*
 * HTTPSClient.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.drapi;

import static com.ecofactor.qa.automation.platform.util.LogUtil.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ecofactor.qa.automation.drapi.HTTPClientException;

/**
 * The Class HTTPSClient.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class HTTPSClient {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(HTTPSClient.class);

    /**
     * Gets the response.
     * @param url the url
     * @param httpClient the http client
     * @return the response
     */
    public static synchronized HttpResponse getResponse(final String url,
            final CloseableHttpClient httpClient) {

        try {
            checkHTTPClient(httpClient);
            final HttpGet httpPost = new HttpGet(url);
            httpPost.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
            httpPost.setHeader(HttpHeaders.ACCEPT, "application/json");

            final CloseableHttpResponse response = httpClient.execute(httpPost);
            setLogString("Status == " + response.getStatusLine(), true);
            return response;

        } catch (IOException | HTTPClientException e) {
            setLogString("Error executing HTTPS method. Reason ::: " + e, true);
            return null;
        }
    }

    /**
     * Post response.
     * @param url the url
     * @param json the json
     * @param httpClient the http client
     * @return the http response
     */
    public static synchronized HttpResponse postResponse(final String url, final String json,
            final CloseableHttpClient httpClient) {

        try {
            checkHTTPClient(httpClient);
            final HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
            httpPost.setHeader(HttpHeaders.ACCEPT, "application/json");

            final StringEntity params = new StringEntity(json);
            httpPost.setEntity(params);

            final CloseableHttpResponse response = httpClient.execute(httpPost);
            setLogString("Status == " + response.getStatusLine(), true);
            return response;

        } catch (IOException | HTTPClientException e) {
            setLogString("Error executing HTTPS method. Reason ::: " + e, true);
            return null;
        }
    }

    /**
     * Put response.
     * @param url the url
     * @param json the json
     * @param httpClient the http client
     * @return the http response
     */
    public static synchronized HttpResponse putResponse(final String url, final String json,
            final CloseableHttpClient httpClient) {

        try {
            checkHTTPClient(httpClient);
            final HttpPut httpPut = new HttpPut(url);
            httpPut.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
            httpPut.setHeader(HttpHeaders.ACCEPT, "application/json");

            final StringEntity params = new StringEntity(json);
            httpPut.setEntity(params);

            final CloseableHttpResponse response = httpClient.execute(httpPut);
            setLogString("URL Values of the API \n" + url + "\n" + json, true);
            setLogString("Status == " + response.getStatusLine(), true);
           // setLogString("response " + response, true);
            return response;

        } catch (IOException | HTTPClientException e) {
            setLogString("Error executing HTTPS method. Reason ::: " + e, true);
            return null;
        }
    }

    /**
     * Patch response.
     * @param url the url
     * @param json the json
     * @param httpClient the http client
     * @return the http response
     */
    public static synchronized HttpResponse patchResponse(final String url, final String json,
            final CloseableHttpClient httpClient) {

        try {
            checkHTTPClient(httpClient);
            final HttpPatch httpPatch = new HttpPatch(url);
            httpPatch.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
            httpPatch.setHeader(HttpHeaders.ACCEPT, "application/json");

            final StringEntity params = new StringEntity(json);
            httpPatch.setEntity(params);

            final CloseableHttpResponse response = httpClient.execute(httpPatch);
            setLogString("Status == " + response.getStatusLine(), true);
            return response;

        } catch (IOException | HTTPClientException e) {
            setLogString("Error executing HTTPS method. Reason ::: " + e, true);
            return null;
        }
    }

    /**
     * Gets the http client.
     * @param certificate the certificate
     * @param password the password
     * @return the http client
     */
    public static CloseableHttpClient getPKCSKeyHttpClient(final String certificate,
            final String password) {

        try {
            final KeyStore keystore = KeyStore.getInstance("pkcs12");
            keystore.load(HTTPSClient.class.getClassLoader().getResourceAsStream(certificate),
                    password.toCharArray());
            final SSLContextBuilder builder = new SSLContextBuilder();
            builder.loadKeyMaterial(keystore, password.toCharArray());
            final SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(builder.build());

            final CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(sslsf)
                    .setHostnameVerifier(new AllowAllHostnameVerifier()).build();
            return httpClient;
        } catch (UnrecoverableKeyException | KeyManagementException | KeyStoreException
                | NoSuchAlgorithmException | CertificateException | IOException e) {
            LOGGER.error("Error processing SSL certificates in HTTPS method. Reason ::: " + e);
            return null;
        }
    }

    /**
     * Gets the result string.
     * @param entity the entity
     * @return the result string
     */
    public static String getResultString(final HttpEntity entity) {

        try (final BufferedReader reader = new BufferedReader(new InputStreamReader(
                entity.getContent(), "iso-8859-1"), 8);) {
            final StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line + "\n");
            }
            final String resultString = builder.toString();
            LOGGER.debug("Result ==> " + resultString);
            return resultString;
        } catch (Exception e) {
            LOGGER.error("Error parsing response. Reason ::: " + e);
            return null;
        }
    }

    /**
     * Check http client.
     * @param httpClient the http client
     * @throws HTTPClientException the HTTP client exception
     */
    private static void checkHTTPClient(final CloseableHttpClient httpClient)
            throws HTTPClientException {

        if (httpClient == null) {
            throw new HTTPClientException("HTTP client was not created due improper certificate.");
        }
    }
}
