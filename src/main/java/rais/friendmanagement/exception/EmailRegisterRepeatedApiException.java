package rais.friendmanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author Muhammad Rais Rahim <rais.gowa@gmail.com>
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class EmailRegisterRepeatedApiException extends ApiException {

    private static final long serialVersionUID = -4841591262205216974L;
    public static final String CODE = "903";

    public EmailRegisterRepeatedApiException() {
        super(CODE);
    }

}
