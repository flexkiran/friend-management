package rais.friendmanagement.rest;

import java.util.List;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rais.friendmanagement.rest.dto.request.UpdatesRequestDto;
import rais.friendmanagement.rest.dto.response.UpdatesResponseDto;
import rais.friendmanagement.service.UpdatesService;

/**
 *
 * @author Muhammad Rais Rahim <rais.gowa@gmail.com>
 */
@RestController
@RequestMapping("/updates")
@Slf4j
public class UpdatesRestController {

    @Autowired
    private UpdatesService updatesService;

    @PostMapping
    public UpdatesResponseDto post(@RequestBody @Valid UpdatesRequestDto req) {
        log.debug("[post]-request={}", req);
        List<String> recipients = updatesService.retrieveAllRecipients(req.getSender(), req.getText());
        return new UpdatesResponseDto(recipients);
    }
}
