package dao;

import execptions.UserAlreadyExistsException;

import java.util.List;

public interface AbstractDao<T> {

    T add(T value) throws UserAlreadyExistsException;

    Integer update(T value);

    Integer remove(Integer id);

    Integer remove(T value);

    T getById(Integer id);

    List<T> getAll();
}
