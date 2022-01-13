package xyz.bumbing.api.service.impl;//package kr.co.everex.mora.auth.api.service.impl;

import lombok.RequiredArgsConstructor;
import xyz.bumbing.domain.entity.Address;
import xyz.bumbing.domain.entity.Role;
import xyz.bumbing.domain.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.bumbing.api.exception.ErrorCode;
import xyz.bumbing.api.exception.UserException;
import xyz.bumbing.api.service.UserService;
import xyz.bumbing.api.service.dto.CreateUserDto;
import xyz.bumbing.domain.dto.UserDto;
import xyz.bumbing.domain.repo.RoleRepository;
import xyz.bumbing.domain.repo.UserRepository;
import xyz.bumbing.domain.type.MemberStatusType;
import xyz.bumbing.security.dto.SingleTokenDto;
import xyz.bumbing.security.type.JwtType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true) // 모든 메소드 기본적용
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    @Transactional
    public UserDto signIn(CreateUserDto createUserDto) {

        validateUser(createUserDto.getEmail());

        Address address = Address.builder()
                .zipCode(createUserDto.getAddress().getZipCode())
                .address1(createUserDto.getAddress().getAddress1())
                .address2(createUserDto.getAddress().getAddress2())
                .build();
        User user = User.builder()
                .name(createUserDto.getName())
                .address(address)
                .birthDay(createUserDto.getBirthDay())
                .password(createUserDto.getPassword())
                .email(createUserDto.getEmail())
                .phone(createUserDto.getPhone())
                .gender(createUserDto.getGender())
                .status(MemberStatusType.Y)
                .build();

        userRepository.save(user);

        Role userRole = roleRepository.findByName("ROLE_USER").orElseThrow(()->new IllegalStateException("Not Found ROLE_USER"));
        user.appendRole(userRole);

        return UserDto.of(user);
    }

    private void validateUser(String email){
        userRepository.findByEmail(email).orElseThrow(()->new IllegalArgumentException("email 존재"));
    }


    @Override
    @Transactional
    public CreateMedicalStaffDto signInStaff(String email, String password, String ipAddress) {
        Member member = this.medicalStaffRepository.findByEmailAndStatus(email, MemberStatusType.ACTIVATION.getCode())
                .orElseThrow(() -> new UserException(ErrorCode.LOGIN_INPUT_INVALID));

        List<RoleType> memberRole = member.getAuthorities().stream().map(Authority::getRole).collect(Collectors.toList());
        Long memberId = member.getId();
        String memberRoleConcat = memberRole.stream().map(Enum::name).collect(Collectors.joining());

        // 비밀번호 확인
        if (!securityUtils.matches(password, member.getPassword())) {
            throw new UserException(ErrorCode.LOGIN_INPUT_INVALID);
        }

        // 이미 존재하는 토큰 있으면 삭제
        this.refreshTokenService.deleteRefreshToken(memberRoleConcat + memberId);

        //access refresh token 생성
        Map<String, Object> accessPayload = this.getAccessTokenPayload(memberId, memberRoleConcat);
        Map<String, Object> refreshPayload = this.getRefreshTokenPayload(memberId, memberRoleConcat);
        SingleTokenDto accessToken = this.jwtService.generateToken(accessPayload, JwtType.ACCESS);
        SingleTokenDto refreshToken = this.jwtService.generateToken(refreshPayload, JwtType.REFRESH);

        // 토큰 등록
        this.refreshTokenService.addRefreshToken(memberRoleConcat + memberId, refreshToken.getToken(), ipAddress);

        return CreateMedicalStaffDto.builder()
                .access(accessToken)
                .refresh(refreshToken)
                .role(memberRoleConcat)
                .id(memberId)
                .build();
    }

    @Override
    @Transactional
    public CreateMedicalStaffDto signInPatients(String email, String password, String ipAddress) {
        Member member = this.patientsRepository.findByEmailAndStatus(email, MemberStatusType.ACTIVATION.getCode())
                .orElseThrow(() -> new UserException(ErrorCode.LOGIN_INPUT_INVALID));

        List<RoleType> memberRole = member.getAuthorities().stream().map(Authority::getRole).collect(Collectors.toList());
        Long memberId = member.getId();
        String memberRoleConcat = memberRole.stream().map(Enum::name).collect(Collectors.joining());

        // 비밀번호 확인
        if (!securityUtils.matches(password, member.getPassword())) {
            throw new UserException(ErrorCode.LOGIN_INPUT_INVALID);
        }

        // 이미 존재하는 토큰 있으면 삭제
        this.refreshTokenService.deleteRefreshToken(memberRoleConcat + memberId);

        //access refresh token 생성
        Map<String, Object> accessPayload = this.getAccessTokenPayload(memberId, memberRoleConcat);
        Map<String, Object> refreshPayload = this.getRefreshTokenPayload(memberId, memberRoleConcat);
        SingleTokenDto accessToken = this.jwtService.generateToken(accessPayload, JwtType.ACCESS);
        SingleTokenDto refreshToken = this.jwtService.generateToken(refreshPayload, JwtType.REFRESH);

        // 토큰 등록
        this.refreshTokenService.addRefreshToken(memberRoleConcat + memberId, refreshToken.getToken(), ipAddress);

        return CreateMedicalStaffDto.builder()
                .access(accessToken)
                .refresh(refreshToken)
                .role(memberRoleConcat)
                .id(memberId)
                .build();
    }

    private Map<String, Object> getAccessTokenPayload(Long memberId, String role) {
        Map<String, Object> retVal = new HashMap<>();
        retVal.put("member", memberId);
        retVal.put("role", role);
        return retVal;
    }

    private Map<String, Object> getRefreshTokenPayload(Long memberId, String role) {
        Map<String, Object> retVal = new HashMap<>();
        retVal.put("member", memberId);
        retVal.put("role", role);
        return retVal;
    }

    @Override
    @Transactional
    public CreateMedicalStaffDto signIn(Long memberId, String memberRoleConcat, String refreshToken) {

        // RefreshToken 유효성 검사
        this.refreshTokenService.validateRefreshToken(memberRoleConcat + memberId, refreshToken);

        //access refresh token 생성
        Map<String, Object> accessPayload = this.getAccessTokenPayload(memberId, memberRoleConcat);
        SingleTokenDto accessToken = this.jwtService.generateToken(accessPayload, JwtType.ACCESS);

        return CreateMedicalStaffDto.builder()
                .access(accessToken)
                .role(memberRoleConcat)
                .id(memberId)
                .build();
    }


}
