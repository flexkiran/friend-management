package rais.friendmanagement.rest.dto.response;

import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * Base Api Response Data. Every Api response should return this or extend this
 * class
 *
 * @author Muhammad Rais Rahim <rais.gowa@gmail.com>
 */
@Data
public class BaseResponseDto {

    private final long timestamp = System.currentTimeMillis();
    private final boolean success;
    // http status
    private int status;

    public BaseResponseDto(boolean success, HttpStatus httpStatus) {
        this.success = success;
        this.status = httpStatus.value();
    }

}
