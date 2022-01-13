package xyz.bumbing.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import xyz.bumbing.api.controller.dto.LoginResponse;
import xyz.bumbing.api.controller.dto.Response;
import xyz.bumbing.api.controller.dto.UserResponse;
import xyz.bumbing.domain.dto.AddressDto;
import xyz.bumbing.domain.type.GenderType;
import xyz.bumbing.domain.type.MemberStatusType;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Tag(name = "User", description = "유저 API") // Swagger Tag
@RequestMapping("/api/user")
public interface UserApi {

    @PostMapping
    @Operation(summary = "회원가입", tags = {"User"})
    Response<UserResponse.V1> create(@RequestBody @Valid UserApi.CreateUserRequest createUserRequest);

    @PutMapping
    @Operation(summary = "회원수정", tags = {"User"})
    Response<UserResponse.V1> update(@RequestBody @Valid UserApi.UpdateUserRequest updateUserRequest);

    @DeleteMapping("/{id}")
    @Operation(summary = "회원탈퇴", tags = {"User"})
    Response<String> deleteMedicalStaff(@PathVariable Long id);

    @PostMapping("/login")
    @Operation(summary = "로그인", tags = {"User"})
    Response<LoginResponse> login(@RequestBody @Valid LoginRequest loginRequest, @Parameter(hidden = true) HttpServletRequest request);

    @PostMapping("/login/refresh")
    @Operation(summary = "로그인(리프레시 토큰)", tags = {"User"})
    Response<LoginResponse> loginRefresh(@RequestBody @Valid LoginRefreshRequest loginRefreshRequest, @Parameter(hidden = true) HttpServletRequest request, @Parameter(hidden = true) Authentication authentication);

    @Data
    @Schema
    public static class CreateUserRequest {

        @Schema
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*[!\"#$%&'()*+,\\-./:;<=>?@\\[\\]^_`{|}~\\\\])(?=.*[0-9])[0-9A-Za-z!\"#$%&'()*+,\\-./:;<=>?@\\[\\]^_`{|}~\\\\]{8,16}$", message = "1002")
        private String password;

        @Schema
        @NotBlank
        private  String name;

        @Schema
        @Email
        private  String email;

        @Schema
        @Pattern(regexp = "[0-9]{3}-[0-9]{3,4}-[0-9]{4}")
        private  String phone;

        @Schema(example = "12345")
        private String zipCode;

        @Schema(example = "서울 A아파트")
        private String address1;

        @Schema(example = "101호")
        private String address2;

        @Schema(example = "M")
        private  GenderType gender;

        @Schema(example = "900101")
        private  LocalDate birthDay;

    }
    @Data
    @Schema
    public static class UpdateUserRequest {

        @Schema
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*[!\"#$%&'()*+,\\-./:;<=>?@\\[\\]^_`{|}~\\\\])(?=.*[0-9])[0-9A-Za-z!\"#$%&'()*+,\\-./:;<=>?@\\[\\]^_`{|}~\\\\]{8,16}$", message = "1002")
        private String password;

        @Schema
        @NotBlank
        private  String name;

        @Schema
        @Email
        private  String email;

        @Schema
        @Pattern(regexp = "[0-9]{3}-[0-9]{3,4}-[0-9]{4}")
        private  String phone;

        @Schema(example = "12345")
        private String zipCode;

        @Schema(example = "서울 A아파트")
        private String address1;

        @Schema(example = "101호")
        private String address2;

        @Schema(example = "M")
        private  GenderType gender;

        @Schema(example = "900101")
        private  LocalDate birthDay;

    }
    @Data
    public static class LoginRequest {
        @NotBlank(message = "1009")
        private String licenseNumber;

        @NotBlank(message = "1002")
        private String password;
    }

    @Data
    public static class LoginRefreshRequest {

        @NotBlank(message = "1005")
        private String refreshToken;

    }

}
