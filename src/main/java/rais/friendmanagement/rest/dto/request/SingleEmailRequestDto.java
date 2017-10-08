package rais.friendmanagement.rest.dto.request;

import javax.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Email;

/**
 *
 * @author Muhammad Rais Rahim <rais.gowa@gmail.com>
 */
@Data
public class SingleEmailRequestDto {

    @Email
    @NotNull
    private String email;
}
