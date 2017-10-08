package rais.friendmanagement.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import rais.friendmanagement.dao.Friend;
import rais.friendmanagement.dao.FriendRepo;
import rais.friendmanagement.dao.Person;
import rais.friendmanagement.exception.FriendConnectionRequestRepeatedApiException;

/**
 *
 * @author Muhammad Rais Rahim <rais.gowa@gmail.com>
 */
@Service
@Slf4j
public class FriendConnectionServiceImpl implements FriendConnectionService {

    @Autowired
    private FriendRepo friendRepo;
    @Autowired
    private PersonService personService;

    @Override
    public Friend createFriendConnection(String email1, String email2) {
        log.debug("[createFriendConnection]-email1={}, email2={}", email1, email2);
        Person p1 = personService.findByEmailOrThrowEmailNotRegisteredApiException(email1);
        Person p2 = personService.findByEmailOrThrowEmailNotRegisteredApiException(email2);
        Friend friend = new Friend();
        // set lower Id to person1 and higher Id to person2
        if (p1.getId() < p2.getId()) {
            friend.setPerson1(p1);
            friend.setPerson2(p2);
        } else {
            friend.setPerson1(p2);
            friend.setPerson2(p1);
        }
        if (friendRepo.exists(Example.of(friend))) {
            throw new FriendConnectionRequestRepeatedApiException();
        }
        return friendRepo.save(friend);
    }

    @Override
    public List<String> retrieveFriends(String email) {
        log.debug("[retrieveFriendList]-email={}", email);
        personService.findByEmailOrThrowEmailNotRegisteredApiException(email);
        return friendRepo.findAllFriendsEmail(email);
    }

    @Override
    public List<String> retrieveCommonFriends(String email1, String email2) {
        log.debug("[retrieveCommonFriends]-email1={}, email2={}", email1, email2);
        personService.findByEmailOrThrowEmailNotRegisteredApiException(email1);
        personService.findByEmailOrThrowEmailNotRegisteredApiException(email2);
        List<String> friends1 = friendRepo.findAllFriendsEmail(email1);
        List<String> friends2 = friendRepo.findAllFriendsEmail(email2);
        return friends1.stream()
                .filter(s -> friends2.contains(s))
                .collect(Collectors.toList());
    }
}
