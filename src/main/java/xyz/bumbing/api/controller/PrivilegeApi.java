package xyz.bumbing.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import xyz.bumbing.api.controller.dto.LoginResponse;
import xyz.bumbing.api.controller.dto.Response;
import xyz.bumbing.api.controller.dto.UserResponse;
import xyz.bumbing.domain.dto.PrivilegeDto;
import xyz.bumbing.domain.type.GenderType;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.List;

@Tag(name = "Privilege", description = "권한 API") // Swagger Tag
@RequestMapping("/api/privilege")
public interface PrivilegeApi {

    @PostMapping
    @Operation(summary = "권한 추가", tags = {"Privilege"})
    Response<PrivilegeDto> create(@RequestBody @Valid PrivilegeApi.CreatePrivilegeRequest createUserRequest);

    @DeleteMapping("/{id}")
    @Operation(summary = "권한 삭제", tags = {"Privilege"})
    Response<Void> delete(@PathVariable Long id);

    @GetMapping("/all")
    @Operation(summary = "권한 조회", tags = {"Privilege"})
    Response<List<PrivilegeDto>> getAll();

    @Data
    @Schema
    public static class CreatePrivilegeRequest {
        @NotBlank
        @Schema(example = "test")
        private String name;
    }
}
