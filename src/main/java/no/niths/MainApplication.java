package main.java.no.niths;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import main.java.no.niths.services.TokenBundle;
import no.niths.android.R;

/**
 * Created with IntelliJ IDEA.
 * Student: elotin
 * Date: 10.05.13
 * Time: 03:43
 * To change this template use File | Settings | File Templates.
 */
public class MainApplication extends Application {
    public static String token;
    private ImageLoader loader;
    private RequestQueue requestQueue;

    public RequestQueue getRequestQueue() {
        return requestQueue;
    }

    public void setRequestQueue(RequestQueue requestQueue) {
        this.requestQueue = requestQueue;
    }

    // ***************************************
    // Application Methods
    // ***************************************
    @Override
    public void onCreate() {
        requestQueue = Volley.newRequestQueue(this);
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null;
    }

    public TokenBundle getTokenBundle() {
        TokenBundle bundle = null;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String sessiontoken = preferences.getString(getString(R.string.niths_token_key), null);
        if (sessiontoken != null) {
            bundle = new TokenBundle();
            bundle.setSessionToken(sessiontoken);
            bundle.setApplicationToken(getString(R.string.niths_application_token));
            bundle.setDeveloperToken(getString(R.string.niths_developer_token));
        }
        return bundle;
    }
}
