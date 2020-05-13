package utn.dao;

import utn.exceptions.UserAlreadyExistsException;

import java.util.List;

public interface AbstractDao<T> {

    T add(T value) throws UserAlreadyExistsException;

    void update(T value);

    void remove(Integer id);

    T getById(Integer id);

    List<T> getAll();
}
