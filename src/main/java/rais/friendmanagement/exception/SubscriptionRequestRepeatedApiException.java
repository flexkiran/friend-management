package rais.friendmanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author Muhammad Rais Rahim <rais.gowa@gmail.com>
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class SubscriptionRequestRepeatedApiException extends ApiException {

    private static final long serialVersionUID = 6501592501994631115L;

    public static final String CODE = "906";

    public SubscriptionRequestRepeatedApiException() {
        super(CODE);
    }

}
