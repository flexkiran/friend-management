package rais.friendmanagement.rest.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * @author Muhammad Rais Rahim <rais.gowa@gmail.com>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class UpdatesResponseDto extends SuccessResponseDto {

    private List<String> recipients;
}
