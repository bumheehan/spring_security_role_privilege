package xyz.bumbing.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import xyz.bumbing.api.controller.dto.Response;
import xyz.bumbing.api.service.AuthService;
import xyz.bumbing.api.service.dto.SigninDto;

@RestController
@Slf4j
@RequiredArgsConstructor
public class AuthController implements AuthApi {

    private final AuthService authService;

    @Override
    public Response<SigninDto> signIn(SignInRequest signInRequest) {
        SigninDto signinDto = authService.signIn(signInRequest.getEmail(), signInRequest.getPassword());
        return Response.ok(signinDto);
    }

    @Override
    public Response<SigninDto> signInRefresh(SignInRefreshTokenRequest signInRefreshTokenRequest) {
        return null;
    }
}
