package xyz.bumbing.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import org.springframework.web.bind.annotation.*;
import xyz.bumbing.api.controller.dto.Response;
import xyz.bumbing.domain.dto.UserDto;
import xyz.bumbing.domain.type.GenderType;

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
    Response<UserDto> create(@RequestBody @Valid UserApi.CreateUserRequest createUserRequest);

    @PutMapping("/{id}")
    @Operation(summary = "회원수정", tags = {"User"})
    Response<UserDto> update(@PathVariable Long id, @RequestBody @Valid UserApi.UpdateUserRequest updateUserRequest);

    @DeleteMapping("/{id}")
    @Operation(summary = "회원탈퇴", tags = {"User"})
    Response<Void> withdraw(@PathVariable Long id);

    @GetMapping("/{id}")
    @Operation(summary = "회원정보", tags = {"User"})
    Response<UserDto> getUser(@PathVariable Long id);

    @Data
    @Schema
    public static class CreateUserRequest {

        @Schema
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*[!\"#$%&'()*+,\\-./:;<=>?@\\[\\]^_`{|}~\\\\])(?=.*[0-9])[0-9A-Za-z!\"#$%&'()*+,\\-./:;<=>?@\\[\\]^_`{|}~\\\\]{8,16}$", message = "1002")
        private String password;

        @Schema
        @NotBlank
        private String name;

        @Schema
        @Email
        private String email;

        @Schema
        @Pattern(regexp = "[0-9]{3}-[0-9]{3,4}-[0-9]{4}")
        private String phone;

        @Schema(example = "12345")
        private String zipCode;

        @Schema(example = "서울 A아파트")
        private String address1;

        @Schema(example = "101호")
        private String address2;

        @Schema(example = "M")
        private GenderType gender;

        @Schema(example = "900101")
        private LocalDate birthDay;


    }

    @Data
    @Schema
    public static class UpdateUserRequest {

        @Schema
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*[!\"#$%&'()*+,\\-./:;<=>?@\\[\\]^_`{|}~\\\\])(?=.*[0-9])[0-9A-Za-z!\"#$%&'()*+,\\-./:;<=>?@\\[\\]^_`{|}~\\\\]{8,16}$", message = "1002")
        private String password;

        @Schema(example = "12345")
        private String zipCode;

        @Schema(example = "서울 A아파트")
        private String address1;

        @Schema(example = "101호")
        private String address2;

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
