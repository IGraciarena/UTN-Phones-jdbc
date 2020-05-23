package utn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import utn.model.City;
import utn.service.CityService;

@Controller
public class CityController {
    CityService cityService;

    @Autowired
    public CityController(CityService cityService){
        this.cityService = cityService;
    }
    public City add(City city){
        return cityService.add(city);
    }
    public void remove(Integer id){
        cityService.remove(id);
    }
    public void update(City city){
        cityService.update(city);
    }
    public City getById(Integer id){
        return cityService.getById(id);
    }
}
