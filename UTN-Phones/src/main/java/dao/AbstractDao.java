package dao;

import execptions.UserAlreadyExistsExecption;

import java.util.List;

public interface AbstractDao<T> {

    T add(T value) throws UserAlreadyExistsExecption;

    Integer update(T value);

    Integer remove(Integer id);

    Integer remove(T value);

    T getById(Integer id);

    List<T> getAll();
}
