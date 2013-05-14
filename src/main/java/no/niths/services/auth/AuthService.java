package main.java.no.niths.services.auth;

import android.content.Context;
import main.java.no.niths.domain.school.Student;

/**
 * Created with IntelliJ IDEA.
 * User: elotin
 * Date: 14.05.13
 * Time: 18:05
 * To change this template use File | Settings | File Templates.
 */
public interface AuthService {
    Student login(String googleToken, Context context);
}
