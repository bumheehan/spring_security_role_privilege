package xyz.bumbing.api.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import xyz.bumbing.api.controller.dto.ErrorResponse;
import xyz.bumbing.api.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 스프링 시큐리티 403 핸들러
 */
@Component
@Slf4j
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper om;

    public CustomAccessDeniedHandler(ObjectMapper om) {
        this.om = om;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        log.debug("CustomAccessDeniedHandler - start");

        //403
        //401 응답
        ErrorResponse er = ErrorResponse.of(ErrorCode.HANDLE_ACCESS_DENIED);

        // 실패코드 403
        response.setStatus(HttpStatus.FORBIDDEN.value());

        // json 리턴 및 한글깨짐 수정.
        response.setContentType("application/json;charset=utf-8");

        response.getOutputStream().println(this.om.writeValueAsString(er));

        log.debug("CustomAccessDeniedHandler - end");
    }

}