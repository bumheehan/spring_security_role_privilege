package xyz.bumbing.auth.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RoleApiTest {

    @Autowired
    MockMvc mockMvc;

    ObjectMapper om;

    @BeforeAll
    public void init() {
        om = new ObjectMapper().registerModule(new JavaTimeModule());
    }

    @Test
    @DisplayName("회원 가입")
    public void create() throws Exception {

    }

    @Test
    @DisplayName("회원 수정")
    public void update() {

    }

    @Test
    @DisplayName("회원 비활성화")
    public void disable() {

    }

    @Test
    @DisplayName("회원 활성화")
    public void enable() {

    }

    @Test
    @DisplayName("회원 로그인")
    public void login() {

    }

    @Test
    @DisplayName("리프레시 토큰 로그인")
    public void loginRefresh() {

    }

}