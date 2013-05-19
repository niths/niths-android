package main.java.no.niths.services.auth;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.reflect.TypeToken;
import main.java.no.niths.MainApplication;
import main.java.no.niths.domain.SessionToken;
import main.java.no.niths.domain.school.Student;
import main.java.no.niths.services.TokenBundle;
import main.java.no.niths.services.utils.GsonRequest;
import main.java.no.niths.services.utils.GsonRequestBuilder;
import no.niths.android.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: elotin
 * Date: 14.05.13
 * Time: 17:54
 * To change this template use File | Settings | File Templates.
 */
public class AuthServiceImpl implements AuthService{

    private RequestQueue requestQueue;
    MainApplication application;
    private String ENDPOINT;
    private Map<String, String> headers;
    private TokenBundle tokens;

    public AuthServiceImpl(MainApplication mainApplication) {
        application = mainApplication;
        requestQueue = application.getRequestQueue();
    }

    @Override
    public void login(String googleToken, Response.Listener<Student> listener, Response.ErrorListener errorListener) {
        SessionToken token1 = new SessionToken();
        token1.setToken(googleToken);
        AuthRequestBuilder builder = new AuthRequestBuilder();
        GsonRequest request = builder.setBody(token1)
                .setApplication(application)
                .setListener(listener)
                .setErrorListener(errorListener)
                .createAuthRequest();
        RequestQueue queue = application.getRequestQueue();
        queue.add(request);
        queue.start();
    }
}