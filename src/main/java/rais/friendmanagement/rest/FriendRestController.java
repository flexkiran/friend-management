package rais.friendmanagement.rest;

import com.google.common.collect.ImmutableMap;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rais.friendmanagement.rest.dto.request.ListOfTwoEmailsRequestDto;

/**
 *
 * @author Muhammad Rais Rahim <rais.gowa@gmail.com>
 */
@RestController
@RequestMapping("/friend")
@Slf4j
public class FriendRestController {

    @PostMapping("/connect")
    public ResponseEntity connect(@RequestBody @Valid ListOfTwoEmailsRequestDto req) {
        log.debug("[connect]-request={}", req);
        return ResponseEntity.ok(ImmutableMap.of("success", true));
    }
}
