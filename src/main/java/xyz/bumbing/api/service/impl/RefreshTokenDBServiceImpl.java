package xyz.bumbing.api.service.impl;//package kr.co.everex.mora.auth.api.service.impl;
//
//import kr.co.everex.mora.auth.api.exception.ErrorCode;
//import kr.co.everex.mora.auth.api.exception.MoraException;
//import kr.co.everex.mora.auth.api.service.RefreshTokenService;
//import kr.co.everex.mora.member.domain.Login;
//import kr.co.everex.mora.member.repo.RefreshTokenRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class RefreshTokenDBServiceImpl implements RefreshTokenService {
//
//    private final RefreshTokenRepository refreshTokenRepository;
//
//    @Override
//    public void deleteRefreshToken(String username) {
//        this.refreshTokenRepository.deleteByUsername(username);
//    }
//
//    @Override
//    public void validateRefreshToken(String username, String refreshToken) {
//        this.refreshTokenRepository.findByUsernameAndRefreshToken(username, refreshToken).orElseThrow(() -> new MoraException(ErrorCode.ENTITY_NOT_FOUND));
//    }
//
//
//    @Override
//    public Long addRefreshToken(String username, String refreshToken, String ipAddress) {
//        Login token = Login.builder().refreshToken(refreshToken).username(username).connectIp(ipAddress).build();
//        Login entity = this.refreshTokenRepository.save(token);
//        return entity.getId();
//    }
//}
