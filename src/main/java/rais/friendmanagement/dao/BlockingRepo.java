package rais.friendmanagement.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Muhammad Rais Rahim <rais.gowa@gmail.com>
 */
@Repository
public interface BlockingRepo extends JpaRepository<Blocking, Blocking.BlockPk> {

}
