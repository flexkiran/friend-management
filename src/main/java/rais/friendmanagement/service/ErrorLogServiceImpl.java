package rais.friendmanagement.service;

import java.io.PrintWriter;
import java.io.StringWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rais.friendmanagement.dao.ErrorLog;
import rais.friendmanagement.dao.ErrorLogRepo;

/**
 *
 * @author Muhammad Rais Rahim <rais.gowa@gmail.com>
 */
@Service
public class ErrorLogServiceImpl implements ErrorLogService {

    @Autowired
    private ErrorLogRepo repo;

    @Override
    public ErrorLog saveException(Exception ex) {
        ErrorLog log = new ErrorLog();
        log.setStackTrace(createStackTrace(ex));
        return repo.save(log);
    }

    private String createStackTrace(Exception ex) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        return sw.toString();
    }
}
