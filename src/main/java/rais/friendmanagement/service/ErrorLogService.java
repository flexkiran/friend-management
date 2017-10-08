package rais.friendmanagement.service;

import rais.friendmanagement.dao.ErrorLog;

/**
 *
 * @author Muhammad Rais Rahim <rais.gowa@gmail.com>
 */
public interface ErrorLogService {

    ErrorLog saveException(Exception ex);
}
