package com.ecofactor.qa.automation.consumerapi;

import static com.ecofactor.qa.automation.platform.util.LogUtil.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.ParseException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class HTTPClient.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class HTTPClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(HTTPClient.class);

    private static final String AUTH_URL = "https://my-apps-qa.ecofactor.com/ws/v1.0/authenticate";

    private static final String SECURITY_COOKIE = "SPRING_SECURITY_REMEMBER_ME_COOKIE";

    private static HttpClientContext context;

    private static String realm;

    private static String nonce;

    private static String opaque;

    // public static CloseableHttpClient httpClient;

    // public static HttpClient httpClient;

    /**
     * Gets the security cookie for user.
     * @param username the username
     * @param passwd the passwd
     * @return the security cookie for user
     */
    public static synchronized void getSecurityCookieForUser(final String username,
            final String passwd) {

        try {
            // RequestConfig globalConfig =
            // RequestConfig.custom().setCookieSpec(CookieSpecs.BEST_MATCH).build();
            initContext();

            // httpClient =
            // HttpClients.custom().setDefaultRequestConfig(globalConfig).setDefaultCookieStore(cookieStore).build();
            CloseableHttpClient httpClient = HttpClients.createDefault();
            // httpClient = new DefaultHttpClient();

            HttpPost post = new HttpPost(AUTH_URL);

            CloseableHttpResponse loginResponse = httpClient.execute(post, context);
            // HttpResponse loginResponse = httpClient.execute(post,context);

            ConcurrentHashMap<String, String> authValues = constructAuthValues(loginResponse);

            final StringBuffer authorization = createAuthorization(username, passwd, authValues);

            post = new HttpPost(AUTH_URL);

            addAuthHeader(post, authorization);

            loginResponse = httpClient.execute(post, context);
            setLogString("Authentication status ==> " + loginResponse.getStatusLine(), true);

            List<Cookie> cookies = context.getCookieStore().getCookies();
            for (Cookie cookie : cookies) {
                if (cookie.getName().equalsIgnoreCase(SECURITY_COOKIE)) {
                    LOGGER.debug("Authentication Successful!");
                    LOGGER.warn(SECURITY_COOKIE + " ==> " + cookie.getValue());
                    return;
                }
            }
        } catch (IOException e) {
            LOGGER.error("Authentication Failed! Cause ::: " + e);
        }
    }

    /**
     * Inits the context.
     */
    private static void initContext() {

        final CookieStore cookieStore = new BasicCookieStore();
        context = HttpClientContext.create();
        context.setCookieStore(cookieStore);
    }

    /**
     * Adds the auth header.
     * @param post the post
     * @param authorization the authorization
     */
    private static void addAuthHeader(final HttpPost post, final StringBuffer authorization) {

        post.addHeader(new Header() {

            @Override
            public String getValue() {

                return authorization.toString();
            }

            @Override
            public String getName() {

                return "Authorization";
            }

            @Override
            public HeaderElement[] getElements() throws ParseException {

                return null;
            }
        });
    }

    /**
     * Creates the authorization.
     * @param username the username
     * @param passwd the passwd
     * @param authValues the auth values
     * @return the string buffer
     */
    private static StringBuffer createAuthorization(final String username, final String passwd,
            ConcurrentHashMap<String, String> authValues) {

        realm = authValues.get("DigestE realm");
        nonce = authValues.get("nonce");
        opaque = authValues.get("opaque");

        final String encPasswd = DigestUtils.sha1Hex(passwd);
        final String ha1 = DigestUtils.sha1Hex(username + ":" + realm + ":" + encPasswd);
        final String response = DigestUtils.sha1Hex(ha1 + ":" + nonce);
        LOGGER.debug("response ==> " + response);

        final StringBuffer authorization = new StringBuffer();
        authorization.append("DigestE username=\"").append(username).append("\", ")
                .append("realm=\"").append(realm).append("\", ").append("nonce=\"").append(nonce)
                .append("\", ").append("response=\"").append(response).append("\", ")
                .append("opaque=\"").append(opaque).append("\"");
        return authorization;
    }

    /**
     * Construct auth values.
     * @param loginResponse the login response
     * @return the concurrent hash map
     */
    private static ConcurrentHashMap<String, String> constructAuthValues(
            final CloseableHttpResponse loginResponse) {

        final Header[] headers = loginResponse.getHeaders("Www-Authenticate");

        final String[] authHeadersValues = headers[0].getValue().split(",");
        final ConcurrentHashMap<String, String> authValues = new ConcurrentHashMap<String, String>();
        for (final String header : authHeadersValues) {
            final String[] keyValue = header.split("=");
            final String key = keyValue[0].trim();
            final String value = keyValue[1].trim().substring(1, keyValue[1].length() - 1);
            authValues.put(key, value);
        }
        return authValues;
    }

    /**
     * Gets the patch response.
     * @param url the url
     * @param json the json
     * @return the patch response
     */
    public static synchronized String getPatchResponse(final String url, final String json) {

        try {
            LOGGER.debug("URL ==> " + url);

            final HttpPatch httpPatch = new HttpPatch(url);
            httpPatch.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
            //httpPatch.setHeader("Content-Type", "application/json");
            httpPatch.setHeader(HttpHeaders.ACCEPT, "application/json");

            /*final StringEntity params = new StringEntity(json,
                    ContentType.create("application/json"));*/
            final StringEntity params = new StringEntity(json);
            //httpPatch.addHeader("content-type", "application/x-www-form-urlencoded");
            httpPatch.setEntity(params);
            /*
             * HttpClient c = new DefaultHttpClient(); HttpResponse response = c.execute(httpPatch,
             * context);
             */

            //final CloseableHttpClient httpClient = HttpClients.custom().setDefaultHeaders(new MediaTypeWithQualityHeaderValu("application/json"))
            
            /*final Header header = new BasicHeader(HttpHeaders.CONTENT_TYPE, "application/json");
            final List<Header> headers = Lists.newArrayList(header);
            final CloseableHttpClient httpClient = HttpClients.custom().setDefaultHeaders(headers).build();*/

            final CloseableHttpClient httpClient = HttpClients.createDefault();
            final CloseableHttpResponse response = httpClient.execute(httpPatch, context);

            // HttpResponse response = httpClient.execute(httpPatch, context);
            // Header[] headers = response.getAllHeaders();
            HttpEntity entity = response.getEntity();

            // convert response to string
            return getResultString(entity);

        } catch (IOException e) {
            LOGGER.error("Error executing PATCH method. Reason ::: " + e);
            return null;
        }
    }

    /**
     * Gets the result string.
     * @param entity the entity
     * @return the result string
     */
    private static String getResultString(final HttpEntity entity) {

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
}
