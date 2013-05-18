package main.java.no.niths.services.auth;

import com.android.volley.Response;
import com.google.gson.Gson;
import main.java.no.niths.MainApplication;
import main.java.no.niths.domain.SessionToken;
import main.java.no.niths.domain.school.Student;

public class AuthRequestBuilder {
    private MainApplication application;
    private Response.Listener<Student> listener;
    private Response.ErrorListener errorListener;
    private String body;

    public AuthRequestBuilder setApplication(MainApplication application) {
        this.application = application;
        return this;
    }

    public AuthRequestBuilder setListener(Response.Listener<Student> listener) {
        this.listener = listener;
        return this;
    }

    public AuthRequestBuilder setErrorListener(Response.ErrorListener errorListener) {
        this.errorListener = errorListener;
        return this;
    }

    public AuthRequestBuilder setBody(String body) {
        this.body = body;
        return this;
    }

    public AuthRequestBuilder setBody(SessionToken token) {
        Gson gson = new Gson();
        setBody(gson.toJson(token));
        return this;
    }

    public AuthRequest createAuthRequest() {
        return new AuthRequest(application, listener, errorListener, body);
    }
}