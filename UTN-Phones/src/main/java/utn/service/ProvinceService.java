package utn.service;

import org.springframework.stereotype.Service;
import utn.dao.ProvinceDao;
import utn.exceptions.AlreadyExistsException;
import utn.model.Province;

@Service
public class ProvinceService {
    ProvinceDao dao;

    public ProvinceService(ProvinceDao provinceDao) {
        this.dao = provinceDao;
    }

    public Province getById(Integer id) {
        return dao.getById(id);
    }

    public void add(Province province) throws AlreadyExistsException {
        dao.add(province);
    }

    public void remove(Integer id) {
        dao.remove(id);
    }

    public void update(Province province) {
        dao.update(province);
    }
}
