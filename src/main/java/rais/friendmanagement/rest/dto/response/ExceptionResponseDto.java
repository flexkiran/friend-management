package rais.friendmanagement.rest.dto.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

/**
 *
 * @author Muhammad Rais Rahim <rais.gowa@gmail.com>
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ExceptionResponseDto extends BaseResponseDto {

    public ExceptionResponseDto() {
        super(false, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // error code
    private String error;
    // error message
    private String message;
    // error message language
    private String lang;

}
