package main.java.no.niths.domain.twitter;

import com.android.volley.Response;
import com.google.gson.reflect.TypeToken;
import main.java.no.niths.domain.school.Student;
import main.java.no.niths.services.utils.GsonRequest;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Map;

/**
 * Created by elotin on 18.05.13.
 */
public class NITHTwitterFetcher extends GsonRequest<TwitterWrapper> {

    public NITHTwitterFetcher(int page, Response.Listener<TwitterWrapper> listener, Response.ErrorListener errorListener) {
        super("http://search.twitter.com/search.json?q=@NITHutdanning&page=" + page, getType(), null, listener, errorListener, Method.GET, null);
    }

    private static Type getType(){
        return new TypeToken<TwitterWrapper>(){}.getType();
    }
}
