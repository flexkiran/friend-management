package rais.friendmanagement.rest.dto.response;

import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author Muhammad Rais Rahim <rais.gowa@gmail.com>
 */
@Data
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ListEmailResponseDto extends SuccessResponseDto {

    @NonNull
    private List<String> friends;

    public int getCount() {
        return friends == null ? 0 : friends.size();
    }
}
