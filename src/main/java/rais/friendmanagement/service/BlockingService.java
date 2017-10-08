package rais.friendmanagement.service;

import java.util.List;
import rais.friendmanagement.dao.Blocking;

/**
 *
 * @author Muhammad Rais Rahim <rais.gowa@gmail.com>
 */
public interface BlockingService {

    Blocking block(String emailTarget, String emailRequestor);

    boolean isBlocked(String emailTarget, String emailRequestor);

    List<String> getAllEmailsWhichAreBlockingTheEmailTarget(String emailRequestor);
}
