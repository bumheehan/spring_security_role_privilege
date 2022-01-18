package xyz.bumbing.api.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import xyz.bumbing.api.exception.ErrorCode;
import xyz.bumbing.api.exception.UserException;
import xyz.bumbing.api.security.JwtAuthenticationProvider;
import xyz.bumbing.api.security.dto.SingleTokenDto;
import xyz.bumbing.api.service.AuthService;
import xyz.bumbing.api.service.dto.SigninDto;
import xyz.bumbing.domain.entity.SignIn;
import xyz.bumbing.domain.entity.User;
import xyz.bumbing.domain.repo.SignInRepository;
import xyz.bumbing.domain.repo.UserRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    private final SignInRepository signInRepository;
    private final PasswordEncoder passwordEncoder;

    private final JwtAuthenticationProvider jwtProvider;

    @Override
    @Transactional
    public SigninDto signIn(String email, String password) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserException(ErrorCode.LOGIN_INPUT_INVALID));
        boolean isValid = passwordEncoder.matches(password, user.getPassword());

        if (!isValid) {
            throw new UserException(ErrorCode.LOGIN_INPUT_INVALID);
        }
        SingleTokenDto accessTokenDto = jwtProvider.generateAccessToken(user.getUserRoles().stream().map(s -> s.getRole().getName()).collect(Collectors.toList())
                , user.getId());
        SingleTokenDto refreshTokenDto = jwtProvider.generateRefreshToken(user.getUserRoles().stream().map(s -> s.getRole().getName()).collect(Collectors.toList())
                , user.getId());

        SignIn signIn = SignIn.signIn(refreshTokenDto.getToken(), getClientIpAddress(), null, getClientDevice());
        user.setSignIn(signIn);

        return SigninDto.builder().accessToken(accessTokenDto.getToken()).refreshToken(refreshTokenDto.getToken()).id(user.getId()).build();
    }

    @Override
    public SigninDto signIn(String refreshToken) {
        SignIn signIn = signInRepository.findByRefreshToken(refreshToken).orElseThrow(() -> new UserException(ErrorCode.LOGIN_INPUT_INVALID));
        User user = signIn.getUser();
        SingleTokenDto accessTokenDto = jwtProvider.generateAccessToken(user.getUserRoles().stream().map(s -> s.getRole().getName()).collect(Collectors.toList())
                , user.getId());

        return SigninDto.builder().accessToken(accessTokenDto.getToken()).refreshToken(refreshToken).id(user.getId()).build();
    }

    private String getClientIpAddress() {
        String addr = "";
        if (RequestContextHolder.getRequestAttributes() != null) {
            HttpServletRequest servletRequest = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            addr = servletRequest.getRemoteAddr();
        }
        return addr;
    }

    private String getClientDevice() {
        String device = "";
        if (RequestContextHolder.getRequestAttributes() != null) {
            HttpServletRequest servletRequest = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            device = servletRequest.getHeader("user-agent");
        }
        return device;
    }
}
