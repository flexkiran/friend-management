package rais.friendmanagement.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Muhammad Rais Rahim <rais.gowa@gmail.com>
 */
@Repository
public interface FriendRepo extends JpaRepository<Friend, Friend.FriendPk> {

    @Query(nativeQuery = true, value
            = "select p2.EMAIL \n"
            + "  from FRIEND f \n"
            + "  join PERSON p1 on f.PID1 = p1.ID \n"
            + "  join PERSON p2 on f.PID2 = p2.ID \n"
            + "  where p1.EMAIL = :email \n"
            + "union \n"
            + "select p1.EMAIL \n"
            + "  from FRIEND f \n"
            + "  join PERSON p1 on f.PID1 = p1.ID \n"
            + "  join PERSON p2 on f.PID2 = p2.ID \n"
            + "  where p2.EMAIL = :email")
    List<String> findAllFriendsEmail(@Param("email") String email);
}
