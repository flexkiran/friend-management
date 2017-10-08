package rais.friendmanagement.service;

import rais.friendmanagement.dao.Subscription;

/**
 *
 * @author Muhammad Rais Rahim <rais.gowa@gmail.com>
 */
public interface SubscriptionService {

    Subscription subscribe(String emailTarget, String emailRequestor);

}
