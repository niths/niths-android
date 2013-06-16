package main.java.no.niths.services.domain.school;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.google.gson.reflect.TypeToken;
import main.java.no.niths.MainApplication;
import main.java.no.niths.domain.school.Event;
import main.java.no.niths.services.TokenBundle;
import main.java.no.niths.services.domain.school.interfaces.EventService;
import main.java.no.niths.services.domain.school.superclass.GenericCrudServiceOperator;
import no.niths.android.R;

import java.lang.reflect.Type;
import java.util.Collection;

/**
 * Created by elotin on 18.05.13.
 */
public class EventServiceImpl extends GenericCrudServiceOperator<Event> implements EventService {

    public static String ENDPOINTURL;

    public EventServiceImpl(TokenBundle tokens, RequestQueue queue, Context context) {
        super(tokens, queue);
        ENDPOINTURL = context.getString(R.string.server_url) + "/events";

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
