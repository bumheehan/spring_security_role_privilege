package xyz.bumbing.api.controller.dto;

import java.time.LocalDateTime;

public class LoginResponse {

    private String accessToken;
    private LocalDateTime accessTokenExpiredDate;
    private String refreshToken;
    private LocalDateTime refreshTokenExpiredDate;

}
