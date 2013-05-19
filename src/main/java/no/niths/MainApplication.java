package main.java.no.niths;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.support.v4.util.LruCache;
import com.android.volley.RequestQueue;
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
    private LruCache<String, Bitmap> mMemoryCache;

    public ImageLoader getImageLoader() {
        return loader;
    }

    public void setLoader(ImageLoader loader) {
        this.loader = loader;
    }

    public LruCache<String, Bitmap> getmMemoryCache() {
        return mMemoryCache;
    }

    public void setmMemoryCache(LruCache<String, Bitmap> mMemoryCache) {
        this.mMemoryCache = mMemoryCache;
    }

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
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 8;
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                // The cache size will be measured in kilobytes rather than
                // number of items.
                return bitmap.getByteCount() / 1024;
            }
        };
        loader = new ImageLoader(requestQueue, new ImageLoader.ImageCache() {
            @Override
            public Bitmap getBitmap(String key) {
                return mMemoryCache.get(key);
            }

            @Override
            public void putBitmap(String key, Bitmap bitmap) {
                if (getBitmap(key) == null) {
                    mMemoryCache.put(key, bitmap);
                }
            }
        });
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
