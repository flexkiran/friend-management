package rais.friendmanagement.rest.dto.response;

import java.util.List;
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
    // error info page link
    private String errorInfo;
    // validation errors
    private List<ValidationError> validationErrors;

    @Data
    public static class ValidationError {

        private String object;
        private String field;
        private Object rejectedValue;
        private String message;
        private String lang;
    }
}
