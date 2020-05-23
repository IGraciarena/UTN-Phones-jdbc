package utn.dao.mysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import utn.dao.UserLineDao;
import utn.exceptions.AlreadyExistsException;
import utn.model.UserLine;

import java.sql.Connection;
import java.util.List;

@Repository
public class UserLineMySQLDao implements UserLineDao {
    Connection con;

    @Autowired
    public UserLineMySQLDao(Connection con){
        this.con = con;
    }


    @Override
    public UserLine add(UserLine userLine) throws AlreadyExistsException {
        return null;
    }

    @Override
    public void update(UserLine value) {

    }

    @Override
    public void remove(Integer id) {

    }

    @Override
    public UserLine getById(Integer id) {
        return null;
    }

    @Override
    public List<UserLine> getAll() {
        return null;
    }
}
