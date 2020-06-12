package utn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import utn.exceptions.AlreadyExistsException;
import utn.exceptions.NoExistsException;
import utn.model.Province;
import utn.service.ProvinceService;

@Controller
public class ProvinceController {
    ProvinceService provinceService;

    @Autowired
    public ProvinceController(ProvinceService provinceService) {
        this.provinceService = provinceService;
    }

    public void add(Province province) throws AlreadyExistsException {
        provinceService.add(province);
    }

    public void remove(Integer id) throws NoExistsException {
        provinceService.remove(id);
    }

    public void update(Province province)throws NoExistsException {
        provinceService.update(province);
    }

    public Province getById(Integer id) throws NoExistsException {
        return provinceService.getById(id);
    }

}
