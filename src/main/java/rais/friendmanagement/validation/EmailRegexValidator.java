package rais.friendmanagement.validation;

import java.util.regex.Pattern;
import lombok.Getter;
import org.springframework.stereotype.Component;

/**
 *
 * @author Muhammad Rais Rahim <rais.gowa@gmail.com>
 */
@Component
public class EmailRegexValidator {

    @Getter
    private Pattern emailPattern = Pattern.compile("[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+");

    public boolean isValidEmail(String email) {
        return emailPattern.matcher(email).matches();
    }

}
