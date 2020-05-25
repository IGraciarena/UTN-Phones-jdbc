package utn.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utn.dao.CityDao;
import utn.exceptions.AlreadyExistsException;
import utn.exceptions.NoExistsException;
import utn.model.City;

import java.util.Optional;

@Service
public class CityService {
    private CityDao dao;

    @Autowired
    public CityService(CityDao dao) {
        this.dao = dao;
    }

    public City getById(Integer id) throws NoExistsException {
        City city = dao.getById(id);
        Optional.ofNullable(city).orElseThrow(NoExistsException::new);
        return dao.getById(id);
    }

    public void add(City city) throws AlreadyExistsException {
        dao.add(city);
    }

    public void remove(Integer id) throws NoExistsException {
        City city = dao.getById(id);
        Optional.ofNullable(city).orElseThrow(NoExistsException::new);
        dao.remove(id);
    }

    public void update(City value) throws NoExistsException {
        City city = dao.getById(value.getId());
        Optional.ofNullable(city).orElseThrow(NoExistsException::new);
        dao.update(value);
    }
}
