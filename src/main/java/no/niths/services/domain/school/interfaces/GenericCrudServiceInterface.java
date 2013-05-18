package main.java.no.niths.services.domain.school.interfaces;

import com.android.volley.Response;
import main.java.no.niths.domain.DomainObject;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: elotin
 * Date: 14.05.13
 * Time: 15:28
 * To change this template use File | Settings | File Templates.
 */
public interface GenericCrudServiceInterface<T> {

    void getById(long id, Response.Listener<T> listener, Response.ErrorListener errorListener);

    void getAll(Response.Listener<List<T>> listener, Response.ErrorListener errorListener);

    void getAll(int startResult, int endResult, Response.Listener<List<T>> listener, Response.ErrorListener errorListener);

    boolean delete(long id);

    void update(T t);

    long create(T t);


}
