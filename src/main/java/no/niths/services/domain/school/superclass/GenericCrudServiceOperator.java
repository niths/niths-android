package main.java.no.niths.services.domain.school.superclass;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.google.gson.Gson;
import main.java.no.niths.MainApplication;
import main.java.no.niths.services.TokenBundle;
import main.java.no.niths.services.domain.school.interfaces.GenericCrudServiceInterface;
import main.java.no.niths.services.utils.GsonRequestBuilder;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: elotin
 * Date: 14.05.13
 * Time: 15:23
 * To change this template use File | Settings | File Templates.
 */
public abstract class GenericCrudServiceOperator<T> implements GenericCrudServiceInterface<T> {

    RestTemplate template;
    HttpHeaders headers;
    Map<String, String> mapHeaders;
    private TokenBundle tokens;
    private RequestQueue queue;

    public GenericCrudServiceOperator(MainApplication application) {
        template = new RestTemplate();
        mapHeaders = new HashMap<String, String>();
        headers = new HttpHeaders();
        tokens = application.getTokenBundle();
        headers.add("Session-token", tokens.getSessionToken());
        headers.add("Developer-token", tokens.getDeveloperToken());
        headers.add("Application-token", tokens.getApplicationToken());
        headers.setAccept(Collections.<MediaType>singletonList(MediaType.APPLICATION_JSON));
        this.tokens = application.getTokenBundle();


        mapHeaders.put("Session-token", tokens.getSessionToken());
        mapHeaders.put("Developer-token", tokens.getDeveloperToken());
        mapHeaders.put("Application-token", tokens.getApplicationToken());
        mapHeaders.put("Content-Type", "application/json");


        template.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
        this.queue = application.getRequestQueue();
    }

    //Generic konstruktør for request siden vi legger alltid til de samme headerene
    private <Y extends HttpRequestBase> Y createGetHeader(Class<Y> tClass, String url) {
        Y requestBase = null;
        try {
            requestBase = tClass.newInstance();
            URI uri = new URI(url);
            requestBase.addHeader("Session-token", tokens.getSessionToken());
            requestBase.addHeader("Developer-token", tokens.getDeveloperToken());
            requestBase.addHeader("Application-token", tokens.getApplicationToken());
            requestBase.setURI(uri);

        } catch (InstantiationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IllegalAccessException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (URISyntaxException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return requestBase;
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
        builder.setType(getListType())
        .setMethod(Request.Method.GET)
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
    }

    @Override
    public boolean delete(long id) {
        DefaultHttpClient client = new DefaultHttpClient();
        String url = String.format(Locale.getDefault(), getEndpoint() + "/%d", id);
        HttpDelete httpDelete = createGetHeader(HttpDelete.class, url);
        try {
            client.execute(httpDelete);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public void update(T o) {
        HttpEntity<T> studentHttpEntity = new HttpEntity<T>(o, headers);
        URI uri = null;
        try {
            uri = new URI(getEndpoint());
            template.put(uri, studentHttpEntity);
        } catch (URISyntaxException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public long create(T o) {
        DefaultHttpClient client = new DefaultHttpClient();
        String url = String.format(Locale.getDefault(), getEndpoint());
        HttpPost httpPost = createGetHeader(HttpPost.class, url);
        Gson gson = new Gson();
        String json = gson.toJson(o);
        HttpResponse response;
        try {
            httpPost.setEntity(new StringEntity(json));
            response = client.execute(httpPost);
            response.getEntity();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (ClientProtocolException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return 1;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public abstract Type getType();

    public abstract Type getListType();

    public abstract String getEndpoint();
}
