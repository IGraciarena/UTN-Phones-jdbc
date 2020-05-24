package utn.dao;

import utn.model.City;

import java.util.List;

public interface CityDao extends AbstractDao<City> {
    City getById(Integer id);
    List<City> getAll();
}
