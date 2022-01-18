package xyz.bumbing.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import xyz.bumbing.api.controller.dto.Response;
import xyz.bumbing.api.service.dto.SigninDto;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Tag(name = "User", description = "유저 API") // Swagger Tag
@RequestMapping("/api/token")
public interface AuthApi {


    @PostMapping
    @Operation(summary = "로그인", tags = {"User"})
    Response<SigninDto> signIn(@RequestBody @Valid AuthApi.SignInRequest signInTokenRequest);

    @PostMapping("/refresh")
    @Operation(summary = "로그인(리프레시 토큰)", tags = {"User"})
    Response<SigninDto> signInRefresh(@RequestBody @Valid AuthApi.SignInRefreshTokenRequest signInRefreshTokenRequest);

    @Data
    public static class SignInRequest {
        @NotBlank(message = "1009")
        private String email;

        @NotBlank(message = "1002")
        private String password;
    }

    @Data
    public static class SignInRefreshTokenRequest {
        @NotBlank(message = "1005")
        private String refreshToken;

    }

}
