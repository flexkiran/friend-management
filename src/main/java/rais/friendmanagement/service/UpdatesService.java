package rais.friendmanagement.service;

import java.util.List;

/**
 *
 * @author Muhammad Rais Rahim <rais.gowa@gmail.com>
 */
public interface UpdatesService {

    List<String> retrieveAllRecipients(String sender, String text);

}
