package main.java.no.niths.services.utils;

import com.android.volley.Request;
import com.android.volley.Response;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Map;

public class GsonRequestBuilder<T> {
    private String url;
    private Map<String, String> headers;
    private Response.Listener<T> listener;
    private Response.ErrorListener errorListener;
    private Type type = new TypeToken<T>() {
    }.getType();
    private int method = Request.Method.GET;
    private String body;

    public GsonRequestBuilder setUrl(String url) {
        this.url = url;
        return this;
    }

    public GsonRequestBuilder setClazz(Class<T> clazz) {
        this.type = new TypeToken<T>() {
        }.getType();
        return this;
    }

    public GsonRequestBuilder setHeaders(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }

    public GsonRequestBuilder setListener(Response.Listener<T> listener) {
        this.listener = listener;
        return this;
    }

    public GsonRequestBuilder setErrorListener(Response.ErrorListener errorListener) {
        this.errorListener = errorListener;
        return this;
    }

    public GsonRequestBuilder setType(Type type) {
        this.type = type;
        return this;
    }

    public GsonRequestBuilder setMethod(int method) {
        this.method = method;
        return this;
    }

    public GsonRequestBuilder setBody(String body) {
        this.body = body;
        return this;
    }

    public GsonRequestBuilder setBody(Object body) {
        Gson gson = new Gson();
        setBody(gson.toJson(body));
        return this;
    }

    public GsonRequest createGsonRequest() {
        GsonRequest request = new GsonRequest(url, type, headers, listener, errorListener, method, body);
        return request;
    }
}