package com.example.provide_vaccine_services.dao.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class logs implements Serializable {
    private int id;
    private String logLevel;
    private String logMessage;
    private String userName;
    private String userIp;
    private Timestamp timestamp;

    // Constructor đầy đủ tham số
    public logs(int id, String logLevel, String logMessage, String userName, String userIp, Timestamp timestamp) {
        this.id = id;
        this.logLevel = logLevel;
        this.logMessage = logMessage;
        this.userName = userName;
        this.userIp = userIp;
        this.timestamp = timestamp;
    }
    public void Logs() {
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getLogLevel() {
        return logLevel;
    }
    public void setLogLevel(String logLevel) {
        this.logLevel = logLevel;
    }

    public String getLogMessage() {
        return logMessage;
    }
    public void setLogMessage(String logMessage) {
        this.logMessage = logMessage;
    }

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserIp() {
        return userIp;
    }
    public void setUserIp(String userIp) {
        this.userIp = userIp;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
