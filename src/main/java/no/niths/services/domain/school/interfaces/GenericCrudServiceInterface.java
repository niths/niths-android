package main.java.no.niths.services.domain.school.interfaces;

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

    T getById(long id);

    List<T> getAll();

    List<T>getAll(int firstResult, int offset);

    boolean delete(long id);

    void update(T t);

    long create(T t);


}
