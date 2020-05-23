package utn.dao.mysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import utn.dao.CityDao;
import utn.model.City;

import java.sql.Connection;
import java.util.List;

@Repository
public class CityMySQLDao implements CityDao {
    Connection con;

    @Autowired
    public CityMySQLDao(Connection con){
        this.con = con;
    }

    @Override
    public City getById(Integer id) {
        return null;
    }

    @Override
    public List<City> getAll() {
        return null;
    }

    @Override
    public City add(City city) {
        return null;
    }

    @Override
    public void remove(Integer id) {

    }

    @Override
    public void update(City city) {

    }
}
