package utn.dao.mysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import utn.dao.RateDao;
import utn.exceptions.AlreadyExistsException;
import utn.model.Rate;

import java.sql.Connection;
import java.util.List;

@Repository
public class RateMySQLDao implements RateDao {
    Connection con;

    @Autowired
    public RateMySQLDao(Connection con){
        this.con = con;
    }

    @Override
    public Rate add(Rate value) throws AlreadyExistsException {
        return null;
    }

    @Override
    public void update(Rate value) {

    }

    @Override
    public void remove(Integer id) {

    }

    @Override
    public Rate getById(Integer id) {
        return null;
    }

    @Override
    public List<Rate> getAll() {
        return null;
    }
}
