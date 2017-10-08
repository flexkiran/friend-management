package rais.friendmanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author Muhammad Rais Rahim <rais.gowa@gmail.com>
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class BlockingRequestRepeatedApiException extends ApiException {

    private static final long serialVersionUID = -3617385901263549297L;

    public static final String CODE = "907";

    public BlockingRequestRepeatedApiException() {
        super(CODE);
    }

}
