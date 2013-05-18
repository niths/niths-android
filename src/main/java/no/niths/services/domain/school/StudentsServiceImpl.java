package main.java.no.niths.services.domain.school;

import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import main.java.no.niths.MainApplication;
import main.java.no.niths.domain.school.Student;
import main.java.no.niths.services.TokenBundle;
import main.java.no.niths.services.domain.school.interfaces.*;
import main.java.no.niths.services.domain.school.superclass.GenericCrudServiceOperator;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * Student: elotin
 * Date: 10.05.13
 * Time: 13:32
 * To change this template use File | Settings | File Templates.
 */
public class StudentsServiceImpl extends GenericCrudServiceOperator<Student> implements StudentsService {
    private String ENDPOINTURL = "http://hmeide.com:8085/students";

    public StudentsServiceImpl(MainApplication application) {
        super(application);
    }

    @Override
    public Student getStudentByEmail(String email) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getEndpoint() {
        return ENDPOINTURL;
    }

    @Override
    public Type getType(){
        return  new TypeToken<Student>(){}.getType();
    }

    @Override
    public Type getListType() {
        return  new TypeToken<Collection<Student>>(){}.getType();
    }
}
