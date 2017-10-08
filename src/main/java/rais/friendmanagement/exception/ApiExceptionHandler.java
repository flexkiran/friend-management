package rais.friendmanagement.exception;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import rais.friendmanagement.rest.dto.response.ExceptionResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import rais.friendmanagement.dao.ErrorLog;
import rais.friendmanagement.rest.dto.response.ExceptionResponseDto.ValidationError;
import rais.friendmanagement.service.ErrorLogService;

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

    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    protected ResponseEntity handleRuntimeException(RuntimeException ex, WebRequest req) {
        log.debug("[handleRuntimeException]-");
        return createResponseEntity(ex, req);
    }

    @Autowired
    private ErrorLogService errorLogService;

    private ResponseEntity createResponseEntity(Exception ex, WebRequest req) {
        log.debug("[createResponseEntity]-class={}", ex.getClass());
        ExceptionResponseDto body = new ExceptionResponseDto();
        Locale locale = resolveLocale(req);
        if (ex instanceof ApiException) {
            ApiException ax = (ApiException) ex;
            body.setError(ax.getErrorCode());
            body.setMessage(ax.getLocalizedMessage(locale));
            body.setLang(locale.getLanguage());
        } else {
            // Unexpected Exception
            body.setError("900");
            body.setMessage(ex.getMessage());
        }
        // set error info page link
        String errorInfo = "/errors/" + body.getError() + "?lang=" + locale.getLanguage();
        // save stacktrace for unexpected exception and add it to error info link
        if ("900".equals(body.getError())) {
            ErrorLog errorLog = errorLogService.saveException(ex);
            errorInfo += "&logId=" + errorLog.getId();
        }
        body.setErrorInfo(errorInfo);

        // from handleMethodArgumentNotValid
        if (ex.getCause() instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException manve = (MethodArgumentNotValidException) ex.getCause();
            List validationErrors = manve.getBindingResult().getAllErrors().stream()
                    .map(err -> createValidationError(err, req))
                    .collect(Collectors.toList());
            body.setValidationErrors(validationErrors);
        }
        HttpStatus httpStatus = resolveAnnotatedResponseStatus(ex);
        body.setStatus(httpStatus.value());
        return new ResponseEntity(body, new HttpHeaders(), httpStatus);
    }

    private ValidationError createValidationError(ObjectError err, WebRequest req) {
        ValidationError ve = new ValidationError();
        ve.setObject(err.getObjectName());
        Locale locale = resolveLocale(req);
        if (err instanceof FieldError) {
            FieldError fe = (FieldError) err;
            ve.setField(fe.getField());
            ve.setRejectedValue(fe.getRejectedValue());
            ve.setMessage(resolveErrorMessage(locale, fe.getCode(), fe.getArguments()));
        }
        ve.setLang(locale.getLanguage());
        if (ve.getMessage() == null) {
            ve.setMessage(err.getDefaultMessage());
        }
        return ve;
    }

    private String resolveErrorMessage(Locale locale, String errorCode, Object... args) {
        String msg = null;
        ResourceBundle rb = ApiException.getResourceBundle(locale);
        if (rb.containsKey(errorCode)) {
            msg = rb.getString(errorCode);
            log.debug("[resolveErrorMessage]-args={}, msg={}", Arrays.asList(args), msg);
            if (args != null) {
                msg = MessageFormat.format(msg, args);
            }
        } else if (rb.containsKey(InvalidRequestApiException.CODE + "." + errorCode)) {
            // resolve @javax.validation.constraints.*
            msg = resolveValidationErrorMessage(rb, errorCode, args);
        } else {
            log.warn("[resolveErrorMessage]-resource bundle does not contain key {}", errorCode);
        }
        return msg;
    }

    private String resolveValidationErrorMessage(ResourceBundle rb, String errorCode, Object... args) {
        // exclude DefaultMessageSourceResolvable
        List list = Arrays.asList(args).stream()
                .peek(a -> log.debug("[resolveValidationErrorMessage]-errorCode={}, class={}, tostring={}", errorCode, a.getClass(), a))
                .filter(a -> !(a instanceof DefaultMessageSourceResolvable))
                .collect(Collectors.toList());
        String msg = rb.getString(InvalidRequestApiException.CODE + "." + errorCode);
        if (list.isEmpty()) {
            return msg;
        }
        return MessageFormat.format(msg, list.toArray());
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
