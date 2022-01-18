package xyz.bumbing.api.controller.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import xyz.bumbing.api.controller.dto.ErrorResponse;
import xyz.bumbing.api.exception.ErrorCode;
import xyz.bumbing.api.exception.UserException;


@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * javax.validation.Valid or @Validated 으로 binding error 발생시 발생한다.
     * HttpMessageConverter 에서 등록한 HttpMessageConverter binding 못할경우 발생
     * 주로 @RequestBody, @RequestPart 어노테이션에서 발생
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("handleMethodArgumentNotValidException", e);
        return ErrorResponse.of(ErrorCode.INVALID_INPUT_VALUE, e.getBindingResult());
    }

    /**
     * @ModelAttribute 으로 binding error 발생시 BindException 발생한다.
     * ref https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#mvc-ann-modelattrib-method-args
     */
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorResponse handleBindException(BindException e) {
        log.error("handleBindException", e);
        return ErrorResponse.of(ErrorCode.INVALID_INPUT_VALUE, e.getBindingResult());
    }

    /**
     * enum type 일치하지 않아 binding 못할 경우 발생
     * 주로 @RequestParam enum으로 binding 못했을 경우 발생
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorResponse handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.error("handleMethodArgumentTypeMismatchException", e);
        return ErrorResponse.of(e);
    }

    /**
     * 지원하지 않은 HTTP method 호출 할 경우 발생
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    protected ErrorResponse handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("handleHttpRequestMethodNotSupportedException", e);
        return ErrorResponse.of(ErrorCode.METHOD_NOT_ALLOWED);
    }

    /**
     * 권한 에러
     */
    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException e) {
        log.error("handleAccessDeniedException", e.getMessage());
        return ResponseEntity
                .status(ErrorCode.HANDLE_ACCESS_DENIED.getStatus())
                .body(ErrorResponse.of(ErrorCode.HANDLE_ACCESS_DENIED));
    }

    /**
     * Mora Service  핸들링
     */
    @ExceptionHandler(UserException.class)
    protected ResponseEntity<ErrorResponse> handleMoraException(final UserException e) {
        log.error("Business Exception", e);
        return ResponseEntity
                .status(e.getErrorCode().getStatus())
                .body(ErrorResponse.of(e.getErrorCode()));
    }


    /**
     * 글로벌 에러처리
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ErrorResponse handleException(Exception e) {
        log.error("Global Exception", e);
        return ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR);
    }

}