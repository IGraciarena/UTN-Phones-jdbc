package utn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import utn.exceptions.AlreadyExistsException;
import utn.exceptions.NoExistsException;
import utn.model.City;
import utn.service.CityService;

@Controller
public class CityController {
    CityService cityService;

    @Autowired
    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    public void add(City city) throws AlreadyExistsException {
        cityService.add(city);
    }

    public void remove(Integer id) throws NoExistsException {
        cityService.remove(id);
    }

    public void update(City city) throws NoExistsException {
        cityService.update(city);
    }

    public City getById(Integer id) throws NoExistsException {
        return cityService.getById(id);
    }
}
