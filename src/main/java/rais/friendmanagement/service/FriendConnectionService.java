package rais.friendmanagement.service;

import java.util.List;
import rais.friendmanagement.dao.Friend;

/**
 *
 * @author Muhammad Rais Rahim <rais.gowa@gmail.com>
 */
public interface FriendConnectionService {

    Friend createFriendConnection(String email1, String email2);

    List<String> retrieveFriends(String email);
}
