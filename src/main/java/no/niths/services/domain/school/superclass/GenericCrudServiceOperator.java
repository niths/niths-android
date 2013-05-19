package main.java.no.niths.services.domain.school.superclass;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import main.java.no.niths.MainApplication;
import main.java.no.niths.services.TokenBundle;
import main.java.no.niths.services.domain.school.interfaces.GenericCrudServiceInterface;
import main.java.no.niths.services.utils.GsonRequestBuilder;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: elotin
 * Date: 14.05.13
 * Time: 15:23
 * To change this template use File | Settings | File Templates.
 */
public abstract class GenericCrudServiceOperator<T> implements GenericCrudServiceInterface<T> {

    Map<String, String> mapHeaders;
    private TokenBundle tokens;
    private RequestQueue queue;

    public GenericCrudServiceOperator(MainApplication application) {
        mapHeaders = new HashMap<String, String>();
        tokens = application.getTokenBundle();

        mapHeaders.put("Session-token", tokens.getSessionToken());
        mapHeaders.put("Developer-token", tokens.getDeveloperToken());
        mapHeaders.put("Application-token", tokens.getApplicationToken());
        mapHeaders.put("Content-Type", "application/json");
        this.queue = application.getRequestQueue();
    }

    public void getAll(Response.Listener<List<T>> listener, Response.ErrorListener errorListener) {

        GsonRequestBuilder<T> builder = new GsonRequestBuilder<T>();
        builder.setUrl(getEndpoint())
                .setListener(listener)
                .setErrorListener(errorListener)
                .setType(getListType());
        getFromBuilder(builder);
    }

    @Override
    public void getAll(int firstResult, int offset, Response.Listener<List<T>> listener, Response.ErrorListener errorListener) {
        String url = String.format(getEndpoint() + "/paginated/%d/%d", firstResult, offset);
        GsonRequestBuilder<T> builder = new GsonRequestBuilder<T>();
        builder.setUrl(url)
                .setListener(listener)
                .setErrorListener(errorListener)
                .setType(getListType());
        getFromBuilder(builder);
    }

    private void getFromBuilder(GsonRequestBuilder builder) {
        builder.setMethod(Request.Method.GET)
                .setHeaders(mapHeaders)
                .createGsonRequest();
        queue.add(builder.createGsonRequest());
        queue.start();
    }

    @Override
    public void getById(long id, Response.Listener<T> listener, Response.ErrorListener errorListener) {
        String url = String.format(getEndpoint() + "/%d", id);
        GsonRequestBuilder<T> builder = new GsonRequestBuilder<T>();
        builder.setUrl(url)
                .setListener(listener)
                .setErrorListener(errorListener)
                .setType(getType());
        getFromBuilder(builder);
        queue.add(builder.createGsonRequest());
        queue.start();
    }

    @Override
    public void delete(long id, Response.Listener listener, Response.ErrorListener errorListener) {
        String url = String.format(Locale.getDefault(), getEndpoint() + "/%d", id);
        GsonRequestBuilder builder = new GsonRequestBuilder();
        builder.setUrl(url)
                .setMethod(Request.Method.DELETE)
                .setHeaders(mapHeaders)
                .setType(getType())
                .setListener(listener)
                .setErrorListener(errorListener);
        queue.add(builder.createGsonRequest());
        queue.start();
    }

    @Override
    public void update(T o, Response.Listener<T> listener, Response.ErrorListener errorListener) {
        GsonRequestBuilder builder = new GsonRequestBuilder();
        builder.setUrl(getEndpoint())
                .setMethod(Request.Method.PUT)
                .setHeaders(mapHeaders)
                .setType(getType())
                .setListener(listener)
                .setBody(o)
                .setErrorListener(errorListener);
        queue.add(builder.createGsonRequest());
        queue.start();
    }

    @Override
    public void create(T o, Response.Listener<T> listener, Response.ErrorListener errorListener) {
        GsonRequestBuilder builder = new GsonRequestBuilder();
        builder.setUrl(getEndpoint())
                .setMethod(Request.Method.POST)
                .setHeaders(mapHeaders)
                .setType(getType())
                .setListener(listener)
                .setBody(o)
                .setErrorListener(errorListener);
        queue.add(builder.createGsonRequest());
        queue.start();
    }

    public abstract Type getType();

    public abstract Type getListType();

    public abstract String getEndpoint();
}
