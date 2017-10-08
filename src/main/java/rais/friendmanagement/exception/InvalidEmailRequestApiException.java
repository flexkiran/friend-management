package rais.friendmanagement.exception;

import lombok.Getter;

/**
 *
 * @author Muhammad Rais Rahim <rais.gowa@gmail.com>
 */
public class InvalidEmailRequestApiException extends InvalidRequestApiException {

    private static final long serialVersionUID = -2900859485396254676L;
    public static final String CODE = "902.1";
    @Getter
    private final String errorCode = CODE;

}
