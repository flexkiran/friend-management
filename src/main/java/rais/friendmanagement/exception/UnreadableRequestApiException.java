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
public class UnreadableRequestApiException extends ApiException {

    private static final long serialVersionUID = -944601419774171201L;

    public UnreadableRequestApiException(Throwable cause) {
        super(cause);
    }

}
