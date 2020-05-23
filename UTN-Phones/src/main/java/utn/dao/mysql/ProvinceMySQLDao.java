package utn.dao.mysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import utn.dao.ProvinceDao;
import utn.exceptions.AlreadyExistsException;
import utn.model.Province;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class ProvinceMySQLDao implements ProvinceDao {
    Connection con;

    @Autowired
    public ProvinceMySQLDao(Connection con){
        this.con = con;
    }


    @Override
    public Province add(Province value) throws AlreadyExistsException {
       // PreparedStatement ps = con.prepareStatement()
        return null;
    }

    @Override
    public void update(Province value) {

    }

    @Override
    public void remove(Integer id) {

    }

    @Override
    public Province getById(Integer id) {
        return null;
    }

    @Override
    public List<Province> getAll() {
        return null;
    }
}
