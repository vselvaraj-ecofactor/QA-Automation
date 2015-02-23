/**
 *
 */
package com.ecofactor.qa.automation.consumerapi;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class to assist with authentication operations.
 * @author npaila
 */
public class Authenticator {

    static final Logger logger = LoggerFactory.getLogger(Authenticator.class);
    public static final String AUTH_URL = "https://my-apps-qa.ecofactor.com/ws/v1.0/authenticate";

    /**
     * Authenticates an User based on the passed in credentials and returns Security cookie.
     * @param username
     * @param passwd
     * @return Cookie sent by server after user is authorized.
     */
    public static synchronized Cookie getSecurityCookieForUser(final String username,
            final String passwd) {

        Client client;
        final Cookie securityCookie;

        // ----- FETCH AUTH VALUES -----//
        // Initial request to get the auth headers
        client = ClientBuilder.newClient();
        Response unauthResponse = client.target(AUTH_URL).request().get();
        MultivaluedMap<String, String> reqHeadersValues = unauthResponse.getStringHeaders();

        logger.debug("/----- [unauth] reqHeadersValues -----/");
        for (String header : reqHeadersValues.keySet()) {
            logger.debug(header + " : " + reqHeadersValues.get(header));
        }

        List<String> authHeaders = reqHeadersValues.get("Www-Authenticate");
        logger.debug("authHeaders.size : " + authHeaders.size());
        String[] authHeadersValues = reqHeadersValues.get("Www-Authenticate").get(0).split(",");
        ConcurrentHashMap<String, String> authValues = new ConcurrentHashMap<String, String>();
        for (String header : authHeadersValues) {
            String[] keyValue = header.split("=");
            logger.debug("keyValue[0] == '" + keyValue[0] + "'");
            logger.debug("keyValue[1] == '" + keyValue[1] + "'");
            logger.debug("keyValue[0].length == '" + keyValue[0].length() + "'");

            String key = keyValue[0].trim();
            String value = keyValue[1].trim().substring(1, keyValue[1].length() - 1);
            logger.debug("key == '" + key + "'");
            logger.debug("value == '" + value + "'");
            authValues.put(key, value);
            logger.debug(header);
        }

        logger.debug("/----- authValues -----/");
        for (String key : authValues.keySet()) {
            logger.debug(key + " : " + authValues.get(key));
        }

        logger.debug("<===== [unauth] cookies ");
        for (String cookie : unauthResponse.getCookies().keySet()) {
            logger.debug(cookie + " : " + unauthResponse.getCookies().get(cookie));
        }
        logger.debug(" =====>");

        // ----- COMPUTE DIGEST -----//
        /*
         * -- ALGO for generating the response ha1 = CryptoJS.SHA1(this.username + ":" +
         * this.wwwAuthenticateHeader.realm + ":" +
         * this.encodedPassword).toString(CryptoJS.enc.Hex); response = CryptoJS.SHA1(ha1 + ":" +
         * this.wwwAuthenticateHeader.nonce).toString(CryptoJS.enc.Hex);
         */
        String encPasswd = DigestUtils.sha1Hex(passwd);
        logger.debug("encPasswd ==> " + encPasswd);
        String ha1 = DigestUtils.sha1Hex(username + ":" + authValues.get("DigestE realm") + ":"
                + encPasswd);
        logger.debug("ha1InputString == '" + username + ":" + authValues.get("DigestE realm") + ":"
                + encPasswd + "'");
        logger.debug("ha1 ==> " + ha1);
        String response = DigestUtils.sha1Hex(ha1 + ":" + authValues.get("nonce"));
        logger.debug("responseInputString == '" + ha1 + ":" + authValues.get("nonce") + "'");
        logger.debug("response ==> " + response);

        /*
         * -- Generating the Authorization header "DigestE " + "username=" + "\"" + this.username +
         * "\", " + "realm=" + "\"" + this.wwwAuthenticateHeader.realm + "\", " + "nonce=" + "\"" +
         * this.wwwAuthenticateHeader.nonce + "\", " + "response=" + "\"" + response + "\", " +
         * "opaque=" + "\"" + this.wwwAuthenticateHeader.opaque + "\"";
         */
        StringBuffer authorization = new StringBuffer();
        authorization.append("DigestE username=\"");
        authorization.append(username);
        authorization.append("\", ");
        authorization.append("realm=\"");
        authorization.append(authValues.get("DigestE realm"));
        authorization.append("\", ");
        authorization.append("nonce=\"");
        authorization.append(authValues.get("nonce"));
        authorization.append("\", ");
        authorization.append("response=\"");
        authorization.append(response);
        authorization.append("\", ");
        authorization.append("opaque=\"");
        authorization.append(authValues.get("opaque"));
        authorization.append("\"");

        // logger.info("authorization ==> "+ authorization.toString());

        Response authResponse = client.target(AUTH_URL)
                .request(MediaType.APPLICATION_JSON)
                .header("Authorization", authorization.toString()).get();
        // logger.info(authResponse.toString());

        MultivaluedMap<String, String> authreqHeadersValues = authResponse.getStringHeaders();

        logger.debug("/----- [auth] reqHeadersValues -----/");
        for (String header : authreqHeadersValues.keySet()) {
            logger.debug(header + " : " + authreqHeadersValues.get(header));
        }

        logger.debug("<===== [auth] cookies ");
        for (String cookie : authResponse.getCookies().keySet()) {
            logger.debug(cookie + " : " + authResponse.getCookies().get(cookie));
        }
        logger.debug(" =====>");

        // WORKAROUND FOR AUTH BUG EVEN AFTER PASSING IN RESPONSE DIGEST
        String securityCookieName = "SPRING_SECURITY_REMEMBER_ME_COOKIE";
        securityCookie = authResponse.getCookies().get(securityCookieName);
        logger.debug("securityCookie == '" + securityCookie + "'");

        return securityCookie;
    }

}
