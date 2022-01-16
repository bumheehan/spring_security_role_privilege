package xyz.bumbing.auth.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import xyz.bumbing.api.controller.PrivilegeApi;
import xyz.bumbing.api.controller.PrivilegeController;
import xyz.bumbing.api.controller.dto.Response;
import xyz.bumbing.domain.dto.PrivilegeDto;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureWebTestClient
class RoleApiTest {

    @Autowired
    WebTestClient webClient;

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
                .expectStatus().is2xxSuccessful()
                .expectBody(new ParameterizedTypeReference<Response<PrivilegeDto>>() {
                }).consumeWith(s->{
                    assertEquals(privilege,s.getResponseBody().getData().getName());
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
        webClient.delete().uri("/api/privilege/"+id)
                .exchange()
                .expectStatus().is2xxSuccessful()
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
                }).consumeWith(s->{
                    assertEquals(privilege1,s.getResponseBody().getData().getName());
                });
        createPrivilegeRequest.setName(privilege2);
        webClient.post().uri("/api/privilege").bodyValue(createPrivilegeRequest)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(new ParameterizedTypeReference<Response<PrivilegeDto>>() {
                }).consumeWith(s->{
                    assertEquals(privilege2,s.getResponseBody().getData().getName());
                });
        //when
        //then
        webClient.get().uri("/api/privilege/all")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(new ParameterizedTypeReference<Response<List<PrivilegeDto>>>() {
                }).consumeWith(s->{
                    assertEquals(privilege1,s.getResponseBody().getData().get(0).getName());
                    assertEquals(privilege2,s.getResponseBody().getData().get(1).getName());
                });

    }
    @Test
    @DisplayName("Role 추가")
    public void createRole() {
//        mockMvc.perform(post("/api/role"))

    }

    @Test
    @DisplayName("Role 수정")
    public void updateRole() {

    }

    @Test
    @DisplayName("Role 삭제")
    public void deleteRole() {

    }



}