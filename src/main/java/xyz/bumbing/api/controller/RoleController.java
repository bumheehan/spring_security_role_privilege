package xyz.bumbing.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.Role;
import org.springframework.web.bind.annotation.RestController;
import xyz.bumbing.api.controller.dto.Response;
import xyz.bumbing.api.service.PrivilegeService;
import xyz.bumbing.api.service.RoleService;
import xyz.bumbing.domain.dto.PrivilegeDto;
import xyz.bumbing.domain.dto.RoleDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class RoleController implements RoleApi {

    private final RoleService roleService;

    @Override
    public Response<RoleDto> create(CreateRoleRequest createRoleRequest) {
        return Response.ok(roleService.create(createRoleRequest.getName(),createRoleRequest.getPrivileges()));
    }

    @Override
    public Response<RoleDto> update(Long id,UpdateRoleRequest updateRoleRequest) {
        return Response.ok(roleService.update(id,updateRoleRequest.getPrivileges()));
    }

    @Override
    public Response<Void> delete(Long id) {
        roleService.delete(id);
        return Response.ok(null);
    }

    @Override
    public Response<List<RoleDto>> getAll() {
        return Response.ok(roleService.getAll());
    }
}

