package rais.friendmanagement.rest;

import java.util.List;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rais.friendmanagement.rest.dto.request.ListOfTwoEmailsRequestDto;
import rais.friendmanagement.rest.dto.request.SingleEmailRequestDto;
import rais.friendmanagement.rest.dto.request.TargetRequestorRequestDto;
import rais.friendmanagement.rest.dto.response.BaseResponseDto;
import rais.friendmanagement.rest.dto.response.ListEmailResponseDto;
import rais.friendmanagement.rest.dto.response.SuccessResponseDto;
import rais.friendmanagement.service.FriendConnectionService;
import rais.friendmanagement.service.SubscriptionService;
import rais.friendmanagement.validation.ListOfTwoEmailsRequestDtoValidator;

/**
 *
 * @author Muhammad Rais Rahim <rais.gowa@gmail.com>
 */
@RestController
@RequestMapping("/friend")
@Slf4j
public class FriendRestController {

    @Autowired
    private FriendConnectionService friendService;
    @Autowired
    private SubscriptionService subscriptionService;
    @Autowired
    private ListOfTwoEmailsRequestDtoValidator listOfTwoEmailRequestDtoValidator;

    // bind validator for ListOfTwoEmailsRequestDto
    @InitBinder("listOfTwoEmailsRequestDto")
    void initBinder(WebDataBinder binder) {
        log.debug("[initBinder]-objectName={}", binder.getObjectName());
        binder.addValidators(listOfTwoEmailRequestDtoValidator);
    }

    @PostMapping("/connect")
    public SuccessResponseDto connect(@RequestBody @Valid ListOfTwoEmailsRequestDto req) {
        log.debug("[connect]-request={}", req);
        friendService.createFriendConnection(req.getFriends().get(0), req.getFriends().get(1));
        return new SuccessResponseDto();
    }

    @PostMapping("/list")
    public ListEmailResponseDto retrieveFriendList(@RequestBody @Valid SingleEmailRequestDto req) {
        log.debug("[retrieveFriendList]-request={}", req);
        List<String> friends = friendService.retrieveFriends(req.getEmail());
        return new ListEmailResponseDto(friends);
    }

    @PostMapping("/common")
    public ListEmailResponseDto retrieveCommonFriends(@RequestBody @Valid ListOfTwoEmailsRequestDto req) {
        log.debug("[retrieveCommonFriends]-request={}", req);
        List<String> friends = friendService.retrieveCommonFriends(req.getFriends().get(0), req.getFriends().get(1));
        return new ListEmailResponseDto(friends);
    }

    @PostMapping("/subscribe")
    public BaseResponseDto subscribe(@RequestBody @Valid TargetRequestorRequestDto req) {
        log.debug("[subscribe]-request={}", req);
        subscriptionService.subscribe(req.getTarget(), req.getRequestor());
        return new SuccessResponseDto();
    }
}
