package rais.friendmanagement.service;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import rais.friendmanagement.dao.Subscription;
import rais.friendmanagement.dao.SubscriptionRepo;
import rais.friendmanagement.exception.SubscriptionRequestRepeatedApiException;

/**
 *
 * @author Muhammad Rais Rahim <rais.gowa@gmail.com>
 */
@Service
@Slf4j
public class SubscriptionServiceImpl implements SubscriptionService {

    @Autowired
    private SubscriptionRepo subscriptionRepo;
    @Autowired
    private PersonService personService;

    @Override
    public Subscription subscribe(String emailTarget, String emailRequestor) {
        log.debug("[subscribe]-emailTarget={}, emailRequestor={}", emailTarget, emailRequestor);
        Subscription subs = new Subscription();
        subs.setTarget(personService.findByEmailOrThrowEmailNotRegisteredApiException(emailTarget));
        subs.setRequestor(personService.findByEmailOrThrowEmailNotRegisteredApiException(emailRequestor));
        if (subscriptionRepo.exists(Example.of(subs))) {
            throw new SubscriptionRequestRepeatedApiException();
        }
        return subscriptionRepo.save(subs);
    }

    @Override
    public List<String> retrieveSubscribers(String email) {
        log.debug("[retrieveSubscribers]-email={}", email);
        return subscriptionRepo.findAllRequestorEmailsByEmailTarget(email);
    }

}
