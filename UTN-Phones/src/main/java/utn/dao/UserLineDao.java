package utn.dao;

import utn.exceptions.AlreadyExistsException;
import utn.model.UserLine;

public interface UserLineDao extends AbstractDao<UserLine> {
    UserLine add(UserLine userLine) throws AlreadyExistsException;
}
