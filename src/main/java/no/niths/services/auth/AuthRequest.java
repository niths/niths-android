package main.java.no.niths.services.auth;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.google.gson.reflect.TypeToken;
import main.java.no.niths.MainApplication;
import main.java.no.niths.domain.school.Student;
import main.java.no.niths.services.utils.GsonRequest;
import no.niths.android.R;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Map;

/**
 * Created by elotin on 18.05.13.
 */

/*
Egen implementasjon av GsonRequest for autentisering mot NITHS.
Dette da vi må parse headeren for å få ut informasjonen vi trenger.
 */

public class AuthRequest extends GsonRequest<Student> {
    private MainApplication application;

    public AuthRequest(MainApplication application, Response.Listener<Student> listener, Response.ErrorListener errorListener, String body) {
        super(application.getString(R.string.server_url) + "/auth/login", new TypeToken<Student>(){}.getType(), Collections.singletonMap("Content-Type", "application/json"), listener, errorListener, Method.POST, body);
        this.application = application;
    }

    @Override
    protected Response<Student> parseNetworkResponse(NetworkResponse response) {
        String sessionToken = response.headers.get("session-token");
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(application);
        String niths_token_key = application.getString(R.string.niths_token_key);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(niths_token_key, sessionToken);
        editor.commit();
        Log.d("NITHS_KALL_HEADER", sessionToken);


        return super.parseNetworkResponse(response);
    }
}
