package xyz.bumbing.api.controller;


import xyz.bumbing.api.controller.dto.LoginResponse;
import xyz.bumbing.api.controller.dto.Response;
import xyz.bumbing.api.controller.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController implements UserApi {

    @Override
    public Response<UserResponse.V1> create(CreateUserRequest createUserRequest) {
        return null;
    }

    @Override
    public Response<UserResponse.V1> update(Long id, UpdateUserRequest updateUserRequest) {
        return null;
    }

    @Override
    public Response<String> deleteMedicalStaff(Long id) {
        return null;
    }

    @Override
    public Response<LoginResponse> login(LoginRequest loginRequest, HttpServletRequest request) {
        return null;
    }

    @Override
    public Response<LoginResponse> loginRefresh(LoginRefreshRequest loginRefreshRequest, HttpServletRequest request, Authentication authentication) {
        return null;
    }
}
