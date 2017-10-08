package rais.friendmanagement.rest;

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
import rais.friendmanagement.rest.dto.response.SuccessResponseDto;
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
        return new SuccessResponseDto();
    }
}
