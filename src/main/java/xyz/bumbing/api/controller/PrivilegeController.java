package xyz.bumbing.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import xyz.bumbing.api.controller.dto.Response;
import xyz.bumbing.api.service.PrivilegeService;
import xyz.bumbing.domain.dto.PrivilegeDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class PrivilegeController implements PrivilegeApi{

    private final PrivilegeService privilegeService;

    @Override
    public Response<PrivilegeDto> create(CreatePrivilegeRequest createUserRequest) {
        return Response.ok(privilegeService.create(createUserRequest.getName()));
    }

    @Override
    public Response<Void> delete(Long id) {
        privilegeService.delete(id);
        return Response.ok(null);
    }

    @Override
    public Response<List<PrivilegeDto>> getAll() {
        return Response.ok(privilegeService.getAll());
    }
}
