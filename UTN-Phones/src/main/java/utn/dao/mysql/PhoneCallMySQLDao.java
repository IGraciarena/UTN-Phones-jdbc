package utn.dao.mysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import utn.dao.PhoneCallDao;
import utn.exceptions.AlreadyExistsException;
import utn.model.PhoneCall;

import java.sql.Connection;
import java.util.List;

@Repository
public class PhoneCallMySQLDao implements PhoneCallDao {
    Connection con ;

    @Autowired
    public PhoneCallMySQLDao(Connection con){
        this.con = con;
    }

    @Override
    public PhoneCall add(PhoneCall value) throws AlreadyExistsException {
        return null;
    }

    @Override
    public void update(PhoneCall value) {

    }

    @Override
    public void remove(Integer id) {

    }

    @Override
    public PhoneCall getById(Integer id) {
        return null;
    }

    @Override
    public List<PhoneCall> getAll() {
        return null;
    }
}
