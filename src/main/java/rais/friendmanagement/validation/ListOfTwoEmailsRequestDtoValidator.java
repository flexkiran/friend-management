package rais.friendmanagement.validation;

import java.util.HashSet;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import rais.friendmanagement.exception.InvalidEmailRequestApiException;
import rais.friendmanagement.exception.NotUniqueRequestApiException;
import rais.friendmanagement.rest.dto.request.ListOfTwoEmailsRequestDto;

/**
 *
 * @author Muhammad Rais Rahim <rais.gowa@gmail.com>
 */
@Component
@Slf4j
public class ListOfTwoEmailsRequestDtoValidator implements Validator {

    @Autowired
    private EmailRegexValidator emailRegexValidator;

    @Override
    public boolean supports(Class<?> type) {
        boolean r = ListOfTwoEmailsRequestDto.class.isAssignableFrom(type);
        log.debug("[supports]-{} is {}", type, r);
        return r;
    }

    @Override
    public void validate(Object o, Errors errors) {
        log.debug("[validate]-{}", o);
        ListOfTwoEmailsRequestDto req = (ListOfTwoEmailsRequestDto) o;
        if (req.getFriends() != null) {
            Set set = new HashSet();
            for (int i = 0; i < req.getFriends().size(); i++) {
                String email = req.getFriends().get(i);
                if (emailRegexValidator.isValidEmail(email) == false) {
                    new InvalidEmailRequestApiException().bindTo(errors, "friends[" + i + "]", q(email));
                }
                if (set.add(email) == false) {
                    new NotUniqueRequestApiException().bindTo(errors, "friends[" + i + "]", q(email));
                }
            }
        }
    }

    /**
     * Single quote
     */
    private String q(String s) {
        return "'" + s + "'";
    }

}
