package rais.friendmanagement.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author Muhammad Rais Rahim <rais.gowa@gmail.com>
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
@NoArgsConstructor
@Getter
@Slf4j
public class ApiException extends RuntimeException {

    private static final long serialVersionUID = -2193200205752285811L;

    public ApiException(Throwable cause) {
        super(cause);
    }

}
