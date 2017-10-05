package rais.friendmanagement.rest.dto.request;

import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 *
 * @author Muhammad Rais Rahim <rais.gowa@gmail.com>
 */
@Data
public class ListOfTwoEmailsRequestDto {

    @NotNull
    @Size(min = 2, max = 2)
    private List<String> friends;
}
