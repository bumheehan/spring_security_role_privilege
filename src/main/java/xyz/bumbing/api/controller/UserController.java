package xyz.bumbing.api.controller;


import xyz.bumbing.auth.api.controller.dto.LoginResponse;
import xyz.bumbing.auth.api.controller.dto.MedicalStaffResponse;
import xyz.bumbing.auth.api.controller.dto.Response;
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
    public Response<MedicalStaffResponse.V1> createMedicalStaff(CreateUserRequest createMedicalStaffRequest) {
        return Response.ok(new MedicalStaffResponse.V1("testName"));
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
