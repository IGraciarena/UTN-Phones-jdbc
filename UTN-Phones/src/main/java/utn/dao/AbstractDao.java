package utn.dao;

import utn.exceptions.AlreadyExistsException;
import utn.exceptions.UserAlreadyExistsException;

import java.util.List;

public interface AbstractDao<T> {

    T add(T value) throws AlreadyExistsException;

    void update(T value);

    void remove(Integer id);

    T getById(Integer id);

    List<T> getAll();
}
