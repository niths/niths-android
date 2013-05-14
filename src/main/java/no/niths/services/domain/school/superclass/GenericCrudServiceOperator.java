package main.java.no.niths.services.domain.school.superclass;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import main.java.no.niths.domain.DomainWrapper;
import main.java.no.niths.services.TokenBundle;
import main.java.no.niths.services.domain.school.interfaces.*;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.*;
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
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

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
    private TokenBundle tokens;

    public GenericCrudServiceOperator(TokenBundle tokens) {
        template = new RestTemplate();
        headers = new HttpHeaders();
        headers.add("Session-token", tokens.getSessionToken());
        headers.add("Developer-token", tokens.getDeveloperToken());
        headers.add("Application-token", tokens.getApplicationToken());
        headers.setAccept(Collections.<MediaType>singletonList(MediaType.APPLICATION_JSON));
        this.tokens = tokens;

        template.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
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

    public List<T> getAll() {
        DefaultHttpClient client = new DefaultHttpClient();

        HttpGet httpGet = createGetHeader(HttpGet.class, getEndpoint());

        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        try {
            Gson gson = new Gson();
            Type collectionType = new TypeToken<Collection<T>>() {
            }.getType();
            String json = client.execute(httpGet, responseHandler);
            List<T> tResult = gson.fromJson(json, collectionType);
            return tResult;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List getAll(int firstResult, int offset) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public T getById(long id) {
        DefaultHttpClient client = new DefaultHttpClient();
        String url = String.format(Locale.getDefault(), getEndpoint() + "/%d", id);
        HttpGet httpGet = createGetHeader(HttpGet.class, url);

        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        T tResult = null;
        try {
            Gson gson = new Gson();
            String json = client.execute(httpGet, responseHandler);

            tResult = gson.fromJson(json, getType());
            return tResult;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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

    private  <T> T deserialize(String jsonString, Class<T> clazz) {
        GsonBuilder builder = new GsonBuilder();
        builder.setDateFormat("MM/dd/yy HH:mm:ss");

        Gson gson = builder.create();
        return gson.fromJson(jsonString, clazz);
    }

    public abstract Type getType();

    public abstract String getEndpoint();
}
