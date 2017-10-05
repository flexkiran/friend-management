package rais.friendmanagement.exception;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author Muhammad Rais Rahim <rais.gowa@gmail.com>
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
@NoArgsConstructor
public class InvalidRequestApiException extends ApiException {

    private static final long serialVersionUID = -8446882108638525978L;

    public InvalidRequestApiException(Throwable cause) {
        super(cause);
    }

}
