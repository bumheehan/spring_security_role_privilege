package xyz.bumbing.auth.api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import xyz.bumbing.api.controller.AuthApi;

import java.util.Objects;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureWebTestClient
class UserApiTest {

    @Autowired
    WebTestClient webClient;

    @Test
    @DisplayName("토큰 가져오기")
    public void getToken() throws Exception {
        AuthApi.SignInRequest request = new AuthApi.SignInRequest();
        request.setEmail("user@a.a");
        request.setPassword("test");

        webClient.post().uri("/api/token").bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody().jsonPath("$.data.access.token").exists()
                .jsonPath("$.data.refresh.token").exists()
                .consumeWith(s -> System.out.println(new String(Objects.requireNonNull(s.getResponseBodyContent()))));

    }

    @Test
    @DisplayName("회원 수정")
    public void update() {

    }
//
//    @Test
//    @DisplayName("회원 비활성화")
//    public void disable() {
//
//    }
//
//    @Test
//    @DisplayName("회원 활성화")
//    public void enable() {
//
//    }

    @Test
    @DisplayName("회원 로그인")
    public void login() {

    }

    @Test
    @DisplayName("리프레시 토큰 로그인")
    public void loginRefresh() {

    }
}