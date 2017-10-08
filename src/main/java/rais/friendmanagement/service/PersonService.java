package rais.friendmanagement.service;

import rais.friendmanagement.dao.Person;

/**
 *
 * @author Muhammad Rais Rahim <rais.gowa@gmail.com>
 */
public interface PersonService {

    Person register(String email);

    Person findByEmailOrThrowEmailNotRegisteredApiException(String email);
}
