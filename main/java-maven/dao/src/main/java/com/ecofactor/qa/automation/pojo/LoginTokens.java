package com.ecofactor.qa.automation.pojo;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ef_login_tokens")
public class LoginTokens implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "series")
    private String series;

    @Column(name = "username")
    private String userName;

    @Column(name = "token")
    private String token;

    @Column(name = "last_used")
    private Calendar lastUsed;

    public String getSeries() {

        return series;
    }

    public void setSeries(String series) {

        this.series = series;
    }

    public String getUserName() {

        return userName;
    }

    public void setUserName(String userName) {

        this.userName = userName;
    }

    public String getToken() {

        return token;
    }

    public void setToken(String token) {

        this.token = token;
    }

    public Calendar getLastUsed() {

        return lastUsed;
    }

    public void setLastUsed(Calendar lastUsed) {

        this.lastUsed = lastUsed;
    }

}
