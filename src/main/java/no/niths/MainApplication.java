package main.java.no.niths;

import android.app.Application;
import android.database.sqlite.SQLiteOpenHelper;
import no.niths.android.R;
import org.springframework.security.crypto.encrypt.AndroidEncryptors;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.sqlite.SQLiteConnectionRepository;
import org.springframework.social.connect.sqlite.support.SQLiteConnectionRepositoryHelper;
import org.springframework.social.connect.support.ConnectionFactoryRegistry;
import org.springframework.social.google.api.Google;
import org.springframework.social.google.connect.GoogleConnectionFactory;

/**
 * Created with IntelliJ IDEA.
 * Student: elotin
 * Date: 10.05.13
 * Time: 03:43
 * To change this template use File | Settings | File Templates.
 */
public class MainApplication extends Application {
    public static String token;

    // ***************************************
    // Application Methods
    // ***************************************
    @Override
    public void onCreate() {
    }
}
