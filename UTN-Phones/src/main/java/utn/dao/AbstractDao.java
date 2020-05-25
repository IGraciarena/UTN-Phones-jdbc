package utn.dao;

import utn.exceptions.AlreadyExistsException;

public interface AbstractDao<T> {

    void add(T value) throws AlreadyExistsException;

    void update(T value);

    void remove(Integer id);

    Object getById(Integer id);

}
