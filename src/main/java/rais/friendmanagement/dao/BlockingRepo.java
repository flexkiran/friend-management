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
public interface BlockingRepo extends JpaRepository<Blocking, Blocking.BlockPk> {

    @Query("select b.requestor.email"
            + " from Blocking b"
            + " join b.requestor r"
            + " join b.target t"
            + " where t.email = ?1")
    List<String> findAllRequestorEmailsByEmailTarget(String emailTarget);

}
