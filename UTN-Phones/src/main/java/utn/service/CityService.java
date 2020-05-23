package utn.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utn.dao.CityDao;
import utn.exceptions.AlreadyExistsException;
import utn.model.City;

@Service
public class CityService {
    private CityDao dao;

    @Autowired
    public CityService(CityDao dao){
        this.dao = dao;
    }

    public City getById(Integer id) {
        return dao.getById(id);
    }

    public City add(City city) throws AlreadyExistsException {
        return dao.add(city);
    }

    public void remove(Integer id) {
        dao.remove(id);
    }

    public void update(City city) {
        dao.update(city);
    }
}
