package xyz.bumbing.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.bumbing.api.controller.dto.Response;

@RestController
@Tag(name = "Test", description = "Test API") // Swagger Tag
@RequestMapping("/api/test")
public class TestController {


    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')")
    @Operation(description = "유저", tags = {"Test"})
    public Response<String> user() {
        return Response.ok("ok");
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(description = "관리자", tags = {"Test"})
    public Response<String> admin() {
        return Response.ok("ok");
    }

    @GetMapping("/user/config")
    @Operation(description = "유저", tags = {"Test"})
    public Response<String> userConfig() {
        return Response.ok("ok");
    }

    @GetMapping("/admin/config")
    @Operation(description = "관리자", tags = {"Test"})
    public Response<String> adminConfig() {
        return Response.ok("ok");
    }
}
