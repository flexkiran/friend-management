package rais.friendmanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author Muhammad Rais Rahim <rais.gowa@gmail.com>
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class EmailNotRegisteredApiException extends ApiException {

    private static final long serialVersionUID = -3972244432876685585L;
    public static final String CODE = "904";

    public EmailNotRegisteredApiException() {
        super(CODE);
    }

}
