package rais.friendmanagement.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Muhammad Rais Rahim <rais.gowa@gmail.com>
 */
@Repository
public interface SubscriptionRepo extends JpaRepository<Subscription, Subscription.SubscribePk> {

}
