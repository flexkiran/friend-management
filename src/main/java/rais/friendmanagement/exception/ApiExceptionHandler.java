package rais.friendmanagement.exception;

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
        return handleApiException(new UnreadableRequestApiException(ex), req);
    }

    /**
     * Thrown from Validator
     */
    @Override
    protected ResponseEntity handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest req) {
        log.debug("[handleMethodArgumentNotValid]-");
        InvalidRequestApiException apiExc = new InvalidRequestApiException(ex);
        return handleApiException(apiExc, req);
    }

    /**
     * Thrown from Services class
     */
    @ExceptionHandler(ApiException.class)
    @ResponseBody
    protected ResponseEntity handleApiException(ApiException ex, WebRequest req) {
        log.debug("[handleApiException]-");
        HttpStatus httpStatus = resolveAnnotatedResponseStatus(ex);
        ExceptionResponseDto body = new ExceptionResponseDto();
        body.setStatus(httpStatus.value());
        return new ResponseEntity(body, new HttpHeaders(), httpStatus);
    }

    private HttpStatus resolveAnnotatedResponseStatus(Exception ex) {
        ResponseStatus status = AnnotatedElementUtils.findMergedAnnotation(ex.getClass(), ResponseStatus.class);
        return (status != null) ? status.value() : HttpStatus.INTERNAL_SERVER_ERROR;
    }

}
