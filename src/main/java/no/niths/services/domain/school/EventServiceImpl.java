package main.java.no.niths.services.domain.school;

import com.google.gson.reflect.TypeToken;
import main.java.no.niths.MainApplication;
import main.java.no.niths.domain.school.Event;
import main.java.no.niths.services.domain.school.interfaces.EventService;
import main.java.no.niths.services.domain.school.superclass.GenericCrudServiceOperator;

import java.lang.reflect.Type;
import java.util.Collection;

/**
 * Created by elotin on 18.05.13.
 */
public class EventServiceImpl extends GenericCrudServiceOperator<Event> implements EventService {

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
