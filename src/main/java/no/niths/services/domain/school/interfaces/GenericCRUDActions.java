package main.java.no.niths.services.domain.school.interfaces;

import main.java.no.niths.domain.DomainObject;

import java.util.List;

public interface GenericCRUDActions<T extends DomainObject> {

    long create(T domain);

    List<T> getAll();

    List<T> getAll(int firstResult, int maxResults);

    T getById(long id);

    void update(T domain);

    boolean delete(long id);
}