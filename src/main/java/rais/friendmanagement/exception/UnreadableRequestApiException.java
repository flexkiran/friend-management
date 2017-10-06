package rais.friendmanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author Muhammad Rais Rahim <rais.gowa@gmail.com>
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UnreadableRequestApiException extends ApiException {

    private static final long serialVersionUID = -944601419774171201L;
    public static final String CODE = "901";

    public UnreadableRequestApiException() {
        super(CODE);
    }

    public UnreadableRequestApiException(Throwable cause) {
        super(CODE, cause);
    }
}
