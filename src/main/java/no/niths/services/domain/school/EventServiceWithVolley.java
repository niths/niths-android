package main.java.no.niths.services.domain.school;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.google.gson.reflect.TypeToken;
import main.java.no.niths.MainApplication;
import main.java.no.niths.domain.school.Event;
import main.java.no.niths.services.utils.GsonRequest;
import main.java.no.niths.services.utils.GsonRequestBuilder;

import java.util.Collection;
import java.util.List;

/**
 * Created by elotin on 18.05.13.
 */
public class EventServiceWithVolley {
    public static String ENDPOINTURL = "http://hmeide.com:8085/events";
    private RequestQueue queue;

    public EventServiceWithVolley(MainApplication application) {
        this.queue = application.getRequestQueue();
    }

    public void getAll(Response.Listener<List<Event>> listener, Response.ErrorListener errorListener){
        GsonRequestBuilder<List<Event>> builder = new GsonRequestBuilder<List<Event>>();
        GsonRequest request = builder.setUrl(ENDPOINTURL)
                .setType(new TypeToken<Collection<Event>>() {
                }.getType())
                .setMethod(Request.Method.GET)
                .setListener(listener)
                .setErrorListener(errorListener)
                .createGsonRequest();
        queue.add(request);
        queue.start();
    }
}
