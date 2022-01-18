package xyz.bumbing.api.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import xyz.bumbing.api.controller.dto.ErrorResponse;
import xyz.bumbing.api.exception.ErrorCode;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
 * 인증안된 상태로  들어갈때 인증 필요한 url 들어갈때, RestAPI에서는 필수
 * */
@Component
@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper om;

    public CustomAuthenticationEntryPoint(ObjectMapper om) {
        this.om = om;
    }


    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        log.debug("CustomAuthenticationEntryPoint - start");
        log.info("#########dd#");


        //401 응답
        ErrorResponse er = ErrorResponse.of(ErrorCode.HANDLE_NO_AUTHENTICATION);

        // 실패코드 403
        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        // json 리턴 및 한글깨짐 수정.
        response.setContentType("application/json;charset=utf-8");

        response.getOutputStream().println(this.om.writeValueAsString(er));

        log.debug("CustomAuthenticationEntryPoint - end");

    }

}