package main.java.no.niths.services.auth;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import main.java.no.niths.domain.SessionToken;
import main.java.no.niths.domain.school.Student;
import no.niths.android.R;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

/**
 * Created with IntelliJ IDEA.
 * User: elotin
 * Date: 14.05.13
 * Time: 17:54
 * To change this template use File | Settings | File Templates.
 */
public class AuthServiceImpl implements AuthService {




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
        String token = preferences.getString(context.getString(R.string.google_token_key), "none");
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(niths_token_key, niths_token);
        editor.commit();
        Log.d("NITHS_KALL_HEADER", niths_token);

        return result.getBody();
    }
}