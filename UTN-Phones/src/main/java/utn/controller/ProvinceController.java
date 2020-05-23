package utn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import utn.exceptions.AlreadyExistsException;
import utn.model.Province;
import utn.service.ProvinceService;

@Controller
public class ProvinceController {
    ProvinceService provinceService;

    @Autowired
    public ProvinceController(ProvinceService provinceService){
        this.provinceService = provinceService;
    }

    public Province add(Province province) throws AlreadyExistsException {
       return provinceService.add(province);
    }
    public void remove(Integer id){
        provinceService.remove(id);
    }

    public void update(Province province){
        provinceService.update(province);
    }

    public Province getById(Integer id){
        return provinceService.getById(id);
    }

}
