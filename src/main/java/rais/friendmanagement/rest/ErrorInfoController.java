package rais.friendmanagement.rest;

import java.util.Locale;
import java.util.MissingResourceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import rais.friendmanagement.dao.ErrorLogRepo;
import rais.friendmanagement.exception.ApiException;

/**
 *
 * @author Muhammad Rais Rahim <rais.gowa@gmail.com>
 */
@Controller
@RequestMapping("/errors")
public class ErrorInfoController {

    @Autowired
    private ErrorLogRepo errorLogRepo;

    @GetMapping("/{errorCode}")
    public String get(
            @PathVariable("errorCode") String code,
            @RequestParam(name = "lang", required = false) String lang,
            @RequestParam(name = "logId", required = false) Long logId,
            Model model) {
        model.addAttribute("errorCode", code);
        try {
            Locale locale = null;
            if (lang != null) {
                locale = new Locale(lang);
            }
            String message = ApiException.getResourceBundle(locale).getString(code);
            model.addAttribute("errorMessage", message);
        } catch (MissingResourceException mre) {
            model.addAttribute("errorMessage", "UNKNOWN_ERROR_CODE");
        }
        if (logId != null) {
            model.addAttribute("errorLog", errorLogRepo.findOne(logId));
        }
        return "errorInfo";
    }
}
