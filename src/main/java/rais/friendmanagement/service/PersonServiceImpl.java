package rais.friendmanagement.service;

import rais.friendmanagement.exception.EmailRegisterRepeatedApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rais.friendmanagement.dao.Person;
import rais.friendmanagement.dao.PersonRepo;
import rais.friendmanagement.exception.EmailNotRegisteredApiException;

/**
 *
 * @author Muhammad Rais Rahim <rais.gowa@gmail.com>
 */
@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepo personRepo;

    @Override
    public Person register(String email) {
        if (personRepo.existsByEmail(email)) {
            throw new EmailRegisterRepeatedApiException();
        }
        return personRepo.save(new Person(email));
    }
}
