package utn.dao;

import utn.model.PhoneCall;

public interface PhoneCallDao extends AbstractDao<PhoneCall> {
    PhoneCall getById(Integer id);
}
