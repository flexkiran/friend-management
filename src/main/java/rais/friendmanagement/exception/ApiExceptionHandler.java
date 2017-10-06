package rais.friendmanagement.exception;

import java.util.Locale;
import rais.friendmanagement.rest.dto.response.ExceptionResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Encapsulate response into ExceptionResponseDto
 *
 * @author Muhammad Rais Rahim <rais.gowa@gmail.com>
 */
@ControllerAdvice
@Slf4j
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Thrown from Jackson parser
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest req) {
        log.debug("[handleHttpMessageNotReadable]-");
        return createResponseEntity(new UnreadableRequestApiException(ex), req);
    }

    /**
     * Thrown from Validator
     */
    @Override
    protected ResponseEntity handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest req) {
        log.debug("[handleMethodArgumentNotValid]-");
        return createResponseEntity(new InvalidRequestApiException(ex), req);
    }

    /**
     * Thrown from Services class
     */
    @ExceptionHandler(ApiException.class)
    @ResponseBody
    protected ResponseEntity handleApiException(ApiException ex, WebRequest req) {
        log.debug("[handleApiException]-");
        return createResponseEntity(ex, req);
    }

    private ResponseEntity createResponseEntity(Exception ex, WebRequest req) {
        log.debug("[createResponseEntity]-class={}", ex.getClass());
        ExceptionResponseDto body = new ExceptionResponseDto();
        if (ex instanceof ApiException) {
            ApiException ax = (ApiException) ex;
            body.setError(ax.getErrorCode());
            Locale locale = resolveLocale(req);
            body.setMessage(ax.getLocalizedMessage(locale));
            body.setLang(locale.getLanguage());
        } else {
            // Unexpected Exception
            body.setError("900");
            body.setMessage(ex.getMessage());
        }
        HttpStatus httpStatus = resolveAnnotatedResponseStatus(ex);
        body.setStatus(httpStatus.value());
        return new ResponseEntity(body, new HttpHeaders(), httpStatus);
    }

    /**
     * resolve locale using the request header 'Accept-Language'
     */
    private Locale resolveLocale(WebRequest req) {
        String lang = req.getHeader(HttpHeaders.ACCEPT_LANGUAGE);
        return lang == null ? Locale.getDefault() : new Locale(lang);
    }

    private HttpStatus resolveAnnotatedResponseStatus(Exception ex) {
        ResponseStatus status = AnnotatedElementUtils.findMergedAnnotation(ex.getClass(), ResponseStatus.class);
        return (status != null) ? status.value() : HttpStatus.INTERNAL_SERVER_ERROR;
    }

}
