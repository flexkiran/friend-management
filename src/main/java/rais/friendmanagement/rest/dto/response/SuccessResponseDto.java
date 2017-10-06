package rais.friendmanagement.rest.dto.response;

import org.springframework.http.HttpStatus;

/**
 *
 * @author Muhammad Rais Rahim <rais.gowa@gmail.com>
 */
public class SuccessResponseDto extends BaseResponseDto {

    public SuccessResponseDto() {
        super(true, HttpStatus.OK);
    }

}
