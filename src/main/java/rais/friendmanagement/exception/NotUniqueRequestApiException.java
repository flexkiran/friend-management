package rais.friendmanagement.exception;

import lombok.Getter;

/**
 *
 * @author Muhammad Rais Rahim <rais.gowa@gmail.com>
 */
public class NotUniqueRequestApiException extends InvalidRequestApiException {

    private static final long serialVersionUID = -3337491517713872679L;
    public static final String CODE = "902.2";
    @Getter
    private final String errorCode = CODE;
}
