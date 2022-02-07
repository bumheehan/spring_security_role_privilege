package xyz.bumbing.api.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import xyz.bumbing.api.controller.dto.ErrorResponse;
import xyz.bumbing.api.exception.ErrorCode;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private final ObjectMapper om;

    public CustomAuthenticationFailureHandler(ObjectMapper om) {
        this.om = om;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        log.debug("CustomAuthenticationFailureHandler - start");


        //401 응답
        ErrorResponse er = ErrorResponse.of(ErrorCode.INVALID_INPUT_VALUE);

        // 실패코드 400
        response.setStatus(HttpStatus.BAD_REQUEST.value());

        // json 리턴 및 한글깨짐 수정.
        response.setContentType("application/json;charset=utf-8");

        response.getOutputStream().println(this.om.writeValueAsString(er));

        log.debug("CustomAuthenticationFailureHandler - end");


    }
}