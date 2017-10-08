package rais.friendmanagement.rest.dto.request;

import javax.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Email;

/**
 *
 * @author Muhammad Rais Rahim <rais.gowa@gmail.com>
 */
@Data
public class TargetRequestorRequestDto {

    @NotNull
    @Email
    private String requestor;
    @NotNull
    @Email
    private String target;
}
