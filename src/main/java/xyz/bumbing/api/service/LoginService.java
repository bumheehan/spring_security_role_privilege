package xyz.bumbing.api.service;

public interface LoginService {

    void deleteRefreshToken(String username);

    void validateRefreshToken(String username, String refreshToken);

    Long addRefreshToken(String username, String refreshToken, String ipAddress);
}