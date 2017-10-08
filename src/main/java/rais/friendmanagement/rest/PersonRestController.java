package rais.friendmanagement.rest;

import rais.friendmanagement.rest.dto.request.SingleEmailRequestDto;
import rais.friendmanagement.rest.dto.response.SuccessResponseDto;
import rais.friendmanagement.rest.dto.response.BaseResponseDto;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rais.friendmanagement.service.PersonService;

/**
 *
 * @author Muhammad Rais Rahim <rais.gowa@gmail.com>
 */
@RestController
@RequestMapping("/person")
@Slf4j
public class PersonRestController {

    @Autowired
    private PersonService personService;

    @PostMapping("/register")
    public BaseResponseDto register(@RequestBody @Valid SingleEmailRequestDto req) {
        log.debug("[register]-request={}", req);
        personService.register(req.getEmail());
        return new SuccessResponseDto();
    }

}
