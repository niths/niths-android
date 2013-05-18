package main.java.no.niths.services.auth;

import android.content.Context;
import com.android.volley.Response;
import main.java.no.niths.domain.school.Student;

/**
 * Created with IntelliJ IDEA.
 * User: elotin
 * Date: 14.05.13
 * Time: 18:05
 * To change this template use File | Settings | File Templates.
 */
public interface AuthService {
    void login(String googleToken, Response.Listener<Student> listener, Response.ErrorListener errorListener);
}
