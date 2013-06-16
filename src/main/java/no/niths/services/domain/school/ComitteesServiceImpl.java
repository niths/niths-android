package main.java.no.niths.services.domain.school;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.google.gson.reflect.TypeToken;
import main.java.no.niths.MainApplication;
import main.java.no.niths.domain.school.Committee;
import main.java.no.niths.domain.school.Faddergruppe;
import main.java.no.niths.domain.school.Student;
import main.java.no.niths.services.TokenBundle;
import main.java.no.niths.services.domain.school.interfaces.CommitteeService;
import main.java.no.niths.services.domain.school.superclass.GenericCrudServiceOperator;
import no.niths.android.R;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: elotin
 * Date: 14.05.13
 * Time: 21:32
 * To change this template use File | Settings | File Templates.
 */
public class ComitteesServiceImpl extends GenericCrudServiceOperator<Committee> implements CommitteeService {

    private final String ENDPOINTURL;

    public ComitteesServiceImpl(TokenBundle tokens, RequestQueue queue, Context context) {
        super(tokens, queue);
        ENDPOINTURL = context.getString(R.string.server_url) + "/commitees";
    }
    @Override
    public Type getType() {
        return  new TypeToken<Committee>(){}.getType();
    }

    @Override
    public Type getListType() {
        return  new TypeToken<Collection<Committee>>(){}.getType();
    }

    @Override
    public String getEndpoint() {
        return ENDPOINTURL;
    }
}
