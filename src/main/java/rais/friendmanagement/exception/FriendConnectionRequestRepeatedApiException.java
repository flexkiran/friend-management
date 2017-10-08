package rais.friendmanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author Muhammad Rais Rahim <rais.gowa@gmail.com>
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class FriendConnectionRequestRepeatedApiException extends ApiException {

    private static final long serialVersionUID = 4242624465953225178L;
    public static final String CODE = "905";

    public FriendConnectionRequestRepeatedApiException() {
        super(CODE);
    }

}
