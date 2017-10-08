package rais.friendmanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author Muhammad Rais Rahim <rais.gowa@gmail.com>
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class BlockedFriendApiException extends ApiException {

    private static final long serialVersionUID = -758361689388301997L;
    public static final String CODE = "908";

    public BlockedFriendApiException() {
        super(CODE);
    }
}
