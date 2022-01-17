package xyz.bumbing.auth.api;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.web.reactive.server.WebTestClient;
import xyz.bumbing.api.controller.PrivilegeApi;
import xyz.bumbing.api.controller.RoleApi;
import xyz.bumbing.api.controller.dto.Response;
import xyz.bumbing.api.service.PrivilegeService;
import xyz.bumbing.api.service.RoleService;
import xyz.bumbing.domain.dto.PrivilegeDto;
import xyz.bumbing.domain.dto.RoleDto;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureWebTestClient
class RoleApiTest {

    @Autowired
    WebTestClient webClient;

    @Autowired
    PrivilegeService privilegeService;

    @Autowired
    RoleService roleService;

    @Test
    @DisplayName("Privilege  추가")
    @Order(1)
    public void createPrivilege() throws Exception {
        //given
        String privilege = "READ_BOARD";

        PrivilegeApi.CreatePrivilegeRequest createPrivilegeRequest = new PrivilegeApi.CreatePrivilegeRequest();
        createPrivilegeRequest.setName(privilege);
        //when
        //then
        webClient.post().uri("/api/privilege").bodyValue(createPrivilegeRequest)
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<Response<PrivilegeDto>>() {
                }).consumeWith(s -> {
                    assertEquals(privilege, s.getResponseBody().getData().getName());
                });
    }

    @Test
    @DisplayName("Privilege 삭제")
    @Order(2)
    public void updatePrivilege() {
        //given
        Long id = 1L;
        //when
        //then
        webClient.delete().uri("/api/privilege/" + id)
                .exchange()
                .expectStatus().isOk()
                .expectBody().json("{}");
    }

    @Test
    @DisplayName("Privilege 보기")
    @Order(3)
    public void getAllPrivilege() {
        //given
        String privilege1 = "READ_BOARD";
        String privilege2 = "WRITE_BOARD";
        PrivilegeApi.CreatePrivilegeRequest createPrivilegeRequest = new PrivilegeApi.CreatePrivilegeRequest();
        createPrivilegeRequest.setName(privilege1);
        webClient.post().uri("/api/privilege").bodyValue(createPrivilegeRequest)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(new ParameterizedTypeReference<Response<PrivilegeDto>>() {
                }).consumeWith(s -> {
                    assertEquals(privilege1, s.getResponseBody().getData().getName());
                });
        createPrivilegeRequest.setName(privilege2);
        webClient.post().uri("/api/privilege").bodyValue(createPrivilegeRequest)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(new ParameterizedTypeReference<Response<PrivilegeDto>>() {
                }).consumeWith(s -> {
                    assertEquals(privilege2, s.getResponseBody().getData().getName());
                });
        //when
        //then
        webClient.get().uri("/api/privilege/all")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(new ParameterizedTypeReference<Response<List<PrivilegeDto>>>() {
                }).consumeWith(s -> {
                    assertEquals(privilege1, s.getResponseBody().getData().get(0).getName());
                    assertEquals(privilege2, s.getResponseBody().getData().get(1).getName());
                });

    }

    @Test
    @DisplayName("Role 추가")
    @Order(4)
    public void createRole() {
        //given

        List<String> privilegeNames = List.of("READ_BOARD", "READ_USER", "WRITE_BOARD", "WRITE_USER");
        List<Long> privilegeIds = List.of(2L, 3L, 4L, 5L);
        privilegeNames.forEach(privilegeService::create);

        RoleApi.CreateRoleRequest createRoleRequest = new RoleApi.CreateRoleRequest();
        createRoleRequest.setName("ROLE_ADMIN");
        createRoleRequest.setPrivileges(privilegeIds);

        Response<RoleDto> responseBody = webClient.post().uri("/api/role").bodyValue(createRoleRequest)
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<Response<RoleDto>>() {
                }).returnResult().getResponseBody();

        assert responseBody != null;
        System.out.println(responseBody);
        List<String> resultNames = responseBody.getData().getPrivileges().stream().map(PrivilegeDto::getName).collect(Collectors.toList());
        List<Long> resultIds = responseBody.getData().getPrivileges().stream().map(PrivilegeDto::getId).collect(Collectors.toList());

        for (String expected : privilegeNames) {
            assertTrue(resultNames.contains(expected));
        }
        for (Long expected : privilegeIds) {
            assertTrue(resultIds.contains(expected));
        }

    }

    @Test
    @DisplayName("Role 수정")
    @Order(5)
    public void updateRole() {

        privilegeService.create("SUPER");
        List<String> privilegeNames = List.of("READ_BOARD", "SUPER");
        List<Long> privilegeIds = List.of(2L, 6L);

        RoleApi.UpdateRoleRequest updateRoleRequest = new RoleApi.UpdateRoleRequest();
        updateRoleRequest.setPrivileges(privilegeIds);


        Response<RoleDto> responseBody = webClient.put().uri("/api/role/1").bodyValue(updateRoleRequest)
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<Response<RoleDto>>() {
                }).returnResult().getResponseBody();

        assert responseBody != null;
        System.out.println(responseBody);
        List<String> resultNames = responseBody.getData().getPrivileges().stream().map(PrivilegeDto::getName).collect(Collectors.toList());
        List<Long> resultIds = responseBody.getData().getPrivileges().stream().map(PrivilegeDto::getId).collect(Collectors.toList());

        for (String expected : privilegeNames) {
            assertTrue(resultNames.contains(expected));
        }
        for (Long expected : privilegeIds) {
            assertTrue(resultIds.contains(expected));
        }
    }

    @Test
    @DisplayName("Role 삭제")
    @Order(6)
    public void deleteRole() {
        webClient.delete().uri("/api/role/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody().json("{}");
    }


}