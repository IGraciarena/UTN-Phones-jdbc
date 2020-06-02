package utn.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utn.dao.PhoneCallDao;
import utn.dao.UserDao;
import utn.dto.PhoneCallDto;
import utn.dto.PhoneCallsBetweenDatesDto;
import utn.dto.ReturnedPhoneCallDto;
import utn.dto.UserDto;
import utn.exceptions.NoExistsException;
import utn.model.PhoneCall;

import java.util.List;
import java.util.Optional;

@Service
public class PhoneCallService {
    PhoneCallDao dao;
    UserDao daoUser;

    @Autowired
    public PhoneCallService(PhoneCallDao dao, UserDao daoUser) {
        this.dao = dao;
        this.daoUser = daoUser;
    }

    public ReturnedPhoneCallDto getById(Integer id) throws NoExistsException {
        ReturnedPhoneCallDto phoneCall = dao.getById(id);
        Optional.ofNullable(phoneCall).orElseThrow(NoExistsException::new);
        return phoneCall;
    }

    public Integer addPhoneCall(PhoneCallDto phoneCall) {
        return dao.addPhoneCall(phoneCall);
    }

    public void remove(Integer id) throws NoExistsException {
        ReturnedPhoneCallDto phoneCall = dao.getById(id);
        Optional.ofNullable(phoneCall).orElseThrow(NoExistsException::new);
        dao.remove(id);
    }

    public void update(PhoneCall value) throws NoExistsException {
        ReturnedPhoneCallDto phoneCall = dao.getById(value.getId());
        Optional.ofNullable(phoneCall).orElseThrow(NoExistsException::new);
        dao.update(value);
    }

    public List<ReturnedPhoneCallDto> getAll() {
        return dao.getAll();
    }

    public List<ReturnedPhoneCallDto> getAllPhoneCallsFromUserId(Integer userId) throws NoExistsException {
        UserDto user = daoUser.getById(userId);
        Optional.ofNullable(user).orElseThrow(NoExistsException::new);
        return dao.getAllPhoneCallsFromUserId(userId);
    }

    public List<ReturnedPhoneCallDto> getPhoneCallsFromUserIdBetweenDates(PhoneCallsBetweenDatesDto phonecallDto) throws NoExistsException {
        UserDto user = daoUser.getById(phonecallDto.getIdUser());
        Optional.ofNullable(user).orElseThrow(NoExistsException::new);
        return dao.getPhoneCallsFromUserIdBetweenDates(phonecallDto);
    }

    public List<String> getMostCalledDestinsByUserId(Integer idUser) throws NoExistsException {
        UserDto user = daoUser.getById(idUser);
        Optional.ofNullable(user).orElseThrow(NoExistsException::new);
        return dao.getMostCalledDestinsByUserId(idUser);
    }
}
