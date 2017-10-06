package rais.friendmanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author Muhammad Rais Rahim <rais.gowa@gmail.com>
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidRequestApiException extends ApiException {

    private static final long serialVersionUID = -8446882108638525978L;
    public static final String CODE = "902";

    public InvalidRequestApiException() {
        super(CODE);
    }

    public InvalidRequestApiException(Throwable cause) {
        super(CODE, cause);
    }
}
