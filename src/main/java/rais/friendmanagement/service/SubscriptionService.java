package rais.friendmanagement.service;

import java.util.List;
import rais.friendmanagement.dao.Subscription;

/**
 *
 * @author Muhammad Rais Rahim <rais.gowa@gmail.com>
 */
public interface SubscriptionService {

    Subscription subscribe(String emailTarget, String emailRequestor);

    List<String> retrieveSubscribers(String email);

}
