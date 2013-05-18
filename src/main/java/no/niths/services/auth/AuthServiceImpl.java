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
public class AuthServiceImpl implements AuthService, Response.Listener<Student>, Response.ErrorListener {

    private RequestQueue requestQueue;
    MainApplication application;
    private String ENDPOINT;
    private Map<String, String> headers;
    private TokenBundle tokens;

    public AuthServiceImpl(MainApplication mainApplication) {
        application = mainApplication;
        requestQueue = application.getRequestQueue();
        /*
        tokens = application.getTokenBundle();

        headers = new HashMap<String, String>();
        headers.put("Session-token", tokens.getSessionToken());
        headers.put("Developer-token", tokens.getDeveloperToken());
        headers.put("Application-token", tokens.getApplicationToken());
        headers.put("Content-Type", "application/json");
        */
    }

    /*
    @Override
    public Student login(String googleToken, Context context) {
        Student student = null;

        RestTemplate template = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        SessionToken token1 = new SessionToken();
        token1.setToken(googleToken);
        HttpEntity<SessionToken> entity = new HttpEntity<SessionToken>(token1, headers);

        template.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
        ResponseEntity<Student> result = template.postForEntity(context.getString(R.string.server_url) + "/auth/login", entity, Student.class);

        Log.d("LOGIN_SERVICE", result.getBody().toString());
        Log.d("LOGIN_SERVICE", result.getHeaders().get("session-token").get(0).toString());
        Log.d("LOGIN_SERVICE", result.getStatusCode().toString());
        String niths_token = result.getHeaders().get("session-token").get(0).toString();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String niths_token_key = context.getString(R.string.niths_token_key);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(niths_token_key, niths_token);
        editor.commit();
        Log.d("NITHS_KALL_HEADER", niths_token);
        return result.getBody();
    }
    */
    @Override
    public void onErrorResponse(VolleyError volleyError) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onResponse(Student student) {
        //To change body of implemented methods use File | Settings | File Templates.
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