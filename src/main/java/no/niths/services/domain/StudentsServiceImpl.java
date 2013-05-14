package main.java.no.niths.services.domain;

import android.net.Uri;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import main.java.no.niths.domain.Student;
import main.java.no.niths.services.TokenBundle;
import main.java.no.niths.services.domain.interfaces.StudentsService;
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
public class StudentsServiceImpl implements StudentsService {
    RestTemplate template;
    HttpHeaders headers;

    public StudentsServiceImpl(TokenBundle tokens) {
        template = new RestTemplate();
        headers = new HttpHeaders();
        headers.add("Session-token", tokens.getSessionToken());
        headers.add("Developer-token", tokens.getDeveloperToken());
        headers.add("Application-token", tokens.getApplicationToken());
        headers.setAccept(Collections.<MediaType>singletonList(MediaType.APPLICATION_JSON));

        template.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
    }



    @Override public List<Student> getStudents(int offset, int max, TokenBundle tokens) {
        Exception exception;

            DefaultHttpClient client = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet("http://hmeide.com:8085/students");
            httpGet.addHeader("Session-token", tokens.getSessionToken());
            httpGet.addHeader("Developer-token", tokens.getDeveloperToken());
            httpGet.addHeader("Application-token", tokens.getApplicationToken() );

            Log.d("NITHS_KALL","Session token: " +  tokens.getSessionToken());
            Log.d("NITHS_KALL","Developer token: " + tokens.getDeveloperToken());
            Log.d("NITHS_KALL","Application-token: " +  tokens.getApplicationToken());


            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            try{
                Gson gson = new Gson();
                Type collectionType = new TypeToken<Collection<Student>>(){}.getType();
                String json = client.execute(httpGet, responseHandler);
                List<Student> students = gson.fromJson(json, collectionType);
                return students;
            }catch(Exception e){
                e.printStackTrace();
            }
            return null;
    }

    @Override public Student updateStudent(Student student) {
        HttpEntity<Student> studentHttpEntity = new HttpEntity<Student>(student, headers);
        URI uri = null;
        try {
            uri = new URI("http://hmeide.com:8085/students");
            template.put(uri, studentHttpEntity);
        } catch (URISyntaxException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getStudentById(student.getId());
    }

    @Override
    public Student getStudentById(Long id) {
        String url = String.format(Locale.getDefault(),"http://hmeide.com:8085/students/%d", id );

        ResponseEntity<Student> response = template.exchange(url,
                    HttpMethod.GET,
                    new HttpEntity(headers),
                    Student.class);
            Log.d("Response code: ", response.getStatusCode().toString());
        return response != null ? response.getBody() : null;
    }

    @Override
    public Student getStudentByEmail(String email) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
