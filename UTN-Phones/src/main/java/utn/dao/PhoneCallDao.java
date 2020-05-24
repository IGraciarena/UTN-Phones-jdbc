package utn.dao;

import utn.dto.PhoneCallDto;
import utn.dto.ReturnedPhoneCallDto;
import utn.model.PhoneCall;

import java.util.List;

public interface PhoneCallDao extends AbstractDao<PhoneCall> {
    ReturnedPhoneCallDto getById(Integer id);

    List<ReturnedPhoneCallDto> getAll();
}
