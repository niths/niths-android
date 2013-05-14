package main.java.no.niths.services.domain.school;

import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import main.java.no.niths.domain.school.Faddergruppe;
import main.java.no.niths.domain.school.Student;
import main.java.no.niths.services.TokenBundle;
import main.java.no.niths.services.domain.school.interfaces.*;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import main.java.no.niths.services.domain.school.superclass.*;

/**
 * Created with IntelliJ IDEA.
 * User: elotin
 * Date: 14.05.13
 * Time: 15:14
 * To change this template use File | Settings | File Templates.
 */
public class FaddergruppeServiceImpl extends GenericCrudServiceOperator<Faddergruppe> implements FaddergruppeService {

    RestTemplate template;
    HttpHeaders headers;
    private TokenBundle tokens;
    private String ENDPOINTURL =  "http://hmeide.com:8085/fadder";

    public FaddergruppeServiceImpl(TokenBundle tokens) {
        super(tokens);
    }

    @Override
    public String getEndpoint() {
        return ENDPOINTURL;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Type getType(){
        return  new TypeToken<Faddergruppe>(){}.getType();
    }


    public Faddergruppe getFaddergruppeForStudent(Student student) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public List<Faddergruppe> getFaddergrupper() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public List<Faddergruppe> getFaddergrupperForLeader(Student leader) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }


}
