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
import xyz.bumbing.domain.dto.PrivilegeDto;
import xyz.bumbing.domain.dto.RoleDto;
import xyz.bumbing.domain.entity.Role;
import xyz.bumbing.domain.type.GenderType;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.List;

@Tag(name = "Role", description = "역할 API") // Swagger Tag
@RequestMapping("/api/role")
public interface RoleApi {

    @PostMapping
    @Operation(summary = "역할 추가", tags = {"Role"})
    Response<RoleDto> create(@RequestBody @Valid RoleApi.CreateRoleRequest createUserRequest);

    @PutMapping("/{id}")
    @Operation(summary = "역할 수정", tags = {"Role"})
    Response<RoleDto> update(@PathVariable Long id, @RequestBody @Valid RoleApi.UpdateRoleRequest updateRoleRequest);

    @DeleteMapping("/{id}")
    @Operation(summary = "역할 삭제", tags = {"Role"})
    Response<Void> delete(@PathVariable Long id);

    @GetMapping("/all")
    @Operation(summary = "역할 조회", tags = {"Role"})
    Response<List<RoleDto>> getAll();

    @Data
    @Schema
    public static class CreateRoleRequest {
        @NotBlank
        @Schema(example = "test")
        private String name;

        @Schema
        private List<Long> privileges;
    }

    @Data
    @Schema
    public static class UpdateRoleRequest {
        @Schema
        private List<Long> privileges;
    }
}
