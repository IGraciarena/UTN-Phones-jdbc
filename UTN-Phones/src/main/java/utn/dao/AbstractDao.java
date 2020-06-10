package utn.dao;

import utn.exceptions.AlreadyExistsException;
import utn.exceptions.NoExistsException;

public interface AbstractDao<T> {

    Object add(T value) throws AlreadyExistsException;

    void update(T value);

    void delete(Integer id);

    Object getById(Integer id);

}
