package main.java.no.niths.services;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: elotin
 * Date: 10.05.13
 * Time: 13:37
 * To change this template use File | Settings | File Templates.
 */
public class TokenBundle {

    private String sessionToken;
    private String developerToken;
    private String applicationToken;

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public String getDeveloperToken() {
        return developerToken;
    }

    public void setDeveloperToken(String developerToken) {
        this.developerToken = developerToken;
    }

    public String getApplicationToken() {
        return applicationToken;
    }

    public void setApplicationToken(String applicationToken) {
        this.applicationToken = applicationToken;
    }

    public Map<String, String> getAsHeaders(){
        Map mapHeaders = new HashMap<String, String>();
        mapHeaders.put("Session-token", getSessionToken());
        mapHeaders.put("Developer-token", getDeveloperToken());
        mapHeaders.put("Application-token", getApplicationToken());
        return mapHeaders;
    }
}
