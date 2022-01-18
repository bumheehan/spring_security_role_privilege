package xyz.bumbing.api.service;

import xyz.bumbing.api.service.dto.SigninDto;

public interface AuthService {

    SigninDto signIn(String phone, String password);

    SigninDto signIn(String refreshToken);

}