package main.java.no.niths.services.domain.school;

import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.reflect.TypeToken;
import main.java.no.niths.MainApplication;
import main.java.no.niths.domain.school.Event;
import main.java.no.niths.services.TokenBundle;
import main.java.no.niths.services.domain.school.interfaces.EventServiceInterface;
import main.java.no.niths.services.domain.school.superclass.GenericCrudServiceOperator;
import main.java.no.niths.services.utils.GsonRequest;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by elotin on 18.05.13.
 */
public class EventServiceImpl extends GenericCrudServiceOperator<Event> implements EventServiceInterface {

    public static String ENDPOINTURL = "http://hmeide.com:8085/events";

    public EventServiceImpl(MainApplication application) {
        super(application);

    }

    @Override
    public String getEndpoint() {
        return ENDPOINTURL;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Type getType() {
        return new TypeToken<Event>() {
        }.getType();
    }

    @Override
    public Type getListType() {
        return new TypeToken<Collection<Event>>() {
        }.getType();
    }
}
