package com.ecofactor.qa.automation.util.mail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * Authenticates the mail Login
 * @author Aximsoft
 */
public class MailAuthenticator extends Authenticator {

    private String user;
    private String pw;

    public MailAuthenticator(final String username, final String password) {

        super();
        this.user = username;
        this.pw = password;
    }

    /**
     * <p>
     * Authenticates the login for the mail
     * </p>
     */
    public PasswordAuthentication getPasswordAuthentication() {

        return new PasswordAuthentication(user, pw);
    }
}
