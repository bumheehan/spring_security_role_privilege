package xyz.bumbing.api.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import xyz.bumbing.api.service.impl.UserDetailsServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;


@Slf4j
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final ObjectMapper om;

    public CustomAuthenticationSuccessHandler(ObjectMapper om) {
        this.om = om;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.debug("CustomAuthenticationSuccessHandler - start");

        log.debug("principal : " + authentication.getPrincipal());
        log.debug("password : " + authentication.getCredentials());
        log.debug("authorities : " + authentication.getAuthorities().stream().map(s -> s.getAuthority()).collect(Collectors.joining(",")));
        log.debug("uid : " + ((UserDetailsServiceImpl.CustomUserDetails) authentication.getPrincipal()).getUid());
        response.setStatus(HttpStatus.OK.value());

        // json 리턴 및 한글깨짐 수정.
        response.setContentType("application/json;charset=utf-8");

        response.getOutputStream().println(this.om.writeValueAsString("OK"));

        log.debug("CustomAuthenticationSuccessHandler - end");

    }
}

//
//    @Override
//    @Transactional
//    public SigninDto signIn(String email, String password) {
//        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserException(ErrorCode.LOGIN_INPUT_INVALID));
//        boolean isValid = passwordEncoder.matches(password, user.getPassword());
//
//        if (!isValid) {
//            throw new UserException(ErrorCode.LOGIN_INPUT_INVALID);
//        }
//        SingleTokenDto accessTokenDto = jwtProvider.generateAccessToken(user.getUserRoles().stream().map(s -> s.getRole().getName()).collect(Collectors.toList())
//                , user.getId());
//        SingleTokenDto refreshTokenDto = jwtProvider.generateRefreshToken(user.getUserRoles().stream().map(s -> s.getRole().getName()).collect(Collectors.toList())
//                , user.getId());
//
//        SignIn signIn = SignIn.signIn(refreshTokenDto.getToken(), getClientIpAddress(), null, getClientDevice());
//        user.setSignIn(signIn);
//
//        return SigninDto.builder().accessToken(accessTokenDto.getToken()).refreshToken(refreshTokenDto.getToken()).id(user.getId()).build();
//    }
//
//    @Override
//    public SigninDto signIn(String refreshToken) {
//        SignIn signIn = signInRepository.findByRefreshToken(refreshToken).orElseThrow(() -> new UserException(ErrorCode.LOGIN_INPUT_INVALID));
//        User user = signIn.getUser();
//        SingleTokenDto accessTokenDto = jwtProvider.generateAccessToken(user.getUserRoles().stream().map(s -> s.getRole().getName()).collect(Collectors.toList())
//                , user.getId());
//
//        return SigninDto.builder().accessToken(accessTokenDto.getToken()).refreshToken(refreshToken).id(user.getId()).build();
//    }
//
//    private String getClientIpAddress() {
//        String addr = "";
//        if (RequestContextHolder.getRequestAttributes() != null) {
//            HttpServletRequest servletRequest = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//            addr = servletRequest.getRemoteAddr();
//        }
//        return addr;
//    }
//
//    private String getClientDevice() {
//        String device = "";
//        if (RequestContextHolder.getRequestAttributes() != null) {
//            HttpServletRequest servletRequest = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//            device = servletRequest.getHeader("user-agent");
//        }
//        return device;
//    }