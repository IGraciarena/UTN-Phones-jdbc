package utn.dao;

import utn.model.City;

public interface CityDao extends AbstractDao<City> {
    City getById(Integer id);
}
