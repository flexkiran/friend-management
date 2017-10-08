package rais.friendmanagement.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import rais.friendmanagement.dao.Blocking;
import rais.friendmanagement.dao.BlockingRepo;
import rais.friendmanagement.exception.BlockingRequestRepeatedApiException;

/**
 *
 * @author Muhammad Rais Rahim <rais.gowa@gmail.com>
 */
@Service
@Slf4j
public class BlockingServiceImpl implements BlockingService {

    @Autowired
    private BlockingRepo blockingRepo;
    @Autowired
    private PersonService personService;

    @Override
    public Blocking block(String emailTarget, String emailRequestor) {
        log.debug("[block]-emailTarget={}, emailRequestor={}", emailTarget, emailRequestor);
        Blocking blocking = new Blocking();
        blocking.setTarget(personService.findByEmailOrThrowEmailNotRegisteredApiException(emailTarget));
        blocking.setRequestor(personService.findByEmailOrThrowEmailNotRegisteredApiException(emailRequestor));
        if (blockingRepo.exists(Example.of(blocking))) {
            throw new BlockingRequestRepeatedApiException();
        }
        return blockingRepo.save(blocking);
    }

    @Override
    public boolean isBlocked(String emailTarget, String emailRequestor) {
        Blocking blocking = new Blocking();
        blocking.setTarget(personService.findByEmailOrThrowEmailNotRegisteredApiException(emailTarget));
        blocking.setRequestor(personService.findByEmailOrThrowEmailNotRegisteredApiException(emailRequestor));
        boolean blocked = blockingRepo.exists(Example.of(blocking));
        log.debug("[isBlocked]-emailTarget={}, emailRequestor={}, blocked={}", emailTarget, emailRequestor, blocked);
        return blocked;
    }
}
